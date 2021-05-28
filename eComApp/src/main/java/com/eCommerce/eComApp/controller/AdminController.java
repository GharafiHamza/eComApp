package com.eCommerce.eComApp.controller;

import com.eCommerce.eComApp.model.Admin;
import com.eCommerce.eComApp.model.User;
import com.eCommerce.eComApp.repository.AdminRepository;
import com.eCommerce.eComApp.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/")
public class AdminController {
    @Autowired
    private AdminRepository adminRepo;

    @Autowired
    private UserController userController;

    @Autowired
    private AdminService adminService;

    @GetMapping("/admins")
    public List<Admin> getAllAdmins(){
        return adminService.getAllAdmins();
    }


    @PostMapping("/admins")
    public void createAdmin(@RequestBody Admin admin){
        adminService.createAdmin(admin);
    }

    @GetMapping("/admins/{id}")
    public Admin getAdminById(@PathVariable("id") String id ){
        return adminService.getAdminById(id);
    }

    @PutMapping("/admins/{id}")
    public void updateById(@PathVariable("id") String id ,@RequestBody Admin newAdmin){
        adminService.updateById(id,newAdmin);
    }
    @PutMapping("/admins")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Admin> identify(@RequestBody Admin admin){
        Admin identified = null;
        if(admin.getEmail()!=null && !admin.getEmail().isEmpty())     identified = adminService.identifyByEmail(admin.getEmail(),admin.getPassword());
        else if(admin.getUserName()!=null && !admin.getUserName().isEmpty())     identified = adminService.identifyByUserName(admin.getUserName(),admin.getPassword());
        if(identified != null){
            return new ResponseEntity<Admin>(identified,HttpStatus.ACCEPTED);
        }else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @DeleteMapping("/admins/{id}")
    public void deleteById(@PathVariable("id") String id ) {
        adminService.deleteById(id);
    }
}
