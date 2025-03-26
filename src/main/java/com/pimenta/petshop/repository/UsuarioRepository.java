package com.pimenta.petshop.repository;

import com.pimenta.petshop.model.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, String> {
    Optional<UsuarioEntity> findByCpf(String cpf);

    void deleteByCpf(String cpf);
}