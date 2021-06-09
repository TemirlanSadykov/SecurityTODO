package com.example.ooo.backend.repository;

import com.example.ooo.backend.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.management.relation.Role;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByLogin(String login);

    Page<User> findUsersByRoleName(Pageable pageable, String name);
    boolean existsByLoginAndRoleId(String login, Long id);
}
