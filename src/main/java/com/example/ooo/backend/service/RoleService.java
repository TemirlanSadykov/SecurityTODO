package com.example.ooo.backend.service;

import com.example.ooo.backend.DTO.RoleDTO;
import com.example.ooo.backend.DTO.TodoDTO;
import com.example.ooo.backend.model.Role;
import com.example.ooo.backend.model.Todo;
import com.example.ooo.backend.repository.RoleRepo;
import com.example.ooo.backend.repository.TodoRepo;
import com.example.ooo.backend.repository.UserRepo;
import com.example.ooo.frontend.forms.TodoForm;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
@AllArgsConstructor
public class RoleService {

    private final RoleRepo roleRepo;

    public List<RoleDTO> getAll() {
        List<Role> roles = new ArrayList<Role>();
        roles = roleRepo.findAll();

        List<RoleDTO> rolesDTO = new ArrayList<RoleDTO>();
        roles.stream().forEach(obj -> {
            rolesDTO.add(RoleDTO.from(obj));
        });
        return rolesDTO;
    }

}
