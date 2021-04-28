package com.clinic.dentist.api.dao;

import com.clinic.dentist.models.Dentist;
import com.clinic.dentist.models.Maintenance;

import java.util.List;

public interface IDentistDao extends GenericDao<Dentist> {
    boolean checkExist(long id);

    void deleteEntity(Dentist entity);

    boolean findDentistByPhoneNumber(String phoneNumber);

    boolean checkDentistHaveMaintenance(Long id, Maintenance maintenance);

    List<Dentist> sortbyAlphabet();
}
