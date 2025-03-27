package com.pimenta.petshop.controller;

import com.pimenta.petshop.model.ContatoDTO;
import com.pimenta.petshop.security.SecurityConfig;
import com.pimenta.petshop.service.ContatoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contatos")
@Tag(name = "Contatos", description = "Gerenciamento de contatos dos clientes")
@SecurityRequirement(name = SecurityConfig.SECURITY)
public class ContatoController {

    @Autowired
    private ContatoService contatoService;

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') || @securityService.isContatoOwner(authentication, #id)")
    public ContatoDTO updateContato(@PathVariable Long id, @RequestBody ContatoDTO dto) {
        return contatoService.updateContato(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN') || @securityService.isContatoOwner(authentication, #id)")
    public void deleteContato(@PathVariable Long id) {
        contatoService.deleteContato(id);
    }

    @GetMapping("/cliente/{cpf}")
    @PreAuthorize("hasRole('ADMIN') || @securityService.isOwner(authentication, #cpf)")
    public List<ContatoDTO> getContatosByCliente(@PathVariable String cpf) {
        return contatoService.getContatosByCliente(cpf);
    }
}