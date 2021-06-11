package com.eCommerce.eComApp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "creditCard")
public @Data class CreditCard {
        String cardNumber;
        String expiryDate;
        String cardHolderName;
        String cvvCode;
}
