package com.eCommerce.eComApp.service;

import com.eCommerce.eComApp.exceptions.NotFoundException;
import com.eCommerce.eComApp.model.Admin;
import com.eCommerce.eComApp.model.Client;
import com.eCommerce.eComApp.model.User;
import com.eCommerce.eComApp.repository.ClientRepository;
import com.eCommerce.eComApp.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        if(userService.checkUser(client)) {
            clientRepo.save(client);
        }
    }

    public Client getClientById( String id ){
        return (Client)userService.getUserById(id);

    }

    public void updateById(String id , Client newClient){

        Client client = (Client) userService.updateById(id, newClient);
        if(newClient.getShippingAddress()!=null && !newClient.getShippingAddress().isEmpty()) 	client.setShippingAddress(newClient.getShippingAddress());
        if(newClient.getCreditCard()!=null && !newClient.getCreditCard().isEmpty()) 	client.setCreditCard(newClient.getCreditCard());
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
}
