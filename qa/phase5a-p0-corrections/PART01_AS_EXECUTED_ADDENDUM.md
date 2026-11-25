# Part 01 – "As Executed" addendum (prepared, not yet applied)

> **Status: PREPARED IN PHASE 5A (2026-11-25). NOT YET APPLIED TO PART 01.**
> This addendum is meant to be added to `3.1-3.2_member1/PART01_TEST_PLAN_AND_STLC.md` (and referenced from the RTM). It sits next to the original plan and **does not replace** it. Every planned value in Part 01 stays as written. This addendum records what was actually executed, by whom (by member role), on which baseline and in which environment.
> Applying it needs: the owner's decision on the canonical Part 01 copy (Phase 4 D-8), and the author/date confirmations in [AUTHOR_AND_DATE_CONFIRMATIONS.md](AUTHOR_AND_DATE_CONFIRMATIONS.md).
> *[Phase 5B rev. 2, 25 Nov 2026: D-8 decided – APPROVED, option CAN-1; the target is the canonical copy `publiccare/qa/3.1-3.2_member1/` only. The author/date confirmations are still pending, and applying this addendum still needs separate approval. **Not applied.** See [CONFIRMATION_REGISTER.md](../phase5b-confirmation-register/CONFIRMATION_REGISTER.md) §5.1 (UB-1).]*
> *[Phase 5B rev. 3, 25 Nov 2026: **applied** to the canonical Test Plan as the section "As Executed Addendum" ([PART01_TEST_PLAN_AND_STLC.md](../3.1-3.2_member1/PART01_TEST_PLAN_AND_STLC.md#as-executed-addendum)), with the Java 21 result written in and the open author/tester fields shown as PENDING. Deviations from this text are listed in the register §5.2. This file is kept unchanged below as the Phase 5A source.]*

**This addendum does not claim that a full integrated test run has passed.** No run exists that combines all members' tests. A combined Java 21 run is a next step (see [README.md](README.md) §7).

## A. Counts – three different numbers that must not be mixed

| Quantity | Value | What it is | Verified by |
|---|---:|---|---|
| Original baseline backend test **methods** | **72** | `@Test` methods in 12 classes at `f565fb4` (the 73rd text match is `@TestConfiguration`, `AuthControllerTest.java:45`) | `git grep` on commit `f565fb4` (Phase 5A); Surefire 72/0 in M2, M3, M4 and Phase 3A baseline runs |
| Part 01 proposed **test cases** | **79** | MT 22, UI 14, API 25, UT 17, INT 1, defined in TP §10.4 and RTM TASK 6 | 79 unique `####` case headings in TP §10.4, no duplicates (Phase 5A recount) |
| Tests run on this branch after Phase 3A | 85 | 72 existing + 13 new (`AuthEmailNormalizationApiTest` 7, `AuthDtosTest` 6) | `phase3a-api04-correction/03-full-suite-after-fix.txt` |

72 and 85 are counts of **JUnit tests**. 79 is a count of **Part 01 test cases**. A JUnit test is not a Part 01 test case, so these numbers are not comparable.

## B. Part 01 test cases – execution status as executed

Status vocabulary: TP §10.3 (`PASS`, `FAIL`, `BLOCKED`, or `PROPOSED / NOT EXECUTED`).

| Part 01 case(s) | Status | Baseline | Environment | Evidence |
|---|---|---|---|---|
| **API-04** – duplicate email with different case or padding | **PASS** (25 Nov 2026) | `f565fb4` + uncommitted owner decision D-1 (`AuthDtos.java` trims the email before validation) | **Java 27** (Oracle 27+35), Maven Wrapper 3.9.16, Spring Boot 3.5.16, **H2**, **MockMvc** (`@SpringBootTest` + `@AutoConfigureMockMvc`); not a real HTTP server or browser | `phase3a-api04-correction/` (logs 00–03, Surefire XML 6/0 and 7/0) |
| All other **78** cases | **PROPOSED / NOT EXECUTED** in Part 01 | – | – | No Part 01 execution on the group baseline. Some of them were exercised in member copies; those runs are listed **separately** in §C and do **not** change this status |

**Not executed for the Phase 3A code:**

| Verification | Status |
|---|---|
| Backend suite on **Java 21** for `f565fb4` + D-1 | **NOT EXECUTED** (only Java 27 was available in Phase 3A) *[Phase 5B rev. 1, 2026-11-25: superseded – executed on Java 21 (Ubuntu OpenJDK 21.0.13.1, WSL2 Ubuntu): 85 tests, 0 failures, 0 errors, 0 skipped, H2 in-memory. See [CONFIRMATION_REGISTER.md](../phase5b-confirmation-register/CONFIRMATION_REGISTER.md) ENV-J6]* |
| Any run on **PostgreSQL** for `f565fb4` + D-1 (or for any member) | **NOT EXECUTED** (all runs used H2) |
| MT-04 (manual UI duplicate email with padding) | **NOT EXECUTED** |

## C. Member-specific isolated runs (reported separately – not Part 01 results)

These runs were made in **separate member copies** of the application, with **different baselines** (see [BASELINE_DEVIATIONS.md](BASELINE_DEVIATIONS.md)) and **different JDKs**. They are evidence for Sections 3.3–3.10. They are **not** recorded as Part 01 PASS results, because none of them ran on the Part 01 baseline. Most of them also cover only part of the case.

### C.1 Member 3 (Sections 3.5–3.6) – copy `93d11a3`, local Windows 11, Temurin 25.0.5, H2

Coverage classification is Member 3's own (unapplied diff `part01-execution-status-edits_2c1e597..93d11a3.diff`).

| Part 01 case | Member 3 result | Coverage of the case | Evidence |
|---|---|---|---|
| UT-14 | Red → Green → Refactor; 6/0 after an **application change in the M3 copy** | Full (per M3). Depends on the unapplied `AdminSetupRunner` change; RTM Q1 open | `3.5-3.6_member3/evidence/tdd/` |
| API-01 | PASS | Full (per M3) | `…/api-automation/` |
| API-02 | PASS | Partial (steps 1–3) | same |
| API-03 | PASS | Partial (password 11/12 only) | same |
| API-04 | Case: `409` as expected. Padding: `400` – **superseded** by D-1 | Partial; superseded by §B | same |
| API-08 | PASS | Partial (steps 1–2) | same |
| API-09 | PASS | Partial (1 endpoint, `GET /api/admin/reports`) | same |
| API-10 | PASS | Partial (steps 1–2) | same |
| API-15 | PASS | Partial (GET only) | same |
| UI-01, UI-02, UI-03, UI-04, UI-05, UI-13 | PASS (Selenium, Chrome 157 headless, local only) | Full (per M3) | `…/ui-automation/` |

Totals for Member 3: 8 cases fully exercised and 7 partly exercised, all in the Member 3 copy. The 91-test backend suite also passed on GitHub Actions (Java 21) for commit `70d01e0` only. The Selenium tests have no CI result.

### C.2 Member 4 (Sections 3.7–3.10) – copy `b97bd96` (+ `f78deb1` for the "after" run), Linux, Azul Zulu 21.0.13.1, H2

Member 4 did not cite these Part 01 IDs. The link is an **[integrator note]**.

| Part 01 case | What Member 4 did | Coverage of the case | Evidence |
|---|---|---|---|
| API-08 | Anonymous `GET /api/auth/me` → `401 {"error":"Authentication required."}` | Step 1 only | `3.7-3.10_member4/evidence/access-checks.txt` |
| API-09 | Anonymous `GET /api/admin/reports` → `401` | Anonymous row, 1 endpoint | same |
| API-21 | `GET /api/health` → `200 UP`; 100/100 JMeter samples | `/api/health` only (`/actuator/health` not checked) | `access-checks.txt`, `health.jtl` |
| UI-04 | Signed-out create page at 390 × 844: submit disabled, sign-in link | Related; used a synthetic `401` session fixture | `evidence/mobile.txt` |
| MT-02 | Not executed. PC-QA-01 is the **recovery path after** the MT-02 mismatch step | Related only | `registration-before.txt/.png` |

### C.3 Member 2 (Sections 3.3–3.4) – copy `0ad36bd`, Windows 11, Microsoft OpenJDK 21.0.13.1, H2/MockMvc

Member 2 used its own IDs (EP-01–EP-05, BVA-01–BVA-07, DT-01–DT-06, paths A1–A3, B1–B2). These relate to Part 01 cases at a different test level (API-03, API-11, API-05/09/20, UT-01/03, API-07; see `CROSS_REFERENCE.md` §2). **No Part 01 case was executed as written.**

## D. Status statements in Part 01 that this addendum scopes (not deleted)

| Part 01 statement (current text kept) | Read it as |
|---|---|
| TP header: "No test case has been executed, and it records no actual results, pass/fail data, defects or measured quality values" (exception: API-04) | True **for this document's Part 01 test cases**. Executed work for Sections 3.3–3.10 is reported by Members 2–4 in their own sections |
| TP §5.3 "Verified quality result: None" | No ISO 25010 attribute is verified **by Part 01 test cases**. Members 2 and 4 report measured coverage (M2: lines 433/641, branches 126/302; M4: lines 434/642, branches 126/302), SonarQube measures (M4) and a JMeter baseline (M4). These are measurements from separate copies, not verified Part 01 quality results |
| TP §7.3 rows 3.3–3.10 "Planned"; §7.8 rows 4–10 "Not started"; §7.9 "Not started"; §8 phases 4–5 "Not started" | Planned values at the time of writing (23 Nov 2026). Actual state on 25 Nov 2026: see [TEST_PLAN_CORRECTIONS.md](TEST_PLAN_CORRECTIONS.md) §3 |
| RTM line 4 "None of them have been run" | Same scope as the TP header |

## E. What this addendum does not claim

- No combined run of all members' tests exists. Test counts from different copies (72, 84, 91, 6, 72, 85) must not be added together.
- No Part 01 case other than API-04 is recorded as PASS.
- Nothing has been run on Java 21 or on PostgreSQL for the code now in the integration working tree. *[Phase 5B rev. 1, 2026-11-25: superseded for Java 21 only – the backend suite of the integration working tree ran on Java 21 (85/0, H2; register ENV-J6). Nothing has been run on **PostgreSQL**.]*
- Member 3's Java 21 CI result applies to commit `70d01e0` only.
- PC-QA-01 is fixed only in the Member 4 copy. It is **still open** in the group baseline.
