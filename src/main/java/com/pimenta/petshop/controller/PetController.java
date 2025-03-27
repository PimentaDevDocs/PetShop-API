package com.pimenta.petshop.controller;

import com.pimenta.petshop.model.PetDTO;
import com.pimenta.petshop.security.SecurityConfig;
import com.pimenta.petshop.service.PetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pets")
@Tag(name = "Pets", description = "Operações com animais de estimação")
@SecurityRequirement(name = SecurityConfig.SECURITY)
public class PetController {

    @Autowired
    private PetService petService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') || @securityService.isPetOwner(authentication, #dto.id)")
    @Operation(summary = "Criar pet", description = "Metodo para criar pet")
    public PetDTO createPet(@RequestBody PetDTO dto) {
        return petService.createPet(dto);
    }

    @GetMapping("/cliente/{cpf}")
    @PreAuthorize("hasRole('ADMIN') || @securityService.isOwner(authentication, #cpf)")
    @Operation(summary = "Listar pet", description = "Metodo para listar pet por cliente")
    public List<PetDTO> getPetsByCliente(@PathVariable String cpf) {
        return petService.getPetsByCliente(cpf);
    }

    @GetMapping("/{petId}")
    @PreAuthorize("hasRole('ADMIN') || @securityService.isPetOwner(authentication, #petId)")
    @Operation(summary = "Listar pet por id", description = "Metodo para listar pet por id")
    public PetDTO getPetById(@PathVariable Long petId) {
        return petService.getPetById(petId);
    }

    @DeleteMapping("/{petId}")
    @PreAuthorize("hasRole('ADMIN') || @securityService.isPetOwner(authentication, #petId)")
    @Operation(summary = "Deletar pet", description = "Metodo para deletar pet")
    public ResponseEntity<Void> deletePet(@PathVariable Long petId) {
        petService.deletePet(petId);
        return ResponseEntity.noContent().build();
    }
}