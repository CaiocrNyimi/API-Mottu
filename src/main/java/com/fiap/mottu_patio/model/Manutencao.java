package com.fiap.mottu_patio.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Manutencao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "A data da manutenção é obrigatória")
    private LocalDateTime dataManutencao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "moto_id", nullable = false)
    private Moto moto;

    @NotBlank(message = "O tipo de serviço é obrigatório")
    private String tipoServico;

    @NotNull(message = "A quilometragem atual é obrigatória")
    private Integer quilometragem;
}