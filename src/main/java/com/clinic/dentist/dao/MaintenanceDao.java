package com.clinic.dentist.dao;

import com.clinic.dentist.api.dao.IMaintenanceDao;
import com.clinic.dentist.comparators.maintenances.MaintenanceAlphabetComparator;
import com.clinic.dentist.models.Dentist;
import com.clinic.dentist.models.Maintenance;
import com.clinic.dentist.repositories.MaintenanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component("maintenanceDao")
public class MaintenanceDao implements IMaintenanceDao {
    @Autowired
    private MaintenanceRepository maintenanceRepository;

    @Override
    public void save(Maintenance entity) {
        maintenanceRepository.save(entity);
    }

    public Maintenance findByName(String name) {
        return maintenanceRepository.findByName(name).orElseThrow(RuntimeException::new);
    }

    @Override
    public Maintenance findById(long id) {
        return maintenanceRepository.findById(id).orElseThrow(RuntimeException::new);

    }

    public List<Dentist> findDentistsByMaintenance(Long Id) {
        return maintenanceRepository.findById(Id).orElseThrow().getDentists();
    }

    public List<Maintenance> sortByName() {
        List<Maintenance> maintenances = getAll();
        return maintenances
                .stream()
                .sorted(new MaintenanceAlphabetComparator())
                .collect(Collectors.toList());
    }
    public boolean checkHaveThisMaintenance(Maintenance maintenance) {
        return maintenanceRepository.findByName(maintenance.getName()).isPresent();
    }
    @Override
    public List<Maintenance> getAll() {
        return maintenanceRepository.findAll();
    }
}
