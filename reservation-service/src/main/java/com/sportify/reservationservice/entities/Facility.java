package com.sportify.reservationservice.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "facility")
public class Facility extends BaseEntity{

    private String name;

    private Boolean isActive;

    @ManyToOne
    @JoinColumn(name = "branchId", nullable = false)
    private Branch branch;

    @OneToMany(mappedBy = "facility")
    private List<Schedule> schedules;
}
