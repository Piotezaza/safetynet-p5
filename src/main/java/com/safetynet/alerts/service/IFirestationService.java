package com.safetynet.alerts.service;

import com.safetynet.alerts.model.dao.Firestation;

import java.util.List;

public interface IFirestationService {

    /**
     * Search all firestations
     * @return a list of firestations
     */
    List<Firestation> getFirestations();

    /**
     * Get all firestations
     * @param station
     * @param address
     * @return
     */
    Firestation getFirestation(String station, String address);

    /**
     * Save a firestation
     * @param firestation
     * @return true or false
     */
    Boolean saveFirestation(Firestation firestation);

    /**
     * Update a firestation
     * @param firestation
     * @return true or false
     */
    Boolean updateFirestation(Firestation firestation);

    /**
     * Delete a firestation
     * @param station
     * @return true or false
     */
    Boolean removeFirestationFromStation(String station);

    /**
     * Delete a firestation
     * @param address
     * @return true or false
     */
    Boolean removeFirestationFromAddress(String address);
}
