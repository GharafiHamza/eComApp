package com.eCommerce.eComApp.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

public @Data class Marchand extends User{

    private List<CreditCard> creditCard;
    private List<Item> items;
}
