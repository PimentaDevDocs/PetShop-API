package com.pimenta.petshop.service;


import com.pimenta.petshop.exception.NotFoundException;
import com.pimenta.petshop.mapper.ClienteMapper;
import com.pimenta.petshop.mapper.ContatoMapper;
import com.pimenta.petshop.model.ClienteDTO;
import com.pimenta.petshop.model.ClienteEntity;
import com.pimenta.petshop.model.ContatoEntity;
import com.pimenta.petshop.repository.ClienteRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final ContatoService contatoService;
    private final ClienteMapper clienteMapper;
    private final ContatoMapper contatoMapper;

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public ClienteDTO createCliente(ClienteDTO dto) {
        ClienteEntity cliente = clienteMapper.toEntity(dto);
        ClienteEntity savedCliente = clienteRepository.save(cliente);

        dto.getContatos().forEach(contatoDTO -> {
            ContatoEntity contato = contatoMapper.toEntity(contatoDTO);
            contato.setCliente(savedCliente);
            contatoService.createContato(contatoMapper.toDto(contato));
        });

        return clienteMapper.toDto(savedCliente);
    }

    @PreAuthorize("hasRole('ADMIN') || @securityService.isOwner(authentication)")
    public ClienteDTO getClienteByCpf(String cpf) {
        return clienteRepository.findByCpf(cpf).map(clienteMapper::toDto).orElseThrow(() -> new NotFoundException(ClienteEntity.class, cpf));
    }
}