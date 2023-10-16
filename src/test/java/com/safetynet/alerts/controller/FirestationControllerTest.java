package com.safetynet.alerts.controller;

import com.safetynet.alerts.service.IFirestationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@WebMvcTest(FirestationController.class)
class FirestationControllerTest {

    // https://www.baeldung.com/spring-boot-testing#unit-testing-with-webmvctest
    @Autowired
    private MockMvc mvc;

    @MockBean
    private IFirestationService firestationService;

//    @Test
//    public void givenEmployees_whenGetEmployees_thenReturnJsonArray() throws Exception {
//
//        Employee alex = new Employee("alex");
//
//        List<Employee> allEmployees = Arrays.asList(alex);
//
//        given(service.getAllEmployees()).willReturn(allEmployees);
//
//        mvc.perform(get("/firestations")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(1)))
//                .andExpect(jsonPath("$[0].name", is(alex.getName())));
//    }
}