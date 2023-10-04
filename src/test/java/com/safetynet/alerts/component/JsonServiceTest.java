package com.safetynet.alerts.component;

import com.safetynet.alerts.model.dao.DataObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.model.dao.Firestation;
import com.safetynet.alerts.model.dao.MedicalRecord;
import com.safetynet.alerts.model.dao.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;



//@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
class JsonServiceTest {
    @Autowired
    private static JsonService jsonService = new JsonService();
    private final String path = "src/test/resources/dataTest.json";
    @Mock
    private DataObject dataObject;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    private void setUpTest() {
        dataObject = new DataObject();
        dataObject.setFirestations(new ArrayList<>());
        Firestation firestation1 = new Firestation("addresse1", "1");
        Firestation firestation2 = new Firestation("addresse2", "2");
        Firestation firestation3 = new Firestation("addresse3", "3");
        dataObject.getFirestations().add(firestation1);
        dataObject.getFirestations().add(firestation2);
        dataObject.getFirestations().add(firestation3);

        dataObject.setPersons(new ArrayList<>());
        Person person1 = new Person("firstName1", "lastName1", "address1", "city1", "zip1", "phone1", "email1");
        Person person2 = new Person("firstName2", "lastName2", "address2", "city2", "zip2", "phone2", "email2");
        Person person3 = new Person("firstName3", "lastName3", "address3", "city3", "zip3", "phone3", "email3");
        dataObject.getPersons().add(person1);
        dataObject.getPersons().add(person2);
        dataObject.getPersons().add(person3);

        List<String> medications = new ArrayList<>();
        medications.add("thradox:700mg");
        medications.add("pharmacol:2500mg");
        List<String> allergies = new ArrayList<>();
        allergies.add("nillacilan");

        dataObject.setMedicalRecords(new ArrayList<>());
        MedicalRecord medicalRecord1 = new MedicalRecord("firstName1", "lastName1", "birthdate", null, allergies);
        MedicalRecord medicalRecord2 = new MedicalRecord("firstName2", "lastName2", "birthdate", medications, allergies);
        MedicalRecord medicalRecord3 = new MedicalRecord("firstName3", "lastName3", "birthdate", null, null);
        dataObject.getMedicalRecords().add(medicalRecord1);
        dataObject.getMedicalRecords().add(medicalRecord2);
        dataObject.getMedicalRecords().add(medicalRecord3);

        when(jsonService.getPersons()).thenReturn(dataObject.getPersons());
    }

//    @Test
//    void saveDataShouldReturnTrue(){
//        var jsonservice = new JsonService();
//        assertTrue(jsonservice.saveData(dataObject));
//    }





//    @BeforeEach
//    private void setUpPerTest() {
//        when(personsDAO.getAll()).thenReturn(new ArrayList<>(Collections.singletonList(person)));
//        MedicalRecord medicalRecord = new MedicalRecord("John", "Boyd","01/01/2016", new ArrayList<>(Arrays.asList("aznol:350mg", "hydrapermazol:100mg")), new ArrayList<>(Collections.singletonList("nillacilan")));
//        when(medicalRecordsDAO.getAll()).thenReturn(new ArrayList<>(Collections.singletonList(medicalRecord)));
//        collectDataService = new CollectDataService(personsDAO,new FirestationsDAO(),medicalRecordsDAO);
//    }
//
//    @ExtendWith(SpringExtension.class)
//    class CollectDataServiceTest {
//
//    @MockBean
//    private PersonsDAO personsDAO;





    @Test
    void getPersonByFirstNameAndLastNameCheckIfReturnTheGoodOne() {
        String firstName = "firstName1";
        String lastName = "lastName1";

        Person person = jsonService.getPerson(firstName, lastName);
        assertEquals(firstName, person.getFirstName());
        assertEquals(lastName, person.getLastName());
    }
}