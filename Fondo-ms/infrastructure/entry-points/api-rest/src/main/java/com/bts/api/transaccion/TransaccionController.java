package com.bts.api.transaccion;

import com.bts.model.common.PageRequests;
import com.bts.model.transaccion.TransaccionFilters;
import com.bts.usecase.transaccion.TransaccionUseCase;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/admin/transacciones", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class TransaccionController {

    private final TransaccionUseCase transaccionUseCase;

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> obtenerTransacciones(
            @RequestParam(name = "transaccionId", required = false) String transaccionId,
            @RequestParam(name = "clienteId", required = false) String clienteId,
            @RequestParam(name = "fondoId", required = false) String fondoId,
            @RequestParam(name = "tipo", required = false) String tipo,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "sort", required = false) String sort
    ) throws Exception {

        TransaccionFilters filters = TransaccionFilters.builder()
                .transaccionId(transaccionId)
                .clienteId(clienteId)
                .fondoId(fondoId)
                .tipo(tipo)
                .build();

        PageRequests pageRequests = PageRequests.builder()
                .page(page)
                .size(size)
                .sort(sort)
                .build();

        return ResponseEntity.ok(transaccionUseCase.obtenerTransacciones(filters, pageRequests));
    }
}
