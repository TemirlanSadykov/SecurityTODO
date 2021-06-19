package com.example.ooo.backend.forms;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.sql.Date;

@Getter
@Setter
public class FindTodoForm {

    private String status;

    private String name;

    private String startDate;
    private String finishDate;
}
