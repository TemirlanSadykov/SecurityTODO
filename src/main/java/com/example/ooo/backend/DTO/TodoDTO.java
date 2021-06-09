package com.example.ooo.backend.DTO;

import com.example.ooo.backend.model.Todo;
import com.example.ooo.backend.model.User;
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
    private String status;
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
