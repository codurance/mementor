package com.codurance.guru.infra.web.serialization;

import com.codurance.guru.core.craftspeople.Craftsperson;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

public class CraftspersonSerializer extends StdSerializer<Craftsperson> {

    protected CraftspersonSerializer() {
        super(Craftsperson.class);
    }

    @Override
    public void serialize(Craftsperson craftsperson, JsonGenerator gen, SerializerProvider provider) throws IOException {
        Person mentor = craftsperson.getMentor()
                .map(Person::new)
                .orElse(null);

        List<Person> mentees = craftsperson.getMentees().stream()
                .map(Person::new)
                .collect(Collectors.toList());

        Long lastMeeting = craftsperson.getLastMeeting()
                .map(Instant::getEpochSecond)
                .orElse(null);

        gen.writeObject(new SerializableCraftsperson(craftsperson, mentor, mentees, lastMeeting));
    }
}

