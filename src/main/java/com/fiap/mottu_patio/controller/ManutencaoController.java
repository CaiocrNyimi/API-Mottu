package com.fiap.mottu_patio.controller;

import com.fiap.mottu_patio.exception.BusinessException;
import com.fiap.mottu_patio.model.Manutencao;
import com.fiap.mottu_patio.model.Moto;
import com.fiap.mottu_patio.service.ManutencaoService;
import com.fiap.mottu_patio.service.MotoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

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
        model.addAttribute("manutencao", new Manutencao());
        model.addAttribute("motos", motoService.findAll());
        return "manutencoes/form";
    }

    @GetMapping("/{id}")
    public String showManutencaoDetails(@PathVariable("id") Long id, Model model) {
        Optional<Manutencao> manutencao = manutencaoService.findById(id);
        if (manutencao.isPresent()) {
            model.addAttribute("manutencao", manutencao.get());
            return "manutencoes/details";
        }
        return "redirect:/manutencoes";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Optional<Manutencao> manutencao = manutencaoService.findById(id);
        if (manutencao.isPresent()) {
            model.addAttribute("manutencao", manutencao.get());
            model.addAttribute("motos", motoService.findAll());
            return "manutencoes/form";
        }
        return "redirect:/manutencoes";
    }

    @PostMapping
    public String createManutencao(@Valid @ModelAttribute("manutencao") Manutencao manutencao,
                                   BindingResult bindingResult,
                                   @RequestParam("motoId") Long motoId,
                                   RedirectAttributes redirectAttributes,
                                   Model model) {
        return saveOrUpdateManutencao(null, manutencao, bindingResult, motoId, redirectAttributes, model);
    }

    @PutMapping("/{id}")
    public String updateManutencao(@PathVariable("id") Long id,
                                   @Valid @ModelAttribute("manutencao") Manutencao manutencao,
                                   BindingResult bindingResult,
                                   @RequestParam("motoId") Long motoId,
                                   RedirectAttributes redirectAttributes,
                                   Model model) {
        manutencao.setId(id);
        return saveOrUpdateManutencao(id, manutencao, bindingResult, motoId, redirectAttributes, model);
    }

    private String saveOrUpdateManutencao(Long id,
                                          Manutencao manutencao,
                                          BindingResult bindingResult,
                                          Long motoId,
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
            redirectAttributes.addFlashAttribute("message",
                    id == null ? "Manutenção registrada com sucesso!" : "Manutenção atualizada com sucesso!");
            return "redirect:/manutencoes";
        } catch (IllegalArgumentException | BusinessException ex) {
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("manutencao", manutencao);
            model.addAttribute("motos", motoService.findAll());
            return "manutencoes/form";
        }
    }

    @DeleteMapping("/{id}")
    public String deleteManutencao(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            manutencaoService.deleteById(id);
            redirectAttributes.addFlashAttribute("message", "Manutenção excluída com sucesso!");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", "Erro ao excluir manutenção: " + ex.getMessage());
        }
        return "redirect:/manutencoes";
    }
}