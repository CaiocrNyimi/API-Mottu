package com.fiap.mottu_patio.mapper;

import com.fiap.mottu_patio.dto.ManutencaoResponse;
import com.fiap.mottu_patio.model.Manutencao;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ManutencaoMapper {

    public ManutencaoResponse toResponse(Manutencao manutencao) {
        return ManutencaoResponse.builder()
                .id(manutencao.getId())
                .motoId(manutencao.getMoto().getId())
                .motoPlaca(manutencao.getMoto().getPlaca())
                .descricao(manutencao.getTipoServico())
                .data(manutencao.getDataManutencao())
                .build();
    }

    public List<ManutencaoResponse> toResponseList(List<Manutencao> manutencoes) {
        return manutencoes.stream().map(this::toResponse).collect(Collectors.toList());
    }
}