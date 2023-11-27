package com.safetynet.alerts.service.implementation;

import com.safetynet.alerts.component.JsonService;
import com.safetynet.alerts.model.dao.Firestation;
import com.safetynet.alerts.model.dao.MedicalRecord;
import com.safetynet.alerts.model.dao.Person;
import com.safetynet.alerts.service.IMedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalRecordService implements IMedicalRecordService {

    @Autowired
    private JsonService jsonService;

    /**
     * Reads the list of MedicalRecords in json data file
     *
     * @return a list of all MedicalRecords
     */
    @Override
    public List<MedicalRecord> getMedicalRecords() {
        return jsonService.getMedicalRecords();
    }

    /**
     * Get MedicalRecords of a specific person
     *
     * @param firstName The firstName of the Person concerned
     * @param lastName The lastName of the Person concerned
     * @return The founded MedicalRecord
     */
    @Override
    public MedicalRecord getMedicalRecord(String firstName, String lastName){
        List<MedicalRecord> medicalRecord = jsonService.getMedicalRecords();
        List<MedicalRecord> foundMedicalRecord = medicalRecord.stream().filter(mr -> mr.getFirstName().equals(firstName) && mr.getLastName().equals(lastName)).toList();

        if (foundMedicalRecord.size() > 0) {
            return foundMedicalRecord.get(0);
        } else {
            return null;
        }
    }

    /**
     * Save a MedicalRecord
     *
     * @param medicalRecord MedicalRecord to save
     * @return True if save is ok, false if save is ko
     */
    @Override
    public Boolean saveMedicalRecord(MedicalRecord medicalRecord) throws RuntimeException {
        MedicalRecord searchMedicalRecord = getMedicalRecord(medicalRecord.getFirstName(), medicalRecord.getLastName());

        if (searchMedicalRecord == null) {
            List<MedicalRecord> medicalRecords = jsonService.getMedicalRecords();
            medicalRecords.add(medicalRecord);
            return jsonService.saveMedicalRecords(medicalRecords);
        } else {
            throw new RuntimeException("Already in database");
        }
    }

    /**
     * Update a MedicalRecord
     *
     * @param medicalRecord MedicalRecord to update
     * @return True if update is ok, false if update is ko
     */
    @Override
    public Boolean updateMedicalRecord(MedicalRecord medicalRecord) {
        List<MedicalRecord> medicalRecordList = jsonService.getMedicalRecords();

        for (MedicalRecord medicalRecord1 : medicalRecordList) {
            if (medicalRecord.getFirstName().equals(medicalRecord1.getFirstName()) && medicalRecord.getLastName().equals(medicalRecord1.getLastName())) {
                medicalRecord1.setFirstName(medicalRecord.getFirstName());
                medicalRecord1.setLastName(medicalRecord.getLastName());
                medicalRecord1.setBirthdate(medicalRecord.getBirthdate());
                medicalRecord1.setMedications(medicalRecord.getMedications());
                medicalRecord1.setAllergies(medicalRecord.getAllergies());
                break;
            }
        }
        return jsonService.saveMedicalRecords(medicalRecordList);
    }

    /**
     * Remove MedicalRecords of a specific Person
     *
     * @param firstName The firstName of the Person concerned
     * @param lastName The lastName of the Person concerned
     * @return True if remove is ok, false if remove is ko
     */
    @Override
    public Boolean removeMedicalRecord(String firstName, String lastName){
        List<MedicalRecord> medicalRecordList = jsonService.getMedicalRecords();

        Integer indice = -1;
        for(MedicalRecord medicalRecord1 : medicalRecordList) {
            indice++;
            if(firstName.equals(medicalRecord1.getFirstName()) && lastName.equals(medicalRecord1.getLastName())) {
                break;
            }
        }

        medicalRecordList.remove(indice);

        return jsonService.saveMedicalRecords(medicalRecordList);
    }
}