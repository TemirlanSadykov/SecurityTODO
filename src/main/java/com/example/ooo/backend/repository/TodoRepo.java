package com.example.ooo.backend.repository;

import com.example.ooo.backend.model.QTodo;
import com.example.ooo.backend.model.Status;
import com.example.ooo.backend.model.Todo;
import com.example.ooo.backend.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Transactional
public interface TodoRepo extends ExCustomRepository<Todo, QTodo, Long> {
    Page<Todo> findAllByUserLogin(Pageable pageable, String login);

    List<Todo> findAllByDateBeforeAndUserLogin(LocalDateTime finish, String login);
    List<Todo> findAllByDateAfterAndUserLogin(LocalDateTime start, String login);

    List<Todo> findAllByUserLogin(String login);
    List<Todo> findAllByNameAndUserLogin(String name, String login);
    List<Todo> findAllByStatusAndUserLogin(Status status, String login);

    @Override
    default void customize(QuerydslBindings querydslBindings, QTodo qTodo) {
        querydslBindings.bind(qTodo.name).first(((stringPath, s) -> qTodo.name.containsIgnoreCase(s)));
    }
}
