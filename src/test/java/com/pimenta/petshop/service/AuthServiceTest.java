package com.pimenta.petshop.service;

import com.pimenta.petshop.enums.ROLE;
import com.pimenta.petshop.mapper.UsuarioMapper;
import com.pimenta.petshop.model.AuthResponse;
import com.pimenta.petshop.model.UsuarioDTO;
import com.pimenta.petshop.model.UsuarioEntity;
import com.pimenta.petshop.security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private UsuarioMapper usuarioMapper;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthService authService;

    private UsuarioDTO usuarioDTO;
    private UsuarioEntity usuario;

    @BeforeEach
    public void setUp() {
        usuarioDTO = new UsuarioDTO();
        usuarioDTO.setCpf("12345678901");
        usuarioDTO.setNome("Teste");
        usuarioDTO.setROLE(ROLE.ADMIN);
        usuarioDTO.setSenha("senha");

        usuario = UsuarioEntity.builder()
                .cpf("12345678901")
                .nome("Teste")
                .ROLE(ROLE.ADMIN)
                .senha("hashedSenha")
                .build();

        ReflectionTestUtils.setField(authService, "authenticationManager", authenticationManager);
    }

    @Test
    public void testRegister() {
        when(passwordEncoder.encode("senha")).thenReturn("hashedSenha");
        when(usuarioMapper.toDto(any(UsuarioEntity.class))).thenReturn(usuarioDTO);
        when(usuarioService.createUsuario(any(UsuarioDTO.class))).thenReturn(usuarioDTO);

        when(jwtUtil.generateToken(any(UsuarioEntity.class))).thenReturn("token123");

        AuthResponse response = authService.register(usuarioDTO);

        assertNotNull(response);
        assertEquals("token123", response.getToken());
    }

    @Test
    public void testLogin() {
        Authentication dummyAuth = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(dummyAuth);

        when(usuarioService.getUsuarioByCpf("12345678901")).thenReturn(usuarioDTO);
        when(usuarioMapper.toEntity(any(UsuarioDTO.class))).thenReturn(usuario);
        when(jwtUtil.generateToken(any(UsuarioEntity.class))).thenReturn("token123");
        AuthResponse response = authService.login(usuarioDTO);

        assertNotNull(response);
        assertEquals("token123", response.getToken());
    }

    @Test
    public void testLogin_WithInvalidCredentials() {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new RuntimeException("Invalid credentials"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            authService.login(usuarioDTO);
        });

        assertTrue(exception.getMessage().contains("Erro de autenticação"));
    }
}
