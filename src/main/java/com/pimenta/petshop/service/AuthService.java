package com.pimenta.petshop.service;

import com.pimenta.petshop.mapper.UsuarioMapper;
import com.pimenta.petshop.model.AuthResponse;
import com.pimenta.petshop.model.UsuarioDTO;
import com.pimenta.petshop.model.UsuarioEntity;
import com.pimenta.petshop.security.JwtUtil;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UsuarioService usuarioService;
    private final UsuarioMapper usuarioMapper;
    private final MeterRegistry meterRegistry;
    private Counter userRegisterCounter;
    private Counter userLoginCounter;

    @PostConstruct
    private void init() {
        this.userRegisterCounter = this.meterRegistry.counter("user_register_success_total", "service", "auth_service");
        this.userLoginCounter = meterRegistry.counter("user_login_attempt_total","service", "auth_service");
    }

    public AuthResponse register(UsuarioDTO usuarioDTO) {
        var user = UsuarioEntity.builder()
                .cpf(usuarioDTO.getCpf())
                .nome(usuarioDTO.getNome())
                .ROLE(usuarioDTO.getROLE())
                .senha(passwordEncoder.encode(usuarioDTO.getSenha()))
                .build();

        userRegisterCounter.increment();
        usuarioService.createUsuario(usuarioMapper.toDto(user));

        return AuthResponse.builder()
                .token(jwtUtil.generateToken(user))
                .build();
    }

    public AuthResponse login(UsuarioDTO usuarioDTO) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            usuarioDTO.getCpf(),
                            usuarioDTO.getSenha()
                    )
            );

            userLoginCounter.increment();

            UsuarioDTO user = usuarioService.getUsuarioByCpf(usuarioDTO.getCpf());

            return AuthResponse.builder()
                    .token(jwtUtil.generateToken(usuarioMapper.toEntity(user)))
                    .build();
        } catch (AuthenticationException e) {
            throw new AuthorizationDeniedException(e.getMessage());
        } catch (RuntimeException e) {
            throw new RuntimeException("Erro de autenticação: " + e.getMessage(), e);
        }
    }
}

