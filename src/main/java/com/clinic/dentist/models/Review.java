package com.clinic.dentist.models;

import javax.persistence.*;

public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;
}
