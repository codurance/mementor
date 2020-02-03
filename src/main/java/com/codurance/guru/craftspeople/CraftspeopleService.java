package com.codurance.guru.craftspeople;

import com.codurance.guru.craftspeople.exceptions.*;
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

    public Craftsperson addCraftsperson(String firstName, String lastName) {
        if (craftspersonDoesNotExist(firstName, lastName)) {
            return repository.save(new Craftsperson(firstName, lastName));
        }
        throw new ExistingCraftspersonException();
    }

    public void addMentor(int mentorId, int menteeId) throws DuplicateMenteeException, InvalidMentorRelationshipException {
        craftspeopleValidator.validateSetMentee(mentorId, menteeId);

        Craftsperson mentor = getCraftsperson(mentorId);
        Craftsperson mentee = getCraftsperson(menteeId);

        mentee.setMentor(mentor);
        repository.save(mentee);
    }

    public void deleteCraftsperson(Integer craftspersonId) {
        Craftsperson craftspersonToRemove = getCraftsperson(craftspersonId);

        for (Craftsperson mentee : craftspersonToRemove.getMentees()) {
            mentee.removeMentor();
            repository.save(mentee);
        }

        repository.deleteById(craftspersonId);
    }

    public void removeMentor(int menteeId) {
        Craftsperson mentee = getCraftsperson(menteeId);

        mentee.removeMentor();
        repository.save(mentee);
    }

    public List<Craftsperson> retrieveAllCraftsperson() {
        return repository.findAll();
    }

    public Optional<Craftsperson> retrieveCraftsperson(Integer craftspersonId) {
        return repository.findById(craftspersonId);
    }

    public void setLastMeeting(int craftspersonId, int lastMeeting) {
        Instant lastMeetingInstant = Instant.ofEpochSecond(lastMeeting);

        if (lastMeetingInstant.isAfter(Instant.now().plus(1, ChronoUnit.DAYS))) {
            throw new InvalidLastMeetingDateException();
        }

        Craftsperson craftsperson = getCraftsperson(craftspersonId);
        craftsperson.setLastMeeting(lastMeetingInstant);
        repository.save(craftsperson);
    }

    public void removeLastMeeting(int craftspersonId) {
        Craftsperson craftsperson = getCraftsperson(craftspersonId);
        craftsperson.removeLastMeeting();
        repository.save(craftsperson);
    }

    public void setMentee(int mentorId, int menteeId) throws DuplicateMenteeException, InvalidMentorRelationshipException {
        craftspeopleValidator.validateSetMentee(mentorId, menteeId);

        Craftsperson mentor = getCraftsperson(mentorId);
        Craftsperson mentee = getCraftsperson(menteeId);

        mentee.setMentor(mentor);

        repository.save(mentee);
    }

    private Craftsperson getCraftsperson(int craftspersonId) {
        return repository.findById(craftspersonId).orElseThrow(CraftspersonDoesntExistException::new);
    }

    private boolean craftspersonDoesNotExist(String firstName, String lastName) {
        return repository.findByFirstNameAndLastName(firstName, lastName).size() == 0;
    }
}
