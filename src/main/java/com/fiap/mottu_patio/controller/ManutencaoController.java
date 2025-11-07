package com.fiap.mottu_patio.controller;

import com.fiap.mottu_patio.model.Manutencao;
import com.fiap.mottu_patio.model.Moto;
import com.fiap.mottu_patio.repository.ManutencaoRepository;
import com.fiap.mottu_patio.repository.MotoRepository;
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

    @Autowired
    private ManutencaoRepository manutencaoRepository;

    @Autowired
    private MotoRepository motoRepository;

    @GetMapping
    public String listarManutencoes(Model model) {
        model.addAttribute("manutencoes", manutencaoRepository.findAll());
        return "manutencoes/list";
    }

    @GetMapping("/new")
    public String novaManutencaoForm(Model model) {
        model.addAttribute("manutencao", new Manutencao());
        model.addAttribute("motos", motoRepository.findAll());
        return "manutencoes/form";
    }

    @GetMapping("/{id}")
    public String detalhesManutencao(@PathVariable Long id, Model model) {
        Optional<Manutencao> manutencao = manutencaoRepository.findById(id);
        if (manutencao.isPresent()) {
            model.addAttribute("manutencao", manutencao.get());
            return "manutencoes/details";
        }
        return "redirect:/manutencoes";
    }

    @GetMapping("/edit/{id}")
    public String editarManutencaoForm(@PathVariable Long id, Model model) {
        Optional<Manutencao> manutencao = manutencaoRepository.findById(id);
        if (manutencao.isPresent()) {
            model.addAttribute("manutencao", manutencao.get());
            model.addAttribute("motos", motoRepository.findAll());
            return "manutencoes/form";
        }
        return "redirect:/manutencoes";
    }

    @PostMapping
    public String criarManutencao(@Valid @ModelAttribute("manutencao") Manutencao manutencao,
                                  BindingResult bindingResult,
                                  @RequestParam Long motoId,
                                  RedirectAttributes redirectAttributes,
                                  Model model) {
        return processarManutencao(null, manutencao, bindingResult, motoId, redirectAttributes, model);
    }

    @PostMapping("/{id}")
    public String atualizarManutencao(@PathVariable Long id,
                                      @Valid @ModelAttribute("manutencao") Manutencao manutencao,
                                      BindingResult bindingResult,
                                      @RequestParam Long motoId,
                                      RedirectAttributes redirectAttributes,
                                      Model model) {
        manutencao.setId(id);
        return processarManutencao(id, manutencao, bindingResult, motoId, redirectAttributes, model);
    }

    private String processarManutencao(Long id,
                                       Manutencao manutencao,
                                       BindingResult bindingResult,
                                       Long motoId,
                                       RedirectAttributes redirectAttributes,
                                       Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("manutencao", manutencao);
            model.addAttribute("motos", motoRepository.findAll());
            return "manutencoes/form";
        }

        try {
            Moto moto = motoRepository.findById(motoId)
                    .orElseThrow(() -> new IllegalArgumentException("Moto não encontrada."));
            manutencao.setMoto(moto);
            manutencaoRepository.save(manutencao);
            redirectAttributes.addFlashAttribute("message",
                    id == null ? "Manutenção registrada com sucesso!" : "Manutenção atualizada com sucesso!");
            return "redirect:/manutencoes";
        } catch (Exception ex) {
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("manutencao", manutencao);
            model.addAttribute("motos", motoRepository.findAll());
            return "manutencoes/form";
        }
    }

    @PostMapping("/delete/{id}")
    public String deletarManutencao(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            manutencaoRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("message", "Manutenção excluída com sucesso!");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", "Erro ao excluir manutenção: " + ex.getMessage());
        }
        return "redirect:/manutencoes";
    }
}