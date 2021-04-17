package com.clinic.dentist.controllers;

import com.clinic.dentist.models.Appointment;
import com.clinic.dentist.models.Patient;
import com.clinic.dentist.services.AppointmentService;
import com.clinic.dentist.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collections;
import java.util.List;

@Controller
public class AdminController {
    @Autowired
    private PatientService patientService;
    @Autowired
    private AppointmentService appointmentService;

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
        List<Appointment> appointments = appointmentService.getAppointmentsWithActive(patient.getId());
        Collections.reverse(appointments);
        model.addAttribute("patient", patient);

        model.addAttribute("orders", appointments);
        return "ordersPatients";
    }

    @GetMapping("/admin/patients/{id}/orders/remove/{id1}")
    public String getUnregisteredPatientView(@PathVariable(value = "id") long id, @PathVariable(value = "id1") long id1, Model model) {


        appointmentService.deleteAppointment(id1);
        Patient patient = patientService.findById(id);
        List<Appointment> appointments = appointmentService.getAppointmentsWithActive(patient.getId());
        Collections.reverse(appointments);
        model.addAttribute("orders", appointments);
        model.addAttribute("patient", patient);
        return "ordersPatients";

    }
}
