package com.clinic.dentist.controllers;

import com.clinic.dentist.date.DateSystem;
import com.clinic.dentist.date.TimeConverter;
import com.clinic.dentist.models.Appointment;
import com.clinic.dentist.models.Dentist;
import com.clinic.dentist.models.Maintenance;
import com.clinic.dentist.models.Patient;
import com.clinic.dentist.services.AppointmentService;
import com.clinic.dentist.services.DentistService;
import com.clinic.dentist.services.MaintenanceService;
import com.clinic.dentist.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class AdminController {
    @Autowired
    private PatientService patientService;
    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private MaintenanceService maintenanceService;
    @Autowired
    private DentistService dentistService;

    @GetMapping("/admin")
    public String greeting(Model model) {
        return "admin";
    }

    @GetMapping("/admin/patients")
    public String getPatientView(Model model) {
        List<Patient> patients = patientService.getRegisteredPatients();
        model.addAttribute("patients", patients);
        return "patient";
    }

    @GetMapping("/admin/patients/register")
    public String getUnregisteredPatientView(Model model) {
        List<Patient> patients = patientService.getUnregisteredPatients();
        model.addAttribute("patients", patients);
        return "unregisteredPatients";
    }

    @GetMapping("/admin/patients/register/{id}")
    public String getUnregisteredPatientView(@PathVariable(value = "id") long id, Model model) {
        patientService.registeredPatient(id);
        List<Patient> patients = patientService.getUnregisteredPatients();
        model.addAttribute("patients", patients);
        return "unregisteredPatients";
    }

    @GetMapping("/admin/patients/{id}/orders")
    public String getPatientOrders(@PathVariable(value = "id") long id, Model model) {
        Patient patient = patientService.findById(id);
        List<Appointment> appointments = appointmentService.getAppointmentsWithActiveForPatient(patient.getId());
        Collections.reverse(appointments);
        model.addAttribute("patient", patient);

        model.addAttribute("orders", appointments);
        return "ordersPatients";
    }

    @GetMapping("/admin/patients/{id}/orders/remove/{id1}")
    public String getUnregisteredPatientView(@PathVariable(value = "id") long id, @PathVariable(value = "id1") long id1, Model model) {


        appointmentService.deleteAppointment(id1);
        Patient patient = patientService.findById(id);
        List<Appointment> appointments = appointmentService.getAppointmentsWithActiveForPatient(patient.getId());
        Collections.reverse(appointments);
        model.addAttribute("orders", appointments);
        model.addAttribute("patient", patient);
        return "ordersPatients";

    }

    @GetMapping("/admin/services")
    public String getService(Model model) {
        List<Maintenance> maintenances = maintenanceService.sortByName();
        model.addAttribute("services", maintenances);
        return ("servicesAdmin");

    }

    @GetMapping("/admin/dentists")
    public String showDentists(Model model) {
        List<Dentist> dentists = dentistService.sortbyAlphabet();
        model.addAttribute("dentists", dentists);
        return ("doctors");
    }

    @GetMapping("/admin/dentists/{id}/remove")
    public String removeDentist(@PathVariable(value = "id") long id, Model model) {
        if (!dentistService.checkExist(id)) {
            return "redirect:/admin/dentists";
        }
        boolean delete = dentistService.deleteEntity(id);
        if (!delete) {
            return "redirect:/admin/dentists/" + id + "/remove";
        }

        return "redirect:/admin/dentists";

    }

    @PostMapping("/admin/dentists/{id}/appointments")
    public String getAppointmentDentistDate(@PathVariable(value = "id") long id, Model model, @RequestParam String Date) {
        if (!dentistService.checkExist(id)) {
            return "redirect:/admin/dentists";
        }

        List<Appointment> appointments = null;
        Dentist dentist = dentistService.findById(id);
        model.addAttribute(dentist);

        if (DateSystem.checkWeekend(Date)) {
            appointments = appointmentService.getActualAppointmentsForDoctor(dentist, DateSystem.NextDay());
            model.addAttribute("dates", DateSystem.NextDay());
            model.addAttribute("norm_date", TimeConverter.getDate2(DateSystem.NextDay()));

        } else {
            appointments = appointmentService.getActualAppointmentsForDoctor(dentist, Date);
            model.addAttribute("dates", Date);
            model.addAttribute("norm_date", Date);


        }
        Collections.sort(appointments);

        model.addAttribute("orders", appointments);
        return "adminDentistAppointment";

    }


    @GetMapping("/admin/dentists/{id}/appointments")
    public String getAppointment(@PathVariable(value = "id") long id, Model model) {
        if (!dentistService.checkExist(id)) {
            return "redirect:/admin/dentists";
        }
        Date thisDay = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy-MM-dd");
        String dat = formatForDateNow.format(thisDay);
        List<Appointment> appointments;
        Dentist dentist = dentistService.findById(id);
        model.addAttribute(dentist);
        if (DateSystem.checkWeekend(dat)) {
            appointments = appointmentService.getActualAppointmentsForDoctor(dentist, DateSystem.NextDay());
            model.addAttribute("dates", DateSystem.NextDay());
            model.addAttribute("norm_date", TimeConverter.getDate2(DateSystem.NextDay()));

        } else {
            appointments = appointmentService.getActualAppointmentsForDoctor(dentist, dat);
            model.addAttribute("dates", dat);
            model.addAttribute("norm_date", dat);

        }
        Collections.sort(appointments);
        model.addAttribute("orders", appointments);

        return "adminDentistAppointment";

    }
}
