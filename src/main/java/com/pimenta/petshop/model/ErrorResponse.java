package com.pimenta.petshop.model;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorResponse(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        String path,
        List<ValidationError> details
) {
    public record ValidationError(String field, String message) {
    }
}