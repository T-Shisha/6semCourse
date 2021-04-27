package com.clinic.dentist.api.dao;

import com.clinic.dentist.models.Clinic;
import com.clinic.dentist.models.Dentist;
import com.clinic.dentist.models.Maintenance;

import java.util.List;

public interface IClinicDao extends GenericDao<Clinic> {
    Clinic findClinicByAddress(String address);
    Iterable<Maintenance> findMaintenancesByClinic(Long ClinicId);
    boolean checkExist(long id);
    List<Dentist> findDentistsByClinic(Long clinicId);
}
