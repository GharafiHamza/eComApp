package com.eCommerce.eComApp.service;

import com.eCommerce.eComApp.exceptions.NotFoundException;
import com.eCommerce.eComApp.model.Client;
import com.eCommerce.eComApp.model.Marchand;
import com.eCommerce.eComApp.model.User;
import com.eCommerce.eComApp.repository.MarchandRepository;
import com.eCommerce.eComApp.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarchandService {
    @Autowired
    private MarchandRepository marchandRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepo;

    Logger logger = LoggerFactory.getLogger(MarchandService.class.getName());

    public List<Marchand> getAllMarchands(){
        List<User> users = userRepo.findByRole("marchand").get();
        List<Marchand> marchands = (List<Marchand>)(List<?>) users;
        return marchands;
    }


    public void createMarchand( Marchand marchand){
        marchand.setRole("Marchand");
        if(userService.checkUser(marchand)) {
            marchandRepo.save(marchand);
        }
    }

    public Marchand getMarchandById( String id ){
        return (Marchand)userService.getUserById(id);

    }

    public void updateById(String id , Marchand newMarchand){
        Marchand marchand = (Marchand) userService.updateById(id, newMarchand);
        if(newMarchand.getItems()!=null && !newMarchand.getItems().isEmpty()) 	marchand.setItems(newMarchand.getItems());
        if(newMarchand.getCreditCard()!=null && !newMarchand.getCreditCard().isEmpty()) 	marchand.setCreditCard(newMarchand.getCreditCard());
        marchandRepo.save(marchand);
    }


    public void deleteById(String id ){
        userService.deleteById(id);
    }

    public Marchand identifyByEmail(String email, String password){
        Marchand marchand =  (Marchand) userService.identifyByEmail(email,password);
        if(marchand.getRole().equals("Marchand"))return marchand;
        else throw new NotFoundException("not a Marchand");
    }
    public Marchand identifyByUserName(String userName, String password){
        Marchand marchand = (Marchand) userService.identifyByUserName(userName,password);
        if(marchand.getRole().equals("Marchand"))return marchand;
        else throw new NotFoundException("not a Marchand");
    }

}
