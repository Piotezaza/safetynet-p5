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

    @Override
    public List<Firestation> getFirestations() {
        return jsonService.getFirestations();
    }

    @Override
    public Firestation getFirestation(String station, String address){
        return jsonService.getFirestation(station, address);
    }

    @Override
    public Boolean saveFirestation(Firestation firestation) {
        return jsonService.saveFirestation(firestation);
    }

    @Override
    public Boolean updateFirestation(Firestation firestation) {
        return jsonService.updateFirestation(firestation);
    }

    @Override
    public Boolean removeFirestationFromStation(String station){
        return jsonService.removeFirestationFromStation(station);
    }

    @Override
    public Boolean removeFirestationFromAddress(String address){
        return jsonService.removeFirestationFromAddress(address);
    }
}
