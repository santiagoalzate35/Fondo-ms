package com.bts.mongo.usuario;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "usuarios")
public class UsuarioDocument {

    @Id
    private String id;

    @Field("nombre")
    private String nombre;

    @Field("email")
    private String email;

    @Field("password")
    private String password;

    @Field("rol")
    private String rol;

    @Field("fechaCreacion")
    private LocalDateTime fechaCreacion;

    @Field("activo")
    private Boolean activo;

    @Field("saldoDisponible")
    private BigDecimal saldoDisponible;

    @Field("notificacionPreferencia")
    private String notificacionPreferencia;

}