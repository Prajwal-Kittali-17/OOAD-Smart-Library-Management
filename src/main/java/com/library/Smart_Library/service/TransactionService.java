package com.library.Smart_Library.service;

import com.library.Smart_Library.model.Transaction;

import com.library.Smart_Library.repository.TransactionRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class TransactionService {

    @Autowired

    private TransactionRepository transactionRepository;

    public List<Transaction> getAllTransactions() {

        return transactionRepository.findAll();

    }

    public Transaction saveTransaction(Transaction transaction) {

        return transactionRepository.save(transaction);

    }

    // Add other methods as needed

}