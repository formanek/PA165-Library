package cz.muni.fi.pa165.projects.library.mvc.controllers;

import cz.muni.fi.pa165.projects.library.dto.BookCreateDTO;
import cz.muni.fi.pa165.projects.library.facade.BookFacade;
import cz.muni.fi.pa165.projects.library.service.config.ServiceConfiguration;
import cz.muni.fi.pa165.projects.library.service.facade.BookFacadeImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

/**
 * @author Jan Mosat
 */
@Controller
@Import({ServiceConfiguration.class})
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookFacade bookFacade = new BookFacadeImpl();

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newBook(Model model) {
        model.addAttribute("bookCreate", new BookCreateDTO());
        return "book/new";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute("bookCreate") BookCreateDTO formBean, BindingResult bindingResult,
                         Model model, RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder) {
        if (bindingResult.hasErrors()) {
            for (FieldError fe : bindingResult.getFieldErrors()) {
                model.addAttribute(fe.getField() + "_error", true);
            }
            return "book/new";
        }
        Long id = bookFacade.addBook(formBean);
        redirectAttributes.addFlashAttribute("alert_info", "Book " + id + " was created");
        return "redirect:" + uriBuilder.path("/book").toUriString();
    }

    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String unprocessed(@PathVariable long id, Model model) {
        model.addAttribute("available", bookFacade.isBookAvailable(id));
        model.addAttribute("book", bookFacade.findBookById(id));
        return "book/detail";
    }

    @RequestMapping(value = "/remove/{id}", method = RequestMethod.POST)
    public String removeAd(@PathVariable("id") long bookId, UriComponentsBuilder uriBuilder, RedirectAttributes redirectAttributes) {
        bookFacade.deleteBook(bookId);
        redirectAttributes.addFlashAttribute("alert_info", "Book " + bookId + " was deleted.");
        return "redirect:" + uriBuilder.path("/book").toUriString();
    }

    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("books", bookFacade.getAllBooks());
        return "book/list";
    }

}
