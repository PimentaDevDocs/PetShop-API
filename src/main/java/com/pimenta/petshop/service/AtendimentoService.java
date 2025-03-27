package com.pimenta.petshop.service;

import com.pimenta.petshop.exception.NotFoundException;
import com.pimenta.petshop.mapper.AtendimentoMapper;
import com.pimenta.petshop.mapper.ClienteMapper;
import com.pimenta.petshop.mapper.PetMapper;
import com.pimenta.petshop.model.AtendimentoDTO;
import com.pimenta.petshop.model.AtendimentoEntity;
import com.pimenta.petshop.model.ClienteDTO;
import com.pimenta.petshop.model.PetDTO;
import com.pimenta.petshop.repository.AtendimentoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AtendimentoService {

    private final AtendimentoRepository atendimentoRepository;
    private final ClienteService clienteService;
    private final PetService petService;
    private final AtendimentoMapper atendimentoMapper;
    private final ClienteMapper clienteMapper;
    private final PetMapper petMapper;

    @Transactional
    @PreAuthorize("hasRole('ADMIN') || (@securityService.isOwner(authentication) && " +
            "@securityService.isPetOwner(authentication, #dto.idPet))")
    public AtendimentoDTO createAtendimento(AtendimentoDTO dto) {
        ClienteDTO clienteDTO = clienteService.getClienteByCpf(dto.getCpf());

        PetDTO petDTO = petService.getPetById(dto.getIdPet());

        AtendimentoEntity atendimento = atendimentoMapper.toEntity(dto);
        atendimento.setCliente(clienteMapper.toEntity(clienteDTO));
        atendimento.setPet(petMapper.toEntity(petDTO));

        return atendimentoMapper.toDto(atendimentoRepository.save(atendimento));
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN') || @securityService.isAtendimentoOwner(authentication, #id)")
    public void deleteAtendimento(Long id) {
        atendimentoRepository.deleteById(id);
    }

    @PreAuthorize("hasRole('ADMIN') || @securityService.isAtendimentoOwner(authentication, #cpf)")
    public List<AtendimentoDTO> getAtendimentosByCliente(String cpf) {

        List<AtendimentoEntity> atendimentos = atendimentoRepository.findByClienteCpf(cpf);

        if (atendimentos == null || atendimentos.isEmpty()) {
            throw new NotFoundException(AtendimentoEntity.class, cpf);
        }

        return atendimentos.stream()
                .map(atendimentoMapper::toDto)
                .toList();
    }

}