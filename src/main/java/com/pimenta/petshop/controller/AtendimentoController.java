package com.pimenta.petshop.controller;

import com.pimenta.petshop.model.AtendimentoDTO;
import com.pimenta.petshop.service.AtendimentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/atendimentos")
@RequiredArgsConstructor
public class AtendimentoController {

    private final AtendimentoService atendimentoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN') || (@securityService.isOwner(authentication, #dto.cpf) && " +
            "@securityService.isPetOwner(authentication, #dto.idPet))")
    public AtendimentoDTO createAtendimento(@RequestBody AtendimentoDTO dto) {
        return atendimentoService.createAtendimento(dto);
    }

    @GetMapping("/cliente/{cpf}")
    @PreAuthorize("hasRole('ADMIN') || @securityService.isAtendimentoOwner(authentication, #cpf)")
    public List<AtendimentoDTO> getAtendimentosByCliente(@PathVariable String cpf) {
        return atendimentoService.getAtendimentosByCliente(cpf);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') || @securityService.isAtendimentoOwner(authentication, #id)")
    public void deleteAtendimento(@PathVariable Long id) {
        atendimentoService.deleteAtendimento(id);
    }
}