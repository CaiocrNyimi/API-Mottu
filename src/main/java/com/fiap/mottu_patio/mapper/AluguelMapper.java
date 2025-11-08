package com.fiap.mottu_patio.mapper;

import com.fiap.mottu_patio.dto.AluguelResponse;
import com.fiap.mottu_patio.model.Aluguel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AluguelMapper {

    public AluguelResponse toResponse(Aluguel aluguel) {
        return AluguelResponse.builder()
                .id(aluguel.getId())
                .userId(aluguel.getUser().getId())
                .userName(aluguel.getUser().getUsername())
                .motoId(aluguel.getMoto().getId())
                .motoModel(aluguel.getMoto().getModelo().name())
                .motoPlaca(aluguel.getMoto().getPlaca())
                .startDate(aluguel.getStartDate())
                .endDate(aluguel.getEndDate())
                .status(aluguel.getStatus())
                .build();
    }

    public List<AluguelResponse> toResponseList(List<Aluguel> alugueis) {
        return alugueis.stream().map(this::toResponse).collect(Collectors.toList());
    }
}