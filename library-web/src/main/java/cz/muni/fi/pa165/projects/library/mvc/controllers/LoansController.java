package cz.muni.fi.pa165.projects.library.mvc.controllers;

import cz.muni.fi.pa165.projects.library.dto.*;
import cz.muni.fi.pa165.projects.library.facade.BookFacade;
import cz.muni.fi.pa165.projects.library.facade.LoanFacade;
import cz.muni.fi.pa165.projects.library.facade.MemberFacade;
import cz.muni.fi.pa165.projects.library.service.config.ServiceConfiguration;
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

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

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

    @Autowired
    MemberFacade memberFacade;

    @Autowired
    BookFacade bookFacade;

    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("loans", loanFacade.findAll());
        return "loan/list";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{memberId}")
    public String listOfMember(@PathVariable long memberId, Model model) {
        model.addAttribute("member", memberFacade.findMemberById(memberId));
        model.addAttribute("loans", loanFacade.findLoansOfMember(memberId));
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
            String[] conditionParam = req.getParameterMap().get("condition" + loanItem.getBook().getId());
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

    @RequestMapping(value = "/new/{memberId}", method = RequestMethod.GET)
    public String newLoan(@PathVariable long memberId, Model model, RedirectAttributes redirectAttributes,
            UriComponentsBuilder uriBuilder) {

        MemberDTO member = memberFacade.findMemberById(memberId);
        if (member == null) {
            redirectAttributes.addFlashAttribute("alert_danger", "Invalid member ID");
            return "redirect:" + uriBuilder.path("/loan").toUriString();
        }
        model.addAttribute("member", member);

        List<BookDTO> availableBooks = new LinkedList<>();
        List<BookDTO> allBooks = bookFacade.getAllLoanableBooks();
        for (BookDTO book : allBooks) {
            if (bookFacade.isBookAvailable(book.getId())) {
                availableBooks.add(book);
            }
        }

        model.addAttribute("books", availableBooks);
        return "loan/new";
    }

    @RequestMapping(value = "/new/{memberId}", method = RequestMethod.POST)
    public String create(@PathVariable long memberId, Model model, UriComponentsBuilder uriBuilder,
                         RedirectAttributes redirectAttributes, HttpServletRequest req) {

        MemberDTO member = memberFacade.findMemberById(memberId);
        if (member == null) {
            redirectAttributes.addFlashAttribute("alert_danger", "Invalid member ID");
            return "redirect:" + uriBuilder.path("/loan").toUriString();
        }

        if (req.getParameterValues("requiredBooks") == null) {
            redirectAttributes.addFlashAttribute("alert_danger", "No books selected");
            return "redirect:" + uriBuilder.path("/loan/new/" + memberId).toUriString();
        }

        Set<LoanItemCreateDTO> loanItems = new HashSet<>();
        for (String bookId : req.getParameterValues("requiredBooks")) {
            String condition = req.getParameter("condition" + bookId);
            if (condition == null || !isValidBookCondition(condition)) {
                redirectAttributes.addFlashAttribute("alert_danger",
                        "Invalid or missing condition for book with id " + bookId);
                return "redirect:" + uriBuilder.path("/loan/new/" + memberId).toUriString();
            }

            LoanItemCreateDTO loanItem = new LoanItemCreateDTO();
            loanItem.setBookId(Long.valueOf(bookId));
            loanItem.setConditionBefore(BookCondition.valueOf(condition));
            loanItems.add(loanItem);
        }

        LoanCreateDTO loanCreateDTO = new LoanCreateDTO();
        loanCreateDTO.setMember(member);
        loanCreateDTO.setLoanItems(loanItems);

        Long loanId = loanFacade.loan(loanCreateDTO);
        return "redirect:" + uriBuilder.path("/loan/detail/" + loanId).toUriString();
    }
    
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
