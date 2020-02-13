package com.codurance.guru.craftspeople;

import com.codurance.guru.audits.Auditor;
import com.codurance.guru.craftspeople.exceptions.*;
import com.codurance.guru.craftspeople.requests.AddCraftspersonRequest;
import com.codurance.guru.craftspeople.requests.AddMentorRequest;
import com.codurance.guru.craftspeople.requests.RemoveMentorRequest;
import com.codurance.guru.craftspeople.requests.UpdateLastMeetingRequest;
import com.codurance.guru.craftspeople.responses.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.*;

@RestController
public class CraftspeopleController {

    @Autowired
    private CraftspeopleService craftspeopleService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private Auditor auditor;

    @PostMapping("/craftspeople/mentor/add")
    public ResponseEntity addMentor(@Valid @RequestBody AddMentorRequest request) {
        try {
            craftspeopleService.addMentor(request.getMentorId(), request.getMenteeId());

            auditor.addMentor(getActorName(), request);

            return ResponseEntity.noContent().build();

        } catch (DuplicateMenteeException ex) {
            return badRequest().body(new ErrorResponse("The craftsperson is already mentoring that mentee."));
        } catch (InvalidMentorRelationshipException ex) {
            return badRequest().body(new ErrorResponse("The craftsperson can't mentor itself."));
        }
    }

    @PostMapping("/craftspeople/add")
    public ResponseEntity addNewCraftsperson(@Valid @RequestBody AddCraftspersonRequest request) {
        try {
            Craftsperson craftsperson = craftspeopleService.addCraftsperson(request.getFirstName(), request.getLastName());

            auditor.addCraftsperson(getActorName(), request);

            return ok(craftsperson.getId());
        } catch (ExistingCraftspersonException ex) {
            return badRequest().body(new ErrorResponse("Craftsperson already exists."));
        }
    }

    @DeleteMapping("/craftspeople/{craftspersonId}")
    public ResponseEntity deleteCraftsperson(@PathVariable Integer craftspersonId) {
        String craftspersonName = craftspeopleService.retrieveCraftsperson(craftspersonId).get().getFullName();
        craftspeopleService.deleteCraftsperson(craftspersonId);

        auditor.deleteCraftsperson(getActorName(), craftspersonName);

        return ok().build();

    }

    @PutMapping("craftspeople/mentee/remove")
    public ResponseEntity removeMentee(@Valid @RequestBody RemoveMentorRequest request) {
        craftspeopleService.removeMentor(request.getMenteeId());

        auditor.removeMentee(getActorName(), request);

        return ok().build();
    }

    @PostMapping("/craftspeople/mentor/remove")
    public ResponseEntity removeMentor(@Valid @RequestBody RemoveMentorRequest request) {
        craftspeopleService.removeMentor(request.getMenteeId());

        auditor.removeMentor(getActorName(), request);

        return ResponseEntity.noContent().build();
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

    @PutMapping("/craftspeople/lastmeeting/add")
    public ResponseEntity<ErrorResponse> setLastMeeting(@Valid @RequestBody UpdateLastMeetingRequest request) {
        try {
            craftspeopleService.setLastMeeting(request.getCraftspersonId(), request.getLastMeeting());

            auditor.setLastMeeting(getActorName(), request);

            return noContent().build();
        } catch (InvalidLastMeetingDateException ex) {
            return badRequest().body(new ErrorResponse("The last meeting date is too far in the future"));
        }
    }

    @PostMapping("/craftspeople/lastmeeting/remove/{craftspersonId}")
    public ResponseEntity<ErrorResponse> removeLastMeeting(@PathVariable Integer craftspersonId) {
        try {
            craftspeopleService.removeLastMeeting(craftspersonId);

            auditor.removeLastMeeting(getActorName(), craftspersonId);

            return noContent().build();
        } catch (CraftspersonDoesntExistException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("That craftsperson doesn't exist"));
        }
    }

    @PutMapping("craftspeople/mentee/add")
    public ResponseEntity setMentee(@Valid @RequestBody AddMentorRequest request) {
        try {
            craftspeopleService.setMentee(
                    request.getMentorId(),
                    request.getMenteeId());

            auditor.setMentee(getActorName(), request);

            return ok().build();
        } catch (InvalidMentorRelationshipException ex) {
            return badRequest().body(new ErrorResponse("Cant add a craftsperson as their own mentor"));
        } catch (DuplicateMenteeException ex) {
            return badRequest().body(new ErrorResponse("Mentee already exists"));
        }
    }

    private String getActorName() {
        try {
            return httpServletRequest.getSession().getAttribute("name").toString();
        } catch (Exception e) {
            //  nice
            return "Unknown User";
        }
    }
}
