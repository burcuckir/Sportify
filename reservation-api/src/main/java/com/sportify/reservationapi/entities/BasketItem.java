package com.sportify.reservationapi.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "basket_item")
public class BasketItem extends BaseEntity{

    @OneToOne
    @JoinColumn(name = "schedule_id", nullable = false, unique = true)
    private Schedule schedule;

    @ManyToOne
    @JoinColumn(name = "basket_id", nullable = false)
    private Basket basket;

}
