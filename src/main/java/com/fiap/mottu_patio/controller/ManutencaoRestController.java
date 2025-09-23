package com.fiap.mottu_patio.controller;

import com.fiap.mottu_patio.dto.ManutencaoRequest;
import com.fiap.mottu_patio.exception.BusinessException;
import com.fiap.mottu_patio.model.Manutencao;
import com.fiap.mottu_patio.model.Moto;
import com.fiap.mottu_patio.service.ManutencaoService;
import com.fiap.mottu_patio.service.MotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/manutencoes")
public class ManutencaoRestController {

    private final ManutencaoService manutencaoService;
    private final MotoService motoService;

    @Autowired
    public ManutencaoRestController(ManutencaoService manutencaoService, MotoService motoService) {
        this.manutencaoService = manutencaoService;
        this.motoService = motoService;
    }

    @GetMapping
    public ResponseEntity<List<Manutencao>> listarTodas() {
        return ResponseEntity.ok(manutencaoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Manutencao> buscarPorId(@PathVariable Long id) {
        return manutencaoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody ManutencaoRequest request) {
        try {
            Moto moto = motoService.findById(request.getMotoId())
                    .orElseThrow(() -> new IllegalArgumentException("Moto não encontrada."));
            Manutencao manutencao = new Manutencao();
            manutencao.setTipoServico(request.getTipoServico());
            manutencao.setDataManutencao(request.getDataManutencao());
            manutencao.setQuilometragem(request.getQuilometragem());
            manutencao.setMoto(moto);
            return ResponseEntity.ok(manutencaoService.save(manutencao));
        } catch (BusinessException | IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody ManutencaoRequest request) {
        try {
            Moto moto = motoService.findById(request.getMotoId())
                    .orElseThrow(() -> new IllegalArgumentException("Moto não encontrada."));
            Manutencao manutencao = manutencaoService.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Manutenção não encontrada."));
            manutencao.setTipoServico(request.getTipoServico());
            manutencao.setDataManutencao(request.getDataManutencao());
            manutencao.setQuilometragem(request.getQuilometragem());
            manutencao.setMoto(moto);
            return ResponseEntity.ok(manutencaoService.save(manutencao));
        } catch (BusinessException | IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        manutencaoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}