# PublicCare QA – Final completion tracker (Phase 7)

| Item | Value |
|---|---|
| Date | 2026-11-25 |
| Branch / baseline | `qa/final-assignment` at `f565fb4` (local only). Working tree: `M AuthDtos.java` (D-1), 2 untracked D-1 test classes, untracked `qa/`. Nothing committed or pushed |
| Purpose | Single tracker for finishing the EC4206 submission (Sections 3.1–3.10). Replaces nothing; the Phase 5B register stays the record of confirmations and decisions |
| Owner instruction (Phase 7) | Finalise the assignment from existing verified evidence **without requesting further reviews from Members 1–4**. Do not invent results, screenshots, personal experiences, test executions or approvals |
| File history | This file did not exist when Phase 7 started (searched the whole workspace: no `FINAL_COMPLETION_TRACKER.md` or other tracker). It was created **once**, at the path the Phase 7 instruction names. No second tracker exists |
| Phase 7 record folder | [`phase7-final-completion/`](phase7-final-completion/) – hash snapshots and stage records |

## How member confirmations are handled in Phase 7

The owner's instruction removes the need to **ask** members again. It does not turn a missing confirmation into a given one. So:

- Text drafted from evidence is labelled **"project-integrated draft"**. It never says that a member wrote, reviewed or approved it.
- A register item that needed a member's *fact* (a name spelling, a student ID, a machine, a date, a screenshot) stays **PENDING**. The report shows the gap instead of a guessed value.
- A register item that needed a member's *agreement to a wording or correction that the evidence already supports* (for example 434 / 642 lines, "Closed in the Member 4 copy; open in the group baseline", FR-02.8 for scenario 4) is applied **as an owner-authorised integration correction** and marked so. Its status becomes **RESOLVED BY OWNER AUTHORISATION (member not consulted)** – not CONFIRMED.

## Inputs read at the start of Phase 7

All inputs are unchanged since the Phase 6 snapshot ([`phase6-member-repo-verification/qa-sha256-after-phase6.txt`](phase6-member-repo-verification/qa-sha256-after-phase6.txt)); the only files not in that snapshot are Phase 6's own four record files. Pre-Phase 7 snapshot of all 191 files under `qa/`: [`phase7-final-completion/qa-sha256-before-phase7.txt`](phase7-final-completion/qa-sha256-before-phase7.txt).

| Input | SHA-256 (start of Phase 7) |
|---|---|
| [Confirmation register](phase5b-confirmation-register/CONFIRMATION_REGISTER.md) (Rev. 4) | `88e08d87…2abf2d` |
| [Phase 6 verification record](phase6-member-repo-verification/README.md) | `3d52d89e…b16cf7` |
| [Integration log](INTEGRATION_LOG.md) | `be82d6af…88e3d` |
| [Evidence index](evidence/EVIDENCE_INDEX.md) | `7b1f0648…51b7e5` |
| [Member 3 skeleton](3.5-3.6_member3/FORMAL_REPORT_SKELETON_3.5-3.6.md) | `b16b43b2…c016024` |
| [Member 4 report](3.7-3.10_member4/MEMBER4_QA_REPORT.md) | `c8f5a598…1eb20a3` |

Register totals at the start of Phase 7: **24 confirmed, 49 pending, 6 rejected, 7 unavailable** (register §1, Revision 4).

## Status vocabulary

| Status | Meaning |
|---|---|
| **DONE** | Complete and checked against evidence |
| **DRAFTED** | Written by integration from evidence, labelled as a project-integrated draft |
| **IN PROGRESS** | Being worked on in Phase 7 |
| **BLOCKED** | Needs an owner action or a value nobody in the records holds |
| **NOT STARTED** | Not yet reached in the Phase 7 order |
| **GAP (accepted)** | Cannot be closed from existing evidence; stated as a limitation in the report |

## 1. Phase 7 steps

| Step | Work | Status | Output |
|---|---|---|---|
| 1 | Update completion tracker | **DONE** | This file |
| 2 | Member 3 report, Sections 3.5–3.6 | **DRAFTED** (project-integrated; not reviewed by Member 3; no personal reflection) | [REPORT_3.5-3.6_TDD_BDD_AUTOMATION_CICD.md](3.5-3.6_member3/REPORT_3.5-3.6_TDD_BDD_AUTOMATION_CICD.md). Marked notes appended to `FORMAL_REPORT_MISSING.md` and the skeleton |
| 3 | Member 4 report, Sections 3.7–3.10 (corrections, group 3.10) | **DRAFTED** (project-integrated revision; original kept unchanged) | [REPORT_3.7-3.10_INTEGRATED.md](3.7-3.10_member4/REPORT_3.7-3.10_INTEGRATED.md) |
| 4 | Section 3.2 execution reconciliation table | **DONE** | [SECTION_3.2_EXECUTION_RECONCILIATION.md](phase7-final-completion/SECTION_3.2_EXECUTION_RECONCILIATION.md) |
| 5 | PostgreSQL INT-01 on a dedicated local database | **DONE – scenario (c) BLOCKED** *[Phase 8A, 25 Nov 2026; was BLOCKED – owner action (B-2)]*. Run by the owner; all 16 output files checked: (a), (b), (d) **PASS**, (c) **BLOCKED** (RTM Q2). INT-01 = PARTIALLY EXECUTED, not an overall PASS. Database and role kept until the owner approves cleanup. Phase 7 record: Prepared: Java 21 jar built, isolated script, safety checks | [int01-postgresql/](phase7-final-completion/int01-postgresql/README.md) |
| 6 | UB-18 and link correction (canonical Part 01 only) | **DONE** | RTM and Test Plan: 282/282 links resolve; 232 line anchors checked. Outer copy unchanged (CAN-3 hashes). Analysis Report not edited (D-6) |
| 7 | Academic audit 3.1–3.10 vs EC4206 task list and LO-1 – LO-4; gap matrix | **DONE** | [ACADEMIC_AUDIT_AND_GAP_MATRIX.md](phase7-final-completion/ACADEMIC_AUDIT_AND_GAP_MATRIX.md): 25 MET, 2 PARTLY MET, 0 NOT MET; one S1 gap (G-01 names) |
| 8 | Proposed ZIP contents for owner approval; ZIP only after approval | **AWAITING OWNER APPROVAL** (no ZIP created) | [ZIP_CONTENTS_PROPOSAL.md](phase7-final-completion/ZIP_CONTENTS_PROPOSAL.md) – decisions Z-1 – Z-7 |

### Register items handled under the owner's Phase 7 authorisation (member not consulted; register status unchanged)

| Item | Applied where | How |
|---|---|---|
| P-M3-1 (narrative), P-M3-9, P-M3-10 | M3 draft; M4 rev. §3.10 | Written from evidence; disclosed as integration text |
| P-M3-6 (scenario 4 → FR-02.8 / FR-06.6) | M3 draft §4.3 | RMR §1 mapping |
| P-M4-1 (434 / 642) | M4 rev. §3.9.2 | Kept, re-verified in Phase 7 |
| P-M4-2, P-M4-3 (defect environment, status) | M4 rev. §3.8.1 | Environment from evidence; "Closed in M4 copy / Open in group baseline" |
| P-M4-6 (bug triage) | M4 rev. §3.9.4 | Static reading of the source; labelled as integration triage |
| P-M4-7, P-M4-8, P-M4-9 | M4 rev. §3.7.2, §3.9.3 | RMR §2 rows 1–5 (FR-02.5 for `/api/auth/me`); baseline wording; risk and error rate |
| P-M4-11 (group 3.10) | M4 rev. §3.10 | Group-level reflection from the records |
| Still PENDING (need a person's own facts) | – | P-M1-1–3, P-M2-1–3, P-M3-2–5, P-M3-7, P-M3-8, P-M3-11, P-M4-4, P-M4-5, P-M4-10, P-M4-12, P-OW-1–4, P-SC-1–4, ENV-J7, ENV-J10 |

### Corrections found in Phase 7 (all in integration or member records, from the evidence)

1. BDD run 1: one of the 4 failures was `401 → 403`, not `201 → 403` (skeleton and member README; logged in the skeleton note and the evidence index).
2. `meRequiresSession` covers API-08 steps 1–2 → FR-02.5 only.
3. API-10 is partly executed (step 3 not), per Member 3's own case status.
4. SonarQube also analysed test code (`sonar.tests`); 3 issues are in test classes.
5. "Linux container" for Member 4 is not in the evidence; the scanner log says `Linux 6.18.57 amd64`.
6. JMeter throughput is bounded by the 250 ms pacing (≈ 19 req/s ceiling); it is not a capacity figure.

## 2. Sections 3.1–3.10 (state at the start of Phase 7)

| Section | Deliverable | State at start | Main open gaps (register / Phase 4 IDs) | Phase 7 step |
|---|---|---|---|---|
| 3.1 | Part 01 Test Plan §1–§7 + RTM (canonical, D-8) | Present; As Executed Addendum applied (UB-1 – UB-17) | Authors TBC (P-M1-1 – P-M1-3); planned dates TBC (P-SC-1, P-SC-2); UB-18 not applied; links broken (UB-19 / P0-6) | 6, 7 |
| 3.2 | Part 01 Test Plan §8–§12 | Present; 1 of 79 cases executed in the group repository (API-04) | No reconciliation of member executions (P1-15); INT-01 never executed (ENV-P6) | 4, 5 |
| 3.3 | PART02 (Member 2) | Present, executed in Member 2 copy | DT-05, DT-06 not executed; byline missing (P-M2-1 – P-M2-3); links broken | 6, 7 |
| 3.4 | PART02 §3.4 | Present, executed in Member 2 copy | Path B1 not executed; "baseline" label of E02–E05 not verified (F6-2) | 7 |
| 3.5 | Member 3 report | **Missing** – skeleton only | P-M3-1 – P-M3-11 | 2 |
| 3.6 | Member 3 report | **Missing** – skeleton only | Same; CI on `70d01e0` only; `ui` job never executed | 2 |
| 3.7 | MEMBER4_QA_REPORT §3.7 | Present | Environment details (P-M4-10); risk / error-rate fields (P-M4-9); coarse FR IDs (P-M4-7) | 3 |
| 3.8 | §3.8 | Present | Environment and status fields (P-M4-2, P-M4-3); lifecycle evidence external only (P-M4-4); PNGs show form state only (F6-3); second RCA (P1-17) | 3 |
| 3.9 | §3.9 | Present; line coverage corrected in Phase 5A | P-M4-1 acceptance; bug triage (P-M4-6); "this baseline" wording (P-M4-8); no Sonar screenshots (P-M4-5) | 3 |
| 3.10 | §3.10 | Present, Member 4 work only | Group-level Agile/DevOps reflection (P-M4-11, P1-9) | 3 |

## 3. Blockers known at the start of Phase 7

| ID | Blocker | Who | Effect |
|---|---|---|---|
| B-1 | Names and registration numbers of all members, owner and integrator (P-M1-1/2, P-M2-1/2, P-M3-2/3, P-M4-12, P-OW-1 – P-OW-4) | Owner / members | Bylines and contribution table stay PENDING. Nothing is inferred from handles or paths (REJ-3) |
| B-2 | PostgreSQL password for the dedicated INT-01 database | Owner | Step 5 cannot run until the owner sets it as an environment variable in their own shell *[Phase 8A, 25 Nov 2026: **resolved** – the owner ran the script in their own window; no password reached the integrator, the chat or any file (credential scan 0)]* |
| B-3 | Owner decisions D-2 (commit D-1), D-3 (apply `f78deb1`), D-4 (apply IPv6 fix), D-5 (bring member tests in), D-6, D-7 | Owner | Execution status stays per copy; PC-QA-01 stays open in the group baseline; no combined run |
| B-4 | Screenshots that only members hold: SonarQube dashboard (P-M4-5), issue #1 timeline (P-M4-4), CI on `93d11a3` / `ui` job (P-M3-7) | Members | Accepted as stated gaps; no screenshot is created or simulated |
| B-5 | Member 3 personal reflection | Member 3 | Not written. The report says so |

## 4. Phase 8A – INT-01 results verified (2026-11-25)

The project owner ran `run-int01.ps1` once (run folder [`run-20261125-125857/`](phase7-final-completion/int01-postgresql/run-20261125-125857/)). The integrator read all 16 output files and did **not** treat the script's `COMPLETED` status as a test result. Full record: [INT-01 README, "Phase 8A – verified results"](phase7-final-completion/int01-postgresql/README.md#phase-8a--verified-results-2026-11-25).

| Item | Result |
|---|---|
| INT-01 overall | **PARTIALLY EXECUTED** – not an overall PASS |
| (a) Fresh database | **PASS** – V1–V5 applied in order, history 1–5 `success = t`, schema matches the scripts, health `200 UP` |
| (b) Restart | **PASS** – "Schema "public" is up to date. No migration necessary"; history and schema unchanged |
| (c) Existing database baselined at V2 | **BLOCKED** (RTM Q2) – not executed, no data invented |
| (d) Schema ownership | **PASS** – no Hibernate DDL, no object of Hibernate origin; effective `ddl-auto` **NOT VERIFIED** at run time (configured `none`, no override) |
| Database | PostgreSQL **18.6** (x86_64-windows, UTF8); dedicated `publiccare_int01_qa` / `publiccare_int01_app`, kept for inspection |
| Flyway | 11.7.2; 5 migrations applied in 0.110 s; WARN: PostgreSQL 18.6 newer than the latest supported version (17) |
| Credentials | Script scan 0; independent pattern search found no credential value |
| Not changed | Application source, tests, dependencies, Flyway configuration, raw evidence. INT-01 not re-run. Nothing committed or pushed |

**Documentation updated in Phase 8A** (marked *[Phase 8A]* notes; earlier text kept): INT-01 README; confirmation register (Revision 6); canonical RTM (FR-09.2 row, TASK 6-E, Q2/Q3, status notes); canonical Test Plan (§10.4 INT-01 entry, ENV-03, §11.1 Database row, As Executed Addendum AE-B/AE-D/AE-E); evidence index; this tracker; academic audit and gap matrix (G-02); 3.2 reconciliation; `qa/README.md`; `INTEGRATION_LOG.md`; ZIP proposal (INT-01 run folder row). Snapshot before the edits: [`qa-sha256-before-phase8a.txt`](phase7-final-completion/int01-postgresql/qa-sha256-before-phase8a.txt).

Register totals after Revision 6: **28 confirmed, 46 pending, 6 rejected, 6 unavailable** (was 24 / 49 / 6 / 7).

### Completion status after Phase 8A

| Step | Status |
|---|---|
| 1–4, 6, 7 | Unchanged (DONE / DRAFTED as in §1) |
| 5 – INT-01 | **DONE – scenario (c) BLOCKED** (RTM Q2) |
| 8 – ZIP | **AWAITING OWNER APPROVAL** (Z-1 – Z-7). The INT-01 run folder now meets its inclusion condition |
| Gap G-02 | Largely closed; S2 → S3 (stated residual limitations) |
| Gap G-01 (names, IDs) | **Still the only S1 blocker** |

**Owner actions still open:** approve cleanup of `publiccare_int01_qa` and `publiccare_int01_app` (as `postgres`: `DROP DATABASE publiccare_int01_qa; DROP ROLE publiccare_int01_app;`), which stay in place until then; answer RTM Q2 if scenario (c) should be run (it would need a new run); B-1, B-3 and the ZIP decisions as before.

## 5. Phase 9 – final submission decisions (2026-11-25)

| Item | Status |
|---|---|
| Member names and registration numbers (G-01, B-1 for Members 1–4) | **DONE** – supplied by the owner, mapping to Members 1–4 confirmed; entered in all final documents; register Revision 7 (36 / 38 / 6 / 6). P-M1-3 and P-OW-1 – P-OW-4 stay PENDING |
| Step 8 – ZIP | **AWAITING OWNER APPROVAL of the exact contents.** Z-1, Z-2, Z-3 (applied), Z-4 decided from the owner's preferences; Z-6 resolved; Z-5, Z-7, Z-8 (new: gitignored INT-01 logs) awaiting approval. No ZIP created |
| INT-01 | Unchanged: PARTIALLY EXECUTED; database and role kept until the package is verified |

Record: [phase9-final-submission/PHASE9_SUBMISSION_DECISIONS.md](phase9-final-submission/PHASE9_SUBMISSION_DECISIONS.md). Submission index: [`../SUBMISSION_INDEX.md`](../SUBMISSION_INDEX.md).

## 6. Phase 10 – final package (2026-11-25)

| Item | Status |
|---|---|
| ZIP decisions Z-1 – Z-8 | **APPROVED** by the project owner (Z-4, Z-5, Z-7 and Z-8 were awaiting approval in Phase 9) |
| Package scope | One ZIP holds both the complete application (frontend, backend, tests, migrations V1 – V5, configuration without secrets) and the complete assignment (Sections 3.1 – 3.10, reports, evidence, integration records Phases 3A – 10, INT-01 evidence incl. the 4 `.log` files) |
| Build 1 | Built and verified: 343 files, 4,583,873 bytes, SHA-256 `4f1558ad…cb8baa`. All checks PASS (record beside the ZIP). Replaced because it did not contain the Phase 10 records |
| Step 8 – ZIP (build 2) | Final file list [`phase10-final-package/PACKAGE_FILE_LIST.txt`](phase10-final-package/PACKAGE_FILE_LIST.txt) (347 paths). Size, SHA-256 and verification results are recorded **outside** the package, in `Public care/submission/PublicCare-EC4206-QA-Submission.VERIFICATION.txt`, because a packaged file cannot contain the package hash |
| INT-01 | Unchanged: PARTIALLY EXECUTED. PostgreSQL service running; database and role not dropped (existence not re-queried: no `postgres` password) |
| Git | Nothing committed or pushed; branch and identity unchanged |
| Register | Revision 8: no status change; 36 / 38 / 6 / 6 |

**Still open (not decided in Phase 10):** D-2 – D-5, D-7; RTM Q1, Q2; P-M1-3, P-OW-1 – P-OW-4; planned schedule dates; cleanup of the INT-01 database and role; any commit. Record: [phase10-final-package/PHASE10_COMPLETION_RECORD.md](phase10-final-package/PHASE10_COMPLETION_RECORD.md).

## 7. Phase 11 – Part 01 execution and final completion (2026-11-25)

| Item | Status |
|---|---|
| Part 01 execution | **DONE for 39 cases** (API-01 – API-03, API-05 – API-25, UT-01 – UT-15). Group repository now 41 of 79 executed: 33 PASS, 6 PASS with one part BLOCKED, 1 BLOCKED, 1 PARTIALLY EXECUTED; no FAIL. [Record](phase11-part01-exec/README.md) |
| Sections 3.1–3.2 | **DONE**: Test Plan and RTM updated from evidence; new [Final Report](3.1-3.2_member1/SECTION_3.1-3.2_FINAL_REPORT.md) |
| New defect | PC-QA-02 – documented; **not** logged in GitHub Issues (owner action); not fixed |
| Not executable here | 22 MT and 8 UI cases (need a human tester / UI automation in the group repository); UT-16, UT-17 (Vitest, owner approval) |
| Final package | New ZIP `PublicCare-EC4206-QA-Final-Submission.zip`; record in `phase11-final-package/` and the verification report beside the ZIP |
| Still open | D-2 – D-5, D-7; RTM Q1, Q2; OQ-12; P-M1-3, P-OW-1 – P-OW-4; INT-01 database cleanup; any commit |
