package com.fiap.mottu_patio.controller;

import com.fiap.mottu_patio.exception.BusinessException;
import com.fiap.mottu_patio.model.User;
import com.fiap.mottu_patio.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private final UserService userService;

    @Autowired
    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping
    public String processRegistration(@Valid @ModelAttribute("user") User user,
                                      BindingResult bindingResult,
                                      Model model,
                                      RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", user);
            return "users/register";
        }

        try {
            userService.save(user);
            redirectAttributes.addFlashAttribute("message", "Registro realizado com sucesso!");
            return "redirect:/login";
        } catch (BusinessException ex) {
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("user", user);
            return "users/register";
        }
    }
}