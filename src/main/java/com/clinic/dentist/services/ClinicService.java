package com.clinic.dentist.services;

import com.clinic.dentist.api.dao.IAppointmentDao;
import com.clinic.dentist.api.dao.IClinicDao;
import com.clinic.dentist.api.service.IClinicServices;
import com.clinic.dentist.models.Clinic;
import com.clinic.dentist.models.Dentist;
import com.clinic.dentist.models.Maintenance;
import com.clinic.dentist.repositories.ClinicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Component("clinicService")
public class ClinicService implements IClinicServices {
    //    @Autowired
//    private ClinicRepository clinicRepository;
    @Autowired
    @Qualifier("clinicDao")
    private IClinicDao clinicDao;

    public List<Clinic> findAll() {
        return clinicDao.getAll();
    }

    public Clinic findClinicByAddress(String address) {
        return clinicDao.findClinicByAddress(address);

    }

    public Iterable<Maintenance> findMaintenancesByClinic(Long ClinicId) {
        return clinicDao.findMaintenancesByClinic(ClinicId);
    }

    public boolean checkExist(long id) {
        return clinicDao.checkExist(id);
    }

    public Clinic findById(Long id) {
        return clinicDao.findById(id);
    }

    public List<Dentist> findDentistsByClinic(Long clinicId) {
      return  clinicDao.findDentistsByClinic(clinicId);
    }
}
