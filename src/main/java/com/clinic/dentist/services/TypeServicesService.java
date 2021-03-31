package com.clinic.dentist.services;

import com.clinic.dentist.comparators.maintenances.MaintenancePriceComparator;
import com.clinic.dentist.models.Maintenance;
import com.clinic.dentist.models.TypeServices;
import com.clinic.dentist.repositories.TypeServicesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TypeServicesService {
    @Autowired
    private TypeServicesRepository typeServicesRepository;

    public List<TypeServices> getAll() {
        return typeServicesRepository.findAll();
    }

    public List<Maintenance> getSortedAscendingMaintenancesByPrice(TypeServices typeServices) {
        List<Maintenance> maintenancesByTypeServices = typeServices.getMaintenances().stream().collect(Collectors.toList());
        return maintenancesByTypeServices
                .stream()
                .sorted(new MaintenancePriceComparator())
                .collect(Collectors.toList());
    }

    public List<Maintenance> getSortedDecreasingMaintenancesByPrice(TypeServices typeServices) {
        List<Maintenance> maintenancesByTypeServices = getSortedAscendingMaintenancesByPrice(typeServices);
        Collections.reverse(maintenancesByTypeServices);
        return maintenancesByTypeServices;

    }
}
