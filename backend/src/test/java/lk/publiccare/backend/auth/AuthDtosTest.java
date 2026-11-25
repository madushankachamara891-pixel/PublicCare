package lk.publiccare.backend.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;
import java.util.stream.Collectors;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/** FR-01.3: the auth request DTOs trim the email (and only the email) before Bean Validation runs. */
class AuthDtosTest {
    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @BeforeAll
    static void createValidator() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @AfterAll
    static void closeValidator() {
        validatorFactory.close();
    }

    private static Set<String> invalidFields(Set<? extends ConstraintViolation<?>> violations) {
        return violations.stream().map(v -> v.getPropertyPath().toString()).collect(Collectors.toSet());
    }

    @Test
    void registerRequestTrimsOnlyTheEmail() {
        var request = new AuthDtos.RegisterRequest("  Citizen  ", " \tA@X.com \n", "  pass word with spaces  ");

        assertEquals("A@X.com", request.email());
        assertEquals("  Citizen  ", request.fullName());
        assertEquals("  pass word with spaces  ", request.password());
    }

    @Test
    void loginRequestTrimsOnlyTheEmail() {
        var request = new AuthDtos.LoginRequest("  A@X.com  ", "  pass word with spaces  ");

        assertEquals("A@X.com", request.email());
        assertEquals("  pass word with spaces  ", request.password());
    }

    @Test
    void paddedEmailPassesValidationBecauseItIsTrimmedFirst() {
        assertTrue(validator.validate(new AuthDtos.RegisterRequest(
                "Citizen", "  A@X.com  ", "a-long-test-password")).isEmpty());
        assertTrue(validator.validate(new AuthDtos.LoginRequest("  A@X.com  ", "a-long-test-password")).isEmpty());
    }

    @Test
    void invalidOrBlankEmailStillFailsValidation() {
        assertEquals(Set.of("email"), invalidFields(validator.validate(
                new AuthDtos.RegisterRequest("Citizen", "  bad  ", "a-long-test-password"))));
        assertEquals(Set.of("email"), invalidFields(validator.validate(
                new AuthDtos.RegisterRequest("Citizen", "   ", "a-long-test-password"))));
        assertEquals(Set.of("email"), invalidFields(validator.validate(
                new AuthDtos.LoginRequest("  bad  ", "a-long-test-password"))));
    }

    @Test
    void missingEmailStaysNullAndIsReportedByValidation() {
        var register = new AuthDtos.RegisterRequest("Citizen", null, "a-long-test-password");
        var login = new AuthDtos.LoginRequest(null, "a-long-test-password");

        assertNull(register.email());
        assertNull(login.email());
        assertEquals(Set.of("email"), invalidFields(validator.validate(register)));
        assertEquals(Set.of("email"), invalidFields(validator.validate(login)));
    }

    @Test
    void passwordLengthIsValidatedOnTheUntrimmedValue() {
        // 10 visible characters + 2 spaces = 12 characters: valid only because spaces are kept.
        assertTrue(validator.validate(new AuthDtos.RegisterRequest(
                "Citizen", "a@x.com", " abcdefghij ")).isEmpty());
    }
}
