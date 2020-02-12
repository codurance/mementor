package com.codurance.guru.audits;

import com.codurance.guru.GuruApplication;
import com.codurance.guru.craftspeople.CraftspeopleRepository;
import com.codurance.guru.craftspeople.Craftsperson;
import com.codurance.guru.craftspeople.requests.AddCraftspersonRequest;
import com.codurance.guru.craftspeople.requests.AddMentorRequest;
import com.codurance.guru.craftspeople.requests.RemoveMentorRequest;
import com.codurance.guru.craftspeople.requests.UpdateLastMeetingRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = GuruApplication.class)
public class AuditorTest {

    @Autowired
    CraftspeopleRepository craftspeopleRepository;

    @Autowired
    EventRepository eventRepository;

    @Autowired
    Auditor auditor;

    private Craftsperson craftspersonOne;
    private Craftsperson craftspersonTwo;
    private String user;

    @Before
    public void setUp() throws Exception {
        craftspersonOne = craftspeopleRepository.save(new Craftsperson("edward", "rixon"));
        craftspersonTwo = craftspeopleRepository.save(new Craftsperson("josito", "wenzelooooooooooooooooó"));
        user = "Arnaldo Arnaldo";
    }

    @Test
    public void can_create_an_event_for_adding_a_mentor_with_correct_message_details() {
        AddMentorRequest request = new AddMentorRequest();
        request.setMentorId(craftspersonOne.getId());
        request.setMenteeId(craftspersonTwo.getId());

        Event event = auditor.addMentor(user, request);

        assertEquals("Arnaldo Arnaldo added edward rixon as a mentor to josito wenzelooooooooooooooooó",
                eventRepository.findById(event.getId()).get().getMessage());
    }

    @Test
    public void can_create_an_event_for_adding_a_craftsperson() {
        AddCraftspersonRequest request = new AddCraftspersonRequest();
        request.setFirstName("edward");
        request.setLastName("rixon");

        Event event = auditor.addCraftsperson(user, request);

        assertEquals("Arnaldo Arnaldo added edward rixon as a new craftsperson",
                eventRepository.findById(event.getId()).get().getMessage());
    }

    @Test
    public void can_create_an_event_for_deleting_a_craftsperson() {
        Event event = auditor.deleteCraftsperson(user, "edward rixon");

        assertEquals("Arnaldo Arnaldo deleted edward rixon from the system",
                eventRepository.findById(event.getId()).get().getMessage());
    }

    @Test
    public void can_create_an_event_for_setting_the_last_meeting() {
        UpdateLastMeetingRequest request = new UpdateLastMeetingRequest();
        request.setCraftspersonId(craftspersonOne.getId());
        request.setLastMeeting(1501234567);

        Event event = auditor.setLastMeeting(user, request);

        assertEquals("Arnaldo Arnaldo updated the last meeting for edward rixon to 28/07/2017",
                eventRepository.findById(event.getId()).get().getMessage());
    }

    @Test
    public void can_create_an_event_for_removing_the_last_meeting() {
        Event event = auditor.removeLastMeeting(user, craftspersonOne.getId());

        assertEquals("Arnaldo Arnaldo removed the last meeting for edward rixon",
                eventRepository.findById(event.getId()).get().getMessage());
    }

    @Test
    public void can_create_an_event_for_setting_the_mentee_for_a_craftsperson() {
        AddMentorRequest request = new AddMentorRequest();
        request.setMentorId(craftspersonOne.getId());
        request.setMenteeId(craftspersonTwo.getId());

        Event event = auditor.setMentee(user, request);

        assertEquals("Arnaldo Arnaldo added josito wenzelooooooooooooooooó as a mentee to edward rixon",
                eventRepository.findById(event.getId()).get().getMessage());
    }

    @Test
    public void can_create_an_event_for_removing_a_mentee_from_a_craftsperson() {
        Event event = auditor.removeMentee(user, craftspersonOne.getId());

        assertEquals("Arnaldo Arnaldo removed the mentor of edward rixon",
                eventRepository.findById(event.getId()).get().getMessage());
    }

    @Test
    public void can_create_an_event_for_removing_the_mentor_of_a_craftsperson() {
        RemoveMentorRequest request = new RemoveMentorRequest();
        request.setMenteeId(craftspersonOne.getId());

        Event event = auditor.removeMentor(user, request);

        assertEquals("Arnaldo Arnaldo removed the mentor of edward rixon",
                eventRepository.findById(event.getId()).get().getMessage());
    }
}
