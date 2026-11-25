# Phase 9 – Final submission decisions and package readiness

| Item | Value |
|---|---|
| Date | 2026-11-25 |
| Starting state | Verified Phase 8A state. Branch `qa/final-assignment` at `f565fb4` (local only). Working tree: `M AuthDtos.java` (D-1), 2 untracked D-1 test classes, untracked `qa/`. Nothing committed or pushed |
| Inputs | [ZIP_CONTENTS_PROPOSAL.md](../phase7-final-completion/ZIP_CONTENTS_PROPOSAL.md); [confirmation register](../phase5b-confirmation-register/CONFIRMATION_REGISTER.md) Rev. 6; [tracker](../FINAL_COMPLETION_TRACKER.md); the owner's Phase 9 instruction and stated preferences |
| Snapshot before edits | [qa-sha256-before-phase9.txt](qa-sha256-before-phase9.txt) (218 files under `qa/`) |
| Not done in Phase 9 | **No ZIP created.** No commit, push or branch switch. INT-01 not re-run. RTM Q2 not answered and no pre-Flyway history assumed. PostgreSQL database `publiccare_int01_qa` and role `publiccare_int01_app` **kept**. No evidence file, application code, test or build file changed. No completed test or audit repeated |

## 1. PostgreSQL status (unchanged, carried forward)

INT-01 = **PARTIALLY EXECUTED**: (a) PASS; (b) PASS; (c) **BLOCKED / NOT EXECUTED** (RTM Q2); (d) PASS, effective run-time `ddl-auto` **NOT VERIFIED**. RTM Q2 stays PENDING (register ENV-P4). The 16 run files were re-hashed in Phase 9: all match the SHA-256 values in the [INT-01 record](../phase7-final-completion/int01-postgresql/README.md).

## 2. Member details

Supplied by the project owner, exact forms (initials not expanded). The owner confirmed in Phase 9 that the order given is Members 1–4:

| Member | Name | Registration number | Sections |
|---|---|---|---|
| Member 1 | Madusanka D.M.C | EG/2023/5720 | 3.1–3.2 |
| Member 2 | Dharmasiri S.O.R | EG/2023/5559 | 3.3–3.4 |
| Member 3 | Kumaranayaka K.I.S | EG/2023/5697 | 3.5–3.6 |
| Member 4 | Madushan S.C | EG/2023/5716 | 3.7–3.10 |

**Check before Phase 9:** no registration number and no Member 1 name appeared in any document. Every final document showed the names as PENDING or used earlier forms. **Omissions corrected** (marked *[Phase 9]* notes; earlier values kept in the note):

| Document | Field |
|---|---|
| Test Plan | New header row "Author (sections 3.1–3.2)"; §7.6 "Work allocation as executed" (4 rows); cross-cutting duty "Defect tracking"; addendum "Confirmations still pending" table (2 rows) |
| RTM | New "Author" row (the RTM had no author field, A-6) |
| PART02 | Byline "Member 2: Dharmasiri S.O.R · EG/2023/5559" (was missing, A-8) |
| Member 3 draft | §0 "Assigned member"; §10 pending row P-M3-2, P-M3-3 |
| Member 4 integrated revision | §0.1 byline row |
| `qa/README.md` | Note under "Sections and responsibilities" |
| Confirmation register | Revision 7: P-M1-1, P-M1-2, P-M2-1, P-M2-2, P-M2-3, P-M3-2, P-M3-3, P-M4-12 → CONFIRMED. Totals 28 / 46 / 6 / 6 → **36 / 38 / 6 / 6** (recounted mechanically) |
| `SUBMISSION_INDEX.md` (new) | Contribution table |

**Not changed:** Member 4's original report (byline "S. A. Chathura Madushan"). Its initials "S. A." differ from the owner-supplied "S.C"; this is recorded in the integrated revision, the register and the index, not corrected. Phase records (Phases 1–8A) keep their earlier wording as the record of that time. **Still PENDING:** P-M1-3 (Part 01 co-authors) and P-OW-1 – P-OW-4 (owner, integrator and API-04 tester as roles). They are not inferred from the member list.

## 3. ZIP decisions Z-1 – Z-8

"Owner preference" = stated by the owner in the Phase 9 instruction. "Awaiting approval" = the proposal needs an explicit owner decision that has not been given; the recommendation is **not** treated as approved.

| # | Decision | Chosen option | Basis | Status |
|---|---|---|---|---|
| Z-1 | Archived `PROJECT_ANALYSIS_REPORT.md` (also settles D-6 for this package) | **(a) Include, labelled ARCHIVED** – superseded by the RTM. The label is in `SUBMISSION_INDEX.md` §2; the file itself stays unchanged (the RTM states the archive is kept unchanged). Its 237 broken links are disclosed | Owner preference | **DECIDED** |
| Z-2 | Member 4's original `MEMBER4_QA_REPORT.md` | **(a) Include** beside the integrated revision, labelled ORIGINAL; the integrated revision is the submission version. 14 broken links disclosed | Owner preference | **DECIDED** |
| Z-3 | PART02's 20 broken links | **(b) Repair** – the 20 mechanical rewrites from INTEGRATION_LOG §7, with a marked note in PART02. **Applied in Phase 9**: 20 / 20 links resolve. The note discloses that `AuthDtos.java` now carries D-1 | Owner preference | **DECIDED – APPLIED** |
| Z-4 | Evidence with local Windows profile paths | **(a) Include unchanged**; disclosed in `SUBMISSION_INDEX.md` §4 | Owner preferences "preserve original evidence files unchanged" and "keep limitations transparent" | **DECIDED** – please confirm, because the preferences rule out editing but do not literally rule out (b) leaving files out |
| Z-5 | Integration records (`qa/phase3a…` – `qa/phase9…`, log, tracker, register, cross-reference) | **(a) Include** | Recommendation; supports "keep execution limitations transparent" | **AWAITING APPROVAL** |
| Z-6 | Names and registration numbers | Supplied and applied (§2). No "PENDING" byline remains for Members 1–4 | Owner-supplied details; mapping confirmed in Phase 9 | **RESOLVED** |
| Z-7 | Commit before packing | **(a) ZIP of the uncommitted working tree.** The Phase 9 instruction forbids committing, so (b) is not possible now. The index states the uncommitted state. ZIP content is the same either way | Owner's choice | **AWAITING APPROVAL** |
| Z-8 *(new, Phase 9 audit)* | INT-01 application logs `02a-run1-fresh-database.log`, `02a-…stderr.log`, `03a-run2-restart.log`, `03a-…stderr.log` are **ignored by `.gitignore` (`*.log`)**. The proposal's selection method (`git ls-files -co --exclude-standard`) would silently drop them, although they are the main evidence for (a), (b) and (d) | **Include explicitly**, by adding the 4 paths to the file list (no `.gitignore` change) | Owner preference "preserve original evidence"; ZIP proposal §4 left the run folder to the owner's decision | **AWAITING APPROVAL** |

## 4. Proposed ZIP

File: `Public care/submission/PublicCare-EC4206-QA-Submission.zip` (outside the Git repository). Root folder inside the ZIP: `publiccare/`.

```
publiccare/
├── SUBMISSION_INDEX.md                  (new – entry point for the marker)
├── MANIFEST-SHA256.txt                  (new – generated at packing time)
├── README.md, AUTH_LOCAL_VERIFICATION.md, .gitignore
├── package.json, package-lock.json, index.html, vite.config.js, eslint.config.js
├── backend/          73 files  source, tests (incl. 2 D-1 tests), pom.xml, Maven Wrapper, migrations V1–V5, .env.example
├── src/              36 files  frontend source
├── public/            2 files
└── qa/
    ├── README.md, INTEGRATION_LOG.md, CROSS_REFERENCE.md, FINAL_COMPLETION_TRACKER.md
    ├── 3.1-3.2_member1/       3   Test Plan, RTM (canonical), Analysis Report (ARCHIVED)
    ├── 3.3-3.4_member2/      15   PART02 (links repaired), brief, E01–E10, reference tests, diff
    ├── 3.5-3.6_member3/      67   Report 3.5–3.6 (project-integrated draft), skeleton, missing-report note, evidence, reference code, diffs
    ├── 3.7-3.10_member4/     22   Integrated revision, ORIGINAL report, JMeter/Playwright/Sonar files, evidence, diff
    ├── evidence/             58   Evidence index; Java 21 and earlier Surefire reports
    ├── phase3a-api04-correction/   10
    ├── phase4-integration-audit/    2
    ├── phase5a-p0-corrections/      8
    ├── phase5b-confirmation-register/ 1
    ├── phase6-member-repo-verification/ 4
    ├── phase7-final-completion/    24   incl. INT-01 script, record, 16-file run folder (4 .log files added explicitly, Z-8)
    └── phase9-final-submission/     4   this record, file list, before/after hash snapshots
```

### 4.1 Included files – exact list

**[PACKAGE_FILE_LIST.txt](PACKAGE_FILE_LIST.txt)** – every path, one per line, relative to `publiccare/`. Built as `git ls-files -co --exclude-standard` **plus** the 4 INT-01 `.log` files (Z-8) **plus** `MANIFEST-SHA256.txt` (written at packing time). The list is final only for the files present now; if any file is added or removed before packing, the list is regenerated and shown again.

### 4.2 Excluded

| Excluded | Reason |
|---|---|
| `backend/.env` | Local configuration; may hold credentials; gitignored; **not read** in any phase |
| `backend/target/` (incl. the INT-01 jar), `dist/`, `node_modules/` | Build output or downloaded dependencies, reproducible from source |
| `.git/` | Repository history is not a deliverable; branch and baseline are named in the index |
| `Public care/qa/` (outer, 3 files) | Non-canonical reference copy (D-8, CAN-3); the canonical Part 01 copy is in the package |
| `Public care/.vscode/`, `Public care/CLAUDE.md`, empty `backend-structure.txt` / `frontend-structure.txt` | Not part of the project |
| Member repositories' `CLAUDE.md`, `CLAUDE_PROMPT.md`, full `project/` copies, temporary/bare clones | Never integrated (INTEGRATION_LOG §3); temporary material |
| `qa/…/run-20261125-125857/uploads/` | Empty folder; a ZIP keeps no empty folders. Disclosed in the index |

## 5. Package audit (Phase 9)

| Check | Result |
|---|---|
| Existence and readability | Every listed path exists and is a readable regular file (checked against the list) |
| Report versions | One submission version per section: Test Plan + RTM (canonical, D-8), PART02, Report 3.5–3.6 (draft), Report 3.7–3.10 (integrated). The other report-like files are labelled ARCHIVED / ORIGINAL / SUPERSEDED in the index. The outer Part 01 copy is excluded |
| Relative links (checked against the package list, not the disk) | All documents except the two kept records: **0 broken**, including canonical Part 01, PART02 (20 / 20), both reports, `SUBMISSION_INDEX.md` and all records; line anchors within range. Kept records: Analysis Report 237 broken, Member 4 original 14 broken (Z-1, Z-2; disclosed) |
| Evidence provenance | Pre-Phase 9 snapshot vs now: only the 7 edited documents changed; no evidence file changed. INT-01 run files: 16 / 16 match the recorded SHA-256 |
| Secrets | Pattern scan of every package file: no credential. Only matches are synthetic test passwords in test code and logs (`Synthetic-Pass-123!`, `long-test-password`) and the placeholders in `.env.example`. `backend/.env` is excluded and was not read |
| Member details | Exact forms in all final documents (§2) |
| Status wording | Checked for PASS / FAIL / BLOCKED / NOT EXECUTED. Three pre-Phase 8A statements that could be read as "PostgreSQL never used" now carry marked notes (Member 3 draft §7 and §9 point 2; Member 4 revision §3.10 point 7 and recommendation 4). "Not run on PostgreSQL" statements for the backend test suite (Test Plan) are accurate as written: INT-01 is not the test suite |
| PostgreSQL limitations | Stated in the index §3 and the INT-01 record as in §1 above; no overall PASS anywhere |

**Remaining residuals, not blocking:** the `qa/README.md` header still reads "Integration draft – not final" (the Phase 1 record; a Phase 9 pointer to the index is added). Part 01 co-authors and the owner/integrator/tester roles are PENDING. RTM Q1/Q2 and D-2 – D-5, D-7 are open and disclosed.

## 6. After approval (not done yet)

1. Regenerate the file list; it must equal [PACKAGE_FILE_LIST.txt](PACKAGE_FILE_LIST.txt).
2. Write `MANIFEST-SHA256.txt`; create the ZIP outside the repository.
3. Verify as in the proposal §6 (file list, manifest, no `.env` / `target/` / `node_modules/` / `.git/`, credential scan, link check on the extracted copy, Windows extractor, size).
4. Only then, and only on the owner's approval, drop the INT-01 database and role.
