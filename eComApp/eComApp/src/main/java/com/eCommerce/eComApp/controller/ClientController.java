package com.eCommerce.eComApp.controller;


import com.eCommerce.eComApp.model.Client;
import com.eCommerce.eComApp.repository.ClientRepository;
import com.eCommerce.eComApp.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class ClientController {
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

    
    @DeleteMapping("/clients/{id}")
    public void deleteById(@PathVariable("id") String id ) {
        clientService.deleteById(id);
    }
}
