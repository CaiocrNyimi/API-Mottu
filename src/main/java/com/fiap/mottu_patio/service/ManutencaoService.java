package com.fiap.mottu_patio.service;

import com.fiap.mottu_patio.exception.BusinessException;
import com.fiap.mottu_patio.exception.ResourceNotFoundException;
import com.fiap.mottu_patio.model.Manutencao;
import com.fiap.mottu_patio.model.Moto;
import com.fiap.mottu_patio.repository.ManutencaoRepository;
import com.fiap.mottu_patio.repository.MotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ManutencaoService {

    private final ManutencaoRepository manutencaoRepository;
    private final MotoRepository motoRepository;

    @Autowired
    public ManutencaoService(ManutencaoRepository manutencaoRepository, MotoRepository motoRepository) {
        this.manutencaoRepository = manutencaoRepository;
        this.motoRepository = motoRepository;
    }

    public List<Manutencao> findAll() {
        return manutencaoRepository.findAll();
    }

    public Manutencao save(Manutencao manutencao) {
        if (manutencao.getMoto().getQuilometragem() < manutencao.getQuilometragem()) {
            throw new BusinessException("A quilometragem da manutenção não pode ser maior que a quilometragem atual da moto.");
        }
        return manutencaoRepository.save(manutencao);
    }
}