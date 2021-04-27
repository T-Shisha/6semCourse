package com.clinic.dentist.api.service;

import com.clinic.dentist.comparators.time.TimeComparator;
import com.clinic.dentist.date.TimeConverter;
import com.clinic.dentist.models.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public interface IAppointmentService {
    List<Appointment> findByDentistAndDate(Dentist dentist, String date);

    ArrayList<Appointment> getAppointmentsWithActiveForPatient(Long id_patient);

    ArrayList<Appointment> getAppointmentsWithActive(List<Appointment> allAppointment);

    void saveAppointment(Long clinicId, Long maintenanceId, Long dentistId, Long patientId, String date, String time);

    Appointment findById(Long id);

    void deleteAppointment(Long id);

    void changeDentistStatusInAppointment(Long id);

    List<Appointment> getAppointmentsWithActiveForDentist(Long id);

    ArrayList<Appointment> getActualAppointmentsForDoctor(Dentist dentist, String date);

    List<Appointment> getActualAppointmentsForClinic(Clinic clinic, String date);
}
