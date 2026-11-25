# PublicCare

PublicCare is a React + Vite citizen issue reporting interface with a Spring Boot and PostgreSQL backend. Citizen reports are linked to the authenticated account; administrators can review reports and change their status.

## Local setup

1. Create the PostgreSQL database `publiccare_db`.
2. Start the backend; Flyway applies migrations V1 through V5 in order (including report images, progress history, and public approvals).
3. Configure `DB_URL`, `DB_USERNAME`, and `DB_PASSWORD` in the backend environment. See [backend setup and API verification](backend/README.md) for details.
4. Start the backend from `backend` with `./mvnw.cmd spring-boot:run`.
5. From this directory, run `npm install` once and `npm run dev`; open the Vite origin at `http://localhost:5173`.
6. Register or sign in before creating a report. The sample home page and fixture helpers remain demo content; signed-in report, detail, edit, and admin pages use the API.

The application uses `ddl-auto: none` and never creates an administrator during regular startup. For the first local development admin, follow the masked-password setup command in [backend setup and API verification](backend/README.md#create-the-first-local-administrator). Citizen-submitted report photos and administrator progress photos are stored privately outside the web root.

## Local verification

```powershell
cd backend
./mvnw.cmd test
cd ..
npm run lint
npm run build
```

## AI-Assisted Development

Claude, an AI model developed by Anthropic, was used through Claude Code as an AI development assistant during the quality-assurance stage of this project. It acted only as an assistant: Claude's suggestions and AI-assisted changes were checked against the available project evidence and incorporated under the project owner's direction. This does not imply that every team member individually reviewed or approved every AI-assisted contribution. Project decisions and responsibility for the application and its documentation remain with the student team.

Within that scope, Claude assisted with the following work:

- **Backend development:** implementing the email normalization fix, which trims surrounding whitespace from email addresses in registration and login requests before validation (commit `1f92649`; evidence in [qa/phase3a-api04-correction/](qa/phase3a-api04-correction/)).
- **Test development and execution:** writing the DTO-level and API-level tests that accompany this fix, and helping to run the backend test suite and the documented test cases whose results are recorded in the QA evidence.
- **QA documentation:** helping to prepare, consolidate, and cross-reference the team's QA documentation for Sections 3.1–3.10 (see [qa/](qa/)).
- **Evidence and submission packaging:** organizing and verifying evidence files, and assembling and checking the submission package (see [SUBMISSION_INDEX.md](SUBMISSION_INDEX.md)).
- **Repository review and Git workflow:** reviewing repository contents before commits and preparing the commits on the `qa/final-assignment` branch.

Claude did not independently develop the PublicCare application. The application baseline that predates the QA work (commits up to `f565fb4`) is not attributed to Claude in the repository history. Commits prepared with Claude's assistance carry a `Co-Authored-By: Claude` trailer, so its involvement can be traced in the Git history.
