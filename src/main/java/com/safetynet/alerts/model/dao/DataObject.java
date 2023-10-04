package com.safetynet.alerts.model.dao;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class DataObject {
    @JsonProperty("persons")
    private List<Person> persons;
    @JsonProperty("firestations")
    private List<Firestation> firestations;
    @JsonProperty("medicalrecords")
    private List<MedicalRecord> medicalRecords;
}