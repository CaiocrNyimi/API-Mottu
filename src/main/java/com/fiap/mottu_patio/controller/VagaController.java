package com.fiap.mottu_patio.controller;

import com.fiap.mottu_patio.exception.ResourceNotFoundException;
import com.fiap.mottu_patio.model.Patio;
import com.fiap.mottu_patio.model.Vaga;
import com.fiap.mottu_patio.service.PatioService;
import com.fiap.mottu_patio.service.VagaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/vagas")
public class VagaController {

    private final VagaService vagaService;
    private final PatioService patioService;

    @Autowired
    public VagaController(VagaService vagaService, PatioService patioService) {
        this.vagaService = vagaService;
        this.patioService = patioService;
    }

    @GetMapping
    public String listVagas(Model model) {
        model.addAttribute("vagas", vagaService.findAll());
        return "vagas/list";
    }

    @GetMapping("/new")
    public String showNewForm(Model model) {
        model.addAttribute("vaga", new Vaga());
        model.addAttribute("patios", patioService.findAll());
        return "vagas/form";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Optional<Vaga> vaga = vagaService.findById(id);
        if (vaga.isPresent()) {
            model.addAttribute("vaga", vaga.get());
            model.addAttribute("patios", patioService.findAll());
            return "vagas/form";
        }
        return "redirect:/vagas";
    }

    @PostMapping("/save")
    public String saveVaga(@ModelAttribute Vaga vaga,
                           @RequestParam(value = "patio", required = false) Long patioId,
                           Model model) {
        try {
            if (vaga.getIdentificador() == null || vaga.getIdentificador().isEmpty()) {
                throw new IllegalArgumentException("O campo 'Identificador' não pode ser vazio.");
            }
            if (vaga.getCodigo() == null || vaga.getCodigo().isEmpty()) {
                throw new IllegalArgumentException("O campo 'Código' não pode ser vazio.");
            }
            if (patioId == null) {
                throw new IllegalArgumentException("Por favor, selecione um pátio.");
            }
            
            Patio patio = patioService.findById(patioId)
                                      .orElseThrow(() -> new ResourceNotFoundException("Pátio não encontrado."));
            vaga.setPatio(patio);

            vagaService.save(vaga);
            return "redirect:/vagas";
        } catch (IllegalArgumentException | IllegalStateException | ResourceNotFoundException ex) {
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("vaga", vaga);
            model.addAttribute("patios", patioService.findAll());
            return "vagas/form";
        }
    }

    @PostMapping("/delete/{id}")
    public String deleteVaga(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            vagaService.deleteById(id);
            redirectAttributes.addFlashAttribute("message", "Vaga excluída com sucesso!");
        } catch (ResourceNotFoundException | IllegalStateException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/vagas";
    }
}