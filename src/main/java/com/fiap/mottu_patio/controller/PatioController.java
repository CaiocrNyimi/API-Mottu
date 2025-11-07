package com.fiap.mottu_patio.controller;

import com.fiap.mottu_patio.model.Patio;
import com.fiap.mottu_patio.repository.PatioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/patios")
public class PatioController {

    @Autowired
    private PatioRepository patioRepository;

    @GetMapping
    public String listarPatios(Model model) {
        model.addAttribute("patios", patioRepository.findAll());
        return "patios/list";
    }

    @GetMapping("/new")
    public String novoPatioForm(Model model) {
        model.addAttribute("patio", new Patio());
        return "patios/form";
    }

    @GetMapping("/{id}")
    public String detalhesPatio(@PathVariable Long id, Model model) {
        Optional<Patio> patio = patioRepository.findById(id);
        if (patio.isPresent()) {
            model.addAttribute("patio", patio.get());
            return "patios/details";
        }
        return "redirect:/patios";
    }

    @GetMapping("/edit/{id}")
    public String editarPatioForm(@PathVariable Long id, Model model) {
        Optional<Patio> patio = patioRepository.findById(id);
        if (patio.isPresent()) {
            model.addAttribute("patio", patio.get());
            return "patios/form";
        }
        return "redirect:/patios";
    }

    @PostMapping
    public String criarPatio(@ModelAttribute Patio patio,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        return processarPatio(null, patio, model, redirectAttributes);
    }

    @PostMapping("/{id}")
    public String atualizarPatio(@PathVariable Long id,
                                 @ModelAttribute Patio patio,
                                 Model model,
                                 RedirectAttributes redirectAttributes) {
        patio.setId(id);
        return processarPatio(id, patio, model, redirectAttributes);
    }

    private String processarPatio(Long id,
                                  Patio patio,
                                  Model model,
                                  RedirectAttributes redirectAttributes) {
        try {
            if (patio.getNome() == null || patio.getNome().isEmpty()) {
                throw new IllegalArgumentException("O campo 'Nome' não pode ser vazio.");
            }
            if (patio.getEndereco() == null || patio.getEndereco().isEmpty()) {
                throw new IllegalArgumentException("O campo 'Endereço' não pode ser vazio.");
            }
            if (patio.getCapacidade() == null || patio.getCapacidade() <= 0) {
                throw new IllegalArgumentException("A capacidade do pátio deve ser um número positivo.");
            }

            patioRepository.save(patio);
            redirectAttributes.addFlashAttribute("message",
                    id == null ? "Pátio criado com sucesso!" : "Pátio atualizado com sucesso!");
            return "redirect:/patios";
        } catch (Exception ex) {
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("patio", patio);
            return "patios/form";
        }
    }

    @PostMapping("/delete/{id}")
    public String deletarPatio(@PathVariable Long id, RedirectAttributes attributes) {
        try {
            patioRepository.deleteById(id);
            attributes.addFlashAttribute("message", "Pátio excluído com sucesso!");
        } catch (DataIntegrityViolationException ex) {
            attributes.addFlashAttribute("error", "Não é possível excluir este pátio porque ele ainda contém motos.");
        } catch (Exception ex) {
            attributes.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/patios";
    }
}