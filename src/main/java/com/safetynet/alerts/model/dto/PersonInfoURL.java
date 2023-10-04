package com.safetynet.alerts.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class PersonInfoURL {
    private String lastName;
    private String email;
    private String address;
    private int age;
    private List<String> medications;
    private List<String> allergies;

    public PersonInfoURL(PersonContactInfoDTO person){
        this.setLastName(person.getLastName());
        this.setEmail(person.getEmail());
        this.setAge(person.getAge());
        this.setMedications(person.getMedications());
        this.setAllergies(person.getAllergies());
    }
}
