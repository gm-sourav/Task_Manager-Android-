package com.taskmanager.taskmanager.dto;

import com.taskmanager.taskmanager.entity.Category;
import com.taskmanager.taskmanager.entity.Priority;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class TaskRequest {
    private  String title;
    private  String description;
    private LocalDate deadline;
    private Priority priority;
    private Category category;
}
