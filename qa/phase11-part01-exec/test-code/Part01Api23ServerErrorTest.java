package lk.publiccare.backend.part01;

import static lk.publiccare.backend.part01.Part01Support.observe;
import static lk.publiccare.backend.part01.Part01Support.uniqueEmail;
import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import lk.publiccare.backend.part01.Part01Support.Client;
import lk.publiccare.backend.part01.Part01Support.Part;
import lk.publiccare.backend.part01.Part01Support.Resp;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * API-23: the upload directory is made unusable by an environment change only (its path is occupied by a
 * regular file, so the directory cannot be created or written). No application code is changed.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
        "spring.datasource.url=jdbc:h2:mem:part01-api23;MODE=PostgreSQL;DB_CLOSE_DELAY=-1",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.flyway.enabled=false",
        "publiccare.upload.directory=target/part01-api23-upload-path-is-a-file"
})
class Part01Api23ServerErrorTest {
    private static final String PASSWORD = "Synthetic-Pass-123!";
    @LocalServerPort int port;
    @Autowired JdbcTemplate jdbc;

    static {
        try {
            Path blocker = Path.of("target", "part01-api23-upload-path-is-a-file");
            Files.createDirectories(blocker.getParent());
            if (!Files.exists(blocker)) Files.writeString(blocker, "placeholder: the upload directory path is a regular file");
        } catch (Exception exception) {
            throw new ExceptionInInitializerError(exception);
        }
    }

    @Test @DisplayName("API-23 Server error does not leak details and the backend keeps serving")
    void api23() {
        jdbc.execute("alter table reports alter column reference drop not null");
        Client citizen = new Client("api23", port);
        String email = uniqueEmail("api23");
        assertThat(citizen.postJson("/api/auth/register", "{\"fullName\":\"QA\",\"email\":\"" + email + "\",\"password\":\"" + PASSWORD + "\"}", true).status()).isEqualTo(201);
        assertThat(citizen.postJson("/api/auth/login", "{\"email\":\"" + email + "\",\"password\":\"" + PASSWORD + "\"}", true).status()).isEqualTo(200);
        Resp created = citizen.postJson("/api/reports", "{\"title\":\"T\",\"description\":\"D\",\"category\":\"C\",\"location\":\"L\"}", true);
        assertThat(created.status()).isEqualTo(201);
        long id = Long.parseLong(created.body().replaceAll(".*\"id\":(\\d+).*", "$1"));
        Path blocker = Path.of("target", "part01-api23-upload-path-is-a-file");
        Resp upload = citizen.multipart("/api/reports/" + id + "/image", List.of(Part.file("a.jpg", "image/jpeg", Part01Support.jpg(8, 8))), true);
        Resp health = new Client("api23-health", port).get("/api/health");
        String lower = upload.body().toLowerCase();
        boolean leaks = lower.contains("exception") || lower.contains("trace") || lower.contains("java.") || lower.contains("unable to store");
        observe("API-23", "upload directory path is a regular file (isRegularFile=" + Files.isRegularFile(blocker) + "); upload -> " + upload
                + "; body leaks stack trace / exception class / message=" + leaks + "; GET /api/health afterwards -> " + health);
        assertThat(upload.status()).isEqualTo(500);
        assertThat(leaks).isFalse();
        assertThat(health.status()).isEqualTo(200);
    }
}
