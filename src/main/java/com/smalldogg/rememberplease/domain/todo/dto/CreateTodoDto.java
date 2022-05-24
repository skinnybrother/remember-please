package com.smalldogg.rememberplease.domain.todo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class CreateTodoDto {
    private String content;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dueDate;
}