package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.dto.*;
import com.safetynet.alerts.service.implementation.URLService;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
public class URLController {
    @Autowired
    private URLService urlService;

    private Logger logger = LogManager.getLogger(URLController.class);

    @GetMapping("/childAlert")
    public Map<String, List<ChildAlertURL>> childAlert(@RequestParam String address) {
        logger.info("URL ChildAlert | ️ send : String address \"" + address + "\" - return : Map<String, List<ChildAlertURL>>");
        return urlService.childAlert(address);
    }

    @GetMapping("/firestation")
    public FirestationURL firestation(@RequestParam String stationNumber) {
        logger.info("URL firestation | ️ send : String stationNumber \"" + stationNumber + "\" - return : FirestationURL");
        return urlService.firestation(stationNumber);
    }

    @GetMapping("/phoneAlert")
    public Set<String> phoneAlert(@RequestParam String firestation){
        logger.info("URL phoneAlert | ️ send : String firestation \"" + firestation + "\" - return : Set<String>");
        return urlService.phoneAlert(firestation);
    }

    @GetMapping("/fire")
    public Map<String, List<FireURL>> fire(@RequestParam String address){
        logger.info("URL fire | ️ send : String address \"" + address + "\" - return : Map<String, List<FireURL>>");
        return urlService.fire(address);
    }

    @GetMapping("/flood/stations")
    public Map<String, List<FireURL>> stations(@RequestParam List<String> stations){
        logger.info("URL flood/stations | ️ send : String stations \"" + stations + "\" - return : Map<String, List<FireURL>>");
        return urlService.floodStations(stations);
    }

    @GetMapping("/personInfo")
    public List<PersonInfoURL> personInfo(@RequestParam String firstName, @RequestParam String lastName){
        logger.info("URL personInfo | ️ send : String firstName \"" + firstName + "\" String lastName \"" + lastName + "\" - return : List<PersonInfoURL>");
        return urlService.personInfo(firstName, lastName);
    }

    @GetMapping("/communityEmail")
    public Set<String> communityEmail(@RequestParam String city){
        logger.info("URL communityEmail | ️ send : String city \"" + city + "\" - return : Set<String>");
        return urlService.communityEmail(city);
    }
}