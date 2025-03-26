package com.pimenta.petshop.service;

import com.pimenta.petshop.exception.NotFoundException;
import com.pimenta.petshop.mapper.UsuarioMapper;
import com.pimenta.petshop.model.UsuarioDTO;
import com.pimenta.petshop.model.UsuarioEntity;
import com.pimenta.petshop.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private UsuarioMapper usuarioMapper;

    @InjectMocks
    private UsuarioService usuarioService;

    private UsuarioDTO usuarioDTO;
    private UsuarioEntity usuarioEntity;

    @BeforeEach
    void setUp() {
        usuarioDTO = new UsuarioDTO();
        usuarioDTO.setCpf("12345678901");
        usuarioDTO.setNome("Teste");
        usuarioDTO.setSenha("senha123");

        usuarioEntity = new UsuarioEntity();
        usuarioEntity.setCpf("12345678901");
        usuarioEntity.setNome("Teste");
        usuarioEntity.setSenha("hashedSenha");
    }

    @Test
    void testCreateUsuario() {
        when(usuarioMapper.toEntity(usuarioDTO)).thenReturn(usuarioEntity);
        when(usuarioRepository.save(usuarioEntity)).thenReturn(usuarioEntity);
        when(usuarioMapper.toDto(usuarioEntity)).thenReturn(usuarioDTO);

        UsuarioDTO result = usuarioService.createUsuario(usuarioDTO);

        assertNotNull(result);
        assertEquals("12345678901", result.getCpf());
        verify(usuarioRepository, times(1)).save(usuarioEntity);
    }

    @Test
    void testGetAllUsuarios() {
        when(usuarioRepository.findAll()).thenReturn(List.of(usuarioEntity));
        when(usuarioMapper.toDtoWithoutPassword(usuarioEntity)).thenReturn(usuarioDTO);

        List<UsuarioDTO> usuarios = usuarioService.getAllUsuarios();

        assertFalse(usuarios.isEmpty());
        assertEquals(1, usuarios.size());
        verify(usuarioRepository, times(1)).findAll();
    }

    @Test
    void testGetUsuarioByCpf_Success() {
        when(usuarioRepository.findByCpf("12345678901")).thenReturn(Optional.of(usuarioEntity));
        when(usuarioMapper.toDtoWithoutPassword(usuarioEntity)).thenReturn(usuarioDTO);

        UsuarioDTO result = usuarioService.getUsuarioByCpf("12345678901");

        assertNotNull(result);
        assertEquals("12345678901", result.getCpf());
        verify(usuarioRepository, times(1)).findByCpf("12345678901");
    }

    @Test
    void testGetUsuarioByCpf_NotFound() {
        when(usuarioRepository.findByCpf("00000000000")).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> usuarioService.getUsuarioByCpf("00000000000"));
        verify(usuarioRepository, times(1)).findByCpf("00000000000");
    }
}