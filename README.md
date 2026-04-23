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
![Database](https://img.shields.io/badge/Database-MySQL-4479A1)
![Build](https://img.shields.io/badge/Build-Maven-brightgreen)

**Tagline:** Borrow smarter, track faster, manage libraries with confidence.

Smart Library Management System is a Spring Boot MVC project that handles login security, book catalog management, transaction tracking, and fine reporting. It is built as an OOAD mini project with clear module ownership and documented GRASP and design pattern usage. The focus is to make the system easy for beginners to use and easy for teams to maintain.

---

## 🗂️ 2. Table of Contents

- [📚 1. Project Title and Hero Section](#-smart-library-management-system)
- [🗂️ 2. Table of Contents](#️-2-table-of-contents)
- [🧒 3. What Is This? Explain Like I Am 10](#-3-what-is-this-explain-like-i-am-10)
- [🎯 4. Features](#-4-features)
- [🏛️ 5. MVC Architecture](#️-5-mvc-architecture)
- [🧠 6. GRASP Principles By Member](#-6-grasp-principles-by-member)
- [🧩 7. Design Patterns By Member](#-7-design-patterns-by-member)
- [📁 8. File and Folder Structure](#-8-file-and-folder-structure)
- [👥 9. Team Contributions Individual Breakdown](#-9-team-contributions-individual-breakdown)
- [🚀 10. Installation and Setup](#-10-installation-and-setup)
- [⌨️ 11. Commands Reference Table](#️-11-commands-reference-table)
- [🗺️ 12. Project Tour The Full Story](#️-12-project-tour-the-full-story)
- [🗄️ 13. Database Schema](#️-13-database-schema)
- [🧪 14. Testing](#-14-testing)
- [⚠️ 15. Known Issues and Roadmap](#️-15-known-issues-and-roadmap)
- [📖 16. Glossary](#-16-glossary)
- [🤝 17. How to Contribute](#-17-how-to-contribute)
- [📜 18. License and Credits](#-18-license-and-credits)

---

## 🧒 3. What Is This? Explain Like I Am 10

Imagine you walk into a magical library where one smart helper knows which books are available, who borrowed what, and who needs to return books on time.

This app is that helper:

- 📚 It keeps a clean book catalog.
- 👤 It tracks users and secure login status.
- 🔄 It records borrowing transactions.
- 💰 It calculates fines for overdue books.

In a school library analogy, this system acts like a digital librarian that never forgets records and never misplaces a book entry.

That is exactly what our Smart Library System does.

---

## 🎯 4. Features

### 📖 Book Management

- ✅ **Add Book** — Save title, author, ISBN, quantity, and price details.
- ✅ **View Catalog** — Display all books in a readable table.
- ✅ **Input Validation** — Prevent invalid quantity and negative pricing.

### 👤 Member Management

- ✅ **User Entity** — Manage username, password, role, lock state, and failed attempts.
- ✅ **Session Tracking** — Keep active logged-in user in session.

### 🔐 Admin Controls

- ✅ **Account Lockout** — Lock account after repeated failed login attempts.
- ✅ **Protected Pages** — Block unauthorized access using interceptor.
- ✅ **Default Admin Seed** — Auto-create admin user on first startup.

### 🔍 Search and Filter

- ✅ **Transaction Listing API** — Retrieve transaction records via REST endpoint.
- ❌ **Advanced Search and Filters** — Not implemented yet.

### 📊 Reports and Stats

- ✅ **Fine Report Page** — Display pending and paid fines.
- ✅ **Role-Based Fine Calculation** — Use strategy classes for rate selection.
- ✅ **Mark Fine as Paid** — Update fine status from UI.

---

## 🏛️ 5. MVC Architecture

Think of MVC like a restaurant 🍽️:

- **Model = Kitchen** (where core data logic is prepared)
- **View = Menu** (what users see)
- **Controller = Waiter** (takes request and routes it correctly)

### 🔄 MVC Architecture Diagram

```text
User Browser
    |
    v
[View: Thymeleaf HTML]
    |
    v
[Controller Layer]
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
[Response back to View]
```

### 📄 MVC Mapping Table

| File                         | MVC Role | What it does                                 |
| ---------------------------- | -------- | -------------------------------------------- |
| `AuthController.java`        | C        | Handles login, logout, and dashboard routing |
| `BookController.java`        | C        | Handles catalog page and add-book form       |
| `FineController.java`        | C        | Handles fine report and mark-paid action     |
| `TransactionController.java` | C        | Exposes transaction list/create REST APIs    |
| `User.java`                  | M        | Stores user credentials and lock state logic |
| `Book.java`                  | M        | Stores book entity data                      |
| `Transaction.java`           | M        | Stores issue and return transaction data     |
| `Fine.java`                  | M        | Stores fine amount and payment status        |
| `AuthService.java`           | M        | Authentication business logic                |
| `BookService.java`           | M        | Catalog business logic                       |
| `FineService.java`           | M        | Overdue and fine calculation logic           |
| `TransactionService.java`    | M        | Transaction save/list logic                  |
| `login.html`                 | V        | Login interface                              |
| `dashboard.html`             | V        | Module navigation page                       |
| `books.html`                 | V        | Book catalog interface                       |
| `transaction-list.html`      | V        | Transaction records page                     |
| `view-fines.html`            | V        | Fine report interface                        |

### 🔄 Step-by-Step Flow: User Borrows a Book

1. User opens dashboard view.
2. User navigates to transaction module.
3. UI sends transaction creation request.
4. `TransactionController` receives request.
5. Controller calls `TransactionService.saveTransaction(...)`.
6. Service calls `TransactionRepository.save(...)`.
7. Repository persists row in `transaction` table.
8. `FineService` later checks issued records for overdue state.
9. Strategy class decides fine rate based on user role.
10. Fine report view renders updated fine status back to user.

---

## 🧠 6. GRASP Principles By Member

---

### 👤 Prajwal Kittali — Security and Auth Lead

> 🎯 _Built secure login flow, lockout logic, and route protection for the app._

#### 🧠 GRASP Principles Applied

| 🧩 Principle       | 📄 File                       | 💡 Why It Applies                                        |
| ------------------ | ----------------------------- | -------------------------------------------------------- |
| Information Expert | `User.java`                   | User object owns lock state and failed attempt behavior  |
| Creator            | `DefaultUserInitializer.java` | Creates default admin account during startup             |
| Controller         | `AuthController.java`         | First layer that handles login UI requests               |
| Low Coupling       | `AuthService.java`            | Returns structured login result so controller stays thin |

#### 💻 Code Highlight

```java
// AuthService.java — successful login path
if (user.getPassword().equals(password)) {
    user.resetLockStateOnSuccess();
    userRepository.save(user);
    return LoginResult.success(user.getUsername());
}
```

#### 📁 Files Owned

- `User.java` — [M] User entity with lock/unlock logic
- `AuthController.java` — [C] Routes login events
- `AuthService.java` — [M] Authentication business logic
- `AuthInterceptor.java` — [C] Blocks unauthorized route access
- `WebMvcSecurityConfig.java` — [C] Registers protected paths
- `DefaultUserInitializer.java` — [M] Seeds admin account
- `UserRepository.java` — [M] User persistence access

---

### 👤 Pranav G — Catalog Module Lead

> 🎯 _Built the book catalog workflow from model to controller and persistence layer._

#### 🧠 GRASP Principles Applied

| 🧩 Principle       | 📄 File               | 💡 Why It Applies                                    |
| ------------------ | --------------------- | ---------------------------------------------------- |
| Information Expert | `Book.java`           | Book entity owns inventory-related state             |
| Controller         | `BookController.java` | Handles catalog requests from UI                     |
| Low Coupling       | `BookService.java`    | Service isolates controller from persistence details |
| High Cohesion      | `BookService.java`    | Service focuses only on catalog actions              |

#### 💻 Code Highlight

```java
// BookController.java — input guardrails
if (book.getQuantity() <= 0) {
    book.setQuantity(1);
}
if (book.getPrice() < 0) {
    book.setPrice(0);
}
bookService.saveBook(book);
```

#### 📁 Files Owned

- `Book.java` — [M] Book entity
- `BookController.java` — [C] Catalog routes and form processing
- `BookService.java` — [M] Catalog business logic
- `BookRepository.java` — [M] Book persistence abstraction

---

### 👤 Pranav S — Finance and Fine Engine Lead

> 🎯 _Built overdue fine calculation and role-based strategy workflow._

#### 🧠 GRASP Principles Applied

| 🧩 Principle       | 📄 File                         | 💡 Why It Applies                           |
| ------------------ | ------------------------------- | ------------------------------------------- |
| Polymorphism       | `FineCalculationStrategy.java`  | Different fine algorithms selected by role  |
| Information Expert | `Fine.java`                     | Fine entity owns amount and payment status  |
| High Cohesion      | `AcademicFineRateStrategy.java` | One class handles one specific policy       |
| Low Coupling       | `FineService.java`              | Finance rules are isolated from controllers |

#### 💻 Code Highlight

```java
// FineService.java — strategy-based fine amount
long overdueDays = ChronoUnit.DAYS.between(dueDate, currentDate);
String role = resolveRole(transaction);
double amount = resolveStrategy(role).calculate(overdueDays);
calculatedFines.add(createOrUpdateFineForTransaction(transaction, amount));
```

#### 📁 Files Owned

- `Fine.java` — [M] Fine entity
- `FineController.java` — [C] Finance report routes
- `FineService.java` — [M] Fine business logic
- `FineRepository.java` — [M] Fine persistence access
- `FineCalculationStrategy.java` — [M] Strategy contract
- `AcademicFineRateStrategy.java` — [M] Faculty fine policy
- `StandardFineRateStrategy.java` — [M] Student/admin fine policy

---

### 👤 Prashant Radder — Transaction Module Lead

> 🎯 _Built transaction APIs and persistence flow for borrow/return records._

#### 🧠 GRASP Principles Applied

| 🧩 Principle       | 📄 File                      | 💡 Why It Applies                                 |
| ------------------ | ---------------------------- | ------------------------------------------------- |
| Controller         | `TransactionController.java` | Receives transaction API requests                 |
| Creator            | `TransactionService.java`    | Creates and saves transaction records             |
| Information Expert | `Transaction.java`           | Transaction entity owns issue/return/status state |
| Low Coupling       | `TransactionRepository.java` | Service uses abstraction instead of direct SQL    |

#### 💻 Code Highlight

```java
// TransactionController.java — create transaction endpoint
@PostMapping
public Transaction createTransaction(@RequestBody Transaction transaction) {
    return transactionService.saveTransaction(transaction);
}
```

#### 📁 Files Owned

- `Transaction.java` — [M] Transaction entity
- `TransactionController.java` — [C] Transaction REST APIs
- `TransactionService.java` — [M] Transaction business logic
- `TransactionRepository.java` — [M] Transaction persistence access

---

### ✅ Coverage Across 9 GRASP Principles

| Principle            | Status                                                    |
| -------------------- | --------------------------------------------------------- |
| Information Expert   | ✅ Covered                                                |
| Creator              | ✅ Covered                                                |
| Controller           | ✅ Covered                                                |
| Low Coupling         | ✅ Covered                                                |
| High Cohesion        | ✅ Covered                                                |
| Polymorphism         | ✅ Covered                                                |
| Pure Fabrication     | ✅ Covered (service/repository layers)                    |
| Indirection          | ✅ Covered (interceptor/repository/strategy abstractions) |
| Protected Variations | ✅ Covered (strategy contract and modular services)       |

---

## 🧩 7. Design Patterns By Member

---

### 👤 Prajwal Kittali — Security Pattern Use

> 🎯 _Designed a structured and secure authentication flow using Spring MVC layers._

#### 🧩 Patterns Applied

| 🧩 Pattern | 📄 File               | 💡 Why It Applies                                |
| ---------- | --------------------- | ------------------------------------------------ |
| Singleton  | `AuthService.java`    | Spring creates one shared service bean           |
| MVC        | `AuthController.java` | Separates auth handling from view rendering      |
| Proxy      | `UserRepository.java` | Spring Data generates repository implementation  |
| Observer   | `Not implemented`     | No event publisher/subscriber workflow currently |
| Facade     | `Not implemented`     | No unified facade class for auth subsystem       |

#### 💻 Code Highlight

```java
@Service
public class AuthService extends BaseService {
    @Autowired
    private UserRepository userRepository;
}
```

---

### 👤 Pranav G — Catalog Pattern Use

> 🎯 _Implemented a clean catalog pipeline with reusable service flow._

#### 🧩 Patterns Applied

| 🧩 Pattern      | 📄 File               | 💡 Why It Applies                                |
| --------------- | --------------------- | ------------------------------------------------ |
| Singleton       | `BookService.java`    | Spring service bean is singleton by default      |
| Template Method | `BaseService.java`    | Reusable validate-then-execute workflow          |
| Proxy           | `BookRepository.java` | Repository implementation is generated by Spring |
| Factory         | `Not implemented`     | No explicit factory object creation class yet    |

#### 💻 Code Highlight

```java
return performActionWithLogging("save-book", () -> {
    if (book == null) {
        throw new IllegalArgumentException("Book is required.");
    }
}, () -> bookRepository.save(book));
```

---

### 👤 Pranav S — Finance Pattern Use

> 🎯 _Built the fine engine with pluggable strategy behavior by user role._

#### 🧩 Patterns Applied

| 🧩 Pattern      | 📄 File                        | 💡 Why It Applies                                   |
| --------------- | ------------------------------ | --------------------------------------------------- |
| Strategy        | `FineCalculationStrategy.java` | Selects fine algorithm at runtime by role           |
| Singleton       | `FineService.java`             | Service bean reused across requests                 |
| Template Method | `BaseService.java`             | Fine operations run through common service template |
| Facade          | `Not implemented`              | No dedicated facade for finance module              |
| Builder         | `Not implemented`              | No builder-based object construction currently      |

#### 💻 Code Highlight

```java
return fineStrategies.stream()
    .filter(strategy -> strategy.supports(role))
    .findFirst()
    .orElseGet(() -> defaultStrategy());
```

---

### 👤 Prashant Radder — Transaction Pattern Use

> 🎯 _Delivered transaction API endpoints with clear model-service-controller separation._

#### 🧩 Patterns Applied

| 🧩 Pattern | 📄 File                      | 💡 Why It Applies                                           |
| ---------- | ---------------------------- | ----------------------------------------------------------- |
| MVC        | `TransactionController.java` | Controller routes, service handles logic, model stores data |
| Singleton  | `TransactionService.java`    | Service managed as singleton Spring bean                    |
| Observer   | `Not implemented`            | No listener-based transaction event mechanism               |
| Builder    | `Not implemented`            | Transaction object not built with builder pattern           |

#### 💻 Code Highlight

```java
@GetMapping
public List<Transaction> getAllTransactions() {
    return transactionService.getAllTransactions();
}
```

---

### ✅ Pattern Coverage Summary

| Pattern   | Status             |
| --------- | ------------------ |
| Singleton | ✅ Implemented     |
| Factory   | ❌ Not implemented |
| Observer  | ❌ Not implemented |
| Strategy  | ✅ Implemented     |
| Facade    | ❌ Not implemented |
| Builder   | ❌ Not implemented |
| MVC       | ✅ Implemented     |

---

## 📁 8. File and Folder Structure

```text
📦 Smart-Library/
├── 📄 README.md
├── 📄 pom.xml
├── 📄 mvnw
├── 📄 mvnw.cmd
├── 📁 .mvn/
│   └── 📁 wrapper/
│       └── 📄 maven-wrapper.properties
├── 📁 src/
│   ├── 📁 main/
│   │   ├── 📁 java/com/library/Smart_Library/
│   │   │   ├── 📄 SmartLibraryApplication.java           [C] — App bootstrap
│   │   │   ├── 📁 config/
│   │   │   │   ├── 📄 AuthInterceptor.java               [C] — Route auth guard
│   │   │   │   ├── 📄 DefaultUserInitializer.java        [M] — Admin seed
│   │   │   │   └── 📄 WebMvcSecurityConfig.java          [C] — Interceptor config
│   │   │   ├── 📁 controller/
│   │   │   │   ├── 📄 AuthController.java                [C] — Login routes
│   │   │   │   ├── 📄 BookController.java                [C] — Book routes
│   │   │   │   ├── 📄 FineController.java                [C] — Fine routes
│   │   │   │   └── 📄 TransactionController.java         [C] — Transaction APIs
│   │   │   ├── 📁 model/
│   │   │   │   ├── 📄 Book.java                          [M] — Book entity
│   │   │   │   ├── 📄 User.java                          [M] — User entity
│   │   │   │   ├── 📄 Transaction.java                   [M] — Transaction entity
│   │   │   │   └── 📄 Fine.java                          [M] — Fine entity
│   │   │   ├── 📁 repository/
│   │   │   │   ├── 📄 BookRepository.java                [M] — Book data access
│   │   │   │   ├── 📄 UserRepository.java                [M] — User data access
│   │   │   │   ├── 📄 TransactionRepository.java         [M] — Transaction data access
│   │   │   │   └── 📄 FineRepository.java                [M] — Fine data access
│   │   │   └── 📁 service/
│   │   │       ├── 📄 BaseService.java                   [M] — Template base
│   │   │       ├── 📄 AuthService.java                   [M] — Auth logic
│   │   │       ├── 📄 BookService.java                   [M] — Catalog logic
│   │   │       ├── 📄 TransactionService.java            [M] — Transaction logic
│   │   │       ├── 📄 FineService.java                   [M] — Fine logic
│   │   │       └── 📁 strategy/
│   │   │           ├── 📄 FineCalculationStrategy.java   [M] — Strategy interface
│   │   │           ├── 📄 AcademicFineRateStrategy.java  [M] — Faculty fine policy
│   │   │           └── 📄 StandardFineRateStrategy.java  [M] — Standard fine policy
│   │   └── 📁 resources/
│   │       ├── 📄 application.properties
│   │       ├── 📁 sql/
│   │       │   └── 📄 Library management.sql
│   │       ├── 📁 static/css/
│   │       │   └── 📄 theme.css
│   │       └── 📁 templates/
│   │           ├── 📄 login.html                         [V] — Login page
│   │           ├── 📄 dashboard.html                     [V] — Dashboard
│   │           ├── 📄 books.html                         [V] — Catalog page
│   │           ├── 📄 transaction-list.html              [V] — Transaction page
│   │           ├── 📄 view-fines.html                    [V] — Fine report
│   │           └── 📁 fragments/
│   │               └── 📄 layout.html                    [V] — Shared UI fragment
│   └── 📁 test/java/com/library/Smart_Library/
│       └── 📄 SmartLibraryApplicationTests.java          [T] — Context smoke test
└── 📁 target/                                             — Build output
```

---

## 👥 9. Team Contributions Individual Breakdown

### 👤 Prajwal Kittali — Security and Authentication Architect

> 🎯 _Made the system secure first by implementing login, lockout, and route protection._

- 🎯 What they built:
  - Login processing and dashboard routing
  - Account lock/unlock behavior
  - Interceptor-based page protection
- 📁 Files they own:
  - `AuthController.java`
  - `AuthService.java`
  - `User.java`
  - `AuthInterceptor.java`
  - `WebMvcSecurityConfig.java`
  - `DefaultUserInitializer.java`
  - `UserRepository.java`

| 🧠 GRASP           | 📄 File                       | 💡 Why                           |
| ------------------ | ----------------------------- | -------------------------------- |
| Information Expert | `User.java`                   | User owns lock state transitions |
| Creator            | `DefaultUserInitializer.java` | Creates initial admin user       |
| Controller         | `AuthController.java`         | Handles login request lifecycle  |

| 🧩 Pattern | 📄 File               | 💡 Why                                     |
| ---------- | --------------------- | ------------------------------------------ |
| Singleton  | `AuthService.java`    | Spring singleton service bean              |
| Proxy      | `UserRepository.java` | Generated Spring Data proxy                |
| MVC        | `AuthController.java` | Controller layer separated from model/view |

- ⭐ Signature feature:
  - Secure login flow with failed-attempt tracking and account lockout after threshold breaches.

```java
if (user.isAccountLocked()) {
    return LoginResult.locked("Your account is locked after 3 failed attempts.");
}
```

---

### 👤 Pranav G — Catalog and Inventory Architect

> 🎯 _Built the complete book catalog module from form input to database save._

- 🎯 What they built:
  - Catalog view and add-book endpoint
  - Catalog service with validation
  - Book persistence abstraction
- 📁 Files they own:
  - `Book.java`
  - `BookController.java`
  - `BookService.java`
  - `BookRepository.java`

| 🧠 GRASP           | 📄 File               | 💡 Why                        |
| ------------------ | --------------------- | ----------------------------- |
| Controller         | `BookController.java` | Routes catalog requests       |
| Information Expert | `Book.java`           | Owns inventory fields         |
| High Cohesion      | `BookService.java`    | Focused only on catalog logic |

| 🧩 Pattern      | 📄 File               | 💡 Why                            |
| --------------- | --------------------- | --------------------------------- |
| Template Method | `BaseService.java`    | Shared service execution template |
| Singleton       | `BookService.java`    | Spring-managed singleton bean     |
| Proxy           | `BookRepository.java` | JPA proxy implementation          |

- ⭐ Signature feature:
  - Clean add-book pipeline with automatic correction for invalid quantity/price values.

```java
if (book.getQuantity() <= 0) {
    book.setQuantity(1);
}
bookService.saveBook(book);
```

---

### 👤 Pranav S — Finance and Strategy Architect

> 🎯 _Created role-aware fine logic using strategy pattern and overdue tracking._

- 🎯 What they built:
  - Fine report and payment update flow
  - Strategy interface and rate implementations
  - Fine creation/update orchestration in service layer
- 📁 Files they own:
  - `Fine.java`
  - `FineController.java`
  - `FineService.java`
  - `FineRepository.java`
  - `FineCalculationStrategy.java`
  - `AcademicFineRateStrategy.java`
  - `StandardFineRateStrategy.java`

| 🧠 GRASP           | 📄 File                        | 💡 Why                       |
| ------------------ | ------------------------------ | ---------------------------- |
| Polymorphism       | `FineCalculationStrategy.java` | Different behavior by role   |
| Information Expert | `Fine.java`                    | Owns fine state              |
| Low Coupling       | `FineService.java`             | Keeps finance rules isolated |

| 🧩 Pattern      | 📄 File                        | 💡 Why                      |
| --------------- | ------------------------------ | --------------------------- |
| Strategy        | `FineCalculationStrategy.java` | Runtime algorithm selection |
| Template Method | `BaseService.java`             | Reusable service workflow   |
| Singleton       | `FineService.java`             | Shared Spring bean          |

- ⭐ Signature feature:
  - Automatic overdue detection using issue date + borrow window, then dynamic role-based fine computation.

```java
LocalDate dueDate = resolveDueDate(transaction);
long overdueDays = ChronoUnit.DAYS.between(dueDate, currentDate);
double amount = resolveStrategy(role).calculate(overdueDays);
```

---

### 👤 Prashant Radder — Transaction Workflow Architect

> 🎯 _Implemented core transaction APIs to store and expose borrowing activity._

- 🎯 What they built:
  - Transaction list API
  - Transaction create API
  - Transaction service/repository base
- 📁 Files they own:
  - `Transaction.java`
  - `TransactionController.java`
  - `TransactionService.java`
  - `TransactionRepository.java`

| 🧠 GRASP           | 📄 File                      | 💡 Why                           |
| ------------------ | ---------------------------- | -------------------------------- |
| Controller         | `TransactionController.java` | Handles transaction requests     |
| Creator            | `TransactionService.java`    | Persists new transaction records |
| Information Expert | `Transaction.java`           | Owns transaction state           |

| 🧩 Pattern | 📄 File                      | 💡 Why                               |
| ---------- | ---------------------------- | ------------------------------------ |
| MVC        | `TransactionController.java` | Clear controller-service-model split |
| Singleton  | `TransactionService.java`    | Spring singleton bean                |
| Builder    | `Not implemented`            | No builder-based construction yet    |

- ⭐ Signature feature:
  - Minimal REST design for transaction creation and retrieval, ready for module expansion.

```java
@GetMapping
public List<Transaction> getAllTransactions() {
    return transactionService.getAllTransactions();
}
```

---

## 🚀 10. Installation and Setup

### ✅ Prerequisites

- Java 17+ — https://adoptium.net/
- Git — https://git-scm.com/downloads
- MySQL 8+ — optional, only if you want to override the default embedded database

### 1️⃣ Clone Repository

```bash
git clone https://github.com/Prajwal-Kittali-17/OOAD-Smart-Library-Management.git
cd OOAD-Smart-Library-Management
```

### 2️⃣ Configure Environment Variables

The app now starts with an embedded H2 database by default, so this step is optional.
Set these variables only if you want to run against MySQL.

#### 🪟 Windows (PowerShell)

```bash
$env:DB_URL="jdbc:mysql://localhost:3306/library_db?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Kolkata"
$env:DB_USERNAME="root"
$env:DB_PASSWORD="your_mysql_password"
```

#### 🍎 macOS

```bash
export DB_URL="jdbc:mysql://localhost:3306/library_db?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Kolkata"
export DB_USERNAME="root"
export DB_PASSWORD="your_mysql_password"
```

#### 🐧 Linux

```bash
export DB_URL="jdbc:mysql://localhost:3306/library_db?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Kolkata"
export DB_USERNAME="root"
export DB_PASSWORD="your_mysql_password"
```

### 3️⃣ Build and Test

```bash
# Windows
mvnw.cmd clean test

# macOS/Linux
./mvnw clean test
```

### 4️⃣ Run Application

```bash
# Windows
mvnw.cmd spring-boot:run

# macOS/Linux
./mvnw spring-boot:run
```

If 8080 is busy:

```bash
mvnw.cmd spring-boot:run "-Dspring-boot.run.arguments=--server.port=8083"
```

🎉 If you see the login screen — it is working!

---

## ⌨️ 11. Commands Reference Table

| ⌨️ Command                                                                  | 📋 What it does                | ⏰ When to use         |
| --------------------------------------------------------------------------- | ------------------------------ | ---------------------- |
| `mvnw.cmd clean compile`                                                    | Compile project on Windows     | First local validation |
| `./mvnw clean compile`                                                      | Compile project on macOS/Linux | First local validation |
| `mvnw.cmd clean test`                                                       | Run tests on Windows           | Before push            |
| `./mvnw clean test`                                                         | Run tests on macOS/Linux       | Before push            |
| `mvnw.cmd spring-boot:run`                                                  | Start app on default port      | Normal local run       |
| `mvnw.cmd spring-boot:run "-Dspring-boot.run.arguments=--server.port=8083"` | Start app on custom port       | Port 8080 conflict     |
| `git checkout main`                                                         | Switch to main branch          | Before sync/push       |
| `git pull origin main`                                                      | Get latest remote changes      | Before new work        |

---

## 🗺️ 12. Project Tour The Full Story

🎬 **Scene 1: Student opens the app**

- Controller: `AuthController`
- Model: `User`
- View: `login.html`

🎬 **Scene 2: Student searches/browses books**

- Controller: `BookController`
- Model: `Book`
- View: `books.html`

🎬 **Scene 3: Student borrows a book**

- Controller: `TransactionController`
- Model: `Transaction`
- View/API: transaction JSON + transaction module page

🎬 **Scene 4: Admin checks overdue books and fines**

- Controller: `FineController`
- Model: `Fine`, `Transaction`
- View: `view-fines.html`

🎬 **Scene 5: Student returns a book (workflow target)**

- Controller: `TransactionController` (planned dedicated return endpoint)
- Model: `Transaction`
- View: `transaction-list.html`

---

## 🗄️ 13. Database Schema

### Tables and Constraints

| Table         | Columns (major)                                                      | Constraints                                                         |
| ------------- | -------------------------------------------------------------------- | ------------------------------------------------------------------- |
| `users`       | `id`, `username`, `password`, `role`, `is_locked`, `failed_attempts` | 🔑 PK `id`, unique `username`                                       |
| `book`        | `id`, `isbn`, `title`, `author`, `price`, `quantity`                 | 🔑 PK `id`                                                          |
| `transaction` | `id`, `user_id`, `book_id`, `issue_date`, `return_date`, `status`    | 🔑 PK `id`, 🔗 FK `user_id -> users.id`, 🔗 FK `book_id -> book.id` |
| `fine`        | `id`, `transaction_id`, `amount`, `payment_status`                   | 🔑 PK `id`, logical link by `transaction_id`                        |

### ASCII ERD

```text
users (1) ---------------- (N) transaction (N) ---------------- (1) book
  id PK                         id PK                                id PK
                               user_id FK -> users.id
                               book_id FK -> book.id

transaction (1) ---------- (0..1 logical) fine
  id PK                         id PK
                               transaction_id
```

---

## 🧪 14. Testing

### How to run tests

```bash
# Windows
mvnw.cmd clean test

# macOS/Linux
./mvnw clean test
```

### Test ownership

- Shared Team Integration:
  - `SmartLibraryApplicationTests.java` (context load smoke test)

### Current testing status

- ✅ Spring context boot test exists
- ❌ Module-level unit tests are not implemented yet

---

## ⚠️ 15. Known Issues and Roadmap

### 🐛 Current bugs/issues

- `transaction-list.html` references fields/routes that are not fully aligned with current transaction REST controller.
- Existing local DB data can trigger FK migration warnings during startup.

### 🚧 Limitations

- No advanced search/filter implementation yet.
- No password hashing yet.
- Limited automated test coverage.

### 🔮 Roadmap

- Add dedicated issue/return transaction endpoints.
- Align transaction UI template with active REST model.
- Add BCrypt password hashing.
- Add richer unit and integration tests.

---

## 📖 16. Glossary

- **MVC**: Model-View-Controller separation of data, UI, and routing.
- **GRASP**: Principles for assigning responsibilities in OO design.
- **Design Pattern**: Reusable solution template for common design problems.
- **OOP**: Object-Oriented Programming with classes and objects.
- **CRUD**: Create, Read, Update, Delete data operations.
- **API**: Interface for one software component to communicate with another.
- **Controller**: Handles incoming requests and coordinates responses.
- **Model**: Represents business data and state.
- **View**: User-facing template/UI.
- **Repository**: Data access layer abstraction.

---

## 🤝 17. How to Contribute

Fork 🍴 -> Branch 🌿 -> Commit 💾 -> PR 📬

1. Fork the repository.
2. Create a branch: `git checkout -b feature/your-change`.
3. Commit with clear message.
4. Push and open a pull request.

---

## 📜 18. License and Credits

- License: No explicit `LICENSE` file currently present.

### Credits

- 👤 Prajwal Kittali — Security and Auth module
- 👤 Pranav G — Book Catalog module
- 👤 Pranav S — Fine and Strategy module
- 👤 Prashant Radder — Transaction module
- 👥 Shared Team — Integration and testing baseline
