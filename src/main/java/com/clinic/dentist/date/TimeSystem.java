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
    @Autowired
    private AppointmentService orderService;
    @Autowired
    private DentistRepository dentistRepository;
    @Autowired
    private MaintenanceRepository maintenanceRepository;
    @Autowired
    private DateSystem dateSystem;
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private DentistService dentistService;
    @Autowired
    private MaintenanceService maintenanceService;

    public boolean checkFreeTime(String date, Long id_dentist)////не занят ли весь день у доктора
    {
        Dentist dentist = dentistRepository.findById(id_dentist).orElseThrow(RuntimeException::new);
        ArrayList<String> time = getFreeTimesDentistWithPatients(date, dentist);

        if (time.size() == 0) {
            return false;
        }
        return true;
    }

    public ArrayList<String> getFreeTimesDentistWithPatients(String date, Dentist dentist)/////свободное время доктора, учитывая записи
    {

        int kol_min_priem;
        // ArrayList<Appointment> list = orderService.getScheduleDentistThisDay(dentist, date);
        List<Appointment> list = appointmentRepository.findAllByDentistAndDate(dentist, date);
        ArrayList<String> busy_time = new ArrayList<String>();
        ArrayList<String> time = new ArrayList<String>();
        ArrayList<String> time_day = getAllWorkTime();
        for (int i = 0; i < time_day.size(); i++) {
            for (Appointment orders : list) {
                kol_min_priem = orders.getMaintenance().getTime() / 10;
                if (orders.getMaintenance().getTime() - 10 * kol_min_priem != 0) {
                    kol_min_priem++;
                }
                if (orders.getTime().trim().equals(time_day.get(i).trim())) {

                    for (int j = 0; j < kol_min_priem; j++) {

                        busy_time.add(time_day.get(i + j));
                    }

                }
            }

        }
        for (int i = 0; i < time_day.size(); i++) {
            boolean b = true;
            for (int j = 0; j < busy_time.size(); j++) {
                if (busy_time.get(j) == time_day.get(i)) {
                    b = false;
                    break;
                }

            }
            if (b) {
                time.add(time_day.get(i));
            }
        }


        return time;

    }

    public ArrayList<String> getAllWorkTime()/////закидываем в лист все рабочее время
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

    public boolean checkFreeTimeForService(String date, Long id_dentist, Long id_service) {

        ArrayList<String> list = getFreeTimeForService(date, id_dentist, id_service);
        if (list.size() == 0) {
            return false;
        }
        return true;
    } //////влезает ли услуга в расписание врача

    public ArrayList<String> getFreeTimeForService(String date, Long dentistId, Long maintenanceId)////время для записи определенной услуги
    {
        Dentist dentist = dentistService.findById(dentistId);
        Maintenance maintenance = maintenanceService.findById(maintenanceId);
        ArrayList<String> time = new ArrayList<String>();
        if (!dateSystem.checkFreeDay(date, dentist.getId())) {
            time = getFreeTimesDentistWithPatients(date, dentist);
        } else {
            time = getAllWorkTime();
        }
        ArrayList<String> list = new ArrayList<String>();

        int kol_min_priem = maintenance.getTime() / 10;

        if (maintenance.getTime() - 10 * kol_min_priem != 0) {
            kol_min_priem++;
        }
        ;

        if (kol_min_priem != 1) {
            for (int i = 0; i < time.size(); i++) {
                boolean l = true;
                if (i + kol_min_priem <= time.size()) {
                    for (int j = i; j < i + kol_min_priem; j++) {


                        if (j != i) {

                            String[] stroka = time.get(j).split(":");
                            String[] prev_stroka = time.get(j - 1).split(":");
                            if (stroka[0].equals(prev_stroka[0])) {
                                if (Integer.parseInt(stroka[1]) != Integer.parseInt(prev_stroka[1]) + 10) {
                                    l = false;
                                    break;
                                }
                            } else {
                                if (Integer.parseInt(stroka[0]) - Integer.parseInt(prev_stroka[0]) != 1) {
                                    l = false;
                                    break;
                                } else if (Integer.parseInt(stroka[0]) - Integer.parseInt(prev_stroka[0]) == 1) {
                                    if (!stroka[1].equals("00") || !prev_stroka[1].equals("50")) {
                                        l = false;
                                        break;
                                    }
                                }
                            }

                        }

                    }
                    if (l) {
                        list.add(time.get(i));
                    }
                }

            }
        } else {
            list = time;
        }
        return list;
    }

}
