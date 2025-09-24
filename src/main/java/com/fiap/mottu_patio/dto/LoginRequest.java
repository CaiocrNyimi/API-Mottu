package com.fiap.mottu_patio.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}