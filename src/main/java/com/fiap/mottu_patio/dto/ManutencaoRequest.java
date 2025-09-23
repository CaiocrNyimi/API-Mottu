package com.fiap.mottu_patio.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ManutencaoRequest {
    private String tipoServico;
    private LocalDateTime dataManutencao;
    private int quilometragem;
    private Long motoId;
}