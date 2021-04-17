package com.clinic.dentist.services;

import com.clinic.dentist.models.Patient;
import com.clinic.dentist.models.Role;
import com.clinic.dentist.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientService {
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private PatientRepository patientRepository;

    public void addPatient(Patient patient) {
        patient.setActive(true);
        patient.setRoles(Collections.singleton(Role.UNREGISTERED));
        patient.setPassword(bCryptPasswordEncoder.encode(patient.getPassword()));
        patientRepository.save(patient);
    }

    public boolean checkPatient(Patient patient) {

        if (patientRepository.findByUsername(patient.getUsername()).isPresent()) {
            return true;
        }
        return false;

    }

    public Patient findUserByUsername(String name) {
        return patientRepository.findByUsername(name).orElseThrow(RuntimeException::new);
    }

    public Patient correctData(Patient patient) {
        patient.setFirstName(patient.getFirstName().trim());
        patient.setLastName(patient.getLastName().trim());
        patient.setUsername(patient.getUsername().trim());
        return patient;
    }

    public Patient findUser(String login, String password) {
        password = bCryptPasswordEncoder.encode(password);
        Patient patient = patientRepository.findByUsername(login).orElseThrow(RuntimeException::new);
        patient.getPassword();
        if (patient.getPassword().equals(password)) {
            return patient;
        } else {
            throw new RuntimeException("Invalid password");
        }
    }

    public List<Patient> getAll() {
        return patientRepository.findAll();
    }
    public List<Patient> getRegisteredPatients(){
        List<Patient> patients = getAll();
        return patients.stream().
                filter(patient -> patient.getRoles().contains(Role.PATIENT))
                .collect(Collectors.toList());
    }
    public List<Patient> getUnregisteredPatients(){
        List<Patient> patients = getAll();
        return patients.stream().
                filter(patient -> patient.getRoles().equals(Role.UNREGISTERED))
                .collect(Collectors.toList());
    }
}
