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
    date = "2025-11-06T21:43:50-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.12 (Oracle Corporation)"
)
@Component
public class PatioMapperImpl implements PatioMapper {

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

    @Override
    public Patio toEntity(PatioRequest request) {
        if ( request == null ) {
            return null;
        }

        Patio.PatioBuilder patio = Patio.builder();

        patio.nome( request.getNome() );
        patio.endereco( request.getEndereco() );
        patio.capacidade( request.getCapacidade() );

        return patio.build();
    }
}
