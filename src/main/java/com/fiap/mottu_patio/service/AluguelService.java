package com.fiap.mottu_patio.service;

import com.fiap.mottu_patio.dto.AluguelRequest;
import com.fiap.mottu_patio.dto.AluguelResponse;
import com.fiap.mottu_patio.exception.ResourceNotFoundException;
import com.fiap.mottu_patio.mapper.AluguelMapper;
import com.fiap.mottu_patio.model.Aluguel;
import com.fiap.mottu_patio.model.Moto;
import com.fiap.mottu_patio.model.User;
import com.fiap.mottu_patio.repository.AluguelRepository;
import com.fiap.mottu_patio.repository.MotoRepository;
import com.fiap.mottu_patio.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AluguelService {

    @Autowired
    private AluguelRepository aluguelRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MotoRepository motoRepository;

    @Autowired
    private AluguelMapper aluguelMapper;

    public List<AluguelResponse> findAll() {
        return aluguelMapper.toResponseList(aluguelRepository.findAll());
    }

    public Optional<AluguelResponse> findById(Long id) {
        return aluguelRepository.findById(id).map(aluguelMapper::toResponse);
    }

    public AluguelResponse criar(AluguelRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado."));
        Moto moto = motoRepository.findById(request.getMotoId())
                .orElseThrow(() -> new ResourceNotFoundException("Moto não encontrada."));

        Aluguel aluguel = Aluguel.builder()
                .user(user)
                .moto(moto)
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .status(request.getStatus())
                .build();

        return aluguelMapper.toResponse(aluguelRepository.save(aluguel));
    }

    public AluguelResponse atualizar(Long id, AluguelRequest request) {
        Aluguel existente = aluguelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aluguel não encontrado."));

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado."));
        Moto moto = motoRepository.findById(request.getMotoId())
                .orElseThrow(() -> new ResourceNotFoundException("Moto não encontrada."));

        existente.setUser(user);
        existente.setMoto(moto);
        existente.setStartDate(request.getStartDate());
        existente.setEndDate(request.getEndDate());
        existente.setStatus(request.getStatus());

        return aluguelMapper.toResponse(aluguelRepository.save(existente));
    }

    public void deleteById(Long id) {
        Aluguel aluguel = aluguelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aluguel não encontrado."));
        aluguelRepository.delete(aluguel);
    }
}