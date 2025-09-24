package com.fiap.mottu_patio.mapper;

import com.fiap.mottu_patio.dto.MotoRequest;
import com.fiap.mottu_patio.dto.MotoResponse;
import com.fiap.mottu_patio.model.Moto;
import com.fiap.mottu_patio.model.Patio;
import com.fiap.mottu_patio.model.enums.ModeloMoto;
import com.fiap.mottu_patio.repository.PatioRepository;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-23T22:01:16-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.15 (Microsoft)"
)
@Component
public class MotoMapperImpl extends MotoMapper {

    @Override
    public Moto toEntity(MotoRequest request, PatioRepository patioRepository) {
        if ( request == null ) {
            return null;
        }

        Moto.MotoBuilder moto = Moto.builder();

        moto.placa( request.getPlaca() );
        if ( request.getModelo() != null ) {
            moto.modelo( Enum.valueOf( ModeloMoto.class, request.getModelo() ) );
        }
        moto.ano( request.getAno() );
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
        motoResponse.setId( moto.getId() );
        motoResponse.setPlaca( moto.getPlaca() );
        if ( moto.getModelo() != null ) {
            motoResponse.setModelo( moto.getModelo().name() );
        }
        motoResponse.setAno( moto.getAno() );
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
