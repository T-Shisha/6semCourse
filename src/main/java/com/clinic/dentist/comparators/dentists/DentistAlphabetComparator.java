package com.clinic.dentist.comparators.dentists;

import com.clinic.dentist.models.Dentist;

import java.util.Comparator;

public class DentistAlphabetComparator implements Comparator<Dentist> {
    @Override
    public int compare(Dentist o1, Dentist o2) {

        int i = o1.getLastName().compareTo(o2.getLastName());
        if (i != 0) {
            return i;
        }
        i = o1.getFirstName().compareTo(o2.getFirstName());
        if (i != 0) {
            return i;
        }
        i = o1.getPatronymic().compareTo(o2.getPatronymic());

        return i;


    }
}
