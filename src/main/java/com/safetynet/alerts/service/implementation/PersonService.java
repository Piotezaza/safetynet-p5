package com.safetynet.alerts.service.implementation;

import com.safetynet.alerts.component.JsonService;
import com.safetynet.alerts.model.dao.Firestation;
import com.safetynet.alerts.model.dao.Person;
import com.safetynet.alerts.service.IPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService implements IPersonService {

    @Autowired
    private JsonService jsonService;

    /**
     * Reads the list of persons in json data file
     *
     * @return a list of all persons
     */
    @Override
    public List<Person> getPersons() {
        return jsonService.getPersons();
    }

    /**
     * Get specific Person
     *
     * @param firstName The firstName of the Person concerned
     * @param lastName The lastName of the Person concerned
     * @return The founded Person
     */
    @Override
    public Person getPerson(String firstName, String lastName){
        List<Person> persons = jsonService.getPersons();
        List<Person> foundPersons = persons.stream().filter(person -> person.getFirstName().equals(firstName) && person.getLastName().equals(lastName)).toList();

        if (foundPersons.size() > 0) {
            return foundPersons.get(0);
        } else {
            return null;
        }
    }

    /**
     * Save a Person
     *
     * @param person Person to save
     * @return True if save is ok, false if save is ko
     */
    @Override
    public Boolean savePerson(Person person) {
        Person searchPerson = getPerson(person.getFirstName(), person.getLastName());

        if (searchPerson == null) {
            List<Person> persons = jsonService.getPersons();
            persons.add(person);
            return jsonService.savePersons(persons);
        } else {
            throw new RuntimeException("Already in database");
        }
    }

    /**
     * Update a Person
     *
     * @param person Person to update
     * @return True if the update is ok, false if the update is ko
     */
    @Override
    public Boolean updatePerson(Person person) {
        List<Person> personList = jsonService.getPersons();

        for (Person person1 : personList) {
            if (person.getFirstName().equals(person1.getFirstName()) && person.getLastName().equals(person1.getLastName())) {
                person1.setAddress(person.getAddress());
                person1.setCity(person.getCity());
                person1.setZip(person.getZip());
                person1.setPhone(person.getPhone());
                person1.setEmail(person.getEmail());
                break;
            }
        }
        return jsonService.savePersons(personList);
    }

    /**
     * Remove specific Person
     *
     * @param firstName The firstName of the Person concerned
     * @param lastName The lastName of the Person concerned
     * @return True if the remove is ok, false if the remove is ko
     */
    @Override
    public Boolean removePerson(String firstName, String lastName){
        List<Person> personList = jsonService.getPersons();

        Integer indice = 0;
        for(Person person1 : personList) {
            if(firstName.equals(person1.getFirstName()) && lastName.equals(person1.getLastName())) {
                break;
            }
            indice++;
        }

        personList.remove(indice);

        return jsonService.savePersons(personList);
    }
}
