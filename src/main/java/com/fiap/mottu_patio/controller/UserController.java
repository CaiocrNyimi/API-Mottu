package com.fiap.mottu_patio.controller;

import com.fiap.mottu_patio.exception.BusinessException;
import com.fiap.mottu_patio.exception.ResourceNotFoundException;
import com.fiap.mottu_patio.model.User;
import com.fiap.mottu_patio.service.UserService;
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

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "users/list";
    }

    @GetMapping("/new")
    public String showNewForm(Model model) {
        model.addAttribute("user", new User());
        return "users/form";
    }

    @GetMapping("/{id}")
    public String showUserDetails(@PathVariable("id") Long id, Model model) {
        Optional<User> user = userService.findById(id);
        if (user.isPresent()) {
            model.addAttribute("user", user.get());
            return "users/details";
        }
        return "redirect:/users";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Optional<User> user = userService.findById(id);
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

    @PutMapping("/{id}")
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
            userService.save(user);
            redirectAttributes.addFlashAttribute("message",
                    isNew ? "Usuário criado com sucesso!" : "Usuário atualizado com sucesso!");
            return "redirect:/users";
        } catch (BusinessException ex) {
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("user", user);
            return "users/form";
        }
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            userService.deleteById(id);
            redirectAttributes.addFlashAttribute("message", "Usuário excluído com sucesso!");
        } catch (ResourceNotFoundException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/users";
    }
}