package com.safetynet.alerts.model.dto;

import com.safetynet.alerts.model.constants.Constants;
import com.safetynet.alerts.model.dao.DataObject;
import com.safetynet.alerts.model.dao.Firestation;
import com.safetynet.alerts.model.dao.MedicalRecord;
import com.safetynet.alerts.model.dao.Person;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Setter
@Getter
public class DataObjectDTO {
    private Map<String, FirestationDTO> firestations;

    public DataObjectDTO(DataObject dataObject) {
        HashMap<String, FirestationDTO> firestations = new HashMap<>();

        // Firestations
        for (Firestation firestation : dataObject.getFirestations()) {
            FirestationDTO firestationDTO;
            // Si on n'a pas encore la firestation, on la crée et on la met dans la map
            if (!firestations.containsKey(firestation.getStation())) {
                // Création de la firestation
                firestationDTO = new FirestationDTO(firestation.getStation());
                // Ajout dans la map
                firestations.put(firestation.getStation(), firestationDTO);
            }
            // Récupère la firestation soit une existante, soit celle qu'on vient de créer
            firestationDTO = firestations.get(firestation.getStation());
            // Limite de l'application parce qu'il y a que cette ville. Si demain on veut rajouter des villes il faudra faire évoluer
            // Ça marche uniquement si on a une seule ville
            CityDTO cityDTO;
            if(firestationDTO.getCities().size() > 0) {
                cityDTO = firestationDTO.getCities().get(0);
            } else {
                cityDTO = new CityDTO(Constants.CITY, Constants.ZIP);
                firestationDTO.getCities().add(cityDTO);
            }
            // Si la maison n'existe pas dans la ville
            if (cityDTO.getHouses().get(firestation.getAddress()) == null) {
                // On ajoute la nouvelle maison
                cityDTO.getHouses().put(firestation.getAddress(), new HouseDTO());
            }
        }

        // Persons
        for (Person person : dataObject.getPersons()) {
            boolean personFound = false;
            for (Map.Entry<String, FirestationDTO> firestation : firestations.entrySet()){
                for(Map.Entry<String, HouseDTO> houseDTO : firestation.getValue().getCities().get(0).getHouses().entrySet()) {
                    if(houseDTO.getKey().equals(person.getAddress())) {
                        houseDTO.getValue().getAdults().add(new PersonContactInfoDTO(person));
                        personFound = true;
                        break;
                    }
                }
                if(personFound) {
                    break;
                }
            }
        }

        for (MedicalRecord medicalRecord : dataObject.getMedicalRecords()){
            boolean personFound = false;
            for (Map.Entry<String, FirestationDTO> firestation : firestations.entrySet()){
                for(Map.Entry<String, HouseDTO> houseDTO : firestation.getValue().getCities().get(0).getHouses().entrySet()) {
                    for(PersonContactInfoDTO person : houseDTO.getValue().getAdults()) {
                        if(person.getLastName().equals(medicalRecord.getLastName()) && person.getFirstName().equals(medicalRecord.getFirstName())) {
                            person.setAllergies(medicalRecord.getAllergies());
                            person.setMedications(medicalRecord.getMedications());
                            person.setBirthdate(medicalRecord.getBirthdate());

                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                            LocalDate birthdate = LocalDate.parse(medicalRecord.getBirthdate(), formatter);
                            int age = Period.between(birthdate, LocalDate.now()).getYears();

                            person.setAge(age);

                            if(age <= 18){
                                person.setPhone(null);
                                houseDTO.getValue().getChildren().add(person); // TODO : cohérence avec PersonContactInfoDTO et PersonDTO ???
                                houseDTO.getValue().getAdults().remove(person);
                            }
                            personFound = true;
                            break;
                        }
                    }
                    if(personFound) {
                        break;
                    }
                }
                if(personFound) {
                    break;
                }
            }
        }

        this.setFirestations(firestations);
    }
}