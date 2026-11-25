# Baselines and code deviations by member

Phase 5A, 2026-11-25. Prepared during integration. **No code change was applied in Phase 5A.**

**Integration working tree at the time of this check:** branch `qa/final-assignment`, HEAD `f565fb4` (= `main` = `origin/main`). `git status` shows:

- `M backend/src/main/java/lk/publiccare/backend/auth/AuthDtos.java`
- `?? backend/src/test/java/lk/publiccare/backend/auth/AuthDtosTest.java`
- `?? backend/src/test/java/lk/publiccare/backend/auth/AuthEmailNormalizationApiTest.java`
- `?? qa/`

`git diff --quiet HEAD -- src backend/pom.xml backend/src/main/java/lk/publiccare/backend/admin` confirms that the frontend source, `pom.xml` and the admin package are unchanged from `f565fb4`.

## 1. Summary table

| Member | Tested commit / baseline | Code changes included in the tested copy | In the current integration working tree? | Owner approval still required? |
|---|---|---|---|---|
| **Member 1** (3.1–3.2) | Static analysis of `f565fb4`. No tests run by Member 1 | None | n/a | No |
| **Member 2** (3.3–3.4) | Repository `PublicCare-QA-Member2-Oshan-`, `main`, commit `0ad36bd` (test changes since handover `962aeaf`). Production code identical to `f565fb4` | **Test code only:** `AuthControllerTest` + `registrationEquivalencePartitionTest` (5 `@CsvSource` rows, EP-01–EP-05); `ReportControllerTest` + `reportTitleBoundaryTest` (7 rows, BVA-01–BVA-07). 66 added lines, 0 removed | **No.** Neither method exists in the working tree (0 matches) | **Yes, to add them to the group repository** (decision D-5; TP §7.11). Not needed to keep them as evidence |
| **Member 3** (3.5–3.6) | Repository `PublicCare-QA-Member3-Ishani-`, branch `member3-tdd-bdd-ci`, commit `93d11a3` (handover `2c1e597`). CI run #1 on `70d01e0`. Results **pre-date** owner decision D-1 | (a) **Production code:** `AdminSetupRunner.java`: `isLoopbackPostgres` made package-private, `[::1]` accepted, helper `isLoopbackHost` extracted (+8 / −4). (b) **`pom.xml`:** +45 lines of test-scope dependencies (`cucumber-bom` 7.23.0, `cucumber-java`, `cucumber-spring`, `cucumber-junit-platform-engine`, `junit-platform-suite`, `rest-assured`, `selenium-java`). (c) **Tests:** 6 classes (`AdminSetupRunnerLoopbackTest`, `PublicCareApiTest`, `PublicCareUiIT`, 3 BDD support classes) and 2 feature files. (d) **CI:** `.github/workflows/ci.yml`. (e) **Docs:** Part 01 status edits on an older Test Plan | **No.** `AdminSetupRunner.java` has no `[::1]` and still declares `private static boolean isLoopbackPostgres`; `pom.xml` has no Cucumber, REST Assured or Selenium entry; no `bdd/`, `api/`, `ui/` test packages; no `.github/` | **Yes.** (a) decision D-4, which also needs RTM Q1 answered. (b)–(d) decision D-5. (e) **Do not apply** (older Test Plan; its API-04 part is superseded by D-1; use the As Executed addendum instead) |
| **Member 4** (3.7–3.10) | Repository `PublicCare-QA-Member4-Chathura-`, branch `chathura`, report at commit `1c8e07b`. Handover `b97bd96` for the backend suite, JMeter, OWASP checks and the "before" registration run. **Handover + `f78deb1`** for the "after" registration run and the SonarQube scan | `src/AccountPage.jsx`: one line. The password input gets `onChange` that clears the confirmation field's custom validity (fix for PC-QA-01). QA scripts (`health.jmx`, `registration.cjs`, `usability.cjs`, `sonar-project.properties`) are test artefacts, not application code | **No.** `register-password` input has no `onChange` in the working tree; only `register-confirm-password` clears its own validity, which reproduces PC-QA-01 | **Yes** (decision D-3). Until then PC-QA-01 is **open** in the group baseline |
| **Integration – Phase 3A** (API-04) | `f565fb4` + D-1 | `AuthDtos.java`: compact constructors trim the email before validation for `RegisterRequest` and `LoginRequest` (+17 lines). Tests: `AuthEmailNormalizationApiTest` (7), `AuthDtosTest` (6) | **Yes, uncommitted.** Stored diff identical to the live `git diff` (Phase 4) | **D-1 approved** (recorded in RTM and `phase3a-api04-correction/README.md`). **Committing** still needs approval (D-2 / P0-5) and should follow a Java 21 run |

## 2. Interactions to expect if changes are combined (from the evidence; none verified by a run)

| If applied | Expected effect | Basis |
|---|---|---|
| M3 tests (c) without M3 production change (a) | `AdminSetupRunnerLoopbackTest` **will not compile** (`isLoopbackPostgres` has private access) | M3 `tdd/01-red-compile-error.txt` shows this exact error |
| M3 `PublicCareApiTest` together with D-1 | `emailWithSurroundingSpacesIsRejectedByValidation` (expects `400`) **would fail**. It is superseded and must be removed or replaced, not "fixed" back | `api-automation/README.md` Phase 3A/3B addendum |
| M3 BDD / REST Assured / Selenium tests without M3 `pom.xml` (b) | Will not compile (missing dependencies) | `pom-test-dependencies_2c1e597..93d11a3.diff` |
| M3 `PublicCareUiIT` | Not part of `mvnw test` (name ends in `IT`). Needs a built frontend, ports 8080 and 5199, and Chrome | M3 `ui-automation/README.md` |
| M2 tests together with D-1 | Probably compatible: EP-04 sends an invalid email (still `400` after trimming). **Not verified** | M2 report §3.3.1; D-1 only trims |
| M4 fix `f78deb1` | Frontend only; no backend test affected. No automated frontend test exists in the group repository to show the fix | M4 `registration.cjs` (Playwright, not in the build) |

## 3. What this means for reported results

- Each member's numbers belong to that member's baseline. A group-level "total tests" figure would be invented until a combined run exists.
- Member 3's 91/0 (Java 21, CI) ran **with** the unapplied `AdminSetupRunner` change and `pom.xml`, on `70d01e0`.
- Member 4's SonarQube measures and "after" registration result include the unapplied `f78deb1`.
- The integration working tree (`f565fb4` + D-1) has been tested only on **Java 27, H2** (85/0). *[Phase 5B rev. 1, 2026-11-25: now also on **Java 21, H2** (Ubuntu OpenJDK 21.0.13.1, WSL2): 85/0. Still not on PostgreSQL. See [CONFIRMATION_REGISTER.md](../phase5b-confirmation-register/CONFIRMATION_REGISTER.md) ENV-J6.]*
