package com.pimenta.petshop.repository;

import com.pimenta.petshop.model.FotoEntity;
import com.pimenta.petshop.model.PetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FotoRepository extends JpaRepository<FotoEntity, Long> {

    @Query("SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END " +
            "FROM FotoEntity f WHERE f.id = :fotoId AND f.cliente.cpf = :cpf")
    boolean existsByIdAndClienteCpf(@Param("fotoId") Long fotoId, @Param("cpf") String cpf);

    List<FotoEntity> findByClienteCpf(String cpf);

    List<FotoEntity> findByPetId(Long petId);

    Long pet(PetEntity pet);
}
