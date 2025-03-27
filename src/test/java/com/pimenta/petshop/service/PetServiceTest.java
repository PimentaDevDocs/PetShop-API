package com.pimenta.petshop.service;

import com.pimenta.petshop.exception.NotFoundException;
import com.pimenta.petshop.mapper.ClienteMapper;
import com.pimenta.petshop.mapper.PetMapper;
import com.pimenta.petshop.mapper.RacaMapper;
import com.pimenta.petshop.model.*;
import com.pimenta.petshop.repository.PetRepository;
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
class PetServiceTest {

    @Mock
    private PetRepository petRepository;

    @Mock
    private PetMapper petMapper;

    @Mock
    private RacaMapper racaMapper;

    @Mock
    private ClienteMapper clienteMapper;

    @Mock
    private ClienteService clienteService;

    @Mock
    private RacaService racaService;

    @InjectMocks
    private PetService petService;

    private PetDTO petDTO;
    private PetEntity petEntity;
    private ClienteDTO clienteDTO;
    private RacaDTO racaDTO;

    @BeforeEach
    void setUp() {
        petDTO = new PetDTO();
        petDTO.setCpf("12345678901");
        petDTO.setIdRaca(1L);

        clienteDTO = new ClienteDTO();
        clienteDTO.setCpf("12345678901");

        racaDTO = new RacaDTO();
        racaDTO.setId(1L);

        petEntity = new PetEntity();
    }

    @Test
    void testCreatePet() {
        when(clienteService.getClienteByCpf(petDTO.getCpf())).thenReturn(clienteDTO);
        when(racaService.getRacaById(petDTO.getIdRaca())).thenReturn(racaDTO);
        when(petMapper.toEntity(any())).thenReturn(petEntity);
        when(clienteMapper.toEntity(any())).thenReturn(new ClienteEntity());
        when(racaMapper.toEntity(any())).thenReturn(new RacaEntity());
        when(petRepository.save(any())).thenReturn(petEntity);
        when(petMapper.toDto(any())).thenReturn(petDTO);

        PetDTO createdPet = petService.createPet(petDTO);

        assertNotNull(createdPet);
        verify(petRepository).save(any());
    }

    @Test
    void testGetPetById_Success() {
        when(petRepository.findById(1L)).thenReturn(Optional.of(petEntity));
        when(petMapper.toDto(any())).thenReturn(petDTO);

        PetDTO foundPet = petService.getPetById(1L);
        assertNotNull(foundPet);
    }

    @Test
    void testGetPetById_NotFound() {
        when(petRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> petService.getPetById(1L));
    }

    @Test
    void testGetPetsByCliente_Success() {
        when(clienteService.getClienteByCpf("12345678901")).thenReturn(clienteDTO);
        when(petRepository.findByClienteCpf("12345678901")).thenReturn(List.of(petEntity));
        when(petMapper.toDto(any())).thenReturn(petDTO);

        List<PetDTO> pets = petService.getPetsByCliente("12345678901");
        assertFalse(pets.isEmpty());
    }

    @Test
    void testGetPetsByCliente_NotFound() {
        when(clienteService.getClienteByCpf("12345678901")).thenReturn(clienteDTO);
        when(petRepository.findByClienteCpf("12345678901")).thenReturn(List.of());

        assertThrows(NotFoundException.class, () -> petService.getPetsByCliente("12345678901"));
    }
}