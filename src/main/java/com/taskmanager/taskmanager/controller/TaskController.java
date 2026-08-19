package com.taskmanager.taskmanager.controller;


import com.taskmanager.taskmanager.dto.TaskRequest;
import com.taskmanager.taskmanager.dto.TaskResponse;
import com.taskmanager.taskmanager.entity.Category;
import com.taskmanager.taskmanager.entity.Status;
import com.taskmanager.taskmanager.service.TaskService;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.util.List;

@Controller
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping
    public ResponseEntity<?> createTask(@RequestBody TaskRequest request, Authentication auth) {
        try {
            TaskResponse response = taskService.createTask(request, auth.getName());
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllTasks(Authentication auth, @RequestParam(required = false) Category category) {
        try {
            List<TaskResponse> tasks;
            if (category != null) {
                tasks = taskService.getTaskByCategory(auth.getName(), category);
            } else {
                tasks = taskService.getAllTasks(auth.getName());
            }
            return ResponseEntity.ok(tasks);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTask(@PathVariable Long id, @RequestBody TaskRequest request, Authentication auth) {
        try {
            TaskResponse response = taskService.updateTask(id, request, auth.getName());
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateTaskStatus(@PathVariable Long id, @RequestParam Status status, Authentication auth) {
        try{
            TaskResponse response = taskService.updateStatus(id, status, auth.getName());
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id, Authentication auth) {
        try {
            taskService.deleteTask(id, auth.getName());
            return ResponseEntity.ok("Task deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
