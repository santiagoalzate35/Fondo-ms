package com.bts.model.notificacion.gateway;

import com.bts.model.notificacion.Notificacion;

public interface NotificacionGateway {
    Notificacion save(Notificacion notificacion);
}
