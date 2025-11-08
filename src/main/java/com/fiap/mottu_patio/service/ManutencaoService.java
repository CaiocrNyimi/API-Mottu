package com.fiap.mottu_patio.service;

import com.fiap.mottu_patio.dto.ManutencaoRequest;
import com.fiap.mottu_patio.dto.ManutencaoResponse;
import com.fiap.mottu_patio.exception.ResourceNotFoundException;
import com.fiap.mottu_patio.mapper.ManutencaoMapper;
import com.fiap.mottu_patio.model.Manutencao;
import com.fiap.mottu_patio.model.Moto;
import com.fiap.mottu_patio.repository.ManutencaoRepository;
import com.fiap.mottu_patio.repository.MotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ManutencaoService {

    @Autowired
    private ManutencaoRepository manutencaoRepository;

    @Autowired
    private MotoRepository motoRepository;

    @Autowired
    private ManutencaoMapper manutencaoMapper;

    public List<ManutencaoResponse> findAll() {
        return manutencaoMapper.toResponseList(manutencaoRepository.findAll());
    }

    public Optional<ManutencaoResponse> findById(Long id) {
        return manutencaoRepository.findById(id).map(manutencaoMapper::toResponse);
    }

    public ManutencaoResponse criar(ManutencaoRequest request) {
        Moto moto = motoRepository.findById(request.getMotoId())
                .orElseThrow(() -> new ResourceNotFoundException("Moto não encontrada."));

        Manutencao manutencao = Manutencao.builder()
                .moto(moto)
                .tipoServico(request.getDescricao())
                .dataManutencao(request.getData())
                .quilometragem(moto.getQuilometragem())
                .build();

        return manutencaoMapper.toResponse(manutencaoRepository.save(manutencao));
    }

    public ManutencaoResponse atualizar(Long id, ManutencaoRequest request) {
        Manutencao existente = manutencaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Manutenção não encontrada."));

        Moto moto = motoRepository.findById(request.getMotoId())
                .orElseThrow(() -> new ResourceNotFoundException("Moto não encontrada."));

        existente.setMoto(moto);
        existente.setTipoServico(request.getDescricao());
        existente.setDataManutencao(request.getData());
        existente.setQuilometragem(moto.getQuilometragem());

        return manutencaoMapper.toResponse(manutencaoRepository.save(existente));
    }

    public void deleteById(Long id) {
        Manutencao manutencao = manutencaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Manutenção não encontrada."));
        manutencaoRepository.delete(manutencao);
    }
}