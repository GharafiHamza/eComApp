package com.eCommerce.eComApp.service;

import com.eCommerce.eComApp.exceptions.NotFoundException;
import com.eCommerce.eComApp.model.*;
import com.eCommerce.eComApp.repository.ClientRepository;
import com.eCommerce.eComApp.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepo;

    Logger logger = LoggerFactory.getLogger(ClientService.class.getName());

    public List<Client> getAllClients(){

        List<User> users = userRepo.findByRole("Client").get();
        List<Client> clients = (List<Client>)(List<?>) users;
        return clients;

    }


    public void createClient( Client client){
        client.setRole("Client");

            clientRepo.save(client);
    }

    public Client getClientById( String id ){
        return (Client)userService.getUserById(id);

    }

    public void updateById(String id , Client newClient){

        Client client = (Client) userService.updateById(id, newClient);
        if(newClient.getShippingAddress()!=null && !newClient.getShippingAddress().isEmpty()) 	client.setShippingAddress(newClient.getShippingAddress());
        if(newClient.getCreditCard()!=null && !newClient.getCreditCard().isEmpty()) 	client.setCreditCard(newClient.getCreditCard());
        if(newClient.getFavourites()!=null && !newClient.getFavourites().isEmpty()) 	client.setFavourites(newClient.getFavourites());
        if(newClient.getShoppingCart()!=null && !newClient.getShoppingCart().isEmpty()) 	client.setShoppingCart(newClient.getShoppingCart());

        clientRepo.save(client);

    }


    public void deleteById(String id ){
        userService.deleteById(id);
    }

    public Client identifyByEmail(String email, String password){
        Client client =  (Client) userService.identifyByEmail(email,password);
        if(client.getRole().equals("Client"))return client;
        else throw new NotFoundException("not a Client");
    }
    public Client identifyByUserName(String userName, String password){
        Client client = (Client) userService.identifyByUserName(userName,password);
        if(client.getRole().equals("Client"))return client;
        else throw new NotFoundException("not a Client");
    }

    public void addToFavourites(Client client, Item item){
        if(client.getFavourites() == null){
            List<Item> listItems = new ArrayList<Item>();
            listItems.add(item);
            client.setFavourites(listItems);
        }else{
            List<Item> listItems = client.getFavourites();
            listItems.add(item);
            client.setFavourites(listItems);
        }
        updateById(client.getId(),client);
    }

    public void addToShoppingCart(Client client, Item item){
        if(client.getShoppingCart() == null){
            List<Item> listItems = new ArrayList<Item>();
            listItems.add(item);
            client.setShoppingCart(listItems);
        }else{
            List<Item> listItems = client.getShoppingCart();
            listItems.add(item);
            client.setShoppingCart(listItems);
        }
        updateById(client.getId(),client);
    }

    public List<Item> getClientFavourites(String id){
        Client client = clientRepo.findById(id).orElseThrow(()-> new NotFoundException("Not Favourites found for this Client"));
        return client.getFavourites();
    }

    public List<Item> getClientCart(String id) {
        Client client = clientRepo.findById(id).orElseThrow(()-> new NotFoundException("Not Favourites found for this Client"));
        return client.getShoppingCart();
    }

    public List<ShippingAddress> getShippingAddress(String id){
        Client client = clientRepo.findById(id).orElseThrow(()-> new NotFoundException("Not Favourites found for this Client"));
        return client.getShippingAddress();
    }

    public Client addCreditCard(String id, CreditCard card){
        Client client = clientRepo.findById(id).orElseThrow(()-> new NotFoundException("Not Favourites found for this Client"));
        if(client.getCreditCard() == null){
            List<CreditCard> listcc = new ArrayList<>();
            listcc.add(card);
            client.setCreditCard(listcc);
        }else{
            List<CreditCard> listcc = client.getCreditCard();
            listcc.add(card);
            client.setCreditCard(listcc);
        }
        updateById(client.getId(),client);
        return client;
    }

    public List<CreditCard> getClientCCs(String id){
        Client client = clientRepo.findById(id).orElseThrow(()-> new NotFoundException("Not Favourites found for this Client"));
        return client.getCreditCard();
    }

}
