package com.fiap.mottu_patio.controller;

import com.fiap.mottu_patio.dto.MotoRequest;
import com.fiap.mottu_patio.dto.MotoResponse;
import com.fiap.mottu_patio.exception.BusinessException;
import com.fiap.mottu_patio.exception.ResourceNotFoundException;
import com.fiap.mottu_patio.mapper.MotoMapper;
import com.fiap.mottu_patio.model.Moto;
import com.fiap.mottu_patio.model.Patio;
import com.fiap.mottu_patio.model.enums.ModeloMoto;
import com.fiap.mottu_patio.model.enums.Status;
import com.fiap.mottu_patio.service.MotoService;
import com.fiap.mottu_patio.service.PatioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/motos")
public class MotoRestController {

    private final MotoService motoService;
    private final PatioService patioService;
    private final MotoMapper motoMapper;

    @Autowired
    public MotoRestController(MotoService motoService, PatioService patioService, MotoMapper motoMapper) {
        this.motoService = motoService;
        this.patioService = patioService;
        this.motoMapper = motoMapper;
    }

    @GetMapping
    public ResponseEntity<List<MotoResponse>> listarTodas() {
        List<Moto> motos = motoService.findAll();
        List<MotoResponse> response = motoMapper.toResponseList(motos);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MotoResponse> buscarPorId(@PathVariable Long id) {
        Moto moto = motoService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Moto não encontrada."));
        return ResponseEntity.ok(motoMapper.toResponse(moto));
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody MotoRequest request) {
        try {
            Moto moto = new Moto();
            moto.setPlaca(request.getPlaca());
            moto.setModelo(ModeloMoto.valueOf(request.getModelo()));
            moto.setAno(request.getAno());
            moto.setQuilometragem(request.getQuilometragem());
            moto.setStatus(Status.DISPONIVEL);
            
            if (request.getPatioId() != null) {
                Patio patio = patioService.findById(request.getPatioId())
                        .orElseThrow(() -> new ResourceNotFoundException("Pátio não encontrado."));
                moto.setPatio(patio);
            }

            Moto saved = motoService.save(moto);
            return ResponseEntity.ok(motoMapper.toResponse(saved));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody MotoRequest request) {
        try {
            Moto moto = motoService.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Moto não encontrada."));
            
            moto.setPlaca(request.getPlaca());
            moto.setModelo(ModeloMoto.valueOf(request.getModelo()));
            moto.setAno(request.getAno());
            moto.setQuilometragem(request.getQuilometragem());

            if (request.getPatioId() != null) {
                Patio patio = patioService.findById(request.getPatioId())
                        .orElseThrow(() -> new ResourceNotFoundException("Pátio não encontrado."));
                moto.setPatio(patio);
            } else {
                moto.setPatio(null);
            }

            Moto updated = motoService.save(moto);
            return ResponseEntity.ok(motoMapper.toResponse(updated));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        try {
            Moto moto = motoService.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Moto não encontrada."));
            
            if (moto.getStatus() != Status.DISPONIVEL) {
                throw new BusinessException("Não é possível excluir uma moto que não está disponível.");
            }
            
            motoService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}