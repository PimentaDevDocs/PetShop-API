package com.pimenta.petshop.controller;

import com.pimenta.petshop.model.PetDTO;
import com.pimenta.petshop.security.SecurityConfig;
import com.pimenta.petshop.service.PetService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
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
    public PetDTO createPet(@RequestBody PetDTO dto) {
        return petService.createPet(dto);
    }

    @GetMapping("/cliente/{cpf}")
    @PreAuthorize("hasRole('ADMIN') || @securityService.isOwner(authentication, #cpf)")
    public List<PetDTO> getPetsByCliente(@PathVariable String cpf) {
        return petService.getPetsByCliente(cpf);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') || @securityService.isPetOwner(authentication, #id)")
    public PetDTO getPetById(@PathVariable Long id) {
        return petService.getPetById(id);
    }
}