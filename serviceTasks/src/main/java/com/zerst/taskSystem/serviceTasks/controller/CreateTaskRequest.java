package com.zerst.taskSystem.serviceTasks.controller;

public record CreateTaskRequest(String title, String status, int priority, Long userId) {
}