package com.fiap.mottu_patio.dto;

import lombok.Data;

@Data
public class UserRequest {
    private String username;
    private String password;
    private String role;
}