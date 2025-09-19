package com.fiap.mottu_patio.mapper;

import com.fiap.mottu_patio.dto.AluguelDTO;
import com.fiap.mottu_patio.model.Aluguel;

public class AluguelMapper {

    public static AluguelDTO toDTO(Aluguel aluguel) {
        if (aluguel == null) {
            return null;
        }

        return AluguelDTO.builder()
                .id(aluguel.getId())
                .userId(aluguel.getUser().getId())
                .userName(aluguel.getUser().getUsername())
                .motoId(aluguel.getMoto().getId())
                .motoModel(aluguel.getMoto().getModelo())
                .startDate(aluguel.getStartDate())
                .endDate(aluguel.getEndDate())
                .status(aluguel.getStatus())
                .build();
    }
}