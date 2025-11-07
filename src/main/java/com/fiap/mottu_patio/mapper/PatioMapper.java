package com.fiap.mottu_patio.mapper;

import com.fiap.mottu_patio.dto.PatioRequest;
import com.fiap.mottu_patio.dto.PatioResponse;
import com.fiap.mottu_patio.dto.VagaResponse;
import com.fiap.mottu_patio.model.Patio;
import org.mapstruct.*;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface PatioMapper {

    @Mapping(target = "vagas", ignore = true)
    PatioResponse toResponse(Patio patio);

    List<PatioResponse> toResponseList(List<Patio> patios);

    @Mapping(target = "id", ignore = true)
    Patio toEntity(PatioRequest request);

    @AfterMapping
    default void preencherVagas(Patio patio, @MappingTarget PatioResponse response) {
        if (patio.getVagas() != null) {
            List<VagaResponse> vagaResponses = patio.getVagas().stream()
                .map(VagaResponse::new)
                .collect(Collectors.toList());
            response.setVagas(vagaResponses);
        }
        response.setVagasDisponiveis(patio.getVagasDisponiveis());
    }
}