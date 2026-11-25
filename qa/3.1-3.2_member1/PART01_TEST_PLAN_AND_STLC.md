# PublicCare – Part 01: Introduction, Test Planning and STLC

> **Test execution status (25 Nov 2026): 41 of the 79 test cases have been executed in the group repository.**
> This document was written as a plan on 23 Nov 2026. API-04 (integration Phase 3A) and INT-01 (Phase 8A) were executed first. Integration Phase 11 then executed 39 further API and unit cases against the unchanged group baseline ([execution record](../phase11-part01-exec/README.md)). Results: **33 PASS**; **6 PASS with one part BLOCKED** (that part observed but not judged, because the intended behaviour is open – RTM Q1 – or not stated); **1 BLOCKED** (API-24, which found defect PC-QA-02); **1 PARTIALLY EXECUTED** (INT-01). Six UI cases passed in the Member 3 copy only. **32 cases are not executed**: the 22 manual cases, 8 UI cases, and the 2 Vitest cases (BLOCKED). No case failed. Each quality attribute below is a planned target unless §5.3 states a verified result. See the As Executed Addendum, AE-F. Items marked **[Proposed]** need agreement from the group. Items marked **To Be Confirmed (TBC)** are not supported by the source documents.
> *[As executed: Phase 5A, applied 25 Nov 2026]* This refers to the Part 01 test cases in this document. Sections 3.3–3.10 report executed work from member copies; see the [As Executed Addendum](#as-executed-addendum).
> QA activities must not change the original PublicCare application without the project owner's explicit approval (see §7.11).

## About this document

| Item | Detail |
|---|---|
| Module | EC4206 – Software Testing and Quality Assurance, Group Assignment |
| Assignment sections covered | 3.1 Introduction & Test Planning (LO-1) and 3.2 STLC & Test Cases (LO-1, LO-2) |
| Author (sections 3.1–3.2) | Member 1 – Madusanka D.M.C, EG/2023/5720 *[Phase 9, 25 Nov 2026: supplied by the project owner]* |
| Software Under Test (SUT) | PublicCare – Citizen Issue Reporting and Tracking Platform (assignment Step 0, Option A: reuse an existing project) |
| Code baseline | git `f565fb4` ("Finalize report deletion and cleanup") in `publiccare/`, as analysed in the source documents |
| Document status | *[Phase 11, 25 Nov 2026: 39 further cases executed; §10.4 Actual Result, Status and a new Execution details row updated for all 79 cases; the header, §5.3, §7.6, §7.8, §7.9, §8, §9.3, §9.4, §10.2–§10.4, §11, §12.4 and the Addendum (new AE-F) updated. Outdated statements were rewritten instead of annotated; the original wording is kept in the archived copy `Public care/qa/PART01_TEST_PLAN_AND_STLC.md` and in the earlier submission package]* Draft for group review, 23 Nov 2026; revised after the Part 01 review on the same date (counts, §7.6, new §7.11, §10.4); §10.4 then completed with all 79 test cases in the full format; §1–§7 (assignment section 3.1) then reviewed against the RTM document and the code. 25 Nov 2026 (integration Phase 3A): API-04 expected result clarified and actual result recorded after owner decision D-1 (FR-01.3, Option A); §9.3, §9.4, §10.4 notes and C-5 updated accordingly. 25 Nov 2026 (integration Phase 3B): existing backend test-method count corrected from 73 to 72 (§1.4, §4, §9.3, OQ-9), OQ-9 answered, and statements that no test had been run aligned with Phase 3A (§5.3, §8 phase table, §10.4 API-05, UT-01, UT-13, UT-15); marked *[Corrected: Phase 3B]* or *[Phase 3A]*. 25 Nov 2026 (integration, after owner decision D-8 made this the canonical copy): the prepared corrections UB-1 to UB-17 of `qa/phase5b-confirmation-register/CONFIRMATION_REGISTER.md` §5.1 applied – header scope note, §5.3, §7.3, §7.6, §7.8, §7.9, §8, §8.4, §11.1, §11.2 ENV-05, §12.2 TD-09, OQ-9, §1.4 and §9.3 (Java 21 run), and the new [As Executed Addendum](#as-executed-addendum); marked *[As executed: Phase 5A, applied 25 Nov 2026]* or *[Java 21 run …]*. Every original planned value is kept. Names, registration numbers, planned dates and the submission deadline stay TBC / PENDING. Authors: TBC |
| Location | `publiccare/qa/3.1-3.2_member1/PART01_TEST_PLAN_AND_STLC.md` – the canonical Part 01 copy (owner decision D-8, CAN-1) *[Corrected: Phase 7, 25 Nov 2026 – same correction as UB-18 (register §5.2, "noticed while applying"). Was `qa/PART01_TEST_PLAN_AND_STLC.md`]* |
| Links *[Phase 7, 25 Nov 2026]* | Relative code links rewritten for the canonical location: prefix `../publiccare/` → `../../` (6 links; UB-19 for this file). Nothing else in the links changed. The linked test classes are unchanged from `f565fb4` |

**Sources used**

| Source | Used for |
|---|---|
| `EC4206_Group_Assignment_Task_List (1).pdf` (module folder, outside this repository) | Required deliverables and terminology. This is the authority for what Part 01 must contain |
| [PART01_REQUIREMENTS_AND_RTM.md](PART01_REQUIREMENTS_AND_RTM.md) | Requirements, business rules, RTM, test case inventory and counts. This is the corrected working copy, so its counts are used here |
| [PROJECT_ANALYSIS_REPORT.md](PROJECT_ANALYSIS_REPORT.md) | Archived original analysis. Used only to confirm facts. Its older counts (59 requirements, 74 test cases) were corrected in the working copy and are **not** used here |

This document does not repeat the requirements, the RTM or the test case inventory. It refers to them.

**Assignment checklist coverage**

| Assignment item (PDF) | Section in this document |
|---|---|
| 3.1 Define scope: which features/modules will be tested | §1, §2 (supported by the FR and NFR overviews in §3, §4) |
| 3.1 Write a short Test Plan (objectives, scope, in/out of scope, resources, schedule) | §7 |
| 3.1 State the quality model/attributes targeted (ISO 25010) | §4, §5 |
| 3.1 Note verification vs. validation activities | §6 |
| 3.2 Map STLC phases for the project | §8 |
| 3.2 Write test cases in a proper format (ID, description, preconditions, steps, expected result, actual result, status) | §9, §10 |
| 3.2 Define test environment/configuration (browser, OS, test data setup) | §11, §12 |

---

## 1. Introduction and Project Overview

### 1.1 Introduction

PublicCare is a web platform where citizens report public issues and track how they are handled. A citizen submits a report with a title, category, description, location and an optional photo. An administrator reviews the report, changes its status and adds progress updates. An administrator can also approve a report for a public feed, which anonymous visitors can read.

The group selected PublicCare as the Software Under Test under assignment Step 0, Option A (an existing project built for another module). The full source code is available, which supports later coverage analysis and SonarQube scanning (assignment sections 3.4 and 3.9).

### 1.2 Project purpose

Based on the source analysis, the system lets:

- citizens report public issues in six UI categories (Road damage, Waste & garbage, Streetlights, Drainage, Water supply, Other public issue);
- citizens follow each report through the statuses **PENDING → IN_PROGRESS → RESOLVED** (the normal flow; the code does not enforce this order, see BR-13) with a reference number (`PC-` + 8 digits), a status timeline and a progress history;
- administrators manage all reports, add progress updates, approve content for public display and delete reports;
- the public see approved reports only, with personal data removed.

### 1.3 User roles

| Role | Description | How the account is created |
|---|---|---|
| **Citizen** | Registers, signs in, creates reports, uploads one photo per report, and views and edits only their own reports | Public registration (always assigned `CITIZEN`) |
| **Administrator (Admin)** | Views and searches all reports, adds progress updates, changes status, approves or revokes public visibility, deletes reports | Not through the web. An operator runs a command-line setup that creates the first admin (FR-06.9) |
| Anonymous visitor | Views the home page and the public feed of approved reports; can register and sign in | – |
| Operator (non-web) | Runs the first-admin setup command | – |

Citizen and Admin are the two main roles for testing. The full permissions matrix is in [PART01_REQUIREMENTS_AND_RTM.md – TASK 3](PART01_REQUIREMENTS_AND_RTM.md#task-3-user-roles-and-permissions).

### 1.4 Technology stack

| Layer | Technology (confirmed in source analysis) |
|---|---|
| Frontend | React 19 + Vite 8 single-page application (`publiccare/src/`). Routing is handled manually from `window.location.pathname`. ESLint is configured. npm scripts: `dev`, `build`, `lint`, `preview` |
| Backend | Spring Boot 3.5.16, Java 21, Spring Security, JPA, Maven (`publiccare/backend/`) |
| Database | PostgreSQL. Schema managed by Flyway migrations V1–V5. Hibernate `ddl-auto: none` |
| File storage | Private image storage outside the web root, with UUID file keys |
| Existing automated tests | 72 backend `@Test` methods in 12 classes *[Corrected: Phase 3B; was 73, which counted `@TestConfiguration`]* (JUnit 5, Mockito, `@WebMvcTest`, `@DataJpaTest` with H2). **No frontend test runner is configured.** These tests exist in the code base. *[Phase 3A: run once on this branch at `f565fb4`: 72 tests, 0 failures, on Java 27 and H2; not run on Java 21 or PostgreSQL. See §9.3]* *[Java 21 run, 25 Nov 2026 (integration; applied 25 Nov 2026): the "not run on Java 21" note above is kept as the Phase 3A record. The backend suite of the integration working tree (`f565fb4` + the uncommitted D-1 change) was later run on **Java 21.0.13.1** (Ubuntu OpenJDK, Linux – WSL2, Ubuntu 26.04.1): **85 tests, 0 failures, 0 errors, 0 skipped** (72 existing + 13 Phase 3A tests), **H2 in-memory**. Still **not run on PostgreSQL**. Evidence: Surefire reports in `qa/evidence/java21-run/surefire-reports/`. The Maven console log and Maven version output were not archived (confirmation register ENV-J10, PENDING)]* |

### 1.5 Architecture overview

```
 Browser
   │  React 19 SPA (Vite 8)
   │  JSON over HTTP · session cookie (JSESSIONID) · CSRF token (XSRF-TOKEN cookie + X-XSRF-TOKEN header)
   ▼
 Spring Boot 3.5 REST API (Java 21)
   ├─ /api/auth/**           register, login, logout, csrf, me
   ├─ /api/reports/**        citizen's own reports, images, progress history
   ├─ /api/admin/reports/**  admin-only management, approvals, deletion (ROLE_ADMIN)
   ├─ /api/public/reports/** anonymous, approved reports only
   └─ /api/health, /actuator/health
   │
   ├──► PostgreSQL (Flyway V1–V5: users, reports, report images, progress updates, public approvals)
   └──► Private image storage (files outside the web root)

 Separate non-web command: first-admin setup (AdminSetupApplication)
```

Key design points that affect testing:

- **Authorisation is enforced on the server.** The frontend admin gate is only a convenience, so access control must be tested at the API level.
- **Owner-scoped queries.** A citizen who requests another user's report receives the same `404` as for a missing report.
- **Three-level public approval.** A report, each progress update and each update note are approved separately (BR-16).
- **Backend code is organised by feature**: `auth`, `report`, `security`, `admin`.

---

## 2. Testing Scope

The scope is based on the features found in code baseline `f565fb4`. Scope labels:

- **Confirmed** – the feature is confirmed in the code (evidence code C or C+D), it has RTM rows and proposed test cases, and nothing blocks testing it.
- **[Proposed]** – testing is planned, but it depends on a group decision, a missing target, a clarification or a tool that is not yet installed.

The whole scope still needs the group's agreement before test execution starts.

The FR rows in §2.1 together cover all **58 functional requirements** (FR-01.1 to FR-09.2). References such as "RTM §2.1" point to TASK 2 of [PART01_REQUIREMENTS_AND_RTM.md](PART01_REQUIREMENTS_AND_RTM.md#task-2-non-functional-requirements).

### 2.1 Features and modules included in testing

| Module | Requirements | Scope status | Notes |
|---|---|---|---|
| User registration | FR-01.1–FR-01.7 | Confirmed | Validation boundaries, email normalisation, duplicate email, role forced to CITIZEN, BCrypt storage |
| Authentication, session and access control | FR-02.1–FR-02.9 | Confirmed | CSRF, login/logout, generic error, `/me`, role-based landing, server authorisation, admin page gate |
| Issue reporting | FR-03.1–FR-03.8 | Confirmed | Report creation, field validation, owner from session, single image upload and validation |
| Citizen issue management | FR-04.1–FR-04.7 | Confirmed | List/filter/paging, detail, own photo, edit, approval revoked on edit |
| Issue tracking | FR-05.1–FR-05.4 | Confirmed | Status timeline, latest admin note, progress history, reference number |
| Admin management | FR-06.1–FR-06.8 | Confirmed | Dashboard, list/search, CSV export, progress updates, status change, history |
| First-admin setup (command line) | FR-06.9 | Confirmed | Unit-test level only (UT-14, UT-15) |
| Public report visibility | FR-07.1–FR-07.7 | Confirmed | Three approval levels, public feed and detail, data minimisation, automatic revocation |
| Report deletion | FR-08.1–FR-08.5 | Confirmed | Admin hard delete, after-commit file clean-up, error handling, citizens cannot delete |
| Platform support | FR-09.1 | Confirmed | Health endpoints |
| Platform support – Flyway schema | FR-09.2 | **[Proposed]** | INT-01 needs a disposable PostgreSQL database. The environment is not decided (RTM Q2, Q3) |
| Security characteristics | NFR-SEC | Confirmed | CSRF, authorisation, IDOR, CORS, error leakage, path traversal. Basic OWASP checks are part of assignment 3.7 |
| Reliability characteristics | NFR-REL | Confirmed (MT-19, API-23) / **[Proposed]** (API-24) | API-24 checks an inferred **[I]** behaviour; its expected result needs clarification |
| Usability and accessibility | NFR-USA | **[Proposed]** | Keyboard and screen-reader walkthrough (MT-16) and account-menu focus (UI-14). Assistive technology to be used is TBC |
| Compatibility | NFR-COMP | **[Proposed]** | Depends on available browsers and devices (TBC). CORS check API-22 |
| Functional correctness | NFR-FUNC | Confirmed | Cross-cutting correctness checks (RTM §2.7). Two items have no test case yet |
| Performance | RTM §2.2 (no RTM row) | **[Proposed]** | No response-time targets are documented. Basic JMeter load test belongs to assignment 3.7 |
| Maintainability | RTM §2.5 (no RTM row) | **[Proposed]** | Assessed by static analysis (SonarQube, assignment 3.9) and white-box analysis (3.4), not by test cases. SonarQube availability is TBC *[As executed: Phase 5A, applied 25 Nov 2026: SonarQube 25.3.0.104237 used by Member 4 (3.9) on the Member 4 copy + `f78deb1` (`MEMBER4_QA_REPORT.md` line 4; `sonar-run.txt`). How 3.9 words this baseline is still to be confirmed by Member 4 (register P-M4-8)]* |
| Frontend unit tests | UT-16, UT-17 | **[Proposed]** | Need Vitest, which is not installed |
| Inferred and clarification-needed behaviour | BR-09, BR-13, BR-22 and the **[I]** items | **[Proposed]** | Expected results cannot be fixed until intent is confirmed (RTM Q1) |

### 2.2 Features and activities excluded from testing

| Excluded item | Reason | Status |
|---|---|---|
| User account deletion | Not implemented in the SUT (TASK 3) | Confirmed exclusion |
| Replacing or deleting a report photo | Not implemented (FR-04.6). Only the absence of the feature is checked, as a negative case in MT-11 | Confirmed exclusion (feature) |
| Admin account creation through the web | Not available; admin setup is command-line only | Confirmed exclusion (feature) |
| Changing the application source code, configuration, dependencies, database schema or CI/CD workflows, or fixing defects | QA activities record defects; they do not change the application. Any change needs the project owner's explicit approval (§7.11) | Confirmed exclusion (project owner's rule, §7.11) |
| Testing against production systems or real personal data | Only a disposable test database and fictitious data are used | [Proposed] exclusion |
| Internal testing of third-party frameworks (Spring Security, React, Flyway, PostgreSQL) | Only the SUT's use of them is tested | [Proposed] exclusion |
| Full penetration testing and formal security certification | The assignment asks for 1–2 manual OWASP Top 10 checks (3.7) | [Proposed] exclusion |
| Large-scale or long-duration load testing | The assignment asks for a basic JMeter test (3.7) | [Proposed] exclusion |
| Formal WCAG conformance certification | A keyboard and screen-reader walkthrough is planned (MT-16), not a certified audit | [Proposed] exclusion |
| Native mobile applications | None exist. Mobile *screen widths* in a browser are in scope (MT-17) | Confirmed exclusion |
| Production deployment and hosting configuration | No deployment environment is documented. The `Secure` cookie setting is recorded as a risk to check (RTM §2.1) | [Proposed] exclusion; see Open Questions |

---

## 3. Functional Requirements Overview

The functional requirements are defined in [PART01_REQUIREMENTS_AND_RTM.md – TASK 1](PART01_REQUIREMENTS_AND_RTM.md#task-1-functional-requirements). They are not copied here.

**Summary: 58 functional requirements in 9 groups.** All 58 are confirmed in the source code (evidence code C or C+D; FR-08.5 is confirmed by the absence of a citizen delete route).

| Group | Name | Count | Main roles | Summary |
|---|---|:-:|---|---|
| FR-01 | User Registration | 7 | Anonymous | Citizen account creation, input validation, email normalisation, duplicate rejection, BCrypt storage |
| FR-02 | Authentication, Session and Access Control | 9 | All | CSRF, session login/logout, generic login error, role-based landing, server and client access control |
| FR-03 | Issue Reporting | 8 | Citizen | Create report, field validation, six UI categories, single photo upload with server and client checks |
| FR-04 | Issue Management (citizen's own reports) | 7 | Citizen | List, filter, page, view, edit own reports; editing revokes public approval |
| FR-05 | Issue Tracking | 4 | Citizen | Status timeline, latest admin note, progress history, reference number |
| FR-06 | Admin Management | 9 | Admin, Operator | Dashboard, search, CSV export, progress updates, status change, first-admin setup |
| FR-07 | Public Report Visibility | 7 | Admin, Anonymous | Approval of reports, updates and notes; public feed and detail; data minimisation; auto-revocation |
| FR-08 | Report Deletion | 5 | Admin | Permanent delete, after-commit file clean-up, error handling, UI safeguards, no citizen delete |
| FR-09 | Platform Support | 2 | – | Health checks; Flyway-managed schema |
| **Total** | | **58** | | |

Related material in the same document:

- **Business rules:** 28 rules, BR-01 to BR-28 (25 confirmed, 3 inferred or needing clarification) – [TASK 4](PART01_REQUIREMENTS_AND_RTM.md#task-4-business-rules).
- **Documentation gaps:** five places where project documentation contradicts the code. Tests follow the code – [§7](PART01_REQUIREMENTS_AND_RTM.md#7-observations-and-documentation-gaps).

---

## 4. Non-Functional Requirements

The non-functional requirements (NFRs) are described in [PART01_REQUIREMENTS_AND_RTM.md – TASK 2](PART01_REQUIREMENTS_AND_RTM.md#task-2-non-functional-requirements). The table below summarises them.

The "design features found in code" column lists mechanisms that exist in the source code. **None of these attributes has been tested, measured or verified.** Their presence in code does not show that the quality requirement is met.

| NFR area | Design features found in code (not yet verified) | What still needs testing | RTM row / test cases |
|---|---|---|---|
| Security (§2.1) | Session authentication with HTTP-only cookie; session ID rotation; CSRF on all writes; BCrypt cost 12; generic login error; server-side role checks; owner-scoped queries; image hardening; public data minimisation; restricted CORS | Access control (IDOR) on every `/{id}` route; XSS payloads; CSRF enforcement; error leakage. **[I]** no login rate limiting; **[I]** registration reveals existing emails; `Secure` cookie default is `false` | NFR-SEC: API-05, API-09, API-22, API-23, UT-12 |
| Performance (§2.2) | Page-size caps (100 and 24); database indexes; upload size limits | **No response-time targets are documented.** **[I]** N+1 queries on the public feed; five summary queries per list call; unindexed `LIKE` search; BCrypt cost under load | **No RTM row and no test case.** A row is to be added when targets and a JMeter test case exist |
| Reliability (§2.3) | Transactions on writes; compensating file delete; after-commit clean-up on delete; duplicate registration race handled; database CHECK constraints | Frontend behaviour when the backend is down; **[I]** concurrent duplicate image upload; **[I]** orphan-file window; unwritable upload directory | NFR-REL: MT-19, API-23, API-24 |
| Usability and accessibility (§2.4) | `role="alert"`/`aria-live` messages; keyboard-operable account menu; `prefers-reduced-motion`; loading and empty states | WCAG 2.1 AA walkthrough; responsive layouts; **[I]** edit-page category fallback; **[I]** My Reports loading message; silent removal of public visibility on edit | NFR-USA: MT-16, UI-14 |
| Maintainability (§2.5) | Feature-based packages; Java records; Flyway; ESLint; 72 backend test methods *[Corrected: Phase 3B; was 73]*; READMEs | No frontend tests; duplicated request helpers; two image validators; dense code; outdated documentation | **No RTM row.** Planned through static analysis (SonarQube, assignment 3.9) |
| Compatibility (§2.6) | Configurable API base URL (`VITE_API_BASE_URL`) and CORS origin (`PUBLICCARE_CORS_ALLOWED_ORIGIN`) | Cross-browser and mobile widths; SameSite cookies across sites; H2 vs PostgreSQL differences; **[I]** IPv6 loopback check | NFR-COMP: MT-17, API-22. Related cases traced under FR rows: UT-14 (IPv6, FR-06.9), INT-01 (PostgreSQL, FR-09.2) |
| Functional correctness (§2.7) | Status enum in Java and database; database-generated reference; shared filter for list and summary | Summary totals vs list totals; sort and paging edges; history order by audience; duplicate history rows on same-status updates; empty note stored as `""` | NFR-FUNC: API-14, API-17, API-19, API-25, UI-08, UT-04, UT-16. Two items (duplicate history rows, empty note) still have no test case |

The untraced items above are listed in the RTM document under [Remaining untraced or ambiguous items](PART01_REQUIREMENTS_AND_RTM.md#remaining-untraced-or-ambiguous-items).

---

## 5. ISO/IEC 25010 Quality Attributes

### 5.1 Quality model

The assignment asks the group to state the quality model and attributes it targets (section 3.1). This plan uses the **product quality model of ISO/IEC 25010** as a reference framework for choosing and grouping test targets.

- This is a **planning framework only**. ISO/IEC 25010 defines quality characteristics; the group uses them as **testing targets**. This document does not claim that PublicCare is certified against, conforms to or complies with ISO/IEC 25010, and no such claim can be made from planned tests.
- The characteristic names follow the commonly taught 2011 edition (for example "Usability" and "Portability"). The 2023 revision renames some characteristics; see Open Questions.

### 5.2 Selected quality attributes

Status for every row: **Planned testing target – not yet verified.** No measurement or test result exists for any attribute.

| ISO/IEC 25010 characteristic | Sub-characteristics in focus | Why it matters for PublicCare | Planned evidence (test cases / activities) | Priority [Proposed] |
|---|---|---|---|---|
| **Functional suitability** | Functional completeness, correctness, appropriateness | The platform exists to record citizen reports and track their status correctly. Errors in status, ownership or approval logic directly mislead citizens | All 58 FRs through the RTM; NFR-FUNC row (cross-cutting correctness checks); MT, UI, API and UT cases; EP, BVA and decision tables (assignment 3.3) | High |
| **Security** | Confidentiality, integrity, authenticity, accountability | The system holds personal data (names, emails) and private reports. Citizens must see only their own reports, and only approved, minimised data may become public | NFR-SEC row; API-09 (authorisation matrix); API-15 (IDOR); API-05 (CSRF); API-19 (public data minimisation); 1–2 manual OWASP Top 10 checks (assignment 3.7) | High |
| **Reliability** | Fault tolerance, recoverability | Reports and photos must not be lost or half-saved when the backend, database or file storage fails | NFR-REL row (MT-19, API-23, API-24); MT-10; UT-09, UT-13 (transaction and clean-up logic) | Medium |
| **Usability** | Operability, user error protection, accessibility | Citizens of varied ability must be able to submit and follow reports; clear validation messages prevent failed submissions | NFR-USA row: MT-16 (keyboard and screen reader), UI-14. Client-side validation cases traced under FR rows (MT-02, MT-03, MT-09, UI-06) | Medium |
| **Compatibility** | Interoperability | The SPA and the API communicate through cookies, CSRF headers and CORS, which behave differently across browsers and site configurations | NFR-COMP row: API-22 (CORS); MT-17 (browsers and 375 px width) | Medium |
| **Performance efficiency** | Time behaviour, capacity | Public pages and lists will slow down as data grows (N+1 queries, unindexed search). **No targets exist yet, so no pass criterion can be written** | Basic JMeter test on one endpoint (assignment 3.7), after the group defines response-time targets | Medium (blocked until targets are set) |
| **Maintainability** | Modularity, analysability, testability | The group will scan the code with SonarQube and must suggest improvements; low testability (no frontend test runner) limits coverage | SonarQube metrics: coverage, code smells, technical debt (assignment 3.9); white-box analysis (assignment 3.4) | Medium |
| Portability | Adaptability | Configuration through environment variables exists, but no deployment environment is documented | Not a primary target [Proposed]. In ISO/IEC 25010, running in different browsers and screen sizes is closest to *adaptability*, so MT-17 also gives some evidence here (it stays traced under NFR-COMP in the RTM). Installability is not targeted | Low |

### 5.3 Planned targets versus verified results

| | Planned testing target | Verified quality result |
|---|---|---|
| Meaning | A characteristic the group intends to test, with the test cases that will provide evidence | A conclusion supported by executed tests or measured metrics, with dated evidence |
| Current state (25 Nov 2026) | All attributes in §5.2 | **Partly verified, at API and unit level, on H2.** *Functional suitability:* 41 executed Part 01 cases cover registration, login and session, report creation and editing, image upload, status and progress updates, public approval, the public feed, deletion, the admin summary and the Flyway schema (INT-01); none failed. *Security:* CSRF (API-05), role checks on all 12 admin endpoints (API-09), CORS (API-22), error-message leakage (API-23), path traversal (UT-12) and data minimisation of the public feed (API-19) passed. *Reliability:* API-23 passed; API-24 found defect PC-QA-02 (a concurrent upload returns `500`). *Usability and compatibility* are **not** verified by Part 01 cases (MT-16, MT-17 and the UI cases on other browsers were not executed). Coverage, SonarQube and JMeter values from member copies are reported in 3.4, 3.7 and 3.9 |
| When it changes | – | After test execution (STLC phase 4) and test closure (phase 5), using actual results and SonarQube or JMeter output |

---

## 6. Verification and Validation

- **Verification** asks: *"Are we building the product right?"* It checks a work product (requirements, design, code, documents, test cases) against its **specification**. It can be static (reviews, static analysis) or dynamic (unit, integration and API tests that compare behaviour with a stated requirement).
- **Validation** asks: *"Are we building the right product?"* It checks, by using the running system in realistic scenarios, that PublicCare meets the **real needs and intended use** of citizens and administrators.

The difference is the reference used, not only whether the code is run: verification compares with what was specified; validation compares with what users actually need.

| | Verification | Validation |
|---|---|---|
| Question | Built right? | Right product? |
| Reference | Requirements, design, specifications (FRs, BRs, RTM) | User needs and intended use |
| Typical methods | Reviews, traceability checks, static analysis, unit/integration/API tests | End-to-end workflows, scenario and acceptance (BDD) tests, usability walkthroughs, confirming intent with the project owner |
| Typical timing | Throughout development, from early documents onwards | Mainly on a working system |

### 6.1 Verification activities

| # | Activity | PublicCare example | Status |
|---|---|---|---|
| V-1 | Static review of source code, migrations and documentation | The read-only requirement analysis that produced the two source documents. It found five places where documentation contradicts the code (for example, AUTH_LOCAL_VERIFICATION.md says all users land on `/my-reports`, but admins go to `/admin`) | Done for Part 01 (documented in the source reports) |
| V-2 | Traceability verification of the RTM | Checking that every FR has an RTM row and every test case ID is defined and traced. Result recorded in the RTM document: 58/58 FRs, 79/79 test cases traced. The Part 01 review also checked that each link matches the test case's scenario and corrected the mis-mapped links | Done as a document consistency check. It does not show that any requirement works |
| V-3 | Peer review of test cases | Group members review the full-format test cases (§10) for correct preconditions, steps and expected results before execution | Not done as planned: the owner instructed integration (Phase 7) to finish without further member reviews. Phase 11 executed the expected results as written; no expected result was changed after execution *[Phase 11; was: Planned]* |
| V-4 | Static code analysis | SonarQube scan for code smells, duplication and technical debt (assignment 3.9); ESLint on the frontend | Done in the Member 4 copy (SonarQube 25.3, Section 3.9); ESLint run in the Member 3 copy *[Phase 11; was: Planned]* |
| V-5 | Unit testing | JUnit 5 + Mockito tests of service logic, for example `ReportService.findPage` paging limits (UT-04) and `PrivateImageStorage.resolve` path-traversal guard (UT-12) | Done (Phase 11, 25 Nov 2026): UT-01 – UT-15 executed in the group repository; UT-16, UT-17 BLOCKED *[Phase 11; was: Planned]* |
| V-6 | White-box analysis | Cyclomatic complexity and statement/branch coverage for a core function (assignment 3.4). Candidate: image validation in `ReportImageService` [Proposed] | Done in the Member 2 copy (Section 3.4) *[Phase 11; was: Planned]* |
| V-7 | Integration testing | INT-01: Flyway migrations V1–V5 apply correctly on PostgreSQL and Hibernate creates nothing | Done in part (Phase 8A): INT-01 (a), (b), (d) PASS on PostgreSQL 18.6; (c) BLOCKED *[Phase 11; was: Planned [Proposed environment]]* |
| V-8 | API contract checks against requirements | API-01 to API-25 compare status codes and response fields with the FR definitions, for example registration never returns a password hash (API-02) | Done (25 Nov 2026): API-01 – API-25 executed in the group repository (API-04 Phase 3A; the rest Phase 11) *[Phase 11; was: Planned]* |

### 6.2 Validation activities

| # | Activity | PublicCare example | Status |
|---|---|---|---|
| VA-1 | End-to-end manual testing of user workflows | Citizen registers, signs in, creates a report with a photo and sees reference `PC-000000NN` (MT-01, MT-05, MT-08); admin adds an IN_PROGRESS update with a note (MT-12) and the citizen sees it (MT-20) | Not executed (manual cases). The same workflows were exercised at API level in Phase 11 (API-02, API-10, API-17); that is verification, not user validation *[Phase 11; was: Planned]* |
| VA-2 | Role-based scenario testing | Public visibility flow: admin approves report, update and note, and each appears on the public feed; revoking removes it (MT-13) | Not executed manually (MT-13). The approval rules were checked at API level (API-18, API-19) *[Phase 11; was: Planned]* |
| VA-3 | Acceptance-style BDD scenarios | 1–2 Gherkin scenarios for a key workflow, for example "Citizen submits a report" (assignment 3.5) | Done in the Member 3 copy: 4 Cucumber scenarios (Section 3.5) *[Phase 11; was: Planned]* |
| VA-4 | Usability and accessibility walkthrough | Keyboard-only and screen-reader use of the main pages (MT-16) | Not executed (MT-16). Member 4 ran scripted usability walkthroughs without participants (Section 3.7) *[Phase 11; was: Planned]* |
| VA-5 | Compatibility checks | Main flows in the browsers and widths that the group confirms (MT-17) | Not executed (MT-17). Chrome 157 (Member 3) and Chromium 153 (Member 4) only *[Phase 11; was: Planned [browsers TBC]]* |
| VA-6 | Confirmation of intended behaviour with the project owner or lecturer | Is it intended that citizens can edit RESOLVED reports (BR-09) and that any status can move to any status (BR-13)? | Open – RTM Q1 not answered. Phase 11 recorded the observed behaviour (API-16, API-18, API-24, UT-14) for that decision *[Phase 11; was: Planned – open question RTM Q1]* |

---

## 7. Software Test Plan

### 7.1 Test objectives

1. Check that the 58 functional requirements behave as described in [TASK 1](PART01_REQUIREMENTS_AND_RTM.md#task-1-functional-requirements) for Anonymous, Citizen and Admin users.
2. Check that server-side access control protects every citizen and admin route, including cross-user access (IDOR) and CSRF protection.
3. Check that public visibility rules expose only approved and minimised data (FR-07, BR-15 to BR-22).
4. Check that the system handles invalid input, boundary values and failures (backend down, bad images, concurrent uploads) without data loss or information leakage.
5. Gather evidence for the ISO/IEC 25010 targets in §5.
6. Keep two-way traceability between requirements and test cases, and record actual results and defects during execution.
7. Confirm or reject the inferred **[I]** behaviours and record the outcome.

### 7.2 Test scope

The scope is defined in §2. In short: all 58 functional requirements in modules FR-01 to FR-09 and the NFR areas Security, Reliability, Usability, Compatibility and Functional correctness are in scope. Performance and Maintainability are in scope through later assignment parts (3.7 and 3.9), with performance blocked until targets are defined.

### 7.3 In-scope activities

| Activity | Assignment section | Status | As executed (25 Nov 2026) *[As executed: Phase 5A, applied 25 Nov 2026]* |
|---|---|---|---|
| Requirement analysis and RTM | 3.1 / 3.2 | Done (source documents) | – |
| Test planning (this document) | 3.1 | Draft | – |
| Writing test cases in the full format | 3.2 | All 79 written (§10.4); peer review (V-3) pending | – |
| Defining the test environment and test data | 3.2 | Draft; many items TBC (§11, §12) | – |
| Black-box design: EP, BVA, decision table | 3.3 | Planned | Done in the Member 2 copy (24 Nov 2026). DT-05 and DT-06 not executed |
| White-box design: cyclomatic complexity, statement and branch coverage | 3.4 | Planned | Done in the Member 2 copy (24 Nov 2026). Path B1 not executed |
| Unit tests (JUnit) and 1–2 BDD scenarios (Cucumber) | 3.5 | Planned | Done in the Member 3 copy (24 Nov 2026): TDD on UT-14; 4 Cucumber scenarios |
| UI or API automation and a CI workflow on push | 3.6 | Planned. No CI workflow file is tracked in the repository at baseline `f565fb4` (no `.github/` or `Jenkinsfile`); adding one needs approval (§7.11) | Done in the Member 3 repository (24 Nov 2026). Selenium CI job not executed; CI not active in the group repository |
| Basic JMeter load test and 1–2 manual OWASP Top 10 checks | 3.7 | Planned | Done in the Member 4 copy (24 Nov 2026). JMeter baseline measured, no pass/fail target (OQ-4); 2 OWASP access checks passed |
| Defect logging, root cause analysis and one full bug life cycle | 3.8 | Planned | 1 defect logged (PC-QA-01, Member 4 copy, 24 Nov 2026). Closed in the Member 4 copy only; still open in the group baseline `f565fb4` (owner decision D-3 pending) |
| SonarQube scan, metrics and improvement proposals | 3.9 | Planned | Done in the Member 4 copy + `f78deb1` (24 Nov 2026); quality gate passed |
| Reflection on Agile/DevOps practices | 3.10 | Planned | Done: group-level 3.10 reflection in the integrated Member 4 revision; Part 01 test summary report in [SECTION_3.1-3.2_FINAL_REPORT.md](SECTION_3.1-3.2_FINAL_REPORT.md#327-test-summary-and-closure) §3.2.7 |

### 7.4 Out-of-scope activities

See §2.2. In summary: changing the application or fixing its defects (§7.11), testing on production systems or with real personal data, testing third-party framework internals, full penetration or large-scale load testing, formal accessibility certification, and features that do not exist (user deletion, photo replacement, web-based admin creation, native mobile apps).

### 7.5 Test strategy and test levels

The strategy is **risk-based and requirement-based**. High-priority requirements and security-critical behaviour are tested first and at more than one level. Expected results follow the **code**, not the outdated project documentation, as recommended in the analysis (§7 of the RTM document).

| Test level | What is tested | Test cases | Tools |
|---|---|---|---|
| Unit | Individual backend services and helpers; frontend helpers | UT-01 – UT-17 | JUnit 5 + Mockito (present in the project). Vitest for UT-16/UT-17 (**not installed**) |
| Integration | Database migrations and persistence with real PostgreSQL | INT-01 | Testcontainers or a dedicated local PostgreSQL database [Proposed]; Testcontainers is not a project dependency |
| System – API | REST endpoints through HTTP, including security and boundaries | API-01 – API-25 | REST Assured or Postman + Newman [Proposed; not installed] |
| System – UI | Browser workflows | UI-01 – UI-14 (automated), MT-01 – MT-22 (manual) | Playwright or Selenium [Proposed; not installed] |
| Acceptance | Key end-to-end user workflows from the user's point of view | Selected MT cases and 1–2 BDD scenarios | Cucumber [Proposed; not installed] |

**Test types and techniques**

| Type / technique | PublicCare application |
|---|---|
| Positive and negative testing | Each test case is labelled P, N or B in the inventory |
| Equivalence Partitioning (EP) | Image types (valid JPG/PNG/WebP vs GIF vs renamed text file); report status filter values |
| Boundary Value Analysis (BVA) | Password 11/12/72/73 characters and 72/73 UTF-8 bytes; title 160/161; image 5,242,880/5,242,881 bytes; page size 0/1/100/101 |
| Decision table | Public visibility of a note (report approved × update approved × note approved, BR-16); authorisation matrix (Anonymous × Citizen × Admin, API-09) |
| State transition | Report status PENDING → IN_PROGRESS → RESOLVED and approval revocation on status change (FR-07.7). Transition rules need clarification (BR-13) |
| Error guessing | Script payloads in text fields; renamed files; concurrent uploads |
| Security testing | CSRF, IDOR, CORS, error leakage, manual OWASP Top 10 checks |
| Regression testing | Automated API/UI tests run in CI on every push (assignment 3.6) [Proposed] |

**Entry criteria [Proposed]**

- Code baseline is fixed and recorded (currently `f565fb4`).
- Test environment is set up as described in §11, with no TBC item blocking the test case.
- Test data from §12 is prepared.
- The test case has been peer-reviewed and has an agreed expected result. Cases that depend on open clarifications (RTM Q1, Q7, Q8) stay blocked until resolved.
- Any change to the application that a test case needs (for example a new test dependency) has been approved by the project owner (§7.11).

**Exit criteria [Proposed – to be agreed by the group]**

- All High-priority test cases have been executed, with actual results recorded.
- Every failed test case has a logged defect.
- No open defect of the highest severity, or the group has accepted it in writing with a reason. (The severity scale is not yet defined; it is set with the defect process in assignment 3.8.)
- The RTM shows the execution status of every requirement.
- A test summary report is written (STLC phase 5).

**Suspension and resumption [Proposed]**

Testing of an area is suspended when a blocking defect prevents its main workflow (for example, login fails) or when the environment is unavailable. It resumes when a fix is available on a recorded baseline and the affected tests are re-run.

### 7.6 Resources and responsibilities

Team members, their availability and their responsibilities are **not stated in the source documents**. The tables below use placeholders. The RTM document proposes three workstreams "for the group to decide"; they are repeated here as a proposal only.

The structure has **three test workstreams**. Test coordination and defect/metrics work are **cross-cutting duties, not extra workstreams**: each is held by a member of one of the three workstreams. The project owner is listed separately, because some decisions need the owner's approval (§7.11); this is not a QA workstream.

**Test workstreams [Proposed – names and allocation TBC]** – *Original proposal, 23 Nov 2026 – not adopted* *[As executed: Phase 5A, applied 25 Nov 2026; see "Work allocation as executed" below]*

| Workstream | Test cases | Count | Other activities | Assigned member(s) |
|---|---|:-:|---|---|
| WS-1 Manual and automated UI | MT-01–MT-22, UI-01–UI-14 | 36 | Browser, mobile-width and accessibility checks | TBC |
| WS-2 Automated API and integration | API-01–API-25, INT-01 | 26 | JMeter load test and manual OWASP checks (assignment 3.7) | TBC |
| WS-3 Unit and clarifications | UT-01–UT-17 | 17 | White-box analysis (assignment 3.4); owns open questions RTM Q1 and Q5 | TBC |
| **Total** | | **79** | | |

**Cross-cutting duties [Proposed – each held by a workstream member, TBC]** – *Original proposal, 23 Nov 2026 – not adopted* *[As executed: Phase 5A, applied 25 Nov 2026; see "Work allocation as executed" below]*

| Duty | Responsibilities | Held by |
|---|---|---|
| Test coordination | Test plan, schedule, RTM updates, test summary report | TBC (a member of WS-1, WS-2 or WS-3) |
| Defect tracking and quality metrics | Defect log, root cause analysis and bug life cycle (assignment 3.8); SonarQube analysis (assignment 3.9) | TBC (a member of WS-1, WS-2 or WS-3) |

**Approval role (outside the QA workstreams)**

| Role | Responsibility | Person |
|---|---|---|
| Project owner (PublicCare developer) | Approves or refuses any change to the original application (§7.11). Possible contact for questions on intended behaviour (RTM Q1, still open) | TBC |

**Work allocation as executed** *[As executed: Phase 5A, applied 25 Nov 2026]*

The three proposed workstreams above were not used. The work was divided by assignment section among four members (confirmation register C-01, from `qa/README.md`). Names and registration numbers are **PENDING**: they are not filled in until each person confirms them. Name forms that already appear in project records are listed for confirmation in the confirmation register §3 and are not repeated here. *[Phase 9, 25 Nov 2026: names and registration numbers supplied by the project owner, entered below in the exact form given; the owner confirmed which person is Member 1, 2, 3 and 4. Register Revision 7. Earlier value of each cell: PENDING]*

| Member | Name | Registration number | Assignment sections | Part 01 cases actually exercised (own copy) | Evidence folder |
|---|---|---|---|---|---|
| Member 1 | Madusanka D.M.C | EG/2023/5720 | 3.1–3.2: requirement analysis, RTM, test plan, STLC, 79 test cases | None executed by Member 1 personally. 41 cases were executed in the group repository during integration (API-04, INT-01 and 39 cases in Phase 11) | `qa/3.1-3.2_member1/` |
| Member 2 | Dharmasiri S.O.R | EG/2023/5559 | 3.3–3.4: EP, BVA, decision table, CFG, cyclomatic complexity, coverage | None as written. Own IDs EP/BVA/DT/A/B | `qa/3.3-3.4_member2/` |
| Member 3 | Kumaranayaka K.I.S | EG/2023/5697 | 3.5–3.6: TDD, BDD, REST Assured, Selenium, GitHub Actions | 8 full and 7 partial, in the Member 3 copy only (As Executed Addendum, AE-C.1) | `qa/3.5-3.6_member3/` |
| Member 4 | Madushan S.C | EG/2023/5716 | 3.7–3.10: JMeter, OWASP checks, usability walkthrough, defect tracking and RCA, SonarQube and metrics, case study | Related checks only (As Executed Addendum, AE-C.2) | `qa/3.7-3.10_member4/` |

Co-authors of Part 01, if any: PENDING (P-M1-3).

**Cross-cutting duties, as executed:**

| Duty | Held by | Basis |
|---|---|---|
| Defect tracking and quality metrics | Member 4 – Madushan S.C, EG/2023/5716 *[Phase 9: was "name form PENDING, P-M4-12"]* | Sections 3.8 and 3.9 are in Member 4's report (register C-09) |
| Test coordination (plan, schedule, RTM updates, summary report) | **Not recorded** – PENDING (P-OW-3) | Integration phases were carried out on branch `qa/final-assignment`, but the documents do not name the person who did them. No test summary report exists yet |
| Project owner / approvals | Project owner – name PENDING (P-OW-1). Whether the owner is also one of Members 1–4: PENDING (P-OW-2) | Owner decisions D-1 and D-8 are recorded in the RTM "Owner decisions" table |

**How the original workstreams map to the actual work** (an explanation, not a correction of the proposal):

| Proposed workstream | What actually happened |
|---|---|
| WS-1 Manual and automated UI (MT-01–MT-22, UI-01–UI-14) | No MT case executed. UI-01–UI-05 and UI-13 automated by Member 3 in the Member 3 copy (Selenium, local only). UI-06–UI-12 and UI-14 not executed |
| WS-2 API and integration + JMeter/OWASP | API cases partly automated by Member 3 in the Member 3 copy. All 25 API cases executed in the group repository during integration (API-04 in Phase 3A; the other 24 in Phase 11). JMeter and OWASP checks by Member 4. INT-01 not executed *[Phase 8A, 25 Nov 2026: INT-01 partly executed by the project owner on a dedicated local PostgreSQL database – (a), (b), (d) PASS; (c) BLOCKED (AE-B)]* |
| WS-3 Unit and white-box | White-box by Member 2. UT-14 by Member 3 (TDD, Member 3 copy). UT-01 – UT-15 executed in the group repository in Phase 11; UT-16 and UT-17 BLOCKED (Vitest) |

**Tool and software resources**

| Resource | Status |
|---|---|
| JUnit 5, Mockito, Spring Boot test slices, H2 | Present in the project (confirmed) |
| ESLint | Configured in the project (confirmed) |
| Playwright or Selenium; REST Assured or Postman + Newman; Vitest; Testcontainers; Cucumber; JMeter; SonarQube; GitHub Actions or Jenkins; Jira, Bugzilla or GitHub Issues | **Not confirmed as installed or available.** Selection and availability TBC. A tool that would be added to the application repository needs the project owner's approval (§7.11) |
| Test machines, operating systems, browsers and devices | TBC (§11) *[As executed: Phase 5A, applied 25 Nov 2026: see the "As executed" column in §11.1]* |

**Tools as executed** *[As executed: Phase 5A, applied 25 Nov 2026]*

Versions are as recorded in member or integration evidence. The table above is kept as the original plan. Tools used only in member copies are **not** part of the group repository (for example, `backend/pom.xml` has no Cucumber, REST Assured or Selenium dependency; bringing member test code in is owner decision D-5, pending).

| Tool | Used? | Version (as recorded) | Used by | Evidence |
|---|---|---|---|---|
| JUnit 5, Mockito, Spring Boot test slices, H2 | Yes | Spring Boot 3.5.16; JUnit Platform 1.12.2 | All | `backend/pom.xml`; M3 `bdd/README.md` |
| Part 01 execution harness *[Phase 11]* | Yes | JUnit 5 + Mockito; `@SpringBootTest(RANDOM_PORT)` (embedded Tomcat) driven by `java.net.http.HttpClient` with one cookie jar per user. **No new dependency** | Integration | [`qa/phase11-part01-exec/test-code/`](../phase11-part01-exec/test-code/) |
| JaCoCo | Yes | 0.8.12 (M2); 0.8.13 (M4) | M2, M4 | PART02 §1; M4 `REPRODUCE.md` |
| Cucumber-JVM | Yes | 7.23.0 | M3 | M3 `bdd/README.md` |
| REST Assured | Yes | 5.5.7 | M3 | M3 `api-automation/README.md` |
| Selenium | Yes, Member 3 copy only (local; no CI result) | 4.31.0; ChromeDriver 157.0.8103.78 | M3 | M3 `ui-automation/README.md` |
| Playwright | Yes (browser scripts) | **Version not recorded** | M4 | M4 `REPRODUCE.md` (Browser checks) |
| GitHub Actions | Yes, in the M3 repository only (not active in the group repository) | `actions/checkout@v4`, `setup-java@v4`, `setup-node@v4`, `upload-artifact@v4` | M3 | `reference-code/github-workflows/ci.yml` |
| JMeter | Yes | 5.6.3 | M4 | M4 `REPRODUCE.md` line 3 |
| SonarQube / SonarScanner | Yes | Community Build / Server 25.3.0.104237; SonarScanner CLI 6.2.1.4610 | M4 | `sonar-run.txt`; `REPRODUCE.md` line 3 |
| GitHub Issues (defect tracker) | Yes | – (issue #1 in the M4 repository) | M4 | `MEMBER4_QA_REPORT.md` line 44 |
| IntelliJ IDEA | Yes | 2026.3 | M2 | PART02 line 14 (member statement) |
| Postman/Newman, Vitest, Testcontainers, Jenkins, Jira, Bugzilla | **Not used** | – | – | No evidence of use |

### 7.7 Test environment

See §11.

### 7.8 Test schedule

No dates are given in the assignment PDF or the source documents. The schedule below shows the **planned order of work**. Dates and durations are placeholders for the group to complete.

*[As executed: Phase 5A, applied 25 Nov 2026]* The last two columns record what actually happened, using only dates found in project records (confirmation register C-07). The planned columns are kept as written and stay **TBC**: no planned date is recorded anywhere, and planned dates must **not** be back-filled from actual dates (register P-SC-1, REJ-2). The final submission deadline is not in any project record (P-SC-2).

| # | Phase / activity | Assignment section | Planned start | Planned end | Status | Actual date(s) | Actual status (25 Nov 2026) |
|---|---|---|---|---|---|---|---|
| 1 | Requirement analysis and RTM | 3.1 / 3.2 | – | – | Done (source documents) | 23 Nov 2026 | Done |
| 2 | Test planning, scope, quality attributes, V&V | 3.1 | TBC | TBC | Draft (this document) | 23 Nov 2026; integration revisions 25 Nov 2026 | Draft; group review pending |
| 3 | Test case design in full format; environment and test data set-up | 3.2 | TBC | TBC | In progress | 23 Nov 2026 | 79 cases written; peer review (V-3) pending |
| 4 | Black-box and white-box test design | 3.3, 3.4 | TBC | TBC | Not started | 24 Nov 2026 (M2 copy) | Done in M2 copy; DT-05, DT-06, B1 not executed |
| 5 | Unit tests, BDD scenarios | 3.5 | TBC | TBC | Not started | 24 Nov 2026 (M3 copy) | Done in M3 copy (TDD on UT-14; 4 Cucumber scenarios) |
| 6 | Test execution (manual, API, UI, integration) and defect logging | 3.2, 3.8 | TBC | TBC | Not started | API-04, INT-01 and Phase 11: 25 Nov 2026. PC-QA-01 logged 24 Nov 2026 (M4 copy); PC-QA-02 found 25 Nov 2026 (Phase 11) | Part 01: 41 of 79 executed in the group repository (§10.4); 6 more in the Member 3 copy only; 32 not executed. Member runs reported separately |
| 7 | Automation and CI pipeline | 3.6 | TBC | TBC | Not started | 24 Nov 2026 (M3 repository) | Done in M3 repository; Selenium CI job not executed; CI not active in group repository |
| 8 | Performance and security checks | 3.7 | TBC | TBC | Not started (performance blocked until targets are set) | 24 Nov 2026 (M4 copy) | Baseline measured, no pass/fail target (OQ-4); 2 OWASP access checks passed |
| 9 | SonarQube analysis and quality metrics | 3.9 | TBC | TBC | Not started | 24 Nov 2026 (M4 copy + `f78deb1`) | Done; quality gate passed |
| 10 | Test closure: summary report, Agile/DevOps reflection | 3.10 | TBC | TBC | Not started | 24 Nov 2026 (M4 §3.10) | Done: group-level 3.10 reflection in the integrated Member 4 revision; Part 01 test summary report in [SECTION_3.1-3.2_FINAL_REPORT.md](SECTION_3.1-3.2_FINAL_REPORT.md#327-test-summary-and-closure) §3.2.7 |
| – | Integration (not in the original plan) | – | – | – | – | 25 Nov 2026 | In progress (integration phases 1–5B and the application of these corrections); backend suite of the integration working tree run on Java 27 (Phase 3A) and on Java 21 (85/0, H2); nothing committed |
| – | Final submission deadline | – | – | TBC | – | – | **TBC – not in the project records. Take it from the module brief or LMS** (P-SC-2) |

### 7.9 Test deliverables

| Deliverable | Status |
|---|---|
| Requirement analysis and RTM ([PART01_REQUIREMENTS_AND_RTM.md](PART01_REQUIREMENTS_AND_RTM.md)) | Exists |
| Test plan and STLC mapping (this document) | Draft |
| Full-format test cases with actual results | All 79 cases in §10.4. Actual results recorded for the 41 executed cases and for the 6 Member 3 copy cases; the other 32 are marked NOT EXECUTED or BLOCKED with the reason |
| Test data set and environment notes | Draft (§11, §12) |
| Defect reports, RCA, bug life cycle | Not started *[As executed: Phase 5A, applied 25 Nov 2026: 1 defect (PC-QA-01), Member 4. Phase 11: PC-QA-02 (concurrent image upload returns `500`), found by API-24]* |
| Automation scripts, CI workflow, pipeline screenshots | Not started *[As executed: Phase 5A, applied 25 Nov 2026: in the Member 3 repository (not the group repository)]* |
| JMeter, OWASP and SonarQube results | Not started *[As executed: Phase 5A, applied 25 Nov 2026: Member 4]* |
| Test summary report | Done for Part 01 – [SECTION_3.1-3.2_FINAL_REPORT.md](SECTION_3.1-3.2_FINAL_REPORT.md#327-test-summary-and-closure) §3.2.7 |

### 7.10 Main testing risks

| Risk | Effect on testing | Mitigation [Proposed] |
|---|---|---|
| Intended behaviour unclear for BR-09, BR-13, BR-22 and the **[I]** items | Expected results cannot be fixed | Ask the project owner or lecturer (RTM Q1); keep the affected cases blocked until answered |
| No response-time targets | Performance cannot pass or fail | Group defines targets before the JMeter test |
| No frontend test runner | Frontend helper logic has no unit-test coverage | Add Vitest only with the project owner's explicit approval (§7.11). Until then, UT-16 and UT-17 stay blocked |
| Approval for an application change is not given | Test cases or activities that need a change (§7.11) cannot run | Ask for approval early; prefer options that need no change (for example a dedicated local PostgreSQL database for INT-01) |
| H2 used in existing JPA tests | PostgreSQL-specific behaviour is not covered | INT-01 on real PostgreSQL |
| Project documentation contradicts the code | Wrong expected results | Follow the code, as recommended; record the gaps |
| Tool availability not confirmed | Automation and analysis may be delayed | Confirm tools early (§7.6) |
| Team members, allocation and dates not yet agreed (OQ-1, OQ-2) | Work may be left unassigned or done twice; the schedule cannot be tracked | Agree the §7.6 allocation and the §7.8 dates before execution starts |
| Code changes after baseline `f565fb4` | Line-level evidence and expected results may become outdated | Record the baseline for every execution; re-check the RTM if the code changes |

### 7.11 Application ownership and source protection

PublicCare is a private project owned by its developer (the project owner). Other group members are authorised to carry out QA activities only. They are not authorised to modify the original application.

> **Any changes to the original application source code, configuration files, dependencies, database schema or CI/CD workflows require explicit approval from the project owner. QA activities must not modify the original application repository without such approval.**

The QA documents are kept in `qa/`, outside the application folder `publiccare/`. The planned items below would change the application, so each one needs approval before it starts.

| Planned item that would change the application | Status |
|---|---|
| New unit tests, BDD scenarios or automation scripts placed in the application repository (assignment 3.5, 3.6) | Needs approval. Where QA test code is kept is an open question (OQ-12) |
| A GitHub Actions or Jenkins workflow in the application repository (assignment 3.6) | Needs approval (OQ-10, OQ-12) |
| Adding Vitest, and exporting `downloadCsv`, for UT-16 and UT-17 | Needs approval; blocked until approved |
| Refactoring `AdminSetupRunner.isLoopbackPostgres` for UT-14 | Needs approval if a refactor is chosen instead of reflection |
| Adding Testcontainers for INT-01 (RTM Q3) | Needs approval. A dedicated local PostgreSQL database needs no change |
| Fixing defects found during testing | Out of scope for QA (§2.2). Defects are reported to the project owner |

Changing the **test environment** (for example making the upload directory unwritable for API-23, or stopping the backend for MT-19) is not a change to the application repository. It must still be done only in a disposable test environment (§11, §12).

---

## 8. Software Testing Life Cycle (STLC)

The assignment asks the group to map the STLC phases to the project: requirement analysis → test planning → test case design → execution → closure. The table gives the current state of each phase, then each phase is described.

| # | Phase | Current state |
|---|---|---|
| 1 | Requirement Analysis | Deliverables produced (source documents) |
| 2 | Test Planning | Draft – this document |
| 3 | Test Case Design | Test cases written – all 79 in the full format (§10.4); peer review pending; some expected results blocked pending confirmation |
| 4 | Test Execution | **Executed for 41 of 79 cases** in the group repository: API-04 (Phase 3A), INT-01 (Phase 8A) and 39 API and unit cases (Phase 11), all on 25 Nov 2026. Not executed: 22 manual, 8 UI, 2 Vitest (blocked). Member sections were executed in member copies (Sections 3.3–3.10; As Executed Addendum) |
| 5 | Test Closure | **Done for Part 01**: test summary and exit-criteria assessment in [SECTION_3.1-3.2_FINAL_REPORT.md](SECTION_3.1-3.2_FINAL_REPORT.md#327-test-summary-and-closure) §3.2.7. Exit criteria only partly met (32 cases not executed; PC-QA-02 open) |

### 8.1 Phase 1 – Requirement Analysis

**Activities in PublicCare**

- Read-only review of the source code, database migrations, READMEs and existing test classes at baseline `f565fb4`.
- Identification of 58 functional requirements, NFR areas, user roles and 28 business rules.
- Labelling each item by evidence (C, C+D, I, C / I) to separate confirmed from inferred requirements.
- Recording documentation gaps and items that need clarification.

**Deliverables**

- [PROJECT_ANALYSIS_REPORT.md](PROJECT_ANALYSIS_REPORT.md) (archived analysis).
- [PART01_REQUIREMENTS_AND_RTM.md](PART01_REQUIREMENTS_AND_RTM.md) (corrected working copy with the RTM).
- List of open questions (RTM Q1–Q8).

### 8.2 Phase 2 – Test Planning

**Activities in PublicCare**

- Define scope, objectives, in-scope and out-of-scope activities.
- Select the ISO/IEC 25010 quality attributes to target.
- Choose the test levels, types and techniques; propose tools.
- Plan resources, responsibilities, environment and schedule.
- Identify testing risks and entry/exit criteria.

**Deliverables**

- Software Test Plan (this document, §1–§7).
- Proposed workstreams and schedule (placeholders until the group confirms).

### 8.3 Phase 3 – Test Case Design

**Activities in PublicCare**

- Review, validate and refine the 79 test cases already documented in the full format required by the assignment (§10.4).
- Apply EP, BVA and decision tables to selected fields (assignment 3.3); design white-box tests (assignment 3.4).
- Prepare test data (§12) and environment configuration (§11).
- Peer-review test cases and update the RTM.

**Deliverables**

- Full-format test case documents (§10.4), with Actual Result `NOT EXECUTED` until a case is run.
- Test data set description.
- Updated RTM.

### 8.4 Phase 4 – Test Execution

**Activities in PublicCare**

- Set up the environment and seed the test data.
- Run manual, API, UI, unit and integration tests against a recorded code baseline.
- Record the actual result, date, tester, baseline and status for each test case.
- Log each failure as a defect with steps to reproduce, and re-test fixes (regression).
- Run automation in CI, the JMeter test and OWASP checks (later assignment parts).

**Deliverables**

- Executed test cases with actual results and evidence (screenshots, logs, reports).
- Defect reports (Jira, Bugzilla or GitHub Issues – TBC). *[As executed: Phase 5A, applied 25 Nov 2026: GitHub Issues in the Member 4 repository (PC-QA-01, issue #1)]*
- CI pipeline evidence.
- Updated RTM execution status.

### 8.5 Phase 5 – Test Closure

**Activities in PublicCare**

- Check the exit criteria (§7.5).
- Summarise results by requirement group, test level and ISO/IEC 25010 attribute.
- Report quality metrics (coverage, defect density, code smells, technical debt).
- Carry out root cause analysis on significant defects.
- Record lessons learned and the Agile/DevOps reflection (assignment 3.10).

**Deliverables**

- Test summary report.
- Final RTM with execution results.
- Metrics report and improvement proposals.
- Lessons learned.

---

## 9. Requirements Traceability Matrix (RTM) Overview

### 9.1 Purpose

The RTM links each requirement to the test cases that will check it. It is used to:

- show that every requirement has at least one planned test (forward traceability);
- show that every test case exists for a reason (backward traceability);
- find gaps: requirements without tests, or tests without requirements;
- assess the impact when a requirement or the code changes;
- report execution status per requirement during and after testing.

### 9.2 Location

The RTM is in [PART01_REQUIREMENTS_AND_RTM.md – TASK 5: Initial Requirement Traceability Matrix](PART01_REQUIREMENTS_AND_RTM.md#task-5-initial-requirement-traceability-matrix). Its columns are: Req ID, Description, Feature, Testing type, Proposed TC IDs, Priority and Execution status. It is not repeated here.

### 9.3 Current counts (from the corrected working copy)

| Item | Count |
|---|---|
| Functional requirements | **58** (FR-01: 7, FR-02: 9, FR-03: 8, FR-04: 7, FR-05: 4, FR-06: 9, FR-07: 7, FR-08: 5, FR-09: 2) |
| RTM rows | **63** (58 FR rows + NFR-SEC, NFR-REL, NFR-USA, NFR-COMP, NFR-FUNC) |
| Business rules | **28** (25 confirmed, 3 inferred or needing clarification). Not traced directly in the RTM |
| Proposed test cases | **79**: 22 Manual (MT-01–MT-22), 14 Automated UI (UI-01–UI-14), 25 Automated API (API-01–API-25), 17 Unit (UT-01–UT-17), 1 Integration (INT-01) |
| Test cases executed | **41** in the group repository (25 Nov 2026): 33 PASS, 6 PASS with one part BLOCKED, 1 BLOCKED (API-24, defect PC-QA-02), 1 PARTIALLY EXECUTED (INT-01). **6** more (UI-01 – UI-05, UI-13) passed in the Member 3 copy only. **32** not executed (22 MT, 8 UI; UT-16 and UT-17 BLOCKED). Details: §10.4 and the [execution record](../phase11-part01-exec/README.md) |
| Existing backend test methods in the code base (separate from the 79) | **72** in 12 classes *[Corrected: Phase 3B; was 73]*. *[Phase 3A]* Run on this branch on 25 Nov 2026 (Windows 11, Java 27, H2): at `f565fb4` 72 tests, 0 failures; after the D-1 change 85 tests, 0 failures (72 existing + 13 new Phase 3A tests). Not run on Java 21 or PostgreSQL. *[Java 21 run, 25 Nov 2026 (applied 25 Nov 2026): the same 85 tests (`f565fb4` + uncommitted D-1) were later run on Java 21.0.13.1 (Ubuntu OpenJDK, Linux – WSL2, Ubuntu 26.04.1): 85 tests, 0 failures, 0 errors, 0 skipped, H2 in-memory. Still not run on PostgreSQL. Console log / Maven version evidence pending (register ENV-J10)]* Running these tests does not execute any of the 79 proposed cases |

The archived report states 59 requirements and 74 test cases. The first working copy corrected these to 58 and 75. The Part 01 review then split MT-12 (adding MT-20, MT-21, MT-22), added API-25 for the dashboard, split NFR-USA/COMP into NFR-USA and NFR-COMP, and added NFR-FUNC. The figures above are the current ones.

### 9.4 Traceability check results

The RTM document records these **document consistency** checks as passed: 58/58 FRs have an RTM row; 79 test case IDs are each defined once; 79/79 are traced in the RTM; no undefined IDs; each RTM row now shows the execution status of each of its cases (Phase 11 update, 25 Nov 2026). The Part 01 review also checked each link against the test case's scenario and corrected the links that did not match (RTM document, corrections 9–17). These checks do not show that any requirement works.

### 9.5 Known gaps

Performance and Maintainability have no NFR row. Performance has no measurable target or test case yet, and Maintainability is assessed through SonarQube (assignment 3.9). Two Functional correctness items, the orphan-file window, some business rules (BR-10, part of BR-17, BR-18, BR-19) and some **[I]** items have no direct test case. Details: [Remaining untraced or ambiguous items](PART01_REQUIREMENTS_AND_RTM.md#remaining-untraced-or-ambiguous-items).

### 9.6 Maintaining the RTM

When a test case is executed, its RTM status should change from PROPOSED / NOT EXECUTED to the actual outcome, together with the date, the code baseline and any defect ID, as the RTM document already instructs.

---

## 10. Test Case Design and Documentation

### 10.1 Design approach

- **Requirement-based:** every test case traces to at least one FR or NFR row in the RTM.
- **Risk-based priority:** test cases inherit the priority of their requirements; High-priority and security-related requirements are covered at more than one level (for example FR-01.1 by MT-01, UI-01, API-02 and UT-01).
- **Techniques:** positive, negative and boundary cases (labelled P, N, B), EP, BVA, decision tables, state transition and error guessing (see §7.5).
- **Expected results follow the code**, because the project documentation is out of date in five places.
- **Blocked expectations:** where intended behaviour is unclear (BR-09, BR-13, BR-22, **[I]** items), the expected result is marked as needing clarification instead of being guessed.

### 10.2 Existing test case inventory

The 79 proposed test cases are listed in [PART01_REQUIREMENTS_AND_RTM.md – TASK 6: Test Case Candidates](PART01_REQUIREMENTS_AND_RTM.md#task-6-test-case-candidates), sections A–E. They are written in summary form. The table shows how the inventory maps to the format the assignment requires.

| Required field (assignment 3.2) | Source in the existing inventory | Gap |
|---|---|---|
| Test Case ID | `TC ID` column (MT-, UI-, API-, UT-, INT-) | None |
| Description | `Scenario` column (UT: `Unit under test` + `Scenarios`) | None |
| Preconditions | Not in the inventory (INT-01 partly; FR "Preconditions" column in TASK 1) | Added in §10.4 |
| Test Steps | Not in the inventory (only INT-01 has steps) | Added in §10.4 |
| Expected Result | `Expected result` column for MT, API and INT | Added in §10.4 for UI and UT cases (from the scenario and the requirement text; some parts blocked pending confirmation) |
| Actual Result | Not in the inventory | Added in §10.4: the observed result of each executed case; `NOT EXECUTED` otherwise |
| Status | `Status` column | Updated in §10.4 and in the RTM from execution evidence (vocabulary in §10.3) |

### 10.3 Test case template

| Field | Content |
|---|---|
| Test Case ID | Existing ID from TASK 6 (do not renumber) |
| Requirement ID(s) | FR/NFR IDs from the RTM |
| Description | What is tested and why |
| Type / Priority | P, N or B / High, Medium or Low |
| Preconditions | System state, user role and data needed before the test |
| Test Data | Reference to a test data category in §12 |
| Test Steps | Numbered actions |
| Expected Result | The behaviour required by the requirement |
| Actual Result | `NOT EXECUTED` until the test is run; then the observed behaviour |
| Status | `PROPOSED / NOT EXECUTED` → after execution: `PASS`, `FAIL` or `BLOCKED`. Also used: `PARTIALLY EXECUTED` (some steps not run); `PASS` with one part `BLOCKED` (that part was observed but cannot be judged until the intended behaviour is confirmed); `PASS – Member 3 copy only` (executed outside the group repository); `BLOCKED – not executable` (a tool or approval is missing); `NOT EXECUTED` |
| Execution details | Tester, date, code baseline, environment, test method, evidence and defect ID; for a case not executed, the reason |

**Rules for recording results**

- The Actual Result and Status may change only after the test has really been run, with evidence (screenshot, log or tool report).
- A `FAIL` needs a defect ID. A `BLOCKED` result needs a reason (for example "open question RTM Q1").

### 10.4 Test cases in the full format

All 79 proposed test cases from TASK 6 are written below in the format required by the assignment (§10.3). Eleven of them (MT-01, MT-05, MT-06, MT-08, MT-12, MT-15, MT-18, MT-20, MT-21, MT-22 and API-05) were converted in the earlier revision and are unchanged. The other 68 were converted in this revision. The cases are grouped by test level and listed in ID order. MT-20, MT-21 and MT-22 are new cases split from MT-12 in the Part 01 review, so that each check can pass or fail on its own.

How the cases were written:

- **IDs, scenarios and types** come from TASK 6 and were not changed. **Requirement IDs** list every FR/NFR row of the RTM (TASK 5) that traces the case, and only those rows.
- **Priority** is the highest priority among the linked RTM rows (§10.1).
- **Expected results** come from TASK 6 and from the requirement definitions in TASK 1 (and TASK 2 or TASK 4 where cited). TASK 6 gives no expected result for the UI and UT cases. For those, the expected result restates the checks named in the scenario, using the requirement text. Where the source documents do not state a value (for example an exact message text or a success status code), the case says so, and the value is recorded at execution instead of being assumed.
- **Steps and preconditions were written for this plan** from those descriptions. They must be checked against the running application and the code during peer review (V-3). Page names are used where the source documents do not give the exact URL. Data references (TD-xx) point to §12.
- **"Blocked pending confirmation"** marks the part of an expected result that depends on an open question (RTM Q1, Q2, Q7, Q8) or on behaviour the source documents do not state. That part cannot pass or fail until it is confirmed. If the case is run earlier, only the observed behaviour is recorded.
- **Tools and approvals:** the UI automation and API tools are not installed (§7.6), and where QA test code will be kept is open (OQ-12). Cases that need an application change (UT-14 if refactored, UT-16, UT-17, INT-01 with Testcontainers) cannot start without the project owner's approval (§7.11).
- **Actual Result, Status and Execution details** come only from execution evidence. 41 cases have a group-repository result (API-04, INT-01 and the 39 Phase 11 cases); 6 UI cases have a Member 3 copy result; the other 32 are `NOT EXECUTED` or `BLOCKED`, with the reason. Expected results were **not** changed after execution.

**Expected results blocked pending confirmation**

| Test case | Blocked part of the expected result | Reason |
|---|---|---|
| API-05 | `403` without a token for login, logout and `PUT /api/reports/{id}` | RTM Q7 |
| MT-21 | Number of reports in Recent Activity (5 or 4) | RTM Q8 |
| API-15 | "Edit accepted" when the approved report used is RESOLVED | RTM Q1 (BR-09) |
| API-16 | Same-status PATCH accepted (step 3) | RTM Q1 (BR-13) |
| UT-06 | Same-status update accepted (step 1) | RTM Q1 (BR-13) |
| API-18 | Result of an approval request with body `{}` (step 6) | RTM Q1 (BR-22 **[I]**) |
| API-24 | Whole expected result (`409` expected; `500` suspected) | RTM Q1 (§2.3 **[I]**) |
| UT-14 | Result for `[::1]` | RTM Q1 (§2.6 **[I]**) |
| INT-01 | Scenario (c), existing database baselined at V2 | RTM Q2 (environment also open, RTM Q3) *[Phase 8A: Q3 answered – dedicated local PostgreSQL database; Q2 still open, so (c) stays BLOCKED]* |
| UI-08 | Timeline marking and CSS classes for IN_PROGRESS and RESOLVED | Not stated in the source documents (FR-05.1 gives PENDING only) |
| UT-16 | `getStatusProgress` for IN_PROGRESS and RESOLVED; `normalizeStatus` results; other status labels | Not stated in the source documents |
| UT-04 | Upper- or mixed-case sort direction | Not stated in the source documents |
| UT-08 | Rules for VP8, VP8L and VP8X; exact pixel-count limit | Not stated in the source documents |
| UT-17 | Newline inside a CSV value | Not stated in the source documents |
| API-12 | Exact code for a 5,242,881-byte image (`400` or `413`) | TASK 6 gives both codes |

Cases that cannot start until a tool, environment or approval is available: UT-16 and UT-17 (Vitest and the project owner's approval), UT-14 (reflection, or a refactor approved by the project owner), INT-01 (RTM Q3; *[Phase 8A: Q3 answered – scenarios (a), (b), (d) executed; (c) still waits for RTM Q2]*), MT-16 (assistive technology TBC), MT-17 (browsers and devices TBC, OQ-3), MT-10 (method for stopping the backend at the right moment to be agreed).

**A. Manual test cases (MT-01 – MT-22)**

#### MT-01 – Register a Citizen account with valid details

| Field | Content |
|---|---|
| Test Case ID | MT-01 |
| Requirement ID(s) | FR-01.1, FR-01.6 |
| Description | A visitor registers a Citizen account with valid, unique details |
| Type / Priority | P / High |
| Preconditions | Frontend and backend running in the test environment. The email address is not registered. The user is signed out |
| Test Data | TD-01 (new citizen; valid name, unused email, password of 12–72 characters) |
| Test Steps | 1. Open the registration page. 2. Enter the full name and email. 3. Enter the password and the same value in the confirmation field. 4. Tick the Terms checkbox. 5. Submit the form |
| Expected Result | The success message "Your Citizen account has been created…" is shown and the form is reset |
| Actual Result | NOT EXECUTED |
| Status | **NOT EXECUTED** |
| Execution details | Not executed in any copy. Manual case: needs a human tester at the running frontend and backend (ENV-01, §11.2). No manual test session is recorded in the project. Automated results for related API or UI cases do not replace it |

#### MT-02 – Registration is blocked when the password confirmation differs

| Field | Content |
|---|---|
| Test Case ID | MT-02 |
| Requirement ID(s) | FR-01.6 |
| Description | The registration form does not submit when the confirmation password differs from the password (client-side check) |
| Type / Priority | N / Medium |
| Preconditions | Frontend and backend running in the test environment. The user is signed out. The email address is not registered |
| Test Data | TD-01 (valid name, unused email, valid password and a different confirmation value) |
| Test Steps | 1. Open the registration page. 2. Enter a valid full name and an unused email. 3. Enter a valid password. 4. Enter a different value in the confirmation field. 5. Tick the Terms checkbox. 6. Submit the form. 7. Note the message. 8. In the browser developer tools, check whether a `POST /api/auth/register` request was sent |
| Expected Result | The browser blocks the submit and shows "Passwords do not match." No registration request is sent, so no account is created |
| Actual Result | NOT EXECUTED |
| Status | **NOT EXECUTED** |
| Execution details | Not executed in any copy. Manual case: needs a human tester at the running frontend and backend (ENV-01, §11.2). No manual test session is recorded in the project. Automated results for related API or UI cases do not replace it |

#### MT-03 – Registration is blocked when the Terms checkbox is not ticked

| Field | Content |
|---|---|
| Test Case ID | MT-03 |
| Requirement ID(s) | FR-01.6 |
| Description | The registration form cannot be submitted without ticking the Terms checkbox. This is a client-side check only: Terms acceptance is not sent to or enforced by the backend (FR-01.6) |
| Type / Priority | N / Medium |
| Preconditions | Frontend and backend running in the test environment. The user is signed out. The email address is not registered |
| Test Data | TD-01 (valid name, unused email, valid password with the same confirmation) |
| Test Steps | 1. Open the registration page. 2. Enter a valid full name, an unused email, a valid password and the same confirmation. 3. Leave the Terms checkbox unticked. 4. Submit the form. 5. Note the message. 6. In the browser developer tools, check whether a `POST /api/auth/register` request was sent |
| Expected Result | The browser's required-field validation blocks the submit at the Terms checkbox. No registration request is sent. The wording of the browser message depends on the browser and is recorded, not judged |
| Actual Result | NOT EXECUTED |
| Status | **NOT EXECUTED** |
| Execution details | Not executed in any copy. Manual case: needs a human tester at the running frontend and backend (ENV-01, §11.2). No manual test session is recorded in the project. Automated results for related API or UI cases do not replace it |

#### MT-04 – Duplicate email with different case and padding is rejected

| Field | Content |
|---|---|
| Test Case ID | MT-04 |
| Requirement ID(s) | FR-01.4 |
| Description | Registering `User@X.com`, then ` user@x.com ` (same address, different case, with surrounding spaces) is rejected as a duplicate, because emails are normalised before the uniqueness check (BR-02) |
| Type / Priority | N / High |
| Preconditions | Frontend and backend running in the test environment. The user is signed out. Neither form of the email is registered before the test |
| Test Data | TD-01 (an unused mixed-case email on the test domain, then the same email in lower case with a leading and a trailing space; valid name and password) |
| Test Steps | 1. Open the registration page. 2. Register with the mixed-case email, a valid name and password, and the Terms checkbox ticked. 3. Note the message. 4. Register again with the lower-case email with surrounding spaces, other fields valid. 5. Note the message |
| Expected Result | Step 3: the success message "Your Citizen account has been created…" is shown. Step 5: the duplicate-account message is shown and no second account is created. The backend message for a duplicate is "An account with this email already exists." (`409`, FR-01.4); the exact text shown by the page is to be confirmed during peer review |
| Actual Result | NOT EXECUTED |
| Status | **NOT EXECUTED** |
| Execution details | Not executed in any copy. Manual case: needs a human tester at the running frontend and backend (ENV-01, §11.2). No manual test session is recorded in the project. Automated results for related API or UI cases do not replace it |

#### MT-05 – Role-based landing page after login

| Field | Content |
|---|---|
| Test Case ID | MT-05 |
| Requirement ID(s) | FR-02.2, FR-02.7 |
| Description | Citizen and Admin are sent to different pages after a successful login |
| Type / Priority | P / High |
| Preconditions | A Citizen account and an Admin account exist (the Admin is created with the first-admin setup command). The user is signed out |
| Test Data | TD-02 (Citizen A), TD-04 (Admin) |
| Test Steps | 1. Open the sign-in page. 2. Sign in as Citizen A. 3. Note the page shown. 4. Log out. 5. Sign in as Admin. 6. Note the page shown |
| Expected Result | Citizen lands on `/my-reports`. Admin lands on `/admin` |
| Actual Result | NOT EXECUTED |
| Status | **NOT EXECUTED** |
| Execution details | Not executed in any copy. Manual case: needs a human tester at the running frontend and backend (ENV-01, §11.2). No manual test session is recorded in the project. Automated results for related API or UI cases do not replace it |

#### MT-06 – Generic error for invalid credentials

| Field | Content |
|---|---|
| Test Case ID | MT-06 |
| Requirement ID(s) | FR-02.3 |
| Description | A wrong password and an unknown email produce the same error message, so the login form does not reveal which accounts exist |
| Type / Priority | N / High |
| Preconditions | Citizen A exists. The user is signed out |
| Test Data | TD-02 (Citizen A email with a wrong password); an unregistered email from TD-01 |
| Test Steps | 1. Open the sign-in page. 2. Enter Citizen A's email and a wrong password; submit. 3. Note the message. 4. Enter an unregistered email and any password; submit. 5. Note the message |
| Expected Result | Both attempts show the identical message "Invalid email or password." |
| Actual Result | NOT EXECUTED |
| Status | **NOT EXECUTED** |
| Execution details | Not executed in any copy. Manual case: needs a human tester at the running frontend and backend (ENV-01, §11.2). No manual test session is recorded in the project. Automated results for related API or UI cases do not replace it |

#### MT-07 – Logout ends access in the UI

| Field | Content |
|---|---|
| Test Case ID | MT-07 |
| Requirement ID(s) | FR-02.6 |
| Description | After logout, the UI shows the signed-out state; refreshing or pressing Back does not give access to the citizen's pages again |
| Type / Priority | P / High |
| Preconditions | Citizen A exists. Frontend and backend running in the test environment |
| Test Data | TD-02 (Citizen A) |
| Test Steps | 1. Sign in as Citizen A and open `/my-reports`. 2. Log out through the account menu. 3. Note the page and the header. 4. Refresh the page. 5. Press the browser Back button. 6. Open `/my-reports` directly |
| Expected Result | Step 3: the UI returns to `/` and the header shows the signed-out state. Steps 4–5: the header still shows the signed-out state. Step 6: `/my-reports` shows the sign-in prompt instead of the citizen's reports |
| Actual Result | NOT EXECUTED |
| Status | **NOT EXECUTED** |
| Execution details | Not executed in any copy. Manual case: needs a human tester at the running frontend and backend (ENV-01, §11.2). No manual test session is recorded in the project. Automated results for related API or UI cases do not replace it |

#### MT-08 – Create a report with a valid photo

| Field | Content |
|---|---|
| Test Case ID | MT-08 |
| Requirement ID(s) | FR-03.1, FR-03.4, FR-03.5, FR-05.4 |
| Description | A signed-in citizen creates a report with a valid PNG and opens it |
| Type / Priority | P / High |
| Preconditions | Citizen A is signed in |
| Test Data | TD-05 (valid report fields, one of the six UI categories); TD-08 (valid PNG ≤ 5 MiB) |
| Test Steps | 1. Open `/create-issue`. 2. Enter the title. 3. Select a category. 4. Enter the description and location. 5. Choose the PNG file. 6. Submit. 7. Select "View report" |
| Expected Result | A reference in the format `PC-000000NN` is shown. The report page shows the photo, PENDING status and the status timeline |
| Actual Result | NOT EXECUTED |
| Status | **NOT EXECUTED** |
| Execution details | Not executed in any copy. Manual case: needs a human tester at the running frontend and backend (ENV-01, §11.2). No manual test session is recorded in the project. Automated results for related API or UI cases do not replace it |

#### MT-09 – Invalid photo files are rejected on the create-report page

| Field | Content |
|---|---|
| Test Case ID | MT-09 |
| Requirement ID(s) | FR-03.7 |
| Description | The client rejects a `.gif`, a 0-byte `.png` and a JPG of 5 MiB + 1 byte. A text file renamed `.jpg` passes the client check, is rejected by the server, and the "Retry image upload" path appears |
| Type / Priority | N/B / Medium |
| Preconditions | Citizen A is signed in. Frontend and backend running in the test environment |
| Test Data | TD-05 (valid report fields), TD-08 (GIF; 0-byte PNG; JPG of 5,242,881 bytes; text file renamed `.jpg`) |
| Test Steps | 1. Open `/create-issue`. 2. Choose the GIF file and note the message. 3. Choose the 0-byte PNG and note the message. 4. Choose the 5,242,881-byte JPG and note the message. 5. Enter valid report fields and choose the text file renamed `.jpg`. 6. Submit. 7. Note the messages and buttons shown |
| Expected Result | Steps 2–4: a client-side error is shown for each file and the file is not accepted for upload. Steps 6–7: the renamed file passes the client check, the server rejects the image (`400`), and the "Retry image upload" path appears. The exact client error texts are not given in the source documents and are recorded at execution |
| Actual Result | NOT EXECUTED |
| Status | **NOT EXECUTED** |
| Execution details | Not executed in any copy. Manual case: needs a human tester at the running frontend and backend (ENV-01, §11.2). No manual test session is recorded in the project. Automated results for related API or UI cases do not replace it |

#### MT-10 – Image upload fails after the report was created

| Field | Content |
|---|---|
| Test Case ID | MT-10 |
| Requirement ID(s) | FR-03.7 |
| Description | If the backend stops after the report has been created but before the image upload completes, the page says that the report exists and offers a retry |
| Type / Priority | N / Medium |
| Preconditions | Citizen A is signed in. Disposable test environment in which the tester can stop the backend (an environment change, not an application change, §7.11). The tester has a way to stop the backend between the create request and the image upload request; the method is to be agreed during peer review |
| Test Data | TD-05 (valid report fields), TD-08 (valid PNG ≤ 5 MiB) |
| Test Steps | 1. Open `/create-issue`. 2. Enter valid report fields and choose the PNG. 3. Submit. 4. After the report-create request has completed and before the image upload has completed, stop the backend. 5. Note the message and the buttons shown. 6. Restart the backend |
| Expected Result | The message "Report … was created, but its image upload failed" is shown together with a "Retry image upload" button |
| Actual Result | NOT EXECUTED |
| Status | **NOT EXECUTED** |
| Execution details | Not executed in any copy. Manual case: needs a human tester at the running frontend and backend (ENV-01, §11.2). No manual test session is recorded in the project. Automated results for related API or UI cases do not replace it |

#### MT-11 – Citizen edits an approved report

| Field | Content |
|---|---|
| Test Case ID | MT-11 |
| Requirement ID(s) | FR-04.4, FR-04.5, FR-04.6 |
| Description | A citizen edits their own report that is approved for public display. The change is saved, the citizen is sent to the report details, public approval is removed and no photo edit is offered |
| Type / Priority | P / High |
| Preconditions | Citizen A owns a report that the Admin has approved for public display. The report's category is one of the six UI categories, so the **[I]** edit-page category fallback (RTM document §2.4) does not affect this case. The report's status is recorded before the test |
| Test Data | TD-02 (Citizen A), TD-04 (Admin), TD-05 (valid new field values), TD-06 (approved report) |
| Test Steps | 1. As Admin, open the admin issues list and check that the report shows "Approved". 2. Sign in as Citizen A, open the report and choose Edit. 3. Check the photo area of the edit page. 4. Change the title and description to valid new values. 5. Save. 6. Note the page shown. 7. As Admin, reload the admin issues list and check the report's label |
| Expected Result | Step 3: no photo edit is offered; the hint "Photo attachments are not supported yet" is shown. Steps 5–6: the changes are saved and the citizen is redirected to the report details, which show the new values. Step 7: the report shows "Private" |
| Actual Result | NOT EXECUTED |
| Status | **NOT EXECUTED** |
| Execution details | Not executed in any copy. Manual case: needs a human tester at the running frontend and backend (ENV-01, §11.2). No manual test session is recorded in the project. Automated results for related API or UI cases do not replace it |

#### MT-12 – Admin saves a progress update

| Field | Content |
|---|---|
| Test Case ID | MT-12 |
| Requirement ID(s) | FR-06.5 |
| Description | An admin saves a progress update with status, note and photo on the admin update page. *(Narrowed in the Part 01 review: the citizen view, dashboard and CSV checks are now MT-20, MT-21 and MT-22.)* |
| Type / Priority | P / High |
| Preconditions | A PENDING report owned by Citizen A exists (for example from MT-08). Admin is signed in |
| Test Data | TD-04 (Admin), TD-02 (Citizen A as report owner), TD-07 (progress note ≤ 1000 characters), TD-08 (valid JPG ≤ 5 MiB) |
| Test Steps | 1. As Admin, open the report from the admin issues list. 2. On the update page, select IN_PROGRESS. 3. Enter the note. 4. Attach the JPG. 5. Select "Save Changes". 6. Reload the page |
| Expected Result | The message "Progress update for PC-… was saved." is shown. The report status is IN_PROGRESS. A new history entry with the note and the photo is shown, and it is still shown after the reload |
| Actual Result | NOT EXECUTED |
| Status | **NOT EXECUTED** |
| Execution details | Not executed in any copy. Manual case: needs a human tester at the running frontend and backend (ENV-01, §11.2). No manual test session is recorded in the project. Automated results for related API or UI cases do not replace it |

#### MT-13 – Three-level public approval and revocation

| Field | Content |
|---|---|
| Test Case ID | MT-13 |
| Requirement ID(s) | FR-07.1, FR-07.2, FR-07.3, FR-07.4 |
| Description | The admin approves a report, then its progress update, then the update's note. Each item appears on the home page feed only after its own approval (BR-16). Revoking each one removes it again |
| Type / Priority | P/N / High |
| Preconditions | Admin is signed in. A report exists with one progress update that has a non-blank note; the report, the update and the note are all private. A second browser window (private window) is open as an anonymous visitor |
| Test Data | TD-04 (Admin), TD-06 (private report with a progress update and a note), TD-07 (non-blank note) |
| Test Steps | 1. As the anonymous visitor, open the home page feed and check that the report is not listed. 2. As Admin, approve the report. 3. Reload the feed. 4. As Admin, approve the progress update. 5. Reload the feed. 6. As Admin, approve the note. 7. Reload the feed. 8. As Admin, revoke the note; reload the feed. 9. Revoke the update; reload the feed. 10. Revoke the report; reload the feed. *(Revoking in the order note → update → report was chosen for this plan so that each removal can be seen on its own.)* |
| Expected Result | Step 3: the report is shown, without the update. Step 5: the update is shown, without the note. Step 7: the note is shown. Step 8: the note is gone; the report and update are still shown. Step 9: the update is gone; the report is still shown. Step 10: the report is gone |
| Actual Result | NOT EXECUTED |
| Status | **NOT EXECUTED** |
| Execution details | Not executed in any copy. Manual case: needs a human tester at the running frontend and backend (ENV-01, §11.2). No manual test session is recorded in the project. Automated results for related API or UI cases do not replace it |

#### MT-14 – Status change removes a report from the public feed

| Field | Content |
|---|---|
| Test Case ID | MT-14 |
| Requirement ID(s) | FR-07.7 |
| Description | Changing the status of an approved report revokes its public approval automatically |
| Type / Priority | P / High |
| Preconditions | Admin is signed in. An approved report with status PENDING is shown on the public feed. A second browser window is open as an anonymous visitor |
| Test Data | TD-04 (Admin), TD-06 (approved PENDING report), TD-07 (progress update data) |
| Test Steps | 1. As the anonymous visitor, check that the report is listed on the public feed. 2. As Admin, open the report on the admin update page, select IN_PROGRESS and select "Save Changes". 3. Reload the public feed. 4. As Admin, open the admin issues list and check the report's label |
| Expected Result | Step 3: the report is no longer on the public feed. Step 4: the report shows "Private". Note: in the UI a status change is made through a progress update (the PATCH status route is API only, FR-06.6), so this case checks the "progress update with a different status" path of FR-07.7. The PATCH path is checked by API-16 and UT-06 |
| Actual Result | NOT EXECUTED |
| Status | **NOT EXECUTED** |
| Execution details | Not executed in any copy. Manual case: needs a human tester at the running frontend and backend (ENV-01, §11.2). No manual test session is recorded in the project. Automated results for related API or UI cases do not replace it |

#### MT-15 – Admin deletes a report

| Field | Content |
|---|---|
| Test Case ID | MT-15 |
| Requirement ID(s) | FR-08.1, FR-08.4 |
| Description | The delete confirmation can be cancelled; accepting it permanently removes the report |
| Type / Priority | N/P / High |
| Preconditions | Admin is signed in. A report exists that has been approved for public display, so its public link can be checked |
| Test Data | TD-04 (Admin); TD-06 (an approved report) |
| Test Steps | 1. Note the report's ID. 2. Open the admin issues list. 3. Select Delete for the report. 4. Cancel the confirmation dialog. 5. Select Delete again and accept. 6. Open `/public-reports/{id}` with the noted ID |
| Expected Result | After cancel, the row is still listed. After accepting, the row is removed and the public link shows "Report unavailable" |
| Actual Result | NOT EXECUTED |
| Status | **NOT EXECUTED** |
| Execution details | Not executed in any copy. Manual case: needs a human tester at the running frontend and backend (ENV-01, §11.2). No manual test session is recorded in the project. Automated results for related API or UI cases do not replace it |

#### MT-16 – Keyboard-only and screen-reader walkthrough

| Field | Content |
|---|---|
| Test Case ID | MT-16 |
| Requirement ID(s) | NFR-USA |
| Description | Exploratory walkthrough of the main pages using only the keyboard and a screen reader |
| Type / Priority | – (exploratory) / Medium |
| Preconditions | Frontend and backend running in the test environment. Citizen A and Admin accounts exist. The screen reader and browser to use are agreed (TBC, §2.1 and OQ-3) |
| Test Data | TD-02 (Citizen A), TD-04 (Admin), TD-05 (valid report fields) |
| Test Steps | 1. Without a mouse (Tab, Shift+Tab, Enter, Space, arrow keys), register, sign in, create a report, open it and sign out. 2. As Admin, open the issues list and save a progress update. 3. Note any action that cannot be reached by keyboard. 4. With the screen reader on, cause an error message (failed login) and a success message (report created), and note whether each is announced. 5. Open the account menu by keyboard and press Escape. 6. Record the tools used and all findings |
| Expected Result | All actions can be reached by keyboard. Alerts and status messages are announced by the screen reader. Escape closes the account menu. This is an exploratory check, not a WCAG 2.1 AA audit (§2.2). It cannot be executed until the assistive technology is confirmed |
| Actual Result | NOT EXECUTED |
| Status | **NOT EXECUTED** |
| Execution details | Not executed in any copy. Manual case: needs a human tester at the running frontend and backend (ENV-01, §11.2). No manual test session is recorded in the project. Automated results for related API or UI cases do not replace it |

#### MT-17 – Cross-browser and mobile-width check

| Field | Content |
|---|---|
| Test Case ID | MT-17 |
| Requirement ID(s) | NFR-COMP |
| Description | The main flows work in Chrome, Firefox, Edge and Safari and at a 375 px mobile width |
| Type / Priority | – / Medium |
| Preconditions | ENV-04 (§11.2). Browser versions and availability are TBC (OQ-3); Safari needs a macOS or iOS device. Citizen A and Admin accounts exist |
| Test Data | TD-02 (Citizen A), TD-04 (Admin), TD-05 (valid report fields), TD-08 (valid PNG) |
| Test Steps | For each agreed browser: 1. Sign in as Citizen A. 2. Create a report with a photo and open it. 3. Sign out; sign in as Admin; open the dashboard and the admin issues list. 4. Open the public feed. 5. Repeat steps 1–4 at a 375 px width. 6. Record the browser name and version, the operating system and any problem |
| Expected Result | No layout breakage in any agreed browser or at 375 px. Cookies work: the sign-in is kept between pages and the state-changing actions in steps 2–3 succeed. A browser that the group cannot provide is recorded as blocked (OQ-3) |
| Actual Result | NOT EXECUTED |
| Status | **NOT EXECUTED** |
| Execution details | Not executed in any copy. Manual case: needs a human tester at the running frontend and backend (ENV-01, §11.2). No manual test session is recorded in the project. Automated results for related API or UI cases do not replace it |

#### MT-18 – Citizen cannot open admin pages or another user's report

| Field | Content |
|---|---|
| Test Case ID | MT-18 |
| Requirement ID(s) | FR-02.9, FR-04.2 |
| Description | A citizen is blocked from admin pages and from viewing another citizen's report |
| Type / Priority | N / High |
| Preconditions | Citizen A is signed in. A report owned by Citizen B exists and its ID is known |
| Test Data | TD-02 (Citizen A), TD-03 (Citizen B and a report owned by B) |
| Test Steps | 1. Open `/admin`. 2. Note the message. 3. Open `/issue-details?id=<Citizen B's report ID>`. 4. Note the message |
| Expected Result | Step 2: "Administrator access required". Step 4: "Report unavailable" |
| Actual Result | NOT EXECUTED |
| Status | **NOT EXECUTED** |
| Execution details | Not executed in any copy. Manual case: needs a human tester at the running frontend and backend (ENV-01, §11.2). No manual test session is recorded in the project. Automated results for related API or UI cases do not replace it |

#### MT-19 – Frontend behaviour when the backend is unreachable

| Field | Content |
|---|---|
| Test Case ID | MT-19 |
| Requirement ID(s) | NFR-REL |
| Description | When the backend is stopped, the pages show a message and do not crash |
| Type / Priority | N / Medium |
| Preconditions | Disposable test environment (§7.11). Frontend running. Citizen A is signed in before the backend is stopped |
| Test Data | TD-02 (Citizen A), TD-05 (valid report fields) |
| Test Steps | 1. Sign in as Citizen A and open My Reports. 2. Stop the backend. 3. Reload My Reports and note the result. 4. Open a report's details, the create-report page (submit a valid report), the public feed and the sign-in page (attempt a sign-in), and note the result on each. 5. Restart the backend |
| Expected Result | Each page shows the message "Unable to reach PublicCare…" and the application does not crash (no blank page) |
| Actual Result | NOT EXECUTED |
| Status | **NOT EXECUTED** |
| Execution details | Not executed in any copy. Manual case: needs a human tester at the running frontend and backend (ENV-01, §11.2). No manual test session is recorded in the project. Automated results for related API or UI cases do not replace it |

#### MT-20 – Citizen sees the admin's progress update

| Field | Content |
|---|---|
| Test Case ID | MT-20 |
| Requirement ID(s) | FR-05.2, FR-05.3 |
| Description | The report owner sees the admin's progress update: the new status, the latest note and the history entry with its photo |
| Type / Priority | P / High |
| Preconditions | A report owned by Citizen A has an admin progress update with status IN_PROGRESS, a note and a photo (from MT-12, or prepared as part of TD-06). Citizen A is signed in |
| Test Data | TD-02 (Citizen A), TD-06 (report with a progress update, a note and a photo) |
| Test Steps | 1. Open My Reports. 2. Open the report. 3. Check the status and the status timeline. 4. Check the "Latest Update from PublicCare" box. 5. Check the progress history |
| Expected Result | The status is IN_PROGRESS. "Latest Update from PublicCare" shows the admin's note. The progress history shows the update with its note and photo. The note is shown even if it is not approved for public display, because owners are not subject to public approval (FR-05.3) |
| Actual Result | NOT EXECUTED |
| Status | **NOT EXECUTED** |
| Execution details | Not executed in any copy. Manual case: needs a human tester at the running frontend and backend (ENV-01, §11.2). No manual test session is recorded in the project. Automated results for related API or UI cases do not replace it |

#### MT-21 – Admin dashboard counts and recent reports

| Field | Content |
|---|---|
| Test Case ID | MT-21 |
| Requirement ID(s) | FR-06.1 |
| Description | The admin dashboard shows correct global counts and the most recent reports, and the counts change after a status change |
| Type / Priority | P / Medium |
| Preconditions | Admin is signed in. The test database holds a known set of reports in each status and in several categories, owned by Citizen A and Citizen B. The expected counts are worked out from the test data before the test |
| Test Data | TD-04 (Admin), TD-02 and TD-03 (two report owners), TD-06 (reports in each status) |
| Test Steps | 1. Open `/admin`. 2. Record Total Issues, Pending, In Progress, Resolved and the Issues by Category counts. 3. Record the reports listed under Recent Activity. 4. Compare steps 2–3 with the expected values. 5. Move one PENDING report to IN_PROGRESS with a progress update (as in MT-12). 6. Reload `/admin` and record the counts again |
| Expected Result | Step 4: the counts equal the expected values and include the reports of all owners. Recent Activity lists the newest reports first. Step 6: Pending is 1 lower, In Progress is 1 higher, and Total is unchanged. **The expected number of reports in Recent Activity needs clarification:** FR-06.1 says 5, but the code requests 5 and displays at most 4 (RTM Q8). This part of the check is blocked until Q8 is answered |
| Actual Result | NOT EXECUTED |
| Status | **NOT EXECUTED** |
| Execution details | Not executed in any copy. Manual case: needs a human tester at the running frontend and backend (ENV-01, §11.2). No manual test session is recorded in the project. Automated results for related API or UI cases do not replace it |

#### MT-22 – Admin exports the current page as CSV

| Field | Content |
|---|---|
| Test Case ID | MT-22 |
| Requirement ID(s) | FR-06.3 |
| Description | The admin exports the current page of the issues list as a CSV file |
| Type / Priority | P / Low |
| Preconditions | Admin is signed in. More than 10 reports exist, so the issues list has at least two pages. One report title contains a double quote and a comma |
| Test Data | TD-04 (Admin), TD-09 (more than 10 reports), TD-05 (title containing `"` and `,`) |
| Test Steps | 1. Open the admin issues list (`/admin/issues`). 2. Note the rows on page 1. 3. Select "Export current page". 4. Open the downloaded file in a text editor and in a spreadsheet program. 5. Go to page 2 and repeat steps 3–4 |
| Expected Result | A file `publiccare-reports.csv` is downloaded each time. The header row is Reference, Issue title, Category, Location, Date reported, Status. Each file holds only the rows of the page that was shown. Each value is in double quotes and a `"` inside a value is doubled, so the title with `"` and `,` appears unchanged in the spreadsheet. The Status column uses the display labels (for example "In progress") |
| Actual Result | NOT EXECUTED |
| Status | **NOT EXECUTED** |
| Execution details | Not executed in any copy. Manual case: needs a human tester at the running frontend and backend (ENV-01, §11.2). No manual test session is recorded in the project. Automated results for related API or UI cases do not replace it |

**B. Automated UI test cases (UI-01 – UI-14)**

#### UI-01 – Registration form boundaries and password mismatch (automated)

| Field | Content |
|---|---|
| Test Case ID | UI-01 |
| Requirement ID(s) | FR-01.1, FR-01.2, FR-01.6 |
| Description | Automated browser check of the registration form: name `maxLength` 120; password of 11 characters rejected, 12 and 72 accepted; mismatched confirmation rejected |
| Type / Priority | B/N/P / High |
| Preconditions | UI automation tool available (Playwright or Selenium, not installed, §7.6) and an agreed location for the script (OQ-12, §7.11). Frontend and backend running with a disposable database. Signed out |
| Test Data | TD-01 (name of 121 characters; passwords of 11, 12 and 72 characters; a new unused email for each accepted submission) |
| Test Steps | 1. Open the registration page. 2. Type a 121-character name and read the field value. 3. Fill valid fields with an 11-character password and the same confirmation; submit. 4. Repeat with a 12-character password and a new email. 5. Repeat with a 72-character password and a new email. 6. Fill valid fields with a different confirmation value; submit |
| Expected Result | Step 2: the field holds at most 120 characters. Step 3: client validation (`minLength`) blocks the submit and no account is created. Steps 4–5: the account is created, the message "Your Citizen account has been created…" is shown and the form is reset. Step 6: the submit is blocked with "Passwords do not match." |
| Actual Result | Not executed in the group repository. Executed in the Member 3 copy (`93d11a3`, local Windows 11, Selenium 4.31.0, Chrome 157 headless): `PublicCareUiIT.registrationBoundariesAndMismatch` passed (6 of 6 UI tests; a deliberate fault check showed the tests can fail). Not run in CI |
| Status | **PASS – Member 3 copy only** (not a group-repository result) |
| Execution details | Member 3 repository `PublicCare-QA-Member3-Ishani-` @ `93d11a3` (private), 24 Nov 2026. Evidence: [ui-automation/README.md](../3.5-3.6_member3/evidence/ui-automation/README.md) and screenshots. Selenium is not a dependency of the group repository (owner decision D-5 pending) |

#### UI-02 – Login redirect by role (automated)

| Field | Content |
|---|---|
| Test Case ID | UI-02 |
| Requirement ID(s) | FR-02.7 |
| Description | After login, a citizen is sent to `/my-reports` and an admin to `/admin` |
| Type / Priority | P / Medium |
| Preconditions | UI automation tool available (§7.6, OQ-12). Citizen A and Admin accounts exist. Signed out |
| Test Data | TD-02 (Citizen A), TD-04 (Admin) |
| Test Steps | 1. Open the sign-in page. 2. Sign in as Citizen A. 3. Assert the URL. 4. Log out. 5. Sign in as Admin. 6. Assert the URL |
| Expected Result | Step 3: the URL path is `/my-reports`. Step 6: the URL path is `/admin` |
| Actual Result | Not executed in the group repository. Executed in the Member 3 copy (`93d11a3`, local Windows 11, Selenium 4.31.0, Chrome 157 headless): `PublicCareUiIT.loginRedirectsByRole` passed (6 of 6 UI tests; a deliberate fault check showed the tests can fail). Not run in CI |
| Status | **PASS – Member 3 copy only** (not a group-repository result) |
| Execution details | Member 3 repository `PublicCare-QA-Member3-Ishani-` @ `93d11a3` (private), 24 Nov 2026. Evidence: [ui-automation/README.md](../3.5-3.6_member3/evidence/ui-automation/README.md) and screenshots. Selenium is not a dependency of the group repository (owner decision D-5 pending) |

#### UI-03 – Failed login shows a generic alert (automated)

| Field | Content |
|---|---|
| Test Case ID | UI-03 |
| Requirement ID(s) | FR-02.3 |
| Description | A failed login shows the generic message in an element with `role=alert` |
| Type / Priority | N / High |
| Preconditions | UI automation tool available (§7.6, OQ-12). Citizen A exists. Signed out |
| Test Data | TD-02 (Citizen A email with a wrong password); an unregistered email from TD-01 |
| Test Steps | 1. Open the sign-in page. 2. Submit Citizen A's email with a wrong password. 3. Read the element with `role=alert`. 4. Submit an unregistered email with any password. 5. Read the element with `role=alert` |
| Expected Result | Steps 3 and 5: an element with `role=alert` shows "Invalid email or password." The user stays signed out |
| Actual Result | Not executed in the group repository. Executed in the Member 3 copy (`93d11a3`, local Windows 11, Selenium 4.31.0, Chrome 157 headless): `PublicCareUiIT.failedLoginShowsGenericAlert` passed (6 of 6 UI tests; a deliberate fault check showed the tests can fail). Not run in CI |
| Status | **PASS – Member 3 copy only** (not a group-repository result) |
| Execution details | Member 3 repository `PublicCare-QA-Member3-Ishani-` @ `93d11a3` (private), 24 Nov 2026. Evidence: [ui-automation/README.md](../3.5-3.6_member3/evidence/ui-automation/README.md) and screenshots. Selenium is not a dependency of the group repository (owner decision D-5 pending) |

#### UI-04 – Signed-out user cannot submit a report (automated)

| Field | Content |
|---|---|
| Test Case ID | UI-04 |
| Requirement ID(s) | FR-03.8 |
| Description | On `/create-issue` without a session, the submit button is disabled and a sign-in link is shown |
| Type / Priority | N / Medium |
| Preconditions | UI automation tool available (§7.6, OQ-12). Frontend and backend running. No session (signed out) |
| Test Data | None |
| Test Steps | 1. Open `/create-issue` without signing in. 2. Check the state of the submit button. 3. Check for the sign-in notice and link |
| Expected Result | The page opens (it is not gated). The submit button is disabled. The "Please sign in" notice and a sign-in link are visible |
| Actual Result | Not executed in the group repository. Executed in the Member 3 copy (`93d11a3`, local Windows 11, Selenium 4.31.0, Chrome 157 headless): `PublicCareUiIT.signedOutUserCannotSubmitReport` passed (6 of 6 UI tests; a deliberate fault check showed the tests can fail). Not run in CI |
| Status | **PASS – Member 3 copy only** (not a group-repository result) |
| Execution details | Member 3 repository `PublicCare-QA-Member3-Ishani-` @ `93d11a3` (private), 24 Nov 2026. Evidence: [ui-automation/README.md](../3.5-3.6_member3/evidence/ui-automation/README.md) and screenshots. Selenium is not a dependency of the group repository (owner decision D-5 pending) |

#### UI-05 – Create-report required fields and successful submission (automated)

| Field | Content |
|---|---|
| Test Case ID | UI-05 |
| Requirement ID(s) | FR-03.1, FR-03.2 |
| Description | Each empty required field blocks the submit; with all fields valid, a success notice with the reference is shown |
| Type / Priority | N/P / High |
| Preconditions | UI automation tool available (§7.6, OQ-12). Citizen A is signed in |
| Test Data | TD-02 (Citizen A), TD-05 (valid report fields) |
| Test Steps | 1. Open `/create-issue`. 2. For each required field in turn (title, category, description, location), fill the others with valid values, leave that field empty and submit; check that no create request is sent. 3. Fill all fields with valid values and submit. 4. Read the success notice |
| Expected Result | Step 2: each empty required field blocks the submit and no report is created. Steps 3–4: a success notice is shown with a reference that matches `^PC-\d{8}$` |
| Actual Result | Not executed in the group repository. Executed in the Member 3 copy (`93d11a3`, local Windows 11, Selenium 4.31.0, Chrome 157 headless): `PublicCareUiIT.requiredFieldsAndSuccessfulReport` passed (6 of 6 UI tests; a deliberate fault check showed the tests can fail). Not run in CI |
| Status | **PASS – Member 3 copy only** (not a group-repository result) |
| Execution details | Member 3 repository `PublicCare-QA-Member3-Ishani-` @ `93d11a3` (private), 24 Nov 2026. Evidence: [ui-automation/README.md](../3.5-3.6_member3/evidence/ui-automation/README.md) and screenshots. Selenium is not a dependency of the group repository (owner decision D-5 pending) |

#### UI-06 – Image picker checks, preview and removal (automated)

| Field | Content |
|---|---|
| Test Case ID | UI-06 |
| Requirement ID(s) | FR-03.7 |
| Description | The image picker shows messages for an invalid type, an empty file and a file over 5 MiB; shows a preview with "Remove photo"; and the input is disabled after submit |
| Type / Priority | N/B/P / Medium |
| Preconditions | UI automation tool available (§7.6, OQ-12). Citizen A is signed in |
| Test Data | TD-05 (valid report fields), TD-08 (GIF; 0-byte PNG; JPG of 5,242,881 bytes; valid PNG) |
| Test Steps | 1. Open `/create-issue`. 2. Choose the GIF and read the message. 3. Choose the 0-byte PNG and read the message. 4. Choose the 5,242,881-byte JPG and read the message. 5. Choose the valid PNG and check the preview. 6. Select "Remove photo" and check the preview and the input. 7. Choose the valid PNG again, fill valid fields and submit. 8. Check the state of the file input |
| Expected Result | Steps 2–4: an error message is shown for each (invalid type, empty file, larger than 5 MiB) and the file is not kept. Step 5: a preview is shown. Step 6: the preview is removed and no file is selected. Step 8: the file input is disabled. The exact message texts are not given in the source documents; they are to be taken from the application during peer review before the script asserts them |
| Actual Result | NOT EXECUTED |
| Status | **NOT EXECUTED** |
| Execution details | Not executed in any copy. No UI automation exists for this case, and Selenium is not a dependency of the group repository (owner decision D-5 pending) |

#### UI-07 – My Reports filters, counts and paging (automated)

| Field | Content |
|---|---|
| Test Case ID | UI-07 |
| Requirement ID(s) | FR-04.1 |
| Description | On My Reports, the status filter and the search reset the page to the first page; the counts are shown; Previous is disabled on page 1 and Next on the last page |
| Type / Priority | P/B / High |
| Preconditions | UI automation tool available (§7.6, OQ-12). Citizen A is signed in and owns more than 10 reports in more than one status, so the list has at least two pages (UI page size 10) |
| Test Data | TD-02 (Citizen A), TD-06 (reports in different statuses), TD-09 (more than 10 reports) |
| Test Steps | 1. Open `/my-reports`. 2. Check that the summary counts are shown. 3. Check the Previous button on page 1. 4. Go forward to the last page and check the Next button. 5. Go to page 2 and change the status filter; check the page shown. 6. Go to page 2 again (if there is one) and enter search text; check the page shown |
| Expected Result | Step 2: the counts are shown. Step 3: Previous is disabled. Step 4: Next is disabled on the last page. Steps 5–6: the list returns to the first page (page 0). The **[I]** loading-message behaviour (RTM document §2.4) is not part of this case |
| Actual Result | NOT EXECUTED |
| Status | **NOT EXECUTED** |
| Execution details | Not executed in any copy. No UI automation exists for this case, and Selenium is not a dependency of the group repository (owner decision D-5 pending) |

#### UI-08 – Status timeline for each status (automated, mocked API)

| Field | Content |
|---|---|
| Test Case ID | UI-08 |
| Requirement ID(s) | FR-05.1, NFR-FUNC |
| Description | The issue details timeline shows the correct step marking for PENDING, IN_PROGRESS and RESOLVED. The API responses are mocked |
| Type / Priority | P / Medium |
| Preconditions | UI automation tool with network mocking available (§7.6, OQ-12). Mocked responses for the report detail request (and the other requests the page makes) with status PENDING, IN_PROGRESS and RESOLVED |
| Test Data | Mocked report responses based on TD-06 |
| Test Steps | For each status: 1. Set the mock to return a report with that status. 2. Open `/issue-details?id=<id>`. 3. Read the four timeline steps and the CSS classes on each |
| Expected Result | The timeline has four steps in order: Issue submitted, Under review, In progress, Resolved. For PENDING, "Under review" is marked as the current step (FR-05.1). **Blocked pending confirmation:** FR-05.1 states only the PENDING mapping. The step marking for IN_PROGRESS and RESOLVED, and the CSS class names, are not given in the source documents. They must be taken from `reportsData.getStatusProgress` and agreed during peer review before this part of the expected result is fixed |
| Actual Result | NOT EXECUTED |
| Status | **NOT EXECUTED** |
| Execution details | Not executed in any copy. No UI automation exists for this case, and Selenium is not a dependency of the group repository (owner decision D-5 pending) |

#### UI-09 – Admin Issues approval toggle and filters (automated)

| Field | Content |
|---|---|
| Test Case ID | UI-09 |
| Requirement ID(s) | FR-06.2, FR-07.1 |
| Description | On Admin Issues, Approve and Revoke toggle the button text and the "Approved/Private" label; the category, status and sort filters change the list |
| Type / Priority | P / High |
| Preconditions | UI automation tool available (§7.6, OQ-12). Admin is signed in. Reports from Citizen A and Citizen B exist in several categories and statuses, with different dates. At least one report is private |
| Test Data | TD-04 (Admin), TD-02 and TD-03 (report owners), TD-06 (reports in different states) |
| Test Steps | 1. Open `/admin/issues`. 2. For a private report, read the label and the button text. 3. Select Approve; read them again. 4. Select Revoke; read them again. 5. Apply a category filter; check the rows. 6. Apply a status filter; check the rows. 7. Change the date sort; check the row order |
| Expected Result | Step 2: "Private" and "Approve". Step 3: "Approved" and "Revoke". Step 4: "Private" and "Approve". Steps 5–6: only rows that match the filter are shown. Step 7: the rows are ordered by date in the selected direction. At most 10 rows are shown per page (FR-06.2) |
| Actual Result | NOT EXECUTED |
| Status | **NOT EXECUTED** |
| Execution details | Not executed in any copy. No UI automation exists for this case, and Selenium is not a dependency of the group repository (owner decision D-5 pending) |

#### UI-10 – Delete dialog and pending-state safeguards (automated)

| Field | Content |
|---|---|
| Test Case ID | UI-10 |
| Requirement ID(s) | FR-08.1, FR-08.4 |
| Description | Dismissing the delete dialog keeps the row; accepting it removes the row; all Delete buttons are disabled while a delete is pending |
| Type / Priority | N/P / High |
| Preconditions | UI automation tool available (§7.6, OQ-12). Admin is signed in. At least two disposable reports are listed. The database is reset after the test (§12.3) |
| Test Data | TD-04 (Admin), TD-06 (disposable reports) |
| Test Steps | 1. Open `/admin/issues`. 2. Select Delete on a report; read the dialog text; dismiss it. 3. Check the row and whether a DELETE request was sent. 4. Select Delete again and accept. 5. While the request is pending (the response may be delayed with network interception), check the state of all Delete buttons. 6. After completion, check the list |
| Expected Result | Step 2: the dialog names the report's reference. Step 3: the row is still listed and no DELETE request was sent. Step 5: all Delete buttons are disabled. Step 6: the list is reloaded and the row is gone |
| Actual Result | NOT EXECUTED |
| Status | **NOT EXECUTED** |
| Execution details | Not executed in any copy. No UI automation exists for this case, and Selenium is not a dependency of the group repository (owner decision D-5 pending) |

#### UI-11 – Admin update page: note limit, photo type, "Mark as resolved" and save (automated)

| Field | Content |
|---|---|
| Test Case ID | UI-11 |
| Requirement ID(s) | FR-06.5, FR-06.8 |
| Description | On the admin update page, the note has `maxLength` 1000, an invalid photo type gives an error, "Mark as resolved" changes only the status select, and saving appends a history item |
| Type / Priority | B/N/P / High |
| Preconditions | UI automation tool available (§7.6, OQ-12). Admin is signed in. A report owned by Citizen A exists with a status other than RESOLVED |
| Test Data | TD-04 (Admin), TD-06 (report), TD-07 (note of 1001 characters; valid note), TD-08 (GIF; valid JPG) |
| Test Steps | 1. Open the admin update page for the report. 2. Type a 1001-character note and read the field value. 3. Choose the GIF as the photo; read the message. 4. Record the saved status; select "Mark as resolved"; read the status select; reload the page without saving; read the saved status. 5. Select a status, enter a valid note, attach the valid JPG and select "Save Changes". 6. Count the history items |
| Expected Result | Step 2: the field holds at most 1000 characters. Step 3: an error is shown and the photo is not accepted. Step 4: the select shows RESOLVED; after the reload the saved status is unchanged, because nothing is saved until "Save Changes" (FR-06.8). Steps 5–6: the message "Progress update for PC-… was saved." is shown and one new history item with the status, note and photo is added |
| Actual Result | NOT EXECUTED |
| Status | **NOT EXECUTED** |
| Execution details | Not executed in any copy. No UI automation exists for this case, and Selenium is not a dependency of the group repository (owner decision D-5 pending) |

#### UI-12 – Public section: empty state, paging, navigation and unknown ID (automated)

| Field | Content |
|---|---|
| Test Case ID | UI-12 |
| Requirement ID(s) | FR-07.4, FR-07.5 |
| Description | The public section shows an empty state; shows pagination only when `totalPages > 1`; a card opens `/public-reports/{id}`; an unknown ID shows "Report unavailable" |
| Type / Priority | P/N / High |
| Preconditions | UI automation tool available (§7.6, OQ-12). Anonymous visitor. Three database states can be prepared: no approved reports; exactly one approved report; more than 24 approved reports |
| Test Data | TD-06 (approved and unapproved reports), TD-09 (more than 24 approved reports) |
| Test Steps | 1. With no approved reports, open the public section and read the empty-state text. 2. With one approved report, check for pagination controls; select the card and check the URL and page. 3. With more than 24 approved reports, check for pagination controls. 4. Open `/public-reports/<ID of an unapproved or non-existent report>` |
| Expected Result | Step 1: an empty-state text is shown (the exact text is not given in the source documents and is recorded). Step 2: no pagination is shown; the card opens `/public-reports/{id}` with that report. Step 3: pagination is shown. Step 4: "Report unavailable" is shown |
| Actual Result | NOT EXECUTED |
| Status | **NOT EXECUTED** |
| Execution details | Not executed in any copy. No UI automation exists for this case, and Selenium is not a dependency of the group repository (owner decision D-5 pending) |

#### UI-13 – Admin pages are gated for a citizen session (automated)

| Field | Content |
|---|---|
| Test Case ID | UI-13 |
| Requirement ID(s) | FR-02.9 |
| Description | With a citizen session, `/admin`, `/admin/issues` and `/admin/update-issue` show the admin gate |
| Type / Priority | N / Medium |
| Preconditions | UI automation tool available (§7.6, OQ-12). Citizen A is signed in |
| Test Data | TD-02 (Citizen A) |
| Test Steps | 1. Open `/admin`; read the page. 2. Open `/admin/issues`; read the page. 3. Open `/admin/update-issue`; read the page |
| Expected Result | Each page shows "Administrator access required". This gate is client-side only; server-side enforcement is checked by API-09 |
| Actual Result | Not executed in the group repository. Executed in the Member 3 copy (`93d11a3`, local Windows 11, Selenium 4.31.0, Chrome 157 headless): `PublicCareUiIT.adminPagesAreGatedForCitizen` passed (6 of 6 UI tests; a deliberate fault check showed the tests can fail). Not run in CI |
| Status | **PASS – Member 3 copy only** (not a group-repository result) |
| Execution details | Member 3 repository `PublicCare-QA-Member3-Ishani-` @ `93d11a3` (private), 24 Nov 2026. Evidence: [ui-automation/README.md](../3.5-3.6_member3/evidence/ui-automation/README.md) and screenshots. Selenium is not a dependency of the group repository (owner decision D-5 pending) |

#### UI-14 – Account menu keyboard behaviour (automated)

| Field | Content |
|---|---|
| Test Case ID | UI-14 |
| Requirement ID(s) | NFR-USA |
| Description | ArrowDown opens the account menu and focuses the first item; Escape closes it and returns focus to the menu button |
| Type / Priority | P / Medium |
| Preconditions | UI automation tool available (§7.6, OQ-12). Citizen A is signed in, so the account menu is shown |
| Test Data | TD-02 (Citizen A) |
| Test Steps | 1. Move the focus to the account menu button. 2. Press ArrowDown. 3. Check that the menu is open and which element has focus. 4. Press Escape. 5. Check that the menu is closed and which element has focus |
| Expected Result | Step 3: the menu is open and the first menu item has focus. Step 5: the menu is closed and the account menu button has focus |
| Actual Result | NOT EXECUTED |
| Status | **NOT EXECUTED** |
| Execution details | Not executed in any copy. No UI automation exists for this case, and Selenium is not a dependency of the group repository (owner decision D-5 pending) |

**C. Automated API test cases (API-01 – API-25)**

#### API-01 – CSRF token endpoint

| Field | Content |
|---|---|
| Test Case ID | API-01 |
| Requirement ID(s) | FR-02.1 |
| Description | `GET /api/auth/csrf` returns a token and sets the `XSRF-TOKEN` cookie |
| Type / Priority | P / High |
| Preconditions | Backend running in the test environment. An API client that keeps cookies is available (REST Assured or Postman, not installed, §7.6). The client holds no cookies |
| Test Data | None |
| Test Steps | 1. Send `GET /api/auth/csrf`. 2. Record the status code, the response body and the `Set-Cookie` headers |
| Expected Result | `200` with a body `{token}` holding a non-empty token, and a `Set-Cookie` header for `XSRF-TOKEN` |
| Actual Result | `200`; body `{token}` with a 96-character token; `Set-Cookie: XSRF-TOKEN=…; Path=/; SameSite=Lax`. |
| Status | **PASS** |
| Execution details | Executed 25 Nov 2026 by integration (Phase 11, on the project owner's instruction; not by a named member). Group baseline `f565fb4` + uncommitted D-1 change; production code SHA-256-identical to the repository (no source change). Environment ENV-P11 (§11.3). Test method `Part01ApiExecutionTest.api01`. Evidence: [execution record](../phase11-part01-exec/README.md#api-01), lines `| API-01 |` in [run2/part01-observations.txt](../phase11-part01-exec/run2/part01-observations.txt) |

#### API-02 – Registration creates a Citizen and returns no password data

| Field | Content |
|---|---|
| Test Case ID | API-02 |
| Requirement ID(s) | FR-01.1, FR-01.5 |
| Description | A valid registration that also sends `"role":"ADMIN"` creates a CITIZEN account and returns no password fields |
| Type / Priority | P/N / High |
| Preconditions | Backend running. API client that keeps cookies. The email is not registered |
| Test Data | TD-01 (valid name, unused email, valid password) |
| Test Steps | 1. Get a token with `GET /api/auth/csrf`. 2. Send `POST /api/auth/register` with the token and a body holding valid `fullName`, `email`, `password` and `"role":"ADMIN"`. 3. Record the status code and body. 4. Sign in with the new account and send `GET /api/auth/me`; record the role |
| Expected Result | Step 3: `201 Created`; the body has `id`, `fullName`, `email`, `role` and `createdAt`; `role` is `CITIZEN`; there is no `password` or `passwordHash` field. Step 4: `role` is `CITIZEN` |
| Actual Result | Step 3: `201`; body fields exactly `id, fullName, email, role, createdAt`; `role` = `CITIZEN` although the request sent `"role":"ADMIN"`; no password or hash field. Step 4: login `200`; `/api/auth/me` `200`, `role` = `CITIZEN`. |
| Status | **PASS** |
| Execution details | Executed 25 Nov 2026 by integration (Phase 11, on the project owner's instruction; not by a named member). Group baseline `f565fb4` + uncommitted D-1 change; production code SHA-256-identical to the repository (no source change). Environment ENV-P11 (§11.3). Test method `Part01ApiExecutionTest.api02`. Evidence: [execution record](../phase11-part01-exec/README.md#api-02), lines `| API-02 |` in [run2/part01-observations.txt](../phase11-part01-exec/run2/part01-observations.txt) |

#### API-03 – Registration input boundaries

| Field | Content |
|---|---|
| Test Case ID | API-03 |
| Requirement ID(s) | FR-01.2 |
| Description | Boundary and invalid values for name, password and email at `POST /api/auth/register` |
| Type / Priority | B/N / High |
| Preconditions | Backend running. API client that keeps cookies, with a CSRF token |
| Test Data | TD-01 (name 120/121 characters; password 11/12/72/73 characters; 37 × "é" (37 characters, 74 bytes); email 254/255 characters in a valid format; blank and whitespace-only values; a new email for each request) |
| Test Steps | 1. For each value, send one registration request with that value and all other fields valid. 2. Record the status code and body of each |
| Expected Result | Name 120: `201`; 121: `400`. Password 12 and 72: `201`; 11 and 73: `400`. 37 × "é": `400` (within the character limit but over 72 bytes). Email 254: `201`; 255: `400`. Blank and whitespace-only fields: `400`. Each `400` body carries the first field error (FR-01.2). The 254-character email depends on assumption A-3 (§12.4) |
| Actual Result | Name 120 → `201`, 121 → `400` "fullName size must be between 0 and 120". Password 12 and 72 → `201`; 11 and 73 → `400` "password size must be between 12 and 72"; 37 × "é" (74 bytes) → `400` "Password must be at most 72 UTF-8 bytes.". Email 254 → `201`; 255 → `400` "email size must be between 0 and 254". Blank and whitespace-only name, email and password → `400`, each with the first field error (for example "fullName must not be blank"). Assumption A-3 held: a 254-character address in valid format was accepted. |
| Status | **PASS** |
| Execution details | Executed 25 Nov 2026 by integration (Phase 11, on the project owner's instruction; not by a named member). Group baseline `f565fb4` + uncommitted D-1 change; production code SHA-256-identical to the repository (no source change). Environment ENV-P11 (§11.3). Test method `Part01ApiExecutionTest.api03`. Evidence: [execution record](../phase11-part01-exec/README.md#api-03), lines `| API-03 |` in [run2/part01-observations.txt](../phase11-part01-exec/run2/part01-observations.txt) |

#### API-04 – Duplicate email with different case or padding

| Field | Content |
|---|---|
| Test Case ID | API-04 |
| Requirement ID(s) | FR-01.3, FR-01.4 |
| Description | An email that differs from a registered one only in case or surrounding spaces is rejected with `409`; the first registration stores the normalised email and name. *Owner decision D-1 (Option A, RTM "Owner decisions"):* surrounding whitespace in the email is trimmed **at the API boundary, before Bean Validation** (`RegisterRequest`/`LoginRequest` compact constructors, `AuthDtos.java:18-20, 26-28, 38-40`); `AuthService` then lower-cases it. Passwords are not trimmed |
| Type / Priority | N / High |
| Preconditions | Backend running. API client that keeps cookies, with a CSRF token. The email is not registered before the test |
| Test Data | TD-01 (an email in the form `" A@X.com "` on the test domain and a name with surrounding spaces; the same email in lower case, and in upper case with spaces) |
| Test Steps | 1. Register with the padded mixed-case email and the padded name. 2. Record the `email` and `fullName` in the response. 3. Register again with the lower-case email. 4. Register again with the upper-case email with surrounding spaces. 5. Record each status code and body |
| Expected Result | Steps 1–2: `201`; the padded email passes validation because it is trimmed first, and is stored and returned trimmed and lower-cased (`" A@X.com "` → `a@x.com`); the name is trimmed (FR-01.3). Steps 3–4: `409` "An account with this email already exists." (FR-01.4); still only one account for the address |
| Actual Result | Executed 25 Nov 2026 on branch `qa/final-assignment` = baseline `f565fb4` **plus the uncommitted D-1 change to `AuthDtos.java`**. Automated with `AuthEmailNormalizationApiTest` (Spring Boot `@SpringBootTest` + MockMvc: full application, real security filter chain with CSRF, validation, exception handler, `AuthService` and JPA, on in-memory H2 in PostgreSQL mode). Step 1 with `"  Api04.User-<random>@Example.TEST  "` and `"  Synthetic Citizen  "`: `201`. Step 2: response and stored `email` = `api04.user-<random>@example.test`, `fullName` = `Synthetic Citizen`, role `CITIZEN`, no `password`/`passwordHash`. Step 3 (lower-case): `409` "An account with this email already exists.". Step 4 (upper-case with spaces): `409`, same body. One stored account for the address. Additional checks in the same class: a padded (spaces, tab) duplicate of an **unpadded** account → `409` (was `400` before the change, finding F-03); padded upper-case login → `200` and `/api/auth/me` returns the normalised email; `"bad"`, `"  bad  "`, `"   "`, `""` and a missing email → `400`; a 254-character email with padding → `201`; a password with surrounding spaces is stored and checked unchanged (the trimmed password gives `401`). Before the change the same tests gave `400` "email must be a well-formed email address" for every padded email (run captured, 9 of 13 new tests failed). After the change: 13/13 new tests pass; full backend suite 85 tests, 0 failures, 0 errors (72 existing + 13 new). Environment: Windows 11, **Java 27** (Oracle, build 27+35; the project targets Java 21), Maven 3.9.16 wrapper, Spring Boot 3.5.16. Not covered: PostgreSQL (H2 only), a real HTTP server/browser. Evidence: `qa/phase3a-api04-correction/` |
| Status | PASS |
| Execution details | Executed 25 Nov 2026 (integration Phase 3A) after owner decision D-1; Oracle Java 27, Maven Wrapper 3.9.16, H2, MockMvc. Evidence: [phase3a-api04-correction/](../phase3a-api04-correction/README.md). Tester: PENDING (P-OW-4) |

#### API-05 – State-changing requests without a CSRF token are rejected

| Field | Content |
|---|---|
| Test Case ID | API-05 |
| Requirement ID(s) | FR-02.1, NFR-SEC |
| Description | State-changing requests without the `X-XSRF-TOKEN` header are refused, and the refusal is caused by the missing token, not by authorisation. *(Revised in the Part 01 review: the citizen API has no PATCH or DELETE routes, so those requests are now sent as Admin, and control requests were added.)* |
| Type / Priority | N / High |
| Preconditions | Backend running in the test environment. An API client that keeps cookies is available. A Citizen A session and an Admin session exist. A report owned by Citizen A exists, and a disposable report exists for the admin DELETE |
| Test Data | TD-01 (new registration body), TD-02 (Citizen A), TD-04 (Admin), TD-05 (valid report body), TD-06 (reports), TD-07 (progress update data) |
| Test Steps | 1. Without the header, send `POST /api/auth/register` (valid body) and `POST /api/auth/login` (valid credentials); with a valid session, send `POST /api/auth/logout`. 2. As Citizen A, without the header, send `POST /api/reports`, `PUT /api/reports/{id}` and `POST /api/reports/{id}/image`. 3. As Admin, without the header, send `POST /api/admin/reports/{id}/progress-updates`, `PATCH /api/admin/reports/{id}/status` and `DELETE /api/admin/reports/{id}`. 4. Record each status code and body. 5. Control: get a token with `GET /api/auth/csrf` and repeat each request with the `X-XSRF-TOKEN` header, using fresh data where needed (for example a new email for register). Send the logout control last, because it ends the session. 6. Record each status code |
| Expected Result | Steps 1–3: each request returns `403` with `{"error":"Access denied."}`, and no data is changed. Step 5: no control request returns `403`; each returns its normal success status. **Verification note:** for register, report create, image upload and the three admin requests, the `403` is supported by the security configuration and by existing slice tests in the code base (*[Phase 3A]* these passed in the backend suite run on this branch, Java 27; that run does not execute API-05). For login, logout and `PUT /api/reports/{id}`, it is derived from the configuration only (CSRF on every path, no exemptions) and must be confirmed by execution (RTM Q7) |
| Actual Result | Steps 1–4: all 9 requests without `X-XSRF-TOKEN` (register, login, logout; citizen create, PUT, image upload; admin progress update, status PATCH, DELETE) returned `403 {"error":"Access denied."}`. No data changed: report count 2 → 2, the report kept its title and status `PENDING`, the disposable report still existed, and no account was created. Step 5: every control request with the token succeeded (register `201`, login `200`, create `201`, PUT `200`, image `204`, progress update `201`, PATCH `200`, DELETE `204`, logout `204`). The RTM Q7 part (login, logout, PUT) is now confirmed by execution. |
| Status | **PASS** |
| Execution details | Executed 25 Nov 2026 by integration (Phase 11, on the project owner's instruction; not by a named member). Group baseline `f565fb4` + uncommitted D-1 change; production code SHA-256-identical to the repository (no source change). Environment ENV-P11 (§11.3). Test method `Part01ApiExecutionTest.api05`. Evidence: [execution record](../phase11-part01-exec/README.md#api-05), lines `| API-05 |` in [run2/part01-observations.txt](../phase11-part01-exec/run2/part01-observations.txt) |

#### API-06 – Login starts a session and rotates the session ID

| Field | Content |
|---|---|
| Test Case ID | API-06 |
| Requirement ID(s) | FR-02.2 |
| Description | A valid login returns `200`, sets an HTTP-only, SameSite=Lax `JSESSIONID`, and the session ID differs from the one held before login |
| Type / Priority | P / High |
| Preconditions | Backend running. Citizen A exists. API client that keeps cookies, with a CSRF token. The client holds a session ID from before login. The source documents do not say how a pre-login session is created; the method is to be agreed during peer review |
| Test Data | TD-02 (Citizen A) |
| Test Steps | 1. Record the `JSESSIONID` held before login. 2. Send `POST /api/auth/login` with Citizen A's credentials and the token. 3. Record the status code, the body and the `Set-Cookie` attributes. 4. Compare the new `JSESSIONID` with step 1. 5. Send `GET /api/auth/me` |
| Expected Result | Step 3: `200` with the user fields; `JSESSIONID` has `HttpOnly` and `SameSite=Lax`. Step 4: the session ID differs from the pre-login ID. Step 5: `200`. The `Secure` attribute depends on the configuration (default `false`, OQ-6); it is recorded, not judged |
| Actual Result | Step 1: **no** `JSESSIONID` existed before login (`GET /api/auth/csrf` does not create a session). The method agreed for this run: the pre-login session is the session of an earlier login by the same client. Step 3: `200` with the user fields; `Set-Cookie: JSESSIONID=<id>; Path=/; HttpOnly; SameSite=Lax`; `Secure` absent (default `SESSION_COOKIE_SECURE=false`, OQ-6 – recorded, not judged). Step 4: a second login while holding that session returned a **different** session ID. Step 5: `/api/auth/me` `200`. |
| Status | **PASS** |
| Execution details | Executed 25 Nov 2026 by integration (Phase 11, on the project owner's instruction; not by a named member). Group baseline `f565fb4` + uncommitted D-1 change; production code SHA-256-identical to the repository (no source change). Environment ENV-P11 (§11.3). Test method `Part01ApiExecutionTest.api06`. Evidence: [execution record](../phase11-part01-exec/README.md#api-06), lines `| API-06 |` in [run2/part01-observations.txt](../phase11-part01-exec/run2/part01-observations.txt) |

#### API-07 – Generic 401 and 72-byte password limit on login

| Field | Content |
|---|---|
| Test Case ID | API-07 |
| Requirement ID(s) | FR-02.3, FR-02.4 |
| Description | A wrong password and an unknown email return identical `401` bodies; a 73-byte password returns `400` |
| Type / Priority | N/B / High |
| Preconditions | Backend running. Citizen A exists. API client that keeps cookies, with a CSRF token |
| Test Data | TD-02 (Citizen A email with a wrong password); an unregistered email from TD-01; a password of 73 bytes (TD-01) |
| Test Steps | 1. Send `POST /api/auth/login` with Citizen A's email and a wrong password. 2. Send it with an unregistered email and any password. 3. Compare the status codes and bodies. 4. Send it with Citizen A's email and a 73-byte password. 5. Record the status code |
| Expected Result | Steps 1–3: both return `401` with identical bodies holding "Invalid email or password.". Step 4: `400` (not `401`) |
| Actual Result | Steps 1–3: wrong password and unregistered email both `401` with identical bodies `{"error":"Invalid email or password."}`. Step 4: 73-byte password → `400` (73 ASCII characters: "password size must be between 0 and 72"; 37 characters / 73 UTF-8 bytes: "Password must be at most 72 UTF-8 bytes."). Never `401`. |
| Status | **PASS** |
| Execution details | Executed 25 Nov 2026 by integration (Phase 11, on the project owner's instruction; not by a named member). Group baseline `f565fb4` + uncommitted D-1 change; production code SHA-256-identical to the repository (no source change). Environment ENV-P11 (§11.3). Test method `Part01ApiExecutionTest.api07`. Evidence: [execution record](../phase11-part01-exec/README.md#api-07), lines `| API-07 |` in [run2/part01-observations.txt](../phase11-part01-exec/run2/part01-observations.txt) |

#### API-08 – Current user before, during and after a session

| Field | Content |
|---|---|
| Test Case ID | API-08 |
| Requirement ID(s) | FR-02.5, FR-02.6 |
| Description | `GET /api/auth/me` without a session, with a session, and after logout; logout ends the session |
| Type / Priority | P/N / High |
| Preconditions | Backend running. Citizen A exists. API client that keeps cookies |
| Test Data | TD-02 (Citizen A) |
| Test Steps | 1. Send `GET /api/auth/me` with no session. 2. Sign in as Citizen A and send `GET /api/auth/me`. 3. Send `POST /api/auth/logout` with a valid CSRF token. 4. Record the status code and `Set-Cookie` headers. 5. Send `GET /api/auth/me` with the same client |
| Expected Result | Step 1: `401` `{"error":"Authentication required."}`. Step 2: `200` with Citizen A's user fields. Steps 3–4: `204`; the `JSESSIONID` and `XSRF-TOKEN` cookies are deleted. Step 5: `401` |
| Actual Result | Step 1: `401 {"error":"Authentication required."}`. Step 2: `200` with Citizen A's fields. Steps 3–4: logout `204`; `Set-Cookie` deletes `JSESSIONID` and `XSRF-TOKEN` (`Max-Age=0`). Step 5: `401`. Extra check: replaying the old `JSESSIONID` from a new client also returned `401`. |
| Status | **PASS** |
| Execution details | Executed 25 Nov 2026 by integration (Phase 11, on the project owner's instruction; not by a named member). Group baseline `f565fb4` + uncommitted D-1 change; production code SHA-256-identical to the repository (no source change). Environment ENV-P11 (§11.3). Test method `Part01ApiExecutionTest.api08`. Evidence: [execution record](../phase11-part01-exec/README.md#api-08), lines `| API-08 |` in [run2/part01-observations.txt](../phase11-part01-exec/run2/part01-observations.txt) |

#### API-09 – Authorisation matrix for every admin endpoint

| Field | Content |
|---|---|
| Test Case ID | API-09 |
| Requirement ID(s) | FR-02.8, FR-06.4, NFR-SEC |
| Description | Every `/api/admin/**` endpoint is called as anonymous, as a citizen and as an admin |
| Type / Priority | N/P / High |
| Preconditions | Backend running. Three API clients that keep cookies: anonymous, Citizen A session, Admin session. Each client sends a valid CSRF token on state-changing requests, so that the result reflects authorisation and not CSRF (see API-05). A report owned by Citizen A with an image and a progress update with a note and photo exists; a disposable report exists for DELETE |
| Test Data | TD-02 (Citizen A), TD-04 (Admin), TD-06 (reports), TD-07 (progress update data), TD-08 (valid JPG) |
| Test Steps | 1. List the admin endpoints from TASK 1: `GET /api/admin/reports`, `GET …/summary`, `GET …/{id}`, `GET …/{id}/image`, `GET …/{id}/progress-updates`, `GET …/progress-updates/{updateId}/image`, `POST …/{id}/progress-updates`, `PATCH …/{id}/status`, `PATCH …/{id}/public-approval`, `PATCH …/progress-updates/{updateId}/public-approval`, the note approval route (`…/note-public-approval`; exact path to be taken from `AdminReportController` during design) and `DELETE …/{id}`. 2. Send each as anonymous. 3. Send each as Citizen A. 4. Send each as Admin with valid data, running DELETE last. 5. Record each status code |
| Expected Result | Anonymous: `401`. Citizen: `403`. Admin: `2xx`. Anonymous and citizen requests change no data |
| Actual Result | All 12 admin endpoints (including `…/note-public-approval`, path taken from `AdminReportController`): anonymous `401` (12/12), Citizen A `403` (12/12), Admin `2xx` (12/12: eleven `200`/`201`, DELETE `204`, run last). After the anonymous and citizen requests the report was unchanged (status, `publicApproved = false`, 1 history row) and the disposable report still existed. |
| Status | **PASS** |
| Execution details | Executed 25 Nov 2026 by integration (Phase 11, on the project owner's instruction; not by a named member). Group baseline `f565fb4` + uncommitted D-1 change; production code SHA-256-identical to the repository (no source change). Environment ENV-P11 (§11.3). Test method `Part01ApiExecutionTest.api09`. Evidence: [execution record](../phase11-part01-exec/README.md#api-09), lines `| API-09 |` in [run2/part01-observations.txt](../phase11-part01-exec/run2/part01-observations.txt) |

#### API-10 – Create report; owner is taken from the session

| Field | Content |
|---|---|
| Test Case ID | API-10 |
| Requirement ID(s) | FR-03.1, FR-03.3 |
| Description | A citizen creates a report with another user's `ownerId` in the body; the report is created as PENDING and owned by the session user |
| Type / Priority | P/N / High |
| Preconditions | Backend running. Citizen A session with a CSRF token. Citizen B exists and B's user ID is known. Admin session (to read the owner) |
| Test Data | TD-02 (Citizen A), TD-03 (Citizen B's ID), TD-04 (Admin), TD-05 (valid report fields) |
| Test Steps | 1. As Citizen A, send `POST /api/reports` with valid title, category, description and location and `"ownerId": <Citizen B's ID>`. 2. Record the status code and body. 3. As Admin, send `GET /api/admin/reports/{id}` and record the owner name and ID |
| Expected Result | Step 2: `201`; status `PENDING`; the reference matches `^PC-\d{8}$`; the owner name and timestamps are returned. Step 3: the owner is Citizen A (`ownerId` = session user), not Citizen B |
| Actual Result | Step 2: `201`; status `PENDING`; reference `PC-00000006` (matches `^PC-\d{8}$`); owner name and both timestamps returned. Step 3: admin view `ownerId` = 13 (Citizen A, the session user), **not** 14 (Citizen B, sent in the body). H2 note: H2 has no generated column, so the `201` response used the application's own fallback (`ReportService.response`, `PC-%08d`); on PostgreSQL the database generates it (V2, §11.1 ENV-P11). |
| Status | **PASS** |
| Execution details | Executed 25 Nov 2026 by integration (Phase 11, on the project owner's instruction; not by a named member). Group baseline `f565fb4` + uncommitted D-1 change; production code SHA-256-identical to the repository (no source change). Environment ENV-P11 (§11.3). Test method `Part01ApiExecutionTest.api10`. Evidence: [execution record](../phase11-part01-exec/README.md#api-10), lines `| API-10 |` in [run2/part01-observations.txt](../phase11-part01-exec/run2/part01-observations.txt) |

#### API-11 – Report field boundaries and trimming

| Field | Content |
|---|---|
| Test Case ID | API-11 |
| Requirement ID(s) | FR-03.2, FR-03.4 |
| Description | Boundary, blank and padded values for the report fields at `POST /api/reports`, and a category that is not in the UI list |
| Type / Priority | B/N / High |
| Preconditions | Backend running. Citizen A session with a CSRF token |
| Test Data | TD-05 (title 160/161; description 5000/5001; category 80/81; location 240/241; `"   "` for each field; values with surrounding spaces; a category not in the UI list, ≤ 80 characters) |
| Test Steps | 1. For each value, send one `POST /api/reports` with that value and all other fields valid. 2. Record each status code and, for `201`, the stored values in the response |
| Expected Result | Title 160, description 5000, category 80, location 240: `201`. Title 161, description 5001, category 81, location 241: `400`. `"   "` in any field: `400`. Values with surrounding spaces: `201`, with the values stored trimmed. Category not in the UI list: `201`, because the backend accepts any string up to 80 characters (FR-03.4) |
| Actual Result | Title 160, description 5000, category 80, location 240 → `201`. Title 161, description 5001, category 81, location 241 → `400` (for example "title size must be between 0 and 160"). `"   "` in each field → `400` "… must not be blank". Padded values → `201`, stored trimmed (`"Padded title"`, `"Roads"`, …). Category not in the UI list → `201`. |
| Status | **PASS** |
| Execution details | Executed 25 Nov 2026 by integration (Phase 11, on the project owner's instruction; not by a named member). Group baseline `f565fb4` + uncommitted D-1 change; production code SHA-256-identical to the repository (no source change). Environment ENV-P11 (§11.3). Test method `Part01ApiExecutionTest.api11`. Evidence: [execution record](../phase11-part01-exec/README.md#api-11), lines `| API-11 |` in [run2/part01-observations.txt](../phase11-part01-exec/run2/part01-observations.txt) |

#### API-12 – Report image upload rules

| Field | Content |
|---|---|
| Test Case ID | API-12 |
| Requirement ID(s) | FR-03.5, FR-03.6 |
| Description | Valid and invalid images, a second upload, size boundaries, a non-owner and an anonymous client at `POST /api/reports/{id}/image` (multipart field `image`) |
| Type / Priority | P/N/B / High |
| Preconditions | Backend running. Citizen A session with a CSRF token; several reports owned by Citizen A without an image (a separate report for each upload that should succeed). Citizen B session. Anonymous client |
| Test Data | TD-02, TD-03, TD-06, TD-08 (valid JPG, PNG and WebP; GIF; text file renamed `.png`; 0-byte file; valid image of exactly 5,242,880 bytes; image of 5,242,881 bytes; image over 10 megapixels) |
| Test Steps | As Citizen A unless stated: 1. Upload the JPG, PNG and WebP, each to its own report. 2. Upload a second image to the JPG report. 3. Upload the GIF, the renamed text file and the 0-byte file to a report without an image. 4. Upload the 5,242,880-byte image to its own report. 5. Upload the 5,242,881-byte image to a report without an image. 6. Upload the image over 10 MP to a report without an image. 7. As Citizen B, upload a valid image to one of Citizen A's reports without an image. 8. As anonymous, do the same. 9. Record each status code |
| Expected Result | Step 1: `204` each. Step 2: `409`. Step 3: `400` each. Step 4: `204`. Step 5: `400` or `413`. **Exact code pending confirmation:** TASK 6 gives both, because it depends on whether the service check (`400`) or the multipart limit (`413`) rejects the file first (FR-03.6); any other code fails the check. Step 6: `400`. Step 7: `404`. Step 8: `401`. Image files with the exact sizes depend on assumption A-2 (§12.4) |
| Actual Result | Step 1: JPG, PNG and WebP (synthetic minimal VP8L structure) `204` each. Step 2: `409`. Step 3: GIF, renamed text file, 0-byte file `400` each. Step 4: valid PNG padded to exactly 5,242,880 bytes `204`. Step 5: 5,242,881 bytes → **`413`** (the multipart limit rejects it first; `413` is one of the two codes the case allows). Step 6: 4000 × 2501 px → `400`. Step 7: Citizen B → `404`. Step 8: anonymous → `401`. Assumption A-2 held. |
| Status | **PASS** |
| Execution details | Executed 25 Nov 2026 by integration (Phase 11, on the project owner's instruction; not by a named member). Group baseline `f565fb4` + uncommitted D-1 change; production code SHA-256-identical to the repository (no source change). Environment ENV-P11 (§11.3). Test method `Part01ApiExecutionTest.api12`. Evidence: [execution record](../phase11-part01-exec/README.md#api-12), lines `| API-12 |` in [run2/part01-observations.txt](../phase11-part01-exec/run2/part01-observations.txt) |

#### API-13 – Image retrieval headers and access scope

| Field | Content |
|---|---|
| Test Case ID | API-13 |
| Requirement ID(s) | FR-04.3, FR-06.4 |
| Description | Image GET responses for the owner, a non-owner, the admin route and a report without an image |
| Type / Priority | P/N / High |
| Preconditions | Backend running. A report owned by Citizen A with an image, and a report owned by Citizen A without an image. Sessions for Citizen A, Citizen B and Admin |
| Test Data | TD-02, TD-03, TD-04, TD-06 (with and without a photo) |
| Test Steps | 1. As Citizen A, send `GET /api/reports/{id}/image` for the report with an image. 2. As Citizen B, send the same request. 3. As Admin, send `GET /api/admin/reports/{id}/image` for the same report. 4. As Citizen A, send `GET /api/reports/{id}/image` for the report without an image. 5. Record each status code and the `Cache-Control`, `X-Content-Type-Options` and `Content-Type` headers |
| Expected Result | Steps 1 and 3: the image bytes are returned with `Cache-Control: no-store`, `X-Content-Type-Options: nosniff` and the `Content-Type` of the stored image. Step 2: `404`. Step 4: `404` |
| Actual Result | Steps 1 and 3 (owner; admin endpoint): `200`, bytes identical to the upload, `Content-Type: image/png`, `Cache-Control: no-store`, `X-Content-Type-Options: nosniff`. Step 2 (Citizen B): `404 {"error":"Report not found."}`. Step 4 (report without image): `404`. |
| Status | **PASS** |
| Execution details | Executed 25 Nov 2026 by integration (Phase 11, on the project owner's instruction; not by a named member). Group baseline `f565fb4` + uncommitted D-1 change; production code SHA-256-identical to the repository (no source change). Environment ENV-P11 (§11.3). Test method `Part01ApiExecutionTest.api13`. Evidence: [execution record](../phase11-part01-exec/README.md#api-13), lines `| API-13 |` in [run2/part01-observations.txt](../phase11-part01-exec/run2/part01-observations.txt) |

#### API-14 – List endpoints: paging, sort, filters, summary and owner scope

| Field | Content |
|---|---|
| Test Case ID | API-14 |
| Requirement ID(s) | FR-04.1, FR-04.7, FR-06.2, NFR-FUNC |
| Description | Paging, sort and filter parameters on `/api/reports/mine` and `/api/admin/reports`; summary counts against `totalElements`; Citizen A cannot see Citizen B's reports |
| Type / Priority | B/N/P / High |
| Preconditions | Backend running. Citizen A and Citizen B each own reports in more than one status and category. Admin session. The expected counts are worked out from the test data before the test |
| Test Data | TD-02, TD-03, TD-04, TD-06, TD-09, TD-10 (page −1; size 0/1/100/101; sort `foo`; status `in progress`/`ALL`/`DONE`; search text matching a reference) |
| Test Steps | Run steps 1–6 as Citizen A on `GET /api/reports/mine` and as Admin on `GET /api/admin/reports`: 1. `page=-1`. 2. `size=0`, `1`, `100`, `101`. 3. `sort=foo`. 4. `status=in progress`, `ALL`, `DONE`. 5. `q=<reference of one of Citizen A's reports>`. 6. For each valid request, compare the summary counts with `totalElements`. 7. As Citizen A, check that no report of Citizen B appears in any `/mine` response. 8. As Admin, check that reports of both owners appear |
| Expected Result | `page=-1`, `size=0`, `size=101`, `sort=foo` and `status=DONE`: `400` (`DONE`: "Unsupported report status."). `size=1` and `size=100`: `200` with at most that many items. `in progress`: only IN_PROGRESS reports. `ALL`: no status filter. `q` with a reference: that report is returned. The summary total equals `totalElements` for the same filters. `/mine` never contains Citizen B's reports; the admin list contains reports of all owners |
| Actual Result | On both `/api/reports/mine` and `/api/admin/reports`: `page=-1`, `size=0`, `size=101` → `400` "Page must be non-negative and size must be between 1 and 100."; `sort=foo` → `400` "Unsupported sort field."; `status=DONE` → `400` "Unsupported report status.". `size=1` → 1 item; `size=100` → `200`. `status=in progress` → IN_PROGRESS only; `ALL` → no filter. `q=PC-00000021` → exactly that report. Summary `total` = `totalElements` for every valid request. Step 7: `/mine` held only Citizen A's 3 reports, none of Citizen B's. Step 8: the admin list held reports of both owners. |
| Status | **PASS** |
| Execution details | Executed 25 Nov 2026 by integration (Phase 11, on the project owner's instruction; not by a named member). Group baseline `f565fb4` + uncommitted D-1 change; production code SHA-256-identical to the repository (no source change). Environment ENV-P11 (§11.3). Test method `Part01ApiExecutionTest.api14`. Evidence: [execution record](../phase11-part01-exec/README.md#api-14), lines `| API-14 |` in [run2/part01-observations.txt](../phase11-part01-exec/run2/part01-observations.txt) |

#### API-15 – Cross-user access and editing an approved report

| Field | Content |
|---|---|
| Test Case ID | API-15 |
| Requirement ID(s) | FR-04.2, FR-04.4, FR-04.5 |
| Description | GET and PUT on another user's report and on a non-existent ID give identical `404` bodies; a PUT on the citizen's own approved report removes the approval and keeps the status |
| Type / Priority | N/P / High |
| Preconditions | Backend running. Citizen A session with a CSRF token. Citizen B owns a report. A report ID that does not exist is known. Citizen A owns a report approved for public display; its status is recorded. Admin session (to read the approval) |
| Test Data | TD-02, TD-03, TD-04, TD-05 (valid changed values), TD-06 (approved report) |
| Test Steps | 1. As Citizen A, send `GET /api/reports/{id}` for Citizen B's report and for the non-existent ID; compare. 2. As Citizen A, send `PUT /api/reports/{id}` with a valid body for Citizen B's report and for the non-existent ID; compare. 3. As Admin, check that Citizen B's report is unchanged. 4. As Citizen A, send `PUT /api/reports/{id}` with valid changed values for the approved own report; record the status code and body. 5. As Admin, send `GET /api/admin/reports/{id}` and record `publicApproved` and the status |
| Expected Result | Steps 1–3: identical `404` bodies "Report not found." for the other user's report and the missing report; Citizen B's report is unchanged. Step 4: the PUT succeeds and returns the new values (the success code is not stated in the source documents; it is recorded). Step 5: `publicApproved` is `false` and the status is unchanged. **Pending confirmation (RTM Q1, BR-09):** the code accepts edits at any status, including RESOLVED, but whether this is intended is open. If the approved report used here is RESOLVED, the "edit accepted" result of step 4 stays blocked until Q1 is answered |
| Actual Result | Steps 1–2: other user's report and missing ID gave identical `404 {"error":"Report not found."}` for GET and PUT. Step 3: Citizen B's report unchanged. Step 4: PUT on the own approved report (status `PENDING`, so the RTM Q1 / BR-09 condition did not apply) → `200` with the new values. Step 5: `publicApproved = false`, status still `PENDING`. |
| Status | **PASS** |
| Execution details | Executed 25 Nov 2026 by integration (Phase 11, on the project owner's instruction; not by a named member). Group baseline `f565fb4` + uncommitted D-1 change; production code SHA-256-identical to the repository (no source change). Environment ENV-P11 (§11.3). Test method `Part01ApiExecutionTest.api15`. Evidence: [execution record](../phase11-part01-exec/README.md#api-15), lines `| API-15 |` in [run2/part01-observations.txt](../phase11-part01-exec/run2/part01-observations.txt) |

#### API-16 – Direct status PATCH rules and approval revocation

| Field | Content |
|---|---|
| Test Case ID | API-16 |
| Requirement ID(s) | FR-05.2, FR-06.6, FR-07.7 |
| Description | `PATCH /api/admin/reports/{id}/status` with null and invalid status, note of 1000 and 1001 characters, the same status, a new status and a null note |
| Type / Priority | N/B/P / High |
| Preconditions | Backend running. Admin session with a CSRF token. A report owned by Citizen A, approved for public display, with status PENDING. Citizen A session (to read the latest note) |
| Test Data | TD-04, TD-02, TD-06 (approved PENDING report), TD-07 (notes of 1000 and 1001 characters) |
| Test Steps | 1. Send `{status: null}` with a note. 2. Send `{status: "DONE"}`. 3. Send `{status: "PENDING"}` (same status) with a 1000-character note; as Admin, read `publicApproved`. 4. Send a valid status with a 1001-character note. 5. As Citizen A, open the report and read the latest admin note. 6. Send `{status: "IN_PROGRESS"}` (new status) with a valid note; as Admin, read `publicApproved`. 7. Send `{status: "IN_PROGRESS", adminUpdateNote: null}`; as Citizen A, read the latest admin note. 8. Record each status code |
| Expected Result | Steps 1, 2 and 4: `400` and no change. Step 3: success (`2xx`; the exact code is not stated in the source documents) and `publicApproved` stays `true`. Step 5: the owner sees the 1000-character note as the latest admin note (FR-05.2). Step 6: success and `publicApproved` is `false`. Step 7: success and the latest note is cleared. **Pending confirmation (RTM Q1, BR-13):** the code accepts a same-status PATCH and any status transition; whether this is intended is open. The step 3 result follows the code and stays blocked as a pass criterion until Q1 is answered. Backward transitions (for example RESOLVED → PENDING) are not part of this case |
| Actual Result | Step 1 (`status: null`) → `400` "A valid status is required."; step 2 (`DONE`) → `400`; no change after either (status `PENDING`, still approved). Step 3 (same status + 1000-character note) → `200`, `publicApproved` stayed `true` (observed). Step 4 (1001-character note) → `400` "adminUpdateNote size must be between 0 and 1000", status unchanged. Step 5: the owner saw the 1000-character note. Step 6 (`IN_PROGRESS`) → `200`, `publicApproved = false`. Step 7 (note `null`) → `200`, the owner's latest note is `null`. |
| Status | **PASS** for steps 1, 2 and 4–7. Step 3: **BLOCKED** as a pass criterion (RTM Q1, BR-13) – observed result recorded, not judged |
| Execution details | Executed 25 Nov 2026 by integration (Phase 11, on the project owner's instruction; not by a named member). Group baseline `f565fb4` + uncommitted D-1 change; production code SHA-256-identical to the repository (no source change). Environment ENV-P11 (§11.3). Test method `Part01ApiExecutionTest.api16`. Evidence: [execution record](../phase11-part01-exec/README.md#api-16), lines `| API-16 |` in [run2/part01-observations.txt](../phase11-part01-exec/run2/part01-observations.txt) |

#### API-17 – Progress update creation is atomic; history order

| Field | Content |
|---|---|
| Test Case ID | API-17 |
| Requirement ID(s) | FR-05.3, FR-06.5, FR-06.7, NFR-FUNC |
| Description | `POST /api/admin/reports/{id}/progress-updates` (multipart) with a missing status, notes of 1000 and 1001 characters, a WebP photo, a corrupt JPG and a valid JPG; invalid input leaves no partial update; history is oldest first |
| Type / Priority | N/B/P / High |
| Preconditions | Backend running. Admin session with a CSRF token. A report owned by Citizen A with status PENDING and a known number of progress updates. Citizen A session |
| Test Data | TD-04, TD-02, TD-06, TD-07 (notes of 1000 and 1001 characters), TD-08 (WebP, corrupt JPG, valid JPG) |
| Test Steps | 1. Send a request without a status. 2. Send a valid status with a 1001-character note. 3. Send a valid status with the WebP photo. 4. Send a valid status with the corrupt JPG. 5. After each of steps 1–4, as Admin, read the report status and count the history rows. 6. Send `IN_PROGRESS` with a 1000-character note and the valid JPG. 7. Send a second valid update with status RESOLVED and a note. 8. As Admin, send `GET /api/admin/reports/{id}/progress-updates`. 9. As Citizen A, send `GET /api/reports/{id}/progress-updates` |
| Expected Result | Steps 1–5: `400` for each, with **no new history row and no status change**. Steps 6–7: `201` each; the report's current status and latest note are set from the update. Step 8: the history is oldest first, with the approval flags and photos (FR-06.7). Step 9: the owner's history is oldest first and holds all notes, whether approved or not (FR-05.3) |
| Actual Result | Steps 1–4: no status → `400` ("Required parameter 'status' is not present."); 1001-character note → `400`; WebP → `400` "Only valid JPG or PNG images are supported."; corrupt JPG → `400` "Uploaded file is not a valid JPG or PNG image.". Step 5: after each, status `PENDING` and 0 history rows. Steps 6–7: `201` each; status IN_PROGRESS then RESOLVED; latest note set from the update. Step 8: admin history oldest first (IN_PROGRESS, RESOLVED) with `publicApproved`, `notePublicApproved` and `hasImage = true` on the first. Step 9: owner history oldest first with both notes. |
| Status | **PASS** |
| Execution details | Executed 25 Nov 2026 by integration (Phase 11, on the project owner's instruction; not by a named member). Group baseline `f565fb4` + uncommitted D-1 change; production code SHA-256-identical to the repository (no source change). Environment ENV-P11 (§11.3). Test method `Part01ApiExecutionTest.api17`. Evidence: [execution record](../phase11-part01-exec/README.md#api-17), lines `| API-17 |` in [run2/part01-observations.txt](../phase11-part01-exec/run2/part01-observations.txt) |

#### API-18 – Public approval endpoints

| Field | Content |
|---|---|
| Test Case ID | API-18 |
| Requirement ID(s) | FR-07.1, FR-07.2, FR-07.3 |
| Description | Approve and revoke a report, an update and a note; approve a blank note; use an update ID that belongs to another report; send an empty body `{}` |
| Type / Priority | P/N/B / High |
| Preconditions | Backend running. Admin session with a CSRF token. Report R1 with update U1 (non-blank note) and update U2 (blank note). Report R2 with update U3 |
| Test Data | TD-04, TD-06, TD-07 (non-blank and blank notes) |
| Test Steps | 1. Send `PATCH /api/admin/reports/R1/public-approval` with `{approved: true}`, then `{approved: false}`; after each, read `publicApproved`. 2. Approve and revoke U1 with `PATCH …/R1/progress-updates/U1/public-approval`; read the flags. 3. Approve and revoke U1's note with the note approval route; read the flags. 4. Approve U2's blank note. 5. Send the update approval for R1 with U3's ID. 6. Approve R1 with `{approved: true}`, then send `{}`; record the response and `publicApproved` |
| Expected Result | Step 1: the response is `{approved: true}`, then `{approved: false}`, and `publicApproved` follows it. The approver ID and time are recorded (FR-07.1); no response field for them is documented, so they are checked in the test database. Steps 2–3: the flags follow each request. Step 4: `400`. Step 5: `404`. Step 6: **Blocked pending confirmation (RTM Q1, BR-22 [I]):** the code analysis says `{}` gives `approved: false` and revokes the approval, but whether this is intended is not confirmed. Record the observed result; do not judge it as pass or fail until Q1 is answered |
| Actual Result | Step 1: `{"approved":true}` then `{"approved":false}`; `publicApproved` followed; approver ID (admin id) and time stored in the database. Steps 2–3: update and note flags followed each approve/revoke request. Step 4: blank note → `400` "A non-empty note is required for public approval.". Step 5: U3 under R1 → `404`. Step 6 (observed): `{}` → `200 {"approved":false}` and the approval was **revoked**, as the code analysis predicted. |
| Status | **PASS** for steps 1–5. Step 6: **BLOCKED** (RTM Q1, BR-22 [I]) – observed result recorded, not judged |
| Execution details | Executed 25 Nov 2026 by integration (Phase 11, on the project owner's instruction; not by a named member). Group baseline `f565fb4` + uncommitted D-1 change; production code SHA-256-identical to the repository (no source change). Environment ENV-P11 (§11.3). Test method `Part01ApiExecutionTest.api18`. Evidence: [execution record](../phase11-part01-exec/README.md#api-18), lines `| API-18 |` in [run2/part01-observations.txt](../phase11-part01-exec/run2/part01-observations.txt) |

#### API-19 – Public feed and detail: paging, approval and data minimisation

| Field | Content |
|---|---|
| Test Case ID | API-19 |
| Requirement ID(s) | FR-07.4, FR-07.5, FR-07.6, NFR-FUNC |
| Description | Page size 0/1/24/25 and page −1 on the public feed; an unapproved ID; the response fields; a report with more than 10 approved updates; the public image route |
| Type / Priority | B/N/P / High |
| Preconditions | Backend running. Anonymous client. Approved and unapproved reports exist. One approved report has more than 10 approved updates, some with approved notes and some with unapproved notes |
| Test Data | TD-06 (including the report with more than 10 approved updates), TD-09 (more than 24 approved reports), TD-10 (size 0/1/24/25; page −1) |
| Test Steps | 1. Send `GET /api/public/reports` with `size=0`, `1`, `24` and `25`. 2. Send it with `page=-1`. 3. Send it with no parameters. 4. Send `GET /api/public/reports/{id}` for an unapproved report. 5. Send it for the approved report with more than 10 updates; record all fields. 6. Check its updates and notes. 7. Send `GET /api/public/reports/{id}/image` |
| Expected Result | Step 1: `size=0` and `size=25`: `400`; `size=1` and `size=24`: `200` with at most that many reports. Step 2: `400`. Step 3: `200`, approved reports only, newest first, default size 9. Step 4: `404`. Step 5: only `id`, `title`, `category`, `status`, `location`, `description`, `reportedAt` and updates with `{status, createdAt, approved note}`; no reference, owner, admin note or image fields. Step 6: at most 10 updates, the newest approved ones, newest first; a note appears only when the note is approved. Step 7: `401` |
| Actual Result | Step 1: `size=0` and `size=25` → `400`; `size=1` → 1 report, `size=24` → 24. Step 2: `page=-1` → `400`. Step 3: default page size 9, approved reports only, newest first. Step 4: unapproved report → `404`. Step 5: fields exactly `id, title, category, status, location, description, reportedAt, progressUpdates`; updates `{status, createdAt, note}`; no reference, owner, admin note or image field. Step 6: 10 of 12 approved updates, newest first, note shown only where approved. Step 7: `GET /api/public/reports/{id}/image` → `401`. |
| Status | **PASS** |
| Execution details | Executed 25 Nov 2026 by integration (Phase 11, on the project owner's instruction; not by a named member). Group baseline `f565fb4` + uncommitted D-1 change; production code SHA-256-identical to the repository (no source change). Environment ENV-P11 (§11.3). Test method `Part01ApiExecutionTest.api19`. Evidence: [execution record](../phase11-part01-exec/README.md#api-19), lines `| API-19 |` in [run2/part01-observations.txt](../phase11-part01-exec/run2/part01-observations.txt) |

#### API-20 – Admin delete and refused delete requests

| Field | Content |
|---|---|
| Test Case ID | API-20 |
| Requirement ID(s) | FR-08.1, FR-08.3, FR-08.5 |
| Description | Admin deletes a report (`204`, then `404`); a repeat delete, a citizen, a request without CSRF and an ID `abc` are refused |
| Type / Priority | P/N / High |
| Preconditions | Backend running. Admin and Citizen A sessions. A disposable report with an image and progress updates, and a second report that must not be deleted. The database is reset afterwards (§12.3) |
| Test Data | TD-02, TD-04, TD-06 (disposable reports) |
| Test Steps | 1. As Citizen A, send `DELETE /api/admin/reports/{id}` for the second report with a valid CSRF token. 2. As Admin, send the same request without the `X-XSRF-TOKEN` header. 3. As Admin, send `DELETE /api/admin/reports/abc` with a token. 4. As Admin, delete the disposable report with a token. 5. As Admin, send `GET /api/admin/reports/{id}` for it. 6. Repeat step 4. 7. As Admin, check that the second report still exists |
| Expected Result | Step 1: `403`. Step 2: `403`. Step 3: `400`. Step 4: `204`. Step 5: `404`. Step 6: `404`. Step 7: the second report is still returned |
| Actual Result | Step 1 (citizen) `403`; step 2 (admin, no token) `403`; step 3 (`/abc`) `400`; step 4 `204`; step 5 `404`; step 6 `404`; step 7 second report still returned (`200`). The deleted report's progress rows were removed (0 left). |
| Status | **PASS** |
| Execution details | Executed 25 Nov 2026 by integration (Phase 11, on the project owner's instruction; not by a named member). Group baseline `f565fb4` + uncommitted D-1 change; production code SHA-256-identical to the repository (no source change). Environment ENV-P11 (§11.3). Test method `Part01ApiExecutionTest.api20`. Evidence: [execution record](../phase11-part01-exec/README.md#api-20), lines `| API-20 |` in [run2/part01-observations.txt](../phase11-part01-exec/run2/part01-observations.txt) |

#### API-21 – Health endpoints

| Field | Content |
|---|---|
| Test Case ID | API-21 |
| Requirement ID(s) | FR-09.1 |
| Description | `GET /api/health` and `GET /actuator/health` without a session |
| Type / Priority | P / Low |
| Preconditions | Backend running with its database reachable. Anonymous API client |
| Test Data | None |
| Test Steps | 1. Send `GET /api/health`. 2. Send `GET /actuator/health`. 3. Record each status code and body |
| Expected Result | Step 1: `200` with `{"status":"UP"}`. Step 2: `200` with status `UP`; the response includes the database health (FR-09.1) |
| Actual Result | Step 1: `200 {"status":"UP"}`. Step 2: `200 {"status":"UP"}`. The body has **no** `components.db` entry, because health details are not exposed (Spring Boot default `show-details: never`). The database indicator is enabled in the configuration and contributes to the aggregate status, but this cannot be seen in the response; this part of the expected result is therefore not observable (observation OBS-3). |
| Status | **PASS** – with a limitation on step 2 |
| Execution details | Executed 25 Nov 2026 by integration (Phase 11, on the project owner's instruction; not by a named member). Group baseline `f565fb4` + uncommitted D-1 change; production code SHA-256-identical to the repository (no source change). Environment ENV-P11 (§11.3). Test method `Part01ApiExecutionTest.api21`. Evidence: [execution record](../phase11-part01-exec/README.md#api-21), lines `| API-21 |` in [run2/part01-observations.txt](../phase11-part01-exec/run2/part01-observations.txt) |

#### API-22 – CORS preflight for allowed and disallowed origins

| Field | Content |
|---|---|
| Test Case ID | API-22 |
| Requirement ID(s) | NFR-SEC, NFR-COMP |
| Description | A CORS preflight from `http://localhost:5173` is allowed with credentials; one from `http://evil.test` is rejected |
| Type / Priority | P/N / High |
| Preconditions | Backend running. The value of `PUBLICCARE_CORS_ALLOWED_ORIGIN` in the test environment is recorded and matches `http://localhost:5173` (§11.1) |
| Test Data | Origins `http://localhost:5173` and `http://evil.test` |
| Test Steps | 1. Send an `OPTIONS` preflight to `/api/reports` with `Origin: http://localhost:5173`, `Access-Control-Request-Method: POST` and `Access-Control-Request-Headers` naming the headers the SPA sends (`Content-Type`, `X-XSRF-TOKEN`). 2. Send the same preflight with `Origin: http://evil.test`. 3. Record each status code and all `Access-Control-*` headers |
| Expected Result | Step 1: allowed; `Access-Control-Allow-Origin` is `http://localhost:5173` and `Access-Control-Allow-Credentials` is `true`. Step 2: rejected; the response does not allow `http://evil.test`. The status code of a rejected preflight is not stated in the source documents; it is recorded |
| Actual Result | Step 1 (`http://localhost:5173`): `200`; `Access-Control-Allow-Origin: http://localhost:5173`, `Access-Control-Allow-Credentials: true`, allowed headers `Content-Type, X-XSRF-TOKEN`, methods `GET,POST,PUT,PATCH,DELETE,OPTIONS`, max age 3600. Step 2 (`http://evil.test`): `403` "Invalid CORS request", no `Access-Control-Allow-Origin` header. The allowed origin was the default value (no override in the test environment). |
| Status | **PASS** |
| Execution details | Executed 25 Nov 2026 by integration (Phase 11, on the project owner's instruction; not by a named member). Group baseline `f565fb4` + uncommitted D-1 change; production code SHA-256-identical to the repository (no source change). Environment ENV-P11 (§11.3). Test method `Part01ApiExecutionTest.api22`. Evidence: [execution record](../phase11-part01-exec/README.md#api-22), lines `| API-22 |` in [run2/part01-observations.txt](../phase11-part01-exec/run2/part01-observations.txt) |

#### API-23 – Server error does not leak details and the backend keeps serving

| Field | Content |
|---|---|
| Test Case ID | API-23 |
| Requirement ID(s) | NFR-SEC, NFR-REL |
| Description | A `500` caused by an unwritable upload directory does not expose a stack trace or exception message, and later requests still work |
| Type / Priority | N / High |
| Preconditions | Disposable test environment. The upload directory is made unwritable (an environment change, not an application change, §7.11). Citizen A session with a CSRF token; a report owned by Citizen A without an image |
| Test Data | TD-02, TD-06, TD-08 (valid JPG) |
| Test Steps | 1. Make the upload directory unwritable. 2. As Citizen A, upload the valid JPG to the report. 3. Record the status code and body. 4. Send `GET /api/health`. 5. Restore the upload directory |
| Expected Result | Step 3: `500`; the body contains no stack trace and no exception class name or message. Step 4: `200`, so the failure is limited to that request and the backend keeps serving |
| Actual Result | Fault injected by an environment change only: the upload-directory path was occupied by a regular file, so the directory could not be created (equivalent to an unwritable directory on Windows). Step 3: `500` with the standard body `{timestamp, status, error: "Internal Server Error", path}` – no stack trace, exception class or message. Step 4: `/api/health` `200 {"status":"UP"}`. |
| Status | **PASS** |
| Execution details | Executed 25 Nov 2026 by integration (Phase 11, on the project owner's instruction; not by a named member). Group baseline `f565fb4` + uncommitted D-1 change; production code SHA-256-identical to the repository (no source change). Environment ENV-P11 (§11.3). Test method `Part01Api23ServerErrorTest.api23`. Evidence: [execution record](../phase11-part01-exec/README.md#api-23), lines `| API-23 |` in [run2/part01-observations.txt](../phase11-part01-exec/run2/part01-observations.txt) |

#### API-24 – Two concurrent image uploads to the same report

| Field | Content |
|---|---|
| Test Case ID | API-24 |
| Requirement ID(s) | NFR-REL |
| Description | Two image uploads to the same report are sent at the same time |
| Type / Priority | N / Medium |
| Preconditions | Backend running. Citizen A session with a CSRF token; a report owned by Citizen A without an image. A tool that can send two requests at the same time (TBC, §7.6) |
| Test Data | TD-02, TD-06, TD-08 (two valid JPG files) |
| Test Steps | 1. Send two `POST /api/reports/{id}/image` requests to the same report at the same time. 2. Record both status codes and bodies. 3. Repeat on new reports several times, because the result depends on timing |
| Expected Result | TASK 6 expects one upload to succeed and the other to return `409`. **Blocked pending confirmation (RTM Q1, §2.3 [I]):** the code analysis suspects that the second request returns `500`, because the unique-constraint error has no handler. Until the intended behaviour is confirmed, record the observed codes; a `500` is reported for Q1, not judged as pass or fail |
| Actual Result | 5 rounds of two simultaneous uploads to a new report: round 2 → `204` + `409`; rounds 1, 3, 4 and 5 → `204` + **`500`** (H2 log: unique-index violation on `report_images.report_id`, no handler). Exactly 1 image was stored in every round, so data stayed consistent. The suspicion in the code analysis is confirmed: the losing request usually gets `500`, not `409`. |
| Status | **BLOCKED** (RTM Q1, §2.3 [I]) – executed; result recorded, not judged. Raised as defect **PC-QA-02** |
| Execution details | Executed 25 Nov 2026 by integration (Phase 11, on the project owner's instruction; not by a named member). Group baseline `f565fb4` + uncommitted D-1 change; production code SHA-256-identical to the repository (no source change). Environment ENV-P11 (§11.3). Test method `Part01ApiExecutionTest.api24`. Evidence: [execution record](../phase11-part01-exec/README.md#api-24), lines `| API-24 |` in [run2/part01-observations.txt](../phase11-part01-exec/run2/part01-observations.txt) |

#### API-25 – Admin summary endpoint

| Field | Content |
|---|---|
| Test Case ID | API-25 |
| Requirement ID(s) | FR-06.1, NFR-FUNC |
| Description | `GET /api/admin/reports/summary` without filters and with `status`, `category` and `q` filters, compared with `GET /api/admin/reports` using the same filters |
| Type / Priority | P / Medium |
| Preconditions | Backend running. Admin session. Reports owned by Citizen A and Citizen B in each status and in several categories. The expected counts are worked out from the test data before the test |
| Test Data | TD-02, TD-03, TD-04, TD-06 (known numbers per status and category), TD-10 (filter values) |
| Test Steps | 1. Send `GET /api/admin/reports/summary` with no filters. 2. Send it with a `status` filter, then a `category` filter, then a `q` filter. 3. For each request, send `GET /api/admin/reports` with the same filters and record `totalElements`. 4. Compare the counts with the expected values and with `totalElements` |
| Expected Result | `200` with `total`, `pending`, `inProgress`, `resolved` and `categories`. The counts match the test data and include all owners. `total` equals the list's `totalElements` for the same filters |
| Actual Result | No filters: summary `total` = list `totalElements` = number of reports in the database (67), and pending + inProgress + resolved = total. With `category`, `status + category` and `q` filters, each summary matched the counts worked out from the test data (for example `[3, 1, 1, 1]` for a category holding reports of both owners) and equalled the list's `totalElements`. |
| Status | **PASS** |
| Execution details | Executed 25 Nov 2026 by integration (Phase 11, on the project owner's instruction; not by a named member). Group baseline `f565fb4` + uncommitted D-1 change; production code SHA-256-identical to the repository (no source change). Environment ENV-P11 (§11.3). Test method `Part01ApiExecutionTest.api25`. Evidence: [execution record](../phase11-part01-exec/README.md#api-25), lines `| API-25 |` in [run2/part01-observations.txt](../phase11-part01-exec/run2/part01-observations.txt) |

**D. Unit test cases (UT-01 – UT-17)**

#### UT-01 – `AuthService.register`: normalisation, BCrypt hash and role

| Field | Content |
|---|---|
| Test Case ID | UT-01 |
| Requirement ID(s) | FR-01.1, FR-01.3, FR-01.5, FR-01.7 |
| Description | `AuthService.register` normalises the email and name, stores a BCrypt hash and forces the CITIZEN role. A test already exists in the code base ([AuthServiceTest.java:35](../../backend/src/test/java/lk/publiccare/backend/auth/AuthServiceTest.java#L35)). *[Phase 3A]* It passed in the backend suite run on this branch (Java 27); that run does not execute UT-01 as specified here, so UT-01 stays NOT EXECUTED |
| Type / Priority | P / High |
| Preconditions | ENV-02 (JUnit 5, Mockito). The user repository is mocked and the saved user is captured. The password encoder is the configured BCrypt encoder (cost 12). Location of new test code agreed (OQ-12, §7.11) |
| Test Data | TD-01 (name with surrounding spaces; email in the form `" A@X.com "`; valid password, generated at test time) |
| Test Steps | 1. Set the mock so that the email does not exist. 2. Call `register` with the padded name, the padded mixed-case email and the password. 3. Inspect the captured user and the returned response |
| Expected Result | The stored email is lower-cased and trimmed; the stored name is trimmed. The stored password is a BCrypt hash with cost 12 that matches the raw password and is not the raw password. The role is `CITIZEN`. The response holds no password or hash |
| Actual Result | Stored email `a@x.com`, name `Padded Name`; hash starts `$2a$12$` (BCrypt cost 12), matches the raw password and differs from it; role `CITIZEN`; `UserResponse` components `id, fullName, email, role, createdAt` (no password or hash). |
| Status | **PASS** |
| Execution details | Executed 25 Nov 2026 by integration (Phase 11, on the project owner's instruction; not by a named member). Group baseline `f565fb4` + uncommitted D-1 change; production code SHA-256-identical to the repository (no source change). Environment ENV-P11 (§11.3). Test method `Part01UnitExecutionTest.ut01`. Evidence: [execution record](../phase11-part01-exec/README.md#ut-01), lines `| UT-01 |` in [run2/part01-observations.txt](../phase11-part01-exec/run2/part01-observations.txt) |

#### UT-02 – Registration race: unique-constraint error becomes a duplicate-email error

| Field | Content |
|---|---|
| Test Case ID | UT-02 |
| Requirement ID(s) | FR-01.4 |
| Description | When `saveAndFlush` throws `DataIntegrityViolationException` (a concurrent duplicate), `AuthService.register` throws `DuplicateEmailException`. Existing coverage is a **gap**: only the pre-check is tested |
| Type / Priority | N / High |
| Preconditions | ENV-02. User repository mocked. Location of new test code agreed (OQ-12, §7.11) |
| Test Data | TD-01 (valid registration data) |
| Test Steps | 1. Set the mock so that the email pre-check finds no account. 2. Set `saveAndFlush` to throw `DataIntegrityViolationException`. 3. Call `register` |
| Expected Result | `DuplicateEmailException` is thrown (the API maps it to `409` "An account with this email already exists.", FR-01.4) |
| Actual Result | With the pre-check false and `saveAndFlush` throwing `DataIntegrityViolationException`, `register` threw `DuplicateEmailException`. |
| Status | **PASS** |
| Execution details | Executed 25 Nov 2026 by integration (Phase 11, on the project owner's instruction; not by a named member). Group baseline `f565fb4` + uncommitted D-1 change; production code SHA-256-identical to the repository (no source change). Environment ENV-P11 (§11.3). Test method `Part01UnitExecutionTest.ut02`. Evidence: [execution record](../phase11-part01-exec/README.md#ut-02), lines `| UT-02 |` in [run2/part01-observations.txt](../phase11-part01-exec/run2/part01-observations.txt) |

#### UT-03 – Password byte limit at registration

| Field | Content |
|---|---|
| Test Case ID | UT-03 |
| Requirement ID(s) | FR-01.2 |
| Description | Passwords of 72 and 73 UTF-8 bytes, mixing ASCII and multibyte characters, at the registration byte check. Partly covered by an existing test (37 × "é") |
| Type / Priority | B / High |
| Preconditions | ENV-02. Dependencies of `AuthService` mocked. Location of new test code agreed (OQ-12, §7.11) |
| Test Data | Generated strings: ASCII plus "é" totalling exactly 72 bytes; ASCII plus "é" totalling 73 bytes with no more than 72 characters |
| Test Steps | 1. Call `register` with the 72-byte password. 2. Call `register` with the 73-byte password. 3. Record the outcome of each |
| Expected Result | The 72-byte password is accepted. The 73-byte password is rejected with the error that the API returns as `400` (FR-01.2), even though it has no more than 72 characters |
| Actual Result | 72 bytes (62 characters) accepted; 73 bytes (63 characters) rejected with `IllegalArgumentException` "Password must be at most 72 UTF-8 bytes.". |
| Status | **PASS** |
| Execution details | Executed 25 Nov 2026 by integration (Phase 11, on the project owner's instruction; not by a named member). Group baseline `f565fb4` + uncommitted D-1 change; production code SHA-256-identical to the repository (no source change). Environment ENV-P11 (§11.3). Test method `Part01UnitExecutionTest.ut03`. Evidence: [execution record](../phase11-part01-exec/README.md#ut-03), lines `| UT-03 |` in [run2/part01-observations.txt](../phase11-part01-exec/run2/part01-observations.txt) |

#### UT-04 – `ReportService.findPage` paging and sort parameters

| Field | Content |
|---|---|
| Test Case ID | UT-04 |
| Requirement ID(s) | FR-04.1, FR-04.7, NFR-FUNC |
| Description | `findPage` with page −1/0, size 0/1/100/101, each allowed sort field, an invalid sort and different letter case for the direction. Existing coverage is a **gap** |
| Type / Priority | B/N / High |
| Preconditions | ENV-02. Repository mocked. Location of new test code agreed (OQ-12, §7.11) |
| Test Data | TD-10 (page −1/0; size 0/1/100/101; sort `createdAt`, `updatedAt`, `title`, `status`, `category`, `foo`; direction `asc`, `desc`, `ASC`, `Desc`) |
| Test Steps | 1. Call `findPage` with each page value. 2. With each size value. 3. With each sort field. 4. With the invalid sort. 5. With each direction value. 6. Record the outcome of each |
| Expected Result | Page −1: rejected; page 0: accepted. Size 0 and 101: rejected; 1 and 100: accepted. Each allowed sort field: accepted. Sort `foo`: rejected (`400`). Direction `asc` and `desc`: accepted. **Blocked pending confirmation:** FR-04.1 lists only `asc` and `desc`. The source documents do not say whether `ASC` or `Desc` are accepted; this must be confirmed from the code before that part of the expected result is fixed |
| Actual Result | Executed at service level (`ReportService.adminList` → `findPage`) on H2 instead of with a mocked repository, because `findPage` is private. Page −1 rejected, 0 accepted; size 0 and 101 rejected, 1 and 100 accepted; `createdAt`, `updatedAt`, `title`, `status`, `category` accepted; `foo` rejected "Unsupported sort field."; `asc` → A, B, C; `desc` → C, B, A. Observed: `ASC` sorts ascending and `Desc` descending (comparison is case-insensitive). |
| Status | **PASS** for page, size, sort field and `asc`/`desc`. Upper/mixed-case direction: **BLOCKED** (not stated in the source documents) – observed, not judged |
| Execution details | Executed 25 Nov 2026 by integration (Phase 11, on the project owner's instruction; not by a named member). Group baseline `f565fb4` + uncommitted D-1 change; production code SHA-256-identical to the repository (no source change). Environment ENV-P11 (§11.3). Test method `Part01ApiExecutionTest.ut04`. Evidence: [execution record](../phase11-part01-exec/README.md#ut-04), lines `| UT-04 |` in [run2/part01-observations.txt](../phase11-part01-exec/run2/part01-observations.txt) |

#### UT-05 – `ReportService.criteria` status and search handling

| Field | Content |
|---|---|
| Test Case ID | UT-05 |
| Requirement ID(s) | FR-04.1, FR-04.7 |
| Description | `criteria` with status `ALL`, blank, `in progress` and an invalid value; search text `q` is trimmed and lower-cased. Existing coverage is a **gap** |
| Type / Priority | P/N / High |
| Preconditions | ENV-02. Location of new test code agreed (OQ-12, §7.11) |
| Test Data | TD-10 (status `ALL`, `""`, `in progress`, `DONE`; `q` with surrounding spaces and upper-case letters) |
| Test Steps | 1. Build the criteria with each status value. 2. Build the criteria with the padded mixed-case `q`. 3. Inspect the resulting filter or the exception |
| Expected Result | `ALL` and blank: no status filter. `in progress`: filter on `IN_PROGRESS`. `DONE`: an exception with "Unsupported report status." (`400`). `q`: trimmed and lower-cased, for a case-insensitive match on title, reference and location |
| Actual Result | Executed at service level on H2. `ALL` and blank: no status filter (PENDING and IN_PROGRESS returned). `in progress`: IN_PROGRESS only. `DONE`: exception "Unsupported report status." – thrown as `IllegalArgumentException` inside the JPA Specification and wrapped by Spring as `InvalidDataAccessApiUsageException` (the API still returns `400`, API-14). `q = "  street LAMP  "`: matched the title "Broken Street LAMP" (trimmed, case-insensitive). Run 1 recorded the wrapper as a test error; see the execution record. |
| Status | **PASS** |
| Execution details | Executed 25 Nov 2026 by integration (Phase 11, on the project owner's instruction; not by a named member). Group baseline `f565fb4` + uncommitted D-1 change; production code SHA-256-identical to the repository (no source change). Environment ENV-P11 (§11.3). Test method `Part01ApiExecutionTest.ut05`. Evidence: [execution record](../phase11-part01-exec/README.md#ut-05), lines `| UT-05 |` in [run2/part01-observations.txt](../phase11-part01-exec/run2/part01-observations.txt) |

#### UT-06 – `ReportService.updateStatus`

| Field | Content |
|---|---|
| Test Case ID | UT-06 |
| Requirement ID(s) | FR-06.6, FR-07.7 |
| Description | Same status keeps the public approval; a changed status revokes it; a null status throws; a null note clears the note. Partly covered by an existing test ([ReportServiceTest.java:102](../../backend/src/test/java/lk/publiccare/backend/report/ReportServiceTest.java#L102)) |
| Type / Priority | P/N / High |
| Preconditions | ENV-02. Repository mocked; an approved report with status PENDING and a note. Location of new test code agreed (OQ-12, §7.11) |
| Test Data | TD-06 (approved report), TD-07 (note) |
| Test Steps | 1. Call `updateStatus` with the same status. 2. Call it with a different status on a fresh approved report. 3. Call it with a null status. 4. Call it with a valid status and a null note. 5. Inspect the approval, status and note after each |
| Expected Result | Step 1: approval kept. Step 2: approval revoked. Step 3: an exception (the API returns `400`). Step 4: the note is cleared. **Pending confirmation (RTM Q1, BR-13):** the code accepts a same-status update; whether that is intended is open, so step 1 follows the code and stays blocked as a pass criterion until Q1 is answered |
| Actual Result | Step 1 (same status): approval kept (observed). Step 2 (changed status): approval revoked. Step 3 (null status): `IllegalArgumentException` "A valid status is required.". Step 4 (null note): note cleared. |
| Status | **PASS** for steps 2–4. Step 1: **BLOCKED** (RTM Q1, BR-13) – observed, not judged |
| Execution details | Executed 25 Nov 2026 by integration (Phase 11, on the project owner's instruction; not by a named member). Group baseline `f565fb4` + uncommitted D-1 change; production code SHA-256-identical to the repository (no source change). Environment ENV-P11 (§11.3). Test method `Part01UnitExecutionTest.ut06`. Evidence: [execution record](../phase11-part01-exec/README.md#ut-06), lines `| UT-06 |` in [run2/part01-observations.txt](../phase11-part01-exec/run2/part01-observations.txt) |

#### UT-07 – `ReportService.updateMine`

| Field | Content |
|---|---|
| Test Case ID | UT-07 |
| Requirement ID(s) | FR-04.5, FR-07.7 |
| Description | A successful citizen edit revokes the public approval; a non-owner gets `ReportNotFoundException`. Partly covered by existing tests |
| Type / Priority | P/N / High |
| Preconditions | ENV-02. Repository mocked; an approved report owned by user A. Location of new test code agreed (OQ-12, §7.11) |
| Test Data | TD-05 (valid changed fields), TD-06 (approved report) |
| Test Steps | 1. Call `updateMine` as user A with valid fields. 2. Inspect the approval. 3. Call `updateMine` as user B for the same report. 4. Record the outcome and check the report is unchanged |
| Expected Result | Steps 1–2: the update is saved and the approval is revoked. Steps 3–4: `ReportNotFoundException` is thrown and the report is not changed |
| Actual Result | Executed at service level on H2, so that the owner filter in the Specification is really evaluated. Owner edit saved and approval revoked. Non-owner edit threw `ReportNotFoundException`; the report kept its values and its (re-granted) approval. |
| Status | **PASS** |
| Execution details | Executed 25 Nov 2026 by integration (Phase 11, on the project owner's instruction; not by a named member). Group baseline `f565fb4` + uncommitted D-1 change; production code SHA-256-identical to the repository (no source change). Environment ENV-P11 (§11.3). Test method `Part01ApiExecutionTest.ut07`. Evidence: [execution record](../phase11-part01-exec/README.md#ut-07), lines `| UT-07 |` in [run2/part01-observations.txt](../phase11-part01-exec/run2/part01-observations.txt) |

#### UT-08 – `ReportImageService.validateImage` and `isWebp`

| Field | Content |
|---|---|
| Test Case ID | UT-08 |
| Requirement ID(s) | FR-03.6 |
| Description | JPG, PNG and WebP signatures; a truncated WebP; a RIFF length mismatch; VP8, VP8L and VP8X WebP variants; the 10-megapixel edge; 0 bytes. Partly covered by existing tests ([ReportImageServiceTest.java](../../backend/src/test/java/lk/publiccare/backend/report/ReportImageServiceTest.java)) |
| Type / Priority | P/N/B / High |
| Preconditions | ENV-02. Location of new test code agreed (OQ-12, §7.11) |
| Test Data | TD-08 (valid JPG, PNG and WebP; truncated WebP; WebP with a wrong RIFF length; WebP files of each variant; images at and just above the pixel limit; 0-byte input) |
| Test Steps | 1. Call `validateImage` (and `isWebp` for WebP input) with each input. 2. Record whether each is accepted and the detected type |
| Expected Result | Valid JPG, PNG and WebP: accepted, with the type detected from the content. Truncated WebP and RIFF length mismatch: rejected. 0 bytes: rejected. At the pixel limit: accepted; above it: rejected. **Blocked pending confirmation:** the source documents say only that WebP is checked by structure, not decoded, and that the limit is "10 megapixels". The acceptance rule for each of VP8, VP8L and VP8X, and the exact pixel-count constant, must be taken from `ReportImageService` and agreed during peer review before those parts are fixed |
| Actual Result | Called through the public `upload` method with mocked repositories (the methods are private). Valid JPG → `image/jpeg`; PNG → `image/png`; WebP VP8L → `image/webp`. Observed: minimal VP8 and VP8X + VP8L structures were also accepted as `image/webp`. Truncated WebP and wrong RIFF length → rejected "Only valid JPG, PNG, or WebP images are supported."; 0 bytes → rejected "Choose an image to upload.". 4000 × 2500 px (exactly 10,000,000 px, the `MAX_IMAGE_PIXELS` constant) accepted; 4000 × 2501 rejected "Image dimensions must not exceed 10 megapixels.". |
| Status | **PASS** for JPG, PNG, WebP (VP8L) detection, truncated WebP, RIFF mismatch, 0 bytes and the pixel limit. VP8 / VP8X rules: **BLOCKED** (to be agreed in peer review) – observed, not judged |
| Execution details | Executed 25 Nov 2026 by integration (Phase 11, on the project owner's instruction; not by a named member). Group baseline `f565fb4` + uncommitted D-1 change; production code SHA-256-identical to the repository (no source change). Environment ENV-P11 (§11.3). Test method `Part01UnitExecutionTest.ut08`. Evidence: [execution record](../phase11-part01-exec/README.md#ut-08), lines `| UT-08 |` in [run2/part01-observations.txt](../phase11-part01-exec/run2/part01-observations.txt) |

#### UT-09 – `ReportProgressService.create`

| Field | Content |
|---|---|
| Test Case ID | UT-09 |
| Requirement ID(s) | FR-06.5 |
| Description | Note of 1000 and 1001 characters; WebP photo rejected; stored file deleted when the save fails; approval revoked on a status change. Partly covered by existing tests ([ReportProgressServiceTest.java](../../backend/src/test/java/lk/publiccare/backend/report/ReportProgressServiceTest.java)) |
| Type / Priority | N/B/P / High |
| Preconditions | ENV-02. Repositories and image storage mocked; an approved report with status PENDING. Location of new test code agreed (OQ-12, §7.11) |
| Test Data | TD-07 (notes of 1000 and 1001 characters), TD-08 (WebP; valid JPG) |
| Test Steps | 1. Call `create` with a 1000-character note. 2. With a 1001-character note. 3. With a WebP photo. 4. With a valid JPG and the repository save set to fail. 5. With a different status on the approved report. 6. Inspect the results, the storage mock and the approval |
| Expected Result | Step 1: accepted. Step 2: rejected. Step 3: rejected (progress photos are JPG/PNG only, BR-24). Step 4: the stored file is deleted (compensating delete). Step 5: the report's public approval is revoked |
| Actual Result | Step 1: 1000-character note accepted. Step 2: 1001 rejected "Update note must be 1000 characters or fewer.". Step 3: WebP rejected "Only valid JPG or PNG images are supported.". Step 4: with the save failing, the stored file was deleted (compensating delete called with the stored key). Step 5: approval revoked by the status change. |
| Status | **PASS** |
| Execution details | Executed 25 Nov 2026 by integration (Phase 11, on the project owner's instruction; not by a named member). Group baseline `f565fb4` + uncommitted D-1 change; production code SHA-256-identical to the repository (no source change). Environment ENV-P11 (§11.3). Test method `Part01UnitExecutionTest.ut09`. Evidence: [execution record](../phase11-part01-exec/README.md#ut-09), lines `| UT-09 |` in [run2/part01-observations.txt](../phase11-part01-exec/run2/part01-observations.txt) |

#### UT-10 – `ReportProgressUpdate.approveNoteForPublic`

| Field | Content |
|---|---|
| Test Case ID | UT-10 |
| Requirement ID(s) | FR-07.3 |
| Description | Approving a `null`, empty or blank note throws; approving a valid note sets the flag and the approver. Existing coverage is a **gap** (no direct test) |
| Type / Priority | N/P / High |
| Preconditions | ENV-02. Location of new test code agreed (OQ-12, §7.11) |
| Test Data | TD-07 (`null`, `""`, `"   "`, a valid note); an approver ID |
| Test Steps | 1. Create a progress update with each note value. 2. Call `approveNoteForPublic` with the approver. 3. Inspect the result, the note approval flag, the approver and the time |
| Expected Result | `null`, `""` and `"   "`: an exception is thrown and the flags are not changed. Valid note: the note approval flag is set and the approver (actor) and time are recorded |
| Actual Result | `null`, `""` and `"   "`: `IllegalArgumentException` "A non-empty note is required for public approval."; flag stayed `false`, approver `null`. Valid note: flag `true`, approver 42, time set. |
| Status | **PASS** |
| Execution details | Executed 25 Nov 2026 by integration (Phase 11, on the project owner's instruction; not by a named member). Group baseline `f565fb4` + uncommitted D-1 change; production code SHA-256-identical to the repository (no source change). Environment ENV-P11 (§11.3). Test method `Part01UnitExecutionTest.ut10`. Evidence: [execution record](../phase11-part01-exec/README.md#ut-10), lines `| UT-10 |` in [run2/part01-observations.txt](../phase11-part01-exec/run2/part01-observations.txt) |

#### UT-11 – `PublicReportService`

| Field | Content |
|---|---|
| Test Case ID | UT-11 |
| Requirement ID(s) | FR-07.4 |
| Description | An unapproved note is not shown; page size 24 and 25; an unapproved report detail is not found. Partly covered by existing tests ([PublicReportServiceTest.java](../../backend/src/test/java/lk/publiccare/backend/report/PublicReportServiceTest.java)) |
| Type / Priority | P/N/B / High |
| Preconditions | ENV-02. Repositories mocked with an approved report that has an approved update with an unapproved note, and an unapproved report. Location of new test code agreed (OQ-12, §7.11) |
| Test Data | TD-06 (approval states), TD-10 (size 24/25) |
| Test Steps | 1. Build the public feed and inspect the update's note. 2. Call the feed with size 24, then 25. 3. Call the detail for the unapproved report |
| Expected Result | Step 1: the note is not included. Step 2: size 24 is accepted; size 25 is rejected. Step 3: not found (the API returns `404`) |
| Actual Result | Executed at service level on H2. Step 1: the unapproved note of an approved update was returned as `null`. Step 2: size 24 accepted, 25 rejected. Step 3: unapproved report → `ReportNotFoundException`. |
| Status | **PASS** |
| Execution details | Executed 25 Nov 2026 by integration (Phase 11, on the project owner's instruction; not by a named member). Group baseline `f565fb4` + uncommitted D-1 change; production code SHA-256-identical to the repository (no source change). Environment ENV-P11 (§11.3). Test method `Part01ApiExecutionTest.ut11`. Evidence: [execution record](../phase11-part01-exec/README.md#ut-11), lines `| UT-11 |` in [run2/part01-observations.txt](../phase11-part01-exec/run2/part01-observations.txt) |

#### UT-12 – `PrivateImageStorage.resolve` path guard

| Field | Content |
|---|---|
| Test Case ID | UT-12 |
| Requirement ID(s) | NFR-SEC |
| Description | `resolve` with `../x.jpg`, `abc.jpg`, a `.gif` extension and a valid UUID key. Existing coverage is a **gap** |
| Type / Priority | N/P / High |
| Preconditions | ENV-02. A temporary storage directory. Location of new test code agreed (OQ-12, §7.11) |
| Test Data | Keys `../x.jpg`, `abc.jpg`, `<UUID>.gif` and `<UUID>.jpg` |
| Test Steps | 1. Call `resolve` with each key. 2. Record whether it is rejected or the path it returns |
| Expected Result | `../x.jpg`, `abc.jpg` and `<UUID>.gif`: rejected. `<UUID>.jpg`: resolves to a path inside the storage directory. These outcomes follow from the UUID keys and path-traversal guard in RTM document §2.1 and must be checked against `PrivateImageStorage.java:47-54` during peer review |
| Actual Result | Through the public `read`/`store` methods (`resolve` is private): `../x.jpg`, `abc.jpg` and `<UUID>.gif` → `IllegalArgumentException` "Invalid image storage key.". A stored `<UUID>.jpg` key resolved to a file inside the temporary storage directory and was read back. The checks agree with `PrivateImageStorage.java:47-54`. |
| Status | **PASS** |
| Execution details | Executed 25 Nov 2026 by integration (Phase 11, on the project owner's instruction; not by a named member). Group baseline `f565fb4` + uncommitted D-1 change; production code SHA-256-identical to the repository (no source change). Environment ENV-P11 (§11.3). Test method `Part01UnitExecutionTest.ut12`. Evidence: [execution record](../phase11-part01-exec/README.md#ut-12), lines `| UT-12 |` in [run2/part01-observations.txt](../phase11-part01-exec/run2/part01-observations.txt) |

#### UT-13 – `ReportDeletionService`

| Field | Content |
|---|---|
| Test Case ID | UT-13 |
| Requirement ID(s) | FR-08.2, FR-08.3 |
| Description | Files are cleaned up after commit; a missing report; a database failure; a file-delete failure is logged. A test already exists ([ReportDeletionServiceTest.java](../../backend/src/test/java/lk/publiccare/backend/report/ReportDeletionServiceTest.java)). *[Phase 3A]* It passed in the backend suite run on this branch (Java 27); that run does not execute UT-13 as specified here, so UT-13 stays NOT EXECUTED |
| Type / Priority | P/N / Medium |
| Preconditions | ENV-02. Repositories, image storage and transaction synchronisation mocked. Location of new test code agreed (OQ-12, §7.11) |
| Test Data | A report with an image and progress photos (mocked) |
| Test Steps | 1. Delete the report and complete the commit; check when the files are deleted. 2. Delete a missing report. 3. Delete with the database delete set to fail; check the storage mock. 4. Delete with the file delete set to fail; check the result and the log |
| Expected Result | Step 1: files are deleted only after the commit. Step 2: a not-found error (the API returns `404`). Step 3: no files are touched and the error maps to `500` "Unable to delete report.". Step 4: the error is logged and the request does not fail |
| Actual Result | Step 1: no file deleted before the commit; both files deleted after `afterCommit`. Step 2: `ReportNotFoundException`. Step 3: database failure → no file touched; through `AdminReportController` + `ApiExceptionHandler` the response is `500 {"error":"Unable to delete report."}`. Step 4: file-delete failure after commit did not fail the request and was logged ("Unable to remove private image after deleting report 21…"). |
| Status | **PASS** |
| Execution details | Executed 25 Nov 2026 by integration (Phase 11, on the project owner's instruction; not by a named member). Group baseline `f565fb4` + uncommitted D-1 change; production code SHA-256-identical to the repository (no source change). Environment ENV-P11 (§11.3). Test method `Part01UnitExecutionTest.ut13`. Evidence: [execution record](../phase11-part01-exec/README.md#ut-13), lines `| UT-13 |` in [run2/part01-observations.txt](../phase11-part01-exec/run2/part01-observations.txt) |

#### UT-14 – `AdminSetupRunner.isLoopbackPostgres`

| Field | Content |
|---|---|
| Test Case ID | UT-14 |
| Requirement ID(s) | FR-06.9 |
| Description | The loopback check with `localhost`, `127.0.0.1`, `[::1]`, a remote host and a `jdbc:mysql:` URL. Existing coverage is a **gap**. Checks the **[I]** IPv6 concern (RTM document §2.6) |
| Type / Priority | P/N / Medium |
| Preconditions | ENV-02. The method is private: it is called through reflection, or through a refactor that changes application source and needs the project owner's approval (§7.11). The approach must be agreed before the test is written. Location of new test code agreed (OQ-12) |
| Test Data | JDBC URLs: `jdbc:postgresql://localhost:5432/db`, `jdbc:postgresql://127.0.0.1:5432/db`, `jdbc:postgresql://[::1]:5432/db`, `jdbc:postgresql://<remote host>:5432/db`, `jdbc:mysql://localhost:3306/db` |
| Test Steps | 1. Call the check with each URL. 2. Record each result |
| Expected Result | `localhost` and `127.0.0.1`: loopback (true). Remote host: false. `jdbc:mysql:`: false (only loopback PostgreSQL is allowed, FR-06.9). `[::1]`: **Blocked pending confirmation (RTM Q1, §2.6 [I]):** the code compares with `"::1"`, but `URI.getHost()` may return `[::1]` with brackets, so the check may never match. Whether IPv6 loopback is meant to be accepted is not confirmed; record the observed result without judging it |
| Actual Result | Called through reflection (no source change). `localhost` → true; `127.0.0.1` → true; remote host → false; `jdbc:mysql:` → false. Observed: `jdbc:postgresql://[::1]:5432/db` → **false**, confirming the suspicion that `URI.getHost()` returns `[::1]` with brackets (observation OBS-1). |
| Status | **PASS** for `localhost`, `127.0.0.1`, remote host and `jdbc:mysql:`. `[::1]`: **BLOCKED** (RTM Q1, §2.6 [I]) – observed, not judged |
| Execution details | Executed 25 Nov 2026 by integration (Phase 11, on the project owner's instruction; not by a named member). Group baseline `f565fb4` + uncommitted D-1 change; production code SHA-256-identical to the repository (no source change). Environment ENV-P11 (§11.3). Test method `Part01UnitExecutionTest.ut14`. Evidence: [execution record](../phase11-part01-exec/README.md#ut-14), lines `| UT-14 |` in [run2/part01-observations.txt](../phase11-part01-exec/run2/part01-observations.txt) |

#### UT-15 – `AdminAccountSetupService`

| Field | Content |
|---|---|
| Test Case ID | UT-15 |
| Requirement ID(s) | FR-06.9 |
| Description | Missing fields; running again for the same admin; refusing to promote a citizen; refusing a second admin. Tests already exist in the code base. *[Phase 3A]* They passed in the backend suite run on this branch (Java 27); that run does not execute UT-15 as specified here, so UT-15 stays NOT EXECUTED |
| Type / Priority | P/N / Medium |
| Preconditions | ENV-02. User repository and password encoder mocked. Location of new test code agreed (OQ-12, §7.11) |
| Test Data | Admin set-up values (name, email, password from 12 characters to 72 bytes); an existing citizen's email; a second admin email |
| Test Steps | 1. Run the set-up with each required field missing. 2. Run it for a new admin, then run it again with the same values. 3. Run it with an existing citizen's email. 4. With one admin present, run it with a different email. 5. Inspect the saved users after each |
| Expected Result | Step 1: rejected; no account is created. Step 2: the first run creates one ADMIN; the second run makes no change. Step 3: refused; the citizen is not promoted. Step 4: refused; no second admin is created |
| Actual Result | Step 1: missing name, email or password → `IllegalArgumentException` ("Admin setup input is invalid: …"), nothing saved. Step 2: first run `CREATED` (one ADMIN, email normalised), second run `ALREADY_CONFIGURED`, no second save. Step 3: citizen email → `IllegalStateException`, not promoted. Step 4: second admin → `IllegalStateException`, nothing saved. |
| Status | **PASS** |
| Execution details | Executed 25 Nov 2026 by integration (Phase 11, on the project owner's instruction; not by a named member). Group baseline `f565fb4` + uncommitted D-1 change; production code SHA-256-identical to the repository (no source change). Environment ENV-P11 (§11.3). Test method `Part01UnitExecutionTest.ut15`. Evidence: [execution record](../phase11-part01-exec/README.md#ut-15), lines `| UT-15 |` in [run2/part01-observations.txt](../phase11-part01-exec/run2/part01-observations.txt) |

#### UT-16 – Frontend status and query helpers (Vitest)

| Field | Content |
|---|---|
| Test Case ID | UT-16 |
| Requirement ID(s) | FR-05.1, NFR-FUNC |
| Description | `reportsData.getStatusProgress` / `normalizeStatus`, `reportsApi.queryString` and `reportStatusLabel`: each status; `"in_progress"`, `"IN-PROGRESS"` and an unknown value; empty and null parameters dropped. Existing coverage is a **gap** |
| Type / Priority | P/N / Medium |
| Preconditions | Vitest is **not installed**. Adding it (and any export needed) changes the application and needs the project owner's approval (§7.11). This case is blocked until that approval is given |
| Test Data | Status values `PENDING`, `IN_PROGRESS`, `RESOLVED`, `"in_progress"`, `"IN-PROGRESS"`, an unknown string; parameter objects with empty-string and null values |
| Test Steps | 1. Call `getStatusProgress` for each status. 2. Call `normalizeStatus` with each input. 3. Call `queryString` with the parameter objects. 4. Call `reportStatusLabel` for each status. 5. Record each result |
| Expected Result | `getStatusProgress("PENDING")` marks "Under review" as current (FR-05.1). `queryString` drops empty and null parameters. `reportStatusLabel("IN_PROGRESS")` returns "In progress" (the label used in the CSV export, MT-22). **Blocked pending confirmation:** the source documents do not state the results of `getStatusProgress` for IN_PROGRESS and RESOLVED, of `normalizeStatus` for `"in_progress"`, `"IN-PROGRESS"` and an unknown value, or the other labels. They must be taken from the code and agreed during peer review |
| Actual Result | NOT EXECUTED |
| Status | **BLOCKED – not executable** (Vitest is not installed; adding it needs the project owner's approval, §7.11) |
| Execution details | Not executed. No frontend unit-test runner exists in the project (§7.6); the case stays blocked until the owner approves Vitest |

#### UT-17 – `downloadCsv` escaping (Vitest)

| Field | Content |
|---|---|
| Test Case ID | UT-17 |
| Requirement ID(s) | FR-06.3 |
| Description | `downloadCsv` escaping for values containing `"`, commas and newlines. Existing coverage is a **gap** |
| Type / Priority | N/P / Low |
| Preconditions | Vitest is **not installed** and `downloadCsv` is not exported. Adding Vitest and exporting the function change the application and need the project owner's approval (§7.11). This case is blocked until that approval is given |
| Test Data | Row values containing `"`, a comma, and a newline |
| Test Steps | 1. Call `downloadCsv` (or the CSV-building part of it) with the rows. 2. Capture the CSV text. 3. Inspect each value |
| Expected Result | Each value is in double quotes. A `"` inside a value is doubled. A comma stays inside the quoted value (FR-06.3, MT-22). **Blocked pending confirmation:** the source documents do not state how newlines inside a value are handled; this part must be confirmed from the code before it is fixed |
| Actual Result | NOT EXECUTED |
| Status | **BLOCKED – not executable** (Vitest is not installed; adding it needs the project owner's approval, §7.11) |
| Execution details | Not executed. No frontend unit-test runner exists in the project (§7.6); the case stays blocked until the owner approves Vitest |

**E. Integration test case (INT-01)**

#### INT-01 – Flyway migration and schema-ownership check

| Field | Content |
|---|---|
| Test Case ID | INT-01 |
| Requirement ID(s) | FR-09.2 |
| Description | Flyway applies V1–V5 to a fresh PostgreSQL database, does not re-apply them on restart, baselines an existing database at V2, and Hibernate creates or alters nothing (`ddl-auto: none`) |
| Type / Priority | P/N / Medium |
| Preconditions | A real, disposable PostgreSQL database that never holds real data (ENV-03). The environment is not decided (RTM Q3): Testcontainers needs Docker and a new test dependency, which needs the project owner's approval (§7.11); a dedicated local database needs no change. For scenario (c), a schema with only the V1 and V2 objects and no `flyway_schema_history` table (TD-11, an assumption, RTM Q2) |
| Test Data | TD-11 (existing-database state) |
| Test Steps | 1. **(a) Fresh database:** start the backend against an empty PostgreSQL schema; read `flyway_schema_history` and the schema. 2. **(b) Re-start:** start the backend again against the same schema; read `flyway_schema_history`. 3. **(c) Existing database:** create a schema holding only the V1 and V2 objects (`users`, `reports`) with some rows and no `flyway_schema_history` table; start the backend; read the history, the schema and the rows. 4. **(d) Schema ownership:** compare the schema with the migration scripts and check the effective `spring.jpa.hibernate.ddl-auto` value |
| Expected Result | **(a)** V1–V5 apply in version order; `flyway_schema_history` lists versions 1–5, each with `success = true`; all tables and constraints from the scripts exist. **(b)** No migration is applied a second time; startup succeeds. **(c)** **Pending confirmation (RTM Q2):** Flyway records a baseline at version 2, applies only V3–V5, and leaves the V1/V2 tables and data intact. This depends on the assumed contents of an "existing database" and stays blocked until Q2 is answered. **(d)** `ddl-auto` resolves to `none`; every table, column and constraint comes from a migration script; Hibernate creates or alters nothing. All expected results come from the configuration and standard Flyway behaviour and must be confirmed by running the test |
| Actual Result | *[Phase 8A, 25 Nov 2026]* Executed by the project owner with `qa/phase7-final-completion/int01-postgresql/run-int01.ps1` on a **dedicated local PostgreSQL 18.6** database (`publiccare_int01_qa`, new and empty; application role `publiccare_int01_app`, no superuser). Baseline `f565fb4` **plus the uncommitted D-1 change**; jar SHA-256 `dd1b8b18…0d9ce0`; JetBrains Runtime 21.0.8 (Java 21), Windows 11. **(a)** Flyway created `flyway_schema_history` and applied V1 → V5 in order ("Successfully applied 5 migrations to schema "public", now at version v5"); history rows 1–5 all `success = t`; the schema dump contains every table, column, constraint and index of V1–V5 and nothing else apart from Flyway's own table; the backend started and `/api/health` and `/actuator/health` returned `200 UP`. **(b)** Second start: "Schema "public" is up to date. No migration necessary"; history and schema unchanged; health `200 UP`. **(c)** Not run: RTM Q2 is still open, and no test data was invented. **(d)** 0 Hibernate DDL statements in both logs; no object of Hibernate origin in the schema; `ddl-auto: none` in the only configuration file and no override active. The *effective* value was not read at run time (only the health endpoint is exposed). Flyway warned that PostgreSQL 18.6 is newer than its latest supported version (17); the migrations still applied without error. Tester: project owner (name PENDING, P-OW-1); results checked by the integrator from the raw output. Evidence: [INT-01 record](../phase7-final-completion/int01-postgresql/README.md) (run folder `run-20261125-125857/`) |
| Status | **PARTIALLY EXECUTED:** (a) `PASS`, (b) `PASS`, (d) `PASS`, (c) `BLOCKED` (RTM Q2). Not an overall `PASS`, because scenario (c) is part of the Expected Result |
| Execution details | Executed 25 Nov 2026 (Phase 8A) by the project owner on PostgreSQL 18.6; see the Actual Result. Evidence: [INT-01 record](../phase7-final-completion/int01-postgresql/README.md) |

All 79 test cases in TASK 6 have a full-format entry in this section. **Execution summary (25 Nov 2026):** group repository 41 (33 PASS, 6 PASS with one part BLOCKED, 1 BLOCKED, 1 PARTIALLY EXECUTED); Member 3 copy only 6 (PASS); not executed 32 (22 MT, 8 UI, 2 UT BLOCKED). 41 + 6 + 32 = 79. No case failed. TASK 6 remains the source list; the RTM (TASK 5) records the status of every case.

---

## 11. Test Environment and Configuration

### 11.1 Confirmed environment details

Only details confirmed by the source analysis are recorded as confirmed. Everything else is **To Be Confirmed (TBC)**. No browser, device, operating system or environment has been tested.

*[As executed: Phase 5A, applied 25 Nov 2026]* The sentence above is kept as written on 23 Nov 2026 and still holds for the Part 01 test cases, apart from API-04. The "As executed" column records only values that appear in member or integration evidence ("not recorded" means no evidence file states the value). These are the environments of **member copies and integration runs**; they are not Part 01 results. No PostgreSQL database, no deployment environment and no Selenium or browser test has been used on the group baseline. *[Phase 8A, 25 Nov 2026: superseded for PostgreSQL only – INT-01 scenarios (a), (b), (d) ran on a dedicated local PostgreSQL 18.6 database; see the Database row.]*

| Item | Detail | Status | As executed (member copies and integration, 24–25 Nov 2026) |
|---|---|---|---|
| Code baseline | git `f565fb4` | Confirmed (analysed baseline). The execution baseline is TBC | Per member, see `qa/phase5a-p0-corrections/BASELINE_DEVIATIONS.md`. Part 01 API-04: `f565fb4` + uncommitted D-1 |
| Frontend | React 19 + Vite 8 SPA; npm scripts `dev`, `build`, `lint`, `preview` | Confirmed | – |
| Backend | Spring Boot 3.5.16 on Java 21; Maven project (`backend/pom.xml`) | Confirmed | – |
| Database | PostgreSQL, schema by Flyway V1–V5 (V2 baseline for existing databases) | Confirmed. PostgreSQL version TBC | **Not used in any execution.** All runs, including the integration Java 21 run, used H2 (`MODE=PostgreSQL` or in-memory). PostgreSQL version stays **TBC** (register ENV-P5). *[Phase 8A, 25 Nov 2026: INT-01 (scenarios a, b, d) ran on **PostgreSQL 18.6** (x86_64-windows, UTF8), dedicated local database `publiccare_int01_qa` (register ENV-P5, ENV-P6 CONFIRMED). The JUnit suite and all member runs still used H2]* |
| Database used by existing JPA tests | H2 with Flyway disabled | Confirmed | – |
| Configuration – API base URL | `VITE_API_BASE_URL` | Confirmed variable; test value TBC | – |
| Configuration – CORS origin | `PUBLICCARE_CORS_ALLOWED_ORIGIN` | Confirmed variable; test value TBC. API-22 uses `http://localhost:5173` as the allowed origin; confirm this matches the configuration | – |
| Configuration – first-admin setup | `PUBLICCARE_ADMIN_SETUP_ENABLED=true`, loopback PostgreSQL only | Confirmed | – |
| Configuration – session cookie | HTTP-only, SameSite=Lax; `Secure` flag configurable, default `false` | Confirmed. The value for the test environment is TBC | – |
| Configuration – upload limit | Multipart limit of 5 MB | Confirmed | – |
| Configuration template | `backend/.env.example` is tracked; real secrets are kept out of git | Confirmed. Real values must not be written in test documents | – |
| Image storage location | Private directory outside the web root | Confirmed design; actual path TBC | – |
| Node.js and npm versions | – | TBC | M3 local: Node v24.13.0, npm 11.11.0. M3 CI: Node 22 (exact patch not recorded). M4: **not recorded** (register P-M4-10) |
| JDK distribution and Maven version | Java 21 required | Distribution and Maven version TBC | M2: Microsoft OpenJDK 21.0.13.1 (version confirmed in E10 log; vendor from the member report). M3 local: Eclipse Temurin 25.0.5. M3 CI: Temurin 21.0.13. M4: Java 21.0.13.1, vendor "Azul Systems, Inc." (`sonar-run.txt`; Member 4 confirmation pending, P-M4-10). Phase 3A integration: Oracle Java 27 (27+35). *[Java 21 run, 25 Nov 2026 (applied 25 Nov 2026):]* integration Java 21 run: Ubuntu OpenJDK 21.0.13.1 (`21.0.13.1+2-1-26.04.1-Ubuntu`, from the Surefire XML). Maven Wrapper 3.9.16 (`maven-wrapper.properties`). For the integration Java 21 run, the Maven version used rests on the integrator's report; the console log and `./mvnw -v` output are not archived (register ENV-J10, **PENDING**) |
| Backend host and port | – | TBC | `127.0.0.1:8080` (M4 JMeter and access checks); port 8080 (M3 Selenium); random port (M3 REST Assured); MockMvc, no port (M3 BDD, M2, Phase 3A) |
| Test execution operating system(s) | – | TBC | Windows 11 (M2, M3 local); Linux 6.18.57 amd64 (M4, `sonar-run.txt`; Member 4 confirmation of the machine pending, P-M4-10); `ubuntu-latest` (M3 CI); Phase 3A: the integration machine (the Phase 3A logs do not print the OS; Phase 4 recorded it as Windows 11). *[Java 21 run, 25 Nov 2026 (applied 25 Nov 2026):]* integration Java 21 run: Linux (WSL2, Ubuntu 26.04.1; kernel `6.18.33.2-microsoft-standard-WSL2`) |
| Browsers and versions | MT-17 proposes Chrome, Firefox, Edge and Safari | Proposed; versions and availability TBC. None tested | Chrome 157 headless (M3 copy, Selenium, Windows, local only); Chromium 153.0.8010.0 via Playwright (M4 copy, Linux). Firefox, Edge, Safari: **not tested**. No browser test on the group baseline |
| Devices and screen widths | MT-17 proposes a 375 px mobile width | Proposed; physical devices TBC. None tested | Playwright viewports 390 × 844 and 1100 × 900 CSS px (M4). 375 px: **not tested**. No physical device testing recorded |
| Deployment environment (staging or hosted) | Not documented | TBC | **None used.** All runs were local (including WSL2) or in CI. Stays **TBC** |
| Test tools | See §7.6 | Mostly TBC | See "Tools as executed" in §7.6 |

### 11.3 Confirmed and proposed environments – summary *[Phase 11, 25 Nov 2026]*

**Confirmed** = an environment actually used for a recorded result (evidence named). **Proposed** = planned, not used for any result.

| ID | Status | Used for | Operating system | Java / runtime | Build | Database | Server / client | Evidence |
|---|---|---|---|---|---|---|---|---|
| **ENV-P11** | Confirmed | Phase 11: 39 Part 01 cases; full suite 125 / 0 | Windows 11 Pro (10.0.26200) | Oracle Java 27 (build 27+35-2325) | Maven 3.9.16 via the Maven Wrapper, offline | H2 in-memory, `MODE=PostgreSQL`, schema by Hibernate `create-drop` (Flyway off) – the PostgreSQL-generated `reports.reference` column is emulated with the V2 expression | Real embedded Tomcat on a random port; Java `HttpClient` with one cookie jar per user; CSRF token taken from `GET /api/auth/csrf` as the SPA does | [00-environment.txt](../phase11-part01-exec/00-environment.txt), [execution record](../phase11-part01-exec/README.md) |
| ENV-3A | Confirmed | API-04; suite 72/0 and 85/0 | Windows 11 | Oracle Java 27 | Maven Wrapper 3.9.16 | H2 | MockMvc (no HTTP server) | `qa/phase3a-api04-correction/` |
| ENV-J21 | Confirmed | Backend suite 85/0 on Java 21 | Linux – WSL2, Ubuntu 26.04.1 | Ubuntu OpenJDK 21.0.13.1 | Maven version not archived (ENV-J10) | H2 | MockMvc | `qa/evidence/java21-run/` |
| ENV-03 (INT-01) | Confirmed | INT-01 (a), (b), (d) | Windows 11 | JetBrains Runtime 21.0.8 | Built jar `dd1b8b18…` | **PostgreSQL 18.6**, dedicated local database | Real HTTP server, health endpoints | INT-01 record |
| M3 local / CI | Confirmed (Member 3 copy) | UI-01 – UI-05, UI-13 (Selenium); member tests | Windows 11; `ubuntu-latest` in CI | Temurin 25.0.5 local; Temurin 21.0.13 in CI | Maven Wrapper; Node 24.13.0 / npm 11.11.0 local, Node 22 in CI | H2 | Chrome 157 headless, ChromeDriver 157.0.8103.78, Selenium 4.31.0 (local only) | `qa/3.5-3.6_member3/evidence/` |
| M2, M4 | Confirmed (member copies) | Sections 3.3–3.4, 3.7–3.9 | Windows 11 (M2); Linux 6.18.57 (M4) | Microsoft OpenJDK 21.0.13.1 (M2); Java 21.0.13.1 Azul (M4) | Maven | H2 | Chromium 153 via Playwright (M4) | member evidence folders |
| ENV-01 | **Proposed – not used** | Manual MT cases | TBC | Java 21 | – | Local PostgreSQL test database | Frontend dev/preview server in a browser | – |
| ENV-04 | **Proposed – not used** | MT-17 cross-browser, 375 px | TBC | – | – | – | Firefox, Edge, Safari; physical devices | – |
| Staging / hosted | **Not available** | – | – | – | – | – | – | No deployment environment exists |

**Frontend and backend technologies (confirmed from the code):** React 19 + Vite 8 SPA (ESLint configured, no unit-test runner); Spring Boot 3.5.16 (Spring Security, Spring Data JPA, Bean Validation, Actuator), Flyway V1–V5, PostgreSQL driver, H2 for tests; JUnit 5, Mockito, AssertJ, Spring Security Test.

**Configuration values observed in ENV-P11 (no override was set):** CORS origin `http://localhost:5173` (API-22); session cookie `HttpOnly; SameSite=Lax`, no `Secure` flag (API-06); multipart limit 5 MB – a 5,242,881-byte file is rejected with `413` (API-12); health details not exposed (API-21).

**Test data set-up used in ENV-P11:** all data synthetic and generated at run time – citizens registered through the API with addresses on `qa.example.test`; one Admin inserted through the repository with a BCrypt hash (the first-admin command only works against loopback PostgreSQL, FR-06.9); reports created through the API; volume data for API-19 (26 approved reports, 12 progress updates) inserted through the repositories; images generated in memory (JPG, PNG, GIF, minimal WebP structures, padded PNG of exact sizes, 4000 × 2501 px PNG). A synthetic password is used only inside the test code. The in-memory database is discarded after each run (§12.1).

### 11.2 Proposed test configurations

| Config ID | Purpose | Configuration | Status |
|---|---|---|---|
| ENV-01 | Manual, UI and API system testing | Frontend dev or preview server + backend + local PostgreSQL test database with fictitious data | [Proposed]; versions, OS and ports TBC |
| ENV-02 | Unit tests and existing slice tests | Backend test run with JUnit 5, Mockito and H2 | [Proposed]; existing set-up in the project. *[Phase 11: used for UT-01 – UT-15 – see ENV-P11 in §11.3]* |
| ENV-03 | Integration test INT-01 | Disposable PostgreSQL database (Testcontainers or dedicated local database). Never a database holding real data | [Proposed]; environment TBC (RTM Q3). *[Phase 8A: used – dedicated local PostgreSQL 18.6 database `publiccare_int01_qa` with role `publiccare_int01_app`, both created new and empty for INT-01; no real data; kept for inspection until the project owner approves cleanup]* |
| ENV-04 | Cross-browser and responsive checks | ENV-01 viewed in each agreed browser and at a 375 px width | [Proposed]; browsers TBC |
| ENV-05 | CI regression | GitHub Actions or Jenkins runner (assignment 3.6) | [Proposed]; not set up *[As executed: Phase 5A, applied 25 Nov 2026: set up and run once in the Member 3 repository (run #1, commit `70d01e0`, 24 Nov 2026; backend and frontend jobs ran – the Selenium `ui` job was not executed). Not set up in the group repository]* |

---

## 12. Test Data Setup

### 12.1 Principles

- **No real personal information.** Use fictitious names and email addresses on a reserved test domain (for example `citizen.a@example.test`).
- **No passwords, tokens or secrets in this document.** Test account passwords are created by the tester at set-up time and kept outside the repository. Boundary-length password inputs are generated strings, not real credentials.
- **Disposable database only.** Test data is loaded into a dedicated test database that can be reset. It is never loaded into a database with real data.
- **Seed through the application where possible** [Proposed], so that business rules (normalisation, references, ownership) are applied. The Admin account can only be created with the first-admin setup command.

### 12.2 Test data categories

| ID | Category | Content | Used by (examples) | Notes |
|---|---|---|---|---|
| TD-01 | New registration data | Valid unique names and emails; name 120/121 characters; email 254/255 characters; password 11/12/72/73 characters and 37 × "é" (74 bytes); blank and whitespace-only fields; email with different case or spaces | MT-01–MT-04, UI-01, API-02–API-05 | Generated at test time |
| TD-02 | Citizen A | Registered Citizen account; owns several reports | Most citizen cases | Password kept outside the repository |
| TD-03 | Citizen B | Second Citizen account with its own reports | MT-18, MT-21, API-14, API-15, API-25 (cross-user access; counts across owners) | Needed for ownership/IDOR checks |
| TD-04 | Administrator | Admin account created by the first-admin setup command | Admin cases, API-05, API-09 | Only one admin can be created this way (BR-06) |
| TD-05 | Report field data | Valid values for each of the six UI categories; title 160/161, description 5000/5001, category 80/81, location 240/241; whitespace-only values; values with surrounding spaces; a category not in the UI list (sent through the API); text with script payloads such as `<script>alert(1)</script>`; a title containing a double quote and a comma (CSV check) | MT-08, MT-22, UI-05, API-05, API-10, API-11, XSS checks | The non-UI category supports the **[I]** edit-page fallback check |
| TD-06 | Report states | Reports in PENDING, IN_PROGRESS and RESOLVED; approved and unapproved; with and without a photo; with progress updates and notes in different approval states; one report with more than 10 approved updates; a known number of reports per status and category (dashboard checks) | MT-11–MT-15, MT-20, MT-21, API-05, API-16–API-19, API-25, UI-08 | Built by admin actions in the application |
| TD-07 | Progress update data | Notes of 1000/1001 characters; empty and blank notes; same-status and changed-status updates | MT-12, API-05, API-16, API-17, API-18, UI-11 | – |
| TD-08 | Image files | Valid JPG, PNG and WebP; GIF; 0-byte PNG; text file renamed `.jpg` / `.png`; image of exactly 5,242,880 bytes and of 5,242,881 bytes; image larger than 10 megapixels; corrupt JPG; truncated WebP | MT-08, MT-09, MT-12, UI-06, API-12, API-17, UT-08 | Images must not show real people or private places |
| TD-09 | Volume data | More than 10 reports for one citizen (UI page size 10); more than 24 approved reports (public page cap) | MT-22, UI-07, UI-12, API-14, API-19 | Basic JMeter volume TBC after targets are set *[As executed: Phase 5A, applied 25 Nov 2026: Member 4 ran 5 threads × 20 iterations = 100 requests to `/api/health` (no target set)]* |
| TD-10 | Paging and filter parameters | page −1/0; size 0/1/24/25/100/101; sort `foo`; status `in progress`, `ALL`, `DONE`; search text matching a reference | API-14, API-19, API-25, UT-04, UT-05 | Parameters, not stored data |
| TD-11 | Existing-database state for INT-01 | Schema with only V1 and V2 objects and no Flyway history table | INT-01 | **Assumption** from `baseline-version: 2`; needs confirmation (RTM Q2) |

### 12.3 Set-up requirements [Proposed]

1. Create an empty PostgreSQL test database and start the backend so that Flyway creates the schema.
2. Create the Admin account (TD-04) with the first-admin setup command, using fictitious details.
3. Register Citizen A and Citizen B (TD-02, TD-03) through the registration page or API.
4. As each citizen, create reports from TD-05 and upload photos from TD-08.
5. As Admin, move reports through the statuses and approvals in TD-06 and TD-07.
6. Create the volume data in TD-09.
7. Record the database state and code baseline before execution, and reset the database between runs when a test changes shared data (for example deletion tests).

### 12.4 Unverified data assumptions

| # | Assumption | Status |
|---|---|---|
| A-1 | Seeding through the UI/API is practical for the volume data in TD-09 | Partly confirmed (Phase 11): ordinary data was seeded through the API; the API-19 volume data was inserted through the repositories inside the test code, which needed no application change |
| A-2 | Image files with the exact boundary sizes in TD-08 can be produced | **Confirmed** (Phase 11): a valid PNG padded to exactly 5,242,880 and 5,242,881 bytes was generated and used in API-12 |
| A-3 | The reserved domain `example.test` passes the backend's email format validation | **Confirmed** (Phase 11): addresses on `qa.example.test`, including a 254-character address, were accepted (API-03) |
| A-4 | The INT-01 "existing database" state in TD-11 is correct | Unverified (RTM Q2) |

---

## Open Questions and Assumptions

### Open questions

The RTM document lists **Q1–Q8** ([Open questions](PART01_REQUIREMENTS_AND_RTM.md#open-questions)). Q4 and Q5 are partly resolved; the rest remain open. They are not repeated here, except that two of them affect this plan directly: **Q7** (API-05: is the `403` confirmed for login, logout and PUT?) and **Q8** (MT-21: does the dashboard show 4 or 5 recent reports?). The questions below are new to this document.

| # | Question | Affects |
|---|---|---|
| OQ-1 | Who are the group members, who takes each workstream and cross-cutting duty in §7.6, and who is named as the project owner for approvals (§7.11)? | §7.6, §7.11 |
| OQ-2 | What are the submission deadline and the dates for each phase? | §7.8 |
| OQ-3 | Which operating systems, browsers (with versions) and devices are available? Safari needs a macOS or iOS device | §11, MT-17 |
| OQ-4 | Which response-time targets should be used for the JMeter test? | §4, §5, assignment 3.7 |
| OQ-5 | Which tools will the group use (Playwright or Selenium; REST Assured or Postman; JMeter; SonarQube; GitHub Actions or Jenkins; Jira, Bugzilla or GitHub Issues)? Are they available? | §7.6, §11 |
| OQ-6 | Is there a staging or hosted deployment, or is all testing local? What value should the `Secure` cookie flag have? | §2.2, §11 |
| OQ-7 | Does the module expect ISO/IEC 25010:2011 names (used here) or the 2023 revision, which renames some characteristics (for example Usability → Interaction capability, Portability → Flexibility) and adds Safety? | §5 |
| OQ-8 | All 79 test cases have now been converted to the full format (§10.4). Does the group agree that this is what Part 01 needs, and who peer-reviews them (V-3)? | §10 |
| OQ-9 | Have the 72 *[Corrected: Phase 3B; was 73]* existing backend test methods ever been run, and with what result? The source documents do not say. *Answered (Phase 3A/3B):* yes. On this branch at `f565fb4`: 72 tests, 0 failures (25 Nov 2026, Java 27, H2). Members 2–4 also report 72 tests, 0 failures in their own copies (`qa/CROSS_REFERENCE.md` §4). Not run here on Java 21 or PostgreSQL. *[Java 21 run, 25 Nov 2026 (applied 25 Nov 2026): the "not run here on Java 21" statement is the Phase 3A/3B record. The suite of the integration working tree (`f565fb4` + uncommitted D-1) was later run here on Java 21.0.13.1 (Ubuntu OpenJDK, Linux – WSL2, Ubuntu 26.04.1): 85 tests, 0 failures, 0 errors, 0 skipped, H2 in-memory. Still not run on PostgreSQL. Console log / Maven version evidence pending (register ENV-J10)]* | §7.5, §9.3 |
| OQ-10 | *Partly answered:* no CI workflow file is tracked at baseline `f565fb4` (no `.github/` folder or `Jenkinsfile`). Still open: will one be added to the application repository (needs approval, §7.11) or kept elsewhere (OQ-12)? | §7.3, ENV-05 |
| OQ-11 | A folder named `for test` next to the project folder contains three JPG files. Are they intended as test images (TD-08)? This was noticed while searching for the assignment PDF and is not part of the source analysis | §12 |
| OQ-12 | Where will QA test code (unit tests, BDD scenarios, automation scripts) and any CI workflow be kept: in the application repository, with the project owner's approval, or in a separate QA location? | §7.11, assignment 3.5, 3.6 |

### Assumptions

| # | Assumption |
|---|---|
| AS-1 | The corrected working copy ([PART01_REQUIREMENTS_AND_RTM.md](PART01_REQUIREMENTS_AND_RTM.md)) is the authoritative source of counts: 58 FRs, 63 RTM rows, 28 business rules, 79 test cases. The archived report's 59 and 74, and the first working copy's 61 rows and 75 test cases, are superseded |
| AS-2 | Testing follows the behaviour in the code where project documentation disagrees, as the analysis recommends |
| AS-3 | Test execution will use code baseline `f565fb4` unless the group records a newer baseline |
| AS-4 | Steps and preconditions in the §10.4 test cases, and the expected results of the UI and UT cases, are derived from the scenario and requirement descriptions and need checking against the running application |
| AS-5 | The assignment's STLC phase names (requirement analysis, test planning, test case design, execution, closure) are used, with "Test Execution" and "Test Closure" as headings |
| AS-6 | The schedule, resources, exit criteria and severity decisions are proposals until the group agrees on them |
| AS-7 | The source-protection rule in §7.11 was stated by the project owner and applies to all QA activities |

### Contradictions noted

| # | Contradiction | Handling |
|---|---|---|
| C-1 | Archived report: 59 FRs and 74 test cases. Working copy: 58 FRs and 79 test cases (75 before the Part 01 review) | The working copy is used (AS-1) |
| C-2 | Five places where project documentation contradicts the code (RTM document §7) | Tests follow the code (AS-2) |
| C-3 | The existing inventory is called "Test Case Candidates"; the assignment says "write test cases in a proper format" | The inventory is the source list; §10 defines the required format and §10.4 holds all 79 cases in it |
| C-4 | FR-06.1 says the dashboard shows the 5 most recent reports; the code displays at most 4 | Recorded as RTM Q8; MT-21 keeps that check blocked until answered |
| C-5 | FR-01.3 says the email is trimmed and API-04 expects `409` for a padded duplicate, but at baseline `f565fb4` `@Email` validated the untrimmed value, so a padded email got `400` (finding F-03, Member 3) | Resolved by owner decision D-1 (Option A, RTM "Owner decisions"): the email is trimmed at the API boundary before validation, for registration and login. API-04 executed after the change: PASS (§10.4) |

---

## As Executed Addendum

> *[As executed: prepared in Phase 5A; Java 21 notes from Phase 5B revision 1; applied 25 Nov 2026 to this canonical copy after owner decision D-8; INT-01 result added in Phase 8A, 25 Nov 2026]*
> This addendum sits next to the original plan and **does not replace** it. Every planned value in this document stays as written. It records what was actually executed, by member role, on which baseline and in which environment.
> **It does not claim that a full integrated test run has passed.** No run combines all members' tests (confirmation register UNA-1).
> Source: [PART01_AS_EXECUTED_ADDENDUM.md](../phase5a-p0-corrections/PART01_AS_EXECUTED_ADDENDUM.md). Open confirmations: [CONFIRMATION_REGISTER.md](../phase5b-confirmation-register/CONFIRMATION_REGISTER.md).

**Confirmations still pending for this addendum** (left blank on purpose; nothing is inferred):

| Field | Value | Register ID |
|---|---|---|
| Part 01 author(s) | Member 1 – Madusanka D.M.C, EG/2023/5720 *[Phase 9, 25 Nov 2026: supplied by the project owner]*. Co-authors: PENDING | P-M1-1 (confirmed, Rev. 7), P-M1-3 |
| Person who prepared and applied this addendum (integrator) | PENDING | P-OW-3 |
| Tester for API-04 (25 Nov 2026) | PENDING | P-OW-4 |
| Member names and registration numbers | Supplied by the project owner, 25 Nov 2026 (Phase 9); see "Work allocation as executed" | P-M1-1, P-M1-2, P-M2-1, P-M2-2, P-M3-2, P-M3-3, P-M4-12 (confirmed, Rev. 7) |
| Planned start/end dates; final submission deadline | TBC | P-SC-1, P-SC-2 |
| Maven console log and `./mvnw -v` output for the Java 21 run | PENDING | ENV-J10 |

### AE-A. Counts – three different numbers that must not be mixed

| Quantity | Value | What it is | Verified by |
|---|---:|---|---|
| Original baseline backend test **methods** | **72** | `@Test` methods in 12 classes at `f565fb4` (the 73rd text match is `@TestConfiguration`, `AuthControllerTest.java:45`) | `git grep` on commit `f565fb4` (Phase 5A); Surefire 72/0 in M2, M3, M4 and Phase 3A baseline runs |
| Part 01 proposed **test cases** | **79** | MT 22, UI 14, API 25, UT 17, INT 1, defined in §10.4 and RTM TASK 6 | 79 unique `####` case headings in §10.4, no duplicates (Phase 5A recount) |
| JUnit tests run on this branch after Phase 3A | 85 | 72 existing + 13 new (`AuthEmailNormalizationApiTest` 7, `AuthDtosTest` 6) | Phase 3A, Java 27: `qa/phase3a-api04-correction/03-full-suite-after-fix.txt` (85/0). *[Java 21 run, 25 Nov 2026:]* Java 21.0.13.1: `qa/evidence/java21-run/surefire-reports/` (14 classes; 85 tests, 0 failures, 0 errors, 0 skipped) |

72 and 85 are counts of **JUnit tests**. 79 is a count of **Part 01 test cases**. A JUnit test is not a Part 01 test case, so these numbers are not comparable. Running the 85 JUnit tests does not execute the 79 Part 01 cases.

### AE-B. Part 01 test cases – execution status as executed

Status vocabulary: §10.3 (`PASS`, `FAIL`, `BLOCKED`, or `PROPOSED / NOT EXECUTED`).

| Part 01 case(s) | Status | Baseline | Environment | Evidence |
|---|---|---|---|---|
| **API-04** – duplicate email with different case or padding | **PASS** (25 Nov 2026) | `f565fb4` + uncommitted owner decision D-1 (`AuthDtos.java` trims the email before validation) | **Java 27** (Oracle 27+35), Maven Wrapper 3.9.16, Spring Boot 3.5.16, **H2**, **MockMvc** (`@SpringBootTest` + `@AutoConfigureMockMvc`); not a real HTTP server or browser | `qa/phase3a-api04-correction/` (logs 00–03, Surefire XML 6/0 and 7/0). Tester: PENDING (P-OW-4) |
| **INT-01** – Flyway migration and schema-ownership check *[Phase 8A]* | **PARTIALLY EXECUTED** (25 Nov 2026): (a) **PASS**, (b) **PASS**, (d) **PASS**, (c) **BLOCKED** (RTM Q2) | `f565fb4` + uncommitted D-1; jar SHA-256 `dd1b8b18…0d9ce0` | **PostgreSQL 18.6**, dedicated local database `publiccare_int01_qa`, role `publiccare_int01_app`; JetBrains Runtime 21.0.8 (Java 21), Windows 11; Flyway 11.7.2; real HTTP server on `127.0.0.1:18081` (health endpoints only) | `qa/phase7-final-completion/int01-postgresql/run-20261125-125857/` (16 files). Run by the project owner; checked by the integrator. Tester name: PENDING (P-OW-1) |
| **39 cases** *[Phase 11]*: API-01 – API-03, API-05 – API-25, UT-01 – UT-15 | 32 **PASS**; 6 **PASS** with one part **BLOCKED** (API-16, API-18, UT-04, UT-06, UT-08, UT-14); 1 **BLOCKED** (API-24 – defect PC-QA-02); API-21 PASS with a limitation (counted in the 32) | `f565fb4` + uncommitted D-1; production code SHA-256-identical | ENV-P11 (§11.3): Windows 11, Oracle Java 27, Maven Wrapper 3.9.16, H2, real embedded Tomcat | [phase11-part01-exec/](../phase11-part01-exec/README.md). Tester: integration (Phase 11) |
| All other **38** cases *[Phase 11; was 77]* | 22 MT and 8 UI **NOT EXECUTED**; UT-16, UT-17 **BLOCKED**. UI-01 – UI-05 and UI-13 are counted here: **PASS in the Member 3 copy only** (AE-C.1) | – | – | No group-repository execution |

**Verification status of the integration working tree (`f565fb4` + D-1):**

| Verification | Status |
|---|---|
| Backend suite on **Java 21** | Phase 5A record: **NOT EXECUTED** (only Java 27 was available in Phase 3A). *[Java 21 run, 25 Nov 2026 – supersedes the Phase 5A record for Java 21 only:]* **Executed** on Java 21.0.13.1 (Ubuntu OpenJDK `21.0.13.1+2-1-26.04.1-Ubuntu`; Linux – WSL2, Ubuntu 26.04.1): **85 tests, 0 failures, 0 errors, 0 skipped, H2 in-memory** (register ENV-J6). Surefire reports archived (28 files). The Maven console log and `./mvnw -v` output are not archived, so `BUILD SUCCESS` and the Maven version used rest on the integrator's report (ENV-J10, **PENDING**) |
| Any run on **PostgreSQL** for `f565fb4` + D-1 (or for any member) | Phase 5A record: **NOT EXECUTED** (all runs used H2). *[Phase 8A, 25 Nov 2026 – supersedes the Phase 5A record for INT-01 only:]* **Executed for INT-01** on PostgreSQL 18.6 (scenarios a, b, d PASS; c BLOCKED). The JUnit suite and all member tests have **not** been run on PostgreSQL |
| Selenium, browser or other UI tests on the group baseline | **NOT EXECUTED** (Selenium tests exist only in the Member 3 copy; AE-C.1). Phase 11 tested the API over real HTTP, not through a browser |
| Combined run of all members' tests | **NOT EXECUTED** (needs owner decisions D-3, D-4, D-5; register UNA-1) |
| MT-04 (manual UI duplicate email with padding) | **NOT EXECUTED** |

### AE-C. Member-specific isolated runs (reported separately – not Part 01 results)

These runs were made in **separate member copies** of the application, with **different baselines** (see `qa/phase5a-p0-corrections/BASELINE_DEVIATIONS.md`) and **different JDKs**. They are evidence for Sections 3.3–3.10. They are **not** recorded as Part 01 PASS results, because none of them ran on the Part 01 baseline. Most of them also cover only part of the case.

**AE-C.1 Member 3 (Sections 3.5–3.6) – copy `93d11a3`, local Windows 11, Temurin 25.0.5, H2**

Coverage classification is Member 3's own (unapplied diff `part01-execution-status-edits_2c1e597..93d11a3.diff`, which is **not** applied to this document; register REJ-4).

| Part 01 case | Member 3 result | Coverage of the case | Evidence |
|---|---|---|---|
| UT-14 | Red → Green → Refactor; 6/0 after an **application change in the M3 copy** | Full (per M3). Depends on the unapplied `AdminSetupRunner` change; RTM Q1 open | `qa/3.5-3.6_member3/evidence/tdd/` |
| API-01 | PASS | Full (per M3) | `qa/3.5-3.6_member3/evidence/api-automation/` |
| API-02 | PASS | Partial (steps 1–3) | same |
| API-03 | PASS | Partial (password 11/12 only) | same |
| API-04 | Case: `409` as expected. Padding: `400` – **superseded** by D-1 (historical result, kept) | Partial; superseded by AE-B | same |
| API-08 | PASS | Partial (steps 1–2) | same |
| API-09 | PASS | Partial (1 endpoint, `GET /api/admin/reports`) | same |
| API-10 | PASS | Partial (steps 1–2) | same |
| API-15 | PASS | Partial (GET only) | same |
| UI-01, UI-02, UI-03, UI-04, UI-05, UI-13 | PASS (Selenium, Chrome 157 headless, local only, Member 3 copy) | Full (per M3) | `qa/3.5-3.6_member3/evidence/ui-automation/` |

Totals for Member 3: 8 cases fully exercised and 7 partly exercised, all in the Member 3 copy. The 91-test backend suite also passed on GitHub Actions (Java 21) for commit `70d01e0` only. The Selenium tests have no CI result.

**AE-C.2 Member 4 (Sections 3.7–3.10) – copy `b97bd96` (+ `f78deb1` for the "after" run), Linux, Java 21.0.13.1 (Azul Systems, Inc.), H2**

Environment as recorded in `qa/3.7-3.10_member4/evidence/sonar-run.txt`; Member 4's confirmation of the environment details is pending (P-M4-10). Member 4 did not cite these Part 01 IDs. The link is an **[integrator note]**.

| Part 01 case | What Member 4 did | Coverage of the case | Evidence |
|---|---|---|---|
| API-08 | Anonymous `GET /api/auth/me` → `401 {"error":"Authentication required."}` | Step 1 only | `qa/3.7-3.10_member4/evidence/access-checks.txt` |
| API-09 | Anonymous `GET /api/admin/reports` → `401` | Anonymous row, 1 endpoint | same |
| API-21 | `GET /api/health` → `200 UP`; 100/100 JMeter samples | `/api/health` only (`/actuator/health` not checked) | `access-checks.txt`, `health.jtl` |
| UI-04 | Signed-out create page at 390 × 844: submit disabled, sign-in link | Related; used a synthetic `401` session fixture | `evidence/mobile.txt` |
| MT-02 | Not executed. PC-QA-01 is the **recovery path after** the MT-02 mismatch step | Related only | `registration-before.txt/.png` |

**AE-C.3 Member 2 (Sections 3.3–3.4) – copy `0ad36bd`, Windows 11, Microsoft OpenJDK 21.0.13.1, H2/MockMvc**

Member 2 used its own IDs (EP-01–EP-05, BVA-01–BVA-07, DT-01–DT-06, paths A1–A3, B1–B2). These relate to Part 01 cases at a different test level (API-03, API-11, API-05/09/20, UT-01/03, API-07; see `qa/CROSS_REFERENCE.md` §2). **No Part 01 case was executed as written.**

### AE-D. Status statements in Part 01 that this addendum scopes (not deleted)

| Part 01 statement (current text kept) | Read it as |
|---|---|
| Header: "No test case has been executed, and it records no actual results, pass/fail data, defects or measured quality values" (exception: API-04) | True **for this document's Part 01 test cases**. Executed work for Sections 3.3–3.10 is reported by Members 2–4 in their own sections |
| §5.3 "Verified quality result: None" | No ISO 25010 attribute is verified **by Part 01 test cases**. Members 2 and 4 report measured coverage (M2: lines 433/641, branches 126/302; M4: lines 434/642, branches 126/302 – the M4 line figure is the integration re-sum of Member 4's own `jacoco.csv`, Member 4's acceptance pending, P-M4-1), SonarQube measures (M4) and a JMeter baseline (M4). These are measurements from separate copies, not verified Part 01 quality results |
| §7.3 rows 3.3–3.10 "Planned"; §7.8 rows 4–10 "Not started"; §7.9 "Not started"; §8 phases 4–5 "Not started" | Planned values at the time of writing (23 Nov 2026). Actual state on 25 Nov 2026: the "As executed" additions in §7.3, §7.8, §7.9 and §8 |
| RTM line 4 "None of them have been run" | Same scope as the header |
| §1.4, §9.3 and OQ-9 "not run on Java 21" | Phase 3A record, kept. Superseded for Java 21 by the Java 21 run (AE-B). The "not run on PostgreSQL" part is still true for the JUnit suite *[Phase 8A: INT-01 (a), (b), (d) ran on PostgreSQL 18.6; AE-B]* |

### AE-E. What this addendum does not claim

- No combined run of all members' tests exists. Test counts from different copies (72, 84, 91, 6, 72, 85) must not be added together.
- No Part 01 case other than API-04 is recorded as PASS. 78 of the 79 cases remain PROPOSED / NOT EXECUTED. *[Phase 8A, 25 Nov 2026: INT-01 is recorded as PARTIALLY EXECUTED (three scenarios PASS, one BLOCKED), not as PASS. 77 of the 79 cases remain PROPOSED / NOT EXECUTED.]*
- *[Phase 5A text: "Nothing has been run on Java 21 or on PostgreSQL for the code now in the integration working tree." Superseded for Java 21 only (Java 21 run, 25 Nov 2026).]* The backend suite of the integration working tree ran on Java 21 (85/0, H2 in-memory; register ENV-J6). Nothing has been run on **PostgreSQL**. *[Phase 8A: superseded for INT-01 scenarios (a), (b), (d) only – run by the project owner on PostgreSQL 18.6.]*
- The Java 21 run does not cover PostgreSQL, Selenium or other browser tests, the frontend, or member test code that is not in the group repository.
- Member 3's Java 21 CI result applies to commit `70d01e0` only.
- PC-QA-01 is fixed only in the Member 4 copy. It is **still open** in the group baseline.
- The D-1 change is still uncommitted. Owner decisions D-2, D-3, D-4 and D-5 and RTM questions Q1, Q2 and Q3 are still open; this addendum does not decide them. *[Phase 8A: Q3 answered by the project owner – dedicated local PostgreSQL database (register P-OW-Q3). Q1 and Q2 are still open.]*

### AE-F. Phase 11 – Part 01 execution campaign (25 Nov 2026)

| Item | Record |
|---|---|
| Why | 77 of the 79 cases had never been executed, although most needed no new tool and no application change |
| What was run | 39 Part 01 cases written as 40 JUnit tests in 3 classes: `Part01ApiExecutionTest` (API-01 – API-03, API-05 – API-22, API-24, API-25, UT-04, UT-05, UT-07, UT-11 + 1 control), `Part01Api23ServerErrorTest` (API-23), `Part01UnitExecutionTest` (UT-01 – UT-03, UT-06, UT-08 – UT-10, UT-12 – UT-15) |
| Where | An isolated copy of `backend/` (without `.env` and `target/`). The production code was SHA-256-compared with the repository before and after the runs: identical (53 files). The test classes are **not** in the application source tree; they are kept as evidence in [`qa/phase11-part01-exec/test-code/`](../phase11-part01-exec/test-code/) (§7.11, OQ-12 still open) |
| Runs | Run 1: 40 tests, 39 passed, 1 error (UT-05 – the test expected a bare `IllegalArgumentException`; Spring wraps it). Run 2 after correcting that test: **40 / 0**. Run 3, full suite: **125 tests, 0 failures** (85 existing + 40) |
| Results | 32 PASS, 6 PASS with one part BLOCKED, 1 BLOCKED (API-24). Defect **PC-QA-02**; observations OBS-1 (IPv6 loopback), OBS-2 (exception wrapping), OBS-3 (health details hidden) |
| Expected results | Not changed. Blocked parts were observed and recorded, not judged |
| Not done | The 22 manual cases, 8 UI cases and 2 Vitest cases. No PostgreSQL run of these cases (H2 only). Not run in CI. Nothing committed |
