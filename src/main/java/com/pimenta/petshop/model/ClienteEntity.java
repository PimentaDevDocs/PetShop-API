package com.pimenta.petshop.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "cliente")
public class ClienteEntity {

    @Id
    @Column(name = "cpf", length = 11, unique = true)
    private String cpf;

    private String nome;

    @Column(name = "data_cadastro")
    private LocalDate dataCadastro;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<ContatoEntity> contatos = new ArrayList<>();

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<EnderecoEntity> enderecos = new ArrayList<>();

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<PetEntity> pets = new ArrayList<>();

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<FotoEntity> fotos = new ArrayList<>();
}
