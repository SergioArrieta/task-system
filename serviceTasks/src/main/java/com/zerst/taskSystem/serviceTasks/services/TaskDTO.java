package com.zerst.taskSystem.serviceTasks.services;

public record TaskDTO(Long id, String title, String status, int priority, Long userId) {
}

