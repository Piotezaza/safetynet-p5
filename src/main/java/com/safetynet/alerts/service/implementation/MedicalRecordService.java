package com.safetynet.alerts.service.implementation;

import com.safetynet.alerts.component.JsonService;
import com.safetynet.alerts.model.dao.MedicalRecord;
import com.safetynet.alerts.service.IMedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalRecordService implements IMedicalRecordService {

    @Autowired
    private JsonService jsonService;

    @Override
    public List<MedicalRecord> getMedicalRecords() {
        return jsonService.getMedicalRecords();
    }

    @Override
    public MedicalRecord getMedicalRecord(String firstName, String lastName){
        return jsonService.getMedicalRecord(firstName, lastName);
    }

    @Override
    public Boolean saveMedicalRecord(MedicalRecord medicalRecord) {
        return jsonService.saveMedicalRecord(medicalRecord);
    }

    @Override
    public Boolean updateMedicalRecord(MedicalRecord medicalRecord) {
        return jsonService.updateMedicalRecord(medicalRecord);
    }

    @Override
    public Boolean removeMedicalRecord(String firstName, String lastName){
        return jsonService.removeMedicalRecord(firstName, lastName);
    }
}
