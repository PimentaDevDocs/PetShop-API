package com.pimenta.petshop.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ClienteDTO {
    private String nome;
    private String cpf;
    private LocalDate dataCadastro;
    private List<ContatoDTO> contatos;
    private List<EnderecoDTO> enderecos;
    private List<PetDTO> pets;
}