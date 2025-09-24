package com.fiap.mottu_patio.dto;

import com.fiap.mottu_patio.model.Vaga;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VagaResponse {
    private Long id;
    private String identificador;
    private String codigo;
    private boolean ocupada;
    private Long patioId;
    private String patioNome;
    private String placaMoto;

    public VagaResponse(Vaga vaga) {
        this.id = vaga.getId();
        this.identificador = vaga.getIdentificador();
        this.codigo = vaga.getCodigo();
        this.ocupada = vaga.isOcupada();
        if (vaga.getPatio() != null) {
            this.patioId = vaga.getPatio().getId();
            this.patioNome = vaga.getPatio().getNome();
        }
        if (vaga.getMoto() != null) {
            this.placaMoto = vaga.getMoto().getPlaca();
        }
    }
}