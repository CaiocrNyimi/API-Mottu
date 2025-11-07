package com.fiap.mottu_patio.controller;

import com.fiap.mottu_patio.exception.BusinessException;
import com.fiap.mottu_patio.exception.ResourceNotFoundException;
import com.fiap.mottu_patio.model.Moto;
import com.fiap.mottu_patio.model.Patio;
import com.fiap.mottu_patio.model.enums.Status;
import com.fiap.mottu_patio.repository.MotoRepository;
import com.fiap.mottu_patio.repository.PatioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/motos")
public class MotoController {

    @Autowired
    private MotoRepository motoRepository;

    @Autowired
    private PatioRepository patioRepository;

    @GetMapping
    public String listarMotos(Model model) {
        model.addAttribute("motos", motoRepository.findAll());
        return "motos/list";
    }

    @GetMapping("/new")
    public String novaMotoForm(Model model) {
        model.addAttribute("moto", new Moto());
        model.addAttribute("patios", patioRepository.findAll());
        return "motos/form";
    }

    @GetMapping("/{id}")
    public String detalhesMoto(@PathVariable Long id, Model model) {
        Optional<Moto> moto = motoRepository.findById(id);
        if (moto.isPresent()) {
            model.addAttribute("moto", moto.get());
            return "motos/details";
        }
        return "redirect:/motos";
    }

    @GetMapping("/edit/{id}")
    public String editarMotoForm(@PathVariable Long id, Model model) {
        Optional<Moto> moto = motoRepository.findById(id);
        if (moto.isPresent()) {
            model.addAttribute("moto", moto.get());
            model.addAttribute("patios", patioRepository.findAll());
            return "motos/form";
        }
        return "redirect:/motos";
    }

    @PostMapping
    public String criarMoto(@Valid @ModelAttribute Moto moto,
                            @RequestParam(required = false) Long patio,
                            Model model,
                            RedirectAttributes redirectAttributes) {
        return processarMoto(null, moto, patio, model, redirectAttributes);
    }

    @PostMapping("/{id}")
    public String atualizarMoto(@PathVariable Long id,
                                @Valid @ModelAttribute Moto moto,
                                @RequestParam(required = false) Long patio,
                                Model model,
                                RedirectAttributes redirectAttributes) {
        moto.setId(id);
        return processarMoto(id, moto, patio, model, redirectAttributes);
    }

    private String processarMoto(Long id,
                                 Moto moto,
                                 Long patioId,
                                 Model model,
                                 RedirectAttributes redirectAttributes) {
        try {
            if (patioId == null) {
                throw new IllegalArgumentException("Por favor, selecione um pátio.");
            }

            Patio patio = patioRepository.findById(patioId)
                    .orElseThrow(() -> new ResourceNotFoundException("Pátio não encontrado."));
            moto.setPatio(patio);

            if (moto.getStatus() == null) {
                moto.setStatus(Status.DISPONIVEL);
            }

            motoRepository.save(moto);
            redirectAttributes.addFlashAttribute("message",
                    id == null ? "Moto cadastrada com sucesso!" : "Moto atualizada com sucesso!");
            return "redirect:/motos";
        } catch (Exception ex) {
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("moto", moto);
            model.addAttribute("patios", patioRepository.findAll());
            return "motos/form";
        }
    }

    @PostMapping("/delete/{id}")
    public String deletarMoto(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            Moto moto = motoRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Moto não encontrada."));
            if (moto.getStatus() != Status.DISPONIVEL) {
                throw new BusinessException("Não é possível excluir uma moto que não está disponível.");
            }
            motoRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("message", "Moto excluída com sucesso!");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/motos";
    }
}