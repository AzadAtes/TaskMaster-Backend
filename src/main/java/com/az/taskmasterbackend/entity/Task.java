package com.az.taskmasterbackend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tasks")
@Getter
@Setter
public class Task {

    @Id
    private String id = UUID.randomUUID().toString();

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    @JsonBackReference
    private Task parentTask;

    @OneToMany(mappedBy = "parentTask", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Task> subTasks;

    // Expose only the parentTask's ID in the serialized output
    public String getParentTaskId() {
        return parentTask != null ? parentTask.getId() : null;
    }
}
