# Phase 3A evidence: FR-01.3 / API-04 email trimming correction

| Item | Value |
|---|---|
| Date | 2026-11-25 |
| Branch | `qa/final-assignment` (local), HEAD `f565fb4`. **All changes are uncommitted** in the working tree |
| Owner decision | **D-1, Option A** (approved by the project owner): trim email whitespace at the API boundary **before Bean Validation**, then lower-case in the service layer. Same policy for login. Passwords unchanged. No global Jackson string trimming |
| Resolves | Finding **F-03** and inconsistency **U-5** (`../CROSS_REFERENCE.md`); Test Plan contradiction C-5 |
| Recorded in | RTM "Owner decisions" D-1, TASK 5 (FR-01.3, FR-01.4), TASK 6-C (API-04); Test Plan §10.4 API-04 |

## Cause (confirmed)

At `f565fb4`, `RegisterRequest.email` and `LoginRequest.email` carry `@NotBlank @Email @Size(max = 254)`. Trimming happened only later, in `AuthService.register` and `AuthController.login`. So a padded email such as `"  a@x.com  "` was rejected by `@Email` before it could be trimmed. The response body, captured in run 01 (Member 3 had not inspected it), is:

```
400 {"error":"email must be a well-formed email address"}
```

A padded 254-character email was also rejected by `@Size(max = 254)`, because the spaces were counted: `400 {"error":"email size must be between 0 and 254"}`.

## Change

Only one production file changed: `backend/src/main/java/lk/publiccare/backend/auth/AuthDtos.java` (diff: [AuthDtos-email-trim_f565fb4..working-tree.diff](AuthDtos-email-trim_f565fb4..working-tree.diff)).

| Lines | Change |
|---|---|
| 18-20 | `trimEmail(String)`: returns `null` for `null` (so `@NotBlank` still reports a missing email), otherwise `email.trim()` (the same `trim()` the service and controller already used) |
| 26-28 | `RegisterRequest` compact constructor: `email = trimEmail(email)` |
| 38-40 | `LoginRequest` compact constructor: `email = trimEmail(email)` |

Jackson creates the records through their canonical constructor, so the compact constructor runs before Spring calls Bean Validation (`@Valid`). `fullName` and `password` are not touched. Lower-casing is unchanged: `AuthService.register` (line 24), `AuthController.login` (line 57), `AuthService.findResponse` and `DatabaseUserDetailsService`. `AuthService` and `AuthController` were not modified; their own `trim()` calls are now redundant but harmless.

## Tests added

| Class | Type | Tests | What it checks |
|---|---|---:|---|
| `backend/src/test/java/lk/publiccare/backend/auth/AuthEmailNormalizationApiTest.java` | `@SpringBootTest` + `@AutoConfigureMockMvc`: the whole application (security filter chain with CSRF, validation, `ApiExceptionHandler`, `AuthService`, JPA) on in-memory H2 (PostgreSQL mode). Same H2 settings as the existing `ReportDeletionJpaTest`. No new dependency, no `pom.xml` change | 7 | API-04 steps 1-4 (padded mixed-case → `201`, stored normalised; lower-case and padded upper-case duplicates → `409`); padded duplicate of an unpadded account → `409`; padded login → `200` and `/me`; invalid/blank/whitespace-only/missing email → `400` (register and login); padded 254-character email → `201`; password with surrounding spaces stored and checked unchanged |
| `backend/src/test/java/lk/publiccare/backend/auth/AuthDtosTest.java` | Plain JUnit + Hibernate Validator | 6 | DTOs trim only the email; padded email passes validation; invalid and blank emails still fail; `null` stays `null` and is reported; password length is validated on the untrimmed value |

## Runs

| # | File | Command (in `backend/`) | Code | Result |
|---|---|---|---|---|
| 00 | [00-baseline-before-change.txt](00-baseline-before-change.txt) | `./mvnw -B clean test` | `f565fb4`, no change | **72 tests, 0 failures, 0 errors, 0 skipped. BUILD SUCCESS** |
| 01 | [01-red-before-fix.txt](01-red-before-fix.txt) | `./mvnw -B test -Dtest=AuthEmailNormalizationApiTest,AuthDtosTest` | new tests, production code unchanged | **13 tests, 9 failures** (expected red): every padded-email case got `400`; the 4 tests for invalid input, `null` and password length passed |
| 02 | [02-green-after-fix.txt](02-green-after-fix.txt) | same as 01 | with the `AuthDtos.java` change | **13 tests, 0 failures, 0 errors. BUILD SUCCESS** |
| 03 | [03-full-suite-after-fix.txt](03-full-suite-after-fix.txt) | `./mvnw -B clean test` | with the change | **85 tests, 0 failures, 0 errors, 0 skipped. BUILD SUCCESS** (72 existing + 13 new; existing `AuthControllerTest` 7/7, `AuthServiceTest` 4/4) |

Surefire reports for the two new classes (from run 03): [surefire-reports/](surefire-reports/).

The `ERROR ... Admin report deletion failed for report 51.` line in runs 00 and 03 is logged on purpose by an existing `ReportControllerTest` case; that test passes.

## Limitations

- **Java 27** (Oracle, build 27+35) was the only JDK available; the project targets Java 21. Not run on Java 21. *[Phase 5B rev. 1, 2026-11-25: superseded for the Java 21 part – the same working tree (`f565fb4` + D-1) was later run on Java 21 (Ubuntu OpenJDK 21.0.13.1, WSL2 Ubuntu): 85 tests, 0 failures, H2 only. PostgreSQL is still not verified. See [CONFIRMATION_REGISTER.md](../phase5b-confirmation-register/CONFIRMATION_REGISTER.md) ENV-J6.]*
- **H2**, not PostgreSQL. The `users.email` unique constraint and the `409` path are exercised on H2 only.
- MockMvc, not a real HTTP server or browser. The frontend was not exercised. Browsers already strip surrounding whitespace from `<input type="email">`, so MT-04 (manual UI) is likely to behave the same, but MT-04 was **not executed**.
- `trim()` removes ASCII control characters and spaces (≤ U+0020) only. Unicode spaces such as U+00A0 (no-break space) are not removed and reach `@Email` untrimmed (expected `400`, as before the change). Not tested.
- Member 3's reference test `emailWithSurroundingSpacesIsRejectedByValidation` (in `../3.5-3.6_member3/reference-code/`, **not part of this build**) asserts the old `400` and would now fail if it were applied. It was not changed, because it is a member's original copy. *[Phase 3B: marked **superseded** in `../3.5-3.6_member3/evidence/api-automation/README.md`, `../3.5-3.6_member3/evidence/README.md` and `../CROSS_REFERENCE.md` (F-03). Its expected `400` is not the current requirement; `AuthEmailNormalizationApiTest` and `AuthDtosTest` above replace it.]*
- No combined run with other members' test additions exists (README G-8).
