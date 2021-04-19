package com.clinic.dentist.controllers;

import com.clinic.dentist.date.DateSystem;
import com.clinic.dentist.date.TimeSystem;
import com.clinic.dentist.models.*;
import com.clinic.dentist.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class PatientController {
    @Autowired
    private ClinicService clinicService;
    @Autowired
    private DentistService dentistService;
    @Autowired
    private MaintenanceService maintenanceService;
    @Autowired
    private PatientService patientService;
    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/user")
    public String greeting(Model model) {
        List<Clinic> clinics = clinicService.findAll();
        model.addAttribute("clinics", clinics);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        Patient patient = patientService.findUserByUsername(name);
        List<Appointment> appointments = appointmentService.getAppointmentsWithActive(patient.getId());
        Collections.reverse(appointments);
        model.addAttribute("orders", appointments);
        return "user";
    }

    @PostMapping("/user")
    public String chooseClinic(@RequestParam String clinicId, Model model) {

        return "redirect:/user/" + clinicId + "/clinic";
    }

    @GetMapping("/user/{id}/clinic")
    public String showService(@PathVariable(value = "id") long id, Model model) {
        Iterable<Maintenance> maintenances = clinicService.findMaintenancesByClinic(id);
        model.addAttribute("maintenances", maintenances);
        model.addAttribute("clinicId", id);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        Patient patient = patientService.findUserByUsername(name);
        List<Appointment> appointments = appointmentService.getAppointmentsWithActive(patient.getId());
        Collections.reverse(appointments);
        model.addAttribute("orders", appointments);
        return "choiceOfMaintenance";
    }

    @PostMapping("/user/{id}/clinic")
    public String chooseService(@PathVariable(value = "id") long id, @RequestParam String maintenanceId, Model model) {

        return "redirect:/user/" + id + "/clinic/" + maintenanceId + "/maintenance";
    }

    @GetMapping("/user/{id}/clinic/{id1}/maintenance")
    public String showDentists(@PathVariable(value = "id") long id, @PathVariable(value = "id1") long id1, Model model) {
        List<Dentist> dentists = maintenanceService.findDentistsByMaintenanceAndClinic(id1, id);
        model.addAttribute("dentists", dentists);
        model.addAttribute("clinicId", id);
        model.addAttribute("serviceId", id1);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        Patient patient = patientService.findUserByUsername(name);
        List<Appointment> appointments = appointmentService.getAppointmentsWithActive(patient.getId());
        Collections.reverse(appointments);
        model.addAttribute("orders", appointments);
        return "choiceOfDentist";
    }

    @PostMapping("/user/{id}/clinic/{id1}/maintenance")
    public String chooseDentist(@PathVariable(value = "id") long id, @PathVariable(value = "id1") long id1, @RequestParam String dentistId, Model model) {
        return "redirect:/user/" + id + "/clinic/" + id1 + "/maintenance/" + dentistId + "/dentist";

    }

    @GetMapping("/user/{id}/clinic/{id1}/maintenance/{id2}/dentist")
    public String chooseDate1(@PathVariable(value = "id") long id, @PathVariable(value = "id1") long id1, @PathVariable(value = "id2") long id2,
                              Model model) {
        model.addAttribute("clinicId", id);

        model.addAttribute("serviceId", id1);
        model.addAttribute("dentistId", id2);
        model.addAttribute("dates", DateSystem.NextDay());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        Patient patient = patientService.findUserByUsername(name);
        List<Appointment> appointments = appointmentService.getAppointmentsWithActive(patient.getId());
        Collections.reverse(appointments);
        model.addAttribute("orders", appointments);
        return "choiceOfDate";

    }

    @PostMapping("/user/{id}/clinic/{id1}/maintenance/{id2}/dentist")
    public String chooseDate(@PathVariable(value = "id") long id, @PathVariable(value = "id1") long id1, @PathVariable(value = "id2") long id2,
                             @RequestParam String Date, Model model) {
        model.addAttribute("serviceId", id1);
        model.addAttribute("dentistId", id2);
        model.addAttribute("clinicId", id);

        if (DateSystem.checkWeekend(Date.trim())) {

            model.addAttribute("dates", DateSystem.NextDay());
            return ("choiceOfDate");


        } else if (dentistService.checkFreeDay(Date, id2)) {


            if (!dentistService.checkFreeTime(Date, id2)) {

                model.addAttribute("dates", DateSystem.NextDay());
                return ("choiceOfDate");

            } else if (!dentistService.checkFreeTimeForService(Date, id2, id1)) {

                model.addAttribute("dates", DateSystem.NextDay());
                return ("choiceOfDate");

            }
        }


        return "redirect:/user/" + id + "/clinic/" + id1 + "/maintenance/" + id2 + "/dentist/" + Date;


    }

    @GetMapping("/user/{id}/clinic/{id1}/maintenance/{id2}/dentist/{date}")
    public String ShowTime(@PathVariable(value = "id") long id, @PathVariable(value = "id1") long id1, @PathVariable(value = "id2") long id2,
                           @PathVariable(value = "date") String date, Model model) {
        model.addAttribute("clinicId", id);
        model.addAttribute("serviceId", id1);
        model.addAttribute("dentistId", id2);
        model.addAttribute("Date", date);
        model.addAttribute("time", dentistService.getFreeTimeForService(date, id2, id1));
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        Patient patient = patientService.findUserByUsername(name);
        List<Appointment> appointments = appointmentService.getAppointmentsWithActive(patient.getId());
        Collections.reverse(appointments);
        model.addAttribute("orders", appointments);
        return ("choiceOfTime");
    }

    @PostMapping("/user/{id}/clinic/{id1}/maintenance/{id2}/dentist/{date}")
    public String ShowTime(@PathVariable(value = "id") long id, @PathVariable(value = "id1") long id1, @PathVariable(value = "id2") long id2,
                           @PathVariable(value = "date") String date, @RequestParam String time, Model model) {
        return "redirect:/user/" + id + "/clinic/" + id1 + "/maintenance/" + id2 + "/dentist/" + date + "/" + time;

    }

    @GetMapping("/user/{id}/clinic/{id1}/maintenance/{id2}/dentist/{date}/{time}")
    public String ShowOrder(@PathVariable(value = "id") long id, @PathVariable(value = "id1") long id1, @PathVariable(value = "id2") long id2,
                            @PathVariable(value = "date") String date, @PathVariable(value = "time") String time, Model model) {

        model.addAttribute("clinic", clinicService.findById(id));
        model.addAttribute("service", maintenanceService.findById(id1));
        model.addAttribute("dentist", dentistService.findById(id2));
        model.addAttribute("date", date);
        model.addAttribute("time", time);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        Patient patient = patientService.findUserByUsername(name);
        List<Appointment> appointments = appointmentService.getAppointmentsWithActive(patient.getId());
        Collections.reverse(appointments);
        model.addAttribute("orders", appointments);
        return ("orderPatient");

    }

    @PostMapping("/user/{id}/clinic/{id1}/maintenance/{id2}/dentist/{date}/{time}")
    public String chooseOrder(@PathVariable(value = "id") long id, @PathVariable(value = "id1") long id1, @PathVariable(value = "id2") long id2,
                              @PathVariable(value = "date") String date, @PathVariable(value = "time") String time, Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        Patient patient = patientService.findUserByUsername(name);
        appointmentService.saveAppointment(id, id1, id2, patient.getId(), date, time);
        return "redirect:/user";

    }
    @GetMapping("/client/orders/{id}/remove")
    public String getUnregisteredPatientView(@PathVariable(value = "id") long id, Model model) {

        appointmentService.deleteAppointment(id);
        List<Clinic> clinics = clinicService.findAll();
        model.addAttribute("clinics", clinics);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        Patient patient = patientService.findUserByUsername(name);
        List<Appointment> appointments = appointmentService.getAppointmentsWithActive(patient.getId());
        Collections.reverse(appointments);
        model.addAttribute("orders", appointments);
        return "user";

    }
}
