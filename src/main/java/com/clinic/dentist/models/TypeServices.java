package com.clinic.dentist.models;

import javax.persistence.*;
import java.util.Set;

public class TypeServices {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "type", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private Set<Maintenance> maintenances;
}
