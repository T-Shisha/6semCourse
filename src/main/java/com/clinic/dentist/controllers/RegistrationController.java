package com.clinic.dentist.controllers;

import com.clinic.dentist.models.Patient;
import com.clinic.dentist.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;
import java.util.Map;

@Controller
public class RegistrationController {
    @Autowired
    private PatientService patientService;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String add(Patient patient, Map<String, Object> models) {
        if (!patientService.checkPatient(patient)) {
            patientService.addPatient(patient);
            return "login";

        }
        models.put("message", "User exits!");
        return "registration";
    }
}
