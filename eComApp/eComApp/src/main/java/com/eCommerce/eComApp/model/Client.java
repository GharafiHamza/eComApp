package com.eCommerce.eComApp.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


public @Data class Client extends User{

    private List<String> shippingAddress;
    private List<CreditCard> creditCard;

}
