package com.eCommerce.eComApp.controller;


import com.eCommerce.eComApp.model.Client;
import com.eCommerce.eComApp.model.CreditCard;
import com.eCommerce.eComApp.model.Item;
import com.eCommerce.eComApp.model.ShippingAddress;
import com.eCommerce.eComApp.repository.ClientRepository;
import com.eCommerce.eComApp.service.ClientService;
import com.eCommerce.eComApp.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class ClientController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private ClientRepository clientRepo;

    @Autowired
    private ClientService clientService;

    @GetMapping("/clients")
    public List<Client> getAllClients(){
        return clientService.getAllClients();
    }

    @PostMapping("/clients")
    public void createClient(@RequestBody Client client){
        clientService.createClient(client);
    }

    @GetMapping("/clients/{id}")
    public Client getClientById(@PathVariable("id") String id ){
        return clientService.getClientById(id);
    }

    @PutMapping("/clients/{id}")
    public void updateById(@PathVariable("id") String id ,@RequestBody Client newClient){
        clientService.updateById(id,newClient);
    }

    @PutMapping("/clients")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Client> identify(@RequestBody Client client){
        Client identified = null;
        if(client.getEmail()!=null && !client.getEmail().isEmpty())     identified = clientService.identifyByEmail(client.getEmail(),client.getPassword());
        else if(client.getUserName()!=null && !client.getUserName().isEmpty())     identified = clientService.identifyByUserName(client.getUserName(),client.getPassword());
        if(identified != null){
            return new ResponseEntity<Client>(identified,HttpStatus.ACCEPTED);
        }else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/clients/addFavourites/{itemId}/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void addToFavourites(@PathVariable("itemId") String itemId, @PathVariable("userId") String userId){
        Client client = clientService.getClientById(userId);
        System.out.println(client.getUserName());
        Item item = itemService.getItemById(itemId);
        System.out.println(item.getNom());
        clientService.addToFavourites(client,item);
    }
    @PutMapping("/clients/addToShoppingCart/{itemId}/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void addToShoppingCart(@PathVariable("itemId") String itemId, @PathVariable("userId") String userId){
        Client client = clientService.getClientById(userId);
        Item item = itemService.getItemById(itemId);
        clientService.addToShoppingCart(client,item);
    }

    @GetMapping("/clients/favourites/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<Item> getClientFavourites(@PathVariable("id") String id){
        return clientService.getClientFavourites(id);
    }
    @GetMapping("/clients/shoppingCart/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<Item> getClientCart(@PathVariable("id") String id){
        return clientService.getClientCart(id);
    }
    
    @DeleteMapping("/clients/{id}")
    public void deleteById(@PathVariable("id") String id ) {
        clientService.deleteById(id);
    }

    @GetMapping("/clients/getAddress/{id}")
    public List<ShippingAddress> getClientShippingAddress(@PathVariable("id") String id){
        return clientService.getShippingAddress(id);
    }

    @PutMapping("/clients/addcc/{id}")
    public void addCC(@PathVariable String id, @RequestBody CreditCard card){
        clientService.addCreditCard(id,card);
    }

    @GetMapping("/clients/getCCs/{id}")
    public List<CreditCard> getClientCCS(@PathVariable String id){
        return clientService.getClientCCs(id);
    }
}
