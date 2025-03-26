package com.pimenta.petshop.controller;


import com.pimenta.petshop.model.UsuarioDTO;
import com.pimenta.petshop.security.SecurityConfig;
import com.pimenta.petshop.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
@Tag(name = "Usuarios", description = "Coisas a ver com Usuario")
@SecurityRequirement(name = SecurityConfig.SECURITY)
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Gerar usuario", description = "Metodo para criar usuario")
    public UsuarioDTO createUsuario(@Valid @RequestBody UsuarioDTO dto) {
        return usuarioService.createUsuario(dto);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Lista todos usuarios", description = "Metodo para retornar todos usuarios")
    @ApiResponse(responseCode = "201", description = "Usuarios listados")
    @ApiResponse(responseCode = "404", description = "Usuarios não encontrados")
    @ApiResponse(responseCode = "500", description = "Erro interno")
    public List<UsuarioDTO> getAllUsuarios() {
        return usuarioService.getAllUsuarios();
    }

    @GetMapping("/{cpf}")
    @PreAuthorize("hasRole('ADMIN') || #cpf == authentication.name")
    @Operation(summary = "Lista usuario por cpf", description = "Metodo para retornar todos usuarios")
    public UsuarioDTO getUsuarioByCpf(@PathVariable String cpf) {
        return usuarioService.getUsuarioByCpf(cpf);
    }

    @DeleteMapping("/{cpf}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Deletar usuario", description = "Metodo para deletar usuario")
    public void deleteUsuario(@PathVariable String cpf) {
        usuarioService.deleteUsuario(cpf);
    }
}