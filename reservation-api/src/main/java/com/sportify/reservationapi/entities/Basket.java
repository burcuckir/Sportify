package com.sportify.reservationapi.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "basket")
public class Basket extends BaseEntity {

    @Column(nullable = false)
    private UUID userId;

    @OneToMany(mappedBy = "basket", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BasketItem> basketItems;

    public Double getTotalAmount(){
        Double totalAmount = 0.0;
        for (BasketItem item : basketItems) {
            totalAmount += item.getSchedule().getPrice();
        }
        return totalAmount;
    }
}
