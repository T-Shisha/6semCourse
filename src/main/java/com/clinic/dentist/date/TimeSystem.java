package com.clinic.dentist.date;

import com.clinic.dentist.models.Appointment;
import com.clinic.dentist.models.Dentist;
import com.clinic.dentist.models.Maintenance;
import com.clinic.dentist.repositories.AppointmentRepository;
import com.clinic.dentist.repositories.DentistRepository;
import com.clinic.dentist.repositories.MaintenanceRepository;
import com.clinic.dentist.services.AppointmentService;
import com.clinic.dentist.services.ClinicService;
import com.clinic.dentist.services.DentistService;
import com.clinic.dentist.services.MaintenanceService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class TimeSystem {
    public static ArrayList<String> getAllWorkTime()/////закидываем в лист все рабочее время
    {
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 9; i < 18; i++) {
            if (i != 13) {
                for (int j = 0; j < 60; j = j + 10) {
                    String k = String.valueOf(i) + ":" + String.valueOf(j);
                    if (j == 0) {
                        k = k + "0";
                    }
                    list.add(k);

                }
            }
        }
        return list;
    }



}
