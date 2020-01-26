package com.codurance.guru.core.craftspeople;

import com.codurance.guru.core.craftspeople.exceptions.DuplicateMenteeException;
import com.codurance.guru.core.craftspeople.exceptions.ExistingCraftspersonException;
import com.codurance.guru.core.craftspeople.exceptions.InvalidLastMeetingDateException;
import com.codurance.guru.core.craftspeople.exceptions.InvalidMentorRelationshipException;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

public class CraftspeopleService {

    private CraftspeopleRepository repository;

    private CraftspeopleValidator craftspeopleValidator;

    public CraftspeopleService(CraftspeopleRepository repository, CraftspeopleValidator validator) {
        this.repository = repository;
        this.craftspeopleValidator = validator;
    }

    public Craftsperson addCraftsperson(String firstName, String lastName) {
        if(craftspersonDoesNotExist(firstName, lastName)) {
            return repository.save(new Craftsperson(firstName, lastName));
        }
        throw new ExistingCraftspersonException();
    }

    public void addMentor(int mentorId, int menteeId) throws DuplicateMenteeException, InvalidMentorRelationshipException {
        craftspeopleValidator.validateSetMentee(mentorId, menteeId);
        repository.addMentor(mentorId, menteeId);
    }

    public void deleteCraftsperson(Integer craftspersonId) {
        Craftsperson craftspersonToRemove = repository.findById(craftspersonId).get();

        for (Integer menteeId: craftspersonToRemove.getMenteeIds()) {
            removeMentor(menteeId);
        }

        repository.deleteById(craftspersonId);
    }

    public void removeMentor(int menteeId){
        repository.removeMentor(menteeId);
    }

    public List<Craftsperson> retrieveAllCraftsperson() {
        return repository.findAll();
    }

    public Optional<Craftsperson> retrieveCraftsperson(Integer craftspersonId) {
        return repository.findById(craftspersonId);
    }

    public void setLastMeeting(int craftspersonId, int lastMeeting) {
        Instant lastMeetingInstant = Instant.ofEpochSecond(lastMeeting);

        if (lastMeetingInstant.isAfter(Instant.now().plus(1, ChronoUnit.DAYS))){
            throw new InvalidLastMeetingDateException();
        }

        repository.updateLastmeeting(craftspersonId, lastMeetingInstant);
    }

    public void setMentee(int mentorId, int menteeId) throws DuplicateMenteeException, InvalidMentorRelationshipException {
        craftspeopleValidator.validateSetMentee(mentorId, menteeId);

        Craftsperson mentor = repository.findById(mentorId).get();
        Craftsperson mentee = repository.findById(menteeId).get();

        mentee.setMentorId(mentor);

        repository.save(mentee);
    }

    private boolean craftspersonDoesNotExist(String firstName, String lastName) {
        return repository.findByFirstNameAndLastName(firstName,lastName).size() == 0;
    }
}
