package cz.muni.fi.pa165.projects.library.mvc.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author jkaspar
 */
@Controller
@RequestMapping("/")
public class HomepageController {

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.GET, value = {"", "/home"})
    public String home(Model model) {
        return "home";
    }
    
    @RequestMapping(value = "error/{code}")
    public String error(@PathVariable String code, Model model) {
        model.addAttribute("code", code);
        return "error";
    }
}
