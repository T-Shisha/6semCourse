package com.clinic.dentist.models;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
public class Maintenance {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @ManyToMany(mappedBy = "maintenances", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private List<Clinic> clinics;
    private int time;
    private int price;
    private String description;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "maintenance", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private Set<Appointment> orders;
    @ManyToMany(mappedBy = "maintenances", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private List<Dentist> dentists;
    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "type_id", nullable = false)
    private TypeServices type;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<Dentist> getDentists() {
        return dentists;
    }

    public int getTime() {
        return time;
    }

    public Long getId() {
        return id;
    }

    public double getPrice() {
        return this.price;
    }
}
