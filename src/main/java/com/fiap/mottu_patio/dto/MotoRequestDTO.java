package com.fiap.mottu_patio.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MotoRequestDTO {

    @NotBlank
    private String placa;

    @NotBlank
    private String modelo;

    @NotBlank
    private String cor;

    @NotNull
    private Integer ano;

    @NotNull
    private Long patioId;
}