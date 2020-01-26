package com.codurance.guru.infra.repository;

import com.codurance.guru.core.configuration.lastmeeting.threshold.LastMeetingThreshold;
import com.codurance.guru.core.configuration.lastmeeting.threshold.LastMeetingThresholdConfigRepository;
import com.codurance.guru.core.craftspeople.Craftsperson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Component
public class SampleDataInjector {

    @Autowired
    private CraftspeopleRepositoryImpl craftspeopleRepository;

    @Autowired
    private LastMeetingThresholdConfigRepository lastMeetingThresholdConfigRepository;

    @EventListener
    public void appReady(ApplicationReadyEvent event) {
        if(lastMeetingThresholdConfigRepository.count() > 0) {
            System.out.println("config repository not empty, skipping initial data population");
        } else {
            System.out.println("injecting config sample data ..");
            lastMeetingThresholdConfigRepository.save(new LastMeetingThreshold(8));
        }

        if(craftspeopleRepository.count() > 0) {
            System.out.println("craftspeople repository not empty, skipping initial data population");
            return;
        }
        System.out.println("injecting craftspeople sample data ..");
        Craftsperson mashB = craftspeopleRepository.save(new Craftsperson("Mashooq", "Badar"));
        Craftsperson sandroM = craftspeopleRepository.save(new Craftsperson("Sandro", "Mancuso"));
        Craftsperson steveL = craftspeopleRepository.save(new Craftsperson("Steve", "Lydford"));
        Craftsperson chrisE = craftspeopleRepository.save(new Craftsperson("Christopher", "Eyre", steveL, makeLastMeeting(2020, 1, 1)));
        Craftsperson liamG = craftspeopleRepository.save(new Craftsperson("Liam", "Griffin-Jowett", chrisE, makeLastMeeting(2019, 12, 19)));
        Craftsperson chrisB = craftspeopleRepository.save(new Craftsperson("Chris", "Bimson", sandroM, makeLastMeeting(2019, 9, 1)));
        Craftsperson neilK = craftspeopleRepository.save(new Craftsperson("Neil", "Kidd", chrisB));
        Craftsperson richW = craftspeopleRepository.save(new Craftsperson("Richard", "Wild", steveL));
        Craftsperson jorgeG = craftspeopleRepository.save(new Craftsperson("Jorge", "Gueorguiev", mashB));
        Craftsperson alfredoF = craftspeopleRepository.save(new Craftsperson("Alfredo", "Fernández", sandroM));
        Craftsperson francescoG = craftspeopleRepository.save(new Craftsperson("Francesco", "Gigli", chrisB));
        Craftsperson danB = craftspeopleRepository.save(new Craftsperson("Dan", "Bunea", mashB));
        Craftsperson jordiC = craftspeopleRepository.save(new Craftsperson("Jordi", "Creus", danB));
        Craftsperson alexI = craftspeopleRepository.save(new Craftsperson("Alexandru", "Ignat"));
        Craftsperson jocelynF = craftspeopleRepository.save(new Craftsperson("Jocelyn", "Facchini",alexI));
        Craftsperson samD = craftspeopleRepository.save(new Craftsperson("Sam", "Davies", richW));
        craftspeopleRepository.save(new Craftsperson("Bartomeu", "Galmés", jordiC));
        craftspeopleRepository.save(new Craftsperson("Scott", "Edwards", steveL));
        craftspeopleRepository.save(new Craftsperson("Rory", "Collins", chrisE));
        craftspeopleRepository.save(new Craftsperson("Chris", "Reinmuller", alfredoF));
        craftspeopleRepository.save(new Craftsperson("Pierre-Jean", "Baraud", francescoG));
        craftspeopleRepository.save(new Craftsperson("Luciano", "Palma", sandroM));
        craftspeopleRepository.save(new Craftsperson("Mattsi", "Jansky", neilK));
        craftspeopleRepository.save(new Craftsperson("Alasdair", "Smith", liamG));
        craftspeopleRepository.save(new Craftsperson("Andrew", "Pattison", mashB));
        craftspeopleRepository.save(new Craftsperson("Tony", "Georgiadis", richW));
        craftspeopleRepository.save(new Craftsperson("Sandro", "Viljoen", liamG));
        craftspeopleRepository.save(new Craftsperson("Christina", "Ripley", jorgeG));
        craftspeopleRepository.save(new Craftsperson("Krzysztof", "Napierala", jorgeG));
        craftspeopleRepository.save(new Craftsperson("Tacuma", "Bellford", jocelynF));
        craftspeopleRepository.save(new Craftsperson("Aurelija", "Zubaviciute",samD));
        craftspeopleRepository.save(new Craftsperson("Robert", "Taylor"));
        craftspeopleRepository.save(new Craftsperson("Chris", "Jeffery"));
        craftspeopleRepository.save(new Craftsperson("Liliana", "Carneiro"));
        craftspeopleRepository.save(new Craftsperson("Terry", "Balfour"));
        craftspeopleRepository.save(new Craftsperson("Keith", "Smale"));
        craftspeopleRepository.save(new Craftsperson("Lau", "Llobet"));
        craftspeopleRepository.save(new Craftsperson("Giulia", "Mantuano"));
        craftspeopleRepository.save(new Craftsperson("Magdalena", "Kocerba-Firek"));
        craftspeopleRepository.save(new Craftsperson("André", "Guelfi Torres"));
        craftspeopleRepository.save(new Craftsperson("Jim", "Bourke"));
        craftspeopleRepository.save(new Craftsperson("Anne-Marie", "Mendonca"));
        craftspeopleRepository.save(new Craftsperson("Jack", "Colemanzo"));
        craftspeopleRepository.save(new Craftsperson("Raul", "Mordillo Lluva"));
        craftspeopleRepository.save(new Craftsperson("Emma", "Booth"));
        craftspeopleRepository.save(new Craftsperson("Ruth", "Díaz"));
        craftspeopleRepository.save(new Craftsperson("James", "Birnie"));
        craftspeopleRepository.save(new Craftsperson("David", "Halewood"));
        craftspeopleRepository.save(new Craftsperson("Sherlock", "Qiao"));
        craftspeopleRepository.save(new Craftsperson("Richard", "Hopwood"));
        craftspeopleRepository.save(new Craftsperson("Andrei", "Bogomja"));
        craftspeopleRepository.save(new Craftsperson("Ivan", "Katzarski"));
        craftspeopleRepository.save(new Craftsperson("Matt", "Gray"));
        craftspeopleRepository.save(new Craftsperson("Gregory", "Cann"));
        craftspeopleRepository.save(new Craftsperson("Enric", "Jorda"));
        craftspeopleRepository.save(new Craftsperson("Josep", "Mir"));
        craftspeopleRepository.save(new Craftsperson("Gonzalo", "Gómez Sullain"));
        craftspeopleRepository.save(new Craftsperson("Daniel", "Bird"));
        craftspeopleRepository.save(new Craftsperson("Jordan", "Robinson"));
        craftspeopleRepository.save(new Craftsperson("Ade", "Adegboye"));
        craftspeopleRepository.save(new Craftsperson("Karolina", "Kondzielewska"));
        craftspeopleRepository.save(new Craftsperson("Laurence", "Lord"));
        craftspeopleRepository.save(new Craftsperson("Angus", "Paterson"));
        craftspeopleRepository.save(new Craftsperson("Riccardo", "Toni"));
        craftspeopleRepository.save(new Craftsperson("José", "Rodriguez"));
        craftspeopleRepository.save(new Craftsperson("Sid", "Sivananda"));
        craftspeopleRepository.save(new Craftsperson("Mark", "Gray"));
        craftspeopleRepository.save(new Craftsperson("Etienne", "Mustow"));
        craftspeopleRepository.save(new Craftsperson("Arnaud", "Claudel"));
        craftspeopleRepository.save(new Craftsperson("Helena", "Abellán"));
        craftspeopleRepository.save(new Craftsperson("Edward", "Rixon"));
        craftspeopleRepository.save(new Craftsperson("José Pablo", "Wenzel"));
        craftspeopleRepository.save(new Craftsperson("Stéphane", "Meny"));
        craftspeopleRepository.save(new Craftsperson("Sylvester", "Abreu Loreto"));
        craftspeopleRepository.save(new Craftsperson("Eloy", "Acosta Padrón"));
        craftspeopleRepository.save(new Craftsperson("Giulio", "Perrone"));
        craftspeopleRepository.save(new Craftsperson("Zabih", "Safdari"));
        craftspeopleRepository.save(new Craftsperson("David", "Welch"));
        craftspeopleRepository.save(new Craftsperson("Nichole", "Mellekas"));
    }

    private Instant makeLastMeeting(int year, int month, int day) {
        return Instant.ofEpochSecond(
                LocalDateTime.of(year, month, day, 1, 0)
                        .toEpochSecond(ZoneOffset.UTC));
    }
}
