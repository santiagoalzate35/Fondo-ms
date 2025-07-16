package com.bts.model.usuario;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Usuario {

    private String id;

    private String nombre;

    private String email;

    private String password;

    private String rol;

    private LocalDateTime fechaCreacion;

    private Boolean activo;

    private BigDecimal saldoDisponible;

    private String notificacionPreferencia;
}
