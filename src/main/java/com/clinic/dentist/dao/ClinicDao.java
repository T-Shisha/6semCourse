package com.clinic.dentist.dao;

import com.clinic.dentist.api.dao.IClinicDao;
import com.clinic.dentist.models.Clinic;
import com.clinic.dentist.models.Dentist;
import com.clinic.dentist.models.Maintenance;
import com.clinic.dentist.repositories.ClinicRepository;
import com.clinic.dentist.repositories.DentistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("clinicDao")
public class ClinicDao implements IClinicDao {
    @Autowired
    private ClinicRepository clinicRepository;
    @Autowired
    private DentistRepository dentistRepository;

    @Override
    public void save(Clinic entity) {

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

    @Override
    public Clinic findById(long id) {
        return clinicRepository.findById(id).orElseThrow(RuntimeException::new);

    }

    public List<Dentist> findDentistsByClinic(Long clinicId) {
        Clinic clinic = findById(clinicId);
        return dentistRepository.findDentistByClinic(clinic);
    }

    @Override
    public List<Clinic> getAll() {
        List<Clinic> clinics = clinicRepository.findAll();
        List<Clinic> list = new ArrayList<>();
        long id=5;
        for (Clinic clinic : clinics) {
            if (!clinic.getId().equals(id)) {
                list.add(clinic);
            }
        }
        return list;
    }
}
