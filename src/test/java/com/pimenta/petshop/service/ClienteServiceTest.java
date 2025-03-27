package com.pimenta.petshop.service;

import com.pimenta.petshop.exception.NotFoundException;
import com.pimenta.petshop.mapper.ClienteMapper;
import com.pimenta.petshop.mapper.ContatoMapper;
import com.pimenta.petshop.model.ClienteDTO;
import com.pimenta.petshop.model.ClienteEntity;
import com.pimenta.petshop.model.ContatoDTO;
import com.pimenta.petshop.model.ContatoEntity;
import com.pimenta.petshop.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ContatoService contatoService;

    @Mock
    private ClienteMapper clienteMapper;

    @Mock
    private ContatoMapper contatoMapper;

    @InjectMocks
    private ClienteService clienteService;

    private ClienteDTO clienteDTO;
    private ClienteEntity clienteEntity;
    private ContatoDTO contatoDTO;
    private ContatoEntity contatoEntity;

    @BeforeEach
    void setUp() {
        clienteDTO = new ClienteDTO();
        clienteDTO.setCpf("12345678901");
        clienteDTO.setNome("Teste Cliente");

        contatoDTO = new ContatoDTO();
        contatoDTO.setDescricao("999999999");
        clienteDTO.setContatos(List.of(contatoDTO));

        clienteEntity = new ClienteEntity();
        clienteEntity.setCpf("12345678901");
        clienteEntity.setNome("Teste Cliente");

        contatoEntity = new ContatoEntity();
        contatoEntity.setValor("999999999");
        contatoEntity.setCliente(clienteEntity);
    }

    @Test
    void testCreateCliente() {
        when(clienteMapper.toEntity(clienteDTO)).thenReturn(clienteEntity);
        when(clienteRepository.save(clienteEntity)).thenReturn(clienteEntity);
        when(clienteMapper.toDto(clienteEntity)).thenReturn(clienteDTO);
        when(contatoMapper.toEntity(contatoDTO)).thenReturn(contatoEntity);
        when(contatoMapper.toDto(contatoEntity)).thenReturn(contatoDTO);

        ClienteDTO result = clienteService.createCliente(clienteDTO);

        assertNotNull(result);
        assertEquals(clienteDTO.getCpf(), result.getCpf());
        verify(clienteRepository).save(clienteEntity);
        verify(contatoService).createContato(any(ContatoDTO.class));
    }

    @Test
    void testGetClienteByCpf_Success() {
        when(clienteRepository.findByCpf("12345678901")).thenReturn(Optional.of(clienteEntity));
        when(clienteMapper.toDto(clienteEntity)).thenReturn(clienteDTO);

        ClienteDTO result = clienteService.getClienteByCpf("12345678901");

        assertNotNull(result);
        assertEquals("12345678901", result.getCpf());
        verify(clienteRepository).findByCpf("12345678901");
    }

    @Test
    void testGetClienteByCpf_NotFound() {
        when(clienteRepository.findByCpf("12345678901")).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> clienteService.getClienteByCpf("12345678901"));
        verify(clienteRepository).findByCpf("12345678901");
    }
}
