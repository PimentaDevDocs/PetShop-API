package com.pimenta.petshop.repository;

import com.pimenta.petshop.model.ContatoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContatoRepository extends JpaRepository<ContatoEntity, Long> {

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END " +
            "FROM ContatoEntity c WHERE c.id = :contatoId AND c.cliente.cpf = :cpf")
    boolean existsByIdAndClienteCpf(@Param("contatoId") Long contatoId,
                                    @Param("cpf") String cpf);

    List<ContatoEntity> findByClienteCpf(String cpf);

}