package com.fiap.mottu_patio.dto;

import com.fiap.mottu_patio.model.enums.Status;
import lombok.Data;

@Data
public class MotoRequest {
    private String placa;
    private String modelo;
    private Integer ano;
    private Integer quilometragem;
    private Status status;
    private Long patioId;
}