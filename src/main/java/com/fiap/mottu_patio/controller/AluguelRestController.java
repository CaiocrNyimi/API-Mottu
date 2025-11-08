package com.fiap.mottu_patio.controller;

import com.fiap.mottu_patio.service.AluguelService;
import com.fiap.mottu_patio.dto.AluguelRequest;
import com.fiap.mottu_patio.dto.AluguelResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alugueis")
public class AluguelRestController {

    @Autowired
    private AluguelService aluguelService;

    @GetMapping
    public ResponseEntity<List<AluguelResponse>> listarTodos() {
        return ResponseEntity.ok(aluguelService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AluguelResponse> buscarPorId(@PathVariable Long id) {
        return aluguelService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AluguelResponse> criar(@RequestBody AluguelRequest request) {
        return ResponseEntity.ok(aluguelService.criar(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AluguelResponse> atualizar(@PathVariable Long id, @RequestBody AluguelRequest request) {
        return ResponseEntity.ok(aluguelService.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        aluguelService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}