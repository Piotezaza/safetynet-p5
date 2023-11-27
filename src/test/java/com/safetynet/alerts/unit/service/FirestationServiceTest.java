package com.safetynet.alerts.unit.service;

import com.safetynet.alerts.component.JsonService;
import com.safetynet.alerts.model.dao.Firestation;
import com.safetynet.alerts.service.implementation.FirestationService;
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
public class FirestationServiceTest {

    @Autowired
    private FirestationService firestationService;
    @MockBean
    private JsonService jsonService;

    private Firestation firestationA;
    private Firestation firestationB;

    List<Firestation> listOfFirestationExpected;
    List<String> listOfAddressesExpected;

    @BeforeEach
    public void setup() {
        firestationA = new Firestation("addressA", "1");
        firestationB = new Firestation("addressB", "2");

        listOfFirestationExpected = new ArrayList<>(Arrays.asList(firestationA, firestationB));
        listOfAddressesExpected = new ArrayList<>(Arrays.asList(firestationA.getAddress(), firestationB.getAddress()));
    }

    @Test
    public void getFirestations_shouldReturnCorrectFirestations() {
        when(jsonService.getFirestations()).thenReturn(listOfFirestationExpected);
        firestationService.getFirestations();

        verify(jsonService, Mockito.times(1)).getFirestations();
        assertThat(firestationService.getFirestations()).isEqualTo(listOfFirestationExpected);
    }

    @Test
    public void getFirestation_shouldReturnCorrectFirestation() {
        when(jsonService.getFirestations()).thenReturn(listOfFirestationExpected);
        firestationService.getFirestation(firestationA.getStation(), firestationA.getAddress());

        verify(jsonService, Mockito.times(1)).getFirestations();
        assertThat(firestationService.getFirestation(firestationA.getStation(), firestationA.getAddress())).isEqualTo(firestationA);
    }

    @Test
    public void getFirestationsByStation_shouldReturnCorrectFirestations() {
        when(jsonService.getFirestations()).thenReturn(listOfFirestationExpected);
        firestationService.getFirestationsByStation(firestationA.getStation());

        verify(jsonService, Mockito.times(1)).getFirestations();
        assertThat(firestationService.getFirestationsByStation(firestationA.getStation())).isEqualTo(List.of(firestationA));
    }

    @Test
    public void getFirestationsByAddress_shouldReturnCorrectFirestations() {
        when(jsonService.getFirestations()).thenReturn(listOfFirestationExpected);
        firestationService.getFirestationsByAddress(firestationA.getAddress());

        verify(jsonService, Mockito.times(1)).getFirestations();
        assertThat(firestationService.getFirestationsByAddress(firestationA.getAddress())).isEqualTo(List.of(firestationA));
    }

    @Test
    public void saveFirestation_shouldReturnTrue_whenFirestationIsSaved() {
        when(jsonService.saveFirestations(anyList())).thenReturn(true);
        firestationService.saveFirestation(firestationA);

        verify(jsonService, Mockito.times(2)).getFirestations();
        assertThat(firestationService.saveFirestation(firestationA)).isTrue();
    }

    @Test
    public void saveFirestation_shouldReturnFalse_whenFirestationAlreadyExist() {
        when(jsonService.getFirestations()).thenReturn(listOfFirestationExpected);
        when(jsonService.saveFirestations(listOfFirestationExpected)).thenReturn(false);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            firestationService.saveFirestation(firestationA);
        });

        String expectedMessage = "Already in database";
        String actualMessage = exception.getMessage();

        verify(jsonService, Mockito.times(1)).getFirestations();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void updateFirestation_shouldReturnTrue_whenFirestationIsUpdated() {
        when(jsonService.getFirestations()).thenReturn(listOfFirestationExpected);
        when(jsonService.saveFirestations(listOfFirestationExpected)).thenReturn(true);

        firestationService.updateFirestation(firestationA);

        verify(jsonService, Mockito.times(1)).getFirestations();
        assertThat(firestationService.updateFirestation(firestationA)).isTrue();
    }

    @Test
    public void removeFirestationFromStation_shouldReturnTrue_whenFirestationIsRemoved() {
        when(jsonService.getFirestations()).thenReturn(listOfFirestationExpected);
        when(jsonService.saveFirestations(listOfFirestationExpected)).thenReturn(true);

        firestationService.removeFirestationFromStation(firestationA.getStation());

        verify(jsonService, Mockito.times(2)).getFirestations();
        assertThat(firestationService.removeFirestationFromStation(firestationA.getStation())).isTrue();
    }

    @Test
    public void removeFirestationFromAddress_shouldReturnTrue_whenFirestationIsRemoved() {
        when(jsonService.getFirestations()).thenReturn(listOfFirestationExpected);
        when(jsonService.saveFirestations(listOfFirestationExpected)).thenReturn(true);

        firestationService.removeFirestationFromAddress(firestationA.getAddress());

        verify(jsonService, Mockito.times(2)).getFirestations();
        assertThat(firestationService.removeFirestationFromAddress(firestationA.getAddress())).isTrue();
    }
}