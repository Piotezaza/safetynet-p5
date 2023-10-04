package com.safetynet.alerts.model.dto;

import com.safetynet.alerts.model.dao.Person;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class PersonDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String birthdate;
    private List<String> medications;
    private List<String> allergies;
    private int age;

    public PersonDTO(Person person) {
        this.setFirstName(person.getFirstName());
        this.setLastName(person.getLastName());
        this.setEmail(person.getEmail());
        this.setMedications(new ArrayList<>());
        this.setAllergies(new ArrayList<>());
    }
}
