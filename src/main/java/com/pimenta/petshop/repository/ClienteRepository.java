package com.pimenta.petshop.repository;

import com.pimenta.petshop.model.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<ClienteEntity, String> {
    @Query("SELECT c FROM ClienteEntity c WHERE c.cpf = :cpf")
    Optional<ClienteEntity> findByCpf(String cpf);

    boolean existsByCpf(String cpf);
}