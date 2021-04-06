package com.clinic.dentist.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PatientController {
    @GetMapping("/user")
    public String greeting(Model model) {
        return "user";
    }
}
