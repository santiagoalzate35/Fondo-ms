package com.bts.mongo.notificacion;

import com.bts.model.notificacion.Notificacion;
import com.bts.model.notificacion.gateway.NotificacionGateway;
import com.bts.mongo.helper.AdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;

@Repository
public class NotificacionRepositoryAdapter extends AdapterOperations<Notificacion, NotificacionDocument, String, NotificacionDBRepository>
 implements NotificacionGateway
{

    public NotificacionRepositoryAdapter(NotificacionDBRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Notificacion.class));
    }
}
