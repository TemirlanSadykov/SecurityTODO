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

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

@Service
@AllArgsConstructor
public class TodoService {

    private final TodoRepo todoRepo;
    private final UserRepo userRepo;

    public void createTodo(TodoForm todoForm, String login) throws NullPointerException{
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

    public void editTodo(TodoForm todoForm) {
        Todo todo = todoRepo.findById(todoForm.getId()).get();
        todo.setName(todoForm.getName());
        todo.setDescription(todoForm.getDescription());
        todo.setStatus(Status.valueOf(todoForm.getStatus()));
        todoRepo.save(todo);
    }

    public Page<TodoDTO> getAll(Pageable pageable, String login) {
        return todoRepo.findAllByUserLogin(pageable, login).map(TodoDTO::from);
    }

    /*public void status(Long id) {
        Todo todo = todoRepo.findById(id).get();
        Status[] statuses = Status.values();
        if (todo.getStatus().equals(Status.values()[statuses.length - 1])) {
            todo.setStatus(Status.values()[0]);
            todoRepo.save(todo);
        } else {
            for (int i = 0; i < statuses.length; i++) {
                if (todo.getStatus().equals(Status.values()[i])) {
                    todo.setStatus(Status.values()[i + 1]);
                    todoRepo.save(todo);
                    break;
                }
            }
        }
    }*/

    public List<Status> getStatus(){
        List<Status> status = new ArrayList<>();
        Collections.addAll(status, Status.values());
        return status;
    }

    public List<Todo> listAll(String login){
        return todoRepo.findAllByUserLogin(login);
    }

    public void delete(Long id) {
        todoRepo.deleteById(id);
    }

    public TodoDTO get(Long id) {
        return todoRepo.findById(id).map(TodoDTO::from).get();
    }

    public List<Todo> findTodo(FindTodoForm findTodoForm, Principal principal) {
        if(findTodoForm.getStatus().equals("all") && findTodoForm.getName().equals("") && findTodoForm.getStartDate().equals("") && findTodoForm.getFinishDate().equals("")) return todoRepo.findAllByUserLogin(principal.getName());
        if(findTodoForm.getStatus().equals("all") && findTodoForm.getName().equals("") && findTodoForm.getStartDate().equals("")) return todoRepo.findAllByDateBeforeAndUserLogin(LocalDateTime.parse(findTodoForm.getFinishDate()), principal.getName());
        if(findTodoForm.getStatus().equals("all") && findTodoForm.getName().equals("") && findTodoForm.getFinishDate().equals("")) return todoRepo.findAllByDateAfterAndUserLogin(LocalDateTime.parse(findTodoForm.getStartDate()), principal.getName());
        if(findTodoForm.getStatus().equals("all") && findTodoForm.getName().equals("")) return todoRepo.findAllByDateBetweenAndUserLogin(
                LocalDateTime.parse(findTodoForm.getStartDate()), LocalDateTime.parse(findTodoForm.getFinishDate()), principal.getName());
        if(findTodoForm.getName().equals("")) return todoRepo.findAllByStatusAndDateBetweenAndUserLogin(Status.valueOf(findTodoForm.getStatus()),
                LocalDateTime.parse(findTodoForm.getStartDate()), LocalDateTime.parse(findTodoForm.getFinishDate()), principal.getName());
        if(findTodoForm.getStatus().equals("all")) return todoRepo.findAllByNameAndDateBetweenAndUserLogin(findTodoForm.getName(),
                LocalDateTime.parse(findTodoForm.getStartDate()), LocalDateTime.parse(findTodoForm.getFinishDate()), principal.getName());

        return todoRepo.findAllByStatusAndNameAndDateBetweenAndUserLogin(
                Status.valueOf(findTodoForm.getStatus()), findTodoForm.getName(),
                LocalDateTime.parse(findTodoForm.getStartDate()), LocalDateTime.parse(findTodoForm.getFinishDate()), principal.getName());
    }
    public void export(HttpServletResponse response, Principal principal)  throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=todos_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List<Todo> todoList = listAll(principal.getName());
        TodoExcelExporterService excelExporter = new TodoExcelExporterService(todoList);
        excelExporter.export(response);
    }
}
