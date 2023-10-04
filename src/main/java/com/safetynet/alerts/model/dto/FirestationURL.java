package com.safetynet.alerts.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class FirestationURL {
    private List<FirestationURLPerson> people;
    private Integer adults;
    private Integer children;


    public FirestationURL(List<FirestationURLPerson> people, Integer adults, Integer children){
        this.setPeople(people);
        this.setAdults(adults);
        this.setChildren(children);
    }
}