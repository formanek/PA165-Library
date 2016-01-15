package cz.muni.fi.pa165.projects.library.mvc.controllers;

import cz.muni.fi.pa165.projects.library.dto.BookCreateDTO;
import cz.muni.fi.pa165.projects.library.dto.BookDTO;
import cz.muni.fi.pa165.projects.library.facade.BookFacade;
import cz.muni.fi.pa165.projects.library.service.config.ServiceConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

import static cz.muni.fi.pa165.projects.library.mvc.config.security.UserRoles.ADMIN;
import static cz.muni.fi.pa165.projects.library.mvc.config.security.UserRoles.LIBRARIAN;

/**
 * SpringMVC Controller for book administration
 * @author Jan Mosat
 */
@Controller
@Import({ServiceConfiguration.class})
@Secured(ADMIN)
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookFacade bookFacade;

    /**
     * Prepares form for book creation
     * @param model data to display
     * @return JSP page
     */
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newBook(Model model) {
        model.addAttribute("bookCreate", new BookCreateDTO());
        return "book/new";
    }

    /**
     * Creates a new book
     * @param model data to display
     * @return JSP page
     */
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
        redirectAttributes.addFlashAttribute("alert_info", "Book " + formBean.getTitle() + " was created");
        return "redirect:" + uriBuilder.path("/book").toUriString();
    }

    /**
     * Shows one specified books
     * @param id of book
     * @param model data to display
     * @return JSP page
     */
    @Secured({ADMIN, LIBRARIAN})
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String detail(@PathVariable long id, Model model, RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder) {
        try {
            model.addAttribute("book", bookFacade.findBookById(id));
            model.addAttribute("available", bookFacade.isBookAvailable(id));
        }
        catch (Exception e) {
            redirectAttributes.addFlashAttribute("alert_danger", "Book " + id + " was not found.");
            return "redirect:" + uriBuilder.path("/book").toUriString();
        }
        return "book/detail";
    }

    /**
     * Switches loanable attribute for book (Removes from books which library lends)
     * @param id of book
     * @param loanable bool if book was marked as loanable
     * @return JSP page
     */
    @RequestMapping(value = "/loanability/{id}", method = RequestMethod.POST)
    public String loanability(@PathVariable("id") long id, @RequestParam(value="loanable", required = false, defaultValue = "true")
    boolean loanable, UriComponentsBuilder uriBuilder, RedirectAttributes redirectAttributes) {
        BookDTO book = bookFacade.findBookById(id);
        if (loanable) {
            redirectAttributes.addFlashAttribute("alert_info", "Book " + book.getTitle() + " was marked as not loanable.");
        }
        else {
            redirectAttributes.addFlashAttribute("alert_info", "Book " + book.getTitle() + " was marked as loanable.");
        }
        bookFacade.changeLoanability(id, !book.getLoanable());
        return "redirect:" + uriBuilder.path("/book").queryParam("loanable",loanable).toUriString();
    }

    /**
     * Shows a list of books which library has access to
     * @param model data to display
     * @param loanable bool if loanable items should be displayed
     * @return JSP page
     */
    @Secured({ADMIN, LIBRARIAN})
    @RequestMapping()
    public String list(@RequestParam(value="loanable", required = false, defaultValue = "true")
    boolean loanable, Model model) {
        if (loanable) {
            model.addAttribute("books", bookFacade.getAllLoanableBooks());
        }
        else {
            model.addAttribute("books", bookFacade.getAllUnloanableBooks());
        }
        return "book/list";
    }
}
