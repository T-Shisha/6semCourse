package com.clinic.dentist.api.service;

import com.clinic.dentist.comparators.maintenances.MaintenancePriceComparator;
import com.clinic.dentist.models.Maintenance;
import com.clinic.dentist.models.TypeServices;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public interface ITypeServicesService {
    List<TypeServices> getAll();

    TypeServices findById(Long id);

    List<Maintenance> getSortedAscendingMaintenancesByPrice(TypeServices typeServices);

    List<Maintenance> getSortedDecreasingMaintenancesByPrice(TypeServices typeServices);
}
