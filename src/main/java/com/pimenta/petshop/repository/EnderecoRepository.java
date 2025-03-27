package com.pimenta.petshop.repository;

import com.pimenta.petshop.model.EnderecoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EnderecoRepository extends JpaRepository<EnderecoEntity, Long> {

    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END " +
            "FROM EnderecoEntity e WHERE e.id = :enderecoId AND e.cliente.cpf = :cpf")
    boolean existsByIdAndClienteCpf(@Param("enderecoId") Long enderecoId, @Param("cpf") String cpf);

    List<EnderecoEntity> findByClienteCpf(String cpf);
}