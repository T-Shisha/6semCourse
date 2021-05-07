package com.clinic.dentist.controllers;

import com.clinic.dentist.api.service.IPatientService;
import com.clinic.dentist.models.Dentist;
import com.clinic.dentist.models.Patient;
import com.clinic.dentist.models.Role;
import com.clinic.dentist.repositories.PatientRepository;
import com.clinic.dentist.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class RegistrationController {
    @Autowired
    @Qualifier("patientService")
    private IPatientService patientService;


    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("patient", new Patient());
        model.addAttribute("success", "");

        return "registration";
    }


    @PostMapping("/registration")
    public String addPatient(@ModelAttribute(name = "patient") Patient patient, Model model) {
        model.addAttribute("success", "");

        if (patient.getFirstName().trim().equals("")) {
            model.addAttribute("firstNameError", "Имя не введено");
            return "registration";

        }
        if (patient.getLastName().trim().equals("")) {
            model.addAttribute("lastNameError", "Фамилия не введена");
            return "registration";

        }
        if (patient.getUsername().trim().equals("")) {
            model.addAttribute("phoneNumberError", "Номер телефона не введен");
            return "registration";

        }
        if (patient.getPassword().equals("")) {
            model.addAttribute("passwordError", "Пароль не введен");
            return "registration";

        }
        if (patient.getPasswordConfirm().equals("")) {
            model.addAttribute("passwordConfirmError", "Повторно пароль не введен");
            return "registration";

        }
        if (!patient.getPassword().equals(patient.getPasswordConfirm())) {
            model.addAttribute("passwordConfirmError", "Пароли не совпадают");
            return "registration";
        }

        Pattern pattern = Pattern.compile("^(375)[0-9]{9}$");
        Matcher matcher = pattern.matcher(patient.getUsername());
        if (!matcher.matches()) {
            model.addAttribute("phoneNumberError", "Номер телефона введен не корректно");
            return "registration";
        }
        if (patientService.checkPatient(patient)) {
            model.addAttribute("phoneNumberError", "Данный номер уже зарегистрирован");
            return "registration";
        }

        patientService.addPatient(patientService.correctData(patient));
        model.addAttribute("success", "Ваша заявка на регистрацию будет рассмотрена");
        model.addAttribute("patient", new Patient());

        return "registration";
    }

}
