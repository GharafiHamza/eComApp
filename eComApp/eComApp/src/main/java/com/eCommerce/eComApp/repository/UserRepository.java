package com.eCommerce.eComApp.repository;

import com.eCommerce.eComApp.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User,String> {
    public Optional<User> findByEmailAndPassword(String email, String password);

    public Optional<User> findByEmail(String email);

    public Optional<User> findByUserName(String userName);

    public Optional<List<User>> findByRole(String role);

    Optional<User> findByUserNameAndPassword(String userName, String password);
}
