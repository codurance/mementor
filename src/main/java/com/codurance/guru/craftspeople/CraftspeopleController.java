package com.codurance.guru.craftspeople;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class CraftspeopleController {

    @Autowired
    private CraftspeopleService craftspeopleService;

    @GetMapping("/craftspeople/{craftspersonId}")
    public ResponseEntity<Craftsperson> retrieveCraftsperson(@PathVariable Integer craftspersonId) {
        Optional<Craftsperson> retrievedCraftsperson = craftspeopleService.retrieveCraftsperson(craftspersonId);
        return retrievedCraftsperson.isPresent()
                ? new ResponseEntity(retrievedCraftsperson.get(), HttpStatus.OK)
                : new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/craftspeople")
    public List<Craftsperson> retrieveAll() {
        return craftspeopleService.retrieveAllCraftsperson();
    }

    @PutMapping("craftspeople/addmentee")
    String setMentee(@RequestBody Map<String, String> mentorAndMenteesIds) {
        craftspeopleService.setMentee(Integer.valueOf(mentorAndMenteesIds.get("mentorId")),
                Integer.valueOf(mentorAndMenteesIds.get("menteeId")));
        return "OK";
    }

    @PutMapping("craftspeople/mentee/remove/{menteeId}")
    String removeMentee(@PathVariable int menteeId){
        craftspeopleService.removeMentee(menteeId);
        return "OK";
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
