package cz.muni.fi.pa165.projects.library.service;

import cz.muni.fi.pa165.projects.library.persistence.entity.Book;
import java.util.List;

/**
 *
 * @author Milan Skipala
 */
public interface BookService {
    /**
     * add new book in the system
     * @param book book we want to add
     * @throws NullPointerException when the book param is null
     */
    void create(Book book);
    /**
     * delete book from the system
     * @param book book we want to delete
     * @throws NullPointerException when the book param is null
     */
    void delete(Book book);
    /**
     * find book with the specified id
     * @param id of the book
     * @return retrieved book or null if the book was not found
     * @throws NullPointerException when the id param is null
     */
    Book findById(Long id);
    /**
     * retrieve all books in the system
     * @return list of books
     */
    List<Book> findAll();
    /**
     * find all books of the specified author
     * @param author
     * @return list of books written by the specified author
     * @throws NullPointerException when author param is null, IllegalArgumentException 
     * when author is empty string
     */
    List<Book> findAllBooksOfAuthor(String author);
    /**
     * find all books with specified isbn
     * @param isbn isbn of the books
     * @return list of books with the same isbn
     * @throws NullPointerException when isbn param is null, IllegalArgumentException 
     * when isbn is empty string
     */
    List<Book> findBookByIsbn(String isbn);
    /**
     * find all books with the specified title
     * @param title title of the book
     * @return list of books with the specified title
     * @throws NullPointerException when title param is null, IllegalArgumentException when title is empty string
     */
    List<Book> findBookByTitle(String title);
    /**
     * Get information whether the book can be loaned to some member. Book can 
     * be loaned only in case when there does not exist any unreturned loan
     * containing this particular book (book with some specific ID).
     * 
     * Example: There are two books in the system - (1,Title 1,Author,Isbn) and (2,Title 1, Author, Isbn).
     * Book with id 1 has not been returned yet, book with id 2 has been already returned. When isBookAvailable 
     * is called on the first instance false is returned, although there exists another "same" book which is available.
     * The availability is bound to specific instance with specific id.
     * 
     * @param book
     * @return true if the book is available
     */
    boolean isBookAvailable(Book book);
    
}
