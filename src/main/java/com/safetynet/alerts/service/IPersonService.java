package com.safetynet.alerts.service;

import com.safetynet.alerts.model.dao.Person;

import java.util.List;

public interface IPersonService {

    /**
     * Search all people
     * @return a list of person
     */
    List<Person> getPersons();

    /**
     * Search a single person
     * @param firstName
     * @param lastName
     * @return Person
     */
    Person getPerson(String firstName, String lastName);

    /**
     * Save a person
     * @param person
     * @return true or false
     */
    Boolean savePerson(Person person);

    /**
     * Change person informations except firstName and lastName
     * @param person
     * @return Person
     */
    Boolean updatePerson(Person person);

    /**
     * Delete a person
     * @param firstName
     * @param lastName
     * @return true or false
     */
    Boolean removePerson(String firstName, String lastName);
}
