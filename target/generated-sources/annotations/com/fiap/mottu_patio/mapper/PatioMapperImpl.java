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
    date = "2025-09-23T22:14:39-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.15 (Microsoft)"
)
@Component
public class PatioMapperImpl implements PatioMapper {

    @Override
    public Patio toEntity(PatioRequest dto) {
        if ( dto == null ) {
            return null;
        }

        Patio.PatioBuilder patio = Patio.builder();

        patio.nome( dto.getNome() );
        patio.endereco( dto.getEndereco() );
        patio.capacidade( dto.getCapacidade() );

        return patio.build();
    }

    @Override
    public PatioResponse toResponse(Patio patio) {
        if ( patio == null ) {
            return null;
        }

        PatioResponse patioResponse = new PatioResponse();

        patioResponse.setId( patio.getId() );
        patioResponse.setNome( patio.getNome() );
        patioResponse.setEndereco( patio.getEndereco() );
        patioResponse.setCapacidade( patio.getCapacidade() );

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
