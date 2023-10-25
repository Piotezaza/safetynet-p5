package com.safetynet.alerts.service.implementation;

import com.safetynet.alerts.component.JsonService;
import com.safetynet.alerts.model.dao.Firestation;
import com.safetynet.alerts.service.IFirestationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FirestationService implements IFirestationService {

    @Autowired
    private JsonService jsonService;

    /**
     * Reads the list of firestations in json data file
     *
     * @return a list of all firestations
     */
    @Override
    public List<Firestation> getFirestations() {
        return jsonService.getFirestations();
    }

    /**
     * Get specific Firestation
     *
     * @param station The station concerned
     * @param address The address concerned
     * @return The founded Firestation
     */
    @Override
    public Firestation getFirestation(String station, String address) {
        List<Firestation> firestations = jsonService.getFirestations();
        List<Firestation> foundStations = firestations.stream().filter(firestation -> firestation.getStation().equals(station) && firestation.getAddress().equals(address)).toList();

        if (foundStations.size() > 0) {
            return foundStations.get(0);
        } else {
            return null;
        }
    }

    /**
     * Get specific Firestation by Station
     *
     * @param station
     * @return All Firestations of the Station
     */
    private List<Firestation> getFirestationsByStation(String station) {
        List<Firestation> firestations = jsonService.getFirestations();
        List<Firestation> foundStations = firestations.stream().filter(firestation -> firestation.getStation().equals(station)).toList();
        return foundStations;
    }

    /**
     * Get specific Firestation by Address
     *
     * @param address
     * @return All Firestations of the Address
     */
    private List<Firestation> getFirestationsByAddress(String address) {
        List<Firestation> firestations = jsonService.getFirestations();
        List<Firestation> foundStations = firestations.stream().filter(firestation -> firestation.getAddress().equals(address)).toList();
        return foundStations;
    }

    /**
     * Save a Firestation
     *
     * @param firestation
     * @return True if save is ok, false if save is ko
     */
    @Override
    public Boolean saveFirestation(Firestation firestation) {
        Firestation searchFirestation = getFirestation(firestation.getStation(), firestation.getAddress());

        if (searchFirestation == null) {
            List<Firestation> firestations = jsonService.getFirestations();
            firestations.add(firestation);
            return jsonService.saveFirestations(firestations);
        } else {
            return false;
        }
    }

    /**
     * Update an address station number
     *
     * @param firestation
     * @return True if the update is ok, false if the update is ko
     */
    @Override
    public Boolean updateFirestation(Firestation firestation) {
        List<Firestation> firestationList = jsonService.getFirestations();

        for (Firestation firestation1 : firestationList) {
            if (firestation.getAddress().equals(firestation1.getAddress())) {
                firestation1.setStation(firestation.getStation());
                break;
            }
        }
        return jsonService.saveFirestations(firestationList);
    }

    /**
     * Remove all Firestations of a Station
     *
     * @param station
     * @return True if the remove is ok, false if the remove is ko
     */
    @Override
    public Boolean removeFirestationFromStation(String station) {
        List<Firestation> firestationList = jsonService.getFirestations();

        List<Firestation> firestationsToDelete = getFirestationsByStation(station);

        for (int i = 0; i < firestationList.size(); i++) {
            for (Firestation firestationToDelete : firestationsToDelete) {
                if (firestationList.get(i).equals(firestationToDelete)) {
                    firestationList.remove(firestationToDelete);
                }
            }
        }

        return jsonService.saveFirestations(firestationList);
    }

    /**
     * Remove all Firestations of an Address
     *
     * @param address
     * @return True if the remove is ok, false if the remove is ko
     */
    @Override
    public Boolean removeFirestationFromAddress(String address) {
        List<Firestation> firestationList = jsonService.getFirestations();

        List<Firestation> firestationsToDelete = getFirestationsByAddress(address);

        for (int i = 0; i < firestationList.size(); i++) {
            for (Firestation firestationToDelete : firestationsToDelete) {
                if (firestationList.get(i).equals(firestationToDelete)) {
                    firestationList.remove(firestationToDelete);
                }
            }
        }

        return jsonService.saveFirestations(firestationList);
    }
}