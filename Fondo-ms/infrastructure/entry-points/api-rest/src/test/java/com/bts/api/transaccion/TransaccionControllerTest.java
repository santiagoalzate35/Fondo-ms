package com.bts.api.transaccion;

import com.bts.api.config.SecurityConfig;
import com.bts.api.fondos.FondosController;
import com.bts.model.common.PageRequests;
import com.bts.model.transaccion.ListTransaccionDTO;
import com.bts.model.transaccion.TipoTransaccion;
import com.bts.model.transaccion.Transaccion;
import com.bts.model.transaccion.TransaccionFilters;
import com.bts.usecase.transaccion.TransaccionUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
@ContextConfiguration(classes = {
        TransaccionController.class,
})
@AutoConfigureMockMvc( addFilters = true)
class TransaccionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransaccionUseCase transaccionUseCase;

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    void deberiaObtenerTransaccionesConAdmin() throws Exception {
        String clienteId = "1";

        Transaccion transaccion = Transaccion.builder()
                .transaccionId("tx123")
                .clienteId(clienteId)
                .fondoId("f1")
                .tipo(TipoTransaccion.SUSCRIPCION.getTipo())
                .monto(BigDecimal.valueOf(100000))
                .build();

        ListTransaccionDTO listTransaccionDTO = ListTransaccionDTO.builder()
                .transacciones(List.of(transaccion))
                .page(1)
                .build();


        Mockito.when(transaccionUseCase.obtenerTransacciones(any(TransaccionFilters.class), any(PageRequests.class)))
                .thenReturn(listTransaccionDTO);

        mockMvc.perform(get("/api/admin/transacciones/all")
                        .param("transaccionId", "123")
                        .param("clienteId", "456")
                        .param("fondoId", "789")
                        .param("tipo", "COMPRA")
                        .param("page", "0")
                        .param("size", "5")
                        .param("sort", "fecha"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transacciones").isArray())
                .andExpect(jsonPath("$.transacciones[0].transaccionId", is("tx123")))
                .andExpect(jsonPath("$.transacciones[0].clienteId", is(clienteId)))
                .andExpect(jsonPath("$.transacciones[0].tipo", is(TipoTransaccion.SUSCRIPCION.getTipo())));

        Mockito.verify(transaccionUseCase).obtenerTransacciones(any(TransaccionFilters.class), any(PageRequests.class));
    }

    @Test
    void noDeberiaPermitirAccesoSinAutenticacion() throws Exception {
        mockMvc.perform(get("/api/admin/transacciones/all"))
                .andExpect(status().isUnauthorized());
    }
}
