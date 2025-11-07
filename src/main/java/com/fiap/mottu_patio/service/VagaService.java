package com.fiap.mottu_patio.service;

import com.fiap.mottu_patio.dto.VagaRequest;
import com.fiap.mottu_patio.exception.ResourceNotFoundException;
import com.fiap.mottu_patio.model.Patio;
import com.fiap.mottu_patio.model.Vaga;
import com.fiap.mottu_patio.repository.PatioRepository;
import com.fiap.mottu_patio.repository.VagaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VagaService {

    @Autowired
    private VagaRepository vagaRepository;

    @Autowired
    private PatioRepository patioRepository;

    public List<Vaga> findAll() {
        return vagaRepository.findAll();
    }

    public Optional<Vaga> findById(Long id) {
        return vagaRepository.findById(id);
    }

    public Vaga criar(VagaRequest request) {
        Patio patio = patioRepository.findById(request.getPatioId())
                .orElseThrow(() -> new ResourceNotFoundException("Pátio não encontrado."));

        Vaga vaga = new Vaga();
        vaga.setIdentificador(request.getIdentificador());
        vaga.setCodigo(request.getCodigo());
        vaga.setOcupada(false);
        vaga.setPatio(patio);

        return vagaRepository.save(vaga);
    }

    public Vaga atualizar(Long id, VagaRequest request) {
        Vaga vaga = vagaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vaga não encontrada."));

        Patio patio = patioRepository.findById(request.getPatioId())
                .orElseThrow(() -> new ResourceNotFoundException("Pátio não encontrado."));

        vaga.setIdentificador(request.getIdentificador());
        vaga.setCodigo(request.getCodigo());
        vaga.setPatio(patio);

        return vagaRepository.save(vaga);
    }

    public void deleteById(Long id) {
        vagaRepository.deleteById(id);
    }
}