package com.codurance.guru.craftspeople;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class CraftspeopleSampleData {

    @Autowired
    private CraftspeopleRepository repository;

    @EventListener
    public void appReady(ApplicationReadyEvent event) {

        Craftsperson mashB = repository.save(new Craftsperson("Mashooq", "Badar"));
        Craftsperson sandroM = repository.save(new Craftsperson("Sandro", "Mancuso"));
        Craftsperson steveL = repository.save(new Craftsperson("Steve", "Lydford"));
        Craftsperson chrisE = repository.save(new Craftsperson("Christopher", "Eyre", steveL));
        Craftsperson liamG = repository.save(new Craftsperson("Liam", "Griffin-Jowett", chrisE));
        Craftsperson chrisB = repository.save(new Craftsperson("Chris", "Bimson", sandroM));
        Craftsperson neilK = repository.save(new Craftsperson("Neil", "Kidd", chrisB));
        Craftsperson richW = repository.save(new Craftsperson("Richard", "Wild", steveL));
        Craftsperson jorgeG = repository.save(new Craftsperson("Jorge", "Gueorguiev", mashB));
        Craftsperson alfredoF = repository.save(new Craftsperson("Alfredo", "Fernández", sandroM));
        Craftsperson francescoG = repository.save(new Craftsperson("Francesco", "Gigli", chrisB));
        Craftsperson danB = repository.save(new Craftsperson("Dan", "Bunea", mashB));
        Craftsperson jordiC = repository.save(new Craftsperson("Jordi", "Creus", danB));
        Craftsperson alexI = repository.save(new Craftsperson("Alexandru", "Ignat"));
        Craftsperson jocelynF = repository.save(new Craftsperson("Jocelyn", "Facchini",alexI));
        Craftsperson samD = repository.save(new Craftsperson("Sam", "Davies", richW));
        repository.save(new Craftsperson("Bartomeu", "Galmés", jordiC));
        repository.save(new Craftsperson("Scott", "Edwards", steveL));
        repository.save(new Craftsperson("Rory", "Collins", chrisE));
        repository.save(new Craftsperson("Chris", "Reinmuller", alfredoF));
        repository.save(new Craftsperson("Pierre-Jean", "Baraud", francescoG));
        repository.save(new Craftsperson("Luciano", "Palma", sandroM));
        repository.save(new Craftsperson("Mattsi", "Jansky", neilK));
        repository.save(new Craftsperson("Alasdair", "Smith", liamG));
        repository.save(new Craftsperson("Andrew", "Pattison", mashB));
        repository.save(new Craftsperson("Tony", "Georgiadis", richW));
        repository.save(new Craftsperson("Sandro", "Viljoen", liamG));
        repository.save(new Craftsperson("Christina", "Ripley", jorgeG));
        repository.save(new Craftsperson("Krzysztof", "Napierala", jorgeG));
        repository.save(new Craftsperson("Tacuma", "Bellford", jocelynF));
        repository.save(new Craftsperson("Aurelija", "Zubaviciute",samD));
        repository.save(new Craftsperson("Robert", "Taylor"));
        repository.save(new Craftsperson("Chris", "Jeffery"));
        repository.save(new Craftsperson("Liliana", "Carneiro"));
        repository.save(new Craftsperson("Terry", "Balfour"));
        repository.save(new Craftsperson("Keith", "Smale"));
        repository.save(new Craftsperson("Lau", "Llobet"));
        repository.save(new Craftsperson("Giulia", "Mantuano"));
        repository.save(new Craftsperson("Magdalena", "Kocerba-Firek"));
        repository.save(new Craftsperson("André", "Guelfi Torres"));
        repository.save(new Craftsperson("Jim", "Bourke"));
        repository.save(new Craftsperson("Anne-Marie", "Mendonca"));
        repository.save(new Craftsperson("Jack", "Colemanzo"));
        repository.save(new Craftsperson("Raul", "Mordillo Lluva"));
        repository.save(new Craftsperson("Emma", "Booth"));
        repository.save(new Craftsperson("Ruth", "Díaz"));
        repository.save(new Craftsperson("James", "Birnie"));
        repository.save(new Craftsperson("David", "Halewood"));
        repository.save(new Craftsperson("Sherlock", "Qiao"));
        repository.save(new Craftsperson("Richard", "Hopwood"));
        repository.save(new Craftsperson("Andrei", "Bogomja"));
        repository.save(new Craftsperson("Ivan", "Katzarski"));
        repository.save(new Craftsperson("Matt", "Gray"));
        repository.save(new Craftsperson("Gregory", "Cann"));
        repository.save(new Craftsperson("Enric", "Jorda"));
        repository.save(new Craftsperson("Josep", "Mir"));
        repository.save(new Craftsperson("Gonzalo", "Gómez Sullain"));
        repository.save(new Craftsperson("Daniel", "Bird"));
        repository.save(new Craftsperson("Jordan", "Robinson"));
        repository.save(new Craftsperson("Ade", "Adegboye"));
        repository.save(new Craftsperson("Karolina", "Kondzielewska"));
        repository.save(new Craftsperson("Laurence", "Lord"));
        repository.save(new Craftsperson("Angus", "Paterson"));
        repository.save(new Craftsperson("Riccardo", "Toni"));
        repository.save(new Craftsperson("José", "Rodriguez"));
        repository.save(new Craftsperson("Sid", "Sivananda"));
        repository.save(new Craftsperson("Mark", "Gray"));
        repository.save(new Craftsperson("Etienne", "Mustow"));
        repository.save(new Craftsperson("Arnaud", "Claudel"));
        repository.save(new Craftsperson("Helena", "Abellán"));
        repository.save(new Craftsperson("Edward", "Rixon"));
        repository.save(new Craftsperson("José Pablo", "Wenzel"));
        repository.save(new Craftsperson("Stéphane", "Meny"));
        repository.save(new Craftsperson("Sylvester", "Abreu Loreto"));
        repository.save(new Craftsperson("Eloy", "Acosta Padrón"));
        repository.save(new Craftsperson("Giulio", "Perrone"));
        repository.save(new Craftsperson("Zabih", "Safdari"));
        repository.save(new Craftsperson("David", "Welch"));
        repository.save(new Craftsperson("Nichole", "Mellekas"));

    }
}
