package sec.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import sec.project.repository.SignupRepository;

@Controller
public class AdminController {

    @Autowired
    private SignupRepository signupRepository;

    @RequestMapping(method = RequestMethod.GET, value = "/admin")
    public String reviewSignups(Model model) {
        model.addAttribute("signups", signupRepository.findAll());
        return "admin";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/signups/delete/{id}")
    public String deleteSignup(@PathVariable Long id) {
        signupRepository.delete(id);
        return "redirect:/admin";
    }


}
