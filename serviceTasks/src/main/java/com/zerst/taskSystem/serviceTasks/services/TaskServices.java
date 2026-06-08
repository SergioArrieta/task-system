package com.zerst.taskSystem.serviceTasks.services;

import com.zerst.taskSystem.serviceTasks.controller.CreateTaskRequest;
import com.zerst.taskSystem.serviceTasks.entities.Task;
import com.zerst.taskSystem.serviceTasks.exceptions.TaskNotFoundException;
import com.zerst.taskSystem.serviceTasks.exceptions.UserNotFoundException;
import com.zerst.taskSystem.serviceTasks.repository.TaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TaskServices {

    private final TaskRepository taskRepository;
    private final RestTemplate restTemplate;

    @Value("${user.service.url}")
    private String userServiceUrl;

    // inyeccion por constructor
    public TaskServices(TaskRepository taskRepository, RestTemplate restTemplate) {
        this.taskRepository = taskRepository;
        this.restTemplate = restTemplate;
    }

    @Transactional
    public TaskDTO createTask(CreateTaskRequest request) {
        try {
            restTemplate.getForObject(userServiceUrl + request.userId(), Object.class);
        } catch (HttpClientErrorException.NotFound e) {
            throw new UserNotFoundException("Task can not be created.");
        }

        Task task = new Task();
        task.setTitle(request.title());
        task.setStatus(request.status());
        task.setPriority(request.priority());
        task.setUserId(request.userId());

        Task savedTask = taskRepository.save(task);

        return savedTask.toDTO();
    }

    @Transactional(readOnly = true)
    public TaskDTO getTaskById(Long id) {
        return taskRepository.findById(id)
                .map(Task::toDTO)
                .orElseThrow(() -> new TaskNotFoundException(String.format("Task ID: %d was not found", id)));
    }

    @Transactional
    public void deleteById(Long id) {
        if (!taskRepository.existsById(id)) {
            log.error("Error deleting task by id. {} id was not found", id);
            throw new TaskNotFoundException(String.format("Task ID: %d was not found", id));
        }
        taskRepository.deleteById(id);
    }

    @Transactional
    public TaskDTO updateById(Long id, CreateTaskRequest request) {
        Task oldTask = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(String.format("Task ID: %d was not found", id)));

        oldTask.setTitle(request.title());
        oldTask.setStatus(request.status());
        oldTask.setPriority(request.priority());
        oldTask.setUserId(request.userId());

        return oldTask.toDTO();
    }

    @Transactional(readOnly = true)
    public List<TaskDTO> getTasksByUser(Long userId) {
        List<Task> tasksByUser = taskRepository.findByUserId(userId);
        return tasksByUser
                .stream()
                .map(Task::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public int deleteByUserId(Long userId) {
        return taskRepository.deleteByUserId(userId);
    }

    @Transactional
    public List<TaskDTO> getAllTasks() {
        return taskRepository.findAll().stream().map(Task::toDTO).collect(Collectors.toList());
    }

}
