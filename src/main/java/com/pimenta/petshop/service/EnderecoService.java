package com.pimenta.petshop.service;

import com.pimenta.petshop.exception.NotFoundException;
import com.pimenta.petshop.mapper.EnderecoMapper;
import com.pimenta.petshop.model.ClienteEntity;
import com.pimenta.petshop.model.EnderecoDTO;
import com.pimenta.petshop.model.EnderecoEntity;
import com.pimenta.petshop.repository.EnderecoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;
    private final EnderecoMapper enderecoMapper;

    public EnderecoDTO createEndereco(EnderecoDTO dto) {
        return enderecoMapper.toDto(
                enderecoRepository.save(enderecoMapper.toEntity(dto))
        );
    }

    @PreAuthorize("hasRole('ADMIN') || @securityService.isEnderecoOwner(authentication, #id)")
    public void deleteEndereco(Long id) {
        enderecoRepository.deleteById(id);
    }

    @PreAuthorize("hasRole('ADMIN') || @securityService.isEnderecoOwner(authentication, #cpf)")
    public List<EnderecoDTO> getEnderecosByCliente(String cpf) {

        List<EnderecoEntity> enderecos = enderecoRepository.findByClienteCpf(cpf);

        if (enderecos == null || enderecos.isEmpty()) {
            throw new NotFoundException(ClienteEntity.class, cpf);
        }

        return enderecos.stream()
                .map(enderecoMapper::toDto)
                .toList();
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN') || @securityService.isEnderecoOwner(authentication, #id)")

    public EnderecoDTO updateEndereco(Long id, EnderecoDTO dto) {
        EnderecoEntity endereco = enderecoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(EnderecoEntity.class, id.toString()));

        enderecoMapper.updateEntityFromDto(dto, endereco);
        return enderecoMapper.toDto(enderecoRepository.save(endereco));
    }
}