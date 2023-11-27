package com.safetynet.alerts.unit.controller;

import com.safetynet.alerts.component.JsonService;
import com.safetynet.alerts.controller.MedicalRecordController;
import com.safetynet.alerts.model.dao.MedicalRecord;
import com.safetynet.alerts.service.implementation.MedicalRecordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = {MedicalRecordController.class})
class MedicalRecordControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private MedicalRecordService medicalRecordService;
    @MockBean
    private JsonService jsonService;
    MedicalRecord medicalRecordExpected;
    MedicalRecord medicalRecordExpected2;

    @BeforeEach
    void setUp() {
        medicalRecordExpected = new MedicalRecord();
        medicalRecordExpected.setFirstName("firstName");
        medicalRecordExpected.setLastName("lastName");
        medicalRecordExpected2 = new MedicalRecord();
        medicalRecordExpected2.setFirstName("firstName2");
        medicalRecordExpected2.setLastName("lastName2");
    }

    @Test
    public void getMedicalRecords_returnListOfMedicalRecords() throws Exception {
        when(medicalRecordService.getMedicalRecords()).thenReturn(List.of(
                medicalRecordExpected,
                medicalRecordExpected2
        ));

        mockMvc.perform(get("/medicalRecords"))
                .andExpect(content().json("[{\"lastName\":\""+ medicalRecordExpected.getLastName() +"\",\"firstName\":\""+ medicalRecordExpected.getFirstName() +"\"},{\"lastName\":\""+ medicalRecordExpected2.getLastName() +"\",\"firstName\":\""+ medicalRecordExpected2.getFirstName() +"\"}]"));
    }

    @Test
    public void getMedicalRecord_returnOnlyOneMedicalRecord() throws Exception {
        when(medicalRecordService.getMedicalRecord(medicalRecordExpected.getFirstName(), medicalRecordExpected.getLastName())).thenReturn(medicalRecordExpected);

        mockMvc.perform(get("/medicalRecord/{firstName}/{lastName}", medicalRecordExpected.getFirstName(), medicalRecordExpected.getLastName()))
                .andExpect(content().json("{\"lastName\":\""+ medicalRecordExpected.getLastName() +"\",\"firstName\":\""+ medicalRecordExpected.getFirstName() +"\"}"));
    }

    @Test
    public void createMedicalRecord_returnCode200_whenMedicalRecordIsCreated() throws Exception {
        when(medicalRecordService.saveMedicalRecord(any())).thenReturn(true);

        mockMvc.perform(post("/medicalRecord")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"lastName\":\" " + medicalRecordExpected.getLastName() + "\",\"firstName\":\"" + medicalRecordExpected.getFirstName() + "\" }"))
                .andExpect((status().isOk()));
    }

    @Test
    public void updateMedicalRecord_returnCode200_whenTheMedicalRecordIsUpdated() throws Exception {
        when(medicalRecordService.updateMedicalRecord(any())).thenReturn(true);

        mockMvc.perform(put("/medicalRecord")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"lastName\":\" " + medicalRecordExpected.getLastName() + "\",\"firstName\":\"" + medicalRecordExpected.getFirstName() + "\" }"))
                .andExpect((status().isOk()));
    }

    @Test
    public void removeMedicalRecord_returnCode200_whenTheMedicalRecordIsRemoved() throws Exception {
        when(medicalRecordService.removeMedicalRecord(medicalRecordExpected.getFirstName(), medicalRecordExpected.getLastName())).thenReturn(true);

        mockMvc.perform(delete("/medicalRecord/{firstName}/{lastName}", medicalRecordExpected.getFirstName(), medicalRecordExpected.getLastName()))
                .andExpect((status().isOk()));
    }

}