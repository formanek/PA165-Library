package cz.muni.fi.pa165.projects.library.mvc.controllers;

import cz.muni.fi.pa165.projects.library.facade.MemberFacade;
import cz.muni.fi.pa165.projects.library.dto.NewMemberDTO;
import cz.muni.fi.pa165.projects.library.service.config.ServiceConfiguration;
import cz.muni.fi.pa165.projects.library.service.facade.MemberFacadeImpl;
import javax.inject.Inject;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;
/**
 *
 * @author Milan Skipala
 */

@Controller
@Import({ServiceConfiguration.class})
@RequestMapping("/member")
public class MemberController {
    
    @Autowired
    private MemberFacade memberFacade;
    
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
    
    /*@RequestMapping(value = "/detail/{email}", method = RequestMethod.GET)
    public String detail(@PathVariable String email, Model model) {
        model.addAttribute("member", memberFacade.findMemberByEmail(email));
        return "member/detail";
    }*/
    
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newProduct(Model model) {
        model.addAttribute("memberCreate", new NewMemberDTO());
        return "member/new";
    }
    
    
    
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute("productCreate") NewMemberDTO formBean, BindingResult bindingResult,
                         Model model, RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder) {
        //create product
        Long id = memberFacade.registerMember(formBean);
        //report success
        redirectAttributes.addFlashAttribute("alert_success", "Product " + id + " was created");
        return "redirect:" + uriBuilder.path("/member/detail/{id}").buildAndExpand(id).encode().toUriString();
    }
    /*
    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String update(@PathVariable Long id, Model model) {
        model.addAttribute("member", memberFacade.findMemberById(id));
        return "member/new";
    }*/
}
