package com.codurance.guru.craftspeople;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CraftspeopleService {

    @Autowired
    private CraftspeopleRepository repository;

    public Optional<Craftsperson> retrieveCraftsperson(Integer craftspersonId) {
        return repository.findById(craftspersonId);
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

    public void removeMentor(int menteeId){
        Craftsperson mentee = repository.findById(menteeId).get();

        mentee.setMentor(null);

        repository.save(mentee);
    }

    public void addMentor(int mentorId, int menteeId) {
        Craftsperson mentor = repository.findById(mentorId).get();
        Craftsperson mentee = repository.findById(menteeId).get();

        mentee.setMentor(mentor);
        repository.save(mentee);
    }

    public void deleteCraftsperson(Integer craftspersonId) {
        Craftsperson craftspersonToRemove = repository.findById(craftspersonId).get();

        for (Craftsperson mentee: craftspersonToRemove.getMentees()) {
            mentee.setMentor(null);
            repository.save(mentee);
        }

        repository.deleteById(craftspersonId);
    }

    public boolean canAddMentee(int mentorId, int menteeId) {
        return mentorId != menteeId;
    }

    public boolean existingMentee(int mentorId, int menteeId) {
        Craftsperson mentor = repository.findById(mentorId).get();
        Craftsperson mentee = repository.findById(menteeId).get();

        return mentor.getMentees().contains(mentee);
    }

    public boolean isMentorable(int mentorId, int menteeId) {
        return canAddMentee(mentorId, menteeId) && !existingMentee(mentorId, menteeId);
    }

    public Craftsperson addCraftsperson(String firstName, String lastName) {
        if(craftspersonDoesNotExist(firstName, lastName)) {
            return repository.save(new Craftsperson(firstName, lastName));
        }
        return null;
    }

    private boolean craftspersonDoesNotExist(String firstName, String lastName) {
        return repository.findByFirstNameAndLastName(firstName,lastName).size() == 0;
    }
}
