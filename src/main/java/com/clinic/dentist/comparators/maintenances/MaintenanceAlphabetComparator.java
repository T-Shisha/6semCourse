package com.clinic.dentist.comparators.maintenances;

import com.clinic.dentist.models.Maintenance;

import java.util.Comparator;

public class MaintenanceAlphabetComparator implements Comparator<Maintenance> {
    @Override
    public int compare(Maintenance o1, Maintenance o2) {

        return o1.getName().compareTo(o2.getName());
    }
}

