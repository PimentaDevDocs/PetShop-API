package com.pimenta.petshop.controller;

import com.pimenta.petshop.model.AtendimentoDTO;
import com.pimenta.petshop.security.SecurityConfig;
import com.pimenta.petshop.service.AtendimentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/atendimentos")
@RequiredArgsConstructor
@Tag(name = "Atendimentos", description = "Rotas de atendimentos")
@SecurityRequirement(name = SecurityConfig.SECURITY)
public class AtendimentoController {

    private final AtendimentoService atendimentoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN') || (@securityService.isOwner(authentication, #dto.cpf) && " +
            "@securityService.isPetOwner(authentication, #dto.idPet))")
    @Operation(summary = "Gerar atendimento", description = "Metodo para criar atendimento")
    public AtendimentoDTO createAtendimento(@RequestBody AtendimentoDTO dto) {
        return atendimentoService.createAtendimento(dto);
    }

    @GetMapping("/cliente/{cpf}")
    @PreAuthorize("hasRole('ADMIN') || @securityService.isOwner(authentication, #cpf)")
    @Operation(summary = "Listar atendimento por cliente", description = "Metodo para listar atendimento por cliente")
    public List<AtendimentoDTO> getAtendimentosByCliente(@PathVariable String cpf) {
        return atendimentoService.getAtendimentosByCliente(cpf);
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') || @securityService.isOwner(authentication, #id)")
    @Operation(summary = "Deletar atendimento", description = "Metodo para deletar atendimento")
    public void deleteAtendimento(@PathVariable Long id) {
        atendimentoService.deleteAtendimento(id);
    }
}