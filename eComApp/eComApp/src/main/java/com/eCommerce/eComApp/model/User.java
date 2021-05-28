package com.eCommerce.eComApp.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public @Data class User {

    @Id
    private String id;

    private String nom;

    private String prenom;

    private String email;

    private String userName;

    private String password;

    private String address;

    private String phoneNumber;

    private String role;

    public Boolean checkRole(String role){
        if(this.role.equals(role)){
            return true;
        }else return false;
    }

}
