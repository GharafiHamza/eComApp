package com.eCommerce.eComApp.service;

import com.eCommerce.eComApp.exceptions.AlreadyExistsException;
import com.eCommerce.eComApp.exceptions.NotFoundException;
import com.eCommerce.eComApp.model.Categorie;
import com.eCommerce.eComApp.model.Item;
import com.eCommerce.eComApp.repository.CategorieRepository;
import com.eCommerce.eComApp.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class CategorieService {
    @Autowired
    CategorieRepository categorieRepository;

    @Autowired
    ItemRepository itemRepository;

    public List<Categorie> getAllCategorie(){
        List<Categorie> categories = categorieRepository.findAll();
        if(categories.size() > 0){
            return categories;
        }else {
            throw new NotFoundException("No categorie was Found");
        }
    }

    public Categorie getCategorieById(String id){
        return categorieRepository.findById(id).orElseThrow(() -> new NotFoundException("No categorie with this id :" + id +"was found"));
    }

    public List<Item> getItems(String id){
        Categorie categorie= categorieRepository.findById(id).orElseThrow(() -> new NotFoundException("No categorie was found with this id :"+id));
        return categorie.getItems();
    }

    public void addCategorie(Categorie categorie){
        Categorie oldCategorie= categorieRepository.findByName(categorie.getName());
        if(categorie != null){
            throw new AlreadyExistsException("There is a categorie with the same name :"+categorie.getName());
        }else categorieRepository.save(categorie);
    }

    public void addItem(String id, Item item){
        Categorie categorie= categorieRepository.findById(id).orElseThrow(() -> new NotFoundException("No categorie was found with this id :"+id));
        List<Item> items = categorie.getItems();
        items.add(item);
        categorie.setItems(items);
        categorieRepository.save(categorie);
    }

    public void deleteCategorie(String id){
        Categorie categorie = categorieRepository.findById(id).orElseThrow(() -> new NotFoundException("No categorie with this id :" + id +"was found"));
        categorieRepository.delete(categorie);
    }
}
