package com.fiap.mottu_patio.controller;

import com.fiap.mottu_patio.model.enums.Status;
import com.fiap.mottu_patio.dto.AluguelResponse;
import com.fiap.mottu_patio.exception.BusinessException;
import com.fiap.mottu_patio.exception.ResourceNotFoundException;
import com.fiap.mottu_patio.model.Aluguel;
import com.fiap.mottu_patio.service.AluguelService;
import com.fiap.mottu_patio.service.MotoService;
import com.fiap.mottu_patio.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/alugueis")
public class AluguelController {

    private final AluguelService aluguelService;
    private final MotoService motoService;
    private final UserService userService;

    @Autowired
    public AluguelController(AluguelService aluguelService, MotoService motoService, UserService userService) {
        this.aluguelService = aluguelService;
        this.motoService = motoService;
        this.userService = userService;
    }

    @GetMapping
    public String listAlugueis(Model model) {
        List<AluguelResponse> alugueis = aluguelService.findAllResponses();
        model.addAttribute("alugueis", alugueis);
        return "alugueis/list";
    }

    @GetMapping("/new")
    public String showNewForm(Model model) {
        model.addAttribute("aluguel", new Aluguel());
        model.addAttribute("motos", motoService.findByStatus(Status.DISPONIVEL));
        model.addAttribute("users", userService.findAll());
        return "alugueis/form";
    }

    @GetMapping("/{id}")
    public String showAluguelDetails(@PathVariable("id") Long id, Model model) {
        Aluguel aluguel = aluguelService.findById(id).orElse(null);
        if (aluguel != null) {
            model.addAttribute("aluguel", aluguel);
            return "alugueis/details";
        }
        return "redirect:/alugueis";
    }

    @PostMapping
    public String createAluguel(@RequestParam("motoId") Long motoId,
                                @RequestParam("userId") Long userId,
                                @RequestParam("startDate") String startDateStr,
                                @RequestParam("endDate") String endDateStr,
                                Model model,
                                RedirectAttributes redirectAttributes) {
        return processAluguel(null, motoId, userId, startDateStr, endDateStr, model, redirectAttributes);
    }

    @PutMapping("/{id}")
    public String updateAluguel(@PathVariable("id") Long id,
                                @RequestParam("motoId") Long motoId,
                                @RequestParam("userId") Long userId,
                                @RequestParam("startDate") String startDateStr,
                                @RequestParam("endDate") String endDateStr,
                                Model model,
                                RedirectAttributes redirectAttributes) {
        return processAluguel(id, motoId, userId, startDateStr, endDateStr, model, redirectAttributes);
    }

    private String processAluguel(Long id,
                                  Long motoId,
                                  Long userId,
                                  String startDateStr,
                                  String endDateStr,
                                  Model model,
                                  RedirectAttributes redirectAttributes) {
        try {
            LocalDateTime startDate = LocalDateTime.parse(startDateStr);
            LocalDateTime endDate = LocalDateTime.parse(endDateStr);

            if (id == null) {
                aluguelService.reserveBike(userId, motoId, startDate, endDate);
                redirectAttributes.addFlashAttribute("message", "Aluguel criado com sucesso!");
            } else {
                aluguelService.updateAluguel(id, userId, motoId, startDate, endDate);
                redirectAttributes.addFlashAttribute("message", "Aluguel atualizado com sucesso!");
            }

            return "redirect:/alugueis";
        } catch (BusinessException | IllegalArgumentException | ResourceNotFoundException ex) {
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("aluguel", new Aluguel());
            model.addAttribute("motos", motoService.findByStatus(Status.DISPONIVEL));
            model.addAttribute("users", userService.findAll());
            return "alugueis/form";
        }
    }

    @PostMapping("/return/{id}")
    public String returnAluguel(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            aluguelService.returnBike(id);
            redirectAttributes.addFlashAttribute("message", "Aluguel devolvido com sucesso!");
        } catch (ResourceNotFoundException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/alugueis";
    }
}