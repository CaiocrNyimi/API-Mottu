package com.fiap.mottu_patio.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fiap.mottu_patio.model.enums.Status;
import jakarta.validation.constraints.Min;
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

    @NotNull(message = "A quilometragem não pode ser nula")
    @Min(value = 0, message = "A quilometragem não pode ser negativa")
    private Integer quilometragem;

    @NotNull(message = "O status não pode ser nulo")
    @Enumerated(EnumType.STRING)
    private Status status;

    @NotNull(message = "O pátio associado é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "patio_id", nullable = false)
    private Patio patio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "vaga_id")
    private Vaga vaga;
}