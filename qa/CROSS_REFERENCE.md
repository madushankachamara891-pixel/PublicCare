# QA Cross-Reference – Phase 1 (Integration Draft)

> **Phase 3A update (25 Nov 2026):** F-03 / U-5 resolved by owner decision D-1 (FR-01.3, Option A: trim the email before validation). The code change in `backend/.../auth/AuthDtos.java` is in the working tree and **not committed**. Evidence: [phase3a-api04-correction/](phase3a-api04-correction/README.md). Rows changed: §1 M1, §2 API-04, §4 counts, §6 F-03, §8 U-2 and U-5. Nothing else in this file was re-checked in Phase 3A.
>
> **Phase 3B update (25 Nov 2026):** Part 01 consistency audit. The existing test-method count is corrected from 73 to 72 in the RTM and Test Plan (both synchronized copies) and OQ-9 is answered; the archived Analysis Report is kept unchanged. Member 3's test `emailWithSurroundingSpacesIsRejectedByValidation` is marked **superseded** by owner decision D-1. Rows changed: §3 M3 REST Assured, §4 statements and resolution, §6 F-03 and F-04, §7 OQ-9, §8 U-1 and U-2.

> **Phase 5A update (25 Nov 2026):** P0 corrections, documents only. §5 M4 backend line coverage corrected from 433 / 641 to **434 / 642 = 67.60%** (Member 4's own `jacoco.csv`); U-8 adjusted to match. §3 M3 BDD scenario 4 mapping corrected from FR-08.5 to FR-02.8 / FR-06.6. Member 2 coverage figures were not changed. Recommended M4 requirement IDs are listed in `phase5a-p0-corrections/REQUIREMENT_MAPPING_REVIEW.md` and are **not** applied here. Rows changed: §3 M3 scenario 4, §5 line coverage, §8 U-8.

> **Phase 5B rev. 2 update (25 Nov 2026):** owner decision **D-8 APPROVED, option CAN-1**. The canonical Part 01 copy is `qa/3.1-3.2_member1/` in this repository (`publiccare/qa/3.1-3.2_member1/`). The outer `Public care/qa/` files are a non-canonical reference copy, kept unchanged and no longer synchronised. References to "both synchronized copies" below describe the state up to D-8. Row changed: §4 resolution (note added). No mapping, count or finding changed.

This file links the four members' work by test ID, requirement, defect and finding. Rows come from the members' documents. Where the integrator noticed a relationship that no member stated, the row is marked **[integrator note]**. These notes are suggestions for review, not findings.

Abbreviations: **M1–M4** = Member 1–4. **RTM** = `3.1-3.2_member1/PART01_REQUIREMENTS_AND_RTM.md`. **TP** = `3.1-3.2_member1/PART01_TEST_PLAN_AND_STLC.md`. **AR** = `3.1-3.2_member1/PROJECT_ANALYSIS_REPORT.md`.

## 1. Test ID schemes

| Owner | IDs | Defined in | Status in the integrated documents |
|---|---|---|---|
| M1 | MT-01–MT-22 (manual), UI-01–UI-14, API-01–API-25, UT-01–UT-17, INT-01: **79 cases** | RTM TASK 6; TP §10.4 | All PROPOSED / NOT EXECUTED (newest local version), except API-04: PASS (Phase 3A, 25 Nov 2026) |
| M2 | EP-01–EP-05, BVA-01–BVA-07, DT-01–DT-06; white-box paths A1–A3, B1–B2 | M2 report §3.3–§3.4 | Executed in the M2 copy, except DT-05, DT-06, B1 (NOT EXECUTED) |
| M3 | Uses M1 IDs (UT-14, API-xx, UI-xx); BDD scenarios numbered 1–4 | `evidence/*/README.md` | Executed in the M3 copy (see §2) |
| M4 | PC-QA-01 (defect); other checks are named by requirement, not by ID | M4 report | Executed in the M4 copy |

## 2. Part 01 test cases touched by later sections

"Result" is what the member reports. "Part 01 status" is what the integrated RTM/TP currently say.

| Part 01 ID | Scenario (short) | Member and work | Result reported | Evidence | Part 01 status |
|---|---|---|---|---|---|
| UT-14 | `AdminSetupRunner.isLoopbackPostgres` incl. `[::1]` | M3 TDD, `AdminSetupRunnerLoopbackTest` (6 tests) | Red confirmed `[::1]` rejected; Green and Refactor pass **after a code change in the M3 copy** | `3.5-3.6_member3/evidence/tdd/` | NOT EXECUTED |
| API-01 | `GET /api/auth/csrf` | M3 REST Assured | PASS | `…member3/evidence/api-automation/` | NOT EXECUTED |
| API-02 | Register incl. `"role":"ADMIN"` | M3 REST Assured | PASS (M3 marks it partly executed: steps 1–3) | same | NOT EXECUTED |
| API-03 | Register boundaries | M3 REST Assured (password 11/12 only) | PASS for the part run | same | NOT EXECUTED |
| API-04 | Duplicate email, case or padding | M3 REST Assured (M3 copy, before the change); **integration Phase 3A** (`AuthEmailNormalizationApiTest`, this repository, after owner decision D-1) | M3: case → 409 (as expected); **padding → 400, expected 409** (see F-03). Phase 3A: padded mixed-case → 201 stored as lower-case trimmed; lower-case and padded upper-case duplicates → 409; padded duplicate of unpadded → 409 | M3 `api-automation/`; `phase3a-api04-correction/` | **PASS** (25 Nov 2026, TP §10.4; RTM FR-01.3, FR-01.4) |
| API-08 | `/me` without/with session, after logout | M3 REST Assured (steps 1–2); M4 anonymous check | 401 / 200 (M3); 401 anonymous (M4) | M3 `api-automation/`; M4 `evidence/access-checks.txt` | NOT EXECUTED |
| API-09 | `/api/admin/**` × {anonymous, citizen, admin} | M3 (one endpoint `GET /api/admin/reports`); M4 (anonymous only, same endpoint) | 401 / 403 / 200 (M3); 401 (M4) | same as above | NOT EXECUTED |
| API-10 | Create report with another `ownerId` | M3 REST Assured | PASS (steps 1–2) | M3 `api-automation/` | NOT EXECUTED |
| API-15 | Another user's report vs missing ID | M3 REST Assured (GET only) | PASS | same | NOT EXECUTED |
| API-21 | `/api/health` anonymously | M4 access check and JMeter (100 requests) **[integrator note: M4 does not cite API-21]** | 200 `UP`; 100/100 success | M4 `evidence/access-checks.txt`, `health.jtl` | NOT EXECUTED |
| UI-01 | Register form boundaries and mismatch | M3 Selenium | PASS | `…member3/evidence/ui-automation/` | NOT EXECUTED |
| UI-02 | Login redirect by role | M3 Selenium | PASS | same | NOT EXECUTED |
| UI-03 | Failed login generic alert | M3 Selenium | PASS | same | NOT EXECUTED |
| UI-04 | Signed out on `/create-issue` | M3 Selenium; M4 mobile walkthrough (FR-03.8, 390 × 844) **[integrator note: M4 does not cite UI-04]** | PASS (both) | M3 `ui-automation/`; M4 `evidence/mobile.txt` | NOT EXECUTED |
| UI-05 | Create form required fields and success | M3 Selenium | PASS | M3 `ui-automation/` | NOT EXECUTED |
| UI-13 | Citizen on admin pages sees the gate | M3 Selenium | PASS | same | NOT EXECUTED |
| MT-02 | Confirm password differs → browser blocks submit | M4 usability walkthrough found PC-QA-01 on the *recovery* path after this step **[integrator note: M4 cites FR-01, not MT-02]** | Mismatch blocked as expected; correction then still blocked (defect) | M4 `evidence/registration-before.*` | NOT EXECUTED |

Related cases that members tested at a different level **[integrator note]**:

| Part 01 ID | Related member work | Difference |
|---|---|---|
| API-11 (title 160/161 …) | M2 BVA-01–BVA-07 (title 0, 1, 2, 80, 159, 160, 161) | M2 uses MockMvc with a mocked service; API-11 is a full API test and also covers other fields |
| API-03 (register boundaries) | M2 EP-01–EP-05 (password 8, 16, 73 chars; invalid email; empty name) | Representative partition values, not the API-03 boundary values |
| API-05, API-09, API-20 (CSRF and role on admin DELETE) | M2 DT-01–DT-04 | One existing MockMvc test; DT-05/DT-06 not executed |
| UT-01, UT-03 (`AuthService.register`, byte limit) | M2 paths A1–A3 (existing tests) | M2 traced existing tests; no new unit test |
| UT-02 (concurrent duplicate save) | M2 §3.4.1: the `DataIntegrityViolationException` path was not exercised | Confirms the gap recorded in UT-02 |
| API-07 (73-byte password on login) | M2 path B1: NOT EXECUTED | Same gap seen from the white-box side |

Part 01 cases with no execution by any member: all MT cases, UI-06–UI-12, UI-14, API-05–API-07, API-11–API-14, API-16–API-25 (API-21 see above), UT-01–UT-13, UT-15–UT-17, INT-01.

## 3. Member test IDs → requirements

### Member 2 (3.3–3.4)

| IDs | Requirement(s) | Feature | Result |
|---|---|---|---|
| EP-01–EP-05 | FR-01.1, FR-01.2 | Registration `POST /api/auth/register` | 5 PASS |
| BVA-01–BVA-07 | FR-03.1, FR-03.2 | Report title length `POST /api/reports` | 7 PASS |
| DT-01 | FR-08.1 | Admin delete, valid CSRF | PASS |
| DT-02 | FR-02.8 | Anonymous delete | PASS |
| DT-03 | FR-02.8, FR-08.5 | Citizen delete | PASS |
| DT-04 | FR-02.1 | Admin delete without CSRF | PASS |
| DT-05, DT-06 | FR-02.1, FR-08.5 | Anonymous / citizen without CSRF | NOT EXECUTED |
| A1–A3 | `AuthService.register()` | V(G) = 3 | 3 paths PASS (existing tests) |
| B1, B2 | `AuthController.login()` | V(G) = 2 | B2 PASS; B1 NOT EXECUTED |

### Member 3 (3.5–3.6)

| Item | Requirement(s) | Result |
|---|---|---|
| TDD UT-14 | FR-06.9 | Red → Green → Refactor (needs the unapplied code change, F-02) |
| BDD scenario 1: register, log in, submit report | FR-01.1, FR-01.5, FR-02.2, FR-03.1, FR-03.3 | PASS |
| BDD scenario 2: visitor cannot submit | FR-02.8 | PASS |
| BDD scenario 3: admin moves report to in progress | FR-06.6, FR-05.2 | PASS |
| BDD scenario 4: citizen cannot change status | FR-02.8 (primary), FR-06.6 (endpoint under test) *[Corrected: Phase 5A. Previously "FR-02.8, FR-08.5". The step sends a citizen `PATCH /api/admin/reports/{id}/status` and asserts `403` (`PublicCareSteps.java:222-231`); no delete request is made, so FR-08.5 "Citizens cannot delete reports" does not apply. Corresponds to one cell of API-09 (citizen × PATCH status). Member 3's feature file and README are unchanged; see `phase5a-p0-corrections/REQUIREMENT_MAPPING_REVIEW.md`]* | PASS |
| REST Assured, 9 tests | API-01, 02, 03, 04, 08, 09, 10, 15 (part) | 9 PASS (M3 copy, before D-1). One of them, `emailWithSurroundingSpacesIsRejectedByValidation` (asserts `400` for a padded email), is **superseded** by owner decision D-1; see F-03 |
| Selenium, 6 tests | UI-01–UI-05, UI-13 | 6 PASS (local only) |
| GitHub Actions run #1 | TP §7.3 / OQ-10 | Success, 91 tests, on commit `70d01e0` |

### Member 4 (3.7–3.10)

| Check | Requirement(s) | Result |
|---|---|---|
| JMeter, 5 users × 20 requests, `/api/health` | No requirement; TP OQ-4 (no response-time target) | 100/100 success; mean 9.37 ms, p95 17 ms, 14.6 req/s. No pass/fail verdict |
| Anonymous `GET /api/admin/reports` | FR-06 (admin restriction); OWASP Broken Access Control | 401 |
| Anonymous `GET /api/auth/me` | FR-02 | 401 |
| Registration typo recovery | FR-01 | **Failed → defect PC-QA-01**; passed after fix in the M4 copy |
| Signed-out create page at 390 × 844 | FR-03.8 | Pass |
| Keyboard login and show/hide password | FR-02 | Pass |
| SonarQube and JaCoCo | TP §2 Maintainability (RTM §2.5) | See §5 |

## 4. Test counts and the 72 vs 73 discrepancy

### What the documents say

| Source | Statement |
|---|---|
| Before Phase 3B: AR line 80 and line 341; RTM "Existing tests" row and §2.5 list (line 107 and line 368 in Phase 1; line 115 and line 376 now); TP line 78, line 201, line 590 | "73 backend `@Test` methods in 12 classes". **Phase 3B:** RTM and TP now say 72, marked *[Corrected: Phase 3B]*. AR (archive, kept unchanged) still says 73 |
| TP OQ-9 (line 1991) | Before Phase 3B: "Have the 73 existing backend test methods ever been run, and with what result?" **Phase 3B:** count corrected to 72 and the question answered (72 tests, 0 failures on this branch in Phase 3A) |
| M2 report §1 | Measured baseline is 72; "used rather than the 73 test methods mentioned in the earlier planning document" |
| M3 `evidence/tdd/README.md`, `evidence/baseline/README.md` | A text search for `@Test` also matches `@TestConfiguration` at `AuthControllerTest.java:45`; the real count is 72 |
| M4 `evidence/junit-summary.txt` | 72 tests, 0 failures |

### Integrator check on `f565fb4`

`grep '@Test'` in `backend/src/test` gives **73** matches in **12** classes. One match is `@TestConfiguration(proxyBeanMethods = false)` at `auth/AuthControllerTest.java:45`, so there are **72** `@Test` methods. There are no `@ParameterizedTest`, `@RepeatedTest` or `@TestFactory` annotations at the baseline. This agrees with M3's explanation and with the Surefire count of 72 in all three member copies.

**Resolution (Phase 3B, 25 Nov 2026):** 72 is the correct count, re-verified on the `f565fb4` source (72 `@Test` methods in 12 classes) and by the Phase 3A baseline run (72 tests). Corrected in the RTM (2 places) and the Test Plan (3 places plus OQ-9), in both synchronized copies (`qa/3.1-3.2_member1/` and the local `Public care/qa/` originals). The archived Analysis Report (AR lines 80 and 341) is deliberately not edited: the RTM records it as "kept unchanged as the original record". The 85 tests in the Phase 3A run are 72 existing + 13 new; 85 is not the original count. *[Phase 5B rev. 2: since owner decision D-8 (CAN-1), `qa/3.1-3.2_member1/` is the only canonical copy; the `Public care/qa/` originals are a non-canonical reference copy and are no longer synchronised.]*

### Test counts by copy – do not add these together

Each count comes from a different, isolated copy. No copy contains all members' changes, and no combined run exists. Adding the figures (for example 84 + 91 + 72) would give a meaningless total.

| Copy | Run | Environment | Tests | Failures | Made up of |
|---|---|---|---:|---:|---|
| M2 | Baseline | Windows, Java 21 | 72 | 0 | Existing tests |
| M2 | Final with JaCoCo | Windows, Java 21 | 84 | 0 | 72 + 12 parameterized invocations from 2 new methods (`registrationEquivalencePartitionTest`, `reportTitleBoundaryTest`) |
| M3 | Baseline | Windows, **Java 25** | 72 | 0 | Existing tests |
| M3 | Final backend | Windows, Java 25 | 91 | 0 | 72 + 6 TDD + 4 Cucumber scenarios + 9 REST Assured |
| M3 | GitHub Actions run #1 | Linux, Java 21 | 91 | 0 | Same 91, on commit `70d01e0` |
| M3 | Selenium (separate `*IT` class) | Windows, Java 25, Chrome 157 | 6 | 0 | Not part of the 91 |
| M4 | Backend suite | Linux, Java 21 | 72 | 0 | Existing tests |
| This repository (`f565fb4`) | Phase 1: not run | – | 72 methods (static count) | – | – |
| This repository (`f565fb4`, unchanged) | Phase 3A baseline, 25 Nov 2026 | Windows 11, **Java 27** | 72 | 0 | Existing tests (`phase3a-api04-correction/00-baseline-before-change.txt`) |
| This repository (`f565fb4` + uncommitted D-1 change) | Phase 3A after the FR-01.3 change, 25 Nov 2026 | Windows 11, **Java 27** | 85 | 0 | 72 existing + 13 new (`AuthEmailNormalizationApiTest` 7, `AuthDtosTest` 6). Does **not** include M2–M4 test additions (`phase3a-api04-correction/03-full-suite-after-fix.txt`) |

## 5. Shared metrics across sections

| Metric | M2 (3.4) | M4 (3.9) | Consistent? |
|---|---|---|---|
| Backend line coverage | 433 / 641 = 67.55% (JaCoCo 0.8.12) | 434 / 642 = 67.60% (JaCoCo 0.8.13) *[Corrected: Phase 5A. Previously "433 / 641 = 67.55%"; re-summed from `3.7-3.10_member4/evidence/jacoco.csv`]* | Close, not identical. Two independent runs on 72-test baselines. The one-line difference is in `lk.publiccare.backend.report` (437 lines in M4's CSV, 436 in M2's E09), consistent with different JaCoCo versions attributing lines differently. *[Corrected: Phase 5A. Previously "Yes"]* |
| Backend branch coverage | 126 / 302 = 41.72% | 126 / 302 = 41.72% | Yes |
| Coverage after M2's 12 added invocations | Unchanged (67.55% / 41.72%) | – | – |
| SonarQube coverage | – | 36.0% | Not comparable: includes frontend code that has no coverage data (M4 explains this) |
| Code coverage for M3's added tests | Not measured (M3: no JaCoCo added) | – | – |

## 6. Defects and findings

Integration keys F-01 to F-10 are added only to make cross-referencing easier. The member's own ID is kept where one exists.

| Key | Member's ID / name | Source | Summary | Classification by the member | Related Part 01 items | State in this repository |
|---|---|---|---|---|---|---|
| F-01 | **PC-QA-01** | M4 §3.8; GitHub issue #1 in the M4 repository | After a password mismatch, correcting only the first password still shows "Passwords do not match." and blocks submit | Defect, Medium / P2; root cause confirmed; fixed and verified **in the M4 copy only** (`f78deb1`) | FR-01; MT-02 (**[integrator note]**); M3 UI-01 tests the mismatch block but not the recovery path | **Not fixed**. Fix stored as `3.7-3.10_member4/unapplied-diffs/AccountPage-registration-fix_f78deb1.diff` |
| F-02 | IPv6 loopback `[::1]` | M3 `evidence/tdd/`; AR §2.6 | `isLoopbackPostgres` rejects `jdbc:postgresql://[::1]/…` | Behaviour confirmed by a failing test; whether it is a defect is open (RTM Q1) | UT-14, FR-06.9, RTM Q1 | **Not changed**. Fix stored as `3.5-3.6_member3/unapplied-diffs/AdminSetupRunner-ipv6-loopback_2c1e597..93d11a3.diff`. Note: UT-14 in RTM says changing this code needs the project owner's approval |
| F-03 | Padded duplicate email | M3 `evidence/api-automation/README.md` | Duplicate email with surrounding spaces returns 400, not the 409 that API-04 expects | Not a confirmed defect; expected result needs clarification (not yet in the RTM open questions). **Phase 3A:** cause confirmed from the response body (`400 {"error":"email must be a well-formed email address"}`: `@Email` validated the untrimmed value). Project owner chose **Option A** (owner decision D-1): trim the email before validation, for registration and login | API-04, FR-01.3; MT-04 uses ` user@x.com ` (**[integrator note]**: browsers strip surrounding spaces from `<input type="email">`, so MT-04 is likely unaffected; MT-04 not executed) | **Fixed in the working tree, not committed** (`AuthDtos.java`, compact constructors). Verified 25 Nov 2026: API-04 PASS; full suite 85/0 (Java 27, H2). See `phase3a-api04-correction/`. **Superseded test (Phase 3B):** M3's `emailWithSurroundingSpacesIsRejectedByValidation` (reference copy only, not in this build) asserts the old `400` and no longer describes the requirement; it is kept unchanged as historical evidence and replaced by `AuthEmailNormalizationApiTest` (padded email `201`/`409`) and `AuthDtosTest` |
| F-04 | 73 vs 72 test methods | M2 §1; M3 `tdd/README.md` | Part 01 count includes `@TestConfiguration` | Documentation error, explained | TP OQ-9; see §4 | **Corrected in Phase 3B** (RTM, TP, both copies; OQ-9 answered). Archived AR unchanged (still 73) |
| F-05 | Low branch coverage | M2 §4 | Backend branch coverage 41.72%; report package 39%; security package instructions 33% | Testing gap, not a defect | UT-02, API-07, many API cases | – |
| F-06 | SonarQube rule findings | M4 §3.9 | 3 bugs, 0 vulnerabilities, 88 code smells, 527 min debt, 0.0% duplication; quality gate passed. Named items: `ReportImageService` java:S3776 (line 101), java:S1192 (line 83); `PublicReportService` java:S1068 (line 13) | Static findings needing triage; not confirmed defects | RTM §2.5 Maintainability | – |
| F-07 | DT-05, DT-06, path B1 | M2 §3.3.3, §3.4.1 | Not executed | Coverage gap | API-05, API-07 | – |
| F-08 | npm vulnerabilities | M3 `evidence/frontend/README.md` | `npm ci` reported 3 moderate vulnerabilities | Not investigated | – | Not investigated |
| F-09 | Deprecated CI actions | M3 `evidence/ci/README.md` | Node.js 20 actions and `setup-java@v4` flagged deprecated | Warning; run still passed | – | No workflow in this repository |
| F-10 | ESLint on generated assets | M4 §3.9 | Repository-wide ESLint after generating JaCoCo HTML gave 138 errors in report assets | Tooling note, not product code | – | `npm run lint` passes on this branch (no generated assets present) |

Defect density: M4 reports 0.24 defects/KLOC, based on PC-QA-01 only (1 defect / 4.206 KLOC). F-02 is not counted because its classification is still open.

## 7. Cross-section references

| From | To | Reference |
|---|---|---|
| TP §2.1 (Performance row) | 3.7 | Basic JMeter load test belongs to assignment 3.7 → M4 §3.7 |
| TP §2.1 (Maintainability row, RTM §2.5) | 3.4, 3.9 | Maintainability assessed by white-box analysis (3.4) and SonarQube (3.9) → M2 §3.4, M4 §3.9 |
| TP §7.3, OQ-10 | 3.6 | CI workflow is "planned"; none at `f565fb4` → M3 created one in their copy only |
| TP §7.11 | 3.5, 3.6, 3.8 | Changes to the original application need the owner's approval → F-01 and F-02 fixes and M3's `pom.xml` change are unapplied |
| TP OQ-4 | 3.7 | No response-time target → M4 reports a baseline without an SLA verdict |
| TP OQ-9 | 3.3, 3.5, 3.9 | "Have the 73 tests been run?" (wording before Phase 3B; now 72) → answered in practice by M2, M3, M4 (72 tests, 0 failures) and by the Phase 3A baseline run on this branch (72 tests, 0 failures, Java 27). Part 01 updated in Phase 3B (count 72, OQ-9 answered) |
| TP §7.5 "Acceptance: selected MT cases and 1–2 BDD scenarios, Cucumber [Proposed; not installed]" | 3.5 | M3 delivered 4 scenarios with Cucumber 7.23.0 (in the M3 copy) |
| TP §7.5 test-tool proposal (REST Assured) | 3.6 | M3 used REST Assured as proposed |
| RTM C-4 / Q8 (dashboard shows 4, not 5) | – | Not addressed by any member |
| RTM Q7 (CSRF on login/logout/PUT) | 3.3 | M2 DT-04 checks CSRF on admin DELETE only; Q7 still open |

## 8. Unresolved inconsistencies

| # | Inconsistency | Where | Suggested owner |
|---|---|---|---|
| U-1 | ~~Part 01 says 73 test methods; correct value is 72~~ **Resolved 25 Nov 2026 (Phase 3B):** RTM and TP corrected to 72 in both copies; OQ-9 answered. The archived AR still says 73 by design (original record) | §4 | Closed |
| U-2 | Integrated Part 01 shows 78 of 79 cases NOT EXECUTED (API-04 recorded as PASS in Phase 3A), while M3 (15 cases) and M4 report executions. M3's status edits were made on an older Test Plan and are not applied. Their API-04 edit ("PARTIALLY EXECUTED", padded email `400`) is superseded by D-1 and must not be applied | §2; M3 Part 01 diff | M1 + M3 |
| U-3 | PC-QA-01 is "closed" in M4's lifecycle, but the fix is not in this repository, so the defect still exists here | F-01 | Project owner (approve or reject the fix) |
| U-4 | F-02 is fixed in the M3 copy, but whether `[::1]` should be accepted is open (RTM Q1) | F-02 | Project owner |
| U-5 | ~~API-04 expected result (409 for a padded duplicate) conflicts with observed 400~~ **Resolved 25 Nov 2026 (Phase 3A):** owner decision D-1 (Option A) kept the 409 expectation and changed the code to trim before validation. API-04 executed after the change: PASS. Remaining: change not committed; run on Java 27 and H2 only | F-03 | Closed (project owner decided; M1 documents updated) |
| U-6 | M3's Java 21 CI evidence is for commit `70d01e0`; later commits (Selenium tests, UI job) have local Java 25 results only | M3 `ci/README.md` | M3 |
| U-7 | M3 has no formal 3.5–3.6 report | README G-1 | M3 |
| U-8 | M2 and M4 use different JaCoCo versions (0.8.12 / 0.8.13) and operating systems. Branch coverage is identical (126 / 302); line coverage differs by one line (M2 433 / 641, M4 434 / 642). *[Corrected: Phase 5A. Previously "report identical coverage"; M4 report corrected to its own CSV]* Consistent, but from separate copies | §5 | – (note only) |
| U-9 | 521 relative links do not resolve in the new layout | INTEGRATION_LOG §7 | Separate link-correction step |
| U-10 | Part 01 Test Plan authors: "TBC" | TP header | M1 / project owner |
