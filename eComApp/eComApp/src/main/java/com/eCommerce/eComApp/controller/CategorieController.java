package com.eCommerce.eComApp.controller;

import com.eCommerce.eComApp.model.Categorie;
import com.eCommerce.eComApp.model.Item;
import com.eCommerce.eComApp.service.CategorieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class CategorieController {
    @Autowired
    CategorieService categorieService;

    @GetMapping("/categories")
    public ResponseEntity<List<Categorie>> getAllcategories(){
        return new ResponseEntity<List<Categorie>>(categorieService.getAllCategorie(), HttpStatus.OK);
    }

    @GetMapping("/categories/{id}")
    public ResponseEntity<Categorie> getCategorieById(@PathVariable String id){
        return new ResponseEntity<Categorie>(categorieService.getCategorieById(id),HttpStatus.OK);
    }

    @PostMapping("/categories")
    public void addcategorie(@RequestBody Categorie categorie){
        categorieService.addCategorie(categorie);
    }

    @PutMapping("/categories/{id}")
    public void addItem(@PathVariable String id, @RequestBody Item item){
        categorieService.addItem(id,item);
    }

    @DeleteMapping("/categories/{id}")
    public void deletecategorie(@PathVariable String id){
        categorieService.deleteCategorie(id);
    }

}
