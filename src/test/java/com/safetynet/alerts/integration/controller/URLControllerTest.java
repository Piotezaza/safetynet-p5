package com.safetynet.alerts.integration.controller;

import com.safetynet.alerts.component.JsonService;
import com.safetynet.alerts.model.constants.Constants;
import com.safetynet.alerts.model.dao.DataObject;
import com.safetynet.alerts.model.dao.Firestation;
import com.safetynet.alerts.model.dao.MedicalRecord;
import com.safetynet.alerts.model.dao.Person;
import com.safetynet.alerts.model.dto.DataObjectDTO;
import com.safetynet.alerts.service.implementation.URLService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class URLControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private URLService urlService;
    @MockBean
    private JsonService jsonService;

    private String testAddress;
    private String testStation;
    private DataObjectDTO dataObjectDTO;
    private DataObject dataObject;
    private Person personA;
    private Person personB;
    private Person personC;
    private List<Person> listOfPersons;
    private Firestation firestationA;
    private Firestation firestationB;
    private List<Firestation> listOfFirestations;
    private MedicalRecord medicalRecordA;
    private MedicalRecord medicalRecordB;
    private MedicalRecord medicalRecordC;
    private List<MedicalRecord> listOfMedicalRecords;

    @BeforeEach
    void setUp() {
        testAddress = "addressA";
        testStation = "1";

        // PERSONS
        personA = new Person("firstNameA", "lastNameA", "addressA", Constants.CITY, "zipA", "phoneA", "emailA");
        personB = new Person("firstNameB", "lastNameB", "addressB", Constants.CITY, "zipB", "phoneB", "emailB");
        personC = new Person("firstNameC", "lastNameC", "addressC", Constants.CITY, "zipC", "phoneC", "emailC");
        listOfPersons = new ArrayList<>(Arrays.asList(personA, personB, personC));

        // FIRESTATIONS
        firestationA = new Firestation("addressA", "1");
        firestationB = new Firestation("addressB", "2");
        listOfFirestations = new ArrayList<>(Arrays.asList(firestationA, firestationB));

        // MEDICAL RECORDS
        List<String> medicationsA = new ArrayList<>(Arrays.asList("medicationA", "medicationA", "medicationA"));
        List<String> allergiesA = new ArrayList<>(Arrays.asList("allergieA", "allergieA", "allergieA"));
        medicalRecordA = new MedicalRecord("firstNameA", "lastNameA", "01/01/2020", medicationsA, allergiesA);
        List<String> medicationsB = new ArrayList<>(Arrays.asList("medicationB", "medicationB", "medicationB"));
        List<String> allergiesB = new ArrayList<>(Arrays.asList("allergieB", "allergieB", "allergieB"));
        medicalRecordB = new MedicalRecord("firstNameB", "lastNameB", "02/02/1992", medicationsB, allergiesB);
        List<String> medicationsC = new ArrayList<>(Arrays.asList("medicationC", "medicationC", "medicationC"));
        List<String> allergiesC = new ArrayList<>(Arrays.asList("allergieC", "allergieC", "allergieC"));
        medicalRecordC = new MedicalRecord("firstNameC", "lastNameC", "02/02/2022", medicationsC, allergiesC);
        listOfMedicalRecords = new ArrayList<>(Arrays.asList(medicalRecordA, medicalRecordB, medicalRecordC));

        dataObject = new DataObject(listOfPersons, listOfFirestations, listOfMedicalRecords);
        dataObjectDTO = new DataObjectDTO(dataObject);
    }

    @Test
    public void childAlertShouldReturnData() throws Exception {
        when(jsonService.initDataObjectDTO()).thenReturn(dataObjectDTO);

        mockMvc.perform(get("/childAlert?address={testAddress}", testAddress))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.children[0].firstName",is(personA.getFirstName())));
    }

    @Test
    public void firestationShouldReturnData() throws Exception {
        when(jsonService.initDataObjectDTO()).thenReturn(dataObjectDTO);

        mockMvc.perform(get("/firestation?stationNumber={testStation}", testStation))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.people[0].firstName",is(personA.getFirstName())));
    }

    @Test
    public void phoneAlertShouldReturnData() throws Exception {
        when(jsonService.initDataObjectDTO()).thenReturn(dataObjectDTO);

        mockMvc.perform(get("/phoneAlert?firestation={testFirestation}", firestationB.getStation()))
                .andExpect(status().isOk())
                .andExpect(content().json("[\"" + personB.getPhone() +"\"]"));
    }

    @Test
    public void fireShouldReturnData() throws Exception {
        when(jsonService.initDataObjectDTO()).thenReturn(dataObjectDTO);

        mockMvc.perform(get("/fire?address={testAddress}", personB.getAddress()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.2[0].lastName",is(personB.getLastName())));
    }

    @Test
    public void stationsShouldReturnData() throws Exception {
        when(jsonService.initDataObjectDTO()).thenReturn(dataObjectDTO);

        mockMvc.perform(get("/flood/stations?stations={testFirestation}", firestationA.getStation()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$."+firestationA.getAddress()+"[0].lastName",is(personA.getLastName())));
    }

    @Test
    public void personInfoShouldReturnData() throws Exception {
        when(jsonService.initDataObjectDTO()).thenReturn(dataObjectDTO);

        mockMvc.perform(get("/personInfo?firstName={testFirstName}&lastName={testLastName}", personA.getFirstName(), personA.getLastName()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].lastName",is(personA.getLastName())));
    }

    @Test
    public void communityEmailShouldReturnData() throws Exception {
        when(jsonService.initDataObjectDTO()).thenReturn(dataObjectDTO);

        mockMvc.perform(get("/communityEmail?city={testCity}", personA.getCity()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0]",is(personB.getEmail())));
    }

}