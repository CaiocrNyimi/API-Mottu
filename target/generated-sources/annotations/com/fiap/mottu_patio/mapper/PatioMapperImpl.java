package com.fiap.mottu_patio.mapper;

import com.fiap.mottu_patio.dto.PatioRequest;
import com.fiap.mottu_patio.dto.PatioResponse;
import com.fiap.mottu_patio.model.Patio;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-23T20:11:08-0300",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.43.0.v20250819-1513, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class PatioMapperImpl implements PatioMapper {

    @Override
    public Patio toEntity(PatioRequest dto) {
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
    public PatioResponse toResponse(Patio patio) {
        if ( patio == null ) {
            return null;
        }

        PatioResponse patioResponse = new PatioResponse();

        patioResponse.setCapacidade( patio.getCapacidade() );
        patioResponse.setEndereco( patio.getEndereco() );
        patioResponse.setId( patio.getId() );
        patioResponse.setNome( patio.getNome() );

        return patioResponse;
    }

    @Override
    public List<PatioResponse> toResponseList(List<Patio> patios) {
        if ( patios == null ) {
            return null;
        }

        List<PatioResponse> list = new ArrayList<PatioResponse>( patios.size() );
        for ( Patio patio : patios ) {
            list.add( toResponse( patio ) );
        }

        return list;
    }
}
