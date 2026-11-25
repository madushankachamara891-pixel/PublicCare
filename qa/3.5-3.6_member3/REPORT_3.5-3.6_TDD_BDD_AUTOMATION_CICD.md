# Sections 3.5–3.6: Test-Driven Development, Behaviour-Driven Development, Test Automation and CI/CD

> **Status: PROJECT-INTEGRATED DRAFT.**
> This report was drafted on 2026-11-25, during QA integration (Phase 7). The project owner authorised drafting it from Member 3's verified evidence, because Member 3 did not submit a formal report ([FORMAL_REPORT_MISSING.md](FORMAL_REPORT_MISSING.md)).
> **Member 3 did not write, review or approve this text.** Every result comes from an evidence file that Member 3 committed to their repository. Each one is cited, and its provenance was verified in Phase 6. The report contains **no personal reflection**, because none exists in the evidence (see §9).
> The work it describes (tests, feature files, workflow, runs and screenshots) is Member 3's. Only the explanatory text was written during integration.

## 0. Front matter

| Field | Value |
|---|---|
| Sections | 3.5 Test-Driven Development and Behaviour-Driven Development; 3.6 Test Automation and CI/CD |
| Assigned member | Member 3 – **Kumaranayaka K.I.S, EG/2023/5697** *[Phase 9, 25 Nov 2026: name form and registration number supplied by the project owner (register P-M3-2, P-M3-3, Revision 7). Earlier text: "Name as recorded in the Git history: `Ishani-Kumaranayaka`. Spelling for the report and registration number: PENDING". This does not change the status above: Member 3 did not write or review this draft]* |
| Date of the work | 2026-11-24 (all local runs and CI run #1) |
| Date of this draft | 2026-11-25 (integration). Member 3's own report date: **PENDING** (P-M3-4) |
| Source repository | `madushankachamara891-pixel/PublicCare-QA-Member3-Ishani-` (**private**), branch `member3-tdd-bdd-ci` |
| Final commit reported on | `93d11a34d22fff59f62d211faeacc60dfc3a50dd` (`93d11a3`), 2026-11-24 11:30 +05:30 |
| Commit tested by CI run #1 | `70d01e0`, 2026-11-24 09:52:47 +05:30. This is an **ancestor** of `93d11a3`, **not** `93d11a3` |
| Application under test | Member 3's isolated copy of PublicCare. Its production code is `f565fb4` **plus** two changes that are **not** in the group repository: the `AdminSetupRunner.java` IPv6 change made during TDD, and test-scope dependencies added to `pom.xml`. All results **pre-date** owner decision D-1 (e-mail trimming) |
| Machine for local runs | Windows 11, amd64. Whose machine it was is **not recorded**: the user-profile name in the log paths differs from the commit author (P-M3-5). Nothing is inferred from it |
| Provenance | All 61 Member 3 files in `qa/` were re-hashed against `93d11a3` in Phase 6: 59 are blob-identical, and 2 READMEs differ only by marked integration notes ([Phase 6 §3](../phase6-member-repo-verification/README.md)) |

## 1. Introduction

### 1.1 Purpose and scope

These sections show how the PublicCare test effort moved from planned test cases (Part 01, Sections 3.1–3.2) to tests that are executable and repeatable. The four tasks from the EC4206 task list are:

1. **3.5 TDD:** write unit tests (JUnit) before the implementation and show the Red → Green → Refactor cycle.
2. **3.5 BDD:** write one or two scenarios in Gherkin and run them with Cucumber.
3. **3.6 Test automation:** automate tests with Selenium and/or REST Assured.
4. **3.6 CI/CD:** run the tests automatically with GitHub Actions on every push, and show a passing pipeline.

All four were carried out in Member 3's isolated copy of the application. **None of the tests, the `pom.xml` change, the application change or the workflow has been applied to the group repository** (`publiccare/`, branch `qa/final-assignment`). In the group repository they exist only as reference copies and diffs (§8). Owner decision D-5 on bringing them in is still pending.

### 1.2 Approach

The tests were added in layers, each with a clear purpose and cost:

| Layer | Technique | Tool | What runs | Typical speed |
|---|---|---|---|---|
| Unit | TDD | JUnit 5 | One static method, no Spring context | Milliseconds |
| Acceptance (in process) | BDD | Cucumber-JVM 7.23.0 + MockMvc | Full Spring context and security filter chain, no HTTP server | Seconds |
| API (over HTTP) | Automation | REST Assured 5.5.7 | Real Tomcat on a random port | Seconds |
| UI (end to end) | Automation | Selenium 4.31.0, headless Chrome 157 | Built React frontend + backend + browser | Tens of seconds |
| Pipeline | CI | GitHub Actions | Backend and frontend jobs on every push and pull request | About one minute |

This follows the test-pyramid idea: many fast, isolated tests at the bottom and a few slow end-to-end tests at the top. The slowest layer (Selenium) is kept out of the default build (§5.2).

## 2. Test environment

| Item | Local runs (Member 3 copy) | GitHub Actions run #1 | Evidence |
|---|---|---|---|
| OS | Windows 11, version 10.0, amd64 | `ubuntu-latest` (image version not recorded) | [baseline/environment.txt](evidence/baseline/environment.txt); [ci.yml](reference-code/github-workflows/ci.yml) line 17 |
| JDK | Eclipse Temurin **25.0.5+8 LTS**. The project targets Java 21 | Temurin **21.0.13** (Eclipse Adoptium, Linux) | environment.txt lines 1–3; [ci/artifact-summary.txt](evidence/ci/artifact-summary.txt) line 22 |
| Build | Maven 3.9.16 through the Maven Wrapper | `./mvnw -B test` | environment.txt line 5; ci.yml line 37 |
| Framework | Spring Boot 3.5.16, JUnit Platform 1.12.2 | Same `pom.xml` | [bdd/README.md](evidence/bdd/README.md) line 3 |
| BDD | Cucumber-JVM 7.23.0 (7.34.9 tried first and failed) | Same | bdd/README.md lines 31–32 |
| API automation | REST Assured 5.5.7 (version managed by Spring Boot) | Same | [api-automation/README.md](evidence/api-automation/README.md) line 3 |
| UI automation | Selenium 4.31.0; Chrome 157 headless; ChromeDriver 157.0.8103.78 | `ui` job **not executed** | [ui-automation/README.md](evidence/ui-automation/README.md) line 3 |
| Frontend | Node v24.13.0, npm 11.11.0, Vite 8.3.5 | Node 22 (`setup-node`; exact patch version not recorded) | [frontend/README.md](evidence/frontend/README.md); ci.yml line 62 |
| Database | In-memory H2 in `MODE=PostgreSQL`; Flyway disabled; schema created by Hibernate | H2 | bdd/README.md line 42 |
| Test data | Synthetic only (for example "Nimali Perera", generated unique e-mails) | Same | feature files; READMEs |

**Java version.** The local runs used Java 25, not the project's target of Java 21. The only Java 21 result for Member 3's tests is CI run #1 on commit `70d01e0` (§6).

## 3. Section 3.5 – Test-Driven Development

### 3.1 Concept

In TDD the developer writes a small failing test that specifies the required behaviour (**Red**). They then write the minimum production code that makes it pass (**Green**), and finally improve the design while all tests stay green (**Refactor**). The failing test has to be *observed* before the code is written. Otherwise there is no proof that the test can detect the missing behaviour. Member 3's brief states this explicitly: "Do not label an existing test as TDD unless the development sequence is demonstrated" ([MEMBER_TASKS.md](MEMBER_TASKS.md) line 22). None of the 72 existing tests was relabelled as TDD.

### 3.2 Choice of target

Before choosing a target, the 72 existing backend tests were inspected and run unchanged: 72 tests, 0 failures ([baseline/mvn-test-output.txt](evidence/baseline/mvn-test-output.txt); per-class table in [tdd/README.md](evidence/tdd/README.md) lines 26–40). No existing test referred to `AdminSetupRunner`. This matches the gap the RTM records as **UT-14** (requirement **FR-06.9**, first-administrator provisioning, which is allowed only against a loopback PostgreSQL database).

The Part 01 analysis had suspected that the IPv6 check could never succeed. `URI.getHost()` returns an IPv6 literal **with** brackets (`[::1]`), but the code compared it with `::1`. A unit test was a good TDD target for three reasons: the method is pure (a string goes in, a boolean comes out), it needs no Spring context, and the expected outcome for most inputs is unambiguous.

### 3.3 Test written first

`AdminSetupRunnerLoopbackTest` ([source](reference-code/project/backend/src/test/java/lk/publiccare/backend/admin/AdminSetupRunnerLoopbackTest.java)) was written before any application change. It contains six tests: three positive partitions and three negative ones.

| Test | Input (JDBC URL) | Expected |
|---|---|---|
| `localhostIsLoopback` | `jdbc:postgresql://localhost:5432/publiccare_db` | `true` |
| `ipv4LoopbackIsLoopback` | `jdbc:postgresql://127.0.0.1:5432/publiccare_db` | `true` |
| `ipv6LoopbackInBracketsIsLoopback` | `jdbc:postgresql://[::1]:5432/publiccare_db` | `true` |
| `remoteHostIsRejected` | `jdbc:postgresql://db.example.com:5432/publiccare_db` | `false` |
| `nonPostgresDriverIsRejected` | `jdbc:mysql://localhost:3306/publiccare_db` | `false` |
| `nonJdbcValueIsRejected` | `postgresql://localhost:5432/publiccare_db` | `false` |

### 3.4 Red → Green → Refactor (actual results)

| Step | Change made | Actual result | Evidence |
|---|---|---|---|
| **Red 1** | Test class only; no application change | Compilation failure: `isLoopbackPostgres(java.lang.String) has private access in lk.publiccare.backend.admin.AdminSetupRunner` (at each of the six calls); BUILD FAILURE | [tdd/01-red-compile-error.txt](evidence/tdd/01-red-compile-error.txt) |
| **Red 2** | Test seam only: `private` removed, so the method is package-private. Behaviour unchanged | Tests run 6, Failures 1: `ipv6LoopbackInBracketsIsLoopback` expected `true` but was `false`; BUILD FAILURE | [tdd/02-red-assertion-failure.txt](evidence/tdd/02-red-assertion-failure.txt) |
| **Green** | `host.equals("[::1]")` added to the host check | Tests run 6, Failures 0; BUILD SUCCESS | [tdd/03-green.txt](evidence/tdd/03-green.txt) |
| **Refactor** | The host comparison was extracted into a helper `isLoopbackHost(String)` with an explanatory comment. Behaviour unchanged | Full suite: Tests run 78, Failures 0, Errors 0 (72 existing + 6 new); BUILD SUCCESS; Java 25.0.5 | [tdd/04-refactor-full-suite.txt](evidence/tdd/04-refactor-full-suite.txt) |

The final production change is in [unapplied-diffs/AdminSetupRunner-ipv6-loopback_2c1e597..93d11a3.diff](unapplied-diffs/AdminSetupRunner-ipv6-loopback_2c1e597..93d11a3.diff): one visibility change, one helper and one added comparison.

### 3.5 Discussion

- **There are two Red steps, for different reasons.** Red 1 is a compile failure: the test could not reach a private method. Changing the visibility is a *test seam*, not a behaviour change. Red 2 is the meaningful Red. Five of the six tests passed against the original code, which shows that they describe existing behaviour correctly, and exactly one failed for the behaviour under question. A Red step with only one failing test is good evidence that the test detects that behaviour and nothing else.
- **The Green change was minimal.** Only one comparison was added. The design was improved only in the Refactor step, and the refactor was protected by the full suite (78/0), not only by the new class.
- **Red confirms a behaviour, not a defect.** Whether PublicCare is *meant* to accept the IPv6 loopback `[::1]` is still an open question (RTM Q1, finding F-02), and owner decision D-4 is pending. The failing test proves that the original code rejects `[::1]`. Whether that is a defect depends on the answer to Q1. TDD is used here to *specify* a proposed behaviour and to show the change is safe. It is not a verdict.
- **The change was made in the Member 3 copy only.** The Test Plan (§7.11) requires owner approval for changes to the application. The fix is therefore kept as an unapplied diff. The test class does not compile in the group repository without it, which is why it must stay out of any combined run until D-4 is decided.

## 4. Section 3.5 – Behaviour-Driven Development

### 4.1 Concept

BDD describes behaviour as concrete examples in a business-readable language (Gherkin: `Given` a context, `When` an action, `Then` an observable outcome). Each example is bound to executable step definitions. The scenarios then act as **living acceptance criteria**: anyone in the team can read them, and they fail when the behaviour changes.

### 4.2 Scenarios

Two features with two scenarios each were written. That is four scenarios, more than the one or two the brief asks for.

**Feature 1 – Citizen registers and submits a report** ([citizen_report.feature](reference-code/project/backend/src/test/resources/features/citizen_report.feature))

```gherkin
Scenario: A new citizen registers, logs in and submits a report
  Given a visitor registers with the name "Nimali Perera" and a unique email
  And the registration is accepted with the role "CITIZEN"
  And the citizen logs in with the registered email and password
  When the citizen submits a report titled "Broken streetlight" in the category "Streetlights" at "Galle Road, Colombo"
  Then the report is created
  And the report status is "PENDING"
  And the report reference starts with "PC-"
  And the report belongs to the logged in citizen

Scenario: A visitor who is not logged in cannot submit a report
  Given a visitor who is not logged in
  When the visitor submits a report titled "Blocked drain" in the category "Drainage" at "Main Street"
  Then the request is rejected because authentication is required
```

**Feature 2 – Administrator updates a report status** ([admin_status_update.feature](reference-code/project/backend/src/test/resources/features/admin_status_update.feature))

```gherkin
Scenario: Administrator moves a pending report to in progress with a note
  Given a citizen has submitted a report titled "Pothole near the school"
  And an administrator is logged in
  When the administrator sets the report status to "IN_PROGRESS" with the note "Repair crew assigned"
  Then the status update is accepted
  And the citizen sees the report status "IN_PROGRESS"
  And the citizen sees the administrator note "Repair crew assigned"

Scenario: A citizen cannot change the status of a report
  Given a citizen has submitted a report titled "Overflowing bin"
  When the citizen tries to set the report status to "RESOLVED"
  Then the request is rejected because access is denied
  And the report status is still "PENDING" for the citizen
```

Each scenario follows the Given/When/Then roles correctly. `Given` sets up state (accounts, reports, sessions), `When` performs exactly one user action, and `Then` checks outcomes that a user can observe (HTTP status, status, note, ownership). Scenarios 2 and 4 are **negative** scenarios: they show that the system refuses an action. This matters for a public-sector service, where authorisation failures are as important as the success paths.

### 4.3 Traceability

| # | Scenario | Requirements | Related Part 01 cases |
|---|---|---|---|
| 1 | Register, log in, submit a report | FR-01.1, FR-01.5, FR-02.2, FR-03.1, FR-03.3 | API-02, API-10 |
| 2 | Visitor not logged in cannot submit | FR-02.8 | API-09 (principle) |
| 3 | Admin moves a report to IN_PROGRESS with a note | FR-06.6, FR-05.2 | API-16 |
| 4 | Citizen cannot change a report's status | **FR-02.8, FR-06.6** | API-09 (principle) |

*Integration correction (scenario 4).* The feature-file comment and Member 3's README list FR-08.5 ("Citizens cannot delete reports") for scenario 4. The step sends a citizen `PATCH /api/admin/reports/{id}/status` and expects `403` ([PublicCareSteps.java](reference-code/project/backend/src/test/java/lk/publiccare/backend/bdd/PublicCareSteps.java) lines 222–231). No deletion is involved. The behaviour checked is the admin-route authorisation rule (FR-02.8: `/api/admin/**` needs `ROLE_ADMIN`, otherwise `403`) applied to the status-change endpoint (FR-06.6). This draft therefore uses FR-02.8 and FR-06.6 ([REQUIREMENT_MAPPING_REVIEW.md](../phase5a-p0-corrections/REQUIREMENT_MAPPING_REVIEW.md) §1; register REJ-6). The reference copy of the feature file is left unchanged.

### 4.4 How the scenarios run

- `@SpringBootTest` starts the real application once: controllers, Spring Security with sessions and CSRF, services, JPA and BCrypt ([CucumberSpringConfiguration.java](reference-code/project/backend/src/test/java/lk/publiccare/backend/bdd/CucumberSpringConfiguration.java)). Requests are sent with **MockMvc**, so no HTTP server or browser is involved.
- CSRF is handled the way the React frontend handles it. The step code calls `/api/auth/csrf`, sends the **raw** token from the `XSRF-TOKEN` `Set-Cookie` header back as a cookie, and sends the **masked** token from the JSON body as the `X-XSRF-TOKEN` header (PublicCareSteps.java lines 49–67).
- The administrator is inserted directly into the test database, because no web route creates administrators (FR-06.9).
- The runner is `RunCucumberTest` on the JUnit Platform, so the scenarios run inside the normal `./mvnw test` and appear in Surefire as four tests.

### 4.5 Execution history (actual results)

| Run | Result | Cause / meaning | Evidence |
|---|---|---|---|
| Dependency check, Cucumber 7.34.9 | 0 tests run; `NoClassDefFoundError: DiscoveryIssueReporter`; BUILD FAILURE | Cucumber 7.34.9 is built for JUnit 5.14, but Spring Boot 3.5.16 manages JUnit 5.12.2 | [bdd/01a-fail-cucumber-7.34.9.txt](evidence/bdd/01a-fail-cucumber-7.34.9.txt) |
| Dependency check, Cucumber 7.23.0 | 78/0; BUILD SUCCESS (no features yet) | The compatible version does not break the existing suite | [bdd/01b-pom-deps-regression-7.23.0.txt](evidence/bdd/01b-pom-deps-regression-7.23.0.txt) |
| Scenarios, run 1 | 4 run, **4 failed**: three `expected: <201> but was: <403>`, and scenario 2 `expected: <401> but was: <403>` | A test-code error. The step code sent the masked token as the cookie, so Spring Security rejected every write with `403` **before** authentication was evaluated. That is why the anonymous scenario also received `403` instead of `401`. It was not an application defect | [bdd/02a-run1-fail-csrf-403.txt](evidence/bdd/02a-run1-fail-csrf-403.txt) lines 77–162 |
| Scenarios, run 2 | 4 run, 0 failures; BUILD SUCCESS | CSRF handling corrected | [bdd/02b-run2.txt](evidence/bdd/02b-run2.txt) |
| Full suite | 82/0 (72 + 6 TDD + 4 scenarios); BUILD SUCCESS; Java 25.0.5 | No regression | [bdd/03-full-suite.txt](evidence/bdd/03-full-suite.txt) |
| Reports | Cucumber HTML and JSON | – | [bdd/cucumber-reports/](evidence/bdd/cucumber-reports/) |

*Correction to the earlier records.* Member 3's BDD README (line 33) and the Phase 5A skeleton describe run 1 as "4 failures, `expected 201 but was 403`". The log shows that one of the four failures was `expected 401 but was 403` (log lines 128–140). The cause is the same. This draft reports the log.

### 4.6 Discussion and limitations

- Run 1 shows the value of negative scenarios. A wrong CSRF implementation in the *test* made the anonymous case fail with an unexpected status. If the tests had only checked "request rejected", this mistake would have gone unnoticed.
- **H2 instead of PostgreSQL.** The check that the reference starts with "PC-" passes because the service falls back to its own format. H2 does not generate the `reference` column that PostgreSQL generates, so the database-generated value is **not** verified. That needs INT-01 on PostgreSQL.
- Because the scenarios use MockMvc, they do not cover browser-side behaviour (for example FR-01.6 or FR-02.7). That is covered by Selenium (§5.2).

## 5. Section 3.6 – Test automation

### 5.1 API automation with REST Assured (`PublicCareApiTest`, 9 tests)

**Tool choice.** The Test Plan (§7.5) proposes REST Assured. It is written in Java next to the existing JUnit tests, and Spring Boot manages a compatible version (5.5.7). Postman/Newman was not used.

**Design.** `@SpringBootTest(webEnvironment = RANDOM_PORT)` starts the real application on a real Tomcat at a random port. REST Assured then sends real HTTP requests, keeps the `JSESSIONID` cookie and handles CSRF as the frontend does. This tests what the Cucumber layer cannot: real HTTP serialisation, cookies and the servlet container ([source](reference-code/project/backend/src/test/java/lk/publiccare/backend/api/PublicCareApiTest.java)).

| Test method | Part 01 case | Requirement(s) | What is checked | Result (Member 3 copy, 2026-11-24) |
|---|---|---|---|---|
| `csrfEndpointReturnsTokenAndCookie` | API-01 | FR-02.1 | 200, `token` in the body, `XSRF-TOKEN` cookie | PASS |
| `registrationForcesCitizenRoleAndHidesPassword` | API-02 (steps 1–3) | FR-01.1, FR-01.5 | Body sends `"role":"ADMIN"`; 201, role `CITIZEN`, no `password` / `passwordHash` field | PASS |
| `passwordLengthBoundary` | API-03 (11 and 12 characters only) | FR-01.2 | 11 characters → 400; 12 characters → 201 | PASS |
| `duplicateEmailWithDifferentCaseIsRejected` | API-04 (case) | FR-01.3, FR-01.4 | Upper-case duplicate → 409 | PASS |
| `emailWithSurroundingSpacesIsRejectedByValidation` | API-04 (padding) | FR-01.3 | Padded duplicate → 400 | PASS at the time – **HISTORICAL, superseded by owner decision D-1** |
| `meRequiresSession` | API-08 (steps 1–2) | FR-02.5 (FR-02.6 logout = steps 3–5, not automated) | `/api/auth/me` 401 without a session, 200 with one | PASS |
| `adminEndpointAuthorisationMatrix` | API-09 (`GET /api/admin/reports` only) | FR-02.8, FR-06.4 | Anonymous 401, citizen 403, admin 200 | PASS |
| `createReportUsesSessionOwner` | API-10 | FR-03.1, FR-03.3 | Body sends `ownerId` 999999; 201, `PENDING`, reference `^PC-\d{8}$`, owner taken from the session | PASS |
| `otherUsersReportLooksLikeMissingReport` | API-15 (GET only) | FR-04.2 | Another user's report and a missing report both return 404 with identical bodies; the owner gets 200 | PASS |

| Run | Result | Evidence |
|---|---|---|
| 1 | Build failure before any test ran (`bad class file … NoSuchFileException` in `target/classes`). Cause **inferred, not proven**: stale build output after the `pom.xml` change | [01a-run1-stale-target-build-failure.txt](evidence/api-automation/01a-run1-stale-target-build-failure.txt) |
| 2 (`clean`) | 8 run, 1 failure: the single duplicate-e-mail test expected `409` but got `400` for the padded e-mail | [01b-run2-fail-duplicate-email-400.txt](evidence/api-automation/01b-run2-fail-duplicate-email-400.txt) |
| 3 | 9/0; BUILD SUCCESS. The failing test was split into a case test and a padding test | [02-run3.txt](evidence/api-automation/02-run3.txt) |
| Full suite | 91/0 (72 + 6 + 4 + 9); BUILD SUCCESS; Java 25.0.5 | [03-full-suite.txt](evidence/api-automation/03-full-suite.txt) |

**The padded e-mail finding and why the test is historical.** Run 2 showed that a duplicate e-mail with surrounding spaces returned `400`, not the `409` that API-04 expects. Member 3 recorded this behaviour as a characterisation test and as a finding that needed clarification, not as a defect. During integration the `400` response body was inspected (`{"error":"email must be a well-formed email address"}`). It confirmed that `@Email` validated the untrimmed value before the service trimmed it. The project owner then decided **D-1, Option A**: trim the e-mail at the API boundary before validation. In the group repository a padded duplicate now returns `409`, and API-04 is PASS ([phase3a-api04-correction/](../phase3a-api04-correction/README.md)). The `400` is therefore reported **only as what was observed in the Member 3 copy on 2026-11-24**. It is not current or expected behaviour, and this test must not be applied to the group repository (register REJ-1).

This is a good example of automation contributing to requirements quality. An ambiguous expected result ("different case **or padding**") was exposed by an executable test, raised as a question and resolved by an owner decision.

**Not automated:** the other API-03 boundaries, API-05 (CSRF on every write), API-06, API-07, the remaining API-09 endpoints, API-11 – API-14 (including images) and API-16 – API-25 ([api-automation/README.md](evidence/api-automation/README.md) line 32).

### 5.2 UI automation with Selenium (`PublicCareUiIT`, 6 tests)

**Decision history.** UI automation was first *not* planned, because API automation already met the task and a browser test needs the frontend, backend, database and browser together. A later check found the setup practical: the form fields have stable IDs, Spring Boot manages a Selenium version, the built frontend can be served from inside the test, and H2 can replace PostgreSQL. Selenium was then added ([ui-automation-decision.md](evidence/ui-automation-decision.md)).

**Design.** The backend starts on port 8080, the API address built into the frontend. The built frontend (`project/dist`) is served on port 5199 by a small HTTP server inside the test. Each test uses a new headless Chrome profile ([source](reference-code/project/backend/src/test/java/lk/publiccare/backend/ui/PublicCareUiIT.java) lines 47–85).

**Why it is not part of the 91.** The class name ends in `IT`. Maven Surefire's default includes do not pick up `*IT` classes, so `./mvnw test` runs 91 tests and **not** 97. The UI tests run only with `-Dtest=PublicCareUiIT`. This keeps the slow browser layer out of the fast feedback loop.

| Test method | Part 01 case | Requirement(s) | What is checked | Result |
|---|---|---|---|---|
| `registrationBoundariesAndMismatch` | UI-01 | FR-01.1, FR-01.2, FR-01.6 | Name max 120 characters; password 11 blocked, 12 and 72 accepted; mismatch blocked with "Passwords do not match." | PASS |
| `loginRedirectsByRole` | UI-02 | FR-02.7 | Citizen → `/my-reports`; admin → `/admin` | PASS |
| `failedLoginShowsGenericAlert` | UI-03 | FR-02.3 | Wrong password and unknown e-mail show the same "Invalid email or password." alert | PASS |
| `signedOutUserCannotSubmitReport` | UI-04 | FR-03.8 | Submit disabled; sign-in notice and link visible | PASS |
| `requiredFieldsAndSuccessfulReport` | UI-05 | FR-03.1, FR-03.2 | Each of the 4 required fields blocks submission; a valid report shows `PC-########` | PASS |
| `adminPagesAreGatedForCitizen` | UI-13 | FR-02.9 | `/admin`, `/admin/issues`, `/admin/update-issue` show "Administrator access required" | PASS |

| Run | Result | Evidence |
|---|---|---|
| Normal suite with Selenium on the class path, run 1 | 0 tests; class files missing from `target/test-classes`; BUILD FAILURE. Cause **not proven** (another process probably rebuilt the project) | [01a-run1-missing-class-files.txt](evidence/ui-automation/01a-run1-missing-class-files.txt) |
| Normal suite, clean build | 91/0 – the new dependency causes no regression | [01b-regression-with-selenium.txt](evidence/ui-automation/01b-regression-with-selenium.txt) |
| UI run 1 | 6/0 | [02-ui-run1.txt](evidence/ui-automation/02-ui-run1.txt) |
| **Fault check (deliberate)** | 6 run, **2 failures, as intended**: `expected: <Passwords do not match.> but was: <BROKEN MESSAGE>` and `expected: <Administrator access required> but was: <BROKEN GATE>` | [03-fault-check-expected-failures.txt](evidence/ui-automation/03-fault-check-expected-failures.txt) lines 89–122 |
| UI final (after rebuilding `dist`) | 6/0 | [04-ui-final.txt](evidence/ui-automation/04-ui-final.txt) |
| Normal suite, final | 91/0 | [05-full-suite.txt](evidence/ui-automation/05-full-suite.txt); [final/full-suite.txt](evidence/final/full-suite.txt) |
| Screenshots | 6, the last page of each test | [ui-automation/screenshots/](evidence/ui-automation/screenshots/) |

**What the fault check shows.** Two user-visible texts were changed on purpose in the *build output only* (`dist`). Exactly the two tests that assert those texts failed, and they failed with the injected values. This is a small, manual form of fault injection (mutation testing). It shows that the passing UI tests really observe the page. Tests that pass whatever the page shows would give false confidence. The build output was rebuilt afterwards, and the final run was green.

**Limitations.** These are local runs only (Java 25, Chrome 157, Windows 11). Only Chrome was tested, with no mobile width. Six of the 14 proposed UI cases are covered (UI-06 – UI-12 and UI-14 are not). "No request was sent" is checked by confirming that nothing was stored after a short wait, not by inspecting network traffic. Fixed ports 8080 and 5199 must be free.

### 5.3 Frontend checks (local)

`npm ci`, `npm run lint` and `npm run build` all exited with code 0 (Node 24.13.0; Vite 8.3.5, 46 modules). `npm ci` reported **3 moderate-severity vulnerabilities**. These were not investigated, because fixing them would change dependencies. There are no frontend unit tests ([frontend/README.md](evidence/frontend/README.md)).

## 6. Section 3.6 – CI/CD with GitHub Actions

### 6.1 Workflow design

The workflow ([reference copy of `.github/workflows/ci.yml`](reference-code/github-workflows/ci.yml)) runs on every `push` and `pull_request`, with read-only repository permissions and no secrets.

| Job | Steps | Purpose |
|---|---|---|
| `backend` – "Backend (Java 21, Maven Wrapper)" | Checkout → Temurin 21 with Maven cache → `./mvnw -B test` → upload Surefire and Cucumber reports (`if: always()`) | Runs the 91 backend tests (72 existing, 6 TDD, 4 BDD, 9 REST Assured) on the project's target Java version |
| `frontend` – "Frontend (Node.js, lint and build)" | Checkout → Node 22 with npm cache → `npm ci` → `npm run lint` → `npm run build` | Fails the pipeline on lint errors or a broken build |
| `ui` – "UI tests (Selenium, headless Chrome)" | Java 21 + Node 22 → build the frontend → `./mvnw -B test -Dtest=PublicCareUiIT` → upload reports and screenshots | Runs the browser tests in CI. **Added after run #1. Never executed** |

The jobs run in parallel, so a frontend failure and a backend failure are reported separately. Reports are uploaded even when tests fail (`if: always()`), so a red build can be diagnosed from its artifacts. All the stages suggested in [MEMBER_TASKS.md](MEMBER_TASKS.md) (lines 53–61) are covered.

### 6.2 Run #1 – the passing pipeline (actual result)

| Fact | Value | Evidence |
|---|---|---|
| Run | Workflow "CI", run **#1**, run ID 45326010526, trigger `push` to `member3-tdd-bdd-ci` | [ci/README.md](evidence/ci/README.md) lines 7–10; screenshot `github-actions-run-1-summary.png` |
| Commit | **`70d01e0`** ("docs(evidence): record UI automation decision and limitation (section 3.6)") | ci/README.md line 10 |
| Status | Success; total 1 m 6 s. Backend job 1 m 1 s; frontend job 16 s | ci/README.md lines 11–14 |
| Backend result | `Tests run: 91, Failures: 0, Errors: 0, Skipped: 0`, BUILD SUCCESS, 49.346 s, finished 2026-11-24T04:24:04Z; Cucumber 4/0 | ci/README.md line 15; screenshot `github-actions-run-1-backend-log-91-tests.png` |
| Artifact | `backend-test-reports`, 364 KB. Downloaded and read: 15 classes, 91 tests, 0/0/0; Java 21.0.13, Eclipse Adoptium, Linux | [ci/artifact-summary.txt](evidence/ci/artifact-summary.txt); screenshot `github-actions-run-1-annotations-artifacts.png` |
| Frontend result | "Success" from the job status. The job log was **not inspected** | ci/README.md line 49 |
| Warnings | Node.js 20 actions deprecated; `setup-java@v4` deprecated | ci/README.md lines 39–44 |

Screenshots: [ci/](evidence/ci/). In Phase 6 the run number, commit, job names, 91/0, artifact digest prefix and timestamps in the screenshots were cross-checked against the Git history, `ci.yml` at `70d01e0` and `artifact-summary.txt`. All were consistent ([Phase 6 §4.2](../phase6-member-repo-verification/README.md)).

### 6.3 What run #1 does and does not show

- **CI ran on `70d01e0`, not on the final commit `93d11a3`.** No CI run of `93d11a3` is recorded.
- Between `70d01e0` and `93d11a3`, the only changes outside `evidence/` and `qa/` are: one Javadoc comment line in `PublicCareApiTest.java`; the test-scoped `selenium-java` dependency; the new `PublicCareUiIT.java` (not run by `./mvnw test`); and the `ui` job. So the **executable code of the 91 tests is the same** at `93d11a3` (Phase 6 finding F6-1). That is a code comparison, **not a CI result** for `93d11a3`.
- **The `ui` job was configured but never executed.** The Selenium tests have local results only.
- The repository is **private**. A marker without collaborator access cannot open the run URL. The three screenshots and `artifact-summary.txt` are the evidence a marker can see (P-M3-8).
- **The workflow is not active in the group repository.** GitHub runs workflows only from `.github/workflows/` at the repository root. The group repository has no `.github/` folder, and the file is kept as an inactive reference copy (decision D-5 pending).

## 7. Execution status summary

The counts below are runs of **the Member 3 copy only**. They must **not** be added to Member 2's counts (72 / 79 / 84), Member 4's (72) or the group repository's (85). They come from different copies with different code (register REJ-5).

### 7.1 Tests executed locally (Member 3 copy, Windows 11, Java 25.0.5, H2)

| Run | Tests | Failures | Evidence |
|---|---:|---:|---|
| Baseline (no change) | 72 | 0 | [baseline/mvn-test-output.txt](evidence/baseline/mvn-test-output.txt) |
| TDD Red 2 (new class only) | 6 | 1 (intended Red) | tdd/02 |
| TDD Green (new class only) | 6 | 0 | tdd/03 |
| After TDD refactor (full suite) | 78 | 0 | tdd/04 |
| BDD scenarios run 1 / run 2 | 4 / 4 | 4 / 0 | bdd/02a, bdd/02b |
| After BDD (full suite) | 82 | 0 | bdd/03 |
| REST Assured run 2 / run 3 | 8 / 9 | 1 / 0 | api-automation/01b, 02 |
| After API automation (full suite) | 91 | 0 | api-automation/03 |
| Final full suite (`./mvnw test`) | 91 | 0 | ui-automation/05; final/full-suite.txt |

### 7.2 Tests executed in CI (GitHub Actions, Member 3 repository)

| Run | Commit | Environment | Tests | Failures |
|---|---|---|---:|---:|
| Run #1, `backend` job | **`70d01e0`** | ubuntu-latest, Temurin 21.0.13, H2 | 91 | 0 |
| Run #1, `frontend` job | `70d01e0` | ubuntu-latest, Node 22 | lint + build | job status "Success" (log not inspected) |

### 7.3 Selenium / UI tests – executed locally, not part of `./mvnw test`

| Run | Tests | Failures | Note |
|---|---:|---:|---|
| UI run 1 | 6 | 0 | `-Dtest=PublicCareUiIT`, Chrome 157 headless |
| Fault check | 6 | 2 | **Intended** failures (fault injected in `dist`) |
| UI final | 6 | 0 | After rebuilding `dist` |

### 7.4 Not executed

| Item | Status |
|---|---|
| CI on the final commit `93d11a3` | **NOT EXECUTED** |
| CI `ui` job (Selenium in GitHub Actions) | **NOT EXECUTED** (configured only) |
| Any of Member 3's tests in the group repository | **NOT EXECUTED** (reference copies only; D-4, D-5 pending) |
| Any Member 3 test on PostgreSQL; INT-01 | **NOT EXECUTED** by Member 3 (H2 only) *[Phase 9: INT-01 was later run by the project owner – PARTIALLY EXECUTED; see §9 point 2]* |
| Code coverage of Member 3's tests | **NOT MEASURED** (no JaCoCo) |
| Part 01 API cases not listed in §5.1; UI-06 – UI-12, UI-14 | **NOT AUTOMATED** |
| `npm audit` for the 3 reported vulnerabilities | **NOT EXECUTED** |

## 8. Where the work is in the group submission

| Item | Location in `qa/3.5-3.6_member3/` | Applied to the group repository? |
|---|---|---|
| TDD test, BDD steps/config/runner, REST Assured and Selenium classes | `reference-code/project/backend/src/test/java/…` | No (D-5) |
| Feature files | `reference-code/project/backend/src/test/resources/features/` | No |
| Workflow | `reference-code/github-workflows/ci.yml` (inactive) | No (D-5) |
| `AdminSetupRunner` IPv6 change | `unapplied-diffs/AdminSetupRunner-ipv6-loopback_2c1e597..93d11a3.diff` | No (D-4) |
| Test dependencies (Cucumber, REST Assured, Selenium) | `unapplied-diffs/pom-test-dependencies_2c1e597..93d11a3.diff` | No (D-5) |
| Member 3's edits to Part 01 status | `unapplied-diffs/part01-execution-status-edits_2c1e597..93d11a3.diff` | **No, and it must not be applied** (REJ-4). The group reconciliation is in the [Section 3.2 reconciliation table](../phase7-final-completion/SECTION_3.2_EXECUTION_RECONCILIATION.md) |
| All logs, reports and screenshots | `evidence/` | Evidence only |

## 9. Limitations

1. **Java 25 locally.** The only Java 21 result is CI run #1 on `70d01e0`.
2. **H2 only.** No PostgreSQL was used anywhere. The database-generated `reference` column is not exercised, and INT-01 was not done by Member 3. *[Phase 9, 25 Nov 2026: group-level PostgreSQL status. INT-01 was later run by the project owner on a dedicated local PostgreSQL 18.6 database (Phase 8A): **PARTIALLY EXECUTED** – (a) fresh database PASS, (b) restart PASS, (c) existing database baselined at V2 BLOCKED / NOT EXECUTED (RTM Q2), (d) schema ownership PASS with the effective run-time `ddl-auto` NOT VERIFIED. INT-01 exercised start-up, migrations and health only; no API call wrote or read data, so the database-generated `reference` value and JPA persistence on PostgreSQL remain NOT VERIFIED, and every automated test suite still ran on H2 only. See the [INT-01 record](../phase7-final-completion/int01-postgresql/README.md)]*
3. **The TDD result depends on an unapplied application change.** The intended IPv6 behaviour is still an open question (RTM Q1, F-02; D-4 pending).
4. **A superseded test.** `emailWithSurroundingSpacesIsRejectedByValidation` is historical (D-1).
5. **Partial coverage.** There are 4 scenarios; 9 API tests that cover parts of 8 API cases; and 6 of 14 UI cases. No code-coverage figure exists.
6. **CI.** There is only one documented run, and it is on `70d01e0`. The `ui` job was never executed, the artifact ZIP was not committed (summary only), and the frontend job log was not inspected.
7. **Inferred causes.** The causes of `api-automation/01a` and `ui-automation/01a` are inferred, not proven.
8. **Vulnerabilities.** The 3 moderate npm vulnerabilities were not investigated.
9. **Authorship.** This report is a project-integrated draft (see the status box). The machine used for the local runs is not confirmed (P-M3-5).
10. **No personal reflection.** Member 3's own reflection on the TDD, BDD and CI work does not exist in any record and was not invented. The group-level Agile/DevOps reflection in Section 3.10 draws on this work from the evidence only.

## 10. Open items (from the confirmation register)

| ID | Item | State in this draft |
|---|---|---|
| P-M3-1 | Report narrative | Drafted from evidence under the owner's Phase 7 authorisation; **not reviewed by Member 3** |
| P-M3-2, P-M3-3 | Name spelling, registration number | **Supplied by the project owner** (Phase 9, 25 Nov 2026) – shown in §0 *[was: PENDING – shown as pending above]* |
| P-M3-4 | Member 3's report date | **PENDING** – draft date given separately |
| P-M3-5 | Machine used locally | **PENDING** |
| P-M3-6 | Scenario 4 mapping | FR-02.8 / FR-06.6 used as an owner-authorised integration correction; Member 3 not consulted |
| P-M3-7 | CI on `93d11a3` incl. `ui` job | Not available. The "configured, not executed" and "`70d01e0`" wording is used throughout |
| P-M3-8 | Marker access to the CI URL | Repository is private. The screenshots are the visible evidence |
| P-M3-9 | Superseded test described as historical | Done (§5.1) |
| P-M3-10 | TDD/BDD/CI points for 3.10 | Taken from this evidence into the group 3.10 text |
| P-M3-11 | Title | The title of this draft; not confirmed by Member 3 |
