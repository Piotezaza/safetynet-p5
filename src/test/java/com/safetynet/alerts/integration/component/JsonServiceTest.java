package com.safetynet.alerts.integration.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.component.JsonService;
import com.safetynet.alerts.model.dto.DataObjectDTO;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.assertj.core.api.Assertions.anyOf;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class JsonServiceTest extends JsonService {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public JsonServiceTest() throws IOException {
        super("src/test/ressources/dataTest.json", objectMapper);
    }

    @Test
    public void saveDataShouldReturnTrue(){
        assertThat(saveData(dataObject)).isTrue();
    }

    @Test
    public void getPersonsShouldReturnPersons() {
        assertThat(getPersons()).isEqualTo(dataObject.getPersons());
    }

    @Test
    public void savePersonsShouldReturnTrue() {
        assertThat(savePersons(dataObject.getPersons())).isTrue();
    }

    @Test
    public void getFirestationsShouldReturnFirestations() {
        assertThat(getFirestations()).isEqualTo(dataObject.getFirestations());
    }

    @Test
    public void saveFirestationsShouldReturnTrue() {
        assertThat(saveFirestations(dataObject.getFirestations())).isTrue();
    }

    @Test
    public void getMedicalRecordsShouldReturnMedicalRecords() {
        assertThat(getMedicalRecords()).isEqualTo(dataObject.getMedicalRecords());
    }

    @Test
    public void saveMedicalRecordsShouldReturnTrue() {
        assertThat(saveMedicalRecords(dataObject.getMedicalRecords())).isTrue();
    }

    @Test
    public void initDataObjectDTOShouldReturnDataObjectDTO (){
        assertThat(initDataObjectDTO()).isInstanceOf(DataObjectDTO.class);
    }
}