package com.eCommerce.eComApp.repository;

import com.eCommerce.eComApp.model.Categorie;
import com.eCommerce.eComApp.model.Item;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategorieRepository extends MongoRepository<Categorie,String> {
    public Categorie findByName(String name);
}
