package com.example.ooo.backend.service;

import com.example.ooo.backend.dto.TodoDTO;
import com.example.ooo.backend.forms.FindTodoForm;
import com.example.ooo.backend.model.*;
import com.example.ooo.backend.repository.TodoRepo;
import com.example.ooo.backend.repository.UserRepo;
import com.example.ooo.backend.forms.TodoForm;
import com.example.ooo.backend.util.Constants;
import com.google.common.collect.Lists;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.thymeleaf.exceptions.TemplateInputException;

import javax.mail.*;
import javax.mail.Session;
import javax.mail.internet.*;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
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
    private final Parser parser;

    public void createTodo(TodoForm todoForm, String login) throws NullPointerException {
        User user = userRepo.findByLogin(login).get();

        Todo todo = Todo.builder()
                .date(LocalDateTime.now())
                .name(todoForm.getName())
                .description(todoForm.getDescription())
                .status(Status.NEW)
                .user(user)
                .term(LocalDateTime.parse(todoForm.getTerm()))
                .alreadysent(false)
                .build();

        todoRepo.save(todo);
    }

    @Transactional
    public List<Todo> findTodo(FindTodoForm findTodoForm, Principal principal) {
        QTodo qTodo = QTodo.todo;

        if (findTodoForm.getStatus().equals("all")){
            return Lists.newArrayList(
                    todoRepo.findAll(qTodo.user.login.eq(principal.getName()).and(
                            qTodo.date.between(parser.localDateTimeParser(findTodoForm.getStartDate()), parser.localDateTimeParser(findTodoForm.getFinishDate()))
                            .and(qTodo.name.eq(findTodoForm.getName())).or(qTodo.date.between(parser.localDateTimeParser(findTodoForm.getStartDate()), parser.localDateTimeParser(findTodoForm.getFinishDate()))))));
        }
        return Lists.newArrayList(
                todoRepo.findAll(qTodo.user.login.eq(principal.getName()).and(
                        qTodo.date.between(parser.localDateTimeParser(findTodoForm.getStartDate()), parser.localDateTimeParser(findTodoForm.getFinishDate()))
                        .andAnyOf(qTodo.name.eq(findTodoForm.getName()), qTodo.status.eq(Status.valueOf(findTodoForm.getStatus()))))));
    }

    public void editTodo(TodoForm todoForm, Long id)  throws IllegalArgumentException{
        Todo todo = todoRepo.findById(id).get();
        todo.setName(todoForm.getName());
        todo.setDescription(todoForm.getDescription());
        todo.setStatus(Status.valueOf(todoForm.getStatus()));
        todo.setTerm(LocalDateTime.parse(todoForm.getTerm()));
        todo.setAlreadysent(false);
        todoRepo.save(todo);
    }

    public Page<TodoDTO> getAllByLogin(Pageable pageable, String login) {
        return todoRepo.findAllByUserLogin(pageable, login).map(TodoDTO::from);
    }

    public List<Status> getStatus() {
        List<Status> status = new ArrayList<>();
        Collections.addAll(status, Status.values());
        return status;
    }

    public void delete(Long id) throws TemplateInputException {
        todoRepo.deleteById(id);
    }

    public TodoDTO get(Long id) {
        return todoRepo.findById(id).map(TodoDTO::from).get();
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

    public void sendMessage(Todo todo) throws MessagingException{

        // Recipient's email ID needs to be mentioned.
        String to = todo.getUser().getEmail();

        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        properties.put("mail.smtp.starttls.enable", "true");


        // Get the default Session object.
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(Constants.SENDER, Constants.SENDER_PASSWORD);
            }
        });

            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(Constants.SENDER));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject("TODO");

            // Now set the actual message
            message.setText("Вы просрочили свое задание: " + todo.getName() + " - " + todo.getTerm());
            // Send message
            Transport.send(message);
            todo.setAlreadysent(true);
            todoRepo.save(todo);
    }
}
