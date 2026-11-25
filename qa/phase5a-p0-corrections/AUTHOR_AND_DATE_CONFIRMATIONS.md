# Author, byline and date fields – register for confirmation

Phase 5A, 2026-11-25. Prepared during integration. **No name, student ID or date was filled in.** This register lists every author or byline field that is missing or TBC. It also lists the name forms that already appear in project records, so that the owner and members can confirm them.

**Rule:** only names already present in project records are listed. Nothing is inferred from GitHub account handles, operating-system user names in log paths, or e-mail addresses. **No student ID appears in any project record.**

## 1. Names found in project records

| Member | Name form(s) in records | Where | Uncertainty | Confirmation needed from |
|---|---|---|---|---|
| Member 1 | **None.** | `qa/README.md:39` says "Member 1's name is not recorded in the Part 01 documents" | No name to confirm | Project owner / Member 1: full name and student ID |
| Member 2 | "Oshan Ravishka" | Git commit author (`INTEGRATION_LOG.md:22`); `qa/README.md:30-31` | Only one form found. Spelling not confirmed by the member | Member 2: spelling and student ID |
| Member 3 | "Ishani-Kumaranayaka" (Git author handle, hyphenated); "Ishani Kumaranayaka" (as written by integration) | `INTEGRATION_LOG.md:23`; `3.5-3.6_member3/evidence/ci/README.md:9`; `qa/README.md:32-33` | **Uncertain.** The space form comes from the integrator's reading of a hyphenated handle | Member 3: exact spelling and student ID |
| Member 4 | "S. A. Chathura Madushan" (report byline); "Chathura Madushan" (Git commit author); "Chathura124" (GitHub assignee handle) | `MEMBER4_QA_REPORT.md:3`; `INTEGRATION_LOG.md:24`; `MEMBER4_QA_REPORT.md:49` | Which form to use in the group report (initials or not) | Member 4: preferred form and student ID |
| Project owner | Only the repository owner handle `madushankachamara891-pixel` | `qa/README.md:14`; `INTEGRATION_LOG.md:7` | No personal name recorded | Project owner: name, and whether the owner is also one of Members 1–4 |
| Integrator (Phases 1–5A) | **None** | Integration documents carry no author | No name to confirm | Project owner |

## 2. Author / byline fields that are TBC or missing

| # | File:line | Field | Current value | What is needed | Status |
|---|---|---|---|---|---|
| A-1 | `3.1-3.2_member1/PART01_TEST_PLAN_AND_STLC.md:15` | Authors | "Authors: TBC" | Member 1 name (and any co-authors) | **TBC – blocked on Member 1 name** |
| A-2 | `PART01_TEST_PLAN_AND_STLC.md:375-377` | WS-1/WS-2/WS-3 "Assigned member(s)" | "TBC" | Not to be filled: the workstreams were not used. Add the actual allocation instead (`TEST_PLAN_CORRECTIONS.md` §1) | Prepared |
| A-3 | `PART01_TEST_PLAN_AND_STLC.md:384` | Test coordination "Held by" | "TBC" | Name of whoever coordinated / integrated | **TBC** |
| A-4 | `PART01_TEST_PLAN_AND_STLC.md:385` | Defect tracking and quality metrics "Held by" | "TBC" | Member 4 (supported by Sections 3.8–3.9) – name form per §1 | Prepared; name form to confirm |
| A-5 | `PART01_TEST_PLAN_AND_STLC.md:391` | Project owner "Person" | "TBC" | Owner's name | **TBC** |
| A-6 | `3.1-3.2_member1/PART01_REQUIREMENTS_AND_RTM.md:1-12` | No author field exists | – | Member 1 name, if the RTM is submitted as a separate document | Missing |
| A-7 | `3.1-3.2_member1/PROJECT_ANALYSIS_REPORT.md` | No author field exists | – | None, if excluded from the submission (Phase 4 §8.1 recommendation, D-6) | Missing; decision D-6 |
| A-8 | `3.3-3.4_member2/PART02_BLACK_BOX_AND_WHITE_BOX_TESTING.md:1-7` | No byline (title, module, sections, date only) | – | "Member 2 · <name> · <student ID>" | Missing |
| A-9 | `3.5-3.6_member3/FORMAL_REPORT_SKELETON_3.5-3.6.md` §0 | Author, date | Report not written | Member 3 name, student ID and report date | **TBC – report not submitted** |
| A-10 | `3.7-3.10_member4/MEMBER4_QA_REPORT.md:3` | Byline | "Member 4 · S. A. Chathura Madushan · 24 November 2026" | Student ID only | Present; ID missing |
| A-11 | `PART01_TEST_PLAN_AND_STLC.md` §10.4, API-04 block | "Execution details" row (template at TP:646): tester, date, baseline, environment | Not present; tester not named | Name of the person who ran API-04 on 25 Nov 2026 (integration) | **TBC** |
| A-12 | Final group report (not yet compiled) | Contribution table (Phase 4 P0-2) | Does not exist | Name, student ID and sections for each member | **TBC** |

## 3. Dates

| # | Date field | Recorded value | Status |
|---|---|---|---|
| D-a | Part 01 document date (TP:15) | 23 Nov 2026 (draft), with revisions on 25 Nov 2026 | Recorded |
| D-b | Member 2 execution date (PART02:5) | 24 November 2026; E10 log 2026-11-24T11:10:53+05:30 | Recorded |
| D-c | Member 3 run dates (READMEs) | 2026-11-24; CI 2026-11-24T04:24:04Z | Recorded |
| D-d | Member 3 report date | – | **TBC** (report not written) |
| D-e | Member 4 report date (`MEMBER4_QA_REPORT.md:3`) | 24 November 2026 | Recorded |
| D-f | API-04 execution (Phase 3A) | 2026-11-25, logs 07:23–07:26 +05:30 | Recorded |
| D-g | Planned start/end dates, TP §7.8 lines 413-421 | "TBC" | **TBC – no planned dates in any record; must not be back-filled from actual dates** |
| D-h | Final submission deadline, TP §7.8 line 422 | "TBC" | **TBC – not in project records. Take it from the module brief or LMS** |
