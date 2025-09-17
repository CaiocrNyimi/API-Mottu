package com.fiap.mottu_patio.model;

import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vaga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String identificador;

    private boolean ocupada = false;

    @NotBlank(message = "O código da vaga é obrigatório")
    @Column(nullable = false)
    private String codigo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patio_id", nullable = false)
    @NotNull(message = "O pátio associado é obrigatório")
    private Patio patio;
}