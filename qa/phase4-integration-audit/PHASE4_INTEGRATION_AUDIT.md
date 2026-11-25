# PublicCare QA – Phase 4 Group Sections Integration Audit (3.1–3.10)

| Item | Value |
|---|---|
| Audit date | 2026-11-25 |
| Repository | `publiccare/` (the parent folder `Public care/` is **not** a Git repository) |
| Branch | `qa/final-assignment` (local only) |
| HEAD / baseline | `f565fb4bb91d6c5af96775aa5333d862ec2883ad` "Finalize report deletion and cleanup" |
| `main` | `f565fb4` = `origin/main`. Unchanged. No commits exist on `qa/final-assignment` beyond `main`. No stash |
| Working tree | `M backend/src/main/java/lk/publiccare/backend/auth/AuthDtos.java` (+17 lines, Phase 3A, D-1) · `?? backend/src/test/java/lk/publiccare/backend/auth/AuthDtosTest.java` · `?? backend/src/test/java/lk/publiccare/backend/auth/AuthEmailNormalizationApiTest.java` · `?? qa/` (117 files) |
| Outside Git | `Public care/qa/` – 3 Member 1 originals |
| Changes made by this audit | **None.** No file in the repository or the outer `qa/` folder was created, edited, moved or deleted. Nothing committed or pushed |
| Authority for task coverage | `D:/4TH SEMESTER/Software Testing & Quality Assurance/project/EC4206_Group_Assignment_Task_List (1).pdf` (read in this audit) |

**Method.** Every numeric claim below was recomputed from the evidence file, not taken from the integration documents: Maven/Surefire logs (totals, BUILD result, JVM version), Surefire XML, the JMeter JTL (recomputed mean/median/p95/max), SonarQube JSON (measures and the 91 listed issues), the JaCoCo CSV (summed per package), Member 2 screenshots E08/E09 (opened and read), Cucumber feature files, test source (`@Test` / `@ParameterizedTest` counts and `@CsvSource` rows), diffs (stored diff vs live `git diff`), SHA-256 of the Member 1 copies, and a link resolver run over every `.md` file under `qa/`.

**Limits.** Member 2–4 sources are GitHub repositories that are not cloned locally, so the Phase 1 "98 of 98 blob hashes identical" claim could not be re-hashed in Phase 4. External URLs (Member 3 CI run, Member 4 GitHub issue #1) were not opened. Member 2 screenshots E01–E07 were not opened (E08 and E09, which carry the final claims, were).

---

## 1. Section-by-section status

| Section | Owner | Status | One-line reason |
|---|---|---|---|
| 3.1 Introduction & Test Planning | Member 1 | **PARTIALLY COMPLETE** | Scope, objectives, ISO 25010 and V&V are complete. "Resources" (§7.6) and "Schedule" (§7.8), both named in the PDF, are placeholders (TBC) and describe 3 proposed workstreams, not the actual 4-member allocation. Authors TBC |
| 3.2 STLC & Test Cases | Member 1 | **PARTIALLY COMPLETE** | STLC mapped; 79 cases in full format (verified: 79 unique IDs, 22/14/25/17/1). Actual result recorded for 1/79 (API-04 PASS). §11 environment is mostly TBC although every value is now known from member evidence. Member 3/4 executions not reconciled into Part 01 |
| 3.3 Black-box | Member 2 | **COMPLETE** (with gaps) | EP on 3 fields, BVA on title length (7 values), decision table (6 rules). All 12 added invocations verified in code and in E08 (84/0). DT-05/DT-06 NOT EXECUTED (disclosed). BVA is on a string length, not a true numeric/date field (PDF wording) |
| 3.4 White-box | Member 2 | **COMPLETE** (with gaps) | CFGs and V(G) recomputed correct (A: E9 N8 → 3; B: E7 N7 → 2). Coverage numbers match E09 exactly. Path B1 NOT EXECUTED (disclosed) |
| 3.5 TDD / BDD | Member 3 | **PARTIALLY COMPLETE** | All evidence present and internally consistent (Red compile → Red 6/1 → Green 6/0 → Refactor 78/0; Cucumber 4 scenarios, 82/0). **No formal report** |
| 3.6 Automation & CI/CD | Member 3 | **PARTIALLY COMPLETE** | REST Assured 9/0, Selenium 6/0 (local), CI run #1 91/0 on Java 21 with 3 screenshots. **No formal report**; CI evidence is for commit `70d01e0`, not audited `93d11a3`; Selenium CI job never executed; workflow is inactive in the group repository |
| 3.7 Performance, Security, Usability | Member 4 | **COMPLETE** (with gaps) | JMeter figures reproduce exactly from the JTL; 2 OWASP access checks + 1 positive control; 3 scripted usability tasks. Screenshots only for the registration task |
| 3.8 Defect Tracking | Member 4 | **PARTIALLY COMPLETE** | One defect (PC-QA-01) with RCA and lifecycle, but lifecycle evidence is only an external issue link (no screenshot), the record lacks the Environment field required by the brief, it is "Closed" only in the Member 4 copy and **open in the submitted code**, and other findings (F-02, F-03) are not logged in a tracker |
| 3.9 Quality Metrics & SonarQube | Member 4 | **COMPLETE** (one incorrect figure) | All Sonar measures match `sonar-measures.json`; quality gate PASSED in `sonar-run.txt`; 3 improvements match `sonar-issues.json`. Backend line coverage in the report (433/641) does **not** match Member 4's own `jacoco.csv` (434/642). No Sonar dashboard screenshot |
| 3.10 Case Study & Best Practices | Member 4 | **PARTIALLY COMPLETE** | A short Agile/DevOps reflection exists (PDF minimum met), but it is written from Member 4's work only and does not draw on TDD/BDD/CI (3.5–3.6), black/white-box (3.3–3.4), planning (3.1–3.2) or the D-1 correction |

---

## 2. Evidence verified and evidence missing

### 2.1 Member 1 (3.1–3.2) and Phase 3A/3B

Verified:

- **Copies = originals.** SHA-256 of the three files in `qa/3.1-3.2_member1/` equals the outer originals: RTM `33bc16d6…193f`, Test Plan `1cc432dc…630e`, Analysis Report `01c67fab…317d`.
- **Test case inventory.** TP §10.4 has exactly 79 `####` case headings (MT 22, UI 14, API 25, UT 17, INT 1), no duplicates. Status rows: 78 × `PROPOSED / NOT EXECUTED`, 1 × `PASS` (API-04). RTM TASK 5: 61 rows `PROPOSED / NOT EXECUTED` + 2 mixed rows (FR-01.3, FR-01.4) carrying API-04 PASS = 63 rows. TASK 6: 78 + 1.
- **72 vs 73 (Phase 3B).** 72 `@Test` methods in 12 classes at `f565fb4` (via `git grep` on the commit). RTM:115, RTM:376, TP:78, TP:201, TP:590, TP:1991 all say 72 with *[Corrected: Phase 3B]*. AR:80 and AR:341 still say 73 (by design, archive).
- **Phase 3A.** Stored `AuthDtos-email-trim_f565fb4..working-tree.diff` is identical to the live `git diff`. Line references 18-20 / 26-28 / 38-40 match the working-tree file. `AuthDtosTest` 6 `@Test`, `AuthEmailNormalizationApiTest` 7 `@Test`; Surefire XML 6/0 and 7/0. Logs: 00 → 72/0 BUILD SUCCESS; 01 → 13 run, 9 failures, BUILD FAILURE (expected red); 02 → 13/0; 03 → 85/0. JVM "Java 27" in every log; Spring Boot 3.5.16 (log banner and `pom.xml`); wrapper Maven 3.9.16 (`maven-wrapper.properties`); `java.version` 21 in `pom.xml`.
- **Superseded test.** `emailWithSurroundingSpacesIsRejectedByValidation` is marked superseded in Member 3 `evidence/README.md:40`, `api-automation/README.md` (test table + addendum), `CROSS_REFERENCE.md:82,151`, `phase3a…/README.md:59`. The reference copy is unchanged (correct).

Missing:

- Author names (TP:15 "Authors: TBC"; README.md:39 says Member 1's name is not recorded).
- Actual resources, schedule dates and environment values (see §5 and Appendix G).
- A Java 21 run of the Phase 3A change (only Java 27). *[Phase 5B rev. 1, 2026-11-25: Java 21 run since executed – 85/0, H2; see Phase 5B register ENV-J6.]* A PostgreSQL run (H2 only). *[Still missing.]*
- An "Execution details" row (tester, date, baseline, environment) in any test case, although TP §10.3 defines it; API-04 does not name the tester.

### 2.2 Member 2 (3.3–3.4)

Verified:

- `reference-code/` = `f565fb4` + `unapplied-diffs/test-code-changes_962aeaf..0ad36bd.diff` exactly (only additions: +33 lines `AuthControllerTest`, +39 lines `ReportControllerTest`; no line removed).
- `registrationEquivalencePartitionTest`: 5 `@CsvSource` rows = EP-01–EP-05 (16→201, 8→400, 73→400, invalid-email→400, empty name→400). `reportTitleBoundaryTest`: 7 rows = BVA-01–BVA-07 (0,1,2,80,159,160,161). 72 + 12 = 84.
- E10 log: 72 tests, 0 failures, BUILD SUCCESS, total 13.616 s, `using Java 21.0.13.1` (4×).
- E08 screenshot: `Tests run: 84, Failures: 0, Errors: 0, Skipped: 0`, `jacoco:0.8.12:report`, 57 classes, BUILD SUCCESS, 16.449 s, finished 2026-11-24T12:04:55+05:30.
- E09 screenshot totals: missed instructions 1,690/4,226 (→ 2,536 covered, 60.01%), missed branches 176/302 (→ 126, 41.72%), missed lines 208/641 (→ 433, 67.55%), missed methods 67/237 (→ 170, 71.73%), missed classes 6/57 (→ 51, 89.47%); report package 39% branches, security 33% instructions. All match PART02:196-226 and :262.
- CFG arithmetic correct (PART02:141-144, :169-172).

Missing / weak:

- Surefire XML for the 79- and 84-test runs (screenshots only; disclosed).
- DT-05, DT-06 (PART02:111-112) and B1 (PART02:176) not executed. B1 is executable without changing production code: a password of 37 × "é" is 37 characters (passes `LoginRequest @Size(max = 72)`) and 74 bytes (> 72), so it reaches the byte-limit branch.
- BVA on a numeric/date field as the PDF words it. Candidates already defined in TP:338: page size 0/1/100/101, image size 5,242,880/5,242,881 bytes, password 11/12/72/73.
- No author byline in the report (PART02:1-7).

### 2.3 Member 3 (3.5–3.6)

Verified (every log's final `Tests run` line, BUILD result and JVM):

| Evidence | Tests / failures | Build | JVM |
|---|---|---|---|
| baseline/mvn-test-output.txt | 72/0 | SUCCESS | 25.0.5 |
| tdd/01-red-compile-error.txt | – (compile error) | FAILURE | – |
| tdd/02-red-assertion-failure.txt | 6/1 | FAILURE | – |
| tdd/03-green.txt | 6/0 | SUCCESS | – |
| tdd/04-refactor-full-suite.txt | 78/0 | SUCCESS | 25.0.5 |
| bdd/01a-fail-cucumber-7.34.9.txt | 0 run | FAILURE | – |
| bdd/01b-pom-deps-regression-7.23.0.txt | 78/0 | SUCCESS | 25.0.5 |
| bdd/02a-run1-fail-csrf-403.txt | 4/4 | FAILURE | 25.0.5 |
| bdd/02b-run2.txt | 4/0 | SUCCESS | 25.0.5 |
| bdd/03-full-suite.txt | 82/0 | SUCCESS | 25.0.5 |
| api-automation/01a-run1-stale-target-build-failure.txt | – | FAILURE | – |
| api-automation/01b-run2-fail-duplicate-email-400.txt | 8/1 | FAILURE | 25.0.5 |
| api-automation/02-run3.txt | 9/0 | SUCCESS | 25.0.5 |
| api-automation/03-full-suite.txt | 91/0 | SUCCESS | 25.0.5 |
| ui-automation/01a-run1-missing-class-files.txt | 0 run | FAILURE | – |
| ui-automation/01b-regression-with-selenium.txt | 91/0 | SUCCESS | 25.0.5 |
| ui-automation/02-ui-run1.txt | 6/0 | SUCCESS | 25.0.5 |
| ui-automation/03-fault-check-expected-failures.txt | 6/2 (intentional) | FAILURE | 25.0.5 |
| ui-automation/04-ui-final.txt | 6/0 | SUCCESS | 25.0.5 |
| ui-automation/05-full-suite.txt | 91/0 | SUCCESS | 25.0.5 |
| final/full-suite.txt | 91/0 | SUCCESS | 25.0.5 |
| ci/artifact-summary.txt | 91/0, 15 classes | – | 21.0.13 Eclipse Adoptium, Linux |

- Reference code: `AdminSetupRunnerLoopbackTest` 6, `PublicCareApiTest` 9, `PublicCareUiIT` 6 `@Test`; 2 feature files × 2 scenarios = 4. 72 + 6 + 4 + 9 = 91. `PublicCareUiIT` ends in `IT`, so Surefire's default includes skip it in `mvnw test` (explains 91, not 97); the CI UI job runs it with `-Dtest=PublicCareUiIT` (ci.yml:110).
- `ci.yml` jobs: backend (Temurin 21), frontend (Node 22), ui (Selenium). Triggers `push`, `pull_request` (meets PDF "on push").

Missing:

- **Formal report for 3.5 and 3.6** (`FORMAL_REPORT_MISSING.md:3`).
- CI result for the `ui` job (ci.yml:75-120): never executed (ci/README.md:24, ui-automation/README.md:43).
- CI evidence for the audited commit `93d11a3` (run #1 is on `70d01e0`, ci/README.md:10). Selenium tests and later commits have Java 25 results only.
- CI artifact ZIP (only a summary); frontend job log not inspected (ci/README.md:49).
- Code coverage for Member 3's tests (no JaCoCo).

### 2.4 Member 4 (3.7–3.10)

Verified:

- `health.jtl` (recomputed): 100 rows, single label `GET /api/health`, 100 × `success=true`, 100 × HTTP 200, mean 9.37 ms, median 8 ms, min 3, max 55, 95th ordered value 17 ms, threads 1–5, span 6.4 s. `jmeter-console.txt`: `summary = 100 in 00:00:07 = 14.6/s … Err: 0 (0.00%)`. Matches MEMBER4_QA_REPORT.md:12-16.
- `access-checks.txt`: `/api/health` 200 UP; `/api/admin/reports` 401; `/api/auth/me` 401. Matches :22-26.
- `sonar-measures.json`: coverage 36.0, bugs 3, vulnerabilities 0, code_smells 88, sqale_index 527, duplicated_lines_density 0.0, ncloc 4206. `sonar-issues.json`: 91 issues = 88 CODE_SMELL + 3 BUG; java:S3776 line 101, java:S1192 line 83, java:S1068 line 13 present. `sonar-run.txt`: SonarQube Server 25.3.0.104237, Java 21.0.13.1 Azul, `QUALITY GATE STATUS: PASSED`, unresolved-types warning (matches :71).
- Defect density 1 / 4.206 = 0.238 → 0.24 (correct).
- `junit-summary.txt`: 72/0 BUILD SUCCESS.
- `registration-before.txt`: `valuesMatch:true, message:"Passwords do not match.", submitted:false` (assertion failure). `registration-after.txt`: `submitted:true`, empty message. Browser 153.0.8010.0. Fix diff `f78deb1` adds a single `onChange` that clears `confirmPassword` custom validity (consistent with the RCA at :48).

Missing / incorrect:

- **Backend line coverage**: report says 433/641 (MEMBER4_QA_REPORT.md:67); Member 4's own `jacoco.csv` sums to **434/642 = 67.60%**. Branches (126/302) and instructions (1,690/4,226 missed) are identical to Member 2. The one-line difference is in `lk.publiccare.backend.report` (437 lines in Member 4's CSV vs 436 in Member 2's E09), consistent with JaCoCo 0.8.13 vs 0.8.12 line attribution.
- SonarQube dashboard / quality-gate screenshot (brief deliverable "SonarQube report/screenshots").
- Screenshot or export of GitHub issue #1 showing New → Assigned → Fixed → Verified → Closed (only an external link, :44).
- Environment field in the defect record (brief requires it; :42-50).
- Screenshots for the FR-03.8 and keyboard-login usability tasks (text only, `mobile.txt`); FR-03.8 task used a synthetic 401 fixture (stubbed session response, disclosed at :35).
- Triage of the 3 Sonar bugs: java:S6218 (report package, line 152), javascript:S2871 `AdminIssues.jsx:48` (sort without comparator), javascript:S1082 `SiteHeader.jsx:117`.
- Participant usability study, database-backed load test (disclosed).

---

## 3. Technical inconsistencies

| # | Inconsistency | Evidence |
|---|---|---|
| T-1 | **Plan says nothing has been executed; the integrated submission reports executed work in 3.3–3.9.** TP header "No test case has been executed, and it records no … defects or measured quality values" (TP:3-4), §5.3 "Verified quality result: None" (TP:237), §7.3 rows 3.3–3.10 "Planned" (TP:307-314), §7.8 rows 4–10 "Not started" (TP:415-421), §7.9 "Not started" (TP:432-435), §8 phases 4–5 "Not started" (TP:480-481). RTM:4 "None of them have been run" | Member 2–4 evidence; Phase 3A |
| T-2 | **Allocation.** TP §7.6 (TP:365-391) proposes 3 workstreams (WS-1 UI/manual, WS-2 API + JMeter/OWASP, WS-3 unit + white-box) with members TBC. Actual allocation is 4 members by assignment section (README.md:26-37). WS-2 assigns JMeter/OWASP (Member 4's 3.7) together with API cases (Member 3's 3.6) | README.md, member folders |
| T-3 | **Coverage.** CROSS_REFERENCE.md:137 records Member 4 as 433/641 and calls the two runs "identical" (:137, :188 U-8). Member 4's CSV says 434/642 | `jacoco.csv` vs E09 |
| T-4 | **Link counts.** README.md:6 and :74 (G-7), INTEGRATION_LOG.md:142-151 and CROSS_REFERENCE.md:189 (U-9) state 521 broken links. Current count is **529**: Phase 3A added 8 links with the old `../publiccare/` prefix to the RTM (e.g. RTM:50, RTM:147) | Appendix E |
| T-5 | **Test-count totals per copy are not additive.** 72 (M2 baseline), 84 (M2), 91 (M3), 6 (M3 Selenium, separate), 72 (M4), 85 (this branch). No combined run exists (README G-8). Any group-level "total tests" figure would be invented | Appendix A |
| T-6 | **Different application baselines** behind each result (Appendix B). Member 3's 91 tests ran with an **unapplied** production change (`AdminSetupRunner`) and an unapplied `pom.xml`; Member 4's "after" registration run and SonarQube scan include the unapplied fix `f78deb1`; this branch includes the uncommitted D-1 change | Appendix B |
| T-7 | **Java versions.** Project targets 21 (`pom.xml:21`). Results: M2 21.0.13.1 (Microsoft), M3 local 25.0.5 (Temurin), M3 CI 21.0.13 (Temurin), M4 21.0.13.1 (Azul Zulu), Phase 3A 27 (Oracle 27+35). The integrated application state (`f565fb4` + D-1) has never been run on Java 21 *[Phase 5B rev. 1, 2026-11-25: superseded – run on Java 21 (Ubuntu OpenJDK 21.0.13.1, WSL2): 85 tests, 0 failures, H2 only; see Phase 5B register ENV-J6]* | Logs |
| T-8 | **Node and browser versions.** M3 local Node 24.13.0 / npm 11.11.0; CI Node 22; M4 not recorded. Browsers: M3 Chrome 157 headless (Windows); M4 Chromium 153.0.8010.0 (Playwright, Linux). TP proposes 375 px mobile width (TP:1913); M4 used 390 × 844 | env files, M4 report |
| T-9 | **Database.** Every executed test used H2 (PostgreSQL mode). TP §11 and INT-01 assume PostgreSQL; no PostgreSQL evidence exists anywhere | All members |
| T-10 | **PC-QA-01 state.** "Closed" in M4 lifecycle (MEMBER4_QA_REPORT.md:49) but the fix is not in this repository, so the defect is **open** in the code being submitted (CROSS_REFERENCE U-3) | `unapplied-diffs/AccountPage-registration-fix_f78deb1.diff` |
| T-11 | **Status vocabulary.** RTM uses "EXECUTED – PASS"; TP uses "PASS" (and its §10.3 template allows only PASS/FAIL/BLOCKED) | RTM TASK 5/6-C vs TP API-04 |
| T-12 | **Two live copies of Part 01** (`Public care/qa/` and `publiccare/qa/3.1-3.2_member1/`), both edited in Phase 3A/3B (CROSS_REFERENCE.md:114 "both synchronized copies"). Currently identical; risk of drift | SHA-256 |
| T-13 | **Line anchors after Phase 3A.** Code links carry `#L…` anchors for `f565fb4`. Once rewritten to `../../backend/…`, they point to the working tree, where `AuthDtos.java` has shifted. The RTM was updated; the AR was not (AR:111 `#L13-L16`, AR:285 `#L17-L28`, AR:413 `#L16`) | AR, `AuthDtos.java` |
| T-14 | **Requirement mapping granularity.** Member 4 maps to whole groups ("FR-01", "FR-02", "FR-06"); Part 01 traces at FR-xx.y. Member 3 BDD scenario 4 (citizen cannot change status) is tagged FR-08.5 "Citizens cannot delete reports" (feature file line 6; CROSS_REFERENCE.md:81) | RTM:140, :504, :545 |

---

## 4. Incorrect or unsupported claims

| # | Claim | Location | Finding |
|---|---|---|---|
| C-1 | "Backend line coverage 67.55% – JaCoCo measured 433 covered out of 641" | MEMBER4_QA_REPORT.md:67 | **Not supported by the member's own evidence** (`jacoco.csv`: 434/642 = 67.60%) |
| C-2 | M4 line coverage "433 / 641 = 67.55% (JaCoCo 0.8.13)"; "Consistent? Yes" | CROSS_REFERENCE.md:137 | Incorrect for M4 (see C-1); branch row (:138) is correct |
| C-3 | "report identical coverage" | CROSS_REFERENCE.md:188 (U-8) | Branches identical; lines differ by one |
| C-4 | "521 relative links … do not resolve"; "All have a verified correction" | README.md:74; INTEGRATION_LOG.md:151; CROSS_REFERENCE.md:189 | Now 529; the 8 Phase 3A links have no recorded correction |
| C-5 | "No test case has been executed … no defects or measured quality values" | TP:3-4; RTM:4 | True for Part 01 as a standalone plan; false for the integrated submission. Must be scoped ("in this document") and cross-referenced |
| C-6 | "Verified quality result: None" | TP:237 | Contradicted by M4 SonarQube/JaCoCo/JMeter and M2 coverage |
| C-7 | §7.3/§7.8/§7.9/§8 "Planned" / "Not started" for 3.3–3.10 | TP:307-314, :415-421, :432-435, :480-481 | Stale |
| C-8 | "Apart from corrections 1–19, nothing was changed" | RTM:44 | Stale after D-1 and the Phase 3B correction (both documented further down; the sentence should say so) |
| C-9 | "Lifecycle … closed" | MEMBER4_QA_REPORT.md:49 | Supported only by an external link; no in-repo evidence of the issue states. True for the M4 copy only |
| C-10 | "The Sonar way quality gate passed for this baseline" | MEMBER4_QA_REPORT.md:71 | Supported (`sonar-run.txt`), but "this baseline" = M4 handover + `f78deb1`, not `f565fb4` |
| C-11 | BDD scenario 4 → FR-08.5 | `admin_status_update.feature:6`; CROSS_REFERENCE.md:81 | FR-08.5 is about deletion (RTM:545); scenario tests status change → FR-02.8 / FR-06.6 |
| C-12 | Anonymous `/api/admin/reports` → "FR-06"; `/api/auth/me` → "FR-02"; registration recovery → "FR-01" | MEMBER4_QA_REPORT.md:20, :34-36; CROSS_REFERENCE.md:91-95 | Too coarse to trace: FR-02.8 (API-09), FR-02.8 (API-08), FR-01.6 (MT-02, UI-01) |
| C-13 | "The CI run (Java 21) gave the same 91 passing tests" | M3 evidence/README.md:59 | True for commit `70d01e0` only; inferred for `93d11a3` |
| C-14 | Inferred results presented with actual ones (all disclosed by the members, but the group report must keep the labels): M2 "Actual HTTP" from passing assertions (PART02:59, :86); M2 statement coverage is a manual trace (PART02:185-192); M2 DT R5/R6 expected values are proposed (PART02:103); M3 failure causes "likely, not proven" (api/ui READMEs); M3 CI frontend result read from job status only (ci/README.md:49); M4 FR-03.8 task used a synthetic 401 fixture (MEMBER4_QA_REPORT.md:35); CROSS_REFERENCE **[integrator note]** rows | various | Keep labels; do not upgrade to "verified" |

No fabricated result was found: every PASS/FAIL count and metric in the member documents traces to an evidence file, except C-1.

---

## 5. Correction register (file and line references)

Priorities: **P0** blocks a credible submission; **P1** a marked inaccuracy or missing required evidence; **P2** quality / consistency.

### P0

| ID | File:line | Problem | Correction |
|---|---|---|---|
| P0-1 | `qa/3.5-3.6_member3/FORMAL_REPORT_MISSING.md:3`; `qa/README.md:32-33, 68` | No 3.5–3.6 report | Member 3 writes it from the existing evidence (TDD cycle with the 4 logs; 4 BDD scenarios incl. the 7.34.9 and CSRF failures; 9 REST Assured tests **with the superseded test labelled**; 6 Selenium tests incl. fault check; CI run #1 with its commit and the untested UI job; limitations: Java 25, H2, `70d01e0`). Must state the copy and baseline (Appendix B). Must not present the 400 expectation as current behaviour |
| P0-2 | TP:15; `qa/README.md:39`; PART02:1-7 | Authors unknown: Part 01 "Authors: TBC"; Member 1 name not recorded; Member 2 report has no byline | Add each member's name and student ID to their section and a contribution table to the final report |
| P0-3 | TP:3-4, :237, :307-314, :415-421, :432-435, :480-481; RTM:4 | Plan states contradict the integrated work (T-1) | Keep the planned values; add a dated "As executed (Phase 4)" column or addendum pointing to 3.3–3.10 and Phase 3A. Scope the header sentence to "in this document" |
| P0-4 | TP:365-391 (§7.6), :406-422 (§7.8) | Resources and schedule (explicit PDF items) are placeholders and show a 3-workstream model | Add the actual allocation (Member 1: 3.1–3.2; Member 2: 3.3–3.4; Member 3: 3.5–3.6; Member 4: 3.7–3.10; integration/Phase 3A by the integrator) and actual dates (analysis 23 Nov; member execution 24 Nov; integration 25 Nov 2026). Keep the original proposal as history |
| P0-5 | `qa/` (untracked), `AuthDtos.java` (modified), 2 test classes (untracked) | Nothing committed; branch is local only | After owner approval (not in this phase): commit on `qa/final-assignment`, push, confirm the remote copy |
| P0-6 | RTM (252), AR (237), TP (6), PART02 (20), MEMBER4 (14) | 529 broken relative links in submitted documents | Apply the prefix rewrites in INTEGRATION_LOG §7 plus the 8 Phase 3A RTM links; decide anchor strategy (P1-12) first; re-run the checker to 0 |

### P1

| ID | File:line | Problem | Correction |
|---|---|---|---|
| P1-1 | MEMBER4_QA_REPORT.md:67 | 433/641 not supported by `jacoco.csv` (434/642) | Member 4 corrects to 434/642 = 67.60% (JaCoCo 0.8.13) or explains; note M2's 0.8.12 gives 433/641 |
| P1-2 | CROSS_REFERENCE.md:137, :188 | Coverage row and U-8 | Record 433/641 (M2, 0.8.12) vs 434/642 (M4, 0.8.13); branches identical |
| P1-3 | README.md:6, :74; INTEGRATION_LOG.md:142-151; CROSS_REFERENCE.md:189 | 521 → 529 | Update counts and per-file table (RTM 253 checked / 252 broken) |
| P1-4 | TP:141, :399-400, :542, :1896, :1908-1913, :1950 | TBC values now known | Fill an "as executed" environment table (Appendix C): OS, JDK distribution, Maven 3.9.16, Node, browsers, host/port 127.0.0.1:8080, viewport 390 × 844, tools and versions, defect tool GitHub Issues, JMeter volume 5 × 20 |
| P1-5 | MEMBER4_QA_REPORT.md:42-50 | Defect record lacks Environment and an explicit Status; "Closed" applies only to the M4 copy | Add Environment (M4 copy at `b97bd96`, Linux, Chromium 153.0.8010.0, Vite dev server) and Status ("Closed in M4 copy; **Open** in group baseline `f565fb4` pending owner decision") |
| P1-6 | MEMBER4_QA_REPORT.md:44 | Lifecycle evidence is external only | Add screenshots/exports of GitHub issue #1 timeline and the linked commit to `evidence/` |
| P1-7 | MEMBER4_QA_REPORT.md:54-79 | No SonarQube dashboard / quality-gate screenshot | Add screenshots (overview, issues, quality gate) |
| P1-8 | MEMBER4_QA_REPORT.md:61 | 3 Sonar bugs not triaged | Triage java:S6218 (report package, line 152), javascript:S2871 (`AdminIssues.jsx:48`), javascript:S1082 (`SiteHeader.jsx:117`) as real defect / false positive; log any real one |
| P1-9 | MEMBER4_QA_REPORT.md:81-87 | Case study covers Member 4's work only | Rewrite 3.10 as a group case study and Agile/DevOps reflection drawing on all sections (shift-left planning, TDD, BDD as living acceptance criteria, CI on push, defect lifecycle, D-1 owner decision, isolation of copies) |
| P1-10 | `reference-code/github-workflows/ci.yml:75-120`; ci/README.md:24; ui-automation/README.md:43 | Selenium CI job never executed | Run the workflow in Member 3's repository and capture screenshots, or report "configured, not executed" everywhere it is mentioned |
| P1-11 | ci/README.md:10 | CI evidence on `70d01e0`, not `93d11a3` | Re-run CI on the final commit, or state the commit wherever "Java 21: 91/0" is cited |
| P1-12 | All `#L…` code links; AR:111, :285, :413 | Anchors refer to `f565fb4`; `AuthDtos.java` changed | Decide: (a) relative links to working tree and re-check every `AuthDtos.java` anchor, or (b) permalinks to `f565fb4` for archived/baseline references |
| P1-13 | PART02:111-112, :176 | DT-05, DT-06, B1 not executed | Execute in the Member 2 copy (or here, with owner approval): two MockMvc requests without CSRF (anonymous, citizen); a login with 37 × "é". Otherwise keep as documented gaps |
| P1-14 | PART02:63-88 | PDF asks BVA on numeric/date fields | Add a justification that title length is a numeric boundary, and/or add a numeric BVA (page size 0/1/100/101 from TP:338) |
| P1-15 | RTM TASK 5/6; TP §10.4; CROSS_REFERENCE.md:22-55 | 15 M3 cases and 4 M4-touched cases show NOT EXECUTED in Part 01 | Add a reconciliation table (case, member, copy/baseline, actual result, evidence, PASS/PARTIAL) in Part 01 or the final report. Do **not** apply `part01-execution-status-edits_2c1e597..93d11a3.diff` (older Test Plan; its API-04 part is superseded) |
| P1-16 | Every section of the final report | Baseline behind each result not stated | Add the baseline line from Appendix B to each section |
| P1-17 | 3.8 group text | Other findings not logged in a tracker | Log F-03 (root cause confirmed in Phase 3A, resolved by D-1) as a second RCA; log F-02 as "open – awaiting RTM Q1". PDF asks RCA on 1–2 bugs |
| P1-18 | Phase 3A; README.md:20 | Integrated state only run on Java 27 *[Phase 5B rev. 1, 2026-11-25: run on Java 21 – 85/0, H2; Surefire reports archived, console log not (register ENV-J6, ENV-J10)]* | Run `./mvnw -B clean test` on JDK 21 before submission and archive the log (expect 85/0) |
| P1-19 | INTEGRATION_LOG.md:38 | Copies of M2–M4 checked by modification time only in Phase 3B | Re-hash with `git hash-object` against `git ls-tree -r <commit>` in each member clone; record the result |

### P2

| ID | File:line | Problem | Correction |
|---|---|---|---|
| P2-1 | `admin_status_update.feature:6` (reference copy, do not edit); CROSS_REFERENCE.md:81 | FR-08.5 mapping for scenario 4 | Correct in CROSS_REFERENCE and in the 3.5 report (FR-02.8, FR-06.6) |
| P2-2 | MEMBER4_QA_REPORT.md:20, :34-36; CROSS_REFERENCE.md:91-95 | Coarse FR mapping | Use FR-02.8/API-09, FR-02.8/API-08, FR-01.6/MT-02/UI-01, FR-03.8/UI-04, NFR-USA for keyboard use |
| P2-3 | RTM TASK 5/6-C; TP API-04 | "EXECUTED – PASS" vs "PASS" | Use one vocabulary (TP §10.3) |
| P2-4 | RTM:44 | Stale sentence | "Apart from corrections 1–19, owner decision D-1 and the Phase 3B correction…" |
| P2-5 | PART02:9, :31, :116, :257, :267 | Numbering §1, §3.3, §3.4, §4, §5 (no §2) | Renumber when compiling |
| P2-6 | TP §10.3 (template) and API-04 block | "Execution details" row never used; API-04 tester not named | Add tester/date/baseline/environment row to executed cases |
| P2-7 | MEMBER4_QA_REPORT.md:12-28 | Security records lack explicit risk/severity and preconditions per brief; error % not stated | Add "Risk: High if failed; observed: none" and "Error rate 0.00%" (from console) |
| P2-8 | Evidence logs and screenshots | Absolute local paths with OS user names appear in Phase 3A logs, M2 screenshots, M3 `environment.txt`, M4 console (Linux sandbox paths). The M3 machine user name differs from the commit author | Do not edit evidence. In the 3.5–3.6 report, Member 3 should state the machine used, to pre-empt authorship questions. Consider whether the submitted logs should expose personal paths |
| P2-9 | CROSS_REFERENCE.md:114 | Two editable copies of Part 01 | Declare `publiccare/qa/3.1-3.2_member1/` canonical; retire the outer copy after final sync (do not delete without approval) |
| P2-10 | M4 report environment | Linux container details (Azul Zulu 21.0.13.1, `ActiveProcessorCount=2`, sandbox paths) not stated, although JMeter figures depend on them | State them in 3.7 |
| P2-11 | CROSS_REFERENCE F-08, F-09 | npm moderate vulnerabilities and deprecated actions not followed up | Mention as follow-ups in 3.9/3.10 |

---

## 6. Recommendations by priority (summary)

- **P0 (6):** Member 3 formal report; names and contribution table; plan-vs-actual addendum in Part 01; real resources/schedule; commit after approval; fix 529 links.
- **P1 (19):** coverage figure and cross-reference; link counts; environment TBCs; defect record fields, issue and Sonar screenshots, bug triage; group-level 3.10; CI on final commit incl. Selenium job (or explicit "not executed"); link-anchor strategy; DT-05/DT-06/B1 and numeric BVA; Part 01 execution reconciliation; per-section baselines; second RCA; Java 21 run; re-hash.
- **P2 (11):** mapping corrections, vocabulary, stale wording, numbering, execution-details row, security record fields, privacy of paths, canonical copy, environment details, follow-ups.

---

## 7. Dependency-aware execution plan

```
Stage 0  Owner decisions ───────────────┐
Stage 1  Member inputs (parallel) ──────┤
Stage 2  Optional code/test application ├─► Stage 3 Document integration ─► Stage 4 Link rewrite ─► Stage 5 Final checks ─► Stage 6 Commit/push (approval)
```

**Stage 0 – Owner decisions (block everything that follows).**

| Decision | Options | Affects |
|---|---|---|
| D-2 | Commit the D-1 change as is (after a Java 21 run) | P0-5, P1-18 |
| D-3 | Apply or reject the PC-QA-01 fix (`f78deb1` diff) | 3.8 status (P1-5), T-10 |
| D-4 | Apply or reject the `AdminSetupRunner` IPv6 fix (answers RTM Q1) | F-02, UT-14 status |
| D-5 | Bring member test code into the group repository (M2 tests; M3 tests + `pom.xml` diff + workflow) | Enables a combined run and live CI; otherwise totals stay per copy |
| D-6 | Analysis Report: exclude from submission (recommended, §8.1) or appendix | P0-6 scope, P1-12 |
| D-7 | Link anchor strategy (working tree vs `f565fb4` permalinks) | P0-6, P1-12 |
| D-8 | Canonical Part 01 copy | P2-9 |

**Stage 1 – Member inputs (parallel, independent of each other).**

- Member 3: formal report (P0-1); CI re-run on the final commit with the UI job and screenshots (P1-10, P1-11).
- Member 4: coverage correction (P1-1); defect record fields (P1-5); issue timeline and Sonar screenshots (P1-6, P1-7); bug triage (P1-8); 3.10 draft input.
- Member 2: optional DT-05/DT-06/B1 and numeric BVA (P1-13, P1-14); byline.
- All members: names, IDs, dates (P0-2, P0-4).
- Integrator: re-hash member copies (P1-19).

**Stage 2 – Code/test application (only if D-2/D-3/D-4/D-5 approve).** Apply approved diffs one at a time, each with a Red/Green log; then one combined `clean test` on JDK 21 and the Selenium run; record a single combined total. Without D-5, skip to Stage 3 and keep totals per copy.

**Stage 3 – Document integration (needs Stage 0 and Stage 1).** Part 01 addendum and fills (P0-3, P0-4, P1-4, P1-15); CROSS_REFERENCE/README/INTEGRATION_LOG corrections (P1-2, P1-3, P2-1, P2-2); group 3.8 and 3.10 text (P1-9, P1-17); baselines per section (P1-16); P2 items.

**Stage 4 – Link rewrite (after all text edits, so line numbers no longer move).** Apply prefix rewrites and the D-7 anchor strategy; run the checker until 0 broken.

**Stage 5 – Final checks.** Recount IDs and statuses; confirm no copied evidence file changed (hash manifest of all `qa/` files before and after); confirm failed and NOT EXECUTED evidence is still present (Appendix D); confirm no test totals are summed across copies.

**Stage 6 – Commit and push** on `qa/final-assignment`, only with explicit approval.

---

## 8. Final submission risks

| # | Risk | Likelihood | Impact | Mitigation |
|---|---|---|---|---|
| R-1 | 3.5–3.6 submitted without a report | High (today) | High | P0-1 |
| R-2 | Examiner reads Part 01 ("nothing executed") against 3.3–3.9 and sees a contradiction | High | High | P0-3 |
| R-3 | Work lost or invisible: everything is uncommitted on a local branch | Medium | Very high | P0-5 after approval |
| R-4 | External evidence (M3 CI run URL, M4 issue #1) unreachable for the marker (private or deleted member repositories) | Medium | Medium | Screenshots in repo (P1-6, P1-10) |
| R-5 | Test totals added across copies, or a combined total claimed | Medium | High | Keep Appendix A; no group total without a combined run |
| R-6 | Java 21 target vs runs on 25/27 *[Phase 5B rev. 1: the integrated state has since run on Java 21, 85/0 (register ENV-J6)]*; H2 only; no PostgreSQL evidence | Certain | Medium | P1-18; state limitations |
| R-7 | Superseded test or Member 3's Part 01 diff applied by mistake, reintroducing the 400 expectation | Low | High | Keep the "do not apply" notes; Stage 5 check |
| R-8 | Dead links in submitted documents | Certain (today) | Medium | P0-6 |
| R-9 | Contribution/authorship questioned (TBC authors; machine user names in evidence) | Medium | High | P0-2, P2-8 |
| R-10 | Marker spots the 433/641 vs 434/642 mismatch | Low–Medium | Medium | P1-1 |
| R-11 | PC-QA-01 reported "Closed" while present in the submitted code | Medium | Medium | P1-5, D-3 |
| R-12 | Archived Analysis Report submitted with 59 FRs / 74 cases / 73 tests and 237 dead links | Medium if included | Medium | Exclude (§8.1) |
| R-13 | CI workflow not in the submitted repository (only an inactive reference copy) | Certain | Low–Medium | D-5, or state that CI ran in the member repository |
| R-14 | BVA judged as not on a numeric/date field | Low–Medium | Low | P1-14 |

### 8.1 Archived `PROJECT_ANALYSIS_REPORT.md`: recommendation

**Exclude it from the submission; keep it in the repository as an archive.** Reasons:

1. The RTM (`RTM:10`) is "the working copy to submit"; the AR is its unedited source.
2. It carries superseded figures (59 FRs, 74 test cases, 73 test methods – AR:80, :341) that contradict Part 01.
3. All 237 relative links are broken, and fixing them means editing a file that the RTM (`RTM:11`) and CROSS_REFERENCE say is kept unchanged as the original record.
4. Its `AuthDtos.java` line anchors (AR:111, :285, :413) no longer match the working tree.

If the group wants it for provenance, include it only as a clearly titled appendix ("Archived original analysis – superseded by the RTM; links refer to `f565fb4`") and accept that its links are not maintained. TP:23 and RTM:11 should then describe it as "archived, not assessed".

---

## Appendix A – Test-run register (keep separate; do not add)

| Copy | Run | Environment | Tests | Fail | Evidence type |
|---|---|---|---:|---:|---|
| M2 | Baseline | Win 11, Microsoft JDK 21.0.13.1 | 72 | 0 | Log (E10) + screenshot |
| M2 | + BVA | same | 79 | 0 | Screenshot only (E06, not opened in Phase 4) |
| M2 | + EP | same | 84 | 0 | Screenshot only (E07, not opened in Phase 4) |
| M2 | Final + JaCoCo 0.8.12 | same | 84 | 0 | Screenshot (E08, verified) |
| M3 | Baseline | Win 11, Temurin 25.0.5 | 72 | 0 | Log |
| M3 | After TDD | same | 78 | 0 | Log |
| M3 | After BDD | same | 82 | 0 | Log |
| M3 | After API / final | same | 91 | 0 | Logs (4 runs) |
| M3 | Selenium (separate) | same, Chrome 157 | 6 | 0 | Log + 6 screenshots |
| M3 | CI run #1 (`70d01e0`) | ubuntu-latest, Temurin 21.0.13 | 91 | 0 | 3 screenshots + artifact summary |
| M4 | Backend suite | Linux container, Azul 21.0.13.1 | 72 | 0 | Text summary |
| This branch | Phase 3A baseline (`f565fb4`) | Win 11, Oracle 27 | 72 | 0 | Log |
| This branch | Phase 3A red (new tests only) | same | 13 | 9 | Log (expected red) |
| This branch | Phase 3A green (new tests only) | same | 13 | 0 | Log |
| This branch | Phase 3A full (`f565fb4` + D-1) | same | 85 | 0 | Log + 2 Surefire XML |

## Appendix B – Application baseline behind each member's results

| Member | Source / commit | Production code tested |
|---|---|---|
| 1 | Static read of `f565fb4`; API-04 executed in Phase 3A | `f565fb4` + uncommitted D-1 (`AuthDtos.java`) |
| 2 | `PublicCare-QA-Member2-Oshan-` `0ad36bd` (tests changed from `962aeaf`) | Identical to `f565fb4`; only 2 test classes extended |
| 3 | `PublicCare-QA-Member3-Ishani-` `member3-tdd-bdd-ci` `93d11a3`; CI on `70d01e0` | `f565fb4` **+ unapplied `AdminSetupRunner` change + unapplied `pom.xml` test dependencies**; results pre-date D-1 |
| 4 | `PublicCare-QA-Member4-Chathura-` `chathura` `1c8e07b` (handover `b97bd96`) | Backend, JMeter, OWASP, "before" run: `f565fb4`-equivalent. Registration "after" run and SonarQube: **+ unapplied `f78deb1`** |

## Appendix C – Environment matrix (as executed)

| | M2 | M3 local | M3 CI | M4 | Phase 3A |
|---|---|---|---|---|---|
| OS | Windows 11 | Windows 11 | ubuntu-latest | Linux x86-64 container | Windows 11 |
| JDK | Microsoft OpenJDK 21.0.13.1 | Temurin 25.0.5 | Temurin 21.0.13 | Azul Zulu 21.0.13.1 | Oracle 27 (27+35) |
| Maven | Wrapper (3.9.16 inferred from the unchanged wrapper file) | 3.9.16 | Wrapper | 3.9.16 | 3.9.16 |
| Spring Boot | 3.5.16 | 3.5.16 | 3.5.16 | 3.5.16 | 3.5.16 |
| Coverage | JaCoCo 0.8.12 | none | none | JaCoCo 0.8.13 | none |
| Node / npm | – | 24.13.0 / 11.11.0 | 22 | not recorded | – |
| Browser | – | Chrome 157 headless | UI job not run | Chromium 153.0.8010.0 | – |
| Database | H2 | H2 | H2 | H2 | H2 |
| Other tools | IntelliJ IDEA 2026.3 | Cucumber 7.23.0, REST Assured 5.5.7, Selenium 4.31.0 | GitHub Actions | JMeter 5.6.3, SonarQube 25.3.0.104237, SonarScanner 6.2.1.4610, Playwright | – |

## Appendix D – Failed, NOT EXECUTED and superseded items (all must be preserved)

**Failed-run evidence (kept, do not delete):** M3 `tdd/01`, `tdd/02`, `bdd/01a`, `bdd/02a`, `api-automation/01a`, `api-automation/01b`, `ui-automation/01a`, `ui-automation/03` (intentional fault check); Phase 3A `01-red-before-fix.txt`; M4 `registration-before.txt/.png`.

**Superseded:** M3 `emailWithSurroundingSpacesIsRejectedByValidation` (by D-1); API-04 part of `part01-execution-status-edits_2c1e597..93d11a3.diff`.

**NOT EXECUTED:**

- Part 01: 78 of 79 in the documents. Not executed by anyone: all MT-01–MT-22 (MT-02 only touched indirectly by M4), UI-06–UI-12, UI-14, API-05–API-07, API-11–API-14, API-16–API-25 (API-21 touched by M4), UT-01–UT-13, UT-15–UT-17, INT-01.
- Partly executed by M3 (in its copy): API-02, API-03, API-08, API-09, API-10, API-15.
- M2: DT-05, DT-06, path B1.
- M3: Selenium CI job; CI on `93d11a3`; INT-01; PostgreSQL.
- M4: participant usability study; database-backed load test.
- Group: Java 21 run of the integrated state *[Phase 5B rev. 1, 2026-11-25: since executed – 85/0, H2; register ENV-J6]*; combined run of all members' tests.

## Appendix E – Broken relative links by file (Phase 4 count)

| File | Relative links | Resolve | Broken | External | Broken prefix |
|---|---:|---:|---:|---:|---|
| 3.1-3.2_member1/PART01_REQUIREMENTS_AND_RTM.md | 253 | 1 | **252** | 0 | `../publiccare/` (252) |
| 3.1-3.2_member1/PROJECT_ANALYSIS_REPORT.md | 237 | 0 | **237** | 0 | `../publiccare/` (237) |
| 3.1-3.2_member1/PART01_TEST_PLAN_AND_STLC.md | 24 | 18 | **6** | 0 | `../publiccare/` (6) |
| 3.3-3.4_member2/PART02_BLACK_BOX_AND_WHITE_BOX_TESTING.md | 20 | 0 | **20** | 0 | `../evidence/` 10, `../project/` 7, `../MEMBER_TASKS.md` 1, `PART01_*.md` 2 |
| 3.7-3.10_member4/MEMBER4_QA_REPORT.md | 17 | 3 | **14** | 1 | `../evidence/member4/` (14) |
| 3.5-3.6_member3/FORMAL_REPORT_MISSING.md | 1 | 1 | 0 | 0 | – |
| README.md | 41 | 41 | 0 | 0 | – |
| INTEGRATION_LOG.md | 1 | 1 | 0 | 0 | – |
| CROSS_REFERENCE.md | 1 | 1 | 0 | 0 | – |
| phase3a-api04-correction/README.md | 6 | 6 | 0 | 0 | – |
| Member 3 evidence READMEs | 0 | – | – | – | paths written as code text |
| **Total** | **601** | **72** | **529** | **1** | |

No broken in-document anchors were found. The outer originals in `Public care/qa/` resolve 514 of 514 links from their own location, which confirms the breakage comes only from the move.

## Appendix F – Hash and copy verification

| Check | Result |
|---|---|
| Member 1: 3 copies vs outer originals (SHA-256) | **Identical** (all 3) |
| Phase 3A stored diff vs live `git diff` | **Identical** (CRLF-normalised) |
| Member 2 reference code vs `f565fb4` + stored diff | **Consistent** (additions only: +33, +39) |
| Member 2–4 copies vs GitHub source blobs | **Not re-verifiable offline**; Phase 1 recorded 98/98; Phase 3B used modification times only (P1-19) |
| Repository code vs `f565fb4` | Only `AuthDtos.java` modified; 2 test classes added; nothing else |

## Appendix G – Remaining TBC fields

**Authors / people:** TP:15 (Authors); TP:375-377 (workstream members); TP:384-385 (coordination, defect/metrics duty holders); TP:391 (project owner); README.md:39 (Member 1 name).

**Schedule:** TP:413-421 (start/end dates for phases 2–10); TP:422 (submission deadline).

**Tools and environment – now resolvable from evidence:** TP:141 (SonarQube availability → used, 25.3); TP:399 (tools → see Appendix C); TP:400, :1911 (OS → Windows 11, Linux); TP:542 (defect tool → GitHub Issues); TP:1896 (execution baseline → Appendix B); TP:1908 (Node/npm); TP:1909 (JDK/Maven); TP:1910 (host/port → 127.0.0.1:8080); TP:1912 (browsers → Chrome 157, Chromium 153; others not tested); TP:1913 (width → 390 × 844 used, 375 px proposed); TP:1950 (JMeter volume → 5 threads × 20).

**Still genuinely open:** TP:137, :924 (assistive technology for MT-16); TP:138, :278, :939, :1924 (browser/device matrix for MT-17, OQ-3); TP:1588 (concurrency tool); TP:1899 (PostgreSQL version – never used); TP:1901-1902, :1904, :1907 (test values for API base URL, CORS origin, secure-cookie flag, upload path); TP:1914 (deployment environment); TP:1921-1923 (proposed configurations); TP:306, :348, :687 (references to the above).

## Appendix H – Official task list coverage (PDF)

| PDF item | Covered by | Verdict |
|---|---|---|
| 3.1 Scope | TP §1–§2 | Met |
| 3.1 Test plan (objectives, scope, in/out, **resources, schedule**) | TP §7 | Partly (resources and schedule TBC) |
| 3.1 Quality model (ISO 25010) | TP §4–§5 | Met (no verified results recorded, see T-1) |
| 3.1 Verification vs validation | TP §6 | Met |
| 3.2 STLC mapping | TP §8 | Met (statuses stale) |
| 3.2 Test cases with actual result and status | TP §10.4 | Format met; actual results 1/79 |
| 3.2 Environment (browser, OS, test data) | TP §11–§12 | Partly (mostly TBC) |
| 3.3 EP on 2–3 fields/features | PART02 §3.3.1 | Met (name, email, password) |
| 3.3 BVA on numeric/date fields | PART02 §3.3.2 | Met on a length; numeric field arguable (P1-14) |
| 3.3 Decision table | PART02 §3.3.3 | Met (4/6 rules executed) |
| 3.4 Cyclomatic complexity | PART02 §3.4.1 | Met |
| 3.4 Statement and branch coverage test cases | PART02 §3.4.2–3.4.3 | Met (B1 not executed) |
| 3.5 Unit tests (JUnit) | M3 TDD evidence | Evidence met; **report missing** |
| 3.5 1–2 BDD scenarios with Cucumber | M3 BDD evidence (4 scenarios) | Evidence met; **report missing** |
| 3.6 Automate tests (Selenium / REST Assured) | M3 api/ui evidence | Evidence met; **report missing** |
| 3.6 GitHub Actions on push | `ci.yml` (member repo) | Met in member repo; inactive in group repo |
| 3.6 Screenshots of passing pipeline | `ci/*.png` | Met (run #1, `70d01e0`) |
| 3.7 JMeter load test | M4 §3.7 | Met |
| 3.7 1–2 OWASP checks | M4 §3.7 | Met (2 + positive control) |
| 3.8 Log bugs in a tracker | GitHub issue #1 | Partly (1 bug; external evidence only) |
| 3.8 RCA on 1–2 bugs | PC-QA-01 | Met (1); F-03 could be a second |
| 3.8 Full lifecycle for ≥1 bug | PC-QA-01 | Met in M4 copy; needs in-repo evidence |
| 3.9 Run SonarQube | `sonar-run.txt` | Met |
| 3.9 Coverage %, defect density, smells, debt | M4 §3.9 | Met (line coverage figure wrong, P1-1) |
| 3.9 2–3 improvements | M4 §3.9 | Met (3) |
| 3.10 Agile/DevOps reflection | M4 §3.10 | Minimal; should be group-level |
