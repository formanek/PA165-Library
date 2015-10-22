/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.projects.library.persistence.dao;

import cz.muni.fi.pa165.projects.library.persistence.entity.Book;
import java.util.ArrayList;
import java.util.List;
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
        em.persist(book);
    }

    @Override
    public void delete(Book book) {
        em.remove(book);
    }

    @Override
    public Book findById(Long id) {
        return em.find(Book.class, id);
    }

    @Override
    public List<Book> find(Book book) {
        if (book.getId() != null) {
            List<Book> l = new ArrayList<>();
            l.add(findById(book.getId()));
            return l;
        } else if (book.getAuthor() != null && book.getIsbn() == null && book.getTitle() == null) {
            return em.createQuery("select * from Book where author = :author", Book.class)
                    .setParameter(":author", book.getAuthor())
                    .getResultList();
        } else if (book.getAuthor() == null && book.getIsbn() != null && book.getTitle() == null) {
            return em.createQuery("select * from Book where isbn = :isbn", Book.class)
                    .setParameter(":isbn", book.getIsbn())
                    .getResultList();
        } else if (book.getAuthor() == null && book.getIsbn() == null && book.getTitle() != null) {
            return em.createQuery("select * from Book where title = :title", Book.class)
                    .setParameter(":title", book.getTitle())
                    .getResultList();
        } else if (book.getAuthor() != null && book.getIsbn() != null && book.getTitle() == null) {
            return em.createQuery("select * from Book where author = :author AND isbn = :isbn", Book.class)
                    .setParameter(":author", book.getAuthor())
                    .setParameter(":isbn", book.getIsbn())
                    .getResultList();
        } else if (book.getAuthor() != null && book.getIsbn() == null && book.getTitle() != null) {
            return em.createQuery("select * from Book where author = :author AND title = :title", Book.class)
                    .setParameter(":author", book.getAuthor())
                    .setParameter(":title", book.getTitle())
                    .getResultList();
        } else if (book.getAuthor() != null && book.getIsbn() != null && book.getTitle() != null) {
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

}
