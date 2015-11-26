package cz.muni.fi.pa165.projects.library.service;

import cz.muni.fi.pa165.projects.library.persistence.dao.BookDao;
import cz.muni.fi.pa165.projects.library.persistence.entity.Book;
import cz.muni.fi.pa165.projects.library.persistence.entity.Loan;
import cz.muni.fi.pa165.projects.library.persistence.entity.LoanItem;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
        Objects.requireNonNull(book, "Book can't be null");
        bookDao.create(book);
    }

    @Override
    public void delete(Book book) {
        Objects.requireNonNull(book, "Book can't be null");
        bookDao.delete(book);
    }

    @Override
    public Book findById(Long id) {
        Objects.requireNonNull(id, "Id can't be null");
        return bookDao.findById(id);
    }

    @Override
    public List<Book> findAll() {
        return bookDao.findAll();
    }

    @Override
    public List<Book> findAllBooksOfAuthor(String author) {
        Objects.requireNonNull(author, "Author can't be null");
        Book b = new Book();
        b.setAuthor(author);
        return bookDao.find(b);
    }

    @Override
    public List<Book> findBookByIsbn(String isbn) {
        Objects.requireNonNull(isbn, "Isbn can't be null");
        Book b = new Book();
        b.setIsbn(isbn);
        return bookDao.find(b);
    }

    @Override
    public List<Book> findBookByTitle(String title) {
        Objects.requireNonNull(title, "Title can't be null");
        Book b = new Book();
        b.setTitle(title);
        return bookDao.find(b);
    }

    @Override
    public boolean isBookAvailable(Book book) {
        Objects.requireNonNull(book);
        List<Loan> allLoans = loanService.findAll();
        List<Loan> unreturnedLoans = new ArrayList<>();
        for (Loan l : allLoans) {
            if (l.getReturnTimestamp() == null) {
                unreturnedLoans.add(l);
            }
        }
        List<Book> unreturnedBooks = new ArrayList<>();
        for (Loan l : unreturnedLoans) {
            for (LoanItem i : l.getLoanItems()) {
                unreturnedBooks.add(i.getBook());
            }
        }
        return !unreturnedBooks.contains(book);
    }
}
