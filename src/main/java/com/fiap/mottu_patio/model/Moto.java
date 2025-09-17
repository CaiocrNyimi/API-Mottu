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
public class Moto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "A placa da moto é obrigatória")
    @Column(nullable = false, unique = true)
    private String placa;

    @NotBlank(message = "O modelo da moto é obrigatório")
    @Column(nullable = false)
    private String modelo;

    @NotBlank(message = "A cor da moto é obrigatória")
    @Column(nullable = false)
    private String cor;

    @NotNull(message = "O ano da moto é obrigatório")
    @Column(nullable = false)
    private Integer ano;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patio_id", nullable = false)
    @NotNull(message = "O pátio associado é obrigatório")
    private Patio patio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vaga_id")
    private Vaga vaga;
}