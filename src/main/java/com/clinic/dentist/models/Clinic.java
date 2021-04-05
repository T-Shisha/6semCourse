package com.clinic.dentist.models;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
public class Clinic {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String address;
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "clinic_maintenance",
            joinColumns = @JoinColumn(name = "Clinic"),
            inverseJoinColumns = @JoinColumn(name = "Maintenance")
    )
    private Set<Maintenance> maintenances;
    @OneToMany(mappedBy = "clinic", cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    private List<Dentist> dentists;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "clinic", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private Set<Appointment> orders;

    public Long getId() {
        return id;
    }

    public Set<Maintenance> getMaintenances() {
        return maintenances;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Clinic clinic = (Clinic) o;
        return Objects.equals(id, clinic.id) &&
                Objects.equals(address, clinic.address) &&
                Objects.equals(maintenances, clinic.maintenances) &&
                Objects.equals(dentists, clinic.dentists) &&
                Objects.equals(orders, clinic.orders);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, address, maintenances, dentists, orders);
    }
}
