package com.clinic.dentist.dao;

import com.clinic.dentist.api.dao.ITypeServicesDao;
import com.clinic.dentist.comparators.maintenances.MaintenancePriceComparator;
import com.clinic.dentist.models.Maintenance;
import com.clinic.dentist.models.TypeServices;
import com.clinic.dentist.repositories.TypeServicesRepository;
import com.clinic.dentist.services.TypeServicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component("typeServicesDao")
public class TypeServicesDao implements ITypeServicesDao {
    @Autowired
    private TypeServicesRepository typeServicesRepository;

    @Override
    public void save(TypeServices entity) {

    }
    public List<Maintenance> getSortedDecreasingMaintenancesByPrice(TypeServices typeServices) {
        List<Maintenance> maintenancesByTypeServices = getSortedAscendingMaintenancesByPrice(typeServices);
        Collections.reverse(maintenancesByTypeServices);
        return maintenancesByTypeServices;

    }
    public List<Maintenance> getSortedAscendingMaintenancesByPrice(TypeServices typeServices) {
        List<Maintenance> maintenancesByTypeServices = typeServices.getMaintenances().stream().collect(Collectors.toList());
        return maintenancesByTypeServices
                .stream()
                .sorted(new MaintenancePriceComparator())
                .collect(Collectors.toList());
    }

    @Override
    public TypeServices findById(long id) {
        return typeServicesRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @Override
    public List<TypeServices> getAll() {
        return typeServicesRepository.findAll();
    }
}
