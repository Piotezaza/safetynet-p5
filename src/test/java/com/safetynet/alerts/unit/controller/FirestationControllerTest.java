package com.safetynet.alerts.unit.controller;

import com.safetynet.alerts.component.JsonService;
import com.safetynet.alerts.controller.FirestationController;
import com.safetynet.alerts.model.dao.Firestation;
import com.safetynet.alerts.service.implementation.FirestationService;
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

@WebMvcTest(controllers = {FirestationController.class})
class FirestationControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private FirestationService firestationService;
    Firestation firestationExpected;
    Firestation firestationExpected2;

    @BeforeEach
    void setUp() {
        firestationExpected = new Firestation("1 rue de la Paix", "10");
        firestationExpected2 = new Firestation("2 rue de la Paix", "11");
    }

    @Test
    public void getFirestations_returnListOfFirestations() throws Exception {
        when(firestationService.getFirestations()).thenReturn(List.of(
                firestationExpected,
                firestationExpected2
        ));

        mockMvc.perform(get("/firestations"))
                .andExpect(content().json("[{\"address\":\""+ firestationExpected.getAddress() +"\",\"station\":\""+ firestationExpected.getStation() +"\"},{\"address\":\""+ firestationExpected2.getAddress() +"\",\"station\":\""+ firestationExpected2.getStation() +"\"}]"));
    }

    @Test
    public void getFirestation_returnOnlyOneFirestation() throws Exception {
        when(firestationService.getFirestation(firestationExpected.getStation(), firestationExpected.getAddress())).thenReturn(firestationExpected);

        mockMvc.perform(get("/firestation/{station}/{address}", firestationExpected.getStation(), firestationExpected.getAddress()))
                .andExpect(content().json("{\"address\":\""+ firestationExpected.getAddress() +"\",\"station\":\""+ firestationExpected.getStation() +"\"}"));
    }

    @Test
    public void createFirestation_returnCode200_whenFirestationIsCreated() throws Exception {
        when(firestationService.saveFirestation(any())).thenReturn(true);

        mockMvc.perform(post("/firestation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"address\":\" " + firestationExpected.getAddress() + "\",\"station\":\"" + firestationExpected.getStation() + "\" }"))
                .andExpect((status().isOk()));
    }

    @Test
    public void updateFirestation_returnCode200_whenTheFirestationIsUpdated() throws Exception {
        when(firestationService.updateFirestation(any())).thenReturn(true);

        mockMvc.perform(put("/firestation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"address\":\" " + firestationExpected.getAddress() + "\",\"station\":\"" + firestationExpected.getStation() + "\" }"))
                .andExpect((status().isOk()));
    }

    @Test
    public void removeFirestationFromStation_returnCode200_whenTheFirestationIsRemoved() throws Exception {
        when(firestationService.removeFirestationFromStation(firestationExpected.getStation())).thenReturn(true);

        mockMvc.perform(delete("/firestation/station/{station}", firestationExpected.getStation()))
                .andExpect((status().isOk()));
    }

    @Test
    public void removeFirestationFromAddress_returnCode200_whenTheFirestationIsRemoved() throws Exception {
        when(firestationService.removeFirestationFromAddress(firestationExpected.getAddress())).thenReturn(true);

        mockMvc.perform(delete("/firestation/address/{address}", firestationExpected.getAddress()))
                .andExpect((status().isOk()));
    }
}