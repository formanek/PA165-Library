package cz.muni.fi.pa165.projects.library.persistence.dao;

import cz.muni.fi.pa165.projects.library.exceptions.EntityNotFoundException;
import cz.muni.fi.pa165.projects.library.persistence.entity.Book;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

/**
 * Data access object which provides access to Book entity
 *
 * @author Milan Skipala
 */
@Repository
public class BookDaoImpl implements BookDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Book book) {
        Objects.requireNonNull(book, "Null book can't be created.");
        /* How do we update a book information when following if-block will be uncommented?
        if (book.getId() != null) {
            throw new IllegalArgumentException("Book already exists");
        }*/
        Objects.requireNonNull(book.getAuthor(), "Can't create a book with null author.");
        Objects.requireNonNull(book.getIsbn(), "Can't create a book with null ISBN.");
        Objects.requireNonNull(book.getTitle(), "Can't create a book with null title.");
        checkString(book.getAuthor(),"Author");
        checkString(book.getIsbn(),"ISBN");
        checkString(book.getTitle(),"Title");
        em.persist(book);
    }

    @Override
    public void delete(Book book) throws EntityNotFoundException{
        Objects.requireNonNull(book, "Null book can't be deleted.");
        checkId(book.getId());
        try {
            em.remove(book);
        } catch (IllegalArgumentException e) {
            throw new EntityNotFoundException("Book not found, can't remove it.", e);
        }
    }

    @Override
    public Book findById(Long id) throws EntityNotFoundException {
        checkId(id);
        Book result = em.find(Book.class, id);
        if (result == null)
            throw new EntityNotFoundException("Book was not found.");
        else
            return result;
    }

    @Override
    public List<Book> find(Book book) {
        if (book.getId() != null) {
            if (book.getAuthor() != null || book.getIsbn() != null || book.getTitle() != null) {
                throw new IllegalArgumentException("When Id is not null other fields have to be null");
            }
            List<Book> l = new ArrayList<>();
            try {
                l.add(findById(book.getId()));
            } catch (EntityNotFoundException ex) {
                Logger.getLogger(BookDaoImpl.class.getName()).log(Level.SEVERE, "Book not found by id.", ex);
            }
            return l;
        } else if (book.getAuthor() != null && book.getIsbn() == null && book.getTitle() == null) {
            checkString(book.getAuthor(), "Author");
            return em.createQuery("select * from Book where author = :author", Book.class)
                    .setParameter(":author", book.getAuthor())
                    .getResultList();
        } else if (book.getAuthor() == null && book.getIsbn() != null && book.getTitle() == null) {
            checkString(book.getIsbn(), "ISBN");
            return em.createQuery("select * from Book where isbn = :isbn", Book.class)
                    .setParameter(":isbn", book.getIsbn())
                    .getResultList();
        } else if (book.getAuthor() == null && book.getIsbn() == null && book.getTitle() != null) {
            checkString(book.getTitle(), "Title");
            return em.createQuery("select * from Book where title = :title", Book.class)
                    .setParameter(":title", book.getTitle())
                    .getResultList();
        } else if (book.getAuthor() != null && book.getIsbn() != null && book.getTitle() == null) {
            checkString(book.getAuthor(), "Author");
            checkString(book.getIsbn(), "ISBN");
            return em.createQuery("select * from Book where author = :author AND isbn = :isbn", Book.class)
                    .setParameter(":author", book.getAuthor())
                    .setParameter(":isbn", book.getIsbn())
                    .getResultList();
        } else if (book.getAuthor() != null && book.getIsbn() == null && book.getTitle() != null) {
            checkString(book.getAuthor(), "Author");
            checkString(book.getTitle(), "Title");
            return em.createQuery("select * from Book where author = :author AND title = :title", Book.class)
                    .setParameter(":author", book.getAuthor())
                    .setParameter(":title", book.getTitle())
                    .getResultList();
        } else if (book.getAuthor() == null && book.getIsbn() != null && book.getTitle() != null) {
            checkString(book.getIsbn(), "ISBN");
            checkString(book.getTitle(), "Title");
            return em.createQuery("select * from Book where isbn = :isbn AND title = :title", Book.class)
                    .setParameter(":isbn", book.getIsbn())
                    .setParameter(":title", book.getTitle())
                    .getResultList();
        } else if (book.getAuthor() != null && book.getIsbn() != null && book.getTitle() != null) {
            checkString(book.getAuthor(), "Author");
            checkString(book.getIsbn(), "ISBN");
            checkString(book.getTitle(), "Title");
            return em.createQuery("select * from Book where author = :author AND isbn = :isbn AND title = :title", Book.class)
                    .setParameter(":author", book.getAuthor())
                    .setParameter(":isbn", book.getIsbn())
                    .setParameter(":title", book.getTitle())
                    .getResultList();
        } else {
            return new ArrayList<Book>();
        }
            
    }

    @Override
    public List<Book> findAll() {
        return em.createQuery("select * from Book", Book.class).getResultList();
    }

    @Override
    public void update(Book book) {
        em.merge(book);
    }
    
    
    
    /**
     * Check whether the arg is empty string. If it is, throw InvalidArgumentException 
     * where argName is used in the error message.
     * @param arg
     * @param argName 
     */
    private void checkString(String arg, String argName) {
        if (arg.trim().isEmpty()) {
            throw new IllegalArgumentException(argName + " was empty string.");
        }
    }
    
    /**
     * Check whether the id parameter can be used as an id. If id is null, NullPointerException
     * is thrown. If id is negative number, IllegalArgumentException is thrown.
     * @param id 
     */
    private void checkId(Long id) {
        if (id == null) {
            throw new NullPointerException("Id was null.");
        }
        if (id < 0) {
            throw new IllegalArgumentException("Id was negative number");
        }
    }
}
