package com.bts.api.fondos.requests;

import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record SuscribirFondoRequest (
        @NotBlank(message = "el ID del cliente no puede estar vacio")
        String clienteId,

        @NotBlank(message = "El ID del fondo no puede estar vacio")
        String fondoId,

        BigDecimal monto
){
}
