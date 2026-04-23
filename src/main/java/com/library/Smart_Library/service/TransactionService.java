package com.library.Smart_Library.service;

import com.library.Smart_Library.model.Transaction;
import com.library.Smart_Library.model.Book;
import com.library.Smart_Library.model.User;
import com.library.Smart_Library.repository.TransactionRepository;
import com.library.Smart_Library.repository.BookRepository;
import com.library.Smart_Library.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.Optional;

/**
 * Owner: Prashant R
 * SRN: NOT-PROVIDED
 * Purpose: Manage book circulation (issue/return) with inventory management.
 * GRASP: Controller for transactions; coordinates with Book and User entities.
 * Pattern: Service layer logic for borrow/return workflows; integrates with
 * FineService.
 */
@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FineService fineService;

    private static final int BORROW_DAYS = 14;

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Transaction saveTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    /**
     * Borrow a book: Create transaction, decrement book quantity, calculate due
     * date.
     * GRASP: Creator pattern - TransactionService creates transaction entries.
     * Information Expert - manages transaction lifecycle.
     */
    public Transaction borrowBook(Long userId, Long bookId) {
        Optional<User> userOpt = userRepository.findById(userId);
        Optional<Book> bookOpt = bookRepository.findById(bookId);

        if (!userOpt.isPresent() || !bookOpt.isPresent()) {
            throw new IllegalArgumentException("User or Book not found");
        }

        Book book = bookOpt.get();
        if (book.getQuantity() <= 0) {
            throw new IllegalStateException("Book not available (quantity = 0)");
        }

        User user = userOpt.get();
        Transaction transaction = new Transaction();
        transaction.setUser(user);
        transaction.setBook(book);
        transaction.setIssueDate(LocalDateTime.now());
        transaction.setDueDate(LocalDate.now().plusDays(BORROW_DAYS));
        transaction.setStatus("ISSUED");

        // Save transaction
        Transaction savedTransaction = transactionRepository.save(transaction);

        // Decrement book quantity
        book.setQuantity(book.getQuantity() - 1);
        bookRepository.save(book);

        return savedTransaction;
    }

    /**
     * Return a book: Mark transaction as RETURNED, increment book quantity.
     * GRASP: Knows how to complete transactions and update inventory.
     */
    public Transaction returnBook(Long transactionId) {
        Optional<Transaction> transactionOpt = transactionRepository.findById(transactionId);

        if (!transactionOpt.isPresent()) {
            throw new IllegalArgumentException("Transaction not found");
        }

        Transaction transaction = transactionOpt.get();

        if ("RETURNED".equalsIgnoreCase(transaction.getStatus())) {
            throw new IllegalStateException("Book already returned");
        }

        transaction.setReturnDate(LocalDateTime.now());
        transaction.setStatus("RETURNED");

        // Save updated transaction
        Transaction savedTransaction = transactionRepository.save(transaction);

        // Increment book quantity
        Book book = transaction.getBook();
        book.setQuantity(book.getQuantity() + 1);
        bookRepository.save(book);

        // Auto-check and generate fine if overdue
        if (transaction.getDueDate() != null && LocalDate.now().isAfter(transaction.getDueDate())) {
            fineService.checkOverdueAndSaveFine(transaction.getId());
        }

        return savedTransaction;
    }

}