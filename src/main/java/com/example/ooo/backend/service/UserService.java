package com.example.ooo.backend.service;

import com.example.ooo.backend.DTO.UserDTO;
import com.example.ooo.backend.model.User;
import com.example.ooo.backend.repository.UserRepo;
import com.example.ooo.frontend.forms.UserLoginForm;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepo userRepo;
    private final PasswordEncoder encoder;

    public String createUser(UserLoginForm userLoginForm){
        if(userRepo.findByLogin(userLoginForm.getLogin()).isPresent()){
            return "Данный пользователь зарегистрирован";
        }
        else {
            User user = User.builder()
                    .login(userLoginForm.getLogin())
                    .email(userLoginForm.getEmail())
                    .password(encoder.encode(userLoginForm.getPassword()))
                    .role(userLoginForm.getRole())
                    .activate("Да")
                    .build();

            userRepo.save(user);
            return null;
        }
    }

    public Page<UserDTO> findUsersByRole(Pageable pageable){
        return userRepo.findUsersByRole(pageable, "ROLE_USER").map(UserDTO::from);
    }

    public void activate(Long id){
        if (userRepo.findById(id).get().getActivate().equals("Да")){
            User user = userRepo.findById(id).get();
            user.setActivate("Нет");
            userRepo.save(user);
        }
        else {
            User user = userRepo.findById(id).get();
            user.setActivate("Да");
            userRepo.save(user);
        }
    }
}
