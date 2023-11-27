package com.safetynet.alerts.unit.service;

import com.safetynet.alerts.component.JsonService;
import com.safetynet.alerts.model.dao.Person;
import com.safetynet.alerts.service.implementation.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PersonServiceTest {

    @Autowired
    private PersonService personService;
    @MockBean
    private JsonService jsonService;

    private Person personA;
    private Person personB;
    private Person personC;

    List<Person> listOfPersonExpected;
    List<String> listOfLastNamesExpected;

    @BeforeEach
    public void setup() {
        personA = new Person("fullName", "lastName", "address", "city", "zip", "phone", "email");
        personB = new Person("fullName2", "lastName2", "address2", "city2", "zip2", "phone2", "email2");

        listOfPersonExpected = new ArrayList<>(Arrays.asList(personA, personB));
        listOfLastNamesExpected = new ArrayList<>(Arrays.asList(personA.getLastName(), personB.getLastName()));
    }

    @Test
    public void getPersons_shouldReturnCorrectPersons() {
        when(jsonService.getPersons()).thenReturn(listOfPersonExpected);
        personService.getPersons();

        verify(jsonService, Mockito.times(1)).getPersons();
        assertThat(personService.getPersons()).isEqualTo(listOfPersonExpected);
    }

    @Test
    public void getPerson_shouldReturnCorrectPerson() {
        when(jsonService.getPersons()).thenReturn(listOfPersonExpected);
        personService.getPerson(personA.getFirstName(), personA.getLastName());

        verify(jsonService, Mockito.times(1)).getPersons();
        assertThat(personService.getPerson(personA.getFirstName(), personA.getLastName())).isEqualTo(personA);
    }

    @Test
    public void savePerson_shouldReturnTrue_whenPersonIsSaved() {
        when(jsonService.savePersons(anyList())).thenReturn(true);
        personService.savePerson(personA);

        verify(jsonService, Mockito.times(2)).getPersons();
        assertThat(personService.savePerson(personA)).isTrue();
    }

    @Test
    public void savePerson_shouldReturnFalse_whenPersonAlreadyExist() {
        when(jsonService.getPersons()).thenReturn(listOfPersonExpected);
        when(jsonService.savePersons(listOfPersonExpected)).thenReturn(false);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            personService.savePerson(personA);
        });

        String expectedMessage = "Already in database";
        String actualMessage = exception.getMessage();

        verify(jsonService, Mockito.times(1)).getPersons();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void updatePerson_shouldReturnTrue_whenPersonIsUpdated() {
        when(jsonService.getPersons()).thenReturn(listOfPersonExpected);
        when(jsonService.savePersons(listOfPersonExpected)).thenReturn(true);

        personService.updatePerson(personA);

        verify(jsonService, Mockito.times(1)).getPersons();
        assertThat(personService.updatePerson(personA)).isTrue();
    }

    @Test
    public void removePerson_shouldReturnTrue_whenPersonIsRemoved() {
        when(jsonService.getPersons()).thenReturn(listOfPersonExpected);
        when(jsonService.savePersons(listOfPersonExpected)).thenReturn(true);

        personService.removePerson(personA.getFirstName(), personA.getLastName());

        verify(jsonService, Mockito.times(1)).getPersons();
        assertThat(personService.removePerson(personA.getFirstName(), personA.getLastName())).isTrue();
    }
}
