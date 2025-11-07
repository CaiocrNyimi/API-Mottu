package com.fiap.mottu_patio.controller;

import com.fiap.mottu_patio.model.Aluguel;
import com.fiap.mottu_patio.model.Moto;
import com.fiap.mottu_patio.model.User;
import com.fiap.mottu_patio.model.enums.Status;
import com.fiap.mottu_patio.repository.AluguelRepository;
import com.fiap.mottu_patio.repository.MotoRepository;
import com.fiap.mottu_patio.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/alugueis")
public class AluguelController {

    @Autowired
    private AluguelRepository aluguelRepository;

    @Autowired
    private MotoRepository motoRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String listarAlugueis(Model model) {
        List<Aluguel> alugueis = aluguelRepository.findAll();
        model.addAttribute("alugueis", alugueis);
        return "alugueis/list";
    }

    @GetMapping("/new")
    public String novoAluguelForm(Model model) {
        model.addAttribute("aluguel", new Aluguel());
        model.addAttribute("motos", motoRepository.findByStatus(Status.DISPONIVEL));
        model.addAttribute("users", userRepository.findAll());
        return "alugueis/form";
    }

    @GetMapping("/{id}")
    public String detalhesAluguel(@PathVariable Long id, Model model) {
        Optional<Aluguel> aluguel = aluguelRepository.findById(id);
        if (aluguel.isPresent()) {
            model.addAttribute("aluguel", aluguel.get());
            return "alugueis/details";
        }
        return "redirect:/alugueis";
    }

    @GetMapping("/edit/{id}")
    public String editarAluguelForm(@PathVariable Long id, Model model) {
        Optional<Aluguel> aluguel = aluguelRepository.findById(id);
        if (aluguel.isPresent()) {
            model.addAttribute("aluguel", aluguel.get());
            model.addAttribute("motos", motoRepository.findByStatus(Status.DISPONIVEL));
            model.addAttribute("users", userRepository.findAll());
            return "alugueis/form";
        }
        return "redirect:/alugueis";
    }

    @PostMapping
    public String criarAluguel(@RequestParam Long motoId,
                               @RequestParam Long userId,
                               @RequestParam String startDate,
                               @RequestParam String endDate,
                               RedirectAttributes redirectAttributes,
                               Model model) {
        return processarAluguel(null, motoId, userId, startDate, endDate, redirectAttributes, model);
    }

    @PostMapping("/{id}")
    public String atualizarAluguel(@PathVariable Long id,
                                   @RequestParam Long motoId,
                                   @RequestParam Long userId,
                                   @RequestParam String startDate,
                                   @RequestParam String endDate,
                                   RedirectAttributes redirectAttributes,
                                   Model model) {
        return processarAluguel(id, motoId, userId, startDate, endDate, redirectAttributes, model);
    }

    private String processarAluguel(Long id,
                                    Long motoId,
                                    Long userId,
                                    String startDateStr,
                                    String endDateStr,
                                    RedirectAttributes redirectAttributes,
                                    Model model) {
        try {
            LocalDateTime startDate = LocalDateTime.parse(startDateStr);
            LocalDateTime endDate = LocalDateTime.parse(endDateStr);

            Moto moto = motoRepository.findById(motoId)
                    .orElseThrow(() -> new IllegalArgumentException("Moto não encontrada."));
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));

            Aluguel aluguel = (id == null) ? new Aluguel() : aluguelRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Aluguel não encontrado."));

            aluguel.setMoto(moto);
            aluguel.setUser(user);
            aluguel.setStartDate(startDate);
            aluguel.setEndDate(endDate);
            aluguel.setStatus(Status.ALUGADA);

            aluguelRepository.save(aluguel);

            redirectAttributes.addFlashAttribute("message",
                    id == null ? "Aluguel criado com sucesso!" : "Aluguel atualizado com sucesso!");
            return "redirect:/alugueis";
        } catch (Exception ex) {
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("aluguel", new Aluguel());
            model.addAttribute("motos", motoRepository.findByStatus(Status.DISPONIVEL));
            model.addAttribute("users", userRepository.findAll());
            return "alugueis/form";
        }
    }

    @PostMapping("/return/{id}")
    public String devolverAluguel(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Optional<Aluguel> aluguelOpt = aluguelRepository.findById(id);
        if (aluguelOpt.isPresent()) {
            Aluguel aluguel = aluguelOpt.get();
            aluguel.setStatus(Status.DISPONIVEL);
            aluguelRepository.save(aluguel);
            redirectAttributes.addFlashAttribute("message", "Aluguel devolvido com sucesso!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Aluguel não encontrado.");
        }
        return "redirect:/alugueis";
    }
}