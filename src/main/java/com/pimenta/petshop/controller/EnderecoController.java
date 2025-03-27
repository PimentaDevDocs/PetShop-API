package com.pimenta.petshop.controller;

import com.pimenta.petshop.model.EnderecoDTO;
import com.pimenta.petshop.security.SecurityConfig;
import com.pimenta.petshop.service.EnderecoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enderecos")
@Tag(name = "Enderecos", description = "Gerenciamento de enderecos dos clientes")
@SecurityRequirement(name = SecurityConfig.SECURITY)
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') || @securityService.isEnderecoOwner(authentication, #id)")
    public EnderecoDTO updateEndereco(@PathVariable Long id, @RequestBody EnderecoDTO dto) {
        return enderecoService.updateEndereco(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN') || @securityService.isEnderecoOwner(authentication, #id)")
    public void deleteEndereco(@PathVariable Long id) {
        enderecoService.deleteEndereco(id);
    }

    @GetMapping("/cliente/{cpf}")
    @PreAuthorize("hasRole('ADMIN') || @securityService.isEnderecoOwner(authentication, #cpf)")
    public List<EnderecoDTO> getEnderecosByCliente(@PathVariable String cpf) {
        return enderecoService.getEnderecosByCliente(cpf);
    }
}