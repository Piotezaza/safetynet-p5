package com.safetynet.alerts.service.implementation;

import com.safetynet.alerts.component.JsonService;
import com.safetynet.alerts.controller.URLController;
import com.safetynet.alerts.model.dto.*;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class URLService {

    @Autowired
    private JsonService jsonService;
    private Logger logger = LogManager.getLogger(URLService.class);

    public FirestationURL firestation(String stationNumber) {
        DataObjectDTO dataObjectDTO = jsonService.initDataObjectDTO();
        logger.debug("DTO recuperation");

        Map<String, FirestationDTO> firestationDTOMap = dataObjectDTO.getFirestations();
        List<FirestationURLPerson> people = new ArrayList<>();
        int numberOfChildren = 0;
        int numberOfAdults = 0;

        for (Map.Entry<String, FirestationDTO> firestation : firestationDTOMap.entrySet()) {
            logger.debug("[START] of FirestationsDTO loop");
            logger.debug("Current Firestation station value = " + firestation.getValue().getStation() + ", test if equals stationNumber passed in param (" + stationNumber + ")");
            if (firestation.getValue().getStation().equals(stationNumber)) {
                logger.debug("SUCCESS : Station value = " + firestation.getValue().getStation() + " & param = " + stationNumber);
                for (CityDTO city : firestation.getValue().getCities()) {
                    logger.debug("-- [START] of Cities loop");
                    for (Map.Entry<String, HouseDTO> house : city.getHouses().entrySet()) {
                        logger.debug("---- [START] of Houses loop");
                        for (PersonContactInfoDTO person : house.getValue().getChildren()) {
                            logger.debug("------ [START] of Children loop");
                            if (person != null) {
                                FirestationURLPerson child = new FirestationURLPerson(person);
                                child.setAddress(house.getKey());
                                people.add(child);
                                numberOfChildren++;
                                logger.debug("-------- Child found (total: " + numberOfChildren + ")");
                            }
                        }
                        logger.debug("------ [END] of Children loop");
                        for (PersonContactInfoDTO person : house.getValue().getAdults()) {
                            logger.debug("------ [START] of Adults loop");
                            FirestationURLPerson adult = new FirestationURLPerson(person);
                            adult.setAddress(house.getKey());
                            people.add(adult);
                            numberOfAdults++;
                            logger.debug("-------- Adult found (total: " + numberOfAdults + ")");
                        }
                        logger.debug("------ [END] of Adults loop");
                    }
                    logger.debug("---- [END] of Houses loop");
                }
                logger.debug("-- [END] of Cities loop");
            }
            logger.debug("[END] of Firestations loop");
        }

        FirestationURL data = new FirestationURL(people, numberOfAdults, numberOfChildren);
        logger.debug("Return data to Controller");
        return data;
    }

    public Map<String, List<ChildAlertURL>> childAlert(String address) {
        DataObjectDTO dataObjectDTO = jsonService.initDataObjectDTO();
        logger.debug("DTO recuperation");
        Map<String, FirestationDTO> firestationDTOMap = dataObjectDTO.getFirestations();
        Map<String, List<ChildAlertURL>> data = new HashMap<>();
        List<ChildAlertURL> childrenAlert = new ArrayList<>();
        List<ChildAlertURL> adultsAlert = new ArrayList<>();

        for (Map.Entry<String, FirestationDTO> firestation : firestationDTOMap.entrySet()) {
            logger.debug("[START] of FirestationsDTO loop");
            for (CityDTO city : firestation.getValue().getCities()) {
                logger.debug("-- [START] of Cities loop");
                for (Map.Entry<String, HouseDTO> house : city.getHouses().entrySet()) {
                    logger.debug("---- [START] of Houses loop");
                    logger.debug("Current house address = " + house.getKey() + ", test if equals address passed in param (" + address + ")");
                    if (house.getKey().equals(address)) {
                        logger.debug("SUCCESS : Address value = " + house.getKey() + " & param = " + address);
                        List<PersonContactInfoDTO> children = house.getValue().getChildren();
                        for (PersonContactInfoDTO child : children) {
                            logger.debug("------ [START] of Children loop");
                            ChildAlertURL childAlertURL = new ChildAlertURL(child);
                            childrenAlert.add(childAlertURL);
                            logger.debug("-------- Child found");
                        }
                        logger.debug("------ [END] of Children loop");
                        List<PersonContactInfoDTO> adults = house.getValue().getAdults();
                        for (PersonContactInfoDTO adult : adults) {
                            logger.debug("------ [START] of Adults loop");
                            ChildAlertURL childAlertURL = new ChildAlertURL(adult);
                            adultsAlert.add(childAlertURL);
                            logger.debug("-------- Adult found");
                        }
                        logger.debug("------ [END] of Adults loop");
                    }
                    logger.debug("---- [END] of Houses loop");
                }
                logger.debug("-- [END] of Cities loop");
            }
            logger.debug("[END] of Firestations loop");
        }

        data.put("children", childrenAlert);
        data.put("householdMembers", adultsAlert);
        logger.debug("Return data to Controller");
        return data;
    }

    public Set<String> phoneAlert(String firestationNumber) {
        DataObjectDTO dataObjectDTO = jsonService.initDataObjectDTO();
        logger.debug("DTO recuperation");
        Map<String, FirestationDTO> firestationDTOMap = dataObjectDTO.getFirestations();
        Set<String> phones = new HashSet<>();

        for (Map.Entry<String, FirestationDTO> firestation : firestationDTOMap.entrySet()) {
            logger.debug("[START] of FirestationsDTO loop");
            logger.debug("Current firestation station = " + firestation.getValue().getStation() + ", test if equals firestationNumber passed in param (" + firestationNumber + ")");
            if (firestation.getValue().getStation().equals(firestationNumber)) {
                logger.debug("SUCCESS : Firestation station value = " + firestation.getValue().getStation() + " & param = " + firestationNumber);
                for (CityDTO city : firestation.getValue().getCities()) {
                    logger.debug("-- [START] of Cities loop");
                    for (Map.Entry<String, HouseDTO> house : city.getHouses().entrySet()) {
                        logger.debug("---- [START] of Houses loop");
                        for (PersonContactInfoDTO person : house.getValue().getAdults()) {
                            logger.debug("------ [START] of Adults loop (Adults only have phone number)");
                            if (!phones.contains(person.getPhone())) {
                                phones.add(person.getPhone());
                                logger.debug("-------- Phone number added");
                            }
                            logger.debug("------ [END] of Adults loop");
                        }
                        logger.debug("---- [END] of Houses loop");
                    }
                    logger.debug("-- [END] of Cities loop");
                }
            }
            logger.debug("[END] of Firestations loop");
        }
        logger.debug("Return data to Controller");
        return phones;
    }

    public Map<String, List<FireURL>> fire(String address) {
        DataObjectDTO dataObjectDTO = jsonService.initDataObjectDTO();
        logger.debug("DTO recuperation");
        Map<String, FirestationDTO> firestationDTOMap = dataObjectDTO.getFirestations();
        Map<String, List<FireURL>> data = new HashMap<>();
        List<FireURL> people = new ArrayList<>();
        String firestationNumber = null;

        for (Map.Entry<String, FirestationDTO> firestation : firestationDTOMap.entrySet()) {
            logger.debug("[START] of FirestationsDTO loop");
            for (CityDTO city : firestation.getValue().getCities()) {
                logger.debug("-- [START] of Cities loop");
                for (Map.Entry<String, HouseDTO> house : city.getHouses().entrySet()) {
                    logger.debug("---- [START] of Houses loop");
                    logger.debug("Current house address = " + house.getKey() + ", test if equals address passed in param (" + address + ")");
                    if (house.getKey().equals(address)) {
                        logger.debug("SUCCESS : House address value = " + house.getKey() + " & param = " + address);
                        for (PersonContactInfoDTO person : house.getValue().getAdults()) {
                            logger.debug("------ [START] of Adults loop");
                            firestationNumber = firestation.getValue().getStation();
                            people.add(new FireURL(person));
                            logger.debug("-------- Adult found");
                            logger.debug("------ [END] of Adults loop");
                        }
                        for (PersonContactInfoDTO person : house.getValue().getChildren()) {
                            logger.debug("------ [START] of Children loop");
                            if (person != null) {
                                people.add(new FireURL(person));
                                logger.debug("-------- Child found");
                            }
                            logger.debug("------ [END] of Children loop");
                        }
                    }
                    logger.debug("---- [END] of Houses loop");
                }
                logger.debug("-- [END] of Cities loop");
            }
            logger.debug("[END] of Firestations loop");
        }

        logger.debug("Return data to Controller");
        data.put(firestationNumber, people);
        return data;
    }

    public Map<String, List<FireURL>> floodStations(List<String> stations) {
        DataObjectDTO dataObjectDTO = jsonService.initDataObjectDTO();
        logger.debug("DTO recuperation");
        Map<String, FirestationDTO> firestationDTOMap = dataObjectDTO.getFirestations();
        Map<String, List<FireURL>> data = new HashMap<>();

        for (Map.Entry<String, FirestationDTO> firestation : firestationDTOMap.entrySet()) {
            logger.debug("[START] of FirestationsDTO loop");
            logger.debug("Current firestation station = " + firestation.getValue().getStation() + " test if it is contained in stations passed in param (" + stations + ")");
            if (stations.contains(firestation.getValue().getStation())) {
                logger.debug("SUCCESS : Firestation station value = " + firestation.getValue().getStation() + " & param = " + stations);
                for (CityDTO city : firestation.getValue().getCities()) {
                    logger.debug("-- [START] of Cities loop");
                    for (Map.Entry<String, HouseDTO> house : city.getHouses().entrySet()) {
                        logger.debug("---- [START] of Houses loop");
                        List<FireURL> people = new ArrayList<>();
                        for (PersonContactInfoDTO person : house.getValue().getAdults()) {
                            logger.debug("------ [START] of Adults loop");
                            people.add(new FireURL(person));
                            logger.debug("-------- Adult found");
                            logger.debug("------ [END] of Adults loop");
                        }
                        for (PersonContactInfoDTO person : house.getValue().getChildren()) {
                            logger.debug("------ [START] of Children loop");
                            if (person != null) {
                                people.add(new FireURL(person));
                                logger.debug("-------- Child found");
                            }
                            logger.debug("------ [END] of Children loop");
                        }
                        data.put(house.getKey(), people);
                        logger.debug("---- [END] of Houses loop");
                    }
                    logger.debug("-- [END] of Cities loop");
                }
            }
            logger.debug("[END] of Firestations loop");
        }

        logger.debug("Return data to Controller");
        return data;
    }

    public List<PersonInfoURL> personInfo(String firstName, String lastName) {
        DataObjectDTO dataObjectDTO = jsonService.initDataObjectDTO();
        logger.debug("DTO recuperation");
        Map<String, FirestationDTO> firestationDTOMap = dataObjectDTO.getFirestations();
        List<PersonInfoURL> data = new ArrayList<>();

        for (Map.Entry<String, FirestationDTO> firestation : firestationDTOMap.entrySet()) {
            logger.debug("[START] of FirestationsDTO loop");
            for (CityDTO city : firestation.getValue().getCities()) {
                logger.debug("-- [START] of Cities loop");
                for (Map.Entry<String, HouseDTO> house : city.getHouses().entrySet()) {
                    logger.debug("---- [START] of Houses loop");
                    for (PersonContactInfoDTO person : house.getValue().getAdults()) {
                        logger.debug("------ [START] of Adults loop");
                        logger.debug("Current adult lastName = " + person.getLastName() + " test if equals lastname passed in param (" + lastName + ")");
                        if (person.getLastName().equals(lastName)) {
                            logger.debug("SUCCESS : adult lastName value = " + person.getLastName() + " & param = " + lastName);
                            PersonInfoURL personInfo = new PersonInfoURL(person);
                            personInfo.setAddress(house.getKey());
                            data.add(personInfo);
                            logger.debug("-------- Adult found");
                        }
                        logger.debug("------ [END] of Adults loop");
                    }
                    for (PersonContactInfoDTO person : house.getValue().getChildren()) {
                        logger.debug("------ [START] of Children loop");
                        if (person.getLastName().equals(lastName)) {
                            logger.debug("SUCCESS : child lastName value = " + person.getLastName() + " & param = " + lastName);
                            PersonInfoURL personInfo = new PersonInfoURL(person);
                            personInfo.setAddress(house.getKey());
                            data.add(personInfo);
                            logger.debug("-------- Child found");
                        }
                        logger.debug("------ [END] of Children loop");
                    }
                    logger.debug("---- [END] of Houses loop");
                }
                logger.debug("-- [END] of Cities loop");
            }
            logger.debug("[END] of Firestations loop");
        }

        logger.debug("Return data to Controller");
        return data;
    }

    public Set<String> communityEmail(String cityName) {
        DataObjectDTO dataObjectDTO = jsonService.initDataObjectDTO();
        logger.debug("DTO recuperation");
        Map<String, FirestationDTO> firestationDTOMap = dataObjectDTO.getFirestations();
        Set<String> data = new HashSet<>();

        for (Map.Entry<String, FirestationDTO> firestation : firestationDTOMap.entrySet()) {
            logger.debug("[START] of FirestationsDTO loop");
            for (CityDTO city : firestation.getValue().getCities()) {
                logger.debug("-- [START] of Cities loop");
                logger.debug("Current city name = " + city.getCityName() + " test if equals cityName passed in param (" + cityName + ")");
                if (city.getCityName().equals(cityName)) {
                    logger.debug("SUCCESS : city name value = " + city.getCityName() + " & param = " + cityName);
                    for (Map.Entry<String, HouseDTO> house : city.getHouses().entrySet()) {
                        logger.debug("---- [START] of Houses loop");
                        for (PersonContactInfoDTO person : house.getValue().getAdults()) {
                            logger.debug("------ [START] of Adults loop");
                            if (!data.contains(person.getEmail())) {
                                data.add(person.getEmail());
                                logger.debug("-------- Adult found");
                            }
                            logger.debug("------ [END] of Adults loop");
                        }
                        for (PersonContactInfoDTO person : house.getValue().getChildren()) {
                            logger.debug("------ [START] of Children loop");
                            if (person != null && !data.contains(person.getEmail())) {
                                data.add(person.getEmail());
                                logger.debug("-------- Child found");
                            }
                            logger.debug("------ [END] of Children loop");
                        }
                        logger.debug("---- [END] of Houses loop");
                    }
                }
                logger.debug("-- [END] of Cities loop");
            }
            logger.debug("[END] of Firestations loop");
        }
        logger.debug("Return data to Controller");
        return data;
    }
}