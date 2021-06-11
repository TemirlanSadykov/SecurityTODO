package com.example.ooo.backend.service;

import com.example.ooo.backend.DTO.RoleDTO;
import com.example.ooo.backend.model.Role;
import com.example.ooo.backend.model.User;
import com.example.ooo.backend.repository.RoleRepo;
import com.example.ooo.backend.repository.TodoRepo;
import com.example.ooo.backend.repository.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class FetchService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;

    public User getUser(String login){
        return userRepo.findByLogin(login).get();
    }

}
