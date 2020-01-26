package com.codurance.guru.infra.web.serialization;

import com.codurance.guru.core.craftspeople.Craftsperson;
import com.codurance.guru.infra.persistence.repository.CraftspeopleRepositoryImpl;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SerializerConfiguration extends SimpleModule {

    private CraftspeopleRepositoryImpl craftspeopleRepository;

    @Autowired
    public SerializerConfiguration(CraftspeopleRepositoryImpl craftspeopleRepository) {
        this.craftspeopleRepository = craftspeopleRepository;
        addSerializer(Craftsperson.class, new CraftspersonSerializer(craftspeopleRepository));
    }
}
