package cz.muni.fi.pa165.projects.library.mvc.controllers;

import cz.muni.fi.pa165.projects.library.dto.BookCondition;
import cz.muni.fi.pa165.projects.library.facade.LoanFacade;
import cz.muni.fi.pa165.projects.library.service.config.ServiceConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author David Formanek
 */
@Controller
@Import({ServiceConfiguration.class})
@RequestMapping("/loan")
public class LoansController {

    @Autowired
    LoanFacade loanFacade;
    
    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("loans", loanFacade.findAll());
        return "loan/list";
    }
    
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String detail(@PathVariable long id, Model model) {
        model.addAttribute("loan", loanFacade.findLoanById(id));
        return "loan/detail";
    }
    
    @RequestMapping(value = "/return/{id}", method = RequestMethod.GET)
    public String returning(@PathVariable long id, Model model) {
        model.addAttribute("loan", loanFacade.findLoanById(id));
        return "loan/return";
    }
    
    // TODO loan return - process form
    
    // TODO loan creation
    
    @ModelAttribute("conditions")
    public BookCondition[] categories() {
        return BookCondition.values();
    }
}
