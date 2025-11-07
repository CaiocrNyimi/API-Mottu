package com.fiap.mottu_patio.service;

import com.fiap.mottu_patio.dto.LoginRequest;
import com.fiap.mottu_patio.dto.LoginResponse;
import com.fiap.mottu_patio.dto.UserRequest;
import com.fiap.mottu_patio.exception.BusinessException;
import com.fiap.mottu_patio.model.User;
import com.fiap.mottu_patio.model.enums.Role;
import com.fiap.mottu_patio.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private SecurityContextRepository securityContextRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ResponseEntity<LoginResponse> login(LoginRequest request, HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            SecurityContextImpl context = new SecurityContextImpl();
            context.setAuthentication(authentication);
            securityContextRepository.saveContext(context, httpRequest, httpResponse);

            Optional<User> userOptional = userRepository.findByEmail(request.getEmail());

            if (userOptional.isPresent()) {
                User user = userOptional.get();
                return ResponseEntity.ok(new LoginResponse(user.getUsername(), "Login realizado com sucesso"));
            } else {
                return ResponseEntity.internalServerError()
                        .body(new LoginResponse(null, "Usuário não encontrado após autenticação"));
            }
        } catch (AuthenticationException ex) {
            return ResponseEntity.status(401)
                    .body(new LoginResponse("Credenciais inválidas", null));
        }
    }

    public ResponseEntity<?> register(UserRequest request) {
        try {
            if (userRepository.findByEmail(request.getEmail()).isPresent()) {
                throw new BusinessException("Usuário já existe com esse e-mail.");
            }

            User user = new User();
            user.setUsername(request.getUsername());
            user.setEmail(request.getEmail());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setRole(Role.valueOf(request.getRole().toUpperCase()));

            return ResponseEntity.ok(userRepository.save(user));
        } catch (BusinessException | IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}