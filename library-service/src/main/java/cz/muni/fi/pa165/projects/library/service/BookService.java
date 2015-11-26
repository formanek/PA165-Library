package cz.muni.fi.pa165.projects.library.service;

import cz.muni.fi.pa165.projects.library.persistence.entity.Book;
import java.util.List;

/**
 *
 * @author Milan Skipala
 */
public interface BookService {
    void create(Book book);
    void delete(Book book);
    Book findById(Long id);
    List<Book> findAll();
    List<Book> findAllBooksOfAuthor(String author);
    List<Book> findBookByIsbn(String isbn);
    List<Book> findBookByTitle(String title);
    boolean isBookAvailable(Book book); //podle ID
    
}
