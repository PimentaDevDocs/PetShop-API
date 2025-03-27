package com.pimenta.petshop.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "endereco")
public class EnderecoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_cpf", referencedColumnName = "cpf")  // Relacionamento com Cliente
    private ClienteEntity cliente;

    private String logradouro;   // Rua, avenida, etc.
    private String cidade;
    private String bairro;
    private String complemento;  // Complemento do endereço
    private String tag;          // Tag para categorizar o endereço (ex: residencial, comercial)
}
