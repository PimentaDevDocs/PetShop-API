package com.pimenta.petshop.model;

import com.pimenta.petshop.enums.TIPO_FOTO;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "foto")
public class FotoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Basic(fetch = FetchType.EAGER)
    @Column
    private byte[] dados;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private TIPO_FOTO tipoFoto;

    @ManyToOne
    @JoinColumn(name = "cliente_cpf", referencedColumnName = "cpf")
    private ClienteEntity cliente;

    @ManyToOne
    @JoinColumn(name = "id_pet")
    private PetEntity pet;
}
