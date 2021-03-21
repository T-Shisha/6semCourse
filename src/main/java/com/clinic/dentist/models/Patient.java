package com.clinic.dentist.models;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String password;
    @Transient
    private String passwordConfirm;
    @SuppressWarnings("JpaAttributeTypeInspection")
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "patient", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private Set<Appointment> orders;
    @SuppressWarnings("JpaAttributeTypeInspection")
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "patient", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private Set<Review> reviews;
}
