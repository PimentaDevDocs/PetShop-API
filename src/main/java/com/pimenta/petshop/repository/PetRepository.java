package com.pimenta.petshop.repository;

import com.pimenta.petshop.model.PetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PetRepository extends JpaRepository<PetEntity, Long> {

    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END " +
            "FROM PetEntity p WHERE p.id = :petId AND p.cliente.cpf = :cpf")
    boolean existsByIdAndClienteCpf(@Param("petId") Long petId, @Param("cpf") String cpf);

    List<PetEntity> findByClienteCpf(String cpf);
}