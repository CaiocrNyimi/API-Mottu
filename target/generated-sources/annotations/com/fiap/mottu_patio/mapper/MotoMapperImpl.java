package com.fiap.mottu_patio.mapper;

import com.fiap.mottu_patio.dto.MotoResponse;
import com.fiap.mottu_patio.model.Moto;
import com.fiap.mottu_patio.model.Vaga;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-10-01T19:48:00-0300",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.43.0.v20250819-1513, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class MotoMapperImpl implements MotoMapper {

    @Override
    public MotoResponse toResponse(Moto moto) {
        if ( moto == null ) {
            return null;
        }

        MotoResponse motoResponse = new MotoResponse();

        if ( moto.getModelo() != null ) {
            motoResponse.setModelo( moto.getModelo().name() );
        }
        if ( moto.getStatus() != null ) {
            motoResponse.setStatus( moto.getStatus().name() );
        }
        motoResponse.setVagaCodigo( motoVagaCodigo( moto ) );
        motoResponse.setAno( moto.getAno() );
        motoResponse.setId( moto.getId() );
        motoResponse.setPlaca( moto.getPlaca() );
        motoResponse.setQuilometragem( moto.getQuilometragem() );

        return motoResponse;
    }

    @Override
    public List<MotoResponse> toResponseList(List<Moto> motos) {
        if ( motos == null ) {
            return null;
        }

        List<MotoResponse> list = new ArrayList<MotoResponse>( motos.size() );
        for ( Moto moto : motos ) {
            list.add( toResponse( moto ) );
        }

        return list;
    }

    private String motoVagaCodigo(Moto moto) {
        if ( moto == null ) {
            return null;
        }
        Vaga vaga = moto.getVaga();
        if ( vaga == null ) {
            return null;
        }
        String codigo = vaga.getCodigo();
        if ( codigo == null ) {
            return null;
        }
        return codigo;
    }
}
