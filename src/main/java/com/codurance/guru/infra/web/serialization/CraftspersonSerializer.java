package com.codurance.guru.infra.web.serialization;

import com.codurance.guru.core.craftspeople.Craftsperson;
import com.codurance.guru.infra.persistence.repository.CraftspeopleRepositoryImpl;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CraftspersonSerializer extends JsonSerializer<Craftsperson> {

    private CraftspeopleRepositoryImpl craftspeopleRepository;

    public CraftspersonSerializer(CraftspeopleRepositoryImpl craftspeopleRepository) {
        this.craftspeopleRepository = craftspeopleRepository;
    }

    @Override
    public void serialize(Craftsperson craftsperson, JsonGenerator gen, SerializerProvider provider) throws IOException {
        Person mentor = craftsperson.getMentor()
                .flatMap(craftspeopleRepository::findById)
                .map(Person::new)
                .orElse(null);

        List<Person> mentees = craftsperson.getMentees().stream()
                .map(craftspeopleRepository::findById)
                .flatMap(Optional::stream)
                .map(Person::new)
                .collect(Collectors.toList());

        Long lastMeeting = craftsperson.getLastMeeting()
                .map(Instant::getEpochSecond)
                .orElse(null);

        gen.writeObject(new SerializableCraftsperson(craftsperson, mentor, mentees, lastMeeting));
    }
}

