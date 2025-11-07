package com.fiap.mottu_patio.service;

import com.fiap.mottu_patio.exception.ResourceNotFoundException;
import com.fiap.mottu_patio.model.Manutencao;
import com.fiap.mottu_patio.repository.ManutencaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ManutencaoService {

    @Autowired
    private ManutencaoRepository manutencaoRepository;

    public List<Manutencao> findAll() {
        return manutencaoRepository.findAll();
    }

    public Optional<Manutencao> findById(Long id) {
        return manutencaoRepository.findById(id);
    }

    public Manutencao criar(Manutencao manutencao) {
        return manutencaoRepository.save(manutencao);
    }

    public Manutencao atualizar(Long id, Manutencao manutencaoAtualizada) {
        Manutencao existente = manutencaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Manutenção não encontrada."));
        existente.setTipoServico(manutencaoAtualizada.getTipoServico());
        existente.setDataManutencao(manutencaoAtualizada.getDataManutencao());
        existente.setQuilometragem(manutencaoAtualizada.getQuilometragem());
        existente.setMoto(manutencaoAtualizada.getMoto());
        return manutencaoRepository.save(existente);
    }

    public void deleteById(Long id) {
        Manutencao manutencao = manutencaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Manutenção não encontrada."));
        manutencaoRepository.delete(manutencao);
    }
}