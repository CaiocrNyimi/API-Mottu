package com.fiap.mottu_patio.service;

import com.fiap.mottu_patio.enums.Status;
import com.fiap.mottu_patio.exception.BusinessException;
import com.fiap.mottu_patio.exception.ResourceNotFoundException;
import com.fiap.mottu_patio.model.Moto;
import com.fiap.mottu_patio.model.Patio;
import com.fiap.mottu_patio.repository.MotoRepository;
import com.fiap.mottu_patio.repository.PatioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MotoService {

    private final MotoRepository motoRepository;
    private final PatioRepository patioRepository;

    @Autowired
    public MotoService(MotoRepository motoRepository, PatioRepository patioRepository) {
        this.motoRepository = motoRepository;
        this.patioRepository = patioRepository;
    }

    public List<Moto> findAll() {
        return motoRepository.findAll();
    }

    public Optional<Moto> findById(Long id) {
        return motoRepository.findById(id);
    }
    
    public List<Moto> findByStatus(Status status) {
        return motoRepository.findByStatus(status);
    }

    @Transactional
    public Moto save(Moto moto) {
        if (moto.getId() == null && motoRepository.existsByPlaca(moto.getPlaca())) {
            throw new BusinessException("A placa já está cadastrada.");
        }
        
        Patio patio = moto.getPatio();
        if (patio != null) {
            int vagasOcupadas = motoRepository.countByPatioId(patio.getId());
            if (vagasOcupadas >= patio.getCapacidade()) {
                throw new BusinessException("O pátio selecionado está lotado.");
            }
        }
        
        if (moto.getStatus() == null) {
            moto.setStatus(Status.DISPONIVEL);
        }

        return motoRepository.save(moto);
    }

    @Transactional
    public void deleteById(Long id) {
        Moto moto = motoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Moto não encontrada."));
        
        if (moto.getStatus() != Status.DISPONIVEL) {
            throw new BusinessException("Não é possível excluir uma moto que não está disponível.");
        }
        
        motoRepository.deleteById(id);
    }
}