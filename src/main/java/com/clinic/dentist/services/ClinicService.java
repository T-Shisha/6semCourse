package com.clinic.dentist.services;

import com.clinic.dentist.api.service.IClinicServices;
import com.clinic.dentist.models.Clinic;
import com.clinic.dentist.models.Dentist;
import com.clinic.dentist.models.Maintenance;
import com.clinic.dentist.repositories.ClinicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Component("clinicService")
public class ClinicService implements IClinicServices {
    @Autowired
    private ClinicRepository clinicRepository;

    public List<Clinic> findAll() {
        return clinicRepository.findAll();
    }

    public Clinic findClinicByAddress(String address) {
        return clinicRepository.findByAddress(address).orElseThrow(RuntimeException::new);

    }

    public Iterable<Maintenance> findMaintenancesByClinic(Long ClinicId) {
        return clinicRepository.findById(ClinicId).orElseThrow().getMaintenances();
    }

    public boolean checkExist(long id) {
        return clinicRepository.existsById(id);
    }

    public Clinic findById(Long id) {
        return clinicRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    public List<Dentist> findDentistsByClinic(Long clinicId) {
        Clinic clinic = findById(clinicId);
        return clinic.getDentists();
    }
}
