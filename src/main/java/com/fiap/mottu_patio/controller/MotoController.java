package com.fiap.mottu_patio.controller;

import com.fiap.mottu_patio.exception.BusinessException;
import com.fiap.mottu_patio.exception.ResourceNotFoundException;
import com.fiap.mottu_patio.model.Moto;
import com.fiap.mottu_patio.model.Patio;
import com.fiap.mottu_patio.service.MotoService;
import com.fiap.mottu_patio.service.PatioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/motos")
public class MotoController {

    private final MotoService motoService;
    private final PatioService patioService;

    @Autowired
    public MotoController(MotoService motoService, PatioService patioService) {
        this.motoService = motoService;
        this.patioService = patioService;
    }

    @GetMapping
    public String listMotos(Model model) {
        model.addAttribute("motos", motoService.findAll());
        return "motos/list";
    }

    @GetMapping("/new")
    public String showNewForm(Model model) {
        model.addAttribute("moto", new Moto());
        model.addAttribute("patios", patioService.findAll());
        return "motos/form";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Optional<Moto> moto = motoService.findById(id);
        if (moto.isPresent()) {
            model.addAttribute("moto", moto.get());
            model.addAttribute("patios", patioService.findAll());
            return "motos/form";
        }
        return "redirect:/motos";
    }

    @PostMapping("/save")
    public String saveMoto(@ModelAttribute Moto moto,
                           @RequestParam(value = "patio", required = false) Long patioId,
                           Model model) {
        try {
            if (moto.getPlaca() == null || moto.getPlaca().isEmpty()) {
                throw new IllegalArgumentException("O campo 'Placa' não pode ser vazio.");
            }
            if (moto.getModelo() == null || moto.getModelo().isEmpty()) {
                throw new IllegalArgumentException("O campo 'Modelo' não pode ser vazio.");
            }
            if (moto.getCor() == null || moto.getCor().isEmpty()) {
                throw new IllegalArgumentException("O campo 'Cor' não pode ser vazio.");
            }
            if (moto.getAno() == null) {
                throw new IllegalArgumentException("O campo 'Ano' não pode ser vazio.");
            }
            if (patioId == null) {
                throw new IllegalArgumentException("Por favor, selecione um pátio.");
            }

            Patio patio = patioService.findById(patioId)
                                      .orElseThrow(() -> new ResourceNotFoundException("Pátio não encontrado."));
            moto.setPatio(patio);

            motoService.save(moto);
            return "redirect:/motos";
        } catch (IllegalArgumentException | ResourceNotFoundException | BusinessException ex) {
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("moto", moto);
            model.addAttribute("patios", patioService.findAll());
            return "motos/form";
        }
    }

    @PostMapping("/delete/{id}")
    public String deleteMoto(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            motoService.deleteById(id);
            redirectAttributes.addFlashAttribute("message", "Moto excluída com sucesso!");
        } catch (ResourceNotFoundException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/motos";
    }
}