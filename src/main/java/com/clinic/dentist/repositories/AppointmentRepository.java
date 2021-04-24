package com.clinic.dentist.repositories;

import com.clinic.dentist.models.Appointment;
import com.clinic.dentist.models.Clinic;
import com.clinic.dentist.models.Dentist;
import com.clinic.dentist.models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findAllByDentistAndDate(Dentist dentist, String date);

    List<Appointment> findAllByDentist(Dentist dentist);

    List<Appointment> findAllByPatient(Patient patient);

    List<Appointment> findAllByClinicAAndDate(Clinic clinic, String date);
}
