package com.fiap.mottu_patio.controller;

import com.fiap.mottu_patio.model.enums.Status;
import com.fiap.mottu_patio.exception.BusinessException;
import com.fiap.mottu_patio.exception.ResourceNotFoundException;
import com.fiap.mottu_patio.model.Aluguel;
import com.fiap.mottu_patio.model.Moto;
import com.fiap.mottu_patio.model.User;
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
        List<Aluguel> alugueis = aluguelService.findAll();
        model.addAttribute("alugueis", alugueis);
        return "alugueis/list";
    }

    @GetMapping("/new")
    public String showNewForm(Model model) {
        List<Moto> motosDisponiveis = motoService.findByStatus((Status.DISPONIVEL));
        List<User> users = userService.findAll();
        model.addAttribute("aluguel", new Aluguel());
        model.addAttribute("motos", motosDisponiveis);
        model.addAttribute("users", users);
        return "alugueis/form";
    }

    @PostMapping("/save")
    public String saveAluguel(@RequestParam("motoId") Long motoId,
                              @RequestParam("userId") Long userId,
                              @RequestParam("startDate") String startDateStr,
                              @RequestParam("endDate") String endDateStr,
                              Model model,
                              RedirectAttributes redirectAttributes) {
        try {
            LocalDateTime startDate = LocalDateTime.parse(startDateStr);
            LocalDateTime endDate = LocalDateTime.parse(endDateStr);
            aluguelService.reserveBike(userId, motoId, startDate, endDate);
            redirectAttributes.addFlashAttribute("message", "Aluguel salvo com sucesso!");
            return "redirect:/alugueis";
        } catch (BusinessException | IllegalArgumentException ex) {
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("motos", motoService.findByStatus((Status.DISPONIVEL)));
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