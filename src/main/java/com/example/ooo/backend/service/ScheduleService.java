package com.example.ooo.backend.service;

import com.example.ooo.backend.model.QTodo;
import com.example.ooo.backend.model.Todo;
import com.example.ooo.backend.repository.TodoRepo;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class ScheduleService {

    private final TodoService todoService;
    private final TodoRepo todoRepo;

    public void schedule() throws MessagingException {
        List<Todo> todos = Lists.newArrayList(todoRepo.findAll(QTodo.todo.term.before(LocalDateTime.now())));
        for (int i = 0; i < todos.size(); i++){
            if (!todos.get(i).isAlreadysent()){
                todoService.sendMessage(todos.get(i));
            }
        }
    }
}
