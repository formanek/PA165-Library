package cz.muni.fi.pa165.projects.library.service.facede;

import cz.muni.fi.pa165.projects.library.dto.BookCreateDTO;
import cz.muni.fi.pa165.projects.library.dto.BookDTO;
import cz.muni.fi.pa165.projects.library.facade.BookFacade;
import cz.muni.fi.pa165.projects.library.persistence.entity.Book;
import cz.muni.fi.pa165.projects.library.service.BookService;
import cz.muni.fi.pa165.projects.library.service.config.ServiceConfiguration;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.util.ReflectionTestUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

/**
 *
 * @author Milan Skipala
 */
@ContextConfiguration(classes = ServiceConfiguration.class)

public class BookFacadeTest extends AbstractTestNGSpringContextTests {
    private Book book;
    private BookDTO bookDTO;
    
    @Mock
    private BookService bookService;
    
    @Inject
    private BookFacade bookFacade;
    
    public BookFacadeTest() {
    }
    
    @BeforeMethod
    public void beforeMethod()
    {
        book = new Book();
        book.setAuthor("Joshua Bloch");
        book.setIsbn("0321356683");
        book.setTitle("Effective Java");
        book.setLoanable(true);
        bookDTO = new BookDTO();
        bookDTO.setAuthor("Joshua Bloch");
        bookDTO.setIsbn("0321356683");
        bookDTO.setTitle("Effective Java");
        when(bookService.findById(anyLong())).thenReturn(book);
    }
    
    @BeforeClass
    public void setup() throws Exception
    {
        MockitoAnnotations.initMocks(this);
        //bookService mock is not injected by @InjectMocks because the bean bookFacade is transactional. 
        BookFacade facade = (BookFacade) unwrapProxy(bookFacade);
        ReflectionTestUtils.setField(facade, "bookService", bookService);
    }
    
    public static final Object unwrapProxy(Object bean) throws Exception {
        /*
         * If the given object is a proxy, set the return value as the object
         * being proxied, otherwise return the given object.
         */
        if (AopUtils.isAopProxy(bean) && bean instanceof Advised) {
            Advised advised = (Advised) bean;
            bean = advised.getTargetSource().getTarget();
        }
        return bean;
    }
        
    @Test(expectedExceptions = {NullPointerException.class})
    void addBookNullDTOTest(){
        bookFacade.addBook(null);
    }
    @Test(expectedExceptions = {NullPointerException.class})
    void addBookInvalidDTOTest(){
        BookCreateDTO createDto = new BookCreateDTO();
        bookFacade.addBook(createDto);
    }
    @Test
    void addBookBasicTest(){
        BookCreateDTO createDto = new BookCreateDTO();
        createDto.setAuthor("Joshua Bloch");
        createDto.setIsbn("0321356683");
        createDto.setTitle("Effective Java");
        Long id = bookFacade.addBook(createDto);
        verify(bookService).create(any(Book.class));
    }
    @Test(expectedExceptions = {NullPointerException.class})
    void findBookByNullIdTest(){
        bookFacade.findBookById(null);
    }
    @Test
    void findBookByIdBasicTest(){
        bookFacade.findBookById(new Long(1));
        verify(bookService, atLeastOnce()).findById(anyLong());
    }
    @Test(expectedExceptions = {NullPointerException.class})
    void findAllBooksOfNullAuthorTest(){
        bookFacade.findAllBooksOfAuthor(null);
    }
    @Test(expectedExceptions = {IllegalArgumentException.class})
    void findAllBooksOfEmptyAuthorTest(){
        bookFacade.findAllBooksOfAuthor("");
    }
    @Test
    void findAllBooksOfAuthorBasicTest(){
        List<Book> blochList = new ArrayList<>();
        blochList.add(book);
        List<BookDTO> blochListDTO = new ArrayList<>();
        blochListDTO.add(bookDTO);
        when(bookService.findAllBooksOfAuthor("Joshua Bloch")).thenReturn(blochList);
        assertEquals(bookFacade.findAllBooksOfAuthor("Joshua Bloch"),blochListDTO);
    }
    @Test(expectedExceptions = {NullPointerException.class})
    void findBookByNullIsbnTest(){
        bookFacade.findBookByIsbn(null);
    }
    @Test(expectedExceptions = {IllegalArgumentException.class})
    void findBookByEmptyIsbnTest(){
        bookFacade.findBookByIsbn("");
    }
    @Test
    void findBookByIsbnBasicTest(){
        List<Book> blochList = new ArrayList<>();
        blochList.add(book);
        List<BookDTO> blochListDTO = new ArrayList<>();
        blochListDTO.add(bookDTO);
        when(bookService.findBookByIsbn("0321356683")).thenReturn(blochList);
        assertEquals(bookFacade.findBookByIsbn("0321356683"),blochListDTO);
    }
    @Test(expectedExceptions = {NullPointerException.class})
    void findBookByNullTitleTest(){
        bookFacade.findBookByTitle(null);
    }
    @Test(expectedExceptions = {IllegalArgumentException.class})
    void findBookByEmptyTitleTest(){
        bookFacade.findBookByTitle("");
    }
    @Test
    void findBookByTitleBasicTest(){
        List<Book> blochList = new ArrayList<>();
        blochList.add(book);
        List<BookDTO> blochListDTO = new ArrayList<>();
        blochListDTO.add(bookDTO);
        when(bookService.findBookByTitle("Effective Java")).thenReturn(blochList);
        assertEquals(bookFacade.findBookByTitle("Effective Java"),blochListDTO);
    }
    @Test
    void getAllBooksBasicTest(){
        assertEquals(bookFacade.getAllBooks().size(), 0);
        
        List<Book> blochList = new ArrayList<>();
        blochList.add(book);
        List<BookDTO> blochListDTO = new ArrayList<>();
        blochListDTO.add(bookDTO);
        when(bookService.findAll()).thenReturn(blochList);
        assertEquals(bookFacade.getAllBooks(), blochListDTO);        
    }
    @Test(expectedExceptions = {NullPointerException.class})
    void isBookAvailableNullIdTest(){
        bookFacade.isBookAvailable(null);
    }
    @Test
    void isBookAvailableIdBasicTest(){
        when(bookService.isBookAvailable(any(Book.class))).thenReturn(true);
        assertTrue(bookFacade.isBookAvailable(new Long(1)));
        when(bookService.isBookAvailable(any(Book.class))).thenReturn(false);
        assertFalse(bookFacade.isBookAvailable(new Long(1)));
    }
    @Test(expectedExceptions = {NullPointerException.class})
    void deleteBookNullIdTest(){
        bookFacade.deleteBook(null);
    }
    @Test
    void deleteBookBasicTest(){
        bookFacade.deleteBook(new Long(1));
        verify(bookService).delete(any(Book.class));
    }    
}
