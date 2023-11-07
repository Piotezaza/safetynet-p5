package com.safetynet.alerts.controller;

import com.safetynet.alerts.component.JsonService;
import com.safetynet.alerts.model.dao.Firestation;
import com.safetynet.alerts.service.IFirestationService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
public class FirestationController {

    @Autowired
    private IFirestationService firestationService;

    private Logger logger = LogManager.getLogger(FirestationController.class);

    @GetMapping("/firestations")
    public List<Firestation> getFirestations() {
        logger.info("Endpoint Firestation : getFirestations | ️ send : nothing - return : List<Firestation>");
        return firestationService.getFirestations();
    }

    @GetMapping("/firestation/{station}/{address}")
    public Firestation getFirestation(@PathVariable String station, @PathVariable String address){
        logger.info("Endpoint Firestation : getFirestation | ️ send : String station \"" + station + "\" & String address \"" + address + "\" - return : Firestation");
        return firestationService.getFirestation(station, address);
    }

    @PostMapping("/firestation")
    public Firestation createFirestation(@RequestBody @Valid Firestation firestation) {
        logger.info("Endpoint Firestation : createFirestation | ️ send : Firestation firestation \"" + firestation + "\" - return : Firestation");
        firestationService.saveFirestation(firestation);
        return firestation;
    }

    @PutMapping("/firestation")
    public Boolean updateFirestation(@RequestBody Firestation firestation) {
        logger.info("Endpoint Firestation : updateFirestation | ️ send : Firestation firestation \"" + firestation + "\" - return : Boolean");
        return firestationService.updateFirestation(firestation);
    }

    @DeleteMapping("/firestation/station/{station}")
    public Boolean removeFirestationFromStation(@PathVariable String station){
        logger.info("Endpoint Firestation : removeFirestationFromStation | ️ send : String station \"" + station + "\" - return : Boolean");
        return firestationService.removeFirestationFromStation(station);
    }

    @DeleteMapping("/firestation/address/{address}")
    public Boolean removeFirestationFromAddress(@PathVariable String address){
        logger.info("Endpoint Firestation : removeFirestationFromAddress | ️ send : String address \"" + address + "\" - return : Boolean");
        return firestationService.removeFirestationFromAddress(address);
    }
}