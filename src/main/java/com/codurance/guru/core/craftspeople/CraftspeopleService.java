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

    private CraftspeopleRepository craftspeopleRepository;

    private CraftspeopleValidator craftspeopleValidator;

    public CraftspeopleService(CraftspeopleRepository craftspeopleRepository, CraftspeopleValidator validator) {
        this.craftspeopleRepository = craftspeopleRepository;
        this.craftspeopleValidator = validator;
    }

    public Craftsperson addCraftsperson(String firstName, String lastName) {
        if(craftspersonDoesNotExist(firstName, lastName)) {
            return craftspeopleRepository.save(new Craftsperson(firstName, lastName));
        }
        throw new ExistingCraftspersonException();
    }

    public void addMentor(int mentorId, int menteeId) throws DuplicateMenteeException, InvalidMentorRelationshipException {
        craftspeopleValidator.validateSetMentee(mentorId, menteeId);
        craftspeopleRepository.addMentor(mentorId, menteeId);
    }

    public void deleteCraftsperson(Integer craftspersonId) {
        Craftsperson craftspersonToRemove = craftspeopleRepository.findById(craftspersonId).get();

        for (Integer menteeId: craftspersonToRemove.getMenteeIds()) {
            removeMentor(menteeId);
        }

        craftspeopleRepository.deleteById(craftspersonId);
    }

    public void removeMentor(int menteeId){
        craftspeopleRepository.removeMentor(menteeId);
    }

    public List<Craftsperson> retrieveAllCraftsperson() {
        return craftspeopleRepository.findAll();
    }

    public Optional<Craftsperson> retrieveCraftsperson(Integer craftspersonId) {
        return craftspeopleRepository.findById(craftspersonId);
    }

    public void setLastMeeting(int craftspersonId, int lastMeeting) {
        Instant lastMeetingInstant = Instant.ofEpochSecond(lastMeeting);

        if (lastMeetingInstant.isAfter(Instant.now().plus(1, ChronoUnit.DAYS))){
            throw new InvalidLastMeetingDateException();
        }

        craftspeopleRepository.updateLastmeeting(craftspersonId, lastMeetingInstant);
    }

    public void setMentee(int mentorId, int menteeId) throws DuplicateMenteeException, InvalidMentorRelationshipException {
        craftspeopleValidator.validateSetMentee(mentorId, menteeId);
        craftspeopleRepository.addMentor(mentorId, menteeId);
    }

    private boolean craftspersonDoesNotExist(String firstName, String lastName) {
        return craftspeopleRepository.findByFirstNameAndLastName(firstName,lastName).size() == 0;
    }
}
