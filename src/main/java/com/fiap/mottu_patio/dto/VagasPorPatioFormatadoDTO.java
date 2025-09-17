package com.fiap.mottu_patio.dto;

import java.util.Map;

import lombok.Data;

import java.util.List;

@Data
public class VagasPorPatioFormatadoDTO {

    private String nomePatio;
    private Map<String, List<String>> vagasPorFileira;

    public VagasPorPatioFormatadoDTO() {}

    public VagasPorPatioFormatadoDTO(String nomePatio, Map<String, List<String>> vagasPorFileira) {
        this.nomePatio = nomePatio;
        this.vagasPorFileira = vagasPorFileira;
    }

}