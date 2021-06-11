package com.eCommerce.eComApp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "ShippingMethod")
public @Data class ShippingMethod {

    @Id
    private String id;
    private String method;
    private String time;
    private double price;
    private boolean suivi;


}
