package com.clinic.dentist.services;

import com.clinic.dentist.comparators.time.TimeComparator;
import com.clinic.dentist.date.DateSystem;
import com.clinic.dentist.date.TimeConverter;
import com.clinic.dentist.models.*;
import com.clinic.dentist.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

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

    public List<Appointment> findByDentistAndDate(Dentist dentist, String date) {
        return appointmentRepository.findAllByDentistAndDate(dentist, date);

    }

    public ArrayList<Appointment> getAppointmentsWithActive(Long id_patient) {
        Date thisDay = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatForTimeNow = new SimpleDateFormat("HH:mm");
        String dat = formatForDateNow.format(thisDay);
        String time = formatForTimeNow.format(thisDay);
        Date date1 = TimeConverter.getDateFromString(dat);
        Patient patient = patientRepository.findById(id_patient).orElseThrow(RuntimeException::new);
        ArrayList<Appointment> allAppointment = (ArrayList<Appointment>) appointmentRepository.findAllByPatient(patient);
        ArrayList<Appointment> appointments = new ArrayList<Appointment>();
        for (Appointment appointment : allAppointment) {
            if (TimeConverter.getDateFromString(appointment.getDate()).after(date1)) {
                appointment.setActive(true);
                appointments.add(appointment);
                continue;
            }
            if (TimeConverter.getDateFromString(appointment.getDate()).equals(date1)) {
                if (TimeComparator.compare(appointment.getTime(), time) == 1) {
                    appointment.setActive(true);
                    appointments.add(appointment);

                }


            }
            appointment.setActive(false);
            appointments.add(appointment);


        }
        Collections.sort(appointments);
        return appointments;

    }

    public void saveAppointment(Long clinicId, Long maintenanceId, Long dentistId, Long patientId, String date, String time) {
        Patient patient = patientRepository.findById(patientId).orElseThrow(RuntimeException::new);
        Dentist dentist = dentistRepository.findById(dentistId).orElseThrow(RuntimeException::new);
        Clinic clinic = clinicRepository.findById(clinicId).orElseThrow(RuntimeException::new);
        Maintenance maintenance = maintenanceRepository.findById(maintenanceId).orElseThrow(RuntimeException::new);
        Appointment appointment = new Appointment(clinic, maintenance, dentist, patient, date, time);
        appointmentRepository.save(appointment);
    }

    public Appointment findById(Long id) {
        return appointmentRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    public void deleteAppointment(Long id) {
        Appointment appointment = findById(id);
        appointmentRepository.delete(appointment);

    }
}
