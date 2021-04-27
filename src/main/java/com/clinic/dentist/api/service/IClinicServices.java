package com.clinic.dentist.api.service;

import com.clinic.dentist.models.Clinic;
import com.clinic.dentist.models.Dentist;
import com.clinic.dentist.models.Maintenance;

import java.util.List;

public interface IClinicServices {
    List<Clinic> findAll();

    Clinic findClinicByAddress(String address);

    Iterable<Maintenance> findMaintenancesByClinic(Long ClinicId);

    boolean checkExist(long id);

    Clinic findById(Long id);

    List<Dentist> findDentistsByClinic(Long clinicId);
}
