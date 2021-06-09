package com.example.ooo.backend.service;

import com.example.ooo.backend.DTO.TodoDTO;
import com.example.ooo.backend.DTO.UserDTO;
import com.example.ooo.backend.model.Status;
import com.example.ooo.backend.model.Todo;
import com.example.ooo.backend.model.User;
import com.example.ooo.backend.repository.TodoRepo;
import com.example.ooo.backend.repository.UserRepo;
import com.example.ooo.frontend.forms.TodoForm;
import com.example.ooo.frontend.forms.UserLoginForm;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

@Service
@AllArgsConstructor
public class TodoService {

    private final TodoRepo todoRepo;
    private final UserRepo userRepo;

    public void createTodo(TodoForm todoForm, String login) {
        Todo todo = Todo.builder()
                .date(Calendar.getInstance().getTime())
                .name(todoForm.getName())
                .description(todoForm.getDescription())
                .status(Status.NEW)
                .user(userRepo.findByLogin(login).get())
                .build();

        todoRepo.save(todo);
    }

    public void editTodo(TodoForm todoForm) {
        Todo todo = todoRepo.findById(todoForm.getId()).get();
        todo.setName(todoForm.getName());
        todo.setDescription(todoForm.getDescription());
        todoRepo.save(todo);
    }

    public Page<TodoDTO> getAll(Pageable pageable, String login) {
        return todoRepo.findAllByUserLogin(pageable, login).map(TodoDTO::from);
    }

    public void status(Long id) {
        Todo todo = todoRepo.findById(id).get();
        Status[] statuses = Status.values();
        if (todo.getStatus().equals(Status.values()[statuses.length-1])){
            todo.setStatus(Status.values()[0]);
            todoRepo.save(todo);
        }
        else {
            for (int i = 0; i < statuses.length; i++){
                if (todo.getStatus().equals(Status.values()[i])) {
                    todo.setStatus(Status.values()[i+1]);
                    todoRepo.save(todo);
                    break;
                }
            }
        }

    }

    public void delete(Long id) {
        todoRepo.deleteById(id);
    }

    public TodoDTO get(Long id) {
        return todoRepo.findById(id).map(TodoDTO::from).get();
    }
}
