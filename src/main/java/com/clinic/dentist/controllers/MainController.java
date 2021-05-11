package com.clinic.dentist.controllers;

import com.clinic.dentist.api.service.IMaintenanceService;
import com.clinic.dentist.api.service.ITypeServicesService;
import com.clinic.dentist.models.Maintenance;
import com.clinic.dentist.models.TypeServices;
import com.clinic.dentist.services.MaintenanceService;
import com.clinic.dentist.services.TypeServicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {
    @Autowired
    @Qualifier("typeServicesService")
    private ITypeServicesService typeServicesService;
    @Autowired
    @Qualifier("maintenanceService")
    private IMaintenanceService maintenanceService;

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

    @GetMapping("/services")
    public String getService(Model model) {
        List<TypeServices> types = typeServicesService.getAll();
        List<Maintenance> services = maintenanceService.sorted();
        model.addAttribute("services", services);
        model.addAttribute("types", types);
        return "serviceMain";
    }

    @GetMapping("/contacts")
    public String getContacts(Model model) {

        return "contacts";
    }

    @GetMapping("/about")
    public String getAbout(Model model) {
        return "about";
    }

    @GetMapping("/services/{id}")
    public String getServices(@PathVariable(value = "id") long id, Model model) {
        List<TypeServices> types = typeServicesService.getAll();
        TypeServices typeServices = typeServicesService.findById(id);
        List<Maintenance> services = typeServicesService.getSortedAscendingMaintenancesByPrice(typeServices);

        model.addAttribute("services", services);
        model.addAttribute("types", types);
        return "serviceMain";

    }

}