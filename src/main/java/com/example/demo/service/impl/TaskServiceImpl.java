package com.example.demo.service.impl;

import com.example.demo.model.Task;
import com.example.demo.model.dto.TaskCreate;
import com.example.demo.model.dto.TaskEdit;
import com.example.demo.service.OpenAIChatService;
import com.example.demo.service.TaskService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;
import com.example.demo.repository.TaskRepository;

import java.util.List;
import java.util.Optional;
@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final OpenAIChatService openAIChatService;

    public TaskServiceImpl(TaskRepository taskRepository, OpenAIChatService openAIChatService) {
        this.taskRepository = taskRepository;
        this.openAIChatService = openAIChatService;
    }

    @Override
    public List<Task> listAll() {
        return this.taskRepository.findAll();
    }

    @Override
    public Optional<Task> findById(int id) {
        return this.taskRepository.findById((long) id);
    }

    @Override
    public void deleteById(int id) {
        this.taskRepository.deleteById((long) id);
    }

    @Override
    public Optional<Task> create(TaskCreate task) throws JsonProcessingException {
        String proposal = openAIChatService.generateChatResponse(task.getDescription());
        Task t = new Task(task.getName(), task.getDescription(),task.getDateCreated(),task.getPriority(), proposal);
        this.taskRepository.save(t);
        return Optional.of(t);
    }

    @Override
    public Optional<Task> edit(int id, TaskEdit task) {
        Task t = this.taskRepository.getById((long) id);
        t.setName(task.getName());
        t.setDescription(task.getDescription());
        t.setDateCreated(task.getDateCreated());
        t.setPriority(task.getPriority());
        this.taskRepository.save(t);
        return Optional.of(t);
    }

}
