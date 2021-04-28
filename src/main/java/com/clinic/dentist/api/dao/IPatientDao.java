package com.clinic.dentist.api.dao;


import com.clinic.dentist.models.Patient;

import java.util.List;

public interface IPatientDao extends GenericDao<Patient> {
    Patient findUserByUsername(String name);

    List<Patient> getRegisteredPatients();

    List<Patient> getUnregisteredPatients();
}
