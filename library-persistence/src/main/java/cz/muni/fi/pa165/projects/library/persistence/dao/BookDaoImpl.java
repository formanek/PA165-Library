package cz.muni.fi.pa165.projects.library.persistence.dao;

import cz.muni.fi.pa165.projects.library.persistence.entity.Book;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

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
        em.remove(findById(book.getId()));
    }

    @Override
    public Book findById(Long id) {
        Objects.requireNonNull(id, "null id");
        Book result = em.find(Book.class, id);
        return result;
    }

    @Override
    public List<Book> find(Book book) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Book> cq = cb.createQuery(Book.class);
        Root<Book> b = cq.from(Book.class);
        ArrayList<Predicate> preds = new ArrayList();
        
        if (book.getId() != null) {
            if (book.getAuthor() != null || book.getIsbn() != null || book.getTitle() != null) {
                throw new IllegalArgumentException("When Id is not null other fields have to be null");
            }
            List<Book> l = new ArrayList<>();
            l.add(findById(book.getId()));
            return l;
        } else {
            if (book.getAuthor() != null){
                checkString(book.getAuthor(), "Author");
                preds.add(cb.equal(b.get("author"),book.getAuthor()));
            }
            if (book.getIsbn() != null){
                checkString(book.getIsbn(), "ISBN");
                preds.add(cb.equal(b.get("isbn"),book.getIsbn()));
            }
            if (book.getTitle() != null){
                checkString(book.getTitle(), "Title");
                preds.add(cb.equal(b.get("title"),book.getTitle()));
            }
            cq.select(b).where(cb.and(preds.toArray(new Predicate[preds.size()])));
            TypedQuery<Book> tq= em.createQuery(cq);
            return tq.getResultList();
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
