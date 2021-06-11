package com.eCommerce.eComApp.service;

import com.eCommerce.eComApp.exceptions.NotFoundException;
import com.eCommerce.eComApp.model.Categorie;
import com.eCommerce.eComApp.model.Item;
import com.eCommerce.eComApp.repository.CategorieRepository;
import com.eCommerce.eComApp.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    CategorieRepository categorieRepository;

    public List<Item> getAllItem(){
        List<Item> items = itemRepository.findAll();
        if(items.size() > 0){
            return items;
        }else {
            throw new NotFoundException("No item was Found");
        }
    }

    public void addItemWC(Item item, String cat){
        Categorie catt = categorieRepository.findByName(cat);
        System.out.println(catt.getDescription());
        Item toAdd = item;
        toAdd.setCategorie(catt);
        itemRepository.save(toAdd);
    }

    public Item getItemById(String id){
        return itemRepository.findById(id).orElseThrow(() -> new NotFoundException("No item with this id :" + id +"was found"));
    }

    public List<Item> getItemByCategorie(String catName){
        List<Item> items = itemRepository.findByCategorie(categorieRepository.findByName(catName));
        if(items.size() > 0){
            return items;
        }else {
            throw new NotFoundException("No item with the categorie : "+catName+" was Found");
        }
    }

    public void addItem(Item item){
        itemRepository.save(item);
    }

    public void updateItem(Item item){
        Optional<Item> opItem = itemRepository.findById(item.getId());
        if(opItem.isPresent()){
            if( item.getQuantity() != null)opItem.get().setQuantity(item.getQuantity());
            if( item.getPrice() != 0) opItem.get().setPrice(item.getPrice());
            if(!item.getDescription().isEmpty() && item.getDescription() != null) opItem.get().setDescription(item.getDescription());
            if(!item.getImageUrl().isEmpty() && item.getImageUrl() != null) opItem.get().setImageUrl(item.getImageUrl());
            itemRepository.save(opItem.get());
        }else throw new NotFoundException("There is no item with this id :"+item.getId()+".");

    }

    public void deleteItem(String id){
        Item item = itemRepository.findById(id).orElseThrow(() -> new NotFoundException("No item with this id :" + id +"was found"));
        itemRepository.delete(item);
    }


}
