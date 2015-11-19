package cz.muni.fi.pa165.projects.library.service.facade;

import cz.muni.fi.pa165.projects.library.dto.BookCreateDTO;
import cz.muni.fi.pa165.projects.library.dto.BookDTO;
import cz.muni.fi.pa165.projects.library.facade.BookFacade;
import cz.muni.fi.pa165.projects.library.persistence.entity.Book;
import cz.muni.fi.pa165.projects.library.service.BeanMappingService;
import cz.muni.fi.pa165.projects.library.service.BookService;
import java.util.List;
import javax.inject.Inject;

/**
 *
 * @author Milan Skipala
 */
public class BookFacadeImpl implements BookFacade{
    @Inject
    private BookService bookService;
    
    @Inject
    private BeanMappingService beanMappingService;

    @Override
    public Long addBook(BookCreateDTO bookCreateDTO) {
        Book book = beanMappingService.mapTo(bookCreateDTO, Book.class);
        bookService.create(book);
        return book.getId();
    }

    @Override
    public BookDTO findBookById(Long id) {
        return beanMappingService.mapTo(bookService.findById(id),BookDTO.class);
    }

    @Override
    public List<BookDTO> findAllBooksOfAuthor(String author) {
        Book book = new Book();
        book.setAuthor(author);
        return beanMappingService.mapTo(bookService.find(book),BookDTO.class);
    }

    @Override
    public List<BookDTO> findBookByIsbn(String isbn) {
        Book book = new Book();
        book.setIsbn(isbn);
        return beanMappingService.mapTo(bookService.find(book),BookDTO.class);
    }

    @Override
    public List<BookDTO> getAllBooks() {
        return beanMappingService.mapTo(bookService.findAll(),BookDTO.class);
    }

    @Override
    public void deleteBook(Long id) {
        bookService.remove(beanMappingService.mapTo(bookService.findById(id),Book.class));
    }
    
}
