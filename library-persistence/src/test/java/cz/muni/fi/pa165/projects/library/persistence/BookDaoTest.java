package cz.muni.fi.pa165.projects.library.persistence;

import cz.muni.fi.pa165.projects.library.LibraryApplicationContext;
import cz.muni.fi.pa165.projects.library.persistence.dao.BookDao;
import cz.muni.fi.pa165.projects.library.persistence.entity.Book;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

import static org.testng.Assert.*;

/**
 *
 * @author David Formanek
 */
@ContextConfiguration(classes = LibraryApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class BookDaoTest extends AbstractTestNGSpringContextTests {

    @PersistenceContext
    public EntityManager em;

    @Inject
    public BookDao bookDao;

    private Book book1;
    private Book book2;

    @BeforeMethod
    public void setUpMethod() {
        book1 = new Book();
        book1.setAuthor("author1");
        book1.setIsbn("0321356683");
        book1.setTitle("title1");
        book2 = new Book();
        book2.setAuthor("author2");
        book2.setIsbn("0596009208");
        book2.setTitle("title2");
    }

    @Test(expectedExceptions = {NullPointerException.class})
    public void createNullBookTest() {
        bookDao.create(null);
    }

    @Test(expectedExceptions = {NullPointerException.class})
    public void createBookWithoutAuthorTest() {
        Book book = new Book();
        book.setIsbn("0321356683");
        book.setTitle("Effective Java");
        bookDao.create(book);
    }

    @Test(expectedExceptions = {NullPointerException.class})
    public void createBookWithoutIsbnTest() {
        Book book = new Book();
        book.setAuthor("Joshua Bloch");
        book.setTitle("Effective Java");
        bookDao.create(book);
    }

    @Test(expectedExceptions = {NullPointerException.class})
    public void createBookWithoutTitleTest() {
        Book book = new Book();
        book.setAuthor("Joshua Bloch");
        book.setIsbn("0321356683");
        bookDao.create(book);
    }

    @Test(expectedExceptions = {DataAccessException.class})
    public void createExistingBookTest() {
        bookDao.create(book1);
        Book book = new Book();
        book.setAuthor("author1changed");
        book.setId(book1.getId());
        book.setIsbn(book1.getIsbn());
        book.setTitle(book1.getTitle());
        bookDao.create(book);
    }

    @Test
    public void createAndFindByIdBookTest() {
        bookDao.create(book1);
        Long id = book1.getId();
        assertNotNull(id);
        assertEquals(bookDao.findById(id), book1);
        bookDao.create(book2);
        assertEquals(bookDao.findById(book2.getId()), book2);
        assertEquals(bookDao.findById(id), book1);
    }

    @Test(expectedExceptions = {NullPointerException.class})
    public void deleteNullBookTest() {
        bookDao.delete(null);
    }

    //TODO FIX
    //@Test(expectedExceptions = {DataAccessException.class})
    public void deleteNonExistingBook() {
        book1.setId(999999999L);
        bookDao.delete(book1);
    }

    @Test
    public void createAndDeleteBookTest() {
        bookDao.create(book1);
        bookDao.create(book2);
        assertEquals(bookDao.findById(book1.getId()), book1);
        bookDao.delete(book1);
        assertTrue(bookDao.findById(book1.getId()) == null);
        assertEquals(bookDao.findById(book2.getId()), book2);
    }

    @Test(expectedExceptions = {NullPointerException.class})
    public void findBookWithNullIdTest() {
        bookDao.findById(null);
    }

    @Test(expectedExceptions = {NullPointerException.class})
    public void findNullBookTest() {
        bookDao.find(null);
    }

    @Test(expectedExceptions = {DataAccessException.class})
    public void findBookWithSetBothIdAndIsbnTest() {
        Book book = new Book();
        book.setId(1234567L);
        book.setIsbn("0321356683");
        bookDao.find(book);
    }

    @Test
    public void findBookWithNothingSetTest() {
        List<Book> result = bookDao.find(new Book());
        assertTrue(result != null);
        assertTrue(result.isEmpty());
    }

    @Test
    public void findBookWithTitleTest() {
        bookDao.create(book1);
        bookDao.create(book2);
        Book book = new Book();
        book.setTitle("title1");
        List<Book> result = bookDao.find(book);
        assertEquals(result.size(), 1);
        assertEquals(result.get(0), book1);
        book.setTitle("non existing title");
        assertTrue(bookDao.find(book).isEmpty());
    }

    @Test
    public void findBookComplexTest() {
        bookDao.create(book1);
        bookDao.create(book2);
        Book book = new Book();
        book.setAuthor("author2");
        List<Book> result = bookDao.find(book);
        assertEquals(result.size(), 1);
        assertEquals(result.get(0), book2);
        book.setAuthor("author1");
        book.setTitle("title1");
        result = bookDao.find(book);
        assertEquals(result.size(), 1);
        assertEquals(result.get(0), book1);
        book.setIsbn(book1.getIsbn());
        result = bookDao.find(book);
        assertEquals(result.size(), 1);
        assertEquals(result.get(0), book1);
        book = new Book();
        book.setIsbn(book2.getIsbn());
        result = bookDao.find(book);
        assertEquals(result.size(), 1);
        assertEquals(result.get(0), book2);
        book.setTitle("title2");
        result = bookDao.find(book);
        assertEquals(result.size(), 1);
        assertEquals(result.get(0), book2);
        book = new Book();
        book.setAuthor("author1");
        book.setIsbn(book1.getIsbn());
        result = bookDao.find(book);
        assertEquals(result.size(), 1);
        assertEquals(result.get(0), book1);
    }

    @Test
    public void findAllInEmptyTest() {
        List<Book> all = bookDao.findAll();
        assertTrue(all != null);
        assertTrue(all.isEmpty());
    }

    @Test
    public void createAndFindAllTest() {
        bookDao.create(book1);
        List<Book> all = bookDao.findAll();
        assertEquals(all.size(), 1);
        assertEquals(all.get(0), book1);
        bookDao.create(book2);
        all = bookDao.findAll();
        assertEquals(all.size(), 2);
        assertTrue(all.contains(book1));
        assertTrue(all.contains(book2));
    }

    @Test(expectedExceptions = {NullPointerException.class})
    public void updateNullBookTest() {
        bookDao.update(null);
    }

    @Test(expectedExceptions = {NullPointerException.class})
    public void updateBookWithoutAuthorTest() {
        Book book = new Book();
        book.setIsbn("0321356683");
        book.setTitle("Effective Java");
        bookDao.update(book);
    }

    @Test(expectedExceptions = {NullPointerException.class})
    public void updateBookWithoutIsbnTest() {
        Book book = new Book();
        book.setAuthor("Joshua Bloch");
        book.setTitle("Effective Java");
        bookDao.update(book);
    }

    @Test(expectedExceptions = {NullPointerException.class})
    public void updateBookWithoutTitleTest() {
        Book book = new Book();
        book.setAuthor("Joshua Bloch");
        book.setIsbn("0321356683");
        bookDao.update(book);
    }

    @Test
    public void createAndUpdateAuthorTest() {
        bookDao.create(book1);
        Book book = new Book();
        book.setAuthor("author1changed");
        book.setId(book1.getId());
        book.setIsbn(book1.getIsbn());
        book.setTitle(book1.getTitle());
        assertEquals(bookDao.findById(book1.getId()).getAuthor(), "author1");
        bookDao.update(book);
        assertEquals(bookDao.findById(book1.getId()).getAuthor(), "author1changed");
    }
}
