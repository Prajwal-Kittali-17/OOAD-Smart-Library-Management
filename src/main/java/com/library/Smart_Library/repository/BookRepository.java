package com.library.Smart_Library.repository;

import com.library.Smart_Library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Owner: Pranav G
 * SRN: NOT-PROVIDED
 * Purpose: Catalog persistence abstraction for books.
 * GRASP: Low Coupling by decoupling controller/service from SQL details.
 * Pattern: Proxy Pattern via Spring Data repository implementation.
 */
public interface BookRepository extends JpaRepository<Book, Long> {
}