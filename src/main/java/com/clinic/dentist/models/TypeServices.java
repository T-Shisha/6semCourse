package com.clinic.dentist.models;

import javax.persistence.*;
import java.util.Set;

@Entity
public class TypeServices {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "type", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private Set<Maintenance> maintenances;

    public Set<Maintenance> getMaintenances() {
        return maintenances;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }
}
