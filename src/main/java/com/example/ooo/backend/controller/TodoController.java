package com.example.ooo.backend.controller;

import com.example.ooo.backend.forms.FindTodoForm;
import com.example.ooo.backend.forms.TodoForm;
import com.example.ooo.backend.repository.UserRepo;
import com.example.ooo.backend.service.PropertiesService;
import com.example.ooo.backend.service.TodoService;
import com.example.ooo.backend.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;

@Controller
@RequestMapping(value={"/user/todo", "/admin/todo"})
@AllArgsConstructor
public class TodoController {

    private final PropertiesService propertiesService;
    private final UserService userService;
    private final TodoService todoService;
    private final UserRepo userRepo;

    @GetMapping
    public String createTodo(Model model, Principal principal) {
        model.addAttribute("userName", principal.getName());
        model.addAttribute("link", userService.checkUserRole(principal));

        return "todo/createTodo";
    }

    @PostMapping
    public String todo(@Valid TodoForm todoForm,
                       BindingResult validationResult, RedirectAttributes attributes, Principal principal) {

        if (validationResult.hasErrors()) {
            attributes.addFlashAttribute("errors", validationResult.getFieldErrors());
            return "redirect:/"+userService.checkUserRole(principal)+"/todo";
        }

        todoService.createTodo(todoForm, principal.getName());
        return "redirect:/"+userService.checkUserRole(principal);
    }

    @GetMapping("/todos")
    public String getTodos(Model model, Pageable pageable, HttpServletRequest uriBuilder, Principal principal) {
        model.addAttribute("userName", principal.getName());
        model.addAttribute("link", userService.checkUserRole(principal));

        var todos = todoService.getAll(pageable, principal.getName());
        String uri = uriBuilder.getRequestURI();
        PropertiesService.constructPageable(todos, propertiesService.getDefaultPageSize(), model, uri);

        return "todo/getTodos";
    }

    @GetMapping("/delete/{id}")
    public String deleteTodo(@PathVariable Long id, Principal principal) {
        todoService.delete(id);

        return "redirect:/"+userService.checkUserRole(principal)+"/todo/todos";
    }

    @GetMapping("/edit/{id}")
    public String editTodo(Model model, Principal principal, @PathVariable Long id) {
        model.addAttribute("userName", principal.getName());
        model.addAttribute("todo", todoService.get(id));
        model.addAttribute("status", todoService.getStatus());
        model.addAttribute("link", userService.checkUserRole(principal));

        return "todo/editTodo";
    }

    @PostMapping("/edit")
    public String editTodo(@Valid TodoForm todoForm,
                           BindingResult validationResult, RedirectAttributes attributes, Principal principal) {
        if (validationResult.hasFieldErrors()) {
            attributes.addFlashAttribute("errors", validationResult.getFieldErrors());
            return "redirect:/"+userService.checkUserRole(principal)+"/todo/edit/" + todoForm.getId();
        }

        todoService.editTodo(todoForm);
        return "redirect:/"+userService.checkUserRole(principal)+"/todo/todos";
    }

    @GetMapping("/open/{id}")
    public String openTodo(Model model, Principal principal, @PathVariable Long id) {
        model.addAttribute("userName", principal.getName());
        model.addAttribute("todo", todoService.get(id));
        model.addAttribute("link", userService.checkUserRole(principal));

        return "todo/openTodo";
    }
    @GetMapping("/search")
    public String search(Model model, Principal principal) {
        model.addAttribute("userName", principal.getName());
        model.addAttribute("status", todoService.getStatus());
        model.addAttribute("link", userService.checkUserRole(principal));

        return "todo/search";
    }

    @PostMapping("/search")
    public String search(FindTodoForm findTodoForm, RedirectAttributes attributes, Principal principal) {
        attributes.addFlashAttribute("items", todoService.findTodo(findTodoForm, principal));
        attributes.addFlashAttribute("FindTodoForm", findTodoForm);
        return "redirect:/"+userService.checkUserRole(principal)+"/todo/search/find";
    }

    @GetMapping("/search/find")
    public String searchFind(Model model, Principal principal, @ModelAttribute("FindTodoForm") FindTodoForm form) {
        model.addAttribute("userName", principal.getName());
        model.addAttribute("link", userService.checkUserRole(principal));
        model.addAttribute("name", form.getName());
        model.addAttribute("status", form.getStatus());
        model.addAttribute("startDate", form.getStartDate());
        model.addAttribute("finishDate", form.getFinishDate());

        return "todo/searchFind";
    }
    @PostMapping("/export")
    public String export(RedirectAttributes attributes, Principal principal, FindTodoForm form){
        attributes.addFlashAttribute("FindTodoForm", form);
        return "redirect:/"+userService.checkUserRole(principal)+"/todo/export";
    }
    @GetMapping("/export")
    public void export(HttpServletResponse response, Principal principal, @ModelAttribute("FindTodoForm") FindTodoForm form) throws IOException {
        todoService.export(response, principal, form);
    }
}
