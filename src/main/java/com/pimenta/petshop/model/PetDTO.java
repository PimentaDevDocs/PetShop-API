package com.pimenta.petshop.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PetDTO {
    private Long id;
    private String cpf;
    private Long idRaca;
    private String nome;
    private LocalDate dataNascimento;
}