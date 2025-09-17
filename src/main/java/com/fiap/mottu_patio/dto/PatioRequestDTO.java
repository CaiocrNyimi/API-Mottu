package com.fiap.mottu_patio.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PatioRequestDTO {

    @NotBlank(message = "O nome do pátio é obrigatório")
    private String nome;

    @NotBlank(message = "O endereço é obrigatório")
    private String endereco;

    @NotNull(message = "A capacidade é obrigatória")
    private Integer capacidade;

}