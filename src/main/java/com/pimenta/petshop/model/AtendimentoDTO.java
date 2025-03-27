package com.pimenta.petshop.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AtendimentoDTO {
    private Long id;
    private String cpf;
    private Long idPet;
    private LocalDateTime dataAtendimento;
    private String valor;
}