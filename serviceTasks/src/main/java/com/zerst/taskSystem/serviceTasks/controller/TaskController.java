package com.zerst.taskSystem.serviceTasks.controller;

import com.zerst.taskSystem.serviceTasks.services.TaskDTO;
import com.zerst.taskSystem.serviceTasks.services.TaskServices;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskServices taskService;

    // inyeccion por constructor
    public TaskController(TaskServices taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<@NonNull TaskDTO> getTaskById(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<@NonNull List<TaskDTO>> getTasksByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(taskService.getTasksByUser(userId));
    }

    @GetMapping()
    public ResponseEntity<@NonNull List<TaskDTO>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @PostMapping
    public ResponseEntity<@NonNull TaskDTO> createTask(@RequestBody CreateTaskRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.createTask(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<@NonNull TaskDTO> updateTask(@PathVariable Long id, @RequestBody CreateTaskRequest request) {
        return ResponseEntity.ok(taskService.updateById(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTaskById(@PathVariable Long id) {
        taskService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<Void> deleteTaskByUserId(@PathVariable Long userId) {
        int deletedTask = taskService.deleteByUserId(userId);
        if (deletedTask == 0) {
            ResponseEntity.ok("User has not task to delete.");
        }
        return ResponseEntity.noContent().build();
    }

}
