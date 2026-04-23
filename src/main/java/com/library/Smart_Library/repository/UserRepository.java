package com.library.Smart_Library.repository;

import com.library.Smart_Library.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Owner: Prajwal Kittali
 * SRN: PES2UG23CS419
 * Purpose: Security persistence abstraction for users.
 * GRASP: Low Coupling by depending on repository interface.
 * Pattern: Proxy Pattern through Spring Data JPA generated implementation.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds user by username for authentication.
     * GRASP: Information Expert support through targeted query abstraction.
     */
    User findByUsername(String username);
}