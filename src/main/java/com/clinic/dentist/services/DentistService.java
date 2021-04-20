package com.clinic.dentist.services;


import com.clinic.dentist.comparators.dentists.DentistAlphabetComparator;
import com.clinic.dentist.comparators.maintenances.MaintenanceAlphabetComparator;
import com.clinic.dentist.converters.ListConverter;
import com.clinic.dentist.converters.SetConverter;
import com.clinic.dentist.date.TimeSystem;
import com.clinic.dentist.models.Appointment;
import com.clinic.dentist.models.Clinic;
import com.clinic.dentist.models.Maintenance;
import com.clinic.dentist.repositories.AppointmentRepository;
import com.clinic.dentist.repositories.DentistRepository;
import com.clinic.dentist.models.Dentist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DentistService {
    @Autowired
    private DentistRepository dentistRepository;
    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private MaintenanceService maintenanceService;

    public List<Dentist> getAll() {
        return dentistRepository.findAll();
    }

    public List<Dentist> sortbyAlphabet() {
        List<Dentist> dentists = getAll();
        return dentists
                .stream()
                .sorted(new DentistAlphabetComparator())
                .collect(Collectors.toList());
    }

    public Dentist findById(Long id) {
        Dentist dentist = dentistRepository.findById(id).orElseThrow(RuntimeException::new);
        return dentist;

    }

    public boolean checkFreeTime(String date, Long id_dentist)////не занят ли весь день у доктора
    {
        Dentist dentist = dentistRepository.findById(id_dentist).orElseThrow(RuntimeException::new);
        ArrayList<String> time = getFreeTimesDentistWithPatients(date, dentist);

        if (time.size() == 0) {
            return false;
        }
        return true;
    }

    public boolean checkFreeDay(String date, Long id_dentist)////проверка на наличие в этот день записей
    {
        int h = 2;
        Dentist dentist = findById(id_dentist);
        List<Appointment> orders = appointmentService.findByDentistAndDate(dentist, date);
        if (orders.size() != 0) {
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
        ArrayList<String> time_day = TimeSystem.getAllWorkTime();
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

    public ArrayList<String> getFreeTimeForService(String date, Long dentistId, Long maintenanceId)////время для записи определенной услуги
    {
        Dentist dentist = findById(dentistId);
        Maintenance maintenance = maintenanceService.findById(maintenanceId);
        ArrayList<String> time = new ArrayList<String>();
        if (!checkFreeDay(date, dentist.getId())) {
            time = getFreeTimesDentistWithPatients(date, dentist);
        } else {
            time = TimeSystem.getAllWorkTime();
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

    public boolean checkFreeTimeForService(String date, Long id_dentist, Long id_service) {

        ArrayList<String> list = getFreeTimeForService(date, id_dentist, id_service);
        if (list.size() == 0) {
            return false;
        }
        return true;
    } //////влезает ли услуга в расписание врача

    public boolean checkExist(long id) {
        return dentistRepository.existsById(id);
    }

    public boolean deleteEntity(long id) {
        Dentist dentist = dentistRepository.findById(id).orElseThrow();

        if (dentist.getOrders() != null) {
            List<Appointment> list = ListConverter.getList(dentist.getOrders());
            for (Appointment appointment : list) {

                appointmentService.deleteAppointment(appointment.getId());

            }
        }

        Set<Maintenance> serviceSet = SetConverter.getSet(dentist.getMaintenances());
        Iterator<Maintenance> iterator = serviceSet.iterator();
        while (iterator.hasNext()) {
            Maintenance item = iterator.next();
            dentist.deleteService(item);
        }

        dentistRepository.delete(dentist);
        Clinic clinic = dentist.getClinic();
        clinic.getDentists().remove(dentist);
        if (appointmentRepository.findAllByDentist(dentist) != null) {
            return false;
        }
        return true;

    }
}
