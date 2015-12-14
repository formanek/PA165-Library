package cz.muni.fi.pa165.projects.library.service.facade;

import cz.muni.fi.pa165.projects.library.dto.BookCreateDTO;
import cz.muni.fi.pa165.projects.library.dto.BookDTO;
import cz.muni.fi.pa165.projects.library.facade.BookFacade;
import cz.muni.fi.pa165.projects.library.persistence.entity.Book;
import cz.muni.fi.pa165.projects.library.service.BeanMappingService;
import cz.muni.fi.pa165.projects.library.service.BookService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Milan Skipala
 */
@Service
@Transactional
public class BookFacadeImpl implements BookFacade{
    
    @Inject
    private BookService bookService;
    
    @Inject
    private BeanMappingService beanMappingService;

    @Override
    public Long addBook(BookCreateDTO bookCreateDTO) {
        Objects.requireNonNull(bookCreateDTO,"Null BookDTO can't be added.");
        Objects.requireNonNull(bookCreateDTO.getAuthor(),"BookDTO with null attribute can't be added.");
        Objects.requireNonNull(bookCreateDTO.getIsbn(),"BookDTO with null attribute can't be added.");
        Objects.requireNonNull(bookCreateDTO.getTitle(),"BookDTO with null attribute can't be added.");
        if (bookCreateDTO.getAuthor().trim().length() == 0 
                || bookCreateDTO.getIsbn().trim().length() == 0 
                || bookCreateDTO.getTitle().trim().length() == 0) {
            throw new IllegalArgumentException("Empty string is not valid attribute of BookDTO.");
        }
            
        Book book = beanMappingService.mapTo(bookCreateDTO, Book.class);
        if (book.getLoanable() == null)
        book.setLoanable(true);
        bookService.create(book);
        return book.getId();
    }

    @Override
    public BookDTO findBookById(Long id) {
        Objects.requireNonNull(id,"Can't find book with null id.");
        return beanMappingService.mapTo(bookService.findById(id),BookDTO.class);
    }

    @Override
    public List<BookDTO> findAllBooksOfAuthor(String author) {
        Objects.requireNonNull(author,"Can't find book with null author.");
        if (author.trim().length() == 0) {
            throw new IllegalArgumentException("Empty string is not valid author.");
        }
        return beanMappingService.mapTo(bookService.findAllBooksOfAuthor(author),BookDTO.class);
    }

    @Override
    public List<BookDTO> findBookByIsbn(String isbn) {
        Objects.requireNonNull(isbn,"Can't find book with null isbn.");
        if (isbn.trim().length() == 0) {
            throw new IllegalArgumentException("Empty string is not valid isbn.");
        }
        return beanMappingService.mapTo(bookService.findBookByIsbn(isbn),BookDTO.class);
    }

    @Override
    public List<BookDTO> getAllBooks() {
        return beanMappingService.mapTo(bookService.findAll(),BookDTO.class);
    }

    @Override
    public List<BookDTO> getAllLoanableBooks() {
        return beanMappingService.mapTo(bookService.findAllLoanable(),BookDTO.class);
    }

    @Override
    public List<BookDTO> getAllUnloanableBooks() {
        return beanMappingService.mapTo(bookService.findAllUnloanable(),BookDTO.class);
    }

    @Override
    public void deleteBook(Long id) {
        Objects.requireNonNull(id,"Can't delete book with null id.");
        bookService.delete(beanMappingService.mapTo(bookService.findById(id),Book.class));
    }

    @Override
    public void changeLoanability(Long id, boolean loanability) {
        Objects.requireNonNull(id,"Can't change loanability of book with null id.");
        Book book = bookService.findById(id);
        if (book.getLoanable() != loanability) {
            bookService.changeLoanability(book);
        }
    }

    @Override
    public List<BookDTO> findBookByTitle(String title) {
        Objects.requireNonNull(title,"Can't find book with null title.");
        if (title.trim().length() == 0) {
            throw new IllegalArgumentException("Empty string is not valid title.");
        }
        return beanMappingService.mapTo(bookService.findBookByTitle(title),BookDTO.class);
    }

    @Override
    public boolean isBookAvailable(Long id) {
        Objects.requireNonNull(id,"Can't obtain availability of book with null id.");
        return bookService.isBookAvailable(bookService.findById(id));
    }


    
}
