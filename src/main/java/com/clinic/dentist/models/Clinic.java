package com.clinic.dentist.models;

import javax.persistence.*;
import java.util.List;
import java.util.Set;
@Entity
public class Clinic {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String address;
    @ManyToMany(fetch = FetchType.EAGER,cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "clinic_maintenance",
            joinColumns = @JoinColumn(name = "Clinic"),
            inverseJoinColumns = @JoinColumn(name = "Maintenance")
    )
    private Set<Maintenance> maintenances;
    @OneToMany(mappedBy = "clinic", cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    private List<Dentist> dentists;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "clinic", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private Set<Appointment> orders;
}
