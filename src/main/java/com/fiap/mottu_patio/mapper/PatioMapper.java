package com.fiap.mottu_patio.mapper;

import com.fiap.mottu_patio.dto.PatioRequest;
import com.fiap.mottu_patio.dto.PatioResponse;
import com.fiap.mottu_patio.model.Patio;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PatioMapper {

    PatioMapper INSTANCE = Mappers.getMapper(PatioMapper.class);

    Patio toEntity(PatioRequest dto);

    PatioResponse toResponse(Patio patio);

    List<PatioResponse> toResponseList(List<Patio> patios);
}