package com.pimenta.petshop.service;

import com.pimenta.petshop.enums.ROLE;
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
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private UsuarioMapper usuarioMapper;

    @InjectMocks
    private UsuarioService usuarioService;

    private UsuarioDTO usuarioDTO;
    private UsuarioEntity usuarioEntity;

    @BeforeEach
    public void setUp() {
        usuarioDTO = new UsuarioDTO();
        usuarioDTO.setCpf("12345678901");
        usuarioDTO.setNome("Teste");
        usuarioDTO.setROLE(ROLE.ADMIN);
        usuarioDTO.setSenha("senha");

        usuarioEntity = UsuarioEntity.builder()
                .cpf("12345678901")
                .nome("Teste")
                .ROLE(ROLE.ADMIN)
                .senha("hashedSenha")
                .build();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testCreateUsuario() {
        when(usuarioMapper.toEntity(usuarioDTO)).thenReturn(usuarioEntity);
        when(usuarioRepository.save(usuarioEntity)).thenReturn(usuarioEntity);
        when(usuarioMapper.toDto(usuarioEntity)).thenReturn(usuarioDTO);

        UsuarioDTO result = usuarioService.createUsuario(usuarioDTO);

        assertNotNull(result);
        assertEquals(usuarioDTO, result);
        verify(usuarioRepository, times(1)).save(usuarioEntity);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testGetAllUsuarios() {
        List<UsuarioEntity> usuarioEntities = Collections.singletonList(usuarioEntity);
        List<UsuarioDTO> usuarioDTOs = Collections.singletonList(usuarioDTO);

        when(usuarioRepository.findAll()).thenReturn(usuarioEntities);
        when(usuarioMapper.toDtoWithoutPassword(usuarioEntity)).thenReturn(usuarioDTO);

        List<UsuarioDTO> result = usuarioService.getAllUsuarios();

        assertNotNull(result);
        assertEquals(usuarioDTOs, result);
        verify(usuarioRepository, times(1)).findAll();
    }

    @Test
    public void testGetUsuarioByCpf() {
        when(usuarioRepository.findByCpf("12345678901")).thenReturn(Optional.of(usuarioEntity));
        when(usuarioMapper.toDto(usuarioEntity)).thenReturn(usuarioDTO);

        UsuarioDTO result = usuarioService.getUsuarioByCpf("12345678901");

        assertNotNull(result);
        assertEquals(usuarioDTO, result);
    }

    @Test
    public void testGetUsuarioByCpf_NotFound() {
        when(usuarioRepository.findByCpf("12345678901")).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> usuarioService.getUsuarioByCpf("12345678901"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testDeleteUsuario() {
        usuarioService.deleteUsuario("12345678901");
        verify(usuarioRepository, times(1)).deleteByCpf("12345678901");
    }
}