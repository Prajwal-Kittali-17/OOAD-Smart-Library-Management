# OOAD-Smart-Library-Management

Smart Library Management System built with Spring Boot, MVC architecture, layered design, and UML-based modeling.

## Prerequisites

- Java 17+
- MySQL 8+
- Maven Wrapper (already included: `mvnw`, `mvnw.cmd`)

## Database Setup

Create MySQL user/database (or let app create DB automatically):

- Database: `library_db`
- Default username fallback: `root`
- Password is read from environment variable `DB_PASSWORD`

Optional environment variables:

- `DB_URL`
- `DB_USERNAME`
- `DB_PASSWORD`

Windows PowerShell example:

```powershell
$env:DB_URL="jdbc:mysql://localhost:3306/library_db?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Kolkata"
$env:DB_USERNAME="root"
$env:DB_PASSWORD="your_mysql_password"
```

## Run Locally

```powershell
.\mvnw.cmd clean compile
.\mvnw.cmd spring-boot:run
```

If port 8080 is occupied, run on a different port:

```powershell
.\mvnw.cmd spring-boot:run "-Dspring-boot.run.arguments=--server.port=8082"
```

## Run Tests

```powershell
.\mvnw.cmd clean test
```
