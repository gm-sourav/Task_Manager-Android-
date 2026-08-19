package com.taskmanager.taskmanager.dto;


import com.taskmanager.taskmanager.entity.Category;
import com.taskmanager.taskmanager.entity.Priority;
import com.taskmanager.taskmanager.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class TaskResponse {
    private Long id;
    private String title;
    private String description;
    private LocalDate deadline;
    private Priority priority;
    private Category category;
    private Status status;
    private LocalDateTime createdAt;

}
