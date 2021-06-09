package com.example.ooo.backend.repository;

import com.example.ooo.backend.model.Role;
import com.example.ooo.backend.model.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

@Transactional
public interface RoleRepo extends JpaRepository<Role, Long> {
    @Modifying
    @Query(value = "insert into roles (id, name)  values (:id, :name);", nativeQuery = true)
    void insertRoleWithId(@Param("id") Long id, @Param("name") String name);
}
