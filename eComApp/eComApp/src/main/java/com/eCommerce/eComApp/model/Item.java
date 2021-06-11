package com.eCommerce.eComApp.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "items")
public @Data class Item {
    private String id;
    private String nom;
    private String description;
    private String imageUrl;
    private double price;
    private Integer quantity;
    private String idMarchand;
    private Categorie categorie;
    private List<ShippingMethod> shippingMethod;
}
