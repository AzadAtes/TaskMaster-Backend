package com.az.taskmasterbackend;

import com.az.taskmasterbackend.model.entity.Task;
import com.az.taskmasterbackend.testutils.IntegrationTest;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

class TasksIntegrationTest extends IntegrationTest {

    @Test
    void testCreateAndFindTask() {

        Task task = new Task();
        task.setId("1");
        task.setName("Test Task");
        task.setDescription("This is a test task");

        Task savedTask = taskRepository.save(task);
        List<Task> tasks = taskRepository.findAll();

        assertThat(savedTask.getId()).isNotNull();
        assertThat(tasks.getFirst().getId()).isEqualTo("1");
        assertThat(tasks.getFirst().getName()).isEqualTo("Test Task");
        assertThat(tasks.getFirst().getDescription()).isEqualTo("This is a test task");
    }
}