package com.clinic.dentist.controllers;

import com.clinic.dentist.date.DateSystem;
import com.clinic.dentist.date.TimeSystem;
import com.clinic.dentist.models.Clinic;
import com.clinic.dentist.models.Dentist;
import com.clinic.dentist.models.Maintenance;
import com.clinic.dentist.services.ClinicService;
import com.clinic.dentist.services.MaintenanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PatientController {
    @Autowired
    private ClinicService clinicService;
    @Autowired
    private MaintenanceService maintenanceService;
    private TimeSystem timeSystem = new TimeSystem();

    @GetMapping("/user")
    public String greeting(Model model) {
        List<Clinic> clinics = clinicService.findAll();
        model.addAttribute("clinics", clinics);
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


        } else if (!new DateSystem().checkFreeDay(Date, id2)) {


            if (!timeSystem.checkFreeTime(Date, id2)) {

                model.addAttribute("dates", DateSystem.NextDay());
                return ("choiceOfDate");

            } else if (!timeSystem.checkFreeTimeForService(Date, id2, id1)) {

                model.addAttribute("dates", DateSystem.NextDay());
                return ("choiceOfDate");

            }
        }


        return "redirect:/user/" + id + "/clinic/" + id1 + "/maintenance/" + id2 + "/dentist";


    }

    @GetMapping("/user/{id}/clinic/{id1}/maintenance/{id2}/dentist/{date}")
    public String ShowTime(@PathVariable(value = "id") long id, @PathVariable(value = "id1") long id1, @PathVariable(value = "id2") long id2,
                           @PathVariable(value = "date") String date, Model model) {
        model.addAttribute("clinicId", id);
        model.addAttribute("serviceId", id1);
        model.addAttribute("dentistId", id2);
        model.addAttribute("Date", date);
        model.addAttribute("time", timeSystem.getFreeTimeForService(date, id2, id1));
        return ("choiceOfTime");
    }
}
