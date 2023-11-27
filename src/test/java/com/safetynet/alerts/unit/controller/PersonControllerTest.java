package com.safetynet.alerts.unit.controller;

import com.safetynet.alerts.component.JsonService;
import com.safetynet.alerts.controller.PersonController;
import com.safetynet.alerts.model.dao.Person;
import com.safetynet.alerts.service.implementation.PersonService;
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

@WebMvcTest(controllers = {PersonController.class})
class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PersonService personService;
    @MockBean
    private JsonService jsonService;
    Person personExpected;
    Person personExpected2;

    @BeforeEach
    void setUp() {
        personExpected = new Person("fullName", "lastName", "address", "city", "zip", "phone", "email");
        personExpected2 = new Person("fullName2", "lastName2", "address2", "city2", "zip2", "phone2", "email2");
    }

    @Test
    public void getPersons_returnListOfPersons() throws Exception {
        when(personService.getPersons()).thenReturn(List.of(
                personExpected,
                personExpected2
        ));

        mockMvc.perform(get("/persons"))
                .andExpect(content().json("[{\"lastName\":\""+ personExpected.getLastName() +"\",\"firstName\":\""+ personExpected.getFirstName() +"\"},{\"lastName\":\""+ personExpected2.getLastName() +"\",\"firstName\":\""+ personExpected2.getFirstName() +"\"}]"));
    }

    @Test
    public void getPerson_returnOnlyOnePerson() throws Exception {
        when(personService.getPerson(personExpected.getFirstName(), personExpected.getLastName())).thenReturn(personExpected);

        mockMvc.perform(get("/person/{firstName}/{lastName}", personExpected.getFirstName(), personExpected.getLastName()))
                .andExpect(content().json("{\"lastName\":\""+ personExpected.getLastName() +"\",\"firstName\":\""+ personExpected.getFirstName() +"\"}"));
    }

    @Test
    public void createPerson_returnCode200_whenPersonIsCreated() throws Exception {
        when(personService.savePerson(any())).thenReturn(true);

        mockMvc.perform(post("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"lastName\":\" " + personExpected.getLastName() + "\",\"firstName\":\"" + personExpected.getFirstName() + "\" }"))
                .andExpect((status().isOk()));
    }

    @Test
    public void updatePerson_returnCode200_whenThePersonIsUpdated() throws Exception {
        when(personService.updatePerson(any())).thenReturn(true);

        mockMvc.perform(put("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"lastName\":\" " + personExpected.getLastName() + "\",\"firstName\":\"" + personExpected.getFirstName() + "\" }"))
                .andExpect((status().isOk()));
    }

    @Test
    public void removePerson_returnCode200_whenThePersonIsRemoved() throws Exception {
        when(personService.removePerson(personExpected.getFirstName(), personExpected.getLastName())).thenReturn(true);

        mockMvc.perform(delete("/person/{firstName}/{lastName}", personExpected.getFirstName(), personExpected.getLastName()))
                .andExpect((status().isOk()));
    }

}