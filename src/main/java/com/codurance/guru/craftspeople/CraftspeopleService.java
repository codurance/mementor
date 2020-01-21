package com.codurance.guru.craftspeople;

import com.codurance.guru.craftspeople.exceptions.InvalidLastMeetingDateException;
import com.codurance.guru.craftspeople.exceptions.DuplicateMenteeException;
import com.codurance.guru.craftspeople.exceptions.InvalidMentorRelationshipException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Component
public class CraftspeopleService {

    @Autowired
    private CraftspeopleRepository repository;

    @Autowired
    private CraftspeopleValidator craftspeopleValidator;

    public CraftspeopleService(CraftspeopleRepository repository, CraftspeopleValidator validator) {

        this.repository = repository;
        this.craftspeopleValidator = validator;
    }

    public Optional<Craftsperson> retrieveCraftsperson(Integer craftspersonId) {
        return repository.findById(craftspersonId);
    }

    public List<Craftsperson> retrieveAllCraftsperson() {
        return repository.findAll();
    }

    public void setMentee(int mentorId, int menteeId) throws DuplicateMenteeException, InvalidMentorRelationshipException {
        craftspeopleValidator.validateSetMentee(mentorId, menteeId);

        Craftsperson mentor = repository.findById(mentorId).get();
        Craftsperson mentee = repository.findById(menteeId).get();

        mentee.setMentor(mentor);

        repository.save(mentee);
    }

    public void removeMentor(int menteeId){
        Craftsperson mentee = repository.findById(menteeId).get();

        mentee.setMentor(null);
        mentee.setLastMeeting(null);

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

    public Craftsperson addCraftsperson(String firstName, String lastName) {
        if(craftspersonDoesNotExist(firstName, lastName)) {
            return repository.save(new Craftsperson(firstName, lastName));
        }
        return null;
    }

    public void setLastMeeting(int craftspersonId, int lastMeeting) {
        Instant lastMeetingInstant = Instant.ofEpochSecond(lastMeeting);

        if (lastMeetingInstant.isAfter(Instant.now().plus(1, ChronoUnit.DAYS))){
            throw new InvalidLastMeetingDateException();
        }

        Craftsperson craftsperson = repository.findById(craftspersonId).get();
        craftsperson.setLastMeeting(lastMeetingInstant);
        repository.save(craftsperson);
    }

    private boolean craftspersonDoesNotExist(String firstName, String lastName) {
        return repository.findByFirstNameAndLastName(firstName,lastName).size() == 0;
    }
}
