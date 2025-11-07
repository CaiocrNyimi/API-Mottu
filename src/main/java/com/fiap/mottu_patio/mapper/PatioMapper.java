package com.fiap.mottu_patio.mapper;

import com.fiap.mottu_patio.dto.PatioRequest;
import com.fiap.mottu_patio.dto.PatioResponse;
import com.fiap.mottu_patio.model.Patio;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PatioMapper {

    PatioResponse toResponse(Patio patio);

    List<PatioResponse> toResponseList(List<Patio> patios);

    @Mapping(target = "id", ignore = true)
    Patio toEntity(PatioRequest request);
}