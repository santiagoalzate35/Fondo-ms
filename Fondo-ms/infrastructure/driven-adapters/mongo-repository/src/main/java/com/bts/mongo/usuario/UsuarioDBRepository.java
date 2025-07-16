package com.bts.mongo.usuario;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface UsuarioDBRepository extends MongoRepository<UsuarioDocument, String> , QueryByExampleExecutor<UsuarioDocument> {
    Boolean existsByEmailAndActivoTrue(String email);

    UsuarioDocument findByEmailAndActivoTrue(String email);
}
