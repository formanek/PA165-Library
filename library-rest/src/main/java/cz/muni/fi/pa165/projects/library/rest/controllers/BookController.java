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
 * @author Jan Mosat
 */
@RestController
@RequestMapping("/book")
public class BookController {

    @Inject
    private BookFacade bookFacade;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<BookDTO> getBooks() {
        return bookFacade.getAllBooks();
    }

    @ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Requested book was not found")
    @ExceptionHandler(Exception.class)
    public void notFound() {
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final BookDTO findBookById(@PathVariable("id") long id) throws Exception {
        return bookFacade.findBookById(id);

    }

    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final BookDTO addBook(@RequestBody BookCreateDTO bookCreateDTO) throws Exception {
        Long id = bookFacade.addBook(bookCreateDTO);
        return bookFacade.findBookById(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public final void deleteBook(@PathVariable("id") long id) throws Exception {
        bookFacade.deleteBook(id);
    }
}