package com.example.ooo.backend.controller;

import com.example.ooo.backend.service.PropertiesService;
import com.example.ooo.backend.service.TodoService;
import com.example.ooo.backend.service.UserService;
import com.example.ooo.backend.forms.TodoForm;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final TodoService todoService;
    private final PropertiesService propertiesService;

    @GetMapping
    public String userPage(Model model, Principal principal) {
        model.addAttribute("userName", principal.getName());
        return "user/user";
    }

    @GetMapping("/todo")
    public String createTodo(Model model, Principal principal) {
        model.addAttribute("userName", principal.getName());

        return "user/createUserTodo";
    }

    @PostMapping("/todo")
    public String todo(@Valid TodoForm todoForm, Principal principal,
                       BindingResult validationResult, RedirectAttributes attributes) {

        if (validationResult.hasFieldErrors()) {
            attributes.addFlashAttribute("errors", validationResult.getFieldErrors());
            return "redirect:/user/todo";
        }

        todoService.createTodo(todoForm, principal.getName());
        return "redirect:/user";
    }

    @GetMapping("/todos")
    public String getTodos(Model model, Pageable pageable, HttpServletRequest uriBuilder, Principal principal) {
        model.addAttribute("userName", principal.getName());

        var todos = todoService.getAll(pageable, principal.getName());
        String uri = uriBuilder.getRequestURI();
        PropertiesService.constructPageable(todos, propertiesService.getDefaultPageSize(), model, uri);

        return "user/getTodos";
    }

    @GetMapping("/todo/delete/{id}")
    public String deleteTodo(@PathVariable Long id) {
        todoService.delete(id);

        return "redirect:/user/todos";
    }

    @GetMapping("/todo/edit/{id}")
    public String editTodo(Model model, Principal principal, @PathVariable Long id) {
        model.addAttribute("userName", principal.getName());
        model.addAttribute("todo", todoService.get(id));
        model.addAttribute("status", userService.getStatus());

        return "user/editTodo";
    }

    @PostMapping("/todo/edit")
    public String editTodo(@Valid TodoForm todoForm,
                           BindingResult validationResult, RedirectAttributes attributes) {
        if (validationResult.hasFieldErrors()) {
            attributes.addFlashAttribute("errors", validationResult.getFieldErrors());
            return "redirect:/user/todo/edit/" + todoForm.getId();
        }

        todoService.editTodo(todoForm);
        return "redirect:/user/todos";
    }

    @GetMapping("/todo/open/{id}")
    public String openTodo(Model model, Principal principal, @PathVariable Long id) {
        model.addAttribute("userName", principal.getName());
        model.addAttribute("todo", todoService.get(id));

        return "openTodo";
    }
}
