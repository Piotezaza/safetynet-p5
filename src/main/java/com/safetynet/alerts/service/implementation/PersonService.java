package com.safetynet.alerts.service.implementation;

import com.safetynet.alerts.component.JsonService;
import com.safetynet.alerts.model.dao.Person;
import com.safetynet.alerts.service.IPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService implements IPersonService {

    @Autowired
    private JsonService jsonService;

    @Override
    public List<Person> getPersons() {
        return jsonService.getPersons();
    }

    @Override
    public Person getPerson(String firstName, String lastName){
        return jsonService.getPerson(firstName, lastName);
    }

    @Override
    public Boolean savePerson(Person person) {
        return jsonService.savePerson(person);
    }

    @Override
    public Boolean updatePerson(Person person) {
        return jsonService.updatePerson(person);
    }

    @Override
    public Boolean removePerson(String firstName, String lastName){
        return jsonService.removePerson(firstName, lastName);
    }
}
