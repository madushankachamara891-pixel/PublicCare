# Section 3.2 – Execution reconciliation (where each test was actually run)

| Item | Value |
|---|---|
| Date | 2026-11-25 (Phase 7, Step 4) |
| Purpose | Shows **where** each test was executed and **which Part 01 test cases** it executed, so that results from separate repositories are not read as one run. It completes Part 01 Section 3.2 (test cases with actual result and status) and replaces the rejected Member 3 Part 01 diff (REJ-4) |
| Sources | Phase 4 Appendix A (test-run register, recomputed from the logs); [CROSS_REFERENCE.md](../CROSS_REFERENCE.md) §2–§4; Member 3's own case statuses in `part01-execution-status-edits_2c1e597..93d11a3.diff` (read only, **not applied**); [Evidence index](../evidence/EVIDENCE_INDEX.md); register ENV-J6 |
| Rule | **Counts from different tables must not be added together.** Each table is a different repository or copy, with different application code (Phase 4 Appendix B). No combined run of all members' tests exists (register UNA-1, REJ-5) |

**Execution-origin labels** (same as the evidence index): **GROUP** = this repository (`publiccare/`, `qa/final-assignment`, `f565fb4` + uncommitted D-1 change). **MEMBER** = a member's separate private repository. **MEMBER-CI** = GitHub Actions in a member's repository. **HISTORICAL** = genuine but superseded. **NOT EXECUTED** / **NOT VERIFIED**.

## A. Executed in the main group repository (GROUP)

| Run | Date | Environment | Code | Tests | Failures | Part 01 cases executed | Evidence |
|---|---|---|---|---:|---:|---|---|
| Phase 3A baseline | 2026-11-25 | Windows 11, Oracle Java 27, H2 | `f565fb4` unchanged | 72 | 0 | – (existing tests are not Part 01 cases) | [00-baseline-before-change.txt](../phase3a-api04-correction/00-baseline-before-change.txt) |
| Phase 3A red (new tests only) | 2026-11-25 | same | new tests, code unchanged | 13 | 9 (expected Red) | API-04 (before fix) | [01-red-before-fix.txt](../phase3a-api04-correction/01-red-before-fix.txt) |
| Phase 3A green (new tests only) | 2026-11-25 | same | + D-1 change | 13 | 0 | API-04 | [02-green-after-fix.txt](../phase3a-api04-correction/02-green-after-fix.txt) |
| Phase 3A full suite | 2026-11-25 | same | + D-1 change | 85 | 0 | **API-04 PASS** | [03-full-suite-after-fix.txt](../phase3a-api04-correction/03-full-suite-after-fix.txt) |
| Java 21 full suite | 2026-11-25 | WSL2 Ubuntu 26.04.1, Ubuntu OpenJDK 21.0.13.1, H2 | + D-1 change | 85 | 0 | API-04 (re-run) | [java21-run/surefire-reports/](../evidence/java21-run/surefire-reports/) (console log not archived, ENV-J10) |
| INT-01 on PostgreSQL *[Phase 8A; Phase 7 row was empty]* | 2026-11-25 | Windows 11, JetBrains Runtime 21.0.8, **PostgreSQL 18.6** (dedicated local database) | + D-1 change (jar `dd1b8b18…`) | 4 scenarios (not JUnit tests) | 0 (1 BLOCKED) | **INT-01 PARTIALLY EXECUTED**: (a), (b), (d) PASS; (c) BLOCKED (RTM Q2) | [int01-postgresql/](int01-postgresql/README.md) (run folder `run-20261125-125857/`) |

**Part 01 cases executed in the group repository: 1 of 79 (API-04, PASS).** *[Phase 8A, 25 Nov 2026: 2 of 79 – API-04 PASS and INT-01 PARTIALLY EXECUTED ((a), (b), (d) PASS; (c) BLOCKED).]*

## B. Executed in Member 2's repository (MEMBER, `PublicCare-QA-Member2-Oshan-` @ `0ad36bd`)

Production code is identical to `f565fb4`; only two test classes were extended.

| Run | Environment | Tests | Failures | Technique / IDs | Evidence |
|---|---|---:|---:|---|---|
| Baseline | Windows 11, Microsoft OpenJDK 21.0.13.1, H2 | 72 | 0 | – | E01, E10 |
| + BVA | same | 79 | 0 | BVA-01 – BVA-07 (report title length 0, 1, 2, 80, 159, 160, 161) | E06 (screenshot only) |
| + EP | same | 84 | 0 | EP-01 – EP-05 (registration name, e-mail, password) | E07 (screenshot only) |
| Final + JaCoCo 0.8.12 | same | 84 | 0 | DT-01 – DT-04; white-box paths A1 – A3, B2 | E08, E09 |

**Part 01 cases executed: none by ID.** Member 2 used their own technique IDs. These relate to, but do not execute, API-03, API-11, API-05/API-09/API-20, UT-01 and UT-03 (CROSS_REFERENCE §2, "Related cases"). **Not executed:** DT-05, DT-06, path B1.

## C. Executed locally in Member 3's repository (MEMBER, `PublicCare-QA-Member3-Ishani-` @ `93d11a3`)

Production code is `f565fb4` + the unapplied `AdminSetupRunner` change + `pom.xml` test dependencies. All runs pre-date D-1.

| Run | Environment | Tests | Failures | Evidence |
|---|---|---:|---:|---|
| Baseline | Windows 11, Temurin 25.0.5, H2 | 72 | 0 | `baseline/` |
| After TDD | same | 78 | 0 | `tdd/04` |
| After BDD | same | 82 | 0 | `bdd/03` |
| After REST Assured / final `./mvnw test` | same | 91 | 0 | `api-automation/03`, `ui-automation/05`, `final/` |
| Selenium `PublicCareUiIT` (separate; **not** in `./mvnw test`) | same + Chrome 157 headless | 6 | 0 (fault check 6/2, intended) | `ui-automation/02`–`04` |

| Part 01 case | Requirement(s) | Test (Member 3) | Member 3's status | Coverage of the case |
|---|---|---|---|---|
| UT-14 | FR-06.9 | `AdminSetupRunnerLoopbackTest` (6) | EXECUTED (needs the unapplied code change; expected result open, RTM Q1) | Full, in the Member 3 copy only |
| API-01 | FR-02.1 | `csrfEndpointReturnsTokenAndCookie` | PASS | Full |
| API-02 | FR-01.1, FR-01.5 | `registrationForcesCitizenRoleAndHidesPassword` | PARTIAL (steps 1–3; step 4 not executed) | Partial |
| API-03 | FR-01.2 | `passwordLengthBoundary` | PARTIAL (password 11 / 12 only) | Partial |
| API-04 | FR-01.3, FR-01.4 | `duplicateEmailWithDifferentCaseIsRejected` (409, PASS); `emailWithSurroundingSpacesIsRejectedByValidation` (400) | PARTIAL – the padding part is **HISTORICAL** (superseded by D-1). The group result in §A is authoritative | Partial |
| API-08 | FR-02.5, FR-02.6 | `meRequiresSession` | PARTIAL (steps 1–2; logout steps 3–5 not executed) | Partial |
| API-09 | FR-02.8, FR-06.4, NFR-SEC | `adminEndpointAuthorisationMatrix` | PARTIAL (1 of 12 endpoints) | Partial |
| API-10 | FR-03.1, FR-03.3 | `createReportUsesSessionOwner` | PARTIAL (steps 1–2; step 3 not executed) | Partial |
| API-15 | FR-04.2, FR-04.4, FR-04.5 | `otherUsersReportLooksLikeMissingReport` | PARTIAL (GET part only) | Partial |
| UI-01 | FR-01.1, FR-01.2, FR-01.6 | `registrationBoundariesAndMismatch` | PASS | Full (local Selenium) |
| UI-02 | FR-02.7 | `loginRedirectsByRole` | PASS | Full (local Selenium) |
| UI-03 | FR-02.3 | `failedLoginShowsGenericAlert` | PASS | Full (local Selenium) |
| UI-04 | FR-03.8 | `signedOutUserCannotSubmitReport` | PASS | Full (local Selenium) |
| UI-05 | FR-03.1, FR-03.2 | `requiredFieldsAndSuccessfulReport` | PASS | Full (local Selenium) |
| UI-13 | FR-02.9 | `adminPagesAreGatedForCitizen` | PASS | Full (local Selenium) |

Also executed, but not tied to a Part 01 case ID: the 4 BDD scenarios (FR-01.1, FR-01.5, FR-02.2, FR-02.8, FR-03.1, FR-03.3, FR-05.2, FR-06.6).

**Part 01 cases executed in the Member 3 copy: 15 (8 in full, 7 in part).**

## D. Executed in Member 3's CI (MEMBER-CI, GitHub Actions run #1)

| Run | Commit | Environment | Tests | Failures | Part 01 cases | Evidence |
|---|---|---|---:|---:|---|---|
| Run #1, `backend` job | **`70d01e0`** (not `93d11a3`) | ubuntu-latest, Temurin 21.0.13, H2 | 91 | 0 | UT-14, API-01, API-02 (p), API-03 (p), API-04 (p), API-08 (p), API-09 (p), API-10 (p), API-15 (p) – **9 cases**, plus the 4 BDD scenarios | `ci/` screenshots, `artifact-summary.txt` |
| Run #1, `frontend` job | `70d01e0` | ubuntu-latest, Node 22 | lint + build | – | – | Job status only |
| `ui` job (Selenium) | – | – | – | – | **UI-01 – UI-05, UI-13 NOT EXECUTED in CI** | Configured only |

The executable code of these 91 tests is identical at `93d11a3` (Phase 6 F6-1). That is a code comparison, not a CI run of `93d11a3`.

## E. Executed in Member 4's repository (MEMBER, `PublicCare-QA-Member4-Chathura-` @ `1c8e07b`)

| Run | Environment | Result | Part 01 case touched | Coverage of the case | Evidence |
|---|---|---|---|---|---|
| Backend suite | Linux 6.18.57, Java 21.0.13.1 (Azul), H2 | 72 / 0 | – | – | `junit-summary.txt` |
| JaCoCo 0.8.13 | same | lines 434 / 642, branches 126 / 302 | – | – | `jacoco.csv` |
| Anonymous `GET /api/admin/reports` | same | 401 | API-09 (anonymous row, 1 endpoint) | Partial | `access-checks.txt` |
| Anonymous `GET /api/auth/me` | same | 401 | API-08 (step 1 only) | Partial | `access-checks.txt` |
| `GET /api/health` (control) + JMeter 100 requests | same, JMeter 5.6.3 | 200 UP; 100 / 100 success | API-21 (functional part; *integrator note – Member 4 does not cite it*) | Partial | `access-checks.txt`, `health.jtl` |
| Registration recovery (PC-QA-01) | Chromium 153 (Playwright) | FAIL before fix; PASS after `f78deb1` (Member 4 copy) | Related to MT-02 / UI-01; **not an execution of either** | – | `registration-*.txt` |
| Signed-out create page, 390 × 844 | same | PASS (synthetic `401` session fixture) | Related to UI-04; not an end-to-end execution | – | `mobile.txt` |
| Keyboard login, Show/Hide password | same | PASS | Small part of MT-16; **not** an execution of MT-16 | – | `mobile.txt` |
| SonarQube 25.3 | same | Gate PASSED; 3 bugs, 88 smells | – | – | `sonar-*.json`, `sonar-run.txt` |

**Part 01 cases touched in the Member 4 copy: 3, all partly (API-08, API-09, API-21).**

## F. Not executed / not verified

| Item | Status |
|---|---|
| Part 01 cases executed by **nobody**, in any copy: MT-01 – MT-22 (22), UI-06 – UI-12 and UI-14 (8), API-05 – API-07 (3), API-11 – API-14 (4), API-16 – API-25 except API-21 (9), UT-01 – UT-13 and UT-15 – UT-17 (16), INT-01 (1) | **63 of 79 NOT EXECUTED** (INT-01: see the Step 5 record in [FINAL_COMPLETION_TRACKER.md](../FINAL_COMPLETION_TRACKER.md)) *[Phase 8A, 25 Nov 2026: INT-01 has since been partly executed in the group repository, so **62 of 79** are executed by nobody]* |
| A combined run of all members' tests on one code base | **NOT EXECUTED** (D-3, D-4, D-5 pending) |
| Any member test on PostgreSQL | **NOT EXECUTED** – all member and group runs used H2 *[Phase 8A, 25 Nov 2026: still true for every member test and the JUnit suite; the group INT-01 run used PostgreSQL 18.6 (§A)]* |
| Member 3 CI on `93d11a3`; the CI `ui` job | **NOT EXECUTED** |
| Member 2 DT-05, DT-06, white-box path B1 | **NOT EXECUTED** |
| Member 2 E02 – E05 captured *before* the added tests ("baseline") | **NOT VERIFIED** (Phase 6 F6-2) |
| PC-QA-01 lifecycle states in GitHub issue #1 | **NOT VERIFIED** (private repository) |
| Member 4 participant usability study; database-backed load test | **NOT EXECUTED** |

## G. Case-level summary (counts of Part 01 cases, not of tests)

| Where executed | Part 01 cases (of 79) | Fully | Partly |
|---|---:|---:|---:|
| Group repository | 1 (API-04) *[Phase 8A: 2 – + INT-01]* | 1 | 0 *[Phase 8A: 1 – INT-01, 3 of 4 scenarios]* |
| Member 2 repository | 0 (own technique IDs only) | – | – |
| Member 3 repository, local | 15 | 8 | 7 |
| Member 3 CI (run #1, `70d01e0`) | 9 (subset of the 15) | 2 (UT-14, API-01) | 7 |
| Member 4 repository | 3 | 0 | 3 |
| **Distinct cases with at least one execution anywhere** | **16** *[Phase 8A: 17]* | – | – |
| **Executed by nobody** | **63** *[Phase 8A: 62]* | – | – |

The 16 distinct cases are API-04, UT-14, API-01, API-02, API-03, API-08, API-09, API-10, API-15, API-21, UI-01 – UI-05 and UI-13. 16 + 63 = 79 (checked). *[Phase 8A, 25 Nov 2026: INT-01 added to the distinct cases – 17 + 62 = 79 (checked).]* The distinct count is a count of **case IDs**. It does not mean those 16 cases passed together in one run, or on one code base.

## H. How to record this in Part 01

Part 01 keeps its planned values (TPC rule). In the group report, cite this file from Section 3.2 next to the As Executed Addendum. **Do not** change the Part 01 status of a case to PASS because of a member-copy execution. The correct form is "Executed in the Member 3 copy (PASS / PARTIAL), not in the group repository", as in the tables above.

## I. Phase 11 update (2026-11-25)

Integration executed 39 further Part 01 cases in the **group repository** (isolated copy of `backend/`, production code SHA-256-identical; Windows 11, Oracle Java 27, H2, real embedded Tomcat). Record: [../phase11-part01-exec/README.md](../phase11-part01-exec/README.md).

| Where executed | Part 01 cases (of 79) | Result |
|---|---:|---|
| Group repository | **41** (API-04, INT-01, API-01 – API-03, API-05 – API-25, UT-01 – UT-15) | 33 PASS, 6 PASS with one part BLOCKED, 1 BLOCKED (API-24), 1 PARTIALLY EXECUTED (INT-01) |
| Member 3 copy only | 6 (UI-01 – UI-05, UI-13) | PASS (local Selenium) |
| Executed by nobody | **32** (MT-01 – MT-22, UI-06 – UI-12, UI-14, UT-16, UT-17) | NOT EXECUTED; UT-16, UT-17 BLOCKED |

41 + 6 + 32 = 79 (checked). The Member 3 and Member 4 partial executions in §C–§E remain valid records for those copies; for the cases now executed in the group repository, the group result in Test Plan §10.4 is authoritative. §F and §G above describe the state **before** Phase 11.
