# PublicCare – Sections 3.1 and 3.2: Test Planning, STLC and Test Cases (Final Report)

| Item | Detail |
|---|---|
| Module | EC4206 – Software Testing and Quality Assurance, Group Assignment |
| Sections | 3.1 Introduction and Test Planning (LO-1); 3.2 STLC and Test Cases (LO-1, LO-2) |
| Section owner | Member 1 – Madusanka D.M.C, EG/2023/5720 |
| Software Under Test | PublicCare – citizen issue reporting and tracking platform (assignment Step 0, Option A) |
| Code baseline | `publiccare/` branch `qa/final-assignment`, commit `f565fb4` + the uncommitted, owner-approved change D-1 (`AuthDtos.java`) |
| Status | Final, 25 Nov 2026 |
| How this report relates to the other Part 01 documents | This is the **summary for the reader**. The detailed sources are: the [Test Plan and STLC](PART01_TEST_PLAN_AND_STLC.md) (full plan, all 79 test cases in the full format, environment and test data), the [Requirements and RTM](PART01_REQUIREMENTS_AND_RTM.md) (58 functional requirements, business rules, RTM) and the [Phase 11 execution record](../phase11-part01-exec/README.md) (evidence of execution) |
| Authorship note | Member 1 wrote the requirement analysis, the RTM, the test plan and the 79 test cases (23 Nov 2026). Execution results, the environment summary and this summary were added during integration (25 Nov 2026) on the project owner's instruction. They are not presented as Member 1's personal work, and no member reviewed them |

---

## 3.1 Introduction and Test Planning

### 3.1.1 Introduction and Software Under Test

PublicCare is a web platform where citizens report public issues (road damage, waste, streetlights, drainage, water supply and other public issues) and follow how they are handled. A citizen submits a report with a title, category, description, location and an optional photo, and receives a reference number (`PC-` followed by 8 digits). An administrator reviews reports, changes their status (PENDING → IN_PROGRESS → RESOLVED), adds progress updates with notes and photos, approves content for a public feed, and deletes reports. Anonymous visitors see only approved reports, with personal data removed.

| Layer | Technology (confirmed in the code) |
|---|---|
| Frontend | React 19 + Vite 8 single-page application; ESLint; no unit-test runner |
| Backend | Spring Boot 3.5.16 on Java 21: Spring Security (session cookie, CSRF token, roles), Spring Data JPA, Bean Validation, Actuator |
| Database | PostgreSQL; schema managed only by Flyway migrations V1–V5 (`ddl-auto: none`) |
| File storage | Private directory outside the web root, random UUID file names |
| Existing tests | 72 backend JUnit tests in 12 classes (JUnit 5, Mockito, Spring test slices, H2) |

User roles: **Citizen** (self-registration), **Administrator** (created only by a command-line setup tool against a loopback PostgreSQL database), **Anonymous visitor**. The full role and permission matrix is in [RTM TASK 3](PART01_REQUIREMENTS_AND_RTM.md#task-3-user-roles-and-permissions).

### 3.1.2 Project objectives and testing objectives

**Project objective (of the SUT):** give citizens a reliable channel to report public issues and see their progress, while protecting their personal data and letting administrators control what becomes public.

**Testing objectives:**

1. Check that the 58 functional requirements (FR-01.1 – FR-09.2) behave as defined for Anonymous, Citizen and Admin users.
2. Check that server-side access control protects every citizen and admin route, including cross-user access (IDOR) and CSRF.
3. Check that the public feed exposes only approved and minimised data.
4. Check the handling of invalid input, boundary values and failures (bad images, storage failure, concurrent requests) without data loss or information leakage.
5. Collect evidence for the selected ISO/IEC 25010 characteristics (§3.1.5).
6. Keep two-way traceability between requirements and test cases, and record actual results and defects.
7. Confirm or reject the behaviours that the analysis could only infer (marked **[I]**), and report them for a decision.

### 3.1.3 Scope

| In scope (modules / features) | Requirements | Notes |
|---|---|---|
| User registration | FR-01.1 – FR-01.7 | Validation limits, email normalisation, duplicates, role forced to CITIZEN, BCrypt |
| Authentication, session, access control | FR-02.1 – FR-02.9 | CSRF, login/logout, generic error, `/me`, role-based landing, admin gate |
| Issue reporting | FR-03.1 – FR-03.8 | Create report, field limits, owner from session, one validated image |
| Citizen issue management | FR-04.1 – FR-04.7 | List, filter, paging, detail, own photo, edit (revokes public approval) |
| Issue tracking | FR-05.1 – FR-05.4 | Status timeline, latest admin note, history, reference number |
| Admin management and first-admin setup | FR-06.1 – FR-06.9 | Dashboard, search, CSV export, progress updates, status change |
| Public report visibility | FR-07.1 – FR-07.7 | Three approval levels, public feed and detail, data minimisation |
| Report deletion | FR-08.1 – FR-08.5 | Hard delete, after-commit file clean-up, error handling |
| Platform | FR-09.1 – FR-09.2 | Health endpoints; Flyway-only schema |
| Quality characteristics | NFR-SEC, NFR-REL, NFR-USA, NFR-COMP, NFR-FUNC | Performance and maintainability through Sections 3.7 and 3.9 |

| In-scope activities | Out-of-scope activities |
|---|---|
| Requirement analysis and RTM; test planning; full-format test cases; environment and test data definition; manual, UI, API, unit and integration testing; black-box and white-box design (3.3–3.4); TDD/BDD (3.5); automation and CI (3.6); basic JMeter and OWASP checks (3.7); defect tracking and RCA (3.8); SonarQube metrics (3.9); Agile/DevOps reflection (3.10) | Changing the application or fixing its defects (needs the project owner's approval); production systems or real personal data; internals of third-party frameworks; full penetration testing; large-scale load testing; formal WCAG certification; features that do not exist (account deletion, photo replacement, web-based admin creation, native mobile apps) |

Details: [Test Plan §2](PART01_TEST_PLAN_AND_STLC.md#2-testing-scope) and [§7.3–§7.4](PART01_TEST_PLAN_AND_STLC.md#73-in-scope-activities).

### 3.1.4 Test strategy and testing types

The strategy is **requirement-based and risk-based**: every test case traces to an RTM row; high-priority and security-related requirements are tested at more than one level; and expected results follow the **code**, because the project documentation is outdated in five places.

| Level / type | Test cases | Techniques | Tools actually used |
|---|---|---|---|
| Unit | UT-01 – UT-17 | Positive, negative, boundary; state checks | JUnit 5 + Mockito (Vitest for UT-16/17 is not installed) |
| Integration | INT-01 | Migration and schema-ownership check on real PostgreSQL | Dedicated local PostgreSQL 18.6 database |
| System – API | API-01 – API-25 | EP, BVA, decision rules, error guessing, security (CSRF, IDOR, CORS) | JUnit 5 + Spring Boot test server + Java HTTP client (Phase 11); REST Assured in the Member 3 copy |
| System – UI | UI-01 – UI-14 (automated), MT-01 – MT-22 (manual) | Scenario, boundary, state transition | Selenium 4.31.0 + Chrome 157 (Member 3 copy only) |
| Acceptance | Selected MT cases; BDD scenarios | Gherkin scenarios (Section 3.5) | Cucumber 7.23.0 (Member 3 copy) |
| Non-functional | API-05, API-09, API-22 – API-24; Sections 3.7 and 3.9 | OWASP checks, load baseline, static analysis | JMeter 5.6.3, SonarQube 25.3 (Member 4 copy) |

### 3.1.5 Quality model – ISO/IEC 25010

The product quality model of ISO/IEC 25010 is used as a framework for choosing and grouping test targets. This is not a claim of conformance or certification.

| Characteristic | Why it matters for PublicCare | Evidence planned | Result on 25 Nov 2026 |
|---|---|---|---|
| **Functional suitability** | Wrong status, ownership or approval logic misleads citizens | All 58 FRs through the RTM; NFR-FUNC | **Partly verified:** 41 executed cases, none failed. 8 of 63 RTM rows still have no executed case (§3.2.4) |
| **Security** | Personal data and private reports; only approved data may become public | API-05, API-09, API-15, API-19, API-22, API-23, UT-12; OWASP checks (3.7) | **Verified at API level on H2:** CSRF on all writes, role checks on all 12 admin endpoints, IDOR returns the same `404` as a missing report, CORS, no leakage in error bodies, path-traversal guard |
| **Reliability** | Reports and photos must not be lost or half-saved | API-23, API-24, UT-09, UT-13, MT-10, MT-19 | **Partly verified:** compensating delete (UT-09), after-commit clean-up (UT-13) and storage failure (API-23) passed. **Defect PC-QA-02:** a concurrent upload returns `500`. MT-10 and MT-19 not executed |
| **Usability** | Citizens of varied ability must complete reports | MT-16, UI-14; walkthroughs (3.7) | **Not verified by Part 01 cases.** Scripted walkthroughs without participants in Section 3.7 |
| **Compatibility** | Cookies, CSRF and CORS between SPA and API; browsers and widths | API-22, MT-17 | CORS verified (API-22). Cross-browser and 375 px **not verified** |
| **Performance efficiency** | Lists and the public feed slow down as data grows | JMeter (3.7) | Baseline only; no target defined, so no pass/fail (Section 3.7) |
| **Maintainability** | Testability and technical debt | SonarQube (3.9), white-box analysis (3.4) | Measured in Sections 3.4 and 3.9 |

### 3.1.6 Verification and validation

**Verification** asks "are we building the product right?" (comparison with the specification); **validation** asks "are we building the right product?" (comparison with real user needs, on the running system).

| Verification activity | State | Validation activity | State |
|---|---|---|---|
| V-1 Static review of code, migrations, documentation | Done (Part 01 analysis) | VA-1 End-to-end manual workflows | Not executed (manual cases) |
| V-2 RTM traceability check (58/58 FRs, 79/79 cases) | Done | VA-2 Role-based public-visibility scenario | Not executed manually; rules checked at API level |
| V-3 Peer review of test cases | **Not done** – members were not consulted after Phase 7 | VA-3 BDD acceptance scenarios | Done in the Member 3 copy (4 scenarios) |
| V-4 Static analysis (SonarQube, ESLint) | Done in member copies | VA-4 Usability / accessibility walkthrough | Partly (Section 3.7, no participants); MT-16 not executed |
| V-5 Unit testing | Done – UT-01 – UT-15 (Phase 11) | VA-5 Compatibility checks | Not executed (MT-17) |
| V-6 White-box analysis | Done (Section 3.4) | VA-6 Confirm intended behaviour with the owner | **Open** (RTM Q1) |
| V-7 Integration testing (INT-01) | Done in part (3 of 4 scenarios PASS) | | |
| V-8 API contract checks | Done – API-01 – API-25 | | |

The executed Part 01 work is therefore mostly **verification**. Validation with real users has not been done; this is the largest gap in the evidence for usability.

### 3.1.7 Resources and responsibilities

| Member | Name | Registration no. | Responsibility (as executed) | Evidence |
|---|---|---|---|---|
| Member 1 | Madusanka D.M.C | EG/2023/5720 | 3.1–3.2: requirement analysis, RTM, test plan, STLC, 79 test cases | `qa/3.1-3.2_member1/` |
| Member 2 | Dharmasiri S.O.R | EG/2023/5559 | 3.3–3.4: EP, BVA, decision tables, cyclomatic complexity, statement and branch coverage | `qa/3.3-3.4_member2/` |
| Member 3 | Kumaranayaka K.I.S | EG/2023/5697 | 3.5–3.6: TDD, BDD (Cucumber), REST Assured, Selenium, GitHub Actions | `qa/3.5-3.6_member3/` |
| Member 4 | Madushan S.C | EG/2023/5716 | 3.7–3.10: JMeter, OWASP checks, usability walkthroughs, defect tracking and RCA, SonarQube, Agile/DevOps reflection | `qa/3.7-3.10_member4/` |
| Integration | Not recorded by name (register P-OW-3) | – | Merging member work, the API-04 correction, Phase 11 execution, final package | `qa/INTEGRATION_LOG.md`, `qa/phase*/` |
| Project owner | Name not recorded (P-OW-1) | – | Approves any application change (D-1, D-8 approved); ran INT-01 | RTM "Owner decisions" |

The three workstreams proposed on 23 Nov 2026 were not used; work was divided by assignment section. Tools used are listed in [Test Plan §7.6](PART01_TEST_PLAN_AND_STLC.md#76-resources-and-responsibilities).

### 3.1.8 Schedule and milestones

**No dated plan was made in advance:** neither the assignment PDF nor any project record states planned start or end dates, so planned dates are not shown, and actual dates are not back-filled as plans. The submission deadline is not in any project record and must be taken from the LMS.

| Milestone | Actual date | Result |
|---|---|---|
| M1 Requirement analysis, RTM, test plan, 79 test cases | 23 Nov 2026 | Done |
| M2 Black-box and white-box testing (3.3–3.4) | 24 Nov 2026 | Done (Member 2 copy) |
| M3 TDD, BDD, automation, CI run (3.5–3.6) | 24 Nov 2026 | Done (Member 3 repository; CI run #1 on `70d01e0`) |
| M4 Performance, security, usability, defects, SonarQube (3.7–3.9) | 24 Nov 2026 | Done (Member 4 copy) |
| M5 Integration into the group repository; API-04 correction (D-1) | 25 Nov 2026 | Done; not committed |
| M6 INT-01 on PostgreSQL 18.6 | 25 Nov 2026 | 3 of 4 scenarios PASS, 1 BLOCKED |
| M7 Part 01 execution campaign (Phase 11) | 25 Nov 2026 | 39 cases executed; full suite 125/0 |
| M8 Test closure, final package | 25 Nov 2026 | This report; submission ZIP |

### 3.1.9 Risks, assumptions, dependencies and constraints

| Type | Item | Effect | Mitigation / status |
|---|---|---|---|
| Risk | Intended behaviour unclear (BR-09, BR-13, BR-22, IPv6 loopback, concurrent upload – RTM Q1) | Expected results cannot be judged | Observed results recorded and marked BLOCKED (6 cases + API-24); decision left to the owner |
| Risk | No response-time targets | Performance cannot pass or fail | Baseline measured only (3.7) |
| Risk | Tests run on H2, production uses PostgreSQL | PostgreSQL-specific behaviour missed | INT-01 on PostgreSQL 18.6; reference column emulated on H2; disclosed |
| Risk | No frontend test runner | UT-16, UT-17 cannot run | BLOCKED until the owner approves Vitest |
| Risk | Work done in separate member copies with different baselines | Results cannot be added together | Results labelled by origin; counts never combined |
| Risk | No manual tester session recorded | 22 MT cases and usability validation missing | Stated as the main remaining gap |
| Assumption | `example.test` addresses pass email validation (A-3) | – | Confirmed in API-03 |
| Assumption | Exact-size images can be produced (A-2) | – | Confirmed in API-12 |
| Assumption | INT-01 "existing database" = V1 + V2 objects without Flyway history (A-4, Q2) | Scenario (c) | Not confirmed; (c) BLOCKED |
| Dependency | Project owner's approval for any application change (D-2 – D-5) | Fixes, CI in the group repository, member test code | Pending |
| Dependency | PostgreSQL credentials held by the owner | PostgreSQL runs | Owner ran INT-01 personally |
| Constraint | QA must not modify the application (Test Plan §7.11) | New test code kept as evidence outside the source tree | Followed |
| Constraint | No real personal data; disposable databases only | – | Followed (synthetic data, in-memory H2) |

### 3.1.10 Entry and exit criteria

| Entry criterion | Met for Phase 11? |
|---|---|
| Code baseline fixed and recorded | Yes – `f565fb4` + D-1, production code hash-checked |
| Environment set up (§3.2.5) | Yes for API, unit and integration; no for manual, UI and cross-browser |
| Test data prepared | Yes – synthetic, generated at run time |
| Test cases peer-reviewed with agreed expected results | **No** (V-3 not done). Expected results used as written; unclear parts marked BLOCKED |
| Required application changes approved | Not needed – no application change was made |

| Exit criterion | Assessment (25 Nov 2026) |
|---|---|
| All High-priority test cases executed | **Not met.** All High-priority API and unit cases ran, but High-priority manual and UI cases (for example MT-01, MT-05, MT-18) did not |
| Every failed test case has a logged defect | Met – no case failed. The defect found (PC-QA-02, via a BLOCKED case) is documented but not yet in GitHub Issues |
| No open defect of the highest severity | Met – PC-QA-02 is Medium; PC-QA-01 (Section 3.8) is open in the group baseline |
| RTM shows the execution status of every requirement | Met – 63/63 rows updated |
| Test summary report written | Met – §3.2.7 |

### 3.1.11 Deliverables and completion criteria

| Deliverable | State | Location |
|---|---|---|
| Requirement analysis and RTM | Complete | [PART01_REQUIREMENTS_AND_RTM.md](PART01_REQUIREMENTS_AND_RTM.md) |
| Test plan and STLC mapping | Complete | [PART01_TEST_PLAN_AND_STLC.md](PART01_TEST_PLAN_AND_STLC.md) §1–§9 |
| 79 test cases in the full format with actual results | Complete in format; 47 with results, 32 not executed (reasons stated) | Test Plan §10.4 |
| Test environment and test data | Complete (confirmed vs proposed) | Test Plan §11–§12 |
| Execution evidence | Complete for executed cases | [phase11-part01-exec/](../phase11-part01-exec/README.md), [phase3a-api04-correction/](../phase3a-api04-correction/README.md), [INT-01](../phase7-final-completion/int01-postgresql/README.md) |
| Test summary report | Complete | §3.2.7 |

The test work is **complete** when every exit criterion in §3.1.10 is met. It is not yet complete: the manual and remaining UI cases still need a tester.

---

## 3.2 STLC and Test Cases

### 3.2.1 STLC as applied to PublicCare

| # | STLC phase | What was done for PublicCare | Output | State |
|---|---|---|---|---|
| 1 | Requirement analysis | Read-only review of code, migrations, READMEs and existing tests at `f565fb4`; 58 FRs, 5 NFR areas, 28 business rules, evidence labels (confirmed / inferred); 8 open questions | RTM document TASK 1–4, 7 | Done 23 Nov 2026 |
| 2 | Test planning | Scope, objectives, ISO/IEC 25010 targets, strategy, resources, risks, entry/exit criteria | Test Plan §1–§7 | Done 23 Nov 2026; updated 25 Nov 2026 |
| 3 | Test case design and development | 79 cases (22 MT, 14 UI, 25 API, 17 UT, 1 INT) in the full format; 63-row RTM; executable test code for 39 cases (Phase 11) | Test Plan §10.4; RTM TASK 5–6; `phase11-part01-exec/test-code/` | Done |
| 4 | Test environment set-up | Isolated copy of the backend (no secrets), H2, embedded server, synthetic data; dedicated PostgreSQL 18.6 for INT-01; member environments | Test Plan §11.3 | Done for API, unit, integration; **not** for manual/browser testing |
| 5 | Test execution | 41 cases in the group repository (API-04, INT-01, 39 in Phase 11); 6 UI cases in the Member 3 copy | Actual results in §10.4; logs and Surefire reports | Done for 47 of 79 |
| 6 | Defect reporting and tracking | PC-QA-02 found by API-24 (this section); PC-QA-01 tracked in GitHub Issues by Member 4 (Section 3.8) | §3.2.6; Section 3.8 | PC-QA-02 **New**, not yet in a tracker |
| 7 | Test closure | Results summarised, exit criteria assessed, residual risks and recommendations | §3.2.7 | Done 25 Nov 2026 |

### 3.2.2 Test case format

Every one of the 79 cases in [Test Plan §10.4](PART01_TEST_PLAN_AND_STLC.md#104-test-cases-in-the-full-format) uses the same fields: **Test Case ID; Requirement ID(s); Description; Type / Priority; Preconditions; Test Data; Test Steps; Expected Result; Actual Result; Status; Execution details** (tester, date, baseline, environment, test method, evidence – or the reason the case was not executed).

Status values:

| Status | Meaning |
|---|---|
| PASS | Executed in the group repository; every judged step met the expected result |
| PASS (part BLOCKED) | As PASS, but one part depends on an unanswered question; that part's observed result is recorded and **not** judged |
| PASS (limitation) | PASS, with a part of the expected result that the response cannot show (API-21) |
| BLOCKED | Executed, but the whole expected result is pending a decision (API-24) |
| PARTIALLY EXECUTED | Some steps or scenarios not run (INT-01) |
| PASS – Member 3 copy only | Executed outside the group repository; not a group result |
| NOT EXECUTED / BLOCKED – not executable | Not run; the reason is stated |

No case is marked PASS without an execution record, and expected results were not changed after execution.

### 3.2.3 Test case inventory and results

**Summary (79 cases):**

| Level | Cases | PASS | PASS (part BLOCKED) | BLOCKED | Partial | Member 3 copy only | Not executed |
|---|---:|---:|---:|---:|---:|---:|---:|
| Manual (MT) | 22 | 0 | 0 | 0 | 0 | 0 | 22 |
| UI automated (UI) | 14 | 0 | 0 | 0 | 0 | 6 | 8 |
| API | 25 | 22 | 2 | 1 | 0 | 0 | 0 |
| Unit (UT) | 17 | 11 | 4 | 0 | 0 | 0 | 2 (BLOCKED) |
| Integration (INT) | 1 | 0 | 0 | 0 | 1 | 0 | 0 |
| **Total** | **79** | **33** | **6** | **1** | **1** | **6** | **32** |

Group repository: 41 executed (52 %); 39 of them gave a PASS for every judged step; **0 FAIL**. Including the Member 3 copy: 47 of 79 (59 %) executed somewhere.

**All 79 cases** (full steps, data, expected and actual results in Test Plan §10.4):

| ID | Title | Level | Requirements | Status | Where executed |
|---|---|---|---|---|---|
| MT-01 | Register a Citizen account with valid details | Manual | FR-01.1, FR-01.6 | **NOT EXECUTED** | – |
| MT-02 | Registration is blocked when the password confirmation differs | Manual | FR-01.6 | **NOT EXECUTED** | – |
| MT-03 | Registration is blocked when the Terms checkbox is not ticked | Manual | FR-01.6 | **NOT EXECUTED** | – |
| MT-04 | Duplicate email with different case and padding is rejected | Manual | FR-01.4 | **NOT EXECUTED** | – |
| MT-05 | Role-based landing page after login | Manual | FR-02.2, FR-02.7 | **NOT EXECUTED** | – |
| MT-06 | Generic error for invalid credentials | Manual | FR-02.3 | **NOT EXECUTED** | – |
| MT-07 | Logout ends access in the UI | Manual | FR-02.6 | **NOT EXECUTED** | – |
| MT-08 | Create a report with a valid photo | Manual | FR-03.1, FR-03.4, FR-03.5, FR-05.4 | **NOT EXECUTED** | – |
| MT-09 | Invalid photo files are rejected on the create-report page | Manual | FR-03.7 | **NOT EXECUTED** | – |
| MT-10 | Image upload fails after the report was created | Manual | FR-03.7 | **NOT EXECUTED** | – |
| MT-11 | Citizen edits an approved report | Manual | FR-04.4, FR-04.5, FR-04.6 | **NOT EXECUTED** | – |
| MT-12 | Admin saves a progress update | Manual | FR-06.5 | **NOT EXECUTED** | – |
| MT-13 | Three-level public approval and revocation | Manual | FR-07.1, FR-07.2, FR-07.3, FR-07.4 | **NOT EXECUTED** | – |
| MT-14 | Status change removes a report from the public feed | Manual | FR-07.7 | **NOT EXECUTED** | – |
| MT-15 | Admin deletes a report | Manual | FR-08.1, FR-08.4 | **NOT EXECUTED** | – |
| MT-16 | Keyboard-only and screen-reader walkthrough | Manual | NFR-USA | **NOT EXECUTED** | – |
| MT-17 | Cross-browser and mobile-width check | Manual | NFR-COMP | **NOT EXECUTED** | – |
| MT-18 | Citizen cannot open admin pages or another user's report | Manual | FR-02.9, FR-04.2 | **NOT EXECUTED** | – |
| MT-19 | Frontend behaviour when the backend is unreachable | Manual | NFR-REL | **NOT EXECUTED** | – |
| MT-20 | Citizen sees the admin's progress update | Manual | FR-05.2, FR-05.3 | **NOT EXECUTED** | – |
| MT-21 | Admin dashboard counts and recent reports | Manual | FR-06.1 | **NOT EXECUTED** | – |
| MT-22 | Admin exports the current page as CSV | Manual | FR-06.3 | **NOT EXECUTED** | – |
| UI-01 | Registration form boundaries and password mismatch (automated) | UI automated | FR-01.1, FR-01.2, FR-01.6 | **PASS – Member 3 copy only** | Member 3 copy (Selenium, local) |
| UI-02 | Login redirect by role (automated) | UI automated | FR-02.7 | **PASS – Member 3 copy only** | Member 3 copy (Selenium, local) |
| UI-03 | Failed login shows a generic alert (automated) | UI automated | FR-02.3 | **PASS – Member 3 copy only** | Member 3 copy (Selenium, local) |
| UI-04 | Signed-out user cannot submit a report (automated) | UI automated | FR-03.8 | **PASS – Member 3 copy only** | Member 3 copy (Selenium, local) |
| UI-05 | Create-report required fields and successful submission (automated) | UI automated | FR-03.1, FR-03.2 | **PASS – Member 3 copy only** | Member 3 copy (Selenium, local) |
| UI-06 | Image picker checks, preview and removal (automated) | UI automated | FR-03.7 | **NOT EXECUTED** | – |
| UI-07 | My Reports filters, counts and paging (automated) | UI automated | FR-04.1 | **NOT EXECUTED** | – |
| UI-08 | Status timeline for each status (automated, mocked API) | UI automated | FR-05.1, NFR-FUNC | **NOT EXECUTED** | – |
| UI-09 | Admin Issues approval toggle and filters (automated) | UI automated | FR-06.2, FR-07.1 | **NOT EXECUTED** | – |
| UI-10 | Delete dialog and pending-state safeguards (automated) | UI automated | FR-08.1, FR-08.4 | **NOT EXECUTED** | – |
| UI-11 | Admin update page: note limit, photo type, "Mark as resolved" and save (automated) | UI automated | FR-06.5, FR-06.8 | **NOT EXECUTED** | – |
| UI-12 | Public section: empty state, paging, navigation and unknown ID (automated) | UI automated | FR-07.4, FR-07.5 | **NOT EXECUTED** | – |
| UI-13 | Admin pages are gated for a citizen session (automated) | UI automated | FR-02.9 | **PASS – Member 3 copy only** | Member 3 copy (Selenium, local) |
| UI-14 | Account menu keyboard behaviour (automated) | UI automated | NFR-USA | **NOT EXECUTED** | – |
| API-01 | CSRF token endpoint | API | FR-02.1 | **PASS** | Group – Phase 11 |
| API-02 | Registration creates a Citizen and returns no password data | API | FR-01.1, FR-01.5 | **PASS** | Group – Phase 11 |
| API-03 | Registration input boundaries | API | FR-01.2 | **PASS** | Group – Phase 11 |
| API-04 | Duplicate email with different case or padding | API | FR-01.3, FR-01.4 | **PASS** | Group – Phase 3A |
| API-05 | State-changing requests without a CSRF token are rejected | API | FR-02.1, NFR-SEC | **PASS** | Group – Phase 11 |
| API-06 | Login starts a session and rotates the session ID | API | FR-02.2 | **PASS** | Group – Phase 11 |
| API-07 | Generic 401 and 72-byte password limit on login | API | FR-02.3, FR-02.4 | **PASS** | Group – Phase 11 |
| API-08 | Current user before, during and after a session | API | FR-02.5, FR-02.6 | **PASS** | Group – Phase 11 |
| API-09 | Authorisation matrix for every admin endpoint | API | FR-02.8, FR-06.4, NFR-SEC | **PASS** | Group – Phase 11 |
| API-10 | Create report; owner is taken from the session | API | FR-03.1, FR-03.3 | **PASS** | Group – Phase 11 |
| API-11 | Report field boundaries and trimming | API | FR-03.2, FR-03.4 | **PASS** | Group – Phase 11 |
| API-12 | Report image upload rules | API | FR-03.5, FR-03.6 | **PASS** | Group – Phase 11 |
| API-13 | Image retrieval headers and access scope | API | FR-04.3, FR-06.4 | **PASS** | Group – Phase 11 |
| API-14 | List endpoints: paging, sort, filters, summary and owner scope | API | FR-04.1, FR-04.7, FR-06.2, NFR-FUNC | **PASS** | Group – Phase 11 |
| API-15 | Cross-user access and editing an approved report | API | FR-04.2, FR-04.4, FR-04.5 | **PASS** | Group – Phase 11 |
| API-16 | Direct status PATCH rules and approval revocation | API | FR-05.2, FR-06.6, FR-07.7 | **PASS (part BLOCKED)** | Group – Phase 11 |
| API-17 | Progress update creation is atomic; history order | API | FR-05.3, FR-06.5, FR-06.7, NFR-FUNC | **PASS** | Group – Phase 11 |
| API-18 | Public approval endpoints | API | FR-07.1, FR-07.2, FR-07.3 | **PASS (part BLOCKED)** | Group – Phase 11 |
| API-19 | Public feed and detail: paging, approval and data minimisation | API | FR-07.4, FR-07.5, FR-07.6, NFR-FUNC | **PASS** | Group – Phase 11 |
| API-20 | Admin delete and refused delete requests | API | FR-08.1, FR-08.3, FR-08.5 | **PASS** | Group – Phase 11 |
| API-21 | Health endpoints | API | FR-09.1 | **PASS (limitation)** | Group – Phase 11 |
| API-22 | CORS preflight for allowed and disallowed origins | API | NFR-SEC, NFR-COMP | **PASS** | Group – Phase 11 |
| API-23 | Server error does not leak details and the backend keeps serving | API | NFR-SEC, NFR-REL | **PASS** | Group – Phase 11 |
| API-24 | Two concurrent image uploads to the same report | API | NFR-REL | **BLOCKED (PC-QA-02)** | Group – Phase 11 |
| API-25 | Admin summary endpoint | API | FR-06.1, NFR-FUNC | **PASS** | Group – Phase 11 |
| UT-01 | `AuthService.register`: normalisation, BCrypt hash and role | Unit | FR-01.1, FR-01.3, FR-01.5, FR-01.7 | **PASS** | Group – Phase 11 |
| UT-02 | Registration race: unique-constraint error becomes a duplicate-email error | Unit | FR-01.4 | **PASS** | Group – Phase 11 |
| UT-03 | Password byte limit at registration | Unit | FR-01.2 | **PASS** | Group – Phase 11 |
| UT-04 | `ReportService.findPage` paging and sort parameters | Unit | FR-04.1, FR-04.7, NFR-FUNC | **PASS (part BLOCKED)** | Group – Phase 11 |
| UT-05 | `ReportService.criteria` status and search handling | Unit | FR-04.1, FR-04.7 | **PASS** | Group – Phase 11 |
| UT-06 | `ReportService.updateStatus` | Unit | FR-06.6, FR-07.7 | **PASS (part BLOCKED)** | Group – Phase 11 |
| UT-07 | `ReportService.updateMine` | Unit | FR-04.5, FR-07.7 | **PASS** | Group – Phase 11 |
| UT-08 | `ReportImageService.validateImage` and `isWebp` | Unit | FR-03.6 | **PASS (part BLOCKED)** | Group – Phase 11 |
| UT-09 | `ReportProgressService.create` | Unit | FR-06.5 | **PASS** | Group – Phase 11 |
| UT-10 | `ReportProgressUpdate.approveNoteForPublic` | Unit | FR-07.3 | **PASS** | Group – Phase 11 |
| UT-11 | `PublicReportService` | Unit | FR-07.4 | **PASS** | Group – Phase 11 |
| UT-12 | `PrivateImageStorage.resolve` path guard | Unit | NFR-SEC | **PASS** | Group – Phase 11 |
| UT-13 | `ReportDeletionService` | Unit | FR-08.2, FR-08.3 | **PASS** | Group – Phase 11 |
| UT-14 | `AdminSetupRunner.isLoopbackPostgres` | Unit | FR-06.9 | **PASS (part BLOCKED)** | Group – Phase 11 |
| UT-15 | `AdminAccountSetupService` | Unit | FR-06.9 | **PASS** | Group – Phase 11 |
| UT-16 | Frontend status and query helpers (Vitest) | Unit | FR-05.1, NFR-FUNC | **BLOCKED – not executable** | – |
| UT-17 | `downloadCsv` escaping (Vitest) | Unit | FR-06.3 | **BLOCKED – not executable** | – |
| INT-01 | Flyway migration and schema-ownership check | Integration | FR-09.2 | **PARTIALLY EXECUTED** | Group – Phase 8A (PostgreSQL 18.6) |

### 3.2.4 Requirements traceability

The RTM ([TASK 5](PART01_REQUIREMENTS_AND_RTM.md#task-5-initial-requirement-traceability-matrix)) has 63 rows (58 FR + 5 NFR). Each row now lists the status of each linked case.

| RTM coverage on 25 Nov 2026 | Rows |
|---|---:|
| Every linked case PASS in the group repository | 17 |
| At least one linked case executed (the rest not executed or partly blocked) | 38 |
| No linked case executed | 8 – FR-03.7, FR-04.6, FR-05.1, FR-05.4, FR-06.3, FR-06.8, FR-08.4, NFR-USA (all traced only by manual, UI or Vitest cases) |
| **Total** | **63** |

Forward traceability: 58/58 FRs have at least one case. Backward traceability: 79/79 cases trace to at least one RTM row.

### 3.2.5 Test environment and configuration

| Environment | Confirmed / proposed | OS | Runtime and build | Database | Browser / client | Used for |
|---|---|---|---|---|---|---|
| ENV-P11 | **Confirmed** | Windows 11 Pro 10.0.26200 | Oracle Java 27 (27+35-2325); Maven 3.9.16 (Maven Wrapper) | H2 in-memory, PostgreSQL mode | Real embedded Tomcat; Java HTTP client with cookies and CSRF header | 39 Phase 11 cases; full suite 125/0 |
| ENV-3A | **Confirmed** | Windows 11 | Oracle Java 27; Maven Wrapper 3.9.16 | H2 | MockMvc | API-04 |
| ENV-J21 | **Confirmed** | Linux – WSL2 Ubuntu 26.04.1 | Ubuntu OpenJDK 21.0.13.1 | H2 | MockMvc | Regression 85/0 on Java 21 |
| ENV-03 | **Confirmed** | Windows 11 | JetBrains Runtime 21.0.8 | **PostgreSQL 18.6** (dedicated local database) | HTTP (health) | INT-01 |
| M3 local / CI | **Confirmed** (Member 3) | Windows 11; `ubuntu-latest` | Temurin 25.0.5; Temurin 21.0.13 in CI; Node 24.13.0 / 22 | H2 | Chrome 157 headless, Selenium 4.31.0 | UI-01 – UI-05, UI-13; CI run #1 |
| ENV-01 | **Proposed – not used** | TBC | Java 21 | Local PostgreSQL | Frontend in a browser | Manual cases |
| ENV-04 | **Proposed – not used** | TBC | – | – | Firefox, Edge, Safari; 375 px; devices | MT-17 |

Configuration observed (no override): CORS origin `http://localhost:5173`; session cookie `HttpOnly; SameSite=Lax`, no `Secure` (default); 5 MB upload limit (5,242,881 bytes → `413`); health details hidden. **Test data:** synthetic only – citizens registered through the API on `qa.example.test`, one Admin inserted with a BCrypt hash, reports through the API, volume data through repositories, images generated in memory; databases discarded after each run. Full details: [Test Plan §11.3 and §12](PART01_TEST_PLAN_AND_STLC.md#113-confirmed-and-proposed-environments--summary-phase-11-25-nov-2026).

### 3.2.6 Defects and observations from Part 01 execution

| ID | Found by | Summary | Severity (proposed) | Status |
|---|---|---|---|---|
| **PC-QA-02** | API-24 | Two simultaneous image uploads to one report: in 4 of 5 rounds the second request returned `500` instead of `409`. Cause: check-then-act race in `ReportImageService.upload`; the unique-constraint error is not mapped. Data stayed consistent (1 image per report) | Medium | **New** – documented in the [execution record](../phase11-part01-exec/README.md#pc-qa-02--a-concurrent-second-image-upload-returns-500-instead-of-409-new-defect); not yet in GitHub Issues; not fixed |
| OBS-1 | UT-14 | The admin-setup loopback check rejects `[::1]` (IPv6) | – | For the owner's decision (RTM Q1, D-4) |
| OBS-2 | UT-05 | Service-level status error is wrapped by Spring (`InvalidDataAccessApiUsageException`); the API still returns `400` | – | Information |
| OBS-3 | API-21 | `/actuator/health` does not show the database component | – | Expected-result wording to be refined |
| PC-QA-01 | Section 3.8 (Member 4) | Registration form recovery after a password mismatch | See 3.8 | Closed in the Member 4 copy; open in the group baseline |

### 3.2.7 Test summary and closure

| Metric | Value |
|---|---|
| Test cases designed | 79 |
| Executed in the group repository | 41 (52 %) |
| Executed anywhere (incl. Member 3 copy) | 47 (59 %) |
| Pass rate of judged steps in executed cases | 100 % – no FAIL |
| Cases with an unresolved (BLOCKED) part | 8 (6 partly, API-24, INT-01 scenario c) |
| New defects | 1 (PC-QA-02, Medium) |
| RTM rows with every case PASS | 17 of 63 |
| Regression run after Phase 11 | 125 JUnit tests, 0 failures |

**Conclusion.** At API, unit and integration level, PublicCare behaves as its requirements state for every executed case. The strongest evidence is for **security** (CSRF, role checks, IDOR, CORS, data minimisation, path traversal) and for **functional suitability** of the backend. The main **residual risks** are: (1) no manual, browser or usability testing of the frontend (32 cases); (2) the concurrency defect PC-QA-02; (3) eight results that wait for the owner's decisions (RTM Q1, Q2); (4) H2 rather than PostgreSQL for all but INT-01.

**Recommendations.** Run the 22 manual cases in one session with the frontend and record screenshots; decide RTM Q1 and Q2 so that the BLOCKED parts can be judged; log PC-QA-02 in GitHub Issues and fix it with the same pattern `AuthService` uses for duplicate emails; approve a location for QA test code (OQ-12) so the Phase 11 tests can run in CI.

### 3.2.8 Limitations and open items

- Phase 11 results were produced by integration, not by a named member, and were not peer-reviewed.
- All Phase 11 results used H2; only INT-01 used PostgreSQL.
- The Phase 11 test code is kept as evidence in `qa/`; it is not in the application source tree and does not run in CI.
- Nothing is committed to Git; the D-1 change is uncommitted.
- Owner decisions still open: D-2 – D-5, D-7; RTM Q1, Q2; cleanup of the INT-01 database.

---

## References and evidence

| Evidence | Location |
|---|---|
| Assignment task list | `EC4206_Group_Assignment_Task_List (1).pdf` (module folder) |
| Test plan, 79 full-format cases, environment, test data, As Executed Addendum | [PART01_TEST_PLAN_AND_STLC.md](PART01_TEST_PLAN_AND_STLC.md) |
| Requirements, business rules, RTM | [PART01_REQUIREMENTS_AND_RTM.md](PART01_REQUIREMENTS_AND_RTM.md) |
| Phase 11 execution record, test code, logs, Surefire reports | [../phase11-part01-exec/](../phase11-part01-exec/README.md) |
| API-04 execution (Phase 3A) | [../phase3a-api04-correction/](../phase3a-api04-correction/README.md) |
| INT-01 execution on PostgreSQL 18.6 | [../phase7-final-completion/int01-postgresql/](../phase7-final-completion/int01-postgresql/README.md) |
| Where each member test ran | [../phase7-final-completion/SECTION_3.2_EXECUTION_RECONCILIATION.md](../phase7-final-completion/SECTION_3.2_EXECUTION_RECONCILIATION.md) |
| Member 3 Selenium evidence | [../3.5-3.6_member3/evidence/ui-automation/](../3.5-3.6_member3/evidence/ui-automation/README.md) |
| Archived original analysis | [PROJECT_ANALYSIS_REPORT.md](PROJECT_ANALYSIS_REPORT.md) (superseded counts) |
