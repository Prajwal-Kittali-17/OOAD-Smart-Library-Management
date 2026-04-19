package com.library.Smart_Library.controller;

import com.library.Smart_Library.model.Transaction;

import com.library.Smart_Library.service.TransactionService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

@RequestMapping("/transactions")

public class TransactionController {

    @Autowired

    private TransactionService transactionService;

    @GetMapping

    public List<Transaction> getAllTransactions() {

        return transactionService.getAllTransactions();

    }

    @PostMapping

    public Transaction createTransaction(@RequestBody Transaction transaction) {

        return transactionService.saveTransaction(transaction);

    }

    // Add other endpoints as needed

}