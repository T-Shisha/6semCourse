package com.clinic.dentist.services;

import com.clinic.dentist.models.Dentist;
import com.clinic.dentist.repositories.ClinicRepository;
import com.clinic.dentist.repositories.MaintenanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MaintenanceService {
    @Autowired
    private MaintenanceRepository maintenanceRepository;
    @Autowired
    private ClinicRepository clinicRepository;

    public Iterable<Dentist> findDentists(Long Id) {
        Iterable<Dentist> dentists = maintenanceRepository.findById(Id).orElseThrow().getDentists();
        return dentists;
    }

}
