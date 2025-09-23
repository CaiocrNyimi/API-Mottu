package com.fiap.mottu_patio.dto;

import lombok.Data;

@Data
public class MotoRequest {
    private String placa;
    private String modelo;
    private String cor;
    private Integer ano;
    private Integer quilometragem;
    private Long patioId;
}