# INT-01 on PostgreSQL – preparation record (Phase 7, Step 5) and verified results (Phase 8A)

| Item | Value |
|---|---|
| Status | Phase 7 record: **PREPARED – NOT YET EXECUTED.** The run needs the PostgreSQL administrator password. Only the project owner can supply it, in their own PowerShell window. *[Phase 8A, 25 Nov 2026: **EXECUTED by the project owner** – run folder [`run-20261125-125857/`](run-20261125-125857/). Result: **PARTIALLY EXECUTED** – (a) PASS, (b) PASS, (d) PASS (effective `ddl-auto` not read at run time), (c) BLOCKED (RTM Q2). INT-01 is therefore **not** recorded as an overall PASS. See "Phase 8A – verified results" below]* |
| Owner approval | The project owner approved a **dedicated local PostgreSQL database** for INT-01 (this answers RTM Q3 for the local-database option). Testcontainers was not used, because it would need a new test dependency |
| Test case | INT-01 – Flyway migration and schema-ownership check (FR-09.2), Test Plan §10.4 |
| Scenarios run by the script | (a) fresh database; (b) restart against the same database; (d) schema ownership / no Hibernate DDL |
| Scenario not run | (c) existing database baselined at V2 – **BLOCKED** by RTM Q2 (the content of an "existing database" is not defined). No test data was invented for it |

## Phase 8A – verified results (2026-11-25)

Executed by the project owner with `run-int01.ps1` in their own PowerShell window. Verified by the integrator by reading all 16 output files in the run folder; nothing was re-run, and no evidence file was changed. The script's `COMPLETED` status was **not** taken as a test result: it only means that both backend starts reached "Started" and no step threw an error (script line 158). Each scenario below was judged against the Expected Result in Test Plan §10.4 / RTM TASK 6-E.

### Run identity

| Item | Value | Evidence |
|---|---|---|
| Run folder | `run-20261125-125857/` (backend runs 12:59:19 – 12:59:46 +05:30) | file timestamps; `02a`/`03a` logs |
| Executed by | Project owner (name PENDING, P-OW-1) | owner's Phase 8A message |
| Code baseline | Git HEAD `f565fb4` + uncommitted D-1 (`M AuthDtos.java`, 2 untracked test classes) | `00-environment.txt` |
| Application jar | SHA-256 `dd1b8b18…0d9ce0` – **identical** to the jar recorded in the preparation record below | `00-environment.txt` |
| Runtime | JetBrains Runtime 21.0.8 (OpenJDK, Java 21), Windows 11 (`Microsoft Windows NT 10.0.26200.0`); Spring Boot 3.5.16, Hibernate ORM 6.6.53.Final | `00-environment.txt`; `02a` log |
| **PostgreSQL server** | **PostgreSQL 18.6** on x86_64-windows (msvc-19.44.35229, 64-bit); `server_encoding` UTF8. psql and pg_dump 18.6. Flyway and Hibernate also detected 18.6 | `00-environment.txt`; `02a`/`03a` logs; schema dumps |
| Dedicated database | `publiccare_int01_qa`, new, **0 tables** in `public` before the first start. Not `publiccare_db` | `01-create-database.txt`; Flyway line `jdbc:postgresql://localhost:5432/publiccare_int01_qa` in both logs |
| Dedicated application role | `publiccare_int01_app`, new, `NOSUPERUSER NOCREATEDB NOCREATEROLE`; owner of the database (script line 104). The application connected as this role: every `flyway_schema_history` row has `installed_by = publiccare_int01_app` | `01-create-database.txt`; `02c`/`03c` |
| `backend/.env` isolation | Backend working directory = the run folder (log: "started by … in …\run-20261125-125857"); the run folder contains no `.env` file. No active profile ("No active profile set") | `02a`/`03a` logs; folder listing |
| Upload directory | `run-20261125-125857/uploads/` – empty (no upload was exercised) | folder listing |
| Database and role after the run | **Kept** for inspection (owner's report). Not dropped | owner's Phase 8A message |

### Scenario results

| Scenario | Expected (TP §10.4) | Observed | Status |
|---|---|---|---|
| **(a) Fresh database** | V1–V5 apply in version order; history lists 1–5, each `success = true`; all tables and constraints from the scripts exist | Flyway: "Schema history table … does not exist yet"; "Successfully validated 5 migrations"; "All configured schemas are empty; baseline operation skipped"; V1 → V5 applied in order; "**Successfully applied 5 migrations to schema "public", now at version v5** (execution time 00:00.110s)". History: installed_rank 1–5 = V1–V5, all `success = t`. Tables: `users`, `reports`, `report_images`, `report_progress_updates`, `flyway_schema_history`. Schema dump compared with V1–V5 (Phase 8A, below): every object present, nothing extra. Backend started (9.5 s); `GET /api/health` → `200 {"status":"UP"}`; `GET /actuator/health` → `200 {"status":"UP"}` (DB health indicator enabled in `application.yml`) | **PASS** |
| **(b) Restart, same database** | No migration applied a second time; startup succeeds | Flyway: "Successfully validated 5 migrations"; "Current version of schema "public": 5"; "**Schema "public" is up to date. No migration necessary.**" History after run 2 is byte-identical to run 1 (same 5 rows, checksums, `installed_by`). Schema dump after run 2 is identical to run 1 except pg_dump's random per-dump `\restrict` key (lines 5 and 358). Backend started (12.1 s); both health endpoints `200 UP` | **PASS** |
| **(c) Existing database baselined at V2** | Pending RTM Q2 | Not run. `05-scenario-c-not-run.txt`: "its test data depends on RTM Q2, which is still open". No test data was invented | **BLOCKED** (RTM Q2) – NOT EXECUTED |
| **(d) Schema ownership** | `ddl-auto` resolves to `none`; every table, column and constraint comes from a migration script; Hibernate creates or alters nothing | (d-1) Every table, column, CHECK, UNIQUE, FK and index in the dump comes from V1–V5 or is Flyway's own history table (comparison below). (d-2) 0 statements matching `create/alter/drop table/sequence/index` in the two backend logs; schema unchanged across the restart. (d-3) Configured value `ddl-auto: none` in `application.yml`, the only configuration file; the script removed `SPRING_JPA_HIBERNATE_DDL_AUTO` and `SPRING_PROFILES_ACTIVE` from its process; no command-line override; no active profile; `.env` not loadable; JVM stderr empty (so no `JAVA_TOOL_OPTIONS` notice) | **PASS** for d-1 and d-2. d-3 is supported but the **effective** value was **NOT VERIFIED** directly at run time (see limitations) |

**Overall INT-01 status: PARTIALLY EXECUTED – 3 of 4 scenarios PASS, scenario (c) BLOCKED.** The §10.3 template allows only PASS, FAIL or BLOCKED for a whole case. INT-01 cannot be recorded as PASS, because scenario (c) is part of its Expected Result; it is recorded per scenario instead.

### Schema comparison with the migration scripts (Phase 8A, static)

Done by the integrator from `02e-schema-after-run1.sql` and `backend/src/main/resources/db/migration/V1–V5` (read only):

| Object type | In the dump | From the scripts | Result |
|---|---:|---:|---|
| Application tables | 4 | V1–V4: 4 | Match (+ `flyway_schema_history`, created by Flyway) |
| CHECK constraints | 13 | V1 1, V2 1, V3 2, V4 2, V5 7 = 13 | Match |
| Foreign keys | 6 | V2 1, V3 1, V4 1, V5 3 = 6 | Match |
| Indexes (`CREATE INDEX`) | 7 | V2 3, V4 1, V5 2 = 6 | Match (+ `flyway_schema_history_s_idx`) |
| Explicitly named constraints and indexes in V1–V5 | all present | – | 0 missing |
| Dump names not written in the scripts | 13 | – | All are PostgreSQL default names for constraints that the scripts declare inline (`*_pkey`, `*_check`, `*_key`, `*_fkey`) or Flyway's own (`flyway_schema_history_pk`, `_s_idx`). None has a Hibernate-style generated name; no `hibernate_sequence` |
| Columns | spot-checked all 5 tables | – | Match, including the generated column `reports.reference` (`'PC-' || lpad(id::text, 8, '0')`) and all V5 columns |

### Flyway output (verbatim, scenario a)

```
FlywayExecutor  : Database: jdbc:postgresql://localhost:5432/publiccare_int01_qa (PostgreSQL 18.6)
WARN Database   : Flyway upgrade recommended: PostgreSQL 18.6 is newer than this version of Flyway and support has not been tested. The latest supported version of PostgreSQL is 17.
JdbcTableSchemaHistory : Schema history table "public"."flyway_schema_history" does not exist yet
DbValidate      : Successfully validated 5 migrations (execution time 00:00.024s)
Flyway          : All configured schemas are empty; baseline operation skipped. ...
JdbcTableSchemaHistory : Creating Schema History table "public"."flyway_schema_history" ...
DbMigrate       : Current version of schema "public": << Empty Schema >>
DbMigrate       : Migrating schema "public" to version "1 - create users"
DbMigrate       : Migrating schema "public" to version "2 - create reports"
DbMigrate       : Migrating schema "public" to version "3 - create report images"
DbMigrate       : Migrating schema "public" to version "4 - create report progress updates"
DbMigrate       : Migrating schema "public" to version "5 - add public report approvals"
DbMigrate       : Successfully applied 5 migrations to schema "public", now at version v5 (execution time 00:00.110s)
```

Scenario (b): the same `Database:` and `WARN` lines, then "Successfully validated 5 migrations (execution time 00:00.070s)", "Current version of schema "public": 5", "Schema "public" is up to date. No migration necessary."

### Credential check

- Script result: "Credential scan: **0 occurrence(s)** found and redacted" (`00-run-status.txt`). The scan compares every file with the two passwords held in memory (script lines 168–174); `00-run-status.txt` itself is written after the scan and holds only the status lines.
- Independent Phase 8A check (without knowing either password): a case-insensitive search for `password`, `secret`, `token`, `DB_PASS` and JDBC URL query strings in all 16 files found only the placeholder `PASSWORD '[not recorded]'` in `01-create-database.txt` (and the column name `password_hash` in the schema dumps). No credential value appears in the evidence. The `\restrict` keys in the two `.sql` dumps are random values that pg_dump 18 generates for each dump; they are not credentials.

### Evidence files (raw, unchanged; SHA-256)

| File | SHA-256 |
|---|---|
| `00-environment.txt` | `f48453daf85b3ce231e81775b484f32c43323c5c69f4ec89056f9062da478a1d` |
| `00-run-status.txt` | `220f4de71cc1f40f1f2004221420dee169add62d46bbc2e442e2864783028cb2` |
| `01-create-database.txt` | `72846659726ebaa4ac917201fc522adb4056bb466296d46f9b39bc77a2fab991` |
| `02a-run1-fresh-database.log` | `3d6d65e86daaaafa146f8c6b548a0e2aa7326f7fc55f6704d4ca4d31460d1ae0` |
| `02a-run1-fresh-database.stderr.log` (empty) | `e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855` |
| `02b-health-run1.txt` | `1486c66a0f8326721fe10c7c95c15453e94cb2a6ff5c210f0d9c988908f476d3` |
| `02c-history-after-run1.txt` | `d90c580a320a93e628ebf660a3c935443b9f3705b0c14cab8d86b1f7c8f99a1f` |
| `02d-tables-after-run1.txt` | `16b59e7545fa95b330984921656456ff0c70b6c5edb5ae0ac4cc6bfc07128448` |
| `02e-schema-after-run1.sql` | `0560501985bdde0729aab609c48f11b690a94adcbd25953e0b10b6c2182305b4` |
| `03a-run2-restart.log` | `f76f7fdb02d169a38b95f673ab3d85ae11893c37097ccebd7dfe350ccc98b684` |
| `03a-run2-restart.stderr.log` (empty) | `e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855` |
| `03b-health-run2.txt` | `1486c66a0f8326721fe10c7c95c15453e94cb2a6ff5c210f0d9c988908f476d3` |
| `03c-history-after-run2.txt` | `d90c580a320a93e628ebf660a3c935443b9f3705b0c14cab8d86b1f7c8f99a1f` |
| `03d-schema-after-run2.sql` | `90131589c3786afa7776a405b932b32bf3e7f11f9295af2ad038169c51129fd1` |
| `04-ddl-check.txt` | `294e654963d04d85b806510d1371948ae20f4d027fe452ca11539273c8938524` |
| `05-scenario-c-not-run.txt` | `c374ba542d8886b41681ae17244b5a8a3cc7ac445e2881f464b9ff38e6ebac22` |
| `uploads/` | empty folder |

No separate JUnit/Surefire test report or result-summary file exists: INT-01 is a scripted integration check, and the script does not produce one. The backend logs (`02a`, `03a`) are the application logs; the Flyway output is inside them. Snapshot of all `qa/` files before the Phase 8A documentation edits: [`qa-sha256-before-phase8a.txt`](qa-sha256-before-phase8a.txt) (identical to the Phase 7 end snapshot apart from the new run folder).

### Observations and limitations of this run

1. **Scenario (c) not executed** – blocked by RTM Q2 (the content of an "existing database" is undefined). The `baseline-on-migrate` / `baseline-version: 2` path is therefore **untested** on PostgreSQL. In scenario (a) Flyway skipped the baseline because the schema was empty, as expected.
2. **Effective `ddl-auto` not read at run time.** Only `/actuator/health` is exposed, so the resolved property could not be queried. The value `none` rests on the configuration file plus the removed overrides. `SPRING_APPLICATION_JSON` was not cleared by the script. The schema evidence (d-1, d-2) is what shows that Hibernate created nothing.
3. **The DDL log check is evidence of absence.** `--logging.level.org.hibernate.SQL=DEBUG` was set, but the logs contain **no** Hibernate SQL line of any kind, because no request touched the database through JPA. So the log check cannot by itself show that SQL logging was active. It is consistent with the schema comparison, which is the stronger evidence.
4. **Flyway support warning.** Flyway 11.7.2 warns that PostgreSQL 18.6 is newer than its latest supported version (17) and "support has not been tested". The migrations nevertheless applied and validated without error. This is a compatibility risk to note, not a failure.
5. **Scope.** Only start-up, migrations and the two health endpoints were exercised. No API call wrote or read application data, so persistence through JPA on PostgreSQL (and H2-vs-PostgreSQL behaviour beyond the schema) remains **NOT VERIFIED**. No upload was made.
6. **Stop method.** Each backend was stopped with `Stop-Process -Force` (script line 132), so the logs have no graceful-shutdown lines. This does not affect the scenarios.
7. **Cosmetic script output.** `/actuator/health` content was written as byte values (`123 34 115 …`) because Windows PowerShell 5.1 did not treat the actuator content type as text. Decoded, it is `{"status":"UP"}`.
8. **Environment differs** from the Java 21 suite run (Ubuntu OpenJDK 21.0.13.1, WSL2): here JetBrains Runtime 21.0.8 on Windows 11. One run only; not repeated.
9. **Cleanup pending.** `publiccare_int01_qa` and `publiccare_int01_app` still exist on the local server and wait for the owner's approval before they are dropped.

## What was checked before the run (2026-11-25, integration machine)

| Check | Result |
|---|---|
| PostgreSQL server | 18.6 (`C:\Program Files\PostgreSQL\18`), `localhost:5432 - accepting connections` (`pg_isready`). A password is required (`fe_sendauth: no password supplied` with `-w`). No `pgpass.conf` exists, and none was created |
| Database credentials in the integrator's shell | None set (`PG*`, `DB_*`, `SPRING_DATASOURCE*` variable names checked; values never read) |
| `backend/.env` | **Exists** (gitignored). **Not read.** Because `application.yml` imports `optional:file:.env`, the script starts the backend with a different working directory (the run folder), so this file cannot be loaded. `DB_URL`, `DB_USERNAME` and `DB_PASSWORD` are set explicitly for the child process |
| Configuration | `backend/src/main/resources/application.yml` is the only configuration file: `ddl-auto: none`, `flyway.baseline-on-migrate: true`, `baseline-version: 2`. Migrations V1–V5 in `db/migration/` |
| Admin setup runner | Active only under the `admin-setup` profile, so it does not run. The script also removes `SPRING_PROFILES_ACTIVE` and `PUBLICCARE_ADMIN_SETUP_ENABLED` from its own process |
| Upload directory | Redirected to `run-<timestamp>/uploads` (`PUBLICCARE_UPLOAD_DIR`), not the user's home folder |
| Application jar | Built by the integrator on 2026-11-25 12:45 +05:30 with JetBrains Runtime **21.0.8** (OpenJDK, Java 21): `./mvnw -B -o -DskipTests package` → BUILD SUCCESS. `backend/target/publiccare-backend-0.0.1-SNAPSHOT.jar` (gitignored build output), SHA-256 `dd1b8b184bdfe84f7d0cce2380f788daba48d7509c778d0d915728adde0d9ce0`. Built from the working tree (`f565fb4` + uncommitted D-1). Contains `flyway-core` 11.7.2, `flyway-database-postgresql` 11.7.2, PostgreSQL JDBC 42.7.11 |
| Source, tests, dependencies, Flyway configuration | **Not changed.** Runtime-only options: `--server.address=127.0.0.1 --server.port=18081 --logging.level.org.hibernate.SQL=DEBUG` (SQL logging makes any Hibernate DDL visible for scenario (d)) |

## How the owner runs it

1. Open a new PowerShell window.
2. Optionally set the administrator password for this window only, so that you are not prompted:
   `$env:PGPASSWORD = Read-Host "postgres password"` (it disappears when the window is closed). Otherwise the script asks for it with hidden input.
3. Run:
   `powershell -ExecutionPolicy Bypass -File "D:\4TH SEMESTER\Web Aplication project\Public care\publiccare\qa\phase7-final-completion\int01-postgresql\run-int01.ps1"`
4. Tell the integrator when it has finished. The script prints the run folder and a one-line status.

**Do not paste the password into the chat**, and do not write it into any file.

## Safety properties of `run-int01.ps1`

- It creates a **new** role `publiccare_int01_app` (no superuser, no create-database or create-role rights) and a **new** database `publiccare_int01_qa`. If either already exists, it stops **before changing anything**. It never uses `publiccare_db`, `postgres` or a template database as the target.
- The role's password is random, generated in memory, sent to `psql` on stdin (not on a command line) and never written to disk. The administrator password stays in this window's memory only.
- All output goes to a **new** folder `run-<timestamp>/`. Earlier evidence is never overwritten.
- At the end, every output file is scanned for both passwords. Any occurrence is replaced by `[REDACTED]`, and the count is written to `00-run-status.txt`.
- Failed runs are kept as they are, with their logs.
- The database and role are kept for inspection. The script prints the `DROP` commands for later cleanup.

## Output files (per run folder)

| File | Content |
|---|---|
| `00-environment.txt` | Date, Windows version, Git HEAD and status, jar SHA-256, `java -version`, psql client version, **server `version()` and `server_version`** |
| `01-create-database.txt` | Role and database creation (password not recorded); table count before the first start |
| `02a-run1-fresh-database.log` | Backend log, scenario (a): Flyway output, Hibernate SQL, start-up |
| `02b-health-run1.txt` | `GET /api/health` and `/actuator/health` responses |
| `02c-history-after-run1.txt` | `flyway_schema_history` after the first start |
| `02d-tables-after-run1.txt`, `02e-schema-after-run1.sql` | Table list; schema-only dump |
| `03a-run2-restart.log`, `03b-health-run2.txt`, `03c-history-after-run2.txt`, `03d-schema-after-run2.sql` | Scenario (b) |
| `04-ddl-check.txt` | Hibernate DDL statements found in both logs (expected: none); configured `ddl-auto` |
| `05-scenario-c-not-run.txt` | Why scenario (c) was not run |
| `00-run-status.txt` | Final status and credential-scan result |

## Known limitations of this design

- PostgreSQL 18.6 is newer than the versions that Flyway 11.7.2 was released against. Flyway may log a warning about this. If it does, the warning is part of the result.
- If a `CREATE ROLE` statement ever failed, PostgreSQL's server log (outside this repository) could record the statement. The script checks that the role does not exist before creating it, so that path is not expected.
- The run uses Java 21 (JetBrains Runtime 21.0.8) on Windows 11. That differs from the Java 21 test run (Ubuntu OpenJDK 21.0.13.1, WSL2).
