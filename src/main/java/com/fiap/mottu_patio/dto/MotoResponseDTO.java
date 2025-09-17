package com.fiap.mottu_patio.dto;

import lombok.Data;

@Data
public class MotoResponseDTO {

    private Long id;
    private String placa;
    private String modelo;
    private String cor;
    private int ano;
    private Long idPatio;
    private String nomePatio;
    private String vagaAtual;
}