package com.fiap.mottu_patio.dto;

import lombok.Data;

@Data
public class VagaRequest {
    private String identificador;
    private String codigo;
    private Long patioId;
}