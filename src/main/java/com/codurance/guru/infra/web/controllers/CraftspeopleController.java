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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.*;

@RestController
public class CraftspeopleController {

    @Autowired
    private CraftspeopleService craftspeopleService;

    @PostMapping("/craftspeople/add")
    public ResponseEntity addNewCraftsperson(@Valid @RequestBody AddCraftspersonRequest request) {
        try {
            Craftsperson craftsperson = craftspeopleService.addCraftsperson(request.getFirstName(), request.getLastName());
            return ok(craftsperson.getId());
        } catch (ExistingCraftspersonException ex) {
            return badRequest().body(new ErrorResponse("Craftsperson already exists."));
        }
    }

    @DeleteMapping("/craftspeople/{craftspersonId}")
    public ResponseEntity deleteCraftsperson(@PathVariable Integer craftspersonId) {
        craftspeopleService.deleteCraftsperson(craftspersonId);
        return ok().build();
    }

    @GetMapping("/craftspeople")
    public List<Craftsperson> retrieveAll() {
        return craftspeopleService.retrieveAllCraftsperson();
    }

    @GetMapping("/craftspeople/{craftspersonId}")
    public ResponseEntity retrieveCraftsperson(@PathVariable Integer craftspersonId) {
        Optional<Craftsperson> retrievedCraftsperson = craftspeopleService.retrieveCraftsperson(craftspersonId);
        return retrievedCraftsperson
                .map(ResponseEntity::ok)
                .orElseGet(() -> notFound().build());
    }

    @PutMapping("/craftspeople/lastmeeting")
    public ResponseEntity<ErrorResponse> setLastMeeting(@Valid @RequestBody UpdateLastMeetingRequest request) {
        try {
            craftspeopleService.setLastMeeting(request.getCraftspersonId(), request.getLastMeeting());
            return noContent().build();
        } catch (InvalidLastMeetingDateException ex) {
            return badRequest().body(new ErrorResponse("The last meeting date is too far in the future"));
        }
    }

}
