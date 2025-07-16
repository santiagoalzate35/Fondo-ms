package com.bts.usecase.transaccion;

import com.bts.model.common.PageRequests;
import com.bts.model.transaccion.ListTransaccionDTO;
import com.bts.model.transaccion.TransaccionFilters;
import com.bts.model.transaccion.gateway.TransaccionGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransaccionUseCaseTest {

    private TransaccionGateway transaccionGateway;
    private TransaccionUseCase transaccionUseCase;

    @BeforeEach
    void setUp() {
        transaccionGateway = mock(TransaccionGateway.class);
        transaccionUseCase = new TransaccionUseCase(transaccionGateway);
    }

    @Test
    void obtenerTransacciones_deberiaRetornarDTO() throws Exception {
        TransaccionFilters filters = TransaccionFilters.builder().clienteId("1").build();
        PageRequests pageRequests = PageRequests.builder().page(0).size(10).build();
        ListTransaccionDTO expected = ListTransaccionDTO.builder().page(1).build();

        when(transaccionGateway.findByFilters(filters, pageRequests)).thenReturn(expected);

        ListTransaccionDTO result = transaccionUseCase.obtenerTransacciones(filters, pageRequests);

        assertEquals(expected, result);
        verify(transaccionGateway, times(1)).findByFilters(filters, pageRequests);
    }

    @Test
    void obtenerTransacciones_deberiaPropagarExcepcion() throws Exception {
        TransaccionFilters filters = TransaccionFilters.builder().clienteId("1").build();
        PageRequests pageRequests = PageRequests.builder().page(0).size(10).build();

        when(transaccionGateway.findByFilters(filters, pageRequests)).thenThrow(new RuntimeException("Error"));

        assertThrows(RuntimeException.class, () -> {
            transaccionUseCase.obtenerTransacciones(filters, pageRequests);
        });
        verify(transaccionGateway, times(1)).findByFilters(filters, pageRequests);
    }

    @Test
    void obtenerTransaccionesClienteId_deberiaRetornarDTO() throws Exception {
        String clienteId = "1";
        PageRequests pageRequests = PageRequests.builder().page(0).size(10).build();
        TransaccionFilters expectedFilters = TransaccionFilters.builder().clienteId(clienteId).build();
        ListTransaccionDTO expected = ListTransaccionDTO.builder().page(1).build();

        when(transaccionGateway.findByFilters(expectedFilters, pageRequests)).thenReturn(expected);

        ListTransaccionDTO result = transaccionUseCase.obtenerTransaccionesClienteId(clienteId, pageRequests);

        assertEquals(expected, result);
        verify(transaccionGateway, times(1)).findByFilters(expectedFilters, pageRequests);
    }

    @Test
    void obtenerTransaccionesClienteId_deberiaPropagarExcepcion() throws Exception {
        String clienteId = "1";
        PageRequests pageRequests = PageRequests.builder().page(0).size(10).build();
        TransaccionFilters expectedFilters = TransaccionFilters.builder().clienteId(clienteId).build();

        when(transaccionGateway.findByFilters(expectedFilters, pageRequests)).thenThrow(new RuntimeException("Error"));

        assertThrows(RuntimeException.class, () -> {
            transaccionUseCase.obtenerTransaccionesClienteId(clienteId, pageRequests);
        });
        verify(transaccionGateway, times(1)).findByFilters(expectedFilters, pageRequests);
    }
}