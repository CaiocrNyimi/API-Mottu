package com.fiap.mottu_patio.dto;

import lombok.Data;

@Data
public class VagaResponse {
    private Long id;
    private String identificador;
    private String codigo;

    private Long patioId;
    private String nomePatio;
}