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

import java.util.Comparator;
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
            throw new IllegalArgumentException("O número máximo de vagas é 260.");
        }

        patio.setVagasDisponiveis(patio.getCapacidade());

        Patio savedPatio = patioRepository.save(patio);

        if (savedPatio.getCapacidade() > 0) {
            generateVagas(savedPatio);
        }

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
                .orElseThrow(() -> new ResourceNotFoundException("Patio não encontrado com ID: " + id));

        int oldCapacity = existingPatio.getCapacidade();
        int newCapacity = updatedPatio.getCapacidade();

        if (newCapacity > 260) {
            throw new IllegalArgumentException("O número máximo de vagas é 260.");
        }

        int occupiedVagas = vagaRepository.countByPatioAndOcupadaTrue(existingPatio);
        if (newCapacity < occupiedVagas) {
            throw new IllegalArgumentException("A nova capacidade não pode ser menor que o número de vagas ocupadas (" + occupiedVagas + ").");
        }

        existingPatio.setNome(updatedPatio.getNome());
        existingPatio.setEndereco(updatedPatio.getEndereco());
        existingPatio.setCapacidade(newCapacity);
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
            .orElseThrow(() -> new ResourceNotFoundException("Patio não encontrado com ID: " + id));

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
        Optional<Vaga> lastVagaOptional = vagaRepository.findTopByPatioOrderByIdDesc(patio);

        int startingSlotNumber = lastVagaOptional.map(vaga -> {
            try {
                String code = vaga.getCodigo();
                return Integer.parseInt(code.substring(1));
            } catch (NumberFormatException e) {
                return 0;
            }
        }).orElse(0);

        char currentRow = lastVagaOptional.map(vaga -> vaga.getCodigo().charAt(0)).orElse('A');

        for (int i = oldCapacity; i < newCapacity; i++) {
            startingSlotNumber++;

            if (startingSlotNumber > 10) {
                startingSlotNumber = 1;
                currentRow++;
            }

            String formattedSlot = String.format("%02d", startingSlotNumber);
            String code = String.valueOf(currentRow) + formattedSlot;
            String identifier = "VAGA-" + code;

            Vaga newVaga = new Vaga();
            newVaga.setIdentificador(identifier);
            newVaga.setCodigo(code);
            newVaga.setOcupada(false);
            newVaga.setPatio(patio);

            vagaRepository.save(newVaga);
        }
    }

    private void removeExcessVagas(Patio patio, int newCapacity) {
        List<Vaga> freeVagas = vagaRepository.findAllByPatioAndOcupadaFalse(patio);

        int totalOccupied = vagaRepository.countByPatioAndOcupadaTrue(patio);
        int vagasToRemoveCount = patio.getCapacidade() - newCapacity;

        if (vagasToRemoveCount > 0 && freeVagas.size() >= vagasToRemoveCount) {
            freeVagas.sort(Comparator.comparing(Vaga::getCodigo).reversed());

            List<Vaga> vagasToRemove = freeVagas.subList(0, vagasToRemoveCount);
            
            vagaRepository.deleteAll(vagasToRemove);
        }
    }
}