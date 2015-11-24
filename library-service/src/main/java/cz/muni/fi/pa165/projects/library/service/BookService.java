package cz.muni.fi.pa165.projects.library.service;

import cz.muni.fi.pa165.projects.library.dto.BookDTO;
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
    List<BookDTO> findAllBooksOfAuthor(String author);
    List<BookDTO> findBookByIsbn(String isbn);
    List<BookDTO> findBookByTitle(String isbn);
    boolean isBookAvailable(Book book); //podle ID
    
}
