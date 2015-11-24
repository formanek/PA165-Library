package cz.muni.fi.pa165.projects.library.service;

import cz.muni.fi.pa165.projects.library.dto.BookDTO;
import cz.muni.fi.pa165.projects.library.persistence.dao.BookDao;
import cz.muni.fi.pa165.projects.library.persistence.entity.Book;
import cz.muni.fi.pa165.projects.library.persistence.entity.Loan;
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
    
    @Inject
    private LoanService loanService;

    @Override
    public void create(Book book) {
        bookDao.create(book);
    }

    @Override
    public void delete(Book book) {
        bookDao.delete(book);
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
    public List<BookDTO> findAllBooksOfAuthor(String author) {
        return null;
    }

    @Override
    public List<BookDTO> findBookByIsbn(String isbn) {
        return null;
    }

    @Override
    public List<BookDTO> findBookByTitle(String isbn) {
        return null;
    }

    @Override
    public boolean isBookAvailable(Book book) {
        //List<Loan> unreturnedLoans = loanService.getUnreturnedLoans();
        //for (Loan l : unreturnedLoans)
        return false;
    }
}
