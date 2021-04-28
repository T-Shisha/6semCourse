package com.clinic.dentist.services;

import com.clinic.dentist.api.dao.IMaintenanceDao;
import com.clinic.dentist.api.dao.IPatientDao;
import com.clinic.dentist.api.service.IPatientService;
import com.clinic.dentist.models.Patient;
import com.clinic.dentist.models.Role;
import com.clinic.dentist.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Component("patientService")
public class PatientService implements IPatientService {
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    @Qualifier("patientDao")
    private IPatientDao patientDao;

    public void addPatient(Patient patient) {
        patient.setActive(true);
        patient.setRoles(Collections.singleton(Role.UNREGISTERED));
        patient.setPassword(bCryptPasswordEncoder.encode(patient.getPassword()));
        patientDao.save(patient);
    }

    public boolean checkPatient(Patient patient) {
        try {
            findUserByUsername(patient.getUsername());
            return true;
        } catch (RuntimeException ex) {
            return false;
//}
//        if (patientRepository.findByUsername(patient.getUsername()).isPresent()) {
//            return true;
//        }
//        return false;

        }
    }

        public Patient findUserByUsername(String name){
            return patientDao.findUserByUsername(name);
        }

        public Patient correctData (Patient patient){
            patient.setFirstName(patient.getFirstName().trim());
            patient.setLastName(patient.getLastName().trim());
            patient.setUsername(patient.getUsername().trim());
            return patient;
        }

        public Patient findUser (String login, String password){
            try {
                password = bCryptPasswordEncoder.encode(password);
                Patient patient = patientDao.findUserByUsername(login);
                patient.getPassword();
                if (patient.getPassword().equals(password)) {
                    return patient;
                } else {
                    throw new RuntimeException("Invalid password");
                }
            }
            catch (RuntimeException ex){
                ex.printStackTrace();
                throw ex;
              }
         }

        public List<Patient> getAll () {
            return patientDao.getAll();
        }

        public List<Patient> getRegisteredPatients () {
             return patientDao.getRegisteredPatients();
        }

        public List<Patient> getUnregisteredPatients () {
            return patientDao.getUnregisteredPatients();
        }

        public Patient findById (Long id){
            return patientDao.findById(id);
        }

        public void registeredPatient (Long id){
            Patient patient = findById(id);
            Set<Role> roles = new HashSet<>();
            roles.add(Role.PATIENT);
            patient.setRoles(roles);

            patientDao.save(patient);

        }
    }
