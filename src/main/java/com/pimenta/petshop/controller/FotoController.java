package com.pimenta.petshop.controller;

import com.pimenta.petshop.enums.TIPO_FOTO;
import com.pimenta.petshop.model.FotoDTO;
import com.pimenta.petshop.service.FotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/fotos")
@RequiredArgsConstructor
public class FotoController {

    private final FotoService fotoService;

    @PostMapping("/cliente/{cpf}")
    @PreAuthorize("hasRole('ADMIN') || @securityService.isOwner(authentication, #cpf)")
    public ResponseEntity<FotoDTO> addFotoCliente(
            @PathVariable String cpf,
            @RequestParam("tipo") TIPO_FOTO tipo,
            @RequestParam("foto") MultipartFile foto) {

        FotoDTO fotoDTO = fotoService.addFotoCliente(cpf, tipo, foto);
        return ResponseEntity.ok(fotoDTO);
    }

    @PostMapping("/pet/{petId}")
    @PreAuthorize("hasRole('ADMIN') || @securityService.isPetOwner(authentication, #petId)")
    public ResponseEntity<FotoDTO> addFotoPet(
            @PathVariable Long petId,
            @RequestParam("tipoFoto") TIPO_FOTO tipoFoto,
            @RequestParam("foto") MultipartFile foto) {

        FotoDTO fotoDTO = fotoService.addFotoPet(petId, tipoFoto, foto);
        return ResponseEntity.ok(fotoDTO);
    }

    @GetMapping("/cliente/{cpf}")
    @PreAuthorize("hasRole('ADMIN') || @securityService.isOwner(authentication, #cpf)")
    public ResponseEntity<List<FotoDTO>> getFotosPorCliente(@PathVariable String cpf) {
        List<FotoDTO> fotos = fotoService.getFotosPorCliente(cpf);
        return ResponseEntity.ok(fotos);
    }

    @GetMapping("/pet/{petId}")
    @PreAuthorize("hasRole('ADMIN') || @securityService.isPetOwner(authentication, #petId)")
    public ResponseEntity<List<FotoDTO>> getFotosPorPet(@PathVariable Long petId) {
        List<FotoDTO> fotos = fotoService.getFotosPorPet(petId);
        return ResponseEntity.ok(fotos);
    }

    @PutMapping("/{fotoId}")
    @PreAuthorize("hasRole('ADMIN') || @securityService.isFotoOwner(authentication, #fotoId)")
    public ResponseEntity<FotoDTO> updateFoto(
            @PathVariable Long fotoId,
            @RequestParam("tipoFoto") TIPO_FOTO tipoFoto,
            @RequestParam("foto") MultipartFile foto) {

        FotoDTO fotoDTO = fotoService.updateFoto(fotoId, tipoFoto, foto);
        return ResponseEntity.ok(fotoDTO);
    }

    @DeleteMapping("/{fotoId}")
    @PreAuthorize("hasRole('ADMIN') || @securityService.isFotoOwner(authentication, #fotoId)")
    public ResponseEntity<Void> deleteFoto(@PathVariable Long fotoId) {
        fotoService.deleteFoto(fotoId);
        return ResponseEntity.noContent().build();
    }
}
