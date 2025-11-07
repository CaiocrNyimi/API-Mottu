package com.fiap.mottu_patio.controller;

import com.fiap.mottu_patio.model.Manutencao;
import com.fiap.mottu_patio.service.ManutencaoService;
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
    public ResponseEntity<List<Manutencao>> listarTodos() {
        return ResponseEntity.ok(manutencaoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Manutencao> buscarPorId(@PathVariable Long id) {
        return manutencaoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Manutencao> criar(@RequestBody Manutencao manutencao) {
        return ResponseEntity.ok(manutencaoService.criar(manutencao));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Manutencao> atualizar(@PathVariable Long id, @RequestBody Manutencao manutencao) {
        return ResponseEntity.ok(manutencaoService.atualizar(id, manutencao));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        manutencaoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}