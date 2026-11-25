# PublicCare QA – Evidence index (Sections 3.1–3.10)

Created in Phase 6 (2026-11-25). One table of all evidence in `qa/`, with where each result was produced. Member evidence stays in the member folders, where Phase 1 put it, so the members' own relative links keep working. It is **not** duplicated into this folder. Per-file provenance (repository, branch, commit, original path, blob hash, retrieval date) for every copied member file: [`../phase6-member-repo-verification/MEMBER_REPO_PROVENANCE_MANIFEST.tsv`](../phase6-member-repo-verification/MEMBER_REPO_PROVENANCE_MANIFEST.tsv).

## Execution-origin labels

| Label | Meaning |
|---|---|
| **GROUP** | Executed in this repository (`publiccare/`, branch `qa/final-assignment`, working tree = `f565fb4` + uncommitted D-1 change) |
| **MEMBER** | Executed in a member's **separate repository**, on that member's copy of the application. **Not a test of this repository.** The application baseline differs per member (Phase 4 Appendix B) |
| **MEMBER-CI** | Executed on GitHub Actions in a member's separate repository |
| **HISTORICAL** | A genuine past result that has been superseded and must not be read as current behaviour |
| **NOT EXECUTED** | Proposed or configured, never run |
| **NOT VERIFIED** | Present, but a stated property (date, ordering, external state) cannot be confirmed from the evidence |

## Source repositories (all private)

| Member | Repository | Branch | Commit | Retrieved / re-verified |
|---|---|---|---|---|
| 1 | Local folder `Public care/qa/` (outside Git; non-canonical since D-8) | – | – | Phase 1; SHA-256 checked in Phase 4 |
| 2 | `madushankachamara891-pixel/PublicCare-QA-Member2-Oshan-` | `main` | `0ad36bd9530a23b6548175734b9b0ae597f9899e` | Phase 1 copy; Phase 6 re-hash 14/14 identical |
| 3 | `madushankachamara891-pixel/PublicCare-QA-Member3-Ishani-` | `member3-tdd-bdd-ci` | `93d11a34d22fff59f62d211faeacc60dfc3a50dd` | Phase 1 copy; Phase 6 re-hash 59/61 identical, 2 logged integration edits |
| 4 | `madushankachamara891-pixel/PublicCare-QA-Member4-Chathura-` | `chathura` | `1c8e07ba92e0ca32937a9c8d3d901ce3077c5058` | Phase 1 copy; Phase 6 re-hash 19/20 identical, 1 logged integration edit |

## Evidence by section

| Section | Evidence (location) | Origin | Environment | Result | Verified by | Notes |
|---|---|---|---|---|---|---|
| 3.1–3.2 | `3.1-3.2_member1/` Part 01 documents | – (static analysis of `f565fb4`) | – | 79 test cases; 1 executed (API-04) *[Phase 8A: + INT-01 partly executed, see the INT-01 row]* | Phase 4 §2.1 | Canonical copy (D-8) |
| 3.2 / API-04 | `phase3a-api04-correction/` logs 00–03 and 2 Surefire XML | **GROUP** | Windows 11, Oracle Java 27, H2 | 72/0 → red 13/9 (expected) → 13/0 → 85/0 | Phase 4 §2.1 | D-1 change uncommitted |
| 3.2 / Java 21 | `evidence/java21-run/surefire-reports/` (28 files) | **GROUP** | WSL2 Ubuntu 26.04.1, OpenJDK 21.0.13.1, H2 | 85/0 (14 classes) | Register ENV-J6 | Console log not archived (ENV-J10) |
| – | `evidence/java21-run/previous-surefire-reports/` (29 files) | **GROUP** | Java 27 (Phase 3A run) | – | Register §6.1.1 | **Not Java 21 evidence** |
| 3.3 | `3.3-3.4_member2/evidence/` E01, E10 | **MEMBER** (M2) | Windows 11, Microsoft JDK 21.0.13.1, H2 | Baseline 72/0 | Phase 4 (E10), Phase 6 (E01) | |
| 3.3 | E06 | **MEMBER** (M2) | same | 79/0 after BVA | Phase 6 §4.1 | Screenshot only (no Surefire XML) |
| 3.3 | E07 | **MEMBER** (M2) | same | 84/0 after EP | Phase 6 §4.1 | Screenshot only |
| 3.3–3.4 | E08, E09 | **MEMBER** (M2) | same, JaCoCo 0.8.12 | 84/0; lines 433/641, branches 126/302 | Phase 4 §2.2 | |
| 3.4 | E02–E05 | **MEMBER** (M2) | JaCoCo 0.8.12 | Coverage and complexity | Phase 6 §4.1 | **NOT VERIFIED** as "baseline": E02 totals equal E09; no timestamp (F6-2) |
| 3.3–3.4 | DT-05, DT-06, path B1 | **NOT EXECUTED** | – | – | Phase 4 | Disclosed in PART02 |
| 3.5 TDD | `3.5-3.6_member3/evidence/tdd/` | **MEMBER** (M3) | Windows 11, Temurin 25.0.5, H2 | compile error → 6/1 → 6/0 → 78/0 | Phase 4 §2.3 | Code fix unapplied here |
| 3.5 BDD | `…/evidence/bdd/` incl. `cucumber-reports/` | **MEMBER** (M3) | same | 4 scenarios; full suite 82/0 | Phase 4 §2.3 | Failed runs 01a, 02a kept |
| 3.6 API | `…/evidence/api-automation/` | **MEMBER** (M3) | same | 9/0; full 91/0 | Phase 4 §2.3 | `emailWithSurroundingSpacesIsRejectedByValidation` = **HISTORICAL** (superseded by D-1) |
| 3.6 UI | `…/evidence/ui-automation/` (6 screenshots) | **MEMBER** (M3) | same, Chrome 157 headless | 6/0 (fault check 6/2 intended) | Phase 4 §2.3 | Not part of the 91 |
| 3.6 CI | `…/evidence/ci/` (3 screenshots, artifact summary) | **MEMBER-CI** (M3) | ubuntu-latest, Temurin 21.0.13, H2 | Run #1 on **`70d01e0`**: 91/0 | Phase 4; Phase 6 §4.2 | Not run on `93d11a3`, but test code for the 91 is identical there (F6-1). URL not viewable without repository access |
| 3.6 CI | `ui` job in `reference-code/github-workflows/ci.yml` | **NOT EXECUTED** | – | – | Phase 4; Phase 6 | Workflow inactive in this repository |
| 3.6 | `…/evidence/frontend/` | **MEMBER** (M3) | Node 24.13.0 / npm 11.11.0 | `npm ci`, lint, build | Copied in Phase 1; logs not recomputed | |
| 3.7 Performance | `3.7-3.10_member4/evidence/health.jtl`, `jmeter-console.txt` | **MEMBER** (M4) | Linux container, Azul 21.0.13.1, JMeter 5.6.3 | 100 req, 0% errors, mean 9.37 ms, p95 17 ms | Phase 4 (recomputed from JTL) | `/api/health` only; no DB load |
| 3.7 Security | `access-checks.txt` | **MEMBER** (M4) | same | 200 / 401 / 401 | Phase 4 | |
| 3.7 Usability | `mobile.txt`, `registration-*.txt/.png` | **MEMBER** (M4) | Chromium 153.0.8010.0 | Scripted walkthroughs | Phase 4; Phase 6 §4.3 | PNGs show form state only, not the defect (F6-3); no participants |
| 3.8 Defect | `registration-before/after.txt`, `unapplied-diffs/AccountPage-registration-fix_f78deb1.diff` | **MEMBER** (M4) | same | before FAIL, after PASS | Phase 4; Phase 6 (diff regenerated) | Fix **not** in this repository: PC-QA-01 open here. Issue #1 timeline **NOT VERIFIED** (private repository, no `gh`) |
| 3.9 | `sonar-measures.json`, `sonar-issues.json`, `sonar-run.txt`, `jacoco.csv` | **MEMBER** (M4) | SonarQube 25.3, JaCoCo 0.8.13 | Gate PASSED; coverage 36.0%; lines 434/642 | Phase 4 | Scan baseline includes unapplied `f78deb1`; no dashboard screenshot |
| – | Combined run of all members' tests | **NOT EXECUTED** | – | – | – | Never add totals across copies (REJ-5) |
| 3.2 / INT-01 | `phase7-final-completion/int01-postgresql/run-20261125-125857/` (16 files) *[Phase 8A]* | **GROUP** | Windows 11, JetBrains Runtime 21.0.8, **PostgreSQL 18.6** (dedicated local database `publiccare_int01_qa`, role `publiccare_int01_app`), Flyway 11.7.2 | (a) PASS, (b) PASS, (d) PASS; (c) **BLOCKED** (RTM Q2) → INT-01 PARTIALLY EXECUTED | Phase 8A: all 16 files read; schema dump compared with V1–V5; credential scan 0 | Run by the project owner. Flyway warns that PostgreSQL 18 is untested (supports ≤ 17). Start-up and health endpoints only; no data written. Record: [INT-01 README](../phase7-final-completion/int01-postgresql/README.md) |
| – | PostgreSQL execution (anything other than INT-01) | **NOT EXECUTED** | – | – | – | Every JUnit and member result above used H2 *[Phase 8A: row was "PostgreSQL execution"; the INT-01 row above was added]* |

## Stored code changes (not applied; provenance)

| Diff | Source | Regenerated in Phase 6 |
|---|---|---|
| `3.3-3.4_member2/unapplied-diffs/test-code-changes_962aeaf..0ad36bd.diff` | M2 `962aeaf..0ad36bd` | Identical |
| `3.5-3.6_member3/unapplied-diffs/AdminSetupRunner-ipv6-loopback_2c1e597..93d11a3.diff` | M3 | Identical |
| `3.5-3.6_member3/unapplied-diffs/pom-test-dependencies_2c1e597..93d11a3.diff` | M3 | Identical |
| `3.5-3.6_member3/unapplied-diffs/part01-execution-status-edits_2c1e597..93d11a3.diff` | M3 | Identical. **Do not apply** (REJ-4) |
| `3.7-3.10_member4/unapplied-diffs/AccountPage-registration-fix_f78deb1.diff` | M4 `f78deb1` | Identical |

## Phase 7 notes (2026-11-25)

- **3.7 environment:** "Linux container" (rows 3.7) is not a term used in any Member 4 evidence file. The scanner log records `Linux 6.18.57 amd64` and `Java 21.0.13.1 Azul Systems, Inc.` (`sonar-run.txt` lines 5–6). Read the rows above as "Linux 6.18.57 amd64 (isolated environment)".
- **3.5 BDD:** the failed run `bdd/02a` had 4 failures: three `expected 201 but was 403` and one `expected 401 but was 403` (scenario 2), with the same CSRF cause.
- **3.9:** `sonar.tests` was set, so 3 of the 91 SonarQube issues are in test code (`java:S5778`).
- New integration documents that cite this evidence: [Member 3 draft](../3.5-3.6_member3/REPORT_3.5-3.6_TDD_BDD_AUTOMATION_CICD.md), [Member 4 integrated revision](../3.7-3.10_member4/REPORT_3.7-3.10_INTEGRATED.md), [3.2 reconciliation](../phase7-final-completion/SECTION_3.2_EXECUTION_RECONCILIATION.md).
- **PostgreSQL / INT-01:** the row "PostgreSQL execution – NOT EXECUTED" stays true until the prepared run in [`../phase7-final-completion/int01-postgresql/`](../phase7-final-completion/int01-postgresql/README.md) has been executed by the owner and its output checked. *[Phase 8A, 25 Nov 2026: executed and checked – see the "3.2 / INT-01" row above. The "NOT EXECUTED" row now covers PostgreSQL runs other than INT-01.]*

## Phase 11 – Part 01 execution (2026-11-25)

| Section | Evidence | Origin | What it shows |
|---|---|---|---|
| 3.2 | [`../phase11-part01-exec/run2/part01-observations.txt`](../phase11-part01-exec/run2/part01-observations.txt) | **GROUP** (isolated copy of the backend; production code SHA-256-identical) | One line per observed result for the 39 executed cases (API-01 – API-03, API-05 – API-25, UT-01 – UT-15) |
| 3.2 | [`../phase11-part01-exec/run2/`](../phase11-part01-exec/run2/) console log and Surefire reports | **GROUP** | Run 2: 40 tests, 0 failures, 0 errors |
| 3.2 | [`../phase11-part01-exec/run1/`](../phase11-part01-exec/run1/) | **GROUP – HISTORICAL** | Run 1: 40 tests, 1 error (UT-05 test expected the unwrapped exception); kept for provenance |
| 3.2 | [`../phase11-part01-exec/run3/`](../phase11-part01-exec/run3/) | **GROUP** | Full regression suite: 125 tests (85 existing + 40), 0 failures |
| 3.2 | [`../phase11-part01-exec/test-code/`](../phase11-part01-exec/test-code/) | **GROUP** (evidence only, not in the source tree) | Executable test code of the 39 cases |
| 3.8 | [`../phase11-part01-exec/README.md`](../phase11-part01-exec/README.md) §3 | **GROUP** | Defect PC-QA-02 (concurrent upload `500`), observations OBS-1 – OBS-3 |
| – | Not produced | **NOT EXECUTED** | MT-01 – MT-22, UI-06 – UI-12, UI-14 in any copy; UT-16, UT-17 (Vitest); any Phase 11 case on PostgreSQL or in CI |
