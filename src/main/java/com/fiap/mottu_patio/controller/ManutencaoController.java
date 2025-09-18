package com.fiap.mottu_patio.controller;

import com.fiap.mottu_patio.exception.BusinessException;
import com.fiap.mottu_patio.model.Manutencao;
import com.fiap.mottu_patio.model.Moto;
import com.fiap.mottu_patio.service.ManutencaoService;
import com.fiap.mottu_patio.service.MotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.validation.Valid;

import java.util.List;

@Controller
@RequestMapping("/manutencoes")
public class ManutencaoController {

    private final ManutencaoService manutencaoService;
    private final MotoService motoService;

    @Autowired
    public ManutencaoController(ManutencaoService manutencaoService, MotoService motoService) {
        this.manutencaoService = manutencaoService;
        this.motoService = motoService;
    }

    @GetMapping
    public String listManutencoes(Model model) {
        model.addAttribute("manutencoes", manutencaoService.findAll());
        return "manutencoes/list";
    }

    @GetMapping("/new")
    public String showNewForm(Model model) {
        List<Moto> motos = motoService.findAll();
        model.addAttribute("manutencao", new Manutencao());
        model.addAttribute("motos", motos);
        return "manutencoes/form";
    }

    @PostMapping("/save")
    public String saveManutencao(@Valid @ModelAttribute("manutencao") Manutencao manutencao,
                                 BindingResult bindingResult,
                                 @RequestParam("motoId") Long motoId,
                                 RedirectAttributes redirectAttributes,
                                 Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("manutencao", manutencao);
            model.addAttribute("motos", motoService.findAll());
            return "manutencoes/form";
        }
        try {
            Moto moto = motoService.findById(motoId)
                    .orElseThrow(() -> new IllegalArgumentException("Moto não encontrada."));
            manutencao.setMoto(moto);
            manutencaoService.save(manutencao);
            redirectAttributes.addFlashAttribute("message", "Manutenção registrada com sucesso!");
        } catch (IllegalArgumentException | BusinessException ex) {
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("manutencao", manutencao);
            model.addAttribute("motos", motoService.findAll());
            return "manutencoes/form";
        }
        return "redirect:/manutencoes";
    }
}