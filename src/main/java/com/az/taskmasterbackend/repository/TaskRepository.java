package com.az.taskmasterbackend.repository;

import com.az.taskmasterbackend.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, String> {
}