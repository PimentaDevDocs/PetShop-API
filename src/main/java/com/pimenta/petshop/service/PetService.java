package com.pimenta.petshop.service;

import com.pimenta.petshop.exception.NotFoundException;
import com.pimenta.petshop.mapper.ClienteMapper;
import com.pimenta.petshop.mapper.PetMapper;
import com.pimenta.petshop.mapper.RacaMapper;
import com.pimenta.petshop.model.ClienteDTO;
import com.pimenta.petshop.model.PetDTO;
import com.pimenta.petshop.model.PetEntity;
import com.pimenta.petshop.model.RacaDTO;
import com.pimenta.petshop.repository.PetRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PetService {

    private final PetRepository petRepository;
    private final PetMapper petMapper;
    private final RacaMapper racaMapper;
    private final ClienteMapper clienteMapper;
    private final ClienteService clienteService;
    private final RacaService racaService;

    @Transactional
    @PreAuthorize("hasRole('ADMIN') || @securityService.isPetOwner(authentication, #dto.id)")
    public PetDTO createPet(PetDTO dto) {

        ClienteDTO clienteDTO = clienteService.getClienteByCpf(dto.getCpf());
        RacaDTO racaDTO = racaService.getRacaById(dto.getIdRaca());

        PetEntity pet = petMapper.toEntity(dto);
        pet.setCliente(clienteMapper.toEntity(clienteDTO));
        pet.setRaca(racaMapper.toEntity(racaDTO));

        return petMapper.toDto(petRepository.save(pet));
    }

    @PreAuthorize("hasRole('ADMIN') || @securityService.isOwner(authentication, #cpf)")
    public List<PetDTO> getPetsByCliente(String cpf) {

        List<PetEntity> pets = petRepository.findByClienteCpf(cpf);
        if (pets == null || pets.isEmpty()) {
            throw new NotFoundException(PetEntity.class, cpf);
        }

        return pets.stream()
                .map(petMapper::toDto)
                .toList();
    }

    @PreAuthorize("hasRole('ADMIN') || @securityService.isPetOwner(authentication, #id)")
    public PetDTO getPetById(Long id) {
        return petRepository.findById(id)
                .map(petMapper::toDto)
                .orElseThrow(() -> new NotFoundException(PetEntity.class, id.toString()));
    }
}