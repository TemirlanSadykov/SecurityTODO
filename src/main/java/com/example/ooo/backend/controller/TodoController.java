package com.example.ooo.backend.controller;

import com.example.ooo.backend.forms.FindTodoForm;
import com.example.ooo.backend.forms.TodoForm;
import com.example.ooo.backend.repository.UserRepo;
import com.example.ooo.backend.service.PropertiesService;
import com.example.ooo.backend.service.TodoService;
import com.example.ooo.backend.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.springframework.data.domain.Pageable;
import org.thymeleaf.exceptions.TemplateInputException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/todo")
@AllArgsConstructor
public class TodoController {

    private final PropertiesService propertiesService;
    private final UserService userService;
    private final TodoService todoService;
    private final UserRepo userRepo;

    @GetMapping
    public String createTodo(Model model, Principal principal) {
        model.addAttribute("userName", principal.getName());
        return "todo/createTodo";
    }

    @PostMapping
    public String todo(@Valid TodoForm todoForm,
                       BindingResult validationResult, RedirectAttributes attributes, Principal principal) {

        if (validationResult.hasErrors()) {
            attributes.addFlashAttribute("errors", validationResult.getFieldErrors());
            return "redirect:/todo";
        }

        todoService.createTodo(todoForm, principal.getName());
        return "redirect:/default";
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

    @DeleteMapping("/todos/delete/{id}")
    public void deleteTodo(@PathVariable Long id) throws TemplateInputException {
        todoService.delete(id);
    }

    @GetMapping("/edit/{id}")
    public String editTodo(Model model, Principal principal, @PathVariable Long id) {
        model.addAttribute("userName", principal.getName());
        model.addAttribute("todo", todoService.get(id));
        model.addAttribute("status", todoService.getStatus());

        return "todo/editTodo";
    }

    @PutMapping("/edit/{id}")
    public ModelAndView editTodo(@Valid TodoForm todoForm,
                                 BindingResult validationResult, RedirectAttributes attributes, @PathVariable Long id) throws IllegalArgumentException {
        if (validationResult.hasFieldErrors()) {
            attributes.addFlashAttribute("errors", validationResult.getFieldErrors());
            return new ModelAndView( "redirect:/todo/edit/" + id);
        }
        todoService.editTodo(todoForm, id);
        return new ModelAndView("redirect:/todo/todos");
    }

    @GetMapping("/open/{id}")
    public String openTodo(Model model, Principal principal, @PathVariable Long id) {
        model.addAttribute("userName", principal.getName());
        model.addAttribute("todo", todoService.get(id));

        return "todo/openTodo";
    }

    @GetMapping("/search")
    public String search(Model model, Principal principal) {
        model.addAttribute("userName", principal.getName());
        model.addAttribute("status", todoService.getStatus());

        return "todo/search";
    }

    @PostMapping("/search")
    public String search(FindTodoForm findTodoForm, RedirectAttributes attributes) {
        attributes.addFlashAttribute("todos", todoService.getByDate(findTodoForm));
        attributes.addFlashAttribute("FindTodoForm", findTodoForm);
        return "redirect:/todo/search/find";
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
    public String export(RedirectAttributes attributes, FindTodoForm form) {
        attributes.addFlashAttribute("FindTodoForm", form);
        return "redirect:/todo/export";
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, Principal principal, @ModelAttribute("FindTodoForm") FindTodoForm form) throws IOException {
        todoService.export(response, principal, form);
    }
}
