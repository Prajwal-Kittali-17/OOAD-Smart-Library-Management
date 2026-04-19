package com.library.Smart_Library.service.strategy;

import org.springframework.stereotype.Component;

/**
 * Owner: Pranav S
 * SRN: NOT-PROVIDED
 * Purpose: Academic fine-rate policy for faculty users.
 * GRASP: High Cohesion by encapsulating one policy per strategy class.
 * Pattern: Strategy.
 */
@Component
public class AcademicFineRateStrategy implements FineCalculationStrategy {

  private static final double ACADEMIC_RATE = 5.0;

  /**
   * Determines whether this strategy applies for the role.
   * GRASP: Polymorphism with role-driven strategy matching.
   */
  @Override
  public boolean supports(String role) {
    return role != null && role.equalsIgnoreCase("FACULTY");
  }

  /**
   * Calculates fine with academic daily rate.
   * GRASP: Information Expert for this rate algorithm.
   */
  @Override
  public double calculate(long overdueDays) {
    return overdueDays * ACADEMIC_RATE;
  }
}
