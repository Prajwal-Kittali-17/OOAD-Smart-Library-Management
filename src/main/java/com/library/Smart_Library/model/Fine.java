package com.library.Smart_Library.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Owner: Pranav S
 * SRN: NOT-PROVIDED
 * Purpose: Finance entity storing calculated dues for transactions.
 * GRASP: Information Expert by owning fine amount/payment status state.
 * Pattern: Persisted via Spring Data proxy repository.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Fine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long transactionId;
    private Double amount;
    private String paymentStatus; // e.g., "PENDING", "PAID"
}