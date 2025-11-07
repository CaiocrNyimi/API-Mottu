package com.fiap.mottu_patio.mapper;

import com.fiap.mottu_patio.dto.MotoResponse;
import com.fiap.mottu_patio.model.Moto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MotoMapper {

    @Mapping(source = "modelo", target = "modelo")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "vaga.codigo", target = "vagaCodigo")
    MotoResponse toResponse(Moto moto);

    List<MotoResponse> toResponseList(List<Moto> motos);
}