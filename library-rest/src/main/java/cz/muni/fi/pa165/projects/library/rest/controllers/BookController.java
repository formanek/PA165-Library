package cz.muni.fi.pa165.projects.library.rest.controllers;

import cz.muni.fi.pa165.projects.library.dto.BookCreateDTO;
import cz.muni.fi.pa165.projects.library.dto.BookDTO;
import cz.muni.fi.pa165.projects.library.facade.BookFacade;
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

        //logger.debug("rest getBooks()");
        return bookFacade.getAllBooks();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final BookDTO findBookById(@PathVariable("id") long id) throws Exception {
//        TODO fix exception catching
//        try {
            BookDTO bookDTO = bookFacade.findBookById(id);
            return bookDTO;
//        } catch (Exception ex) {
//            throw new ResourceNotFoundException();
//        }

    }

    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final BookDTO addBook(@RequestBody BookCreateDTO bookCreateDTO) throws Exception {
//        TODO fix exception catching
//        try {
            Long id = bookFacade.addBook(bookCreateDTO);
            return bookFacade.findBookById(id);
//        } catch (Exception ex) {
//            throw new ResourceAlreadyExistingException();
//        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public final void deleteBook(@PathVariable("id") long id) throws Exception {
//        TODO fix exception catching
//        try {
                bookFacade.deleteBook(id);
//        } catch (Exception ex) {
//            throw new ResourceNotFoundException();
//        }
    }
}