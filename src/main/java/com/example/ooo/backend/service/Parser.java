package com.example.ooo.backend.service;

import com.example.ooo.backend.model.QTodo;
import com.example.ooo.backend.model.Status;
import javafx.beans.binding.BooleanExpression;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class Parser {
    public LocalDateTime localDateTimeParser(String localDateTime) {
        if (localDateTime.equals("")) {
            return null;
        }
        return LocalDateTime.parse(localDateTime);
    }
}
