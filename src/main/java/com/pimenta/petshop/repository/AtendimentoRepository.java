package com.pimenta.petshop.repository;

import com.pimenta.petshop.model.AtendimentoEntity;
import com.pimenta.petshop.model.PetEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AtendimentoRepository extends JpaRepository<AtendimentoEntity, Long> {
    List<AtendimentoEntity> findByClienteCpf(String cpf);

    List<AtendimentoEntity> pet(PetEntity pet);
}