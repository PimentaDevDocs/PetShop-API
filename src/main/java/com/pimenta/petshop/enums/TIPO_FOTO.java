package com.pimenta.petshop.enums;

import lombok.Getter;

@Getter
public enum TIPO_FOTO {
    PNG("png"),
    JPEG("jpeg");

    private final String tipoFoto;

    TIPO_FOTO(String tipoFoto) {
        this.tipoFoto = tipoFoto;
    }
}