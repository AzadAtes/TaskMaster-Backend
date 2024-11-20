package com.az.taskmasterbackend;

import com.az.taskmasterbackend.entity.Task;
import com.az.taskmasterbackend.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;



import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TaskRepositoryTest   {

    @Autowired
    private TaskRepository taskRepository;

    @Test
    void testCreateAndFindTask() {
        // Given
        Task task = new Task();
        task.setId("1");
        task.setName("Test Task");
        task.setDescription("This is a test task");

        // When
        Task savedTask = taskRepository.save(task);
        List<Task> tasks = taskRepository.findAll();

        // Then
        assertThat(savedTask.getId()).isNotNull();
        assertThat(tasks.getFirst().getId()).isEqualTo("1");
        assertThat(tasks.getFirst().getName()).isEqualTo("Test Task");
        assertThat(tasks.getFirst().getDescription()).isEqualTo("This is a test task");
    }
}