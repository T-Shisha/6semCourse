package com.clinic.dentist.api.service;

import com.clinic.dentist.comparators.maintenances.MaintenanceAlphabetComparator;
import com.clinic.dentist.models.Clinic;
import com.clinic.dentist.models.Dentist;
import com.clinic.dentist.models.Maintenance;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public interface IMaintenanceService {
    Maintenance findByName(String name);

    List<Dentist> findDentistsByMaintenance(Long Id);

    List<Dentist> findDentistsByMaintenanceAndClinic(Long MaintenanceId, Long ClinicId);

    Maintenance findById(Long id);

    List<Maintenance> findAll();

    List<Maintenance> sortByName();

    List<Maintenance> findMaintenanceForAddingForDentist(Long dentistId, Long clinicId);

    void addMaintenance(Maintenance maintenance);

    boolean checkHaveThisMaintenance(Maintenance maintenance);

    Set<Maintenance> getSetFromArrayMaintenance(String[] array);
}
