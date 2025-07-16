package com.bts.model.transaccion;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoTransaccion {
    SUSCRIPCION("SUSCRIPCION"), CANCELACION("CANCELACION");
    private final String tipo;

}
