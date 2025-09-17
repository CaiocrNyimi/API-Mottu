package com.fiap.mottu_patio.service;

import com.fiap.mottu_patio.dto.PatioResponseDTO;
import com.fiap.mottu_patio.exception.ResourceNotFoundException;
import com.fiap.mottu_patio.model.Patio;
import com.fiap.mottu_patio.model.Vaga;
import com.fiap.mottu_patio.repository.PatioRepository;
import com.fiap.mottu_patio.repository.VagaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PatioService {

    private final PatioRepository patioRepository;
    private final VagaRepository vagaRepository;

    @Autowired
    public PatioService(PatioRepository patioRepository, VagaRepository vagaRepository) {
        this.patioRepository = patioRepository;
        this.vagaRepository = vagaRepository;
    }
    
    public Patio save(Patio patio) {
        if (patio.getCapacidade() > 260) {
            throw new IllegalArgumentException("The maximum number of slots allowed is 260.");
        }
        patio.setVagasDisponiveis(patio.getCapacidade());
        Patio savedPatio = patioRepository.save(patio);
        generateVagas(savedPatio);
        return savedPatio;
    }

    public List<Patio> findAll() {
        return patioRepository.findAll();
    }
    
    public Optional<Patio> findById(Long id) {
        return patioRepository.findById(id);
    }

    public Patio update(Long id, Patio updatedPatio) {
        Patio existingPatio = findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patio not found with id: " + id));

        existingPatio.setNome(updatedPatio.getNome());
        existingPatio.setEndereco(updatedPatio.getEndereco());

        int oldCapacity = existingPatio.getCapacidade();
        int newCapacity = updatedPatio.getCapacidade();

        if (newCapacity > 260) {
            throw new IllegalArgumentException("The maximum number of slots allowed is 260.");
        }

        existingPatio.setCapacidade(newCapacity);
        int occupiedVagas = vagaRepository.countByPatioAndOcupadaTrue(existingPatio);
        existingPatio.setVagasDisponiveis(newCapacity - occupiedVagas);

        Patio savedPatio = patioRepository.save(existingPatio);

        if (newCapacity > oldCapacity) {
            generateAdditionalVagas(savedPatio, oldCapacity, newCapacity);
        } else if (newCapacity < oldCapacity) {
            removeExcessVagas(savedPatio, newCapacity);
        }

        return savedPatio;
    }

    @Transactional
    public void deleteById(Long id) {
        Patio patio = patioRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Patio not found with id: " + id));

        vagaRepository.deleteAllByPatio(patio);

        patioRepository.delete(patio);
    }
    
    private void generateVagas(Patio patio) {
        char row = 'A';
        int slotNumber = 1;
        for (int i = 0; i < patio.getCapacidade(); i++) {
            String identifier = row + ":" + slotNumber;
            Vaga vaga = Vaga.builder()
                    .identificador(identifier)
                    .codigo(row + String.format("%02d", slotNumber))
                    .ocupada(false)
                    .patio(patio)
                    .build();
            vagaRepository.save(vaga);
            slotNumber++;
            if (slotNumber > 10) {
                slotNumber = 1;
                row++;
            }
        }
    }

    private void generateAdditionalVagas(Patio patio, int oldCapacity, int newCapacity) {
        List<Vaga> existingVagas = vagaRepository.findByPatio(patio);
        int lastVagaIndex = existingVagas.size() - 1;
        String[] parts = existingVagas.get(lastVagaIndex).getCodigo().split("(?<=\\D)(?=\\d)");
        char row = parts[0].charAt(0);
        int slotNumber = Integer.parseInt(parts[1]) + 1;
        
        for (int i = oldCapacity; i < newCapacity; i++) {
            if (slotNumber > 10) {
                slotNumber = 1;
                row++;
            }
            String code = row + String.format("%02d", slotNumber);
            Vaga newVaga = Vaga.builder()
                    .identificador(row + ":" + slotNumber)
                    .codigo(code)
                    .ocupada(false)
                    .patio(patio)
                    .build();
            vagaRepository.save(newVaga);
            slotNumber++;
        }
    }

    private void removeExcessVagas(Patio patio, int newCapacity) {
        List<Vaga> vagasToRemove = vagaRepository.findByPatio(patio)
                .stream()
                .skip(newCapacity)
                .collect(Collectors.toList());
        vagaRepository.deleteAll(vagasToRemove);
    }
}