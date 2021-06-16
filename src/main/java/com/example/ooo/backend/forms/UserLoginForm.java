package com.example.ooo.backend.forms;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UserLoginForm {

    @NotBlank(message = "Обязательное поле")
    private String login;

    @NotBlank(message = "Обязательное поле")
    @Email(message = "Такого email нет")
    private String email;

    @NotNull(message = "Вы не выбрали должность")
    private Long role;

    @NotBlank(message = "Обязательное поле")
    @Size(min = 8, message = "Пароль должен содержать минимум 8 символов")
    private String password;

}
