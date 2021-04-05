package com.clinic.dentist.services;

import com.clinic.dentist.models.Admin;
import com.clinic.dentist.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public Admin findUser(String login, String password) {
        password=bCryptPasswordEncoder.encode(password);
        Admin admin = adminRepository.findByUsername(login).orElseThrow(RuntimeException::new);
        if (admin.getPassword().equals(password)) {
            return admin;
        } else {
            throw new RuntimeException("Invalid password");
        }
    }
}
