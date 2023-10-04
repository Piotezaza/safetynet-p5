package com.safetynet.alerts.controller;

import com.safetynet.alerts.service.IPersonService;
import com.safetynet.alerts.model.dao.Person;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PersonController {

    @Autowired
    private IPersonService personService;

    private Logger logger = LogManager.getLogger(PersonController.class);

    @GetMapping("/persons")
    public List<Person> getPersons() {
        logger.info("Endpoint Person : getPersons | ️ send : nothing - return : List<Person>");
        return personService.getPersons();
    }

    @GetMapping("/person/{firstName}/{lastName}")
    public Person getPerson(@PathVariable String firstName, @PathVariable String lastName) {
        logger.info("Endpoint Person : getPerson | ️ send : String firstName \"" + firstName + "\" & String lastName \"" + lastName + "\" - return : Person");
        return personService.getPerson(firstName, lastName);
    }

    @PostMapping("/person")
    public Person createPerson(@RequestBody Person person) {
        logger.info("Endpoint Person : createPerson | ️ send : Person person \"" + person + "\" - return : Person");
        personService.savePerson(person);
        return person;
    }

    @PutMapping("/person")
    public Boolean updatePerson(@RequestBody Person person) {
        logger.info("Endpoint Person : updatePerson | ️ send : Person person \"" + person + "\" - return : Boolean");
        return personService.updatePerson(person);
    }

    @DeleteMapping("/person/{firstName}/{lastName}")
    public Boolean removePerson(@PathVariable String firstName, @PathVariable String lastName) {
        logger.info("Endpoint Person : removePersonFromStation | ️ send : String firstName \"" + firstName + "\" & String lastName \"" + lastName + "\" - return : Boolean");
        return personService.removePerson(firstName, lastName);
    }
}