# Sections 3.7–3.10: Performance, Security and Usability Testing; Defect Tracking; Quality Metrics; Case Study

> **Status: PROJECT-INTEGRATED REVISION of Member 4's report.**
> Source: [MEMBER4_QA_REPORT.md](MEMBER4_QA_REPORT.md) (Member 4, 24 November 2026; provenance verified in Phase 6). That file is kept as Member 4's original record and was **not** changed in Phase 7.
> This revision was prepared on 2026-11-25 (Phase 7) on the project owner's authorisation, without asking Member 4 again. It corrects and completes the text **from the existing evidence only**. Every change is listed in §0.2. **Member 4 did not review or approve these changes.** No screenshot was added, and no confirmation is claimed from Member 4.
> Section 3.10 is new: a **group-level** case study and Agile/DevOps reflection, drawn from the records of Sections 3.1–3.9. It describes what the evidence shows. It does not report personal experiences.

## 0. Front matter and baseline

### 0.1 Identification

| Field | Value |
|---|---|
| Sections | 3.7 Performance, security and usability; 3.8 Defect tracking and root cause; 3.9 Quality metrics and SonarQube; 3.10 Case study and best practices |
| Member 4 byline (original) | Member 4 · S. A. Chathura Madushan · 24 November 2026. Preferred name form and registration number: ~~PENDING~~ *[Phase 9, 25 Nov 2026: supplied by the project owner – **Madushan S.C, EG/2023/5716** (register P-M4-12, Revision 7). This form is used for the group submission. The original byline above is kept as Member 4 wrote it; its initials ("S. A.") differ from the owner-supplied form ("S.C"), and nothing is changed in Member 4's original report]* |
| Source repository | `madushankachamara891-pixel/PublicCare-QA-Member4-Chathura-` (**private**), branch `chathura`, commit `1c8e07b`. The handover state is `b97bd96`. The fix commit is `f78deb1` |
| Application under test | Backend suite, JMeter, OWASP checks and the "before" registration run: the Member 4 copy at `b97bd96`, whose code is equivalent to the group baseline `f565fb4`. The registration "after" run and the SonarQube scan: the same copy **plus fix `f78deb1`** (`src/AccountPage.jsx`), which is **not** in the group repository |
| Environment (from the evidence) | Linux 6.18.57 amd64; Java 21.0.13.1 (Azul Systems, Inc.); Maven Wrapper 3.9.16; Spring Boot 3.5.16; H2 in PostgreSQL mode; JMeter 5.6.3; JaCoCo 0.8.13; SonarQube Server 25.3.0.104237; SonarScanner 6.2.1.4610; Chromium 153.0.8010.0 driven by Playwright. The scanner log shows `JAVA_TOOL_OPTIONS: -XX:ActiveProcessorCount=2` ([sonar-run.txt](evidence/sonar-run.txt) lines 1, 5–6, 11; [REPRODUCE.md](member4/REPRODUCE.md) line 3). **Not recorded:** Node/npm version, Playwright version, and whether the processor limit also applied to the JMeter run (P-M4-10) |
| Date of the runs | 2026-11-24 (JMeter 20:41:59–20:42:06 IST; scanner 20:46 IST) |

### 0.2 Changes made in this revision (Phase 7)

| # | Where | Change | Basis |
|---|---|---|---|
| R-1 | Evidence links | `../evidence/member4/…` → `evidence/…` (the integrated layout) | INTEGRATION_LOG §7 |
| R-2 | 3.7 performance | Error rate (0.00%), the workload ceiling set by the timer, and the environment added. "Linux x86-64" made exact | `jmeter-console.txt` line 11; `health.jmx`; `sonar-run.txt` |
| R-3 | 3.7 security | Each check given an OWASP category, a requirement ID, a precondition and a risk rating | P-M4-9, P-M4-7 (RMR §2 rows 1–2) |
| R-4 | 3.7 usability | Requirement IDs refined (FR-01.6, FR-03.8, NFR-USA). The registration PNGs are described as form-state captures | RMR §2 rows 3–5; Phase 6 F6-3 |
| R-5 | 3.8 PC-QA-01 | Environment field and an explicit two-part status added. The lifecycle is marked as unverifiable by an outside reader | P-M4-2, P-M4-3, P-M4-4; Phase 4 P1-5 |
| R-6 | 3.8 | Second root-cause analysis added (F-03, e-mail padding, group repository). F-02 recorded as an open question | Phase 4 P1-17; `phase3a-api04-correction/` |
| R-7 | 3.9 | Line coverage kept at 434 / 642 = 67.60% (Phase 5A correction). Instruction, method and per-package figures added | `jacoco.csv` re-summed in Phase 7 |
| R-8 | 3.9 | Explanation of why SonarQube shows 36.0% while JaCoCo shows 67.60% | SonarQube coverage formula; `jacoco.csv`; `sonar-measures.json` |
| R-9 | 3.9 | Triage of all 3 SonarQube bugs against the source code, and a breakdown of the 91 issues | P-M4-6; `sonar-issues.json` |
| R-10 | 3.9 | Scan scope corrected: test code was also analysed (3 issues in test classes). "This baseline" made explicit | `sonar-project.properties` line 4; P-M4-8 |
| R-11 | 3.10 | Replaced by a group-level case study and Agile/DevOps reflection. Member 4's original points are kept in it | P-M4-11; Phase 4 P1-9 |
| R-12 *[Phase 11]* | 3.8 | Added §3.8.2A (defect PC-QA-02, found by Part 01 case API-24 in the group repository) and a note on F-02 (confirmed in the group repository by UT-14). Not Member 4's work | [phase11-part01-exec](../phase11-part01-exec/README.md) |

---

## 3.7 Performance, security and usability testing

### 3.7.1 Performance (Apache JMeter)

**Objective.** Measure the response behaviour of the HTTP stack under a light, periodic read load. No response-time target exists in Part 01 (open question OQ-4), so the result is a **baseline**, not a pass/fail verdict.

**Test design** ([health.jmx](member4/health.jmx)). This is a closed workload with think time:

| Parameter | Value |
|---|---|
| Endpoint | `GET http://127.0.0.1:8080/api/health` (loopback; client and server in the same environment) |
| Users (threads) | 5, ramp-up 2 s |
| Iterations | 20 per user (maximum 100 samples) |
| Pacing | Constant timer 250 ms after each request |
| Safety cap | Scheduler duration 10 s |
| Assertion | Response body contains the health payload. The HTTP status is recorded separately in the JTL |
| Server | Spring Boot 3.5.16 on Java 21.0.13.1, H2 in-memory (`MODE=PostgreSQL`) |

**Results** (recomputed from all 100 rows of [health.jtl](evidence/health.jtl) in Phase 4, and again in Phase 7):

| Samples | HTTP 200 / assertions passed | Error rate | Mean | Median | 95th percentile | Min | Max | Throughput |
|---:|---:|---:|---:|---:|---:|---:|---:|---:|
| 100 | 100 / 100 | **0.00%** | 9.37 ms | 8 ms | 17 ms | 3 ms | 55 ms | 14.6 req/s (JMeter final summary) |

The 95th percentile is the 95th value in ascending order. The JMeter console ([jmeter-console.txt](evidence/jmeter-console.txt) lines 9–11) confirms `Err: 0 (0.00%)`. It also shows that the first interval (2 samples, mean 34 ms, maximum 55 ms) was slower than the steady interval (98 samples, mean 8 ms). The maximum therefore reflects warm-up, not steady-state behaviour.

**Interpretation.**

- **The throughput figure measures the workload model, not the server's capacity.** With 5 users and a 250 ms pause after each request, the plan cannot exceed about 5 / (0.25 s + ≈0.009 s) ≈ 19 requests/s. The observed 14.6 req/s overall, and 16.0 req/s in the steady interval, are close to that ceiling. The server was never saturated, so this test gives **no capacity or scalability result**.
- `/api/health` returns a constant status map and does no database work. The result describes the HTTP and security stack only. Database-backed listings, network latency, concurrent writes and saturation were **not measured**.
- Client and server shared one environment, so client overhead is included in the times and competes for the same CPUs.

**Recommended next experiment.** Agree a latency target with the group (OQ-4). Then load `GET /api/public/reports` with realistic page sizes against PostgreSQL (not H2), increase the number of users step by step until throughput stops rising, and run it on a stable runner so that results can be compared between builds.

### 3.7.2 Security (manual OWASP checks)

**Category.** OWASP Top 10:2021 **A01 – Broken Access Control**. **Method.** A fresh local service with no cookies. One ordinary anonymous `GET` per path, with `curl` ([REPRODUCE.md](member4/REPRODUCE.md) lines 29–32). The health request is a **positive control**: it shows that the service was reachable, so the two `401` results are real refusals and not connection failures.

| ID | Check | Requirement / Part 01 case | Precondition | Expected | Observed ([access-checks.txt](evidence/access-checks.txt)) | Result | Risk if it failed |
|---|---|---|---|---|---|---|---|
| SEC-0 | Public `GET /api/health` (control) | FR-09.1 | Service started | `200`, `UP` | `{"status":"UP"} HTTP 200` | PASS | – (control) |
| SEC-1 | Anonymous `GET /api/admin/reports` | FR-02.8 (endpoint FR-06.2); NFR-SEC; API-09, anonymous row only | No session cookie | `401`, no report data | `{"error":"Authentication required."} HTTP 401` | PASS | **High** – it would expose every citizen report to the public |
| SEC-2 | Anonymous `GET /api/auth/me` | FR-02.5 (FR-02.8 secondary); API-08 step 1 only | No session cookie | `401`, no account data | `{"error":"Authentication required."} HTTP 401` | PASS | **High** – it would leak account data without authentication |

**Observed severity: none** (no defect found). The `401` (and not `403`) for anonymous access to an admin route is correct. FR-02.8 says unauthenticated requests receive `401`, and only an authenticated non-admin receives `403`.

**Scope.** These checks cover one identity (anonymous) and two of the protected routes. The citizen → admin (`403`) path and CSRF enforcement were exercised by other members' automated tests in their own copies: Member 3's `adminEndpointAuthorisationMatrix` (REST Assured) and BDD scenario 4. Those tests are **not** part of this check. Keep both negative checks as regression tests whenever route permissions change.

### 3.7.3 Usability (scripted task walkthroughs)

**Method.** Scripted task walkthroughs in Chromium 153.0.8010.0, driven by Playwright ([registration.cjs](member4/registration.cjs), [usability.cjs](member4/usability.cjs)). **No participants took part.** No satisfaction scores, task-completion times or think-aloud data were collected. This is an expert/scripted inspection of task flows, not a usability study with users.

| Task | Requirement | Viewport | Result | Evidence |
|---|---|---|---|---|
| Recover from a registration typo: submit two different passwords, then correct only the first field | FR-01.6 (related to MT-02 and UI-01, but **not** an execution of either: they test the mismatch block, while this task tests the recovery after it) | 1100 × 900 | **FAIL** before the fix (defect PC-QA-01, §3.8). PASS after `f78deb1` (Member 4 copy only) | [registration-before.txt](evidence/registration-before.txt), [registration-after.txt](evidence/registration-after.txt) |
| Find the next action while signed out | FR-03.8 (related to UI-04) | 390 × 844 | PASS: submit disabled, sign-in link next to it. **The session response was a synthetic `401` fixture** (`usability.cjs` line 8), so this was not an end-to-end signed-out session | [mobile.txt](evidence/mobile.txt) line 1 |
| Enter login details with the keyboard; Show/Hide password | NFR-USA (keyboard operation); width fit relates to NFR-COMP | 390 × 844 | PASS: Tab moves from email to password; the toggle works in both directions; no horizontal scrolling at 390 CSS px | mobile.txt line 2 |

**About the screenshots.** [registration-before.png](evidence/registration-before.png) and [registration-after.png](evidence/registration-after.png) show the registration form with synthetic data. **They do not show the defect.** The browser's validation tooltip is not captured by `page.screenshot` (Phase 6 F6-3). The evidence for the defect and the fix is the two `.txt` outputs.

---

## 3.8 Defect tracking and root-cause analysis

### 3.8.1 Defect PC-QA-01 – registration cannot be resubmitted after a corrected password mismatch

| Field | Value |
|---|---|
| ID / tracker | PC-QA-01; [GitHub issue #1](https://github.com/madushankachamara891-pixel/PublicCare-QA-Member4-Chathura-/issues/1) in the Member 4 repository. **The repository is private**, so the issue page cannot be opened by a reader without access. Its timeline could not be exported during integration (Phase 6 F6-4) |
| Requirement | FR-01.6 (client-side password confirmation) |
| Severity / priority | Medium / P2. The citizen can recover by editing the confirmation field again, but the page does not explain this |
| Environment | Member 4 copy at `b97bd96`; Linux 6.18.57 amd64; Chromium 153.0.8010.0 (Playwright); frontend on the Vite dev server (`npm run dev -- --host 127.0.0.1`); backend on `127.0.0.1:8080` with H2. *[Added in Phase 7 from `registration-before.txt`, `REPRODUCE.md` and `sonar-run.txt`; not confirmed by Member 4 (P-M4-2)]* |
| Preconditions | On `/register`; all required fields valid; terms accepted |
| Steps | 1. Enter two different passwords of valid length and submit. 2. Change **only the first** password so that it matches the confirmation. 3. Submit again |
| Expected | The second submission is accepted |
| Actual | "Passwords do not match." stays on the confirmation field, and the submit event is suppressed: `{"valuesMatch":true,"message":"Passwords do not match.","submitted":false}` and the assertion `false !== true` |
| Root cause | The submit handler sets a custom validity message on the **confirmation** input. Only an edit of *that* field cleared the message. After an edit of the *first* field, the stale custom validity stayed set. The browser's native constraint validation then blocked the next submit **before** React's handler could compare the two values again |
| Fix | `f78deb1`: one `onChange` that clears the confirmation field's custom validity when either password changes ([unapplied-diffs/AccountPage-registration-fix_f78deb1.diff](unapplied-diffs/AccountPage-registration-fix_f78deb1.diff), regenerated identically from the source in Phase 6) |
| Verification | The same scripted assertion was re-run after the fix: `{"valuesMatch":true,"message":"","submitted":true}`. Both runs first checked that a real mismatch is still blocked. The script stops at the submit event, so no account was created |
| Lifecycle (Member 4's record) | New (#1) → Assigned (`Chathura124`) → Fixed (`f78deb1`) → Verified (same assertion) → Closed. **Not independently verified:** only the fix commit was confirmed (Phase 6). The issue states and their dates were not visible to the integration (P-M4-4) |
| **Status** | **Closed in the Member 4 copy. OPEN in the group baseline `f565fb4`**: the fix has not been applied there, pending owner decision D-3 |

### 3.8.2 Defect F-03 – a padded e-mail is rejected before it can be normalised (group repository)

The task list asks for root-cause analysis on one or two bugs. This second record comes from the group repository and has the most complete evidence of any finding in the project.

| Field | Value |
|---|---|
| ID / tracker | F-03 in [CROSS_REFERENCE.md](../CROSS_REFERENCE.md) (findings table). **Not logged in GitHub Issues** |
| Requirement / case | FR-01.3 (e-mail trimmed and lower-cased); API-04 |
| Severity / priority | Low / P3. *[Integration assessment, Phase 7]* API clients that send surrounding spaces cannot register or log in. Browsers usually strip spaces from `type="email"` fields, so the web UI is probably unaffected, but this was **not verified** (MT-04 not executed) |
| Detected | 2026-11-24, by Member 3's REST Assured run 2 in the Member 3 copy: expected `409`, got `400` ([01b-run2-fail-duplicate-email-400.txt](../3.5-3.6_member3/evidence/api-automation/01b-run2-fail-duplicate-email-400.txt)) |
| Environment of the analysis and fix | Group repository, `f565fb4` + working tree; Windows 11, Oracle Java 27; H2; MockMvc on the full application. Re-verified on Ubuntu OpenJDK 21.0.13.1 (WSL2) |
| Root cause | `RegisterRequest.email` and `LoginRequest.email` carry `@Email @Size(max = 254)`. Trimming happened only later, in `AuthService.register` and `AuthController.login`. Bean Validation therefore saw the untrimmed value and rejected it: `400 {"error":"email must be a well-formed email address"}` ([phase3a README](../phase3a-api04-correction/README.md), "Cause") |
| Classification | A **requirements defect** as much as a code defect. API-04 expected `409` "with different case **or padding**", while the code only normalised after validation. The owner had to decide which behaviour was intended |
| Decision | Owner decision **D-1, Option A**: trim the e-mail at the API boundary before validation, for registration and login. Passwords are not trimmed |
| Fix | `AuthDtos.java` compact constructors (working tree, **uncommitted**; [diff](../phase3a-api04-correction/AuthDtos-email-trim_f565fb4..working-tree.diff)) |
| Verification | Test-first: 13 new tests red (13 run, 9 failures) → green (13/0) → full suite 85/0 on Java 27. Then 85/0 on Java 21 ([phase3a-api04-correction/](../phase3a-api04-correction/README.md); [Java 21 Surefire reports](../evidence/java21-run/surefire-reports/)) |
| **Status** | **Resolved in the working tree. Not committed** (owner decision D-2 pending). Member 3's characterisation test that expected `400` is now **historical** |

### 3.8.2A Defect PC-QA-02 – a concurrent second image upload returns `500` instead of `409` (group repository) *[added in integration Phase 11, 25 Nov 2026; not Member 4's work]*

| Field | Value |
|---|---|
| ID / tracker | PC-QA-02, recorded in the [Phase 11 execution record](../phase11-part01-exec/README.md#pc-qa-02--a-concurrent-second-image-upload-returns-500-instead-of-409-new-defect). **Not yet logged in GitHub Issues** (owner action) |
| Requirement / case | NFR-REL; Part 01 case API-24 (its expected result was already marked BLOCKED pending RTM Q1, because the analysis suspected this behaviour) |
| Detected | 25 Nov 2026, Phase 11 run 2; group repository code, Windows 11, Oracle Java 27, H2, real embedded Tomcat |
| Observed | 5 rounds of two simultaneous uploads to one report: 4 rounds `204` + `500`, 1 round `204` + `409`. One image stored per report |
| Root cause | Check-then-act race: `ReportImageService.upload` checks `existsByReportId` before `saveAndFlush`; the losing insert violates `uk_report_images_report` (V3) and the resulting `DataIntegrityViolationException` has no handler. `AuthService.register` already handles the same race for e-mails |
| Severity / priority (proposed) | Medium / Low – no data corruption; the client gets an unexplained server error |
| Lifecycle | **New** only. Not assigned, not fixed (QA does not change the application), not verified, not closed |

### 3.8.3 Other findings (not defects)

| ID | Finding | Status |
|---|---|---|
| F-02 | `AdminSetupRunner.isLoopbackPostgres` rejects the IPv6 loopback `[::1]` (confirmed by Member 3's TDD Red run) | **Open question, not a confirmed defect.** The intended behaviour is RTM Q1; the fix is in the Member 3 copy only (D-4 pending). *[Phase 11: also observed in the group repository – UT-14 returns `false` for `[::1]` (OBS-1)]* |
| F-06 | SonarQube rule findings | Triaged in §3.9.4 |

### 3.8.4 Root-cause pattern

Both defects share one pattern: **a validation step ran on stale or un-normalised input.** In PC-QA-01, browser constraint validation used a stale custom-validity flag. In F-03, Bean Validation saw the e-mail before normalisation. Neither was found by the 72 existing backend tests, because those tests did not exercise the boundary where the problem occurs: the browser for PC-QA-01, and padded input through the full validation chain for F-03. This is evidence for testing at the layer where the user's input actually enters the system.

---

## 3.9 Quality metrics and SonarQube

### 3.9.1 Test execution and build checks (Member 4 copy)

The backend suite ran **72 tests, 0 failures, errors or skips; BUILD SUCCESS** ([junit-summary.txt](evidence/junit-summary.txt)). The Vite production build and a source-only ESLint check passed. A repository-wide ESLint run, made after the JaCoCo HTML had been generated, also linted the generated report JavaScript and returned 138 errors in those report assets. That is why the source-only check is the one that measures product code.

### 3.9.2 Measured results

All figures are re-verified in Phase 7 against [sonar-measures.json](evidence/sonar-measures.json), [sonar-issues.json](evidence/sonar-issues.json) and [jacoco.csv](evidence/jacoco.csv) (57 classes).

| Metric | Value | Source |
|---|---:|---|
| SonarQube coverage (overall) | 36.0% | `coverage` |
| Bugs | 3 | `bugs` |
| Vulnerabilities | 0 | `vulnerabilities` |
| Code smells | 88 | `code_smells` |
| Technical debt | 527 min (≈ 8.8 h) | `sqale_index` |
| Duplicated lines | 0.0% | `duplicated_lines_density` |
| Lines of code analysed | 4,206 (ncloc; sources only) | `ncloc` |
| Quality gate ("Sonar way") | **PASSED** | `sonar-run.txt` |
| Backend line coverage (JaCoCo 0.8.13) | **434 / 642 = 67.60%** *[Corrected in Phase 5A from 433 / 641; see the note on the original report]* | `jacoco.csv` |
| Backend branch coverage | 126 / 302 = 41.72% | `jacoco.csv` |
| Backend instruction coverage | 2,536 / 4,226 = 60.01% | `jacoco.csv` |
| Backend method coverage | 170 / 237 = 71.73% | `jacoco.csv` |
| Defect density (observed) | 1 confirmed defect / 4.206 KLOC = **0.24 per KLOC** | PC-QA-01 only, as Member 4 defined it |

**Per-package branch coverage (JaCoCo)** shows where the risk is concentrated:

| Package | Lines | Branches |
|---|---:|---:|
| `report` | 282 / 437 (64.5%) | **97 / 246 (39.4%)** |
| `admin` | 30 / 62 (48.4%) | 23 / 48 (47.9%) |
| `auth` | 40 / 48 (83.3%) | 5 / 6 |
| `config`, `health`, `user`, `exception`, `security`, root | 82 / 95 | 1 / 2 |

The `report` package holds 246 of the 302 branches (81%) and has the lowest branch coverage. It is also where most of the backend SonarQube findings are (`ReportImageService`, `ReportService`, `ReportProgressService`).

### 3.9.3 Interpreting the metrics

- **Why SonarQube reports 36.0% while JaCoCo reports 67.60% for lines.** SonarQube's coverage combines lines and branch outcomes: (covered conditions + covered lines) / (conditions to cover + lines to cover). JaCoCo's 302 branches are imported as the conditions to cover. The figure also includes the **frontend**, for which no coverage report was imported. With the backend JaCoCo totals alone, the formula gives (126 + 434) / (302 + 642) = 560 / 944 ≈ **59.3%** *[integration calculation, not a SonarQube output]*. The reported 36.0% is much lower, which fits frontend lines being counted as lines to cover with no coverage data. The exact frontend line count is not in the evidence. **Neither 36.0% nor 67.60% is a figure for the whole system.** The frontend has no automated coverage at all.
- **Line coverage and branch coverage tell different stories.** About two-thirds of the backend lines run in tests, but fewer than half of the decision outcomes do. Branch coverage is the better guide to untested behaviour, especially in `report`.
- **A passing quality gate does not mean defect-free.** The default "Sonar way" gate applies its conditions to *new code*. Whether that server held earlier analyses of the project is not recorded. If this was the first analysis, the existing findings could not fail the gate. The gate passed while 3 bugs and 88 smells were reported.
- **The defect density is a discovery measure, not a quality measure.** It counts what one tester confirmed in a short exercise, divided by all analysed lines (frontend and backend). *[Integration note, Phase 7:]* Counting the group-repository defect F-03 as well (§3.8.2) gives 2 / 4.206 = 0.48 per KLOC. But F-03 was not found in the scanned copy, so the two figures measure different things and should not be compared over time.
- **Tool versions matter.** Member 2 (JaCoCo 0.8.12) measured 433 / 641 lines, and Member 4 (0.8.13) measured 434 / 642, with identical branches (126 / 302). The one-line difference is in `report`, which is consistent with a change in line attribution between versions. Comparable trends need a fixed tool version.

**Scan scope and baseline** *[Corrected in Phase 7]*. `sonar.sources` is `project/src` (frontend) and `project/backend/src/main/java`. **`sonar.tests` is `project/backend/src/test/java`**, so the test classes were analysed with test rules: 3 of the 91 issues are in test code (`java:S5778` in `AuthServiceTest` and `ReportImageServiceTest`). The 4,206 ncloc count sources only. Migrations and infrastructure were outside the scan. The scanner reported unresolved Java types and no Git blame data in the isolated checkout. **"This baseline"** means the Member 4 copy, handover `b97bd96` + fix `f78deb1`. It differs from the group baseline `f565fb4` only in `src/AccountPage.jsx` (P-M4-8).

### 3.9.4 Triage of SonarQube findings

**Breakdown of the 91 issues:** 3 bugs + 88 code smells. Severity: 11 critical, 71 major, 9 minor. Language: 63 JavaScript/JSX, 24 Java, 4 CSS. The most frequent rules are `javascript:S6819` "prefer a native element to an ARIA role" (19), `javascript:S6774` missing React props validation (17), `javascript:S3358` nested ternary (15) and `java:S1192` duplicated string literal (6, all critical).

**The three bugs.** Each was checked against the source in the group repository. The three files are identical to the scanned copy. *[Integration triage, Phase 7: static reading only; no test was run for this triage; not confirmed by Member 4 (P-M4-6)]*

| Rule (severity) | Location | Finding | Triage | Action |
|---|---|---|---|---|
| `java:S6218` (major) | `ReportImageService.java:152`, `record ImageContent(byte[] bytes, String contentType)` | A record with an array component inherits `equals`/`hashCode`/`toString` that compare the array **reference**, not its content | **Not a defect in current use.** `ImageContent` is only created and read through its accessors (`ReportController.java:52`, `ReportImageService.java:52–65`, `ReportProgressService.java:52–81`). No code compares or hashes it. It is a latent trap for future code or tests that compare two instances | Low priority: document the limitation, or override the three methods with `Arrays.equals` / `Arrays.hashCode` |
| `javascript:S2871` (critical) | `AdminIssues.jsx:48`: `[...new Set([...fixed categories, ...Object.keys(summary.categories)])].sort()` | `sort()` without a comparator orders by UTF-16 code units | **Real, low impact.** Categories are free text (`@NotBlank @Size(max = 80)`, `ReportDtos.java:13`). A category in lower case or with accents would be listed out of alphabetical order in the admin filter. It does not affect correctness or security. The "critical" severity is the rule's default, not an impact assessment. **Not reproduced at runtime** | `sort((a, b) => a.localeCompare(b))`; add a unit test once a frontend test framework exists |
| `javascript:S1082` (minor) | `SiteHeader.jsx:117`: `onClick` on the `role="menu"` container | A click handler on a non-interactive element without a keyboard listener | **False positive in effect.** The handler is event delegation: it closes the menu when a `menuitem` link is activated. Activating a link with the keyboard (Enter) also fires a `click` that bubbles to the container. The wrapper already handles arrow, Home and End keys (`handleAccountMenuKeyDown`, line 68). The related `javascript:S6852` on the same line ("menu role must be focusable") is worth a manual accessibility check (part of MT-16, **not executed**) | Move the handler onto the menu items, or mark as "won't fix" with this reason |

**One code smell that looks like a bug.** `java:S2681` at `ReportProgressService.java:38`: `catch (RuntimeException e) { if (key != null) storage.delete(key); throw e; }`. The rethrow is meant to run unconditionally, so the logic is correct. The single-line form makes it easy to misread. Braces, or one statement per line, remove the risk.

### 3.9.5 Improvements grounded in the findings

1. **`ReportProgressService.create` and `ReportImageService` (line 101).** These have cognitive complexity 20 and 17 against a limit of 15 (`java:S3776`), and they sit in the package with the lowest branch coverage. Split them into named validation steps, and add tests for each rejected-input branch **before** refactoring (the TDD discipline of §3.5).
2. **Duplicated messages in `ReportImageService` (line 83, `java:S1192`).** Centralise the repeated validation text, so that all rejected uploads get consistent feedback, and assert the messages in tests.
3. **Unused `PUBLIC_UPDATES_PER_REPORT` in `PublicReportService` (line 13, `java:S1068`).** Check it against the intended public-feed limit. Either enforce the limit with a test, or remove the field.
4. **Frontend.** Address the accessibility rules (`S6819`, `S6852`, `S6848`) as part of the keyboard and screen-reader walkthrough MT-16, and add a frontend test runner so that frontend coverage can be measured at all.

---

## 3.10 Case study and best practices (group level)

### 3.10.1 Case

PublicCare is a citizen issue-reporting platform: a React/Vite frontend, a Spring Boot backend and PostgreSQL with Flyway. Citizens register, submit reports with photos and follow their progress. Administrators review reports, change their status, publish approved updates and delete reports. A defect can therefore expose citizens' data (access control), lose a report (validation, persistence) or stop a citizen from taking part (usability). The group's question was how far a four-person team could raise justified confidence in this system in a short QA cycle, and what the evidence shows about the process itself.

The work ran in three stages (register C-07):

| Date | Stage | Main outputs |
|---|---|---|
| 23 Nov 2026 | Analysis and planning (3.1–3.2) | 58 functional requirements in 9 groups plus 5 non-functional groups (63 RTM rows) traced to 79 test cases; ISO/IEC 25010 model; STLC; open questions Q1–Q3 |
| 24 Nov 2026 | Execution in four isolated copies (3.3–3.9) | EP, BVA, decision table, CFG and coverage (Member 2); TDD, BDD, REST Assured, Selenium, CI (Member 3); JMeter, OWASP, usability, defect lifecycle, SonarQube (Member 4) |
| 25 Nov 2026 | Integration | Owner decision D-1 and its fix (API-04); Java 21 run 85/0; audit, provenance re-hash, this report |

### 3.10.2 What the evidence shows

1. **Shift-left analysis paid off, but did not find everything.** Static analysis in Part 01, before any test ran, flagged the IPv6 loopback check as suspect (Analysis Report §2.6). Member 3's TDD Red run later confirmed it (F-02). The API-04 ambiguity (F-03) was **not** foreseen: it was not among the RTM open questions, and only an executed REST Assured test exposed it. Early review and execution complement each other. Neither replaces the other.
2. **Each test layer found something the others could not.** The 72 existing backend tests passed in every copy. They still missed PC-QA-01, which lives in browser constraint validation, and F-03, which needs padded input to pass through the full validation chain. Only a browser walkthrough and a full-stack API test found them. This is the practical argument for a **test pyramid** with several layers, rather than a high count of tests at one layer.
3. **Executable acceptance criteria resolved an ambiguity.** API-04 said "different case or padding → `409`", but the code returned `400` for padding. Making the case executable forced a decision. The project owner, acting as product owner, chose Option A (D-1), and the fix was developed test-first: 13 tests, 9 red → 13 green → 85/0. This is BDD's central idea in practice: a shared, executable example removes the misunderstanding.
4. **Negative tests protect the most important rules.** The access-control checks (anonymous `401`, citizen `403`), BDD scenarios 2 and 4, and Member 3's Selenium fault check all test what must *not* happen. The fault check is a small mutation test: it showed that the UI assertions fail when the page is wrong, which is the only reason their passes can be trusted.
5. **Working in isolation was fast but blocked integration.** Each member worked in a separate repository. This allowed parallel work with no conflicts, but it produced four different application baselines (Phase 4 Appendix B), three code changes still waiting for owner decisions (committing the D-1 fix is D-2; the PC-QA-01 fix is D-3; the IPv6 fix is D-4), test totals that cannot be added together (72, 84, 91, 72, 85), and **no combined run**. In DevOps terms, the team ran a long-lived branch for each member and paid the integration cost at the end.
6. **CI existed, but not where it mattered.** Member 3's GitHub Actions workflow is well designed: it runs on push and pull request, has parallel jobs and uploads artifacts on failure. But it ran once (run #1, on `70d01e0`), its Selenium job never ran, and it is **not active in the group repository**. Continuous integration gives value only when it runs on every change to the shared codebase.
7. **Environment parity was a recurring gap.** The project targets Java 21, but the runs used Java 21, 25 and 27. The integrated state was brought onto Java 21 (85/0) only at the end. Every automated test used H2 in PostgreSQL mode, so the database-generated `reference` column and Flyway migrations (INT-01) were not exercised by any member. The PostgreSQL execution status is in the [Section 3.2 reconciliation](../phase7-final-completion/SECTION_3.2_EXECUTION_RECONCILIATION.md). *[Phase 9, 25 Nov 2026: group-level PostgreSQL status. INT-01 was later run by the project owner on a dedicated local PostgreSQL 18.6 database (Phase 8A): **PARTIALLY EXECUTED** – (a) fresh database PASS, (b) restart PASS, (c) existing database baselined at V2 BLOCKED / NOT EXECUTED (RTM Q2), (d) schema ownership PASS with the effective run-time `ddl-auto` NOT VERIFIED. INT-01 exercised start-up, migrations and health only; no API call wrote or read data, so the database-generated `reference` value and JPA persistence on PostgreSQL remain NOT VERIFIED, and every automated test suite still ran on H2 only. See the [INT-01 record](../phase7-final-completion/int01-postgresql/README.md)]*
8. **Metrics need interpretation.** SonarQube's 36.0%, JaCoCo's 67.60% lines and 41.72% branches, and a passing quality gate alongside 3 bugs are all correct, but none of them alone answers "is it good enough?". The lowest branch coverage (39.4%) and most complexity findings are in the same package (`report`), and that combination is what should drive the next tests.
9. **Traceability and evidence discipline made the work auditable.** Requirement IDs on tests, kept failed runs, raw outputs (JTL, CSV, JSON, Surefire XML) and hashed provenance meant that every figure in this report could be recomputed. They also exposed errors that were then corrected: for example, a coverage figure off by one line, a mis-mapped BDD scenario, and a failed-run status (`401` versus `403`) reported less precisely than the log.

### 3.10.3 Best practices recommended for the next iteration

| # | Practice | Concrete step for PublicCare | Evidence that motivates it |
|---|---|---|---|
| 1 | Integrate continuously into one repository | Bring the member tests into `publiccare/` through small pull requests (D-5), excluding the superseded `400` test | Point 5 |
| 2 | Run CI on every pull request in the shared repository | Activate the workflow at `.github/workflows/`, update the deprecated actions (`setup-java@v5`), and make the backend and frontend jobs required checks | Point 6; F-09 |
| 3 | Keep slow tests separate but scheduled | Keep `*IT` Selenium tests out of `mvnw test`, but run the `ui` job on pull requests to `main` or nightly | Member 3 design |
| 4 | Test on the production database engine | Run INT-01 and a repository test on PostgreSQL (dedicated local database or Testcontainers, with owner approval) *[Phase 9: INT-01 partly done – (a), (b), (d) PASS, (c) BLOCKED (RTM Q2); a repository test on PostgreSQL is still open]* | Point 7 |
| 5 | Pin the toolchain | Java 21 in CI and locally; fixed JaCoCo and SonarQube versions | Points 7, 8 |
| 6 | Turn every defect into a regression test | Add PC-QA-01's browser assertion to the FR-01.6 acceptance criteria, and keep F-03's 13 tests | §3.8 |
| 7 | Decide open questions early | Ask the owner to answer RTM Q1 (IPv6), Q2 and Q3 at sprint planning, not after testing | F-02, F-03 |
| 8 | Use a quality gate that blocks risk | Add conditions on new-code branch coverage and on new bugs, and review existing critical findings in `report` | §3.9 |
| 9 | Agree non-functional targets before measuring | Set a latency target (OQ-4) before the next load test | §3.7.1 |

### 3.10.4 Limits of this reflection

This reflection was written during integration from the project records. It does not report any member's personal experience or opinion, and no retrospective meeting is recorded. Member 4's original 3.10 points (layers of the same product, retaining JTL/JaCoCo/scanner results with each build, a PostgreSQL-backed follow-up load test, an agreed latency target) are included above (points 2, 7, 8 and practices 4, 5, 9).
