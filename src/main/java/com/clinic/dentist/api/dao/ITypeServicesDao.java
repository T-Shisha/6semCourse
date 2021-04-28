package com.clinic.dentist.api.dao;

import com.clinic.dentist.models.Admin;
import com.clinic.dentist.models.Maintenance;
import com.clinic.dentist.models.TypeServices;

import java.util.List;

public interface ITypeServicesDao extends GenericDao<TypeServices> {
    List<Maintenance> getSortedAscendingMaintenancesByPrice(TypeServices typeServices);
    List<Maintenance> getSortedDecreasingMaintenancesByPrice(TypeServices typeServices);
}
