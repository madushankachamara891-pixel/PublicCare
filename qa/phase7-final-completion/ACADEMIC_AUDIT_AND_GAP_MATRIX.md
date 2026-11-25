# Phase 7 – Academic audit of Sections 3.1–3.10 and final gap matrix

| Item | Value |
|---|---|
| Date | 2026-11-25 (Phase 7, Step 7) |
| Authority | `EC4206_Group_Assignment_Task_List (1).pdf` (module folder; re-read in Phase 7). The PDF names LO-1 – LO-4 only as labels on each section; **it does not define them**, and no project record does. This audit therefore uses the PDF's own section-to-LO mapping and does not paraphrase any LO |
| Documents audited (submission candidates) | Part 01 Test Plan and RTM (canonical, after Step 6); PART02 (Member 2); [Member 3 draft](../3.5-3.6_member3/REPORT_3.5-3.6_TDD_BDD_AUTOMATION_CICD.md); [Member 4 integrated revision](../3.7-3.10_member4/REPORT_3.7-3.10_INTEGRATED.md); [3.2 reconciliation](SECTION_3.2_EXECUTION_RECONCILIATION.md); [Evidence index](../evidence/EVIDENCE_INDEX.md); `qa/README.md` |
| Not repeated | Phase 4 recomputed every log total, the Member 2 screenshots E08/E09, the CFG arithmetic and the Cucumber counts. Phase 6 re-hashed all member copies. These results are reused. Phase 7 re-verified only what it used to write new text: all Member 4 metrics (Step 3), the Member 3 failure details (Step 2), all Part 01 links and anchors (Step 6) |

## 1. Task-list coverage (every PDF bullet)

Status: **MET** · **MET (gap disclosed)** – met, with a limitation stated in the text · **PARTLY MET** · **NOT MET**. Origin labels as in the evidence index.

| § | PDF task | LO (PDF) | Where | Evidence and origin | Status | Remaining gap (ID in §4) |
|---|---|---|---|---|---|---|
| 3.1 | Define scope | LO-1 | TP §1–§2 | Analysis of `f565fb4` | MET | – |
| 3.1 | Short test plan: objectives, scope, in/out of scope, **resources, schedule** | LO-1 | TP §7; As Executed Addendum; §7.6, §7.8 actual columns | Records | PARTLY MET | Names in the resources table are PENDING (G-01). Planned dates are TBC (actual dates are recorded) (G-08) |
| 3.1 | Quality model (ISO/IEC 25010) | LO-1 | TP §4–§5 | – | MET | – |
| 3.1 | Verification vs validation | LO-1 | TP §6 | – | MET | – |
| 3.2 | STLC phases mapped | LO-1, LO-2 | TP §8 (+ as-executed column) | – | MET | – |
| 3.2 | Test cases in full format (ID, description, preconditions, steps, expected, actual, status) | LO-1, LO-2 | TP §10.4 (79 cases); [reconciliation](SECTION_3.2_EXECUTION_RECONCILIATION.md) | GROUP: API-04. MEMBER: 15 + 3 cases | MET (gap disclosed) | Format complete for 79/79. Actual result in the group repository for 1/79; member-copy results reconciled for 16 distinct cases; 63 never executed (disclosed) *[Phase 8A, 25 Nov 2026: group repository 2/79 (API-04 PASS; INT-01 PARTIALLY EXECUTED); 17 distinct cases executed somewhere; 62 never executed]* |
| 3.2 | Test environment (browser, OS, test data) | LO-1, LO-2 | TP §11–§12 + as-executed column | Member evidence | MET (gap disclosed) | PostgreSQL version stays TBC until INT-01 runs (G-02) *[Phase 8A, 25 Nov 2026: INT-01 ran; PostgreSQL 18.6 recorded (register ENV-P5)]* |
| 3.3 | EP on 2–3 fields | LO-2 | PART02 §3.3.1 | MEMBER (M2): EP-01 – EP-05 | MET | – |
| 3.3 | BVA on numeric/date fields | LO-2 | PART02 §3.3.2 | MEMBER: BVA-01 – BVA-07 on title **length** | MET (gap disclosed) | No sentence explains why a length counts as a numeric boundary (G-11) |
| 3.3 | Decision table, multiple conditions | LO-2 | PART02 §3.3.3 | MEMBER: DT-01 – DT-04 executed | MET (gap disclosed) | DT-05, DT-06 not executed (disclosed) |
| 3.4 | Cyclomatic complexity | LO-2 | PART02 §3.4.1 | V(G) = 3 and 2, recomputed in Phase 4 | MET | – |
| 3.4 | Statement and branch coverage test cases | LO-2 | PART02 §3.4.2–3.4.4 | MEMBER: JaCoCo 0.8.12, E08/E09 | MET (gap disclosed) | Path B1 not executed. "Baseline" label of E02–E05 not verified (F6-2) |
| 3.5 | Unit tests (JUnit) | LO-2, LO-3 | M3 draft §3 | MEMBER (M3): Red → Red → Green → Refactor logs | MET | Draft not reviewed by Member 3 (G-03) |
| 3.5 | 1–2 BDD scenarios, Gherkin + Cucumber | LO-2, LO-3 | M3 draft §4 | MEMBER: 4 scenarios, Cucumber reports; MEMBER-CI: 4/0 | MET | – |
| 3.6 | Automate a few tests (Selenium or REST Assured) | LO-3 | M3 draft §5 | MEMBER: REST Assured 9; Selenium 6 (local) | MET | – |
| 3.6 | GitHub Actions on push | LO-3 | M3 draft §6; `ci.yml` copy | MEMBER-CI: run #1 on `70d01e0` | MET (gap disclosed) | Active in the Member 3 repository only (G-07) |
| 3.6 | Screenshots of a passing pipeline | LO-3 | `3.5-3.6_member3/evidence/ci/` (3 PNG) | MEMBER-CI, cross-checked in Phase 6 | MET | The run page is private. The screenshots are the visible evidence |
| 3.7 | Basic JMeter load test | LO-2, LO-4 | M4 rev. §3.7.1 | MEMBER (M4): 100 samples, re-verified in Phase 7 | MET | Health endpoint only; no SLA (OQ-4) (disclosed) |
| 3.7 | 1–2 OWASP Top 10 checks | LO-2, LO-4 | M4 rev. §3.7.2 | MEMBER: 2 checks + control (A01) | MET | – |
| 3.7 | (Usability – section title) | LO-2, LO-4 | M4 rev. §3.7.3 | MEMBER: 3 scripted walkthroughs | MET (gap disclosed) | No participants (disclosed) |
| 3.8 | Log bugs in Jira / Bugzilla / GitHub Issues | LO-2, LO-4 | M4 rev. §3.8.1 | GitHub issue #1 (private) | PARTLY MET | Only PC-QA-01 is in a tracker. F-03 is logged in the integration findings table, not in a tracker. The issue page cannot be opened by a marker (G-09, G-10) |
| 3.8 | RCA on 1–2 significant bugs | LO-2, LO-4 | M4 rev. §3.8.1, §3.8.2, §3.8.4 | MEMBER (PC-QA-01); GROUP (F-03) | MET | – |
| 3.8 | Full lifecycle (New → Assigned → Fixed → Verified → Closed) for ≥1 bug | LO-2, LO-4 | M4 rev. §3.8.1 | Member 4's record + fix commit `f78deb1` (verified) | MET (gap disclosed) | The issue states are not independently visible (G-09). PC-QA-01 is still open in the group baseline (G-05) |
| 3.9 | Run SonarQube | LO-4 | M4 rev. §3.9 | MEMBER: scanner log, gate PASSED | MET | No dashboard screenshot. Not required by the PDF (G-12) |
| 3.9 | Coverage %, defect density, code smells, technical debt | LO-4 | M4 rev. §3.9.2–3.9.3 | Re-verified against JSON/CSV in Phase 7 | MET | – |
| 3.9 | 2–3 concrete improvements | LO-4 | M4 rev. §3.9.5 | Tied to issue data and branch coverage | MET | – |
| 3.10 | Agile/DevOps reflection on the testing approach | LO-1, LO-4 | M4 rev. §3.10 (group level) | Drawn from records 3.1–3.9 | MET | Integration-written; no member's personal view (disclosed) |

**Result (27 rows: the 26 PDF bullets plus the "usability" part of the 3.7 title): 25 MET (8 of them with a disclosed gap), 2 PARTLY MET (3.1 resources/schedule; 3.8 tracker logging), 0 NOT MET.**

## 2. LO view (PDF mapping only)

| LO | Sections that the PDF maps to it | Coverage in the submission |
|---|---|---|
| LO-1 | 3.1, 3.2, 3.10 | Plan, quality model, V&V, STLC, 79 full-format cases, environment, group Agile/DevOps reflection. Weakest point: names and planned dates (G-01, G-08) |
| LO-2 | 3.2, 3.3, 3.4, 3.5, 3.7, 3.8 | EP, BVA, decision table, CFG / V(G), statement and branch coverage, TDD, BDD, non-functional checks, RCA |
| LO-3 | 3.5, 3.6 | TDD/BDD with Cucumber, REST Assured, Selenium, GitHub Actions with screenshots |
| LO-4 | 3.7, 3.8, 3.9, 3.10 | JMeter, OWASP, defect lifecycle, SonarQube/JaCoCo metrics with interpretation, improvements, reflection |

## 3. Cross-cutting checks

| Check | Result | Residual issues |
|---|---|---|
| **Completeness** | Every section has a deliverable. 3.5–3.6 now has a report (project-integrated draft) | G-01 (names), G-03 (M3 draft disclosure) |
| **Technical accuracy** | Phase 7 found and corrected: BDD run-1 failure detail (`401`/`403`); API-08 maps to FR-02.5 only; SonarQube scan included test code; "Linux container" not in the evidence; JMeter throughput set by the pacing; three bugs triaged; 434/642 kept. Integration's own arithmetic was checked (16 + 63 = 79; 11 critical issues; 560/944) | None open in the new texts. PART02 not re-audited beyond Phase 4/6 (G-11) |
| **Academic English** | The new texts use plain, formal English: defined terms, active voice, no unsupported superlatives, one idea per sentence where possible | PART02 numbering (§1, §3.3, §3.4, §4, §5 – no §2) (G-11) |
| **Requirement-to-test traceability** | RTM 63 rows ↔ 79 cases (Part 01 checks pass). Member work mapped at FR-xx.y level in the M3 draft and the M4 revision (RMR §1–§2 applied). Reconciliation links each execution to a case | CROSS_REFERENCE §3 still shows Member 4's group-level IDs as recorded history (superseded for submission by the M4 revision) |
| **Correct metrics** | All M4 figures reproduce from raw files. Coverage figures from the two JaCoCo versions are kept apart | – |
| **Genuine evidence** | Every result cites a file. Provenance: Phase 6 (95 copies). No screenshot, log or result was created by integration. The two new texts state they were not written or reviewed by the members | Registration PNGs show form state only (described as such) |
| **Consistent terminology** | Origin labels GROUP / MEMBER / MEMBER-CI / HISTORICAL / NOT EXECUTED / NOT VERIFIED used across the index, reconciliation and new texts | RTM "EXECUTED – PASS" vs TP "PASS" (P2-3) (G-15) |
| **Correct execution status** | `70d01e0` vs `93d11a3` kept apart; Selenium never described as part of the 91 or as run in CI; member results never described as group runs; PC-QA-01 "Closed in M4 copy / Open in group baseline" | INT-01 pending (G-02) *[Phase 8A, 25 Nov 2026: INT-01 recorded per scenario – (a), (b), (d) PASS, (c) BLOCKED; not described as an overall PASS]* |
| **No unsupported claims** | One unsupported claim was found in the new 3.10 draft (that Part 01 foresaw the API-04 ambiguity). It was corrected before this audit closed | – |
| **No contradictions** | Statements made outdated by Phase 7 in integration documents: `qa/README.md` ("formal report missing", "links do not resolve") and the evidence index ("Linux container"). Both annotated in Phase 7 (marked notes, original text kept) | FORMAL_REPORT_MISSING.md stays true for Member 3's own submission (annotated) |
| **Links** | Canonical Part 01: 282/282 resolve; 232 line anchors in range and non-blank. All Phase 7 files: 0 broken | Archived Analysis Report 237, PART02 20, Member 4's original report 14 (G-13, G-11, G-14) |
| **Privacy** | Evidence logs and screenshots contain absolute paths with Windows user-profile names, including names different from the members' (P2-8). Evidence was not edited | G-16 – owner decision before the ZIP |

## 4. Final gap matrix

Severity: **S1** blocks a credible submission · **S2** should be fixed or explicitly accepted before submission · **S3** minor / disclosed limitation.

| ID | Gap | Sections | Severity | Can integration close it? | Who / action | Register refs |
|---|---|---|---|---|---|---|
| G-01 | Names, registration numbers and a contribution statement are missing (Part 01 "Authors: TBC"; PART02 has no byline; M3 name and ID PENDING; M4 ID PENDING) | All | **S1** | **No** – no record holds these values, and inferring them is rejected (REJ-3) | Owner supplies names and IDs for all four members (and the owner/integrator role). Integration then fills the bylines and a contribution table | P-M1-1–3, P-M2-1–3, P-M3-2–3, P-M4-12, P-OW-1–4 | *[Phase 9, 25 Nov 2026: **closed for Members 1–4** – names and registration numbers supplied by the owner and entered (register Revision 7). Still PENDING: Part 01 co-authors (P-M1-3), owner/integrator roles (P-OW-1 – P-OW-4)]*
| G-02 | INT-01 (PostgreSQL) not executed; every result used H2 *[Phase 8A, 25 Nov 2026: **largely closed** – the owner ran INT-01 on PostgreSQL 18.6: (a), (b), (d) PASS; (c) BLOCKED (RTM Q2). Residual, disclosed: the V2-baseline path and data persistence on PostgreSQL are untested; Flyway 11.7.2 does not officially support PostgreSQL 18; the JUnit suite and member tests still used H2]* | 3.2, 3.5–3.6, 3.10 | S2 *[Phase 8A: now S3 (stated)]* | Prepared (script, isolated DB). The run needs the owner's database password | Owner runs `int01-postgresql/run-int01.ps1`. If not run, keep "NOT EXECUTED" (already stated everywhere) | ENV-P3, ENV-P5, ENV-P6 |
| G-03 | The 3.5–3.6 report was written by integration, not by Member 3. No personal reflection | 3.5–3.6 | S2 (integrity) | Disclosed in the document | Owner states this in the contribution statement. Member 3 can still add their own reflection later | P-M3-1, P-M3-10 |
| G-04 | Nothing is committed. `qa/` is untracked; the D-1 fix is uncommitted | All | S2 | No (commit needs approval) | Owner decides D-2 and whether to commit `qa/` before or after the ZIP | P0-5, D-2 |
| G-05 | PC-QA-01 is open in the group baseline (fix `f78deb1` not applied) | 3.8 | S3 (stated) | No (code change) | Owner decides D-3 | D-3, P-M4-3 |
| G-06 | IPv6 loopback behaviour undecided | 3.5, 3.8 | S3 (stated) | No | Owner answers RTM Q1; decides D-4 | D-4, P-OW-Q1 |
| G-07 | CI is not active in the group repository; one run on `70d01e0`; `ui` job never executed | 3.6 | S3 (stated) | No (workflow and code change) | Optional: owner decides D-5. Otherwise the stated wording is sufficient for the PDF items | D-5, P-M3-7 |
| G-08 | Planned schedule dates and the submission deadline are TBC | 3.1 | S2 | No (must not be back-filled, REJ-2) | Owner supplies the planned dates if they exist, or accepts a sentence that no dated plan was made in advance | P-SC-1, P-SC-2 |
| G-09 | Defect lifecycle states and the CI run page are in **private** repositories; a marker cannot open them | 3.6, 3.8 | S2 | No | The owner owns these repositories and can capture screenshots of issue #1 (timeline) and run #1 personally, or grant the marker access. No member review is needed | P-M3-8, P-M4-4 |
| G-10 | Only one bug is logged in a tracker (F-03 is not) | 3.8 | S3 | No (creating an issue is an outward action) | Optional: owner logs F-03 in GitHub Issues and links the D-1 change | P1-17 |
| G-11 | PART02: 20 broken links, no byline, section numbering, no sentence justifying BVA on a length, "baseline" label of E02–E05 not verified | 3.3–3.4 | S2 (links) / S3 | Links: yes, mechanically (verified rewrites in INTEGRATION_LOG §7), **if the owner approves** editing the member report, or via an integrated copy as done for Member 4 | Owner approval for a PART02 link fix (20 rewrites) | P0-6, P1-14, P2-5, F6-2 |
| G-12 | No SonarQube dashboard screenshot | 3.9 | S3 | No (never captured) | Not required by the PDF ("Run SonarQube"). The JSON exports and scanner log are the evidence | P-M4-5 |
| G-13 | Archived Analysis Report: 237 broken links and superseded counts (59 FRs, 74 cases, 73 tests) | 3.1 | S2 if included | – | Recommended: **exclude** it from the ZIP (Phase 4 §8.1). Owner decision D-6 | D-6 |
| G-14 | Member 4's original report: 14 broken links | 3.7–3.10 | S3 | Superseded for submission by the integrated revision | Include as an appendix (marked "original") or exclude | – |
| G-15 | Status vocabulary "EXECUTED – PASS" (RTM) vs "PASS" (TP) | 3.2 | S3 | Yes, if approved | Owner approval (P2-3) | – |
| G-16 | Personal Windows profile paths in evidence logs and screenshots | Evidence | S2 (privacy) | No – evidence must not be edited | Owner decides whether to submit the logs as they are (provenance) or to omit them | P2-8 |
| G-17 | Java 21 run: console log and `00-environment.txt` not archived | 3.2 | S3 | No | Disclosed wherever the run is cited | ENV-J10 |
| G-18 | Not executed and disclosed: DT-05, DT-06, B1; 63 Part 01 cases; combined run; participant usability; DB-backed load test; Selenium in CI; code coverage of M3 tests | Various | S3 | – | Keep the disclosures | UNA-1, UNA-4 |

**Submission blockers:** only **G-01** (names and registration numbers) blocks a credible academic submission. Everything else is either closed, disclosed as a limitation, or an owner decision with a safe default (keep the current wording). *[Phase 9, 25 Nov 2026: G-01 is closed for the member names and IDs, so no S1 gap remains. The remaining step is the owner's approval of the ZIP contents ([Phase 9 record](../phase9-final-submission/PHASE9_SUBMISSION_DECISIONS.md))]*

## 5. Phase 7 corrections made during this audit

| File | Correction |
|---|---|
| `3.7-3.10_member4/REPORT_3.7-3.10_INTEGRATED.md` §3.10.2 point 1 | Removed the unsupported claim that Part 01 foresaw the API-04 ambiguity |
| same, §3.9.3 | SonarQube coverage formula corrected: backend-only result 59.3% (not 44.9%) |
| same, §3.10.1 | Requirement count corrected to 58 FR + 5 NFR groups (63 RTM rows) |
| `qa/README.md`, `evidence/EVIDENCE_INDEX.md` | Phase 7 notes for outdated statements (original text kept) |

## 6. Phase 11 re-check (2026-11-25)

Re-checked against the task list after the Phase 11 execution. Only rows whose evidence changed are listed; all other rows of §1 and §4 are unchanged.

| § / gap | Before | After Phase 11 |
|---|---|---|
| 3.1 resources, schedule | PARTLY MET (planned dates TBC) | **Still PARTLY MET.** Names complete (Phase 9). No planned dates exist; the final report states this and gives the actual milestone dates instead of back-filling a plan (REJ-2) |
| 3.2 test cases with actual result and status | MET (gap disclosed): 2 of 79 executed in the group repository | **MET.** 41 of 79 executed in the group repository with recorded actual results (33 PASS, 6 PASS with one part BLOCKED, 1 BLOCKED, 1 PARTIALLY EXECUTED; no FAIL); 6 in the Member 3 copy; 32 not executed, each with a reason. Summary in [SECTION_3.1-3.2_FINAL_REPORT.md](../3.1-3.2_member1/SECTION_3.1-3.2_FINAL_REPORT.md) |
| 3.2 test environment | MET (gap disclosed) | **MET.** Confirmed vs proposed environments in Test Plan §11.3 |
| 3.8 log bugs in a tracker (G-10) | PARTLY MET – only PC-QA-01 in GitHub Issues | **Still PARTLY MET.** A further defect, PC-QA-02, was found and documented with RCA (Member 4 integrated revision §3.8.2A), but it is not in a tracker: creating a GitHub issue is the owner's action |
| G-18 not executed | 63 Part 01 cases | 32 Part 01 cases (22 MT, 8 UI, UT-16, UT-17) |
| G-02 PostgreSQL | S3 | Unchanged: the 39 Phase 11 cases used H2 |
| New G-19 | – | Phase 11 results were produced by integration and not peer-reviewed (V-3); disclosed in the final report §3.2.8. S3 |

Result (27 rows, unchanged count): **25 MET** (7 with a disclosed gap – was 8; the 3.2 test-case row no longer has an execution gap), **2 PARTLY MET** (3.1 schedule; 3.8 tracker logging), **0 NOT MET**. No S1 gap.
