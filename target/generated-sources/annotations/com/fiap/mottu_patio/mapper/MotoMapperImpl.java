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
    date = "2025-09-21T21:45:01-0300",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.43.0.v20250819-1513, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class MotoMapperImpl extends MotoMapper {

    @Override
    public Moto toEntity(MotoRequestDTO request, PatioRepository patioRepository) {
        if ( request == null ) {
            return null;
        }

        Moto.MotoBuilder moto = Moto.builder();

        moto.ano( request.getAno() );
        moto.cor( request.getCor() );
        moto.modelo( request.getModelo() );
        moto.placa( request.getPlaca() );

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
        if ( moto.getAno() != null ) {
            motoResponseDTO.setAno( moto.getAno() );
        }
        motoResponseDTO.setCor( moto.getCor() );
        motoResponseDTO.setId( moto.getId() );
        motoResponseDTO.setModelo( moto.getModelo() );
        motoResponseDTO.setPlaca( moto.getPlaca() );

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
