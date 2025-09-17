package com.fiap.mottu_patio.dto;

import lombok.Data;

@Data
public class PatioResponseDTO {

    private Long id;
    private String nome;
    private String endereco;
    private Integer capacidade;
    private Integer vagasDisponiveis;
}