package lk.publiccare.backend.part01;

import static lk.publiccare.backend.part01.Part01Support.observe;
import static lk.publiccare.backend.part01.Part01Support.repeat;
import static lk.publiccare.backend.part01.Part01Support.uniqueEmail;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lk.publiccare.backend.part01.Part01Support.Client;
import lk.publiccare.backend.part01.Part01Support.Part;
import lk.publiccare.backend.part01.Part01Support.Resp;
import lk.publiccare.backend.report.PublicCareReport;
import lk.publiccare.backend.report.PublicReportService;
import lk.publiccare.backend.report.ReportNotFoundException;
import lk.publiccare.backend.report.ReportProgressUpdate;
import lk.publiccare.backend.report.ReportProgressUpdateRepository;
import lk.publiccare.backend.report.ReportRepository;
import lk.publiccare.backend.report.ReportService;
import lk.publiccare.backend.report.ReportDtos;
import lk.publiccare.backend.report.ReportStatus;
import lk.publiccare.backend.user.PublicCareUser;
import lk.publiccare.backend.user.UserRepository;
import lk.publiccare.backend.user.UserRole;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Part 01 (EC4206 Section 3.2) API and service-level test cases, executed over real HTTP against the
 * embedded Tomcat server of the unchanged PublicCare backend on in-memory H2 (PostgreSQL mode).
 * Every request goes through the production security filter chain (session, CSRF, CORS, roles).
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
        "spring.datasource.url=jdbc:h2:mem:part01-api;MODE=PostgreSQL;DB_CLOSE_DELAY=-1",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.flyway.enabled=false",
        "publiccare.upload.directory=target/part01-uploads"
})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.MethodName.class)
class Part01ApiExecutionTest {
    private static final String PASSWORD = "Synthetic-Pass-123!";
    private static final String DENIED = "{\"error\":\"Access denied.\"}";

    @LocalServerPort int port;
    @Autowired ObjectMapper json;
    @Autowired UserRepository users;
    @Autowired ReportRepository reports;
    @Autowired ReportProgressUpdateRepository updates;
    @Autowired ReportService reportService;
    @Autowired PublicReportService publicReportService;
    @Autowired PasswordEncoder encoder;
    @Autowired JdbcTemplate jdbc;

    Client admin;
    long adminId;
    String adminEmail;

    @BeforeAll
    void fixtures() {
        // PostgreSQL generates reports.reference (V2). Hibernate's H2 schema cannot, so the column is relaxed
        // here and filled with the same expression after each insert (see emulateReference()).
        jdbc.execute("alter table reports alter column reference drop not null");
        adminEmail = uniqueEmail("qa-admin");
        PublicCareUser saved = users.saveAndFlush(new PublicCareUser("QA Admin", adminEmail, encoder.encode(PASSWORD), UserRole.ADMIN));
        adminId = saved.getId();
        admin = new Client("admin", port);
        Resp login = admin.postJson("/api/auth/login", body("email", adminEmail, "password", PASSWORD), true);
        assertThat(login.status()).as("admin fixture login").isEqualTo(200);
    }

    // ------------------------------------------------------------------ helpers

    private String body(Object... keyValues) {
        Map<String, Object> map = new LinkedHashMap<>();
        for (int i = 0; i < keyValues.length; i += 2) map.put((String) keyValues[i], keyValues[i + 1]);
        try { return json.writeValueAsString(map); } catch (Exception e) { throw new IllegalStateException(e); }
    }

    private JsonNode parse(Resp response) {
        try { return json.readTree(response.body()); } catch (Exception e) { throw new IllegalStateException("Not JSON: " + response, e); }
    }

    private void emulateReference() {
        jdbc.update("update reports set reference = 'PC-' || lpad(cast(id as varchar), 8, '0') where reference is null");
    }

    private record Citizen(Client client, String email, long id) { }

    private Citizen citizen(String label) {
        Client client = new Client(label, port);
        String email = uniqueEmail(label);
        Resp reg = client.postJson("/api/auth/register", body("fullName", "QA " + label, "email", email, "password", PASSWORD), true);
        assertThat(reg.status()).as("register fixture " + label).isEqualTo(201);
        Resp login = client.postJson("/api/auth/login", body("email", email, "password", PASSWORD), true);
        assertThat(login.status()).as("login fixture " + label).isEqualTo(200);
        return new Citizen(client, email, parse(login).get("id").asLong());
    }

    private long createReport(Client owner, String title, String category) {
        Resp r = owner.postJson("/api/reports", body("title", title, "description", "Synthetic description",
                "category", category, "location", "QA Street"), true);
        assertThat(r.status()).as("create report fixture: " + r).isEqualTo(201);
        emulateReference();
        return parse(r).get("id").asLong();
    }

    private Resp upload(Client c, long reportId, String filename, String type, byte[] content) {
        return c.multipart("/api/reports/" + reportId + "/image", List.of(Part.file(filename, type, content)), true);
    }

    private Resp setStatus(long reportId, String status, String note) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("status", status);
        map.put("adminUpdateNote", note);
        try { return admin.patchJson("/api/admin/reports/" + reportId + "/status", json.writeValueAsString(map), true); }
        catch (Exception e) { throw new IllegalStateException(e); }
    }

    private Resp progress(long reportId, String status, String note, Part image) {
        List<Part> parts = new ArrayList<>();
        if (status != null) parts.add(Part.field("status", status));
        if (note != null) parts.add(Part.field("note", note));
        if (image != null) parts.add(image);
        return admin.multipart("/api/admin/reports/" + reportId + "/progress-updates", parts, true);
    }

    private JsonNode adminReport(long id) { return parse(admin.get("/api/admin/reports/" + id)); }

    private String cat() { return "QA-" + UUID.randomUUID().toString().substring(0, 8); }

    // ------------------------------------------------------------------ API cases

    @Test @DisplayName("API-01 CSRF token endpoint")
    void api01() {
        Client anonymous = new Client("anonymous", port);
        Resp r = anonymous.get("/api/auth/csrf");
        observe("API-01", "GET /api/auth/csrf -> " + r.status() + "; token length=" + parse(r).get("token").asText().length()
                + "; Set-Cookie=" + r.setCookies());
        assertThat(r.status()).isEqualTo(200);
        assertThat(parse(r).get("token").asText()).isNotBlank();
        assertThat(r.setCookies()).anyMatch(c -> c.startsWith("XSRF-TOKEN="));
    }

    @Test @DisplayName("API-02 Registration creates a Citizen and returns no password data")
    void api02() {
        Client c = new Client("api02", port);
        String email = uniqueEmail("api02");
        Resp r = c.postJson("/api/auth/register", body("fullName", "QA Api02", "email", email, "password", PASSWORD, "role", "ADMIN"), true);
        JsonNode node = parse(r);
        List<String> fields = new ArrayList<>();
        node.fieldNames().forEachRemaining(fields::add);
        observe("API-02", "register with \"role\":\"ADMIN\" -> " + r.status() + "; fields=" + fields + "; role=" + node.get("role").asText());
        assertThat(r.status()).isEqualTo(201);
        assertThat(fields).containsExactlyInAnyOrder("id", "fullName", "email", "role", "createdAt");
        assertThat(node.get("role").asText()).isEqualTo("CITIZEN");
        Resp login = c.postJson("/api/auth/login", body("email", email, "password", PASSWORD), true);
        Resp me = c.get("/api/auth/me");
        observe("API-02", "login -> " + login.status() + "; GET /api/auth/me -> " + me.status() + " role=" + parse(me).get("role").asText());
        assertThat(parse(me).get("role").asText()).isEqualTo("CITIZEN");
    }

    @Test @DisplayName("API-03 Registration input boundaries")
    void api03() {
        Client c = new Client("api03", port);
        String domain = repeat('b', 63) + "." + repeat('c', 63) + ".";
        String local = repeat('a', 64);
        String email254 = local + "@" + domain + repeat('d', 56) + ".test";
        String email255 = local + "@" + domain + repeat('e', 57) + ".test";
        assertThat(email254).hasSize(254);
        assertThat(email255).hasSize(255);
        record Row(String label, String name, String email, String password, int expected) { }
        List<Row> rows = List.of(
                new Row("name 120", repeat('N', 120), uniqueEmail("b1"), PASSWORD, 201),
                new Row("name 121", repeat('N', 121), uniqueEmail("b2"), PASSWORD, 400),
                new Row("password 11", "QA", uniqueEmail("b3"), repeat('p', 11), 400),
                new Row("password 12", "QA", uniqueEmail("b4"), repeat('p', 12), 201),
                new Row("password 72", "QA", uniqueEmail("b5"), repeat('p', 72), 201),
                new Row("password 73", "QA", uniqueEmail("b6"), repeat('p', 73), 400),
                new Row("password 37 x e-acute (74 bytes)", "QA", uniqueEmail("b7"), repeat('é', 37), 400),
                new Row("email 254", "QA", email254, PASSWORD, 201),
                new Row("email 255", "QA", email255, PASSWORD, 400),
                new Row("blank name", "", uniqueEmail("b8"), PASSWORD, 400),
                new Row("whitespace-only name", "   ", uniqueEmail("b9"), PASSWORD, 400),
                new Row("blank email", "QA", "", PASSWORD, 400),
                new Row("whitespace-only email", "QA", "   ", PASSWORD, 400),
                new Row("blank password", "QA", uniqueEmail("b10"), "", 400),
                new Row("whitespace-only password (12 spaces)", "QA", uniqueEmail("b11"), repeat(' ', 12), 400));
        for (Row row : rows) {
            Resp r = c.postJson("/api/auth/register", body("fullName", row.name(), "email", row.email(), "password", row.password()), true);
            observe("API-03", row.label() + " -> " + r);
            assertThat(r.status()).as(row.label()).isEqualTo(row.expected());
            if (r.status() == 400) assertThat(parse(r).get("error").asText()).as(row.label() + " error body").isNotBlank();
        }
    }

    @Test @DisplayName("API-05 State-changing requests without a CSRF token are rejected")
    void api05() {
        Citizen a = citizen("api05a");
        long own = createReport(a.client(), "API-05 own report", cat());
        long disposable = createReport(a.client(), "API-05 disposable", cat());
        long before = reports.count();
        String email = uniqueEmail("api05reg");
        Map<String, Resp> noToken = new LinkedHashMap<>();
        Client fresh = new Client("api05-fresh", port);
        noToken.put("POST /api/auth/register", fresh.postJson("/api/auth/register", body("fullName", "QA", "email", email, "password", PASSWORD), false));
        noToken.put("POST /api/auth/login", fresh.postJson("/api/auth/login", body("email", a.email(), "password", PASSWORD), false));
        noToken.put("POST /api/auth/logout (citizen session)", a.client().send("POST", "/api/auth/logout", null, null, false, Map.of()));
        String report = body("title", "Changed", "description", "D", "category", "C", "location", "L");
        noToken.put("POST /api/reports", a.client().postJson("/api/reports", report, false));
        noToken.put("PUT /api/reports/{id}", a.client().putJson("/api/reports/" + own, report, false));
        noToken.put("POST /api/reports/{id}/image", a.client().multipart("/api/reports/" + own + "/image",
                List.of(Part.file("a.png", "image/png", Part01Support.png(4, 4))), false));
        noToken.put("POST /api/admin/reports/{id}/progress-updates", admin.multipart("/api/admin/reports/" + own + "/progress-updates",
                List.of(Part.field("status", "IN_PROGRESS")), false));
        noToken.put("PATCH /api/admin/reports/{id}/status", admin.patchJson("/api/admin/reports/" + own + "/status", body("status", "RESOLVED"), false));
        noToken.put("DELETE /api/admin/reports/{id}", admin.delete("/api/admin/reports/" + disposable, false));
        noToken.forEach((k, v) -> observe("API-05", "without X-XSRF-TOKEN: " + k + " -> " + v));
        noToken.forEach((k, v) -> {
            assertThat(v.status()).as(k).isEqualTo(403);
            assertThat(v.body()).as(k).isEqualTo(DENIED);
        });
        JsonNode ownAfter = adminReport(own);
        observe("API-05", "after rejected requests: reports count " + before + " -> " + reports.count() + "; own report title='"
                + ownAfter.get("title").asText() + "' status=" + ownAfter.get("status").asText() + "; disposable still exists="
                + reports.existsById(disposable) + "; account created for " + email + "=" + users.findByEmail(email).isPresent());
        assertThat(reports.count()).isEqualTo(before);
        assertThat(ownAfter.get("title").asText()).isEqualTo("API-05 own report");
        assertThat(ownAfter.get("status").asText()).isEqualTo("PENDING");
        assertThat(reports.existsById(disposable)).isTrue();
        assertThat(users.findByEmail(email)).isEmpty();
        // Step 5 – controls with the token (logout last).
        Map<String, Resp> control = new LinkedHashMap<>();
        control.put("POST /api/auth/register", fresh.postJson("/api/auth/register", body("fullName", "QA", "email", email, "password", PASSWORD), true));
        control.put("POST /api/auth/login", fresh.postJson("/api/auth/login", body("email", email, "password", PASSWORD), true));
        control.put("POST /api/reports", a.client().postJson("/api/reports", report, true));
        control.put("PUT /api/reports/{id}", a.client().putJson("/api/reports/" + own, report, true));
        control.put("POST /api/reports/{id}/image", upload(a.client(), own, "a.png", "image/png", Part01Support.png(4, 4)));
        control.put("POST /api/admin/reports/{id}/progress-updates", progress(own, "IN_PROGRESS", null, null));
        control.put("PATCH /api/admin/reports/{id}/status", setStatus(own, "RESOLVED", null));
        control.put("DELETE /api/admin/reports/{id}", admin.delete("/api/admin/reports/" + disposable, true));
        control.put("POST /api/auth/logout (citizen session)", a.client().send("POST", "/api/auth/logout", null, null, true, Map.of()));
        emulateReference();
        control.forEach((k, v) -> observe("API-05", "control with X-XSRF-TOKEN: " + k + " -> " + v.status()));
        control.forEach((k, v) -> assertThat(v.status()).as(k).isBetween(200, 299));
    }

    @Test @DisplayName("API-06 Login starts a session and rotates the session ID")
    void api06() {
        Citizen a = citizen("api06");
        Client c = new Client("api06-client", port);
        c.refreshCsrf();
        String preLogin = c.cookies.get("JSESSIONID");
        observe("API-06", "step 1: JSESSIONID held before the first login = " + preLogin
                + " (GET /api/auth/csrf does not create a session)");
        Resp first = c.postJson("/api/auth/login", body("email", a.email(), "password", PASSWORD), true);
        String sessionOne = c.cookies.get("JSESSIONID");
        String sessionCookie = first.setCookies().stream().filter(s -> s.startsWith("JSESSIONID=")).findFirst().orElse("");
        observe("API-06", "first login -> " + first.status() + "; Set-Cookie JSESSIONID attributes: "
                + sessionCookie.replaceFirst("JSESSIONID=[^;]*", "JSESSIONID=<id>"));
        // Method agreed for this run: the pre-login session is the session of an earlier login of the same client.
        Resp second = c.postJson("/api/auth/login", body("email", a.email(), "password", PASSWORD), true);
        String sessionTwo = c.cookies.get("JSESSIONID");
        Resp me = c.get("/api/auth/me");
        observe("API-06", "second login while holding session 1 -> " + second.status() + "; session ID changed=" + !sessionOne.equals(sessionTwo)
                + "; GET /api/auth/me -> " + me.status() + "; Secure attribute present=" + sessionCookie.toLowerCase().contains("secure"));
        assertThat(first.status()).isEqualTo(200);
        assertThat(parse(first).get("email").asText()).isEqualTo(a.email().toLowerCase());
        assertThat(sessionCookie).containsIgnoringCase("HttpOnly").containsIgnoringCase("SameSite=Lax");
        assertThat(sessionTwo).isNotEqualTo(sessionOne);
        assertThat(me.status()).isEqualTo(200);
    }

    @Test @DisplayName("API-07 Generic 401 and 72-byte password limit on login")
    void api07() {
        Citizen a = citizen("api07");
        Client c = new Client("api07-client", port);
        Resp wrong = c.postJson("/api/auth/login", body("email", a.email(), "password", "Wrong-Pass-123!"), true);
        Resp unknown = c.postJson("/api/auth/login", body("email", uniqueEmail("nobody"), "password", PASSWORD), true);
        Resp ascii73 = c.postJson("/api/auth/login", body("email", a.email(), "password", repeat('p', 73)), true);
        Resp bytes73 = c.postJson("/api/auth/login", body("email", a.email(), "password", repeat('é', 36) + "p"), true);
        observe("API-07", "wrong password -> " + wrong + "; unregistered email -> " + unknown + "; bodies identical=" + wrong.body().equals(unknown.body()));
        observe("API-07", "73 ASCII characters (73 bytes) -> " + ascii73 + "; 37 characters / 73 UTF-8 bytes -> " + bytes73);
        assertThat(wrong.status()).isEqualTo(401);
        assertThat(unknown.status()).isEqualTo(401);
        assertThat(wrong.body()).isEqualTo(unknown.body()).isEqualTo("{\"error\":\"Invalid email or password.\"}");
        assertThat(ascii73.status()).isEqualTo(400);
        assertThat(bytes73.status()).isEqualTo(400);
    }

    @Test @DisplayName("API-08 Current user before, during and after a session")
    void api08() {
        Client anonymous = new Client("api08-anon", port);
        Resp before = anonymous.get("/api/auth/me");
        Citizen a = citizen("api08");
        Resp during = a.client().get("/api/auth/me");
        String oldSession = a.client().cookies.get("JSESSIONID");
        Resp logout = a.client().send("POST", "/api/auth/logout", null, null, true, Map.of());
        Resp after = a.client().get("/api/auth/me");
        Client replay = new Client("api08-replay", port);
        replay.cookies.put("JSESSIONID", oldSession);
        Resp replayed = replay.get("/api/auth/me");
        observe("API-08", "step 1 no session -> " + before + "; step 2 -> " + during.status() + " email=" + parse(during).get("email").asText());
        observe("API-08", "step 3-4 logout -> " + logout.status() + "; Set-Cookie=" + logout.setCookies());
        observe("API-08", "step 5 same client -> " + after + "; old JSESSIONID replayed by a new client -> " + replayed.status());
        assertThat(before.status()).isEqualTo(401);
        assertThat(before.body()).isEqualTo("{\"error\":\"Authentication required.\"}");
        assertThat(during.status()).isEqualTo(200);
        assertThat(logout.status()).isEqualTo(204);
        assertThat(logout.setCookies()).anyMatch(s -> s.startsWith("JSESSIONID=") && s.contains("Max-Age=0"));
        assertThat(logout.setCookies()).anyMatch(s -> s.startsWith("XSRF-TOKEN=") && s.contains("Max-Age=0"));
        assertThat(after.status()).isEqualTo(401);
        assertThat(replayed.status()).isEqualTo(401);
    }

    @Test @DisplayName("API-09 Authorisation matrix for every admin endpoint")
    void api09() {
        Citizen a = citizen("api09");
        long id = createReport(a.client(), "API-09 report", cat());
        assertThat(upload(a.client(), id, "a.jpg", "image/jpeg", Part01Support.jpg(8, 8)).status()).isEqualTo(204);
        Resp seeded = progress(id, "IN_PROGRESS", "Seeded note", Part.file("p.jpg", "image/jpeg", Part01Support.jpg(8, 8)));
        assertThat(seeded.status()).isEqualTo(201);
        long updateId = parse(seeded).get("id").asLong();
        long disposable = createReport(a.client(), "API-09 disposable", cat());
        Client anonymous = new Client("api09-anon", port);
        String base = "/api/admin/reports";
        record Call(String label, java.util.function.Function<Client, Resp> call) { }
        List<Call> calls = List.of(
                new Call("GET " + base, c -> c.get(base)),
                new Call("GET " + base + "/summary", c -> c.get(base + "/summary")),
                new Call("GET " + base + "/{id}", c -> c.get(base + "/" + id)),
                new Call("GET " + base + "/{id}/image", c -> c.get(base + "/" + id + "/image")),
                new Call("GET " + base + "/{id}/progress-updates", c -> c.get(base + "/" + id + "/progress-updates")),
                new Call("GET " + base + "/{id}/progress-updates/{updateId}/image", c -> c.get(base + "/" + id + "/progress-updates/" + updateId + "/image")),
                new Call("POST " + base + "/{id}/progress-updates", c -> c.multipart(base + "/" + id + "/progress-updates", List.of(Part.field("status", "IN_PROGRESS")), true)),
                new Call("PATCH " + base + "/{id}/status", c -> c.patchJson(base + "/" + id + "/status", body("status", "IN_PROGRESS"), true)),
                new Call("PATCH " + base + "/{id}/public-approval", c -> c.patchJson(base + "/" + id + "/public-approval", body("approved", true), true)),
                new Call("PATCH " + base + "/{id}/progress-updates/{updateId}/public-approval", c -> c.patchJson(base + "/" + id + "/progress-updates/" + updateId + "/public-approval", body("approved", true), true)),
                new Call("PATCH " + base + "/{id}/progress-updates/{updateId}/note-public-approval", c -> c.patchJson(base + "/" + id + "/progress-updates/" + updateId + "/note-public-approval", body("approved", true), true)),
                new Call("DELETE " + base + "/{id}", c -> c.delete(base + "/" + disposable, true)));
        long historyBefore = updates.findByReportIdOrderByCreatedAtAscIdAsc(id).size();
        for (Call call : calls) {
            Resp anon = call.call().apply(anonymous);
            Resp cit = call.call().apply(a.client());
            observe("API-09", call.label() + ": anonymous -> " + anon.status() + ", citizen -> " + cit.status());
            assertThat(anon.status()).as("anonymous " + call.label()).isEqualTo(401);
            assertThat(cit.status()).as("citizen " + call.label()).isEqualTo(403);
        }
        JsonNode unchanged = adminReport(id);
        observe("API-09", "after anonymous/citizen calls: status=" + unchanged.get("status").asText() + " publicApproved="
                + unchanged.get("publicApproved").asBoolean() + " history rows " + historyBefore + " -> "
                + updates.findByReportIdOrderByCreatedAtAscIdAsc(id).size() + " disposable exists=" + reports.existsById(disposable));
        assertThat(unchanged.get("publicApproved").asBoolean()).isFalse();
        assertThat(updates.findByReportIdOrderByCreatedAtAscIdAsc(id)).hasSize((int) historyBefore);
        assertThat(reports.existsById(disposable)).isTrue();
        for (Call call : calls) {
            Resp ad = call.call().apply(admin);
            observe("API-09", call.label() + ": admin -> " + ad.status());
            assertThat(ad.status()).as("admin " + call.label()).isBetween(200, 299);
        }
    }

    @Test @DisplayName("API-10 Create report; owner is taken from the session")
    void api10() {
        Citizen a = citizen("api10a");
        Citizen b = citizen("api10b");
        Resp r = a.client().postJson("/api/reports", body("title", "API-10", "description", "D", "category", "Roads",
                "location", "L", "ownerId", b.id()), true);
        emulateReference();
        JsonNode node = parse(r);
        JsonNode seen = adminReport(node.get("id").asLong());
        observe("API-10", "create with \"ownerId\":" + b.id() + " (Citizen B) as Citizen A (id " + a.id() + ") -> " + r.status()
                + "; status=" + node.get("status").asText() + " reference=" + node.get("reference").asText()
                + " ownerName=" + node.get("ownerName").asText() + "; admin view ownerId=" + seen.get("ownerId").asLong());
        assertThat(r.status()).isEqualTo(201);
        assertThat(node.get("status").asText()).isEqualTo("PENDING");
        assertThat(node.get("reference").asText()).matches("^PC-\\d{8}$");
        assertThat(node.get("createdAt").isNull()).isFalse();
        assertThat(node.get("updatedAt").isNull()).isFalse();
        assertThat(seen.get("ownerId").asLong()).isEqualTo(a.id()).isNotEqualTo(b.id());
    }

    @Test @DisplayName("API-11 Report field boundaries and trimming")
    void api11() {
        Client c = citizen("api11").client();
        record Row(String label, String title, String description, String category, String location, int expected) { }
        List<Row> rows = List.of(
                new Row("title 160", repeat('t', 160), "D", "C", "L", 201),
                new Row("title 161", repeat('t', 161), "D", "C", "L", 400),
                new Row("description 5000", "T", repeat('d', 5000), "C", "L", 201),
                new Row("description 5001", "T", repeat('d', 5001), "C", "L", 400),
                new Row("category 80", "T", "D", repeat('c', 80), "L", 201),
                new Row("category 81", "T", "D", repeat('c', 81), "L", 400),
                new Row("location 240", "T", "D", "C", repeat('l', 240), 201),
                new Row("location 241", "T", "D", "C", repeat('l', 241), 400),
                new Row("title whitespace-only", "   ", "D", "C", "L", 400),
                new Row("description whitespace-only", "T", "   ", "C", "L", 400),
                new Row("category whitespace-only", "T", "D", "   ", "L", 400),
                new Row("location whitespace-only", "T", "D", "C", "   ", 400),
                new Row("category not in the UI list", "T", "D", "Category-Not-In-UI-List", "L", 201));
        for (Row row : rows) {
            Resp r = c.postJson("/api/reports", body("title", row.title(), "description", row.description(),
                    "category", row.category(), "location", row.location()), true);
            observe("API-11", row.label() + " -> " + r.status() + (r.status() == 400 ? " " + r.body() : ""));
            assertThat(r.status()).as(row.label()).isEqualTo(row.expected());
        }
        Resp padded = c.postJson("/api/reports", body("title", "  Padded title  ", "description", "  Padded description  ",
                "category", "  Roads  ", "location", "  Padded location  "), true);
        JsonNode node = parse(padded);
        observe("API-11", "values with surrounding spaces -> " + padded.status() + "; stored title='" + node.get("title").asText()
                + "' description='" + node.get("description").asText() + "' category='" + node.get("category").asText()
                + "' location='" + node.get("location").asText() + "'");
        assertThat(padded.status()).isEqualTo(201);
        assertThat(node.get("title").asText()).isEqualTo("Padded title");
        assertThat(node.get("description").asText()).isEqualTo("Padded description");
        assertThat(node.get("category").asText()).isEqualTo("Roads");
        assertThat(node.get("location").asText()).isEqualTo("Padded location");
        emulateReference();
    }

    @Test @DisplayName("API-12 Report image upload rules")
    void api12() {
        Citizen a = citizen("api12a");
        Citizen b = citizen("api12b");
        String category = cat();
        long jpgReport = createReport(a.client(), "jpg", category);
        long pngReport = createReport(a.client(), "png", category);
        long webpReport = createReport(a.client(), "webp", category);
        long invalidTarget = createReport(a.client(), "invalid", category);
        long exactReport = createReport(a.client(), "exact", category);
        long otherTarget = createReport(a.client(), "other", category);
        Map<String, Integer> codes = new LinkedHashMap<>();
        codes.put("1 JPG", upload(a.client(), jpgReport, "a.jpg", "image/jpeg", Part01Support.jpg(32, 32)).status());
        codes.put("1 PNG", upload(a.client(), pngReport, "a.png", "image/png", Part01Support.png(32, 32)).status());
        codes.put("1 WebP (synthetic VP8L)", upload(a.client(), webpReport, "a.webp", "image/webp", Part01Support.webpVp8l()).status());
        codes.put("2 second image on JPG report", upload(a.client(), jpgReport, "b.jpg", "image/jpeg", Part01Support.jpg(8, 8)).status());
        codes.put("3 GIF", upload(a.client(), invalidTarget, "a.gif", "image/gif", Part01Support.gif(8, 8)).status());
        codes.put("3 text file renamed .png", upload(a.client(), invalidTarget, "fake.png", "image/png", "not an image".getBytes()).status());
        codes.put("3 0-byte file", upload(a.client(), invalidTarget, "empty.png", "image/png", new byte[0]).status());
        codes.put("4 PNG of exactly 5,242,880 bytes", upload(a.client(), exactReport, "exact.png", "image/png", Part01Support.pngOfExactSize(5_242_880)).status());
        codes.put("5 PNG of 5,242,881 bytes", upload(a.client(), invalidTarget, "big.png", "image/png", Part01Support.pngOfExactSize(5_242_881)).status());
        codes.put("6 PNG 4000 x 2501 (10,004,000 px)", upload(a.client(), invalidTarget, "huge.png", "image/png", Part01Support.png(4000, 2501)).status());
        codes.put("7 Citizen B uploads to Citizen A's report", upload(b.client(), otherTarget, "b.png", "image/png", Part01Support.png(8, 8)).status());
        codes.put("8 anonymous uploads to Citizen A's report", upload(new Client("api12-anon", port), otherTarget, "c.png", "image/png", Part01Support.png(8, 8)).status());
        codes.forEach((k, v) -> observe("API-12", "step " + k + " -> " + v));
        assertThat(codes.get("1 JPG")).isEqualTo(204);
        assertThat(codes.get("1 PNG")).isEqualTo(204);
        assertThat(codes.get("1 WebP (synthetic VP8L)")).isEqualTo(204);
        assertThat(codes.get("2 second image on JPG report")).isEqualTo(409);
        assertThat(codes.get("3 GIF")).isEqualTo(400);
        assertThat(codes.get("3 text file renamed .png")).isEqualTo(400);
        assertThat(codes.get("3 0-byte file")).isEqualTo(400);
        assertThat(codes.get("4 PNG of exactly 5,242,880 bytes")).isEqualTo(204);
        assertThat(codes.get("5 PNG of 5,242,881 bytes")).isIn(400, 413);
        assertThat(codes.get("6 PNG 4000 x 2501 (10,004,000 px)")).isEqualTo(400);
        assertThat(codes.get("7 Citizen B uploads to Citizen A's report")).isEqualTo(404);
        assertThat(codes.get("8 anonymous uploads to Citizen A's report")).isEqualTo(401);
    }

    @Test @DisplayName("API-13 Image retrieval headers and access scope")
    void api13() {
        Citizen a = citizen("api13a");
        Citizen b = citizen("api13b");
        long withImage = createReport(a.client(), "with image", cat());
        long withoutImage = createReport(a.client(), "without image", cat());
        byte[] png = Part01Support.png(12, 12);
        assertThat(upload(a.client(), withImage, "a.png", "image/png", png).status()).isEqualTo(204);
        Resp owner = a.client().get("/api/reports/" + withImage + "/image");
        Resp other = b.client().get("/api/reports/" + withImage + "/image");
        Resp adminView = admin.get("/api/admin/reports/" + withImage + "/image");
        Resp none = a.client().get("/api/reports/" + withoutImage + "/image");
        for (var entry : Map.of("1 owner", owner, "2 Citizen B", other, "3 admin", adminView, "4 owner, no image", none).entrySet()) {
            Resp r = entry.getValue();
            observe("API-13", "step " + entry.getKey() + " -> " + r.status() + "; Content-Type=" + r.header("content-type")
                    + "; Cache-Control=" + r.header("cache-control") + "; X-Content-Type-Options=" + r.header("x-content-type-options")
                    + (r.status() == 200 ? "; bytes identical to upload=" + java.util.Arrays.equals(r.bytes(), png) : "; body=" + r.body()));
        }
        for (Resp r : List.of(owner, adminView)) {
            assertThat(r.status()).isEqualTo(200);
            assertThat(r.bytes()).isEqualTo(png);
            assertThat(r.header("cache-control")).isEqualTo("no-store");
            assertThat(r.header("x-content-type-options")).isEqualTo("nosniff");
            assertThat(r.header("content-type")).isEqualTo("image/png");
        }
        assertThat(other.status()).isEqualTo(404);
        assertThat(none.status()).isEqualTo(404);
    }

    @Test @DisplayName("API-14 List endpoints: paging, sort, filters, summary and owner scope")
    void api14() {
        Citizen a = citizen("api14a");
        Citizen b = citizen("api14b");
        String category = cat();
        long a1 = createReport(a.client(), "A pending", category);
        long a2 = createReport(a.client(), "A in progress", category);
        long a3 = createReport(a.client(), "A resolved", "QA-other-" + category);
        long b1 = createReport(b.client(), "B pending", category);
        long b2 = createReport(b.client(), "B in progress", category);
        assertThat(setStatus(a2, "IN_PROGRESS", null).status()).isEqualTo(200);
        assertThat(setStatus(a3, "RESOLVED", null).status()).isEqualTo(200);
        assertThat(setStatus(b2, "IN_PROGRESS", null).status()).isEqualTo(200);
        String reference = adminReport(a1).get("reference").asText();
        for (String endpoint : List.of("/api/reports/mine", "/api/admin/reports")) {
            Client c = endpoint.startsWith("/api/admin") ? admin : a.client();
            String scope = endpoint.startsWith("/api/admin") ? "&category=" + category : "";
            Map<String, Resp> results = new LinkedHashMap<>();
            for (String query : List.of("page=-1", "size=0", "size=1", "size=100", "size=101", "sort=foo",
                    "status=in%20progress", "status=ALL", "status=DONE", "q=" + reference)) {
                results.put(query, c.get(endpoint + "?" + query + scope));
            }
            results.forEach((q, r) -> observe("API-14", endpoint + "?" + q + (scope.isEmpty() ? "" : " (+ category filter for isolation)") + " -> "
                    + r.status() + (r.status() == 200 ? " totalElements=" + parse(r).get("totalElements").asLong() + " items="
                    + parse(r).get("content").size() + " summary.total=" + parse(r).get("summary").get("total").asLong() : " " + r.body())));
            for (String bad : List.of("page=-1", "size=0", "size=101", "sort=foo", "status=DONE")) {
                assertThat(results.get(bad).status()).as(endpoint + " " + bad).isEqualTo(400);
            }
            assertThat(parse(results.get("status=DONE")).get("error").asText()).isEqualTo("Unsupported report status.");
            assertThat(parse(results.get("size=1")).get("content").size()).isLessThanOrEqualTo(1);
            assertThat(results.get("size=100").status()).isEqualTo(200);
            for (JsonNode item : parse(results.get("status=in%20progress")).get("content")) {
                assertThat(item.get("status").asText()).isEqualTo("IN_PROGRESS");
            }
            JsonNode byReference = parse(results.get("q=" + reference)).get("content");
            assertThat(byReference).hasSize(1);
            assertThat(byReference.get(0).get("id").asLong()).isEqualTo(a1);
            for (String ok : List.of("size=1", "size=100", "status=in%20progress", "status=ALL", "q=" + reference)) {
                JsonNode page = parse(results.get(ok));
                assertThat(page.get("summary").get("total").asLong()).as(endpoint + " " + ok).isEqualTo(page.get("totalElements").asLong());
            }
        }
        JsonNode mine = parse(a.client().get("/api/reports/mine?size=100"));
        List<Long> mineIds = new ArrayList<>();
        mine.get("content").forEach(n -> mineIds.add(n.get("id").asLong()));
        JsonNode all = parse(admin.get("/api/admin/reports?size=100&category=" + category));
        List<Long> owners = new ArrayList<>();
        all.get("content").forEach(n -> owners.add(n.get("ownerId").asLong()));
        observe("API-14", "step 7: /mine ids=" + mineIds + " (B's reports " + b1 + "," + b2 + " present=" + (mineIds.contains(b1) || mineIds.contains(b2))
                + "); step 8: admin list owners for the category=" + owners);
        assertThat(mineIds).containsExactlyInAnyOrder(a1, a2, a3).doesNotContain(b1, b2);
        assertThat(owners).contains(a.id(), b.id());
    }

    @Test @DisplayName("API-15 Cross-user access and editing an approved report")
    void api15() {
        Citizen a = citizen("api15a");
        Citizen b = citizen("api15b");
        long bReport = createReport(b.client(), "B original", cat());
        long aReport = createReport(a.client(), "A approved", cat());
        assertThat(admin.patchJson("/api/admin/reports/" + aReport + "/public-approval", body("approved", true), true).status()).isEqualTo(200);
        long missing = 9_999_999L;
        Resp getOther = a.client().get("/api/reports/" + bReport);
        Resp getMissing = a.client().get("/api/reports/" + missing);
        String change = body("title", "Changed by A", "description", "Changed", "category", "Changed", "location", "Changed");
        Resp putOther = a.client().putJson("/api/reports/" + bReport, change, true);
        Resp putMissing = a.client().putJson("/api/reports/" + missing, change, true);
        JsonNode bAfter = adminReport(bReport);
        String statusBefore = adminReport(aReport).get("status").asText();
        Resp own = a.client().putJson("/api/reports/" + aReport, change, true);
        JsonNode aAfter = adminReport(aReport);
        observe("API-15", "GET other's report -> " + getOther + "; GET missing -> " + getMissing);
        observe("API-15", "PUT other's report -> " + putOther + "; PUT missing -> " + putMissing + "; B's report title after='" + bAfter.get("title").asText() + "'");
        observe("API-15", "PUT own approved report (status " + statusBefore + ") -> " + own.status() + " title='" + parse(own).get("title").asText()
                + "'; admin view publicApproved=" + aAfter.get("publicApproved").asBoolean() + " status=" + aAfter.get("status").asText());
        assertThat(getOther.status()).isEqualTo(404);
        assertThat(getOther.body()).isEqualTo(getMissing.body()).isEqualTo("{\"error\":\"Report not found.\"}");
        assertThat(putOther.status()).isEqualTo(404);
        assertThat(putOther.body()).isEqualTo(putMissing.body());
        assertThat(bAfter.get("title").asText()).isEqualTo("B original");
        assertThat(own.status()).isEqualTo(200);
        assertThat(parse(own).get("title").asText()).isEqualTo("Changed by A");
        assertThat(aAfter.get("publicApproved").asBoolean()).isFalse();
        assertThat(aAfter.get("status").asText()).isEqualTo(statusBefore);
    }

    @Test @DisplayName("API-16 Direct status PATCH rules and approval revocation")
    void api16() {
        Citizen a = citizen("api16");
        long id = createReport(a.client(), "API-16", cat());
        assertThat(admin.patchJson("/api/admin/reports/" + id + "/public-approval", body("approved", true), true).status()).isEqualTo(200);
        String note1000 = repeat('n', 1000);
        Resp s1 = setStatus(id, null, "note");
        Resp s2 = setStatus(id, "DONE", "note");
        JsonNode afterBad = adminReport(id);
        Resp s3 = setStatus(id, "PENDING", note1000);
        boolean approvedAfterSame = adminReport(id).get("publicApproved").asBoolean();
        Resp s4 = setStatus(id, "IN_PROGRESS", repeat('n', 1001));
        JsonNode after4 = adminReport(id);
        String ownerNote = parse(a.client().get("/api/reports/" + id)).get("adminUpdateNote").asText();
        Resp s6 = setStatus(id, "IN_PROGRESS", "Work started");
        boolean approvedAfterChange = adminReport(id).get("publicApproved").asBoolean();
        Resp s7 = setStatus(id, "IN_PROGRESS", null);
        JsonNode ownerView = parse(a.client().get("/api/reports/" + id));
        observe("API-16", "step 1 status null -> " + s1 + "; step 2 status DONE -> " + s2.status() + "; after steps 1-2 status="
                + afterBad.get("status").asText() + " publicApproved=" + afterBad.get("publicApproved").asBoolean());
        observe("API-16", "step 3 same status PENDING + 1000-char note -> " + s3.status() + "; publicApproved=" + approvedAfterSame
                + " (observed; pass criterion blocked by RTM Q1 / BR-13)");
        observe("API-16", "step 4 1001-char note -> " + s4 + "; status after=" + after4.get("status").asText()
                + "; step 5 owner sees latest note length=" + ownerNote.length());
        observe("API-16", "step 6 new status IN_PROGRESS -> " + s6.status() + " publicApproved=" + approvedAfterChange
                + "; step 7 note null -> " + s7.status() + " owner adminUpdateNote isNull=" + ownerView.get("adminUpdateNote").isNull());
        assertThat(s1.status()).isEqualTo(400);
        assertThat(s2.status()).isEqualTo(400);
        assertThat(afterBad.get("status").asText()).isEqualTo("PENDING");
        assertThat(afterBad.get("publicApproved").asBoolean()).isTrue();
        assertThat(s4.status()).isEqualTo(400);
        assertThat(after4.get("status").asText()).isEqualTo("PENDING");
        assertThat(ownerNote).isEqualTo(note1000);
        assertThat(s6.status()).isEqualTo(200);
        assertThat(approvedAfterChange).isFalse();
        assertThat(s7.status()).isEqualTo(200);
        assertThat(ownerView.get("adminUpdateNote").isNull()).isTrue();
    }

    @Test @DisplayName("API-17 Progress update creation is atomic; history order")
    void api17() {
        Citizen a = citizen("api17");
        long id = createReport(a.client(), "API-17", cat());
        Map<String, Resp> bad = new LinkedHashMap<>();
        bad.put("1 no status", progress(id, null, "note", null));
        bad.put("2 1001-char note", progress(id, "IN_PROGRESS", repeat('n', 1001), null));
        bad.put("3 WebP photo", progress(id, "IN_PROGRESS", "note", Part.file("p.webp", "image/webp", Part01Support.webpVp8l())));
        bad.put("4 corrupt JPG", progress(id, "IN_PROGRESS", "note", Part.file("p.jpg", "image/jpeg", Part01Support.corruptJpg())));
        for (var entry : bad.entrySet()) {
            JsonNode report = adminReport(id);
            int rows = parse(admin.get("/api/admin/reports/" + id + "/progress-updates")).size();
            observe("API-17", "step " + entry.getKey() + " -> " + entry.getValue() + "; step 5: status=" + report.get("status").asText() + " history rows=" + rows);
            assertThat(entry.getValue().status()).as(entry.getKey()).isEqualTo(400);
            assertThat(report.get("status").asText()).isEqualTo("PENDING");
            assertThat(rows).isZero();
        }
        String note1000 = repeat('m', 1000);
        Resp s6 = progress(id, "IN_PROGRESS", note1000, Part.file("p.jpg", "image/jpeg", Part01Support.jpg(16, 16)));
        JsonNode after6 = adminReport(id);
        Resp s7 = progress(id, "RESOLVED", "Repair completed", null);
        JsonNode after7 = adminReport(id);
        JsonNode adminHistory = parse(admin.get("/api/admin/reports/" + id + "/progress-updates"));
        JsonNode ownerHistory = parse(a.client().get("/api/reports/" + id + "/progress-updates"));
        observe("API-17", "step 6 -> " + s6.status() + "; status=" + after6.get("status").asText() + " note length=" + after6.get("adminUpdateNote").asText().length());
        observe("API-17", "step 7 -> " + s7.status() + "; status=" + after7.get("status").asText() + " note='" + after7.get("adminUpdateNote").asText() + "'");
        observe("API-17", "step 8 admin history=" + adminHistory);
        observe("API-17", "step 9 owner history statuses=" + ownerHistory.findValuesAsText("status") + " hasImage=" + ownerHistory.findValuesAsText("hasImage"));
        assertThat(s6.status()).isEqualTo(201);
        assertThat(after6.get("status").asText()).isEqualTo("IN_PROGRESS");
        assertThat(after6.get("adminUpdateNote").asText()).isEqualTo(note1000);
        assertThat(s7.status()).isEqualTo(201);
        assertThat(after7.get("status").asText()).isEqualTo("RESOLVED");
        assertThat(adminHistory.findValuesAsText("status")).containsExactly("IN_PROGRESS", "RESOLVED");
        assertThat(adminHistory.get(0).has("publicApproved")).isTrue();
        assertThat(adminHistory.get(0).has("notePublicApproved")).isTrue();
        assertThat(adminHistory.get(0).get("hasImage").asBoolean()).isTrue();
        assertThat(ownerHistory.findValuesAsText("status")).containsExactly("IN_PROGRESS", "RESOLVED");
        assertThat(ownerHistory.findValuesAsText("note")).containsExactly(note1000, "Repair completed");
    }

    @Test @DisplayName("API-18 Public approval endpoints")
    void api18() {
        Citizen a = citizen("api18");
        long r1 = createReport(a.client(), "R1", cat());
        long r2 = createReport(a.client(), "R2", cat());
        long u1 = parse(progress(r1, "IN_PROGRESS", "Visible note", null)).get("id").asLong();
        long u2 = parse(progress(r1, "IN_PROGRESS", "   ", null)).get("id").asLong();
        long u3 = parse(progress(r2, "IN_PROGRESS", "Other", null)).get("id").asLong();
        String base = "/api/admin/reports/" + r1;
        Resp approve = admin.patchJson(base + "/public-approval", body("approved", true), true);
        boolean flag1 = adminReport(r1).get("publicApproved").asBoolean();
        Map<String, Object> approver = jdbc.queryForMap("select last_public_approved_by, last_public_approved_at from reports where id = ?", r1);
        Resp revoke = admin.patchJson(base + "/public-approval", body("approved", false), true);
        boolean flag2 = adminReport(r1).get("publicApproved").asBoolean();
        observe("API-18", "step 1 approve -> " + approve + " flag=" + flag1 + " approver(db)=" + approver.get("LAST_PUBLIC_APPROVED_BY")
                + " (admin id " + adminId + ") at=" + approver.get("LAST_PUBLIC_APPROVED_AT") + "; revoke -> " + revoke + " flag=" + flag2);
        assertThat(approve.body()).isEqualTo("{\"approved\":true}");
        assertThat(flag1).isTrue();
        assertThat(((Number) approver.get("LAST_PUBLIC_APPROVED_BY")).longValue()).isEqualTo(adminId);
        assertThat(approver.get("LAST_PUBLIC_APPROVED_AT")).isNotNull();
        assertThat(revoke.body()).isEqualTo("{\"approved\":false}");
        assertThat(flag2).isFalse();
        for (String route : List.of("public-approval", "note-public-approval")) {
            Resp on = admin.patchJson(base + "/progress-updates/" + u1 + "/" + route, body("approved", true), true);
            JsonNode h1 = parse(admin.get(base + "/progress-updates")).get(0);
            Resp off = admin.patchJson(base + "/progress-updates/" + u1 + "/" + route, body("approved", false), true);
            JsonNode h2 = parse(admin.get(base + "/progress-updates")).get(0);
            String field = route.equals("public-approval") ? "publicApproved" : "notePublicApproved";
            observe("API-18", "U1 " + route + ": approve -> " + on + " " + field + "=" + h1.get(field).asBoolean()
                    + "; revoke -> " + off + " " + field + "=" + h2.get(field).asBoolean());
            assertThat(h1.get(field).asBoolean()).isTrue();
            assertThat(h2.get(field).asBoolean()).isFalse();
        }
        Resp blank = admin.patchJson(base + "/progress-updates/" + u2 + "/note-public-approval", body("approved", true), true);
        Resp wrongReport = admin.patchJson(base + "/progress-updates/" + u3 + "/public-approval", body("approved", true), true);
        admin.patchJson(base + "/public-approval", body("approved", true), true);
        Resp empty = admin.patchJson(base + "/public-approval", "{}", true);
        boolean afterEmpty = adminReport(r1).get("publicApproved").asBoolean();
        observe("API-18", "step 4 approve blank note U2 -> " + blank + "; step 5 U3 under R1 -> " + wrongReport);
        observe("API-18", "step 6 approve then {} -> " + empty + "; publicApproved after {}=" + afterEmpty + " (observed; not judged, RTM Q1 / BR-22)");
        assertThat(blank.status()).isEqualTo(400);
        assertThat(wrongReport.status()).isEqualTo(404);
    }

    @Test @DisplayName("API-19 Public feed and detail: paging, approval and data minimisation")
    void api19() {
        PublicCareUser owner = users.findByEmail(adminEmail).orElseThrow();
        PublicCareUser citizen = users.saveAndFlush(new PublicCareUser("QA Feed Owner", uniqueEmail("api19"), encoder.encode(PASSWORD), UserRole.CITIZEN));
        for (int i = 0; i < 26; i++) {
            PublicCareReport r = reports.saveAndFlush(new PublicCareReport("Feed " + i, "D", "Roads", "L", citizen));
            r.approveForPublic(owner.getId());
            reports.saveAndFlush(r);
        }
        PublicCareReport unapproved = reports.saveAndFlush(new PublicCareReport("Not approved", "D", "Roads", "L", citizen));
        PublicCareReport rich = reports.saveAndFlush(new PublicCareReport("Rich", "D", "Roads", "L", citizen));
        rich.approveForPublic(owner.getId());
        rich.setAdminUpdateNote("internal admin note");
        reports.saveAndFlush(rich);
        List<Long> updateIds = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            ReportProgressUpdate u = new ReportProgressUpdate(rich, ReportStatus.IN_PROGRESS, "note " + i, null, null, null);
            u.approveForPublic(owner.getId());
            if (i % 2 == 0) u.approveNoteForPublic(owner.getId());
            updateIds.add(updates.saveAndFlush(u).getId());
        }
        emulateReference();
        Client anonymous = new Client("api19-anon", port);
        Map<String, Integer> paging = new LinkedHashMap<>();
        for (String q : List.of("size=0", "size=1", "size=24", "size=25", "page=-1")) {
            Resp r = anonymous.get("/api/public/reports?" + q);
            paging.put(q, r.status());
            observe("API-19", "GET /api/public/reports?" + q + " -> " + r.status() + (r.status() == 200 ? " items=" + parse(r).get("content").size() : " " + r.body()));
            if (r.status() == 200) assertThat(parse(r).get("content").size()).isLessThanOrEqualTo(Integer.parseInt(q.substring(5)));
        }
        assertThat(paging).containsEntry("size=0", 400).containsEntry("size=25", 400).containsEntry("page=-1", 400)
                .containsEntry("size=1", 200).containsEntry("size=24", 200);
        JsonNode feed = parse(anonymous.get("/api/public/reports"));
        List<Long> feedIds = new ArrayList<>();
        List<String> dates = new ArrayList<>();
        feed.get("content").forEach(n -> { feedIds.add(n.get("id").asLong()); dates.add(n.get("reportedAt").asText()); });
        boolean allApproved = feedIds.stream().allMatch(id -> reports.findById(id).orElseThrow().isPublicApproved());
        observe("API-19", "step 3 default -> size=" + feed.get("size").asInt() + " ids=" + feedIds + " all approved=" + allApproved + " reportedAt=" + dates);
        assertThat(feed.get("size").asInt()).isEqualTo(9);
        assertThat(allApproved).isTrue();
        List<String> sorted = new ArrayList<>(dates);
        sorted.sort(java.util.Comparator.reverseOrder());
        assertThat(dates).isEqualTo(sorted);
        Resp hidden = anonymous.get("/api/public/reports/" + unapproved.getId());
        Resp detail = anonymous.get("/api/public/reports/" + rich.getId());
        JsonNode d = parse(detail);
        List<String> fields = new ArrayList<>();
        d.fieldNames().forEachRemaining(fields::add);
        List<String> updateFields = new ArrayList<>();
        d.get("progressUpdates").get(0).fieldNames().forEachRemaining(updateFields::add);
        List<String> notes = new ArrayList<>();
        d.get("progressUpdates").forEach(n -> notes.add(n.get("note").isNull() ? "null" : n.get("note").asText()));
        Resp image = anonymous.get("/api/public/reports/" + rich.getId() + "/image");
        observe("API-19", "step 4 unapproved detail -> " + hidden + "; step 5 fields=" + fields + " update fields=" + updateFields);
        observe("API-19", "step 6 updates returned=" + d.get("progressUpdates").size() + " notes newest first=" + notes + "; step 7 image -> " + image.status());
        assertThat(hidden.status()).isEqualTo(404);
        assertThat(fields).containsExactly("id", "title", "category", "status", "location", "description", "reportedAt", "progressUpdates");
        assertThat(updateFields).containsExactly("status", "createdAt", "note");
        assertThat(detail.body()).doesNotContain("internal admin note").doesNotContain("reference").doesNotContain("owner");
        assertThat(notes).containsExactly("null", "note 10", "null", "note 8", "null", "note 6", "null", "note 4", "null", "note 2");
        assertThat(image.status()).isEqualTo(401);
    }

    @Test @DisplayName("API-20 Admin delete and refused delete requests")
    void api20() {
        Citizen a = citizen("api20");
        long disposable = createReport(a.client(), "disposable", cat());
        long keep = createReport(a.client(), "keep", cat());
        assertThat(upload(a.client(), disposable, "a.png", "image/png", Part01Support.png(8, 8)).status()).isEqualTo(204);
        assertThat(progress(disposable, "IN_PROGRESS", "note", Part.file("p.jpg", "image/jpeg", Part01Support.jpg(8, 8))).status()).isEqualTo(201);
        Resp s1 = a.client().delete("/api/admin/reports/" + keep, true);
        Resp s2 = admin.delete("/api/admin/reports/" + keep, false);
        Resp s3 = admin.delete("/api/admin/reports/abc", true);
        Resp s4 = admin.delete("/api/admin/reports/" + disposable, true);
        Resp s5 = admin.get("/api/admin/reports/" + disposable);
        Resp s6 = admin.delete("/api/admin/reports/" + disposable, true);
        Resp s7 = admin.get("/api/admin/reports/" + keep);
        observe("API-20", "1 citizen -> " + s1 + "; 2 admin no token -> " + s2 + "; 3 /abc -> " + s3.status() + "; 4 delete -> " + s4.status()
                + "; 5 GET deleted -> " + s5 + "; 6 repeat -> " + s6 + "; 7 second report -> " + s7.status()
                + "; progress rows left=" + updates.findByReportIdOrderByCreatedAtAscIdAsc(disposable).size());
        assertThat(s1.status()).isEqualTo(403);
        assertThat(s2.status()).isEqualTo(403);
        assertThat(s3.status()).isEqualTo(400);
        assertThat(s4.status()).isEqualTo(204);
        assertThat(s5.status()).isEqualTo(404);
        assertThat(s6.status()).isEqualTo(404);
        assertThat(s7.status()).isEqualTo(200);
    }

    @Test @DisplayName("API-21 Health endpoints")
    void api21() {
        Client anonymous = new Client("api21", port);
        Resp api = anonymous.get("/api/health");
        Resp actuator = anonymous.get("/actuator/health");
        observe("API-21", "GET /api/health -> " + api + "; GET /actuator/health -> " + actuator
                + "; body has a components/db entry=" + actuator.body().contains("\"db\""));
        assertThat(api.status()).isEqualTo(200);
        assertThat(api.body()).isEqualTo("{\"status\":\"UP\"}");
        assertThat(actuator.status()).isEqualTo(200);
        assertThat(parse(actuator).get("status").asText()).isEqualTo("UP");
    }

    @Test @DisplayName("API-22 CORS preflight for allowed and disallowed origins")
    void api22() {
        Client c = new Client("api22", port);
        Map<String, Resp> results = new LinkedHashMap<>();
        for (String origin : List.of("http://localhost:5173", "http://evil.test")) {
            results.put(origin, c.options("/api/reports", Map.of("Origin", origin, "Access-Control-Request-Method", "POST",
                    "Access-Control-Request-Headers", "Content-Type, X-XSRF-TOKEN")));
        }
        results.forEach((origin, r) -> {
            Map<String, List<String>> acHeaders = new LinkedHashMap<>();
            r.headers().map().forEach((k, v) -> { if (k.toLowerCase().startsWith("access-control")) acHeaders.put(k, v); });
            observe("API-22", "preflight Origin " + origin + " -> " + r.status() + " " + acHeaders + " body=" + r.body());
        });
        Resp allowed = results.get("http://localhost:5173");
        Resp evil = results.get("http://evil.test");
        assertThat(allowed.status()).isEqualTo(200);
        assertThat(allowed.header("access-control-allow-origin")).isEqualTo("http://localhost:5173");
        assertThat(allowed.header("access-control-allow-credentials")).isEqualTo("true");
        assertThat(evil.header("access-control-allow-origin")).isNull();
    }

    @Test @DisplayName("API-24 Two concurrent image uploads to the same report (observed only)")
    void api24() throws Exception {
        Citizen a = citizen("api24");
        ExecutorService pool = Executors.newFixedThreadPool(2);
        try {
            for (int round = 1; round <= 5; round++) {
                long id = createReport(a.client(), "API-24 round " + round, cat());
                a.client().refreshCsrf();
                CountDownLatch start = new CountDownLatch(1);
                List<Future<Resp>> futures = new ArrayList<>();
                for (int i = 0; i < 2; i++) {
                    byte[] content = Part01Support.jpg(64 + i, 64);
                    futures.add(pool.submit(() -> { start.await(); return upload(a.client(), id, "c.jpg", "image/jpeg", content); }));
                }
                start.countDown();
                List<String> outcomes = new ArrayList<>();
                for (Future<Resp> f : futures) outcomes.add(f.get().toString());
                observe("API-24", "round " + round + ": " + outcomes + "; images stored for the report=" +
                        jdbc.queryForObject("select count(*) from report_images where report_id = ?", Long.class, id));
            }
        } finally {
            pool.shutdownNow();
        }
    }

    @Test @DisplayName("API-25 Admin summary endpoint")
    void api25() {
        Citizen a = citizen("api25a");
        Citizen b = citizen("api25b");
        String category = cat();
        String other = cat();
        long a1 = createReport(a.client(), "Summary Alpha", category);
        long a2 = createReport(a.client(), "Summary Beta", category);
        long b1 = createReport(b.client(), "Summary Gamma", category);
        long b2 = createReport(b.client(), "Summary Delta", other);
        setStatus(a2, "IN_PROGRESS", null);
        setStatus(b1, "RESOLVED", null);
        setStatus(b2, "RESOLVED", null);
        Map<String, long[]> expected = new LinkedHashMap<>();
        expected.put("category=" + category, new long[] {3, 1, 1, 1});
        expected.put("category=" + other, new long[] {1, 0, 0, 1});
        expected.put("status=RESOLVED&category=" + category, new long[] {1, 0, 0, 1});
        expected.put("q=summary%20gamma", new long[] {1, 0, 0, 1});
        JsonNode none = parse(admin.get("/api/admin/reports/summary"));
        long listTotal = parse(admin.get("/api/admin/reports")).get("totalElements").asLong();
        observe("API-25", "no filters: summary=" + none + " list totalElements=" + listTotal + " reports in database=" + reports.count());
        assertThat(none.get("total").asLong()).isEqualTo(listTotal).isEqualTo(reports.count());
        assertThat(none.get("pending").asLong() + none.get("inProgress").asLong() + none.get("resolved").asLong()).isEqualTo(listTotal);
        for (var entry : expected.entrySet()) {
            JsonNode s = parse(admin.get("/api/admin/reports/summary?" + entry.getKey()));
            long total = parse(admin.get("/api/admin/reports?" + entry.getKey())).get("totalElements").asLong();
            observe("API-25", entry.getKey() + " -> summary " + s + "; list totalElements=" + total + "; expected [total,pending,inProgress,resolved]="
                    + java.util.Arrays.toString(entry.getValue()));
            long[] e = entry.getValue();
            assertThat(new long[] {s.get("total").asLong(), s.get("pending").asLong(), s.get("inProgress").asLong(), s.get("resolved").asLong()}).isEqualTo(e);
            assertThat(s.get("total").asLong()).isEqualTo(total);
        }
        JsonNode cats = parse(admin.get("/api/admin/reports/summary?q=summary")).get("categories");
        observe("API-25", "q=summary categories=" + cats + " (owners A=" + a.id() + ", B=" + b.id() + ")");
        assertThat(cats.get(category).asLong()).isEqualTo(3);
        assertThat(cats.get(other).asLong()).isEqualTo(1);
        assertThat(a1).isPositive();
    }

    // ------------------------------------------------------------------ UT cases executed at service level on H2

    @Test @DisplayName("UT-04 ReportService.findPage paging and sort parameters (via adminList, H2)")
    void ut04() {
        Client c = citizen("ut04").client();
        String category = cat();
        createReport(c, "Bravo", category);
        createReport(c, "Alpha", category);
        createReport(c, "Charlie", category);
        Map<String, String> outcome = new LinkedHashMap<>();
        java.util.function.Function<java.util.function.Supplier<ReportDtos.PageResponse<?>>, String> run = call -> {
            try { var page = call.get(); return "accepted (" + page.content().size() + " items)"; }
            catch (IllegalArgumentException e) { return "rejected: " + e.getMessage(); }
        };
        outcome.put("page -1", run.apply(() -> reportService.adminList(-1, 10, "createdAt", "desc", null, category, null)));
        outcome.put("page 0", run.apply(() -> reportService.adminList(0, 10, "createdAt", "desc", null, category, null)));
        for (int size : new int[] {0, 1, 100, 101}) outcome.put("size " + size, run.apply(() -> reportService.adminList(0, size, "createdAt", "desc", null, category, null)));
        for (String sort : List.of("createdAt", "updatedAt", "title", "status", "category", "foo")) {
            outcome.put("sort " + sort, run.apply(() -> reportService.adminList(0, 10, sort, "desc", null, category, null)));
        }
        for (String direction : List.of("asc", "desc", "ASC", "Desc")) {
            var page = reportService.adminList(0, 10, "title", direction, null, category, null);
            outcome.put("direction " + direction, "accepted, title order=" + page.content().stream().map(r -> r.title()).toList());
        }
        outcome.forEach((k, v) -> observe("UT-04", k + " -> " + v));
        assertThat(outcome.get("page -1")).startsWith("rejected");
        assertThat(outcome.get("page 0")).startsWith("accepted");
        assertThat(outcome.get("size 0")).startsWith("rejected");
        assertThat(outcome.get("size 101")).startsWith("rejected");
        assertThat(outcome.get("size 1")).startsWith("accepted");
        assertThat(outcome.get("size 100")).startsWith("accepted");
        for (String sort : List.of("createdAt", "updatedAt", "title", "status", "category")) assertThat(outcome.get("sort " + sort)).startsWith("accepted");
        assertThat(outcome.get("sort foo")).isEqualTo("rejected: Unsupported sort field.");
        assertThat(outcome.get("direction asc")).endsWith("[Alpha, Bravo, Charlie]");
        assertThat(outcome.get("direction desc")).endsWith("[Charlie, Bravo, Alpha]");
    }

    @Test @DisplayName("UT-05 ReportService.criteria status and search handling (via adminList, H2)")
    void ut05() {
        Client c = citizen("ut05").client();
        String category = cat();
        long pending = createReport(c, "Broken Street LAMP", category);
        long progressing = createReport(c, "Pothole", category);
        setStatus(progressing, "IN_PROGRESS", null);
        Map<String, String> outcome = new LinkedHashMap<>();
        for (String status : new String[] {"ALL", "", "in progress", "DONE"}) {
            try {
                var page = reportService.adminList(0, 10, "createdAt", "desc", status, category, null);
                outcome.put("status '" + status + "'", "statuses=" + page.content().stream().map(r -> r.status().name()).sorted().toList());
            } catch (RuntimeException e) {
                // Run 1 showed that Spring's repository exception translation wraps the IllegalArgumentException
                // thrown inside the Specification; the API maps it to 400 through the cause (see API-14).
                Throwable cause = e.getCause() == null ? e : e.getCause();
                outcome.put("status '" + status + "'", "exception: " + e.getMessage());
                observe("UT-05", "status '" + status + "' exception class=" + e.getClass().getSimpleName()
                        + ", cause=" + cause.getClass().getSimpleName() + ": " + cause.getMessage());
                assertThat(cause).isInstanceOf(IllegalArgumentException.class);
            }
        }
        var search = reportService.adminList(0, 10, "createdAt", "desc", null, category, "  street LAMP  ");
        outcome.put("q '  street LAMP  '", "ids=" + search.content().stream().map(r -> r.id()).toList());
        outcome.forEach((k, v) -> observe("UT-05", k + " -> " + v));
        assertThat(outcome.get("status 'ALL'")).isEqualTo("statuses=[IN_PROGRESS, PENDING]");
        assertThat(outcome.get("status ''")).isEqualTo("statuses=[IN_PROGRESS, PENDING]");
        assertThat(outcome.get("status 'in progress'")).isEqualTo("statuses=[IN_PROGRESS]");
        assertThat(outcome.get("status 'DONE'")).isEqualTo("exception: Unsupported report status.");
        assertThat(search.content()).extracting(r -> r.id()).containsExactly(pending);
    }

    @Test @DisplayName("UT-07 ReportService.updateMine (service level, H2)")
    void ut07() {
        Citizen a = citizen("ut07a");
        Citizen b = citizen("ut07b");
        long id = createReport(a.client(), "Original", cat());
        reportService.setPublicApproval(id, true, adminId);
        var changed = new ReportDtos.UpdateRequest("New title", "New description", "Roads", "New place");
        var result = reportService.updateMine(a.id(), id, changed);
        boolean approvedAfterOwnerEdit = reports.findById(id).orElseThrow().isPublicApproved();
        reportService.setPublicApproval(id, true, adminId);
        Throwable thrown = null;
        try { reportService.updateMine(b.id(), id, new ReportDtos.UpdateRequest("Hijack", "x", "x", "x")); }
        catch (Throwable t) { thrown = t; }
        PublicCareReport after = reports.findById(id).orElseThrow();
        observe("UT-07", "owner edit -> title='" + result.title() + "', approval after edit=" + approvedAfterOwnerEdit
                + "; non-owner edit -> " + (thrown == null ? "no exception" : thrown.getClass().getSimpleName())
                + "; title after non-owner attempt='" + after.getTitle() + "', approval=" + after.isPublicApproved());
        assertThat(result.title()).isEqualTo("New title");
        assertThat(approvedAfterOwnerEdit).isFalse();
        assertThat(thrown).isInstanceOf(ReportNotFoundException.class);
        assertThat(after.getTitle()).isEqualTo("New title");
        assertThat(after.isPublicApproved()).isTrue();
    }

    @Test @DisplayName("UT-11 PublicReportService (service level, H2)")
    void ut11() {
        PublicCareUser owner = users.saveAndFlush(new PublicCareUser("QA UT-11", uniqueEmail("ut11"), encoder.encode(PASSWORD), UserRole.CITIZEN));
        PublicCareReport approved = reports.saveAndFlush(new PublicCareReport("UT-11 approved", "D", "Roads", "L", owner));
        approved.approveForPublic(adminId);
        reports.saveAndFlush(approved);
        ReportProgressUpdate update = new ReportProgressUpdate(approved, ReportStatus.IN_PROGRESS, "unapproved note", null, null, null);
        update.approveForPublic(adminId);
        updates.saveAndFlush(update);
        PublicCareReport hidden = reports.saveAndFlush(new PublicCareReport("UT-11 hidden", "D", "Roads", "L", owner));
        emulateReference();
        var detail = publicReportService.detail(approved.getId());
        String size24;
        String size25;
        try { size24 = "accepted (" + publicReportService.list(0, 24).size() + ")"; } catch (IllegalArgumentException e) { size24 = "rejected: " + e.getMessage(); }
        try { size25 = "accepted (" + publicReportService.list(0, 25).size() + ")"; } catch (IllegalArgumentException e) { size25 = "rejected: " + e.getMessage(); }
        Throwable notFound = null;
        try { publicReportService.detail(hidden.getId()); } catch (Throwable t) { notFound = t; }
        observe("UT-11", "step 1 note of approved update with unapproved note=" + detail.progressUpdates().get(0).note()
                + "; step 2 size 24 -> " + size24 + ", size 25 -> " + size25
                + "; step 3 unapproved detail -> " + (notFound == null ? "returned" : notFound.getClass().getSimpleName()));
        assertThat(detail.progressUpdates().get(0).note()).isNull();
        assertThat(size24).startsWith("accepted");
        assertThat(size25).startsWith("rejected");
        assertThat(notFound).isInstanceOf(ReportNotFoundException.class);
    }

    @Test @DisplayName("Control: service-level rejection of an invalid sort raises IllegalArgumentException")
    void zzControl() {
        assertThatThrownBy(() -> reportService.adminList(0, 10, "foo", "asc", null, null, null)).isInstanceOf(IllegalArgumentException.class);
    }
}
