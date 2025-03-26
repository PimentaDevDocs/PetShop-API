package com.pimenta.petshop.model;

import com.pimenta.petshop.enums.ROLE;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuario")
public class UsuarioEntity {

    @Id
    @Column(name = "cpf", length = 11)
    private String cpf;

    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(name = "perfil")
    private ROLE ROLE;

    private String senha;
}