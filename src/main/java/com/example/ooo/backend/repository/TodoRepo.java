package com.example.ooo.backend.repository;

import com.example.ooo.backend.model.Status;
import com.example.ooo.backend.model.Todo;
import com.example.ooo.backend.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Transactional
public interface TodoRepo extends JpaRepository<Todo, Long> {
    Page<Todo> findAllByUserLogin(Pageable pageable, String login);

    List<Todo> findAllByStatusAndNameAndDateBetweenAndUserLogin(Status status, String name, LocalDateTime start, LocalDateTime finish, String login);
    List<Todo> findAllByDateBeforeAndUserLogin(LocalDateTime finish, String login);
    List<Todo> findAllByDateAfterAndUserLogin(LocalDateTime start, String login);
    List<Todo> findAllByDateBetweenAndUserLogin(LocalDateTime start, LocalDateTime finish, String login);
    List<Todo> findAllByNameAndDateBetweenAndUserLogin(String name, LocalDateTime start, LocalDateTime finish, String login);
    List<Todo> findAllByStatusAndDateBetweenAndUserLogin(Status status, LocalDateTime start, LocalDateTime finish, String login);

    List<Todo> findAllByUserLogin(String login);
}
