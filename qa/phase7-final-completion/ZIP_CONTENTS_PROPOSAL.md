# Proposed submission ZIP – contents for owner approval (Phase 7, Step 8)

| Item | Value |
|---|---|
| Date | 2026-11-25 |
| Status | **PROPOSAL ONLY. No ZIP has been created.** The owner authorised creating and verifying the ZIP **after** approving its contents | *[Phase 9, 25 Nov 2026: decisions Z-1 – Z-7 resolved or put to the owner, Z-8 added (gitignored INT-01 logs), exact file list prepared – see [Phase 9 record](../phase9-final-submission/PHASE9_SUBMISSION_DECISIONS.md). Still no ZIP created]*
| Proposed file | `Public care/submission/PublicCare-EC4206-QA-Submission.zip`. This is **outside** the Git repository, so the ZIP can never be committed by accident |
| Root folder inside the ZIP | `publiccare/` |

## 1. Why the ZIP is a working-tree snapshot, not only `qa/`

The canonical RTM and Test Plan link to the code as `../../backend/…` (254 + 6 links, fixed in Step 6). A ZIP of `qa/` alone would break all of them again. The proposal therefore packs the **repository working tree**: every tracked file plus every untracked file that `.gitignore` does not exclude (`git ls-files -co --exclude-standard`). This gives exactly the code the reports describe: `f565fb4` + the uncommitted D-1 change + the two D-1 test classes + `qa/`.

## 2. Proposed contents (319 files at the time of this proposal, about 8.3 MB, plus the items in §4)

| Group | Files | Content |
|---|---:|---|
| Application – backend | 73 | `backend/` source, tests (including the D-1 tests), `pom.xml`, Maven Wrapper, migrations, `.env.example` (placeholders only – checked) |
| Application – frontend | 36 + 2 + 5 | `src/`, `public/`, `package.json`, `package-lock.json`, `index.html`, `vite.config.js`, `eslint.config.js` |
| Repository root | 3 | `README.md`, `AUTH_LOCAL_VERIFICATION.md`, `.gitignore` |
| **3.1–3.2** | 3 | `qa/3.1-3.2_member1/` – Test Plan and RTM (canonical, links fixed); archived Analysis Report (see Z-1) |
| **3.3–3.4** | 15 | `qa/3.3-3.4_member2/` – PART02, E01–E10, reference test code, diff (see Z-3) |
| **3.5–3.6** | 67 | `qa/3.5-3.6_member3/` – **project-integrated report draft**, skeleton, missing-report note, all evidence (logs, Cucumber reports, 6 UI + 3 CI screenshots), reference code, inactive workflow copy, unapplied diffs |
| **3.7–3.10** | 22 | `qa/3.7-3.10_member4/` – **integrated revision**, Member 4's original report (see Z-2), JMeter / Playwright / Sonar files, evidence, fix diff |
| Group evidence | 58 | `qa/evidence/` – evidence index; Java 21 Surefire reports (28); earlier Java 27 reports (29, labelled "not Java 21 evidence") |
| Integration records | 35 | `qa/README.md`, `INTEGRATION_LOG.md`, `CROSS_REFERENCE.md`, `FINAL_COMPLETION_TRACKER.md`; `phase3a…`, `phase4…`, `phase5a…`, `phase5b…`, `phase6…`, `phase7…` (reconciliation, audit and gap matrix, INT-01 script and record, hash snapshot, this proposal) |

## 3. Excluded

| Excluded | Reason |
|---|---|
| `backend/.env` | Local configuration, gitignored, **not read** during integration; may contain credentials |
| `backend/target/` (including the 61 MB INT-01 jar), `dist/`, `node_modules/` | Build output, reproducible from source |
| `.git/` | Repository history is not a deliverable. The branch, baseline and commits are named in the records |
| Outer copy `Public care/qa/` (3 files) | Non-canonical reference copy (D-8, CAN-3). The canonical copy is inside the ZIP |
| `Public care/.vscode/`, `Public care/CLAUDE.md`, empty `*-structure.txt` | Not part of the project |
| Member repositories' `CLAUDE.md`, `CLAUDE_PROMPT.md`, full `project/` copies | Never integrated (INTEGRATION_LOG §3) |

## 4. To be added at packing time (after approval)

| File | Content |
|---|---|
| `publiccare/SUBMISSION_INDEX.md` (new) | A one-page guide for the marker: section → document; the origin labels; the statement that the 3.5–3.6 report and the 3.7–3.10 revision were written or revised by integration from members' evidence; baseline and uncommitted state; known limitations; the contribution table (names and IDs as supplied for G-01) |
| `publiccare/MANIFEST-SHA256.txt` (new) | SHA-256 of every file in the ZIP |
| INT-01 run folder | Only if the owner has run `run-int01.ps1` and its output has been checked *[Phase 8A, 25 Nov 2026: condition met – run `run-20261125-125857/` checked; 16 files, no credential found. The `uploads/` subfolder is empty. Inclusion still needs the owner's ZIP decision]* |

## 5. Decisions needed from the owner

| # | Decision | Options | Recommendation |
|---|---|---|---|
| Z-1 | Archived `PROJECT_ANALYSIS_REPORT.md` (237 broken links, superseded counts; D-6) | (a) include, marked "archived, superseded by the RTM" in the submission index; (b) exclude (breaks the RTM's "Source" link) | **(a)** – it keeps provenance, and the index prevents it from being marked as current |
| Z-2 | Member 4's original `MEMBER4_QA_REPORT.md` (14 broken links) | (a) include as the original record next to the integrated revision; (b) exclude | **(a)** |
| Z-3 | PART02 has 20 broken links (verified rewrites exist) | (a) include as is; (b) approve the 20 mechanical link rewrites in PART02, with a marked note | **(b)** – a marker cannot open the evidence otherwise |
| Z-4 | 59 text files and some screenshots contain local Windows profile paths (P2-8, G-16) | (a) include the evidence unchanged (provenance); (b) leave those files out | **(a)** – editing evidence is not allowed, and leaving it out weakens the submission. Disclose it in the index |
| Z-5 | Integration records (phases 3A–7) | (a) include; (b) leave out | **(a)** – they are the audit trail for every correction |
| Z-6 | Names and registration numbers (G-01, the only S1 gap) | (a) wait for them; (b) build now with "PENDING" in the bylines | **(a)** |
| Z-7 | Commit before packing (D-2, P0-5) | (a) ZIP of the uncommitted working tree; (b) commit first, then ZIP | Owner's choice. The ZIP content is the same either way. The index states the state used |

## 6. Verification after the ZIP is created

1. The file list in the ZIP equals the approved list (no extra, no missing file).
2. `MANIFEST-SHA256.txt` matches every file in a fresh extraction.
3. No `.env`, `target/`, `node_modules/` or `.git/` entry is present; a text scan of the extraction finds no credential.
4. The link checker run on the extracted copy reports the same results as in the repository (canonical Part 01 and all new documents: 0 broken).
5. The ZIP opens with the Windows built-in extractor, and its size is recorded.
