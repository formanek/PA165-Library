package cz.muni.fi.pa165.projects.library.facade;

import cz.muni.fi.pa165.projects.library.dto.BookCreateDTO;
import cz.muni.fi.pa165.projects.library.dto.BookDTO;
import java.util.List;

/**
 *
 * @author Milan Skipala
 */
public interface BookFacade {
    Long addBook(BookCreateDTO book);
    BookDTO findBookById(Long id);
    List<BookDTO> findAllBooksOfAuthor(String author);
    List<BookDTO> findBookByIsbn(String isbn);
    List<BookDTO> findBookByTitle(String title);
    List<BookDTO> getAllBooks();
    boolean isBookAvailable(Long id);
    void deleteBook(Long id);
}
