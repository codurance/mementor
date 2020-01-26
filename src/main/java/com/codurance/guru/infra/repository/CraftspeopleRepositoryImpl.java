package com.codurance.guru.infra.repository;

import com.codurance.guru.core.craftspeople.CraftspeopleRepository;
import com.codurance.guru.core.craftspeople.Craftsperson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Component
public class CraftspeopleRepositoryImpl implements CraftspeopleRepository {

    @Autowired
    CraftspeopleJpaRepository craftspeopleJpaRepository;

    public List<Craftsperson> findByFirstNameAndLastName(String firstName, String lastName) {
        return craftspeopleJpaRepository.findByFirstNameAndLastName(firstName, lastName);
    }

    @Override
    public Craftsperson save(Craftsperson craftsperson) {
        return craftspeopleJpaRepository.save(craftsperson);
    }

    @Override
    public Optional<Craftsperson> findById(Integer id) {
        return craftspeopleJpaRepository.findById(id);
    }

    @Override
    public void deleteById(Integer craftspersonId) {
        craftspeopleJpaRepository.deleteById(craftspersonId);
    }

    @Override
    public List<Craftsperson> findAll() {
        return craftspeopleJpaRepository.findAll();
    }

    public long count() {
        return craftspeopleJpaRepository.count();
    }

    public boolean existsById(Integer id) {
        return craftspeopleJpaRepository.existsById(id);
    }
}

@Repository
interface CraftspeopleJpaRepository extends JpaRepository<Craftsperson, Integer> {

    List<Craftsperson> findByFirstNameAndLastName(String firstName, String lastName);

}