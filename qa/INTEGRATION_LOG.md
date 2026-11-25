# QA Integration Log – Phase 1

| Item | Value |
|---|---|
| Phase | 1: create the integration branch and collect QA deliverables |
| Date | 2026-11-25 |
| Repository | `madushankachamara891-pixel/PublicCare` (local clone in `publiccare/`) |
| Branch | `qa/final-assignment`, created from `main` |
| Starting commit | `f565fb4bb91d6c5af96775aa5333d862ec2883ad` ("Finalize report deletion and cleanup"); working tree clean before branching |
| Commits made in this phase | None. Nothing pushed. |
| Status | **Integration draft, not final.** Waiting for the project owner's review. |

> **Later phases (25 Nov 2026, all uncommitted).** This log describes Phase 1. Statements below that no file outside `qa/` was changed, that no copied document was edited, and that no test was run on this branch are true **for Phase 1 only**:
> - **Phase 3A** (owner decision D-1): changed `backend/src/main/java/lk/publiccare/backend/auth/AuthDtos.java`; added `AuthEmailNormalizationApiTest.java` and `AuthDtosTest.java`; ran the backend suite on this branch (72/0 before, 85/0 after; Java 27, H2); edited the Part 01 RTM and Test Plan and added integration addenda to Member 3's `evidence/README.md` and `api-automation/README.md`. See [phase3a-api04-correction/](phase3a-api04-correction/README.md).
> - **Phase 3B**: documents only. Corrected the existing test-method count from 73 to 72 in the Part 01 RTM and Test Plan, aligned their execution statements with Phase 3A, and marked Member 3's `emailWithSurroundingSpacesIsRejectedByValidation` as superseded (integration notes in Member 3's two READMEs). Member 2–4 evidence files, reference code, Surefire reports and diffs were not changed.
> - **Phase 4**: read-only audit; no file changed. Report preserved in Phase 5A as [phase4-integration-audit/PHASE4_INTEGRATION_AUDIT.md](phase4-integration-audit/PHASE4_INTEGRATION_AUDIT.md) (byte-identical copy, SHA-256 recorded).
> - **Phase 5A**: documents only; no application code, test code or evidence file changed. Corrected Member 4 line coverage (434 / 642) in `MEMBER4_QA_REPORT.md` and `CROSS_REFERENCE.md`, and the BDD scenario 4 mapping in `CROSS_REFERENCE.md`. Added an integration note to Member 3's `FORMAL_REPORT_MISSING.md`. New files only in `phase4-integration-audit/`, `phase5a-p0-corrections/` and `3.5-3.6_member3/FORMAL_REPORT_SKELETON_3.5-3.6.md`. See [phase5a-p0-corrections/README.md](phase5a-p0-corrections/README.md).
> - **Phase 5B**: documents only. Confirmation register added ([phase5b-confirmation-register/CONFIRMATION_REGISTER.md](phase5b-confirmation-register/CONFIRMATION_REGISTER.md)); revision 1 recorded the integrator's Java 21 run (85/0, H2). **Revision 2 – owner decision D-8 APPROVED, option CAN-1:** the canonical Part 01 location is `publiccare/qa/3.1-3.2_member1/` (this repository). D-8 row added to the RTM "Owner decisions" table; no other Part 01 text changed. The Member 1 source folder `Public care/qa/` (§1, §2 below) is now a **non-canonical reference copy**, left unchanged (not deleted, not overwritten, no longer synchronised). Corrections unblocked by D-8 are listed in register §5.1 and not applied. **Revision 3 – UB-1 to UB-17 applied** after a separate owner authorisation, to the canonical Part 01 Test Plan and RTM only (register §5.2). Names, IDs and planned dates stay PENDING / TBC; ENV-J10 stays pending. Analysis Report, outer copy, application code, tests, build files and evidence not changed; UB-18 and UB-19 not applied; no test run; nothing committed.
> - **Phase 6** (25 Nov 2026, documents only): member repositories re-inspected read-only from bare clones. All three evidence branches are still at the commits in §1 below; no member file is new or changed, so nothing was retrieved or overwritten. Re-hash of all 95 Member 2–4 copies: 92 blob-identical, 3 differ only by the logged integration edits; all 5 stored diffs regenerate identically. The repositories are **private** (the names end with `-`). New: [phase6-member-repo-verification/](phase6-member-repo-verification/README.md) (verification record, per-file provenance manifest, hash snapshots) and [evidence/EVIDENCE_INDEX.md](evidence/EVIDENCE_INDEX.md). No evidence, member report, Part 01 file, code or test changed; no test run; nothing committed or pushed.
> - **Phase 7 and Phase 8A** (25 Nov 2026, documents only): recorded in [FINAL_COMPLETION_TRACKER.md](FINAL_COMPLETION_TRACKER.md). Phase 8A checked the owner's INT-01 run on PostgreSQL 18.6 (run folder [`run-20261125-125857/`](phase7-final-completion/int01-postgresql/run-20261125-125857/)): (a), (b), (d) PASS; (c) BLOCKED (RTM Q2). The integrator did not re-run INT-01, changed no evidence, code, test, dependency or Flyway configuration, and kept the database and role. Nothing committed or pushed.
> - **Phase 9** (25 Nov 2026, documents only): owner-supplied member names and registration numbers entered in the final documents (register Revision 7); PART02's 20 links repaired with a marked note (Z-3); marked PostgreSQL status notes added to the Member 3 draft and the Member 4 revision; `../SUBMISSION_INDEX.md` drafted; ZIP decisions and exact file list prepared for approval. No ZIP created, nothing committed, INT-01 not re-run, database kept. Record: [phase9-final-submission/](phase9-final-submission/PHASE9_SUBMISSION_DECISIONS.md).
> - **Phase 10** (25 Nov 2026, documents and packaging only): the project owner approved Z-1 – Z-8. A first ZIP (343 files, from the Phase 9 list) was built and verified outside the repository. It was then replaced by a final ZIP whose file list adds the Phase 10 records (347 files). That ZIP holds both the application and the assignment, under the single root folder `publiccare/`. Phase 10 notes were added to `qa/README.md`, this log, the tracker (§6), the register (Revision 8, no status change) and `../SUBMISSION_INDEX.md`. No evidence, code, test, migration or build file changed; no test or INT-01 re-run; database and role kept; nothing committed or pushed. Record: [phase10-final-package/](phase10-final-package/PHASE10_COMPLETION_RECORD.md).

## 1. Sources

| Member | Sections | Source | Branch | Commit used | Commit author / date |
|---|---|---|---|---|---|
| 1 | 3.1–3.2 | Local folder `Public care/qa/` (outside the Git repository) | – | – (files dated 23–24 Nov 2026) | – |
| 2 | 3.3–3.4 | https://github.com/madushankachamara891-pixel/PublicCare-QA-Member2-Oshan- | `main` (contains the commit) | `0ad36bd9530a23b6548175734b9b0ae597f9899e` | Oshan Ravishka, 2026-11-24 |
| 3 | 3.5–3.6 | https://github.com/madushankachamara891-pixel/PublicCare-QA-Member3-Ishani- | `member3-tdd-bdd-ci` | `93d11a34d22fff59f62d211faeacc60dfc3a50dd` | Ishani-Kumaranayaka, 2026-11-24 |
| 4 | 3.7–3.10 | https://github.com/madushankachamara891-pixel/PublicCare-QA-Member4-Chathura- | `chathura` | `1c8e07ba92e0ca32937a9c8d3d901ce3077c5058` | Chathura Madushan, 2026-11-24 |

The member repositories do not share Git history with `PublicCare`. Each holds a handover copy of the application under `project/`. Each `project/` folder was compared file by file (Git blob hashes) with `f565fb4`. All baseline files are present, and only these files differ:

| Member | Files in `project/` that differ from `f565fb4` |
|---|---|
| 2 | Changed: `backend/src/test/.../auth/AuthControllerTest.java`, `backend/src/test/.../report/ReportControllerTest.java` |
| 3 | Changed: `backend/pom.xml`, `backend/src/main/.../admin/AdminSetupRunner.java`. Added: 6 test classes and 2 feature files (listed in §2). Also added outside `project/`: `.github/workflows/ci.yml` |
| 4 | Changed: `src/AccountPage.jsx` (commit `f78deb1`) |

The pre-change version of every changed file in the member repositories is identical to `f565fb4`, so each diff in `unapplied-diffs/` is a diff against this repository's baseline.

## 2. Files copied

Member 2–4 files were written as raw Git blobs from the audited commit (`git cat-file blob <commit>:<path>`). After copying, every file's blob hash was checked against the source: **98 of 98 identical** (3 Member 1 files compared byte for byte with the local originals). No evidence file was edited. *[Phase 1 only. In Phase 3A/3B the Member 1 RTM and Test Plan and Member 3's `evidence/README.md` and `api-automation/README.md` received marked integration edits; no other copied file has been modified since the Phase 1 copy (checked by file modification time in Phase 3B, not re-hashed).]*

### Member 1 → `3.1-3.2_member1/` (3 files)

| Source | Destination | Note |
|---|---|---|
| `Public care/qa/PROJECT_ANALYSIS_REPORT.md` | `PROJECT_ANALYSIS_REPORT.md` | Identical to the handover copy in all three member repositories |
| `Public care/qa/PART01_REQUIREMENTS_AND_RTM.md` | `PART01_REQUIREMENTS_AND_RTM.md` | Identical to the handover copy in the Member 2 and Member 4 repositories |
| `Public care/qa/PART01_TEST_PLAN_AND_STLC.md` | `PART01_TEST_PLAN_AND_STLC.md` | **Newest local version** (24 Nov, 170,240 bytes). The older handover copy (blob `a5bc1c6`) was not used. |

*[Phase 5B rev. 2, 25 Nov 2026: owner decision D-8 (CAN-1) made the destination folder `3.1-3.2_member1/` the canonical Part 01 location. The source files in `Public care/qa/` are kept unchanged as a non-canonical reference copy. Until D-8 both were edited in parallel and were byte-identical; see register CAN-2 and CAN-3.]*

### Member 2 → `3.3-3.4_member2/` (14 files + 1 generated diff)

| Source path (at `0ad36bd`) | Destination |
|---|---|
| `qa/PART02_BLACK_BOX_AND_WHITE_BOX_TESTING.md` | `PART02_BLACK_BOX_AND_WHITE_BOX_TESTING.md` |
| `MEMBER_TASKS.md` | `MEMBER_TASKS.md` (assignment brief; linked from the report) |
| `evidence/01_…png` to `evidence/09_…png`, `evidence/10_Baseline_Maven_Log.txt` | `evidence/` (same names; E01–E10) |
| `project/backend/src/test/java/lk/publiccare/backend/auth/AuthControllerTest.java` | `reference-code/project/backend/src/test/java/lk/publiccare/backend/auth/AuthControllerTest.java` |
| `project/backend/src/test/java/lk/publiccare/backend/report/ReportControllerTest.java` | `reference-code/project/backend/src/test/java/lk/publiccare/backend/report/ReportControllerTest.java` |
| Generated: `git diff 962aeaf 0ad36bd -- project/backend/src/test/` | `unapplied-diffs/test-code-changes_962aeaf..0ad36bd.diff` |

### Member 3 → `3.5-3.6_member3/` (61 files + 3 generated diffs)

| Source path (at `93d11a3`) | Destination |
|---|---|
| `MEMBER_TASKS.md` | `MEMBER_TASKS.md` |
| `evidence/README.md`, `evidence/ui-automation-decision.md` | `evidence/` |
| `evidence/baseline/`: README.md, environment.txt, mvn-test-output.txt | `evidence/baseline/` |
| `evidence/tdd/`: 01-red-compile-error.txt, 02-red-assertion-failure.txt, 03-green.txt, 04-refactor-full-suite.txt, README.md | `evidence/tdd/` |
| `evidence/bdd/`: 01a-fail-cucumber-7.34.9.txt, 01b-pom-deps-regression-7.23.0.txt, 02a-run1-fail-csrf-403.txt, 02b-run2.txt, 03-full-suite.txt, README.md, cucumber-reports/cucumber.html, cucumber.json, cucumber.xml | `evidence/bdd/` |
| `evidence/api-automation/`: 01a-run1-stale-target-build-failure.txt, 01b-run2-fail-duplicate-email-400.txt, 02-run3.txt, 03-full-suite.txt, README.md, 2 Surefire reports | `evidence/api-automation/` |
| `evidence/ui-automation/`: 01a-run1-missing-class-files.txt, 01b-regression-with-selenium.txt, 02-ui-run1.txt, 03-fault-check-expected-failures.txt, 04-ui-final.txt, 05-full-suite.txt, README.md, 6 screenshots, 1 Surefire report | `evidence/ui-automation/` |
| `evidence/ci/`: README.md, artifact-summary.txt, 3 GitHub Actions screenshots | `evidence/ci/` |
| `evidence/frontend/`: 01-npm-ci.txt, 02-npm-lint.txt, 03-npm-build.txt, README.md, environment.txt | `evidence/frontend/` |
| `evidence/final/full-suite.txt` | `evidence/final/` |
| `project/backend/src/test/java/lk/publiccare/backend/admin/AdminSetupRunnerLoopbackTest.java` (TDD) | `reference-code/project/backend/src/test/java/…` (same relative path) |
| `…/bdd/CucumberSpringConfiguration.java`, `…/bdd/PublicCareSteps.java`, `…/bdd/RunCucumberTest.java` | `reference-code/project/backend/src/test/java/…` |
| `…/api/PublicCareApiTest.java` | `reference-code/project/backend/src/test/java/…` |
| `…/ui/PublicCareUiIT.java` | `reference-code/project/backend/src/test/java/…` |
| `project/backend/src/test/resources/features/citizen_report.feature`, `admin_status_update.feature` | `reference-code/project/backend/src/test/resources/features/` |
| `.github/workflows/ci.yml` | `reference-code/github-workflows/ci.yml` (**inactive**: GitHub only runs workflows from `.github/workflows/` at the repository root) |
| Generated: `git diff 2c1e597 93d11a3 -- …/admin/AdminSetupRunner.java` | `unapplied-diffs/AdminSetupRunner-ipv6-loopback_2c1e597..93d11a3.diff` |
| Generated: `git diff 2c1e597 93d11a3 -- project/backend/pom.xml` | `unapplied-diffs/pom-test-dependencies_2c1e597..93d11a3.diff` |
| Generated: `git diff 2c1e597 93d11a3 -- qa/` | `unapplied-diffs/part01-execution-status-edits_2c1e597..93d11a3.diff` |

### Member 4 → `3.7-3.10_member4/` (20 files + 1 generated diff)

| Source path (at `1c8e07b`) | Destination |
|---|---|
| `qa/MEMBER4_QA_REPORT.md` | `MEMBER4_QA_REPORT.md` |
| `MEMBER_TASKS.md` | `MEMBER_TASKS.md` |
| `qa/member4/`: REPRODUCE.md, health.jmx, registration.cjs, usability.cjs, sonar-project.properties | `member4/` (same folder name, so the report's `member4/…` links still work) |
| `evidence/member4/`: access-checks.txt, health.jtl, jacoco.csv, jmeter-console.txt, junit-summary.txt, mobile.txt, registration-before.png/.txt, registration-after.png/.txt, sonar-issues.json, sonar-measures.json, sonar-run.txt | `evidence/` (flattened; 13 files) |
| Generated: `git show --format=fuller f78deb1 -- project/src/AccountPage.jsx` | `unapplied-diffs/AccountPage-registration-fix_f78deb1.diff` |

### Files created during integration

| File | Purpose |
|---|---|
| `README.md` | Overview, responsibilities, gaps |
| `INTEGRATION_LOG.md` | This log |
| `CROSS_REFERENCE.md` | ID mappings, findings, counts, inconsistencies |
| `3.5-3.6_member3/FORMAL_REPORT_MISSING.md` | Records that the 3.5–3.6 report was not submitted. Contains no report content. |

## 3. Files deliberately excluded

| Excluded | Reason |
|---|---|
| Each member's full `project/` application copy | Instruction: do not copy or merge complete application copies. Only the changed or added files are kept, as reference copies or diffs. |
| Member 3 `project/backend/pom.xml` (full file) | Stored only as a diff. A second `pom.xml` in the repository could be picked up by IDEs or dependency scanners. |
| Member 3 `AdminSetupRunner.java`, Member 4 `AccountPage.jsx` (full files) | Code fixes stay unapplied. Kept only as diffs. |
| Member 3 modified `qa/PART01_REQUIREMENTS_AND_RTM.md` and `qa/PART01_TEST_PLAN_AND_STLC.md` | Based on the older handover Test Plan. Part 01 content is not changed in this phase. The edits are kept as a diff (see §5). |
| `qa/PROJECT_ANALYSIS_REPORT.md`, `qa/PART01_*.md` handover copies in the Member 2 and Member 4 repositories | Duplicates or older versions of Member 1's documents. |
| `README.md` in each member repository | Generic handover README. Member 4 added only 4 lines with links to the report, evidence and reproduction steps, and these are already linked from this README. |
| `CLAUDE.md`, `CLAUDE_PROMPT.md` in each member repository | AI-assistant handover instructions, not QA deliverables. |
| Member 3 CI artifact ZIP `backend-test-reports` | Not committed by Member 3; only its summary (`ci/artifact-summary.txt`) exists. |
| Generated build output (`target/`, JaCoCo HTML, `dist/`) | Not committed in any member repository. |

## 4. Changes made to the main repository

- Created `qa/` with four member folders and the files listed in §2.
- No file outside `qa/` was created, modified or deleted *[Phase 1 only; Phase 3A changed `AuthDtos.java` and added two test classes, see the note at the top]*. `backend/`, `src/`, `public/`, `backend/pom.xml`, `package.json`, `package-lock.json`, database migrations, application configuration and `.gitignore` are unchanged. The repository had no `.github/` folder at `f565fb4`, and none was created.
- No copied document was edited *[Phase 1 only; see the note at the top for Phase 3A and 3B edits]*. Links were not changed (see §7).
- Checks run after copying: nothing under `qa/` is matched by `.gitignore`; `npm run lint` (ESLint, without `--fix`) reports no errors with `qa/` present; a text scan of the copied files found no tokens, keys or passwords other than synthetic test values.

## 5. Member 3 edits to Part 01 (kept, not applied)

In their copy, Member 3 edited the Part 01 RTM and Test Plan (diffstat: 34 and 68 changed lines, all replacements) to record actual results for 15 test cases (8 fully executed, 7 partly executed). The edits were made on the **handover** Test Plan (`a5bc1c6`), which is older than the local Test Plan used here. The local Test Plan has other revisions (for example new §6 verification/validation table, updated OQ-10, scope wording), so Member 3's edits cannot simply be applied to it. They are kept in `3.5-3.6_member3/unapplied-diffs/part01-execution-status-edits_2c1e597..93d11a3.diff` for a later decision. *[Phase 3B: the API-04 part of that diff ("PARTIALLY EXECUTED", padded email `400`) is superseded by owner decision D-1; Part 01 now records API-04 as PASS. It must not be applied.]*

## 6. Evidence limitations

These are stated by the members in their own documents. They are repeated here so that results are not read as stronger than the evidence.

| Member | Limitation |
|---|---|
| All | Every test result comes from a member's isolated copy, not from this repository. No test has been run on this branch. *[Phase 1 only. Phase 3A ran the backend suite on this branch; see the note at the top.]* |
| 2 | Run on Windows 11, Java 21 (Microsoft OpenJDK 21.0.13.1), JaCoCo 0.8.12. EP/BVA/DT tests are MockMvc tests with mocked services; no database persistence is shown. Evidence is mainly screenshots plus one baseline Maven log; there are no Surefire XML files for the 79- and 84-test runs. "Actual HTTP" values come from passing assertions, not captured traffic. |
| 3 | Local runs used **Java 25** (the project targets Java 21). The Java 21 result comes from GitHub Actions run #1, which ran on commit `70d01e0`, not on the audited commit `93d11a3`. The Selenium UI job was added after run #1 and has no CI result. All backend automation uses H2, not PostgreSQL. The CI frontend job log was not inspected. The CI artifact ZIP is not committed. |
| 4 | Runs in an isolated Linux environment (Java 21, JaCoCo 0.8.13, JMeter 5.6.3, SonarQube Community Build 25.3). Load test: 100 requests to `/api/health` only (no database work), and no response-time target exists (Part 01 OQ-4). Usability: scripted walkthroughs in Chromium, no participants. SonarQube coverage 36.0% includes the frontend, which has no coverage data. The defect lifecycle (fixed, verified, closed) applies to the Member 4 copy only. |

## 7. Link inventory

All Markdown links in the copied documents were listed and resolved against the new layout. **No link was changed.** Link corrections are left for a separate step after review.

| Document | Links checked | Resolve | Broken | External |
|---|---:|---:|---:|---:|
| 3.1-3.2_member1/PROJECT_ANALYSIS_REPORT.md | 237 | 0 | 237 | 0 |
| 3.1-3.2_member1/PART01_REQUIREMENTS_AND_RTM.md | 245 | 1 | 244 | 0 |
| 3.1-3.2_member1/PART01_TEST_PLAN_AND_STLC.md | 24 | 18 | 6 | 0 |
| 3.3-3.4_member2/PART02_BLACK_BOX_AND_WHITE_BOX_TESTING.md | 20 | 0 | 20 | 0 |
| 3.7-3.10_member4/MEMBER4_QA_REPORT.md | 18 | 3 | 14 | 1 |
| Member 3 evidence READMEs | 0 | – | – | – |

Every broken link matches one of the patterns below. Each proposed target was tested and resolves to an existing file (521 of 521).

| Document | Current prefix | Proposed prefix | Links | Reason |
|---|---|---|---:|---|
| Member 1 (all three) | `../publiccare/` | `../../` | 487 | Written when `qa/` was outside the repository. Line anchors (`#L…`) refer to `f565fb4`, which is unchanged. |
| Member 2 report | `../evidence/` | `evidence/` | 10 | Evidence now sits beside the report |
| Member 2 report | `../project/backend/src/test/` | `reference-code/project/backend/src/test/` | 3 | Extended test classes kept as reference copies |
| Member 2 report | `../project/backend/src/main/` | `../../backend/src/main/` | 4 | The Member 2 copy's production code is identical to `f565fb4` |
| Member 2 report | `../MEMBER_TASKS.md` | `MEMBER_TASKS.md` | 1 | Brief copied beside the report |
| Member 2 report | `PART01_…md` | `../3.1-3.2_member1/PART01_…md` | 2 | Part 01 is in the Member 1 folder |
| Member 4 report | `../evidence/member4/` | `evidence/` | 14 | Evidence folder flattened |

Notes:

- Member 3's evidence READMEs name files as code text (for example `project/backend/src/test/...`, `.github/workflows/ci.yml`, `qa/PART01_...`), not as links. These paths now map to `reference-code/project/...`, `reference-code/github-workflows/ci.yml` and the Part 01 diff. They are not broken links, but readers need this mapping.
- Member 4's GitHub issue link (`PublicCare-QA-Member4-Chathura-/issues/1`) is external and was not checked online.
- Member 2's report describes generated coverage at `project/backend/target/site/jacoco/index.html` as plain text. That folder is build output and was never committed, so it is not available.

## Phase 11 – Part 01 execution and final completion (2026-11-25)

| Item | Record |
|---|---|
| Instruction | Owner: complete the assignment as far as possible from evidence, Member 1 sections first; no fabricated results; no commits |
| Execution | 39 Part 01 cases executed in an isolated copy of `backend/` (no `.env`, no `target/`); production code SHA-256-identical before and after. Runs: 40 tests 39/1 error (test expectation), 40/0, full suite 125/0. Record: [phase11-part01-exec/README.md](phase11-part01-exec/README.md) |
| Findings | New defect PC-QA-02 (API-24); observations OBS-1 – OBS-3 |
| Documents updated | Test Plan (header, §5.3, §6.1–§6.2, §7.6, §7.8, §7.9, §8, §9.3, §9.4, §10.2–§10.4 incl. Actual Result / Status / Execution details for all 79 cases, §11.3 new, §11.2, §12.4, AE-B, AE-F new); RTM (header, TASK 5 all 63 rows, TASK 6 all statuses, counts); new [Section 3.1–3.2 Final Report](3.1-3.2_member1/SECTION_3.1-3.2_FINAL_REPORT.md); Member 4 integrated revision §3.8 (PC-QA-02, F-02 note); reconciliation; evidence index; tracker; `qa/README.md`; `SUBMISSION_INDEX.md` |
| Not changed | Application source, tests in `backend/src/test/`, dependencies, configuration, member originals, archived reports, raw evidence. Nothing committed or pushed |
