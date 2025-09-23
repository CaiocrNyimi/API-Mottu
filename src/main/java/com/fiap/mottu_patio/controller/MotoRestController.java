package com.fiap.mottu_patio.controller;

import com.fiap.mottu_patio.dto.MotoRequest;
import com.fiap.mottu_patio.exception.BusinessException;
import com.fiap.mottu_patio.exception.ResourceNotFoundException;
import com.fiap.mottu_patio.model.Moto;
import com.fiap.mottu_patio.model.Patio;
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

    @Autowired
    public MotoRestController(MotoService motoService, PatioService patioService) {
        this.motoService = motoService;
        this.patioService = patioService;
    }

    @GetMapping
    public ResponseEntity<List<Moto>> listarTodas() {
        return ResponseEntity.ok(motoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Moto> buscarPorId(@PathVariable Long id) {
        return motoService.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Moto não encontrada."));
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody MotoRequest request) {
        try {
            Patio patio = patioService.findById(request.getPatioId())
                    .orElseThrow(() -> new ResourceNotFoundException("Pátio não encontrado."));
            Moto moto = new Moto();
            moto.setPlaca(request.getPlaca());
            moto.setModelo(request.getModelo());
            moto.setCor(request.getCor());
            moto.setAno(request.getAno());
            moto.setQuilometragem(request.getQuilometragem());
            moto.setStatus(Status.DISPONIVEL);
            moto.setPatio(patio);
            return ResponseEntity.ok(motoService.save(moto));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody MotoRequest request) {
        try {
            Moto moto = motoService.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Moto não encontrada."));
            Patio patio = patioService.findById(request.getPatioId())
                    .orElseThrow(() -> new ResourceNotFoundException("Pátio não encontrado."));
            moto.setPlaca(request.getPlaca());
            moto.setModelo(request.getModelo());
            moto.setCor(request.getCor());
            moto.setAno(request.getAno());
            moto.setQuilometragem(request.getQuilometragem());
            moto.setPatio(patio);
            return ResponseEntity.ok(motoService.save(moto));
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