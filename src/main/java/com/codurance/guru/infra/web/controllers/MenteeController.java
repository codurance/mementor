package com.codurance.guru.infra.web.controllers;

import com.codurance.guru.core.craftspeople.CraftspeopleService;
import com.codurance.guru.core.craftspeople.exceptions.DuplicateMenteeException;
import com.codurance.guru.core.craftspeople.exceptions.InvalidMentorRelationshipException;
import com.codurance.guru.infra.web.requests.AddMentorRequest;
import com.codurance.guru.infra.web.responses.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.ResponseEntity.*;

@Controller
@RequestMapping("craftspeople/mentee")
public class MenteeController {

    @Autowired
    private CraftspeopleService craftspeopleService;

    @PutMapping("add")
    public ResponseEntity<ErrorResponse> setMentee(@Valid @RequestBody AddMentorRequest request) {
        try {
            craftspeopleService.setMentee(request.getMentorId(), request.getMenteeId());
            return noContent().build();
        } catch (InvalidMentorRelationshipException ex) {
            return badRequestError("Cant add a craftsperson as their own mentor");
        } catch (DuplicateMenteeException ex) {
            return badRequestError("Mentee already exists");
        }
    }

    private ResponseEntity<ErrorResponse> badRequestError(String message) {
        return badRequest().body(new ErrorResponse(message));
    }

    @PutMapping("remove/{menteeId}")
    public ResponseEntity<Void> removeMentee(@PathVariable int menteeId) {
        craftspeopleService.removeMentor(menteeId);
        return noContent().build();
    }
}
