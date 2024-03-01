package com.example.demo.web;

import com.example.demo.model.Task;
import com.example.demo.model.dto.TaskCreate;
import com.example.demo.model.dto.TaskEdit;
import com.example.demo.service.OpenAIChatService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.demo.service.TaskService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping({"/tasks", "/", ""})
public class TaskController {

    private final TaskService taskService;
    private final OpenAIChatService openAIChatService;


    public TaskController(TaskService taskService, OpenAIChatService openAIChatService) {
        this.taskService = taskService;
        this.openAIChatService = openAIChatService;
    }

    @GetMapping
    public String getAllTasks(Model model) {
        List<Task> tasks = taskService.listAll();
        model.addAttribute("tasks", tasks);
        return "tasks";
    }

    @GetMapping("/{id}")
    public String getTaskById(@PathVariable Long id, Model model) {
        Optional<Task> optionalEntity = taskService.findById(Math.toIntExact(id));

        if (optionalEntity.isPresent()) {
            model.addAttribute("task", optionalEntity.get());
            return "taskDetails";
        } else {
            return "notFound";
        }
    }

    @GetMapping("/create")
    public String showCreateTaskForm(Model model) {
        model.addAttribute("task", new TaskCreate());
        return "createTask";
    }

    @PostMapping("/create")
    public String createTask(@ModelAttribute("task") TaskCreate task) throws JsonProcessingException {
        taskService.create(task);
        return "redirect:/tasks";
    }

    @GetMapping("/update/{id}")
    public String showUpdateTaskForm(@PathVariable int id, Model model) {
        Optional<Task> optionalEntity = taskService.findById(id);

        if (optionalEntity.isPresent()) {
            model.addAttribute("task", optionalEntity.get());
            return "updateTask";
        } else {
            return "notFound";
        }
    }

    @PostMapping("/update/{id}")
    public String updateTask(@PathVariable int id, @ModelAttribute("task") TaskEdit task) {
        taskService.edit(id, task);
        return "redirect:/tasks";
    }

    @GetMapping("/delete/{id}")
    public String showDeleteTaskForm(@PathVariable int id, Model model) {
        Optional<Task> optionalEntity = taskService.findById(id);

        if (optionalEntity.isPresent()) {
            model.addAttribute("task", optionalEntity.get());
            return "deleteTask";
        } else {
            return "notFound";
        }
    }

    @PostMapping("/delete/{id}")
    public String deleteTask(@PathVariable int id) {
        taskService.deleteById(id);
        return "redirect:/tasks";
    }

    @GetMapping("/details/{id}")
    public String showTaskDetails(@PathVariable Long id, Model model) {
        Optional<Task> optionalEntity = taskService.findById(Math.toIntExact(id));

        if (optionalEntity.isPresent()) {
            model.addAttribute("task", optionalEntity.get());
            return "taskDetails";
        } else {
            return "notFound";
        }
    }
}
