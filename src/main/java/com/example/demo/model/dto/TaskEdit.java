package com.example.demo.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskEdit {
    private String name;
    private String description;
    private LocalDateTime dateCreated;
    private String priority;

    public TaskEdit(String name, String description, LocalDateTime dateCreated,String priority) {
        this.name = name;
        this.description = description;
        this.dateCreated = dateCreated;
        this.priority = priority;
    }
}
