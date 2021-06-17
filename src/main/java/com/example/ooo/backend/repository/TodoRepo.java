package com.example.ooo.backend.repository;

import com.example.ooo.backend.model.Status;
import com.example.ooo.backend.model.Todo;
import com.example.ooo.backend.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Transactional
public interface TodoRepo extends JpaRepository<Todo, Long> {
    Page<Todo> findAllByUserLogin(Pageable pageable, String login);
    List<Todo> findAllByStatusAndNameAndDateBetween(Status status, String name, Date start, Date finish);
    List<Todo> findAllBy(Pageable pageable, Status status, String name, Date start, Date finish);

}
