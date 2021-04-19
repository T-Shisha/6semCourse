package com.clinic.dentist.controllers;

import com.clinic.dentist.models.Maintenance;
import com.clinic.dentist.models.TypeServices;
import com.clinic.dentist.services.MaintenanceService;
import com.clinic.dentist.services.TypeServicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class MainController {
    @Autowired
    private TypeServicesService typeServicesService;
    @Autowired
    private MaintenanceService maintenanceService;

    @GetMapping("/")
    public String greeting(Model model) {
        return "main";
    }

    @GetMapping("/types")
    public String getTypes(Model model) {
        List<TypeServices> types = typeServicesService.getAll();
        model.addAttribute("types", types);
        return "servicesType";
    }

    @GetMapping("/types/{id}")
    public String getServices(@PathVariable(value = "id") long id, Model model) {
        TypeServices typeServices= typeServicesService.findById(id);
        List<Maintenance> services =typeServicesService.getSortedAscendingMaintenancesByPrice(typeServices) ;
        model.addAttribute("services", services);
        return "services";
    }
}