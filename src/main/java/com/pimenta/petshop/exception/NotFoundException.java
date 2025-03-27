package com.pimenta.petshop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

    public NotFoundException(Class<?> resourceClass, String fieldValue) {
        super(String.format("%s não encontrado(a) para valor: '%s'", getResourceName(resourceClass), fieldValue));
    }

    private static String getResourceName(Class<?> resourceClass) {
        String className = resourceClass.getSimpleName();
        return className.replaceAll("(Entity|DTO)$", "");
    }
}