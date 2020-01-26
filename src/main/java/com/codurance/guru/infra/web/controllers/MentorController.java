package com.codurance.guru.infra.web.controllers;

import com.codurance.guru.core.craftspeople.CraftspeopleService;
import com.codurance.guru.core.craftspeople.exceptions.DuplicateMenteeException;
import com.codurance.guru.core.craftspeople.exceptions.InvalidMentorRelationshipException;
import com.codurance.guru.infra.web.requests.AddMentorRequest;
import com.codurance.guru.infra.web.requests.RemoveMentorRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

import static com.codurance.guru.infra.web.responses.ErrorResponse.errorResponse;
import static com.codurance.guru.infra.web.responses.SuccessResponse.successResponse;

@Controller
@RequestMapping("craftspeople/mentor")
public class MentorController {

    @Autowired
    private CraftspeopleService craftspeopleService;

    @PostMapping("add")
    public ResponseEntity<?> addMentor(@Valid @RequestBody AddMentorRequest request) {
        try {
            craftspeopleService.addMentor(request.getMentorId(), request.getMenteeId());
            return successResponse();
        } catch (DuplicateMenteeException ex) {
            return errorResponse("The craftsperson is already mentoring that mentee.");
        } catch (InvalidMentorRelationshipException ex) {
            return errorResponse("The craftsperson can't mentor itself.");
        }
    }

    @PostMapping("remove")
    public ResponseEntity<Void> removeMentor(@Valid @RequestBody RemoveMentorRequest request) {
        craftspeopleService.removeMentor(request.getMenteeId());
        return successResponse();
    }

}
