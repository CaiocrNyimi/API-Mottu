package com.fiap.mottu_patio.mapper;

import com.fiap.mottu_patio.dto.MotoRequestDTO;
import com.fiap.mottu_patio.dto.MotoResponseDTO;
import com.fiap.mottu_patio.model.Moto;
import com.fiap.mottu_patio.model.Patio;
import com.fiap.mottu_patio.repository.PatioRepository;

import org.mapstruct.*;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class MotoMapper {

    @Mapping(target = "patio", expression = "java(mapPatio(request.getPatioId(), patioRepository))")
    public abstract Moto toEntity(MotoRequestDTO request, @Context PatioRepository patioRepository);

    @Mapping(source = "patio.id", target = "idPatio")
    @Mapping(source = "patio.nome", target = "nomePatio")
    @Mapping(target = "vagaAtual", expression = "java(moto.getVaga() != null ? moto.getVaga().getCodigo() : null)")
    public abstract MotoResponseDTO toResponse(Moto moto);

    public abstract List<MotoResponseDTO> toResponseList(List<Moto> motos);

    protected Patio mapPatio(Long patioId, @Context PatioRepository patioRepository) {
        return patioRepository.findById(patioId)
                .orElseThrow(() -> new RuntimeException("Pátio não encontrado com id: " + patioId));
    }
}