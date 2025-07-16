package com.bts.model.transaccion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ListTransaccionDTO {

    private List<Transaccion> transacciones;
    private Long totalItems;
    private Integer page;

}
