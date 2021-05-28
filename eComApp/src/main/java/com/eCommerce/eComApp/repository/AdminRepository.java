package com.eCommerce.eComApp.repository;

import com.eCommerce.eComApp.model.Admin;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AdminRepository extends MongoRepository<Admin,String> {

    public Optional<Admin> findByEmail(String email);
    public Optional<Admin> findByUserName(String userName);
}
