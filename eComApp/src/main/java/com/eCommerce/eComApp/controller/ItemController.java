package com.eCommerce.eComApp.controller;

import com.eCommerce.eComApp.model.Item;
import com.eCommerce.eComApp.repository.ItemRepository;
import com.eCommerce.eComApp.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class ItemController {

    @Autowired
    ItemService itemService;

    @GetMapping("/items")
    public ResponseEntity<List<Item>> getAllItems(){
        return new ResponseEntity<List<Item>>(itemService.getAllItem(),HttpStatus.OK);
    }

    @GetMapping("/items/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable String id){
        return new ResponseEntity<Item>(itemService.getItemById(id),HttpStatus.OK);
    }

    @PostMapping("/items")
    public void addItem(@RequestBody Item item){
        itemService.addItem(item);
    }

    @PutMapping("/items/{id}")
    public void updateItem(@PathVariable String id, @RequestBody Item item){
        item.setId(id);
        itemService.updateItem(item);
    }

    @DeleteMapping("/items/{id}")
    public void deleteItem(@PathVariable String id){
        itemService.deleteItem(id);
    }

}
