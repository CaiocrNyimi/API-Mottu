package com.fiap.mottu_patio.service;

import com.fiap.mottu_patio.dto.MotoRequest;
import com.fiap.mottu_patio.dto.MotoResponse;
import com.fiap.mottu_patio.exception.BusinessException;
import com.fiap.mottu_patio.exception.ResourceNotFoundException;
import com.fiap.mottu_patio.mapper.MotoMapper;
import com.fiap.mottu_patio.model.Moto;
import com.fiap.mottu_patio.model.Patio;
import com.fiap.mottu_patio.model.Vaga;
import com.fiap.mottu_patio.model.enums.ModeloMoto;
import com.fiap.mottu_patio.model.enums.Status;
import com.fiap.mottu_patio.repository.MotoRepository;
import com.fiap.mottu_patio.repository.PatioRepository;
import com.fiap.mottu_patio.repository.VagaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MotoService {

    @Autowired
    private MotoRepository motoRepository;

    @Autowired
    private PatioRepository patioRepository;

    @Autowired
    private VagaRepository vagaRepository;

    @Autowired
    private MotoMapper motoMapper;

    public List<MotoResponse> findAll() {
        return motoMapper.toResponseList(motoRepository.findAll());
    }

    public Optional<MotoResponse> findById(Long id) {
        return motoRepository.findById(id).map(motoMapper::toResponse);
    }

    public MotoResponse criar(MotoRequest request) {
        Patio patio = patioRepository.findById(request.getPatioId())
                .orElseThrow(() -> new ResourceNotFoundException("Pátio não encontrado."));

        String placaNormalizada = request.getPlaca().trim().toUpperCase();

        if (motoRepository.findByPlaca(placaNormalizada).isPresent()) {
            throw new BusinessException("Já existe uma moto com essa placa.");
        }

        Vaga vagaDisponivel = vagaRepository.findFirstByPatioIdAndOcupadaFalse(patio.getId())
                .orElseThrow(() -> new BusinessException("Não há vagas disponíveis neste pátio."));

        Moto moto = new Moto();
        moto.setPlaca(placaNormalizada);
        moto.setModelo(ModeloMoto.valueOf(request.getModelo().toUpperCase()));
        moto.setAno(request.getAno());
        moto.setQuilometragem(request.getQuilometragem());
        moto.setStatus(request.getStatus() != null ? request.getStatus() : Status.DISPONIVEL);
        moto.setPatio(patio);
        moto.setVaga(vagaDisponivel);

        Moto salva = motoRepository.save(moto);

        vagaDisponivel.setMoto(salva);
        vagaDisponivel.setOcupada(true);
        vagaRepository.save(vagaDisponivel);

        return motoMapper.toResponse(salva);
    }

    public MotoResponse atualizar(Long id, MotoRequest request) {
        Moto motoExistente = motoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Moto não encontrada."));

        Patio patio = patioRepository.findById(request.getPatioId())
                .orElseThrow(() -> new ResourceNotFoundException("Pátio não encontrado."));

        String novaPlaca = request.getPlaca().trim().toUpperCase();

        if (!motoExistente.getPlaca().equals(novaPlaca)) {
            boolean placaEmUso = motoRepository.findByPlaca(novaPlaca)
                    .filter(m -> !m.getId().equals(id))
                    .isPresent();

            if (placaEmUso) {
                throw new BusinessException("Já existe outra moto com essa placa.");
            }

            motoExistente.setPlaca(novaPlaca);
        }

        motoExistente.setModelo(ModeloMoto.valueOf(request.getModelo().toUpperCase()));
        motoExistente.setAno(request.getAno());
        motoExistente.setQuilometragem(request.getQuilometragem());
        motoExistente.setStatus(request.getStatus());
        motoExistente.setPatio(patio);

        return motoMapper.toResponse(motoRepository.save(motoExistente));
    }

    public void deleteById(Long id) {
        Moto moto = motoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Moto não encontrada."));
        motoRepository.delete(moto);
    }
}