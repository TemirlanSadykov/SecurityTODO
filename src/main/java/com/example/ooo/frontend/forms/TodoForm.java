package com.example.ooo.frontend.forms;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class TodoForm {

    @NotBlank(message = "Обязательное поле")
    @Size(max = 64, message = "Вы превысили лимит в 16 слова")
    private String name;

    @NotBlank(message = "Обязательное поле")
    @Size(max = 256, message = "Вы превысили лимит в 256 слова")
    private String description;

    private Long id;
}
