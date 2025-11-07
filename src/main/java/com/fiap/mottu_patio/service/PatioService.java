package com.fiap.mottu_patio.service;

import com.fiap.mottu_patio.dto.PatioRequest;
import com.fiap.mottu_patio.dto.PatioResponse;
import com.fiap.mottu_patio.exception.ResourceNotFoundException;
import com.fiap.mottu_patio.mapper.PatioMapper;
import com.fiap.mottu_patio.model.Patio;
import com.fiap.mottu_patio.model.Vaga;
import com.fiap.mottu_patio.repository.PatioRepository;
import com.fiap.mottu_patio.repository.VagaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PatioService {

    @Autowired
    private PatioRepository patioRepository;

    @Autowired
    private VagaRepository vagaRepository;

    @Autowired
    private PatioMapper patioMapper;

    public List<PatioResponse> findAll() {
        return patioMapper.toResponseList(patioRepository.findAll());
    }

    public Optional<PatioResponse> findById(Long id) {
        return patioRepository.findById(id).map(patioMapper::toResponse);
    }

    public PatioResponse criar(PatioRequest request) {
        Patio patio = patioMapper.toEntity(request);
        Patio salvo = patioRepository.save(patio);

        List<Vaga> vagas = new ArrayList<>();
        for (int i = 1; i <= patio.getCapacidade(); i++) {
            Vaga vaga = Vaga.builder()
                .identificador("V" + i)
                .codigo(String.format("%03d", i))
                .ocupada(false)
                .patio(salvo)
                .build();
            vagas.add(vaga);
        }

        vagaRepository.saveAll(vagas);
        salvo.setVagas(vagas);
        salvo.setVagasDisponiveis(patio.getCapacidade());

        return patioMapper.toResponse(salvo);
    }

    public PatioResponse atualizar(Long id, PatioRequest request) {
        Patio patioExistente = patioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pátio não encontrado."));

        patioExistente.setNome(request.getNome());
        patioExistente.setEndereco(request.getEndereco());
        patioExistente.setCapacidade(request.getCapacidade());

        Patio atualizado = patioRepository.save(patioExistente);
        return patioMapper.toResponse(atualizado);
    }

    public void deleteById(Long id) {
        Patio patio = patioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pátio não encontrado."));
        vagaRepository.deleteAllByPatio(patio);
        patioRepository.delete(patio);
    }
}