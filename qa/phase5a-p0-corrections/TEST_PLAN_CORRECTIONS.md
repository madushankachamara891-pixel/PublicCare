# Test Plan corrections (Part 01) – prepared, not applied

Phase 5A, 2026-11-25. Prepared during integration. **Nothing in this file has been applied to Part 01 yet.**

- Line numbers refer to `3.1-3.2_member1/PART01_TEST_PLAN_AND_STLC.md` (TP) and `PART01_REQUIREMENTS_AND_RTM.md` (RTM) **as they are at the end of Phase 5A**. Phase 5A did not edit either file, so the Phase 4 audit's line numbers are still valid.
- **Rule for applying:** keep every original planned value and add the as-executed value beside it, marked *[As executed: Phase 5A]*. Do not delete the original proposal. Phase 4 P0-3 and P0-4 ask for this.
- Part 01 exists in **two synchronised copies** (`publiccare/qa/3.1-3.2_member1/` and the outer `Public care/qa/`). Before applying anything, the owner must choose the canonical copy (D-8). Otherwise the corrections have to be made in both copies and their hashes compared afterwards. *[Phase 5B rev. 2, 25 Nov 2026: D-8 decided – APPROVED, option CAN-1. Apply in the canonical copy `publiccare/qa/3.1-3.2_member1/` only; the outer `Public care/qa/` copy is a non-canonical reference copy and is not edited. Still **not applied**: each correction needs separate approval and the **CONFIRM** values below. Line-by-line list: [CONFIRMATION_REGISTER.md](../phase5b-confirmation-register/CONFIRMATION_REGISTER.md) §5.1. Line numbers in this file are unchanged for the TP; in the RTM, lines after 50 moved down by one (D-8 row added at line 51).]* *[Phase 5B rev. 3, 25 Nov 2026: §1–§4 **applied** to the canonical copy (UB-4 – UB-12, with UB-16 and UB-17 in §11.1); values marked **CONFIRM** were entered as PENDING, not filled in. §5 (Authors) not applied. The TP line numbers in this file are those **before** rev. 3; the new locations and every deviation are in [CONFIRMATION_REGISTER.md](../phase5b-confirmation-register/CONFIRMATION_REGISTER.md) §5.2.]*
- Values marked **CONFIRM** must not be applied until the named person confirms them.

## 1. Work allocation (TP §7.6, lines 365-391)

**Current:** three proposed workstreams (WS-1 manual and UI; WS-2 API, INT-01, JMeter and OWASP; WS-3 unit and white-box), with every member "TBC". This plan was not followed. The work was divided by assignment section among four members.

**Proposed addition (below the existing tables, which are relabelled "Original proposal, 23 Nov 2026 – not adopted"):**

| Member | Name (from project records) | Assignment sections | Part 01 cases actually exercised (own copy) | Evidence folder | Confirmation |
|---|---|---|---|---|---|
| Member 1 | **Not recorded** | 3.1–3.2: requirement analysis, RTM, test plan, STLC, 79 test cases | None executed by Member 1 (API-04 was executed during integration) | `qa/3.1-3.2_member1/` | **CONFIRM** name and student ID |
| Member 2 | Oshan Ravishka | 3.3–3.4: EP, BVA, decision table, CFG, cyclomatic complexity, coverage | None as written. Own IDs EP/BVA/DT/A/B | `qa/3.3-3.4_member2/` | **CONFIRM** spelling and student ID |
| Member 3 | Ishani Kumaranayaka | 3.5–3.6: TDD, BDD, REST Assured, Selenium, GitHub Actions | 8 full and 7 partial, in the M3 copy (addendum §C.1) | `qa/3.5-3.6_member3/` | **CONFIRM** spelling and student ID |
| Member 4 | S. A. Chathura Madushan | 3.7–3.10: JMeter, OWASP checks, usability walkthrough, defect tracking and RCA, SonarQube and metrics, case study | Related checks only (addendum §C.2) | `qa/3.7-3.10_member4/` | **CONFIRM** student ID; check which name form to use (see author file) |

**Cross-cutting duties (TP lines 380-385), as executed:**

| Duty | Held by | Basis | Confirmation |
|---|---|---|---|
| Defect tracking and quality metrics | Member 4 | Sections 3.8 and 3.9 are in Member 4's report | Verified from the documents |
| Test coordination (plan, schedule, RTM updates, summary report) | **Not recorded** | Integration Phases 1–5A were carried out on the integration branch, but the documents do not name the person who did them | **CONFIRM** |
| Project owner / approvals (TP line 391) | Owner of the `madushankachamara891-pixel/PublicCare` repository. The person's name is not recorded in the QA documents | Owner decision D-1 recorded in RTM and `phase3a-api04-correction/README.md` | **CONFIRM** name |

**How the original workstreams map to the actual work** (an explanation to add, not a correction of the proposal):

| Proposed workstream | What actually happened |
|---|---|
| WS-1 Manual and automated UI (MT-01–MT-22, UI-01–UI-14) | No MT case executed. UI-01–UI-05 and UI-13 automated by Member 3. UI-06–UI-12 and UI-14 not executed |
| WS-2 API and integration + JMeter/OWASP | API cases partly automated by Member 3. JMeter and OWASP checks by Member 4. INT-01 not executed |
| WS-3 Unit and white-box | White-box by Member 2. UT-14 by Member 3 (TDD). Other UT cases not executed |

## 2. Resources – tools and machines (TP lines 394-400; also line 141 and line 542)

**Current TP line 399:** every tool "Not confirmed as installed or available. Selection and availability TBC".

**Proposed as-executed table (add below line 400):**

| Tool | Used? | Version (as recorded) | Used by | Evidence |
|---|---|---|---|---|
| JUnit 5, Mockito, Spring Boot test slices, H2 | Yes | Spring Boot 3.5.16; JUnit Platform 1.12.2 | All | `backend/pom.xml`; M3 `bdd/README.md` |
| JaCoCo | Yes | 0.8.12 (M2); 0.8.13 (M4) | M2, M4 | PART02 §1; M4 `REPRODUCE.md` |
| Cucumber-JVM | Yes | 7.23.0 | M3 | M3 `bdd/README.md` |
| REST Assured | Yes | 5.5.7 | M3 | M3 `api-automation/README.md` |
| Selenium | Yes | 4.31.0; ChromeDriver 157.0.8103.78 | M3 | M3 `ui-automation/README.md` |
| Playwright | Yes (browser scripts) | **Version not recorded** | M4 | M4 `REPRODUCE.md` line 38 |
| GitHub Actions | Yes, in the M3 repository only (not active in the group repository) | `actions/checkout@v4`, `setup-java@v4`, `setup-node@v4`, `upload-artifact@v4` | M3 | `reference-code/github-workflows/ci.yml` |
| JMeter | Yes | 5.6.3 | M4 | M4 `REPRODUCE.md` line 3 |
| SonarQube / SonarScanner | Yes | Community Build / Server 25.3.0.104237; SonarScanner CLI 6.2.1.4610 | M4 | `sonar-run.txt`; `REPRODUCE.md` line 3 |
| GitHub Issues (defect tracker) | Yes | – (issue #1 in the M4 repository) | M4 | `MEMBER4_QA_REPORT.md:44` |
| IntelliJ IDEA | Yes | 2026.3 | M2 | PART02 line 14 (member statement) |
| Postman/Newman, Vitest, Testcontainers, Jenkins, Jira, Bugzilla | **Not used** | – | – | No evidence of use |

**Line-level replacements:**

| TP line | Current | Proposed |
|---|---|---|
| 141 | "SonarQube availability is TBC" | Keep; add *[As executed: SonarQube 25.3.0.104237 used by Member 4 (3.9), M4 copy + `f78deb1`]* |
| 400 | "Test machines, operating systems, browsers and devices – TBC (§11)" | Keep; add *[As executed: see §11.1 as-executed column]* |
| 542 | "Defect reports (Jira, Bugzilla or GitHub Issues – TBC)" | Keep; add *[As executed: GitHub Issues in the Member 4 repository (PC-QA-01, issue #1)]* |

## 3. Schedule (TP §7.8, lines 406-422) and execution status

**Current:** no dates; "Planned start/end" all TBC; rows 4–10 "Not started".

**Dates the records support** (only these may be used):

| Date | Activity | Source |
|---|---|---|
| 23 Nov 2026 | Part 01 draft for group review; revised the same day | TP line 15 (document status) |
| 24 Nov 2026 | Member 2 execution (E10 log finished 2026-11-24T11:10:53+05:30) | PART02 line 5; `10_Baseline_Maven_Log.txt` |
| 24 Nov 2026 | Member 3 local runs and CI run #1 (job finished 2026-11-24T04:24:04Z) | M3 READMEs; `ci/README.md` line 15 |
| 24 Nov 2026 | Member 4 runs and report | `MEMBER4_QA_REPORT.md` line 3; M4 commit date (INTEGRATION_LOG §1) |
| 25 Nov 2026 | Integration Phases 1, 3A (API-04 executed 07:23–07:26 +05:30), 3B, 4 and 5A | INTEGRATION_LOG; `phase3a-api04-correction/` logs |

**No planned start or end dates are recorded anywhere.** The Planned start/end columns must stay TBC, or be filled by the group from its own records. They must **not** be back-filled from the actual dates above.

**Proposed as-executed columns for §7.8** (add "Actual date(s)" and "Actual status (25 Nov 2026)" and keep the existing columns):

| # | Phase | Actual date(s) | Actual status (25 Nov 2026) |
|---|---|---|---|
| 1 | Requirement analysis and RTM | 23 Nov 2026 | Done |
| 2 | Test planning | 23 Nov 2026; integration revisions 25 Nov 2026 | Draft; group review pending |
| 3 | Test case design | 23 Nov 2026 | 79 cases written; peer review (V-3) pending |
| 4 | Black-box and white-box | 24 Nov 2026 (M2 copy) | Done in M2 copy; DT-05, DT-06, B1 not executed |
| 5 | Unit tests and BDD | 24 Nov 2026 (M3 copy) | Done in M3 copy (TDD on UT-14; 4 Cucumber scenarios) |
| 6 | Test execution and defect logging | API-04: 25 Nov 2026. PC-QA-01 logged 24 Nov 2026 (M4 copy) | Part 01: 1 of 79 executed (API-04 PASS). Member runs reported separately |
| 7 | Automation and CI | 24 Nov 2026 (M3 repository) | Done in M3 repository; Selenium CI job not executed; CI not active in group repository |
| 8 | Performance and security | 24 Nov 2026 (M4 copy) | Baseline measured, no pass/fail target (OQ-4); 2 OWASP access checks passed |
| 9 | SonarQube and metrics | 24 Nov 2026 (M4 copy + `f78deb1`) | Done; quality gate passed |
| 10 | Test closure and reflection | 24 Nov 2026 (M4 §3.10) | In progress: 3.10 covers Member 4's work only; no group summary report |
| – | Integration | 25 Nov 2026 | In progress (Phase 5A); nothing committed |
| – | Final submission deadline | – | **TBC – not in the project records. Take it from the module brief or LMS** |

**Other execution-status statements:**

| Location | Current | Proposed (keep the original, add the scope) |
|---|---|---|
| TP lines 3-4 (header) | "No test case has been executed, and it records no actual results … measured quality values" | Add: "*[As executed: Phase 5A]* This refers to the Part 01 test cases in this document. Sections 3.3–3.10 report executed work from member copies; see the As Executed addendum." |
| TP line 237 (§5.3 "Verified quality result: None") | as is | Add: "No quality attribute is verified by Part 01 cases. Measured values from member copies (coverage, SonarQube, JMeter) are reported in 3.4, 3.7 and 3.9 and are not Part 01 results." |
| TP lines 307-314 (§7.3 rows 3.3–3.10 "Planned") | "Planned" | Add an "As executed" column with the statuses from the table above |
| TP lines 432-435 (§7.9 deliverables "Not started") | "Not started" | Defect reports: "1 defect (PC-QA-01), M4"; Automation/CI: "M3 repository"; JMeter/OWASP/SonarQube: "M4"; Summary report: "Not started" (unchanged) |
| TP lines 480-481 (§8 phases 4–5) | "Not started" | Phase 4: "Part 01: API-04 only. Member sections executed in member copies." Phase 5: unchanged ("Not started") |
| RTM line 4 | "None of them have been run…" | Same scoping sentence as TP header |
| Status vocabulary (Phase 4 T-11 / P2-3) | RTM "EXECUTED – PASS" vs TP "PASS" | Recommend TP §10.3 vocabulary (`PASS`) in both. Not applied (P2) |

## 4. Environment (TP §11.1, lines 1890-1915; ENV rows 1921-1924; TD-09 line 1950)

Only values that appear in member evidence are proposed. "Not recorded" means no evidence file states the value.

| TP line | Item | Current | Proposed as-executed value | Source |
|---|---|---|---|---|
| 1896 | Execution baseline | "TBC" | Per member, see `BASELINE_DEVIATIONS.md`. Part 01 API-04: `f565fb4` + uncommitted D-1 | BASELINE_DEVIATIONS.md |
| 1899 | PostgreSQL version | "TBC" | **Not used in any execution.** All runs used H2 (`MODE=PostgreSQL`). Version stays TBC | All member READMEs |
| 1908 | Node.js / npm | "TBC" | M3 local: Node v24.13.0, npm 11.11.0. M3 CI: Node 22 (exact patch not recorded). M4: **not recorded** | M3 `frontend/environment.txt`; `ci.yml` |
| 1909 | JDK / Maven | "Distribution and Maven version TBC" | M2: Microsoft OpenJDK 21.0.13.1 (version confirmed in E10 log; vendor from the member report). M3 local: Eclipse Temurin 25.0.5. M3 CI: Temurin 21.0.13. M4: Azul Zulu 21.0.13.1. Phase 3A: Oracle Java 27 (27+35). Maven Wrapper 3.9.16 (`maven-wrapper.properties`) | Logs; `sonar-run.txt`; `artifact-summary.txt` |
| 1910 | Backend host / port | "TBC" | `127.0.0.1:8080` (M4 JMeter and access checks); port 8080 (M3 Selenium); random port (M3 REST Assured); MockMvc, no port (M3 BDD, M2, Phase 3A) | `health.jmx` line 13; `REPRODUCE.md` line 31; M3 `ui-automation/README.md` line 13 |
| 1911 | OS | "TBC" | Windows 11 (M2, M3 local); Linux 6.18.57 amd64 container (M4); `ubuntu-latest` (M3 CI); Phase 3A: the integration machine. The Phase 3A logs do not print the OS; Phase 4 recorded it as Windows 11 | PART02 line 13; M3 `environment.txt`; `sonar-run.txt` |
| 1912 | Browsers | "None tested" | Chrome 157 headless (M3, Windows); Chromium 153.0.8010.0 via Playwright (M4, Linux). Firefox, Edge, Safari: **not tested** | M3 `ui-automation/README.md`; M4 `registration-before.txt` |
| 1913 | Devices / widths | "375 px proposed. None tested" | 390 × 844 and 1100 × 900 CSS px, emulated (M4). 375 px: **not tested**. No physical devices | `MEMBER4_QA_REPORT.md:34-35` |
| 1914 | Deployment environment | "TBC" | **None used.** All runs were local, in a container or in CI. Stays TBC | – |
| 1924 (ENV-05) | CI regression | "not set up" | Set up and run once in the M3 repository (run #1, commit `70d01e0`). Not set up in the group repository | M3 `ci/README.md` |
| 1950 (TD-09) | JMeter volume | "TBC after targets are set" | 5 threads × 20 iterations = 100 requests to `/api/health` (no target set) | `health.jtl`; `jmeter-console.txt` |

**Still genuinely open (do not fill):** TP lines 137 and 924 (assistive technology for MT-16); 138, 278, 939 and 1924 (browser/device matrix, OQ-3); 1588 (concurrency tool); 1901, 1902, 1904 and 1907 (test values for API base URL, CORS origin, secure-cookie flag, upload path); 1921 and 1923 (proposed ENV configurations); 306, 348 and 687 (references to these).

## 5. Author field (TP line 15)

"Authors: TBC" – see [AUTHOR_AND_DATE_CONFIRMATIONS.md](AUTHOR_AND_DATE_CONFIRMATIONS.md). Not applied: Member 1's name is not in any project record.
