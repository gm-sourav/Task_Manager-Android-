package com.taskmanager.taskmanager.service;


import com.taskmanager.taskmanager.dto.TaskRequest;
import com.taskmanager.taskmanager.dto.TaskResponse;
import com.taskmanager.taskmanager.entity.Category;
import com.taskmanager.taskmanager.entity.Status;
import com.taskmanager.taskmanager.entity.Task;
import com.taskmanager.taskmanager.entity.User;
import com.taskmanager.taskmanager.repository.TaskRepository;
import com.taskmanager.taskmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.hibernate.Hibernate.map;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    private User getCurrentUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    private TaskResponse mapToTaskResponse(Task task) {
        return new TaskResponse(
                task.getId(), task.getTitle(), task.getDescription(),task.getDeadline(), task.getPriority(), task.getCategory(), task.getStatus(), task.getCreatedAt()
        );
    }

    public TaskResponse createTask(TaskRequest request, String email) {
        User user = getCurrentUser(email);
        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setDeadline(request.getDeadline());
        task.setPriority(request.getPriority());
        task.setCategory(request.getCategory());
        task.setStatus(Status.PENDING);
        task.setUser(user);

        Task saved = taskRepository.save(task);
        return mapToTaskResponse(saved);

    }

    public List<TaskResponse> getAllTasks(String email) {
        User user = getCurrentUser(email);
        return taskRepository.findByUser(user)
                .stream()
                .map(this::mapToTaskResponse)
                .collect(Collectors.toList());
    }



    public List<TaskResponse> getTaskByCategory(String email, Category category) {
        User user = getCurrentUser(email);
        return taskRepository.findByUserAndCategory(user, category)
                .stream()
                .map(this::mapToTaskResponse)
                .collect(Collectors.toList());
    }

    public TaskResponse updateTask(Long taskId, TaskRequest request, String email) {
        User user = getCurrentUser(email);
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        if (!task.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You are not allowed to edit this task");

        }
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setDeadline(request.getDeadline());
        task.setPriority(request.getPriority());
        task.setCategory(request.getCategory());

        Task updated = taskRepository.save(task);
        return mapToTaskResponse(updated);
    }


    public TaskResponse updateStatus(Long taskId, Status status, String email) {
        User user = getCurrentUser(email);
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        if (!task.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You are not allowed to edit this task");
        }
        task.setStatus(status);
        Task updated = taskRepository.save(task);
        return mapToTaskResponse(updated);
    }

    public void deleteTask(Long taskId, String email) {
        User user = getCurrentUser(email);
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));


        if(!task.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You are not allowed to delete this task");
        }

        taskRepository.delete(task);
    }










}