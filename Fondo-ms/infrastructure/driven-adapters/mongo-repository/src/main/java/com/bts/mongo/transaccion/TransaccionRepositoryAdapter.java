package com.bts.mongo.transaccion;

import com.bts.model.common.PageRequests;
import com.bts.model.transaccion.ListTransaccionDTO;
import com.bts.model.transaccion.Transaccion;
import com.bts.model.transaccion.TransaccionFilters;
import com.bts.model.transaccion.gateway.TransaccionGateway;
import com.bts.mongo.helper.AdapterOperations;
import lombok.extern.slf4j.Slf4j;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class TransaccionRepositoryAdapter extends AdapterOperations<Transaccion, TransaccionDocument, String, TransaccionDBRepository>
 implements TransaccionGateway
{

    public TransaccionRepositoryAdapter(TransaccionDBRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Transaccion.class));
    }

    @Override
    public List<Transaccion> findByClienteIdAndFondoId(String clienteId, String fondoId) {
        return repository.findByClienteIdAndFondoId(clienteId, fondoId);
    }

    @Override
    public ListTransaccionDTO findByFilters(TransaccionFilters filters, PageRequests pageRequests) throws Exception {
        try {
            Pageable pageable = PageRequest.of(
                    pageRequests.getPage(),
                    pageRequests.getSize(),
                    pageRequests.getSort() != null && !pageRequests.getSort().isEmpty()
                            ? Sort.by(pageRequests.getSort())
                            : Sort.unsorted()
            );
            Page<TransaccionDocument> transaccionDocuments = repository.buscarTransacciones(
                    Optional.ofNullable(filters.getTransaccionId()).orElse(".*"),
                    Optional.ofNullable(filters.getClienteId()).orElse(".*"),
                    Optional.ofNullable(filters.getTipo()).orElse(".*"),
                    Optional.ofNullable(filters.getFondoId()).orElse(".*"),
                    pageable
            );

            ListTransaccionDTO listMovieDTO = ListTransaccionDTO.builder()
                    .transacciones(transaccionDocuments.map(this::toEntity).toList())
                    .totalItems(transaccionDocuments.getTotalElements())
                    .page(transaccionDocuments.getNumber())
                    .build();

            log.info("Lista de transacciones: {}", listMovieDTO.getTotalItems());
            return listMovieDTO;
        } catch (Exception e) {
            log.info("Error listing transaccion by: {}", filters);
            throw new Exception(e);
        }
    }
}
