package com.clinic.dentist.controllers;

import com.clinic.dentist.models.Patient;
import com.clinic.dentist.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AdminController {
    @Autowired
    private PatientService patientService;
    @GetMapping("/admin")
    public String greeting(Model model) {
        return "admin";
    }

    @GetMapping("/admin/patients")
    public String getPatientView(Model model) {
        List<Patient> patients=patientService.getRegisteredPatients();
        model.addAttribute("patients", patients);
        return "patient";
    }
}
