package com.safetynet.alerts.component;

import com.safetynet.alerts.model.dao.DataObject;
import com.safetynet.alerts.model.dao.Firestation;
import com.safetynet.alerts.model.dao.MedicalRecord;
import com.safetynet.alerts.model.dao.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class JsonServiceTestIntegration {
    @Mock
    private DataObject dataObject;

    private JsonService jsonService = new JsonService();

    @BeforeEach
    private void setUpTest() {
        dataObject = new DataObject();
        dataObject.setFirestations(new ArrayList<>());
        dataObject.getFirestations().add(new Firestation("address1", "1"));
        dataObject.getFirestations().add(new Firestation("address1", "2"));
        dataObject.getFirestations().add(new Firestation("address3", "3"));
        dataObject.getFirestations().add(new Firestation("address4", "3"));

        dataObject.setPersons(new ArrayList<>());
        dataObject.getPersons().add(new Person("firstName1", "lastName1", "address1", "city1", "zip1", "phone1", "email1"));
        dataObject.getPersons().add(new Person("firstName2", "lastName2", "address2", "city2", "zip2", "phone2", "email2"));
        dataObject.getPersons().add(new Person("firstName3", "lastName3", "address3", "city3", "zip3", "phone3", "email3"));

        dataObject.setMedicalRecords(new ArrayList<>());
        List<String> medications = new ArrayList<>();
        medications.add("thradox:700mg");
        medications.add("pharmacol:2500mg");
        List<String> allergies = new ArrayList<>();
        allergies.add("nillacilan");

        dataObject.getMedicalRecords().add(new MedicalRecord("firstName1", "lastName1", "birthdate", null, allergies));
        dataObject.getMedicalRecords().add(new MedicalRecord("firstName2", "lastName2", "birthdate", medications, allergies));
        dataObject.getMedicalRecords().add(new MedicalRecord("firstName3", "lastName3", "birthdate", null, null));
    }

    // TODO : INTEGRATION, il faut mocker DataObject (pas de when) --- https://www.baeldung.com/spring-boot-testing#mocking-with-mockbean
    @Test
    void getFirestationsByStationCheckIfReturnGoodSize() {
        // Quand je cherche la station 3, doit me retourner deux firestations
        List<Firestation> firestations = jsonService.getFirestations();
        //List<Firestation> foundStations = firestations.stream().filter(firestation -> firestation.getStation().equals(station)).toList();

        //assertEquals(EXPECTED, ACTUAL);
        //assertEquals(EXPECTED, ACTUAL);
    }

//    @Test
//    void getFirestationByAddressCheckIfReturnTheGoodOne() {
//        when(this.jsonService.getFirestationsByAddress("address1")).thenReturn(this.dataObject.getFirestations());
//
//        assertEquals(this.dataObject.getFirestations().get(0).getStation(), this.jsonService.getFirestation("firestation1", "address1").getStation());
//        assertEquals(this.dataObject.getFirestations().get(0).getAddress(), this.jsonService.getFirestation("firestation1", "address1").getAddress());
//    }


//     _____ _______ ____
//    |  __ \__   __/ __ \
//    | |  | | | | | |  | |
//    | |  | | | | | |  | |
//    | |__| | | | | |__| |
//    |_____/  |_|  \____/

//    @Test
//    void checkIfInitialisationOfDataObjectDTOIsOk() {
//        when(this.jsonService.initDataObjectDTO()).thenReturn(any(DataObjectDTO.class));
//
//        assertEquals(any(DataObjectDTO.class), this.jsonService.initDataObjectDTO());
//    }
}
