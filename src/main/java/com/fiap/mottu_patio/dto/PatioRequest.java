package com.fiap.mottu_patio.dto;

import lombok.Data;

@Data
public class PatioRequest {
    private String nome;
    private String endereco;
    private Integer capacidade;
}