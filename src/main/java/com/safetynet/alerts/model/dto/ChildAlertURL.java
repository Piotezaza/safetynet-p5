package com.safetynet.alerts.model.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChildAlertURL {
    private String firstName;
    private String lastName;
    private int age;

    public ChildAlertURL(PersonContactInfoDTO person){
        this.setFirstName(person.getFirstName());
        this.setLastName(person.getLastName());
        this.setAge(person.getAge());
    }
}
