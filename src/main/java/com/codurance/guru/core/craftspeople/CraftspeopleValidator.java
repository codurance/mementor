package com.codurance.guru.core.craftspeople;

import com.codurance.guru.core.craftspeople.exceptions.DuplicateMenteeException;
import com.codurance.guru.core.craftspeople.exceptions.InvalidMentorRelationshipException;

public class CraftspeopleValidator {

    private CraftspeopleRepository repository;

    public CraftspeopleValidator(CraftspeopleRepository repository) {
        this.repository = repository;
    }

    public void validateSetMentee(int mentorId, int menteeId) {
        if(isMentorSameAsMentee(mentorId, menteeId)){
            throw new InvalidMentorRelationshipException();
        }

        if(existingMentee(mentorId, menteeId)){
            throw new DuplicateMenteeException();
        }
    }

    private boolean isMentorSameAsMentee(int mentorId, int menteeId) {
        return mentorId == menteeId;
    }

    private boolean existingMentee(int mentorId, int menteeId) {
        Craftsperson mentor = repository.findById(mentorId).get();

        return mentor.getMenteeIds().contains(menteeId);
    }
}
