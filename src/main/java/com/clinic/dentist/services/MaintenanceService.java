package com.clinic.dentist.services;

import com.clinic.dentist.comparators.maintenances.MaintenanceAlphabetComparator;
import com.clinic.dentist.comparators.maintenances.MaintenancePriceComparator;
import com.clinic.dentist.models.Clinic;
import com.clinic.dentist.models.Dentist;
import com.clinic.dentist.models.Maintenance;
import com.clinic.dentist.repositories.ClinicRepository;
import com.clinic.dentist.repositories.MaintenanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MaintenanceService {
    @Autowired
    private MaintenanceRepository maintenanceRepository;
    @Autowired
    private ClinicRepository clinicRepository;

    public Maintenance findByName(String name) {
        return maintenanceRepository.findByName(name).orElseThrow(RuntimeException::new);
    }

    public List<Dentist> findDentistsByMaintenance(Long Id) {
        List<Dentist> dentists = maintenanceRepository.findById(Id).orElseThrow().getDentists();
        return dentists;
    }

    public List<Dentist> findDentistsByMaintenanceAndClinic(Long MaintenanceId, Long ClinicId) {
        List<Dentist> dentistsByMaintenance = findDentistsByMaintenance(MaintenanceId);
        List<Dentist> necessaryDentists = new ArrayList<>();
        Clinic clinic = clinicRepository.findById(ClinicId).orElseThrow();
        for (Dentist dentist : dentistsByMaintenance) {
            Long i=dentist.getClinic().getId();
            if (i.equals(ClinicId)) {
                necessaryDentists.add(dentist);
            }
        }
        return necessaryDentists;
    }

    public Maintenance findById(Long id) {
        return maintenanceRepository.findById(id).orElseThrow(RuntimeException::new);
    }
    public List<Maintenance> findAll(){
        return maintenanceRepository.findAll();
    }
    public List<Maintenance> sortByName(){
        List<Maintenance> maintenances=findAll();
        return maintenances
                .stream()
                .sorted(new MaintenanceAlphabetComparator())
                .collect(Collectors.toList());
    }

}
