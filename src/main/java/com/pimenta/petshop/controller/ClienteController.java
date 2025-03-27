package com.pimenta.petshop.controller;

import com.pimenta.petshop.model.ClienteDTO;
import com.pimenta.petshop.security.SecurityConfig;
import com.pimenta.petshop.service.ClienteService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clientes")
@Tag(name = "Clientes", description = "Operações com clientes")
@SecurityRequirement(name = SecurityConfig.SECURITY)
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public ClienteDTO createCliente(@RequestBody ClienteDTO dto) {
        return clienteService.createCliente(dto);
    }

    @GetMapping("/{cpf}")
    @PreAuthorize("hasRole('ADMIN') || @securityService.isOwner(authentication)")
    public ClienteDTO getClienteByCpf(@PathVariable String cpf) {
        return clienteService.getClienteByCpf(cpf);
    }
}