package com.example.ooo.backend.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "todos")
public class Todo extends AbstractEntity {

    private LocalDateTime date;

    @NotBlank(message = "Обязательное поле")
    @Column(length = 64)
    private String name;

    @NotBlank(message = "Обязательное поле")
    @Column(length = 256)
    private String description;

    @Column
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private LocalDateTime term;

    @Column
    private boolean alreadysent;

}
