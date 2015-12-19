package cz.muni.fi.pa165.projects.library.mvc.controllers;

import cz.muni.fi.pa165.projects.library.dto.MemberDTO;
import cz.muni.fi.pa165.projects.library.dto.NewMemberDTO;
import cz.muni.fi.pa165.projects.library.facade.MemberFacade;
import cz.muni.fi.pa165.projects.library.service.config.ServiceConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.security.access.annotation.Secured;
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

import static cz.muni.fi.pa165.projects.library.mvc.config.security.UserRoles.LIBRARIAN;

/**
 *
 * @author Milan Skipala
 */

@Controller
@Import({ServiceConfiguration.class})
@Secured(LIBRARIAN)
@RequestMapping("/member")
public class MemberController {
    
    @Autowired
    private MemberFacade memberFacade;
    
    @RequestMapping(method = RequestMethod.GET)
    public String home(Model model) {
        return list(model);
    }
    
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("members", memberFacade.getAllMembers());
        return "member/list";
    }
    
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("member", memberFacade.findMemberById(id));
        return "member/detail";
    }
    
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newMember(Model model) {
        model.addAttribute("memberCreate", new NewMemberDTO());
        return "member/new";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute("memberCreate") NewMemberDTO formBean, BindingResult bindingResult,
                         Model model, RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder) {
        if (bindingResult.hasErrors()) {
            for (FieldError fe : bindingResult.getFieldErrors()) {
                model.addAttribute(fe.getField() + "_error", true);
            }
            return "member/new";
        }
        //create product
        Long id = memberFacade.registerMember(formBean);
        //report success
        redirectAttributes.addFlashAttribute("alert_info", "Member " + id + " was created");
        return "redirect:" + uriBuilder.path("/member/list").toUriString();
    }
    
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public String update(@Valid @ModelAttribute("member") MemberDTO formBean, BindingResult bindingResult,
                         Model model, RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder, @PathVariable Long id) {
        if (formBean.getId() == null) {
            formBean.setId(id);
        }
        if (bindingResult.hasErrors()) {
            for (FieldError fe : bindingResult.getFieldErrors()) {
                model.addAttribute(fe.getField() + "_error", true);
            }
            return  "member/detail";
        }
        memberFacade.updateMember(formBean);
        redirectAttributes.addFlashAttribute("alert_info", "Member " + formBean.getGivenName() + " " + formBean.getSurname() +  " was updated");
        return "redirect:" + uriBuilder.path("/member/list").toUriString();
    }
}
