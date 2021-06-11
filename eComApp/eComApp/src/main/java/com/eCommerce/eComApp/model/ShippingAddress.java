package com.eCommerce.eComApp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "ShippingAddress")
public class ShippingAddress {

    @Id
    private String id;
    private String streetAddress;
    private String state;
    private String zipCode;
    private String city;
    private  String country;
}
