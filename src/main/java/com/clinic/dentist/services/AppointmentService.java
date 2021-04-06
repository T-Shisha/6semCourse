package com.clinic.dentist.services;

import com.clinic.dentist.models.*;
import com.clinic.dentist.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private DentistRepository dentistRepository;
    @Autowired
    private ClinicRepository clinicRepository;
    @Autowired
    private MaintenanceRepository maintenanceRepository;

    public void saveAppointment(Long clinicId, Long maintenanceId, Long dentistId, Long patientId, String date, String time) {
        Patient patient = patientRepository.findById(patientId).orElseThrow(RuntimeException::new);
        Dentist dentist = dentistRepository.findById(dentistId).orElseThrow(RuntimeException::new);
        Clinic clinic = clinicRepository.findById(clinicId).orElseThrow(RuntimeException::new);
        Maintenance maintenance = maintenanceRepository.findById(maintenanceId).orElseThrow(RuntimeException::new);
        Appointment appointment = new Appointment(clinic, maintenance, dentist, patient, date, time);
        appointmentRepository.save(appointment);
    }
}
