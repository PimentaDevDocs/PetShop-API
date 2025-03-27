package com.pimenta.petshop.repository;

import com.pimenta.petshop.model.AtendimentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AtendimentoRepository extends JpaRepository<AtendimentoEntity, Long> {

    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END " +
            "FROM AtendimentoEntity a WHERE a.id = :atendimentoId AND a.cliente.cpf = :cpf")
    boolean existsByIdAndClienteCpf(@Param("atendimentoId") Long atendimentoId, @Param("cpf") String cpf);

    List<AtendimentoEntity> findByClienteCpf(String cpf);

}