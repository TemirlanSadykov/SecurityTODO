package com.example.ooo.backend.service;

import com.example.ooo.backend.dto.RoleDTO;
import com.example.ooo.backend.model.Role;
import com.example.ooo.backend.repository.RoleRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class RoleService {

    private final RoleRepo roleRepo;

    public List<RoleDTO> getAll() {
        List<Role> roles = new ArrayList<Role>();
        roles = roleRepo.findAll();

        List<RoleDTO> rolesDTO = new ArrayList<RoleDTO>();
        roles.forEach(obj -> {
            rolesDTO.add(RoleDTO.from(obj));
        });
        return rolesDTO;
    }

}
