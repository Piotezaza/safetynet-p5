package com.safetynet.alerts.model.dto;

import com.safetynet.alerts.model.dao.Person;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class FirestationDTO {
    private String station;
    private List<CityDTO> cities;

    FirestationDTO(String station) {
        this.station = station;
        this.cities = new ArrayList<>();
    }
}
