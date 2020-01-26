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
public class MentorController {

    @Autowired
    private CraftspeopleService craftspeopleService;

    @PostMapping("/craftspeople/mentor/add")
    public ResponseEntity addMentor(@Valid @RequestBody AddMentorRequest request) {
        try {
            craftspeopleService.addMentor(request.getMentorId(), request.getMenteeId());
            return ResponseEntity.noContent().build();

        } catch (DuplicateMenteeException ex) {
            return badRequest().body(new ErrorResponse("The craftsperson is already mentoring that mentee."));
        } catch (InvalidMentorRelationshipException ex) {
            return badRequest().body(new ErrorResponse("The craftsperson can't mentor itself."));
        }
    }

    @PostMapping("/craftspeople/mentor/remove")
    public ResponseEntity<Void> removeMentor(@Valid @RequestBody RemoveMentorRequest request) {
        craftspeopleService.removeMentor(request.getMenteeId());
        return ResponseEntity.noContent().build();
    }

}
