package com.codurance.guru.craftspeople;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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


}
