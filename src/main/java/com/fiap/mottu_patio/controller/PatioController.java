package com.fiap.mottu_patio.controller;

import com.fiap.mottu_patio.exception.ResourceNotFoundException;
import com.fiap.mottu_patio.model.Patio;
import com.fiap.mottu_patio.service.PatioService;
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

    private final PatioService patioService;

    @Autowired
    public PatioController(PatioService patioService) {
        this.patioService = patioService;
    }

    @GetMapping
    public String listPatios(Model model) {
        model.addAttribute("patios", patioService.findAll());
        return "patios/list";
    }

    @GetMapping("/new")
    public String showNewForm(Model model) {
        model.addAttribute("patio", new Patio());
        return "patios/form";
    }

    @PostMapping("/save")
    public String savePatio(@ModelAttribute Patio patio, Model model, RedirectAttributes redirectAttributes) {
        try {
            if (patio.getNome() == null || patio.getNome().isEmpty()) {
                throw new IllegalArgumentException("O campo 'Nome' não pode ser vazio.");
            }
            if (patio.getEndereco() == null || patio.getEndereco().isEmpty()) {
                throw new IllegalArgumentException("O campo 'Endereço' não pode ser vazio.");
            }
            if (patio.getCapacidade() <= 0) {
                throw new IllegalArgumentException("A capacidade do pátio deve ser um número positivo.");
            }

            if (patio.getId() == null) {
                patioService.save(patio);
                redirectAttributes.addFlashAttribute("message", "Pátio criado com sucesso!");
            } else {
                patioService.update(patio.getId(), patio);
                redirectAttributes.addFlashAttribute("message", "Pátio atualizado com sucesso!");
            }
            return "redirect:/patios";
        } catch (IllegalArgumentException | IllegalStateException | ResourceNotFoundException ex) {
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("patio", patio);
            return "patios/form";
        }
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<Patio> patio = patioService.findById(id);
        if (patio.isPresent()) {
            model.addAttribute("patio", patio.get());
            return "patios/form";
        }
        return "redirect:/patios";
    }

    @PostMapping("/delete/{id}")
    public String deletePatio(@PathVariable("id") Long id, RedirectAttributes attributes) {
        try {
            patioService.deleteById(id);
            attributes.addFlashAttribute("message", "Pátio excluído com sucesso!");
        } catch (DataIntegrityViolationException ex) {
            attributes.addFlashAttribute("error", "Não é possível excluir este pátio porque ele ainda contém motos.");
        } catch (ResourceNotFoundException | IllegalStateException ex) {
            attributes.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/patios";
    }
}