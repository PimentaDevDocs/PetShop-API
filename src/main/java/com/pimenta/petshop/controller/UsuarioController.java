package com.pimenta.petshop.controller;


import com.pimenta.petshop.model.UsuarioDTO;
import com.pimenta.petshop.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public UsuarioDTO createUsuario(@Valid @RequestBody UsuarioDTO dto) {
        return usuarioService.createUsuario(dto);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<UsuarioDTO> getAllUsuarios() {
        return usuarioService.getAllUsuarios();
    }

    @GetMapping("/{cpf}")
    @PreAuthorize("hasRole('ADMIN') || #cpf == authentication.name")
    public UsuarioDTO getUsuarioByCpf(@PathVariable String cpf) {
        return usuarioService.getUsuarioByCpf(cpf);
    }

    @DeleteMapping("/{cpf}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteAtendimento(@PathVariable String cpf) {
        usuarioService.deleteUsuario(cpf);
    }
}