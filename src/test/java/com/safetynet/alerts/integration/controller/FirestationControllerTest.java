package com.safetynet.alerts.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.controller.FirestationController;
import com.safetynet.alerts.model.dao.Firestation;
import com.safetynet.alerts.service.IFirestationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.BDDMockito.*;
import org.springframework.http.MediaType;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@WebMvcTest(FirestationController.class)
class FirestationControllerTest {

    // TODO : https://www.baeldung.com/spring-boot-testing#unit-testing-with-webmvctest

    @Autowired
    private MockMvc mvc;

    private final Firestation firestationForTest = new Firestation();

    @MockBean
    private IFirestationService firestationService;

    @BeforeEach
    private void setUp() {
        firestationForTest.setAddress("Address");
        firestationForTest.setStation("1");
    }

    @Test
    public void add() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        String firestationAsString = objectMapper.writeValueAsString(firestationForTest);

        // Test to save a Firestation
        given(firestationService.saveFirestation(any(Firestation.class))).willReturn(true);
        mvc.perform(post("/firestation")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(firestationAsString))
                .andExpect(status().isOk());

        // Test to save an empty Firestation
        Firestation firestation = new Firestation();
        firestationAsString = objectMapper.writeValueAsString(firestation);
        mvc.perform(post("/firestation")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(firestationAsString))
                .andExpect(status().isBadRequest());
    }
}