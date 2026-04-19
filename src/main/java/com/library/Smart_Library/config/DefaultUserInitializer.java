package com.library.Smart_Library.config;

import com.library.Smart_Library.model.User;
import com.library.Smart_Library.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Owner: Prajwal Kittali
 * SRN: PES2UG23CS419
 * Purpose: Seeds default security user.
 * GRASP: Creator by creating initial User when persistence store is empty.
 * Pattern: Singleton configuration bean via Spring.
 */
@Configuration
public class DefaultUserInitializer {

  private static final String DEFAULT_USERNAME = "admin";
  private static final String DEFAULT_PASSWORD = "admin123";

  /**
   * Initializes default admin account on first run.
   * GRASP: Creator through controlled object creation policy at startup.
   */
  @Bean
  CommandLineRunner seedDefaultUser(UserRepository userRepository) {
    return args -> {
      if (userRepository.count() == 0) {
        User defaultUser = new User();
        defaultUser.setUsername(DEFAULT_USERNAME);
        defaultUser.setPassword(DEFAULT_PASSWORD);
        defaultUser.setRole("ADMIN");
        defaultUser.setLocked(false);
        defaultUser.setFailedAttempts(0);
        userRepository.save(defaultUser);
      }
    };
  }
}
