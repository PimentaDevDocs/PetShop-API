package com.pimenta.petshop.service;

import com.pimenta.petshop.exception.NotFoundException;
import com.pimenta.petshop.mapper.AtendimentoMapper;
import com.pimenta.petshop.mapper.ClienteMapper;
import com.pimenta.petshop.mapper.PetMapper;
import com.pimenta.petshop.model.*;
import com.pimenta.petshop.repository.AtendimentoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AtendimentoServiceTest {

    @Mock
    private AtendimentoRepository atendimentoRepository;

    @Mock
    private ClienteService clienteService;

    @Mock
    private PetService petService;

    @Mock
    private AtendimentoMapper atendimentoMapper;

    @Mock
    private ClienteMapper clienteMapper;

    @Mock
    private PetMapper petMapper;

    @InjectMocks
    private AtendimentoService atendimentoService;

    private AtendimentoDTO atendimentoDTO;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);

        ClienteEntity clienteEntity = new ClienteEntity();
        clienteEntity.setCpf("12345678900");
        clienteEntity.setNome("Cliente Teste");

        PetEntity petEntity = new PetEntity();
        petEntity.setId(1L);
        petEntity.setNome("Pet Teste");
        petEntity.setCliente(clienteEntity);

        AtendimentoEntity atendimentoEntity = new AtendimentoEntity();
        atendimentoEntity.setId(1L);

        atendimentoDTO = new AtendimentoDTO();
        atendimentoDTO.setIdPet(1L);
        atendimentoDTO.setValor("100");
        atendimentoDTO.setDataAtendimento(LocalDateTime.parse("2025-03-01T00:00:00"));

        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setCpf("12345678900");
        clienteDTO.setNome("Cliente Teste");

        PetDTO petDTO = new PetDTO();

        petDTO.setId(1L);
        petDTO.setNome("Pet Teste");

        atendimentoDTO = new AtendimentoDTO();
        atendimentoDTO.setIdPet(1L);
        atendimentoDTO.setValor("100");
        atendimentoDTO.setDataAtendimento(LocalDateTime.parse("2025-03-01T00:00:00"));

        when(clienteService.getClienteByCpf("12345678900")).thenReturn(clienteDTO);
        when(petService.getPetById(1L)).thenReturn(petDTO);
        when(atendimentoRepository.save(any(AtendimentoEntity.class))).thenReturn(atendimentoEntity);
        when(atendimentoRepository.findByClienteCpf("12345678900")).thenReturn(List.of(atendimentoEntity));

        when(atendimentoMapper.toEntity(atendimentoDTO)).thenReturn(atendimentoEntity);
        when(atendimentoMapper.toDto(atendimentoEntity)).thenReturn(atendimentoDTO);
        when(clienteMapper.toEntity(clienteDTO)).thenReturn(clienteEntity);
        when(petMapper.toEntity(petDTO)).thenReturn(petEntity);
    }

    @Test
    void testCreateAtendimento() {
        AtendimentoDTO result = atendimentoService.createAtendimento(atendimentoDTO);

        assertNotNull(result);
        assertEquals(atendimentoDTO.getId(), result.getId());
        assertEquals(atendimentoDTO.getCpf(), result.getCpf());
        verify(atendimentoRepository, times(1)).save(any(AtendimentoEntity.class));
    }

    @Test
    void testDeleteAtendimento() {
        atendimentoService.deleteAtendimento(1L);

        verify(atendimentoRepository, times(1)).deleteById(1L);
    }

    @Test
    void testGetAtendimentosByCliente_Success() {
        List<AtendimentoDTO> result = atendimentoService.getAtendimentosByCliente("12345678900");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(atendimentoDTO.getId(), result.get(0).getId());
    }

    @Test
    void testGetAtendimentosByCliente_NotFound() {
        when(atendimentoRepository.findByClienteCpf("12345678900")).thenReturn(List.of());

        NotFoundException thrown = assertThrows(NotFoundException.class, () -> atendimentoService.getAtendimentosByCliente("12345678900"));

        assertEquals("Atendimento não encontrado(a) para valor: '12345678900'", thrown.getMessage());
    }
}
