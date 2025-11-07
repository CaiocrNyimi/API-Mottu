package com.fiap.mottu_patio.controller;

import com.fiap.mottu_patio.model.User;
import com.fiap.mottu_patio.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "users/list";
    }

    @GetMapping("/new")
    public String showNewForm(Model model) {
        model.addAttribute("user", new User());
        return "users/form";
    }

    @GetMapping("/{id}")
    public String showUserDetails(@PathVariable("id") Long id, Model model) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            model.addAttribute("user", user.get());
            return "users/details";
        }
        return "redirect:/users";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            model.addAttribute("user", user.get());
            return "users/form";
        }
        return "redirect:/users";
    }

    @PostMapping
    public String createUser(@Valid @ModelAttribute("user") User user,
                             BindingResult bindingResult,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        return saveOrUpdateUser(user, bindingResult, model, redirectAttributes, true);
    }

    @PostMapping("/{id}")
    public String updateUser(@PathVariable("id") Long id,
                             @Valid @ModelAttribute("user") User user,
                             BindingResult bindingResult,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        user.setId(id);
        return saveOrUpdateUser(user, bindingResult, model, redirectAttributes, false);
    }

    private String saveOrUpdateUser(User user,
                                    BindingResult bindingResult,
                                    Model model,
                                    RedirectAttributes redirectAttributes,
                                    boolean isNew) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", user);
            return "users/form";
        }
        try {
            userRepository.save(user);
            redirectAttributes.addFlashAttribute("message",
                    isNew ? "Usuário criado com sucesso!" : "Usuário atualizado com sucesso!");
            return "redirect:/users";
        } catch (Exception ex) {
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("user", user);
            return "users/form";
        }
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            userRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("message", "Usuário excluído com sucesso!");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", "Erro ao excluir usuário: " + ex.getMessage());
        }
        return "redirect:/users";
    }
}