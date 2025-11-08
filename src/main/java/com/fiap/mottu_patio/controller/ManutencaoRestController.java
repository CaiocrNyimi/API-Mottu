package com.fiap.mottu_patio.controller;

import com.fiap.mottu_patio.service.ManutencaoService;
import com.fiap.mottu_patio.dto.ManutencaoRequest;
import com.fiap.mottu_patio.dto.ManutencaoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/manutencoes")
public class ManutencaoRestController {

    @Autowired
    private ManutencaoService manutencaoService;

    @GetMapping
    public ResponseEntity<List<ManutencaoResponse>> listarTodos() {
        return ResponseEntity.ok(manutencaoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ManutencaoResponse> buscarPorId(@PathVariable Long id) {
        return manutencaoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ManutencaoResponse> criar(@RequestBody ManutencaoRequest request) {
        return ResponseEntity.ok(manutencaoService.criar(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ManutencaoResponse> atualizar(@PathVariable Long id, @RequestBody ManutencaoRequest request) {
        return ResponseEntity.ok(manutencaoService.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        manutencaoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}