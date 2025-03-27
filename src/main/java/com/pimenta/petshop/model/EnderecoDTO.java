package com.pimenta.petshop.model;

import lombok.Data;

@Data
public class EnderecoDTO {
    private Long id;
    private String cpf;
    private String logradouro;
    private String cidade;
    private String bairro;
    private String complemento;
    private String tag;
}