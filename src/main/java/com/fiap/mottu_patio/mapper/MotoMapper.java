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

    // Mapeia o DTO para entidade, com injeção de Patio via repository
    @Mapping(target = "patio", expression = "java(mapPatio(request.getPatioId(), patioRepository))")
    public abstract Moto toEntity(MotoRequestDTO request, @Context PatioRepository patioRepository);

    // Mapeia entidade Moto para DTO de resposta
    @Mapping(source = "patio.id", target = "idPatio")
    @Mapping(source = "patio.nome", target = "nomePatio")
    @Mapping(target = "vagaAtual", expression = "java(moto.getVaga() != null ? moto.getVaga().getCodigo() : null)")
    public abstract MotoResponseDTO toResponse(Moto moto);

    // Lista de Moto -> Lista de MotoResponseDTO
    public abstract List<MotoResponseDTO> toResponseList(List<Moto> motos);

    // Busca o pátio no banco para preencher o campo
    protected Patio mapPatio(Long patioId, @Context PatioRepository patioRepository) {
        return patioRepository.findById(patioId)
                .orElseThrow(() -> new RuntimeException("Pátio não encontrado com id: " + patioId));
    }
}