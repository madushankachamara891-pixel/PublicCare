# Requirement mapping review – Member 3 BDD scenario 4 and Member 4 checks

Phase 5A, 2026-11-25. Prepared during integration. Documents only. No member evidence, feature file or reference code was edited.

Requirement definitions are quoted from `3.1-3.2_member1/PART01_REQUIREMENTS_AND_RTM.md` (TASK 1 tables and TASK 5 RTM). Test-case definitions are taken from `3.1-3.2_member1/PART01_TEST_PLAN_AND_STLC.md` §10.4.

## 1. Member 3 BDD scenario 4 – "A citizen cannot change the status of a report"

### Evidence of what the scenario does

| Source | Content |
|---|---|
| `3.5-3.6_member3/reference-code/project/backend/src/test/resources/features/admin_status_update.feature:18-22` | Given a citizen has submitted a report → When the citizen tries to set the report status to "RESOLVED" → Then the request is rejected because access is denied → And the report status is still "PENDING" for the citizen |
| `…/bdd/PublicCareSteps.java:222-226` | The When step sends `PATCH /api/admin/reports/{id}/status` **as the citizen**, with a CSRF token, body `{status, adminUpdateNote}` |
| `…/bdd/PublicCareSteps.java:228-231` | The Then step asserts HTTP `403` |
| `…/bdd/PublicCareSteps.java:233-236` | The last step reads `GET /api/reports/{id}` as the owner and checks that the status is unchanged |
| `3.5-3.6_member3/evidence/bdd/02b-run2.txt`, `03-full-suite.txt` | 4 scenarios, 0 failures; 82/0 full suite (Java 25.0.5, H2) |

No step sends a `DELETE` request.

### Requirements compared

| Requirement | Definition (RTM) | Matches scenario 4? |
|---|---|---|
| **FR-02.8** Server-side authorisation | "`/api/admin/**` needs `ROLE_ADMIN` (`403` otherwise). Everything else needs authentication (`401`)" | **Yes, primary.** A citizen request to an `/api/admin/**` route gets `403` |
| **FR-06.6** Direct status/note change | "Admin, CSRF. `PATCH /api/admin/reports/{id}/status` `{status, adminUpdateNote}`…" | **Yes, the endpoint under test** (negative case: the wrong role) |
| FR-08.5 Citizens cannot delete reports | "No delete route exists under `/api/reports`. Admin route → `403` for citizens" | **No.** The scenario does not delete anything |

### Conclusion

The mapping can be established from the code and the scenario. **FR-02.8 (primary) and FR-06.6.** FR-08.5 is not supported. In Part 01 terms, the scenario executes one cell of **API-09** (citizen × `PATCH …/status` → `403`). It does not execute the whole API-09 matrix.

| Where the old mapping appears | Action in Phase 5A |
|---|---|
| `CROSS_REFERENCE.md` §3 (Member 3 table) | **Corrected** (marked *[Corrected: Phase 5A]*) |
| `admin_status_update.feature:6` (feature-level comment lists FR-06.6, FR-05.2, FR-02.8, FR-08.5) | Not edited. It is a reference copy of Member 3's commit `93d11a3` |
| `3.5-3.6_member3/evidence/bdd/README.md:23` | Not edited. Member 3's own evidence |
| Member 3 formal report (not yet written) | Member 3 should use FR-02.8 / FR-06.6 (see `../3.5-3.6_member3/FORMAL_REPORT_SKELETON_3.5-3.6.md`) |

**Member 3 confirmation needed:** whether Member 3 accepts the corrected mapping for their own report. If Member 3 meant a different requirement, they should say which one. Nothing in the evidence supports FR-08.5.

The other scenarios were also checked. No change is recommended:

| Scenario | Stated mapping | Check |
|---|---|---|
| 1 Register, log in, submit | FR-01.1, FR-01.5, FR-02.2, FR-03.1, FR-03.3 | Consistent with the steps (`citizen_report.feature:9-17`) |
| 2 Visitor cannot submit | FR-02.8 | Consistent (anonymous write → `401`) |
| 3 Admin moves report to in progress | FR-06.6, FR-05.2 | Consistent (admin `PATCH …/status` `200`; owner sees the note) |

## 2. Member 4 group-level mappings

Member 4 maps checks to whole requirement groups ("FR-01", "FR-02", "FR-06") in `MEMBER4_QA_REPORT.md:20, :34-36`, repeated in `CROSS_REFERENCE.md` §3. Part 01 traces at FR-xx.y level. The table recommends specific IDs **only where the evidence supports them**. These recommendations are **not applied** to the Member 4 report or to `CROSS_REFERENCE.md`. Member 4 should confirm them, or they can be applied in the group report.

| # | Member 4 check | Current mapping | Evidence | Recommended requirement(s) | Related Part 01 case | Confidence and caveats |
|---|---|---|---|---|---|---|
| 1 | Anonymous `GET /api/admin/reports` → `401` | FR-06 (and OWASP Broken Access Control) | `evidence/access-checks.txt`: `{"error":"Authentication required."} HTTP 401` | **FR-02.8** (server-side authorisation); endpoint is **FR-06.2** (admin list/search); **NFR-SEC** | API-09, anonymous row, this one endpoint only | High. Covers 1 of 3 identities and 1 of the admin endpoints listed in API-09 |
| 2 | Anonymous `GET /api/auth/me` → `401` | FR-02 | `access-checks.txt`: `{"error":"Authentication required."} HTTP 401` | **FR-02.5** (current-user lookup: "Without a session → `401 {"error":"Authentication required."}`"). FR-02.8 as secondary | API-08 step 1 only | High. The observed body matches the FR-02.5 expected result exactly. *Note: the Phase 4 audit (P2-2) suggested FR-02.8 here. FR-02.5 is more specific, because it names this endpoint and this response* |
| 3 | Registration typo recovery (PC-QA-01) | FR-01 | `registration-before.txt`: `valuesMatch:true, message:"Passwords do not match.", submitted:false`; `registration-after.txt` | **FR-01.6** (client-side checks: password confirmation must match) | Related to MT-02 and UI-01. **Not an execution of either:** both test the mismatch block, while PC-QA-01 is the recovery path after it | High for FR-01.6. Do not record MT-02 or UI-01 as executed because of this check |
| 4 | Signed-out create page at 390 × 844 | FR-03.8 | `evidence/mobile.txt` line 1 | **FR-03.8** (already specific; no change) | UI-04 (Member 4 does not cite it) | High. The session response was a synthetic `401` fixture (`MEMBER4_QA_REPORT.md:35`), so this is not an end-to-end signed-out session |
| 5 | Keyboard login, Show/Hide password | FR-02 | `mobile.txt` line 2: Tab email → password; toggle both ways; fits 390 px | **NFR-USA** (RTM §2.4 lists "Show/hide password" and keyboard operation); FR-02.2 only as the page context. Width fit relates to **NFR-COMP** | Small part of MT-16 (keyboard-only walkthrough). **Not** an execution of MT-16: no screen reader, no account menu, no other pages | Medium. No FR row covers show/hide or keyboard order, so a group-level FR ID would be invented |
| 6 | JMeter 5 × 20 on `/api/health` | No requirement; TP OQ-4 | `health.jtl` 100/100 success; `jmeter-console.txt` | Endpoint: **FR-09.1** (public health checks). Performance: **no requirement exists** (RTM §2.2 has no RTM row; OQ-4 open) | API-21 (functional part only; Member 4 does not cite it) | High for FR-09.1. No performance pass/fail can be recorded without a target |
| 7 | SonarQube and JaCoCo | TP §2 Maintainability (RTM §2.5) | `sonar-*.json`, `jacoco.csv` | No change. Maintainability has no RTM row | – | – |

**Not supported by evidence (left as is):** no FR-level ID is proposed for the JMeter performance figures, the SonarQube rule findings or the participant-free usability method.

**Member 4 confirmation needed:** rows 1–5 (specific IDs replacing group-level IDs). Row 2 differs from the Phase 4 audit's suggestion, so it needs a decision.
