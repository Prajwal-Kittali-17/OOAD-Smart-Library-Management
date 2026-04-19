package com.library.Smart_Library.service.strategy;

/**
 * Owner: Pranav S
 * SRN: NOT-PROVIDED
 * Purpose: Strategy contract for fine calculation by user category.
 * GRASP: Low Coupling/High Cohesion by isolating algorithm variations.
 * Pattern: Strategy.
 */
public interface FineCalculationStrategy {

  /**
   * Checks whether strategy supports a role.
   * GRASP: Polymorphism through role-based algorithm routing.
   */
  boolean supports(String role);

  /**
   * Calculates fine amount for overdue days.
   * GRASP: Information Expert delegated to concrete algorithm class.
   */
  double calculate(long overdueDays);
}
