package com.library.Smart_Library.service;

import com.library.Smart_Library.model.Book;
import com.library.Smart_Library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Owner: Pranav G
 * SRN: NOT-PROVIDED
 * Purpose: Catalog module business logic for books and inventory operations.
 * GRASP: Controller support by centralizing catalog rules outside UI
 * controller.
 * Pattern: Singleton service bean and Template Method through BaseService.
 */
@Service
public class BookService extends BaseService {

  @Autowired
  private BookRepository bookRepository;

  /**
   * Retrieves all books for the catalog screen.
   * GRASP: Information Expert at service boundary for catalog retrieval use case.
   */
  public List<Book> getAllBooks() {
    return performActionWithLogging("list-books", () -> {
    }, bookRepository::findAll);
  }

  /**
   * Persists a new or edited book.
   * GRASP: Low Coupling by isolating persistence orchestration from controller.
   */
  public Book saveBook(Book book) {
    return performActionWithLogging("save-book", () -> {
      if (book == null) {
        throw new IllegalArgumentException("Book is required.");
      }
      if (book.getTitle() == null || book.getTitle().isBlank()) {
        throw new IllegalArgumentException("Book title is required.");
      }
    }, () -> bookRepository.save(book));
  }
}
