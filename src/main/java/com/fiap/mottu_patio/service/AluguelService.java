package com.fiap.mottu_patio.service;

import com.fiap.mottu_patio.exception.ResourceNotFoundException;
import com.fiap.mottu_patio.model.Aluguel;
import com.fiap.mottu_patio.repository.AluguelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AluguelService {

    @Autowired
    private AluguelRepository aluguelRepository;

    public List<Aluguel> findAll() {
        return aluguelRepository.findAll();
    }

    public Optional<Aluguel> findById(Long id) {
        return aluguelRepository.findById(id);
    }

    public Aluguel criar(Aluguel aluguel) {
        return aluguelRepository.save(aluguel);
    }

    public Aluguel atualizar(Long id, Aluguel aluguelAtualizado) {
        Aluguel existente = aluguelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aluguel não encontrado."));
        existente.setUser(aluguelAtualizado.getUser());
        existente.setMoto(aluguelAtualizado.getMoto());
        existente.setStartDate(aluguelAtualizado.getStartDate());
        existente.setEndDate(aluguelAtualizado.getEndDate());
        existente.setStatus(aluguelAtualizado.getStatus());
        return aluguelRepository.save(existente);
    }

    public void deleteById(Long id) {
        Aluguel aluguel = aluguelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aluguel não encontrado."));
        aluguelRepository.delete(aluguel);
    }
}