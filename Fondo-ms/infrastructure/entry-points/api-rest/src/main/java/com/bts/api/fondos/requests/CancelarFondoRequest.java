package com.bts.api.fondos.requests;

import jakarta.validation.constraints.NotBlank;

public record CancelarFondoRequest(
        @NotBlank(message = "el ID del cliente no puede estar vacio")
        String clienteId,

        @NotBlank(message = "El ID del fondo no puede estar vacio")
        String fondoId
) {
}
