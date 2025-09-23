package com.fiap.mottu_patio.mapper;

import com.fiap.mottu_patio.dto.MotoRequest;
import com.fiap.mottu_patio.dto.MotoResponse;
import com.fiap.mottu_patio.model.Moto;
import com.fiap.mottu_patio.model.Patio;
import com.fiap.mottu_patio.repository.PatioRepository;
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
public class MotoMapperImpl extends MotoMapper {

    @Override
    public Moto toEntity(MotoRequest request, PatioRepository patioRepository) {
        if ( request == null ) {
            return null;
        }

        Moto.MotoBuilder moto = Moto.builder();

        moto.ano( request.getAno() );
        moto.cor( request.getCor() );
        moto.modelo( request.getModelo() );
        moto.placa( request.getPlaca() );
        moto.quilometragem( request.getQuilometragem() );

        moto.patio( mapPatio(request.getPatioId(), patioRepository) );

        return moto.build();
    }

    @Override
    public MotoResponse toResponse(Moto moto) {
        if ( moto == null ) {
            return null;
        }

        MotoResponse motoResponse = new MotoResponse();

        motoResponse.setIdPatio( motoPatioId( moto ) );
        motoResponse.setNomePatio( motoPatioNome( moto ) );
        motoResponse.setAno( moto.getAno() );
        motoResponse.setCor( moto.getCor() );
        motoResponse.setId( moto.getId() );
        motoResponse.setModelo( moto.getModelo() );
        motoResponse.setPlaca( moto.getPlaca() );
        motoResponse.setQuilometragem( moto.getQuilometragem() );
        if ( moto.getStatus() != null ) {
            motoResponse.setStatus( moto.getStatus().name() );
        }

        motoResponse.setVagaAtual( moto.getVaga() != null ? moto.getVaga().getCodigo() : null );

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

    private Long motoPatioId(Moto moto) {
        if ( moto == null ) {
            return null;
        }
        Patio patio = moto.getPatio();
        if ( patio == null ) {
            return null;
        }
        Long id = patio.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String motoPatioNome(Moto moto) {
        if ( moto == null ) {
            return null;
        }
        Patio patio = moto.getPatio();
        if ( patio == null ) {
            return null;
        }
        String nome = patio.getNome();
        if ( nome == null ) {
            return null;
        }
        return nome;
    }
}
