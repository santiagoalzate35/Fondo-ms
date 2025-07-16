package com.bts.mongo.fondo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "fondos")
public class FondoDocument {

    @Id
    @Field("_id")
    private String fondoId;

    @Field("nombre")
    private String nombre;

    @Field("montoMinimo")
    private BigDecimal montoMinimo;

    @Field("categoria")
    private String categoria;

}