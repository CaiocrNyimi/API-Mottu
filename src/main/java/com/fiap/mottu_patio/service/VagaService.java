package com.fiap.mottu_patio.service;

import com.fiap.mottu_patio.exception.ResourceNotFoundException;
import com.fiap.mottu_patio.model.Vaga;
import com.fiap.mottu_patio.repository.VagaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VagaService {

    private final VagaRepository vagaRepository;

    @Autowired
    public VagaService(VagaRepository vagaRepository) {
        this.vagaRepository = vagaRepository;
    }

    public List<Vaga> findAll() {
        return vagaRepository.findAll();
    }

    public Vaga save(Vaga vaga) {
        return vagaRepository.save(vaga);
    }

    public Optional<Vaga> findById(Long id) {
        return vagaRepository.findById(id);
    }

    public void deleteById(Long id) {
        if (!vagaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Vaga não encontrada com o ID: " + id);
        }
        vagaRepository.deleteById(id);
    }
    
    public Optional<Vaga> findFirstAvailableVaga(Long patioId) {
        return vagaRepository.findFirstByPatioIdAndOcupadaFalse(patioId);
    }
}