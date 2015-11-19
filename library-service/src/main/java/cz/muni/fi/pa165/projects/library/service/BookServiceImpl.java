package cz.muni.fi.pa165.projects.library.service;

import cz.muni.fi.pa165.projects.library.persistence.dao.BookDao;
import cz.muni.fi.pa165.projects.library.persistence.entity.Book;
import java.util.List;
import javax.inject.Inject;
import org.springframework.stereotype.Service;

/**
 *
 * @author Milan Skipala
 */
@Service
public class BookServiceImpl implements BookService {
    
    @Inject
    private BookDao bookDao;

    @Override
    public void create(Book book) {
        bookDao.create(book);
    }

    @Override
    public void remove(Book book) {
        bookDao.delete(book);
    }

    @Override
    public void update(Book book) {
        bookDao.update(book);
    }

    @Override
    public Book findById(Long id) {
        return bookDao.findById(id);
    }

    @Override
    public List<Book> findAll() {
        return bookDao.findAll();
    }

    @Override
    public List<Book> find(Book book) {
        return bookDao.find(book);
    }    
}
