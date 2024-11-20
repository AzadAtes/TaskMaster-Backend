package com.az.taskmasterbackend.controller;

import com.az.taskmasterbackend.entity.Task;
import com.az.taskmasterbackend.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public Task createTask(@RequestBody Task task) {
        return taskService.createTask(task);
    }

    @GetMapping("/{id}")
    public Optional<Task> getTaskById(@PathVariable String id) {
        return taskService.getTask(id);
    }

    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }
}
