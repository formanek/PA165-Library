package cz.muni.fi.pa165.projects.library.rest.controllers;

import cz.muni.fi.pa165.projects.library.dto.BookCreateDTO;
import cz.muni.fi.pa165.projects.library.dto.BookDTO;
import cz.muni.fi.pa165.projects.library.facade.BookFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

/**
 * Rest Controller for book
 * @author Jan Mosat
 */
@RestController
@RequestMapping("/book")
public class BookController {

    @Inject
    private BookFacade bookFacade;

    /**
     * Get all books
     * @return list of BookDTOs
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<BookDTO> getBooks() {
        return bookFacade.getAllBooks();
    }

    /**
     * Handles Exception throw during processing REST actions
     */
    @ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Requested book was not found")
    @ExceptionHandler(Exception.class)
    public void notFound() {
    }

    /**
     * Get one specified id
     * @param id of book
     * @return BookDTO
     * @throws Exception
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final BookDTO findBookById(@PathVariable("id") long id) throws Exception {
        return bookFacade.findBookById(id);
    }

    /**
     * Create a new book
     * @param bookCreateDTO book to add
     * @return BookDTO of book which was added
     * @throws Exception
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final BookDTO addBook(@RequestBody BookCreateDTO bookCreateDTO) throws Exception {
        Long id = bookFacade.addBook(bookCreateDTO);
        return bookFacade.findBookById(id);
    }

    /**
     * Changes loanable attribute for book
     * @param id of book
     * @param book book to be changed
     * @return BookDTO of changed book
     * @throws Exception
     */
    @RequestMapping(value = "/{id}/loanability", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final BookDTO changeLoanability(@PathVariable("id") long id, @RequestBody BookDTO book) throws Exception {
        bookFacade.changeLoanability(id, book.getLoanable());
        return bookFacade.findBookById(id);
    }
}