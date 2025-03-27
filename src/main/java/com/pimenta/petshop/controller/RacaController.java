package com.pimenta.petshop.controller;

import com.pimenta.petshop.model.RacaDTO;
import com.pimenta.petshop.security.SecurityConfig;
import com.pimenta.petshop.service.RacaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/racas")
@Tag(name = "Raças", description = "Gerenciamento de raças de animais")
@SecurityRequirement(name = SecurityConfig.SECURITY)
public class RacaController {

    @Autowired
    private RacaService racaService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Criar raca", description = "Metodo para criar raca")
    public RacaDTO createRaca(@RequestBody RacaDTO dto) {
        return racaService.createRaca(dto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Criar atualizar raca", description = "Metodo para atualizar raca")
    public RacaDTO updateRaca(@PathVariable Long id, @RequestBody RacaDTO dto) {
        return racaService.updateRaca(id, dto);
    }

    @GetMapping
    @PreAuthorize("hasRole('CLIENTE') || hasRole('ADMIN')")
    @Operation(summary = "Listar raca", description = "Metodo para listar racas")
    public List<RacaDTO> getAllRacas() {
        return racaService.getAllRacas();
    }

    @DeleteMapping("/{racaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Deletar raca", description = "Metodo para deletar raca")
    public void deleteRaca(@PathVariable Long racaId) {
        racaService.deleteRaca(racaId);
    }
}