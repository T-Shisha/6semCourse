package com.clinic.dentist.dao;

import com.clinic.dentist.api.dao.IAdminDao;
import com.clinic.dentist.models.Admin;
import com.clinic.dentist.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
@Component("adminDao")
public class AdminDao implements IAdminDao {
    @Autowired
    private AdminRepository adminRepository;

    @Override
    public void save(Admin entity) {
        adminRepository.save(entity);
    }

    @Override
    public Admin findById(long id) {
        return adminRepository.findById(id).orElseThrow(RuntimeException::new);
    }


    @Override
    public List<Admin> getAll() {
        return adminRepository.findAll();
    }


}
