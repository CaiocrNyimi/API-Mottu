package com.fiap.mottu_patio.controller;

import com.fiap.mottu_patio.dto.MotoRequest;
import com.fiap.mottu_patio.dto.MotoResponse;
import com.fiap.mottu_patio.service.MotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/motos")
public class MotoRestController {

    @Autowired
    private MotoService motoService;

    @GetMapping
    public ResponseEntity<List<MotoResponse>> listarTodos() {
        return ResponseEntity.ok(motoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MotoResponse> buscarPorId(@PathVariable Long id) {
        return motoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MotoResponse> criar(@RequestBody MotoRequest request) {
        return ResponseEntity.ok(motoService.criar(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MotoResponse> atualizar(@PathVariable Long id, @RequestBody MotoRequest request) {
        return ResponseEntity.ok(motoService.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        motoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}