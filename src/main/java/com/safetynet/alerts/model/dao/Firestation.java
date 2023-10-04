package com.safetynet.alerts.model.dao;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Firestation {
    @JsonProperty
    private String address;
    @JsonProperty
    private String station;
}
