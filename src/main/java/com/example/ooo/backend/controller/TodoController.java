package com.example.ooo.backend.controller;

import com.example.ooo.backend.forms.FindTodoForm;
import com.example.ooo.backend.repository.UserRepo;
import com.example.ooo.backend.service.TodoService;
import com.example.ooo.backend.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.springframework.data.domain.Pageable;
import java.security.Principal;

@Controller
@RequestMapping("/user/todo")
@AllArgsConstructor
public class TodoController {

    private final UserService userService;
    private final TodoService todoService;
    private final UserRepo userRepo;

    @GetMapping("/search")
    public String search(Model model, Principal principal) {
        model.addAttribute("userName", principal.getName());
        model.addAttribute("status", todoService.getStatus());
        return "user/search";
    }

    @PostMapping("/search")
    public String search(FindTodoForm findTodoForm, Pageable pageable, RedirectAttributes attributes) {
        attributes.addFlashAttribute("items", todoService.findTodo(pageable, findTodoForm));
        return "redirect:/user/todo/search/find";
    }

    @GetMapping("/search/find")
    public String searchFind(Model model, Principal principal) {
        model.addAttribute("userName", principal.getName());
        return "user/searchFind";
    }
}
