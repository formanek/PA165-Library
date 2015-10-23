package cz.muni.fi.pa165.projects.library.persistence.dao;

import cz.muni.fi.pa165.projects.library.persistence.entity.Book;
import java.util.List;

/**
 * Interface for data access objects which provide access to Book entity
 * 
 * @author Milan Skipala
 */
public interface BookDao {
    /**
     * Persist new book. All of the book attributes except id must be nonNull and nonEmpty strings.
     * @param book 
     */
    public void create(Book book);
    
    /**
     * Delete the book. book.id must be valid id.
     * @param book 
     */
    
    public void delete(Book book);
    /**
     * Find the book with the specified id. Id must be valid id.
     * @param id
     * @return  book with the specified id
     */
    public Book findById(Long id);
    
    /**
     * Find the books with specified details. If book.id is not null, other attributes
     * have to be null. If id is null, the search is based on nonNull attributes.
     * @param book carries details about books which we want to find
     * @return list of found books
     */
    public List<Book> find(Book book);
    
    /**
     * finds all books in the database
     * @return all books
     */
    public List<Book> findAll();
    
}
