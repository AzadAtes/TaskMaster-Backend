package com.az.taskmasterbackend.service;

import com.az.taskmasterbackend.model.entity.Task;
import com.az.taskmasterbackend.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public Optional<Task> getTask(String Id) {
        return taskRepository.findById(Id);
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
}