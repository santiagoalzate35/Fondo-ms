package com.bts.mongo.notificacion;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface NotificacionDBRepository extends MongoRepository<NotificacionDocument, String> , QueryByExampleExecutor<NotificacionDocument> {
}
