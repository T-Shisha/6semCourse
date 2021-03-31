package com.clinic.dentist.models;

import javax.persistence.*;
import java.util.List;
import java.util.Set;
@Entity
public class Dentist {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String phoneNumber;
    private String position;

    @OneToMany(mappedBy = "dentist", cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    private List<Appointment> orders;
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "dentist_maintenance",
            joinColumns = @JoinColumn(name = "Dentist"),
            inverseJoinColumns = @JoinColumn(name = "Maintenance")
    )
    private Set<Maintenance> maintenances;
    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "dentist_id", nullable = false)
    private Clinic clinic;
}
