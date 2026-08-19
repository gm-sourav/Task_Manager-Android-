package com.taskmanager.taskmanager.dto;

import lombok.Data;

@Data
public class AuthResponse {
    private String name;
    private String email;
    private String password;
}
