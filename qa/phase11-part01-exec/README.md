# Phase 11 – Part 01 test execution record (Section 3.2)

| Item | Value |
|---|---|
| Date | 25 Nov 2026 |
| Purpose | Execute the Part 01 test cases that need no new tool and no application change, and record their actual results with evidence. Before Phase 11 only 2 of the 79 cases (API-04, INT-01) had a group-repository result |
| Executed by | Integration, on the project owner's instruction ("complete the assignment as far as possible from evidence"). **Not** executed by a named group member; no member reviewed the results |
| Code under test | Group repository `publiccare/`, branch `qa/final-assignment`, `f565fb4` + the uncommitted owner-approved D-1 change (`AuthDtos.java`). Run in an isolated copy of `backend/` (without `.env` and `target/`) |
| Code identity | SHA-256 of all 53 files under `backend/src/main` compared before and after the runs: **identical** to the repository ([repository list](prod-src-sha256-repo.txt), [copy after the runs](prod-src-sha256-scratch-after-runs.txt)). No production file, dependency, `pom.xml` or configuration file was changed |
| Test code | 4 new test classes, kept **here as evidence**, not in the application source tree (Test Plan §7.11; OQ-12 – where QA test code lives – is still the owner's decision): [test-code/](test-code/) |
| Environment | **ENV-P11** – Windows 11 Pro 10.0.26200, Oracle Java 27 (27+35-2325), Maven 3.9.16 via the Maven Wrapper (offline), Spring Boot 3.5.16, JUnit 5, Mockito; H2 in-memory (`MODE=PostgreSQL`); real embedded Tomcat on a random port for the API cases ([00-environment.txt](00-environment.txt)) |
| Result | 39 Part 01 cases executed: **32 PASS** (one with a stated limitation), **6 PASS with one part BLOCKED**, **1 BLOCKED** (API-24). **No FAIL.** 1 new defect (**PC-QA-02**), 3 observations |

## 1. Runs

| Run | Command (in the isolated copy) | Tests | Failures / errors | Evidence |
|---|---|---:|---:|---|
| 1 | `./mvnw -o test -Dtest=Part01*` | 40 | 0 / **1** | [run1/surefire-reports/](run1/surefire-reports/), [run1/part01-observations.txt](run1/part01-observations.txt) |
| 2 | same, after correcting the UT-05 test (see below) | 40 | 0 / 0 | [run2/console.txt](run2/console.txt), [run2/surefire-reports/](run2/surefire-reports/), [run2/part01-observations.txt](run2/part01-observations.txt) |
| 3 | `./mvnw -o test` (full suite, regression) | **125** (85 existing + 40) | 0 / 0 | [run3/console.txt](run3/console.txt), [run3/surefire-reports/](run3/surefire-reports/) |

**Run 1 error (kept as evidence).** `ut05` expected an `IllegalArgumentException` from `ReportService.adminList(…, status "DONE", …)`. The service throws it inside a JPA `Specification`, and Spring's repository exception translation wraps it as `InvalidDataAccessApiUsageException` (same message). The product behaviour was not wrong – the API still maps it to `400 "Unsupported report status."` because `@ExceptionHandler` matches on the cause (API-14 passed in the same run). The **test** was corrected to accept the wrapper and assert the cause, and the wrapping was recorded as observation OBS-2. No expected result was changed. Run 2 is the run used for the actual results.

The 40 tests are 39 Part 01 cases plus one control test (`zzControl`). Counting rule (Test Plan §AE-A): a JUnit test is not a Part 01 case, so 40 and 125 are not case counts.

## 2. Summary of results

| Case | Status | Test method | Observation lines |
|---|---|---|---:|
| [API-01](#api-01) | PASS | `Part01ApiExecutionTest.api01` | 1 |
| [API-02](#api-02) | PASS | `Part01ApiExecutionTest.api02` | 2 |
| [API-03](#api-03) | PASS | `Part01ApiExecutionTest.api03` | 15 |
| [API-05](#api-05) | PASS | `Part01ApiExecutionTest.api05` | 19 |
| [API-06](#api-06) | PASS | `Part01ApiExecutionTest.api06` | 3 |
| [API-07](#api-07) | PASS | `Part01ApiExecutionTest.api07` | 2 |
| [API-08](#api-08) | PASS | `Part01ApiExecutionTest.api08` | 3 |
| [API-09](#api-09) | PASS | `Part01ApiExecutionTest.api09` | 25 |
| [API-10](#api-10) | PASS | `Part01ApiExecutionTest.api10` | 1 |
| [API-11](#api-11) | PASS | `Part01ApiExecutionTest.api11` | 14 |
| [API-12](#api-12) | PASS | `Part01ApiExecutionTest.api12` | 12 |
| [API-13](#api-13) | PASS | `Part01ApiExecutionTest.api13` | 4 |
| [API-14](#api-14) | PASS | `Part01ApiExecutionTest.api14` | 21 |
| [API-15](#api-15) | PASS | `Part01ApiExecutionTest.api15` | 3 |
| [API-16](#api-16) | PASS (part BLOCKED) | `Part01ApiExecutionTest.api16` | 4 |
| [API-17](#api-17) | PASS | `Part01ApiExecutionTest.api17` | 8 |
| [API-18](#api-18) | PASS (part BLOCKED) | `Part01ApiExecutionTest.api18` | 5 |
| [API-19](#api-19) | PASS | `Part01ApiExecutionTest.api19` | 8 |
| [API-20](#api-20) | PASS | `Part01ApiExecutionTest.api20` | 1 |
| [API-21](#api-21) | PASS (limitation) | `Part01ApiExecutionTest.api21` | 1 |
| [API-22](#api-22) | PASS | `Part01ApiExecutionTest.api22` | 2 |
| [API-23](#api-23) | PASS | `Part01Api23ServerErrorTest.api23` | 1 |
| [API-24](#api-24) | BLOCKED | `Part01ApiExecutionTest.api24` | 5 |
| [API-25](#api-25) | PASS | `Part01ApiExecutionTest.api25` | 6 |
| [UT-01](#ut-01) | PASS | `Part01UnitExecutionTest.ut01` | 1 |
| [UT-02](#ut-02) | PASS | `Part01UnitExecutionTest.ut02` | 1 |
| [UT-03](#ut-03) | PASS | `Part01UnitExecutionTest.ut03` | 1 |
| [UT-04](#ut-04) | PASS (part BLOCKED) | `Part01ApiExecutionTest.ut04` | 16 |
| [UT-05](#ut-05) | PASS | `Part01ApiExecutionTest.ut05` | 6 |
| [UT-06](#ut-06) | PASS (part BLOCKED) | `Part01UnitExecutionTest.ut06` | 1 |
| [UT-07](#ut-07) | PASS | `Part01ApiExecutionTest.ut07` | 1 |
| [UT-08](#ut-08) | PASS (part BLOCKED) | `Part01UnitExecutionTest.ut08` | 10 |
| [UT-09](#ut-09) | PASS | `Part01UnitExecutionTest.ut09` | 6 |
| [UT-10](#ut-10) | PASS | `Part01UnitExecutionTest.ut10` | 4 |
| [UT-11](#ut-11) | PASS | `Part01ApiExecutionTest.ut11` | 1 |
| [UT-12](#ut-12) | PASS | `Part01UnitExecutionTest.ut12` | 4 |
| [UT-13](#ut-13) | PASS | `Part01UnitExecutionTest.ut13` | 3 |
| [UT-14](#ut-14) | PASS (part BLOCKED) | `Part01UnitExecutionTest.ut14` | 5 |
| [UT-15](#ut-15) | PASS | `Part01UnitExecutionTest.ut15` | 6 |

Totals: 39 cases = 32 PASS (incl. API-21 with a limitation) + 6 PASS with one part BLOCKED (API-16, API-18, UT-04, UT-06, UT-08, UT-14) + 1 BLOCKED (API-24).

## 3. Findings

### PC-QA-02 – A concurrent second image upload returns `500` instead of `409` (new defect)

| Field | Value |
|---|---|
| Found by | API-24 (NFR-REL), Phase 11 run 2, 25 Nov 2026 |
| Steps | 1. Citizen A creates a report without an image. 2. Two `POST /api/reports/{id}/image` requests with valid JPGs are released at the same moment (two threads, one latch). 3. Repeat on 5 new reports |
| Expected | One `204`; the other `409 {"error":"This report already has an image."}` (TASK 6). The expected result was marked BLOCKED pending RTM Q1, because the code analysis suspected `500` |
| Actual | Round 2: `204` + `409`. Rounds 1, 3, 4, 5: `204` + **`500`** with the generic Spring error body. H2 logged a unique-index violation on `report_images.report_id`. Exactly one image row was stored in every round (no data corruption) |
| Root cause (static reading, not a fix) | Check-then-act race in `ReportImageService.upload`: `images.existsByReportId()` is checked before `saveAndFlush()`. Both requests pass the check before either commits; the database constraint `uk_report_images_report` (V3) rejects the second insert with `DataIntegrityViolationException`, which `ApiExceptionHandler` does not map. `AuthService.register` already handles the same race by catching `DataIntegrityViolationException` (FR-01.4), so the pattern exists in the code base |
| Severity / priority (proposed) | Medium / Low: data stays consistent and the stored file of the losing request is removed by the existing `catch` block, but the client receives an unexplained server error |
| Environment note | H2. The same unique constraint exists in the PostgreSQL migration V3; the run was **not** repeated on PostgreSQL |
| Status | **New** – reported here only. Not logged in GitHub Issues (creating an issue is an outward action for the owner). **Not fixed** (QA does not change the application, §7.11). Possible fix for the owner: catch `DataIntegrityViolationException` around `saveAndFlush` in `upload` and throw `ImageAlreadyExistsException` (→ `409`) |

### Observations (not defects)

| ID | Case | Observation | Effect |
|---|---|---|---|
| OBS-1 | UT-14 | `isLoopbackPostgres("jdbc:postgresql://[::1]:5432/db")` returns **false**. `URI.getHost()` keeps the brackets, so the comparison with `"::1"` never matches | Confirms the RTM §2.6 [I] suspicion. Whether IPv6 loopback should be accepted is RTM Q1 (owner decision D-4). Recorded, not judged |
| OBS-2 | UT-05 | `ReportService` throws `IllegalArgumentException` for an unsupported status inside a JPA Specification; callers of the service receive `InvalidDataAccessApiUsageException` | No user-visible effect (API returns `400`). Relevant to anyone writing service-level tests |
| OBS-3 | API-21 | `/actuator/health` returns only `{"status":"UP"}`; no `components.db` entry, because health details are not exposed | The database indicator is enabled and contributes to the status, but the response does not show it. The expected result "includes the database health" is not observable as written |

Other observed values recorded for blocked parts (not judged): API-16 step 3 (same-status PATCH accepted, approval kept); API-18 step 6 (`{}` revokes the approval); UT-04 (direction is case-insensitive); UT-06 step 1 (approval kept); UT-08 (minimal VP8 and VP8X + VP8L structures accepted).

## 4. Deviations from the written test steps (all disclosed in the case entries)

| Case(s) | Deviation | Why |
|---|---|---|
| All API cases | An HTTP client in a JUnit test (`java.net.http.HttpClient`, one cookie jar per user) instead of REST Assured or Postman | No new dependency. Requests go through the real embedded Tomcat and the production security filter chain; the CSRF token is taken from `GET /api/auth/csrf` as the SPA does |
| All cases | H2 (`MODE=PostgreSQL`, schema created by Hibernate) instead of PostgreSQL | No PostgreSQL credentials are available to integration. The PostgreSQL-generated `reports.reference` column is emulated with the V2 expression after each insert. INT-01 covers the PostgreSQL schema separately |
| Admin cases | The Admin account was inserted through `UserRepository` with a BCrypt hash, not created by the first-admin command | The command only runs against loopback PostgreSQL (FR-06.9); UT-15 tests the command's service |
| API-06 | No session exists before login (`GET /api/auth/csrf` does not create one). Session rotation was checked by logging in again while holding the first session | The case says the method was to be agreed; this is the method used |
| API-19 | Volume data (26 approved reports; 12 updates) inserted through the repositories | Faster and deterministic; TD-09 allows either way (assumption A-1) |
| API-23 | "Upload directory unwritable" was produced by occupying the directory path with a regular file | Windows file permissions cannot be removed reliably from a test; the effect (directory cannot be created or written) is the same. Environment change only |
| UT-04, UT-05, UT-07, UT-11 | Executed at service level against H2 instead of with a mocked repository | `findPage` and `criteria` are private, and the owner filter is a JPA Specification that a mock would not evaluate |
| UT-08, UT-12 | Called through the public methods (`upload`, `read`, `store`) | The methods under test are private; no source change |
| UT-14 | Called through reflection | Allowed by the case; avoids a source change |

## 5. Not covered by Phase 11

- Manual cases MT-01 – MT-22 (need a human tester at the running frontend) and UI cases UI-06 – UI-12, UI-14 (no UI automation in the group repository; Selenium is a Member 3 dependency only, D-5). UI-01 – UI-05 and UI-13 have Member 3 copy results only.
- UT-16 and UT-17 (Vitest not installed; needs the owner's approval).
- Any run on PostgreSQL, in CI, or in a browser. Code coverage of the new tests was not measured.
- Peer review of the expected results (V-3) – not done; members were not consulted (owner instruction, Phase 7).
- Nothing was committed or pushed. The new test classes are not part of the application.

## 6. How to reproduce

1. Copy `publiccare/backend/` to an empty directory, excluding `.env` and `target/`.
2. Copy the four files from [test-code/](test-code/) to `src/test/java/lk/publiccare/backend/part01/` in that copy.
3. Run `./mvnw test -Dtest=Part01*` (or `./mvnw test` for the full suite). Observations are written to `target/part01-observations.txt`.

## 7. Per-case results

#### API-01

| | |
|---|---|
| Status | **PASS** |
| Actual result | `200`; body `{token}` with a 96-character token; `Set-Cookie: XSRF-TOKEN=…; Path=/; SameSite=Lax`. |
| Test method | `Part01ApiExecutionTest.api01` ([source](test-code/Part01ApiExecutionTest.java)) |
| Observation lines | 1 line(s) starting with `| API-01 |` in [run2/part01-observations.txt](run2/part01-observations.txt) |

#### API-02

| | |
|---|---|
| Status | **PASS** |
| Actual result | Step 3: `201`; body fields exactly `id, fullName, email, role, createdAt`; `role` = `CITIZEN` although the request sent `"role":"ADMIN"`; no password or hash field. Step 4: login `200`; `/api/auth/me` `200`, `role` = `CITIZEN`. |
| Test method | `Part01ApiExecutionTest.api02` ([source](test-code/Part01ApiExecutionTest.java)) |
| Observation lines | 2 line(s) starting with `| API-02 |` in [run2/part01-observations.txt](run2/part01-observations.txt) |

#### API-03

| | |
|---|---|
| Status | **PASS** |
| Actual result | Name 120 → `201`, 121 → `400` "fullName size must be between 0 and 120". Password 12 and 72 → `201`; 11 and 73 → `400` "password size must be between 12 and 72"; 37 × "é" (74 bytes) → `400` "Password must be at most 72 UTF-8 bytes.". Email 254 → `201`; 255 → `400` "email size must be between 0 and 254". Blank and whitespace-only name, email and password → `400`, each with the first field error (for example "fullName must not be blank"). Assumption A-3 held: a 254-character address in valid format was accepted. |
| Test method | `Part01ApiExecutionTest.api03` ([source](test-code/Part01ApiExecutionTest.java)) |
| Observation lines | 15 line(s) starting with `| API-03 |` in [run2/part01-observations.txt](run2/part01-observations.txt) |

#### API-05

| | |
|---|---|
| Status | **PASS** |
| Actual result | Steps 1–4: all 9 requests without `X-XSRF-TOKEN` (register, login, logout; citizen create, PUT, image upload; admin progress update, status PATCH, DELETE) returned `403 {"error":"Access denied."}`. No data changed: report count 2 → 2, the report kept its title and status `PENDING`, the disposable report still existed, and no account was created. Step 5: every control request with the token succeeded (register `201`, login `200`, create `201`, PUT `200`, image `204`, progress update `201`, PATCH `200`, DELETE `204`, logout `204`). The RTM Q7 part (login, logout, PUT) is now confirmed by execution. |
| Test method | `Part01ApiExecutionTest.api05` ([source](test-code/Part01ApiExecutionTest.java)) |
| Observation lines | 19 line(s) starting with `| API-05 |` in [run2/part01-observations.txt](run2/part01-observations.txt) |

#### API-06

| | |
|---|---|
| Status | **PASS** |
| Actual result | Step 1: **no** `JSESSIONID` existed before login (`GET /api/auth/csrf` does not create a session). The method agreed for this run: the pre-login session is the session of an earlier login by the same client. Step 3: `200` with the user fields; `Set-Cookie: JSESSIONID=<id>; Path=/; HttpOnly; SameSite=Lax`; `Secure` absent (default `SESSION_COOKIE_SECURE=false`, OQ-6 – recorded, not judged). Step 4: a second login while holding that session returned a **different** session ID. Step 5: `/api/auth/me` `200`. |
| Test method | `Part01ApiExecutionTest.api06` ([source](test-code/Part01ApiExecutionTest.java)) |
| Observation lines | 3 line(s) starting with `| API-06 |` in [run2/part01-observations.txt](run2/part01-observations.txt) |

#### API-07

| | |
|---|---|
| Status | **PASS** |
| Actual result | Steps 1–3: wrong password and unregistered email both `401` with identical bodies `{"error":"Invalid email or password."}`. Step 4: 73-byte password → `400` (73 ASCII characters: "password size must be between 0 and 72"; 37 characters / 73 UTF-8 bytes: "Password must be at most 72 UTF-8 bytes."). Never `401`. |
| Test method | `Part01ApiExecutionTest.api07` ([source](test-code/Part01ApiExecutionTest.java)) |
| Observation lines | 2 line(s) starting with `| API-07 |` in [run2/part01-observations.txt](run2/part01-observations.txt) |

#### API-08

| | |
|---|---|
| Status | **PASS** |
| Actual result | Step 1: `401 {"error":"Authentication required."}`. Step 2: `200` with Citizen A's fields. Steps 3–4: logout `204`; `Set-Cookie` deletes `JSESSIONID` and `XSRF-TOKEN` (`Max-Age=0`). Step 5: `401`. Extra check: replaying the old `JSESSIONID` from a new client also returned `401`. |
| Test method | `Part01ApiExecutionTest.api08` ([source](test-code/Part01ApiExecutionTest.java)) |
| Observation lines | 3 line(s) starting with `| API-08 |` in [run2/part01-observations.txt](run2/part01-observations.txt) |

#### API-09

| | |
|---|---|
| Status | **PASS** |
| Actual result | All 12 admin endpoints (including `…/note-public-approval`, path taken from `AdminReportController`): anonymous `401` (12/12), Citizen A `403` (12/12), Admin `2xx` (12/12: eleven `200`/`201`, DELETE `204`, run last). After the anonymous and citizen requests the report was unchanged (status, `publicApproved = false`, 1 history row) and the disposable report still existed. |
| Test method | `Part01ApiExecutionTest.api09` ([source](test-code/Part01ApiExecutionTest.java)) |
| Observation lines | 25 line(s) starting with `| API-09 |` in [run2/part01-observations.txt](run2/part01-observations.txt) |

#### API-10

| | |
|---|---|
| Status | **PASS** |
| Actual result | Step 2: `201`; status `PENDING`; reference `PC-00000006` (matches `^PC-\d{8}$`); owner name and both timestamps returned. Step 3: admin view `ownerId` = 13 (Citizen A, the session user), **not** 14 (Citizen B, sent in the body). H2 note: H2 has no generated column, so the `201` response used the application's own fallback (`ReportService.response`, `PC-%08d`); on PostgreSQL the database generates it (V2, §11.1 ENV-P11). |
| Test method | `Part01ApiExecutionTest.api10` ([source](test-code/Part01ApiExecutionTest.java)) |
| Observation lines | 1 line(s) starting with `| API-10 |` in [run2/part01-observations.txt](run2/part01-observations.txt) |

#### API-11

| | |
|---|---|
| Status | **PASS** |
| Actual result | Title 160, description 5000, category 80, location 240 → `201`. Title 161, description 5001, category 81, location 241 → `400` (for example "title size must be between 0 and 160"). `"   "` in each field → `400` "… must not be blank". Padded values → `201`, stored trimmed (`"Padded title"`, `"Roads"`, …). Category not in the UI list → `201`. |
| Test method | `Part01ApiExecutionTest.api11` ([source](test-code/Part01ApiExecutionTest.java)) |
| Observation lines | 14 line(s) starting with `| API-11 |` in [run2/part01-observations.txt](run2/part01-observations.txt) |

#### API-12

| | |
|---|---|
| Status | **PASS** |
| Actual result | Step 1: JPG, PNG and WebP (synthetic minimal VP8L structure) `204` each. Step 2: `409`. Step 3: GIF, renamed text file, 0-byte file `400` each. Step 4: valid PNG padded to exactly 5,242,880 bytes `204`. Step 5: 5,242,881 bytes → **`413`** (the multipart limit rejects it first; `413` is one of the two codes the case allows). Step 6: 4000 × 2501 px → `400`. Step 7: Citizen B → `404`. Step 8: anonymous → `401`. Assumption A-2 held. |
| Test method | `Part01ApiExecutionTest.api12` ([source](test-code/Part01ApiExecutionTest.java)) |
| Observation lines | 12 line(s) starting with `| API-12 |` in [run2/part01-observations.txt](run2/part01-observations.txt) |

#### API-13

| | |
|---|---|
| Status | **PASS** |
| Actual result | Steps 1 and 3 (owner; admin endpoint): `200`, bytes identical to the upload, `Content-Type: image/png`, `Cache-Control: no-store`, `X-Content-Type-Options: nosniff`. Step 2 (Citizen B): `404 {"error":"Report not found."}`. Step 4 (report without image): `404`. |
| Test method | `Part01ApiExecutionTest.api13` ([source](test-code/Part01ApiExecutionTest.java)) |
| Observation lines | 4 line(s) starting with `| API-13 |` in [run2/part01-observations.txt](run2/part01-observations.txt) |

#### API-14

| | |
|---|---|
| Status | **PASS** |
| Actual result | On both `/api/reports/mine` and `/api/admin/reports`: `page=-1`, `size=0`, `size=101` → `400` "Page must be non-negative and size must be between 1 and 100."; `sort=foo` → `400` "Unsupported sort field."; `status=DONE` → `400` "Unsupported report status.". `size=1` → 1 item; `size=100` → `200`. `status=in progress` → IN_PROGRESS only; `ALL` → no filter. `q=PC-00000021` → exactly that report. Summary `total` = `totalElements` for every valid request. Step 7: `/mine` held only Citizen A's 3 reports, none of Citizen B's. Step 8: the admin list held reports of both owners. |
| Test method | `Part01ApiExecutionTest.api14` ([source](test-code/Part01ApiExecutionTest.java)) |
| Observation lines | 21 line(s) starting with `| API-14 |` in [run2/part01-observations.txt](run2/part01-observations.txt) |

#### API-15

| | |
|---|---|
| Status | **PASS** |
| Actual result | Steps 1–2: other user's report and missing ID gave identical `404 {"error":"Report not found."}` for GET and PUT. Step 3: Citizen B's report unchanged. Step 4: PUT on the own approved report (status `PENDING`, so the RTM Q1 / BR-09 condition did not apply) → `200` with the new values. Step 5: `publicApproved = false`, status still `PENDING`. |
| Test method | `Part01ApiExecutionTest.api15` ([source](test-code/Part01ApiExecutionTest.java)) |
| Observation lines | 3 line(s) starting with `| API-15 |` in [run2/part01-observations.txt](run2/part01-observations.txt) |

#### API-16

| | |
|---|---|
| Status | **PASS** for steps 1, 2 and 4–7. Step 3: **BLOCKED** as a pass criterion (RTM Q1, BR-13) – observed result recorded, not judged |
| Actual result | Step 1 (`status: null`) → `400` "A valid status is required."; step 2 (`DONE`) → `400`; no change after either (status `PENDING`, still approved). Step 3 (same status + 1000-character note) → `200`, `publicApproved` stayed `true` (observed). Step 4 (1001-character note) → `400` "adminUpdateNote size must be between 0 and 1000", status unchanged. Step 5: the owner saw the 1000-character note. Step 6 (`IN_PROGRESS`) → `200`, `publicApproved = false`. Step 7 (note `null`) → `200`, the owner's latest note is `null`. |
| Test method | `Part01ApiExecutionTest.api16` ([source](test-code/Part01ApiExecutionTest.java)) |
| Observation lines | 4 line(s) starting with `| API-16 |` in [run2/part01-observations.txt](run2/part01-observations.txt) |

#### API-17

| | |
|---|---|
| Status | **PASS** |
| Actual result | Steps 1–4: no status → `400` ("Required parameter 'status' is not present."); 1001-character note → `400`; WebP → `400` "Only valid JPG or PNG images are supported."; corrupt JPG → `400` "Uploaded file is not a valid JPG or PNG image.". Step 5: after each, status `PENDING` and 0 history rows. Steps 6–7: `201` each; status IN_PROGRESS then RESOLVED; latest note set from the update. Step 8: admin history oldest first (IN_PROGRESS, RESOLVED) with `publicApproved`, `notePublicApproved` and `hasImage = true` on the first. Step 9: owner history oldest first with both notes. |
| Test method | `Part01ApiExecutionTest.api17` ([source](test-code/Part01ApiExecutionTest.java)) |
| Observation lines | 8 line(s) starting with `| API-17 |` in [run2/part01-observations.txt](run2/part01-observations.txt) |

#### API-18

| | |
|---|---|
| Status | **PASS** for steps 1–5. Step 6: **BLOCKED** (RTM Q1, BR-22 [I]) – observed result recorded, not judged |
| Actual result | Step 1: `{"approved":true}` then `{"approved":false}`; `publicApproved` followed; approver ID (admin id) and time stored in the database. Steps 2–3: update and note flags followed each approve/revoke request. Step 4: blank note → `400` "A non-empty note is required for public approval.". Step 5: U3 under R1 → `404`. Step 6 (observed): `{}` → `200 {"approved":false}` and the approval was **revoked**, as the code analysis predicted. |
| Test method | `Part01ApiExecutionTest.api18` ([source](test-code/Part01ApiExecutionTest.java)) |
| Observation lines | 5 line(s) starting with `| API-18 |` in [run2/part01-observations.txt](run2/part01-observations.txt) |

#### API-19

| | |
|---|---|
| Status | **PASS** |
| Actual result | Step 1: `size=0` and `size=25` → `400`; `size=1` → 1 report, `size=24` → 24. Step 2: `page=-1` → `400`. Step 3: default page size 9, approved reports only, newest first. Step 4: unapproved report → `404`. Step 5: fields exactly `id, title, category, status, location, description, reportedAt, progressUpdates`; updates `{status, createdAt, note}`; no reference, owner, admin note or image field. Step 6: 10 of 12 approved updates, newest first, note shown only where approved. Step 7: `GET /api/public/reports/{id}/image` → `401`. |
| Test method | `Part01ApiExecutionTest.api19` ([source](test-code/Part01ApiExecutionTest.java)) |
| Observation lines | 8 line(s) starting with `| API-19 |` in [run2/part01-observations.txt](run2/part01-observations.txt) |

#### API-20

| | |
|---|---|
| Status | **PASS** |
| Actual result | Step 1 (citizen) `403`; step 2 (admin, no token) `403`; step 3 (`/abc`) `400`; step 4 `204`; step 5 `404`; step 6 `404`; step 7 second report still returned (`200`). The deleted report's progress rows were removed (0 left). |
| Test method | `Part01ApiExecutionTest.api20` ([source](test-code/Part01ApiExecutionTest.java)) |
| Observation lines | 1 line(s) starting with `| API-20 |` in [run2/part01-observations.txt](run2/part01-observations.txt) |

#### API-21

| | |
|---|---|
| Status | **PASS** – with a limitation on step 2 |
| Actual result | Step 1: `200 {"status":"UP"}`. Step 2: `200 {"status":"UP"}`. The body has **no** `components.db` entry, because health details are not exposed (Spring Boot default `show-details: never`). The database indicator is enabled in the configuration and contributes to the aggregate status, but this cannot be seen in the response; this part of the expected result is therefore not observable (observation OBS-3). |
| Test method | `Part01ApiExecutionTest.api21` ([source](test-code/Part01ApiExecutionTest.java)) |
| Observation lines | 1 line(s) starting with `| API-21 |` in [run2/part01-observations.txt](run2/part01-observations.txt) |

#### API-22

| | |
|---|---|
| Status | **PASS** |
| Actual result | Step 1 (`http://localhost:5173`): `200`; `Access-Control-Allow-Origin: http://localhost:5173`, `Access-Control-Allow-Credentials: true`, allowed headers `Content-Type, X-XSRF-TOKEN`, methods `GET,POST,PUT,PATCH,DELETE,OPTIONS`, max age 3600. Step 2 (`http://evil.test`): `403` "Invalid CORS request", no `Access-Control-Allow-Origin` header. The allowed origin was the default value (no override in the test environment). |
| Test method | `Part01ApiExecutionTest.api22` ([source](test-code/Part01ApiExecutionTest.java)) |
| Observation lines | 2 line(s) starting with `| API-22 |` in [run2/part01-observations.txt](run2/part01-observations.txt) |

#### API-23

| | |
|---|---|
| Status | **PASS** |
| Actual result | Fault injected by an environment change only: the upload-directory path was occupied by a regular file, so the directory could not be created (equivalent to an unwritable directory on Windows). Step 3: `500` with the standard body `{timestamp, status, error: "Internal Server Error", path}` – no stack trace, exception class or message. Step 4: `/api/health` `200 {"status":"UP"}`. |
| Test method | `Part01Api23ServerErrorTest.api23` ([source](test-code/Part01Api23ServerErrorTest.java)) |
| Observation lines | 1 line(s) starting with `| API-23 |` in [run2/part01-observations.txt](run2/part01-observations.txt) |

#### API-24

| | |
|---|---|
| Status | **BLOCKED** (RTM Q1, §2.3 [I]) – executed; result recorded, not judged. Raised as defect **PC-QA-02** |
| Actual result | 5 rounds of two simultaneous uploads to a new report: round 2 → `204` + `409`; rounds 1, 3, 4 and 5 → `204` + **`500`** (H2 log: unique-index violation on `report_images.report_id`, no handler). Exactly 1 image was stored in every round, so data stayed consistent. The suspicion in the code analysis is confirmed: the losing request usually gets `500`, not `409`. |
| Test method | `Part01ApiExecutionTest.api24` ([source](test-code/Part01ApiExecutionTest.java)) |
| Observation lines | 5 line(s) starting with `| API-24 |` in [run2/part01-observations.txt](run2/part01-observations.txt) |

#### API-25

| | |
|---|---|
| Status | **PASS** |
| Actual result | No filters: summary `total` = list `totalElements` = number of reports in the database (67), and pending + inProgress + resolved = total. With `category`, `status + category` and `q` filters, each summary matched the counts worked out from the test data (for example `[3, 1, 1, 1]` for a category holding reports of both owners) and equalled the list's `totalElements`. |
| Test method | `Part01ApiExecutionTest.api25` ([source](test-code/Part01ApiExecutionTest.java)) |
| Observation lines | 6 line(s) starting with `| API-25 |` in [run2/part01-observations.txt](run2/part01-observations.txt) |

#### UT-01

| | |
|---|---|
| Status | **PASS** |
| Actual result | Stored email `a@x.com`, name `Padded Name`; hash starts `$2a$12$` (BCrypt cost 12), matches the raw password and differs from it; role `CITIZEN`; `UserResponse` components `id, fullName, email, role, createdAt` (no password or hash). |
| Test method | `Part01UnitExecutionTest.ut01` ([source](test-code/Part01UnitExecutionTest.java)) |
| Observation lines | 1 line(s) starting with `| UT-01 |` in [run2/part01-observations.txt](run2/part01-observations.txt) |

#### UT-02

| | |
|---|---|
| Status | **PASS** |
| Actual result | With the pre-check false and `saveAndFlush` throwing `DataIntegrityViolationException`, `register` threw `DuplicateEmailException`. |
| Test method | `Part01UnitExecutionTest.ut02` ([source](test-code/Part01UnitExecutionTest.java)) |
| Observation lines | 1 line(s) starting with `| UT-02 |` in [run2/part01-observations.txt](run2/part01-observations.txt) |

#### UT-03

| | |
|---|---|
| Status | **PASS** |
| Actual result | 72 bytes (62 characters) accepted; 73 bytes (63 characters) rejected with `IllegalArgumentException` "Password must be at most 72 UTF-8 bytes.". |
| Test method | `Part01UnitExecutionTest.ut03` ([source](test-code/Part01UnitExecutionTest.java)) |
| Observation lines | 1 line(s) starting with `| UT-03 |` in [run2/part01-observations.txt](run2/part01-observations.txt) |

#### UT-04

| | |
|---|---|
| Status | **PASS** for page, size, sort field and `asc`/`desc`. Upper/mixed-case direction: **BLOCKED** (not stated in the source documents) – observed, not judged |
| Actual result | Executed at service level (`ReportService.adminList` → `findPage`) on H2 instead of with a mocked repository, because `findPage` is private. Page −1 rejected, 0 accepted; size 0 and 101 rejected, 1 and 100 accepted; `createdAt`, `updatedAt`, `title`, `status`, `category` accepted; `foo` rejected "Unsupported sort field."; `asc` → A, B, C; `desc` → C, B, A. Observed: `ASC` sorts ascending and `Desc` descending (comparison is case-insensitive). |
| Test method | `Part01ApiExecutionTest.ut04` ([source](test-code/Part01ApiExecutionTest.java)) |
| Observation lines | 16 line(s) starting with `| UT-04 |` in [run2/part01-observations.txt](run2/part01-observations.txt) |

#### UT-05

| | |
|---|---|
| Status | **PASS** |
| Actual result | Executed at service level on H2. `ALL` and blank: no status filter (PENDING and IN_PROGRESS returned). `in progress`: IN_PROGRESS only. `DONE`: exception "Unsupported report status." – thrown as `IllegalArgumentException` inside the JPA Specification and wrapped by Spring as `InvalidDataAccessApiUsageException` (the API still returns `400`, API-14). `q = "  street LAMP  "`: matched the title "Broken Street LAMP" (trimmed, case-insensitive). Run 1 recorded the wrapper as a test error; see the execution record. |
| Test method | `Part01ApiExecutionTest.ut05` ([source](test-code/Part01ApiExecutionTest.java)) |
| Observation lines | 6 line(s) starting with `| UT-05 |` in [run2/part01-observations.txt](run2/part01-observations.txt) |

#### UT-06

| | |
|---|---|
| Status | **PASS** for steps 2–4. Step 1: **BLOCKED** (RTM Q1, BR-13) – observed, not judged |
| Actual result | Step 1 (same status): approval kept (observed). Step 2 (changed status): approval revoked. Step 3 (null status): `IllegalArgumentException` "A valid status is required.". Step 4 (null note): note cleared. |
| Test method | `Part01UnitExecutionTest.ut06` ([source](test-code/Part01UnitExecutionTest.java)) |
| Observation lines | 1 line(s) starting with `| UT-06 |` in [run2/part01-observations.txt](run2/part01-observations.txt) |

#### UT-07

| | |
|---|---|
| Status | **PASS** |
| Actual result | Executed at service level on H2, so that the owner filter in the Specification is really evaluated. Owner edit saved and approval revoked. Non-owner edit threw `ReportNotFoundException`; the report kept its values and its (re-granted) approval. |
| Test method | `Part01ApiExecutionTest.ut07` ([source](test-code/Part01ApiExecutionTest.java)) |
| Observation lines | 1 line(s) starting with `| UT-07 |` in [run2/part01-observations.txt](run2/part01-observations.txt) |

#### UT-08

| | |
|---|---|
| Status | **PASS** for JPG, PNG, WebP (VP8L) detection, truncated WebP, RIFF mismatch, 0 bytes and the pixel limit. VP8 / VP8X rules: **BLOCKED** (to be agreed in peer review) – observed, not judged |
| Actual result | Called through the public `upload` method with mocked repositories (the methods are private). Valid JPG → `image/jpeg`; PNG → `image/png`; WebP VP8L → `image/webp`. Observed: minimal VP8 and VP8X + VP8L structures were also accepted as `image/webp`. Truncated WebP and wrong RIFF length → rejected "Only valid JPG, PNG, or WebP images are supported."; 0 bytes → rejected "Choose an image to upload.". 4000 × 2500 px (exactly 10,000,000 px, the `MAX_IMAGE_PIXELS` constant) accepted; 4000 × 2501 rejected "Image dimensions must not exceed 10 megapixels.". |
| Test method | `Part01UnitExecutionTest.ut08` ([source](test-code/Part01UnitExecutionTest.java)) |
| Observation lines | 10 line(s) starting with `| UT-08 |` in [run2/part01-observations.txt](run2/part01-observations.txt) |

#### UT-09

| | |
|---|---|
| Status | **PASS** |
| Actual result | Step 1: 1000-character note accepted. Step 2: 1001 rejected "Update note must be 1000 characters or fewer.". Step 3: WebP rejected "Only valid JPG or PNG images are supported.". Step 4: with the save failing, the stored file was deleted (compensating delete called with the stored key). Step 5: approval revoked by the status change. |
| Test method | `Part01UnitExecutionTest.ut09` ([source](test-code/Part01UnitExecutionTest.java)) |
| Observation lines | 6 line(s) starting with `| UT-09 |` in [run2/part01-observations.txt](run2/part01-observations.txt) |

#### UT-10

| | |
|---|---|
| Status | **PASS** |
| Actual result | `null`, `""` and `"   "`: `IllegalArgumentException` "A non-empty note is required for public approval."; flag stayed `false`, approver `null`. Valid note: flag `true`, approver 42, time set. |
| Test method | `Part01UnitExecutionTest.ut10` ([source](test-code/Part01UnitExecutionTest.java)) |
| Observation lines | 4 line(s) starting with `| UT-10 |` in [run2/part01-observations.txt](run2/part01-observations.txt) |

#### UT-11

| | |
|---|---|
| Status | **PASS** |
| Actual result | Executed at service level on H2. Step 1: the unapproved note of an approved update was returned as `null`. Step 2: size 24 accepted, 25 rejected. Step 3: unapproved report → `ReportNotFoundException`. |
| Test method | `Part01ApiExecutionTest.ut11` ([source](test-code/Part01ApiExecutionTest.java)) |
| Observation lines | 1 line(s) starting with `| UT-11 |` in [run2/part01-observations.txt](run2/part01-observations.txt) |

#### UT-12

| | |
|---|---|
| Status | **PASS** |
| Actual result | Through the public `read`/`store` methods (`resolve` is private): `../x.jpg`, `abc.jpg` and `<UUID>.gif` → `IllegalArgumentException` "Invalid image storage key.". A stored `<UUID>.jpg` key resolved to a file inside the temporary storage directory and was read back. The checks agree with `PrivateImageStorage.java:47-54`. |
| Test method | `Part01UnitExecutionTest.ut12` ([source](test-code/Part01UnitExecutionTest.java)) |
| Observation lines | 4 line(s) starting with `| UT-12 |` in [run2/part01-observations.txt](run2/part01-observations.txt) |

#### UT-13

| | |
|---|---|
| Status | **PASS** |
| Actual result | Step 1: no file deleted before the commit; both files deleted after `afterCommit`. Step 2: `ReportNotFoundException`. Step 3: database failure → no file touched; through `AdminReportController` + `ApiExceptionHandler` the response is `500 {"error":"Unable to delete report."}`. Step 4: file-delete failure after commit did not fail the request and was logged ("Unable to remove private image after deleting report 21…"). |
| Test method | `Part01UnitExecutionTest.ut13` ([source](test-code/Part01UnitExecutionTest.java)) |
| Observation lines | 3 line(s) starting with `| UT-13 |` in [run2/part01-observations.txt](run2/part01-observations.txt) |

#### UT-14

| | |
|---|---|
| Status | **PASS** for `localhost`, `127.0.0.1`, remote host and `jdbc:mysql:`. `[::1]`: **BLOCKED** (RTM Q1, §2.6 [I]) – observed, not judged |
| Actual result | Called through reflection (no source change). `localhost` → true; `127.0.0.1` → true; remote host → false; `jdbc:mysql:` → false. Observed: `jdbc:postgresql://[::1]:5432/db` → **false**, confirming the suspicion that `URI.getHost()` returns `[::1]` with brackets (observation OBS-1). |
| Test method | `Part01UnitExecutionTest.ut14` ([source](test-code/Part01UnitExecutionTest.java)) |
| Observation lines | 5 line(s) starting with `| UT-14 |` in [run2/part01-observations.txt](run2/part01-observations.txt) |

#### UT-15

| | |
|---|---|
| Status | **PASS** |
| Actual result | Step 1: missing name, email or password → `IllegalArgumentException` ("Admin setup input is invalid: …"), nothing saved. Step 2: first run `CREATED` (one ADMIN, email normalised), second run `ALREADY_CONFIGURED`, no second save. Step 3: citizen email → `IllegalStateException`, not promoted. Step 4: second admin → `IllegalStateException`, nothing saved. |
| Test method | `Part01UnitExecutionTest.ut15` ([source](test-code/Part01UnitExecutionTest.java)) |
| Observation lines | 6 line(s) starting with `| UT-15 |` in [run2/part01-observations.txt](run2/part01-observations.txt) |

## 8. File hashes (SHA-256, first 16 hex digits)

| File | SHA-256 |
|---|---|
| `test-code/Part01Support.java` | `6f9adf63062e79b5…` |
| `test-code/Part01ApiExecutionTest.java` | `5af6fcd0cb71745b…` |
| `test-code/Part01UnitExecutionTest.java` | `b8deebf9b591fec3…` |
| `test-code/Part01Api23ServerErrorTest.java` | `9a2af75c43935e01…` |
| `run1/part01-observations.txt` | `72dcaca95fe0c459…` |
| `run2/part01-observations.txt` | `aa49173376e67d03…` |
| `run2/console.txt` | `940d5bbb7e4a9a52…` |
| `run3/console.txt` | `4b9411b51252b446…` |
| `00-environment.txt` | `37a6498400194d5f…` |
| `prod-src-sha256-repo.txt` | `47c85510f2a38996…` |
| `prod-src-sha256-scratch-after-runs.txt` | `47c85510f2a38996…` |
