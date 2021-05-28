package com.eCommerce.eComApp.controller;

import com.eCommerce.eComApp.model.User;
import com.eCommerce.eComApp.repository.UserRepository;
import com.eCommerce.eComApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private UserService userService;

    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public List<User> getAllUsers()
    {
        return userService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User getUserById(@PathVariable("id") String id ){
        return userService.getUserById(id);
    }


    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@RequestBody User user){
        userService.createUser(user);
    }



    @PutMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateById(@PathVariable("id") String id ,@RequestBody User user){
        userService.updateById(id,user);
    }

    @PutMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<User> identify(@RequestBody User user){
        User identified = null;
        if(user.getEmail()!=null && !user.getEmail().isEmpty())     identified = userService.identifyByEmail(user.getEmail(),user.getPassword());
        else if(user.getUserName()!=null && !user.getUserName().isEmpty())     identified = userService.identifyByUserName(user.getUserName(),user.getPassword());
        if(identified != null){
            return new ResponseEntity<User>(identified,HttpStatus.ACCEPTED);
        }else return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }


    @DeleteMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteById(@PathVariable("id") String id ){
        userService.deleteById(id);
    }



}
