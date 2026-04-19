package com.library.Smart_Library.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Supplier;

/**
 * Owner: Shared OOAD Infrastructure
 * SRN: TEAM-SHARED
 * Purpose: Template Method base for all module services.
 * GRASP: Low Coupling by centralizing cross-cutting workflow hooks.
 * Pattern: Template Method via performActionWithLogging.
 */
public abstract class BaseService {

  private final Logger logger = LoggerFactory.getLogger(getClass());

  /**
   * Template Method used by all services to enforce a uniform
   * validate-then-execute-then-log workflow.
   * GRASP: Low Coupling by removing duplicated boilerplate from concrete
   * services.
   */
  protected final <T> T performActionWithLogging(String operationName, Runnable validator,
      Supplier<T> action) {
    logger.info("Starting operation: {}", operationName);
    validator.run();
    T result = action.get();
    logger.info("Completed operation: {}", operationName);
    return result;
  }
}
