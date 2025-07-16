package com.bts.usecase.fondos;

import com.bts.model.common.exception.ErrorException;
import com.bts.model.fondo.Fondo;
import com.bts.model.fondo.gateway.FondoGateway;
import com.bts.model.mail.MailGateway;
import com.bts.model.notificacion.gateway.NotificacionGateway;
import com.bts.model.transaccion.TipoTransaccion;
import com.bts.model.transaccion.Transaccion;
import com.bts.model.transaccion.gateway.TransaccionGateway;
import com.bts.model.usuario.Usuario;
import com.bts.model.usuario.gateways.UsuarioGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FondosUseCaseTest {

    @Mock
    private FondoGateway fondoGateway;

    @Mock
    private TransaccionGateway transaccionGateway;

    @Mock
    private UsuarioGateway usuarioGateway;

    @Mock
    private MailGateway mailGateway;

    @Mock
    private NotificacionGateway notificacionGateway;

    @InjectMocks
    private FondosUseCase fondosUseCase;

    @Test
    public void suscribirSuccessTest() throws Exception {
        Usuario usuario1 = Usuario.builder()
                .id("usr_001")
                .nombre("Juan Pérez")
                .email("juan.perez@email.com")
                .password("$2a$10$encrypted_password_hash")
                .rol("CLIENTE")
                .fechaCreacion(LocalDateTime.now())
                .activo(true)
                .saldoDisponible(new BigDecimal("500000.00"))
                .notificacionPreferencia("EMAIL")
                .build();

        Fondo fondo = Fondo.builder()
                .fondoId("1")
                .nombre("FPV_BTG_PACTUAL_RECAUDADORA")
                .montoMinimo(new BigDecimal("75000.00"))
                .categoria("FPV")
                .build();
        Transaccion suscripcion = Transaccion.builder()
                .transaccionId("TXN_001")
                .clienteId("usr_001")
                .fondoId("1")
                .tipo("SUSCRIPCION")
                .monto(new BigDecimal("500000.00"))
                .fecha(LocalDateTime.now())
                .build();

        when(usuarioGateway.findById("usr_001")).thenReturn(usuario1);
        when(fondoGateway.findById("1")).thenReturn(fondo);
        when(transaccionGateway.save(any(Transaccion.class))).thenReturn(suscripcion);
        when(usuarioGateway.save(any(Usuario.class))).thenReturn(usuario1);

        Transaccion transaccionGuardado = fondosUseCase.suscribir(suscripcion);

        Assertions.assertEquals(suscripcion, transaccionGuardado);

    }

    @Test
    public void suscribirErrorWhenNoSaveUserTest() throws Exception {
        Usuario usuario1 = Usuario.builder()
                .id("usr_001")
                .nombre("Juan Pérez")
                .email("juan.perez@email.com")
                .password("$2a$10$encrypted_password_hash")
                .rol("CLIENTE")
                .fechaCreacion(LocalDateTime.now())
                .activo(true)
                .saldoDisponible(new BigDecimal("500000.00"))
                .notificacionPreferencia("EMAIL")
                .build();

        Fondo fondo = Fondo.builder()
                .fondoId("1")
                .nombre("FPV_BTG_PACTUAL_RECAUDADORA")
                .montoMinimo(new BigDecimal("75000.00"))
                .categoria("FPV")
                .build();
        Transaccion suscripcion = Transaccion.builder()
                .transaccionId("TXN_001")
                .clienteId("usr_001")
                .fondoId("1")
                .tipo("SUSCRIPCION")
                .monto(new BigDecimal("500000.00"))
                .fecha(LocalDateTime.now())
                .build();

        when(usuarioGateway.findById("usr_001")).thenReturn(usuario1);
        when(fondoGateway.findById("1")).thenReturn(fondo);
        when(usuarioGateway.save(any(Usuario.class))).thenReturn(null);

        Assertions.assertThrows(ErrorException.class, ()-> fondosUseCase.suscribir(suscripcion));
    }

    @Test
    public void suscribirErrorWhenSaldoMenorMontoUserTest() throws Exception {
        Usuario usuario1 = Usuario.builder()
                .id("usr_001")
                .nombre("Juan Pérez")
                .email("juan.perez@email.com")
                .password("$2a$10$encrypted_password_hash")
                .rol("CLIENTE")
                .fechaCreacion(LocalDateTime.now())
                .activo(true)
                .saldoDisponible(new BigDecimal("500000.00"))
                .notificacionPreferencia("EMAIL")
                .build();

        Fondo fondo = Fondo.builder()
                .fondoId("1")
                .nombre("FPV_BTG_PACTUAL_RECAUDADORA")
                .montoMinimo(new BigDecimal("75000.00"))
                .categoria("FPV")
                .build();
        Transaccion suscripcion = Transaccion.builder()
                .transaccionId("TXN_001")
                .clienteId("usr_001")
                .fondoId("1")
                .tipo("SUSCRIPCION")
                .monto(new BigDecimal("550000.00"))
                .fecha(LocalDateTime.now())
                .build();

        when(usuarioGateway.findById("usr_001")).thenReturn(usuario1);
        when(fondoGateway.findById("1")).thenReturn(fondo);

        Assertions.assertThrows(ErrorException.class, ()-> fondosUseCase.suscribir(suscripcion));
    }
    @Test
    public void suscribirErrorWhenSaldoMenorMontoMinimoFondoUserTest() throws Exception {
        Usuario usuario1 = Usuario.builder()
                .id("usr_001")
                .nombre("Juan Pérez")
                .email("juan.perez@email.com")
                .password("$2a$10$encrypted_password_hash")
                .rol("CLIENTE")
                .fechaCreacion(LocalDateTime.now())
                .activo(true)
                .saldoDisponible(new BigDecimal("50000.00"))
                .notificacionPreferencia("EMAIL")
                .build();

        Fondo fondo = Fondo.builder()
                .fondoId("1")
                .nombre("FPV_BTG_PACTUAL_RECAUDADORA")
                .montoMinimo(new BigDecimal("75000.00"))
                .categoria("FPV")
                .build();
        Transaccion suscripcion = Transaccion.builder()
                .transaccionId("TXN_001")
                .clienteId("usr_001")
                .fondoId("1")
                .tipo("SUSCRIPCION")
                .monto(new BigDecimal("550000.00"))
                .fecha(LocalDateTime.now())
                .build();

        when(usuarioGateway.findById("usr_001")).thenReturn(usuario1);
        when(fondoGateway.findById("1")).thenReturn(fondo);

        Assertions.assertThrows(ErrorException.class, ()-> fondosUseCase.suscribir(suscripcion));
    }

    @Test
    public void suscribirErrorWhenNoOneUserTest() throws Exception {
        Transaccion suscripcion = Transaccion.builder()
                .transaccionId("TXN_001")
                .clienteId("usr_001")
                .fondoId("1")
                .tipo("SUSCRIPCION")
                .monto(new BigDecimal("500000.00"))
                .fecha(LocalDateTime.now())
                .build();


        Assertions.assertThrows(ErrorException.class, ()-> fondosUseCase.suscribir(suscripcion));
    }
    @Test
    public void suscribirErrorWhenNoFindOneFondoTest() throws Exception {
        Usuario usuario1 = Usuario.builder()
                .id("usr_001")
                .nombre("Juan Pérez")
                .email("juan.perez@email.com")
                .password("$2a$10$encrypted_password_hash")
                .rol("CLIENTE")
                .fechaCreacion(LocalDateTime.now())
                .activo(true)
                .saldoDisponible(new BigDecimal("500000.00"))
                .notificacionPreferencia("EMAIL")
                .build();

        Transaccion suscripcion = Transaccion.builder()
                .transaccionId("TXN_001")
                .clienteId("usr_001")
                .fondoId("1")
                .tipo("SUSCRIPCION")
                .monto(new BigDecimal("500000.00"))
                .fecha(LocalDateTime.now())
                .build();

        when(usuarioGateway.findById("usr_001")).thenReturn(usuario1);
        Assertions.assertThrows(ErrorException.class, ()-> fondosUseCase.suscribir(suscripcion));
    }
    @Test
    public void CancelarSuccessTest() throws Exception {
        Usuario usuario1 = Usuario.builder()
                .id("usr_001")
                .nombre("Juan Pérez")
                .email("juan.perez@email.com")
                .password("$2a$10$encrypted_password_hash")
                .rol("CLIENTE")
                .fechaCreacion(LocalDateTime.now())
                .activo(true)
                .saldoDisponible(new BigDecimal("500000.00"))
                .notificacionPreferencia("EMAIL")
                .build();

        Fondo fondo = Fondo.builder()
                .fondoId("1")
                .nombre("FPV_BTG_PACTUAL_RECAUDADORA")
                .montoMinimo(new BigDecimal("75000.00"))
                .categoria("FPV")
                .build();
        Transaccion suscripcion = Transaccion.builder()
                .transaccionId("TXN_001")
                .clienteId("usr_001")
                .fondoId("1")
                .tipo("SUSCRIPCION")
                .monto(new BigDecimal("500000.00"))
                .fecha(LocalDateTime.now())
                .build();

        when(usuarioGateway.findById("usr_001")).thenReturn(usuario1);
        when(fondoGateway.findById("1")).thenReturn(fondo);
        when(transaccionGateway.save(any(Transaccion.class))).thenReturn(suscripcion);
        when(usuarioGateway.save(any(Usuario.class))).thenReturn(usuario1);
        when(transaccionGateway.findByClienteIdAndFondoId(any(), any())).thenReturn(List.of(suscripcion));

        Transaccion transaccionGuardado = fondosUseCase.cancelarFondo(suscripcion);

        Assertions.assertEquals(suscripcion, transaccionGuardado);

    }

    @Test
    public void CancelarErrorWhenTipoCANCELACIONTest() throws Exception {
        Usuario usuario1 = Usuario.builder()
                .id("usr_001")
                .nombre("Juan Pérez")
                .email("juan.perez@email.com")
                .password("$2a$10$encrypted_password_hash")
                .rol("CLIENTE")
                .fechaCreacion(LocalDateTime.now())
                .activo(true)
                .saldoDisponible(new BigDecimal("500000.00"))
                .notificacionPreferencia("EMAIL")
                .build();

        Fondo fondo = Fondo.builder()
                .fondoId("1")
                .nombre("FPV_BTG_PACTUAL_RECAUDADORA")
                .montoMinimo(new BigDecimal("75000.00"))
                .categoria("FPV")
                .build();
        Transaccion suscripcion = Transaccion.builder()
                .transaccionId("TXN_001")
                .clienteId("usr_001")
                .fondoId("1")
                .tipo(TipoTransaccion.CANCELACION.getTipo())
                .monto(new BigDecimal("500000.00"))
                .fecha(LocalDateTime.now())
                .build();

        when(usuarioGateway.findById("usr_001")).thenReturn(usuario1);
        when(fondoGateway.findById("1")).thenReturn(fondo);
        when(transaccionGateway.findByClienteIdAndFondoId(any(), any())).thenReturn(List.of(suscripcion));

        Assertions.assertThrows(ErrorException.class, ()-> fondosUseCase.cancelarFondo(suscripcion));;

    }

    @Test
    public void obtenerFondosTest(){
        when(fondoGateway.findAll()).thenReturn(List.of());
        List<Fondo> listaFondos = fondosUseCase.obtenerFondos();
        Assertions.assertNotNull(listaFondos);
    }

}
