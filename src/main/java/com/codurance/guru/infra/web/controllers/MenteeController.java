package com.codurance.guru.infra.web.controllers;

import com.codurance.guru.core.craftspeople.CraftspeopleService;
import com.codurance.guru.core.craftspeople.Craftsperson;
import com.codurance.guru.core.craftspeople.exceptions.DuplicateMenteeException;
import com.codurance.guru.core.craftspeople.exceptions.ExistingCraftspersonException;
import com.codurance.guru.core.craftspeople.exceptions.InvalidLastMeetingDateException;
import com.codurance.guru.core.craftspeople.exceptions.InvalidMentorRelationshipException;
import com.codurance.guru.infra.web.requests.AddCraftspersonRequest;
import com.codurance.guru.infra.web.requests.AddMentorRequest;
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
public class MenteeController {

    @Autowired
    private CraftspeopleService craftspeopleService;

    @PutMapping("craftspeople/mentee/add")
    public ResponseEntity setMentee(@Valid @RequestBody AddMentorRequest request) {
        try {
            craftspeopleService.setMentee(
                    request.getMentorId(),
                    request.getMenteeId());
            return ok().build();
        } catch (InvalidMentorRelationshipException ex) {
            return badRequest().body(new ErrorResponse("Cant add a craftsperson as their own mentor"));
        } catch (DuplicateMenteeException ex) {
            return badRequest().body(new ErrorResponse("Mentee already exists"));
        }
    }

    @PutMapping("craftspeople/mentee/remove/{menteeId}")
    public ResponseEntity removeMentee(@PathVariable int menteeId) {
        craftspeopleService.removeMentor(menteeId);
        return ok().build();
    }
}
