package cz.muni.fi.pa165.projects.library.mvc.controllers;

import cz.muni.fi.pa165.projects.library.dto.BookCreateDTO;
import cz.muni.fi.pa165.projects.library.facade.BookFacade;
import cz.muni.fi.pa165.projects.library.service.config.ServiceConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
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
    private BookFacade bookFacade;

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

    @RequestMapping(value = "/loanability/{id}", method = RequestMethod.POST)
    public String loanability(@PathVariable("id") long bookId, @RequestParam(value="loanable", required = false, defaultValue = "true")
    boolean loanable, UriComponentsBuilder uriBuilder, RedirectAttributes redirectAttributes) {
        if (loanable) {
            redirectAttributes.addFlashAttribute("alert_info", "Book was marked as not loanable.");
        }
        else {
            redirectAttributes.addFlashAttribute("alert_info", "Book was marked as loanable.");
        }
        bookFacade.changeLoanability(bookId);
        return "redirect:" + uriBuilder.path("/book").queryParam("loanable",loanable).toUriString();
    }

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
