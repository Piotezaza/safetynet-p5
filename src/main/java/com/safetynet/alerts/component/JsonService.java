package com.safetynet.alerts.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.controller.FirestationController;
import com.safetynet.alerts.model.dao.DataObject;
import com.safetynet.alerts.model.dao.Firestation;
import com.safetynet.alerts.model.dao.MedicalRecord;
import com.safetynet.alerts.model.dao.Person;
import com.safetynet.alerts.model.dto.DataObjectDTO;
import jakarta.annotation.PostConstruct;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

@Service
public class JsonService {

    private final String path = "src/main/resources/data.json";

    @Autowired
    private ObjectMapper objectMapper;

    private static DataObject dataObject;

    private Logger logger = LogManager.getLogger(JsonService.class);


    @PostConstruct
    protected void getData() throws IOException {
        logger.info("Get data from json file");
        dataObject = objectMapper.readValue(Paths.get(path).toFile(), DataObject.class);
    }

    public Boolean saveData(DataObject data) {
        try {
            objectMapper.writeValue(new File(path), data);
            logger.info("Save data to json file");
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


//     _____  ______ _____   _____  ____  _   _
//    |  __ \|  ____|  __ \ / ____|/ __ \| \ | |
//    | |__) | |__  | |__) | (___ | |  | |  \| |
//    |  ___/|  __| |  _  / \___ \| |  | | . ` |
//    | |    | |____| | \ \ ____) | |__| | |\  |
//    |_|    |______|_|  \_\_____/ \____/|_| \_|

    public List<Person> getPersons() {
        return dataObject.getPersons();
    }

    // TODO : regarder pour "ranger" les fonctions, genre quand ça calcule ça va dans les Services
    // PAS POSSIBLE -> savePerson utilise getPerson et dataObject est private donc on peut pas l'appeler en dehors pour avoir le searchPerson

    public Person getPerson(String firstName, String lastName) {
        List<Person> persons = getPersons();
        List<Person> foundPersons = persons.stream().filter(person -> person.getFirstName().equals(firstName) && person.getLastName().equals(lastName)).toList();

        if (foundPersons.size() > 0) {
            return foundPersons.get(0);
        } else {
            return null;
        }
    }

    public Boolean savePerson(Person person) {
        Person searchPerson = getPerson(person.getFirstName(), person.getLastName());

        if (searchPerson == null) {
            List<Person> persons = getPersons();
            persons.add(person);
            dataObject.setPersons(persons);
            return saveData(dataObject);
        } else {
            return false;
        }
    }

    public Boolean updatePerson(Person person) {
        Person foundPerson = getPerson(person.getFirstName(), person.getLastName());

        if (foundPerson != null) {
            Boolean removed = removePerson(foundPerson.getFirstName(), foundPerson.getLastName());
            if (removed) {
                savePerson(person);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public Boolean removePerson(String firstName, String lastName) {
        List<Person> persons = dataObject.getPersons();
        Person foundPerson = getPerson(firstName, lastName);
        persons.remove(foundPerson);
        dataObject.setPersons(persons);
        return saveData(dataObject);
    }

//     ______ _____ _____  ______  _____ _______    _______ _____ ____  _   _
//    |  ____|_   _|  __ \|  ____|/ ____|__   __|/\|__   __|_   _/ __ \| \ | |
//    | |__    | | | |__) | |__  | (___    | |  /  \  | |    | || |  | |  \| |
//    |  __|   | | |  _  /|  __|  \___ \   | | / /\ \ | |    | || |  | | . ` |
//    | |     _| |_| | \ \| |____ ____) |  | |/ ____ \| |   _| || |__| | |\  |
//    |_|    |_____|_|  \_\______|_____/   |_/_/    \_\_|  |_____\____/|_| \_|

    public List<Firestation> getFirestations() {
        return dataObject.getFirestations();
    }

    public Firestation getFirestation(String station, String address) {
        List<Firestation> firestations = getFirestations();
        List<Firestation> foundStations = firestations.stream().filter(firestation -> firestation.getStation().equals(station) && firestation.getAddress().equals(address)).toList();

        if (foundStations.size() > 0) {
            return foundStations.get(0);
        } else {
            return null;
        }
    }

    public List<Firestation> getFirestationsByStation(String station) {
        List<Firestation> firestations = getFirestations();
        List<Firestation> foundStations = firestations.stream().filter(firestation -> firestation.getStation().equals(station)).toList();
        return foundStations;
    }

    public List<Firestation> getFirestationsByAddress(String address) {
        List<Firestation> firestations = getFirestations();
        List<Firestation> foundStations = firestations.stream().filter(firestation -> firestation.getAddress().equals(address)).toList();
        return foundStations;
    }

    public Boolean saveFirestation(Firestation firestation) {
        Firestation searchFirestation = getFirestation(firestation.getStation(), firestation.getAddress());

        if (searchFirestation == null) {
            List<Firestation> firestations = getFirestations();
            firestations.add(firestation);
            dataObject.setFirestations(firestations);
            return saveData(dataObject);
        } else {
            return false;
        }
    }

    public Boolean updateFirestation(Firestation firestation) {
        Firestation foundFirestation = getFirestation(firestation.getStation(), firestation.getAddress());

        if (foundFirestation == null) {
            return saveFirestation(firestation);
        } else {
            return false;
        }
    }

    public Boolean removeFirestationFromStation(String station) {
        List<Firestation> foundFirestations = getFirestationsByStation(station);
        List<Firestation> firestations = getFirestations();

        for (Firestation foundFirestation : foundFirestations) {
            firestations.remove(foundFirestation);
        }

        dataObject.setFirestations(firestations);
        return saveData(dataObject);
    }

    public Boolean removeFirestationFromAddress(String address) {
        List<Firestation> foundFirestations = getFirestationsByAddress(address);
        List<Firestation> firestations = getFirestations();

        for (Firestation foundFirestation : foundFirestations) {
            firestations.remove(foundFirestation);
        }

        dataObject.setFirestations(firestations);
        return saveData(dataObject);
    }

//     __  __ ______ _____ _____ _____          _      _____  ______ _____ ____  _____  _____
//    |  \/  |  ____|  __ \_   _/ ____|   /\   | |    |  __ \|  ____/ ____/ __ \|  __ \|  __ \
//    | \  / | |__  | |  | || || |       /  \  | |    | |__) | |__ | |   | |  | | |__) | |  | |
//    | |\/| |  __| | |  | || || |      / /\ \ | |    |  _  /|  __|| |   | |  | |  _  /| |  | |
//    | |  | | |____| |__| || || |____ / ____ \| |____| | \ \| |___| |___| |__| | | \ \| |__| |
//    |_|  |_|______|_____/_____\_____/_/    \_\______|_|  \_\______\_____\____/|_|  \_\_____/

    public List<MedicalRecord> getMedicalRecords() {
        return dataObject.getMedicalRecords();
    }

    public MedicalRecord getMedicalRecord(String firstName, String lastName) {
        List<MedicalRecord> medicalRecord = getMedicalRecords();
        List<MedicalRecord> foundMedicalRecord = medicalRecord.stream().filter(mr -> mr.getFirstName().equals(firstName) && mr.getLastName().equals(lastName)).toList();

        if (foundMedicalRecord.size() > 0) {
            return foundMedicalRecord.get(0);
        } else {
            return null;
        }
    }

    public Boolean saveMedicalRecord(MedicalRecord medicalRecord) {
        MedicalRecord searchMedicalRecord = getMedicalRecord(medicalRecord.getFirstName(), medicalRecord.getLastName());

        if (searchMedicalRecord == null) {
            List<MedicalRecord> medicalRecords = getMedicalRecords();
            medicalRecords.add(medicalRecord);
            dataObject.setMedicalRecords(medicalRecords);
            return saveData(dataObject);
        } else {
            return false;
        }
    }

    public Boolean updateMedicalRecord(MedicalRecord medicalRecord) {
        MedicalRecord foundMedicalRecord = getMedicalRecord(medicalRecord.getFirstName(), medicalRecord.getLastName());

        if (foundMedicalRecord != null) {
            Boolean removed = removeMedicalRecord(medicalRecord.getFirstName(), medicalRecord.getLastName());
            if (removed) {
                saveMedicalRecord(medicalRecord);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public Boolean removeMedicalRecord(String firstName, String lastName) {
        List<MedicalRecord> medicalRecords = dataObject.getMedicalRecords();
        MedicalRecord foundMedicalRecord = getMedicalRecord(firstName, lastName);
        medicalRecords.remove(foundMedicalRecord);
        dataObject.setMedicalRecords(medicalRecords);
        return saveData(dataObject);
    }

//     _____ _______ ____
//    |  __ \__   __/ __ \
//    | |  | | | | | |  | |
//    | |  | | | | | |  | |
//    | |__| | | | | |__| |
//    |_____/  |_|  \____/

    public DataObjectDTO initDataObjectDTO() {
        DataObjectDTO dataObjectDTO = new DataObjectDTO(dataObject);
        return dataObjectDTO;
    }
}
