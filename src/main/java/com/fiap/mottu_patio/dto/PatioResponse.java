package com.fiap.mottu_patio.dto;

import com.fiap.mottu_patio.model.Patio;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class PatioResponse {
    private Long id;
    private String nome;
    private String endereco;
    private Integer capacidade;
    private Integer vagasDisponiveis;
    private List<VagaResponse> vagas;

    public PatioResponse(Patio patio) {
        this.id = patio.getId();
        this.nome = patio.getNome();
        this.endereco = patio.getEndereco();
        this.capacidade = patio.getCapacidade();
        this.vagasDisponiveis = patio.getVagasDisponiveis();
        
        this.vagas = patio.getVagas().stream()
                .map(VagaResponse::new)
                .collect(Collectors.toList());
    }
}