package com.pimenta.petshop.controller;

import com.pimenta.petshop.model.AuthResponse;
import com.pimenta.petshop.model.UsuarioDTO;
import com.pimenta.petshop.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Gerar usuário",
            description = "Cria um novo usuário no sistema. Sem permissão de admin, caso o banco esteja vazio"
    )
    public ResponseEntity<AuthResponse> register(@RequestBody UsuarioDTO usuarioDTO) {
        return ResponseEntity.ok(authService.register(usuarioDTO));
    }

    @PostMapping("/login")
    @Operation(
            summary = "Login",
            description = "Retorna um token se o usuario for válido."
    )
    public ResponseEntity<AuthResponse> login(@RequestBody UsuarioDTO usuarioDTO) {
        return ResponseEntity.ok(authService.login(usuarioDTO));
    }
}