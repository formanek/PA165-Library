package cz.muni.fi.pa165.projects.library.service;

import cz.muni.fi.pa165.projects.library.persistence.entity.Book;
import java.util.List;

/**
 *
 * @author Milan Skipala
 */
public interface BookService {
    void create(Book book);
    void remove(Book book);
    void update(Book book);
    Book findById(Long id);
    List<Book> findAll();
    List<Book> find(Book book);
}
