package com.fiap.mottu_patio.controller;

import com.fiap.mottu_patio.dto.AluguelRequest;
import com.fiap.mottu_patio.exception.ResourceNotFoundException;
import com.fiap.mottu_patio.model.Aluguel;
import com.fiap.mottu_patio.service.AluguelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alugueis")
public class AluguelRestController {

    private final AluguelService aluguelService;

    @Autowired
    public AluguelRestController(AluguelService aluguelService) {
        this.aluguelService = aluguelService;
    }

    @GetMapping
    public ResponseEntity<List<Aluguel>> listarTodos() {
        return ResponseEntity.ok(aluguelService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Aluguel> buscarPorId(@PathVariable Long id) {
        return aluguelService.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Aluguel não encontrado."));
    }

    @PostMapping
    public ResponseEntity<Aluguel> criarAluguel(@RequestBody AluguelRequest request) {
        Aluguel aluguel = aluguelService.reserveBike(
                request.getUserId(),
                request.getMotoId(),
                request.getStartDate(),
                request.getEndDate()
        );
        return ResponseEntity.ok(aluguel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Aluguel> atualizarAluguel(@PathVariable Long id, @RequestBody AluguelRequest request) {
        Aluguel aluguel = aluguelService.updateAluguel(
                id,
                request.getUserId(),
                request.getMotoId(),
                request.getStartDate(),
                request.getEndDate()
        );
        return ResponseEntity.ok(aluguel);
    }

    @PutMapping("/return/{id}")
    public ResponseEntity<Aluguel> devolverAluguel(@PathVariable Long id) {
        Aluguel aluguel = aluguelService.returnBike(id);
        return ResponseEntity.ok(aluguel);
    }
}