package cz.muni.fi.pa165.projects.library.service;

import cz.muni.fi.pa165.projects.library.persistence.entity.Book;
import cz.muni.fi.pa165.projects.library.service.config.ServiceConfiguration;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import org.mockito.InjectMocks;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import static org.testng.Assert.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author Milan Skipala
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class BookServiceTest extends AbstractTestNGSpringContextTests {
    @Inject
    private BeanMappingService beanMappingService;
    
    @InjectMocks
    @Inject
    private BookService bookService;
    
    private Book book1;
    
    public BookServiceTest() {
    }

    @BeforeMethod
    public void setUpMethod() {
        book1 = new Book();
        book1.setAuthor("Joshua Bloch");
        book1.setTitle("Effective Java");
        book1.setIsbn("0321356683");
    }

    
    @Test(expectedExceptions = {NullPointerException.class})
    public void createNullBookTest() {
        bookService.create(null);
    }

    @Test(expectedExceptions = {NullPointerException.class})
    public void createBookWithoutAuthorTest() {
        book1.setAuthor(null);
        bookService.create(book1);
    }

    @Test(expectedExceptions = {NullPointerException.class})
    public void createBookWithoutIsbnTest() {
        book1.setIsbn(null);
        bookService.create(book1);
    }

    @Test(expectedExceptions = {NullPointerException.class})
    public void createBookWithoutTitleTest() {
        book1.setTitle(null);
        bookService.create(book1);
    }

    @Test(expectedExceptions = {PersistenceException.class})
    public void createExistingBookTest() {
        bookService.create(book1);
        Book book = new Book();
        book.setAuthor("other author");
        book.setId(book1.getId());
        book.setIsbn(book1.getIsbn());
        book.setTitle(book1.getTitle());
        bookService.create(book);
    }

    @Test
    public void createAndFindByIdBookTest() {
        bookService.create(book1);
        Long id = book1.getId();
        assertNotNull(id);
        assertEquals(bookService.findById(id), book1);
    }

    @Test(expectedExceptions = {NullPointerException.class})
    public void deleteNullBookTest() {
        bookService.delete(null);
    }

    @Test(expectedExceptions = {IllegalArgumentException.class})
    public void deleteNonExistingBookTest() {
        book1.setId(999999999L);
        bookService.delete(book1);
    }

    //@Test
    public void createAndDeleteBookTest() {
        Book book2 = new Book();
        book2.setAuthor("Joshua Bloch Jr.");
        book2.setTitle("Another Java Book");
        book2.setIsbn("0321356683");
        
        bookService.create(book1);
        bookService.create(book2);
        assertEquals(bookService.findById(book1.getId()), book1);
        bookService.delete(book1);
        assertTrue(bookService.findById(book1.getId()) == null);
        assertEquals(bookService.findById(book2.getId()), book2);
    }

    @Test(expectedExceptions = {NullPointerException.class})
    public void findBookWithNullIdTest() {
        bookService.findById(null);
    }

    //@Test(expectedExceptions = {NullPointerException.class})
    public void findNullBookTest() {
        //bookService.find(null);
    }

    //@Test(expectedExceptions = {IllegalArgumentException.class})
    public void findBookWithSetBothIdAndIsbnTest() {
        Book book = new Book();
        book.setId(1234567L);
        book.setIsbn("0321356683");
        //bookService.find(book);
    }

    @Test
    public void findBookWithEverythingNullTest() {
        //List<Book> result = bookService.find(new Book());
        //assertTrue(result != null);
        //assertTrue(result.isEmpty());
    }

    @Test
    public void findBookWithTitleTest() {
        Book book2 = new Book();
        book2.setAuthor("Joshua Bloch Jr.");
        book2.setTitle("Another Java Book");
        book2.setIsbn("0321356683");
        
        bookService.create(book1);
        bookService.create(book2);
        Book book = new Book();
        book.setTitle("Effective Java");
        //List<Book> result = bookService.find(book);
        //assertEquals(result.size(), 1);
        //assertEquals(result.get(0), book1);
        book.setTitle("non existing title");
        //assertTrue(bookService.find(book).isEmpty());
    }

    @Test
    public void findBookComplexTest() {
        Book book2 = new Book();
        book2.setAuthor("Joshua Bloch Jr.");
        book2.setTitle("Another Java Book");
        book2.setIsbn("9682532442");
        bookService.create(book1);
        bookService.create(book2);
        /*
        Book book = new Book();
        book.setAuthor("Joshua Bloch");
        List<Book> result = bookService.find(book);
        assertEquals(result.size(), 1);
        assertEquals(result.get(0), book1);
        book.setAuthor("Joshua Bloch");
        book.setTitle("Effective Java");
        result = bookService.find(book);
        assertEquals(result.size(), 1);
        assertEquals(result.get(0), book1);
        book.setIsbn(book1.getIsbn());
        result = bookService.find(book);
        assertEquals(result.size(), 1);
        assertEquals(result.get(0), book1);
        book = new Book();
        book.setIsbn(book2.getIsbn());
        result = bookService.find(book);
        assertEquals(result.size(), 1);
        assertEquals(result.get(0), book2);
        book.setTitle("Another Java Book");
        result = bookService.find(book);
        assertEquals(result.size(), 1);
        assertEquals(result.get(0), book2);
        book = new Book();
        book.setAuthor("Joshua Bloch");
        book.setIsbn(book1.getIsbn());
        result = bookService.find(book);
        assertEquals(result.size(), 1);
        assertEquals(result.get(0), book1);*/
    }

    @Test
    public void findAllInEmptyTest() {
        List<Book> all = bookService.findAll();
        assertTrue(all != null);
        assertTrue(all.isEmpty());
    }

    @Test
    public void createAndFindAllTest() {
        Book book2 = new Book();
        book2.setAuthor("Joshua Bloch Jr.");
        book2.setTitle("Another Java Book");
        book2.setIsbn("0321356683");
        
        bookService.create(book1);
        List<Book> all = bookService.findAll();
        assertEquals(all.size(), 1);
        assertEquals(all.get(0), book1);
        bookService.create(book2);
        all = bookService.findAll();
        assertEquals(all.size(), 2);
        assertTrue(all.contains(book1));
        assertTrue(all.contains(book2));
    }
}
