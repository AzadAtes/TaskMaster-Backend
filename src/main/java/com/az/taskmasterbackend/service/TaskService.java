package com.az.taskmasterbackend.service;

import com.az.taskmasterbackend.model.entity.Task;
import com.az.taskmasterbackend.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TaskService {

    private final TaskRepository taskRepository;
    protected final Log logger = LogFactory.getLog(this.getClass());

    public Task createTask(Task task) {

        Optional<Task> existingTask = taskRepository.findById(task.getId());
        if (existingTask.isEmpty()) {
            return taskRepository.save(task);
        } else {
            throw new IllegalArgumentException("Task already exists");
        }
    }

    public Optional<Task> getTask(String Id) {
        return taskRepository.findById(Id);
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
}