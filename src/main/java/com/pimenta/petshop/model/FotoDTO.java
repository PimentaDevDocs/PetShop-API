package com.pimenta.petshop.model;

import com.pimenta.petshop.enums.TIPO_FOTO;
import lombok.Data;

@Data
public class FotoDTO {
    private Long id;
    private TIPO_FOTO tipoFoto;
    private byte[] dados;
    private String cpf;
    private Long idPet;
}
