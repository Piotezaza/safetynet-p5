package com.safetynet.alerts.model.dto;

import com.safetynet.alerts.model.dao.Person;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class HouseDTO {
    private List<PersonContactInfoDTO> adults;
    private List<PersonContactInfoDTO> children;

    public HouseDTO() {
        this.setAdults(new ArrayList<>());
        this.setChildren(new ArrayList<>());
    }
}
