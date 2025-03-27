package com.pimenta.petshop.service;

import com.pimenta.petshop.enums.ROLE;
import com.pimenta.petshop.mapper.UsuarioMapper;
import com.pimenta.petshop.model.AuthResponse;
import com.pimenta.petshop.model.UsuarioDTO;
import com.pimenta.petshop.model.UsuarioEntity;
import com.pimenta.petshop.security.JwtUtil;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
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

    @Mock
    private MeterRegistry meterRegistry;

    @InjectMocks
    private AuthService authService;

    private UsuarioDTO usuarioDTO;
    private UsuarioEntity usuarioEntity;
    private Counter userRegisterCounter;
    private Counter userLoginCounter;

    @BeforeEach
    public void setUp() throws Exception {
        usuarioDTO = new UsuarioDTO();
        usuarioDTO.setCpf("12345678901");
        usuarioDTO.setNome("Teste");
        usuarioDTO.setROLE(ROLE.ADMIN);
        usuarioDTO.setSenha("senha");

        usuarioEntity = UsuarioEntity.builder().cpf("12345678901").nome("Teste").ROLE(ROLE.ADMIN).senha("hashedSenha").build();

        userRegisterCounter = mock(Counter.class);
        userLoginCounter = mock(Counter.class);

        when(meterRegistry.counter(eq("user_register_success_total"), any(), any())).thenReturn(userRegisterCounter);
        when(meterRegistry.counter(eq("user_login_attempt_total"), any(), any())).thenReturn(userLoginCounter);

        when(jwtUtil.generateToken(any(UsuarioEntity.class))).thenReturn("token123");

        Method initMethod = AuthService.class.getDeclaredMethod("init");
        initMethod.setAccessible(true);
        initMethod.invoke(authService);
    }

    @Test
    public void testRegister() {
        when(passwordEncoder.encode("senha")).thenReturn("hashedSenha");
        when(usuarioMapper.toDto(any(UsuarioEntity.class))).thenReturn(usuarioDTO);
        when(usuarioService.createUsuario(any(UsuarioDTO.class))).thenReturn(usuarioDTO);

        AuthResponse response = authService.register(usuarioDTO);

        assertNotNull(response);
        assertEquals("token123", response.getToken());
        verify(userRegisterCounter, times(1)).increment();
    }

    @Test
    public void testLogin() {
        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(usuarioService.getUsuarioByCpf("12345678901")).thenReturn(usuarioDTO);
        when(usuarioMapper.toEntity(any(UsuarioDTO.class))).thenReturn(usuarioEntity);

        AuthResponse response = authService.login(usuarioDTO);

        assertNotNull(response);
        assertEquals("token123", response.getToken());
        verify(userLoginCounter, times(1)).increment();
    }

    @Test

    public void testLogin_WithInvalidCredentials() {

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenThrow(new RuntimeException("Invalid credentials"));
        RuntimeException exception = assertThrows(RuntimeException.class, () -> authService.login(usuarioDTO));
        assertTrue(exception.getMessage().contains("Erro de autenticação"));
    }
}