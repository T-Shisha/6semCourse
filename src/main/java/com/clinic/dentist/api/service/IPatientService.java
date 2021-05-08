package com.clinic.dentist.api.service;

import com.clinic.dentist.models.Patient;
import com.clinic.dentist.models.Role;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public interface IPatientService {
    void addPatient(Patient patient);

    boolean checkPatient(String username);

    Patient findUserByUsername(String name);

    void delete(Patient patient);

    void save(Patient patient);

    Patient findUser(String login, String password);

    List<Patient> getAll();

    List<Patient> getRegisteredPatients();

    List<Patient> getUnregisteredPatients();

    Patient findById(Long id);

    void registeredPatient(Long id);

    boolean checkExist(long id);

    Patient correctData(Patient patient);
}
