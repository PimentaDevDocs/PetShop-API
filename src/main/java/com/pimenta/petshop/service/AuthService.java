package com.pimenta.petshop.service;

import com.pimenta.petshop.mapper.UsuarioMapper;
import com.pimenta.petshop.model.AuthResponse;
import com.pimenta.petshop.model.UsuarioDTO;
import com.pimenta.petshop.model.UsuarioEntity;
import com.pimenta.petshop.security.JwtUtil;
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

    public AuthResponse register(UsuarioDTO usuarioDTO) {
        var user = UsuarioEntity.builder()
                .cpf(usuarioDTO.getCpf())
                .nome(usuarioDTO.getNome())
                .ROLE(usuarioDTO.getROLE())
                .senha(passwordEncoder.encode(usuarioDTO.getSenha()))
                .build();

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

