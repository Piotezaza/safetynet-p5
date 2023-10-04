package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.dao.MedicalRecord;
import com.safetynet.alerts.service.IMedicalRecordService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MedicalRecordController {

    @Autowired
    private IMedicalRecordService medicalRecordService;

    private Logger logger = LogManager.getLogger(MedicalRecordController.class);

    @GetMapping("/medicalRecords")
    public List<MedicalRecord> getMedicalRecords() {
        logger.info("Endpoint MedicalRecord : getMedicalRecords | ️ send : nothing - return : List<MedicalRecord>");
        return medicalRecordService.getMedicalRecords();
    }

    @GetMapping("/medicalRecord/{firstName}/{lastName}")
    public MedicalRecord getMedicalRecord(@PathVariable String firstName, @PathVariable String lastName){
        logger.info("Endpoint MedicalRecord : getMedicalRecord | ️ send : String firstName \"" + firstName + "\" & String lastName \"" + lastName + "\" - return : MedicalRecord");
        return medicalRecordService.getMedicalRecord(firstName, lastName);
    }

    @PostMapping("/medicalRecord")
    public MedicalRecord createMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        logger.info("Endpoint MedicalRecord : createMedicalRecord | ️ send : MedicalRecord medicalRecord \"" + medicalRecord + "\" - return : MedicalRecord");
        medicalRecordService.saveMedicalRecord(medicalRecord);
        return medicalRecord;
    }

    @PutMapping("/medicalRecord")
    public Boolean updateMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        logger.info("Endpoint MedicalRecord : updateMedicalRecord | ️ send : MedicalRecord medicalRecord \"" + medicalRecord + "\" - return : Boolean");
        return medicalRecordService.updateMedicalRecord(medicalRecord);
    }

    @DeleteMapping("/medicalRecord/{firstName}/{lastName}")
    public Boolean removeMedicalRecord(@PathVariable String firstName, @PathVariable String lastName){
        logger.info("Endpoint MedicalRecord : removeMedicalRecordFromStation | ️ send : String firstName \"" + firstName + "\" & String lastName \"" + lastName + "\" - return : Boolean");
        return medicalRecordService.removeMedicalRecord(firstName, lastName);
    }
}