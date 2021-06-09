package com.example.ooo.backend.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Обязательное поле")
    @Column(length = 64)
    private String login;

    @NotBlank(message = "Обязательное поле")
    @Email(message = "Такого email нет")
    @Column(length = 64)
    private String email;

    @NotBlank(message = "Обязательное поле")
    @Size(min = 8, message = "Пароль должен содержать минимум 8 символов")
    @Column(length = 64)
    private String password;

    @NotNull(message = "Обязательное поле")
    @Column
    private boolean activate;

    @Column
    @Builder.Default
    private boolean enabled = true;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

}
