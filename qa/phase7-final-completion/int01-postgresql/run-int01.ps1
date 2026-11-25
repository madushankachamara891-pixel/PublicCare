# INT-01 - Flyway migration and schema-ownership check on a dedicated local PostgreSQL database.
# Phase 7, Step 5. Run by the project owner in their own PowerShell window (Windows PowerShell 5.1 or 7).
#
# What it does (no application source, test, dependency or Flyway configuration is changed):
#   1. Creates a NEW dedicated role and a NEW empty database (refuses to run if either already exists).
#   2. Scenario (a): starts the already-built backend jar against the empty database; records
#      flyway_schema_history and the schema.
#   3. Scenario (b): starts it a second time against the same database; records the history again.
#   4. Scenario (d): records Hibernate SQL logging (any DDL would be visible) and a schema-only dump.
#   Scenario (c) is NOT run: it depends on RTM Q2 (contents of an "existing database"), still open.
#
# Credentials:
#   - The PostgreSQL administrator password is read from $env:PGPASSWORD if the owner has set it in
#     this window, otherwise from a hidden prompt. It is kept in memory for this process only.
#   - The application role gets a random password generated in memory. It is never written to disk.
#   - At the end every output file is scanned for both passwords. Any occurrence is redacted and reported.
#
# Usage (from any folder):
#   powershell -ExecutionPolicy Bypass -File "<path>\run-int01.ps1"
# Optional parameters: -AdminUser postgres -Database publiccare_int01_qa -AppRole publiccare_int01_app -Port 18081

param(
    [string]$AdminUser = 'postgres',
    [string]$Database = 'publiccare_int01_qa',
    [string]$AppRole = 'publiccare_int01_app',
    [int]$Port = 18081,
    [string]$PgBin = 'C:\Program Files\PostgreSQL\18\bin',
    [string]$JavaExe = 'C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2025.2.3\jbr\bin\java.exe'
)

$ErrorActionPreference = 'Stop'
$here = Split-Path -Parent $MyInvocation.MyCommand.Path
$repo = (Resolve-Path (Join-Path $here '..\..\..')).Path            # publiccare/
$jar = Join-Path $repo 'backend\target\publiccare-backend-0.0.1-SNAPSHOT.jar'
$psql = Join-Path $PgBin 'psql.exe'
$pgdump = Join-Path $PgBin 'pg_dump.exe'

if ($Database -notmatch '^[a-z_][a-z0-9_]{0,40}$' -or $AppRole -notmatch '^[a-z_][a-z0-9_]{0,40}$') { throw 'Database and role names must be lower-case identifiers.' }
if ($Database -in @('postgres', 'publiccare_db', 'template0', 'template1')) { throw "Refusing to use database '$Database'." }
foreach ($p in @($jar, $psql, $pgdump, $JavaExe)) { if (-not (Test-Path $p)) { throw "Not found: $p" } }

# New, empty run folder (never overwrite earlier evidence). It is also the working directory of the
# backend, so 'spring.config.import: optional:file:.env' cannot pick up backend/.env.
$stamp = Get-Date -Format 'yyyyMMdd-HHmmss'
$out = Join-Path $here "run-$stamp"
if (Test-Path $out) { throw "Run folder already exists: $out" }
New-Item -ItemType Directory -Path $out | Out-Null
$uploads = Join-Path $out 'uploads'
New-Item -ItemType Directory -Path $uploads | Out-Null

function Write-Evidence([string]$name, $content) { $content | Out-File -FilePath (Join-Path $out $name) -Encoding utf8 -Append }
function Invoke-Psql([string]$user, [string]$db, [string]$sql) {
    # SQL is passed on stdin (not on the command line). Exit code decides success, so stderr
    # notices do not abort the script under Windows PowerShell 5.1.
    $ErrorActionPreference = 'Continue'
    $result = $sql | & $psql -h localhost -p 5432 -U $user -d $db -w -v ON_ERROR_STOP=1 -X -P pager=off -f - 2>&1 | ForEach-Object { "$_" }
    if ($LASTEXITCODE -ne 0) { throw "psql failed (exit $LASTEXITCODE): $result" }
    return $result
}
function Get-ToolOutput([string]$exe, [string[]]$toolArgs) {
    $ErrorActionPreference = 'Continue'
    try { return ((& $exe @toolArgs 2>&1 | ForEach-Object { "$_" }) -join "`n") } catch { return "not available: $($_.Exception.Message)" }
}

# --- Credentials (memory only) ---
$adminPassword = $env:PGPASSWORD
if ([string]::IsNullOrEmpty($adminPassword)) {
    $secure = Read-Host -AsSecureString "Password for PostgreSQL user '$AdminUser' (input hidden)"
    $bstr = [Runtime.InteropServices.Marshal]::SecureStringToBSTR($secure)
    try { $adminPassword = [Runtime.InteropServices.Marshal]::PtrToStringBSTR($bstr) } finally { [Runtime.InteropServices.Marshal]::ZeroFreeBSTR($bstr) }
}
$chars = 'ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz23456789'.ToCharArray()
$rng = [Security.Cryptography.RandomNumberGenerator]::Create()
$bytes = New-Object byte[] 32; $rng.GetBytes($bytes)
$appPassword = -join ($bytes | ForEach-Object { $chars[$_ % $chars.Length] })

# Remove variables that could change the application's behaviour in this window's child processes.
foreach ($v in @('SPRING_PROFILES_ACTIVE', 'PUBLICCARE_ADMIN_SETUP_ENABLED', 'SPRING_CONFIG_IMPORT', 'SPRING_DATASOURCE_URL', 'SPRING_DATASOURCE_USERNAME', 'SPRING_DATASOURCE_PASSWORD', 'SPRING_FLYWAY_ENABLED', 'SPRING_JPA_HIBERNATE_DDL_AUTO')) { Remove-Item "Env:$v" -ErrorAction SilentlyContinue }

$status = 'INCOMPLETE'
try {
    # --- 00 Environment ---
    $env:PGPASSWORD = $adminPassword
    Write-Evidence '00-environment.txt' "INT-01 run $stamp (local time $(Get-Date -Format o))"
    Write-Evidence '00-environment.txt' "Windows: $([Environment]::OSVersion.VersionString)"
    Write-Evidence '00-environment.txt' ("Git HEAD: " + (Get-ToolOutput 'git' @('-C', $repo, 'rev-parse', 'HEAD')))
    Write-Evidence '00-environment.txt' "Git status --short:"
    Write-Evidence '00-environment.txt' (Get-ToolOutput 'git' @('-C', $repo, 'status', '--short'))
    Write-Evidence '00-environment.txt' ("Jar SHA-256: " + (Get-FileHash -Algorithm SHA256 $jar).Hash.ToLower() + "  " + $jar)
    Write-Evidence '00-environment.txt' "java -version:"
    Write-Evidence '00-environment.txt' (Get-ToolOutput $JavaExe @('-version'))
    Write-Evidence '00-environment.txt' ("psql client: " + (Get-ToolOutput $psql @('--version')))
    Write-Evidence '00-environment.txt' "Server (as $AdminUser):"
    Write-Evidence '00-environment.txt' (Invoke-Psql $AdminUser 'postgres' 'SELECT version(); SHOW server_version; SHOW server_encoding;')
    Write-Evidence '00-environment.txt' "Database name: $Database   Application role: $AppRole   Backend port: 127.0.0.1:$Port"

    # --- 01 Create dedicated role and database (refuse if they exist) ---
    $existing = Invoke-Psql $AdminUser 'postgres' "SELECT 'db_exists=' || EXISTS (SELECT 1 FROM pg_database WHERE datname = '$Database') || ' role_exists=' || EXISTS (SELECT 1 FROM pg_roles WHERE rolname = '$AppRole');"
    if (($existing -join ' ') -notmatch 'db_exists=false role_exists=false') { throw "Database '$Database' or role '$AppRole' already exists ($($existing -join ' ')). Choose other names with -Database / -AppRole. Nothing was changed." }
    # The password literal contains only letters and digits; it is sent on stdin, never on a command line,
    # and it is not written to any file (the result of this call is discarded).
    Invoke-Psql $AdminUser 'postgres' "CREATE ROLE $AppRole LOGIN PASSWORD '$appPassword' NOSUPERUSER NOCREATEDB NOCREATEROLE;" | Out-Null
    Write-Evidence '01-create-database.txt' "CREATE ROLE $AppRole LOGIN PASSWORD '[not recorded]' NOSUPERUSER NOCREATEDB NOCREATEROLE -> OK"
    Write-Evidence '01-create-database.txt' (Invoke-Psql $AdminUser 'postgres' "CREATE DATABASE $Database OWNER $AppRole ENCODING 'UTF8' TEMPLATE template0;")
    Write-Evidence '01-create-database.txt' "Tables in public schema before the first start:"
    Write-Evidence '01-create-database.txt' (Invoke-Psql $AdminUser $Database "SELECT count(*) AS tables_in_public FROM information_schema.tables WHERE table_schema = 'public';")

    # --- Backend runs ---
    $env:PGPASSWORD = $appPassword
    $env:DB_URL = "jdbc:postgresql://localhost:5432/$Database"
    $env:DB_USERNAME = $AppRole
    $env:DB_PASSWORD = $appPassword
    $env:PUBLICCARE_UPLOAD_DIR = $uploads

    function Start-Backend([string]$tag) {
        $log = Join-Path $out "$tag.log"; $err = Join-Path $out "$tag.stderr.log"
        $javaArgs = @('-jar', "`"$jar`"", "--server.address=127.0.0.1", "--server.port=$Port", '--logging.level.org.hibernate.SQL=DEBUG', '--logging.level.org.flywaydb=INFO')
        $p = Start-Process -FilePath $JavaExe -ArgumentList $javaArgs -WorkingDirectory $out -RedirectStandardOutput $log -RedirectStandardError $err -PassThru -WindowStyle Hidden
        $started = $false
        for ($i = 0; $i -lt 120; $i++) {
            Start-Sleep -Seconds 1
            if ($p.HasExited) { break }
            if ((Test-Path $log) -and (Select-String -Path $log -Pattern 'Started PublicCareBackendApplication' -Quiet)) { $started = $true; break }
        }
        $health = ''
        if ($started) {
            foreach ($path in @('/api/health', '/actuator/health')) {
                try { $r = Invoke-WebRequest -UseBasicParsing -Uri "http://127.0.0.1:$Port$path" -TimeoutSec 10; $health += "GET $path -> $($r.StatusCode) $($r.Content)`n" }
                catch { $code = ''; if ($_.Exception.Response) { $code = [int]$_.Exception.Response.StatusCode }; $health += "GET $path -> $code $($_.Exception.Message)`n" }
            }
        }
        if (-not $p.HasExited) { Stop-Process -Id $p.Id -Force; Start-Sleep -Seconds 2 }
        return @{ Started = $started; ExitedEarly = (-not $started); Health = $health }
    }

    # --- 02 Scenario (a): fresh database ---
    $r1 = Start-Backend '02a-run1-fresh-database'
    Write-Evidence '02b-health-run1.txt' "Started: $($r1.Started)"; Write-Evidence '02b-health-run1.txt' $r1.Health
    Write-Evidence '02c-history-after-run1.txt' (Invoke-Psql $AppRole $Database 'SELECT installed_rank, version, description, type, script, checksum, installed_by, success FROM flyway_schema_history ORDER BY installed_rank;')
    Write-Evidence '02d-tables-after-run1.txt' (Invoke-Psql $AppRole $Database "SELECT table_name FROM information_schema.tables WHERE table_schema = 'public' ORDER BY table_name;")
    $null = Get-ToolOutput $pgdump @('-h', 'localhost', '-p', '5432', '-U', $AppRole, '-d', $Database, '-w', '--schema-only', '--no-owner', '--no-privileges', '-f', (Join-Path $out '02e-schema-after-run1.sql'))
    if ($LASTEXITCODE -ne 0) { throw 'pg_dump failed.' }

    # --- 03 Scenario (b): restart against the same database ---
    $r2 = Start-Backend '03a-run2-restart'
    Write-Evidence '03b-health-run2.txt' "Started: $($r2.Started)"; Write-Evidence '03b-health-run2.txt' $r2.Health
    Write-Evidence '03c-history-after-run2.txt' (Invoke-Psql $AppRole $Database 'SELECT installed_rank, version, description, type, script, checksum, installed_by, success FROM flyway_schema_history ORDER BY installed_rank;')
    $null = Get-ToolOutput $pgdump @('-h', 'localhost', '-p', '5432', '-U', $AppRole, '-d', $Database, '-w', '--schema-only', '--no-owner', '--no-privileges', '-f', (Join-Path $out '03d-schema-after-run2.sql'))

    # --- 04 Scenario (d): DDL issued by Hibernate? (SQL logging is DEBUG in both runs) ---
    $ddl = Select-String -Path (Join-Path $out '0*-run*.log') -Pattern '\b(create|alter|drop)\s+(table|sequence|index)\b' -AllMatches
    Write-Evidence '04-ddl-check.txt' "Hibernate SQL statements matching create/alter/drop table|sequence|index in the two backend logs: $(@($ddl).Count)"
    $ddl | ForEach-Object { Write-Evidence '04-ddl-check.txt' $_.Line }
    Write-Evidence '04-ddl-check.txt' "Configured spring.jpa.hibernate.ddl-auto in backend/src/main/resources/application.yml (only configuration file):"
    Write-Evidence '04-ddl-check.txt' (Select-String -Path (Join-Path $repo 'backend\src\main\resources\application.yml') -Pattern 'ddl-auto').Line

    Write-Evidence '05-scenario-c-not-run.txt' 'Scenario (c) (existing database baselined at V2) was NOT run: its test data depends on RTM Q2, which is still open.'
    if ($r1.Started -and $r2.Started) { $status = 'COMPLETED (see files for results)' } else { $status = 'COMPLETED WITH A FAILED START (see the .log files)' }
}
catch {
    $status = 'STOPPED WITH ERROR'
    Write-Evidence '99-error.txt' $_.Exception.Message
    Write-Host "INT-01 stopped: $($_.Exception.Message)" -ForegroundColor Red
}
finally {
    foreach ($v in @('PGPASSWORD', 'DB_PASSWORD', 'DB_USERNAME', 'DB_URL', 'PUBLICCARE_UPLOAD_DIR')) { Remove-Item "Env:$v" -ErrorAction SilentlyContinue }
    # Credential leak scan and redaction
    $leaks = 0
    Get-ChildItem -Path $out -File -Recurse | ForEach-Object {
        $text = [IO.File]::ReadAllText($_.FullName)
        $new = $text
        foreach ($secret in @($adminPassword, $appPassword)) { if (-not [string]::IsNullOrEmpty($secret) -and $new.Contains($secret)) { $new = $new.Replace($secret, '[REDACTED]'); $leaks++ } }
        if ($new -ne $text) { [IO.File]::WriteAllText($_.FullName, $new) }
    }
    $adminPassword = $null; $appPassword = $null
    Write-Evidence '00-run-status.txt' "Status: $status"
    Write-Evidence '00-run-status.txt' "Credential scan: $leaks occurrence(s) found and redacted"
    Write-Host "INT-01 run folder: $out"
    Write-Host "Status: $status. Credential scan: $leaks occurrence(s) redacted."
    Write-Host "The database '$Database' and role '$AppRole' were kept for inspection. To remove them later (as $AdminUser): DROP DATABASE $Database; DROP ROLE $AppRole;"
}
