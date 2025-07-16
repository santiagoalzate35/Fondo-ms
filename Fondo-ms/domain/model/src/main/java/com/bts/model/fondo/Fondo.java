package com.bts.model.fondo;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)

public class Fondo {

    private String fondoId;

    private String nombre;

    private BigDecimal montoMinimo;

    private String categoria;
}