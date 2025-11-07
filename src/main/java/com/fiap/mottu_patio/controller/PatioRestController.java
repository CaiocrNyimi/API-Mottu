package com.fiap.mottu_patio.controller;

import com.fiap.mottu_patio.dto.PatioRequest;
import com.fiap.mottu_patio.dto.PatioResponse;
import com.fiap.mottu_patio.service.PatioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patios")
public class PatioRestController {

    @Autowired
    private PatioService patioService;

    @GetMapping
    public ResponseEntity<List<PatioResponse>> listarTodos() {
        return ResponseEntity.ok(patioService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatioResponse> buscarPorId(@PathVariable Long id) {
        return patioService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PatioResponse> criar(@RequestBody PatioRequest request) {
        return ResponseEntity.ok(patioService.criar(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatioResponse> atualizar(@PathVariable Long id, @RequestBody PatioRequest request) {
        return ResponseEntity.ok(patioService.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        patioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}