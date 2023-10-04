package com.safetynet.alerts.model.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
public class CityDTO {
    private String cityName;
    private String zip;
    private Map<String, HouseDTO> houses;

    public CityDTO (String cityName, String zip){
        this.setCityName(cityName);
        this.setZip(zip);
        this.setHouses(new HashMap<>());
    }

    public void addHouse(String address){
        this.houses.put(address, new HouseDTO());
    }
}

