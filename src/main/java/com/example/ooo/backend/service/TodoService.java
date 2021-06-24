package com.example.ooo.backend.service;

import com.example.ooo.backend.dto.TodoDTO;
import com.example.ooo.backend.forms.FindTodoForm;
import com.example.ooo.backend.model.Status;
import com.example.ooo.backend.model.Todo;
import com.example.ooo.backend.model.User;
import com.example.ooo.backend.repository.TodoRepo;
import com.example.ooo.backend.repository.UserRepo;
import com.example.ooo.backend.forms.TodoForm;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.thymeleaf.exceptions.TemplateInputException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TodoService {

    private final TodoRepo todoRepo;
    private final UserRepo userRepo;
    private final PredicateService predicateService;

    public void createTodo(TodoForm todoForm, String login) throws NullPointerException {
        User user = userRepo.findByLogin(login).get();

        Todo todo = Todo.builder()
                .date(LocalDateTime.now())
                .name(todoForm.getName())
                .description(todoForm.getDescription())
                .status(Status.NEW)
                .user(user)
                .build();

        todoRepo.save(todo);
    }

    public void editTodo(TodoForm todoForm, Long id)  throws IllegalArgumentException{
        Todo todo = todoRepo.findById(id).get();
        todo.setName(todoForm.getName());
        todo.setDescription(todoForm.getDescription());
        todo.setStatus(Status.valueOf(todoForm.getStatus()));
        todoRepo.save(todo);
    }

    public Page<TodoDTO> getAll(Pageable pageable, String login) {
        return todoRepo.findAllByUserLogin(pageable, login).map(TodoDTO::from);
    }

    public List<Status> getStatus() {
        List<Status> status = new ArrayList<>();
        Collections.addAll(status, Status.values());
        return status;
    }

    public List<Todo> listAll(String login) {
        return todoRepo.findAllByUserLogin(login);
    }

    public void delete(Long id) throws TemplateInputException {
        todoRepo.deleteById(id);
    }

    public TodoDTO get(Long id) {
        return todoRepo.findById(id).map(TodoDTO::from).get();
    }

    public List<Todo> findTodo(FindTodoForm findTodoForm, Principal principal) {
        return predicateService.filterTodo(principal, findTodoForm);
    }

    public void export(HttpServletResponse response, Principal principal, FindTodoForm form) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=todos_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List<Todo> todoList = findTodo(form, principal);
        TodoExcelExporterService excelExporter = new TodoExcelExporterService(todoList);
        excelExporter.export(response);
    }
}
