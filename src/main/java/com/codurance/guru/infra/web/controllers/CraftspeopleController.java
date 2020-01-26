package com.codurance.guru.infra.web.controllers;

import com.codurance.guru.core.craftspeople.CraftspeopleService;
import com.codurance.guru.core.craftspeople.Craftsperson;
import com.codurance.guru.core.craftspeople.exceptions.DuplicateMenteeException;
import com.codurance.guru.core.craftspeople.exceptions.ExistingCraftspersonException;
import com.codurance.guru.core.craftspeople.exceptions.InvalidLastMeetingDateException;
import com.codurance.guru.core.craftspeople.exceptions.InvalidMentorRelationshipException;
import com.codurance.guru.infra.web.requests.AddCraftspersonRequest;
import com.codurance.guru.infra.web.requests.AddMentorRequest;
import com.codurance.guru.infra.web.requests.RemoveMentorRequest;
import com.codurance.guru.infra.web.requests.UpdateLastMeetingRequest;
import com.codurance.guru.infra.web.responses.ErrorResponse;
import com.codurance.guru.infra.web.responses.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.*;

@Controller
@RequestMapping("craftspeople")
public class CraftspeopleController {

    @Autowired
    private CraftspeopleService craftspeopleService;

    @GetMapping
    public List<Craftsperson> retrieveAll() {
        return craftspeopleService.retrieveAllCraftsperson();
    }

    @GetMapping("{craftspersonId}")
    public ResponseEntity<Craftsperson> retrieveCraftsperson(@PathVariable Integer craftspersonId) {
        Optional<Craftsperson> retrievedCraftsperson = craftspeopleService.retrieveCraftsperson(craftspersonId);
        return retrievedCraftsperson
                .map(SuccessResponse::successResponse)
                .orElseGet(() -> notFound().build());
    }

}
