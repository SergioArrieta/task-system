package com.zerst.taskSystem.serviceTasks.services;

import com.zerst.taskSystem.serviceTasks.repository.TaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
public class KafkaConsumer {

    private final TaskRepository taskRepository;

    public KafkaConsumer(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @KafkaListener(topics = "user-events", groupId = "tasks-group")
    @Transactional
    public void listen(String userId) {
        log.info("User ID {} deleted, deleting related tasks", userId);
        taskRepository.deleteByUserId(Long.parseLong(userId));
    }
}
