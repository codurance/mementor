package com.codurance.guru.infra.web.controllers;

import com.codurance.guru.core.craftspeople.CraftspeopleService;
import com.codurance.guru.core.craftspeople.Craftsperson;
import com.codurance.guru.core.craftspeople.exceptions.ExistingCraftspersonException;
import com.codurance.guru.infra.web.requests.AddCraftspersonRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.codurance.guru.infra.web.responses.Responses.errorResponse;
import static com.codurance.guru.infra.web.responses.Responses.successResponse;

@Controller
@RequestMapping("craftspeople")
public class AdminController {

    @Autowired
    private CraftspeopleService craftspeopleService;

    @PostMapping("add")
    public ResponseEntity<?> addNewCraftsperson(@Valid @RequestBody AddCraftspersonRequest request) {
        try {
            Craftsperson craftsperson = craftspeopleService.addCraftsperson(request.getFirstName(), request.getLastName());
            return successResponse(craftsperson.getId());
        } catch (ExistingCraftspersonException ex) {
            return errorResponse("Craftsperson already exists.");
        }
    }

    @DeleteMapping("{craftspersonId}")
    public ResponseEntity<Void> deleteCraftsperson(@PathVariable Integer craftspersonId) {
        craftspeopleService.deleteCraftsperson(craftspersonId);
        return successResponse();
    }

}
