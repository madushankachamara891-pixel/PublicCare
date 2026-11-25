package lk.publiccare.backend.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import lk.publiccare.backend.user.PublicCareUser;
import lk.publiccare.backend.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

/**
 * FR-01.3 / API-04: email whitespace is trimmed at the API boundary before Bean Validation,
 * then lower-cased by the service, for both registration and login (owner decision: Option A).
 *
 * <p>Runs the whole application (real security filter chain, CSRF, validation, exception handler,
 * {@link AuthService} and JPA) against an in-memory H2 database. All data is synthetic.
 */
@SpringBootTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:auth-email-normalization;MODE=PostgreSQL;DB_CLOSE_DELAY=-1",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.flyway.enabled=false",
        "publiccare.upload.directory=target/auth-email-normalization-uploads"
})
@AutoConfigureMockMvc
class AuthEmailNormalizationApiTest {
    private static final String PASSWORD = "Synthetic-Pass-123!";
    private static final String DUPLICATE_MESSAGE = "An account with this email already exists.";

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;
    @Autowired UserRepository users;
    @Autowired PasswordEncoder passwordEncoder;

    /** A unique mixed-case address, e.g. {@code Api04.User-1a2b3c4d@Example.TEST}. */
    private static String uniqueMixedCaseEmail() {
        return "Api04.User-" + UUID.randomUUID().toString().substring(0, 8) + "@Example.TEST";
    }

    private static String normalized(String email) {
        return email.trim().toLowerCase(Locale.ROOT);
    }

    private ResultActions register(String fullName, String email, String password) throws Exception {
        return mockMvc.perform(post("/api/auth/register").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(
                        Map.of("fullName", fullName, "email", email, "password", password))));
    }

    private ResultActions login(String email, String password) throws Exception {
        return mockMvc.perform(post("/api/auth/login").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Map.of("email", email, "password", password))));
    }

    private long accountsWithEmail(String normalizedEmail) {
        return users.findAll().stream()
                .map(PublicCareUser::getEmail)
                .filter(stored -> stored.trim().equalsIgnoreCase(normalizedEmail))
                .count();
    }

    @Test
    @DisplayName("API-04 step 1-2: padded mixed-case email registers with 201 and is stored trimmed and lower-cased")
    void paddedMixedCaseEmailRegistersAndIsStoredNormalized() throws Exception {
        String email = uniqueMixedCaseEmail();
        String expected = normalized(email);

        register("  Synthetic Citizen  ", "  " + email + "  ", PASSWORD)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value(expected))
                .andExpect(jsonPath("$.fullName").value("Synthetic Citizen"))
                .andExpect(jsonPath("$.role").value("CITIZEN"))
                .andExpect(jsonPath("$.password").doesNotExist())
                .andExpect(jsonPath("$.passwordHash").doesNotExist());

        PublicCareUser stored = users.findByEmail(expected).orElseThrow();
        assertEquals(expected, stored.getEmail());
        assertEquals(1, accountsWithEmail(expected));
    }

    @Test
    @DisplayName("API-04 step 3-4: equivalent lower-case and padded upper-case duplicates return 409")
    void equivalentDuplicatesOfPaddedRegistrationAreRejected() throws Exception {
        String email = uniqueMixedCaseEmail();
        String expected = normalized(email);
        register("Synthetic Citizen", "  " + email + "  ", PASSWORD).andExpect(status().isCreated());

        register("Synthetic Citizen", expected, PASSWORD)
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error").value(DUPLICATE_MESSAGE));
        register("Synthetic Citizen", "  " + email.toUpperCase(Locale.ROOT) + "  ", PASSWORD)
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error").value(DUPLICATE_MESSAGE));

        assertEquals(1, accountsWithEmail(expected));
    }

    @Test
    @DisplayName("API-04 (reverse order): padded duplicate of an unpadded registration returns 409, not 400")
    void paddedDuplicateOfUnpaddedRegistrationIsRejectedWithConflict() throws Exception {
        String email = uniqueMixedCaseEmail();
        register("Synthetic Citizen", email, PASSWORD).andExpect(status().isCreated());

        register("Synthetic Citizen", "  " + email + "  ", PASSWORD)
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error").value(DUPLICATE_MESSAGE));
        register("Synthetic Citizen", "\t" + normalized(email) + " ", PASSWORD)
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error").value(DUPLICATE_MESSAGE));

        assertEquals(1, accountsWithEmail(normalized(email)));
    }

    @Test
    @DisplayName("FR-01.3 login: padded mixed-case email logs in to the same account and starts a session")
    void paddedEmailLoginUsesSameNormalizationPolicy() throws Exception {
        String email = uniqueMixedCaseEmail();
        String expected = normalized(email);
        register("Synthetic Citizen", email, PASSWORD).andExpect(status().isCreated());

        MvcResult result = login("  " + email.toUpperCase(Locale.ROOT) + "  ", PASSWORD)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(expected))
                .andReturn();
        MockHttpSession session = (MockHttpSession) result.getRequest().getSession(false);
        mockMvc.perform(get("/api/auth/me").session(session))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(expected));

        login("  " + email + "  ", "Wrong-Pass-123!")
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error").value("Invalid email or password."));
    }

    @Test
    @DisplayName("FR-01.2: invalid, blank and whitespace-only emails are still rejected with 400")
    void invalidEmailsAreStillRejected() throws Exception {
        long before = users.count();

        register("Synthetic Citizen", "bad", PASSWORD)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").exists());
        register("Synthetic Citizen", "  bad  ", PASSWORD)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").exists());
        register("Synthetic Citizen", "   ", PASSWORD)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").exists());
        register("Synthetic Citizen", "", PASSWORD)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").exists());
        mockMvc.perform(post("/api/auth/register").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"fullName\":\"Synthetic Citizen\",\"password\":\"" + PASSWORD + "\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").exists());

        login("  bad  ", PASSWORD).andExpect(status().isBadRequest());
        login("   ", PASSWORD).andExpect(status().isBadRequest());

        assertEquals(before, users.count());
    }

    @Test
    @DisplayName("FR-01.2 boundary: padding does not count towards the 254-character email limit")
    void emailLengthLimitAppliesToTrimmedValue() throws Exception {
        // 64 + 1 + 189 = 254 characters; a valid address at the @Size(max = 254) boundary.
        String suffix = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
        String local = ("b" + suffix).repeat(8).substring(0, 64);
        String email = local + "@" + "d".repeat(63) + "." + "e".repeat(63) + "." + "f".repeat(56) + ".test";
        assertEquals(254, email.length());

        register("Synthetic Citizen", "   " + email + "   ", PASSWORD)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value(email));
    }

    @Test
    @DisplayName("Passwords are not trimmed: surrounding spaces are part of the stored and checked password")
    void passwordWhitespaceIsPreserved() throws Exception {
        String email = uniqueMixedCaseEmail();
        String paddedPassword = "  Padded-Pass-123!  ";
        String trimmedPassword = paddedPassword.trim();

        register("Synthetic Citizen", "  " + email + "  ", paddedPassword).andExpect(status().isCreated());

        PublicCareUser stored = users.findByEmail(normalized(email)).orElseThrow();
        assertTrue(passwordEncoder.matches(paddedPassword, stored.getPasswordHash()));
        assertFalse(passwordEncoder.matches(trimmedPassword, stored.getPasswordHash()));

        login("  " + email + "  ", paddedPassword).andExpect(status().isOk());
        login(email, trimmedPassword)
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error").value("Invalid email or password."));
    }
}
