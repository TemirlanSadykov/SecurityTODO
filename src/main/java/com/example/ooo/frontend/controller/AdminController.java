package com.example.ooo.frontend.controller;

import com.example.ooo.backend.service.PropertiesService;
import com.example.ooo.backend.service.TodoService;
import com.example.ooo.backend.service.UserService;
import com.example.ooo.frontend.forms.TodoForm;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {

    private final UserService userService;
    private final PropertiesService propertiesService;
    private final TodoService todoService;

    @GetMapping
    public String adminPage(Model model, Principal principal) {
        model.addAttribute("userName", principal.getName());
        return "admin/admin";
    }

    @GetMapping("/users")
    public String adminPageUsers(Model model, Pageable pageable, HttpServletRequest uriBuilder, Principal principal) {
        model.addAttribute("userName", principal.getName());

        var users = userService.findUsersByRole(pageable);
        String uri = uriBuilder.getRequestURI();
        PropertiesService.constructPageable(users, propertiesService.getDefaultPageSize(), model, uri);

        return "admin/adminTool";
    }

    @GetMapping("/users/activate/{id}")
    public String adminPageUsersActivate(@PathVariable Long id) {
        userService.activate(id);

        return "redirect:/admin/users";
    }

    @GetMapping("/todo")
    public String createTodo(Model model, Principal principal) {
        model.addAttribute("userName", principal.getName());

        return "admin/createAdminTodo";
    }

    @PostMapping("/todo")
    public String todo(@Valid TodoForm todoForm, Principal principal,
                       BindingResult validationResult, RedirectAttributes attributes) {

        if (validationResult.hasFieldErrors()) {
            attributes.addFlashAttribute("errors", validationResult.getFieldErrors());
            return "redirect:/admin/todo";
        }

        todoService.createTodo(todoForm, principal.getName());
        return "redirect:/admin";

    }

    @GetMapping("/todos")
    public String getTodos(Model model, Pageable pageable, HttpServletRequest uriBuilder, Principal principal) {
        model.addAttribute("userName", principal.getName());

        var todos = todoService.getAll(pageable, principal.getName());
        String uri = uriBuilder.getRequestURI();
        PropertiesService.constructPageable(todos, propertiesService.getDefaultPageSize(), model, uri);

        return "admin/getTodos";
    }

    @GetMapping("/todo/status/{id}")
    public String changeTodoStatus(@PathVariable Long id) {
        todoService.status(id);

        return "redirect:/admin/todos";
    }

    @GetMapping("/todo/delete/{id}")
    public String deleteTodo(@PathVariable Long id) {
        todoService.delete(id);

        return "redirect:/admin/todos";
    }

    @GetMapping("/todo/edit/{id}")
    public String editTodo(Model model, Principal principal, @PathVariable Long id) {
        model.addAttribute("userName", principal.getName());
        model.addAttribute("todo", todoService.get(id));

        return "admin/editTodo";
    }

    @PostMapping("/todo/edit")
    public String editTodo(@Valid TodoForm todoForm,
                           BindingResult validationResult, RedirectAttributes attributes) {

        if (validationResult.hasFieldErrors()) {
            attributes.addFlashAttribute("errors", validationResult.getFieldErrors());
            return "redirect:/admin/todo/edit/" + todoForm.getId();
        }

        todoService.editTodo(todoForm);
        return "redirect:/admin/todos";

    }

    @GetMapping("/todo/open/{id}")
    public String openTodo(Model model, Principal principal, @PathVariable Long id) {
        model.addAttribute("userName", principal.getName());
        model.addAttribute("todo", todoService.get(id));

        return "openTodo";
    }
}
