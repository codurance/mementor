package com.codurance.guru.infra.web.requests;

import javax.validation.constraints.NotBlank;

public class AddCraftspersonRequest {

    @NotBlank(message = "First name must have a value")
    private String firstName;

    @NotBlank(message = "Last name must have a value")
    private String lastName;

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
}
