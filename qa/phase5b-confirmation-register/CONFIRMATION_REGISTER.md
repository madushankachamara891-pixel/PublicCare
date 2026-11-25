# Phase 5B – Confirmation register (Members 1–4 and project owner)

| Item | Value |
|---|---|
| Date | 2026-11-25 |
| Branch / baseline | `qa/final-assignment` at `f565fb4` (= `main` = `origin/main`). Working tree as in Phase 5A: `M AuthDtos.java` (D-1), 2 untracked auth test classes, untracked `qa/` |
| Purpose | One place to collect every confirmation, decision and environment check still needed before Part 01 corrections, bylines, the Member 3 report and the commit can be completed |
| Changes made in Phase 5B | This file (added) and a pointer in [`qa/README.md`](../README.md). Nothing else. No application code, test code, evidence, reference code, stored diff, Part 01 copy or Phase 4/5A file was edited. Nothing committed or pushed |
| Revision 1 (2026-11-25) – Java 21 run | The integrator ran the backend suite on **Java 21** (WSL2 Ubuntu, H2 in-memory): 85 tests, 0 failures. Evidence: [`qa/evidence/java21-run/surefire-reports/`](../evidence/java21-run/surefire-reports/). Updated here: §1 counts, the "Rules" line on execution, D-2 prerequisite (§4), §6.1, §6.2 note on ENV-P6, §9 integrator row. Statements that the integrated code had "never been run on Java 21" were annotated (not deleted) in the Phase 3A, Phase 4 and Phase 5A files listed in §6.1.4. Part 01 copies were **not** edited (D-8 pending; see §6.1.5). This revision ran no test and changed no application code, test code, Maven file or evidence file. Nothing committed or pushed |
| Revision 2 (2026-11-25) – D-8 | The project owner decided **D-8: APPROVED, option CAN-1**. The canonical Part 01 location is `publiccare/qa/3.1-3.2_member1/` (inside the Git repository). Updated here: header, §1 counts, §4 (intro note, D-8 row, recording template), §5 (CAN-1, CAN-2 note, new CAN-3, new §5.1 list of unblocked corrections), §6.1.5. Also recorded in the canonical RTM "Owner decisions" table (D-8 row added, nothing else in Part 01 changed) and noted in `qa/README.md`, `qa/INTEGRATION_LOG.md`, `qa/CROSS_REFERENCE.md` and the Phase 5A README, `TEST_PLAN_CORRECTIONS.md` and `PART01_AS_EXECUTED_ADDENDUM.md`. The outer copy in `Public care/qa/` was **not** modified, deleted or overwritten. The corrections D-8 unblocks are listed in §5.1 and **not applied**. No other decision (D-2, D-3, D-4, D-5) and no question (RTM Q1, Q2, Q3) was decided. No test was run; no application code, test code or evidence file changed. Nothing committed or pushed |
| Revision 3 (2026-11-25) – UB-1 to UB-17 applied | The project owner authorised a controlled documentation-correction phase for UB-1 – UB-17 (§5.1). Each correction was checked against the evidence before it was applied (§5.2). **Applied to the canonical copy only** (`3.1-3.2_member1/`): Test Plan (As Executed Addendum and marked additions) and RTM (scope note at the top; status note on the D-8 row). Names, registration numbers, planned dates and the submission deadline stay PENDING / TBC. ENV-J10 stays **PENDING**. The canonical Analysis Report, the outer copy `Public care/qa/`, application code, test code, build files and evidence were **not** changed. UB-18 (outside the authorised range) and UB-19 (link repair; D-6, D-7 pending) were **not** applied. No decision was made for D-2, D-3, D-4, D-5 or RTM Q1, Q2, Q3. No status in this register changed, so the §1 totals are unchanged (recounted). Status notes were also added to `qa/README.md`, `qa/INTEGRATION_LOG.md`, the Phase 5A README, `TEST_PLAN_CORRECTIONS.md` and `PART01_AS_EXECUTED_ADDENDUM.md`. No test was run. Nothing committed or pushed |
| Revision 4 (2026-11-25) – Phase 6 | Member repositories re-inspected read-only from bare clones ([Phase 6 record](../phase6-member-repo-verification/README.md)). **UNA-5 UNAVAILABLE → CONFIRMED**: 95 Member 2–4 copies re-hashed against the source commits, 92 identical and 3 differing only by logged integration edits; the 5 stored diffs regenerate identically. Factual notes added to P-M3-8 and P-M4-4 (the repositories are private); both stay PENDING. No other status changed. No test run; no evidence, code or Part 01 file changed. Nothing committed or pushed |
| Revision 5 (2026-11-25) – Phase 7 | The project owner instructed integration to finish the assignment from verified evidence **without further member reviews**. Applied: **UB-18** (RTM Location row; the same correction in the Test Plan Location row) and the **Part 01 link correction** (UB-19 for the RTM and Test Plan only: 258 links `../publiccare/` → `../../`; anchor strategy = relative links to the working tree). The Analysis Report was not edited (D-6 open; recommended for exclusion). Member 3 report drafted and Member 4 report revised as **project-integrated** documents. Items that needed a member's agreement to an evidence-supported wording (P-M3-6, P-M3-9, P-M4-1, P-M4-3, P-M4-6, P-M4-7, P-M4-8, P-M4-9, P-M4-11) are applied there as owner-authorised integration text. **Their status here is unchanged (PENDING)**, because no member confirmed them. Items needing a member's own facts (names, IDs, dates, machine, screenshots) stay PENDING and are shown as gaps. D-2 – D-7 not decided. No status in this register changed, so the §1 totals are unchanged. Record: [FINAL_COMPLETION_TRACKER.md](../FINAL_COMPLETION_TRACKER.md). Nothing committed or pushed |
| Revision 6 (2026-11-25) – Phase 8A, INT-01 | The project owner ran INT-01 (`run-int01.ps1`) on a dedicated local PostgreSQL 18.6 database (`publiccare_int01_qa`, role `publiccare_int01_app`). The integrator checked all 16 output files ([INT-01 record](../phase7-final-completion/int01-postgresql/README.md#phase-8a--verified-results-2026-11-25)): scenarios (a), (b), (d) **PASS**; (c) **BLOCKED** (RTM Q2). Status changes: **P-OW-Q3** PENDING → CONFIRMED (dedicated local database, approved by the owner in Phase 7 Step 5 and used by the owner's own run); **ENV-P3** PENDING → CONFIRMED; **ENV-P5** PENDING → CONFIRMED (18.6, now a tested version); **ENV-P6** UNAVAILABLE → CONFIRMED (PostgreSQL evidence now exists, limited to INT-01 a/b/d). **ENV-P4** (RTM Q2) stays PENDING. §6.2 requirements 1–3 and 5 annotated. No other status changed. No application code, test, dependency or Flyway configuration changed; evidence not edited; database and role kept. Nothing committed or pushed |
| Revision 7 (2026-11-25) – Phase 9, member details | The project owner supplied the names and registration numbers of the four members in an exact form and confirmed which person is Member 1, 2, 3 and 4: **Member 1 – Madusanka D.M.C, EG/2023/5720; Member 2 – Dharmasiri S.O.R, EG/2023/5559; Member 3 – Kumaranayaka K.I.S, EG/2023/5697; Member 4 – Madushan S.C, EG/2023/5716.** Initials are not expanded. Status changes: **P-M1-1, P-M1-2, P-M2-1, P-M2-2, P-M2-3, P-M3-2, P-M3-3, P-M4-12** PENDING → CONFIRMED (owner-supplied; no member was asked). Still PENDING: P-M1-3 (co-authors), P-OW-1 – P-OW-4 (owner and integrator identity; not inferred from the member list), and all other items. Applied to: Test Plan (§7.6 allocation table, cross-cutting duties, addendum pending table, header Author row), RTM (Author row), PART02 (byline), Member 3 draft §0, Member 4 integrated revision §0.1, `qa/README.md`. Member 4's original report is not changed; its byline initials ("S. A.") differ from the owner-supplied "S.C" (recorded, not corrected). Record: [Phase 9](../phase9-final-submission/PHASE9_SUBMISSION_DECISIONS.md). Nothing committed or pushed |
| Revision 8 (2026-11-25) – Phase 10, final package | The project owner approved ZIP decisions **Z-1 – Z-8** (Phase 9 record §3; Z-4, Z-5, Z-7, Z-8 were awaiting approval there). Z-1 settles D-6 **for this package only** (Analysis Report included, labelled ARCHIVED). The Z-decisions are not rows of this register, so **no row changed status**; totals stay **36 / 38 / 6 / 6** (recounted). D-2 – D-5, D-7, RTM Q1, Q2 (ENV-P4), P-M1-3 and P-OW-1 – P-OW-4 stay PENDING. The §9 sign-off sheet stays blank: no person signed. Database `publiccare_int01_qa` and role `publiccare_int01_app` not dropped. Record: [Phase 10](../phase10-final-package/PHASE10_COMPLETION_RECORD.md). Nothing committed or pushed |
| Sources | [Phase 4 audit](../phase4-integration-audit/PHASE4_INTEGRATION_AUDIT.md) (P4); [Phase 5A README](../phase5a-p0-corrections/README.md) (5A); [AUTHOR_AND_DATE_CONFIRMATIONS.md](../phase5a-p0-corrections/AUTHOR_AND_DATE_CONFIRMATIONS.md) (A&D); [TEST_PLAN_CORRECTIONS.md](../phase5a-p0-corrections/TEST_PLAN_CORRECTIONS.md) (TPC); [BASELINE_DEVIATIONS.md](../phase5a-p0-corrections/BASELINE_DEVIATIONS.md) (BD); [REQUIREMENT_MAPPING_REVIEW.md](../phase5a-p0-corrections/REQUIREMENT_MAPPING_REVIEW.md) (RMR); [FORMAL_REPORT_SKELETON_3.5-3.6.md](../3.5-3.6_member3/FORMAL_REPORT_SKELETON_3.5-3.6.md) (SKEL) |

**Rules used in this register**

- **No personal detail was filled in.** Name forms are listed only where they already appear in project records (A&D §1). No name was inferred from GitHub handles, OS user names in log paths or e-mail addresses. **No registration number (student ID) appears in any project record**, so every ID field is blank. *[Revision 7: the project owner supplied the four members' names and registration numbers on 25 Nov 2026 (Phase 9); they are now recorded in §3]*
- **No execution result was produced or assumed.** No test was run while this register was written or revised. Environment facts in §6 are tool-version observations made on 2026-11-25, not test results – except ENV-J6, which records the Java 21 test run executed separately by the integrator, taken from its archived Surefire reports (Revision 1).
- Decision meanings (§4) are copied from the Phase 4 audit, §7 "Stage 0 – Owner decisions" table. Nothing is added to their meaning.

## Status vocabulary

| Status | Meaning |
|---|---|
| **CONFIRMED** | Established by a project record or by verified evidence. No confirmation from a person is outstanding |
| **PENDING** | Needs a confirmation, a value or a decision from the named person. Nothing can be applied until it is given |
| **REJECTED** | Ruled out by a recorded owner decision or by a documented integration rule. Listed so it is not reused |
| **UNAVAILABLE** | Does not exist in any record or on the integration machine, and cannot be produced in this phase. It needs an outside action (installation, a run, a member's resubmission) |

## 1. Summary

| Section | Confirmed | Pending | Rejected | Unavailable |
|---|---:|---:|---:|---:|
| §2 Confirmed records (allocation, dates, bylines, D-1) | 10 | – | – | – |
| §3.1, §3.2, §3.5, §3.6 Names, registration numbers, people, schedule | 5 | 10 | – | – |
| §3.3 Member 3 report and confirmations | 2 | 9 | – | – |
| §3.4 Member 4 technical confirmations | 1 | 11 | – | – |
| §4 Owner decisions D-2, D-3, D-4, D-5, D-8 and RTM Q1, Q3 | 2 | 5 | – | – |
| §5 Canonical Part 01 location | 3 | – | – | – |
| §6 Java 21 and PostgreSQL environment | 12 | 3 | – | 1 |
| §7 Rejected items | – | – | 6 | – |
| §8 Unavailable evidence | 1 | – | – | 5 |
| **Total** | **36** | **38** | **6** | **6** |

The counts are the number of rows with that status in each section. P-SC-3 repeats P-M3-4 so that the schedule list is complete. The recording template in §4 and the action list in §5.1 are not counted. *[Revision 1: §6 was 5 / 6 / – / 4 and the total 16 / 52 / 6 / 10. Changes: ENV-J5, ENV-J6 UNAVAILABLE → CONFIRMED; ENV-J8, ENV-J9 PENDING → CONFIRMED; ENV-J10 added as PENDING. No other row changed status.]* *[Revision 2: §4 was – / 7 / – / –, §5 was 1 / 1 / – / – and the total 20 / 51 / 6 / 8. Changes: D-8 PENDING → CONFIRMED (APPROVED, CAN-1); CAN-1 PENDING → CONFIRMED; CAN-3 added as CONFIRMED. No other row changed status.]* *[Revision 3: no row changed status. Recounted mechanically from the tables on 25 Nov 2026: 23 / 49 / 6 / 8, per section as above. Applying UB-1 – UB-17 confirms none of the PENDING items it depends on; they stay PENDING in Part 01 and here.]* *[Revision 4: §8 was – / – / – / 6 and the total 23 / 49 / 6 / 8. Change: UNA-5 UNAVAILABLE → CONFIRMED (Phase 6 re-hash). No other row changed status.]* *[Revision 6: §4 was 1 / 6 / – / –, §6 was 9 / 5 / – / 2 and the total 24 / 49 / 6 / 7. Changes: P-OW-Q3, ENV-P3, ENV-P5 PENDING → CONFIRMED; ENV-P6 UNAVAILABLE → CONFIRMED (INT-01 run, Phase 8A). §6 recounted mechanically from the ENV- rows. No other row changed status.]* *[Revision 7: §3.1–§3.6 was – / 15 / – / –, §3.3 – / 11 / – / –, §3.4 – / 12 / – / – and the total 28 / 46 / 6 / 6. Changes: P-M1-1, P-M1-2, P-M2-1, P-M2-2, P-M2-3, P-M3-2, P-M3-3, P-M4-12 PENDING → CONFIRMED (owner-supplied member details, Phase 9). No other row changed status.]*

## 2. Confirmed items

| ID | Item | Value | Source |
|---|---|---|---|
| C-01 | Section allocation | Member 1: 3.1–3.2. Member 2: 3.3–3.4. Member 3: 3.5–3.6. Member 4: 3.7–3.10 | `qa/README.md` "Sections and responsibilities"; TPC §1 |
| C-02 | Part 01 document date | Draft 23 Nov 2026; revised 23 Nov 2026; integration revisions 25 Nov 2026 (Phases 3A, 3B) | TP line 15 |
| C-03 | Member 2 report / execution date | 24 November 2026; E10 log 2026-11-24T11:10:53+05:30 | PART02 line 5; A&D D-b |
| C-04 | Member 3 run dates | Local 2026-11-24; CI run #1 job finished 2026-11-24T04:24:04Z | A&D D-c; M3 `evidence/ci/README.md` |
| C-05 | Member 4 report date | 24 November 2026 | `MEMBER4_QA_REPORT.md:3`; A&D D-e |
| C-06 | API-04 execution (integration, Phase 3A) | 2026-11-25, logs 07:23–07:26 +05:30 | A&D D-f; `phase3a-api04-correction/` |
| C-07 | Actual schedule dates | 23 Nov 2026 analysis and Part 01; 24 Nov 2026 member execution (M2, M3, M4); 25 Nov 2026 integration (Phases 1, 3A, 3B, 4, 5A, 5B) | TPC §3 |
| C-08 | Member 4 byline | "Member 4 · S. A. Chathura Madushan · 24 November 2026" is present (the student ID is not) | `MEMBER4_QA_REPORT.md:3`; A&D A-10 |
| C-09 | Defect tracking and quality-metrics duty | Held by Member 4 (Sections 3.8–3.9 are in Member 4's report) | TPC §1, cross-cutting duties |
| C-10 | Owner decision D-1 | Approved, Option A (FR-01.3 / API-04: e-mail trimmed before validation). Implemented in the working tree, **not committed** | RTM "Owner decisions recorded after Part 01" (RTM lines 46-50); `phase3a-api04-correction/README.md` |

## 3. Pending confirmations by person

### 3.1 Member 1 (Sections 3.1–3.2)

| ID | Item | Field(s) affected | Current value | Needed | Status |
|---|---|---|---|---|---|
| P-M1-1 | Full name | TP:15 "Authors"; RTM has no author field (A&D A-1, A-6); `qa/README.md` | **No name in any record** | Full name as it should appear | **CONFIRMED** – Madusanka D.M.C *[Rev. 7, Phase 9, 25 Nov 2026: supplied by the project owner]* |
| P-M1-2 | Registration number (student ID) | Contribution table (A&D A-12) | None | ID | **CONFIRMED** – EG/2023/5720 *[Rev. 7, Phase 9, 25 Nov 2026: supplied by the project owner]* |
| P-M1-3 | Co-authors of Part 01, if any | TP:15 | "Authors: TBC" | Names (or "none") | **PENDING** |

### 3.2 Member 2 (Sections 3.3–3.4)

| ID | Item | Field(s) affected | Current value | Needed | Status |
|---|---|---|---|---|---|
| P-M2-1 | Name spelling | PART02 byline (A&D A-8); contribution table | "Oshan Ravishka" (Git commit author only) | Confirm or correct spelling | **CONFIRMED** – Dharmasiri S.O.R (form for the submission) *[Rev. 7, Phase 9, 25 Nov 2026: supplied by the project owner]* |
| P-M2-2 | Registration number | PART02 byline; contribution table | None | ID | **CONFIRMED** – EG/2023/5559 *[Rev. 7, Phase 9, 25 Nov 2026: supplied by the project owner]* |
| P-M2-3 | Byline | PART02 lines 1-7 (no author line) | – | Approve "Member 2 · <name> · <student ID>" | **CONFIRMED** – "Member 2: Dharmasiri S.O.R · EG/2023/5559" added to PART02 *[Rev. 7, Phase 9, 25 Nov 2026: supplied by the project owner]* |
| P-M2-4 | Optional technical items | DT-05, DT-06, path B1 (P4 P1-13); BVA on a numeric field (P4 P1-14) | Documented as NOT EXECUTED; BVA on a string length | Execute in the M2 copy, or confirm they stay as documented gaps | **PENDING** (optional) |

### 3.3 Member 3 (Sections 3.5–3.6) – formal report requirements and missing confirmations

**Report status:** no formal report exists (`FORMAL_REPORT_MISSING.md`; P4 P0-1). The skeleton (SKEL) gives the structure and verified facts. The narrative must come from Member 3; integration must not write it.

**Formal report requirements** (from P4 P0-1 and SKEL §0–§7). The report must:

| # | Requirement | Source |
|---|---|---|
| R-1 | Name the source repository and branch (`PublicCare-QA-Member3-Ishani-`, `member3-tdd-bdd-ci`) and the commit reported on (`93d11a3`) | SKEL §0 |
| R-2 | State the application baseline: `f565fb4` **plus** the unapplied `AdminSetupRunner.java` change and the unapplied `pom.xml` test dependencies; results pre-date D-1 | SKEL §0; BD §1 |
| R-3 | 3.5 TDD: UT-14 / FR-06.9; Red (compile error) → Red (6 run, 1 failure) → Green (6/0) → Refactor (78/0), with the four logs; state that `[::1]` acceptance is still an open question (RTM Q1, F-02) and that D-4 is pending | SKEL §2 |
| R-4 | 3.5 BDD: the 4 Cucumber scenarios, including the Cucumber 7.34.9 failure and the CSRF `403` run-1 failure; scenario 4 mapped to FR-02.8 / FR-06.6 unless Member 3 gives another supported mapping | SKEL §3; RMR §1 |
| R-5 | 3.6 REST Assured: 9 tests, with `emailWithSurroundingSpacesIsRejectedByValidation` labelled **superseded by D-1**. The `400` must be reported as observed in the M3 copy on 2026-11-24, not as current or expected behaviour | SKEL §4.1 |
| R-6 | 3.6 Selenium: 6 tests, including the deliberate fault check (6 run, 2 failures as intended); local only, Chrome 157 headless; not part of the 91 | SKEL §4.2 |
| R-7 | 3.6 CI: run #1 on commit `70d01e0` (not `93d11a3`), 91/0 on Temurin 21.0.13; `ui` job configured, **never executed**; workflow not active in the group repository | SKEL §5 |
| R-8 | Results table per run, **not added** to Member 2 (84), Member 4 (72) or the group repository (85) | SKEL §6 |
| R-9 | All limitations in SKEL §7 (Java 25 locally; H2 only, no PostgreSQL, INT-01 not done; no JaCoCo; inferred failure causes; `npm ci` vulnerabilities not investigated) | SKEL §7 |
| R-10 | Front matter: title, author, registration number, report date (not the integration date), machine used | SKEL §0 |

**Missing confirmations and inputs:**

| ID | Item | Status |
|---|---|---|
| P-M3-1 | Narrative for SKEL §2–§5 (the report itself) | **PENDING** |
| P-M3-2 | Exact name spelling: "Ishani-Kumaranayaka" (Git handle) vs "Ishani Kumaranayaka" (integrator's reading). Uncertain | **CONFIRMED** – Kumaranayaka K.I.S *[Rev. 7, Phase 9, 25 Nov 2026: supplied by the project owner]* |
| P-M3-3 | Registration number | **CONFIRMED** – EG/2023/5697 *[Rev. 7, Phase 9, 25 Nov 2026: supplied by the project owner]* |
| P-M3-4 | Report date (evidence dates are 2026-11-24; A&D D-d) | **PENDING** |
| P-M3-5 | Machine used for the local runs. The user-profile name in the evidence paths differs from the commit author (P4 P2-8; SKEL §0) | **PENDING** |
| P-M3-6 | Accept or reject the scenario 4 mapping FR-02.8 / FR-06.6 (RMR §1) | **PENDING** |
| P-M3-7 | CI on the final commit including the `ui` job with screenshots, **or** agreement to state "UI job configured, not executed" and "Java 21 result is for `70d01e0`" wherever cited (P4 P1-10, P1-11) | **PENDING** |
| P-M3-8 | Whether the CI run URL is viewable by the marker (P4 R-4) *[Revision 4 fact: the repository is **private** (API 404 without authentication), so the URL is not viewable without collaborator access. Whether to grant access or rely on the screenshots is still open]* | **PENDING** |
| P-M3-9 | Confirm the superseded `400` test is described as historical | **PENDING** |
| P-M3-10 | TDD / BDD / CI points for the group 3.10 case study (P4 P1-9) | **PENDING** |
| P-M3-11 | Final title of the 3.5–3.6 report | **PENDING** |

### 3.4 Member 4 (Sections 3.7–3.10) – outstanding technical confirmations

| ID | Item | Current state | Needed from Member 4 | Source | Status |
|---|---|---|---|---|---|
| P-M4-1 | Backend line coverage | Corrected by integration in Phase 5A to **434 / 642 = 67.60%** (from Member 4's own `jacoco.csv`), marked *[Corrected: Phase 5A]* | Accept the correction, or explain the 433 / 641 figure | P4 P1-1; `MEMBER4_QA_REPORT.md:67` | **PENDING** |
| P-M4-2 | PC-QA-01 record: Environment field | Missing (brief requires it) | Confirm or correct the proposed value: M4 copy at `b97bd96`, Linux, Chromium 153.0.8010.0, Vite dev server | P4 P1-5 | **PENDING** |
| P-M4-3 | PC-QA-01 record: Status | "Closed" applies to the M4 copy only; the fix `f78deb1` is not in the group repository | Agree the wording "Closed in M4 copy; **Open** in group baseline `f565fb4` pending owner decision D-3" | P4 P1-5, T-10; BD §1 | **PENDING** |
| P-M4-4 | Lifecycle evidence for GitHub issue #1 | External link only | Screenshots or export of New → Assigned → Fixed → Verified → Closed and the linked commit; and whether the issue URL is viewable by the marker *[Revision 4 fact: the repository is **private**, so the issue URL is not viewable without access. The integrator could not export the timeline (GitHub CLI not installed; Phase 6 F6-4)]* | P4 P1-6, R-4 | **PENDING** |
| P-M4-5 | SonarQube screenshots | None (JSON and scanner log only) | Overview, issues and quality-gate screenshots | P4 P1-7 | **PENDING** |
| P-M4-6 | Triage of the 3 SonarQube bugs | Not triaged | Real defect or false positive for: java:S6218 (report package, line 152); javascript:S2871 (`AdminIssues.jsx:48`); javascript:S1082 (`SiteHeader.jsx:117`) | P4 P1-8 | **PENDING** |
| P-M4-7 | Requirement IDs for the checks | Group-level IDs ("FR-01", "FR-02", "FR-06") | Confirm RMR §2 rows 1–5. Row 2 needs a choice: **FR-02.5** (RMR) or **FR-02.8** (P4 P2-2) | RMR §2 | **PENDING** |
| P-M4-8 | SonarQube "this baseline" wording | Refers to the M4 handover + `f78deb1`, not `f565fb4` | Confirm the baseline wording for 3.9 | P4 C-10 | **PENDING** |
| P-M4-9 | Security record fields | No explicit risk/severity or preconditions per check; error rate not stated | Confirm "Risk: High if failed; observed: none" and "Error rate 0.00%" (from `jmeter-console.txt`) | P4 P2-7 | **PENDING** |
| P-M4-10 | 3.7 environment details | Not stated in the report; Node/npm and Playwright versions **not recorded** in any evidence | Confirm Azul Zulu 21.0.13.1, Linux container, `ActiveProcessorCount=2`; supply Node/npm and Playwright versions if known | P4 P2-10; TPC §2, §4 | **PENDING** |
| P-M4-11 | Group 3.10 case study | Current text covers Member 4's work only | Agree to a group-level rewrite drawing on 3.1–3.9 and D-1 | P4 P1-9 | **PENDING** |
| P-M4-12 | Name form and registration number | "S. A. Chathura Madushan" (byline), "Chathura Madushan" (Git), "Chathura124" (assignee handle) | Preferred form for the group report; student ID | A&D §1, A-10 | **CONFIRMED** – Madushan S.C, EG/2023/5716 *[Rev. 7, Phase 9, 25 Nov 2026: supplied by the project owner]*. The original byline's "S. A." differs; Member 4's original report is not changed |

### 3.5 Project owner and integrator – people

| ID | Item | Current value | Needed | Source | Status |
|---|---|---|---|---|---|
| P-OW-1 | Project owner name | Only the repository owner handle is recorded | Name | A&D A-5; TP:391 | **PENDING** |
| P-OW-2 | Whether the owner is also one of Members 1–4 | Not recorded | Yes (which member) / No | A&D §1 | **PENDING** |
| P-OW-3 | Integrator / test coordinator name (Phases 1–5B) | Not recorded | Name | A&D A-3; TP:384 | **PENDING** |
| P-OW-4 | Tester for API-04 "Execution details" row | Not named | Name of the person who ran API-04 on 2026-11-25 | A&D A-11 | **PENDING** |

### 3.6 Schedule details

| ID | Item | Current value | Needed | Status |
|---|---|---|---|---|
| P-SC-1 | Planned start/end dates, phases 2–10 (TP lines 413-421) | "TBC". No planned dates in any record | Dates from the group's own records, or keep "TBC". **Must not be back-filled from actual dates** (see REJ-2) | **PENDING** |
| P-SC-2 | Final submission deadline (TP line 422) | "TBC". Not in any project record | Date from the module brief or LMS | **PENDING** |
| P-SC-3 | Member 3 report date | – | See P-M3-4 | **PENDING** |
| P-SC-4 | Final group report date | Report not compiled | Date when compiled | **PENDING** |

## 4. Owner decisions

The **Decision** and **Affects** columns are copied from the Phase 4 audit, §7, "Stage 0 – Owner decisions". No decision below has been recorded in the RTM "Owner decisions" table or anywhere else in `qa/`. Phase 5A §7 step 4 says each decision is to be recorded in that RTM table before anything is applied. *[Revision 2, 2026-11-25: D-8 has now been decided by the project owner and recorded in the RTM "Owner decisions" table of the canonical copy (`3.1-3.2_member1/PART01_REQUIREMENTS_AND_RTM.md`). D-2, D-3, D-4 and D-5 remain undecided and are not recorded anywhere.]*

| ID | Decision (Phase 4 wording) | Affects (Phase 4) | Prerequisite / related item in the records | Consequence while pending (from the records) | Status |
|---|---|---|---|---|---|
| D-2 | Commit the D-1 change as is (after a Java 21 run) | P0-5, P1-18 | Java 21 run of `f565fb4` + D-1 (§6.1): **executed 2026-11-25, 85 tests, 0 failures, H2 only** (ENV-J6) *[Revision 1; was "currently unavailable"]*. The run is a prerequisite only; it does not make the decision | The D-1 change stays uncommitted in the working tree (5A §6) | **PENDING** |
| D-3 | Apply or reject the PC-QA-01 fix (`f78deb1` diff) | 3.8 status (P1-5), T-10 | P-M4-3 (status wording) | PC-QA-01 stays **open** in the group baseline (BD §1) | **PENDING** |
| D-4 | Apply or reject the `AdminSetupRunner` IPv6 fix (answers RTM Q1) | F-02, UT-14 status | RTM Q1 (P-OW-Q1 below) | `AdminSetupRunnerLoopbackTest` must be left out of any combined run, because it does not compile without the fix (5A §7 step 5d; BD §2) | **PENDING** |
| D-5 | Bring member test code into the group repository (M2 tests; M3 tests + `pom.xml` diff + workflow) | Enables a combined run and live CI; otherwise totals stay per copy | Java 21 environment (§6.1); exclusion of the superseded M3 test (REJ-1) | No combined run; test totals stay per copy; CI not active in the group repository (P4 §7 Stage 2; P4 R-13) | **PENDING** |
| D-8 | Canonical Part 01 copy | P2-9 | Proposed location in §5 | The As Executed addendum and Test Plan corrections cannot be applied; otherwise they must be made in both copies and their hashes compared (TPC header; 5A §6). *[Revision 2: no longer pending. The corrections are unblocked as far as D-8 is concerned, but not applied; see §5.1]* | **CONFIRMED / APPROVED** – option **CAN-1**, canonical location `publiccare/qa/3.1-3.2_member1/`. Decided by the project owner, 25 Nov 2026; recorded in the canonical RTM (Revision 2). *[Was **PENDING** until Revision 2]* |

**Questions the owner must answer with these decisions** (definitions from RTM "Open questions"):

| ID | Question | Needed for | Status |
|---|---|---|---|
| P-OW-Q1 | RTM Q1: who confirms the intended behaviour of the open clarification items, including IPv6 loopback (project owner or lecturer)? | D-4; UT-14 expected result | **PENDING** |
| P-OW-Q3 | RTM Q3: which environment will INT-01 run on? Testcontainers needs Docker and a new test dependency (an application change needing owner approval); a dedicated local PostgreSQL database needs no code change | §6.2; INT-01 | **CONFIRMED** – dedicated local PostgreSQL database (no code change; Testcontainers not used). Approved by the project owner in Phase 7 Step 5 and used in the owner's INT-01 run on 25 Nov 2026 ([record](../phase7-final-completion/int01-postgresql/README.md)). *[Revision 6; was **PENDING**]* |

**Recording template** (one row per decision, to be copied into the RTM "Owner decisions" table when made):

| ID | Decision (Approve / Reject / Defer) | Decided by (name) | Date | Conditions | Recorded in RTM (Y/N) |
|---|---|---|---|---|---|
| D-2 | | | | | |
| D-3 | | | | | |
| D-4 | | | | | |
| D-5 | | | | | |
| D-8 | Approve – option CAN-1 | Project owner (name not recorded; see P-OW-1) | 25 Nov 2026 | Canonical location `publiccare/qa/3.1-3.2_member1/` (inner, Git-tracked). Rationale: single source of truth, to prevent divergence between duplicate Part 01 copies. Outer copy `Public care/qa/` kept as a non-canonical reference copy: not deleted, not overwritten, not modified (CAN-3) | Y (Revision 2) |

Out of scope here: D-6 (Analysis Report inclusion) and D-7 (link-anchor strategy) are also pending in the Phase 4 audit but were not requested for this register.

## 5. Proposed canonical Part 01 document location (for D-8)

*[Revision 2, 2026-11-25: the proposal below was approved by the project owner as decision D-8, option CAN-1. The original proposal text is kept as written.]*

| ID | Item | Value | Status |
|---|---|---|---|
| CAN-1 | **Proposed canonical location** | `publiccare/qa/3.1-3.2_member1/` (inside the Git repository, on `qa/final-assignment`). The outer copy in `Public care/qa/` (outside Git) is retired after a final sync and **not deleted without approval**. *[Revision 2: **approved** (D-8). Canonical location: `publiccare/qa/3.1-3.2_member1/`. Rationale (project owner): select the inner Git-tracked copy as the single source of truth to prevent future divergence between duplicate Part 01 copies. No "final sync" was needed: the copies were byte-identical when the decision was made (CAN-2). The outer copy is handled as in CAN-3. Git state on 25 Nov 2026: the folder is inside the repository's working tree and not ignored, but `qa/` is still **untracked** (`?? qa/`, nothing committed); it becomes Git-tracked only when committed, which needs separate owner approval (P0-5)]* | **CONFIRMED / APPROVED** (D-8, project owner, 25 Nov 2026). *[Was **PENDING** (D-8) until Revision 2]*. Proposal from P4 P2-9 |
| CAN-2 | Current state of the two copies | Byte-identical on 2026-11-25 (Phase 5B re-check, SHA-256): RTM `33bc16d6…193f`, Test Plan `1cc432dc…630e`, Analysis Report `01c67fab…317d` – the same values as Phase 4 and Phase 5A. *[Revision 2: this row records the state **before** D-8 was recorded and is kept as the audit record. Re-checked immediately before the Revision 2 edit: still byte-identical. After Revision 2 the canonical RTM differs from the outer copy by exactly one added line (the D-8 row in "Owner decisions"); new canonical RTM SHA-256 `491f4cf4e4cb56580bd2b63308306bde62e0e8ae06cb933f4908c06fcd7208e0`. The canonical Test Plan and Analysis Report are unchanged and still identical to the outer copy]* | **CONFIRMED** |
| CAN-3 | *[Revision 2, new]* Status of the outer duplicate copy | `Public care/qa/` (outside the Git repository) – `PART01_REQUIREMENTS_AND_RTM.md`, `PART01_TEST_PLAN_AND_STLC.md`, `PROJECT_ANALYSIS_REPORT.md` (plus nothing else in that folder). **Non-canonical reference copy.** Kept as it was when D-8 was decided: not deleted, not overwritten, not modified, and no longer synchronised with the canonical copy. Any change to it needs separate owner approval. SHA-256 at the D-8 decision and after Revision 2 (unchanged): RTM `33bc16d6c7d9ca8b7b723d5c1ccaf50260ff8ddadf8b155eb60c2bc0a3a2193f`, Test Plan `1cc432dc52b9ac9571fd779de7456081fa283e673e14c0bc0d41e6d8dc8a630e`, Analysis Report `01c67fab7cf7b36d81a1c2055059a0669f995198e5c078a4e70022ffbb50317d`. It is the original Member 1 source of the Phase 1 copy (INTEGRATION_LOG §2) and, from its own location, resolves all its relative links (P4 Appendix E) | **CONFIRMED** |

Why this location (from the records): it is the only copy under version control; both copies have been edited in parallel so far (CROSS_REFERENCE.md:116, "both synchronized copies"), which is the drift risk P4 T-12 describes. Links in the copies currently assume the outer location (P4 Appendix E), so link repair (P0-6) depends on this decision. *[Revision 2: decided. Parallel editing of both copies ends here; future Part 01 edits go to the canonical copy only.]*

### 5.1 Part 01 corrections unblocked by D-8 – not applied (Revision 2)

*[Revision 3, 2026-11-25: UB-1 – UB-17 have now been applied; UB-18 and UB-19 have not. The table below is kept as the Revision 2 record; application status, final locations and deviations are in §5.2.]*

D-8 removes only the "which copy" blocker. **None of the items below has been applied.** Each needs separate approval before it is applied, and several also need confirmations that are still **PENDING** elsewhere in this register. They are to be applied in the canonical copy only (`publiccare/qa/3.1-3.2_member1/`), keeping every original planned value and adding the as-executed value beside it (TPC rule). Their technical content is exactly as prepared in the source files; nothing was changed in Revision 2. Line numbers refer to the canonical Test Plan (TP, unchanged since Phase 5A) and RTM (one line added at 51 in Revision 2, so RTM lines after 50 are +1 compared with Phase 4/5A references).

| # | Correction (source) | Target in canonical Part 01 | Other prerequisites still open |
|---|---|---|---|
| UB-1 | **As Executed addendum**, sections A–E ([PART01_AS_EXECUTED_ADDENDUM.md](../phase5a-p0-corrections/PART01_AS_EXECUTED_ADDENDUM.md)), including its two Phase 5B rev. 1 Java 21 notes (§B "Not executed" table, §E) | Add to TP (as an addendum next to the plan, not replacing it); reference from RTM | Author/date confirmations (addendum line 5): P-M1-1 – P-M1-3, P-OW-1, P-OW-3, P-OW-4 |
| UB-2 | Scoping sentence for the TP header (TPC §3 "Other execution-status statements"; addendum §D) | TP lines 3-4 | None besides approval |
| UB-3 | Same scoping sentence for the RTM header (TPC §3; addendum §D) | RTM line 4 | None besides approval |
| UB-4 | Work allocation as executed: member/section table, cross-cutting duties, workstream mapping; existing tables relabelled "Original proposal, 23 Nov 2026 – not adopted" (TPC §1) | TP §7.6, lines 365-391 | Names and IDs marked **CONFIRM**: P-M1-1, P-M1-2, P-M2-1, P-M2-2, P-M3-2, P-M3-3, P-M4-12, P-OW-1, P-OW-3. Rows without those values can be applied only with "not recorded" wording as prepared |
| UB-5 | Tools as executed table (TPC §2) | Below TP line 400 | Playwright version not recorded (stays "not recorded"); P-M4-10 |
| UB-6 | Line-level additions: SonarQube used (TP line 141), see §11.1 (TP line 400), GitHub Issues (TP line 542) (TPC §2) | TP lines 141, 400, 542 | P-M4-8 (SonarQube baseline wording) for line 141 |
| UB-7 | Schedule: "Actual date(s)" and "Actual status (25 Nov 2026)" columns; planned columns stay TBC (TPC §3) | TP §7.8, lines 406-422 | P-SC-1, P-SC-2 stay TBC; must **not** be back-filled (REJ-2) |
| UB-8 | §5.3 scoping note "Verified quality result" (TPC §3) | TP line 237 | None besides approval |
| UB-9 | "As executed" column for §7.3 rows 3.3–3.10 (TPC §3) | TP lines 307-314 | None besides approval |
| UB-10 | §7.9 deliverables as executed (TPC §3) | TP lines 432-435 | None besides approval |
| UB-11 | §8 phase 4 as executed; phase 5 unchanged (TPC §3) | TP lines 480-481 | None besides approval |
| UB-12 | Environment as executed: TP lines 1896, 1899 (stays TBC – PostgreSQL never used), 1908, 1909, 1910, 1911, 1912, 1913, 1914 (stays TBC), 1924 (ENV-05), 1950 (TD-09) (TPC §4) | TP §11.1 and related rows | P-M4-10 (M4 environment); ENV-P5 (PostgreSQL version stays TBC) |
| UB-13 | **Java 21 execution status** – line 78: "not run on Java 21 or PostgreSQL" → add run on Java 21 (WSL2 Ubuntu, 2026-11-25) with D-1: 85/0, H2; not run on PostgreSQL (§6.1.5) | TP line 78 | ENV-J10 (console log not archived) must be noted wherever the run is cited |
| UB-14 | Java 21 execution status – same correction as UB-13 (§6.1.5) | TP line 590 | As UB-13 |
| UB-15 | Java 21 execution status – same correction as UB-13 (§6.1.5) | TP line 1991 (OQ-9) | As UB-13 |
| UB-16 | Java 21 environment – add to the TPC row 1909 value: integration Java 21 run, Ubuntu OpenJDK 21.0.13.1, Maven Wrapper 3.9.16 (§6.1.5) | TP line 1909 (together with UB-12) | Maven Wrapper version rests on the integrator's report (ENV-J10) |
| UB-17 | Java 21 environment – add to the TPC row 1911 value: integration Java 21 run, Linux (WSL2, Ubuntu 26.04.1) (§6.1.5) | TP line 1911 (together with UB-12) | None besides approval |
| UB-18 | RTM "About this document" → "Location" still reads `qa/PART01_REQUIREMENTS_AND_RTM.md` (the outer layout). Proposed: state the canonical location `publiccare/qa/3.1-3.2_member1/PART01_REQUIREMENTS_AND_RTM.md` *[new in Revision 2; identified while recording D-8]* | RTM line 13 | None besides approval |
| UB-19 | Link repair (P0-6): the Part 01 links still assume the outer location (`../publiccare/` prefix; P4 Appendix E: RTM 252, Analysis Report 237, TP 6 broken). D-8 fixes the base location for the repair | All three canonical Part 01 files | D-6 (Analysis Report inclusion) and D-7 (link-anchor strategy) are still open; Phase 5A deferred link repair until all text edits are done |

Not unblocked by D-8 (unchanged): TP line 15 "Authors: TBC" (P-M1-1, P-M1-3); REJ-4 (Member 3's Part 01 execution-status diff stays rejected); status vocabulary alignment "EXECUTED – PASS" vs "PASS" (TPC §3, P2, not proposed for application); anything depending on D-2, D-3, D-4, D-5 or RTM Q1, Q2, Q3.

### 5.2 Application of UB-1 – UB-17 (Revision 3, 2026-11-25)

Authorised by the project owner (name: P-OW-1) as a controlled documentation-correction phase for UB-1 – UB-17. Applied in the canonical copy only. Every original planned value was kept; additions are marked *[As executed: Phase 5A, applied 25 Nov 2026]* or *[Java 21 run …]*. Line numbers below are **after** Revision 3: Test Plan (TP) 2016 → 2178 lines, RTM 776 → 777 lines. Evidence checked before applying: Java 21 Surefire XML (14 classes, sum 85 / 0 / 0 / 0; `java.version` 21.0.13.1; `os.name` Linux, WSL2 kernel; only `jdbc:h2:mem:` URLs); `maven-wrapper.properties` (3.9.16); member evidence for every value in TPC §2–§4 (tool versions, JDKs, Node/npm, OS, ports, browsers, viewports, JMeter 5 × 20 = 100 samples, CI run #1 on `70d01e0`, M4 `jacoco.csv` re-sum 434 / 642 lines and 126 / 302 branches).

| # | Status | Location (after Rev. 3) | Deviation from the prepared text, and why | Still pending |
|---|---|---|---|---|
| UB-1 | **APPLIED** (open fields PENDING) | TP "As Executed Addendum", lines 2072–2178; linked from TP line 5 and RTM line 5 | Java 21 result written into AE-A, AE-B, AE-D, AE-E; the Phase 5A "not executed on Java 21" text is kept and marked superseded **for Java 21 only**. Two NOT EXECUTED rows added to AE-B from §6.1.3 (Selenium / browser tests on the group baseline; combined run). M4 JDK: "Azul Zulu" replaced by the evidence wording "Java 21.0.13.1 (Azul Systems, Inc.)", because "Zulu" appears in no evidence file (`sonar-run.txt`). M4 line coverage marked as an integration re-sum awaiting P-M4-1. Added a table of open confirmations. AE-C subsections are bold labels, not `####` headings, so the count of 79 `####` case headings stays valid | P-M1-1, P-M1-3, P-OW-3, P-OW-4 (shown as PENDING); ENV-J10; P-M4-1, P-M4-10 |
| UB-2 | **APPLIED** | TP line 5 | As prepared, plus an anchor link to the addendum | – |
| UB-3 | **APPLIED** | RTM line 5 | As prepared, with a link to the TP addendum (this is the RTM reference required by UB-1) | – |
| UB-4 | **APPLIED** (names and IDs PENDING) | TP §7.6: relabels on lines 372 and 381; new tables, lines 394–421 | Name and registration-number cells show PENDING with the register ID. The unconfirmed name forms in TPC §1 were **not** copied into Part 01 (§3 keeps them for confirmation). The project owner's repository handle was not used as a name (REJ-3). The original tables and their TBC cells are unchanged | P-M1-1, P-M1-2, P-M1-3, P-M2-1, P-M2-2, P-M3-2, P-M3-3, P-M4-12, P-OW-1, P-OW-2, P-OW-3 |
| UB-5 | **APPLIED** | TP §7.6 "Tools as executed", lines 432–449 | As prepared. Selenium scoped to "Member 3 copy only (local; no CI result)". Playwright source given as `REPRODUCE.md` "Browser checks". Note added that member-only tools are not in the group `pom.xml` (BD §1; D-5 pending) | Playwright version not recorded; P-M4-10 |
| UB-6 | **APPLIED** | TP lines 142, 430, 594 | Line 142 (SonarQube): baseline "Member 4 copy + `f78deb1`" taken from Member 4's own report (`MEMBER4_QA_REPORT.md` line 4); P-M4-8 named as still pending for the 3.9 wording | P-M4-8 |
| UB-7 | **APPLIED** (planned dates TBC) | TP §7.8, lines 459–474 | Two columns added; planned columns unchanged and TBC (not back-filled, REJ-2). Integration row updated from "In progress (Phase 5A)" to the state on 25 Nov 2026, including the Java 27 and Java 21 backend runs. Row 6 adds "the other 78 not executed" | P-SC-1, P-SC-2 |
| UB-8 | **APPLIED** | TP line 239 | As prepared. (TPC cited line 237; the cell was on line 238 before Rev. 3) | – |
| UB-9 | **APPLIED** | TP §7.3, lines 302–315 | New "As executed" column; rows 3.1–3.2 "–". DT-05 / DT-06 listed under 3.3 and path B1 under 3.4 (PART02 lines 111–112, 176). Row 3.8 says "still open in the group baseline (owner decision D-3 pending)" – D-3 not decided | – |
| UB-10 | **APPLIED** | TP lines 484–486 | As prepared; "Test summary report" unchanged | – |
| UB-11 | **APPLIED** | TP line 533 | As prepared; phase 5 unchanged. (TPC cited lines 480–481; phase 4 was on line 481) | – |
| UB-12 | **APPLIED** (M4 values await P-M4-10) | TP §11.1 scope note line 1946, table lines 1948–1969; ENV-05 line 1979; TD-09 line 2004 | Added an "As executed" column ("–" for rows not in TPC §4) and a scope note under the §11.1 intro sentence, which is kept. Database and deployment rows stay TBC. M4 OS given as "Linux 6.18.57 amd64" without "container" (not in evidence). Devices: "Playwright viewports … No physical device testing recorded" instead of "emulated … No physical devices". ENV-05: backend and frontend jobs ran; the `ui` job did not (M3 `ci/README.md`). (TPC cited ENV-05 as line 1924; it was line 1925) | P-M4-10; ENV-P5 |
| UB-13 | **APPLIED** | TP line 79 | Phase 3A "not run on Java 21" kept as the record; Java 21 annotation added | ENV-J10 noted in text |
| UB-14 | **APPLIED** | TP line 642 | As UB-13 | ENV-J10 noted in text |
| UB-15 | **APPLIED** | TP line 2045 (OQ-9) | As UB-13. A full stop was added after the original last sentence of the cell | ENV-J10 noted in text |
| UB-16 | **APPLIED** | TP §11.1 JDK / Maven row, line 1963 | Ubuntu OpenJDK 21.0.13.1 from the Surefire XML. Maven Wrapper 3.9.16 from `maven-wrapper.properties`; the Maven version **used in the run** marked as resting on the integrator's report (ENV-J10 **PENDING**) | ENV-J10 |
| UB-17 | **APPLIED** | TP §11.1 OS row, line 1965 | Kernel string `6.18.33.2-microsoft-standard-WSL2` added from the Surefire XML | – |
| UB-18 | **NOT APPLIED** | – | Outside the authorised range UB-1 – UB-17 | Separate approval |
| UB-19 | **NOT APPLIED** | – | Link repair waits for D-6 and D-7 | D-6, D-7 |

Also changed in the canonical copy (bookkeeping only): TP "Document status" (line 16) has a revision entry inserted before "Authors: TBC", which stays TBC; the RTM D-8 row (line 52) has a status note saying these corrections are applied. New links: TP → `#as-executed-addendum` (2), `../phase5a-p0-corrections/PART01_AS_EXECUTED_ADDENDUM.md`, `../phase5b-confirmation-register/CONFIRMATION_REGISTER.md`; RTM → `PART01_TEST_PLAN_AND_STLC.md#as-executed-addendum`. All resolve; the anchor matches exactly one heading. Existing links were not touched (UB-19).

SHA-256 after Revision 3: RTM `1a057973bb829358d7b3d82b951301775272aee00565bac2543416a196597e45`; Test Plan `8148e6acf91a86ad2be60adb4fe6ec083b6fff4e7b504fe0bf43a87e4d3cb4f7`; Analysis Report `01c67fab7cf7b36d81a1c2055059a0669f995198e5c078a4e70022ffbb50317d` (unchanged). Line endings kept: Test Plan CRLF, RTM LF. The outer copy still has the CAN-3 hashes.

**Noticed while applying, not edited** (no UB item covers them; for later approval):

- TP line 17 "Location" still reads `qa/PART01_TEST_PLAN_AND_STLC.md` (the same issue as UB-18 for the RTM).
- TP OQ-1, OQ-2, OQ-3 and OQ-5 are partly answered by the as-executed data but are still marked as open questions. §7.10 risk "Team members, allocation and dates not yet agreed" is unchanged.
- RTM line 4 still cites only the Phase 3A Java 27 run; no UB item adds the Java 21 run to the RTM.

## 6. Environment verification requirements

### 6.1 Java 21

The project targets Java 21 (`backend/pom.xml:21`, `<java.version>21</java.version>`). The integration working tree (`f565fb4` + D-1) was first run on Java 27 (Phase 3A, Windows 11, 85/0, H2). *[Revision 1:]* It has now also been run on **Java 21** (2026-11-25, WSL2 Ubuntu, 85 tests, 0 failures, H2 in-memory) – see ENV-J6 and §6.1.1. **Java 21 is CONFIRMED for this integrated backend test run only.** It does not cover PostgreSQL (§6.2, still NOT VERIFIED), Selenium/browser tests, the frontend, or any member test code that is not in the group repository (§6.1.3).

| ID | Item | Value / observation (2026-11-25) | Status |
|---|---|---|---|
| ENV-J1 | Target version | Java 21 (`pom.xml:21`) | **CONFIRMED** |
| ENV-J2 | Maven Wrapper | 3.9.16 (`backend/.mvn/wrapper/maven-wrapper.properties`) | **CONFIRMED** |
| ENV-J3 | `backend/mvnw` line endings | LF in index and working tree (`git ls-files --eol`), so the wrapper can run from a Linux shell | **CONFIRMED** |
| ENV-J4 | JDK 21 on the Windows integration machine | `java -version` → Oracle Java 27 (build 27+35-2325). Only `C:\Program Files\Java\latest` found. *[Revision 1: not re-checked. Not needed for ENV-J6, which ran in WSL]* | **UNAVAILABLE** (Windows only) |
| ENV-J5 | JDK in WSL (Ubuntu 26.04.1 LTS) | Earlier on 2026-11-25: `java` and `javac` not found. *[Revision 1:]* **Now present:** OpenJDK 21.0.13.1, vendor `Ubuntu`, runtime `21.0.13.1+2-1-26.04.1-Ubuntu`, `java.home=/usr/lib/jvm/java-21-openjdk-amd64` (Surefire XML properties) | **CONFIRMED** |
| ENV-J6 | Java 21 run of `f565fb4` + D-1 | *[Revision 1; was "Never executed"]* **Executed 2026-11-25** by the integrator in WSL2 Ubuntu: `./mvnw -B clean test` → **BUILD SUCCESS; 14 test classes, 85 tests, 0 failures, 0 errors, 0 skipped.** Database: **H2 in-memory only**. Details and evidence basis: §6.1.1 | **CONFIRMED** (this backend run only; H2, not PostgreSQL) |
| ENV-J7 | Who installs JDK 21, and where (Windows or WSL) | *[Revision 1:]* **Where** is answered by the run: WSL2 Ubuntu, Ubuntu's OpenJDK 21 package (not the Temurin 21 suggested in Phase 5A §7 step 1); Java 27 on Windows was not involved. **Who** installed it and ran the suite is not recorded (the integrator's name is P-OW-3) | **PENDING** (owner / integrator – name only) |

#### 6.1.1 Evidence for ENV-J6

| Fact | Value | Basis |
|---|---|---|
| Java version | `21.0.13.1` | `java.version` property in all 14 `TEST-*.xml` files – independent of the integrator's report |
| JVM | OpenJDK 64-Bit Server VM, vendor `Ubuntu`, runtime `21.0.13.1+2-1-26.04.1-Ubuntu` | XML properties `java.vm.name`, `java.vendor`, `java.runtime.version` |
| OS | Linux, kernel `6.18.33.2-microsoft-standard-WSL2` (WSL2, Ubuntu 26.04.1) | XML `os.name`, `os.version`; Ubuntu release from the runtime string and ENV-J5. Recorded as Linux (WSL), **not** Windows 11 |
| Code run | This repository through the WSL mount (`user.dir=/mnt/d/…/Public care/publiccare/backend`); HEAD `f565fb4`. The 14 classes include `AuthDtosTest` and `AuthEmailNormalizationApiTest`, the two untracked D-1 test classes, so the run used the working tree with D-1 | XML `user.dir` and class list; `git rev-parse` |
| Totals | 14 classes, **85 tests, 0 failures, 0 errors, 0 skipped** (72 existing + 13 D-1 tests) | Sum of the `<testsuite>` attributes in the 14 XML files |
| Command, Maven, build result | `./mvnw -B clean test`, Maven Wrapper 3.9.16, `BUILD SUCCESS` | Reported by the integrator from the run. **Not in the archived files** (Surefire XML records neither the Maven version nor the build result) – see ENV-J10 |
| Database | **H2 in-memory.** Reports show `jdbc:h2:mem:auth` and `jdbc:h2:mem:report`, database version `2.3.232` (H2). No PostgreSQL connection appears in any report; the PostgreSQL JDBC driver appears **only on the classpath** | XML `system-out` |
| Archived files | [`qa/evidence/java21-run/surefire-reports/`](../evidence/java21-run/surefire-reports/): **28 files** = 14 `TEST-*.xml` + 14 `*.txt` (one pair per class) | Directory listing |
| Not Java 21 evidence | [`qa/evidence/java21-run/previous-surefire-reports/`](../evidence/java21-run/previous-surefire-reports/) (29 files: 14 XML, 14 TXT, 1 `.dumpstream`) records `java.version=27`, Oracle, Windows 11; the `.dumpstream` is dated 2026-11-25T07:26:12, matching the Phase 3A Java 27 run. **Must not be cited as Java 21 evidence** | XML properties; file header |

#### 6.1.2 Requirements for the Java 21 run to count as verified

From 5A §7 steps 1–3:

1. A JDK 21 is installed without removing Java 27; `JAVA_HOME` and `PATH` point to it in the shell used.
2. `00-environment.txt` records `java -version`, `./mvnw -v`, `node -v`, `npm -v`, `git rev-parse HEAD`, `git status --short`. `./mvnw -v` must report `Java version: 21…` and Maven 3.9.16; otherwise stop.
3. `./mvnw -B clean test` on the working tree as it is, with the full log and `target/surefire-reports/` stored in a new evidence folder (for example `qa/phase5b-java21-run/`). Failed runs are kept.
4. Only the Surefire total in that log may be reported. The expected 85 (72 + 13) is an expectation, **not a result**.
5. The OS and JDK distribution are recorded as run. A WSL run is recorded as Linux (WSL, Ubuntu 26.04.1), not Windows 11.

*[Revision 1]* Check of the 2026-11-25 run against these requirements:

| # | Result | Note |
|---|---|---|
| 1 | Met | JDK 21 in WSL. Java 27 on Windows was not involved |
| 2 | **Not met** | No `00-environment.txt` in the evidence folder. Java version and OS are nevertheless recorded independently in the Surefire XML (§6.1.1). See ENV-J10 |
| 3 | Partly met | `./mvnw -B clean test` on the working tree as it is; Surefire reports archived in `qa/evidence/java21-run/surefire-reports/` (the folder name in step 3 was an example). Full console log not archived – ENV-J10 |
| 4 | Met | 85 is now a **result**: the Surefire XML total, not the earlier expectation |
| 5 | Met | Recorded as Linux (WSL2, Ubuntu 26.04.1), Ubuntu OpenJDK 21.0.13.1 |

| ID | Item | Value / observation | Status |
|---|---|---|---|
| ENV-J8 | JDK 21 vendor and exact version used | *[Revision 1]* Ubuntu OpenJDK **21.0.13.1** (`21.0.13.1+2-1-26.04.1-Ubuntu`), from the Surefire XML | **CONFIRMED** |
| ENV-J9 | Java 21 run log and Surefire reports archived | *[Revision 1]* Surefire reports archived: 28 files in `qa/evidence/java21-run/surefire-reports/`. The console log is not – see ENV-J10 | **CONFIRMED** (Surefire reports) |
| ENV-J10 | *[Revision 1, new]* Full Maven console log and `00-environment.txt` (`java -version`, `./mvnw -v`, `git rev-parse HEAD`, `git status --short`) for the Java 21 run | Not in the evidence folder. `BUILD SUCCESS` and Maven Wrapper 3.9.16 rest on the integrator's report of the run. Integrator: archive the console output if it is still available, otherwise keep this note wherever the run is cited | **PENDING** (integrator) |

#### 6.1.3 What ENV-J6 does not verify

- **PostgreSQL: NOT VERIFIED.** The Java 21 run used H2 in-memory only. §6.2 and ENV-P6 are unchanged.
- **Selenium / browser / UI tests: not run.** They are not in the group repository; UNA-4 is unchanged.
- **Frontend:** not built or tested in this run.
- **Combined run of all members' tests: not run.** UNA-1 is unchanged (needs D-3, D-4, D-5). Test totals from member copies must still not be added (REJ-5).
- Member 3's Java 21 CI result still applies to commit `70d01e0` only (P-M3-7).
- **D-1 is still uncommitted.** The run satisfies the prerequisite of D-2; the decision itself is still **PENDING**.

#### 6.1.4 Outdated statements annotated in Revision 1

Each statement was kept and followed by a dated *[Phase 5B rev. 1 …]* note, so the earlier audit record stays readable.

| File | Statement | Why outdated |
|---|---|---|
| [`phase3a-api04-correction/README.md`](../phase3a-api04-correction/README.md) "Limitations" | "Not run on Java 21." | ENV-J6 |
| [`phase4-integration-audit/PHASE4_INTEGRATION_AUDIT.md`](../phase4-integration-audit/PHASE4_INTEGRATION_AUDIT.md) §2 "Missing"; T-7; P1-18; R-6; Appendix D "Group" | "only Java 27"; "has never been run on Java 21"; "only run on Java 27"; "runs on 25/27"; "Java 21 run of the integrated state" listed as not executed | ENV-J6 |
| [`phase5a-p0-corrections/BASELINE_DEVIATIONS.md`](../phase5a-p0-corrections/BASELINE_DEVIATIONS.md) §3 | "tested only on Java 27, H2" | ENV-J6 |
| [`phase5a-p0-corrections/PART01_AS_EXECUTED_ADDENDUM.md`](../phase5a-p0-corrections/PART01_AS_EXECUTED_ADDENDUM.md) "Not executed" table; §E | Java 21 backend suite "NOT EXECUTED"; "Nothing has been run on Java 21 … for the code now in the integration working tree" | ENV-J6. The PostgreSQL parts of both statements are **unchanged and still true** |
| [`phase5a-p0-corrections/README.md`](../phase5a-p0-corrections/README.md) §6 blockers | Java 21 run blocked: "No JDK 21 on the integration machine" | ENV-J5, ENV-J6 |

Not changed, because the new evidence does not contradict them: historical records of the Phase 3A Java 27 run (`INTEGRATION_LOG.md`, `CROSS_REFERENCE.md` §4, addendum API-04 row); statements about Member 3's own Java 21 result (SKEL, M3 evidence READMEs); "a combined Java 21 run is a next step" (addendum line 7; 5A §7 steps 5–6); all PostgreSQL statements.

#### 6.1.5 Part 01 statements now outdated – not edited (D-8 pending)

*[Revision 2, 2026-11-25: D-8 is decided (APPROVED, CAN-1). The corrections below are unblocked for the canonical copy `publiccare/qa/3.1-3.2_member1/` only, and are listed as UB-13 – UB-17 in §5.1. They have **not** been applied; they wait for separate approval. The outer copy will not receive them.]* *[Revision 3, 2026-11-25: applied to the canonical Test Plan (lines 79, 642, 1963, 1965, 2045 after Rev. 3), with ENV-J10 noted wherever the run is cited; see §5.2. The line numbers in the table below are those before Rev. 3. The outer copy was not changed.]*

Part 01 copies may not be edited until D-8 decides the canonical copy (TPC header; 5A §6). Both copies (`publiccare/qa/3.1-3.2_member1/` and `Public care/qa/`) were left byte-identical. When D-8 is decided, these Test Plan lines need the Java 21 result added:

| TP line | Current text (excerpt) | Correction needed |
|---|---|---|
| 78 | "not run on Java 21 or PostgreSQL" | Run on Java 21 (WSL2 Ubuntu, 2026-11-25) with D-1: 85/0, H2. Not run on PostgreSQL |
| 590 | "Not run on Java 21 or PostgreSQL" | Same as line 78 |
| 1909 | "Distribution and Maven version TBC" | Add to the TPC row 1909 value: integration Java 21 run – Ubuntu OpenJDK 21.0.13.1, Maven Wrapper 3.9.16 |
| 1911 | OS "TBC" | Add to the TPC row 1911 value: integration Java 21 run – Linux (WSL2, Ubuntu 26.04.1) |
| 1991 | "Not run here on Java 21 or PostgreSQL" | Same as line 78 |

### 6.2 PostgreSQL

Every executed test used H2 in `MODE=PostgreSQL` (P4 T-9). *[Revision 1: the Java 21 run (ENV-J6) also used H2 in-memory.]* The application's default datasource is PostgreSQL: `application.yml` line 10, `${DB_URL:jdbc:postgresql://localhost:5432/publiccare_db}`, with credentials from environment variables. Tests override it with H2 URLs (for example `AuthEmailNormalizationApiTest.java:39`, `ReportDeletionJpaTest.java:22`).

| ID | Item | Value / observation (2026-11-25) | Status |
|---|---|---|---|
| ENV-P1 | PostgreSQL on the integration machine | PostgreSQL **18.6** installed (`C:\Program Files\PostgreSQL\18`); Windows service `postgresql-x64-18` **RUNNING**. Not used by any test so far | **CONFIRMED** (installed only) |
| ENV-P2 | Docker on the integration machine | Docker 29.8.1 present. Relevant only to the Testcontainers option, which needs owner approval (RTM Q3) | **CONFIRMED** (installed only) |
| ENV-P3 | Test environment choice for INT-01 | RTM Q3 open. *[Revision 6: answered – dedicated local database `publiccare_int01_qa` on the installed PostgreSQL 18.6, dedicated role `publiccare_int01_app` (P-OW-Q3)]* | **CONFIRMED** *[Revision 6; was **PENDING**]* |
| ENV-P4 | INT-01 scenario (c) database content | RTM Q2 open: whether some databases had V1 + V2 tables before Flyway. *[Revision 6: still open; INT-01 scenario (c) was not run (`05-scenario-c-not-run.txt`) and is **BLOCKED**]* | **PENDING** (owner) |
| ENV-P5 | PostgreSQL version for TP line 1899 | "TBC" in TP. ENV-P1 is the installed version, but it may be recorded as the test version **only after** a run on it. *[Revision 6: the run exists – server `PostgreSQL 18.6 on x86_64-windows, compiled by msvc-19.44.35229, 64-bit`, `server_version` 18.6, UTF8 (`run-20261125-125857/00-environment.txt`; Flyway and Hibernate also report 18.6)]* | **CONFIRMED** – 18.6 *[Revision 6; was **PENDING**]* |
| ENV-P6 | Any PostgreSQL execution evidence (INT-01 or other) | None exists for any member or for the integration tree. *[Revision 1: the Java 21 run (ENV-J6) used H2 in-memory and does not change this. PostgreSQL integration testing remains **NOT VERIFIED**]* *[Revision 6: INT-01 run by the owner on 25 Nov 2026, GROUP origin (`f565fb4` + D-1, jar `dd1b8b18…0d9ce0`, JetBrains Runtime 21.0.8, Windows 11): (a) fresh database – V1–V5 applied, **PASS**; (b) restart – no migration, **PASS**; (d) schema ownership – **PASS** (effective `ddl-auto` not read at run time); (c) **BLOCKED** (RTM Q2). Evidence: [`run-20261125-125857/`](../phase7-final-completion/int01-postgresql/run-20261125-125857/). Still **NOT VERIFIED** on PostgreSQL: the V2 baseline path, any data read/write through the API or JPA, all member tests]* | **CONFIRMED** (INT-01 a/b/d only) *[Revision 6; was **UNAVAILABLE**]* |

**Requirements for a PostgreSQL run to count as verified:**

1. RTM Q3 answered. Using the installed PostgreSQL with a dedicated database needs no code change. Testcontainers needs a new test dependency and therefore explicit owner approval.
2. The database is disposable and contains no real user data. Credentials are supplied through environment variables (`DB_URL`, `DB_USERNAME`, password variable) and **must not appear** in any stored log or document.
3. The PostgreSQL server version and the Flyway migration output are recorded in the evidence.
4. INT-01 scenario (c) waits for RTM Q2.
5. Until such a run exists, every report keeps the limitation "H2 only, no PostgreSQL".

*[Revision 6, Phase 8A:]* Requirement 1 met (P-OW-Q3). Requirement 2 met: new empty database and role; credentials passed through `DB_URL`, `DB_USERNAME`, `DB_PASSWORD`; script credential scan 0 occurrences, and an independent pattern search of all 16 files found no credential value. Requirement 3 met: server version and Flyway output are in `00-environment.txt` and the `02a`/`03a` logs. Requirement 4: scenario (c) still waits for RTM Q2 (BLOCKED). Requirement 5: the limitation now reads "**all suite and member tests used H2; on PostgreSQL only INT-01 scenarios (a), (b), (d) were run**".

## 7. Rejected items

No owner or member has rejected any item in this register. The rows below are ruled out by a **recorded owner decision** or by a **documented integration rule**, and must not be reused.

| ID | Item | Rejected by | Source |
|---|---|---|---|
| REJ-1 | `400` as the expected response for a padded duplicate e-mail; Member 3's `emailWithSurroundingSpacesIsRejectedByValidation` as current behaviour | Owner decision **D-1, Option A** (recorded) | RTM Owner decisions; SKEL §4.1 |
| REJ-2 | Back-filling planned schedule dates from actual dates | Integration rule | A&D D-g; TPC §3 |
| REJ-3 | Inferring names from GitHub handles, OS user names in log paths or e-mail addresses | Integration rule | A&D rule |
| REJ-4 | Applying `part01-execution-status-edits_2c1e597..93d11a3.diff` to Part 01 (older Test Plan; API-04 part superseded) | Integration rule; use the As Executed addendum instead | P4 P1-15; BD §1 |
| REJ-5 | Adding test totals across member copies (72, 84, 91, 6, 72, 85) or claiming a combined total | Integration rule | P4 T-5, R-5; PART01_AS_EXECUTED_ADDENDUM §E |
| REJ-6 | FR-08.5 as the mapping for BDD scenario 4 | Not supported by the evidence (no delete step). Final wording awaits P-M3-6 | RMR §1 |

## 8. Unavailable evidence (outside the environment items in §6)

| ID | Item | Reason | Source |
|---|---|---|---|
| UNA-1 | Combined run of all members' tests | Needs D-3, D-4, D-5 and a Java 21 environment | P4 Appendix D; 5A §6 |
| UNA-2 | Member 2 Surefire XML for the 79- and 84-test runs | Screenshots only (disclosed) | P4 §2.2 |
| UNA-3 | Member 3 CI artifact ZIP | Summary only | P4 §2.3 |
| UNA-4 | CI result for the Selenium `ui` job and CI on `93d11a3` | Never executed; depends on P-M3-7 | P4 P1-10, P1-11 |
| UNA-5 | Re-hash of member copies against GitHub source blobs | *[Revision 4: **CONFIRMED** – done in Phase 6 from bare clones: 92 of 95 blob-identical, 3 differ only by logged integration edits; per-file record in [`MEMBER_REPO_PROVENANCE_MANIFEST.tsv`](../phase6-member-repo-verification/MEMBER_REPO_PROVENANCE_MANIFEST.tsv). Was: "Member repositories are not cloned locally", UNAVAILABLE]* | P4 P1-19, Appendix F; Phase 6 §3 |
| UNA-6 | Student IDs in any project record | None recorded; must come from each member (P-M1-2, P-M2-2, P-M3-3, P-M4-12) | A&D rule |

## 9. Sign-off sheet

To be completed by each person. Blank fields are intentional.

| Role | Name (as confirmed) | Registration number | Items confirmed (IDs) | Date | Signature / message reference |
|---|---|---|---|---|---|
| Member 1 | | | P-M1-1 – P-M1-3 | | |
| Member 2 | | | P-M2-1 – P-M2-4 | | |
| Member 3 | | | P-M3-1 – P-M3-11 | | |
| Member 4 | | | P-M4-1 – P-M4-12 | | |
| Project owner | | | P-OW-1 – P-OW-4, P-OW-Q1, P-OW-Q3, D-2, D-3, D-4, D-5, D-8, ENV-J7, ENV-P3, ENV-P4 | | |
| Integrator / coordinator | | | ENV-J8, ENV-J9, ENV-J10, ENV-P5 | | |
