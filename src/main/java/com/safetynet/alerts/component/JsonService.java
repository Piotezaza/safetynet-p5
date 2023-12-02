package com.safetynet.alerts.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.model.dao.DataObject;
import com.safetynet.alerts.model.dao.Firestation;
import com.safetynet.alerts.model.dao.MedicalRecord;
import com.safetynet.alerts.model.dao.Person;
import com.safetynet.alerts.model.dto.DataObjectDTO;
import jakarta.annotation.PostConstruct;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

@Service
public class JsonService {

    @Value("${cheminFichier}")
    private final String path;

    @Autowired
    private final ObjectMapper objectMapper;

    protected final DataObject dataObject;

    private Logger logger = LogManager.getLogger(JsonService.class);

    @Autowired
    public JsonService(@Value("${cheminFichier}") String cheminFichier, ObjectMapper mapperObject) throws IOException {

        this.objectMapper = mapperObject;
        this.path = cheminFichier;

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

    public Boolean savePersons(List<Person> persons) {
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

    public Boolean saveFirestations(List<Firestation> firestations) {
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

    public Boolean saveMedicalRecords(List<MedicalRecord> medicalRecords) {
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