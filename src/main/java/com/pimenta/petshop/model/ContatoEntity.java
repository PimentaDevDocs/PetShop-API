package com.pimenta.petshop.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "contato")
public class ContatoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_cpf", referencedColumnName = "cpf")  // Relacionamento com Cliente
    private ClienteEntity cliente;

    private String tipo;    // Tipo (email, telefone, etc)
    private String valor;   // O valor do contato (e.g., telefone ou e-mail)
    private String tag;     // Uma tag para identificar ou categorizar o contato
}
