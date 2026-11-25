# Phase 10 – Final submission package: completion record

| Item | Value |
|---|---|
| Date | 2026-11-25 |
| Starting state | Verified Phase 9 state. Branch `qa/final-assignment` at `f565fb4` (local only). Working tree: `M AuthDtos.java` (D-1), 2 untracked D-1 test classes, untracked `SUBMISSION_INDEX.md` and `qa/`. Nothing committed or pushed |
| Inputs | [Phase 9 decisions](../phase9-final-submission/PHASE9_SUBMISSION_DECISIONS.md); [Phase 9 file list](../phase9-final-submission/PACKAGE_FILE_LIST.txt) (343 paths); [confirmation register](../phase5b-confirmation-register/CONFIRMATION_REGISTER.md) Rev. 7; [tracker](../FINAL_COMPLETION_TRACKER.md); [ZIP proposal](../phase7-final-completion/ZIP_CONTENTS_PROPOSAL.md); the project owner's Phase 10 instructions |
| Owner instruction | One ZIP holds **both** the complete PublicCare application (frontend, backend, tests, migrations, configuration) **and** the complete EC4206 assignment (Sections 3.1–3.10, reports, plans, RTM, test cases, results, evidence, integration records). Z-1 – Z-8 approved |
| Snapshot before Phase 10 edits | [qa-sha256-before-phase10.txt](qa-sha256-before-phase10.txt) (222 files under `qa/` + `SUBMISSION_INDEX.md`) |
| Not done in Phase 10 | No commit, push, branch switch or Git identity change. No test re-run (no test result in this record is new). INT-01 not re-run. RTM Q2 not answered. PostgreSQL database and role **not dropped**. No evidence file, application code, test, migration or build file changed. `backend/.env` not read |

## 1. Owner approvals Z-1 – Z-8

Phase 9 recorded these decisions; four of them were then awaiting approval. In Phase 10 the project owner approved **all eight**. The Phase 9 record is kept unchanged as the record of that time. Where it says "AWAITING APPROVAL" or "No ZIP created", this record supersedes it.

| # | Decision | Phase 9 status | Phase 10 status |
|---|---|---|---|
| Z-1 | Include `PROJECT_ANALYSIS_REPORT.md`, labelled **ARCHIVED** (label in `SUBMISSION_INDEX.md` §2; file unchanged; 237 broken links disclosed). Settles D-6 for this package | DECIDED | **APPROVED** |
| Z-2 | Include Member 4's original `MEMBER4_QA_REPORT.md`, labelled **ORIGINAL**, beside the integrated revision (submission version); 14 broken links disclosed | DECIDED | **APPROVED** |
| Z-3 | PART02's 20 broken links repaired, with a marked note | DECIDED – APPLIED | **APPROVED** (applied in Phase 9) |
| Z-4 | Evidence with local Windows profile paths included unchanged; disclosed in the index §4 | DECIDED (confirmation requested) | **APPROVED** |
| Z-5 | Integration records included (log, tracker, register, cross-reference, phase folders) | AWAITING APPROVAL | **APPROVED** – now Phases 3A – 10 |
| Z-6 | Owner-supplied member names and registration numbers | RESOLVED | **APPROVED** |
| Z-7 | ZIP built from the **uncommitted** working tree | AWAITING APPROVAL | **APPROVED** |
| Z-8 | The 4 INT-01 `.log` files (ignored by `.gitignore` `*.log`) added to the file list explicitly; `.gitignore` not changed | AWAITING APPROVAL | **APPROVED** |

Member details used (exact forms supplied by the project owner; initials not expanded): Member 1 – Madusanka D.M.C, EG/2023/5720; Member 2 – Dharmasiri S.O.R, EG/2023/5559; Member 3 – Kumaranayaka K.I.S, EG/2023/5697; Member 4 – Madushan S.C, EG/2023/5716.

## 2. Build 1 – first ZIP (2026-11-25, before this record existed)

After the approvals, the first ZIP was built from the Phase 9 file list and verified. Its record is kept **outside** the package, beside the ZIP (`Public care/submission/PublicCare-EC4206-QA-Submission.VERIFICATION.txt`). The values below are copied from that record. The ZIP hash and the manifest hash were checked again at the start of Phase 10.

| Item | Result |
|---|---|
| Path | `Public care/submission/PublicCare-EC4206-QA-Submission.zip` (outside the Git repository) |
| Size | 4,583,873 bytes (uncompressed 8,601,488 bytes) |
| SHA-256 | `4f1558adcd9b4cfb948259fefa00c63880273660b47a883d925d81a9cccb8baa` (re-checked in Phase 10: matches) |
| Entries | 343 files, single root folder `publiccare/`, no directory entries |
| Selection | `git ls-files -co --exclude-standard` + 4 INT-01 `.log` files (Z-8) + `MANIFEST-SHA256.txt` = 343 paths, byte-identical to the Phase 9 file list |
| Manifest | `publiccare/MANIFEST-SHA256.txt` (in the ZIP only), 342 lines (every file except itself), format `<sha256> *<path>`; SHA-256 `1de034598bacdda6abcd23388b73b28a2f0eb4377650b1ba39ae54aca3fb3283` (re-checked in Phase 10: matches; and the working tree still matched all 342 lines) |
| ZIP integrity | PASS – `unzip -t` "No errors detected"; Python `zipfile.testzip` OK |
| Root folder / duplicates | PASS – all entries under `publiccare/`; no absolute, backslash or `../` paths; no duplicates |
| Completeness | PASS – entries equal the approved 343-path list |
| Manifest check | PASS – `sha256sum -c --strict` on the extracted copy: 342 / 342 OK; also 342 / 342 after Windows `Expand-Archive` |
| Extraction | PASS – `unzip` (Git Bash) and Windows `Expand-Archive` both extracted 343 files |
| Excluded files | PASS – no `.env`, `.git/`, `node_modules/`, `dist/`, `target/`, `.jar` / `.class`, `.vscode/`, `CLAUDE*.md`, keys or certificates, temp / backup files, outer Part 01 copy. Only `backend/.env.example` (placeholders) |
| Credential scan | PASS – no token, key or real password. Matches were synthetic test passwords, placeholders, `[REDACTED]` / `[not recorded]` markers and schema column names |
| INT-01 logs | PASS – `02a-run1-fresh-database.log` (8,554 B), `02a-run1-fresh-database.stderr.log` (0 B), `03a-run2-restart.log` (6,778 B), `03a-run2-restart.stderr.log` (0 B) |
| Relative links | PASS – 0 broken on the extracted copy, except the two disclosed kept records (Analysis Report 237 / 237, Member 4 original 14 / 17) |
| Working tree unchanged by packing | PASS – 342 / 342 source hashes; HEAD `f565fb4`; Git index and identity unchanged; INT-01 run files 16 / 16 match the INT-01 record |

**Why build 1 is replaced.** Build 1 contains the Phase 9 records, which still show Z-4, Z-5, Z-7 and Z-8 as awaiting approval and say no ZIP exists. The owner asked for the Phase 10 records to be in the package. So the ZIP is rebuilt (§4).

## 3. Phase 10 documentation changes

Marked *[Phase 10]* notes; earlier text kept.

| File | Change |
|---|---|
| This folder (new) | This record; [PACKAGE_FILE_LIST.txt](PACKAGE_FILE_LIST.txt); before and after hash snapshots |
| [`qa/README.md`](../README.md) | Phase 10 pointer under the header; Phase 9 and Phase 10 records added to "Integration documents" |
| [`qa/INTEGRATION_LOG.md`](../INTEGRATION_LOG.md) | Phase 10 bullet in "Later phases" |
| [`qa/FINAL_COMPLETION_TRACKER.md`](../FINAL_COMPLETION_TRACKER.md) | New §6 Phase 10 |
| [Confirmation register](../phase5b-confirmation-register/CONFIRMATION_REGISTER.md) | Revision 8 row (Z-1 – Z-8 approved). **No row changed status**; totals stay 36 / 38 / 6 / 6 |
| [`SUBMISSION_INDEX.md`](../../SUBMISSION_INDEX.md) | Phase 10 row; manifest wording ("every file except itself"); extraction path-length limitation; records range "3A – 10" |

Not edited: the Phase 9 records (kept as the record of that time; their hashes are in [qa-sha256-after-phase9.txt](../phase9-final-submission/qa-sha256-after-phase9.txt)), all evidence, all member reports, Part 01, application code, tests, migrations and build files.

## 4. Package file list (final)

**[PACKAGE_FILE_LIST.txt](PACKAGE_FILE_LIST.txt)** – the 343 Phase 9 paths plus the 4 files of this folder = **347 paths**, one per line, relative to `publiccare/`, sorted in byte order. It is built the same way as before: `git ls-files -co --exclude-standard` + the 4 INT-01 `.log` files (Z-8) + `MANIFEST-SHA256.txt` (written at packing time, in the ZIP only).

| Part | Contents |
|---|---|
| Application – root | `README.md`, `AUTH_LOCAL_VERIFICATION.md`, `.gitignore`, `package.json`, `package-lock.json`, `index.html`, `vite.config.js`, `eslint.config.js` |
| Application – frontend | `src/` (36 files), `public/` (2 files) |
| Application – backend | `backend/` (73 files): source, tests incl. the 2 D-1 test classes, Flyway migrations V1 – V5, `pom.xml`, Maven Wrapper, `README.md`, `.env.example` (placeholders only) |
| Assignment – Part 01 (3.1–3.2) | Canonical Test Plan and STLC, Requirements and RTM; Analysis Report (ARCHIVED, Z-1) |
| Assignment – PART02 (3.3–3.4) | Report (links repaired, Z-3), brief, evidence E01–E10, reference tests, diff |
| Assignment – 3.5–3.6 | Member 3 project-integrated report, superseded skeleton and missing-report note, evidence, reference code, diffs |
| Assignment – 3.7–3.10 | Member 4 integrated revision (submission version) and ORIGINAL report (Z-2), JMeter / Playwright / Sonar files, evidence, diff |
| Evidence | `qa/evidence/` (evidence index, Java 21 and earlier Surefire reports); all member evidence, unchanged (Z-4) |
| Integration records (Z-5) | `qa/README.md`, `INTEGRATION_LOG.md`, `CROSS_REFERENCE.md`, `FINAL_COMPLETION_TRACKER.md`; `phase3a-…`, `phase4-…`, `phase5a-…`, `phase5b-…`, `phase6-…`, `phase7-…`, `phase9-…`, `phase10-…` (there is no Phase 8 folder: Phase 8A is recorded inside `phase7-final-completion/int01-postgresql/`) |
| PostgreSQL INT-01 | Script, record, and the 16-file run folder `run-20261125-125857/`, including the 4 `.log` files (Z-8) |
| Entry point and integrity | `SUBMISSION_INDEX.md`; `MANIFEST-SHA256.txt` |

**Excluded:** `backend/.env` (never read), `.git/`, `node_modules/`, `dist/`, `backend/target/` (incl. the INT-01 jar), the empty INT-01 `uploads/` folder (a ZIP keeps no empty folders), the outer non-canonical Part 01 copy `Public care/qa/`, `Public care/.vscode/`, `Public care/CLAUDE.md`, the empty `*-structure.txt` files, member repositories' `CLAUDE*.md` and full project copies, temporary clones and caches.

## 5. Build 2 – final ZIP (after this record is saved)

The final ZIP is built after this record, the updated documents and the after-snapshot are saved. It replaces build 1 at the same path, with the same root folder `publiccare/`, and contains 347 files (346 listed in its manifest).

**A file inside the package cannot hold the hash of the package, or of the manifest that lists it.** So the build 2 size, SHA-256, manifest hash and verification results are recorded **outside** the package, in `Public care/submission/PublicCare-EC4206-QA-Submission.VERIFICATION.txt` (replaced for build 2; the build 1 values are kept in that file as history). This record does not claim any build 2 result.

Planned checks (owner's Phase 10 Step 4): ZIP integrity; extraction into a separate folder with two extractors; single root folder; entries equal to this file list; manifest matches every file; no duplicate entries; no `.env`, credentials, private keys or secrets; no build artifacts or temporary files; required reports and evidence present; relative links (disclosed exceptions only); 4 INT-01 logs present; working tree and evidence unchanged; size below 10 MB.

## 6. Git and PostgreSQL

- **Git:** nothing committed or pushed; no branch switch; Git identity unchanged. HEAD `f565fb4` on `qa/final-assignment`. The ZIP is outside the repository.
- **PostgreSQL:** the `postgresql-x64-18` service is running and `pg_isready` reports "accepting connections" (checked 2026-11-25, Phase 10). No `DROP` was issued in any phase, so database `publiccare_int01_qa` and role `publiccare_int01_app` stay as the owner left them. Their existence was **not re-queried** in Phase 10: that needs the `postgres` password, which the integrator does not have and did not look for. Owner check: `psql -U postgres -tAc "select datname from pg_database where datname='publiccare_int01_qa'; select rolname from pg_roles where rolname='publiccare_int01_app';"`. They stay until the owner approves cleanup.

## 7. Limitations and pending decisions (unchanged by Phase 10)

**Academic and technical limitations**

1. INT-01 is **PARTIALLY EXECUTED**: (a), (b), (d) PASS; (c) BLOCKED / NOT EXECUTED (RTM Q2); effective run-time `ddl-auto` NOT VERIFIED. Persistence on PostgreSQL and the database-generated `reference` value are NOT VERIFIED. All automated suites ran on H2.
2. Part 01: 79 planned cases; 2 executed in the group repository (API-04 PASS, INT-01 PARTIALLY EXECUTED); 17 with an execution in any repository; 62 executed by nobody.
3. Member results come from separate private repositories and are not group-repository executions; counts are not added together. No combined run exists (D-5).
4. The Member 3 report is a project-integrated draft that Member 3 did not write, review or approve; it has no personal reflection. Member 4 did not review the integrated revision.
5. Evidence gaps reported, not filled: no SonarQube dashboard screenshots, no issue #1 timeline export, no CI run on Member 3's final commit `93d11a3`, CI `ui` job never ran.
6. Some evidence contains local Windows profile paths (kept unchanged, Z-4).
7. Kept records with broken links: Analysis Report (237) and Member 4 original (14).
8. The package is an uncommitted working-tree snapshot (Z-7).
9. Windows path length: the longest entry is 136 characters, so the package should be extracted into a folder whose full path is about 120 characters or shorter.
10. The empty INT-01 `uploads/` folder is not in the ZIP.

**Pending decisions and confirmations** (shown as PENDING / TBC in the documents; none is decided here)

| Item | Status |
|---|---|
| D-2 (commit D-1), D-3 (PC-QA-01 fix), D-4 (IPv6 fix / RTM Q1), D-5 (member tests into the group repository), D-7 (link-anchor strategy) | PENDING |
| RTM Q1; RTM Q2 (= ENV-P4, needed for INT-01 scenario (c)) | PENDING |
| P-M1-3 (Part 01 co-authors); P-OW-1 – P-OW-4 (project owner, integrator and API-04 tester as roles) | PENDING – not inferred from the member list |
| Planned schedule dates | PENDING / TBC |
| Cleanup of `publiccare_int01_qa` / `publiccare_int01_app` | Awaiting owner approval; kept |
| Commit of the D-1 change and of `qa/` | Not requested; nothing committed |
| Register totals | 36 confirmed / 38 pending / 6 rejected / 6 unavailable (Revision 8, unchanged) |
