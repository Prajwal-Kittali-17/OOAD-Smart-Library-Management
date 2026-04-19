package com.library.Smart_Library.repository;

import com.library.Smart_Library.model.Transaction;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}