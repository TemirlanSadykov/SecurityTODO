package com.example.ooo.frontend.controller;

import com.example.ooo.backend.repository.UserRepo;
import com.example.ooo.backend.service.UserService;
import com.example.ooo.frontend.forms.UserLoginForm;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping
@AllArgsConstructor
public class LoginController {

    private final UserService userService;

    private final UserRepo userRepo;

    @GetMapping("/default")
    public String defaultPage(@RequestParam(required = false, defaultValue = "false") Boolean error, Model model, Principal principal) {
        model.addAttribute("error", error);

        String login = principal.getName();
        if(userRepo.findByLogin(login).get().getRole().equals("ROLE_ADMIN")){
            return "redirect:/admin";
        }
        else {
            return "redirect:/user";
        }

    }

    @GetMapping("/")
    public String login(Model model, @RequestParam(required = false, defaultValue = "false") Boolean error, Principal principal) {
        model.addAttribute("error", error);
        return "user/login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        return "user/register";
    }

    @PostMapping("/register")
    public String login(@Valid UserLoginForm userLoginForm,
                        BindingResult validationResult, RedirectAttributes attributes){

        if (validationResult.hasFieldErrors()) {
            attributes.addFlashAttribute("errors", validationResult.getFieldErrors());
            return "redirect:/register";
        }

        String user = userService.createUser(userLoginForm);
        if (user == null){
            return "redirect:/";
        }
        else {
            attributes.addFlashAttribute("error", user);
            return "redirect:/register";
        }

    }

}
