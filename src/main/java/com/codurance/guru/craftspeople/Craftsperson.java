package com.codurance.guru.craftspeople;

import javax.persistence.*;

@Entity
@Table(name = "craftspeople")
public class Craftsperson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @ManyToOne
    private Craftsperson mentor;

    public Craftsperson() { }

    public Craftsperson(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Craftsperson(String firstName, String lastName, Craftsperson mentor) {
        this(firstName, lastName);
        this.mentor = mentor;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Craftsperson getMentor() {
        return mentor;
    }

    public void setMentor(Craftsperson mentor) {
        this.mentor = mentor;
    }
}
