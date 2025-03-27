package com.pimenta.petshop.service;

import com.pimenta.petshop.mapper.RacaMapper;
import com.pimenta.petshop.model.RacaDTO;
import com.pimenta.petshop.model.RacaEntity;
import com.pimenta.petshop.repository.RacaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RacaServiceTest {

    @Mock
    private RacaRepository racaRepository;

    @Mock
    private RacaMapper racaMapper;

    @InjectMocks
    private RacaService racaService;

    private RacaDTO racaDTO;
    private RacaEntity racaEntity;

    @BeforeEach
    public void setup() {
        racaDTO = new RacaDTO();
        racaDTO.setId(1L);
        racaDTO.setDescricao("Labrador");

        racaEntity = new RacaEntity();
        racaEntity.setId(1L);
        racaEntity.setDescricao("Labrador");
    }

    @Test
    public void testCreateRaca() {
        when(racaMapper.toEntity(racaDTO)).thenReturn(racaEntity);
        when(racaRepository.save(racaEntity)).thenReturn(racaEntity);
        when(racaMapper.toDto(racaEntity)).thenReturn(racaDTO);

        RacaDTO result = racaService.createRaca(racaDTO);
        assertNotNull(result);
        assertEquals(racaDTO.getId(), result.getId());
    }

    @Test
    public void testUpdateRaca() {
        when(racaRepository.findById(1L)).thenReturn(java.util.Optional.of(racaEntity));
        doNothing().when(racaMapper).updateEntityFromDto(racaDTO, racaEntity);
        when(racaRepository.save(racaEntity)).thenReturn(racaEntity);
        when(racaMapper.toDto(racaEntity)).thenReturn(racaDTO);

        RacaDTO result = racaService.updateRaca(1L, racaDTO);
        assertNotNull(result);
        assertEquals(racaDTO.getId(), result.getId());
    }

    @Test
    public void testDeleteRaca() {
        when(racaRepository.existsById(1L)).thenReturn(true);
        doNothing().when(racaRepository).deleteById(1L);

        racaService.deleteRaca(1L);
        verify(racaRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testGetAllRacas() {
        when(racaRepository.findAll()).thenReturn(Collections.singletonList(racaEntity));
        when(racaMapper.toDto(racaEntity)).thenReturn(racaDTO);

        var result = racaService.getAllRacas();
        assertNotNull(result);
        assertEquals(1, result.size());
    }
}
