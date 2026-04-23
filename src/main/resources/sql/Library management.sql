-- ============================================================================
-- SMART LIBRARY MANAGEMENT - COMPLETE SQL SETUP AND FEATURE TEST SCRIPT
-- File: Library management . SQL
-- DB: MySQL 8.0.43
-- Purpose:
--   1) Create all core tables used by the project (Security, Catalog, Transaction, Finance)
--   2) Seed initial data for UI usage
--   3) Provide ready-to-run verification queries for each module
--   4) Provide optional test queries for lockout, issue/return, and fine reporting
-- ============================================================================

-- ----------------------------------------------------------------------------
-- SECTION 1: DATABASE BOOTSTRAP
-- ----------------------------------------------------------------------------
CREATE DATABASE IF NOT EXISTS library_db;
USE library_db;

-- ----------------------------------------------------------------------------
-- SECTION 2: SECURITY MODULE (PRAJWAL) - USERS TABLE
-- Features covered:
--   - Login credentials
--   - Account lockout state (is_locked)
--   - Failed attempt counter (failed_attempts)
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(255),
    is_locked BIT(1) DEFAULT b'0',
    failed_attempts INT DEFAULT 0
);

-- Seed primary admin account used by login page
INSERT INTO users (username, password, role, is_locked, failed_attempts)
VALUES ('admin', 'admin123', 'ADMIN', b'0', 0)
ON DUPLICATE KEY UPDATE
    password = VALUES(password),
    role = VALUES(role),
    is_locked = VALUES(is_locked),
    failed_attempts = VALUES(failed_attempts);

-- Optional second user to test strategy-based finance rates
INSERT INTO users (username, password, role, is_locked, failed_attempts)
SELECT 'faculty1', 'faculty123', 'FACULTY', b'0', 0
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'faculty1');

-- ----------------------------------------------------------------------------
-- SECTION 3: CATALOG MODULE (PRANAV G) - BOOK TABLE
-- Features covered:
--   - Add books
--   - View books
--   - Quantity/inventory tracking
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS book (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    isbn VARCHAR(255),
    title VARCHAR(255),
    author VARCHAR(255),
    quantity INT
);

-- Seed catalog with sample books used in UI testing
INSERT INTO book (isbn, title, author, quantity)
SELECT '9780134685991', 'Effective Java', 'Joshua Bloch', 6
WHERE NOT EXISTS (SELECT 1 FROM book WHERE isbn = '9780134685991');

INSERT INTO book (isbn, title, author, quantity)
SELECT '9781617294945', 'Spring in Action', 'Craig Walls', 5
WHERE NOT EXISTS (SELECT 1 FROM book WHERE isbn = '9781617294945');

INSERT INTO book (isbn, title, author, quantity)
SELECT '9781491950357', 'Designing Data-Intensive Applications', 'Martin Kleppmann', 4
WHERE NOT EXISTS (SELECT 1 FROM book WHERE isbn = '9781491950357');

INSERT INTO book (isbn, title, author, quantity)
SELECT '9780132350884', 'Clean Code', 'Robert C. Martin', 7
WHERE NOT EXISTS (SELECT 1 FROM book WHERE isbn = '9780132350884');

INSERT INTO book (isbn, title, author, quantity)
SELECT '9780596009205', 'Head First Java', 'Kathy Sierra', 8
WHERE NOT EXISTS (SELECT 1 FROM book WHERE isbn = '9780596009205');

-- ----------------------------------------------------------------------------
-- SECTION 4: TRANSACTION MODULE (PRASHANT R) - TRANSACTION TABLE
-- Features covered:
--   - Issue date, due date, and lifecycle status
--   - Tracks each issue/return action
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS `transaction` (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT,
    book_id BIGINT,
    issue_date DATE,
    due_date DATE,
    status VARCHAR(255),
    CONSTRAINT fk_transaction_user FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_transaction_book FOREIGN KEY (book_id) REFERENCES book(id)
);

SET @idx_exists = (
    SELECT COUNT(1)
    FROM information_schema.statistics
    WHERE table_schema = DATABASE()
      AND table_name = 'transaction'
      AND index_name = 'idx_transaction_user_id'
);
SET @sql = IF(@idx_exists = 0,
    'CREATE INDEX idx_transaction_user_id ON `transaction` (user_id)',
    'SELECT "idx_transaction_user_id already exists"'
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

SET @idx_exists = (
    SELECT COUNT(1)
    FROM information_schema.statistics
    WHERE table_schema = DATABASE()
      AND table_name = 'transaction'
      AND index_name = 'idx_transaction_book_id'
);
SET @sql = IF(@idx_exists = 0,
    'CREATE INDEX idx_transaction_book_id ON `transaction` (book_id)',
    'SELECT "idx_transaction_book_id already exists"'
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

SET @idx_exists = (
    SELECT COUNT(1)
    FROM information_schema.statistics
    WHERE table_schema = DATABASE()
      AND table_name = 'transaction'
      AND index_name = 'idx_transaction_status'
);
SET @sql = IF(@idx_exists = 0,
    'CREATE INDEX idx_transaction_status ON `transaction` (status)',
    'SELECT "idx_transaction_status already exists"'
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- ----------------------------------------------------------------------------
-- SECTION 5: FINANCE MODULE (PRANAV S) - FINE TABLE
-- Features covered:
--   - Fine records separated from transaction history
--   - Pending/paid payment tracking
--   - One fine row per transaction (unique transaction_id)
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS fine (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    transaction_id BIGINT NOT NULL,
    amount DOUBLE,
    payment_status VARCHAR(255),
    CONSTRAINT uq_fine_transaction UNIQUE (transaction_id),
    CONSTRAINT fk_fine_transaction FOREIGN KEY (transaction_id) REFERENCES `transaction`(id)
);

SET @idx_exists = (
    SELECT COUNT(1)
    FROM information_schema.statistics
    WHERE table_schema = DATABASE()
      AND table_name = 'fine'
      AND index_name = 'idx_fine_payment_status'
);
SET @sql = IF(@idx_exists = 0,
    'CREATE INDEX idx_fine_payment_status ON fine (payment_status)',
    'SELECT "idx_fine_payment_status already exists"'
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- ----------------------------------------------------------------------------
-- SECTION 6: OPTIONAL TEST DATA FOR TRANSACTION + FINANCE
-- NOTE: These are safe inserts and will skip if same logical row already exists.
-- ----------------------------------------------------------------------------
INSERT INTO `transaction` (user_id, book_id, issue_date, due_date, status)
SELECT u.id, b.id, CURDATE() - INTERVAL 20 DAY, CURDATE() - INTERVAL 6 DAY, 'ISSUED'
FROM users u
JOIN book b ON b.isbn = '9780134685991'
WHERE u.username = 'admin'
  AND NOT EXISTS (
      SELECT 1 FROM `transaction` t
      WHERE t.user_id = u.id
        AND t.book_id = b.id
        AND t.status = 'ISSUED'
  );

INSERT INTO `transaction` (user_id, book_id, issue_date, due_date, status)
SELECT u.id, b.id, CURDATE() - INTERVAL 10 DAY, CURDATE() + INTERVAL 4 DAY, 'ISSUED'
FROM users u
JOIN book b ON b.isbn = '9781617294945'
WHERE u.username = 'faculty1'
  AND NOT EXISTS (
      SELECT 1 FROM `transaction` t
      WHERE t.user_id = u.id
        AND t.book_id = b.id
        AND t.status = 'ISSUED'
  );

-- ----------------------------------------------------------------------------
-- SECTION 7: OPTIONAL MANUAL FINE UPSERT FOR OVERDUE ISSUED TRANSACTIONS
-- This section simulates the backend fine creation logic for quick DB-only checks.
-- Actual app also computes/saves fines from service layer.
-- ----------------------------------------------------------------------------
INSERT INTO fine (transaction_id, amount, payment_status)
SELECT
    t.id,
    GREATEST(DATEDIFF(CURDATE(), t.due_date), 0) * 10,
    'PENDING'
FROM `transaction` t
WHERE t.status = 'ISSUED'
  AND t.due_date < CURDATE()
ON DUPLICATE KEY UPDATE
    amount = VALUES(amount),
    payment_status = VALUES(payment_status);

-- ----------------------------------------------------------------------------
-- SECTION 8: VERIFICATION QUERIES (RUN AFTER APP START)
-- ----------------------------------------------------------------------------

-- 8.1 Security module verification
SELECT id, username, role, is_locked, failed_attempts
FROM users
ORDER BY id;

-- 8.2 Catalog module verification
SELECT id, isbn, title, author, quantity
FROM book
ORDER BY id;

-- 8.3 Transaction module verification
SELECT id, user_id, book_id, issue_date, due_date, status
FROM `transaction`
ORDER BY id DESC;

-- 8.4 Finance module verification
SELECT id, transaction_id, amount, payment_status
FROM fine
ORDER BY id DESC;

-- ----------------------------------------------------------------------------
-- SECTION 9: REPORTING QUERIES FOR FINANCE DASHBOARD EXPLANATION
-- ----------------------------------------------------------------------------

-- Pending vs collected summary
SELECT payment_status, COUNT(*) AS total_records, COALESCE(SUM(amount), 0) AS total_amount
FROM fine
GROUP BY payment_status;

-- Full joined finance report (with username and book title)
SELECT
    f.id AS fine_id,
    f.transaction_id,
    u.username,
    b.title AS book_title,
    t.issue_date,
    t.due_date,
    f.amount,
    f.payment_status
FROM fine f
JOIN `transaction` t ON t.id = f.transaction_id
JOIN users u ON u.id = t.user_id
JOIN book b ON b.id = t.book_id
ORDER BY f.id DESC;

-- ----------------------------------------------------------------------------
-- SECTION 10: AUTH LOCKOUT TEST QUERIES (MANUAL QA SUPPORT)
-- ----------------------------------------------------------------------------

-- Check current lock state for admin
SELECT username, failed_attempts, is_locked
FROM users
WHERE username = 'admin';

-- Manual unlock/reset for repeated UI testing
UPDATE users
SET failed_attempts = 0,
    is_locked = b'0'
WHERE id > 0
  AND is_locked = b'1';

-- ----------------------------------------------------------------------------
-- SECTION 11: CLEAN IMMUTABILITY NOTE
-- ----------------------------------------------------------------------------
-- Transaction table should track issue/return history and status transitions.
-- Fine table stores penalty lifecycle separately (pending/paid), keeping
-- transaction rows clean and focused on circulation events.
-- ============================================================================
