package com.library.Smart_Library.controller;

import com.library.Smart_Library.model.Book;
import com.library.Smart_Library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Owner: Pranav G
 * SRN: NOT-PROVIDED
 * Purpose: Catalog module UI controller.
 * GRASP: Controller - sole entry point for all catalog UI events.
 * Pattern: Singleton collaborator injection through Spring IoC.
 */
@Controller
public class BookController {

    @Autowired
    private BookService bookService;

    /**
     * Handles the catalog listing event from UI.
     * GRASP: Controller by accepting list-book request and delegating business
     * work.
     */
    @GetMapping("/books")
    public String listBooks(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("books", bookService.getAllBooks());
        return "books";
    }

    /**
     * Persists submitted catalog data through service boundary.
     * GRASP: Controller by delegating save use case to BookService.
     */
    @PostMapping("/add-book")
    public String saveBook(@ModelAttribute("book") Book book) {
        if (book.getQuantity() <= 0) {
            book.setQuantity(1);
        }
        if (book.getPrice() < 0) {
            book.setPrice(0);
        }
        bookService.saveBook(book);
        return "redirect:/books";
    }
}