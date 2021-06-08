package com.example.ooo.frontend.forms;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UserLoginForm {

    @NotBlank(message = "Обязательное поле")
    private String login;

    @NotBlank(message = "Обязательное поле")
    @Email(message = "Такого email нет")
    private String email;

    @NotBlank(message = "Вы не выбрали должность")
    private String role;

    @NotBlank(message = "Обязательное поле")
    @Size(min = 8, message = "Пароль должен содержать минимум 8 символов")
    private String password;

}
