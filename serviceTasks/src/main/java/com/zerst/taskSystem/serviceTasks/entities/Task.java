package com.zerst.taskSystem.serviceTasks.entities;

import com.zerst.taskSystem.serviceTasks.services.TaskDTO;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tasks")
@Data
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String status;
    private int priority;
    private Long userId;

    public TaskDTO toDTO() {
        return new TaskDTO(this.id, this.title, this.status, this.priority, this.userId);
    }
}
