package com.fiap.mottu_patio.mapper;

import com.fiap.mottu_patio.dto.MotoRequestDTO;
import com.fiap.mottu_patio.dto.MotoResponseDTO;
import com.fiap.mottu_patio.model.Moto;
import com.fiap.mottu_patio.model.Patio;
import com.fiap.mottu_patio.repository.PatioRepository;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-17T22:17:22-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.15 (Microsoft)"
)
@Component
public class MotoMapperImpl extends MotoMapper {

    @Override
    public Moto toEntity(MotoRequestDTO request, PatioRepository patioRepository) {
        if ( request == null ) {
            return null;
        }

        Moto.MotoBuilder moto = Moto.builder();

        moto.placa( request.getPlaca() );
        moto.modelo( request.getModelo() );
        moto.cor( request.getCor() );
        moto.ano( request.getAno() );

        moto.patio( mapPatio(request.getPatioId(), patioRepository) );

        return moto.build();
    }

    @Override
    public MotoResponseDTO toResponse(Moto moto) {
        if ( moto == null ) {
            return null;
        }

        MotoResponseDTO motoResponseDTO = new MotoResponseDTO();

        motoResponseDTO.setIdPatio( motoPatioId( moto ) );
        motoResponseDTO.setNomePatio( motoPatioNome( moto ) );
        motoResponseDTO.setId( moto.getId() );
        motoResponseDTO.setPlaca( moto.getPlaca() );
        motoResponseDTO.setModelo( moto.getModelo() );
        motoResponseDTO.setCor( moto.getCor() );
        if ( moto.getAno() != null ) {
            motoResponseDTO.setAno( moto.getAno() );
        }

        motoResponseDTO.setVagaAtual( moto.getVaga() != null ? moto.getVaga().getCodigo() : null );

        return motoResponseDTO;
    }

    @Override
    public List<MotoResponseDTO> toResponseList(List<Moto> motos) {
        if ( motos == null ) {
            return null;
        }

        List<MotoResponseDTO> list = new ArrayList<MotoResponseDTO>( motos.size() );
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
