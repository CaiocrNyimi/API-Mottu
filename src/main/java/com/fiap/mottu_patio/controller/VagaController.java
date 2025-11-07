package com.fiap.mottu_patio.controller;

import com.fiap.mottu_patio.model.Patio;
import com.fiap.mottu_patio.model.Vaga;
import com.fiap.mottu_patio.repository.PatioRepository;
import com.fiap.mottu_patio.repository.VagaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/vagas")
public class VagaController {

    @Autowired
    private VagaRepository vagaRepository;

    @Autowired
    private PatioRepository patioRepository;

    @GetMapping
    public String listVagas(Model model) {
        model.addAttribute("vagas", vagaRepository.findAll());
        return "vagas/list";
    }

    @GetMapping("/new")
    public String showNewForm(Model model) {
        model.addAttribute("vaga", new Vaga());
        model.addAttribute("patios", patioRepository.findAll());
        return "vagas/form";
    }

    @GetMapping("/{id}")
    public String showVagaDetails(@PathVariable("id") Long id, Model model) {
        Optional<Vaga> vaga = vagaRepository.findById(id);
        if (vaga.isPresent()) {
            model.addAttribute("vaga", vaga.get());
            return "vagas/details";
        }
        return "redirect:/vagas";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Optional<Vaga> vaga = vagaRepository.findById(id);
        if (vaga.isPresent()) {
            model.addAttribute("vaga", vaga.get());
            model.addAttribute("patios", patioRepository.findAll());
            return "vagas/form";
        }
        return "redirect:/vagas";
    }

    @PostMapping
    public String createVaga(@ModelAttribute Vaga vaga,
                             @RequestParam(value = "patio", required = false) Long patioId,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        return saveOrUpdateVaga(null, vaga, patioId, model, redirectAttributes);
    }

    @PostMapping("/{id}")
    public String updateVaga(@PathVariable("id") Long id,
                             @ModelAttribute Vaga vaga,
                             @RequestParam(value = "patio", required = false) Long patioId,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        vaga.setId(id);
        return saveOrUpdateVaga(id, vaga, patioId, model, redirectAttributes);
    }

    private String saveOrUpdateVaga(Long id,
                                    Vaga vaga,
                                    Long patioId,
                                    Model model,
                                    RedirectAttributes redirectAttributes) {
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

            Patio patio = patioRepository.findById(patioId)
                    .orElseThrow(() -> new IllegalArgumentException("Pátio não encontrado."));
            vaga.setPatio(patio);

            vagaRepository.save(vaga);
            redirectAttributes.addFlashAttribute("message",
                    id == null ? "Vaga criada com sucesso!" : "Vaga atualizada com sucesso!");
            return "redirect:/vagas";
        } catch (Exception ex) {
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("vaga", vaga);
            model.addAttribute("patios", patioRepository.findAll());
            return "vagas/form";
        }
    }

    @PostMapping("/delete/{id}")
    public String deleteVaga(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            vagaRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("message", "Vaga excluída com sucesso!");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", "Erro ao excluir vaga: " + ex.getMessage());
        }
        return "redirect:/vagas";
    }
}