@echo off
setlocal

rem Move from review-prep to project root
cd /d "%~dp0.."

rem Fail fast if the app port is already in use.
for /f "tokens=5" %%P in ('netstat -ano ^| findstr ":8080 " ^| findstr LISTENING') do (
  echo Error: port 8080 is already in use by PID %%P.
  echo Stop the process or change the port before running this script.
  exit /b 1
)

rem Default to the original MySQL database created by the SQL script.
if not defined DB_URL set "DB_URL=jdbc:mysql://localhost:3306/library_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC"
if not defined DB_USERNAME set "DB_USERNAME=root"
if not defined DB_PASSWORD set /p "DB_PASSWORD=Enter MySQL password for %DB_USERNAME%: "
if "%DB_PASSWORD%"=="" (
  echo Error: DB_PASSWORD is required to connect to the original MySQL database.
  echo Set DB_PASSWORD and run the script again.
  exit /b 1
)
if not defined DB_DRIVER set "DB_DRIVER=com.mysql.cj.jdbc.Driver"
if not defined DB_DIALECT set "DB_DIALECT=org.hibernate.dialect.MySQLDialect"

echo [1/2] Building project (skip tests)...
if exist mvnw.cmd (
  call mvnw.cmd clean install -DskipTests
) else (
  mvn clean install -DskipTests
)
if errorlevel 1 (
  echo Build failed. Fix errors and rerun.
  exit /b 1
)

echo [2/2] Starting Spring Boot app...
if exist mvnw.cmd (
  call mvnw.cmd spring-boot:run
) else (
  mvn spring-boot:run
)
