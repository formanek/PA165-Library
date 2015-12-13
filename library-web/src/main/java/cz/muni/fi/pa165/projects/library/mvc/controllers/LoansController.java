package cz.muni.fi.pa165.projects.library.mvc.controllers;

import cz.muni.fi.pa165.projects.library.dto.BookCondition;
import cz.muni.fi.pa165.projects.library.dto.LoanItemDTO;
import cz.muni.fi.pa165.projects.library.dto.ReturnLoanDTO;
import cz.muni.fi.pa165.projects.library.dto.ReturnLoanItemDTO;
import cz.muni.fi.pa165.projects.library.facade.LoanFacade;
import cz.muni.fi.pa165.projects.library.service.config.ServiceConfiguration;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

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
    
    @RequestMapping(value = "/return/{id}", method = RequestMethod.POST)
    public String processReturn(@PathVariable long id, Model model, UriComponentsBuilder uriBuilder,
            RedirectAttributes redirectAttributes, HttpServletRequest req) {
        Set<LoanItemDTO> loanItems = loanFacade.findLoanById(id).getLoanItems();
        ReturnLoanDTO returnLoanDTO = new ReturnLoanDTO();
        returnLoanDTO.setLoanId(id);
        Set<ReturnLoanItemDTO> loanItemDTOs = new HashSet<>();
        for (LoanItemDTO loanItem : loanItems) {
            String[] conditionParam = req.getParameterMap().get("condition" + loanItem.getId());
            if (conditionParam == null || conditionParam.length != 1
                    || !isValidBookCondition(conditionParam[0])) {
                // this should never happen if request is not tampered
                redirectAttributes.addFlashAttribute("alert_danger", "invalid request");
                return "redirect:" + uriBuilder.path("/loan/return/" + id).toUriString();
            }
            ReturnLoanItemDTO returnLoanItemDTO = new ReturnLoanItemDTO();
            returnLoanItemDTO.setLoanItemId(loanItem.getId());
            returnLoanItemDTO.setCondition(BookCondition.valueOf(conditionParam[0]));
            loanItemDTOs.add(returnLoanItemDTO);
        }
        returnLoanDTO.setLoanItems(loanItemDTOs);
        loanFacade.returnLoan(returnLoanDTO);
        redirectAttributes.addFlashAttribute("alert_info", "loan " + id + " returned successfully");
        return "redirect:" + uriBuilder.path("/loan").toUriString();
    }
    
    // TODO loan creation
    
    @ModelAttribute("conditions")
    public BookCondition[] conditions() {
        return BookCondition.values();
    }
    
    private boolean isValidBookCondition(String condition) {
        for (BookCondition bookCondition : conditions()) {
            if (bookCondition.name().equals(condition)) {
                return true;
            }
        }
        return false;
    }
}
