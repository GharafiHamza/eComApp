package com.eCommerce.eComApp.service;

import com.eCommerce.eComApp.exceptions.AlreadyExistsException;
import com.eCommerce.eComApp.exceptions.NotFoundException;
import com.eCommerce.eComApp.model.Admin;
import com.eCommerce.eComApp.model.Client;
import com.eCommerce.eComApp.model.User;
import com.eCommerce.eComApp.repository.AdminRepository;
import com.eCommerce.eComApp.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class AdminService {
    
    @Autowired
    private AdminRepository adminRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepo;

    Logger logger = LoggerFactory.getLogger(AdminService.class.getName());

    public List<Admin> getAllAdmins(){
        List<User> users = userRepo.findByRole("Admin").get();
        List<Admin> admins = (List<Admin>)(List<?>) users;
        return admins;
    }


    public void createAdmin( Admin admin){
        admin.setRole("Admin");
        if(userService.checkUser(admin)) {
            adminRepo.save(admin);
        }
    }

    public Admin getAdminById( String id ){
        return (Admin)userService.getUserById(id);

    }

    public void updateById(String id , Admin newAdmin){
        Admin admin = (Admin) userService.updateById(id, newAdmin);
        adminRepo.save(admin);
    }


    public void deleteById(String id ){
        userService.deleteById(id);
    }

    public Admin identifyByEmail(String email, String password){
        Admin admin =  (Admin) userService.identifyByEmail(email,password);
        if(admin.getRole().equals("Admin"))return admin;
        else throw new NotFoundException("not an Admin");
    }
    public Admin identifyByUserName(String userName, String password){
        Admin admin = (Admin) userService.identifyByUserName(userName,password);
        if(admin.getRole().equals("Admin"))return admin;
        else throw new NotFoundException("not an Admin");
    }
}
