package com.example.ooo.backend.service;

import com.example.ooo.backend.dto.UserDTO;
import com.example.ooo.backend.model.Status;
import com.example.ooo.backend.model.User;
import com.example.ooo.backend.repository.RoleRepo;
import com.example.ooo.backend.repository.UserRepo;
import com.example.ooo.backend.forms.UserLoginForm;
import com.example.ooo.backend.util.Constants;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepo userRepo;
    private final PasswordEncoder encoder;
    private final RoleRepo roleRepo;
    private final RoleService roleService;

    public void createUser(UserLoginForm userLoginForm) {
            User user = User.builder()
                    .login(userLoginForm.getLogin())
                    .email(userLoginForm.getEmail())
                    .password(encoder.encode(userLoginForm.getPassword()))
                    .role(roleRepo.findById(userLoginForm.getRole()).get())
                    .activate(true)
                    .build();

            userRepo.save(user);
    }

    public Page<UserDTO> findUsersByRole(Pageable pageable, String role) {
        return userRepo.findUsersByRoleName(pageable, role).map(UserDTO::from);
    }

    public void activate(Long id) {
        if (userRepo.findById(id).get().isActivate()) {
            User user = userRepo.findById(id).get();
            user.setActivate(false);
            userRepo.save(user);
        } else {
            User user = userRepo.findById(id).get();
            user.setActivate(true);
            userRepo.save(user);
        }
    }

    public String giveRoles(Principal principal){
        String login = principal.getName();
        for (int i = 0; i < roleService.getAll().size(); i++) {
            if (userRepo.existsByLoginAndRoleId(login, roleService.getAll().get(i).getId())) {
                return Constants.REDIRECT_LIST().get(i);
            }
        }
        return "/";
    }
}
