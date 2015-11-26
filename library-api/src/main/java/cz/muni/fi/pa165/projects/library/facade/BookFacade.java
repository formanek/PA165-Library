package cz.muni.fi.pa165.projects.library.facade;

import cz.muni.fi.pa165.projects.library.dto.BookCreateDTO;
import cz.muni.fi.pa165.projects.library.dto.BookDTO;
import java.util.List;

/**
 *
 * @author Milan Skipala
 */
public interface BookFacade {
    /**
     * Add book in the system.
     * @param book
     * @return id of the added book
     */
    Long addBook(BookCreateDTO book);
    /**
     * retrieves the book with specified id
     * @param id
     * @return retrieved book or null if the book was not found
     * @throws NullPointerException when book param is null, IllegalArgumentException 
     * when book param has some of the attributes null or empty
     */
    BookDTO findBookById(Long id);
    /**
     * find all books of the specified author
     * @param author
     * @return list of books written by the specified author
     * @throws NullPointerException when author param is null, IllegalArgumentException 
     * when author is empty string
     */
    List<BookDTO> findAllBooksOfAuthor(String author);
    /**
     * find all books with specified isbn
     * @param isbn isbn of the books
     * @return list of books with the same isbn
     * @throws NullPointerException when isbn param is null, IllegalArgumentException 
     * when isbn is empty string
     */
    List<BookDTO> findBookByIsbn(String isbn);
    /**
     * find all books with the specified title
     * @param title title of the book
     * @return list of books with the specified title
     * @throws NullPointerException when title param is null, IllegalArgumentException when title is empty string
     */
    List<BookDTO> findBookByTitle(String title);
    /**
     * retrieve all books that are in the system
     * @return list of all books in the system
     */
    List<BookDTO> getAllBooks();
    /**
     * obtain information, whether the book is available for loaning or not.
     * See BookService documentation for more detailed description
     * @param id id of the book
     * @return true if book can be loaned, false otherwise
     * @throws NullPointerException when the id is null
     */
    boolean isBookAvailable(Long id);
    /**
     * delete book from the system
     * @param id id of the book
     * @throws NullPointerException when the id is null
     */
    void deleteBook(Long id);
}
