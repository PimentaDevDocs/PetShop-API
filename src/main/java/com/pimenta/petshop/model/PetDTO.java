package com.pimenta.petshop.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class PetDTO {
    private Long id;
    private String cpf;
    private Long idRaca;
    private String nome;
    private LocalDate dataNascimento;
    private List<FotoDTO> fotos;
}