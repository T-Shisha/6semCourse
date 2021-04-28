package com.clinic.dentist.services;

import com.clinic.dentist.api.dao.IClinicDao;
import com.clinic.dentist.api.dao.ITypeServicesDao;
import com.clinic.dentist.api.service.ITypeServicesService;
import com.clinic.dentist.comparators.maintenances.MaintenancePriceComparator;
import com.clinic.dentist.models.Maintenance;
import com.clinic.dentist.models.TypeServices;
import com.clinic.dentist.repositories.TypeServicesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Component("typeServicesService")
public class TypeServicesService implements ITypeServicesService {
    @Autowired
    @Qualifier("typeServicesDao")
    private ITypeServicesDao typeServicesDao;

    public List<TypeServices> getAll() {
        return typeServicesDao.getAll();
    }

    public TypeServices findById(Long id) {
        return typeServicesDao.findById(id);
    }

    public List<Maintenance> getSortedAscendingMaintenancesByPrice(TypeServices typeServices) {
        return typeServicesDao.getSortedAscendingMaintenancesByPrice(typeServices);
    }

    public List<Maintenance> getSortedDecreasingMaintenancesByPrice(TypeServices typeServices) {

        return typeServicesDao.getSortedDecreasingMaintenancesByPrice(typeServices);

    }
}
