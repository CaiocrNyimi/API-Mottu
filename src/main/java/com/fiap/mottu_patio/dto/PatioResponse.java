package com.fiap.mottu_patio.dto;

import lombok.Data;
import java.util.List;

@Data
public class PatioResponse {
    private Long id;
    private String nome;
    private String endereco;
    private Integer capacidade;
    private int vagasDisponiveis;
    private List<VagaResponse> vagas;
}