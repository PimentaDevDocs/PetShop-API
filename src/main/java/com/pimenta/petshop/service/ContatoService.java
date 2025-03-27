package com.pimenta.petshop.service;

import com.pimenta.petshop.exception.NotFoundException;
import com.pimenta.petshop.mapper.ContatoMapper;
import com.pimenta.petshop.model.ClienteEntity;
import com.pimenta.petshop.model.ContatoDTO;
import com.pimenta.petshop.model.ContatoEntity;
import com.pimenta.petshop.repository.ContatoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContatoService {

    private final ContatoRepository contatoRepository;
    private final ContatoMapper contatoMapper;

    public ContatoDTO createContato(ContatoDTO dto) {
        return contatoMapper.toDto(
                contatoRepository.save(contatoMapper.toEntity(dto))
        );
    }

    @PreAuthorize("hasRole('ADMIN') || @securityService.isContatoOwner(authentication, #id)")
    public void deleteContato(Long id) {
        contatoRepository.deleteById(id);
    }

    @PreAuthorize("hasRole('ADMIN') || @securityService.isOwner(authentication, #cpf)")
    public List<ContatoDTO> getContatosByCliente(String cpf) {

        List<ContatoEntity> contatos = contatoRepository.findByClienteCpf(cpf);

        if (contatos == null || contatos.isEmpty()) {
            throw new NotFoundException(ClienteEntity.class, cpf);
        }
        return contatos.stream()
                .map(contatoMapper::toDto)
                .toList();
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN') || @securityService.isContatoOwner(authentication, #id)")
    public ContatoDTO updateContato(Long id, ContatoDTO dto) {
        ContatoEntity contato = contatoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ContatoEntity.class, id.toString()));

        contatoMapper.updateEntityFromDto(dto, contato);
        return contatoMapper.toDto(contatoRepository.save(contato));
    }
}