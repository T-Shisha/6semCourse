package com.clinic.dentist.controllers;

import com.clinic.dentist.date.DateSystem;
import com.clinic.dentist.date.TimeConverter;
import com.clinic.dentist.models.*;
import com.clinic.dentist.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    @Autowired
    private ClinicService clinicService;

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

    private Dentist den;

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

    @GetMapping("/admin/dentists/add")
    public String createDentist(Model model) {
        List<Clinic> clinics = clinicService.findAll();
        model.addAttribute("dentist", new Dentist());
        return "createDentist";
    }

    @PostMapping("/admin/dentists/add")
    public String getDentist(@ModelAttribute(name = "dentist") Dentist dentist, Model model) {
        den = null;
        if (dentist.getFirstName().trim().equals("")) {
            model.addAttribute("firstNameError", "Имя не введено");

            return "createDentist";
        } else if (dentist.getLastName().trim().equals("")) {

            model.addAttribute("lastNameError", "Фамилия не введена");

            return "createDentist";
        } else if (dentist.getPatronymic().trim().equals("")) {

            model.addAttribute("patronymicError", "Отчество не введено");

            return "createDentist";
        } else if (dentist.getPhoneNumber().trim().equals("")) {

            model.addAttribute("phoneNumberError", "Номер телефона не введен");


            return "createDentist";
        } else if (dentist.getClinic().equals("")) {

            model.addAttribute("clinicError", "Клиника не выбрана");

            return "createDentist";
        }
        Pattern pattern = Pattern.compile("^(375)[0-9]{9}$");
        Matcher matcher = pattern.matcher(dentist.getPhoneNumber());
        if (!matcher.matches()) {

            model.addAttribute("phoneNumberError", "Номер телефона введен не корректно");
            return "createDentist";
        } else if (dentistService.findDentistByPhoneNumber(dentist.getPhoneNumber().trim())) {

            model.addAttribute("phoneNumberError", "Врач с данным номером телефона зарегистрирован");

            return "createDentist";
        }
        dentist.setFirstName(dentist.getFirstName().trim());
        dentist.setLastName(dentist.getLastName().trim());
        dentist.setPhoneNumber(dentist.getPhoneNumber().trim());
        dentist.setPatronymic(dentist.getPatronymic().trim());


        den = dentist;
        return "redirect:/admin/dentists/add/services";
    }

    @GetMapping("/admin/dentists/add/services")
    public String getServices(Model model) {
        Iterable<Maintenance> maintenanceList = clinicService.findMaintenancesByClinic(den.getClinic().getId());
        model.addAttribute("services", maintenanceList);
        if (den != null) {
            model.addAttribute("dentist", den);
        }
        return "choiceServicesForDentist";

    }

    @PostMapping("/admin/dentist/services")
    public String finishCreateDentist1(@RequestParam(required = false) String[] services, Model model) {
        if (services == null) {
            Iterable<Maintenance> maintenanceList = clinicService.findMaintenancesByClinic(den.getClinic().getId());
            model.addAttribute("services", maintenanceList);
            model.addAttribute("servicesError", "Услуги не выбраны");
            model.addAttribute("dentist", den);
            return "createDentistEnd";
        }
        den.setMaintenances(maintenanceService.getSetFromArrayMaintenance(services));
        dentistService.addEntity(den);
        den = null;
        return "redirect:/admin/dentists";

    }

}
