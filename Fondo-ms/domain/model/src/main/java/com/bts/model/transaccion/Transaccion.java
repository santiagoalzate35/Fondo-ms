package com.bts.model.transaccion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Transaccion {

    private String transaccionId;

    private String clienteId;

    private String fondoId;

    private String tipo;

    private BigDecimal monto;

    private LocalDateTime fecha;

}
