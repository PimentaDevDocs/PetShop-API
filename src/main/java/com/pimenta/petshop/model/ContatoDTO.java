package com.pimenta.petshop.model;

import lombok.Data;

@Data
public class ContatoDTO {
    private Long id;
    private String tipo;
    private String descricao;
    private String tag;
    private String cpf;
}