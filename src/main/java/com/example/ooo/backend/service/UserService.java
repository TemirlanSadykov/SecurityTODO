package com.example.ooo.backend.service;

import com.example.ooo.backend.dto.UserDTO;
import com.example.ooo.backend.model.Status;
import com.example.ooo.backend.model.User;
import com.example.ooo.backend.repository.RoleRepo;
import com.example.ooo.backend.repository.UserRepo;
import com.example.ooo.backend.util.Constants;
import com.example.ooo.frontend.forms.UserLoginForm;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepo userRepo;
    private final PasswordEncoder encoder;
    private final RoleRepo roleRepo;

    public String createUser(UserLoginForm userLoginForm) {
        if (userRepo.findByLogin(userLoginForm.getLogin()).isPresent()) {
            return "Данный пользователь зарегистрирован";
        } else {
            User user = User.builder()
                    .login(userLoginForm.getLogin())
                    .email(userLoginForm.getEmail())
                    .password(encoder.encode(userLoginForm.getPassword()))
                    .role(roleRepo.findById(userLoginForm.getRole()).get())
                    .activate(true)
                    .build();

            userRepo.save(user);
            return null;
        }
    }

    public Page<UserDTO> findUsersByRole(Pageable pageable) {
        return userRepo.findUsersByRoleName(pageable, Constants.ROLE_USER).map(UserDTO::from);
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
    public List<Status> getStatus(){
        List<Status> status = new ArrayList<>();
        Collections.addAll(status, Status.values());
        return status;
    }
}
