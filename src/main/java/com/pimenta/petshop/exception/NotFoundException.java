package com.pimenta.petshop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

    public NotFoundException(Class<?> resourceClass, String fieldName, Object fieldValue) {
        super(String.format("%s não encontrado(a) com %s: '%s'", getResourceName(resourceClass), fieldName, fieldValue));
    }

    public NotFoundException(Class<?> resourceClass) {
        super(String.format("%s não encontrado(a)", getResourceName(resourceClass)));
    }

    private static String getResourceName(Class<?> resourceClass) {
        String className = resourceClass.getSimpleName();
        return className.replaceAll("(Entity|DTO)$", "");
    }

    public static NotFoundException of(Class<?> resourceClass) {
        return new NotFoundException(resourceClass);
    }

    public static NotFoundException of(Class<?> resourceClass, String fieldName, Object fieldValue) {
        return new NotFoundException(resourceClass, fieldName, fieldValue);
    }
}