package com.safetynet.alerts.model.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FirestationURLPerson {
    private String firstName;
    private String lastName;
    private String address;
    private String phone;

    public FirestationURLPerson(PersonContactInfoDTO person){
        this.setFirstName(person.getFirstName());
        this.setLastName(person.getLastName());
        this.setPhone(person.getPhone());
    }
}
