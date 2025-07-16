package com.bts.model.transaccion;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransaccionFilters {
    private String transaccionId;
    private String clienteId;
    private String fondoId;
    private String tipo;
}