package com.pimenta.petshop.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "atendimento")
public class AtendimentoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_cpf", referencedColumnName = "cpf")
    private ClienteEntity cliente;

    @ManyToOne
    @JoinColumn(name = "id_pet", nullable = false)
    private PetEntity pet;

    @Column(name = "data_atendimento")
    private LocalDateTime dataAtendimento;

    private String valor;
}