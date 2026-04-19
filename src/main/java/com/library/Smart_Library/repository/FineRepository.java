package com.library.Smart_Library.repository;

import com.library.Smart_Library.model.Fine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Owner: Pranav S
 * SRN: NOT-PROVIDED
 * Purpose: Finance persistence abstraction for fine records.
 * GRASP: Low Coupling by separating storage concern from finance logic.
 * Pattern: Proxy Pattern via generated Spring Data implementation.
 */
public interface FineRepository extends JpaRepository<Fine, Long> {

  /**
   * Finds fine by transaction linkage.
   * GRASP: Information Expert support with focused repository contract.
   */
  Optional<Fine> findByTransactionId(Long transactionId);
}