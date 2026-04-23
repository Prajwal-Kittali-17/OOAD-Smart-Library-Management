package com.library.Smart_Library.controller;

import com.library.Smart_Library.model.Transaction;
import com.library.Smart_Library.service.BookService;
import com.library.Smart_Library.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Owner: Prashant R
 * SRN: NOT-PROVIDED
 * Purpose: Handle book circulation endpoints (borrow/return).
 * GRASP: Facade controller routing requests to TransactionService.
 */
@Controller
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private BookService bookService;

    @GetMapping
    @ResponseBody
    public List<Transaction> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    @GetMapping("/view")
    public String viewTransactions(Model model) {
        model.addAttribute("transactions", transactionService.getAllTransactions());
        model.addAttribute("books", bookService.getAllBooks());
        return "transaction-list";
    }

    @PostMapping
    @ResponseBody
    public Transaction createTransaction(@RequestBody Transaction transaction) {
        return transactionService.saveTransaction(transaction);
    }

    /**
     * POST /transactions/borrow - Issue a book to a user
     * Parameters: userId, bookId
     * Returns: Transaction object with ISSUED status and calculated due date
     * Error cases: User/Book not found, Book quantity = 0
     */
    @PostMapping("/borrow")
    public String borrowBook(
            @RequestParam Long userId,
            @RequestParam Long bookId,
            RedirectAttributes redirectAttributes) {
        try {
            Transaction transaction = transactionService.borrowBook(userId, bookId);
            redirectAttributes.addFlashAttribute("transactionMessage",
                    "Book borrowed successfully! Due date: " + transaction.getDueDate());
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("transactionError", "Error: " + e.getMessage());
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("transactionError", "Error: " + e.getMessage());
        }
        return "redirect:/transactions/view";
    }

    /**
     * POST /transactions/return - Return a borrowed book
     * Parameters: transactionId
     * Returns: Updated transaction with RETURNED status and returnDate
     * Error cases: Transaction not found, Already returned
     * Side effect: Auto-generates fine if overdue
     */
    @PostMapping("/return")
    public String returnBook(
            @RequestParam Long transactionId,
            RedirectAttributes redirectAttributes) {
        try {
            Transaction transaction = transactionService.returnBook(transactionId);
            redirectAttributes.addFlashAttribute("transactionMessage",
                    "Book returned successfully!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("transactionError", "Error: " + e.getMessage());
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("transactionError", "Error: " + e.getMessage());
        }
        return "redirect:/transactions/view";
    }
}