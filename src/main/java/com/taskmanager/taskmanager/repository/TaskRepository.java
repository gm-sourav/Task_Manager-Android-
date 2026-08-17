package com.taskmanager.taskmanager.repository;

import com.taskmanager.taskmanager.entity.Category;
import com.taskmanager.taskmanager.entity.Task;
import com.taskmanager.taskmanager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUser(User user);
    List<Task> findByUserAndCategory(User user, Category category);
    List<Task> findByUserId(Long userID);
}
