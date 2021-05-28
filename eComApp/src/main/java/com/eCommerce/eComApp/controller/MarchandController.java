package com.eCommerce.eComApp.controller;


import com.eCommerce.eComApp.model.Marchand;
import com.eCommerce.eComApp.repository.MarchandRepository;
import com.eCommerce.eComApp.service.MarchandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class MarchandController {

    @Autowired
    private MarchandRepository marchandRepo;

    @Autowired
    private MarchandService marchandService;

    @GetMapping("/marchands")
    public List<Marchand> getAllMarchands(){
        return marchandService.getAllMarchands();
    }


    @PostMapping("/marchands")
    public void createMarchand(@RequestBody Marchand marchand){
        marchandService.createMarchand(marchand);
    }

    @GetMapping("/marchands/{id}")
    public Marchand getMarchandById(@PathVariable("id") String id ){
        return marchandService.getMarchandById(id);
    }

    @PutMapping("/marchands/{id}")
    public void updateById(@PathVariable("id") String id ,@RequestBody Marchand newMarchand){
        marchandService.updateById(id,newMarchand);
    }

    @PutMapping("/marchands")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Marchand> identify(@RequestBody Marchand marchand){
        Marchand identified = null;
        if(marchand.getEmail()!=null && !marchand.getEmail().isEmpty())     identified = marchandService.identifyByEmail(marchand.getEmail(),marchand.getPassword());
        else if(marchand.getUserName()!=null && !marchand.getUserName().isEmpty())     identified = marchandService.identifyByUserName(marchand.getUserName(),marchand.getPassword());
        if(identified != null){
            return new ResponseEntity<Marchand>(identified,HttpStatus.ACCEPTED);
        }else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @DeleteMapping("/marchands/{id}")
    public void deleteById(@PathVariable("id") String id ) {
        marchandService.deleteById(id);
    }
}
