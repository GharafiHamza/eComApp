package com.eCommerce.eComApp.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "creditCard")
public @Data class CreditCard {
}
