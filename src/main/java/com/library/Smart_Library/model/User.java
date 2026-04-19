package com.library.Smart_Library.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Owner: Prajwal Kittali
 * SRN: PES2UG23CS419
 * Purpose: Security entity that owns authentication state and lock metadata.
 * GRASP: Information Expert by encapsulating account lock decisions.
 * Pattern: Persisted by Spring Data proxy repository infrastructure.
 */
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String role;
    private boolean isLocked = false;
    private int failedAttempts = 0;

    /**
     * Domain-level lock query.
     * GRASP: Information Expert - lock state is answered by User itself.
     */
    public boolean isAccountLocked() {
        return this.isLocked;
    }

    /**
     * Records failed attempt and locks account at threshold.
     * GRASP: Information Expert - User applies lockout transition rules.
     */
    public void recordFailedAttempt(int maxFailedAttempts) {
        this.failedAttempts++;
        if (this.failedAttempts >= maxFailedAttempts) {
            this.isLocked = true;
        }
    }

    /**
     * Clears lock metadata after successful authentication.
     * GRASP: Information Expert - User owns reset behavior of its state.
     */
    public void resetLockStateOnSuccess() {
        this.failedAttempts = 0;
        this.isLocked = false;
    }
}