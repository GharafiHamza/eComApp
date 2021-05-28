package com.eCommerce.eComApp.repository;

import com.eCommerce.eComApp.model.Categorie;
import com.eCommerce.eComApp.model.Item;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ItemRepository extends MongoRepository<Item,String> {
    public List<Item> findByCategorie(Categorie categorie);
}
