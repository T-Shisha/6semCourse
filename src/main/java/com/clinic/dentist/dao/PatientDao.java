package com.clinic.dentist.dao;

import com.clinic.dentist.api.dao.IPatientDao;
import com.clinic.dentist.models.Patient;
import com.clinic.dentist.models.Role;
import com.clinic.dentist.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component("patientDao")
public class PatientDao implements IPatientDao {
    @Autowired
    private PatientRepository patientRepository;

    @Override
    public void save(Patient entity) {
        patientRepository.save(entity);
    }

    public Patient findUserByUsername(String name) {
        return patientRepository.findByUsername(name).orElseThrow(RuntimeException::new);
    }

    @Override
    public Patient findById(long id) {
        return patientRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    public List<Patient> getUnregisteredPatients() {
        List<Patient> patients = getAll();
        return patients.stream().
                filter(patient -> patient.getRoles().contains(Role.UNREGISTERED))
                .collect(Collectors.toList());
    }

    public List<Patient> getRegisteredPatients() {
        List<Patient> patients = getAll();
        return patients.stream().
                filter(patient -> patient.getRoles().contains(Role.PATIENT))
                .collect(Collectors.toList());
    }

    @Override
    public List<Patient> getAll() {
        return patientRepository.findAll();

    }
}
