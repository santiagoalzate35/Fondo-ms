package com.bts.mongo.fondo;

import com.bts.model.common.exception.ErrorException;
import com.bts.model.common.exception.CodeError;
import com.bts.model.fondo.Fondo;
import com.bts.model.fondo.gateway.FondoGateway;
import com.bts.mongo.helper.AdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;

@Repository
public class FondoRepositoryAdapter extends AdapterOperations<Fondo, FondoDocument, String, FondoDBRepository>
implements FondoGateway
{
    public FondoRepositoryAdapter(FondoDBRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Fondo.class));
    }
}
