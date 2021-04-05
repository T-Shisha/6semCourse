package com.clinic.dentist.repositories;

import com.clinic.dentist.models.Appointment;
import com.clinic.dentist.models.Dentist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findAllByDentistAndDate(Dentist dentist, String date);

}
