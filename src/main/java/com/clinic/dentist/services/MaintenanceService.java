package com.clinic.dentist.services;

import com.clinic.dentist.api.dao.IClinicDao;
import com.clinic.dentist.api.dao.IDentistDao;
import com.clinic.dentist.api.service.IMaintenanceService;
import com.clinic.dentist.comparators.maintenances.MaintenanceAlphabetComparator;
import com.clinic.dentist.comparators.maintenances.MaintenancePriceComparator;
import com.clinic.dentist.models.Clinic;
import com.clinic.dentist.models.Dentist;
import com.clinic.dentist.models.Maintenance;
import com.clinic.dentist.repositories.ClinicRepository;
import com.clinic.dentist.repositories.MaintenanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Component("maintenanceService")
public class MaintenanceService implements IMaintenanceService {
    @Autowired
    private MaintenanceRepository maintenanceRepository;
    @Autowired
    private ClinicRepository clinicRepository;
    @Autowired
    @Qualifier("clinicDao")
    private IClinicDao clinicDao;
    @Autowired
    @Qualifier("dentistDao")
    private IDentistDao dentistDao;

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
            Long i = dentist.getClinic().getId();
            if (i.equals(ClinicId)) {
                necessaryDentists.add(dentist);
            }
        }
        return necessaryDentists;
    }

    public Maintenance findById(Long id) {
        return maintenanceRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    public List<Maintenance> findAll() {
        return maintenanceRepository.findAll();
    }

    public List<Maintenance> sortByName() {
        List<Maintenance> maintenances = findAll();
        return maintenances
                .stream()
                .sorted(new MaintenanceAlphabetComparator())
                .collect(Collectors.toList());
    }

    public List<Maintenance> findMaintenanceForAddingForDentist(Long dentistId, Long clinicId) {
        Iterable<Maintenance> clinicMaintenances = clinicDao.findMaintenancesByClinic(clinicId);
        List<Maintenance> necessaryList = new ArrayList<>();
        for (Maintenance maintenance : clinicMaintenances) {
            if (!dentistDao.checkDentistHaveMaintenance(dentistId, maintenance)) {
                necessaryList.add(maintenance);
            }
        }
        return necessaryList;
    }

    public void addMaintenance(Maintenance maintenance) {
        maintenanceRepository.save(maintenance);
    }

    public boolean checkHaveThisMaintenance(Maintenance maintenance) {
        return maintenanceRepository.findByName(maintenance.getName()).isPresent();
    }

    public Set<Maintenance> getSetFromArrayMaintenance(String[] array) {
        Set<Maintenance> s = new HashSet<>();
        for (String a : array) {
            Maintenance service = maintenanceRepository.findByName(a).orElseThrow(RuntimeException::new);
            s.add(service);


        }
        return s;
    }

}
