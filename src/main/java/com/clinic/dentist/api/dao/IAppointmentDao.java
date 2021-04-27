package com.clinic.dentist.api.dao;

import com.clinic.dentist.models.*;

import java.util.List;

public interface IAppointmentDao extends GenericDao<Appointment> {
    List<Appointment> findByDentistAndDate(Dentist dentist, String date);

    List<Appointment> findAllByPatient(Patient patient);

    void deleteAppointment(Appointment appointment);

    List<Appointment> findAllByDentist(Dentist dentist);

    List<Appointment> findAllByClinicAndDate(Clinic clinic, String date);
}
