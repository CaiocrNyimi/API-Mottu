package com.fiap.mottu_patio.service;

import com.fiap.mottu_patio.exception.BusinessException;
import com.fiap.mottu_patio.exception.ResourceNotFoundException;
import com.fiap.mottu_patio.model.Moto;
import com.fiap.mottu_patio.model.Patio;
import com.fiap.mottu_patio.model.Vaga;
import com.fiap.mottu_patio.repository.MotoRepository;
import com.fiap.mottu_patio.specification.MotoSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MotoService {

    private final MotoRepository motoRepository;
    private final VagaService vagaService;

    @Autowired
    public MotoService(MotoRepository motoRepository, VagaService vagaService) {
        this.motoRepository = motoRepository;
        this.vagaService = vagaService;
    }

    @Transactional
    public Moto save(Moto moto) {
        Long patioId = moto.getPatio().getId();
        
        Optional<Vaga> vagaLivre = vagaService.findFirstAvailableVaga(patioId);

        if (vagaLivre.isPresent()) {
            Vaga vagaParaOcupar = vagaLivre.get();
            vagaParaOcupar.setOcupada(true);

            moto.setVaga(vagaParaOcupar);

            vagaService.save(vagaParaOcupar);
            return motoRepository.save(moto);
        } else {
            throw new BusinessException("O pátio está lotado, não há vagas disponíveis.");
        }
    }

    public List<Moto> findAll() {
        return motoRepository.findAll();
    }

    public Optional<Moto> findById(Long id) {
        return motoRepository.findById(id);
    }

    public Moto update(Long id, Moto updatedMoto) {
        Moto existingMoto = findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Motorcycle not found with id: " + id));
        existingMoto.setPlaca(updatedMoto.getPlaca());
        existingMoto.setModelo(updatedMoto.getModelo());
        existingMoto.setCor(updatedMoto.getCor());
        existingMoto.setAno(updatedMoto.getAno());
        existingMoto.setPatio(updatedMoto.getPatio());
        
        return motoRepository.save(existingMoto);
    }

    @Transactional
    public void deleteById(Long id) {
        Moto moto = motoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Moto não encontrada com o ID: " + id));

        if (moto.getVaga() != null) {
            Vaga vaga = moto.getVaga();
            vaga.setOcupada(false);
            vagaService.save(vaga);
        }

        motoRepository.deleteById(id);
    }

    public List<Moto> findByFilter(String modelo, Integer ano, Patio patio) {
        Specification<Moto> specification = Specification.where(MotoSpecification.hasModelo(modelo))
                .and(MotoSpecification.hasAno(ano))
                .and(MotoSpecification.hasPatio(patio));
        return motoRepository.findAll(specification);
    }
}