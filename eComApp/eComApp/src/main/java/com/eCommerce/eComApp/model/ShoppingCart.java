package com.eCommerce.eComApp.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "ShoppingCart")
public @Data class ShoppingCart {

    @Id
    private String id;
    private Client client;
    private List<Item> items;

}
