package com.library.Smart_Library.service.strategy;

import org.springframework.stereotype.Component;

/**
 * Owner: Pranav S
 * SRN: NOT-PROVIDED
 * Purpose: Standard fine-rate policy.
 * GRASP: High Cohesion by keeping one fine algorithm per class.
 * Pattern: Strategy.
 */
@Component
public class StandardFineRateStrategy implements FineCalculationStrategy {

  private static final double STANDARD_RATE = 10.0;

  /**
   * Determines whether this strategy applies for the role.
   * GRASP: Polymorphism through role-based algorithm selection.
   */
  @Override
  public boolean supports(String role) {
    return role != null && (role.equalsIgnoreCase("STUDENT") || role.equalsIgnoreCase("ADMIN"));
  }

  /**
   * Calculates fine with standard daily rate.
   * GRASP: Information Expert for this pricing algorithm.
   */
  @Override
  public double calculate(long overdueDays) {
    return overdueDays * STANDARD_RATE;
  }
}
