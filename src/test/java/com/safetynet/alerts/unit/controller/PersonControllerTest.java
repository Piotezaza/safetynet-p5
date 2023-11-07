package com.safetynet.alerts.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.controller.PersonController;
import com.safetynet.alerts.model.dao.Person;
import com.safetynet.alerts.service.implementation.PersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import util.InstanceBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PersonController.class)
public class PersonControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService personService;

    @Test
    public void getPersons_shouldReturnOk() throws Exception {
        List<Person> PersonResponseList = new ArrayList<>(Arrays.asList(
                InstanceBuilder.createPerson("Micheal", "Scott", "126 Kellum Court", "Scranton", "PA 18510", "(212)-555-2102", "michael.scott@dundermifflin.com"),
                InstanceBuilder.createPerson("Dwigth", "Schrute", "Schrute Farms Rural Rt. 6", "Honesdale", "PA 18431", "(717)-555-0177", "dwight.schrute@dundermifflin.com")));

        when(personService.getPersons()).thenReturn(PersonResponseList);

        mockMvc.perform(get("/persons").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*].firstName").isNotEmpty())
                .andExpect(jsonPath("$.[*].lastName").isNotEmpty())
                .andExpect(jsonPath("$.[*].address").isNotEmpty())
                .andExpect(jsonPath("$.[*].city").isNotEmpty())
                .andExpect(jsonPath("$.[*].zip").isNotEmpty())
                .andExpect(jsonPath("$.[*].phone").isNotEmpty())
                .andExpect(jsonPath("$.[*].email").isNotEmpty());
    }

    @Test
    public void getPerson_shouldReturnOk() throws Exception {
        Person person = InstanceBuilder.createPerson("Micheal", "Scott", "126 Kellum Court", "Scranton", "PA 18510", "(212)-555-2102", "michael.scott@dundermifflin.com");

        when(personService.getPerson("Micheal", "Scott")).thenReturn(person);

        mockMvc.perform(get("/persons").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*].firstName").isNotEmpty())
                .andExpect(jsonPath("$.[*].lastName").isNotEmpty())
                .andExpect(jsonPath("$.[*].address").isNotEmpty())
                .andExpect(jsonPath("$.[*].city").isNotEmpty())
                .andExpect(jsonPath("$.[*].zip").isNotEmpty())
                .andExpect(jsonPath("$.[*].phone").isNotEmpty())
                .andExpect(jsonPath("$.[*].email").isNotEmpty());
    }
}
