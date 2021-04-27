package com.clinic.dentist.dao;

import com.clinic.dentist.api.dao.IAppointmentDao;
import com.clinic.dentist.models.Appointment;
import com.clinic.dentist.models.Clinic;
import com.clinic.dentist.models.Dentist;
import com.clinic.dentist.models.Patient;
import com.clinic.dentist.repositories.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("appointmentDao")
public class AppointmentDao implements IAppointmentDao {
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Override
    public void save(Appointment entity) {
        appointmentRepository.save(entity);
    }

    @Override
    public Appointment findById(long id) {
        return appointmentRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    public List<Appointment> findByDentistAndDate(Dentist dentist, String date) {
        return appointmentRepository.findAllByDentistAndDate(dentist, date);

    }

    @Override
    public List<Appointment> getAll() {
        return appointmentRepository.findAll();
    }

    public List<Appointment> findAllByPatient(Patient patient) {
        return appointmentRepository.findAllByPatient(patient);
    }

    public void deleteAppointment(Appointment appointment) {
        appointmentRepository.delete(appointment);

    }

    public List<Appointment> findAllByDentist(Dentist dentist) {
        return appointmentRepository.findAllByDentist(dentist);
    }
    public List<Appointment> findAllByClinicAndDate(Clinic clinic, String date){
      return  appointmentRepository.findAllByClinicAndDate(clinic, date);
    }
}
