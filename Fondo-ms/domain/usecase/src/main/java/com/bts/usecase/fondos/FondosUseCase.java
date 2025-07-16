package com.bts.usecase.fondos;

import com.bts.model.common.exception.CodeError;
import com.bts.model.common.exception.ErrorException;
import com.bts.model.fondo.Fondo;
import com.bts.model.fondo.gateway.FondoGateway;
import com.bts.model.mail.EmailDto;
import com.bts.model.mail.MailGateway;
import com.bts.model.notificacion.Notificacion;
import com.bts.model.notificacion.gateway.NotificacionGateway;
import com.bts.model.transaccion.TipoTransaccion;
import com.bts.model.transaccion.Transaccion;
import com.bts.model.transaccion.gateway.TransaccionGateway;
import com.bts.model.usuario.Usuario;
import com.bts.model.usuario.gateways.UsuarioGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

@Log
@RequiredArgsConstructor
public class FondosUseCase {

    private final FondoGateway fondoGateway;
    private final TransaccionGateway transaccionGateway;
    private final UsuarioGateway usuarioGateway;
    private final MailGateway mailGateway;
    private final NotificacionGateway notificacionGateway;

    public Transaccion suscribir(Transaccion transaccion) throws Exception {
        log.info("Inicio la suscripcion a un fondo");
        Usuario usuario = buscarUsuarioPorId(transaccion.getClienteId());

        Fondo fondo = buscarFondoPorId(transaccion.getFondoId());

        if (usuario.getSaldoDisponible().compareTo(fondo.getMontoMinimo()) < 0 ||
                transaccion.getMonto().compareTo(usuario.getSaldoDisponible()) > 0) {
            String mensaje = String.format(
                    "No tiene saldo disponible para vincularse al fondo %s", fondo.getNombre()
            );
            log.severe(mensaje);
            throw new ErrorException(mensaje, CodeError.UNPROCESSABLE_ENTITY);
        }
        log.info("Inscripcion al fondo: " + transaccion.getFondoId() + " Exitosa");
        disminuirSaldoDisponibleUsuario(usuario, transaccion);
        enviarEmailSuscripcion(usuario, fondo);
        return transaccionGateway.save(transaccion);
    }

    public Transaccion cancelarFondo(Transaccion transaccion) throws Exception {
        log.info("Inicio la transaccion de cancelar fondo");
        Transaccion ultimaTransaccion = transaccionGateway.findByClienteIdAndFondoId(transaccion.getClienteId(), transaccion.getFondoId())
                .stream().max(Comparator.comparing(Transaccion::getFecha)).get();

        Usuario usuario = buscarUsuarioPorId(transaccion.getClienteId());
        Fondo fondo =  buscarFondoPorId(transaccion.getFondoId());

        if(TipoTransaccion.CANCELACION.getTipo().equals(ultimaTransaccion.getTipo())) {
            log.severe("Error el usuario no se encuentra incrito en el fondo");
            throw new ErrorException("El usuario: "+usuario.getNombre()+" No esta Incrito en el fondo", CodeError.INTERNAL_SERVER_ERROR);
        }
        transaccion.setMonto(ultimaTransaccion.getMonto());
        aumentarSaldoDisponibleUsuario(usuario, transaccion);
        log.info("Se cancelo la suscripcion a el fondo: "+transaccion.getFondoId());
        enviarEmailCancelarSuscripcon(usuario, fondo);
        return transaccionGateway.save(transaccion);
    }

    public List<Fondo> obtenerFondos() {
        return fondoGateway.findAll();
    }

    private Usuario buscarUsuarioPorId(String id) throws ErrorException {
        Usuario usuario = usuarioGateway.findById(id);
        if (usuario == null) {
            log.severe("Usuario no encontrado");
            throw new ErrorException("Usuario no existente", CodeError.NOT_FOUND);
        }
        return usuario;
    }

    private Fondo buscarFondoPorId(String id) throws ErrorException {
        Fondo fondo = fondoGateway.findById(id);
        if (fondo == null) {
            log.severe("Fondo no encontrado");
            throw new ErrorException("Fondo no existente", CodeError.NOT_FOUND);
        }
        return fondo;
    }

    private void disminuirSaldoDisponibleUsuario(Usuario usuarioActual, Transaccion transaccion) throws ErrorException {
        log.info("Inicia disminucion de saldo disponible para el usuario:" + usuarioActual.getNombre());
        BigDecimal saldoDisponible = usuarioActual.getSaldoDisponible().subtract(transaccion.getMonto());
        usuarioActual.setSaldoDisponible(saldoDisponible);
        actualizarUsuario(usuarioActual);
    }

    private void aumentarSaldoDisponibleUsuario(Usuario usuarioActual, Transaccion transaccion) throws ErrorException {
        log.info("Inicia aumento de saldo disponible para el usuario:" + usuarioActual.getNombre());
        BigDecimal saldoDisponible = usuarioActual.getSaldoDisponible().add(transaccion.getMonto());
        usuarioActual.setSaldoDisponible(saldoDisponible);
        actualizarUsuario(usuarioActual);
    }

    private void actualizarUsuario(Usuario usuario) throws ErrorException {
        Usuario usuarioActualizado =  usuarioGateway.save(usuario);
        if(usuarioActualizado == null){
            log.severe("Ocurrio un error actualizando el usuario");
            throw new ErrorException("Ocurrio un error actualizando el usuario", CodeError.INTERNAL_SERVER_ERROR);
        }
        log.info("Usuario actualizado");
    }

    private void enviarEmailSuscripcion(Usuario usuario, Fondo fondo) throws Exception {
        EmailDto email = new EmailDto(
                "Suscripcion exitosa",
                usuario.getEmail(),
                usuario.getNombre(),
                fondo.getNombre(),
                true
        );
        guardarNotificacion(usuario, fondo, TipoTransaccion.SUSCRIPCION.getTipo());
        mailGateway.sendMail(email);
    }
    private void enviarEmailCancelarSuscripcon(Usuario usuario, Fondo fondo) throws Exception {
        EmailDto email = EmailDto.builder()
                .subject("Suscripcion Cancelada Exitosamente")
                .addressee(usuario.getEmail())
                .fondoName(fondo.getNombre())
                .userName(usuario.getNombre())
                .isSuscripcion(false)
                .build();
        guardarNotificacion(usuario, fondo, TipoTransaccion.CANCELACION.getTipo());
        mailGateway.sendMail(email);
    }
    private void guardarNotificacion(Usuario usuario, Fondo fondo, String tipo) throws Exception {
        Notificacion notificacion = Notificacion.builder()
                .fondoId(fondo.getFondoId())
                .clienteId(usuario.getId())
                .tipo(tipo)
                .build();
        Notificacion notificacion1 = notificacionGateway.save(notificacion);

    }
}
