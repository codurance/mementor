package com.codurance.guru.craftspeople;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
public class CraftspeopleController {

    @Autowired
    private CraftspeopleService craftspeopleService;

    @GetMapping("/craftspeople/{craftspersonId}")
    public Craftsperson retrieveCraftsperson(@PathVariable Integer craftspersonId) {
        return craftspeopleService.retrieveCraftsperson(craftspersonId);
    }

    @GetMapping("/craftspeople")
    public List<Craftsperson> retrieveAll() {
        return craftspeopleService.retrieveAllCraftsperson();
    }

    @DeleteMapping("/craftspeople/{craftspersonId}")
    public void deleteCraftsperson(@PathVariable Integer craftspersonId){
        craftspeopleService.deleteCraftsperson(craftspersonId);
    }


    @PostMapping("/craftspeople/mentor/add")
    public ResponseEntity<Void> addMentor(@RequestBody AddMentorRequest request) {
        craftspeopleService.addMentor(request.getMentorId(), request.getMenteeId());
        return ResponseEntity.noContent().build();
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
