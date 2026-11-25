# PublicCare – QA Assignment (Integration Draft)

> **Status: INTEGRATION DRAFT – NOT FINAL.**
> Phase 1 of the QA integration on branch `qa/final-assignment` (started from `f565fb4`).
> The materials in this folder were collected from four separate sources and have not yet been reviewed together by the project owner.
> One deliverable is missing (formal report for Sections 3.5–3.6), and many relative links inside the copied documents do not resolve yet (see [INTEGRATION_LOG.md](INTEGRATION_LOG.md#7-link-inventory)).
> *[Phase 9, 25 Nov 2026: the status lines above are the Phase 1 record. The submission entry point is [`../SUBMISSION_INDEX.md`](../SUBMISSION_INDEX.md); Phase 9 record: [phase9-final-submission/](phase9-final-submission/PHASE9_SUBMISSION_DECISIONS.md).]*
> *[Phase 10, 25 Nov 2026: the project owner approved ZIP decisions Z-1 – Z-8. The final package is one ZIP containing both the application and the assignment, built from the uncommitted working tree outside the repository (`Public care/submission/PublicCare-EC4206-QA-Submission.zip`). Record: [phase10-final-package/](phase10-final-package/PHASE10_COMPLETION_RECORD.md). Nothing committed or pushed.]*

> *[Phase 11, 25 Nov 2026: 39 further Part 01 cases were executed against the unchanged group baseline (41 of 79 now executed in the group repository; no FAIL; new defect PC-QA-02). Lecturer-facing summary of Sections 3.1–3.2: [3.1-3.2_member1/SECTION_3.1-3.2_FINAL_REPORT.md](3.1-3.2_member1/SECTION_3.1-3.2_FINAL_REPORT.md); evidence: [phase11-part01-exec/](phase11-part01-exec/README.md). Final package record: [phase11-final-package/](phase11-final-package/)]*

## Assignment overview

| Item | Value |
|---|---|
| Module | EC4206 – Software Testing and Quality Assurance (as stated in the Member 2 report) |
| System under test | PublicCare: a citizen issue-reporting and tracking platform (React + Vite frontend, Spring Boot backend, PostgreSQL with Flyway) |
| Application baseline | `f565fb4bb91d6c5af96775aa5333d862ec2883ad` (`main` of `madushankachamara891-pixel/PublicCare`) |
| Integration branch | `qa/final-assignment` (local only, not pushed) |
| How the work was done | Member 1 analysed the original repository read-only. Members 2–4 each worked in a **separate, isolated copy** of the application in their own GitHub repository. Their test results come from those copies, not from this repository. |

Apart from the Phase 3A exception below, the application code in this repository is unchanged. No member test code, build change, workflow or code fix has been applied. These items are stored as reference copies or diffs inside each member's folder.

> **Phase 3A exception (25 Nov 2026, uncommitted):** with the project owner's approval (owner decision D-1, Option A), `backend/src/main/java/lk/publiccare/backend/auth/AuthDtos.java` now trims the email before validation (FR-01.3 / API-04, finding F-03), and two test classes were added (`AuthEmailNormalizationApiTest`, `AuthDtosTest`). API-04 is now recorded as PASS in Part 01. Evidence and limitations: [phase3a-api04-correction/](phase3a-api04-correction/README.md).
>
> **Phase 3B (25 Nov 2026, uncommitted):** Part 01 consistency audit, documents only. The existing backend test-method count is corrected from 73 to 72 in the RTM and Test Plan (finding F-04, U-1); Member 3's test `emailWithSurroundingSpacesIsRejectedByValidation` is marked superseded by D-1. Execution status is unchanged: 1 of 79 Part 01 cases executed (API-04 PASS), 78 PROPOSED / NOT EXECUTED.
>
> **Phase 4 (25 Nov 2026):** read-only integration audit of Sections 3.1–3.10, preserved in [phase4-integration-audit/](phase4-integration-audit/README.md).
>
> **Phase 5A (25 Nov 2026, uncommitted):** P0 corrections and preparation, documents only. Member 4 backend line coverage corrected to 434 / 642 = 67.60% (from its own `jacoco.csv`); BDD scenario 4 mapping corrected in CROSS_REFERENCE. Prepared but **not applied**: the Part 01 "As Executed" addendum, Test Plan corrections, the Member 3 report skeleton, requirement-mapping recommendations, an author/date confirmation register and a baseline-deviation table. Execution status is unchanged. See [phase5a-p0-corrections/](phase5a-p0-corrections/README.md).
>
> **Phase 5B (25 Nov 2026, uncommitted):** confirmation register for Members 1–4 and the project owner, documents only: names, registration numbers, dates and schedule; Member 3 report requirements; Member 4 technical confirmations; owner decisions D-2, D-3, D-4, D-5 and D-8; the proposed canonical Part 01 location; Java 21 and PostgreSQL verification requirements. No personal detail filled in, no test run. See [phase5b-confirmation-register/CONFIRMATION_REGISTER.md](phase5b-confirmation-register/CONFIRMATION_REGISTER.md).
>
> **Owner decision D-8 (25 Nov 2026, Phase 5B revision 2, uncommitted): APPROVED, option CAN-1.** The **canonical Part 01 location** is [`3.1-3.2_member1/`](3.1-3.2_member1/) in this repository (`publiccare/qa/3.1-3.2_member1/`), chosen as the single source of truth inside the Git repository to prevent divergence between duplicate copies (still untracked until `qa/` is committed, which needs separate approval). Recorded in the RTM "Owner decisions" table and in the confirmation register (§4, §5). The outer duplicate in `Public care/qa/` (outside Git) is a **non-canonical reference copy**: kept unchanged, not deleted, not overwritten, no longer synchronised. The Part 01 corrections that D-8 unblocks (As Executed addendum, Test Plan corrections, Java 21 execution status) are listed in register §5.1 and are **not yet applied**. *[Phase 5B revision 3, 25 Nov 2026: UB-1 – UB-17 **applied** to the canonical copy only (the As Executed Addendum and marked additions in the Test Plan; a scope note in the RTM). Names, IDs and planned dates stay PENDING / TBC. UB-18 and UB-19 (link repair) not applied. See register §5.2.]*

> **Phase 6 (25 Nov 2026, uncommitted):** read-only re-inspection of the three member repositories (all private). They are unchanged since Phase 1; all copies re-hashed against the source commits (92 of 95 identical, 3 logged integration edits) and all stored diffs regenerated identically. Group evidence index with execution-origin labels (group repository / member repository / member CI / historical / not executed / not verified): [evidence/EVIDENCE_INDEX.md](evidence/EVIDENCE_INDEX.md). Record: [phase6-member-repo-verification/](phase6-member-repo-verification/README.md).

> **Phase 7 (25 Nov 2026, uncommitted): final completion**, on the project owner's instruction to finish without further member reviews. Tracker: [FINAL_COMPLETION_TRACKER.md](FINAL_COMPLETION_TRACKER.md). Some statements above are now outdated and are kept as the earlier record:
> - **3.5–3.6:** a report now exists as a **project-integrated draft** written from Member 3's evidence: [REPORT_3.5-3.6_TDD_BDD_AUTOMATION_CICD.md](3.5-3.6_member3/REPORT_3.5-3.6_TDD_BDD_AUTOMATION_CICD.md). Member 3 did not write or review it. G-1 and the "Missing" entries in the table below are true only for Member 3's own submission.
> - **3.7–3.10:** integrated revision with corrections and a group-level 3.10: [REPORT_3.7-3.10_INTEGRATED.md](3.7-3.10_member4/REPORT_3.7-3.10_INTEGRATED.md). Member 4's original report is unchanged.
> - **3.2:** execution reconciliation per repository: [SECTION_3.2_EXECUTION_RECONCILIATION.md](phase7-final-completion/SECTION_3.2_EXECUTION_RECONCILIATION.md).
> - **Links (G-7):** the 258 Part 01 links in the canonical RTM and Test Plan now resolve (UB-18 and the link correction applied). Still broken: the archived Analysis Report (237), PART02 (20) and Member 4's original report (14).
> - **INT-01 (PostgreSQL):** prepared and not yet executed: [phase7-final-completion/int01-postgresql/](phase7-final-completion/int01-postgresql/README.md). *[Phase 8A, 25 Nov 2026: executed by the project owner on PostgreSQL 18.6 – scenarios (a), (b), (d) PASS; (c) BLOCKED (RTM Q2). INT-01 is PARTIALLY EXECUTED, not an overall PASS.]*
> - Audit and final gap matrix: [ACADEMIC_AUDIT_AND_GAP_MATRIX.md](phase7-final-completion/ACADEMIC_AUDIT_AND_GAP_MATRIX.md).

## Sections and responsibilities

| Section | Topic | Member | Main document | Status in this integration |
|---|---|---|---|---|
| 3.1 | Scope, test plan, ISO/IEC 25010 quality attributes, verification vs validation | Member 1 | [PART01_TEST_PLAN_AND_STLC.md](3.1-3.2_member1/PART01_TEST_PLAN_AND_STLC.md) §1–§7; [PART01_REQUIREMENTS_AND_RTM.md](3.1-3.2_member1/PART01_REQUIREMENTS_AND_RTM.md); [PROJECT_ANALYSIS_REPORT.md](3.1-3.2_member1/PROJECT_ANALYSIS_REPORT.md) | Present (newest local versions). Document status in the file: "Draft for group review"; authors recorded as "TBC". **Canonical copy** (owner decision D-8, CAN-1) |
| 3.2 | STLC mapping, test cases in full format, test environment | Member 1 | [PART01_TEST_PLAN_AND_STLC.md](3.1-3.2_member1/PART01_TEST_PLAN_AND_STLC.md) §8–§12 | Present. 78 of 79 test cases are marked PROPOSED / NOT EXECUTED in this version; API-04 PASS (Phase 3A) |
| 3.3 | Black-box testing (EP, BVA, decision table) | Member 2 (Oshan Ravishka) | [PART02_BLACK_BOX_AND_WHITE_BOX_TESTING.md](3.3-3.4_member2/PART02_BLACK_BOX_AND_WHITE_BOX_TESTING.md) | Present, executed. 2 decision-table rules NOT EXECUTED |
| 3.4 | White-box testing (CFG, cyclomatic complexity, statement and branch coverage) | Member 2 (Oshan Ravishka) | Same report, §3.4 | Present, executed. Path B1 NOT EXECUTED |
| 3.5 | TDD and BDD | Member 3 (Ishani Kumaranayaka) | **Missing – no formal report.** Evidence index: [evidence/README.md](3.5-3.6_member3/evidence/README.md) | Evidence present; formal report not submitted |
| 3.6 | Test automation and CI/CD | Member 3 (Ishani Kumaranayaka) | **Missing – no formal report.** Evidence index: [evidence/README.md](3.5-3.6_member3/evidence/README.md) | Evidence present; formal report not submitted |
| 3.7 | Performance, security and usability testing | Member 4 (S. A. Chathura Madushan) | [MEMBER4_QA_REPORT.md](3.7-3.10_member4/MEMBER4_QA_REPORT.md) §3.7 | Present, executed |
| 3.8 | Defect tracking and root-cause analysis | Member 4 | Same report, §3.8 | Present (one defect, PC-QA-01) |
| 3.9 | SonarQube and quality metrics | Member 4 | Same report, §3.9 | Present, executed |
| 3.10 | Case study and best practices | Member 4 | Same report, §3.10 | Present |

Member names are taken from the sources: the Member 2 and Member 3 Git commit authors, and the byline of the Member 4 report. Member 1's name is not recorded in the Part 01 documents, so it is not given here.

> **Phase 9 (25 Nov 2026): member details supplied by the project owner** (exact forms; the owner confirmed which person is which member): Member 1 – Madusanka D.M.C, EG/2023/5720; Member 2 – Dharmasiri S.O.R, EG/2023/5559; Member 3 – Kumaranayaka K.I.S, EG/2023/5697; Member 4 – Madushan S.C, EG/2023/5716. These forms are used in the final documents. The names in the table above are the earlier forms (Git commit authors, Member 4's byline) and are kept as the earlier record. Phase 9 record: [phase9-final-submission/PHASE9_SUBMISSION_DECISIONS.md](phase9-final-submission/PHASE9_SUBMISSION_DECISIONS.md).

## Folder layout

| Folder | Contents |
|---|---|
| [3.1-3.2_member1/](3.1-3.2_member1/) | Project analysis report, requirements and RTM, test plan and STLC. **Canonical Part 01 location** (D-8, CAN-1); the outer `Public care/qa/` copy is a non-canonical reference copy |
| [3.3-3.4_member2/](3.3-3.4_member2/) | Report, `evidence/` (E01–E10), `reference-code/` (2 extended test classes), `unapplied-diffs/`, `MEMBER_TASKS.md` |
| [3.5-3.6_member3/](3.5-3.6_member3/) | `evidence/` (TDD, BDD, API, UI, CI, frontend, baseline, final), `reference-code/` (new tests, feature files, inactive workflow copy), `unapplied-diffs/` (code fix, `pom.xml` change, Part 01 status edits), `MEMBER_TASKS.md`, [FORMAL_REPORT_MISSING.md](3.5-3.6_member3/FORMAL_REPORT_MISSING.md) |
| [3.7-3.10_member4/](3.7-3.10_member4/) | Report, `member4/` (JMeter plan, browser scripts, Sonar settings, reproduction steps), `evidence/`, `unapplied-diffs/` (registration fix), `MEMBER_TASKS.md` |

## Key evidence by section

| Section | Evidence |
|---|---|
| 3.3–3.4 | [Screenshots E01–E09 and baseline Maven log E10](3.3-3.4_member2/evidence/) |
| 3.5 TDD | [tdd/](3.5-3.6_member3/evidence/tdd/) – Red (compile error, assertion failure), Green, Refactor |
| 3.5 BDD | [bdd/](3.5-3.6_member3/evidence/bdd/) – runs and Cucumber HTML/JSON/XML reports; [feature files](3.5-3.6_member3/reference-code/project/backend/src/test/resources/features/) |
| 3.6 API automation | [api-automation/](3.5-3.6_member3/evidence/api-automation/) – REST Assured runs and Surefire reports |
| 3.6 UI automation | [ui-automation/](3.5-3.6_member3/evidence/ui-automation/) – Selenium runs and screenshots; [decision record](3.5-3.6_member3/evidence/ui-automation-decision.md) |
| 3.6 CI | [ci/](3.5-3.6_member3/evidence/ci/) – GitHub Actions run #1 screenshots and artifact summary; [workflow copy (inactive)](3.5-3.6_member3/reference-code/github-workflows/ci.yml) |
| 3.7 | [JMeter plan](3.7-3.10_member4/member4/health.jmx), [JTL](3.7-3.10_member4/evidence/health.jtl), [console](3.7-3.10_member4/evidence/jmeter-console.txt), [access checks](3.7-3.10_member4/evidence/access-checks.txt), [mobile walkthrough](3.7-3.10_member4/evidence/mobile.txt) |
| 3.8 | [Registration before](3.7-3.10_member4/evidence/registration-before.png) / [after](3.7-3.10_member4/evidence/registration-after.png), [regression script](3.7-3.10_member4/member4/registration.cjs), [fix diff (unapplied)](3.7-3.10_member4/unapplied-diffs/AccountPage-registration-fix_f78deb1.diff) |
| 3.9 | [Sonar measures](3.7-3.10_member4/evidence/sonar-measures.json), [Sonar issues](3.7-3.10_member4/evidence/sonar-issues.json), [scanner log](3.7-3.10_member4/evidence/sonar-run.txt), [JaCoCo CSV](3.7-3.10_member4/evidence/jacoco.csv) |

## Missing or incomplete deliverables

| # | Item | Detail |
|---|---|---|
| G-1 | **Formal report for Sections 3.5–3.6** | Not submitted at the audited commit `93d11a3`. Only the evidence index and per-folder READMEs exist. Not written during integration. *[Phase 5A: still missing. A structure and evidence map for Member 3 was prepared: [FORMAL_REPORT_SKELETON_3.5-3.6.md](3.5-3.6_member3/FORMAL_REPORT_SKELETON_3.5-3.6.md).]* |
| G-2 | Part 01 execution status | The integrated Part 01 documents (newest local versions) still show 78 of 79 cases as PROPOSED / NOT EXECUTED (API-04: PASS since Phase 3A). Member 3 recorded results for 15 cases in an older copy; those edits are kept only as an [unapplied diff](3.5-3.6_member3/unapplied-diffs/part01-execution-status-edits_2c1e597..93d11a3.diff). |
| G-3 | Part 01 authors | The Test Plan records "Authors: TBC". |
| G-4 | Selenium UI job in CI | Added to the workflow after CI run #1; no CI result for it is recorded. |
| G-5 | Not executed items | Member 2: DT-05, DT-06, white-box path B1. Member 3: INT-01 and most of the 79 Part 01 cases. Member 4: no participant usability study, no database-backed load test. |
| G-6 | Unapplied code fixes | `AdminSetupRunner` IPv6 loopback fix (Member 3) and the registration fix for PC-QA-01 (Member 4) exist only in member copies. In this repository both behaviours are unchanged. |
| G-7 | Broken relative links | 521 relative links in the copied documents do not resolve in the new layout. All have a verified correction, recorded but not yet applied. |
| G-8 | Single combined test run | No run exists that combines all members' test additions. Test counts from different copies must not be added together (see [CROSS_REFERENCE.md](CROSS_REFERENCE.md#4-test-counts-and-the-72-vs-73-discrepancy)). |

## Integration documents

- [INTEGRATION_LOG.md](INTEGRATION_LOG.md): sources, commits, files copied and excluded, evidence limitations, link inventory.
- [CROSS_REFERENCE.md](CROSS_REFERENCE.md): test-ID and requirement mappings, defects and findings, test counts, open inconsistencies.
- [phase4-integration-audit/PHASE4_INTEGRATION_AUDIT.md](phase4-integration-audit/PHASE4_INTEGRATION_AUDIT.md): Phase 4 audit of Sections 3.1–3.10 (preserved unchanged).
- [phase5a-p0-corrections/](phase5a-p0-corrections/README.md): Phase 5A P0 corrections, prepared addenda and confirmation registers.
- [phase5b-confirmation-register/CONFIRMATION_REGISTER.md](phase5b-confirmation-register/CONFIRMATION_REGISTER.md): Phase 5B confirmation register (confirmed, pending, rejected and unavailable items; owner decisions; environment verification).
- [phase6-member-repo-verification/](phase6-member-repo-verification/README.md): Phase 6 member repository re-inspection, provenance manifest and screenshot verification.
- [evidence/EVIDENCE_INDEX.md](evidence/EVIDENCE_INDEX.md): evidence index for all sections, with execution origin and provenance.
- *[Phase 10 additions]* [FINAL_COMPLETION_TRACKER.md](FINAL_COMPLETION_TRACKER.md): Phases 7 – 10 tracker. [phase7-final-completion/](phase7-final-completion/ZIP_CONTENTS_PROPOSAL.md): Phase 7 records, ZIP proposal and the INT-01 PostgreSQL record (Phase 8A). [phase9-final-submission/](phase9-final-submission/PHASE9_SUBMISSION_DECISIONS.md): Phase 9 member details and ZIP decisions. [phase10-final-package/](phase10-final-package/PHASE10_COMPLETION_RECORD.md): Phase 10 approvals, final file list and package record.
