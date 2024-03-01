package com.example.demo.service;

import com.example.demo.model.Task;
import com.example.demo.model.dto.TaskCreate;
import com.example.demo.model.dto.TaskEdit;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    List<Task> listAll();
    Optional<Task> findById(int id);
    void deleteById(int id);
    Optional<Task> create(TaskCreate task) throws JsonProcessingException;
    Optional<Task> edit(int id, TaskEdit task);


}
