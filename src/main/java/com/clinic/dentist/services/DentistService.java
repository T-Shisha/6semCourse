package com.clinic.dentist.services;

import com.clinic.dentist.models.Dentist;
import com.clinic.dentist.repositories.DentistRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class DentistService {
    @Autowired
    private DentistRepository dentistRepository;

    public Dentist findById(Long id) {
        return dentistRepository.findById(id).orElseThrow(RuntimeException::new);

    }
}
