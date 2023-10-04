package com.safetynet.alerts.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class FireURL {
    private String lastName;
    private String phone;
    private int age;
    private List<String> medications;
    private List<String> allergies;

    public FireURL(PersonContactInfoDTO person){
        this.setLastName(person.getLastName());
        this.setPhone(person.getPhone());
        this.setAge(person.getAge());
        this.setMedications(person.getMedications());
        this.setAllergies(person.getAllergies());
    }
}
