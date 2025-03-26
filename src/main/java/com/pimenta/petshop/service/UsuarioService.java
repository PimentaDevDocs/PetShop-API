package com.pimenta.petshop.service;

import com.pimenta.petshop.exception.NotFoundException;
import com.pimenta.petshop.mapper.UsuarioMapper;
import com.pimenta.petshop.model.UsuarioDTO;
import com.pimenta.petshop.model.UsuarioEntity;
import com.pimenta.petshop.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    @Transactional
    public UsuarioDTO createUsuario(UsuarioDTO dto) {
        UsuarioEntity usuario = usuarioMapper.toEntity(dto);
        usuario.setSenha(dto.getSenha());
        return usuarioMapper.toDto(usuarioRepository.save(usuario));
    }

    public List<UsuarioDTO> getAllUsuarios() {
        return usuarioRepository.findAll().stream()
                .map(usuarioMapper::toDtoWithoutPassword)
                .toList();
    }

    public UsuarioDTO getUsuarioByCpf(String cpf) {
        return usuarioRepository.findByCpf(cpf)
                .map(usuarioMapper::toDtoWithoutPassword)
                .orElseThrow(() -> new NotFoundException(UsuarioEntity.class, "cpf", cpf));
    }

    @Transactional
    public void deleteUsuario(String cpf) {
        usuarioRepository.deleteByCpf(cpf);
    }
}