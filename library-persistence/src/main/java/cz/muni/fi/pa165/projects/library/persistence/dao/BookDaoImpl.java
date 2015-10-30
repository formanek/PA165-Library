package cz.muni.fi.pa165.projects.library.persistence.dao;

import cz.muni.fi.pa165.projects.library.persistence.entity.Book;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Data access object which provides access to Book entity
 *
 * @author Milan Skipala
 */
@Named
public class BookDaoImpl implements BookDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Book book) {
        Objects.requireNonNull(book, "Null book can't be created.");
        Objects.requireNonNull(book.getAuthor(), "Can't create a book with null author.");
        Objects.requireNonNull(book.getIsbn(), "Can't create a book with null ISBN.");
        Objects.requireNonNull(book.getTitle(), "Can't create a book with null title.");
        checkString(book.getAuthor(), "Author");
        checkString(book.getIsbn(), "ISBN");
        checkString(book.getTitle(), "Title");
        em.persist(book);
    }

    @Override
    public void delete(Book book) {
        Objects.requireNonNull(book, "Null book can't be deleted.");
        //Objects.requireNonNull(book.getId(), "null id");
        em.remove(book);
    }

    @Override
    public Book findById(Long id) {
        Objects.requireNonNull(id, "null id");
        Book result = em.find(Book.class, id);
        return result;
    }

    @Override
    public List<Book> find(Book book) {
        if (book.getId() != null) {
            if (book.getAuthor() != null || book.getIsbn() != null || book.getTitle() != null) {
                throw new IllegalArgumentException("When Id is not null other fields have to be null");
            }
            List<Book> l = new ArrayList<>();
            l.add(findById(book.getId()));
            return l;
        } else if (book.getAuthor() != null && book.getIsbn() == null && book.getTitle() == null) {
            checkString(book.getAuthor(), "Author");
            return em.createQuery("select b from Book b where b.author = :author", Book.class)
                    .setParameter("author", book.getAuthor())
                    .getResultList();
        } else if (book.getAuthor() == null && book.getIsbn() != null && book.getTitle() == null) {
            checkString(book.getIsbn(), "ISBN");
            return em.createQuery("select b from Book b where b.isbn = :isbn", Book.class)
                    .setParameter("isbn", book.getIsbn())
                    .getResultList();
        } else if (book.getAuthor() == null && book.getIsbn() == null && book.getTitle() != null) {
            checkString(book.getTitle(), "Title");
            return em.createQuery("select b from Book b where b.title = :title", Book.class)
                    .setParameter("title", book.getTitle())
                    .getResultList();
        } else if (book.getAuthor() != null && book.getIsbn() != null && book.getTitle() == null) {
            checkString(book.getAuthor(), "Author");
            checkString(book.getIsbn(), "ISBN");
            return em.createQuery("select b from Book b where b.author = :author AND b.isbn = :isbn", Book.class)
                    .setParameter("author", book.getAuthor())
                    .setParameter("isbn", book.getIsbn())
                    .getResultList();
        } else if (book.getAuthor() != null && book.getIsbn() == null && book.getTitle() != null) {
            checkString(book.getAuthor(), "Author");
            checkString(book.getTitle(), "Title");
            return em.createQuery("select b from Book b where b.author = :author AND b.title = :title", Book.class)
                    .setParameter("author", book.getAuthor())
                    .setParameter("title", book.getTitle())
                    .getResultList();
        } else if (book.getAuthor() == null && book.getIsbn() != null && book.getTitle() != null) {
            checkString(book.getIsbn(), "ISBN");
            checkString(book.getTitle(), "Title");
            return em.createQuery("select b from Book b where b.isbn = :isbn AND b.title = :title", Book.class)
                    .setParameter("isbn", book.getIsbn())
                    .setParameter("title", book.getTitle())
                    .getResultList();
        } else if (book.getAuthor() != null && book.getIsbn() != null && book.getTitle() != null) {
            checkString(book.getAuthor(), "Author");
            checkString(book.getIsbn(), "ISBN");
            checkString(book.getTitle(), "Title");
            return em.createQuery("select b from Book b where b.author = :author AND b.isbn = :isbn AND b.title = :title", Book.class)
                    .setParameter("author", book.getAuthor())
                    .setParameter("isbn", book.getIsbn())
                    .setParameter("title", book.getTitle())
                    .getResultList();
        } else {
            return new ArrayList<>();
        }

    }

    @Override
    public List<Book> findAll() {
        return em.createQuery("select b from Book b", Book.class).getResultList();
    }

    @Override
    public void update(Book book) {
        Objects.requireNonNull(book, "Null book can't be updated.");
        Objects.requireNonNull(book.getAuthor(), "Can't update a book with null author.");
        Objects.requireNonNull(book.getIsbn(), "Can't update a book with null ISBN.");
        Objects.requireNonNull(book.getTitle(), "Can't update a book with null title.");
        checkString(book.getAuthor(), "Author");
        checkString(book.getIsbn(), "ISBN");
        checkString(book.getTitle(), "Title");
        em.merge(book);
    }

    /**
     * Check whether the arg is empty string. If it is, throw IllegalArgumentException where argName
     * is used in the error message.
     *
     * @param arg
     * @param argName
     */
    private void checkString(String arg, String argName) {
        if (arg.trim().isEmpty()) {
            throw new IllegalArgumentException(argName + " was empty string.");
        }
    }
}
