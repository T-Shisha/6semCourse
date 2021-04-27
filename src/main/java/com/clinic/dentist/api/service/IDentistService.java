package com.clinic.dentist.api.service;

import com.clinic.dentist.comparators.dentists.DentistAlphabetComparator;
import com.clinic.dentist.converters.ListConverter;
import com.clinic.dentist.converters.SetConverter;
import com.clinic.dentist.date.TimeSystem;
import com.clinic.dentist.models.Appointment;
import com.clinic.dentist.models.Clinic;
import com.clinic.dentist.models.Dentist;
import com.clinic.dentist.models.Maintenance;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public interface IDentistService {
    List<Dentist> getAll();

    List<Dentist> sortbyAlphabet();

    Dentist findById(Long id);

    boolean checkFreeTime(String date, Long id_dentist);

    boolean checkFreeDay(String date, Long id_dentist);

    ArrayList<String> getFreeTimesDentistWithPatients(String date, Dentist dentist);

    ArrayList<String> getFreeTimeForService(String date, Long dentistId, Long maintenanceId);

    boolean checkFreeTimeForService(String date, Long id_dentist, Long id_service);

    boolean checkExist(long id);

    boolean deleteEntity(long id);

    void addEntity(Dentist entity);

    boolean checkDentistHaveMaintenance(Long id, Maintenance maintenance);

    boolean findDentistByPhoneNumber(String phoneNumber);
}
