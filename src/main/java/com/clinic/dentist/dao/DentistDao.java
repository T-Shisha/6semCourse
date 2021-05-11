package com.clinic.dentist.dao;

import com.clinic.dentist.api.dao.IDentistDao;
import com.clinic.dentist.comparators.dentists.DentistAlphabetComparator;
import com.clinic.dentist.models.Clinic;
import com.clinic.dentist.models.Dentist;
import com.clinic.dentist.models.Maintenance;
import com.clinic.dentist.repositories.ClinicRepository;
import com.clinic.dentist.repositories.DentistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component("dentistDao")
public class DentistDao implements IDentistDao {
    @Autowired
    private DentistRepository dentistRepository;
    @Autowired
    private ClinicRepository clinicRepository;
    @Override
    public void save(Dentist entity) {
       Clinic clinic=entity.getClinic();
       Clinic cl=clinicRepository.findById(clinic.getId()).orElseThrow();
       entity.setClinic(cl);

        dentistRepository.save(entity);


    }

    public boolean checkExist(long id) {
        return dentistRepository.existsById(id);
    }

    @Override
    public Dentist findById(long id) {
        return dentistRepository.findById(id).orElseThrow(RuntimeException::new);

    }

    public void deleteEntity(Dentist entity) {
        dentistRepository.delete(entity);
        ;
    }
    public List<Dentist> sortbyAlphabet() {
        List<Dentist> dentists = getAll();
        return dentists
                .stream()
                .sorted(new DentistAlphabetComparator())
                .collect(Collectors.toList());
    }
    public boolean findDentistByPhoneNumber(String phoneNumber) {
        return dentistRepository.findByPhoneNumber(phoneNumber).isPresent();
    }
    public boolean checkDentistHaveMaintenance(Long id, Maintenance maintenance) {
        Dentist dentist = findById(id);
        return dentist.getMaintenances().contains(maintenance);
    }
    @Override
    public List<Dentist> getAll() {
        List<Dentist> dentists=dentistRepository.findAll();
        List<Dentist> dentists1=new ArrayList<>();
        long id=2;
        for(Dentist dentist: dentists){
            if(!dentist.getId().equals(id)){
                dentists1.add(dentist);
            }
        }
        return dentists1;
    }
}
