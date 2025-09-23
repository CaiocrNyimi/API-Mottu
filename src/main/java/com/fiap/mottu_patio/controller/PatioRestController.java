package com.fiap.mottu_patio.controller;

import com.fiap.mottu_patio.exception.ResourceNotFoundException;
import com.fiap.mottu_patio.model.Patio;
import com.fiap.mottu_patio.service.PatioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patios")
public class PatioRestController {

    private final PatioService patioService;

    @Autowired
    public PatioRestController(PatioService patioService) {
        this.patioService = patioService;
    }

    @GetMapping
    public ResponseEntity<List<Patio>> listarTodos() {
        return ResponseEntity.ok(patioService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patio> buscarPorId(@PathVariable Long id) {
        return patioService.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Pátio não encontrado."));
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody Patio patio) {
        try {
            validar(patio);
            return ResponseEntity.ok(patioService.save(patio));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Patio patio) {
        try {
            validar(patio);
            patio.setId(id);
            return ResponseEntity.ok(patioService.update(id, patio));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        try {
            patioService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (DataIntegrityViolationException ex) {
            return ResponseEntity.badRequest().body("Não é possível excluir este pátio porque ele ainda contém motos.");
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    private void validar(Patio patio) {
        if (patio.getNome() == null || patio.getNome().isEmpty()) {
            throw new IllegalArgumentException("O campo 'Nome' não pode ser vazio.");
        }
        if (patio.getEndereco() == null || patio.getEndereco().isEmpty()) {
            throw new IllegalArgumentException("O campo 'Endereço' não pode ser vazio.");
        }
        if (patio.getCapacidade() == null || patio.getCapacidade() <= 0) {
            throw new IllegalArgumentException("A capacidade do pátio deve ser um número positivo.");
        }
    }
}