package com.example.ooo.backend.dto;

import com.example.ooo.backend.model.Status;
import com.example.ooo.backend.model.Todo;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TodoDTO {

    private Long id;
    private LocalDateTime date;
    private String name;
    private String description;
    private Status status;
    private UserDTO userDTO;
    private LocalDateTime term;
    private boolean already_sent;

    public static TodoDTO from(Todo todo) {
        return builder()
                .id(todo.getId())
                .date(todo.getDate())
                .name(todo.getName())
                .description(todo.getDescription())
                .status(todo.getStatus())
                .term(todo.getTerm())
                .already_sent(todo.isAlready_sent())
                .userDTO(UserDTO.from(todo.getUser()))
                .build();
    }
}
