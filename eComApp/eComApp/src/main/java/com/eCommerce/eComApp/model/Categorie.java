package com.eCommerce.eComApp.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("categories")
public @Data class Categorie {

    private String id;
    private String name;
    private List<Item> items;
    private String description;
    
}
