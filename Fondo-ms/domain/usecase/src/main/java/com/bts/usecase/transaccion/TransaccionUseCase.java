package com.bts.usecase.transaccion;

import com.bts.model.common.PageRequests;
import com.bts.model.transaccion.ListTransaccionDTO;
import com.bts.model.transaccion.TransaccionFilters;
import com.bts.model.transaccion.gateway.TransaccionGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

@Log
@RequiredArgsConstructor
public class TransaccionUseCase {

    private final TransaccionGateway transaccionGateway;

    public ListTransaccionDTO obtenerTransacciones(TransaccionFilters filters, PageRequests pageRequests) throws Exception {
        log.info("Obteniendo transacciones");
        return transaccionGateway.findByFilters(filters, pageRequests);
    }

    public ListTransaccionDTO obtenerTransaccionesClienteId(String clienteId,PageRequests pageRequests) throws Exception {
        log.info("Obteniendo transacciones po clienteId");
        return transaccionGateway.findByFilters(
                TransaccionFilters.builder().clienteId(clienteId).build(),
                pageRequests);
    }

}
