package com.pimenta.petshop.service;

import com.pimenta.petshop.model.UsuarioDTO;
import com.pimenta.petshop.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityService {

    private final PetRepository petRepository;
    private final ContatoRepository contatoRepository;
    private final EnderecoRepository enderecoRepository;
    private final FotoRepository fotoRepository;

    private boolean compareCpf(Authentication authentication, String cpf) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }

        Object principal = authentication.getPrincipal();

        String authCpf;

        if (principal instanceof UsuarioDTO) {
            authCpf = ((UsuarioDTO) principal).getCpf();
        } else {
            authCpf = authentication.getName();
        }

        return authCpf.equals(cpf);
    }

    public boolean isOwner(Authentication authentication, String cpf) {
        return compareCpf(authentication, cpf);
    }

    public boolean isPetOwner(Authentication authentication, Long petId) {
        String cpf = authentication.getName();
        return petRepository.existsByIdAndClienteCpf(petId, cpf);
    }

    public boolean isContatoOwner(Authentication authentication, Long contatoId) {
        String cpf = authentication.getName();
        return contatoRepository.existsByIdAndClienteCpf(contatoId, cpf);
    }

    public boolean isEnderecoOwner(Authentication authentication, Long enderecoId) {
        String cpf = authentication.getName();
        return enderecoRepository.existsByIdAndClienteCpf(enderecoId, cpf);
    }

    public boolean isFotoOwner(Authentication authentication, Long fotoId) {
        String cpf = authentication.getName();
        return fotoRepository.existsByIdAndClienteCpf(fotoId, cpf);
    }

}