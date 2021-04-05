package com.clinic.dentist.models;

import javax.persistence.*;

@Entity
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String time;
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

    public Maintenance getMaintenance() {
        return maintenance;
    }

    public String getTime() {
        return time;
    }

    public Appointment() {
    }

    public Appointment(Clinic clinic, Maintenance maintenance, Dentist dentist, Patient patient, String date, String time) {
        this.dentist = dentist;
        this.maintenance = maintenance;
        this.date = date;
        this.time = time;
        this.clinic = clinic;
        this.patient = patient;
    }
}
