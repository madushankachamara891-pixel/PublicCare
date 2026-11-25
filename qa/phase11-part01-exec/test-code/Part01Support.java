package lk.publiccare.backend.part01;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.imageio.ImageIO;

/**
 * Shared helpers for the Part 01 (Section 3.2) execution campaign.
 *
 * <p>All data is synthetic and generated at run time. Observations are appended to
 * {@code target/part01-observations.txt} so that every actual result in the report can be traced
 * to a line written by the test run itself.
 */
final class Part01Support {
    private Part01Support() { }

    static final Path OBSERVATIONS = Path.of("target", "part01-observations.txt");

    static synchronized void observe(String caseId, String text) {
        String line = Instant.now() + " | " + caseId + " | " + text.replace('\n', ' ') + System.lineSeparator();
        try {
            Files.createDirectories(OBSERVATIONS.getParent());
            Files.writeString(OBSERVATIONS, line, StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException exception) {
            throw new UncheckedIOException(exception);
        }
    }

    static String uniqueEmail(String prefix) {
        return prefix + "-" + UUID.randomUUID().toString().substring(0, 8) + "@qa.example.test";
    }

    static String repeat(char c, int n) {
        char[] chars = new char[n];
        Arrays.fill(chars, c);
        return new String(chars);
    }

    static String abbreviate(String body) {
        if (body == null) return "null";
        return body.length() <= 220 ? body : body.substring(0, 220) + "…(" + body.length() + " chars)";
    }

    // ---------------------------------------------------------------- images (synthetic)

    static byte[] png(int width, int height) {
        return write(new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY), "png");
    }

    static byte[] jpg(int width, int height) {
        return write(new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB), "jpg");
    }

    static byte[] gif(int width, int height) {
        return write(new BufferedImage(width, height, BufferedImage.TYPE_BYTE_INDEXED), "gif");
    }

    private static byte[] write(BufferedImage image, String format) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            if (!ImageIO.write(image, format, out)) throw new IllegalStateException("No writer for " + format);
            return out.toByteArray();
        } catch (IOException exception) {
            throw new UncheckedIOException(exception);
        }
    }

    /** A valid PNG padded with trailing zero bytes to an exact total size. */
    static byte[] pngOfExactSize(int totalBytes) {
        byte[] base = png(16, 16);
        return Arrays.copyOf(base, totalBytes);
    }

    /** A JPG signature followed by bytes that are not a JPG image. */
    static byte[] corruptJpg() {
        byte[] bytes = new byte[64];
        bytes[0] = (byte) 0xff; bytes[1] = (byte) 0xd8; bytes[2] = (byte) 0xff;
        for (int i = 3; i < bytes.length; i++) bytes[i] = (byte) 0x41;
        return bytes;
    }

    /** Minimal lossless WebP (RIFF + VP8L chunk, 1 x 1) that follows the structure ReportImageService checks. */
    static byte[] webpVp8l() {
        return riff(chunk("VP8L", new byte[] {0x2f, 0, 0, 0, 0}));
    }

    /** Minimal lossy WebP (RIFF + "VP8 " chunk with key-frame start code, 1 x 1). */
    static byte[] webpVp8() {
        return riff(chunk("VP8 ", new byte[] {0, 0, 0, (byte) 0x9d, 0x01, 0x2a, 1, 0, 1, 0}));
    }

    /** Extended WebP: VP8X header (1 x 1 canvas) followed by a VP8L image chunk. */
    static byte[] webpVp8x() {
        byte[] vp8x = chunk("VP8X", new byte[10]);
        byte[] vp8l = chunk("VP8L", new byte[] {0x2f, 0, 0, 0, 0});
        byte[] both = new byte[vp8x.length + vp8l.length];
        System.arraycopy(vp8x, 0, both, 0, vp8x.length);
        System.arraycopy(vp8l, 0, both, vp8x.length, vp8l.length);
        return riff(both);
    }

    static byte[] chunk(String fourCc, byte[] payload) {
        int padded = payload.length + (payload.length & 1);
        byte[] out = new byte[8 + padded];
        System.arraycopy(fourCc.getBytes(StandardCharsets.US_ASCII), 0, out, 0, 4);
        littleEndian(out, 4, payload.length);
        System.arraycopy(payload, 0, out, 8, payload.length);
        return out;
    }

    static byte[] riff(byte[] chunks) {
        byte[] out = new byte[12 + chunks.length];
        System.arraycopy("RIFF".getBytes(StandardCharsets.US_ASCII), 0, out, 0, 4);
        littleEndian(out, 4, out.length - 8);
        System.arraycopy("WEBP".getBytes(StandardCharsets.US_ASCII), 0, out, 8, 4);
        System.arraycopy(chunks, 0, out, 12, chunks.length);
        return out;
    }

    static void littleEndian(byte[] target, int offset, int value) {
        target[offset] = (byte) value;
        target[offset + 1] = (byte) (value >>> 8);
        target[offset + 2] = (byte) (value >>> 16);
        target[offset + 3] = (byte) (value >>> 24);
    }

    // ---------------------------------------------------------------- HTTP client with its own cookie jar

    record Resp(int status, String body, byte[] bytes, java.net.http.HttpHeaders headers) {
        List<String> setCookies() { return headers.allValues("set-cookie"); }
        String header(String name) { return headers.firstValue(name).orElse(null); }
        @Override public String toString() { return status + " " + abbreviate(body); }
    }

    /** One API client ("browser") that keeps its own cookies, like the SPA. */
    static final class Client {
        private static final HttpClient HTTP = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.NEVER).connectTimeout(Duration.ofSeconds(10)).build();
        final String name;
        final String base;
        final Map<String, String> cookies = new LinkedHashMap<>();
        String csrfToken;

        Client(String name, int port) {
            this.name = name;
            this.base = "http://localhost:" + port;
        }

        Resp get(String path) { return send("GET", path, null, null, false, Map.of()); }
        Resp postJson(String path, String json, boolean csrf) { return send("POST", path, json.getBytes(StandardCharsets.UTF_8), "application/json", csrf, Map.of()); }
        Resp putJson(String path, String json, boolean csrf) { return send("PUT", path, json.getBytes(StandardCharsets.UTF_8), "application/json", csrf, Map.of()); }
        Resp patchJson(String path, String json, boolean csrf) { return send("PATCH", path, json.getBytes(StandardCharsets.UTF_8), "application/json", csrf, Map.of()); }
        Resp delete(String path, boolean csrf) { return send("DELETE", path, null, null, csrf, Map.of()); }

        Resp multipart(String path, List<Part> parts, boolean csrf) {
            String boundary = "----part01" + UUID.randomUUID().toString().replace("-", "");
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            try {
                for (Part part : parts) {
                    out.write(("--" + boundary + "\r\n").getBytes(StandardCharsets.UTF_8));
                    if (part.filename() == null) {
                        out.write(("Content-Disposition: form-data; name=\"" + part.name() + "\"\r\n\r\n").getBytes(StandardCharsets.UTF_8));
                    } else {
                        out.write(("Content-Disposition: form-data; name=\"" + part.name() + "\"; filename=\"" + part.filename() + "\"\r\n"
                                + "Content-Type: " + part.contentType() + "\r\n\r\n").getBytes(StandardCharsets.UTF_8));
                    }
                    out.write(part.content());
                    out.write("\r\n".getBytes(StandardCharsets.UTF_8));
                }
                out.write(("--" + boundary + "--\r\n").getBytes(StandardCharsets.UTF_8));
            } catch (IOException exception) {
                throw new UncheckedIOException(exception);
            }
            return send("POST", path, out.toByteArray(), "multipart/form-data; boundary=" + boundary, csrf, Map.of());
        }

        Resp options(String path, Map<String, String> headers) { return send("OPTIONS", path, null, null, false, headers); }

        /** Fetches a CSRF token the way the SPA does: GET /api/auth/csrf, token from the body. */
        String refreshCsrf() {
            Resp response = get("/api/auth/csrf");
            if (response.status() != 200) throw new IllegalStateException("CSRF endpoint returned " + response);
            String body = response.body();
            int start = body.indexOf("\"token\":\"") + 9;
            csrfToken = body.substring(start, body.indexOf('"', start));
            return csrfToken;
        }

        Resp send(String method, String path, byte[] body, String contentType, boolean csrf, Map<String, String> headers) {
            try {
                HttpRequest.Builder builder = HttpRequest.newBuilder(URI.create(base + path)).timeout(Duration.ofSeconds(60))
                        .method(method, body == null ? HttpRequest.BodyPublishers.noBody() : HttpRequest.BodyPublishers.ofByteArray(body));
                if (contentType != null) builder.header("Content-Type", contentType);
                if (csrf) {
                    if (csrfToken == null || !cookies.containsKey("XSRF-TOKEN")) refreshCsrf();
                    builder.header("X-XSRF-TOKEN", csrfToken);
                }
                headers.forEach(builder::header);
                if (!cookies.isEmpty()) {
                    List<String> pairs = new ArrayList<>();
                    cookies.forEach((k, v) -> pairs.add(k + "=" + v));
                    builder.header("Cookie", String.join("; ", pairs));
                }
                HttpResponse<byte[]> response = HTTP.send(builder.build(), HttpResponse.BodyHandlers.ofByteArray());
                for (String setCookie : response.headers().allValues("set-cookie")) {
                    String first = setCookie.split(";", 2)[0];
                    int eq = first.indexOf('=');
                    String cookieName = first.substring(0, eq).trim();
                    String value = first.substring(eq + 1).trim();
                    boolean expired = setCookie.toLowerCase(java.util.Locale.ROOT).contains("max-age=0") || value.isEmpty();
                    if (expired) cookies.remove(cookieName); else cookies.put(cookieName, value);
                }
                byte[] bytes = response.body();
                return new Resp(response.statusCode(), new String(bytes, StandardCharsets.UTF_8), bytes, response.headers());
            } catch (IOException exception) {
                throw new UncheckedIOException(exception);
            } catch (InterruptedException exception) {
                Thread.currentThread().interrupt();
                throw new IllegalStateException(exception);
            }
        }
    }

    record Part(String name, String filename, String contentType, byte[] content) {
        static Part file(String filename, String contentType, byte[] content) { return new Part("image", filename, contentType, content); }
        static Part field(String name, String value) { return new Part(name, null, null, value.getBytes(StandardCharsets.UTF_8)); }
    }
}
