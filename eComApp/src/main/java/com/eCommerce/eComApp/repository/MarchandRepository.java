package com.eCommerce.eComApp.repository;

import com.eCommerce.eComApp.model.Marchand;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MarchandRepository extends MongoRepository<Marchand,String> {
}
