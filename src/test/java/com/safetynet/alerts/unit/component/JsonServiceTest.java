package com.safetynet.alerts.unit.component;

import com.safetynet.alerts.component.JsonService;
import com.safetynet.alerts.model.dao.DataObject;
import com.safetynet.alerts.model.dao.Firestation;
import com.safetynet.alerts.model.dao.MedicalRecord;
import com.safetynet.alerts.model.dao.Person;
import com.safetynet.alerts.service.implementation.FirestationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JsonServiceTest {

    @Mock
    private JsonService jsonService;

    private DataObject dataObject;

    @Autowired
    private FirestationService firestationService;

    @BeforeEach
    private void setUpTest() {
        this.firestationService = new FirestationService();
//        this.jsonService = new JsonService();

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

//     _____  ______ _____   _____  ____  _   _
//    |  __ \|  ____|  __ \ / ____|/ __ \| \ | |
//    | |__) | |__  | |__) | (___ | |  | |  \| |
//    |  ___/|  __| |  _  / \___ \| |  | | . ` |
//    | |    | |____| | \ \ ____) | |__| | |\  |
//    |_|    |______|_|  \_\_____/ \____/|_| \_|

    @Test
    void getPersonsAndCheckIfReturnRightSize() {
        when(this.jsonService.getPersons()).thenReturn(this.dataObject.getPersons());

        List<Person> personList = jsonService.getPersons();
        assertEquals(personList.size(), this.dataObject.getPersons().size());
    }

    @Test
    void savePersonShouldReturnTrue() {
        when(this.jsonService.savePersons(this.dataObject.getPersons())).thenReturn(true);

        assertEquals(true, this.jsonService.savePersons(this.dataObject.getPersons()));
    }


//     ______ _____ _____  ______  _____ _______    _______ _____ ____  _   _
//    |  ____|_   _|  __ \|  ____|/ ____|__   __|/\|__   __|_   _/ __ \| \ | |
//    | |__    | | | |__) | |__  | (___    | |  /  \  | |    | || |  | |  \| |
//    |  __|   | | |  _  /|  __|  \___ \   | | / /\ \ | |    | || |  | | . ` |
//    | |     _| |_| | \ \| |____ ____) |  | |/ ____ \| |   _| || |__| | |\  |
//    |_|    |_____|_|  \_\______|_____/   |_/_/    \_\_|  |_____\____/|_| \_|

    @Test
    void getFirestationsAndCheckIfReturnRightSize() {
        when(this.jsonService.getFirestations()).thenReturn(this.dataObject.getFirestations());

        List<Firestation> personList = jsonService.getFirestations();
        assertEquals(personList.size(), this.firestationService.getFirestations().size());
    }

    @Test
    void saveFirestationShouldReturnTrue() {
        when(this.jsonService.saveFirestations(this.dataObject.getFirestations())).thenReturn(true);

        assertEquals(true, this.jsonService.saveFirestations(this.dataObject.getFirestations()));
    }

//     __  __ ______ _____ _____ _____          _      _____  ______ _____ ____  _____  _____
//    |  \/  |  ____|  __ \_   _/ ____|   /\   | |    |  __ \|  ____/ ____/ __ \|  __ \|  __ \
//    | \  / | |__  | |  | || || |       /  \  | |    | |__) | |__ | |   | |  | | |__) | |  | |
//    | |\/| |  __| | |  | || || |      / /\ \ | |    |  _  /|  __|| |   | |  | |  _  /| |  | |
//    | |  | | |____| |__| || || |____ / ____ \| |____| | \ \| |___| |___| |__| | | \ \| |__| |
//    |_|  |_|______|_____/_____\_____/_/    \_\______|_|  \_\______\_____\____/|_|  \_\_____/

    @Test
    void getMedicalRecordsAndCheckIfReturnRightSize() {
        when(this.jsonService.getMedicalRecords()).thenReturn(this.dataObject.getMedicalRecords());

        List<MedicalRecord> personList = jsonService.getMedicalRecords();
        assertEquals(personList.size(), this.dataObject.getMedicalRecords().size());
    }

    @Test
    void saveMedicalRecordShouldReturnTrue() {
        when(this.jsonService.saveMedicalRecords(this.dataObject.getMedicalRecords())).thenReturn(true);

        assertEquals(true, this.jsonService.saveMedicalRecords(this.dataObject.getMedicalRecords()));
    }

//     _____ _______ ____
//    |  __ \__   __/ __ \
//    | |  | | | | | |  | |
//    | |  | | | | | |  | |
//    | |__| | | | | |__| |
//    |_____/  |_|  \____/

    // TODO : NE PASSE PAS || tester DataObjectDTO (constructeur)
    /* Faire un new DataObjectDTO en lui envoyant le DataObject de test et check les valeurs recherchées si tout est ok
    * */
//    @Test
//    void checkIfInitialisationOfDataObjectDTOIsOk() {
//        when(this.jsonService.initDataObjectDTO()).thenReturn(any(DataObjectDTO.class));
//
//        assertEquals(any(DataObjectDTO.class), this.jsonService.initDataObjectDTO());
//    }
}

// TODO : INTEGRATION, il faut mocker DataObject (pas de when) --- https://www.baeldung.com/spring-boot-testing#mocking-with-mockbean
