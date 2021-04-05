package com.clinic.dentist.services;

import com.clinic.dentist.models.Maintenance;
import com.clinic.dentist.repositories.ClinicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClinicService {
    @Autowired
    private ClinicRepository clinicRepository;

    public Iterable<Maintenance> findMaintenancesByClinic(Long ClinicId) {
        return clinicRepository.findById(ClinicId).orElseThrow().getMaintenances();
    }
}
