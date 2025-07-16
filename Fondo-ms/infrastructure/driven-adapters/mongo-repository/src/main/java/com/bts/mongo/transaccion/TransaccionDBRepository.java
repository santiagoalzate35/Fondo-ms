package com.bts.mongo.transaccion;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.bts.model.transaccion.Transaccion;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.List;

public interface TransaccionDBRepository extends MongoRepository<TransaccionDocument, String> , QueryByExampleExecutor<TransaccionDocument> {
    List<Transaccion> findByClienteIdAndFondoId(String clienteId, String fondoId);

    @Query("{ '$or': [ " +
            "  { 'transaccionId': { $regex: ?0, $options: 'i' } }, " +
            "  { 'clienteId': { $regex: ?1, $options: 'i' } }, " +
            "  { 'tipo':         { $regex: ?2, $options: 'i' } }, " +
            "  { 'fondoId':      { $regex: ?3, $options: 'i' } } " +
            "] }")
    Page<TransaccionDocument> buscarTransacciones(
            String transaccionId,
            String clienteId,
            String tipo,
            String fondoId,
            Pageable pageable
    );
}
