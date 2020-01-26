package com.codurance.guru.infra.persistence.repository;

import com.codurance.guru.core.craftspeople.CraftspeopleRepository;
import com.codurance.guru.core.craftspeople.Craftsperson;
import com.codurance.guru.infra.persistence.entity.CraftspersonEntity;
import com.codurance.guru.infra.persistence.jpa.CraftspeopleJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CraftspeopleRepositoryImpl implements CraftspeopleRepository {

    @Autowired
    CraftspeopleJpaRepository jpaRepository;

    public List<Craftsperson> findByFirstNameAndLastName(String firstName, String lastName) {
        return jpaRepository
                .findByFirstNameAndLastName(firstName, lastName).stream()
                .map(CraftspersonEntity::toPOJO)
                .collect(Collectors.toList());
    }

    @Override
    public void updateLastmeeting(Integer id, Instant lastMeeting) {
        CraftspersonEntity craftsperson = jpaRepository.findById(id).get();
        craftsperson.setLastMeeting(lastMeeting);
        jpaRepository.save(craftsperson);
    }

    @Override
    public Craftsperson save(Craftsperson craftsperson) {
        return jpaRepository.save(new CraftspersonEntity(craftsperson)).toPOJO();
    }

    @Override
    public Optional<Craftsperson> findById(Integer id) {
        return jpaRepository.findById(id)
                .map(CraftspersonEntity::toPOJO);
    }

    @Override
    public void deleteById(Integer craftspersonId) {
        jpaRepository.deleteById(craftspersonId);
    }

    @Override
    public List<Craftsperson> findAll() {
        return jpaRepository.findAll().stream()
                .map(CraftspersonEntity::toPOJO)
                .collect(Collectors.toList());
    }

    @Override
    public void removeMentor(int menteeId) {
        CraftspersonEntity craftspersonEntity = jpaRepository.findById(menteeId).get();
        craftspersonEntity.setMentor(null);
        craftspersonEntity.setLastMeeting(null);

        jpaRepository.save(craftspersonEntity);
    }

    @Override
    public void addMentor(int mentorId, int menteeId) {
        CraftspersonEntity mentor = jpaRepository.findById(mentorId).get();
        CraftspersonEntity mentee = jpaRepository.findById(menteeId).get();

        mentee.setMentor(mentor);
        jpaRepository.save(mentee);
    }

    @Override
    public Craftsperson create(String firstName, String lastName) {
        return jpaRepository.save(new CraftspersonEntity(firstName, lastName)).toPOJO();
    }

    public long count() {
        return jpaRepository.count();
    }

    public boolean existsById(Integer id) {
        return jpaRepository.existsById(id);
    }
}

