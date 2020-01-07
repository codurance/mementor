package com.codurance.guru.craftspeople;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CraftspeopleController {

    @Autowired
    private CraftspeopleService craftspeopleService;

    @GetMapping("/craftspeople/{craftspersonId}")
    public Craftsperson retrieveCraftsperson(@PathVariable Integer craftspersonId) {
        return craftspeopleService.retrieveCraftsperson(craftspersonId);
    }

    @GetMapping("/craftspeople")
    public List<Craftsperson> retrieveAll() {
        List<Craftsperson> data = new ArrayList<>();
        data.add(new Craftsperson("Riccardo","Toni"));
        data.add(new Craftsperson("Jose", "Campos"));
        return data;
    }

}
