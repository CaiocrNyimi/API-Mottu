package com.fiap.mottu_patio.controller;

import com.fiap.mottu_patio.model.Aluguel;
import com.fiap.mottu_patio.service.AluguelService;
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
    public ResponseEntity<List<Aluguel>> listarTodos() {
        return ResponseEntity.ok(aluguelService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Aluguel> buscarPorId(@PathVariable Long id) {
        return aluguelService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Aluguel> criar(@RequestBody Aluguel aluguel) {
        return ResponseEntity.ok(aluguelService.criar(aluguel));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Aluguel> atualizar(@PathVariable Long id, @RequestBody Aluguel aluguel) {
        return ResponseEntity.ok(aluguelService.atualizar(id, aluguel));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        aluguelService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}