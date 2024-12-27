package com.sportify.reservationapi.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "branch")
public class Branch extends BaseEntity{

    private String name;

    @OneToMany(mappedBy = "branch")
    private List<Facility> facilities;

    private Boolean isActive;
}

