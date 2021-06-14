package com.example.ooo.backend.dto;

import com.example.ooo.backend.model.Role;
import lombok.*;

@Data
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RoleDTO {

    private Long id;
    private String name;

    public static RoleDTO from(Role role) {
        return builder()
                .id(role.getId())
                .name(role.getName())
                .build();
    }
}
