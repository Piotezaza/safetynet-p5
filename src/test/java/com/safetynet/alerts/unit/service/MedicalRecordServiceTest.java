package com.safetynet.alerts.unit.service;

import com.safetynet.alerts.component.JsonService;
import com.safetynet.alerts.model.dao.MedicalRecord;
import com.safetynet.alerts.service.implementation.MedicalRecordService;
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
public class MedicalRecordServiceTest {

    @Autowired
    private MedicalRecordService medicalRecordService;
    @MockBean
    private JsonService jsonService;

    private MedicalRecord medicalRecordA;
    private MedicalRecord medicalRecordB;
    private MedicalRecord medicalRecordC;

    List<MedicalRecord> listOfMedicalRecordExpected;
    List<String> listOfLastNamesExpected;

    @BeforeEach
    public void setup() {
        List<String> medicationsA = new ArrayList<>(Arrays.asList("medicationA", "medicationA", "medicationA"));
        List<String> allergiesA = new ArrayList<>(Arrays.asList("allergieA", "allergieA", "allergieA"));
        medicalRecordA = new MedicalRecord("firstNameA", "lastNameA", "birthdateA", medicationsA, allergiesA);

        List<String> medicationsB = new ArrayList<>(Arrays.asList("medicationB", "medicationB", "medicationB"));
        List<String> allergiesB = new ArrayList<>(Arrays.asList("allergieB", "allergieB", "allergieB"));
        medicalRecordB = new MedicalRecord("firstNameB", "lastNameB", "birthdateB", medicationsB, allergiesB);

        List<String> medicationsC = new ArrayList<>(Arrays.asList("medicationC", "medicationC", "medicationC"));
        List<String> allergiesC = new ArrayList<>(Arrays.asList("allergieC", "allergieC", "allergieC"));
        medicalRecordC = new MedicalRecord("firstNameC", "lastNameC", "birthdateC", medicationsC, allergiesC);

        listOfMedicalRecordExpected = new ArrayList<>(Arrays.asList(medicalRecordA, medicalRecordB, medicalRecordC));
        listOfLastNamesExpected = new ArrayList<>(Arrays.asList(medicalRecordA.getLastName(), medicalRecordB.getLastName()));
    }

    @Test
    public void getMedicalRecords_shouldReturnCorrectMedicalRecords() {
        when(jsonService.getMedicalRecords()).thenReturn(listOfMedicalRecordExpected);
        medicalRecordService.getMedicalRecords();

        verify(jsonService, Mockito.times(1)).getMedicalRecords();
        assertThat(medicalRecordService.getMedicalRecords()).isEqualTo(listOfMedicalRecordExpected);
    }

    @Test
    public void getMedicalRecord_shouldReturnCorrectMedicalRecord() {
        when(jsonService.getMedicalRecords()).thenReturn(listOfMedicalRecordExpected);
        medicalRecordService.getMedicalRecord(medicalRecordA.getFirstName(), medicalRecordA.getLastName());

        verify(jsonService, Mockito.times(1)).getMedicalRecords();
        assertThat(medicalRecordService.getMedicalRecord(medicalRecordA.getFirstName(), medicalRecordA.getLastName())).isEqualTo(medicalRecordA);
    }

    @Test
    public void saveMedicalRecord_shouldReturnTrue_whenMedicalRecordIsSaved() {
        when(jsonService.saveMedicalRecords(anyList())).thenReturn(true);
        medicalRecordService.saveMedicalRecord(medicalRecordA);

        verify(jsonService, Mockito.times(2)).getMedicalRecords();
        assertThat(medicalRecordService.saveMedicalRecord(medicalRecordA)).isTrue();
    }

    @Test
    public void saveMedicalRecord_shouldReturnFalse_whenMedicalRecordAlreadyExist() {
        when(jsonService.getMedicalRecords()).thenReturn(listOfMedicalRecordExpected);
        when(jsonService.saveMedicalRecords(listOfMedicalRecordExpected)).thenReturn(false);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            medicalRecordService.saveMedicalRecord(medicalRecordA);
        });

        String expectedMessage = "Already in database";
        String actualMessage = exception.getMessage();

        verify(jsonService, Mockito.times(1)).getMedicalRecords();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void updateMedicalRecord_shouldReturnTrue_whenMedicalRecordIsUpdated() {
        when(jsonService.getMedicalRecords()).thenReturn(listOfMedicalRecordExpected);
        when(jsonService.saveMedicalRecords(listOfMedicalRecordExpected)).thenReturn(true);

        medicalRecordService.updateMedicalRecord(medicalRecordA);

        verify(jsonService, Mockito.times(1)).getMedicalRecords();
        assertThat(medicalRecordService.updateMedicalRecord(medicalRecordA)).isTrue();
    }

    @Test
    public void removeMedicalRecord_shouldReturnTrue_whenMedicalRecordIsRemoved() {
        when(jsonService.getMedicalRecords()).thenReturn(listOfMedicalRecordExpected);
        when(jsonService.saveMedicalRecords(listOfMedicalRecordExpected)).thenReturn(true);

        medicalRecordService.removeMedicalRecord(medicalRecordA.getFirstName(), medicalRecordA.getLastName());

        verify(jsonService, Mockito.times(1)).getMedicalRecords();
        assertThat(medicalRecordService.removeMedicalRecord(medicalRecordA.getFirstName(), medicalRecordA.getLastName())).isTrue();
    }
}
