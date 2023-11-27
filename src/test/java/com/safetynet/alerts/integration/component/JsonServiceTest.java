package com.safetynet.alerts.integration.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.AlertsApplication;
import com.safetynet.alerts.component.JsonService;
import com.safetynet.alerts.controller.URLController;
import com.safetynet.alerts.model.constants.Constants;
import com.safetynet.alerts.model.dao.DataObject;
import com.safetynet.alerts.model.dao.Firestation;
import com.safetynet.alerts.model.dao.MedicalRecord;
import com.safetynet.alerts.model.dao.Person;
import com.safetynet.alerts.model.dto.ChildAlertURL;
import com.safetynet.alerts.model.dto.DataObjectDTO;
import com.safetynet.alerts.service.implementation.URLService;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class JsonServiceTest {

    // TODO : "injectmock" annotation

    @InjectMocks
    private JsonService jsonService;

    @Mock
    private ObjectMapper objectMapper;
    private DataObjectDTO dataObjectDTO;

    private DataObject fakeDataObject;
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

    @Before
    void setUp() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, IOException, NoSuchFieldException {

        MockitoAnnotations.initMocks(this);

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

        fakeDataObject = new DataObject(listOfPersons, listOfFirestations, listOfMedicalRecords);
        dataObjectDTO = new DataObjectDTO(fakeDataObject);


        when(objectMapper.readValue(Paths.get(any(String.class)).toFile(), DataObject.class)).thenReturn(fakeDataObject);

//      call post-constructor
//        Method postConstruct =  JsonService.class.getDeclaredMethod("getData"); // methodName,parameters
//        postConstruct.setAccessible(true);
//        postConstruct.invoke(jsonService);
//
//        Field field = jsonService.getClass().getDeclaredField("dataObject");
//        field.setAccessible(true);
//        field.set(jsonService, fakeDataObject);

        ReflectionTestUtils.setField(jsonService, "dataObject", fakeDataObject, DataObject.class);
    }

    @Test
    public void getPersonsShouldReturnPersons() throws Exception {
        when(jsonService.getPersons()).thenReturn(listOfPersons);

        assertThat(jsonService.getPersons()).isEqualTo("");
    }
}