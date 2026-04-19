package com.library.Smart_Library.service;

import com.library.Smart_Library.model.Fine;
import com.library.Smart_Library.model.Transaction;
import com.library.Smart_Library.repository.FineRepository;
import com.library.Smart_Library.repository.TransactionRepository;
import com.library.Smart_Library.repository.UserRepository;
import com.library.Smart_Library.service.strategy.FineCalculationStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.Optional;

/**
 * Owner: Pranav S
 * SRN: NOT-PROVIDED
 * Purpose: Finance module business logic for overdue fine calculations.
 * GRASP: Low Coupling by isolating all fine math away from controller.
 * Pattern: Strategy (StandardFineRate and AcademicFineRate) and Template
 * Method via BaseService.
 */
@Service
public class FineService extends BaseService {

  @Autowired
  private TransactionRepository transactionRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private List<FineCalculationStrategy> fineStrategies;

  @Autowired
  private TransactionService transactionService;

  @Autowired
  private FineRepository fineRepository;

  private static final double DEFAULT_RATE = 10.0;

  /**
   * Calculates and persists fine for one transaction when overdue.
   * GRASP: Low Coupling through service-level financial policy isolation.
   */
  public Optional<Fine> checkOverdueAndSaveFine(Long transactionId) {
    return performActionWithLogging("check-overdue-fine", () -> {
      if (transactionId == null) {
        throw new IllegalArgumentException("Transaction ID is required.");
      }
    }, () -> {
      Optional<Transaction> transactionOpt = transactionRepository.findById(transactionId);
      if (transactionOpt.isEmpty()) {
        return Optional.empty();
      }

      Transaction transaction = transactionOpt.get();
      if (!"ISSUED".equalsIgnoreCase(transaction.getStatus())) {
        return Optional.empty();
      }
      if (transaction.getDueDate() == null || !LocalDate.now().isAfter(transaction.getDueDate())) {
        return Optional.empty();
      }

      long overdueDays = ChronoUnit.DAYS.between(transaction.getDueDate(), LocalDate.now());
      String role = userRepository.findById(transaction.getUserId()).map(u -> u.getRole()).orElse("STUDENT");
      double fineAmount = resolveStrategy(role).calculate(overdueDays);
      return Optional.of(transactionService.createOrUpdateFineForTransaction(transaction, fineAmount));
    });
  }

  /**
   * Calculates and saves fines for all overdue issued transactions.
   * GRASP: Low Coupling by centralizing finance rules in a single service.
   */
  public List<Fine> calculateAndSaveOverdueIssuedFines() {
    return performActionWithLogging("calculate-overdue-fines", () -> {
    }, () -> {
      LocalDate currentDate = LocalDate.now();
      List<Fine> calculatedFines = new ArrayList<>();

      for (Transaction transaction : transactionRepository.findAll()) {
        if (!"ISSUED".equalsIgnoreCase(transaction.getStatus())) {
          continue;
        }
        if (transaction.getDueDate() == null || !currentDate.isAfter(transaction.getDueDate())) {
          continue;
        }

        long overdueDays = ChronoUnit.DAYS.between(transaction.getDueDate(), currentDate);
        String role = userRepository.findById(transaction.getUserId()).map(u -> u.getRole()).orElse("STUDENT");
        double amount = resolveStrategy(role).calculate(overdueDays);
        calculatedFines.add(transactionService.createOrUpdateFineForTransaction(transaction, amount));
      }

      return calculatedFines;
    });
  }

  /**
   * Returns UI-ready fine data after refreshing overdue fines from current
   * transactions.
   */
  public List<Fine> getTrackedFines() {
    return performActionWithLogging("track-fines-ui", () -> {
    }, () -> {
      calculateAndSaveOverdueIssuedFines();
      List<Fine> fines = fineRepository.findAll();
      fines.sort(Comparator.comparing(Fine::getId).reversed());
      return fines;
    });
  }

  /**
   * Marks a fine as paid for UI-driven finance tracking workflow.
   */
  public boolean markFineAsPaid(Long fineId) {
    return performActionWithLogging("mark-fine-paid", () -> {
      if (fineId == null) {
        throw new IllegalArgumentException("Fine ID is required.");
      }
    }, () -> {
      Optional<Fine> fineOpt = fineRepository.findById(fineId);
      if (fineOpt.isEmpty()) {
        return false;
      }

      Fine fine = fineOpt.get();
      if ("PAID".equalsIgnoreCase(fine.getPaymentStatus())) {
        return true;
      }

      fine.setPaymentStatus("PAID");
      fineRepository.save(fine);
      return true;
    });
  }

  /**
   * Resolves strategy implementation for user role.
   * GRASP: Polymorphism under Strategy pattern for replaceable algorithms.
   */
  private FineCalculationStrategy resolveStrategy(String role) {
    return fineStrategies.stream()
        .filter(strategy -> strategy.supports(role))
        .findFirst()
        .orElseGet(() -> new FineCalculationStrategy() {
          @Override
          public boolean supports(String ignored) {
            return true;
          }

          @Override
          public double calculate(long overdueDays) {
            return overdueDays * DEFAULT_RATE;
          }
        });
  }
}
