package com.bts.mongo.fondo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface FondoDBRepository extends MongoRepository<FondoDocument, String> , QueryByExampleExecutor<FondoDocument> {
}
