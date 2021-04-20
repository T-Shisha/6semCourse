package com.clinic.dentist.models;

import com.clinic.dentist.comparators.time.TimeComparator;
import com.clinic.dentist.date.TimeConverter;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Appointment implements Comparable<Appointment> {
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

    @Override
    public int compareTo(Appointment o) {

        Date date1 = TimeConverter.getDateFromString(this.date);
        Date date2 = TimeConverter.getDateFromString(o.getDate());

        int i = date1.compareTo(date2);
        if (i != 0) return i;


        i = TimeComparator.compare(this.time, o.getTime());
        return i;

    }

    public Appointment() {
    }

    public String getDate() {
        return date;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    public Dentist getDentist() {
        return dentist;
    }

    public Clinic getClinic() {
        return clinic;
    }

    public Long getId() {
        return id;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setDentist(Dentist dentist) {
        this.dentist = dentist;
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
