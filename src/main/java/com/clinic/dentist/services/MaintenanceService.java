package com.clinic.dentist.services;

import com.clinic.dentist.api.dao.IClinicDao;
import com.clinic.dentist.api.dao.IDentistDao;
import com.clinic.dentist.api.dao.IMaintenanceDao;
import com.clinic.dentist.api.service.IMaintenanceService;
import com.clinic.dentist.comparators.maintenances.MaintenanceAlphabetComparator;
import com.clinic.dentist.comparators.maintenances.MaintenancePriceComparator;
import com.clinic.dentist.dao.AppointmentDao;
import com.clinic.dentist.dao.MaintenanceDao;
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
    @Qualifier("maintenanceDao")
    private IMaintenanceDao maintenanceDao;

    @Autowired
    @Qualifier("clinicDao")
    private IClinicDao clinicDao;
    @Autowired
    @Qualifier("dentistDao")
    private IDentistDao dentistDao;

    public Maintenance findByName(String name) {
        return maintenanceDao.findByName(name);
    }

    public List<Dentist> findDentistsByMaintenance(Long Id) {
        return maintenanceDao.findDentistsByMaintenance(Id);
    }

    public List<Dentist> findDentistsByMaintenanceAndClinic(Long MaintenanceId, Long ClinicId) {
        List<Dentist> dentistsByMaintenance = findDentistsByMaintenance(MaintenanceId);
        List<Dentist> necessaryDentists = new ArrayList<>();
        Clinic clinic = clinicDao.findById(ClinicId);
        for (Dentist dentist : dentistsByMaintenance) {
            Long i = dentist.getClinic().getId();
            if (i.equals(ClinicId)) {
                necessaryDentists.add(dentist);
            }
        }
        return necessaryDentists;
    }

    public Maintenance findById(Long id) {
        return maintenanceDao.findById(id);
    }

    public List<Maintenance> findAll() {
        return maintenanceDao.getAll();
    }
    public List<Maintenance> sorted(){
        return findAll()
                .stream()
                .sorted(new MaintenancePriceComparator())
                .collect(Collectors.toList());
    }

    public List<Maintenance> sortByName() {
        return maintenanceDao.sortByName();
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
        maintenanceDao.save(maintenance);
    }

    public boolean checkHaveThisMaintenance(Maintenance maintenance) {
        return maintenanceDao.checkHaveThisMaintenance(maintenance);
    }

    public Set<Maintenance> getSetFromArrayMaintenance(String[] array) {
        Set<Maintenance> s = new HashSet<>();
        for (String a : array) {
            Maintenance service = maintenanceDao.findByName(a);
            s.add(service);


        }
        return s;
    }

}
