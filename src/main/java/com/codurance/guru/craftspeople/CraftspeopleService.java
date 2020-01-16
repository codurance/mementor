package com.codurance.guru.craftspeople;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CraftspeopleService {

    @Autowired
    private CraftspeopleRepository repository;

    public Craftsperson retrieveCraftsperson(Integer craftspersonId) {
        return repository.findById(craftspersonId).orElse(new Craftsperson());
    }

    public List<Craftsperson> retrieveAllCraftsperson() {
        return repository.findAll();
    }

    public void setMentee(int mentorId, int menteeId) {
        Craftsperson mentor = repository.findById(mentorId).get();
        Craftsperson mentee = repository.findById(menteeId).get();

        mentee.setMentor(mentor);

        repository.save(mentee);
    }

    public void removeMentee(int menteeId){
        Craftsperson mentee = repository.findById(menteeId).get();

        mentee.setMentor(null);

        repository.save(mentee);
    }
}
