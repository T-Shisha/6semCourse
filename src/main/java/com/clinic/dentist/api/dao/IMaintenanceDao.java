package com.clinic.dentist.api.dao;


import com.clinic.dentist.models.Dentist;
import com.clinic.dentist.models.Maintenance;

import java.util.List;

public interface IMaintenanceDao extends GenericDao<Maintenance> {
    Maintenance findByName(String name);
    List<Dentist> findDentistsByMaintenance(Long Id);
    List<Maintenance> sortByName();
    boolean checkHaveThisMaintenance(Maintenance maintenance);
}
