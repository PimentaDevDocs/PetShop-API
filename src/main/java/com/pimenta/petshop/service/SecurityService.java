package com.pimenta.petshop.service;

import com.pimenta.petshop.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityService {

    private final ClienteRepository clienteRepository;
    private final PetRepository petRepository;
    private final ContatoRepository contatoRepository;
    private final EnderecoRepository enderecoRepository;
    private final AtendimentoRepository atendimentoRepository;

    public boolean isOwner(Authentication authentication) {
        String cpf = authentication.getName();
        return clienteRepository.existsByCpf(cpf);
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

    public boolean isAtendimentoOwner(Authentication authentication, Long atendimentoId) {
        String cpf = authentication.getName();
        return atendimentoRepository.existsByIdAndClienteCpf(atendimentoId, cpf);
    }

}