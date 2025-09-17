package com.fiap.mottu_patio.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Patio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do pátio é obrigatório")
    private String nome;

    @NotBlank(message = "O endereço do pátio é obrigatório")
    private String endereco;

    @NotNull(message = "A capacidade do pátio é obrigatória")
    private Integer capacidade;

    private int vagasDisponiveis;

    @PostPersist
    @PostLoad
    public void inicializarVagasDisponiveis() {
        if (this.vagasDisponiveis == 0) {
            this.vagasDisponiveis = this.capacidade;
        }
    }
}