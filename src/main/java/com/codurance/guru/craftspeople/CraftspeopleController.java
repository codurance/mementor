package com.codurance.guru.craftspeople;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CraftspeopleController {

    @Autowired
    private CraftspeopleService craftspeopleService;

    @GetMapping("/craftspeople/{craftspersonId}")
    public ResponseEntity retrieveCraftsperson(@PathVariable Integer craftspersonId) {
        Optional<Craftsperson> retrievedCraftsperson = craftspeopleService.retrieveCraftsperson(craftspersonId);
        return retrievedCraftsperson.map(craftsperson -> new ResponseEntity(craftsperson, HttpStatus.OK)).orElseGet(() -> new ResponseEntity(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/craftspeople")
    public List<Craftsperson> retrieveAll() {
        return craftspeopleService.retrieveAllCraftsperson();
    }

    @DeleteMapping("/craftspeople/{craftspersonId}")
    public void deleteCraftsperson(@PathVariable Integer craftspersonId){
        craftspeopleService.deleteCraftsperson(craftspersonId);
    }

    @PostMapping("/craftspeople/add")
    public ResponseEntity addNewCraftsperson(@RequestBody AddCraftsperson addCraftsperson) {
        ResponseEntity responseEntity = new ResponseEntity(craftspeopleService.addCraftsperson(addCraftsperson.firstName, addCraftsperson.lastName).getId(), HttpStatus.OK);
        return responseEntity;
    }


    private static class AddCraftsperson {
        String firstName;
        String lastName;

        public AddCraftsperson() {
        }

        public AddCraftsperson(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }
    }
}
