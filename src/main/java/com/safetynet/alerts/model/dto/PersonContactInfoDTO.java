package com.safetynet.alerts.model.dto;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.safetynet.alerts.model.dao.Person;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonFilter("dynamicPeopleFiltrer")
public class PersonContactInfoDTO extends PersonDTO {
    private String phone;

    public PersonContactInfoDTO(Person person) {
        super(person);
        this.setPhone(person.getPhone());
    }
}
