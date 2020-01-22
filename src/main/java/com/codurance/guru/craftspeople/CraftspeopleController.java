package com.codurance.guru.craftspeople;

import com.codurance.guru.craftspeople.exceptions.DuplicateMenteeException;
import com.codurance.guru.craftspeople.exceptions.InvalidLastMeetingDateException;
import com.codurance.guru.craftspeople.exceptions.InvalidMentorRelationshipException;
import com.codurance.guru.craftspeople.middleware.RequestInterceptor;
import com.codurance.guru.craftspeople.requests.AddCraftspersonRequest;
import com.codurance.guru.craftspeople.requests.AddMentorRequest;
import com.codurance.guru.craftspeople.requests.RemoveMentorRequest;
import com.codurance.guru.craftspeople.requests.UpdateLastMeetingRequest;
import com.codurance.guru.craftspeople.responses.ErrorResponse;
import com.google.api.client.extensions.appengine.http.UrlFetchTransport;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.*;

@RestController
public class CraftspeopleController {

    @Autowired
    private CraftspeopleService craftspeopleService;

    @PostMapping("/tokensignin")
    public ResponseEntity authenticateClient(@RequestBody String idTokenString) throws GeneralSecurityException, IOException {
        JacksonFactory jsonFactory = new JacksonFactory();
        HttpTransport transport = new NetHttpTransport();
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                // Specify the CLIENT_ID of the app that accesses the backend:
                .setAudience(Collections.singletonList("677831756912-90mpqndj2c96nac10mgjciibcdoiinra.apps.googleusercontent.com"))
                // Or, if multiple clients access the backend:
                //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
                .build();
// (Receive idTokenString by HTTPS POST)
        GoogleIdToken idToken = verifier.verify(idTokenString);
        if (idToken != null) {

            return ok().build();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }


    @GetMapping("/craftspeople/{craftspersonId}")
    public ResponseEntity retrieveCraftsperson(@PathVariable Integer craftspersonId) {
        Optional<Craftsperson> retrievedCraftsperson = craftspeopleService.retrieveCraftsperson(craftspersonId);
        return retrievedCraftsperson
                .map(ResponseEntity::ok)
                .orElseGet(() -> notFound().build());

    }

    @GetMapping("/craftspeople")
    public List<Craftsperson> retrieveAll() {

        return craftspeopleService.retrieveAllCraftsperson();
    }

    @PutMapping("craftspeople/mentee/add")
    public ResponseEntity setMentee(@Valid @RequestBody AddMentorRequest request){
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

    @DeleteMapping("/craftspeople/{craftspersonId}")
    public ResponseEntity deleteCraftsperson(@PathVariable Integer craftspersonId) {
        craftspeopleService.deleteCraftsperson(craftspersonId);
        return ok().build();
    }

    @PostMapping("/craftspeople/add")
    public ResponseEntity addNewCraftsperson(@Valid @RequestBody AddCraftspersonRequest request) {
        Craftsperson craftsperson = craftspeopleService.addCraftsperson(request.getFirstName(), request.getLastName());
        if (craftsperson == null) {
            return ResponseEntity.status(409).build();
        }
        return ok(craftsperson.getId());
    }

    @PostMapping("/craftspeople/mentor/add")
    public ResponseEntity<Void> addMentor(@Valid @RequestBody AddMentorRequest request) {
        craftspeopleService.addMentor(request.getMentorId(), request.getMenteeId());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/craftspeople/mentor/remove")
    public ResponseEntity<Void> removeMentor(@Valid @RequestBody RemoveMentorRequest request) {
        craftspeopleService.removeMentor(request.getMenteeId());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/craftspeople/lastmeeting")
    public ResponseEntity<ErrorResponse> setLastMeeting(@Valid @RequestBody UpdateLastMeetingRequest request) {
        try {
            craftspeopleService.setLastMeeting(request.getCraftspersonId(), request.getLastMeeting());
            return ResponseEntity.noContent().build();
        }
        catch (InvalidLastMeetingDateException ex){
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse("The last meeting date is too far in the future"));
        }
    }
}
