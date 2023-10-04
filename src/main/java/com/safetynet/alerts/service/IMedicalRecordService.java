package com.safetynet.alerts.service;

import com.safetynet.alerts.model.dao.MedicalRecord;
import com.safetynet.alerts.model.dao.MedicalRecord;

import java.util.List;

public interface IMedicalRecordService {

    /**
     * Search all medical records
     * @return a list of medicalRecord
     */
    List<MedicalRecord> getMedicalRecords();

    /**
     * Search a single medicalRecord
     * @param firstName
     * @param lastName
     * @return MedicalRecord
     */
    MedicalRecord getMedicalRecord(String firstName, String lastName);

    /**
     * Save a medicalRecord
     * @param medicalRecord
     * @return true or false
     */
    Boolean saveMedicalRecord(MedicalRecord medicalRecord);

    /**
     * Change medicalRecord information except firstName and lastName
     * @param medicalRecord
     * @return MedicalRecord
     */
    Boolean updateMedicalRecord(MedicalRecord medicalRecord);

    /**
     * Delete a medicalRecord
     * @param firstName
     * @param lastName
     * @return true or false
     */
    Boolean removeMedicalRecord(String firstName, String lastName);
}
