package com.example.ooo.backend.controller;

import com.example.ooo.backend.service.PropertiesService;
import com.example.ooo.backend.service.TodoService;
import com.example.ooo.backend.service.UserService;
import com.example.ooo.backend.util.Constants;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
@RequestMapping
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final TodoService todoService;
    private final PropertiesService propertiesService;

    @GetMapping("/user")
    public String userPage(Model model, Principal principal) {
        model.addAttribute("userName", principal.getName());
        return "user/user";
    }

    @GetMapping("/admin")
    public String adminPage(Model model, Principal principal) {
        model.addAttribute("userName", principal.getName());
        return "user/admin";
    }

    @GetMapping("/admin/users")
    public String adminPageUsers(Model model, Pageable pageable, HttpServletRequest uriBuilder, Principal principal) {
        model.addAttribute("userName", principal.getName());

        var users = userService.findUsersByRole(pageable, Constants.USER);
        String uri = uriBuilder.getRequestURI();
        PropertiesService.constructPageable(users, propertiesService.getDefaultPageSize(), model, uri);

        return "user/adminTool";
    }

    @GetMapping("/admin/users/activate/{id}")
    public String adminPageUsersActivate(@PathVariable Long id) {
        userService.activate(id);

        return "redirect:/admin/users";
    }
}
