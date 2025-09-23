package com.fiap.mottu_patio.controller;

import com.fiap.mottu_patio.dto.VagaResponse;
import com.fiap.mottu_patio.exception.ResourceNotFoundException;
import com.fiap.mottu_patio.model.Patio;
import com.fiap.mottu_patio.model.Vaga;
import com.fiap.mottu_patio.service.PatioService;
import com.fiap.mottu_patio.service.VagaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vagas")
public class VagaRestController {

    private final VagaService vagaService;
    private final PatioService patioService;

    @Autowired
    public VagaRestController(VagaService vagaService, PatioService patioService) {
        this.vagaService = vagaService;
        this.patioService = patioService;
    }

    @GetMapping
    public ResponseEntity<List<Vaga>> listarTodas() {
        return ResponseEntity.ok(vagaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vaga> buscarPorId(@PathVariable Long id) {
        return vagaService.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Vaga não encontrada."));
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody VagaResponse request) {
        try {
            Patio patio = patioService.findById(request.getPatioId())
                    .orElseThrow(() -> new ResourceNotFoundException("Pátio não encontrado."));
            Vaga vaga = new Vaga();
            vaga.setIdentificador(request.getIdentificador());
            vaga.setCodigo(request.getCodigo());
            vaga.setPatio(patio);
            return ResponseEntity.ok(vagaService.save(vaga));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody VagaResponse request) {
        try {
            Vaga vaga = vagaService.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Vaga não encontrada."));
            Patio patio = patioService.findById(request.getPatioId())
                    .orElseThrow(() -> new ResourceNotFoundException("Pátio não encontrado."));
            vaga.setIdentificador(request.getIdentificador());
            vaga.setCodigo(request.getCodigo());
            vaga.setPatio(patio);
            return ResponseEntity.ok(vagaService.save(vaga));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        vagaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}