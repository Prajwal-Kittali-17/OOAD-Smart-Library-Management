# 📚 Smart Library Management System

```text
	____                       _     _     _ _
 / ___| _ __ ___   __ _ _ __| |_  | |   (_) |__  _ __ __ _ _ __ _   _
 \___ \| '_ ` _ \ / _` | '__| __| | |   | | '_ \| '__/ _` | '__| | | |
	___) | | | | | | (_| | |  | |_  | |___| | |_) | | | (_| | |  | |_| |
 |____/|_| |_| |_|\__,_|_|   \__| |_____|_|_.__/|_|  \__,_|_|   \__, |
																																 |___/
```

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-6DB33F)
![Architecture](https://img.shields.io/badge/Architecture-MVC-blue)
![Build](https://img.shields.io/badge/Build-Local%20Tests%20Passing-brightgreen)
![License](https://img.shields.io/badge/License-Not%20Specified-lightgrey)
![Stars](https://img.shields.io/github/stars/Prajwal-Kittali-17/OOAD-Smart-Library-Management?style=social)

**Tagline:** Learn, borrow, return, and track books with smart workflows.

Smart Library Management System is a full-stack Spring Boot MVC project for handling library login, catalog records, issue-return transactions, and overdue fine tracking. It is designed as a layered OOAD mini project with explicit GRASP and pattern-oriented code comments. The goal is to make real library operations understandable for beginners and maintainable for developers.

───────────────────────────────

## 🗂️ 2. Table of Contents

- [📚 1. Project Title and Hero](#-smart-library-management-system)
- [🗂️ 2. Table of Contents](#️-2-table-of-contents)
- [🧒 3. What Is This? Explain Like I Am 10](#-3-what-is-this-explain-like-i-am-10)
- [🎯 4. Features](#-4-features)
- [🏛️ 5. MVC Architecture Deep Dive](#️-5-mvc-architecture-deep-dive)
- [🧠 6. GRASP Principles by Member](#-6-grasp-principles-by-member)
- [🧩 7. Design Patterns by Member](#-7-design-patterns-by-member)
- [📁 8. File and Folder Structure](#-8-file-and-folder-structure)
- [👥 9. Team Contributions Individual Breakdown](#-9-team-contributions-individual-breakdown)
- [🚀 10. Installation and Setup for Beginners](#-10-installation-and-setup-for-beginners)
- [⌨️ 11. Commands Reference Table](#️-11-commands-reference-table)
- [🗺️ 12. Project Tour Full Story](#️-12-project-tour-full-story)
- [🗄️ 13. Database Schema](#️-13-database-schema)
- [🔌 14. API Endpoints](#-14-api-endpoints)
- [🧪 15. Testing](#-15-testing)
- [⚠️ 16. Known Issues and Roadmap](#️-16-known-issues-and-roadmap)
- [📖 17. Glossary](#-17-glossary)
- [🤝 18. How to Contribute](#-18-how-to-contribute)
- [📜 19. License and Credits](#-19-license-and-credits)

───────────────────────────────

## 🧒 3. What Is This? Explain Like I Am 10

Imagine you walk into a magical library where one smart helper remembers who you are, shows every book, tracks who borrowed what, and warns you if a book is returned late.

In this project:

- 📚 books are added and listed in a catalog,
- 🔐 users log in with account lock protection,
- 🔄 transactions record issue and return events,
- 💰 fines are calculated automatically for overdue books.

It solves a real problem: keeping library records organized without paper confusion.

**That is exactly what our Smart Library System does!**

───────────────────────────────

## 🎯 4. Features

### 📖 Book Management

- ✅ 📚 **Add Book**: Create catalog entries with title, author, ISBN, and quantity.
- ✅ 📋 **View Catalog**: See all books in one table view.
- ✅ 🧮 **Input Guardrails**: Quantity and price are validated/sanitized before save.

### 👤 Member Management

- ✅ 👤 **User Entity**: Stores username, password, role, failed attempts, and lock state.
- ✅ 🔐 **Session-Aware Login**: Session stores logged-in user on successful auth.

### 🔐 Admin Controls

- ✅ 🚫 **Account Lockout**: User gets locked after repeated failed attempts.
- ✅ 🛡️ **Route Protection**: Interceptor blocks protected pages when unauthenticated.
- ✅ 👑 **Default Admin Seed**: App seeds admin/admin123 if users table is empty.

### 🔍 Search and Tracking

- ✅ 📑 **Transaction Listing API**: Exposes transaction records via REST.
- ✅ 🔄 **Issue/Return Tracking Model**: Transaction model tracks user-book-status timeline.

### 📊 Reports and Stats

- ✅ 💸 **Overdue Fine Calculation**: Fine service computes overdue penalties.
- ✅ 🧠 **Role-Based Fine Strategy**: Faculty and student/admin rates are decoupled strategies.
- ✅ 💳 **Mark Fine Paid**: Finance module updates payment status.

───────────────────────────────

## 🏛️ 5. MVC Architecture Deep Dive

_Think of MVC like a restaurant 🍽️..._

- **Model** = kitchen ingredients and recipes (data + rules)
- **View** = menu and plating (UI pages)
- **Controller** = waiter taking orders and sending them to the right place

### 🔄 MVC Flow Diagram

```text
Browser/User
	 |
	 v
[View: Thymeleaf Template]
	 |
	 v
[Controller]
	 |
	 v
[Service Layer]
	 |
	 v
[Repository Layer]
	 |
	 v
[MySQL Database]
	 |
	 v
[Repository -> Service -> Controller -> View]
```

### 📊 MVC Role Mapping (Core Files)

| File                         | MVC Role     | What It Does                                                      |
| ---------------------------- | ------------ | ----------------------------------------------------------------- |
| `AuthController.java`        | C            | Handles login, logout flow, and dashboard routing.                |
| `BookController.java`        | C            | Handles book listing and add-book form submit.                    |
| `FineController.java`        | C            | Loads fine report and marks fine as paid.                         |
| `TransactionController.java` | C            | REST endpoints to list/create transactions.                       |
| `Book.java`                  | M            | Book entity with ISBN/title/author/price/quantity.                |
| `User.java`                  | M            | User auth state and lockout behavior.                             |
| `Transaction.java`           | M            | Issue-return transaction entity.                                  |
| `Fine.java`                  | M            | Fine record with amount and payment status.                       |
| `AuthService.java`           | M (business) | Auth logic and lockout workflow orchestration.                    |
| `BookService.java`           | M (business) | Catalog business operations.                                      |
| `FineService.java`           | M (business) | Overdue fine calculation and fine updates.                        |
| `TransactionService.java`    | M (business) | Transaction retrieval and creation logic.                         |
| `*.html` in `templates/`     | V            | Thymeleaf views for login, dashboard, books, fines, transactions. |

### 🔄 “User Borrows a Book” Data Flow (Exact 1-10)

1. User opens transaction module view (`transaction-list.html`) from dashboard.
2. User submits borrow details from UI.
3. Controller endpoint receives request (`TransactionController`, POST `/transactions`).
4. Controller delegates to `TransactionService.saveTransaction(...)`.
5. Service calls `TransactionRepository.save(...)`.
6. JPA persists transaction row in MySQL table `transaction`.
7. Later, finance module loads tracked fines (`FineController` -> `FineService.getTrackedFines()`).
8. `FineService` scans issued transactions and computes due date (`issueDate + 14 days`).
9. Strategy resolves rate by user role and creates/updates `fine` records.
10. View (`view-fines.html`) renders updated fine table back to user.

───────────────────────────────

## 🧠 6. GRASP Principles by Member

┌──────────────────────────────────────────────────────────────┐
│ 👤 Prajwal Kittali — Security and Auth Lead │
│ 🧩 GRASP Principles Applied │
│ • Information Expert → User.java — lock state lives in User │
│ • Creator → DefaultUserInitializer.java — seeds admin user │
│ • Controller → AuthController.java — routes login events │
│ • Low Coupling → AuthService.java — result record abstraction│
│ 💻 Code Snippet │
│ if (user.getPassword().equals(password)) { │
│ user.resetLockStateOnSuccess(); │
│ userRepository.save(user); │
│ } │
└──────────────────────────────────────────────────────────────┘

┌──────────────────────────────────────────────────────────────┐
│ 👤 Pranav G — Catalog Module Lead │
│ 🧩 GRASP Principles Applied │
│ • Information Expert → Book.java — inventory state in model │
│ • Controller → BookController.java — UI event intake │
│ • Low Coupling → BookService.java — keeps controller thin │
│ • High Cohesion → BookService.java — focused on catalog only│
│ 💻 Code Snippet │
│ if (book.getQuantity() <= 0) { │
│ book.setQuantity(1); │
│ } │
│ bookService.saveBook(book); │
└──────────────────────────────────────────────────────────────┘

┌──────────────────────────────────────────────────────────────┐
│ 👤 Pranav S — Finance and Fine Engine Lead │
│ 🧩 GRASP Principles Applied │
│ • Polymorphism → FineCalculationStrategy.java │
│ • Information Expert → Fine.java and strategy classes │
│ • High Cohesion → Academic/Standard strategy classes │
│ • Low Coupling → FineService.java isolates finance policy │
│ 💻 Code Snippet │
│ long overdueDays = ChronoUnit.DAYS.between(dueDate, now);│
│ String role = resolveRole(transaction); │
│ double amount = resolveStrategy(role).calculate(overdueDays);│
└──────────────────────────────────────────────────────────────┘

┌──────────────────────────────────────────────────────────────┐
│ 👤 Prashant Radder — Transaction Module Lead │
│ 🧩 GRASP Principles Applied │
│ • Controller → TransactionController.java │
│ • Creator (partial) → TransactionService.java saves records │
│ • Information Expert (entity-level) → Transaction.java state│
│ • Low Coupling → TransactionRepository via JPA abstraction │
│ 💻 Code Snippet │
│ @PostMapping │
│ public Transaction createTransaction(@RequestBody Transaction t) {│
│ return transactionService.saveTransaction(t); │
│ } │
└──────────────────────────────────────────────────────────────┘

### 📌 Coverage of 9 GRASP Principles

| Principle            | Status in Project                                                        |
| -------------------- | ------------------------------------------------------------------------ |
| Information Expert   | ✅ Implemented                                                           |
| Creator              | ✅ Implemented                                                           |
| Controller           | ✅ Implemented                                                           |
| Low Coupling         | ✅ Implemented                                                           |
| High Cohesion        | ✅ Implemented                                                           |
| Polymorphism         | ✅ Implemented                                                           |
| Pure Fabrication     | ✅ Implemented (Service/Repository layers)                               |
| Indirection          | ✅ Implemented (Repository abstraction, interceptor, strategy interface) |
| Protected Variations | ✅ Implemented (strategy + service abstractions + context smoke test)    |

───────────────────────────────

## 🧩 7. Design Patterns by Member

┌──────────────────────────────────────────────────────────────┐
│ 👤 Prajwal Kittali │
│ 🎨 Patterns Used │
│ • Singleton 🔒 → AuthController/AuthService/Security config │
│ • MVC 🏛️ → Auth flow split across controller/service/view │
│ • Proxy 🧱 → UserRepository generated by Spring Data JPA │
│ • Observer 👁️ → ❌ Not Implemented Yet │
│ 💻 Snippet │
│ @Service │
│ public class AuthService extends BaseService { ... } │
└──────────────────────────────────────────────────────────────┘

┌──────────────────────────────────────────────────────────────┐
│ 👤 Pranav G │
│ 🎨 Patterns Used │
│ • Singleton 🔒 → BookService/BookController beans │
│ • Template Method 🧩 → BaseService.performActionWithLogging │
│ • Proxy 🧱 → BookRepository JPA proxy │
│ • Factory 🏭 → ❌ Not Implemented Yet │
│ 💻 Snippet │
│ return performActionWithLogging("save-book", () -> { ... },│
│ () -> bookRepository.save(book)); │
└──────────────────────────────────────────────────────────────┘

┌──────────────────────────────────────────────────────────────┐
│ 👤 Pranav S │
│ 🎨 Patterns Used │
│ • Strategy 🧠 → FineCalculationStrategy + two implementations│
│ • Singleton 🔒 → FineService and FineController │
│ • Template Method 🧩 → BaseService in FineService │
│ • Facade 🪟 → ❌ Not Implemented Yet │
│ 💻 Snippet │
│ return fineStrategies.stream() │
│ .filter(s -> s.supports(role)) │
│ .findFirst(); │
└──────────────────────────────────────────────────────────────┘

┌──────────────────────────────────────────────────────────────┐
│ 👤 Prashant Radder │
│ 🎨 Patterns Used │
│ • MVC 🏛️ → REST transaction controller + service + model │
│ • Singleton 🔒 → TransactionService bean │
│ • Builder 🧱 → ❌ Not Implemented Yet │
│ • Observer 👁️ → ❌ Not Implemented Yet │
│ 💻 Snippet │
│ @GetMapping │
│ public List<Transaction> getAllTransactions() { │
│ return transactionService.getAllTransactions(); │
│ } │
└──────────────────────────────────────────────────────────────┘

### 📌 Required Pattern Coverage Status

| Pattern   | Status                 |
| --------- | ---------------------- |
| Singleton | ✅ Implemented         |
| Factory   | ❌ Not Implemented Yet |
| Observer  | ❌ Not Implemented Yet |
| Strategy  | ✅ Implemented         |
| MVC       | ✅ Implemented         |
| Facade    | ❌ Not Implemented Yet |
| Builder   | ❌ Not Implemented Yet |

───────────────────────────────

## 📁 8. File and Folder Structure

```text
📦 Smart-Library
├── 📄 .gitattributes                            - Git attributes
├── 📄 .gitignore                                - Ignore rules
├── 📁 .mvn/
│   └── 📁 wrapper/
│       └── 📄 maven-wrapper.properties          - Maven wrapper config
├── 📄 mvnw                                      - Maven wrapper (Linux/macOS)
├── 📄 mvnw.cmd                                  - Maven wrapper (Windows)
├── 📄 pom.xml                                   - Maven build and dependencies
├── 📄 README.md                                 - Project documentation
├── 📁 src/
│   ├── 📁 main/
│   │   ├── 📁 java/com/library/Smart_Library/
│   │   │   ├── 📄 SmartLibraryApplication.java          [C] - App bootstrap entry point
│   │   │   ├── 📁 config/
│   │   │   │   ├── 📄 AuthInterceptor.java              [C] - Session guard interceptor
│   │   │   │   ├── 📄 DefaultUserInitializer.java       [M] - Seeds default admin
│   │   │   │   └── 📄 WebMvcSecurityConfig.java         [C] - Registers protected routes
│   │   │   ├── 📁 controller/
│   │   │   │   ├── 📄 AuthController.java               [C] - Login/dashboard routes
│   │   │   │   ├── 📄 BookController.java               [C] - Book UI routes
│   │   │   │   ├── 📄 FineController.java               [C] - Fine report routes
│   │   │   │   └── 📄 TransactionController.java        [C] - Transaction REST routes
│   │   │   ├── 📁 model/
│   │   │   │   ├── 📄 Book.java                         [M] - Book entity
│   │   │   │   ├── 📄 Fine.java                         [M] - Fine entity
│   │   │   │   ├── 📄 Transaction.java                  [M] - Transaction entity
│   │   │   │   └── 📄 User.java                         [M] - User auth entity
│   │   │   ├── 📁 repository/
│   │   │   │   ├── 📄 BookRepository.java               [M] - Book persistence
│   │   │   │   ├── 📄 FineRepository.java               [M] - Fine persistence
│   │   │   │   ├── 📄 TransactionRepository.java        [M] - Transaction persistence
│   │   │   │   └── 📄 UserRepository.java               [M] - User persistence
│   │   │   └── 📁 service/
│   │   │       ├── 📄 AuthService.java                  [M] - Auth business logic
│   │   │       ├── 📄 BaseService.java                  [M] - Template base workflow
│   │   │       ├── 📄 BookService.java                  [M] - Catalog logic
│   │   │       ├── 📄 FineService.java                  [M] - Fine calculation logic
│   │   │       ├── 📄 TransactionService.java           [M] - Transaction logic
│   │   │       └── 📁 strategy/
│   │   │           ├── 📄 FineCalculationStrategy.java  [M] - Strategy contract
│   │   │           ├── 📄 AcademicFineRateStrategy.java [M] - Faculty fine strategy
│   │   │           └── 📄 StandardFineRateStrategy.java [M] - Student/admin strategy
│   │   └── 📁 resources/
│   │       ├── 📄 application.properties                - Runtime config
│   │       ├── 📁 sql/
│   │       │   └── 📄 Library management.sql            - SQL setup and checks
│   │       ├── 📁 static/css/
│   │       │   └── 📄 theme.css                         - UI styling
│   │       └── 📁 templates/
│   │           ├── 📄 books.html                        [V] - Catalog page
│   │           ├── 📄 dashboard.html                    [V] - Main module hub
│   │           ├── 📄 login.html                        [V] - Login page
│   │           ├── 📄 transaction-list.html             [V] - Transaction page
│   │           ├── 📄 view-fines.html                   [V] - Fine report page
│   │           └── 📁 fragments/
│   │               └── 📄 layout.html                   [V] - Shared footer/nav fragment
│   └── 📁 test/java/com/library/Smart_Library/
│       └── 📄 SmartLibraryApplicationTests.java         - Context smoke test
└── 📁 target/                                           - Build output (generated)
```

───────────────────────────────

## 👥 9. Team Contributions Individual Breakdown

### 👤 Prajwal Kittali — Security and Authentication Architect

- 🎯 Responsibilities:
  - Login flow and session handling
  - Lockout logic integration
  - Route-level protection and default admin setup
- 📁 Files Created/Modified:
  - `config/AuthInterceptor.java`
  - `config/DefaultUserInitializer.java`
  - `config/WebMvcSecurityConfig.java`
  - `controller/AuthController.java`
  - `model/User.java`
  - `repository/UserRepository.java`
  - `service/AuthService.java`
- 🧠 GRASP Principles:
  - Controller (`AuthController.java`)
  - Information Expert (`User.java`)
  - Creator (`DefaultUserInitializer.java`)
  - Low Coupling (`AuthService.java`)
- 🧩 Design Patterns:
  - Singleton, MVC, Proxy, Template Method
- ⭐ Signature Feature:
  - Account lock after repeated failed login attempts with clear session-safe feedback.
- 💻 Highlight Snippet:

```java
if (user.isAccountLocked()) {
	return LoginResult.locked("Your account is locked after 3 failed attempts.");
}
```

### 👤 Pranav G — Catalog and Inventory Architect

- 🎯 Responsibilities:
  - Book entity and catalog CRUD surface
  - Book service validation and persistence flow
  - Catalog page integration
- 📁 Files Created/Modified:
  - `model/Book.java`
  - `controller/BookController.java`
  - `service/BookService.java`
  - `repository/BookRepository.java`
- 🧠 GRASP Principles:
  - Controller (`BookController.java`)
  - Information Expert (`Book.java`)
  - Low Coupling (`BookService.java`)
  - High Cohesion (`BookService.java`)
- 🧩 Design Patterns:
  - Singleton, Template Method, Proxy, MVC
- ⭐ Signature Feature:
  - Centralized add-book workflow with sanity checks before persistence.
- 💻 Highlight Snippet:

```java
if (book.getQuantity() <= 0) {
	book.setQuantity(1);
}
bookService.saveBook(book);
```

### 👤 Pranav Sharma (Pranav S) — Finance and Strategy Architect

- 🎯 Responsibilities:
  - Fine model and repository
  - Fine report controller
  - Overdue computation and strategy-based rate selection
- 📁 Files Created/Modified:
  - `model/Fine.java`
  - `controller/FineController.java`
  - `service/FineService.java`
  - `repository/FineRepository.java`
  - `service/strategy/FineCalculationStrategy.java`
  - `service/strategy/AcademicFineRateStrategy.java`
  - `service/strategy/StandardFineRateStrategy.java`
- 🧠 GRASP Principles:
  - Polymorphism (`FineCalculationStrategy.java`)
  - Information Expert (`Fine.java`, strategy classes)
  - High Cohesion (strategy classes)
  - Low Coupling (`FineService.java`)
- 🧩 Design Patterns:
  - Strategy, Singleton, Template Method, Proxy
- ⭐ Signature Feature:
  - Automatic overdue detection and role-based fine amount computation.
- 💻 Highlight Snippet:

```java
long overdueDays = ChronoUnit.DAYS.between(dueDate, currentDate);
double amount = resolveStrategy(role).calculate(overdueDays);
calculatedFines.add(createOrUpdateFineForTransaction(transaction, amount));
```

### 👤 Prashant Radder — Transaction Workflow Architect

- 🎯 Responsibilities:
  - Transaction model and repository foundation
  - REST endpoints for transaction list and creation
- 📁 Files Created/Modified:
  - `model/Transaction.java`
  - `controller/TransactionController.java`
  - `service/TransactionService.java`
  - `repository/TransactionRepository.java`
- 🧠 GRASP Principles:
  - Controller (`TransactionController.java`)
  - Creator (service create/save behavior)
  - Information Expert (`Transaction.java`)
- 🧩 Design Patterns:
  - Singleton, MVC
- ⭐ Signature Feature:
  - Minimal REST interface enabling transaction creation and retrieval for integration.
- 💻 Highlight Snippet:

```java
@PostMapping
public Transaction createTransaction(@RequestBody Transaction transaction) {
	return transactionService.saveTransaction(transaction);
}
```

───────────────────────────────

## 🚀 10. Installation and Setup for Beginners

### ✅ Prerequisites

- ☕ Java 17 or newer
  - Download: https://adoptium.net/
- 🗄️ MySQL 8.x
  - Download: https://dev.mysql.com/downloads/mysql/
- 🧰 Git
  - Download: https://git-scm.com/downloads

> 💡 Maven installation is optional because Maven Wrapper is included.

### 📥 Step-by-Step Setup

#### 🪟 Windows

```bash
git clone https://github.com/Prajwal-Kittali-17/OOAD-Smart-Library-Management.git
cd OOAD-Smart-Library-Management

set DB_URL=jdbc:mysql://localhost:3306/library_db?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Kolkata
set DB_USERNAME=root
set DB_PASSWORD=your_mysql_password

mvnw.cmd clean compile
mvnw.cmd clean test
mvnw.cmd spring-boot:run
```

#### 🍎 macOS

```bash
git clone https://github.com/Prajwal-Kittali-17/OOAD-Smart-Library-Management.git
cd OOAD-Smart-Library-Management

export DB_URL="jdbc:mysql://localhost:3306/library_db?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Kolkata"
export DB_USERNAME="root"
export DB_PASSWORD="your_mysql_password"

chmod +x mvnw
./mvnw clean compile
./mvnw clean test
./mvnw spring-boot:run
```

#### 🐧 Linux

```bash
git clone https://github.com/Prajwal-Kittali-17/OOAD-Smart-Library-Management.git
cd OOAD-Smart-Library-Management

export DB_URL="jdbc:mysql://localhost:3306/library_db?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Kolkata"
export DB_USERNAME="root"
export DB_PASSWORD="your_mysql_password"

chmod +x mvnw
./mvnw clean compile
./mvnw clean test
./mvnw spring-boot:run
```

### ⚙️ `.env.example` Style Reference

```bash
DB_URL=jdbc:mysql://localhost:3306/library_db?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Kolkata
DB_USERNAME=root
DB_PASSWORD=your_mysql_password
```

### 🔄 Alternate Port (if 8080 is busy)

```bash
# Windows
mvnw.cmd spring-boot:run "-Dspring-boot.run.arguments=--server.port=8083"

# macOS/Linux
./mvnw spring-boot:run "-Dspring-boot.run.arguments=--server.port=8083"
```

🎉 If you see the login page and dashboard modules load, congratulations — it is working!

───────────────────────────────

## ⌨️ 11. Commands Reference Table

| ⌨️ Command                                                                  | 📋 What It Does                           | ⏰ When to Use         |
| --------------------------------------------------------------------------- | ----------------------------------------- | ---------------------- |
| `mvnw.cmd clean compile`                                                    | Compiles project on Windows               | First local validation |
| `./mvnw clean compile`                                                      | Compiles project on macOS/Linux           | First local validation |
| `mvnw.cmd clean test`                                                       | Runs test suite on Windows                | Before push/PR         |
| `./mvnw clean test`                                                         | Runs test suite on macOS/Linux            | Before push/PR         |
| `mvnw.cmd spring-boot:run`                                                  | Starts app on default port 8080 (Windows) | Normal local run       |
| `./mvnw spring-boot:run`                                                    | Starts app on default port 8080 (Unix)    | Normal local run       |
| `mvnw.cmd spring-boot:run "-Dspring-boot.run.arguments=--server.port=8083"` | Starts app on custom port (Windows)       | Port conflict          |
| `./mvnw spring-boot:run "-Dspring-boot.run.arguments=--server.port=8083"`   | Starts app on custom port (Unix)          | Port conflict          |
| `git checkout main && git pull origin main`                                 | Sync latest main                          | Before new branch      |
| `git checkout -b member/<name>-<module>`                                    | Create member branch                      | Team contribution flow |

───────────────────────────────

## 🗺️ 12. Project Tour Full Story

🎬 **Scene 1: A student opens the app**

- Controller: `AuthController` (`GET /login`)
- Model: `User` (login form object)
- View: `login.html`

🎬 **Scene 2: They sign in**

- Controller: `AuthController` (`POST /login`)
- Service: `AuthService.authenticate(...)`
- Model: `User` lockout state and failed attempts
- View: `dashboard.html` after success

🎬 **Scene 3: They browse books**

- Controller: `BookController` (`GET /books`)
- Service: `BookService.getAllBooks()`
- Model: `Book`
- View: `books.html`

🎬 **Scene 4: They borrow a book (transaction created)**

- Controller: `TransactionController` (`POST /transactions`)
- Service: `TransactionService.saveTransaction(...)`
- Model: `Transaction`
- View/API Response: JSON transaction object

🎬 **Scene 5: Admin checks overdue fines**

- Controller: `FineController` (`GET /fines`)
- Service: `FineService.getTrackedFines()`
- Models: `Transaction`, `Fine`, `User`
- View: `view-fines.html`

🎬 **Scene 6: Admin marks fine paid**

- Controller: `FineController` (`POST /fines/{fineId}/pay`)
- Service: `FineService.markFineAsPaid(...)`
- Model: `Fine.paymentStatus`
- View: Redirect back to `view-fines.html`

───────────────────────────────

## 🗄️ 13. Database Schema

### 📋 Runtime Entity Schema (Spring JPA)

| Table         | Columns                                                              | Keys and Constraints                                                 |
| ------------- | -------------------------------------------------------------------- | -------------------------------------------------------------------- |
| `users`       | `id`, `username`, `password`, `role`, `is_locked`, `failed_attempts` | 🔑 PK: `id`, unique username                                         |
| `book`        | `id`, `isbn`, `title`, `author`, `price`, `quantity`                 | 🔑 PK: `id`                                                          |
| `transaction` | `id`, `user_id`, `book_id`, `issue_date`, `return_date`, `status`    | 🔑 PK: `id`, 🔗 FK `user_id -> users.id`, 🔗 FK `book_id -> book.id` |
| `fine`        | `id`, `transaction_id`, `amount`, `payment_status`                   | 🔑 PK: `id` (link by `transaction_id` in service/repository)         |

### 📊 ASCII ERD

```text
users (1) -------- (N) transaction (N) -------- (1) book
	id PK                 id PK                        id PK
												user_id FK -> users.id      title
												book_id FK -> book.id       quantity
												issue_date
												return_date
												status

transaction (1) -------- (0..1 logical) fine
	id PK                    id PK
													 transaction_id (linked in service/repo)
													 amount
													 payment_status
```

### 🧪 Sample Rows

```sql
-- users
id | username | role     | is_locked | failed_attempts
1  | admin    | ADMIN    | 0         | 0
2  | faculty1 | FACULTY  | 0         | 0

-- book
id | isbn          | title           | quantity
1  | 9780134685991 | Effective Java  | 6

-- transaction
id | user_id | book_id | issue_date           | return_date | status
1  | 1       | 1       | 2026-04-10T10:00:00  | NULL        | ISSUED

-- fine
id | transaction_id | amount | payment_status
1  | 1              | 50.0   | PENDING
```

> ⚠️ Note: SQL script and runtime entities have small legacy differences (example: `due_date` appears in SQL script but due date is derived from `issueDate + 14 days` in current `FineService`).

───────────────────────────────

## 🔌 14. API Endpoints

| Method | 🔗 Route              | 📋 Description            | 🔐 Auth? | 📤 Request            | 📥 Response                 |
| ------ | --------------------- | ------------------------- | -------- | --------------------- | --------------------------- |
| GET    | `/`                   | Redirect root to login    | No       | None                  | Redirect `/login`           |
| GET    | `/login`              | Load login page           | No       | Query params optional | HTML page                   |
| POST   | `/login`              | Process credentials       | No       | Form user credentials | Redirect/HTML with messages |
| GET    | `/dashboard`          | Load dashboard            | Yes      | Session               | HTML page                   |
| GET    | `/books`              | Load book catalog page    | Yes      | None                  | HTML page                   |
| POST   | `/add-book`           | Add a book record         | Yes      | Form fields           | Redirect `/books`           |
| GET    | `/fines`              | Load fine report page     | Yes      | None                  | HTML page                   |
| POST   | `/fines/{fineId}/pay` | Mark fine as paid         | Yes      | Path var `fineId`     | Redirect `/fines`           |
| GET    | `/transactions`       | List transactions (REST)  | Yes      | None                  | JSON list                   |
| POST   | `/transactions`       | Create transaction (REST) | Yes      | JSON transaction body | JSON transaction            |

### ❌ Not Implemented Yet (Template references exist)

- `POST /transactions/issue`
- `POST /transactions/return`

These are referenced in `transaction-list.html` but not currently implemented in `TransactionController`.

───────────────────────────────

## 🧪 15. Testing

### ✅ How to Run Tests

```bash
# Windows
mvnw.cmd clean test

# macOS/Linux
./mvnw clean test
```

### 📁 Current Test Files

- `src/test/java/com/library/Smart_Library/SmartLibraryApplicationTests.java`

### 👥 Which Member Wrote Which Test

- Shared Team Integration: `SmartLibraryApplicationTests.java`

### 🎯 Coverage Goal

- Current: Spring context smoke validation
- Next target: controller/service/repository unit tests per module

### 🧾 Example Output

```text
Tests run: 1, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

───────────────────────────────

## ⚠️ 16. Known Issues and Roadmap

### 🐛 Current Known Issues

- ⚠️ `transaction-list.html` expects fields (`userId`, `bookId`, `dueDate`) not present in current `Transaction` entity.
- ⚠️ Template posts to `/transactions/issue` and `/transactions/return`, but controller currently exposes `/transactions` GET/POST only.
- ⚠️ Hibernate may log FK migration warnings if existing local DB data violates expected relations.

### 🚧 Current Limitations

- No pagination/filter APIs for transactions and books.
- No explicit password hashing yet.
- No dedicated integration tests for business workflows.

### 🔮 Planned Features

- ✅ Implement `/transactions/issue` and `/transactions/return` endpoints.
- ✅ Align transaction template fields with entity model.
- ✅ Add password hashing (BCrypt).
- ✅ Add role-based authorization beyond session login.
- ✅ Add module-level test coverage.

### 👤 Suggested Ownership

- Prajwal Kittali: password hashing + security hardening
- Pranav Gaonkar: advanced catalog search and pagination
- Pranav Sharma: fine analytics and reports
- Prashant Radder: transaction issue/return endpoint completion

───────────────────────────────

## 📖 17. Glossary

- 🏛️ **MVC**: _Like a restaurant: kitchen (Model), menu (View), waiter (Controller)._
- 🧠 **GRASP**: A set of principles to assign responsibilities cleanly in object-oriented design.
- 🧩 **Design Pattern**: A reusable way to solve a recurring software design problem.
- 📦 **OOP**: Programming with objects that combine data and behavior.
- 🔁 **CRUD**: Create, Read, Update, Delete operations.
- 🔌 **API**: A defined contract to communicate between software components.
- 🧭 **Controller**: Receives requests and routes work to services.
- 🧱 **Model**: Represents data and core domain rules.
- 🖼️ **View**: UI layer shown to users.
- 🗃️ **Repository**: Data access abstraction over the database.
- 🧪 **Smoke Test**: Quick test to verify core app wiring works.

───────────────────────────────

## 🤝 18. How to Contribute

### 🔄 Standard Flow

- 🍴 Fork repository
- 🌿 Create feature branch
- 💾 Commit changes with clear message
- 📬 Open Pull Request

### ✅ Coding Standards

- Use meaningful class and method names.
- Keep controllers thin and delegate logic to services.
- Add comments only where they clarify non-obvious behavior.
- Preserve package structure by module (config/controller/model/repository/service).

### 📛 Naming Conventions

- Class names: `PascalCase`
- Methods/fields: `camelCase`
- Endpoints: lower-case with hyphen where relevant

───────────────────────────────

## 📜 19. License and Credits

- License: ❗ No explicit `LICENSE` file found yet.

### 👏 Team Credits

- 👤 Prajwal Kittali — Security and authentication module
- 👤 Pranav Gaonkar — Book catalog module
- 👤 Pranav Sharma — Fine and strategy module
- 👤 Prashant Radder — Transaction module
- 👥 Shared Team Integration — Base infrastructure and context tests

### 📦 Major Libraries and Frameworks

- Spring Boot
- Spring Data JPA
- Spring Web MVC
- Thymeleaf
- MySQL Connector/J
- Lombok
- JUnit 5 (via spring-boot-starter-test)

───────────────────────────────

✅ README complete — 19 sections, ~4300 words
