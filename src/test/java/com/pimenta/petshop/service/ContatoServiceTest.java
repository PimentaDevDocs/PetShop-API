package com.pimenta.petshop.service;

import com.pimenta.petshop.exception.NotFoundException;
import com.pimenta.petshop.mapper.ContatoMapper;
import com.pimenta.petshop.model.ClienteEntity;
import com.pimenta.petshop.model.ContatoDTO;
import com.pimenta.petshop.model.ContatoEntity;
import com.pimenta.petshop.repository.ContatoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ContatoServiceTest {

    @InjectMocks
    private ContatoService contatoService;

    @Mock
    private ContatoRepository contatoRepository;

    @Mock
    private ContatoMapper contatoMapper;

    private ContatoEntity contatoEntity;
    private ContatoDTO contatoDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        ClienteEntity clienteEntity = new ClienteEntity();
        clienteEntity.setCpf("00000000000");

        contatoEntity = new ContatoEntity();
        contatoEntity.setId(1L);
        contatoEntity.setCliente(clienteEntity);
        contatoEntity.setTipo("Telefone");
        contatoEntity.setValor("100");
        contatoEntity.setTag("Principal");

        contatoDTO = new ContatoDTO();
        contatoDTO.setId(1L);
        contatoDTO.setTipo("Telefone");
        contatoDTO.setDescricao("Contato teste");
        contatoDTO.setTag("Principal");
    }

    @Test
    void testCreateContato() {
        when(contatoMapper.toEntity(any(ContatoDTO.class))).thenReturn(contatoEntity);
        when(contatoRepository.save(any(ContatoEntity.class))).thenReturn(contatoEntity);
        when(contatoMapper.toDto(any(ContatoEntity.class))).thenReturn(contatoDTO);

        ContatoDTO createdContato = contatoService.createContato(contatoDTO);

        assertNotNull(createdContato);
        assertEquals("Telefone", createdContato.getTipo());
        verify(contatoRepository, times(1)).save(any(ContatoEntity.class));
    }

    @Test
    void testDeleteContato() {
        doNothing().when(contatoRepository).deleteById(anyLong());

        contatoService.deleteContato(1L);

        verify(contatoRepository, times(1)).deleteById(1L);
    }

    @Test
    void testGetContatosByCliente_ContatosExistem() {
        when(contatoRepository.findByClienteCpf(anyString())).thenReturn(Collections.singletonList(contatoEntity));
        when(contatoMapper.toDto(any(ContatoEntity.class))).thenReturn(contatoDTO);

        List<ContatoDTO> contatos = contatoService.getContatosByCliente("00000000000");

        assertNotNull(contatos);
        assertEquals(1, contatos.size());
        assertEquals("Telefone", contatos.get(0).getTipo());
    }

    @Test
    void testGetContatosByCliente_NotFound() {
        when(contatoRepository.findByClienteCpf(anyString())).thenReturn(List.of());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> contatoService.getContatosByCliente("00000000000"));

        assertEquals("Cliente não encontrado(a) para valor: '00000000000'", exception.getMessage());
    }

    @Test
    void testUpdateContato() {
        when(contatoRepository.findById(anyLong())).thenReturn(Optional.of(contatoEntity));
        when(contatoRepository.save(any(ContatoEntity.class))).thenReturn(contatoEntity);
        when(contatoMapper.toDto(any(ContatoEntity.class))).thenReturn(contatoDTO);

        ContatoDTO updatedContato = contatoService.updateContato(1L, contatoDTO);

        assertNotNull(updatedContato);
        assertEquals("Telefone", updatedContato.getTipo());
        verify(contatoRepository, times(1)).save(any(ContatoEntity.class));
    }

    @Test
    void testUpdateContato_NotFound() {
        when(contatoRepository.findById(anyLong())).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> contatoService.updateContato(1L, contatoDTO));

        assertEquals("Contato não encontrado(a) para valor: '1'", exception.getMessage());
    }
}
