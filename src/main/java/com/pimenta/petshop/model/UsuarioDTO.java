package com.pimenta.petshop.model;

import com.pimenta.petshop.enums.ROLE;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UsuarioDTO {

    @NotBlank(message = "CPF é obrigatório")
    @Pattern(regexp = "\\d{11}", message = "CPF deve conter 11 dígitos")
    private String cpf;

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @Pattern(regexp = "^(ADMIN|CLIENTE)$", message = "Perfil inválido")
    private ROLE ROLE;

    @NotBlank(message = "Senha é obrigatória")
    private String senha;
}