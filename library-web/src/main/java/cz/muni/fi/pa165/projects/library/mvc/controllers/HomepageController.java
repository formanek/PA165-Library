package cz.muni.fi.pa165.projects.library.mvc.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author jkaspar
 */
@Controller
@RequestMapping("/")
public class HomepageController {

    @RequestMapping(method = RequestMethod.GET)
    public String home(Model model) {
        return "home";
    }
}
