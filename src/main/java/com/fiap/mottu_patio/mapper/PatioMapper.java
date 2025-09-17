package com.fiap.mottu_patio.mapper;

import com.fiap.mottu_patio.dto.PatioRequestDTO;
import com.fiap.mottu_patio.dto.PatioResponseDTO;
import com.fiap.mottu_patio.model.Patio;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PatioMapper {

    PatioMapper INSTANCE = Mappers.getMapper(PatioMapper.class);

    Patio toEntity(PatioRequestDTO dto);

    PatioResponseDTO toResponse(Patio patio);

    List<PatioResponseDTO> toResponseList(List<Patio> patios);
}