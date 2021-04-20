package com.clinic.dentist.converters;

import com.clinic.dentist.models.Appointment;

import java.util.ArrayList;
import java.util.List;

public class ListConverter {
    public static List<Appointment> getList(List<Appointment> list) {
        List<Appointment> appointments = new ArrayList<Appointment>();
        for (Appointment a : list) {
            appointments.add(a);

        }
        return appointments;
    }
}
