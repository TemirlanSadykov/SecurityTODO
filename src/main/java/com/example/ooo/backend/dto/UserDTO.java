package com.example.ooo.backend.dto;

import com.example.ooo.backend.model.User;
import lombok.*;

@Data
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDTO {

    private Long id;
    private String login;
    private String email;
    private String password;
    private boolean activate;

    public static UserDTO from(User user) {
        return builder()
                .id(user.getId())
                .email(user.getEmail())
                .login(user.getLogin())
                .password(user.getPassword())
                .activate(user.isActivate())
                .build();
    }
}
