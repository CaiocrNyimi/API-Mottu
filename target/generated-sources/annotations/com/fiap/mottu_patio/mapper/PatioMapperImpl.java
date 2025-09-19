package com.fiap.mottu_patio.mapper;

import com.fiap.mottu_patio.dto.PatioRequestDTO;
import com.fiap.mottu_patio.dto.PatioResponseDTO;
import com.fiap.mottu_patio.model.Patio;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-19T20:37:02-0300",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.43.0.v20250819-1513, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class PatioMapperImpl implements PatioMapper {

    @Override
    public Patio toEntity(PatioRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Patio.PatioBuilder patio = Patio.builder();

        patio.capacidade( dto.getCapacidade() );
        patio.endereco( dto.getEndereco() );
        patio.nome( dto.getNome() );

        return patio.build();
    }

    @Override
    public PatioResponseDTO toResponse(Patio patio) {
        if ( patio == null ) {
            return null;
        }

        PatioResponseDTO patioResponseDTO = new PatioResponseDTO();

        patioResponseDTO.setCapacidade( patio.getCapacidade() );
        patioResponseDTO.setEndereco( patio.getEndereco() );
        patioResponseDTO.setId( patio.getId() );
        patioResponseDTO.setNome( patio.getNome() );
        patioResponseDTO.setVagasDisponiveis( patio.getVagasDisponiveis() );

        return patioResponseDTO;
    }

    @Override
    public List<PatioResponseDTO> toResponseList(List<Patio> patios) {
        if ( patios == null ) {
            return null;
        }

        List<PatioResponseDTO> list = new ArrayList<PatioResponseDTO>( patios.size() );
        for ( Patio patio : patios ) {
            list.add( toResponse( patio ) );
        }

        return list;
    }
}
