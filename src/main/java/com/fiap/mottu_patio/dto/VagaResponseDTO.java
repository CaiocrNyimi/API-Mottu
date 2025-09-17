package com.fiap.mottu_patio.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VagaResponseDTO {
    private Long id;
    private String identificador;
    private boolean ocupada;
    private String nomePatio;
}