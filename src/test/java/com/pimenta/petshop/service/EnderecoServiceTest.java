package com.pimenta.petshop.service;

import com.pimenta.petshop.exception.NotFoundException;
import com.pimenta.petshop.mapper.EnderecoMapper;
import com.pimenta.petshop.model.EnderecoDTO;
import com.pimenta.petshop.model.EnderecoEntity;
import com.pimenta.petshop.repository.EnderecoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class EnderecoServiceTest {

    @InjectMocks
    private EnderecoService enderecoService;

    @Mock
    private EnderecoRepository enderecoRepository;

    @Mock
    private EnderecoMapper enderecoMapper;

    private EnderecoEntity enderecoEntity;
    private EnderecoDTO enderecoDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        enderecoEntity = new EnderecoEntity();
        enderecoEntity.setId(1L);
        enderecoEntity.setBairro("Bairro A");

        enderecoDTO = new EnderecoDTO();
        enderecoDTO.setId(1L);
        enderecoDTO.setBairro("Bairro A");
    }

    @Test
    void testCreateEndereco() {
        when(enderecoMapper.toEntity(enderecoDTO)).thenReturn(enderecoEntity);
        when(enderecoRepository.save(enderecoEntity)).thenReturn(enderecoEntity);
        when(enderecoMapper.toDto(enderecoEntity)).thenReturn(enderecoDTO);

        EnderecoDTO createdEndereco = enderecoService.createEndereco(enderecoDTO);

        assertNotNull(createdEndereco);
        assertEquals("Bairro A", createdEndereco.getBairro());
    }

    @Test
    void testGetEnderecosByCliente() {
        when(enderecoRepository.findByClienteCpf("00000000000")).thenReturn(Collections.singletonList(enderecoEntity));
        when(enderecoMapper.toDto(enderecoEntity)).thenReturn(enderecoDTO);

        List<EnderecoDTO> enderecos = enderecoService.getEnderecosByCliente("00000000000");

        assertNotNull(enderecos);
        assertEquals(1, enderecos.size());
    }

    @Test
    void testGetEnderecosByCliente_NotFound() {
        when(enderecoRepository.findByClienteCpf("99999999999")).thenReturn(List.of());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            enderecoService.getEnderecosByCliente("99999999999");
        });

        assertEquals("Cliente não encontrado(a) para valor: '99999999999'", exception.getMessage());
    }

    @Test
    void testUpdateEndereco() {
        when(enderecoRepository.findById(1L)).thenReturn(Optional.of(enderecoEntity));
        when(enderecoRepository.save(enderecoEntity)).thenReturn(enderecoEntity);
        when(enderecoMapper.toDto(enderecoEntity)).thenReturn(enderecoDTO);

        EnderecoDTO updatedEndereco = enderecoService.updateEndereco(1L, enderecoDTO);

        assertNotNull(updatedEndereco);
        assertEquals("Bairro A", updatedEndereco.getBairro());
    }

    @Test
    void testUpdateEndereco_NotFound() {
        when(enderecoRepository.findById(1L)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            enderecoService.updateEndereco(1L, enderecoDTO);
        });

        assertEquals("Endereco não encontrado(a) para valor: '1'", exception.getMessage());
    }
}
