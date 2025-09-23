package com.fiap.mottu_patio.dto;

import lombok.Data;

@Data
public class MotoResponse {
    private Long id;
    private String placa;
    private String modelo;
    private String cor;
    private Integer ano;
    private Integer quilometragem;
    private String status;

    private Long idPatio;
    private String nomePatio;

    private String vagaAtual;
}