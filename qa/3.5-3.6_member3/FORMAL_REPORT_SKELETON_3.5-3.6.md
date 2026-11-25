# Sections 3.5–3.6 – Formal report skeleton and evidence map

> **Status: STRUCTURE ONLY – NOT THE FORMAL REPORT.**
> Prepared during integration (Phase 5A, 2026-11-25) so that Member 3 can write the formal report. This file contains **no report narrative**. It gives the required structure, the facts that were re-verified against Member 3's evidence files, and links to that evidence. Text marked **[M3 TO WRITE]** must be written by Member 3. Items marked **[M3 TO CONFIRM]** must be confirmed or corrected by Member 3 before they are used.
> [FORMAL_REPORT_MISSING.md](FORMAL_REPORT_MISSING.md) remains true: no formal 3.5–3.6 report has been submitted.

**How facts were verified (Phase 4 and Phase 5A).** Each log's final `Tests run:` line, its BUILD result and the JVM in its Spring Boot start-up line were read from the file. The test counts are confirmed by the `@Test` annotations and feature files in `reference-code/`. Facts not found in an evidence file are not listed.

---

## 0. Front matter

| Field | Value | Status |
|---|---|---|
| Title | **[M3 TO WRITE]** | – |
| Author | Ishani Kumaranayaka (from the Git commit author `Ishani-Kumaranayaka` and `qa/README.md`) | **[M3 TO CONFIRM]** exact spelling and student ID |
| Date | **[M3 TO WRITE]** (evidence dates are 2026-11-24) | Do not use the integration date |
| Source repository | `PublicCare-QA-Member3-Ishani-`, branch `member3-tdd-bdd-ci` | Verified (`INTEGRATION_LOG.md` §1) |
| Commit reported on | `93d11a34d22fff59f62d211faeacc60dfc3a50dd` | Verified. The report must name it |
| Application baseline under test | `f565fb4` **plus** an unapplied change to `AdminSetupRunner.java` and an unapplied `pom.xml` change (test dependencies). The results pre-date owner decision D-1 | Verified (`../phase5a-p0-corrections/BASELINE_DEVIATIONS.md`) |
| Machine used | The local evidence paths show a Windows user-profile name (`evidence/baseline/environment.txt` line 6) that differs from the commit author | **[M3 TO CONFIRM]** whose machine ran the local tests, so that authorship is clear (Phase 4 P2-8) |

## 1. Environment (exact values from the evidence)

| Item | Local runs | GitHub Actions run #1 | Evidence |
|---|---|---|---|
| OS | Windows 11, version 10.0, amd64 | `ubuntu-latest` (exact image version not recorded) | [baseline/environment.txt](evidence/baseline/environment.txt); [ci.yml](reference-code/github-workflows/ci.yml) line 17 |
| JDK | Eclipse Temurin **25.0.5+8 LTS** (the project targets Java 21) | Temurin **21.0.13** (Eclipse Adoptium, Linux) | [baseline/environment.txt](evidence/baseline/environment.txt) lines 1-3; [ci/artifact-summary.txt](evidence/ci/artifact-summary.txt) line 22 |
| Maven | 3.9.16 (Maven Wrapper) | Maven Wrapper (`./mvnw -B test`) | environment.txt line 5; ci.yml line 37 |
| Spring Boot / JUnit | 3.5.16 / JUnit Platform 1.12.2 | same `pom.xml` | [bdd/README.md](evidence/bdd/README.md) line 3 |
| Cucumber-JVM | 7.23.0 (7.34.9 tried first and failed) | same | bdd/README.md lines 3, 31-32 |
| REST Assured | 5.5.7 (managed by Spring Boot) | same | [api-automation/README.md](evidence/api-automation/README.md) line 3 |
| Selenium | 4.31.0 (managed by Spring Boot); Chrome 157 headless; ChromeDriver 157.0.8103.78 | UI job **not executed** | [ui-automation/README.md](evidence/ui-automation/README.md) line 3 |
| Node.js / npm | **v24.13.0 / 11.11.0** | Node **22** (`setup-node`, `node-version: '22'`); exact patch version not recorded | [frontend/environment.txt](evidence/frontend/environment.txt); ci.yml line 62 |
| Frontend build | Vite 8.3.5, 46 modules | Job status "Success" only; log not inspected | [frontend/README.md](evidence/frontend/README.md) |
| Database | In-memory H2, `MODE=PostgreSQL`, Flyway off | H2 | bdd/README.md line 42 |
| Date of runs | 2026-11-24 | 2026-11-24T04:24:04Z (job finish) | READMEs; [ci/README.md](evidence/ci/README.md) line 15 |

## 2. Section 3.5 – Test-Driven Development

**Brief requires:** unit tests (JUnit) written test-first, showing Red → Green → Refactor.

**Verified facts**

| Step | What was done | Actual result | Evidence |
|---|---|---|---|
| Target | UT-14: `AdminSetupRunner.isLoopbackPostgres` with bracketed IPv6 `[::1]`; requirement FR-06.9 | – | [tdd/README.md](evidence/tdd/README.md) lines 1-3 |
| Red 1 | `AdminSetupRunnerLoopbackTest` (6 tests) written before any application change | Compilation error: `isLoopbackPostgres` has private access; BUILD FAILURE | [tdd/01-red-compile-error.txt](evidence/tdd/01-red-compile-error.txt) |
| Red 2 | Visibility changed only (no behaviour change) | Tests run 6, Failures 1 (`ipv6LoopbackInBracketsIsLoopback`: expected true, was false); BUILD FAILURE | [tdd/02-red-assertion-failure.txt](evidence/tdd/02-red-assertion-failure.txt) |
| Green | `host.equals("[::1]")` added | Tests run 6, Failures 0; BUILD SUCCESS | [tdd/03-green.txt](evidence/tdd/03-green.txt) |
| Refactor | Helper `isLoopbackHost(String)` extracted; behaviour unchanged | Full suite 78/0; BUILD SUCCESS; Java 25.0.5 | [tdd/04-refactor-full-suite.txt](evidence/tdd/04-refactor-full-suite.txt) |
| Code change | `AdminSetupRunner.java` in the Member 3 copy only. **Not applied** to the group repository | – | [unapplied-diffs/AdminSetupRunner-ipv6-loopback_2c1e597..93d11a3.diff](unapplied-diffs/AdminSetupRunner-ipv6-loopback_2c1e597..93d11a3.diff) |

**[M3 TO WRITE]** Why UT-14 was chosen; the test written first (quote or summarise the 6 tests); what the Red results show; the minimal Green change; what the refactor improved; why existing tests were not labelled TDD.

**Must be stated in the report:**
- Whether `[::1]` should be accepted is still an **open question** (RTM Q1, finding F-02). The Red result confirms the behaviour; it does not prove a defect.
- The code change exists only in the Member 3 copy. The project owner has not approved it (TP §7.11, owner decision D-4 pending).
- Runs used Java 25. The first Java 21 result is CI run #1 (§5).

## 3. Section 3.5 – Behaviour-Driven Development

**Brief requires:** 1–2 BDD scenarios written in Gherkin and run with Cucumber.

**Verified facts**

| # | Scenario | Feature file | Requirements |
|---|---|---|---|
| 1 | A new citizen registers, logs in and submits a report | [citizen_report.feature](reference-code/project/backend/src/test/resources/features/citizen_report.feature) lines 9-17 | FR-01.1, FR-01.5, FR-02.2, FR-03.1, FR-03.3 |
| 2 | A visitor who is not logged in cannot submit a report | same, lines 19-22 | FR-02.8 |
| 3 | Administrator moves a pending report to in progress with a note | [admin_status_update.feature](reference-code/project/backend/src/test/resources/features/admin_status_update.feature) lines 10-16 | FR-06.6, FR-05.2 |
| 4 | A citizen cannot change the status of a report | same, lines 18-22 | **FR-02.8, FR-06.6** – see note below |

**[M3 TO CONFIRM] Scenario 4 mapping.** Member 3's README and the feature comment list FR-08.5 ("Citizens cannot delete reports"). The step sends a citizen `PATCH /api/admin/reports/{id}/status` and expects `403` (`PublicCareSteps.java:222-231`). No deletion takes place. The evidence supports FR-02.8 and FR-06.6, not FR-08.5. Details: [../phase5a-p0-corrections/REQUIREMENT_MAPPING_REVIEW.md](../phase5a-p0-corrections/REQUIREMENT_MAPPING_REVIEW.md) §1.

| Run | Result | Evidence |
|---|---|---|
| Cucumber 7.34.9 | 0 tests run, `NoClassDefFoundError: DiscoveryIssueReporter`; BUILD FAILURE | [bdd/01a-fail-cucumber-7.34.9.txt](evidence/bdd/01a-fail-cucumber-7.34.9.txt) |
| Cucumber 7.23.0 dependency regression | 78/0; BUILD SUCCESS | [bdd/01b-pom-deps-regression-7.23.0.txt](evidence/bdd/01b-pom-deps-regression-7.23.0.txt) |
| Scenarios, run 1 | 4/4 failed, `expected 201 but was 403` (test-code CSRF error, not an application defect) | [bdd/02a-run1-fail-csrf-403.txt](evidence/bdd/02a-run1-fail-csrf-403.txt) |
| Scenarios, run 2 | 4/0; BUILD SUCCESS | [bdd/02b-run2.txt](evidence/bdd/02b-run2.txt) |
| Full suite | 82/0 (72 + 6 + 4); BUILD SUCCESS | [bdd/03-full-suite.txt](evidence/bdd/03-full-suite.txt) |
| Reports | HTML, JSON and XML | [bdd/cucumber-reports/](evidence/bdd/cucumber-reports/) |

**[M3 TO WRITE]** Why these scenarios were chosen; how Given/When/Then map to the steps; how the scenarios run (MockMvc against the full Spring context, CSRF token handling, synthetic data, admin inserted directly); the two failures and what was learned from them.

**Must be stated:** H2 not PostgreSQL. The `PC-` reference passes through the service's fallback format, not the database-generated value. MockMvc, so no server and no browser.

## 4. Section 3.6 – Test automation

**Brief requires:** automate tests using Selenium and/or REST Assured.

### 4.1 REST Assured API tests (`PublicCareApiTest`, 9 tests)

| Test | Part 01 case | Result (Member 3 copy, 2026-11-24) |
|---|---|---|
| csrfEndpointReturnsTokenAndCookie | API-01 | PASS |
| registrationForcesCitizenRoleAndHidesPassword | API-02 (steps 1-3) | PASS |
| passwordLengthBoundary | API-03 (11 and 12 characters only) | PASS |
| duplicateEmailWithDifferentCaseIsRejected | API-04 (case) | PASS |
| emailWithSurroundingSpacesIsRejectedByValidation | API-04 (padding) | PASS at the time, **SUPERSEDED** by owner decision D-1 |
| meRequiresSession | API-08 (steps 1-2) | PASS |
| adminEndpointAuthorisationMatrix | API-09 (`GET /api/admin/reports` only) | PASS |
| createReportUsesSessionOwner | API-10 | PASS |
| otherUsersReportLooksLikeMissingReport | API-15 (GET only) | PASS |

Source: [api-automation/README.md](evidence/api-automation/README.md) lines 20-30; [surefire-reports/](evidence/api-automation/surefire-reports/).

| Run | Result | Evidence |
|---|---|---|
| 1 | Build failure before tests (stale `target/`; cause inferred, not proven) | [01a-run1-stale-target-build-failure.txt](evidence/api-automation/01a-run1-stale-target-build-failure.txt) |
| 2 | 8 run, 1 failure: duplicate email expected `409`, got `400` | [01b-run2-fail-duplicate-email-400.txt](evidence/api-automation/01b-run2-fail-duplicate-email-400.txt) |
| 3 | 9/0; BUILD SUCCESS | [02-run3.txt](evidence/api-automation/02-run3.txt) |
| Full suite | 91/0 (72 + 6 + 4 + 9); BUILD SUCCESS; Java 25.0.5 | [03-full-suite.txt](evidence/api-automation/03-full-suite.txt) |

**The report must not present the `400` for a padded email as current or expected behaviour.** After Member 3's run, the cause was confirmed (`@Email` validated the untrimmed value). The project owner then decided (D-1, Option A) to trim the email before validation. In the group repository a padded duplicate now returns `409`, and API-04 is PASS (Java 27, H2; [../phase3a-api04-correction/](../phase3a-api04-correction/README.md)). Report the `400` as **what was observed in the Member 3 copy on 2026-11-24, later superseded**.

**[M3 TO WRITE]** Tool choice (REST Assured, as proposed in TP §7.5); real server on a random port; CSRF and session handling; which API cases are not automated (README line 32).

### 4.2 Selenium UI tests (`PublicCareUiIT`, 6 tests)

| Test | Part 01 case | Result |
|---|---|---|
| registrationBoundariesAndMismatch | UI-01 | PASS |
| loginRedirectsByRole | UI-02 | PASS |
| failedLoginShowsGenericAlert | UI-03 | PASS |
| signedOutUserCannotSubmitReport | UI-04 | PASS |
| requiredFieldsAndSuccessfulReport | UI-05 | PASS |
| adminPagesAreGatedForCitizen | UI-13 | PASS |

| Run | Result | Evidence |
|---|---|---|
| Normal suite with Selenium, run 1 | 0 tests, class files missing; BUILD FAILURE (cause not proven) | [01a-run1-missing-class-files.txt](evidence/ui-automation/01a-run1-missing-class-files.txt) |
| Normal suite, clean | 91/0 | [01b-regression-with-selenium.txt](evidence/ui-automation/01b-regression-with-selenium.txt) |
| UI run 1 | 6/0 | [02-ui-run1.txt](evidence/ui-automation/02-ui-run1.txt) |
| Fault check (deliberate) | 6 run, 2 failures, **as intended** (2 texts changed in `dist` only) | [03-fault-check-expected-failures.txt](evidence/ui-automation/03-fault-check-expected-failures.txt) |
| UI final | 6/0 | [04-ui-final.txt](evidence/ui-automation/04-ui-final.txt) |
| Normal suite, final | 91/0 | [05-full-suite.txt](evidence/ui-automation/05-full-suite.txt) |
| Screenshots | 6, one per test | [screenshots/](evidence/ui-automation/screenshots/) |

**Must be stated:** local runs only (Java 25, Chrome 157 headless). The class name ends in `IT`, so it is **not** part of the 91. Chrome only, no mobile width. The decision history is in [ui-automation-decision.md](evidence/ui-automation-decision.md).

**[M3 TO WRITE]** Why Selenium was added after the first "not implemented" decision; how the frontend is served (port 5199) and the backend run (port 8080); what the fault check demonstrates.

## 5. Section 3.6 – CI/CD (GitHub Actions)

**Brief requires:** a GitHub Actions workflow that runs on push, and screenshots of a passing pipeline.

| Fact | Value | Evidence |
|---|---|---|
| Workflow | `.github/workflows/ci.yml`. Triggers: `push`, `pull_request`. Jobs: `backend` (Temurin 21, `./mvnw -B test`), `frontend` (Node 22, `npm ci`, lint, build), `ui` (Selenium) | [reference-code/github-workflows/ci.yml](reference-code/github-workflows/ci.yml) |
| Run documented | Run #1, status Success, 1 m 6 s | [ci/README.md](evidence/ci/README.md) lines 5-17 |
| Commit of run #1 | **`70d01e0`**, not the audited `93d11a3` | ci/README.md line 10 |
| Backend job | 91 tests, 0 failures, BUILD SUCCESS; Java 21.0.13 Temurin, Linux; 15 classes | [ci/artifact-summary.txt](evidence/ci/artifact-summary.txt); screenshot `ci/github-actions-run-1-backend-log-91-tests.png` |
| Frontend job | Success (job status only; log not inspected) | ci/README.md line 49 |
| UI job | **Configured, never executed** (added after run #1) | ci/README.md line 24 |
| Screenshots | 3 | [ci/](evidence/ci/) |
| Warnings | Node.js 20 actions deprecated; `setup-java@v4` deprecated | ci/README.md lines 39-44 |
| In the group repository | The workflow is **not active**. It is a reference copy only (no `.github/` folder) | `qa/README.md`; D-5 pending |
| *[Phase 6, 2026-11-25]* Difference `70d01e0` → `93d11a3` | Outside `evidence/` and `qa/`, only: one Javadoc comment line in `PublicCareApiTest.java`; test-scoped `selenium-java` in `pom.xml`; new `PublicCareUiIT.java` (not run by `./mvnw -B test`); the `ui` job in `ci.yml`. The executable test code for the 91 tests is therefore identical at `93d11a3`, but CI was **not** run on `93d11a3`: keep "Java 21 result is for `70d01e0`" | `git diff 70d01e0 93d11a3` in the cloned repository; [Phase 6 §4.2](../phase6-member-repo-verification/README.md) |
| *[Phase 6]* Screenshot cross-check | Run 45326010526 #1, commit, job names, 91/0, artifact digest prefix and timestamps are consistent with the Git history, `ci.yml` at `70d01e0` and `artifact-summary.txt` | Phase 6 §4.2 |
| *[Phase 6]* Visibility | The repository is **private**: the run URL is not viewable by a marker without collaborator access. The screenshots are the evidence a marker can see | Phase 6 §1 |

**[M3 TO CONFIRM / DECIDE]**
1. Either re-run the workflow on the final commit (including the `ui` job) and add screenshots, or state "UI job configured, not executed" and "Java 21 result is for commit `70d01e0`" everywhere these results are cited (Phase 4 P1-10, P1-11).
2. Whether the run URL is publicly viewable by the marker. If not, the screenshots are the only evidence.

**[M3 TO WRITE]** Pipeline design (stages vs MEMBER_TASKS.md), artifact upload, how CI fits the team's workflow.

## 6. Results summary (do not add across copies)

| Run | Environment | Tests | Failures |
|---|---|---:|---:|
| Baseline | Local, Java 25.0.5 | 72 | 0 |
| After TDD | Local, Java 25.0.5 | 78 | 0 |
| After BDD | Local, Java 25.0.5 | 82 | 0 |
| After API automation / final | Local, Java 25.0.5 | 91 | 0 |
| Selenium (separate) | Local, Java 25.0.5, Chrome 157 | 6 | 0 |
| CI run #1, commit `70d01e0` | ubuntu-latest, Temurin 21.0.13 | 91 | 0 |

These counts are for the Member 3 copy only. They must not be added to Member 2 (84), Member 4 (72) or the group repository (85).

## 7. Limitations (all verified; must appear in the report)

- Local runs on Java 25, not Java 21. The only Java 21 result is CI run #1 on `70d01e0`.
- H2 only; no PostgreSQL. INT-01 not done. The generated `reference` column is not exercised.
- The TDD result depends on an application change that is not applied to the group repository (F-02 / RTM Q1 open).
- `emailWithSurroundingSpacesIsRejectedByValidation` is superseded by D-1.
- Partial coverage: 4 scenarios; 9 API tests covering parts of 8 API cases; 6 of 14 UI cases.
- No code coverage measured (no JaCoCo).
- Selenium CI job never executed. CI artifact ZIP not committed (summary only). Frontend CI log not inspected.
- Some failure causes are inferred, not proven (`api-automation/01a`, `ui-automation/01a`).
- `npm ci` reported 3 moderate vulnerabilities, not investigated.

## 8. Items requiring Member 3 input (checklist)

| # | Item | Why |
|---|---|---|
| M3-1 | Write the narrative for §2–§5 | No report text exists. Integration must not write it |
| M3-2 | Name, student ID, report date | Byline |
| M3-3 | Machine used for local runs | Evidence paths show a different user-profile name from the commit author |
| M3-4 | Accept or reject the scenario 4 mapping (FR-02.8 / FR-06.6) | FR-08.5 not supported by the steps |
| M3-5 | CI on the final commit incl. UI job, or explicit "not executed" wording | P1-10, P1-11 |
| M3-6 | Whether the CI run URL is public | Marker access (risk R-4) |
| M3-7 | Confirm the superseded test is described as historical | Must not present `400` as expected behaviour |
| M3-8 | Reflection points for the group 3.10 case study (TDD, BDD, CI) | P1-9 |

---

*[Phase 7, 2026-11-25, integration note:]* On the project owner's authorisation, the narrative marked **[M3 TO WRITE]** above was drafted by integration from the evidence in [REPORT_3.5-3.6_TDD_BDD_AUTOMATION_CICD.md](REPORT_3.5-3.6_TDD_BDD_AUTOMATION_CICD.md) (a project-integrated draft, not written or reviewed by Member 3). The **[M3 TO CONFIRM]** items that need Member 3's own facts (name spelling, student ID, report date, machine used) are still open and are shown as PENDING in the draft. One correction was found while drafting: §3 "Scenarios, run 1" says all 4 failures were `expected 201 but was 403`, but `bdd/02a-run1-fail-csrf-403.txt` lines 128–140 show that scenario 2 failed with `expected 401 but was 403` (same CSRF cause). The draft reports the log; the table above is left as written.
