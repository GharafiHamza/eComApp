package com.eCommerce.eComApp.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


public @Data class Client extends User{

    private List<ShippingAddress> shippingAddress;
    private List<CreditCard> creditCard;
    private List<Item> favourites;
    private List<Item> shoppingCart;


}
