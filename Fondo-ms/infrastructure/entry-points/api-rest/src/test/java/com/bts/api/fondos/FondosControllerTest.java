package com.bts.api.fondos;

import com.bts.api.fondos.requests.SuscribirFondoRequest;
import com.bts.model.common.PageRequests;
import com.bts.model.transaccion.ListTransaccionDTO;
import com.bts.model.transaccion.TipoTransaccion;
import com.bts.model.transaccion.Transaccion;
import com.bts.usecase.fondos.FondosUseCase;
import com.bts.usecase.transaccion.TransaccionUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.argThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ContextConfiguration(classes = {FondosController.class})
@AutoConfigureMockMvc(addFilters = true)
public class FondosControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FondosUseCase fondosUseCase;

    @MockBean
    private TransaccionUseCase transaccionUseCase;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String CLIENTE_ID = "1";
    private static final String FONDO_ID = "1";
    private static final BigDecimal MONTO = new BigDecimal("100000");

    @Test
    @WithMockUser(username = "test1", authorities = {"CLIENTE"})
    public void deberiaSuscribirFondoYRetornarStatusOk() throws Exception {
        // Given
        SuscribirFondoRequest request = new SuscribirFondoRequest(FONDO_ID, CLIENTE_ID, MONTO);
        Transaccion expected = crearTransaccionSuscripcion();

        when(fondosUseCase.suscribir(argThat(t ->
                t.getFondoId().equals(FONDO_ID) &&
                        t.getClienteId().equals(CLIENTE_ID) &&
                        t.getMonto().equals(MONTO)
        ))).thenReturn(expected);

        // When & Then
        mockMvc.perform(post("/api/v1/fondos/suscribir")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transaccionId", is("1")))
                .andExpect(jsonPath("$.tipo", is(TipoTransaccion.SUSCRIPCION.getTipo())));

        // Verificar interacciones
        verify(fondosUseCase, times(1)).suscribir(any(Transaccion.class));
    }

    @Test
    public void deberiaDevolverUnauthorizedSinAutenticacion() throws Exception {
        SuscribirFondoRequest request = new SuscribirFondoRequest(FONDO_ID, CLIENTE_ID, MONTO);

        mockMvc.perform(post("/api/v1/fondos/suscribir")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "test", authorities = {"CLIENTE"})
    public void deberiaObtenerFondosYRetornarStatusOk() throws Exception {
        when(fondosUseCase.obtenerFondos()).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/fondos/list")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "test", authorities = {"ADMIN"})
    public void deberiaObtenerHistorialDeTransaccionesYRetornarStatusOk() throws Exception {
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

        when(transaccionUseCase.obtenerTransaccionesClienteId(Mockito.eq(clienteId), Mockito.any(PageRequests.class)))
                .thenReturn(listTransaccionDTO);

        mockMvc.perform(get("/api/v1/fondos/historial/{id}", clienteId)
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transacciones").isArray())
                .andExpect(jsonPath("$.transacciones[0].transaccionId", is("tx123")))
                .andExpect(jsonPath("$.transacciones[0].clienteId", is(clienteId)))
                .andExpect(jsonPath("$.transacciones[0].tipo", is(TipoTransaccion.SUSCRIPCION.getTipo())));
    }


    private Transaccion crearTransaccionSuscripcion() {
        return Transaccion.builder()
                .fondoId(FONDO_ID)
                .clienteId(CLIENTE_ID)
                .monto(MONTO)
                .tipo(TipoTransaccion.SUSCRIPCION.getTipo())
                .transaccionId("1")
                .build();
    }
}