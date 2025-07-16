package com.bts.model.notificacion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Notificacion {

    private String notificacionId;

    private String clienteId;

    private String tipo;

    private String fondoId;
}