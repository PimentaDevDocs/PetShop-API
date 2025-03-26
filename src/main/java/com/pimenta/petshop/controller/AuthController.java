package com.pimenta.petshop.controller;

import com.pimenta.petshop.model.AuthResponse;
import com.pimenta.petshop.model.UsuarioDTO;
import com.pimenta.petshop.service.AuthService;
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
    public ResponseEntity<AuthResponse> register(@RequestBody UsuarioDTO usuarioDTO) {
        return ResponseEntity.ok(authService.register(usuarioDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody UsuarioDTO usuarioDTO) {
        return ResponseEntity.ok(authService.login(usuarioDTO));
    }
}