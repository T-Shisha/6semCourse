package com.clinic.dentist.comparators.maintenances;

import com.clinic.dentist.models.Maintenance;

import java.util.Comparator;

public class MaintenancePriceComparator implements Comparator<Maintenance> {
    @Override
    public int compare(Maintenance o1, Maintenance o2) {

        return new Double(o1.getPrice()).compareTo(new Double(o2.getPrice()));
    }
}
