package com.fiap.mottu_patio.model.enums;

public enum ModeloMoto {
    MOTTU_SPORT("Mottu Sport"),
    MOTTU_E("Mottu E"),
    MOTTU_POP("Mottu Pop");

    private final String descricao;

    ModeloMoto(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}