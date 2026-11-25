# Phase 6 – Member repository re-inspection and provenance verification

| Item | Value |
|---|---|
| Date | 2026-11-25 (clones taken about 12:15 +05:30) |
| Authority | Project owner's "Additional authorisation – integrate evidence from all member GitHub repositories" (read-only access to the Member 2, 3 and 4 repositories) |
| Method | Read-only **bare** clones (no checkout) of the three member repositories in a temporary folder outside the project. Git blob hashes compared with `git hash-object` on every copy in `qa/`. Stored diffs regenerated from the clones. Screenshots opened and compared with logs, code and reports |
| Changes to member repositories | **None.** No push, commit, branch, pull request or settings change |
| Changes to this repository | New files in this folder; a new [`qa/evidence/EVIDENCE_INDEX.md`](../evidence/EVIDENCE_INDEX.md); marked Phase 6 notes in `qa/README.md`, `qa/INTEGRATION_LOG.md`, the Phase 5B register and the Member 3 report skeleton. **No evidence file, reference copy, stored diff, member report, Part 01 file, application code or test code was changed. No test was run. Nothing committed or pushed** |
| Hash snapshot | [`qa-sha256-before-phase6.txt`](qa-sha256-before-phase6.txt) (186 files, taken before any Phase 6 edit) and [`qa-sha256-after-phase6.txt`](qa-sha256-after-phase6.txt) |

## 1. Repository names and access

The repository names given in the authorisation (`PublicCare-QA-Member2-Oshan`, `…-Member3-Ishani`, `…-Member4-Chathura`) **do not exist**. The real names end with a hyphen, as already recorded in Phase 1:

| Member | Repository | Visibility |
|---|---|---|
| 2 | `madushankachamara891-pixel/PublicCare-QA-Member2-Oshan-` | **Private** (GitHub REST API returns 404 without authentication) |
| 3 | `madushankachamara891-pixel/PublicCare-QA-Member3-Ishani-` | **Private** |
| 4 | `madushankachamara891-pixel/PublicCare-QA-Member4-Chathura-` | **Private** |

Git access works through the credential already stored on this machine. The GitHub CLI (`gh`) is **not installed**, so the GitHub API (Actions runs, job logs, artifacts, issues) could not be read. The stored Git credential was not extracted or reused for API calls. Consequence: the Member 3 CI run page and the Member 4 issue #1 page could not be inspected in this phase (see §5).

**Because the repositories are private, a marker without collaborator access cannot open the CI run URL or the issue #1 URL.** The in-repository screenshots and text files are the only evidence such a reader can see (answers the factual part of register P-M3-8 and P-M4-4; Phase 4 risk R-4 is confirmed).

## 2. Inventory

| | Member 2 | Member 3 | Member 4 |
|---|---|---|---|
| Branch (evidence) | `main` | `member3-tdd-bdd-ci` | `chathura` |
| Latest commit | `0ad36bd9530a23b6548175734b9b0ae597f9899e` (Oshan Ravishka, 2026-11-24 12:17 +05:30) | `93d11a34d22fff59f62d211faeacc60dfc3a50dd` (Ishani-Kumaranayaka, 2026-11-24 11:30 +05:30) | `1c8e07ba92e0ca32937a9c8d3d901ce3077c5058` (Chathura Madushan, 2026-11-24 20:48 +05:30) |
| Same as Phase 1 audited commit? | **Yes** | **Yes** | **Yes** |
| Other branches | – | `main` = `2c1e597` (handover) | `main` = `b97bd96` (handover; ancestor of `chathura`, adds nothing) |
| Commits on branch | 2 | 21 (20 by Member 3) | 3 (handover, `f78deb1` fix, `1c8e07b` docs) |
| Report | `qa/PART02_BLACK_BOX_AND_WHITE_BOX_TESTING.md` | **None** (evidence READMEs only) | `qa/MEMBER4_QA_REPORT.md` |
| Evidence | `evidence/` 9 PNG + 1 log | `evidence/` 51 files in 8 folders | `evidence/member4/` 13 files |
| Test / CI artifacts | 2 extended test classes | 6 test classes, 2 feature files, `.github/workflows/ci.yml`, Surefire/Cucumber reports | JMeter plan, 2 browser scripts, Sonar properties |
| Changes to application code in `project/` vs `f565fb4` | None (tests only) | `pom.xml`, `AdminSetupRunner.java` | `src/AccountPage.jsx` |

**Conclusion: no file in any member repository is new or changed since Phase 1.** Every relevant file is already in `qa/`. No additional file was retrieved in Phase 6, and none was overwritten.

Files in the member repositories that are deliberately **not** integrated (unchanged from INTEGRATION_LOG §3): `CLAUDE.md`, `CLAUDE_PROMPT.md`, the handover `README.md`, the handover copies of `qa/PART01_*.md` and `PROJECT_ANALYSIS_REPORT.md` (Member 1's canonical copy is `3.1-3.2_member1/`, owner decision D-8), and the full `project/` application copies (only the changed files are kept, as reference copies or diffs).

## 3. Provenance re-hash (closes Phase 4 P1-19 / register UNA-5)

Full per-file record: [`MEMBER_REPO_PROVENANCE_MANIFEST.tsv`](MEMBER_REPO_PROVENANCE_MANIFEST.tsv) (member, repository, branch, commit, original path, destination, source blob, destination blob, status, retrieval date).

| Member | Copies | Blob-identical | Differ |
|---|---:|---:|---:|
| 2 | 14 | 14 | 0 |
| 3 | 61 | 59 | 2 |
| 4 | 20 | 19 | 1 |
| **Total** | **95** | **92** | **3** |

The 95 copies are Phase 1's 98 minus the 3 Member 1 files (local originals, checked by SHA-256 in Phase 4). The 3 differing files were diffed line by line against the source. Each difference is exactly a marked, attributed integration edit that is already logged, and nothing else:

| File | Only difference | Logged in |
|---|---|---|
| `3.5-3.6_member3/evidence/README.md` | Bracketed integration notes on lines 40–41 (D-1, 72 count) | INTEGRATION_LOG top note (Phase 3A, 3B) |
| `3.5-3.6_member3/evidence/api-automation/README.md` | "SUPERSEDED" note on line 26; "Integration addendum" section appended | Same |
| `3.7-3.10_member4/MEMBER4_QA_REPORT.md` | Line 67: 433/641 → 434/642 with *[Corrected: Phase 5A]* note | Phase 5A README |

**Stored diffs regenerated from the clones** (compared ignoring line endings): all 5 identical.

| Stored diff | Regenerated with | Result |
|---|---|---|
| `3.3-3.4_member2/unapplied-diffs/test-code-changes_962aeaf..0ad36bd.diff` | `git diff 962aeaf 0ad36bd -- project/backend/src/test/` | Identical |
| `3.5-3.6_member3/unapplied-diffs/AdminSetupRunner-ipv6-loopback_2c1e597..93d11a3.diff` | `git diff 2c1e597 93d11a3 -- …/AdminSetupRunner.java` | Identical |
| `3.5-3.6_member3/unapplied-diffs/pom-test-dependencies_2c1e597..93d11a3.diff` | `git diff 2c1e597 93d11a3 -- project/backend/pom.xml` | Identical |
| `3.5-3.6_member3/unapplied-diffs/part01-execution-status-edits_2c1e597..93d11a3.diff` | `git diff 2c1e597 93d11a3 -- qa/` | Identical |
| `3.7-3.10_member4/unapplied-diffs/AccountPage-registration-fix_f78deb1.diff` | `git show --format=fuller f78deb1 -- project/src/AccountPage.jsx` | Identical |

**`project/` copies vs `f565fb4`** (blob hashes): the differing files are exactly the set in INTEGRATION_LOG §1 (Member 2: 2 changed test classes; Member 3: `pom.xml`, `AdminSetupRunner.java` changed, 6 test classes and 2 feature files added; Member 4: `src/AccountPage.jsx`). Nothing else differs.

## 4. Technical verification added in Phase 6

Phase 4 already recomputed the Maven/Surefire totals, JaCoCo CSV, JMeter JTL, SonarQube JSON, Cucumber files and E08/E09 (Phase 4 §2). Those checks were **not repeated**. Phase 6 covers only what Phase 4 could not check without the clones or did not open.

### 4.1 Member 2 screenshots E01–E07 (not opened in Phase 4)

| Evidence | What it shows | Matches | Verdict |
|---|---|---|---|
| E01 | IntelliJ, `main`, Maven `test`: 72/0/0/0, BUILD SUCCESS, 13.616 s, finished 2026-11-24T11:10:53+05:30 | E10 log (same total time and timestamp); PART02:24 | **Verified** |
| E02 | JaCoCo 0.8.12 overall: 1,690/4,226 instr. missed (60%), 176/302 branches missed (41%), 208/641 lines missed, 67/237 methods, 6/57 classes | Identical to final E09 totals | **Not verifiable as "baseline"** (see F6-2) |
| E03 | `lk.publiccare.backend.auth` package: 39/250 instr. missed, 1/6 branches, 8/48 lines; AuthService 76% / 100%, AuthController 87% / 50% | Consistent with E04 and E05 row by row | Consistent |
| E04 | `AuthService`: `register` Cxty 3, 100% branches; class 22/93 instr. missed, 0/4 branches, 5/17 lines | PART02:144 V(G) = 3 | **Verified** |
| E05 | `AuthController`: `login` Cxty 2, 1 of 2 branches missed; class 11/90, 2/22 lines | PART02:172 V(G) = 2; the login byte-limit branch was not executed (path B1, disclosed) | **Verified** |
| E06 | `ReportControllerTest.reportTitleBoundaryTest` (line 380, body 395–407) open; 79/0, 10.833 s, finished 11:49:42 | Reference code line numbers and content identical; PART02:25 | **Verified** |
| E07 | `AuthControllerTest.registrationEquivalencePartitionTest` (line 145, body 148–169) open; 84/0, 10.671 s, finished 12:01:23 | Reference code identical; PART02:26. A separate run from E08 (12:04:55, 16.449 s) | **Verified** |

All E01–E07 come from the Member 2 copy (`C:\Users\oshan\PublicCare-QA-Member2-Oshan-\project\backend`, visible in E03/E05), Windows, JaCoCo 0.8.12. They are **member-repository executions**, not executions in this repository.

### 4.2 Member 3 CI (run #1)

The three screenshots in `3.5-3.6_member3/evidence/ci/` were cross-checked against the Git history and the workflow file:

| Check | Result |
|---|---|
| Run URL, run number | `…/actions/runs/45326010526`, run #1, trigger `push` – same in the summary and artifacts screenshots |
| Commit and branch | `70d01e0` on `member3-tdd-bdd-ci`. `70d01e0` exists in the cloned history (2026-11-24 09:52:47 +05:30) and is an ancestor of `93d11a3` |
| Job names | "Backend (Java 21, Maven Wrapper)" and "Frontend (Node.js, lint and build)" = `name:` values in `ci.yml` at `70d01e0` (the `ui` job does not exist at that commit) |
| Result | Backend log: `Tests run: 91, Failures: 0`, BUILD SUCCESS, 49.346 s, finished 2026-11-24T04:24:04Z; Cucumber 4/0 |
| Artifact | `backend-test-reports`, 364 KB, digest prefix `sha256:5e2ec554ba1894c1b280e3dbf6f41a2951c119ce173…` = first 43 hex characters of the SHA-256 in `artifact-summary.txt` |
| Timeline | Screenshot clock 10:00 (11/24/2026), "pushed 7 minutes ago": consistent with the commit time 09:52:47 |
| Verdict | **Consistent.** Genuine-looking capture of the documented run. The run page itself could not be opened (private repository, no `gh`) |

**New finding F6-1 – what differs between the CI commit and the audited commit.** `git diff 70d01e0 93d11a3` outside `evidence/` and `qa/` changes only:

| File | Change | Effect on the 91 tests run by `./mvnw -B test` |
|---|---|---|
| `PublicCareApiTest.java` | One **Javadoc comment line** (removed "(RTM Q1)"), commit `ef5ed72` | None (comment only) |
| `pom.xml` | Added test-scoped `selenium-java` dependency | Class path only; no test added to the default run |
| `PublicCareUiIT.java` | New (318 lines) | Not run: name ends in `IT`, excluded by Surefire's default includes |
| `.github/workflows/ci.yml` | `ui` job added (+47 lines) | Not executed by any recorded run |

So the 91 tests that passed in CI on `70d01e0` have the same executable test code at `93d11a3`. This narrows Phase 4 C-13 ("inferred for `93d11a3`"), but it is **still not a run on `93d11a3`**. Reports must keep citing the Java 21 result as "CI run #1, commit `70d01e0`". The `ui` job stays "configured, never executed".

### 4.3 Member 4 registration screenshots

`registration-before.png` and `registration-after.png` show the same registration form with synthetic data (`QA Citizen`, `qa@example.invalid`, masked passwords). The only visible difference is the focus ring on "Confirm password" in the before image. **The defect itself (custom-validity message, blocked submit) is not visible in either image**, because the browser validation tooltip is not captured by the script's `page.screenshot`. The result is carried by the text outputs: before `{"valuesMatch":true,"message":"Passwords do not match.","submitted":false}` plus the assertion failure; after `{"message":"","submitted":true}` (Phase 4 §2.4). The report (MEMBER4_QA_REPORT.md:38) links them as "Registration before/after" without claiming more. **The final report must describe them as form-state captures and cite the `.txt` files as the evidence of the defect and the fix.**

## 5. Findings

| ID | Finding | Effect | Action |
|---|---|---|---|
| F6-1 | CI commit `70d01e0` vs audited `93d11a3`: executable test code for the 91 tests identical (comment line, test dependency, an IT class, the `ui` job) | Narrows Phase 4 C-13 | Noted in the Member 3 skeleton §5. Wording "Java 21 result is for `70d01e0`" unchanged |
| F6-2 | E02 is labelled "Baseline overall coverage" (PART02:272) but its totals equal final E09 exactly. No timestamp or test count in the image. Plausible (the 12 added tests are MockMvc tests with mocked services, on paths already covered), but the image cannot prove it was taken before the additions. E03–E05 ("baseline", PART02:203, :216) have the same limitation | The report's "baseline" label for E02–E05 is **not verified**. The figures themselves are consistent with E09 | Member 2 may confirm when E02–E05 were captured. Not corrected (no evidence supports a correction) |
| F6-3 | Member 4 registration PNGs do not show the defect (§4.3) | Weak visual evidence; text evidence is sound | Describe as form-state captures in the final 3.8 text |
| F6-4 | Member repositories are private; `gh` not installed | CI run page, job logs, artifact ZIP and issue #1 timeline not retrievable; marker cannot open the external links | Owner action, if wanted: install GitHub CLI and run `gh auth login` in an interactive terminal. Then the Actions run metadata and issue #1 events can be exported as JSON with provenance (P4 P1-6, P1-10, P1-11). **Do not paste tokens into chat** |
| F6-5 | Repository names in the authorisation omit the trailing hyphen | Documentation risk only | Use the hyphenated names (§1) |

No duplicate, outdated or conflicting evidence was found beyond what Phases 1–5B already record (superseded API-04 characterization test; Member 3 Part 01 diff not to be applied; 433/641 vs 434/642 coverage in two JaCoCo versions).

## 6. Status changes

- Phase 4 **P1-19** and register **UNA-5** (re-hash of member copies): **done** – 92/95 identical, 3 logged edits (§3). Register Revision 4 changes UNA-5 to CONFIRMED.
- Register **P-M3-8** (is the CI URL viewable by the marker?) and **P-M4-4** (is the issue URL viewable?): factual answer recorded – **no, the repositories are private**. Both stay PENDING, because the remaining parts (granting access or supplying screenshots/exports) are decisions for the owner and members.
- Nothing else changed status. No owner decision (D-2 to D-7) was taken.
