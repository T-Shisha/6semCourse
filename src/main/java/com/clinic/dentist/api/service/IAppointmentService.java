package com.clinic.dentist.api.service;

import com.clinic.dentist.models.*;

import java.util.ArrayList;
import java.util.List;

public interface IAppointmentService {

    ArrayList<Appointment> getAppointmentsWithActiveForPatient(Long id_patient);

    ArrayList<Appointment> getAppointmentsWithActive(List<Appointment> allAppointment);

    void saveAppointment(Long clinicId, Long maintenanceId, Long dentistId, Long patientId, String date, String time);

    Appointment findById(Long id);

    void deleteAppointment(Long id);

    void changeDentistStatusInAppointment(Long id);

    List<Appointment> getAppointmentsWithActiveForDentist(Long id);

    List<Appointment> getActualAppointmentsForDoctor(Dentist dentist, String date);

    List<Appointment> getActualAppointmentsForClinic(Clinic clinic, String date);
}
