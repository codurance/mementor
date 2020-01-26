package com.codurance.guru.infra.persistence.repository;

import com.codurance.guru.core.craftspeople.CraftspeopleRepository;
import com.codurance.guru.core.craftspeople.Craftsperson;
import com.codurance.guru.infra.persistence.entity.CraftspersonEntity;
import com.codurance.guru.infra.persistence.jpa.CraftspeopleJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CraftspeopleRepositoryImpl implements CraftspeopleRepository {

    @Autowired
    CraftspeopleJpaRepository craftspeopleJpaRepository;

    public List<Craftsperson> findByFirstNameAndLastName(String firstName, String lastName) {
        return craftspeopleJpaRepository
                .findByFirstNameAndLastName(firstName, lastName).stream()
                .map(CraftspersonEntity::toPOJO)
                .collect(Collectors.toList());
    }

    @Override
    public Craftsperson save(Craftsperson craftsperson) {
        return craftspeopleJpaRepository.save(new CraftspersonEntity(craftsperson)).toPOJO();
    }

    @Override
    public Optional<Craftsperson> findById(Integer id) {
        return craftspeopleJpaRepository.findById(id)
                .map(CraftspersonEntity::toPOJO);
    }

    @Override
    public void deleteById(Integer craftspersonId) {
        craftspeopleJpaRepository.deleteById(craftspersonId);
    }

    @Override
    public List<Craftsperson> findAll() {
        return craftspeopleJpaRepository.findAll().stream()
                .map(CraftspersonEntity::toPOJO)
                .collect(Collectors.toList());
    }

    public long count() {
        return craftspeopleJpaRepository.count();
    }

    public boolean existsById(Integer id) {
        return craftspeopleJpaRepository.existsById(id);
    }
}

