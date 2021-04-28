package com.clinic.dentist.services;

import com.clinic.dentist.api.dao.IAdminDao;
import com.clinic.dentist.api.dao.IAppointmentDao;
import com.clinic.dentist.api.service.IAdminService;
import com.clinic.dentist.models.Admin;
import com.clinic.dentist.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component("adminService")
public class AdminService implements IAdminService {
    @Autowired
    @Qualifier("adminDao")
    private IAdminDao adminDao;


}
