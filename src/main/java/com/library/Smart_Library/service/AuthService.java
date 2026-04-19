package com.library.Smart_Library.service;

import com.library.Smart_Library.model.User;
import com.library.Smart_Library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Owner: Prajwal Kittali
 * SRN: PES2UG23CS419
 * Purpose: Security module business rules for login and lockout.
 * GRASP: Controller support + Information Expert collaboration with User.
 * Pattern: Singleton service bean and Template Method through BaseService.
 */
@Service
public class AuthService extends BaseService {

  private static final int MAX_FAILED_ATTEMPTS = 3;

  @Autowired
  private UserRepository userRepository;

  /**
   * Executes authentication workflow and updates lock state using User entity
   * behavior.
   * GRASP: Information Expert by delegating lock-state decisions to User.
   */
  public LoginResult authenticate(String username, String password) {
    return performActionWithLogging("authenticate-user", () -> {
      if (username == null || username.isBlank() || password == null) {
        throw new IllegalArgumentException("Username and password are required.");
      }
    }, () -> {
      User user = userRepository.findByUsername(username);
      if (user == null) {
        return LoginResult.invalid("Invalid Credentials", null);
      }

      if (user.isAccountLocked()) {
        return LoginResult.locked("Your account is locked after 3 failed attempts.");
      }

      if (user.getPassword().equals(password)) {
        user.resetLockStateOnSuccess();
        userRepository.save(user);
        return LoginResult.success(user.getUsername());
      }

      user.recordFailedAttempt(MAX_FAILED_ATTEMPTS);
      userRepository.save(user);
      if (user.isAccountLocked()) {
        return LoginResult.locked("Your account is locked after 3 failed attempts.");
      }

      int attemptsLeft = MAX_FAILED_ATTEMPTS - user.getFailedAttempts();
      return LoginResult.invalid("Invalid Credentials. Attempts left: " + attemptsLeft, attemptsLeft);
    });
  }

  /**
   * Immutable authentication response object used by controller.
   * GRASP: Low Coupling by reducing controller dependency on persistence model.
   */
  public record LoginResult(LoginStatus status, String message, Optional<String> username, Integer attemptsRemaining) {

    /**
     * Creates success login response.
     * GRASP: Low Coupling through typed state transfer to controller.
     */
    public static LoginResult success(String username) {
      return new LoginResult(LoginStatus.SUCCESS, "Login successful", Optional.ofNullable(username), null);
    }

    /**
     * Creates invalid-credentials response.
     * GRASP: Low Coupling through explicit error-state abstraction.
     */
    public static LoginResult invalid(String message, Integer attemptsRemaining) {
      return new LoginResult(LoginStatus.INVALID, message, Optional.empty(), attemptsRemaining);
    }

    /**
     * Creates locked-account response.
     * GRASP: Low Coupling by exposing lock state without leaking entity details.
     */
    public static LoginResult locked(String message) {
      return new LoginResult(LoginStatus.LOCKED, message, Optional.empty(), 0);
    }
  }

  /**
   * Login state transitions returned to controller.
   * GRASP: Low Coupling through explicit state communication.
   */
  public enum LoginStatus {
    SUCCESS,
    INVALID,
    LOCKED
  }
}
