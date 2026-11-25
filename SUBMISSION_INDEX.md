# PublicCare – EC4206 QA Group Submission: Index

| Item | Value |
|---|---|
| Module | EC4206 – Software Testing and Quality Assurance, Group Assignment |
| System under test | PublicCare – citizen issue reporting and tracking platform (React + Vite frontend, Spring Boot backend, PostgreSQL with Flyway) |
| Package date | 25 Nov 2026 |
| Code state in this package | Git `f565fb4` ("Finalize report deletion and cleanup", branch `qa/final-assignment`) **plus uncommitted changes**: `backend/src/main/java/lk/publiccare/backend/auth/AuthDtos.java` (owner decision D-1: e-mail trimmed before validation), two new test classes (`AuthDtosTest`, `AuthEmailNormalizationApiTest`) and the whole `qa/` folder. Nothing was committed or pushed (decision Z-7) |
| Integrity | `MANIFEST-SHA256.txt` lists the SHA-256 of every file in this package *[Phase 10: every file except the manifest itself, which cannot list its own hash. The ZIP and manifest hashes are recorded outside the package, in `PublicCare-EC4206-QA-Submission.VERIFICATION.txt` beside the ZIP]* |
| Package contents *[Phase 10]* | This one ZIP contains **both** the PublicCare application (frontend `src/`, `public/`; backend `backend/` with tests, Flyway migrations V1 – V5 and `.env.example`; build configuration) **and** the EC4206 assignment (`qa/`: Sections 3.1 – 3.10, evidence, integration records). Not included: `.env`, `.git/`, `node_modules/`, `dist/`, `backend/target/`. Exact list: [qa/phase10-final-package/PACKAGE_FILE_LIST.txt](qa/phase10-final-package/PACKAGE_FILE_LIST.txt) |
| Packaging decisions *[Phase 10]* | Z-1 – Z-8 approved by the project owner on 25 Nov 2026 ([Phase 10 record](qa/phase10-final-package/PHASE10_COMPLETION_RECORD.md)) |

## 1. Group members

Names and registration numbers as supplied by the project owner (25 Nov 2026). Initials are given exactly as supplied.

| Member | Name | Registration number | Assignment sections |
|---|---|---|---|
| Member 1 | Madusanka D.M.C | EG/2023/5720 | 3.1 – 3.2 |
| Member 2 | Dharmasiri S.O.R | EG/2023/5559 | 3.3 – 3.4 |
| Member 3 | Kumaranayaka K.I.S | EG/2023/5697 | 3.5 – 3.6 |
| Member 4 | Madushan S.C | EG/2023/5716 | 3.7 – 3.10 |

## 2. Where each section is

| Section | Read this | What kind of document it is |
|---|---|---|
| 3.1 Test planning | **Start here:** [Section 3.1–3.2 Final Report](qa/3.1-3.2_member1/SECTION_3.1-3.2_FINAL_REPORT.md) §3.1. Details: [Test Plan and STLC](qa/3.1-3.2_member1/PART01_TEST_PLAN_AND_STLC.md) §1–§7; [Requirements and RTM](qa/3.1-3.2_member1/PART01_REQUIREMENTS_AND_RTM.md) | Member 1's documents. The final report, the execution results and the environment summary were added during integration (Phase 11, 25 Nov 2026) and are labelled as such |
| 3.2 STLC and test cases | [Final Report](qa/3.1-3.2_member1/SECTION_3.1-3.2_FINAL_REPORT.md) §3.2 (STLC, 79-case table, environment, test summary). All 79 cases in the full format with actual results: [Test Plan](qa/3.1-3.2_member1/PART01_TEST_PLAN_AND_STLC.md) §10.4. Evidence: [Phase 11 execution record](qa/phase11-part01-exec/README.md); [execution reconciliation](qa/phase7-final-completion/SECTION_3.2_EXECUTION_RECONCILIATION.md) | Cases designed by Member 1; 39 of them executed during integration (Phase 11) |
| 3.3 – 3.4 Black-box and white-box testing | [PART02](qa/3.3-3.4_member2/PART02_BLACK_BOX_AND_WHITE_BOX_TESTING.md) | Member 2's report. During integration only the byline was added and the 20 relative links were repaired (marked note at the top of the report) |
| 3.5 – 3.6 TDD, BDD, automation, CI/CD | [Report 3.5–3.6](qa/3.5-3.6_member3/REPORT_3.5-3.6_TDD_BDD_AUTOMATION_CICD.md) | **Project-integrated draft.** Written during integration from Member 3's verified evidence, because no formal report was submitted. Member 3 did not write, review or approve the text. It contains no personal reflection |
| 3.7 – 3.10 Performance, security, usability; defects; metrics; case study | [Report 3.7–3.10 (integrated revision)](qa/3.7-3.10_member4/REPORT_3.7-3.10_INTEGRATED.md) | **Submission version.** Member 4's report, corrected and completed from the evidence during integration; every change is listed in its §0.2. Section 3.10 is a group-level case study written from the project records. Member 4 did not review the changes |

### Original and archived records (kept unchanged, for provenance)

| Document | Label | Note |
|---|---|---|
| [MEMBER4_QA_REPORT.md](qa/3.7-3.10_member4/MEMBER4_QA_REPORT.md) | **ORIGINAL** – Member 4's report as submitted on 24 Nov 2026 | Superseded for marking by the integrated revision above. Its byline reads "S. A. Chathura Madushan"; the owner-supplied form for this submission is "Madushan S.C". 14 of its links point to Member 4's own folder layout and do not open here |
| [PROJECT_ANALYSIS_REPORT.md](qa/3.1-3.2_member1/PROJECT_ANALYSIS_REPORT.md) | **ARCHIVED** – superseded by the Requirements and RTM document | The 23 Nov 2026 analysis that the RTM was built from. Its counts are superseded, and its 237 links point to an earlier folder layout and do not open here. Not to be read as a current deliverable |
| [FORMAL_REPORT_SKELETON_3.5-3.6.md](qa/3.5-3.6_member3/FORMAL_REPORT_SKELETON_3.5-3.6.md), [FORMAL_REPORT_MISSING.md](qa/3.5-3.6_member3/FORMAL_REPORT_MISSING.md) | **SUPERSEDED working files** | The planning skeleton and the note that Member 3's report was missing. Replaced by the 3.5–3.6 draft |

## 3. Execution status – how to read the results

- Results come from **separate repositories**. Members 2, 3 and 4 each tested an isolated copy of the application in their own private repository. Their results are **not** executions in this group repository, and test counts from different copies are not added together. The [evidence index](qa/evidence/EVIDENCE_INDEX.md) labels every evidence file with where it was produced.
- **Group repository:** 72 existing backend tests, 0 failures at `f565fb4`; 85 tests, 0 failures after D-1, on Java 27 and on Java 21 (H2 in-memory database). API-04: **PASS**.
- **Part 01 cases (updated Phase 11, 25 Nov 2026):** 79 planned. Executed in the group repository: **41** – 33 PASS, 6 PASS with one part BLOCKED, 1 BLOCKED (API-24, which found defect PC-QA-02), 1 PARTIALLY EXECUTED (INT-01); **no FAIL**. Six more UI cases passed in the Member 3 copy only. **32 not executed** (22 manual, 8 UI, 2 Vitest cases BLOCKED). Phase 11 ran 40 new execution tests (40/0) and the full suite (125/0) on Java 27 with H2, on production code identical to the repository ([execution record](qa/phase11-part01-exec/README.md)).
- Statuses are used strictly: **PASS**, **FAIL**, **PARTIAL / PARTIALLY EXECUTED**, **BLOCKED**, **NOT EXECUTED**, **NOT VERIFIED**. Nothing reported as not executed was simulated.

### PostgreSQL (INT-01)

INT-01 was run once by the project owner on 25 Nov 2026 on a dedicated local PostgreSQL 18.6 database ([INT-01 record](qa/phase7-final-completion/int01-postgresql/README.md)). It is **PARTIALLY EXECUTED**, not an overall PASS:

| Scenario | Status |
|---|---|
| (a) Fresh database: Flyway applies V1–V5 | **PASS** |
| (b) Restart: no migration re-applied | **PASS** |
| (c) Existing database baselined at V2 | **BLOCKED / NOT EXECUTED** – the pre-Flyway database history is not known (RTM Q2). No data was invented for it |
| (d) Schema ownership: no Hibernate DDL | **PASS**. The effective run-time `ddl-auto` value is **NOT VERIFIED** (configured `none`; it could not be read at run time) |

INT-01 covered start-up, migrations and health checks only. No API call wrote or read data, so persistence on PostgreSQL and the database-generated `reference` value are **NOT VERIFIED**. Every automated test suite (group and members) ran on H2.

## 4. Known limitations of this package

1. **Uncommitted state.** The package is a snapshot of the working tree (see "Code state" above).
2. **Private member repositories.** Links to Member 3's CI run and to Member 4's GitHub issue #1 need collaborator access. The CI screenshots and the defect record in the package are the evidence available without access.
3. **Evidence that does not exist** is reported as a gap, not replaced: no SonarQube dashboard screenshots, no export of the issue #1 timeline, no CI run on Member 3's final commit `93d11a3`, the CI `ui` job never ran, no Member 3 personal reflection.
4. **Evidence files are unchanged.** Some logs and screenshots contain local Windows user-profile paths. They are kept as produced, for provenance.
5. **Open items** that no record answers: co-authors of Part 01; the names of the project owner, integrator and API-04 tester as separate roles; the planned schedule dates; RTM questions Q1 and Q2; owner decisions D-2 – D-5 and D-7. They are shown as PENDING / TBC in the documents.
6. The INT-01 run produced an empty `uploads/` folder. A ZIP file does not keep empty folders, so it is not in the package.
7. *[Phase 10]* **Windows path length.** The longest path in the package is 136 characters. Extract the ZIP into a short folder (for example `C:\submission\`); a very deep target folder can make Windows tools fail with "path not found".
8. *[Phase 11]* **Part 01 execution code is evidence, not application code.** The four Phase 11 test classes are in `qa/phase11-part01-exec/test-code/`, not in `backend/src/test/`; they were run in an isolated copy whose production code is identical to this package. They are not run by CI. Their results used H2, and no member reviewed them.

## 5. Integration records (audit trail)

[qa/README.md](qa/README.md) (overview), [INTEGRATION_LOG.md](qa/INTEGRATION_LOG.md), [CROSS_REFERENCE.md](qa/CROSS_REFERENCE.md), [FINAL_COMPLETION_TRACKER.md](qa/FINAL_COMPLETION_TRACKER.md), [confirmation register](qa/phase5b-confirmation-register/CONFIRMATION_REGISTER.md), [academic audit and gap matrix](qa/phase7-final-completion/ACADEMIC_AUDIT_AND_GAP_MATRIX.md) and the phase folders `qa/phase3a-…` to `qa/phase9-…` *[Phase 10: and `qa/phase10-final-package/`]* *[Phase 11: and `qa/phase11-part01-exec/` (Part 01 execution) and `qa/phase11-final-package/` (final package record)]*. They record every correction, its evidence and who decided it.
