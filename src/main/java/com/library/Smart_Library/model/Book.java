package com.library.Smart_Library.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Owner: Pranav G
 * SRN: NOT-PROVIDED
 * Purpose: Catalog entity for books and inventory quantity.
 * GRASP: Information Expert by owning inventory state attributes.
 * Pattern: Persisted through Spring Data JPA proxy repositories.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String isbn;
    private String title;
    private String author;
    private double price;
    private int quantity;
}