package com.clinic.dentist.models;

import javax.persistence.*;

@Entity
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String password;
    private String login;
    @Transient
    private final Role role=Role.PATIENT;
}
