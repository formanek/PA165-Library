package cz.muni.fi.pa165.projects.library.service;

import cz.muni.fi.pa165.projects.library.persistence.dao.BookDao;
import cz.muni.fi.pa165.projects.library.persistence.entity.Book;
import cz.muni.fi.pa165.projects.library.persistence.entity.Loan;
import cz.muni.fi.pa165.projects.library.persistence.entity.LoanItem;
import cz.muni.fi.pa165.projects.library.service.config.ServiceConfiguration;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import org.mockito.InjectMocks;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import org.mockito.Mock;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import static org.testng.Assert.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author Milan Skipala
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class BookServiceTest extends AbstractTestNGSpringContextTests {    
    
    @Mock
    private BookDao bookDao;
    
    @Mock
    private LoanService loanService;
    
    private Loan loan;
    
    private LoanItem loanItem;
    
    @Inject
    @InjectMocks
    private BookService bookService;
    
    private Book book1;
    
    public BookServiceTest() {
    }

    @BeforeClass
    public void setup()
    {
        MockitoAnnotations.initMocks(this);
    }
    
    @BeforeMethod
    public void setUpMethod() {
        book1 = new Book();
        book1.setAuthor("Joshua Bloch");
        book1.setTitle("Effective Java");
        book1.setIsbn("0321356683");
    }

    @Test
    public void createBookBasicTest() {        
        bookService.create(book1);     
        verify(bookDao).create(any(Book.class));
    }
    
    @Test(expectedExceptions = {NullPointerException.class})
    public void createNullBookTest() {
        doThrow(new NullPointerException()).when(bookDao).create(null);
        bookService.create(null);
    }

    @Test
    public void deleteBookBasicTest() {
        bookService.delete(book1);  
        verify(bookDao).delete(any(Book.class));
    }
    
    @Test(expectedExceptions = {NullPointerException.class})
    public void deleteNullBookTest() {
        doThrow(new NullPointerException()).when(bookDao).delete(null);
        bookService.delete(null);
    }

    @Test(expectedExceptions = {NullPointerException.class})
    public void findBookByNullIdTest() {
        doThrow(new NullPointerException()).when(bookDao).findById(null);
        bookService.findById(null);
    }
    
    @Test
    public void findBookByIdTest() {        
        bookService.findById(new Long(1));  
        verify(bookDao).findById(anyLong());
    }

    @Test
    public void findAllBooksTest() {
        bookService.findAll();
        verify(bookDao).findAll();
    }

    @Test(expectedExceptions = {NullPointerException.class})
    public void findAllBooksOfNullAuthorTest() {
        bookService.findAllBooksOfAuthor(null);
    }

    @Test
    public void findAllBooksOfAuthorTest() {        
        bookService.findAllBooksOfAuthor("author");
        verify(bookDao).find(any(Book.class));
    }

    @Test(expectedExceptions = {NullPointerException.class})
    public void findBookByNullIsbnTest() {
        bookService.findBookByIsbn(null);
    }
    
    @Test
    public void findBookByIsbnTest() {        
        bookService.findBookByIsbn("ISBN");
        verify(bookDao, atLeastOnce()).find(any(Book.class));
    }

    @Test(expectedExceptions = {NullPointerException.class})
    public void findBookByNullTitleTest() {
        bookService.findBookByTitle(null);
    }
    
    @Test
    public void findBookByTitleTest() {        
        bookService.findBookByTitle("ISBN");
        verify(bookDao, atLeastOnce()).find(any(Book.class));
    }
    
    @Test(expectedExceptions = {NullPointerException.class})
    public void isNullBookAvailableTest() {
        bookService.isBookAvailable(null);
    }
    
    @Test
    public void isBookAvailableBasicTest() {
        loanItem = new LoanItem();
        loan = new Loan();
        loanItem.setBook(book1);
        
        Set<LoanItem> items = new HashSet<>();
        items.add(loanItem);
        
        loan.setLoanItems(items);
        
        List<Loan> list = new ArrayList<>();
        list.add(loan);
        when(loanService.findAllUnreturnedLoans()).thenReturn(list);
        
        assertFalse(bookService.isBookAvailable(book1));
        when(loanService.findAllUnreturnedLoans()).thenReturn(new ArrayList<Loan>());
        assertTrue(bookService.isBookAvailable(book1));
    }
}
