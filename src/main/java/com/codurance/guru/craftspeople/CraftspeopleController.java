package com.codurance.guru.craftspeople;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class CraftspeopleController {

    @Autowired
    private CraftspeopleService craftspeopleService;

    @GetMapping("/craftspeople/{craftspersonId}")
    public ResponseEntity retrieveCraftsperson(@PathVariable Integer craftspersonId) {
        Optional<Craftsperson> retrievedCraftsperson = craftspeopleService.retrieveCraftsperson(craftspersonId);
        return retrievedCraftsperson
                .map(craftsperson -> new ResponseEntity<>(craftsperson, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/craftspeople")
    public List<Craftsperson> retrieveAll() {
        return craftspeopleService.retrieveAllCraftsperson();
    }

    @PutMapping("craftspeople/mentee/add")
    public ResponseEntity setMentee(@RequestBody Map<String, String> mentorAndMenteesIds) {
        craftspeopleService.setMentee(Integer.parseInt(mentorAndMenteesIds.get("mentorId")),
                Integer.parseInt(mentorAndMenteesIds.get("menteeId")));
        return ResponseEntity.ok().build();
    }

    @PutMapping("craftspeople/mentee/remove/{menteeId}")
    public ResponseEntity removeMentee(@PathVariable int menteeId) {
        craftspeopleService.removeMentor(menteeId);
        return ResponseEntity.ok().build();
    }


    @DeleteMapping("/craftspeople/{craftspersonId}")
    public ResponseEntity deleteCraftsperson(@PathVariable Integer craftspersonId) {
        craftspeopleService.deleteCraftsperson(craftspersonId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/craftspeople/add")
    public ResponseEntity addNewCraftsperson(@RequestBody AddCraftspersonRequest addCraftspersonRequest) {
        if (addCraftspersonRequest.firstName == null || addCraftspersonRequest.lastName == null)
            return ResponseEntity.badRequest().build();

        Craftsperson craftsperson = craftspeopleService.addCraftsperson(addCraftspersonRequest.firstName, addCraftspersonRequest.lastName);
        return ResponseEntity.ok(craftsperson.getId());
    }

    @PostMapping("/craftspeople/mentor/add")
    public ResponseEntity<Void> addMentor(@RequestBody AddMentorRequest request) {
        craftspeopleService.addMentor(request.getMentorId(), request.getMenteeId());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/craftspeople/mentor/remove")
    public ResponseEntity<Void> removeMentor(@RequestBody RemoveMentorRequest request) {
        craftspeopleService.removeMentor(request.getMenteeId());
        return ResponseEntity.noContent().build();
    }


}

class AddCraftspersonRequest {
    String firstName;
    String lastName;

    public AddCraftspersonRequest() {
    }

    public AddCraftspersonRequest(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}

class AddMentorRequest {
    private int mentorId;
    private int menteeId;

    public int getMentorId() {
        return mentorId;
    }

    public void setMentorId(int mentorId) {
        this.mentorId = mentorId;
    }

    public int getMenteeId() {
        return menteeId;
    }

    public void setMenteeId(int menteeId) {
        this.menteeId = menteeId;
    }
}

class RemoveMentorRequest {
    private int menteeId;

    public int getMenteeId() {
        return menteeId;
    }

    public void setMenteeId(int menteeId) {
        this.menteeId = menteeId;
    }
}

