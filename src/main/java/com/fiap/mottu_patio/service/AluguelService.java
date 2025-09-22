package com.fiap.mottu_patio.service;

import com.fiap.mottu_patio.exception.BusinessException;
import com.fiap.mottu_patio.exception.ResourceNotFoundException;
import com.fiap.mottu_patio.model.Aluguel;
import com.fiap.mottu_patio.model.Moto;
import com.fiap.mottu_patio.model.User;
import com.fiap.mottu_patio.model.enums.Status;
import com.fiap.mottu_patio.repository.AluguelRepository;
import com.fiap.mottu_patio.repository.MotoRepository;
import com.fiap.mottu_patio.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AluguelService {

    private final AluguelRepository aluguelRepository;
    private final MotoRepository motoRepository;
    private final UserRepository userRepository;

    @Autowired
    public AluguelService(AluguelRepository aluguelRepository, MotoRepository motoRepository, UserRepository userRepository) {
        this.aluguelRepository = aluguelRepository;
        this.motoRepository = motoRepository;
        this.userRepository = userRepository;
    }

    public List<Aluguel> findAll() {
        return aluguelRepository.findAll();
    }

    public Optional<Aluguel> findById(Long id) {
        return aluguelRepository.findById(id);
    }

    @Transactional
    public Aluguel reserveBike(Long userId, Long motoId, LocalDateTime startDate, LocalDateTime endDate) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado."));
        Moto moto = motoRepository.findById(motoId)
                .orElseThrow(() -> new ResourceNotFoundException("Moto não encontrada."));

        List<Aluguel> existingAlugueis = aluguelRepository.findByMotoIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                motoId, endDate, startDate);

        if (!existingAlugueis.isEmpty() || !Status.DISPONIVEL.equals(moto.getStatus())) {
            throw new BusinessException("A moto não está disponível para o período solicitado.");
        }

        if (startDate.isAfter(endDate) || startDate.isBefore(LocalDateTime.now())) {
            throw new BusinessException("As datas do aluguel são inválidas.");
        }

        Aluguel newAluguel = Aluguel.builder()
                .user(user)
                .moto(moto)
                .startDate(startDate)
                .endDate(endDate)
                .status("Reservado")
                .build();

        aluguelRepository.save(newAluguel);

        moto.setStatus(Status.ALUGADA);
        motoRepository.save(moto);

        return newAluguel;
    }

    @Transactional
    public Aluguel updateAluguel(Long id, Long userId, Long motoId, LocalDateTime startDate, LocalDateTime endDate) {
        Aluguel aluguel = aluguelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aluguel não encontrado."));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado."));
        Moto moto = motoRepository.findById(motoId)
                .orElseThrow(() -> new ResourceNotFoundException("Moto não encontrada."));

        aluguel.setUser(user);
        aluguel.setMoto(moto);
        aluguel.setStartDate(startDate);
        aluguel.setEndDate(endDate);
        aluguel.setStatus("Reservado");

        aluguelRepository.save(aluguel);
        return aluguel;
    }

    @Transactional
    public Aluguel returnBike(Long aluguelId) {
        Aluguel aluguel = aluguelRepository.findById(aluguelId)
                .orElseThrow(() -> new ResourceNotFoundException("Aluguel não encontrado."));

        if (!"Alugado".equals(aluguel.getStatus()) && !"Reservado".equals(aluguel.getStatus())) {
            throw new BusinessException("Este aluguel não pode ser devolvido.");
        }

        aluguel.setStatus("Devolvido");
        aluguelRepository.save(aluguel);

        aluguel.getMoto().setStatus(Status.DISPONIVEL);
        motoRepository.save(aluguel.getMoto());

        return aluguel;
    }
}