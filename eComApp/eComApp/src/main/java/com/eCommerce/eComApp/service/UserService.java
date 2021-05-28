package com.eCommerce.eComApp.service;

import com.eCommerce.eComApp.exceptions.AlreadyExistsException;
import com.eCommerce.eComApp.exceptions.NotFoundException;
import com.eCommerce.eComApp.model.User;
import com.eCommerce.eComApp.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    Logger logger = LoggerFactory.getLogger(UserService.class.getName());

    public List<User> getAllUsers(){
        List<User> users = userRepo.findAll();
        if(users.size() > 0){
            return users;
        }else {
            throw new NotFoundException("No Users Wher Found");
        }

    }


    public void createUser( User user){

        if(checkUser(user)){
            userRepo.save(user);
        }
        logger.debug("un nouveau utilisateur est creé .");
    }

    public User getUserById( String id ){
        User opUser = userRepo.findById(id).orElseThrow(() -> new NotFoundException("Aucun user avec l'id : "+id+",est trouvée"));
        return opUser;

    }

    public User updateById(String id , User newUser){
        Optional<User> opUser = userRepo.findById(id);

        if(opUser.isPresent()) {
            User user  = opUser.get();
            if(newUser.getNom()!=null && !newUser.getNom().isEmpty()) 	user.setNom(newUser.getNom());
            if(newUser.getPrenom()!=null && !newUser.getPrenom().isEmpty()) 	user.setPrenom(newUser.getPrenom());
            if(newUser.getPhoneNumber()!=null && !newUser.getPhoneNumber().isEmpty()) 	user.setPhoneNumber(newUser.getPhoneNumber());
            if(newUser.getEmail()!=null && !newUser.getEmail().isEmpty()) 	user.setEmail(newUser.getEmail());
            if(newUser.getUserName()!=null && !newUser.getUserName().isEmpty()) 	user.setUserName(newUser.getUserName());
            if(newUser.getAddress()!=null && !newUser.getAddress().isEmpty()) 	user.setAddress(newUser.getAddress());
            if(newUser.getPassword()!=null && !newUser.getPassword().isEmpty()) 	user.setPassword(newUser.getPassword());
            return user;
        }else {
            throw new NotFoundException("Aucun user avec l'id :"+id+", est trouvée .");
        }
    }


    public void deleteById(String id ){
        Optional<User> opUser = userRepo.findById(id);
        if(opUser.isPresent()) {
            userRepo.delete(opUser.get());
        }else {
            throw new NotFoundException("Aucun user avec l'id :"+id+", est trouvée .");
        }
    }

    public  boolean checkUser(User user) {
        System.out.println("I'm in checkUser");
        Optional<User> oldUserEmail = userRepo.findByEmail(user.getEmail());
        Optional<User> oldUserUserName = userRepo.findByUserName(user.getUserName());
        boolean notExistent = false;
        if (oldUserUserName.isPresent()) {
            System.out.println("UserName already exists");
        }else if (oldUserEmail.isPresent()) {
            if (oldUserEmail.get().checkRole(user.getRole()) && !user.getRole().isEmpty()) {
                System.out.println("in Email problem !!!");
                throw new AlreadyExistsException("Un " + oldUserEmail.get().getRole() + " avec l'email' " + user.getEmail() + " existe déjà");
            } else {
                System.out.println("in Email");
                notExistent = true;
            }
        }else notExistent = true;

        return notExistent;
    }

    public User identifyByEmail(String email, String password){
        Optional<User> user = userRepo.findByEmailAndPassword(email, password);
        if(user.isPresent()){
            return user.get();
        }else throw new NotFoundException("no user with this email : '"+ email + "' , was found.");
    }

    public User identifyByUserName(String userName, String password){
        Optional<User> user = userRepo.findByUserNameAndPassword(userName, password);
        if(user.isPresent()){
            return user.get();
        }else throw new NotFoundException("no user with this email : '"+ userName + "' , was found.");
    }

}
