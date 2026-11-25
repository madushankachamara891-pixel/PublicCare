# Phase 11 – Final submission package record

| Item | Value |
|---|---|
| Date | 25 Nov 2026 |
| Package | `PublicCare-EC4206-QA-Final-Submission.zip`, built **outside** the repository in `Public care/submission/`. The earlier verified package `PublicCare-EC4206-QA-Submission.zip` (Phase 10, build 2) is kept unchanged beside it |
| Why a new package | Phase 11 executed 39 Part 01 cases, added the Section 3.1–3.2 final report, and updated the Test Plan, RTM, Member 4 integrated revision §3.8 and the integration records. The Phase 10 package does not contain these |
| Selection rule | Same as Phase 10 (approved decisions Z-1 – Z-8): `git ls-files -co --exclude-standard` on the working tree + the 4 INT-01 `.log` files (Z-8) + `MANIFEST-SHA256.txt` (generated inside the ZIP only). Root folder in the ZIP: `publiccare/` |
| File list | [PACKAGE_FILE_LIST.txt](PACKAGE_FILE_LIST.txt) – one path per line, relative to `publiccare/`, byte-order sorted |
| Excluded | `.env`, `.git/`, `node_modules/`, `dist/`, `backend/target/`, build output, IDE settings, the scratch copy used for Phase 11 |
| Integrity | `MANIFEST-SHA256.txt` in the ZIP lists the SHA-256 of every file except itself. The ZIP's size, file count and SHA-256, and the verification results, are in `Public care/submission/PublicCare-EC4206-QA-Final-Submission.VERIFICATION.txt` (a file cannot contain the hash of the archive it is in) |
| Git | Nothing committed or pushed; branch `qa/final-assignment`, identity unchanged |
| Changes since the Phase 10 package | See the [integration log](../INTEGRATION_LOG.md#phase-11--part-01-execution-and-final-completion-2026-11-25) |
