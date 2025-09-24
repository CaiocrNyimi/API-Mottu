package com.fiap.mottu_patio.controller;

import com.fiap.mottu_patio.dto.VagaRequest;
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
import java.util.stream.Collectors;

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
    public ResponseEntity<List<VagaResponse>> listarTodas() {
        List<Vaga> vagas = vagaService.findAll();
        List<VagaResponse> response = vagas.stream()
                .map(VagaResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VagaResponse> buscarPorId(@PathVariable Long id) {
        return vagaService.findById(id)
                .map(vaga -> ResponseEntity.ok(new VagaResponse(vaga)))
                .orElseThrow(() -> new ResourceNotFoundException("Vaga não encontrada."));
    }

    @PostMapping
    public ResponseEntity<VagaResponse> criar(@RequestBody VagaRequest request) {
        try {
            Patio patio = patioService.findById(request.getPatioId())
                    .orElseThrow(() -> new ResourceNotFoundException("Pátio não encontrado."));
            Vaga vaga = new Vaga();
            vaga.setIdentificador(request.getIdentificador());
            vaga.setCodigo(request.getCodigo());
            vaga.setOcupada(false);
            vaga.setPatio(patio);
            Vaga novaVaga = vagaService.save(vaga);
            return ResponseEntity.ok(new VagaResponse(novaVaga));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<VagaResponse> atualizar(@PathVariable Long id, @RequestBody VagaRequest request) {
        try {
            Vaga vaga = vagaService.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Vaga não encontrada."));
            Patio patio = patioService.findById(request.getPatioId())
                    .orElseThrow(() -> new ResourceNotFoundException("Pátio não encontrado."));
            vaga.setIdentificador(request.getIdentificador());
            vaga.setCodigo(request.getCodigo());
            vaga.setPatio(patio);
            Vaga vagaAtualizada = vagaService.save(vaga);
            return ResponseEntity.ok(new VagaResponse(vagaAtualizada));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        vagaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}