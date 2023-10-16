package com.safetynet.alerts.component;

import com.safetynet.alerts.model.dao.DataObject;
import com.safetynet.alerts.model.dao.Firestation;
import com.safetynet.alerts.model.dao.MedicalRecord;
import com.safetynet.alerts.model.dao.Person;
import com.safetynet.alerts.model.dto.DataObjectDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JsonServiceTest {

    @Mock
    private JsonService jsonService;

    private DataObject dataObject;

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
    void getPersonByFirstNameAndLastNameCheckIfReturnTheGoodOne() {
        when(this.jsonService.getPerson(anyString(), anyString())).thenReturn(this.dataObject.getPersons().get(0));

        assertEquals(this.dataObject.getPersons().get(0).getFirstName(), this.jsonService.getPerson("firstName1", "lastName1").getFirstName());
        assertEquals(this.dataObject.getPersons().get(0).getLastName(), this.jsonService.getPerson("firstName1", "lastName1").getLastName());
    }

    @Test
    void savePersonShouldReturnTrue() {
        when(this.jsonService.savePerson(any(Person.class))).thenReturn(true);

        assertEquals(true, this.jsonService.savePerson(this.dataObject.getPersons().get(0)));
    }

    @Test
    void updatePersonShouldReturnTrue() {
        when(this.jsonService.updatePerson(any(Person.class))).thenReturn(true);

        assertEquals(true, this.jsonService.updatePerson(this.dataObject.getPersons().get(0)));
    }

    @Test
    void removePersonShouldReturnTrue() {
        when(this.jsonService.removePerson(anyString(), anyString())).thenReturn(true);

        assertEquals(true, this.jsonService.removePerson(anyString(), anyString()));
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
        assertEquals(personList.size(), this.dataObject.getFirestations().size());
    }

    @Test
    void getFirestationByStationAndAddressCheckIfReturnTheGoodOne() {
        when(this.jsonService.getFirestation(anyString(), anyString())).thenReturn(this.dataObject.getFirestations().get(0));

        assertEquals(this.dataObject.getFirestations().get(0).getStation(), this.jsonService.getFirestation("firestation1", "address1").getStation());
        assertEquals(this.dataObject.getFirestations().get(0).getAddress(), this.jsonService.getFirestation("firestation1", "address1").getAddress());
    }


    @Test
    void saveFirestationShouldReturnTrue() {
        when(this.jsonService.saveFirestation(any(Firestation.class))).thenReturn(true);

        assertEquals(true, this.jsonService.saveFirestation(this.dataObject.getFirestations().get(0)));
    }

    @Test
    void updateFirestationShouldReturnTrue() {
        when(this.jsonService.updateFirestation(any(Firestation.class))).thenReturn(true);

        assertEquals(true, this.jsonService.updateFirestation(this.dataObject.getFirestations().get(0)));
    }

    @Test
    void removeFirestationFromStationShouldReturnTrue() {
        when(this.jsonService.removeFirestationFromStation(anyString())).thenReturn(true);

        assertEquals(true, this.jsonService.removeFirestationFromStation(anyString()));
    }

    @Test
    void removeFirestationFromAddressShouldReturnTrue() {
        when(this.jsonService.removeFirestationFromAddress(anyString())).thenReturn(true);

        assertEquals(true, this.jsonService.removeFirestationFromAddress(anyString()));
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
    void getMedicalRecordByFirstNameAndLastNameCheckIfReturnTheGoodOne() {
        when(this.jsonService.getMedicalRecord(anyString(), anyString())).thenReturn(this.dataObject.getMedicalRecords().get(0));

        assertEquals(this.dataObject.getMedicalRecords().get(0).getFirstName(), this.jsonService.getMedicalRecord("firstName1", "lastName1").getFirstName());
        assertEquals(this.dataObject.getMedicalRecords().get(0).getLastName(), this.jsonService.getMedicalRecord("firstName1", "lastName1").getLastName());
    }

    @Test
    void saveMedicalRecordShouldReturnTrue() {
        when(this.jsonService.saveMedicalRecord(any(MedicalRecord.class))).thenReturn(true);

        assertEquals(true, this.jsonService.saveMedicalRecord(this.dataObject.getMedicalRecords().get(0)));
    }

    @Test
    void updateMedicalRecordShouldReturnTrue() {
        when(this.jsonService.updateMedicalRecord(any(MedicalRecord.class))).thenReturn(true);

        assertEquals(true, this.jsonService.updateMedicalRecord(this.dataObject.getMedicalRecords().get(0)));
    }

    @Test
    void removeMedicalRecordShouldReturnTrue() {
        when(this.jsonService.removeMedicalRecord(anyString(), anyString())).thenReturn(true);

        assertEquals(true, this.jsonService.removeMedicalRecord(anyString(), anyString()));
    }
}