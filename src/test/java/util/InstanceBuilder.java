package util;

import com.safetynet.alerts.model.dao.Person;

public class InstanceBuilder {

    public static Person createPerson(String fullName, String lastName, String address, String city, String zip, String phone, String email){
        return new Person(fullName, lastName, address, city, zip, phone, email);
    }
}