# Phase 5A – P0 corrections and member integration preparation

| Item | Value |
|---|---|
| Date | 2026-11-25 |
| Branch / HEAD | `qa/final-assignment` at `f565fb4` (= `main` = `origin/main`); no stash |
| Git state before and after | `M AuthDtos.java` (Phase 3A, D-1), `?? AuthDtosTest.java`, `?? AuthEmailNormalizationApiTest.java`, `?? qa/`. **Unchanged by Phase 5A** |
| Commits / pushes | **None.** `main` not touched |
| Application or test code changed | **None.** SHA-256 of `AuthDtos.java` and the auth test classes identical before and after |
| Evidence changed | **None.** No log, screenshot, Surefire/Cucumber report, JaCoCo/Sonar/JMeter file, reference-code file or stored diff changed (manifest comparison, §8) |
| Part 01 documents | **Not edited** (both copies). Outer originals in `Public care/qa/` still match their Phase 4 SHA-256 |
| Link repair | **Not started** (deferred to the next phase as instructed) |

## 1. Files changed

**Modified (5)** – every change is marked *[Corrected: Phase 5A]* or *[Phase 5A …]* in the file, and the previous value is kept in the marker:

| File | Change | Task |
|---|---|---|
| `3.7-3.10_member4/MEMBER4_QA_REPORT.md` (line 67) | Backend line coverage 67.55% / 433 of 641 → **67.60% / 434 of 642** | 4 |
| `CROSS_REFERENCE.md` | Phase 5A header note; §3 BDD scenario 4 FR-08.5 → FR-02.8 / FR-06.6; §5 M4 coverage 434 / 642 and "Consistent?" wording; §8 U-8 | 4, 5 |
| `3.5-3.6_member3/FORMAL_REPORT_MISSING.md` | Appended integration note pointing to the skeleton; status still NOT SUBMITTED | 2 |
| `README.md` | Phase 4 and 5A notes; G-1 pointer; integration document list | index |
| `INTEGRATION_LOG.md` | Phase 4 and 5A entries in the "Later phases" note | index |

**Added (11):**

| File | Task |
|---|---|
| `phase4-integration-audit/PHASE4_INTEGRATION_AUDIT.md` (byte-identical copy) | 1 |
| `phase4-integration-audit/README.md` (provenance and SHA-256) | 1 |
| `3.5-3.6_member3/FORMAL_REPORT_SKELETON_3.5-3.6.md` | 2 |
| `phase5a-p0-corrections/PART01_AS_EXECUTED_ADDENDUM.md` | 3 |
| `phase5a-p0-corrections/REQUIREMENT_MAPPING_REVIEW.md` | 5 |
| `phase5a-p0-corrections/TEST_PLAN_CORRECTIONS.md` | 6 |
| `phase5a-p0-corrections/AUTHOR_AND_DATE_CONFIRMATIONS.md` | 7 |
| `phase5a-p0-corrections/BASELINE_DEVIATIONS.md` | 8 |
| `phase5a-p0-corrections/README.md` (this file) | report |
| `phase5a-p0-corrections/qa-sha256-before-phase5a.txt`, `qa-sha256-after-phase5a.txt` | verification |

**Deleted:** none.

## 2. P0 status after Phase 5A

| Phase 4 P0 | Status | Detail |
|---|---|---|
| P0-1 Member 3 formal report | **Prepared, not complete** | Skeleton with verified facts and evidence links. The narrative must come from Member 3 |
| P0-2 Authors and contribution table | **Prepared, not complete** | 12 fields registered; Member 1's name is not in any record; no student IDs anywhere |
| P0-3 Plan vs. actual in Part 01 | **Prepared, not applied** | As Executed addendum ready; needs D-8 (canonical copy) and confirmations before it goes into Part 01 |
| P0-4 Resources and schedule | **Prepared, not applied** | Actual allocation, tools and evidenced dates ready; planned dates and deadline remain TBC |
| P0-5 Commit and push | **Not done** (out of scope for this phase) | Needs owner approval |
| P0-6 Link repair | **Not started** (out of scope for this phase) | Count after Phase 5A: see §8 |

**Completed in Phase 5A:**

- Phase 4 audit preserved permanently. Before this, the only copy was in a temporary scratchpad.
- Member 4 coverage corrected (Phase 4 P1-1, P1-2; Task 4). Re-summed from `jacoco.csv`: lines 434 covered / 208 missed / 642 total = **67.60%**; branches 126 / 302 = 41.72% (unchanged). Member 2 figures (433 / 641, JaCoCo 0.8.12) untouched at PART02:198, :223 and CROSS_REFERENCE §5 rows 1 and 3.
- BDD scenario 4 mapping corrected in CROSS_REFERENCE (Phase 4 P2-1; Task 5). Evidence: citizen `PATCH /api/admin/reports/{id}/status` → `403`.

## 3. Missing Member 3 inputs

From `FORMAL_REPORT_SKELETON_3.5-3.6.md` §8:

1. M3-1 – the formal report narrative for 3.5 (TDD, BDD) and 3.6 (automation, CI).
2. M3-2 – name spelling ("Ishani-Kumaranayaka" vs "Ishani Kumaranayaka"), student ID, report date.
3. M3-3 – which machine ran the local tests (the evidence path's user-profile name differs from the commit author).
4. M3-4 – accept or reject the scenario 4 mapping FR-02.8 / FR-06.6.
5. M3-5 – CI on the final commit including the Selenium job, or explicit "configured, not executed" and "Java 21 result is for `70d01e0`" wording.
6. M3-6 – whether the CI run URL is publicly viewable.
7. M3-7 – confirm the superseded `400` test is described as historical.
8. M3-8 – TDD/BDD/CI points for the group 3.10 case study.

## 4. Author and date confirmations required

From `AUTHOR_AND_DATE_CONFIRMATIONS.md`:

- **Member 1:** full name and student ID (not recorded anywhere). Blocks TP:15 "Authors: TBC".
- **Member 2:** confirm "Oshan Ravishka" and student ID. PART02 has no byline.
- **Member 3:** exact spelling and student ID; report date.
- **Member 4:** preferred form ("S. A. Chathura Madushan" / "Chathura Madushan") and student ID.
- **Project owner:** name (only the repository handle is recorded), TP:391.
- **Integrator / test coordinator:** name (TP:384; API-04 "Execution details" tester).
- **Dates:** planned start/end dates (TP §7.8) and the final submission deadline are not in any record and stay TBC. Actual dates 23, 24 and 25 Nov 2026 are evidenced.

## 5. Baseline deviations (summary of `BASELINE_DEVIATIONS.md`)

| Member | Tested on | Unapplied changes in that result | In working tree? | Approval |
|---|---|---|---|---|
| 1 | Static `f565fb4` | – | – | – |
| 2 | `0ad36bd` | 2 test methods (12 invocations) | No | D-5 |
| 3 | `93d11a3` (CI `70d01e0`) | `AdminSetupRunner.java`, `pom.xml`, 6 test classes, 2 features, `ci.yml`; Part 01 edits (do not apply) | No | D-4 (+ RTM Q1), D-5 |
| 4 | `b97bd96`; `+ f78deb1` for "after" run and SonarQube | `AccountPage.jsx` one line (PC-QA-01 fix) | No | D-3 |
| Integration 3A | `f565fb4` + D-1 | `AuthDtos.java` + 2 test classes | **Yes, uncommitted** | D-1 approved; commit needs D-2 |

## 6. Items still blocked

| Blocked item | Blocked on |
|---|---|
| Member 3 formal report (P0-1) | Member 3 (M3-1 to M3-8) |
| Bylines and contribution table (P0-2) | Names and student IDs from all members and the owner |
| Applying the addendum and Test Plan corrections (P0-3, P0-4) | Owner decision D-8 (canonical Part 01 copy) and the confirmations in §4. *[Phase 5B rev. 2, 25 Nov 2026: D-8 decided – APPROVED, option CAN-1, canonical copy `publiccare/qa/3.1-3.2_member1/`. Still blocked on the confirmations in §4 and on separate approval to apply; not applied. See [CONFIRMATION_REGISTER.md](../phase5b-confirmation-register/CONFIRMATION_REGISTER.md) §5.1]* *[Phase 5B rev. 3, 25 Nov 2026: P0-3 and P0-4 **applied** to the canonical copy (UB-1 – UB-17) after separate approval; the §4 confirmations are still outstanding and are shown as PENDING / TBC in Part 01. See the register §5.2]* |
| Member 4 requirement IDs (rows 1–5 of the mapping review) | Member 4 confirmation; row 2 differs from the Phase 4 suggestion (FR-02.5 vs FR-02.8) |
| Combined run with member tests | Owner decisions D-3, D-4, D-5 |
| Java 21 run of the integration working tree (P1-18) | **No JDK 21 on the integration machine.** On 2026-11-25 `java -version` reports Oracle `27+35`, and the only JDK folder found is `C:\Program Files\Java\latest`. *[Phase 5B rev. 1, 2026-11-25: resolved – Ubuntu OpenJDK 21.0.13.1 in WSL2 Ubuntu; run executed: 85/0, H2 only. Console log not archived. See [CONFIRMATION_REGISTER.md](../phase5b-confirmation-register/CONFIRMATION_REGISTER.md) ENV-J5, ENV-J6, ENV-J10]* |
| Commit and push (P0-5) | Owner approval (D-2), after the Java 21 run |
| Link repair (P0-6) | Deferred until all text edits are done |
| PostgreSQL verification / INT-01 | No PostgreSQL test environment (RTM Q3) |
| Member 4 issue-timeline and SonarQube screenshots, bug triage (P1-6 to P1-8) | Member 4 |

## 7. Exact next steps for the combined Java 21 test run

Run these on the `qa/final-assignment` working tree in Git Bash. Only steps 1–3 can run before any owner decision. Store every output in a new evidence folder, for example `qa/phase5b-java21-run/`. Keep every failed run.

1. **Install a Java 21 JDK** (for example Eclipse Temurin 21, the distribution used in CI) without removing Java 27. In the shell used for the run:
   `export JAVA_HOME="<path to JDK 21>"; export PATH="$JAVA_HOME/bin:$PATH"`
2. **Record the environment** into `00-environment.txt`:
   `java -version; cd backend && ./mvnw -v; cd ..; node -v; npm -v; git rev-parse HEAD; git status --short`
   Check that `./mvnw -v` reports `Java version: 21…` and Maven 3.9.16. If it does not, stop.
3. **Run the integration working tree as it is** (`f565fb4` + D-1), with no other change:
   `cd backend && ./mvnw -B clean test 2>&1 | tee ../qa/phase5b-java21-run/01-java21-f565fb4-plus-D1.txt`
   Copy `backend/target/surefire-reports/` into the evidence folder. The expected result is 85 tests (72 + 13), but only the log may be reported. This closes P1-18 and is the precondition for the commit decision D-2.
4. **Owner decisions** D-3 (M4 fix), D-4 (M3 `AdminSetupRunner` + RTM Q1) and D-5 (bring member tests, `pom.xml` and workflow into the group repository). Record each decision in the RTM "Owner decisions" table before applying anything.
5. **Apply approved changes one at a time**, each followed by `./mvnw -B clean test` on Java 21 with its own log:
   - (a) Member 2 test diff (`test-code-changes_962aeaf..0ad36bd.diff`; paths start with `project/`, apply with `git apply -p2`, or `--directory` as needed after checking with `git apply --check`).
   - (b) Member 3 `pom.xml` diff.
   - (c) Member 3 test classes and features from `reference-code/`. **Leave out or replace** `emailWithSurroundingSpacesIsRejectedByValidation` (superseded by D-1). Record this as a deviation from Member 3's copy.
   - (d) Member 3 `AdminSetupRunner` diff, only if D-4 is approved. Without it, leave out `AdminSetupRunnerLoopbackTest`, because it does not compile.
   - (e) Member 4 `AccountPage.jsx` fix, only if D-3 is approved; then `npm ci && npm run lint && npm run build`.
6. **One final combined run** after all approved changes: `./mvnw -B clean test` on Java 21. Report **only the Surefire total from this run** as the combined figure, never a sum of earlier copies.
7. **Selenium separately** (if (c) is applied): `npm ci && npm run build`; ports 8080 and 5199 free; Chrome installed; then `cd backend && ./mvnw -B test -Dtest=PublicCareUiIT` on Java 21. Record Chrome and ChromeDriver versions.
8. **State the limits that remain:** H2 only (no PostgreSQL, INT-01 not executed); Windows only; GitHub Actions not active in the group repository unless D-5 includes `ci.yml` and the branch is pushed.
9. Then continue with Stage 3 (apply the prepared documents), Stage 4 (link repair), Stage 5 (hash and count checks) and Stage 6 (commit and push, with approval) from the Phase 4 plan.

## 8. Verification performed in Phase 5A

| Check | Result |
|---|---|
| `qa/` manifest before vs after (SHA-256, all files) | 5 modified, 11 added (including this README and the two manifest files), 0 removed. Modified files are the 5 in §1 only |
| Evidence, reference code, stored diffs, Part 01 copies | Unchanged (not in the modified list) |
| Outer Part 01 originals (`Public care/qa/`, 3 files) | SHA-256 unchanged (`33bc16d6…`, `1cc432dc…`, `01c67fab…`) |
| `AuthDtos.java`, `AuthDtosTest.java`, `AuthEmailNormalizationApiTest.java` | SHA-256 unchanged |
| Phase 4 audit copy | Byte-identical to the scratchpad original (`1874b4ab…1391`) |
| Member 4 `jacoco.csv` | Re-summed: lines 434 / 642, branches 126 / 302, instructions 2,536 / 4,226, methods 170 / 237 |
| Part 01 counts | 79 case headings (22 / 14 / 25 / 17 / 1), no duplicates; 78 `PROPOSED / NOT EXECUTED` + 1 `PASS` |
| Baseline test methods | 72 `@Test` at `f565fb4` (`git grep`, excluding `@TestConfiguration`) |
| Member 3 logs | 26 log files re-read; totals, BUILD results and JVMs match the Phase 4 table |
| Relative links in files created or edited by Phase 5A | All resolve. Phase 5A added no link with an old prefix, so the Phase 4 broken-link count (529) is unchanged |
| Tests run in Phase 5A | **None** |
