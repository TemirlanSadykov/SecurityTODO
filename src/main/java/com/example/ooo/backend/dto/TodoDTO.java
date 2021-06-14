package com.example.ooo.backend.dto;

import com.example.ooo.backend.model.Status;
import com.example.ooo.backend.model.Todo;
import lombok.*;

import java.util.Date;

@Data
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TodoDTO {

    private Long id;
    private Date date;
    private String name;
    private String description;
    private Status status;
    private UserDTO userDTO;

    public static TodoDTO from(Todo todo) {
        return builder()
                .id(todo.getId())
                .date(todo.getDate())
                .name(todo.getName())
                .description(todo.getDescription())
                .status(todo.getStatus())
                .userDTO(UserDTO.from(todo.getUser()))
                .build();
    }
}
