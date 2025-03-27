package com.pimenta.petshop.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "raca")
public class RacaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descricao;
}