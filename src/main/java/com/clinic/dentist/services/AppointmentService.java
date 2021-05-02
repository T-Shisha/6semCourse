package com.clinic.dentist.services;

import com.clinic.dentist.api.dao.*;
import com.clinic.dentist.api.service.IAppointmentService;
import com.clinic.dentist.comparators.time.TimeComparator;
import com.clinic.dentist.date.TimeConverter;
import com.clinic.dentist.models.*;
import com.clinic.dentist.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
@Component("appointmentService")
public class AppointmentService implements IAppointmentService {
    @Autowired
    @Qualifier("appointmentDao")
    private IAppointmentDao appointmentDao;
    @Autowired
    @Qualifier("patientDao")
    private IPatientDao patientDao;

    @Autowired
    @Qualifier("maintenanceDao")
    private IMaintenanceDao maintenanceDao;
    @Autowired
    @Qualifier("clinicDao")
    private IClinicDao clinicDao;


    @Autowired
    @Qualifier("dentistDao")
    private IDentistDao dentistDao;


//    public List<Appointment> findByDentistAndDate(Dentist dentist, String date) {
//        return appointmentRepository.findAllByDentistAndDate(dentist, date);
//
//    }

    public List<Appointment> getAll() {
        return appointmentDao.getAll();
    }

    public ArrayList<Appointment> getAppointmentsWithActiveForPatient(Long id_patient) {

        Patient patient = patientDao.findById(id_patient);
        ArrayList<Appointment> allAppointment = (ArrayList<Appointment>) appointmentDao.findAllByPatient(patient);
        return getAppointmentsWithActive(allAppointment);

    }

    public ArrayList<Appointment> getAppointmentsWithActive(List<Appointment> allAppointment) {
        Date thisDay = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatForTimeNow = new SimpleDateFormat("HH:mm");
        String dat = formatForDateNow.format(thisDay);
        String time = formatForTimeNow.format(thisDay);
        Date date1 = TimeConverter.getDateFromString(dat);

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
                    continue;

                }


            }
            appointment.setActive(false);
            appointments.add(appointment);


        }
        Collections.sort(appointments);
        return appointments;

    }

    public void saveAppointment(Long clinicId, Long maintenanceId, Long dentistId, Long patientId, String date, String time) {
       try {
           Patient patient = patientDao.findById(patientId);
           Dentist dentist = dentistDao.findById(dentistId);
           Clinic clinic = clinicDao.findById(clinicId);
           Maintenance maintenance = maintenanceDao.findById(maintenanceId);
           Appointment appointment = new Appointment(clinic, maintenance, dentist, patient, date, time);
           appointmentDao.save(appointment);
       }catch (RuntimeException ex){
           ex.printStackTrace();
       }
    }

    public Appointment findById(Long id) {
        return appointmentDao.findById(id);
    }

    public void deleteAppointment(Long id) {
        Appointment appointment = findById(id);
        appointmentDao.deleteAppointment(appointment);

    }



    public List<Appointment> getAppointmentsWithActiveForDentist(Long id) {
        Dentist dentist = dentistDao.findById(id);
        ArrayList<Appointment> allAppointment = (ArrayList<Appointment>) appointmentDao.findAllByDentist(dentist);
        return getAppointmentsWithActive(allAppointment);
    }

    public List<Appointment> getActualAppointmentsForDoctor(Dentist dentist, String date) {

        ArrayList<Appointment> allAppointmeintsInThisDay = (ArrayList<Appointment>) appointmentDao.findByDentistAndDate(dentist, date);
        return getAppointmentsWithActive(allAppointmeintsInThisDay);

    }

    public List<Appointment> getActualAppointmentsForClinic(Clinic clinic, String date) {

        ArrayList<Appointment> allAppointmeintsInThisDay = (ArrayList<Appointment>) appointmentDao.findAllByClinicAndDate(clinic, date);
        return getAppointmentsWithActive(allAppointmeintsInThisDay);

    }
}
