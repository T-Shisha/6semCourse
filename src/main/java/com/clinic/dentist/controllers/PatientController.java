package com.clinic.dentist.controllers;

import com.clinic.dentist.models.Clinic;
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

    @GetMapping("/user")
    public String greeting(Model model) {
        List<Clinic> clinics = clinicService.findAll();
        model.addAttribute("clinics", clinics);
        return "user";
    }

    @PostMapping("/user")
    public String chooseClinic(@RequestParam String address, Model model) {
        Clinic necessaryClinic = clinicService.findClinicByAddress(address);

        return "redirect:/user/" + necessaryClinic.getId() + "/clinic";
    }

    @GetMapping("/user/{id}/clinic")
    public String showService(@PathVariable(value = "id") long id, Model model) {
        Iterable<Maintenance> maintenances = clinicService.findMaintenancesByClinic(id);
        model.addAttribute("maintenances", maintenances);
        return "choiceOfMaintenance";
    }

    @PostMapping("/user/{id}/clinic")
    public String chooseService(@PathVariable(value = "id") long id, @RequestParam String maintenance, Model model) {
        Maintenance necessaryMaintenance = maintenanceService.findByName(maintenance);

        return "redirect:/user/" + id + "/clinic/"+necessaryMaintenance.getId()+"/maintenance";
    }
}
