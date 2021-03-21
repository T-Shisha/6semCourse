package com.clinic.dentist.models;

import javax.persistence.*;

public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String Time;
    private String date;
    @Transient
    private boolean active;
    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;
    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "dentist_id", nullable = false)
    private Dentist dentist;
    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "maintenance_id", nullable = false)
    private Maintenance maintenance;
    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "clinic_id", nullable = false)
    private Clinic clinic;
}
