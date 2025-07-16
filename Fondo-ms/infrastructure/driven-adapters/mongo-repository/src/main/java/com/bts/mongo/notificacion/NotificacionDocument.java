package com.bts.mongo.notificacion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "notificaciones")
public class NotificacionDocument {

    @Id
    private String notificacionId;

    @Field("clienteId")
    private String clienteId;

    @Field("tipo")
    private String tipo;

    @Field("fondoId")
    private String fondoId;

}