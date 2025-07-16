package com.bts.mongo.transaccion;

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
@Document(collection = "transacciones")
public class TransaccionDocument {

    @Id
    private String transaccionId;

    @Field("clienteId")
    private String clienteId;

    @Field("fondoId")
    private String fondoId;

    @Field("tipo")
    private String tipo;

    @Field("monto")
    private BigDecimal monto;

    @Field("fecha")
    private LocalDateTime fecha;

}