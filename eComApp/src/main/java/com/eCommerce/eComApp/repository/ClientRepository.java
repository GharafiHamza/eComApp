package com.eCommerce.eComApp.repository;

import com.eCommerce.eComApp.model.Client;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ClientRepository extends MongoRepository<Client,String> {
}
